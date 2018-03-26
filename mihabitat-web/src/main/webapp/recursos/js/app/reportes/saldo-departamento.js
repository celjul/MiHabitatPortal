var SaldoDepartamento = function() {
    
    var self = this;

    var fechaAux = new Date();

    self.fecha = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.saldos = ko.observableArray();
    self.detalle = ko.observable(false);
    
    self.limpiar = function() {
        self.saldos([]);
    }
    
    self.cargar = function(data) {
        
        if (data && data.saldos && data.saldos.length > 0) {
            ko.utils.arrayForEach(data.saldos, function(s) {
                var saldo = new EstadoCuenta();
                saldo.cargar(s);
                self.saldos.push(saldo);
            });
        }
    }
}