var CfdiViewModel = function (data) {

    var self = this;

    self.facturasxp = new CFDI();

    if (data && data.facturasxp) {
        self.facturasxp.cargar(data.facturasxp);
    }

    if (data && data.condominio) {
        //console.log("Condominio:: " + data.condominio.id);
        self.facturasxp.condominio.cargar(data.condominio);
    }

    self.proveedores = ko.observableArray([]);
    if (data && data.proveedores) {
        self.proveedores(data.proveedores);
    }

    self.facturasxp.agregarConcepto();

    self.cuenta = ko.observableArray([]);
    if (data && data.cuenta) {
        self.cuenta(ordenar(data.cuenta, true));
    }

    self.tipo_cfdi = [
        {descripcion: "Ingreso"},
        {descripcion: "Egreso"}
    ];

    self.forma_pago = [
        {descripcion: "Pago en una sola exhibición"},
        {descripcion: "Pago en parcialidades"}
    ];

    self.metodosPago = ko.observableArray([]);
    if (data && data.metodosPago) {
        ko.utils.arrayForEach(data.metodosPago, function (mp) {
            var metodo = new Catalogo();
            metodo.cargar(mp);
            self.metodosPago.push(metodo);
        });
    }

    self.proveedorById = function () {
        if (self.facturasxp.proveedor.id()) {
            var p = ko.utils.arrayFirst(self.proveedores(), function (item) {
                return self.facturasxp.proveedor.id() == item.id;
            });
            self.facturasxp.proveedor.cargar(p);
            self.facturasxp.rfc(self.facturasxp.proveedor.rfc());
            self.facturasxp.emisor(self.facturasxp.proveedor.nombre());
            if (self.facturasxp.fechaEmision()) {
                self.calculaFechaVencimiento();
            }
        } else {
            self.facturasxp.proveedor.limpiar();
        }
    }

    self.calculaFechaVencimiento = function () {
        if (self.facturasxp.proveedor.diasCredito()) {
            var fecVenc = Date.convertir(self.facturasxp.fechaEmision());
            fecVenc.setDate(fecVenc.getDate() + self.facturasxp.proveedor.diasCredito());
            self.facturasxp.fechaVencimiento(Date.convertirFecha(fecVenc));
        }
        var fec = Date.convertir(self.facturasxp.fechaEmision());
        $('#fechaVencimiento').datepicker('option', 'minDate', new Date(fec));
    }

    self.guardar = function () {
        var validateFormFxp = $("#facturasporpagar-form").valid();
        if (validateFormFxp) {
            var fxp = JSON.stringify(self.facturasxp.getJson());
            console.log(fxp);
            $.ajax({
                url: contextPath + "/administrador/facturasporpagar/guardar",
                type: 'POST',
                dataType: 'json',
                data: fxp,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.facturasxp.limpiar();
                    //self.facturasxp.cargar(data);
                    notificacionExito("La factura se ha guardado correctamente");
                    //location.href = contextPath + '/administrador/facturasporpagar/lista';
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar la factura");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.actualizar = function () {
        if ($("#facturasporpagar-form").valid()) {
            var fxp = JSON.stringify(self.facturasxp.getJson());
            console.log(fxp);
            $.ajax({
                async: true,
                cache: false,
                url: contextPath + "/administrador/facturasporpagar/actualizar",
                type: 'POST',
                dataType: 'json',
                data: fxp,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    //self.facturasxp.limpiar();
                    self.facturasxp.cargar(data);
                    notificacionExito("La factura se ha actualizado correctamente");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al actualizar la factura");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }
}

var ListaFacturaPorPagarViewModel = function (data) {
    self = this;
    self.facturasxp = ko.observableArray([]);
    if (data.facturasxp != undefined && data.facturasxp.length > 0) {

        ko.utils.arrayForEach(data.facturasxp, function (f) {
            var cfdi = new CFDI();
            cfdi.cargar(f);
            self.facturasxp.push(cfdi);
        });
    }
    self.temporal = new CFDI();
    self.previewCFDI = function (data) {
        if (!data.proveedor.diasCredito()) {
            self.prepararAgregarProveedor(data);
        }
        self.temporal.emisor(data.emisor());
        self.temporal.rfc(data.rfc());
        self.temporal.fechaEmision(data.fechaEmision());
        self.temporal.fechaVencimiento(data.fechaVencimiento());
        self.temporal.uuid(data.uuid());
        self.temporal.tipo(data.tipo());
        self.temporal.formadepago(data.formadepago());
        self.temporal.metododepago(data.metododepago());
        self.temporal.condiciondepago(data.condiciondepago());
        self.temporal.conceptos(data.conceptos());
        self.temporal.impuestoRetenido(data.impuestoRetenido());
        self.temporal.impuestoTrasladado(data.impuestoTrasladado());
        self.temporal.total(data.total());
    }
    self.actualizar = function (data) {
        location.href = contextPath + "/administrador/facturasporpagar/actualizar/" + data.id();
    };
}