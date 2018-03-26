var EstatusPago = function() {
    
    var self = this;
    
    self.comentario = ko.observable();
    self.estatus = new Catalogo();
    self.id = ko.observable();
    self.usuario = new Usuario();
    
    self.limpiar = function() {
        self.comentario(undefined);
        self.estatus.limpiar();
        self.id(undefined);
        self.usuario.limpiar();
    }
    
    self.cargar = function(data) {
        self.comentario(data ? data.comentario : undefined);
        self.estatus.cargar(data ? data.estatus : undefined);
        self.id(data ? data.id : undefined);
        self.usuario.cargar(data ? data.usuario : undefined);
    }
    
    self.getJson = function() {
        var estatus = self.estructurar(ko.toJS(self));
        estatus = validarObject(estatus);
        return estatus;
    }
    
    self.estructurar = function(data) {
        if (data && data.usuario) {
            data.usuario = self.usuario.getJson();
        }
        return data;
    }
}

var Usuario = function() {
    
    var self = this;
    
    self.id = ko.observable();
    self.user = ko.observable();
    
    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.user(data ? data.user : undefined);
    }
    
    self.limpiar = function(data) {
        self.id(undefined);
        self.user(undefined);
    }
    
    self.getJson = function() {
        var usuario = ko.toJS(self);
        usuario = validarObject(usuario);
        return usuario;     
    }
}