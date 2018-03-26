var GrupoCondominio = function(data) {
    var self = this;

    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.descripcion = ko.observable(data ? data.descripcion : undefined);
    self.id = ko.observable(data ? data.id : undefined);

    self.cargar = function(data) {
        self.condominio.cargar(data ? data.condominio : undefined);
        self.descripcion(data ? data.descripcion: undefined);
        self.id(data ? data.id : undefined);
    }

    self.limpiar = function() {
        self.condominio.limpiar();
        self.descripcion(undefined);
        self.id(undefined);
    }
    
    self.getJson = function() {
        var grupo = self.estructurar(ko.toJS(self));
        grupo = validarObject(grupo);
        return grupo;
    }
    
    self.estructurar = function(data) {
        if (data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        return data;
    }
}