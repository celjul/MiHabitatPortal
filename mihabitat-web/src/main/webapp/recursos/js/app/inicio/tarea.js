var Tarea = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.fecha = ko.observable(data ? data.fecha : undefined);
    self.fechaVencimiento = ko.observable(data ? data.fechaVencimiento : undefined);
    self.titulo = ko.observable(data ? data.titulo : undefined);
    self.detalle = ko.observable(data ? data.detalle : undefined);
    self.completada = ko.observable(data ? data.completada : false);
    self.critica = ko.observable(data ? data.critica : false);

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.fechaVencimiento(data ? data.fechaVencimiento : undefined);
        self.titulo(data ? data.titulo : undefined);
        self.detalle(data ? data.detalle : undefined);
        self.completada(data ? data.completada : false);
        self.critica(data ? data.critica : false);
    }

    self.limpiar = function() {
        self.id(undefined);
        self.condominio.limpiar();
        self.fecha(undefined);
        self.fechaVencimiento(undefined);
        self.titulo(undefined);
        self.detalle(undefined);
        self.completada(false);
        self.critica(false);
    }

    self.getJson = function() {
        var grupo = self.estructurar(ko.toJS(self));
        grupo = validarObject(grupo);
        return grupo;
    }

    self.estructurar = function(data) {
        if (data.condominio) {
            data.condominio = {id: data.condominio.id};
        }
        return data;
    }
}