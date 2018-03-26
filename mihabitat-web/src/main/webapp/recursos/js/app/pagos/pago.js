var Pago = function(data) {

    var self = this;

    self.condominio = new Condominio();
    self.contacto = new Contacto();
    self.comprobante = ko.observable();
    self.cuenta = new Cuenta();
    self.estatus = ko.observableArray();
    self.fecha = ko.observable();
    self.id = ko.observable();
    self.metodoPago = new Catalogo();
    self.pagosDepartamento = ko.observableArray();
    self.monto = ko.observable().extend({
        currency: 2
    });;

    /*ko.pureComputed({
        read: function () {
            var monto = 0;
            ko.utils.arrayForEach(self.pagosDepartamento(), function(pd) {
                monto = monto + pd.monto();
            });
            return monto;
        },
        write: function (value) {
            if(self.pagosDepartamento().length > 0) {
                self.pagosDepartamento()[0].monto(value);
            }
        },
        owner: this
    });*/
        /*.extend({
        currency: 2
    });*/
    self.referencia = ko.observable();
    self.folio = ko.observable();
    //self.movimientos = ko.observableArray();


    self.limpiar = function() {
        self.condominio.limpiar();
        self.contacto.limpiar();
        self.comprobante(undefined);
        self.cuenta.limpiar();
        self.estatus([]);
        self.fecha(undefined);
        self.id(undefined);
        self.metodoPago.limpiar();
        //self.monto(undefined);
        self.referencia(undefined);
        self.folio(undefined);
        //self.movimientos([]);
        self.pagosDepartamento([]);
    }

    self.cargar = function(data) {
        self.condominio.cargar(data ? data.condominio : undefined);
        self.contacto.cargar(data ? data.contacto : undefined);
        self.comprobante(data ? data.comprobante : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);

        if (data && data.estatus) {
            ko.utils.arrayForEach(data.estatus, function(e) {
                var estatus = new EstatusPago();
                estatus.cargar(e);
                self.estatus.push(estatus);
            });
        }

        self.fecha(data ? data.fecha : undefined);
        self.id(data ? data.id : undefined);
        self.metodoPago.cargar(data ? data.metodoPago : undefined);
        //self.monto(data ? data.monto : undefined);
        self.referencia(data ? data.referencia : undefined);
        self.folio(data ? data.folio : undefined);
        
        /**if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function(mov) {
                var movimiento = new MovimientoCargoAplicado();
                movimiento.cargar(mov);
                self.movimientos.push(movimiento);
            });
        }*/
        if (data && data.pagosDepartamento) {
            ko.utils.arrayForEach(data.pagosDepartamento, function(pd) {
                var pagoDepartamento = new PagoDepartamento();
                pagoDepartamento.cargar(pd);
                self.pagosDepartamento.push(pagoDepartamento);
            });
        }
    }

    self.getJson = function(estatus, departamentos) {
        var pago = self.estructurar(ko.toJS(self), estatus, departamentos);
        pago = validarObject(pago);
        return pago;
    }

    self.estructurar = function(data, status, departamentos) {
        if (data && data.comprobante) {
            delete data.comprobante;
        }
        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        if (data && data.contacto) {
            data.contacto = self.contacto.getJson();
        }
        if (data && data.cuenta && data.cuenta.id) {
            /*data.cuenta = self.cuenta.getJson();*/
            data.cuenta = {id: self.cuenta.id()};
        } else {
            delete data.cuenta;
        }
        var estatus = [];
        if (data && data.estatus) {
            var estatus = [];
            ko.utils.arrayForEach(self.estatus(), function(e) {
                var est = new EstatusPago();
                est.cargar(e);
                estatus.push(est.getJson());
            });
        }
        var est = new EstatusPago();
        est.cargar(status);
        estatus.push(est.getJson());
        data.estatus = estatus;
        if (data && data.metodoPago) {
            data.metodoPago = self.metodoPago.getJson();
        }
        if(self.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor) {
            data.monto=0;
        }
        /*data.pagosDepartamento =[];*/
        if (departamentos && departamentos().length > 0) {
            var pagosDepartamento = [];
            ko.utils.arrayForEach(departamentos(), function(departamento) {
                var pagoDepartamento = departamento.getJson();
                if(self.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor) {
                    pagoDepartamento.monto = departamento.pagadoTemporal();
                    data.monto=data.monto + pagoDepartamento.monto;
                }
                if(pagoDepartamento.monto > 0) {
                    var movimientos = [];
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
                    pagoDepartamento.movimientos = movimientos;
                    delete pagoDepartamento.pagoCondomino;

                    var obj = ko.utils.arrayFirst(self.pagosDepartamento(), function(pd) {
                        return pagoDepartamento.departamento.id == pd.departamento.id();
                    });
                    if(obj) {
                        pagoDepartamento.id = obj.id();
                    }
                    pagosDepartamento.push(pagoDepartamento);

                }
            });
            data.pagosDepartamento = pagosDepartamento;
        }
        return data;
    }
}
