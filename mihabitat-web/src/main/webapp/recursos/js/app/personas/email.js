var Email = function(data) {
    var self = this;

    self.direccion = ko.observable(data ? data.direccion : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.tipo = new Catalogo(data ? data.tipo : undefined);

    self.cargar = function(data) {
        self.direccion(data ? data.direccion : undefined);
        self.id(data ? data.id : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
    }

    self.limpiar = function() {
        self.direccion(undefined);
        self.id(undefined);
        self.tipo.limpiar();
    }

    self.getJson = function() {
        var email = self.estructurar(ko.toJS(self));
        email = validarObject(email);
        return email;
    }

    self.estructurar = function(data) {
        if (data.tipo) {
            data.tipo = self.tipo.getJson();
        }
        return data;
    }
}