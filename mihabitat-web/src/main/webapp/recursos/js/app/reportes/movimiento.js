var Movimiento = function() {
    
    var self = this;
    
    self.cuenta = new Cuenta();
    self.debe = ko.observable();
    self.descripcion = ko.observable();
    self.fecha = ko.observable();
    self.haber = ko.observable();
    
    self.nombre = ko.observable();
    self.referencia = ko.observable();    
    
    self.limpiar = function() {
        self.cuenta.limpiar();
        self.debe(undefined);
        self.descripcion(undefined);
        self.fecha(undefined);
        self.haber(undefined);
        
        self.nombre(undefined);
        self.referencia(undefined);
    }
    
    self.cargar = function(data) {
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.debe(data ? data.debe : undefined);
        self.descripcion(data ? data.descripcion : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.haber(data ? data.haber : undefined);
        
        self.nombre(data ? data.nombre : undefined);
        self.referencia(data ? data.referencia : undefined);
    }
}