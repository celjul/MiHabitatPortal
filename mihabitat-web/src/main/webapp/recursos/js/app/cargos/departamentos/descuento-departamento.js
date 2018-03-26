var DescuentoDepartamento = function(data) {
    Descuento.call(this, data); 
    var self = this;

    self.fecha = ko.observable(data ? data.fecha : undefined);
    
    self.cargarDescuentoDepartamento = function(data) {
        self.cargar(data);
        
        self.fecha(data ? data.fecha : undefined);
    }

    self.limpiarDescuentoDepartamento = function() {
        self.limpiar();
        
        self.fecha(undefined);
    }

    self.getJsonDescuentoDepartamento = function() {
        var descuento = self.estructurar(ko.toJS(self));
        descuento = validarObject(descuento);
        return descuento;
    }
}