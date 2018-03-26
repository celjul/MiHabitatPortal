var RecargoRecurrente = function(data) {
    Recargo.call(this, data); 
    var self = this;
    
    self.dia = ko.observable(data ? data.dia : undefined);
    
    self.cargarRecargoRecurrente = function(data) {
        self.cargar(data);
        
        self.dia(data ? data.dia : undefined);
    }
    
    self.limpiarRecargoRecurrente = function() {
        self.limpiar();
        
        self.dia(undefined);
    }

    self.getJsonRecargoRecurrente = function() {
        var recargo = self.estructurar(ko.toJS(self));
        recargo = validarObject(recargo);
        return recargo;
    }
}