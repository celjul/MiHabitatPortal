var Notificacion = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.mensaje= ko.observable(data ? data.mensaje : undefined);
    self.titulo= ko.observable(data ? data.titulo : undefined);
    self.link= ko.observable(data ? data.link : undefined);
    self.visto= ko.observable(data ? data.visto : false);

    self.cargar = function(data) {

        self.id(data ? data.id : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.mensaje(data ? data.mensaje : undefined);
        self.titulo(data ? data.titulo : undefined);
        self.link(data ? data.link : undefined);
        self.visto(data ? data.visto : false);
    }

    self.limpiar = function() {
        self.id(undefined);
        self.condominio.limpiar();
        self.mensaje(undefined);
        self.titulo(undefined);
        self.link(undefined);
        self.visto(false);
    }

    self.getJson = function() {
        var notificacion = self.estructurar(ko.toJS(self));
        notificacion = validarObject(notificacion);
        return notificacion;
    }

    self.estructurar = function(data) {
        if (data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        return data;
    }

}