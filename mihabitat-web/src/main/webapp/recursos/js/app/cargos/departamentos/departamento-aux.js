var Departamento = function(data) {
    var self = this;
    
    self.id = ko.observable(data ? data.id : undefined);
    self.mantenimiento = new MantenimientoCondominio(data ? data.mantenimiento : undefined);
    self.nombre = ko.observable(data ? data.nombre : undefined);
    self.seleccionado = ko.observable(data ? data.seleccionado : undefined);
    self.cargo = new CargoDepartamento(data ? data.cargo : undefined);
    self.contactos = ko.observableArray(data ? data.contactos : []);
    self.grupos = ko.observableArray(data ? data.grupos : []);
    self.unidadIndiviso = ko.observable(data ? data.unidadIndiviso : undefined);

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.mantenimiento.cargar(data ? data.mantenimiento : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.cargo.cargarCargoDepartamento(data ? data.cargo : undefined);
        self.contactos(data ? data.contactos : []);
        if (data && data.grupos && data.grupos.length > 0) {
            ko.utils.arrayForEach(data.grupos, function(g) {
//				var grupo = new GrupoCondominio();
//				grupo.cargar(g);
                self.grupos.push(g);
            });
        }
        self.unidadIndiviso(data ? data.unidadIndiviso : undefined);
        /*if (data && data.contactos && data.contactos.length > 0) {
            ko.utils.arrayForEach(data.contactos, function(c) {
                var cd = new ContactoDepartamento();
                cd.cargar(c);
                self.contactos.push(cd);
            });
        }
        if (data && data.grupos && data.grupos.length > 0) {
            ko.utils.arrayForEach(data.grupos, function(g) {
//				var grupo = new GrupoCondominio();
//				grupo.cargar(g);
                self.grupos.push(g.id);
            });
        }*/
    }

    self.limpiar = function() {
        self.id(undefined);
        self.nombre(undefined);
        self.mantenimiento.limpiar();
        self.seleccionado(undefined);
        self.cargo.limpiar();
        self.contactos([]);
        self.grupos([]);
        self.unidadIndiviso(undefined);
    }

    self.getJson = function() {
        var departamento = self.estructurar(ko.toJS(self));
        departamento = validarObject(departamento);
        return departamento;
    }

    self.estructurar = function(data) {
        if (data.mantenimiento) {
            data.mantenimiento = self.mantenimiento.getJson();
        }
        data.departamento.id = self.id();
        delete data.contactos;
        delete data.grupos;
        return data;
    }
}