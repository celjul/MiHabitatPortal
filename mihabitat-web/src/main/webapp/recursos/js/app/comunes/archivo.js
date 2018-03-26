var Archivo = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.nombre = ko.observable(data ? data.nombre : undefined);
    self.tamano = ko.observable(data ? data.tamano : undefined);
    self.tipo = ko.observable(data ? data.tipo : undefined);
    self.bytes = ko.observable(data ? data.bytes : undefined);

    self.cargar = function(data) {        
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.tamano(data ? data.tamano : undefined);
        self.tipo(data ? data.tipo : undefined);
        self.bytes(data ? data.bytes : undefined);
    }

    self.limpiar = function() {
    	self.id(undefined);
        self.nombre(undefined);
        self.tamano(undefined);
        self.tipo(undefined);
        self.bytes(undefined);
    }

    self.getJson = function() {
        var archivo = self.estructurar(ko.toJS(self));
        archivo = validarObject(archivo);
        return archivo;
    }
    
    self.estructurar = function(data) {
        
        return data;
    }
}