var PagoDepartamento = function(data) {

    var self = this;

    self.condominio = new Condominio();
    self.pagoCondomino = new Pago();
    self.departamento = new Departamento();
    self.id = ko.observable();
    /*self.monto = ko.observable().extend({
        currency: 2
    });*/
    self.cargos = ko.observableArray([]);

    self.monto =ko.observable(data ? data.monto : 0).extend({
        currency: 2
    });
    self.total = ko.computed(function() {
        var monto = 0;
        ko.utils.arrayForEach(self.cargos(), function(cargo) {
            monto = monto + cargo.saldoPendiente();
        });
        return monto;
    });

    self.pagadoTemporal = ko.computed(function() {
        var pt = 0;
        ko.utils.arrayForEach(self.cargos(), function(cargo) {
            pt = pt + cargo.pagoTemporal();
        });
        return pt;
    });

    self.movimientos = ko.observableArray();

    self.limpiar = function() {
        self.condominio.limpiar();
        self.pagoCondomino.limpiar();
        self.departamento.limpiar();
        self.id = ko.observable();
        self.monto(undefined);
        self.movimientos([]);
        self.cargos([]);
    }

    self.cargar = function(data) {
        self.condominio.cargar(data ? data.condominio : undefined);
        self.pagoCondomino.cargar(data ? data.pagoCondomino : undefined);
        self.departamento.cargar(data ? data.departamento : undefined);
        self.id(data ? data.id : undefined);
        self.monto(data ? data.monto : undefined);
        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function(mov) {
                var movimiento = new MovimientoCargoAplicado();
                movimiento.cargar(mov);
                self.movimientos.push(movimiento);
            });
        }
        if (data && data.cargos && data.cargos.length > 0) {
            ko.utils.arrayForEach(data.cargos, function(c) {
                var cargo = new CargoDepartamento();
                cargo.cargarCargoDepartamento(c);
                self.cargos.push(cargo);
            });
        }
    }

    self.getJson = function() {
        var pago = self.estructurar(ko.toJS(self));
        pago = validarObject(pago);
        return pago;
    }

    self.estructurar = function(data) {
        if (data && data.condominio) {
            data.condominio = {id: self.condominio.id()};
        }
        if (data && data.pagoCondomino) {
            data.pagoCondomino = {id: self.pagoCondomino.id()};
        }
        if (data && data.departamento) {
            data.departamento = {id: self.departamento.id()};
        }
        var movimientos = [];
        if (data && data.movimientos) {
            var movimiento = [];
            ko.utils.arrayForEach(self.movimientos(), function(m) {
                var mca = new MovimientoCargoAplicado();
                mca.cargar(m);
                movimientos.push(mca.getJson());
            });
        }
        data.movimientos = movimientos;

        delete data.cargos;
        delete data.total;
        delete data.pagadoTemporal;

        return data;
    }
}
