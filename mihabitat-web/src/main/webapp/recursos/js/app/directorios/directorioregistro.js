var DirectorioRegistro = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.condominios = ko.observableArray(data ? data.condominios : []);
    self.titulo= ko.observable(data ? data.titulo : undefined);
    self.telefono= ko.observable(data ? data.telefono : undefined);
    self.email= ko.observable(data ? data.email : undefined);
    self.pagina = ko.observable(data ? data.pagina : undefined);
    self.direccion = ko.observable(data ? data.direccion : undefined);
    self.pais = ko.observable(data ? data.pais : undefined);
    self.estado = ko.observable(data ? data.estado : undefined);
    self.municipio = ko.observable(data ? data.municipio : undefined);
    self.tipo = new Catalogo(data ? data.tipo : undefined);

    self.puedeEditar = function(data) {
        if(!self.pais() && !self.estado() && !self.municipio() && (self.condominios().length == 1)){
            return true;
        }
        return false;
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.condominios(data ? data.condominios : []);
        self.titulo(data ? data.titulo : undefined);
        self.telefono(data ? data.telefono : undefined);
        self.email(data ? data.email : undefined);
        self.pagina(data ? data.pagina : undefined);
        self.direccion(data ? data.direccion : undefined);
        self.pais(data ? data.pais : undefined);
        self.estado(data ? data.estado : undefined);
        self.municipio(data ? data.municipio : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
    }

    self.limpiar = function() {

        self.id(undefined);
        self.condominios([]);
        self.titulo(undefined);
        self.telefono(undefined);
        self.email(undefined);
        self.pagina(undefined);
        self.direccion(undefined);
        self.tipo.limpiar();

    }

    self.getJson = function() {
        var dir = self.estructurar(ko.toJS(self));
        dir = validarObject(dir);
        return dir;
    }

    self.estructurar = function(data) {
        /*if (data.condominios) {
            ko.utils.arrayForEach(data.condominios, function(t) {

            });
        }*/
        return data;
    }
}