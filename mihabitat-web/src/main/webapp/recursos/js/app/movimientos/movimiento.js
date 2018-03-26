var Movimiento = function() {
    
    var self = this;
    
    self.debe = ko.observable().extend({
        currency: 2
    });
    self.fecha = ko.observable();
    self.haber = ko.observable().extend({
        currency: 2
    });
    self.id = ko.observable();
    
    self.limpiar = function() {
        self.debe(undefined);
        self.fecha(undefined);
        self.haber(undefined);
        self.id(undefined);
    }
    
    self.cargar = function(data) {
        self.debe(data ? data.debe : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.haber(data ? data.haber : undefined);
        self.id(data ? data.id : undefined);
    }
    
    self.getJson = function() {
        var movimiento = ko.toJS(self);
        movimiento = validarObject(movimiento);
        return movimiento;
    }
}