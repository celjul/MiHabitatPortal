var DescuentoRecurrente = function(data) {
    Descuento.call(this, data); 
    var self = this;

    self.dia = ko.observable(data ? data.dia : undefined);
    
    self.cargarDescuentoRecurrente = function(data) {
        self.cargar(data);
        
        self.dia(data ? data.dia : undefined);
    }

    self.limpiarDescuentoRecurrente = function() {
        self.limpiar();
        
        self.dia(undefined);
    }

    self.getJsonDescuentoRecurrente = function() {
        var descuento = self.estructurar(ko.toJS(self));
        descuento = validarObject(descuento);
        return descuento;
    }
}