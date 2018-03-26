var Descuento = function(data) {
    var self = this;
    
    self.id = ko.observable(data ? data.id : undefined);
    self.monto = ko.observable(data ? data.monto : undefined);
    self.monto.subscribe(function() {
        self.monto(numeral(self.monto()).format('0,0.00'));
    });
    self.porcentaje = ko.observable(data ? data.porcentaje : true);
    
    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.monto(data ? data.monto : undefined);
        self.porcentaje(data ? data.porcentaje : true);
    }

    self.limpiar = function() {
        self.id(undefined);
        self.monto(undefined);
        self.porcentaje(true);
    }

    self.getJson = function() {
        var descuento = self.estructurar(ko.toJS(self));
        descuento = validarObject(descuento);
        return descuento;
    }

    self.estructurar = function(data) {
        data.monto = numeral().unformat(data.monto);
        return data;
    }
}