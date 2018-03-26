var RecargoDepartamento = function(data) {
    Recargo.call(this, data);
    var self = this;

    self.fecha = ko.observable(data ? data.fecha : undefined);

    self.cargarRecargoDepartamento = function(data) {
        self.cargar(data);

        self.fecha(data ? data.fecha : undefined);
    }

    self.limpiarRecargoDepartamento = function() {
        self.limpiar();

        self.fecha(undefined);
    }

    self.getJsonRecargoDepartamento = function() {
        var recargo = self.estructurar(ko.toJS(self));
        recargo = validarObject(recargo);
        return recargo;
    }
}