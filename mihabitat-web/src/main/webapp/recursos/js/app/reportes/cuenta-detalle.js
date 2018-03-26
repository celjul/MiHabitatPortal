var CuentaDetalle = function() {
    
    var self = this;
    
    self.cuenta = new Cuenta();
    self.inicio = ko.observable();
    self.fin = ko.observable();
    self.movimientos = ko.observableArray();
    
    self.limpiar = function() {
        self.cuenta.limpiar();
        self.inicio(undefined);
        self.fin(undefined);
        self.movimientos([]);
    }
    
    self.cargar = function(data) {
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.inicio(data ? data.inicio : undefined);
        self.fin(data ? data.fin : undefined);
        
        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function(m) {
                var movimiento = new Movimiento();
                movimiento.cargar(m);
                if(m.debe) {
                    self.cuenta.debe(self.cuenta.debe() + m.debe);
                }
                if (m.haber) {
                    self.cuenta.haber(self.cuenta.haber() + m.haber);
                }
                self.movimientos.push(movimiento);
            });
        }
    }
}