var PeriodoCobranza = function() {
    
    var self = this;
    
    self.periodo = ko.observable();
    self.cobros = ko.observable();
    self.pagosATiempo = ko.observable();
    self.pagosExtemporaneos = ko.observable();
    self.porCobrar = ko.observable();
    self.porcentajeCumplimiento = ko.observable();
    self.porcentajeEfectividad = ko.observable();
    self.porcentajeMorosidad = ko.observable();
    
    self.limpiar = function() {
        self.periodo(undefined);
        self.cobros(undefined);
        self.pagosATiempo(undefined);
        self.pagosExtemporaneos(undefined);
        self.porCobrar(undefined);
        self.porcentajeCumplimiento(undefined);
        self.porcentajeEfectividad(undefined);
        self.porcentajeMorosidad(undefined);
    }
    
    self.cargar = function(data) {
        self.periodo(data ? data.periodo : undefined);
        self.cobros(data ? data.cobros : undefined);
        self.pagosATiempo(data ? data.pagosATiempo : undefined);
        self.pagosExtemporaneos(data ? data.pagosExtemporaneos : undefined);
        self.porCobrar(data ? data.porCobrar : undefined);
        self.porcentajeCumplimiento(data ? data.porcentajeCumplimiento : undefined);
        self.porcentajeEfectividad(data ? data.porcentajeEfectividad : undefined);
        self.porcentajeMorosidad(data ? data.porcentajeMorosidad : undefined);
    }
}