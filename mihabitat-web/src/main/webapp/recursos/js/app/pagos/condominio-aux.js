var Condominio = function() {

    var self = this;

    self.id = ko.observable();
    self.nombre = ko.observable();

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.monto : undefined);
    }

    self.limpiar = function() {
        self.id(undefined);
        self.nombre(undefined);
    }

    self.getJson = function() {
        var condominio = ko.toJS(self);
        condominio = validarObject(condominio);
        return condominio;
    }
}