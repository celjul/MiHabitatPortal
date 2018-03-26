var BalanzaComprobacion = function() {
    
    var self = this;
    
    self.debe = ko.observable();
    self.haber = ko.observable();
    self.inicio = ko.observable();
    self.fin = ko.observable();
    self.cuentas = ko.observableArray();
    self.detalle = ko.observable(false);
    
    self.limpiar = function() {
        self.debe(undefined);
        self.haber(undefined);
//        self.inicio(undefined);
//        self.fin(undefined);
        self.cuentas([]);
    }
    
    self.cargar = function(data) {
        self.debe(data ? data.debe : undefined);
        self.haber(data ? data.haber : undefined);
//        self.inicio(data ? data.inicio : undefined);
//        self.fin(data ? data.fin : undefined);
        
        if (data && data.cuentas) {
            ko.utils.arrayForEach(data.cuentas, function( c ) {
                var cuentas = new Cuenta();
                cuentas.cargar(c);
                self.cuentas.push(cuentas);
            });
        }
    }
}