var CuentasCobrar = function() {
    
    var self = this;
    
    self.adeudos = ko.observableArray([]);
    self.fin = ko.observable();
    
    self.limpiar = function() {
        self.adeudos([]);
        self.fin(undefined);
    }
    
    self.cargar = function(data) {
        
        if (data && data.adeudos) {
            ko.utils.arrayForEach(data.adeudos, function(a) {
                var adeudo = new Adeudo();
                adeudo.cargar(a);
                self.adeudos.push(adeudo);
            });
        }
        self.fin(data ? data.fin : undefined);
    }
}