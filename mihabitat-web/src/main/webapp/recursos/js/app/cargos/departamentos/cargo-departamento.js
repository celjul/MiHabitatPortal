var CargoDepartamento = function (data) {
    Cargo.call(this, data);
    var self = this;

    //    self.departamento = new Departamento(data ? data.departamento : undefined);
    self.descuento = new DescuentoDepartamento(data ? data.descuento
        : undefined);
    self.fecha = ko.observable(data ? data.fecha : undefined);
    self.mantenimientoDepartamento = ko
        .observable(data ? data.mantenimientoDepartamento : undefined);
    self.mantenimientoDepartamento.subscribe(function (val) {
        if (!val) {
            self.monto(undefined);
        }
    });
    self.recargo = new RecargoDepartamento(data ? data.recargo : undefined);

    self.aplicaDescuento = ko.observable(false);
    self.aplicaDescuento.subscribe(function (val) {
        if (!val) {
            self.descuento.limpiarDescuentoDepartamento();
        }
    });
    self.aplicaRecargo = ko.observable(false);
    self.aplicaRecargo.subscribe(function (val) {
        if (!val) {
            self.recargo.limpiarRecargoDepartamento();
        }
    });

    self.consumo = new ConsumoDepartamento();
    /** NUEVAS PROPIEDADES CON MOVIMIENTOS * */
    self.movimientos = ko.observableArray([]);
    self.movimientosReales = ko.observableArray([]);
    self.monto = ko.observable(0).extend({
        currency: 2
    });

    self.cargo = new MovimientoCargo();
    self.ajustes = ko.observableArray([]);
    self.recargos = ko.observableArray([]);
    self.descuentos = ko.observableArray([]);
    self.cargoscancelados = ko.observableArray([]);
    self.recargoscancelados = ko.observableArray([]);
    self.descuentoscancelados = ko.observableArray([]);

    self.totalMonto = ko.computed(function () {
        if (self.cargo.cancelado()) {
            return 0;
        }
        var monto = self.cargo.debe();
        ko.utils.arrayForEach(self.ajustes(), function (ajuste) {
            if (ajuste.debe()) {
                monto = monto + ajuste.debe();
            } else if (ajuste.haber()) {
                monto = monto - ajuste.haber();
            }
        });

        return monto;
    });

    self.totalRecargos = ko.computed(function () {
        var recargos = 0;
        ko.utils.arrayForEach(self.recargos(), function (recargo) {
            if (recargo.debe()) {
                recargos = recargos + recargo.debe();
            }
        });
        ko.utils.arrayForEach(self.recargoscancelados(), function (recargocancelado) {
            if (recargocancelado.haber()) {
                recargos = recargos - recargocancelado.haber();
            }
        });
        return recargos;
    });

    self.totalDescuentos = ko.computed(function () {
        var descuentos = 0;
        ko.utils.arrayForEach(self.descuentos(), function (descuento) {
            if (descuento.haber()) {
                descuentos = descuentos + descuento.haber();
            }
        });
        ko.utils.arrayForEach(self.descuentoscancelados(), function (descuentocancelado) {
            if (descuentocancelado.debe()) {
                descuentos = descuentos - descuentocancelado.debe();
            }
        });
        return descuentos;
    });

    self.descuentosPorAplicar = ko.computed(function () {
        var descuento = 0;
        if (self.descuento.id()) {
            if (self.descuento.porcentaje()) {
                descuento = self.totalMonto() * (self.descuento.monto() * 0.01);
            } else {
                descuento = self.descuento.monto();
            }
        }
        return descuento;
    });

    self.recargosPorAplicar = ko.computed(function () {
        var recargo = 0;
        if (self.recargo.id()) {
            if (self.recargo.porcentaje()) {
                recargo = self.totalMonto() * (self.recargo.monto() * 0.01);
            } else {
                recargo = self.recargo.monto();
            }
        }
        return recargo;
    });

    self.totalPagado = ko.computed(function () {
        var pagado = 0;
        pagado = self.cargo.total();
        ko.utils.arrayForEach(self.recargos(), function (recargo) {
            pagado = pagado + recargo.total();
        });
        ko.utils.arrayForEach(self.descuentos(), function (descuento) {
            pagado = pagado + descuento.total();
        });
        return pagado;
    });

    self.saldoPendiente = ko.computed(function () {
        /*return self.totalMonto() + self.totalRecargos() - self.totalDescuentos() - self.totalPagado();*/
        return parseFloat(parseFloat(self.totalMonto() + self.totalRecargos() - self.totalDescuentos() - self.totalPagado()).toFixed(2));
    });

    //Propiedad temporal para pagos
    self.pagoTemporal = ko.observable().extend({
        currency: 2
    });
    /*self.pagoTemporal.subscribe(function(data) {
     self.pagoTemporal(numeral(data).format('0,0.00'));
     });*/

    self.editable = ko.observable(true);

    self.cargarCargoDepartamento = function (data) {
        self.cargar(data);
        self.descuento.cargarDescuentoDepartamento(data ? data.descuento : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.mantenimientoDepartamento(data ? data.mantenimientoDepartamento : undefined);
        self.recargo.cargarRecargoDepartamento(data ? data.recargo : undefined);

        if (self.descuento.id()) {
            self.aplicaDescuento(true);
        }
        if (self.recargo.id()) {
            self.aplicaRecargo(true);
        }
        if (data && data.consumo) {
            self.consumo.cargarCD(data.consumo);
        }

        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function (m) {
                switch (m.tipo.id) {
                    case AppConfig.catalogos.movimientos.tipos.cargo:
                        self.cargo.cargarMC(m);
                        self.movimientos.push(self.cargo);
                        self.movimientosReales.push(self.cargo);
                        ko.utils.arrayForEach(self.cargo.aplicados(), function (a) {
                            self.movimientos.push(a);
                        });
                        break;
                    case AppConfig.catalogos.movimientos.tipos.ajustecargo:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.ajustes.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        break;
                    case AppConfig.catalogos.movimientos.tipos.recargo:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.recargos.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        ko.utils.arrayForEach(mov.aplicados(), function (a) {
                            self.movimientos.push(a);
                        });
                        break;
                    case AppConfig.catalogos.movimientos.tipos.descuento:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.descuentos.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        ko.utils.arrayForEach(mov.aplicados(), function (a) {
                            self.movimientos.push(a);
                        });
                        break;
                    case AppConfig.catalogos.movimientos.tipos.cancelacioncargo:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.cargoscancelados.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        break;
                    case AppConfig.catalogos.movimientos.tipos.cancelacionrecargo:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.recargoscancelados.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        break;
                    case AppConfig.catalogos.movimientos.tipos.cancelaciondescuento:
                        var mov = new MovimientoCargo();
                        mov.cargarMC(m);
                        self.descuentoscancelados.push(mov);
                        self.movimientos.push(mov);
                        self.movimientosReales.push(mov);
                        break;
                }
            });
        }
        self.monto(self.totalMonto());

        if (self.id()) {
            if (self.cargo.aplicados().length > 0 && self.cargo.total() != 0) {
                //Hay Pagos
                self.editable(false);
            }
            if (self.cargo.cancelado()) {
                //Esta cancelado
                self.editable(false);
            }
        }
    }

    self.limpiarCargoDepartamento = function () {
        self.limpiar();
        self.consumo.limpiarCD();
        self.descuento.limpiarDescuentoDepartamento();
        self.fecha(undefined);
        self.mantenimientoDepartamento(undefined);
        self.recargo.limpiarRecargoDepartamento();
        self.movimientos([]);
        self.aplicaDescuento(false);
        self.aplicaRecargo(false);
    }

    self.getJsonCargoDepartamento = function (fechaCD) {
        var cargo = self.estructurarCargoDepartamento(ko.toJS(self), fechaCD);
        cargo = validarObject(cargo);
        return cargo;
    }

    self.estructurarCargoDepartamento = function (data, fechaCD) {
        data = self.estructurar(data);

        if (data.descuento && (data.descuento.fecha)) {
            data.descuento = self.descuento.getJsonDescuentoDepartamento();
        } else {
            delete data.descuento;
        }
        if (data.consumo) {
            data.consumo = data.consumo.estructurarConsumoDep();
        }
        if (data.recargo && (data.recargo.fecha)) {
            data.recargo = self.recargo.getJsonRecargoDepartamento();
        } else {
            delete data.recargo;
        }

        var movimientos = [];

        if (self.movimientosReales().length > 0) {
            ko.utils.arrayForEach(self.movimientosReales(), function (m) {
                movimientos.push(m.getJsonMC());
            });

            if (self.totalMonto() < data.monto) {
                var ajuste = new MovimientoCargo();
                ajuste.debe(data.monto - self.totalMonto());
                //var fecha = new Date();
                //ajuste.fecha( fecha.getDate() + "-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear() );
                ajuste.fecha(data.fecha);
                ajuste.tipo.id(AppConfig.catalogos.movimientos.tipos.ajustecargo);
                ajuste.cancelado(false);
                movimientos.push(ajuste.getJsonMC());
            } else if (self.totalMonto() > data.monto) {
                var ajuste = new MovimientoCargo();
                ajuste.haber(self.totalMonto() - data.monto);
                //var fecha = new Date();
                //ajuste.fecha( fecha.getDate() + "-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear() );
                ajuste.fecha(data.fecha);
                ajuste.tipo.id(AppConfig.catalogos.movimientos.tipos.ajustecargo);
                ajuste.cancelado(false);
                movimientos.push(ajuste.getJsonMC());
            }
        } else {
            var movimiento = new MovimientoCargo();
            movimiento.tipo.id(AppConfig.catalogos.movimientos.tipos.cargo)
            movimiento.debe(data.monto);
            movimiento.fecha(fechaCD);
            movimiento.cancelado(false);
            movimientos.push(movimiento.getJsonMC());
        }

        data.movimientos = movimientos;

        delete data.aplicaDescuento;
        delete data.aplicaRecargo;
        delete data.movimientosReales;
        delete data.monto;
        delete data.cargo;
        delete data.ajustes;
        delete data.recargos;
        delete data.descuentos;
        delete data.cargoscancelados;
        delete data.recargoscancelados;
        delete data.descuentoscancelados;
        delete data.totalMonto;
        delete data.totalRecargos;
        delete data.totalDescuentos;
        delete data.totalPagado;
        delete data.saldoPendiente;

        delete data.descuentosPorAplicar;
        delete data.recargosPorAplicar;

        delete data.pagoTemporal;
        delete data.editable;
        return data;
    }
}
