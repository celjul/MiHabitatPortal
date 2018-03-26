var MovimientoCfdiAplicado = function() {
    Movimiento.call(this);
    var self = this;

    self.cancelado = ko.observable();
    self.aplicado = ko.observable();
    self.cuenta = new Cuenta();
    //self.movimientoCfdi = new MovimientoCfdi();
    //self.pagoProveedor = new PagoProveedor();
    self.tipo = new Catalogo();


    self.limpiarMCA = function() {
        self.cancelado(undefined);
        self.aplicado(undefined);
        self.cuenta.limpiar();
//        self.movimientoCfdi.limpiar();
//        self.pagoProveedor.limpiar();
        self.tipo.limpiar();
    }


    self.cargarMCA = function(data) {
        self.cargar(data);
        
        self.cancelado(data ? data.cancelado : undefined);
        self.aplicado(data ? data.aplicado : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);

        //self.movimientoCfdi.cargar(data ? data.movimientoCfdi : undefined);
        //self.pagoProveedor.cargar(data ? data.pagoProveedor : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
    }


    self.getJsonMCA = function() {
        var movimiento = self.estructurar(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }


    self.estructurar = function(data) {
        data = self.getJson();
        if (data && data.cuenta) {
            data.cuenta = self.cuenta.getJson();
        }
        if (data && data.tipo) {
            data.tipo = self.tipo.getJson();
        }
//        if (data && data.movimientoCfdi) {
//            data.movimientoCfdi = self.movimientoCfdi.getJson();
//        }
//        if (data && data.pagoProveedor) {
//            data.pagoProveedor = self.pagoProveedor.getJson();
//        }
        return data;
    }
}