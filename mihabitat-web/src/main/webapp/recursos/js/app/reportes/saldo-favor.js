var SaldoFavor = function() {
    
    var self = this;
    
    self.saldos = ko.observableArray();
    self.detalle = ko.observable(false);
    
    self.limpiar = function() {
        self.saldos([]);
    }
    
    self.cargar = function(data) {
        
        if (data && data.saldos) {
            ko.utils.arrayForEach(data.saldos, function(s) {
                var saldo = new Saldo();
                saldo.cargar(s);
                self.saldos.push(saldo);
            });
        }
    }
}