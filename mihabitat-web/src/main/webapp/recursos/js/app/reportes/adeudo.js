var Adeudo = function() {
    
    var self = this;
    
    self.departamento = ko.observable();
    self.contacto = ko.observable();
    self.saldo = ko.observable();
    self.saldoFavor = ko.observable();
    self.antiguedad = ko.observable();
    self.torresEtiquetas = ko.observable();
    
    self.limpiar = function() {
        self.departamento(undefined);
        self.contacto(undefined);
        self.saldo(undefined);
        self.saldoFavor(undefined);
        self.antiguedad(undefined);
        self.torresEtiquetas(undefined);
    }
    
    self.cargar = function(data) {
        self.departamento(data ? data.departamento : undefined);
        self.contacto(data ? data.contacto : undefined);
        self.saldo(data ? data.saldo : undefined);
        self.saldoFavor(data ? data.saldoFavor : undefined);
        self.antiguedad(data ? data.antiguedad : undefined);
        self.torresEtiquetas(data ? data.torresEtiquetas : undefined);
    }
}