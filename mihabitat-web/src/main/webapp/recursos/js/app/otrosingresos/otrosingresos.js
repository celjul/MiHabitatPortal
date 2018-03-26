var OtroIngreso = function(data) {

    var self = this;

    self.id = ko.observable();
    self.condominio = new Condominio();
    self.conceptos = ko.observableArray();
    self.fecha = ko.observable();
    self.metodoPago = new Catalogo();
    self.referencia = ko.observable();
    self.movimientoOtroIngreso = new MovimientoGasto();

    self.limpiar = function() {
        self.id(undefined);
        self.condominio.limpiar();
        self.conceptos([]);
        self.fecha(undefined);
        self.metodoPago.limpiar();
        self.referencia(undefined);
        self.movimientoOtroIngreso.limpiarMG();
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);

        if (data && data.conceptos) {
            ko.utils.arrayForEach(data.conceptos, function (d) {
                var concepto = new ConceptoIngreso();
                concepto.cargar(d);
                self.conceptos.push(concepto);
            });
        }
        self.fecha(data ? data.fecha : undefined);
        self.metodoPago.cargar(data ? data.metodoPago : undefined);
        self.movimientoOtroIngreso.cargarMG(data ? data.movimientoOtroIngreso : undefined);
        self.referencia(data ? data.referencia : undefined);
    }

    self.getJson = function () {
        var otroIngreso = self.estructurar(ko.toJS(self));
        otroIngreso = validarObject(otroIngreso);
        return otroIngreso;
    }

    self.estructurar = function(data) {
        if (data.condominio) {
            data.condominio = {
                id: data.condominio.id
            }
        }
        if (data.conceptos) {
            var conceptos = [];
            ko.utils.arrayForEach(self.conceptos(), function (d) {
                var concepto = d.getJson();
                concepto.movimientoConceptoOtroIngreso.aplicado = true;
                concepto.movimientoConceptoOtroIngreso.cancelado = false;
                concepto.movimientoConceptoOtroIngreso.fecha = data.fecha;
                concepto.movimientoConceptoOtroIngreso.tipo = {
                    id: AppConfig.catalogos.movimientos.tipos.pago
                }
                conceptos.push(concepto);
            });
            data.conceptos = conceptos;
        }
        if (data.metodoPago) {
            data.metodoPago = {
                id: data.metodoPago.id
            }
        }
        if (data.movimientoOtroIngreso) {
            data.movimientoOtroIngreso = self.movimientoOtroIngreso.getJsonMG();
            data.movimientoOtroIngreso.aplicado = true;
            data.movimientoOtroIngreso.cancelado = false;
            data.movimientoOtroIngreso.fecha = data.fecha;
            data.movimientoOtroIngreso.tipo = {
                id: AppConfig.catalogos.movimientos.tipos.pago
            }
        }

        return data;
    }

    self.agregarConcepto = function() {
        self.conceptos.push(new ConceptoIngreso());
    }

    self.eliminarConcepto = function(data) {
        bootbox.confirm("¿Deseas remover el concepto?", function(result) {
            if (result){
                self.conceptos.remove(data);
            }
        });
    }
}
