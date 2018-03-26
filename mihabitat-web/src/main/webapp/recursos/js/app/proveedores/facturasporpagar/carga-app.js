var CargaViewModel = function (data) {

    var self = this;

    self.cfdis = ko.observableArray([]);
    self.file = ko.observableArray([]);
    self.regCFDI = new CFDI();
    self.proveedor = new Proveedor();
    self.proveedor.esEmpleado(false);

    if (data && data.condominio) {
        console.log("Condominio:: " + data.condominio.id);
    }

    self.remover = function (data) {
        $.ajax({
            url: contextPath + "/administrador/facturasporpagar/removerCfdi",
            type: 'POST',
            dataType: 'json',
            data: {uuid: data.uuid},
            success: function (data) {
                //notificacionExito("Se eliminó registro");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Error al eliminar registro.");
            }
        });
        self.cfdis.remove(data);
    }


    self.giross = ko.observableArray([]);
    if (data && data.giros) {
        ko.utils.arrayForEach(data.giros, function (g) {
            var giro = new Catalogo();
            giro.cargar(g);
            self.giross.push(giro);
        });
    }

    self.cuenta = ko.observableArray([]);
    if (data && data.cuenta) {
        self.cuenta(ordenar(data.cuenta, true));
    }

    $('#fileupload').fileupload(
        {
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(xml)$/i,
            dataType: 'json',
            done: function (e, data1) {
                console.log(data1.uuid);
                if (data1.result.uuid != 'err') {
                    var factura = new CFDI();
                    factura.cargar(data1.result);
                    factura.condominio.id(data.condominio.id);
                    /*var factura = data1.result;
                     factura.archivo = {
                     nombre : data1.files[0].name,
                     tipo: data1.files[0].type
                     }*/
                    if (factura.proveedor.diasCredito()) {
                        console.log(factura.proveedor.cuenta.id());

                        ko.utils.arrayForEach(factura.conceptos(), function (g) {
                            g.cuenta.id(factura.proveedor.cuenta.id());
                        });
                        self.calculaFechaVencimiento(factura);
                    }
                    if (!factura.proveedor.id()) {
                        var rfc = factura.rfc();
                        console.log(rfc)
                        if (rfc.length == 12) {
                            factura.proveedor.tipoPersona("Moral");
                        } else {
                            factura.proveedor.tipoPersona("Física");
                        }
                    }
                    self.cfdis.push(factura);
                } else {
                    notificacionAdvertencia("La versión del CFDI debe ser 3.2.");
                }
            }, error: function (xhr, ajaxOptions, thrownError) {
            var excepcion = $.parseJSON(xhr.responseText);
            notificacionError(excepcion.error);
        },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total
                    * 100, 10);
                $('#progress .progress-bar').css('width',
                    progress + '%');
            }
        }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

    self.guardar = function () {
        if (self.cfdis().length != 0) {
            var b = ko.utils.arrayFilter(self.cfdis(), function (p) {
                return p.proveedor.id() == null;
            });
            if (b.length != 0) {
                notificacionAdvertencia("Existe al menos un proveedor que no se encuentra registrado en el sistema. " +
                    "La lista de CFDIs deben tener un proveedor ya registrado.");
            } else {
                var cfdiJson = [];
                ko.utils.arrayForEach(self.cfdis(), function (b) {
                    cfdiJson.push(b.getJson());
                });
                var cfdi = JSON.stringify(cfdiJson);
                console.log(cfdi);
                $.ajax({
                    url: contextPath + "/administrador/facturasporpagar/guardarcfdi",
                    type: 'POST',
                    dataType: 'json',
                    data: cfdi,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        self.cfdis([]);
                        notificacionExito("El CFDI se ha guardado correctamente");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Esta intentando cargar un folio de CFDI ya existente, por favor verifique.");
                    }
                });
            }
        } else {
            notificacionAdvertencia("Debe cargar al menos un CFDI");
        }
    }

    self.temporal = new CFDI();

    self.prepararAgregarProveedor = function (data) {
        self.proveedor.nombre(data.emisor());
        self.proveedor.rfc(data.rfc());
        self.proveedor.activo(true);
        self.proveedor.esEmpleado(false);
        self.proveedor.razonSocial(data.emisor());
        self.proveedor.condominio.id(data.condominio.id());
        self.proveedor.tipoPersona(data.proveedor.tipoPersona());
        self.proveedor.esFacturable(true);
        self.temporal = data;
    }


    self.previewCFDI = function (data) {
        if (!data.proveedor.diasCredito()) {
            self.prepararAgregarProveedor(data);
        }
        self.regCFDI.emisor(data.emisor());
        self.regCFDI.rfc(data.rfc());
        self.regCFDI.fechaEmision(data.fechaEmision());
        self.regCFDI.fechaVencimiento(data.fechaVencimiento());
        self.regCFDI.uuid(data.uuid());
        self.regCFDI.tipo(data.tipo());
        self.regCFDI.formadepago(data.formadepago());
        self.regCFDI.metododepago(data.metododepago());
        self.regCFDI.condiciondepago(data.condiciondepago());
        self.regCFDI.conceptos(data.conceptos());
        //self.regCFDI.conceptos()[0].cuenta.id(data.proveedor.cuenta.id());
        self.regCFDI.impuestoRetenido(data.impuestoRetenido());
        self.regCFDI.impuestoTrasladado(data.impuestoTrasladado());
        self.regCFDI.total(data.total());
    }


    self.calculaFechaVencimiento = function (f) {
        if (f.proveedor.diasCredito()) {
            var fecVenc = Date.convertir(f.fechaEmision());
            fecVenc.setDate(fecVenc.getDate() + f.proveedor.diasCredito());
            f.fechaVencimiento(Date.convertirFecha(fecVenc));
        }
        var fec = Date.convertir(f.fechaEmision());
        //$('#fechaVencimiento').datepicker('option', 'minDate', new Date(fec));
    }


    self.guardarProveedor = function () {
        var validateForm = $("#proveedor-form").valid();
        if (validateForm) {
            var proveedor = JSON.stringify(self.proveedor.getJson());
            console.log(proveedor);
            $.ajax({
                url: contextPath + "/administrador/facturasporpagar/registraProveedor",
                type: 'POST',
                dataType: 'json',
                data: proveedor,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.cfdis.remove(self.temporal);
                    var _cfdi = self.temporal;
                    _cfdi.proveedor.cargar(data);
                    self.cfdis.push(_cfdi);
                    self.calculaFechaVencimiento(_cfdi);
                    self.proveedor.limpiar();
                    notificacionExito("El proveedor se ha guardado correctamente");
                    $('#proveedorModal').modal('hide');
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el proveedor");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }
}
