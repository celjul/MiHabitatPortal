var IngresoNoIdentificado = function(data) {

    var self = this;

    self.condominio = new Condominio();
    self.comprobante = ko.observable();
    self.cuentaBanco = new Cuenta();
    self.cuentaIngreso = new Cuenta();
    self.estatus = new Catalogo();
    self.fecha = ko.observable();
    self.id = ko.observable();
    self.metodoPago = new Catalogo();
    self.monto = ko.observable().extend({
        currency: 2
    });
    self.referencia = ko.observable();
    self.comentario = ko.observable();
    self.aplicadoEn = ko.observable();
    self.movimientos = ko.observableArray();

    self.limpiar = function() {
        self.condominio.limpiar();
        self.comprobante(undefined);
        self.cuentaBanco.limpiar();
        self.cuentaIngreso.limpiar();
        self.estatus.limpiar();
        self.fecha(undefined);
        self.id(undefined);
        self.metodoPago.limpiar();
        self.monto(undefined);
        self.referencia(undefined);
        self.comentario(undefined);
        self.aplicadoEn(undefined);
        self.movimientos([]);
    }

    self.cargar = function(data) {
        self.condominio.cargar(data ? data.condominio : undefined);
        self.comprobante(data ? data.comprobante : undefined);
        self.cuentaBanco.cargar(data ? data.cuentaBanco : undefined);
        self.cuentaIngreso.cargar(data ? data.cuentaIngreso : undefined);

        self.estatus.cargar(data ? data.estatus : undefined);

        self.fecha(data ? data.fecha : undefined);
        self.id(data ? data.id : undefined);
        self.metodoPago.cargar(data ? data.metodoPago : undefined);
        self.monto(data ? data.monto : undefined);
        self.referencia(data ? data.referencia : undefined);
        self.comentario(data ? data.comentario : undefined);
        self.aplicadoEn(data ? data.aplicadoEn : undefined);

        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function(mov) {
                var movimiento = new MovimientoGasto();
                movimiento.cargar(mov);
                self.movimientos.push(movimiento);
            });
        }
    }

    self.getJson = function() {
        var ingresoNoIdentificado = self.estructurar(ko.toJS(self));
        ingresoNoIdentificado = validarObject(ingresoNoIdentificado);
        return ingresoNoIdentificado;
    }

    self.estructurar = function(data) {
        if (data && data.comprobante) {
            delete data.comprobante;
        }
        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        if (data && data.contacto) {
            data.contacto = self.contacto.getJson();
        }
        if (data && data.cuentaBanco && data.cuentaBanco.id) {
            /*data.cuenta = self.cuenta.getJson();*/
            data.cuentaBanco = {id: self.cuentaBanco.id()};
        } else {
            delete data.cuentaBanco;
        }
        if (data && data.cuentaIngreso && data.cuentaIngreso.id) {
            /*data.cuenta = self.cuenta.getJson();*/
            data.cuentaIngreso = {id: self.cuentaIngreso.id()};
        } else {
            delete data.cuentaIngreso;
        }
        /*var estatus = [];
        if (data && data.estatus) {
            var estatus = [];
            ko.utils.arrayForEach(self.estatus(), function(e) {
                var est = new Catalogo();
                est.cargar(e);
                estatus.push(est.getJson());
            });
        }
        var est = new Catalogo();
        est.cargar(status);
        estatus.push(est.getJson());
        data.estatus = estatus;*/

        if (data && data.metodoPago) {
            data.metodoPago = self.metodoPago.getJson();
        }

        if(!data.id) {
            var movimiento = new MovimientoGasto();
            movimiento.haber = self.monto();
            delete movimiento.cuenta.cuentaAnterior;
            data.movimientos.push(movimiento);
        } else {
            data.movimientos = [];
            ko.utils.arrayForEach(self.movimientos(), function(movimiento) {
                var movimiento = movimiento.getJsonMG();
                /*delete movimiento.cuenta.cuentaAnterior;*/
                data.movimientos.push(movimiento);
            });
        }

        /*if (departamentos && departamentos().length > 0) {
            var movimientos = [];
            ko.utils.arrayForEach(departamentos(), function(departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function(cargo) {
                    var pagoTemp = numeral().unformat(cargo.pagoTemporal());
                    if (pagoTemp > 0) {
                        var movimiento = new MovimientoCargoAplicado();
                        movimiento.haber = pagoTemp;
                        movimiento.movimientoCargo = {
                            cargo : {
                                id : cargo.id()
                            }
                        }
                        delete movimiento.cuenta.cuentaAnterior;
                        movimientos.push(movimiento);
                    }
                });
            });
            data.movimientos = movimientos;
        }*/
        return data;
    }
}
