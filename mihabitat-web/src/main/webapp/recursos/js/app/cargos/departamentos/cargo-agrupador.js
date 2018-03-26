var CargoAgrupador = function(data) {
    Cargo.call(this, data);
    var self = this;

    self.cargos = ko.observableArray(data ? data.cargos : []);
    self.descuento = new DescuentoDepartamento(data ? data.descuento
            : undefined);
    self.fecha = ko.observable(data ? data.fecha : undefined);
    self.monto = ko.observable(data ? data.monto : undefined).extend({
        currency: 2
    });
    /*self.monto.subscribe(function() {
        self.monto(numeral(self.monto()).format('0,0.00'));
    });*/
    self.mantenimientoDepartamento = ko
            .observable(data ? data.mantenimientoDepartamento : false);
    self.mantenimientoDepartamento.subscribe(function(val) {
        if (val) {
            self.monto(0);
        }
    });
    self.recargo = new RecargoDepartamento(data ? data.recargo : undefined);

    self.aplicaDescuento = ko.observable(false);
    self.aplicaDescuento.subscribe(function(val) {
        if (!val) {
            self.descuento.limpiarDescuentoDepartamento();
        }
    });
    self.aplicaRecargo = ko.observable(false);
    self.aplicaRecargo.subscribe(function(val) {
        if (!val) {
            self.recargo.limpiarRecargoDepartamento();
        }
    });

    self.cargarCargoAgrupador = function(data, departamentos) {
        self.cargar(data);
        if (data && data.cargos && data.cargos.length > 0) {
            ko.utils.arrayForEach(data.cargos, function(c) {
                ko.utils.arrayForEach(departamentos, function(dv) {
                    if (c.departamento.id == dv.id()) {
                        dv.seleccionado(true);
                        dv.cargo.limpiarCargoDepartamento();
                        dv.cargo.cargarCargoDepartamento(c);
                        return false;
                    }
                });
                ko.utils.arrayForEach(departamentos, function(dv) {
                    if (!dv.seleccionado()) {
                        dv.cargo.monto(0);
                    }
                });
            });
        }
        self.descuento.cargarDescuentoDepartamento(data ? data.descuento
                : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.mantenimientoDepartamento(data ? data.mantenimientoDepartamento
                : false);
        self.monto(data ? data.monto : undefined);
        if (self.consumo) {
            if (self.consumo instanceof ConsumoAgrupadorSimple) {
                self.consumo.cargarCAS(data.consumo);
            } else if (self.consumo instanceof ConsumoAgrupadorProrrateo) {
                self.consumo.cargarCAP(data.consumo);
            } else if (self.consumo instanceof ConsumoAgrupadorIndiviso) {
                self.consumo.cargarCAI(data.consumo);
            }
        }
        self.recargo.cargarRecargoDepartamento(data ? data.recargo : undefined);

        if (self.descuento.id()) {
            self.aplicaDescuento(true);
        }
        if (self.recargo.id()) {
            self.aplicaRecargo(true);
        }
    }

    self.limpiarCargoAgrupador = function() {
        self.limpiar();

        self.cargos([]);
        self.descuento.limpiarDescuentoDepartamento();
        self.fecha(undefined);
        self.mantenimientoDepartamento(false);
        self.monto(0);
        if (self.consumo) {
            if (self.consumo instanceof ConsumoAgrupadorSimple) {
                self.consumo.limpiarCAS();
            } else if (self.consumo instanceof ConsumoAgrupadorProrrateo) {
                self.consumo.limpiarCAP();
            } else if (self.consumo instanceof ConsumoAgrupadorIndiviso) {
                self.consumo.limpiarCAI();
            }
        }
        self.recargo.limpiar();
        self.aplicaDescuento(false);
        self.aplicaRecargo(false);
    }

    self.getJsonCargoAgrupador = function(departamentos) {
        var cargo = self
                .estructurarCargoAgrupador(ko.toJS(self), departamentos);
        cargo = validarObject(cargo);
        return cargo;
    }

    self.estructurarCargoAgrupador = function(data, departamentos) {
        data = self.estructurar(data);
        if (!data.monto) {
            data.monto = 0;
        } else {
            data.monto = numeral().unformat(data.monto);
        }

        if (data.descuento && data.aplicaDescuento) {
            data.descuento = self.descuento.getJsonDescuentoDepartamento();
        } else {
            delete data.descuento;
        }

        if (data.recargo && data.aplicaRecargo) {
            data.recargo = self.recargo.getJsonRecargoDepartamento();
        } else {
            delete data.recargo;
        }

        if (departamentos.length > 0) {
            var cargos = [];
            ko.utils.arrayForEach(departamentos, function(d) {
                if (d.seleccionado()) {
                    var cargo = d.cargo.getJsonCargoDepartamento(data.fecha);
                    /*var cargo = d.cargo.getJsonCargoDepartamento(moment(data.fecha,'YYYY-MM-DD').format('DD-MM-YYYY'));*/
    
                    if (cargo.movimientos.length > 0) {
                        cargo.departamento = {
                            id : d.id()
                        }
                        cargo.tipo = {
                            id : data.tipo.id
                        }
                        if (cargo.consumo) {
                            cargo.consumo.costoUnidad = data.consumo.costoUnidad;
                            
                            if (cargo.consumo.consumoFactor) {
                                delete cargo.consumo.consumoFactor;
                            }
                        }
                        cargo.concepto = data.concepto;
                        cargo.fecha = data.fecha;
                        /*cargo.fecha = (moment(data.fecha,'YYYY-MM-DD').format('DD-MM-YYYY'));*/
                        cargo.activo = data.activo;
                        cargo.cuenta = {
                            id : data.cuenta.id
                        }
                        cargo.mantenimientoDepartamento = data.mantenimientoDepartamento;
                        if (!$.isEmptyObject(data.descuento) && (data.descuento.monto!=undefined)) {
                            cargo.descuento = {
                                    id: cargo.descuento ? cargo.descuento.id : undefined,
                                    monto: data.descuento.monto,
                                    porcentaje: data.descuento.porcentaje,
                                    fecha: data.descuento.fecha
                                    /*fecha: (moment(data.descuento.fecha,'YYYY-MM-DD').format('DD-MM-YYYY'))*/
                            }
                        } else {
                            delete cargo.descuento;
                        }
                        if (!$.isEmptyObject(data.recargo) && (data.recargo.monto!=undefined)) {
                            cargo.recargo = {
                                   id:  cargo.recargo ? cargo.recargo.id : undefined,
                                   monto: data.recargo.monto,
                                   redondear: data.recargo.redondear,
                                   porcentaje: data.recargo.porcentaje,
                                   tipoInteres: {
                                       id: data.recargo.tipoInteres.id
                                   },
                                    fecha: data.recargo.fecha
                                   /*fecha: (moment(data.recargo.fecha,'YYYY-MM-DD').format('DD-MM-YYYY'))*/
                            }
                        } else {
                            delete cargo.recargo;
                        }
                        cargos.push(cargo);
                    }
                }
            });
            data.cargos = cargos;
        }

        if (data.tipo.id != AppConfig.catalogos.cargo.tipos.consumo) {
            delete data.consumo;
        } else if (data.consumo) {
            if (data.consumo.catalogoTipo.id === AppConfig.catalogos.consumos.tipos.simple) {
                data.consumo = self.consumo.getJsonCAS();
            } else if (data.consumo.catalogoTipo.id === AppConfig.catalogos.consumos.tipos.prorrateo) {
                data.consumo = self.consumo.getJsonCAP();
            }
        }

        /*if(data.fecha){
            data.fecha = (moment(data.fecha,'YYYY-MM-DD').format('DD-MM-YYYY'));
        }*/

        delete data.aplicaDescuento;
        delete data.aplicaRecargo;
        delete data.total;
        delete data.consumoTotal;

        return data;
    }
}