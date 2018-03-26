var TemaRevisado = function(data) {

    var self = this;

    self.id = ko.observable();
    self.tema = new Tema();
    self.usuario = new Usuario();
    self.fecha = ko.observable();

    self.limpiar = function() {
        self.id(undefined);
        self.tema.limpiar();
        self.usuario.limpiar();
        self.fecha(undefined);
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.tema.cargar(data ? data.tema : undefined);
        self.usuario.cargar(data ? data.usuario : undefined)
        self.fecha(data ? data.fecha : undefined);
    }

    self.getJson = function() {
        var post = self.estructurar(ko.toJS(self));
        post = validarObject(post);
        return post;
    }

    self.estructurar = function(data) {

        return data;
    }
}