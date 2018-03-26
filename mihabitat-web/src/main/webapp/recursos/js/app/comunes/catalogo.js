var Catalogo = function(data) {
    var self = this;

    self.descripcion = ko.observable(data ? data.descripcion : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.seleccionado = ko.observable(data ? data.seleccionado : undefined);

    self.cargar = function(data) {
        self.descripcion(data ? data.descripcion : undefined);
        self.id(data ? data.id : undefined);
    }

    self.limpiar = function() {
        self.descripcion(undefined);
        self.id(undefined);
    }

    self.getJson = function() {
        var catalogo = self.estructurar(ko.toJS(self));
        catalogo = validarObject(catalogo);
        return catalogo;
    }
    
    self.estructurar = function(data) {
        if (data.seleccionado) {
            delete data.seleccionado;
        }
        if (data.descripcion) {
            delete data.descripcion;
        }
        return data;
    }
}