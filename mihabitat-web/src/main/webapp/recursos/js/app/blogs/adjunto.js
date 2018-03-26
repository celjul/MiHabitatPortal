var AdjuntoPost = function() {

    var self = this;

    self.id = ko.observable();
    self.archivo = new Archivo();

    self.limpiar = function() {
        self.id(undefined);
        self.archivo.limpiar();
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.archivo.cargar(data ? data.archivo : undefined)
    }

    self.getJson = function() {
        var adjunto = self.estructurar(ko.toJS(self));
        adjunto = validarObject(adjunto);
        return adjunto;
    }

    self.estructurar = function(data) {

        return data;
    }
}
