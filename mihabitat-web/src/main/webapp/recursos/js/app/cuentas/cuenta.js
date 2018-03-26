var Cuenta = function (data) {
    var self = this;

    self.activo = ko.observable(data ? data.activo : true);
    self.actual = ko.observable(data ? data.actual : undefined);
    self.agrupadorSat = new AgrupadorSat(data ? data.agrupadorSat : undefined);
    self.bancoSat = new BancoSat(data ? data.bancoSat : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.fecha = ko.observable(data ? data.fecha : undefined);
    self.padre = new CuentaPadre(data ? data.padre : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.inicial = ko.observable(data ? data.inicial : 0.00);
    self.inicial.subscribe(function () {
        self.inicial(numeral(self.inicial()).format('0,0.00'));
    });
    self.nombre = ko.observable(data ? data.nombre : undefined);
    self.numero = ko.observable(data ? data.numero : undefined);
    self.numeroHija = ko.observable(data ? data.numeroHija : undefined);
    self.numeroNieto = ko.observable(data ? data.numeroNieto : undefined);
    self.numeroBis = ko.observable(data ? data.numeroBis : undefined);
    self.campoBanco = ko.observable(false);
    self.campoPadre = ko.observable(data ? data.campoPadre : undefined);
    self.campoHija = ko.observable(data ? data.campoHija : undefined);
    self.campoNieta = ko.observable(data ? data.campoNieta : undefined);
    self.campoBisnieta = ko.observable(data ? data.campoBisnieta : undefined);
    self.minPadre = ko.observable("0");
    self.minHija = ko.observable("0");
    self.minNieto = ko.observable("0");
    self.minBis = ko.observable("0");
    self.requiredPadre = ko.observable("false");
    self.requiredHija = ko.observable("false");
    self.requiredNieto = ko.observable("false");
    self.requiredBis = ko.observable("false");
    self.cuentaCorrecta = ko.observable(true);
    self.isSelectedPadre = ko.observable(false);
    self.isSelectedHija = ko.observable(false);
    self.isSelectedNieto = ko.observable(false);
    self.isSelectedBis = ko.observable(false);
    self.maxSaldoInicial = ko.observable("");
    self.cuentaAnterior = 0;
    self.activarCuentaSuperPadre = ko.observable(true);
    self.permisoActualizarCuenta = ko.observable(true);

    self.numero.subscribe(function () {
        if (self.numero()) {
            if ((self.numero().length == 4 && self.isSelectedPadre()) && !(self.cuentaAnterior == self.numero())) {
                self.validaCuentaExistente();
            }
        }
    });

    self.numeroHija.subscribe(function () {
        if (self.numeroHija()) {
            if ((self.numeroHija().length == 4 && self.isSelectedHija()) && !(self.cuentaAnterior == self.numeroHija())) {
                self.validaCuentaExistente();

            }
        }
    });

    self.numeroNieto.subscribe(function () {
        if (self.numeroNieto()) {
            if ((self.numeroNieto().length == 4 && self.isSelectedNieto()) && !(self.cuentaAnterior == self.numeroNieto())) {
                self.validaCuentaExistente();
            }
        }
    });

    self.numeroBis.subscribe(function () {
        if (self.numeroBis()) {
            if ((self.numeroBis().length == 4 && self.isSelectedBis()) && !(self.cuentaAnterior == self.numeroBis())) {
                self.validaCuentaExistente();
            }
        }
    });

    self.cargar = function (data) {
        self.activo(data ? data.activo : undefined);
        self.actual(data ? data.actual : undefined);
        self.agrupadorSat.cargar(data ? data.agrupadorSat : undefined);
        self.bancoSat.cargar(data ? data.bancoSat : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.padre.cargar(data ? data.padre : undefined);
        self.id(data ? data.id : undefined);
        self.inicial(data ? data.inicial : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.numero(data ? data.numero : undefined);
        self.numeroHija(data ? data.numeroHija : undefined);
        self.numeroNieto(data ? data.numeroNieto : undefined);
        self.numeroBis(data ? data.numeroBis : undefined);
        self.campoBanco(false);
        self.campoHija(data ? data.campoHija : undefined);
        self.campoNieta(data ? data.campoNieta : undefined);
        self.campoBisnieta(data ? data.campoBisnieta : undefined);
        self.campoPadre(data ? data.campoPadre : undefined);
        self.minPadre("0");
        self.minHija("0");
        self.minNieto("0");
        self.minBis("0");
        self.requiredPadre("false");
        self.requiredHija("false");
        self.requiredNieto("false");
        self.requiredBis("false");
        self.cuentaCorrecta(true);
        self.isSelectedPadre(false);
        self.isSelectedHija(false);
        self.isSelectedNieto(false);
        self.isSelectedBis(false);
        self.maxSaldoInicial("");
        self.cuentaAnterior = 0;
        self.activarCuentaSuperPadre(true);
        self.permisoActualizarCuenta(true);
    }

    self.limpiar = function () {
        self.activo(true);
        self.actual(undefined);
        self.agrupadorSat.limpiar();
        self.bancoSat.limpiar();
        self.condominio.limpiar();
        self.fecha(undefined);
        self.padre.limpiar();
        self.id(undefined);
        self.inicial(0.00);
        self.nombre(undefined);
        self.numero(undefined);
        self.numeroHija(undefined);
        self.numeroNieto(undefined);
        self.numeroBis(undefined);
        self.campoBanco(false);
        self.campoHija(undefined);
        self.campoNieta(undefined);
        self.campoBisnieta(undefined);
        self.campoPadre(undefined);
        self.minPadre("0");
        self.minHija("0");
        self.minNieto("0");
        self.minBis("0");
        self.requiredPadre("false");
        self.requiredHija("false");
        self.requiredNieto("false");
        self.requiredBis("false");
        self.cuentaCorrecta(true);
        self.isSelectedPadre(false);
        self.isSelectedHija(false);
        self.isSelectedNieto(false);
        self.isSelectedBis(false);
        self.maxSaldoInicial("");
        self.cuentaAnterior = 0;
        self.activarCuentaSuperPadre(true);
        self.permisoActualizarCuenta(true);
    }

    self.getJson = function () {
        var cuenta = self.estructurar(ko.toJS(self));
        cuenta = validarObject(cuenta);
        return cuenta;
    }
    self.estructurar = function (data) {
        //data.inicial = numeral().unformat(data.inicial);
        delete data.campoBanco;
        delete data.campoHija;
        delete data.campoNieta;
        delete data.campoBisnieta;
        delete data.campoPadre;
        delete data.minPadre;
        delete data.minHija;
        delete data.minNieto;
        delete data.minBis;
        delete data.requiredPadre;
        delete data.requiredHija;
        delete data.requiredNieto;
        delete data.requiredBis;
        delete data.cuentaCorrecta;
        delete data.isSelectedPadre;
        delete data.isSelectedHija;
        delete data.isSelectedNieto;
        delete data.isSelectedBis;
        delete data.maxSaldoInicial;
        delete data.cuentaAnterior;
        delete data.activarCuentaSuperPadre;
        delete data.permisoActualizarCuenta;
        if (!data.agrupadorSat.id) {
            delete data.agrupadorSat;
        }
        if (!data.bancoSat.id) {
            delete data.bancoSat;
        }
        data.inicial = numeral().unformat(data.inicial);
        return data;
    }

    self.validaNombre = function () {
        if (self.nombre()) {
            var nombre = self.nombre().toUpperCase().trim();
            if (nombre != 'EGRESOS' && nombre != 'INGRESOS' && nombre != 'BANCOS'
                && nombre != 'CAJAS' && nombre != 'RECARGOS' && nombre != 'DESCUENTOS' && nombre != 'SALDO A FAVOR') {
                return true;
            } else {
                notificacionAdvertencia("Este nombre está reservado.");
                self.nombre(undefined);
            }
        }
    }

    self.validaSaldoInicial = function () {
        if (self.inicial()) {
            self.maxSaldoInicial("12");
        } else {
            self.maxSaldoInicial("");
        }
    }

    self.conocerCuenta = function (cuentas) {
        self.conocerPadre(cuentas);
        self.conocerBanco(cuentas);

    }

    self.verificarPadreActivo = function () {
        if (self.id() && !self.activo()) {
            if (!self.padre.activo()) {
                self.permisoActualizarCuenta(false);
                notificacionAdvertencia("No puede activar la cuenta ya que la cueta padre esta desactivada, active la cuenta padre.");
            }
        }
    }

    self.limpiarCuentas = function () {
        self.numero(undefined);
        self.numeroHija(undefined);
        self.numeroNieto(undefined);
        self.numeroBis(undefined);
    }

    self.conocerPadre = function (cuentas) {
        self.activarCuentaSuperPadre(false);
        if (self.padre.id()) {
            if (cuentas) {
                var item = ko.utils.arrayFirst(cuentas(), function (c) {
                    return c.id === self.padre.id();
                });
            }
            if (item) {
                if (item.numero && item.numeroHija == null) {
                    var validator = $("#cuenta-form").validate();
                    validator.resetForm();
                    $(".input").removeClass("state-error");
                    $(".input").removeClass("state-success");
                    self.minPadre("4");
                    self.minHija("4");
                    self.minNieto("0");
                    self.minBis("0");
                    self.requiredPadre("true");
                    self.requiredHija("true");
                    self.requiredNieto("false");
                    self.requiredBis("false");
                    self.numero(item.numero);
                    if (self.numeroHija() && self.id() != undefined) {
                        self.numeroHija(self.numeroHija());
                        self.cuentaAnterior = self.numeroHija();
                    } else {
                        self.numeroHija(undefined);
                    }
                    self.numeroNieto(undefined);
                    self.numeroBis(undefined);
                    self.campoPadre(true);
                    self.campoHija(false);
                    self.campoNieta(true);
                    self.campoBisnieta(true);
                    self.increntarCuenta(item.id, 2);
                } else if (item.numero && item.numeroHija && item.numeroNieto == null) {
                    var validator = $("#cuenta-form").validate();
                    validator.resetForm();
                    $(".input").removeClass("state-error");
                    $(".input").removeClass("state-success");
                    self.minPadre("4");
                    self.minHija("4");
                    self.minNieto("4");
                    self.minBis("0");
                    self.requiredPadre("true");
                    self.requiredHija("true");
                    self.requiredNieto("true");
                    self.requiredBis("false");
                    self.numero(item.numero);
                    self.numeroHija(item.numeroHija);
                    if (self.numeroNieto() && self.id() != undefined) {
                        self.numeroNieto(self.numeroNieto());
                        self.cuentaAnterior = self.numeroNieto();
                    } else {
                        self.numeroNieto(undefined);
                    }
                    self.numeroBis(undefined);
                    self.campoPadre(true);
                    self.campoHija(true);
                    self.campoNieta(false);
                    self.campoBisnieta(true);
                    self.increntarCuenta(item.id, 3);
                } else if (item.numero && item.numeroHija && item.numeroNieto && item.numeroBis == null) {
                    var validator = $("#cuenta-form").validate();
                    validator.resetForm();
                    $(".input").removeClass("state-error");
                    $(".input").removeClass("state-success");
                    self.minPadre("4");
                    self.minHija("4");
                    self.minNieto("4");
                    self.minBis("4");
                    self.requiredPadre("true");
                    self.requiredHija("true");
                    self.requiredNieto("true");
                    self.requiredBis("true");
                    self.numero(item.numero);
                    self.numeroHija(item.numeroHija);
                    self.numeroNieto(item.numeroNieto);
                    if (self.numeroBis() && self.id() != undefined) {
                        self.numeroBis(self.numeroBis());
                        self.cuentaAnterior = self.numeroBis();
                    }
                    self.campoPadre(true);
                    self.campoHija(true);
                    self.campoNieta(true);
                    self.campoBisnieta(false);
                    self.increntarCuenta(item.id, 4);
                } else if (item.numero && item.numeroHija && item.numeroNieto && item.numeroBis) {
                    var validator = $("#cuenta-form").validate();
                    validator.resetForm();
                    $(".input").removeClass("state-error");
                    $(".input").removeClass("state-success");
                    self.limpiarCuentas();
                    self.minPadre("4");
                    self.minHija("4");
                    self.minNieto("4");
                    self.minBis("4");
                    self.requiredPadre("true");
                    self.requiredHija("true");
                    self.requiredNieto("true");
                    self.requiredBis("true");
                    self.numero(item.numero);
                    self.numeroHija(item.numeroHija);
                    self.numeroNieto(item.numeroNieto);
                    self.numeroBis(item.numeroBis);
                    self.campoPadre(true);
                    self.campoHija(true);
                    self.campoNieta(true);
                    self.campoBisnieta(true);
                }
            }
        } else {
            self.activarCuentaSuperPadre(true);

            self.cuentaAnterior = self.numero();
            self.numeroHija(undefined);
            self.numeroNieto(undefined);
            self.numeroBis(undefined);
            if (self.nombre()) {
                self.campoPadre(false);
            } else {
                self.campoPadre(true);
            }
            self.campoHija(true);
            self.campoNieta(true);
            self.campoBisnieta(true);

        }
    }

    self.conocerBanco = function (cuentas) {
        self.campoBanco(false);
        var cuentaBanco = AppConfig.catalogos.cuentas.banco;
        var BreakException = {};
        if (self.padre.id()) {
            if (cuentas) {
                var item = ko.utils.arrayFirst(cuentas(), function (c) {
                    return c.id === self.padre.id();
                });
                ko.utils.arrayForEach(ko.toJS(cuentas()), function (c) {
                    if (c.nombre == cuentaBanco) {
                        if (c.id === item.id) {
                            self.campoBanco(true);
                        } else {
                            if (c.cuentasHijas) {
                                ko.utils.arrayForEach(c.cuentasHijas, function (hija) {
                                    if (hija.id === item.id) {
                                        try {
                                            self.campoBanco(true);
                                            throw BreakException;
                                        } catch (e) {
                                        }
                                    } else {
                                        if (hija.cuentasHijas) {
                                            ko.utils.arrayForEach(hija.cuentasHijas, function (nieta) {
                                                try {
                                                    if (nieta.id === item.id) {
                                                        self.campoBanco(true);
                                                        throw BreakException;
                                                    }
                                                } catch (e) {
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    self.increntarCuenta = function (idCuentaPadre, nivel) {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + '/administrador/cuentas/incrementar',
            type: 'POST',
            dataType: 'json',
            data: {
                idPadre: idCuentaPadre,
                nivelCuenta: nivel
            },
            success: function (data) {
                console.log(ko.toJS(data));
                if (data) {
                    if (nivel === 2) {
                        self.numeroHija(ko.toJS(data));
                    } else if (nivel === 3) {
                        self.numeroNieto(ko.toJS(data));
                    } else if (nivel === 4) {
                        self.numeroBis(ko.toJS(data));
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Error.");
            }
        });
    }

    self.validaCuentaExistente = function () {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + '/administrador/cuentas/comprobar',
            type: 'POST',
            dataType: 'json',
            data: {
                cuentaPadre: self.numero(),
                cuentaHija: self.numeroHija(),
                cuentaNieto: self.numeroNieto(),
                cuentaBis: self.numeroBis()
            },
            success: function (data) {
                console.log(ko.toJS(data));
                if (data.id) {
                    bootbox.confirm("La cuenta ya existe ¿Deseas cargar los datos para editarla?", function (result) {
                        if (result) {
                            self.limpiar();
                            self.cargar(data);
                        } else {
                            if (!self.campoPadre()) {
                                self.numero(undefined);
                            }
                            if (!self.campoHija()) {
                                self.numeroHija(undefined);
                            }
                            if (!self.campoNieta()) {
                                self.numeroNieto(undefined);
                            }
                            if (!self.campoBisnieta()) {
                                self.numeroBis(undefined);
                            }
                        }
                    });
                } else {
                    self.cuentaCorrecta(true);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                self.cuentaCorrecta(false);
                notificacionError("Error al verificar la cuenta.");
            }
        });
    }

}

var CuentaPadre = function (data) {
    var self = this;
    self.id = ko.observable(data ? data.id : undefined);
    self.nombre = ko.observable(data ? data.nombre : undefined);
    self.activo = ko.observable(data ? data.activo : undefined);


    self.cargar = function (data) {
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.activo(data ? data.activo : undefined);
    }

    self.limpiar = function () {
        self.id(undefined);
        self.nombre(undefined);
        self.activo(undefined);
    }
}

var ordenar = function (cuenta, pintarBisnietos) {
    var cuentas = [];
    ko.utils.arrayForEach(cuenta, function (c) {
        if (c && c.numeroHija == null) {
            var padre = c;
            cuentas.push(padre);
            if (padre.cuentasHijas) {
                ko.utils.arrayForEach(padre.cuentasHijas, function (h) {
                    h.nombre = "-- " + h.nombre;
                    var hijo = h;
                    cuentas.push(hijo);
                    if (hijo.cuentasHijas) {
                        ko.utils.arrayForEach(hijo.cuentasHijas, function (n) {
                            n.nombre = "--------- " + n.nombre;
                            var nieto = n;
                            cuentas.push(nieto);
                            if (nieto.cuentasHijas && pintarBisnietos) {
                                ko.utils.arrayForEach(nieto.cuentasHijas, function (b) {
                                    b.nombre = "-------------- " + b.nombre;
                                    var bisnieto = b;
                                    cuentas.push(bisnieto);
                                });
                            }

                        });
                    }
                });
            }
        }
    });
    return cuentas;
}

var CuentaSelect = function (data) {
    var self = this;

    self.id = data ? data.id : undefined;
    self.nombre = data ? data.nombre : undefined;
}