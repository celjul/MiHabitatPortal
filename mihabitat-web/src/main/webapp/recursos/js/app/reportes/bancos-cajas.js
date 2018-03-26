var BancosCajas = function() {
    
    var self = this;
    
    self.inicio = ko.observable();
    self.fin = ko.observable();
    self.cuentas = ko.observableArray();
    self.detalle = ko.observable(false);
    
    self.limpiar = function() {
//        self.inicio(undefined);
//        self.fin(undefined);
        self.cuentas([]);
    }
    
    self.cargar = function(data) {
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