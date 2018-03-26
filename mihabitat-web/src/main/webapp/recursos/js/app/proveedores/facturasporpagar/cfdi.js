var CFDI = function (data) {

    var self = this;

    self.id = ko.observable();
    self.proveedor = new Proveedor(data ? data.proveedor : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.fechaEmision = ko.observable(data ? data.fechaEmision : undefined);
    self.fechaVencimiento = ko.observable(data ? data.fechaVencimiento : undefined);
    self.rfc = ko.observable(data ? data.rfc : undefined);
    self.emisor = ko.observable(data ? data.emisor : undefined);
    self.conceptos = ko.observableArray(data ? data.conceptos : []);
    self.uuid = ko.observable(data ? data.uuid : undefined);
    self.impuestoTrasladado = ko.observable(data ? data.impuestoTrasladado : 0);
    self.impuestoRetenido = ko.observable(data ? data.impuestoRetenido : 0);
    self.total = ko.observable(data ? data.total : 0);
    self.tipo = ko.observable(data ? data.tipo : undefined);
    self.archivo = new Archivo(data ? data.archivo : undefined);
    self.formadepago = ko.observable(data ? data.formadepago : undefined);
    self.metododepago = ko.observable(data ? data.metododepago : undefined);
    self.condiciondepago = ko.observable(data ? data.condiciondepago : undefined);
    //self.movimientos = ko.observableArray(data ? data.movimientos : []);
    self._antiguedad = ko.observable(data ? data.rfc : undefined);

    self.movimientoImpuestoRetenido = new MovimientoCfdi(data ? data.movimiento : undefined);
    self.movimientoImpuestoTrasladado = new MovimientoCfdi(data ? data.movimiento : undefined);

    //Propiedad temporal para pagos
    self.pagoTemporal = ko.observable();

    self.aplicaPago = ko.observable();

    self.pagoTemporal.subscribe(function (data) {
        self.pagoTemporal(numeral(data).format("0,0.00"))
    });

    self.tooltipTitle = ko.observable(self.uuid());
    self.tooltipPlacement = ko.observable('right');

    self.totalCalculado = ko.computed(function () {
        var tot = 0;
        if (self.conceptos().length > 0) {
            ko.utils.arrayForEach(self.conceptos(), function (c) {
                if (c.importe()) {
                    tot = c.importe() + tot;
                }
            });
        }
        else {
            self.impuestoTrasladado(0);
            self.impuestoRetenido(0);
        }
        tot = tot + parseFloat(self.impuestoTrasladado()) - parseFloat(self.impuestoRetenido());
        self.total(tot);
        return tot;
    });

    // ATRIBUTOS PARA PAGOS
    self.pagado = ko.computed(function () {
        var pagado = 0;
        ko.utils.arrayForEach(self.conceptos(), function (concepto) {
            pagado = pagado + concepto.movimientoCfdi.pagado();
        });
        pagado = pagado + self.movimientoImpuestoTrasladado.pagado();
        return pagado;
    });

    self.pendiente = ko.computed(function () {
        return (self.total() - self.pagado()).toFixed(2);
    });

    self.limpiar = function () {
        self.id(undefined);
        self.proveedor.limpiar();
        self.condominio.limpiar();
        self.fechaEmision(undefined);
        self.fechaVencimiento(undefined);
        self.rfc(undefined);
        self.emisor(undefined);
        self.conceptos([]);
        self.uuid(undefined);
        self.impuestoTrasladado(0);
        self.impuestoRetenido(0);
        self.total(0);
        self.tipo(undefined);
        self.archivo.limpiar(undefined);
        self.formadepago(undefined);
        self.metododepago(undefined);
        self.condiciondepago(undefined);
        //self.movimientos([]);
        self._antiguedad(undefined);
        self.movimientoImpuestoRetenido.limpiarMCP();
        self.movimientoImpuestoTrasladado.limpiarMCP();
    }


    self.cargar = function (data) {

        self.id(data ? data.id : undefined);
        self.proveedor.cargar(data ? data.proveedor : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.fechaEmision(data ? data.fechaEmision : undefined);
        self.fechaVencimiento(data ? data.fechaVencimiento : undefined);
        self.rfc(data ? data.rfc : undefined);
        self.emisor(data ? data.emisor : undefined);

        if (data && data.conceptos && data.conceptos.length > 0) {
            ko.utils.arrayForEach(data.conceptos, function (c) {
                var cd = new DetalleFactura();
                cd.cargar(c);
                self.conceptos.push(cd);
            });
        }
        self.uuid(data ? data.uuid : undefined);
        self.impuestoTrasladado(data ? data.impuestoTrasladado : undefined);
        self.impuestoRetenido(data ? data.impuestoRetenido : undefined);
        self.total(data ? data.total : 0);
        self.tipo(data ? data.tipo : undefined);
        self.archivo.cargar(data ? data.archivo : undefined);
        self.formadepago(data ? data.formadepago : undefined);
        self.metododepago(data ? data.metododepago : undefined);
        self.condiciondepago(data ? data.condiciondepago : undefined);

        /*if (data && data.movimientos && data.movimientos.length > 0) {
         ko.utils.arrayForEach(data.movimientos, function(c) {
         var cd = new MovimientoCfdi();
         cd.cargar(c);
         self.movimientos.push(cd);
         });
         }*/
        self._antiguedad(data ? data._antiguedad : undefined);

        self.movimientoImpuestoRetenido.cargarMCP(data ? data.movimientoImpuestoRetenido : undefined);
        self.movimientoImpuestoTrasladado.cargarMCP(data ? data.movimientoImpuestoTrasladado : undefined);
    }


    self.getJson = function () {
        var factura = self.estructurar(ko.toJS(self));
        factura = validarObject(factura);
        return factura;
    }


    self.estructurar = function (data) {
        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }

        if (data && data.proveedor) {
            data.proveedor = {
                id: self.proveedor.id()
            }
        }
        if (data.conceptos && data.conceptos.length > 0) {
            var conceptos = [];
            ko.utils.arrayForEach(self.conceptos(), function (c) {
                var cd = c.getJson(data.fechaEmision);
                conceptos.push(cd);
            });
            data.conceptos = conceptos;
        }
        data.impuestoTrasladado = parseFloat(self.impuestoTrasladado());
        data.impuestoRetenido = parseFloat(self.impuestoRetenido());
        data.total = parseFloat(self.total());

        /*var movimientos = [];
         if(self.movimientos().length > 0 ) {
         // actualizar
         console.log("actualizar..");

         } else {
         if( data.total > 0 ) {
         var movimiento = new MovimientoCfdi();
         movimiento.tipo.id( AppConfig.catalogos.movimientos.tipos.cargo )
         movimiento.haber( data.total );
         movimiento.fecha( data.fechaEmision );
         movimiento.cancelado(false);
         movimientos.push( movimiento.getJsonMCP() );
         }
         }
         data.movimientos = movimientos;*/

        if (data && data.movimientoImpuestoRetenido && data.movimientoImpuestoRetenido.id) {
            console.log("actualizar retenido..");
        } else {
            data.movimientoImpuestoRetenido = {
                fecha: data.fechaEmision,
                haber: data.impuestoRetenido,
                cancelado: false,

                tipo: {
                    id: AppConfig.catalogos.movimientos.tipos.retenido
                }
            };
        }

        if (data && data.movimientoImpuestoTrasladado && data.movimientoImpuestoTrasladado.id) {
            console.log("actualizar trasladado..");
        } else {
            data.movimientoImpuestoTrasladado = {
                fecha: data.fechaEmision,
                haber: data.impuestoTrasladado,
                cancelado: false,

                tipo: {
                    id: AppConfig.catalogos.movimientos.tipos.trasladado
                }
            };
        }
        delete data.totalCalculado;
        delete data._antiguedad;
        delete data.tooltipPlacement;
        delete data.pagado;
        delete data.pendiente;
        return data;
    }


    self.agregarConcepto = function () {
        var validos = true;
        if (self.conceptos().length > 0) {
            ko.utils.arrayForEach(self.conceptos(), function (c) {
                if (!c.regValido()) {
                    validos = false;
                    return false;
                }
            });
        }
        if (validos) {
            self.conceptos.push(new DetalleFactura());
        } else {
            notificacionAdvertencia("Debe llenar todos los campos del concepto");
        }

    }


    self.removerConcepto = function (data) {
        if (self.conceptos().length > 1) {
            bootbox.confirm("¿Deseas remover el concepto?", function (result) {
                if (result) {
                    self.conceptos.remove(data);
                }
            });
        } else {
            notificacionAdvertencia("Debe existir al menos un concepto");
        }
    }


    self.existe = function () {
        if (String.returnValue(self.uuid()).length > 0) {
            $.ajax({
                url: contextPath + '/administrador/facturasporpagar/existe',
                type: 'POST',
                dataType: 'json',
                data: {
                    condominio: self.condominio.id(),
                    uuid: self.uuid()
                },
                success: function (data) {
                    if (data.uuid) {
                        bootbox.dialog({
                            message: "Debe capturar un folio diferente debido a que el CFDI ya ha sido de alta en el sistema.",
                            title: "CFDI existente",
                            buttons: {
                                success: {
                                    label: "Aceptar",
                                    className: "btn-success",
                                    callback: function () {
                                        self.uuid(undefined);
                                    }
                                }
                            }
                        });
                    }

                }
            });
        }
    }
}
