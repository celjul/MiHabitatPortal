var MovimientoPago = function() {
    
    Movimiento.call(this);
    var self = this;
    
    self.cuenta = new Cuenta();
    self.pago = new Pago();
    
    self.limpiarMP = function() {
        self.cuenta.limpiar();
        self.pago.limpiar();
        
        self.limpiar();
    }
    
    self.cargarMP = function(data) {
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.pago.cargar(data ? data.pago : undefined);
        
        self.cargar(data);
    }
    
    self.getJsonMP = function() {
        var movimiento = self.estructurar(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }
    
    self.estructurar = function (data) {
        data = self.getJson();
        if (data && data.cuenta) {
            data.cuenta = self.cuenta.getJson();
        }
        if (data && data.pago) {
            data.pago = {
                    id : self.pago.id()
            }
        }
        return data;
    }
}