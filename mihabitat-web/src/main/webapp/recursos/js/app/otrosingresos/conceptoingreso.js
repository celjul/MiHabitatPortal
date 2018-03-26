var ConceptoIngreso = function(data) {

    var self = this;

    self.id = ko.observable();
    self.concepto = ko.observable();
    self.movimientoConceptoOtroIngreso = new MovimientoDetalle();

    self.limpiar = function() {
        self.id(undefined);
        self.concepto(undefined);
        self.movimientoConceptoOtroIngreso.limpiarMD();
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.concepto(data ? data.concepto :undefined);
        self.movimientoConceptoOtroIngreso.cargarMD(data ? data.movimientoConceptoOtroIngreso : undefined);
    }

    self.getJson = function () {
        var concepto = self.estructurar(ko.toJS(self));
        concepto = validarObject(concepto);
        return concepto;
    }

    self.estructurar = function(data) {
        if (data.movimientoConceptoOtroIngreso) {
            data.movimientoConceptoOtroIngreso = self.movimientoConceptoOtroIngreso.getJsonMD();
        }
        return data;
    }
}
