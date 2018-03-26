var CumplimientoEfectividadCobranza = function() {
    
    var self = this;
    
    self.anio = ko.observable();
    self.anios = ko.observableArray();
    self.periodos = ko.observableArray();
    
    self.limpiar = function() {
        //self.anio(undefined);
        self.anios([]);
        self.periodos([]);
    }
    
    self.cargar = function(data) {
//        self.anio(data ? data.anio : undefined);
        
        if (data && data.anios) {
            ko.utils.arrayForEach(data.anios, function(anio) {
                self.anios.push(anio);
            });
        }
        
        if (data && data.periodos) {
            ko.utils.arrayForEach(data.periodos, function(p) {
                var periodo = new PeriodoCobranza();
                periodo.cargar(p);
                self.periodos.push(periodo);
            });
        }
    }
}