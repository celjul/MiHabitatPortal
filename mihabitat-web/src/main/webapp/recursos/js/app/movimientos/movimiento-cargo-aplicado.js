var MovimientoCargoAplicado = function() {

    Movimiento.call(this);
    var self = this;

    self.aplicado = ko.observable();
    self.cancelado = ko.observable();
    self.cuenta = new Cuenta();
    self.imprimible = ko.observable();
    //self.movimientoCargo = new MovimientoCargo();
//    self.pago = new Pago();
    self.tipo = new Catalogo();

    self.limpiarMCA = function() {
        self.aplicado(undefined);
        self.cancelado(undefined);
        self.cuenta.limpiar();
        self.imprimible(undefined);
        //self.movimientoCargo.limpiar();
//        self.pago.limpiar();
        self.tipo.limpiar();
        
        self.limpiar();
    }

    self.cargarMCA = function(data) {
        self.cancelado(data ? data.cancelado : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);
        //self.movimientoCargo.cargar(data ? data.movimientoCargo : undefined);
//        self.pago.cargar(data ? data.pago : undefined);
        self.aplicado(data ? data.aplicado : undefined);
        self.imprimible(data ? data.imprimible : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
        self.cargar(data);
    }

    self.getJsonMCA = function() {
        var movimiento = self.estructurar(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }

    self.estructurar = function(data) {
        data = self.getJson();
        /*if (data && data.cuenta) {
            data.cuenta = self.cuenta.getJson();
        }*/
        if (data && data.cuenta) {
            data.cuenta = {id: data.cuenta.id};
        }
        if (data && data.tipo) {
            data.tipo = self.tipo.getJson();
        }
        return data;
    }
}