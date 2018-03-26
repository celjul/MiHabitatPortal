var MovimientoSaldo = function() {
    
    Movimiento.call(this);
    var self = this;
    
    self.contacto = new Contacto();
    self.pago = new Pago();
    self.pagoDepartamento = new PagoDepartamento();
    
    self.limpiarMP = function() {
        self.contacto.limpiar();
        self.pago.limpiar();
        self.pagoDepartamento.limpiar();
        
        self.limpiar();
    }
    
    self.cargarMS = function(data) {
        self.contacto.cargar(data ? data.cuenta : undefined);
        self.pago.cargar(data ? data.pago : undefined);
        self.pagoDepartamento.cargar(data ? data.pagoDepartamento : undefined);
        
        self.cargar(data);
    }
    
    self.getJsonMS = function() {
        var movimiento = self.estructurar(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }
    
    self.estructurar = function (data) {
        data = self.getJson();
        if (data && data.contacto) {
            data.contacto = self.contacto.getJson();
        }
        if (data && data.pago) {
            data.pago = {
                    id : self.pago.id()
            }
        }
        if (data && data.pagoDepartamento) {
            data.pagoDepartamento = {
                id : self.pagoDepartamento.id()
            }
        }
        return data;
    }
}