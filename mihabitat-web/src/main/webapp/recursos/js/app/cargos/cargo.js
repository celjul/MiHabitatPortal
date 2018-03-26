var Cargo = function(data) {
    var self = this;
    
    self.activo = ko.observable(data ? data.activo : undefined);
    self.concepto = ko.observable(data ? data.concepto : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.cuenta = new Cuenta(data ? data.cuenta : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.tipo = new Catalogo(data ? data.tipo : undefined);
    
    self.cargar = function(data) {
        self.activo(data ? data.activo : undefined);
        self.concepto(data ? data.concepto : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.id(data ? data.id : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
    }
    
    self.limpiar = function() {
        self.activo(undefined);
        self.concepto(undefined);
        self.condominio.limpiar();
        self.cuenta.limpiar();
        self.id(undefined);
        self.tipo.limpiar();
    }
    
    self.getJson = function() {
        var cargo = self.estructurar(ko.toJS(self));
        cargo = validarObject(cargo);
        return cargo;
    }
    
    self.estructurar = function(data) {
        /*if (data.condominio) {
            data.condominio = self.condominio.getJson();
        }*/
        if (data.condominio) {
            data.condominio = {id: data.condominio.id};
        }
        if (data.cuenta) {
            data.cuenta = {
                    id : self.cuenta.id()
            }
        }
        if (data.tipo) {
            data.tipo = self.tipo.getJson();
        }
        return data;
    }
}