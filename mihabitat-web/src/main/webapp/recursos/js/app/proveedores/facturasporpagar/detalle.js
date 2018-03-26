var DetalleFactura = function(data) {

    var self = this;

    self.id = ko.observable();
    self.cantidad = ko.observable(data ? data.cantidad : undefined);
    self.codigo = ko.observable(data ? data.codigo : undefined);
    self.descripcion = ko.observable(data ? data.descripcion : undefined);
    self.precioUnitario = ko.observable(data ? data.precioUnitario : undefined);
    self.importe = ko.observable(data ? data.importe : undefined);
    self.unidadDeMedida = ko.observable(data ? data.unidadDeMedida : undefined);
    self.cuenta = new Cuenta(data ? data.cuenta : undefined);
    self.movimientoCfdi = new MovimientoCfdi(data ? data.movimientoCfdi : undefined);

    self.importe = ko.computed(function () {
        return self.cantidad() * self.precioUnitario();
    });

    self.limpiar = function() {
        self.id (undefined);
        self.cantidad(undefined);
        self.codigo(undefined);
        self.descripcion(undefined);
        self.precioUnitario(undefined);
        //self.importe(undefined);
        self.unidadDeMedida(undefined);
        self.cuenta.limpiar();
        self.movimientoCfdi.limpiarMCP();
    }


    self.cargar = function(data) {
        console.log("cargar detalle del cfdi");

        self.id(data ? data.id : undefined);
        self.cantidad(data ? data.cantidad : undefined);
        self.codigo(data ? data.codigo : undefined);
        self.descripcion(data ? data.descripcion : undefined);
        self.precioUnitario(data ? data.precioUnitario : undefined);
        //self.importe(data ? data.importe : undefined);
        self.unidadDeMedida(data ? data.unidadDeMedida : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.movimientoCfdi.cargarMCP(data ? data.movimientoCfdi : undefined);
    }


    self.getJson = function(fecha) {
        var factura = self.estructurar(ko.toJS(self) , fecha);
        factura = validarObject(factura);
        return factura;
    }


    self.estructurar = function(data, fecha) {
        if (data && data.cuenta) {
            data.cuenta = {
                id: data.cuenta.id
            };
        }
        data.cantidad = parseInt(self.cantidad());
        data.precioUnitario = parseFloat(self.precioUnitario());
        data.importe = parseFloat(self.importe());

        if (data && data.movimientoCfdi && data.movimientoCfdi.id) {
            console.log("actualizar..");
        } else {
            data.movimientoCfdi = {
                fecha: fecha,
                haber: data.importe,
                cancelado: false,

                tipo: {
                    id: AppConfig.catalogos.movimientos.tipos.cfdi
                }
            };
        }
        console.log("valor de data" + JSON.stringify(data));
        return data;
    }

    self.regValido = function() {
        var valido = true;
        if (!self.cantidad() || self.cantidad() < 1) {
            valido = false;
        } else if (!self.unidadDeMedida() || self.unidadDeMedida() < 1) {
            valido = false;
        } else if (!self.codigo() || self.codigo() < 1) {
            valido = false;
        } else if (!self.descripcion() || self.descripcion() < 1) {
            valido = false;
        } else if (!self.precioUnitario() || self.precioUnitario() < 1) {
            valido = false;
        } else if (!self.importe() || self.importe() < 1) {
            valido = false;
        }
        return valido;
    }

}
