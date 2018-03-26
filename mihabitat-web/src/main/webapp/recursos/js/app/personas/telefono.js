var Telefono = function(data) {
    var self = this;

    self.extension = ko.observable(data ? data.extension : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.lada = ko.observable(data ? data.lada : undefined);
    self.numero = ko.observable(data ? data.numero : undefined);
    self.tipo = new Catalogo(data ? data.tipo : undefined);

    self.cargar = function(data) {
        self.extension(data ? data.extension : undefined);
        self.id(data ? data.id : undefined);
        self.lada(data ? data.lada : undefined);
        self.numero(data ? data.numero : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
    }

    self.limpiar = function() {
        self.extension(undefined);
        self.direccion(undefined);
        self.id(undefined);
        self.lada(undefined);
        self.numero(undefined);
        self.tipo.limpiar();
    }

    self.getJson = function() {
        var telefono = self.estructurar(ko.toJS(self));
        telefono = validarObject(telefono);
        return telefono;
    }

    self.estructurar = function(data) {
        if (data.tipo) {
            data.tipo = self.tipo.getJson();
        }
        return data;
    }
}