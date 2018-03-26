var CuentaViewModel = function (data) {
    var self = this;

    self.cuenta = new Cuenta();
    self.agrupadores = ko.observableArray([]);
    self.bancos = ko.observableArray([]);
    self.cuentasContables = ko.observableArray([]);
    self.condominio = new Condominio(undefined);
    self.actualizarCuentaPadre = ko.observable(false);
    self.cuenta.activo(true);

    self.operacionCorrecta = ko.observable(false);

//	if (!data.cuenta.fecha()){
//		data.cuenta.fecha(new Date());
//		console.log("LE AGREGO LA FECHA DE HOY");
//	}

    if (data && data.cuenta) {
        self.actualizarCuentaPadre(true);
        self.cuenta.cargar(data.cuenta);
        self.cuenta.conocerCuenta(self.cuentasContables);
    } else {
        self.actualizarCuentaPadre(false);
    }


    if (data && data.condominio) {
        self.condominio.cargar(data.condominio);
    }

    if (data && data.agrupadores) {
        ko.utils.arrayForEach(data.agrupadores, function (a) {
            var agrupador = new AgrupadorSat();
            agrupador.cargar(a);
            self.agrupadores.push(agrupador);
        });
    }

    if (data && data.bancos) {
        ko.utils.arrayForEach(data.bancos, function (b) {
            var bancoSat = new BancoSat();
            bancoSat.cargar(b);
            self.bancos.push(bancoSat);

        });
    }

    if (data && data.cuentasContables) {
        self.cuentasContables(ordenar(data.cuentasContables, false));
    }

    self.editar = function (data) {
        if (data && data.cuenta) {
            self.actualizarCuentaPadre(true);
            self.cuenta.cargar(data.cuenta);
            self.cuenta.condominio.cargar({
                id: self.condominio.id()
            });
            self.cuenta.conocerCuenta(self.cuentasContables);

        } else {
            self.actualizarCuentaPadre(false);
        }
        $("#agrupadorSat").select2();
        $("#bancoSat").select2();
        $("#cuentaPadre").select2();
    }

    self.limpiar = function (data) {
        self.cuenta.limpiar();
        self.actualizarCuentaPadre(false);
        self.cuenta.condominio.cargar({
            id: self.condominio.id()
        });
        $("#agrupadorSat").select2();
        $("#bancoSat").select2();
        $("#cuentaPadre").select2();
        $("#fecha").datepicker('setDate', new Date());
    }

    self.guardar = function () {
        if (!self.cuenta.fecha()) {
            self.cuenta.fecha($("#fecha").val());
        }
        console.log(self.cuenta.fecha());
        if ($("#cuenta-form").valid()) {
            if (self.cuenta.cuentaCorrecta()) {
                var cuenta = JSON.stringify(self.cuenta.getJson());
                console.log(cuenta);
                $.ajax({
                    async: false,
                    cache: false,
                    url: contextPath + "/administrador/cuentas/guardar",
                    type: 'POST',
                    dataType: 'json',
                    data: cuenta,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        self.actualizarCuentaPadre(true);
                        self.cuenta.limpiar();
                        self.cuenta.cargar(data);
                        console.log("LA CUENTA AL MOMENTO DE GUARDARLA");
                        console.log(JSON.stringify(data));
                        $("#myModalCuenta").modal("hide");
                        notificacionExito("La cuenta se ha guardado correctamente");
                        self.cuenta.conocerCuenta(self.cuentasContables);
                        console.log("ACTUALIZAR CUENTA PADRE");
                        console.log(self.actualizarCuentaPadre());
                        self.operacionCorrecta(true);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al guardar la cuenta");
                        self.operacionCorrecta(false);
                    }
                });
            } else {
                notificacionAdvertencia("La cuenta ya existe, intente con otro numero de cuenta.");
                self.operacionCorrecta(false);
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores");
            self.operacionCorrecta(false);
        }
    }

    self.actualizar = function () {
        if ($("#cuenta-form").valid() && self.cuenta.permisoActualizarCuenta()) {
            var cuenta = JSON.stringify(self.cuenta.getJson());
            $.ajax({
                async: false,
                cache: false,
                url: contextPath + "/administrador/cuentas/actualizar",
                type: 'POST',
                dataType: 'json',
                data: cuenta,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.cuenta.limpiar();
                    self.cuenta.cargar(data);
                    $("#myModalCuenta").modal("hide");
                    notificacionExito("La cuenta se ha actualizado correctamente");
                    self.cuenta.conocerPadre();
                    self.cuenta.activarCuentaSuperPadre(false);
                    self.operacionCorrecta(true);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar la cuenta");
                    self.operacionCorrecta(false);
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
            self.operacionCorrecta(false);
        }
    }
}

var ListaCuentasViewModel = function (data) {
    self = this;

    self.cuentaViewModel = new CuentaViewModel(data);

    self.cuentas = ko.observableArray([]);

    if (data.cuentas != undefined && data.cuentas.length > 0) {
        ko.utils.arrayForEach(data.cuentas, function (c) {
            var cta = new Cuenta();
            cta.cargar(c);
            self.cuentas.push(cta);
        });
    }

    self.nuevo = function (data) {
        self.cuentaViewModel.limpiar();
    }

    self.editar = function (data) {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + "/administrador/cuentas/actualizar/" + (typeof data.id === "function" ? data.id() : data.id),
            type: 'GET',
            dataType: 'json',
            data: '',
            success: function (data) {
                self.cuentaViewModel.editar(
                    {
                        cuenta: data
                    });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al guardar el mantenimiento");
            }
        });
    }

    self.guardar = function (data) {
        self.cuentaViewModel.guardar();
        if (self.cuentaViewModel.operacionCorrecta()) {
            var cuenta = self.cuentaViewModel.cuenta;
            var item = ko.utils.arrayFirst(self.cuentaViewModel.agrupadores(), function (a) {
                return a.id() === cuenta.agrupadorSat.id();
            });
            cuenta.agrupadorSat.cargar(item ? item.getJson() : undefined);
            item = ko.utils.arrayFirst(self.cuentas(), function (c) {
                return c.id() === cuenta.padre.id();
            });
            cuenta.padre.cargar(item ? item.getJson() : undefined);
            /*self.cuentas.push(self.cuentaViewModel.cuenta);*/
            var cta = new Cuenta();
            cta.cargar(self.cuentaViewModel.cuenta.getJson());
            self.cuentas.push(cta);
        }
        setTimeout(function () {
            location.href = contextPath + "/administrador/cuentas/lista";
        }, 1000);
    }

    self.actualizar = function (data) {
        self.cuentaViewModel.actualizar();
        if (self.cuentaViewModel.operacionCorrecta()) {
            var cuenta = self.cuentaViewModel.cuenta;
            var item = ko.utils.arrayFirst(self.cuentaViewModel.agrupadores(), function (a) {
                return a.id() === cuenta.agrupadorSat.id();
            });
            cuenta.agrupadorSat.cargar(item ? item.getJson() : undefined);
            item = ko.utils.arrayFirst(self.cuentas(), function (c) {
                return c.id() === cuenta.padre.id();
            });
            if (item) {
                cuenta.padre.cargar(item ? item.getJson() : undefined);
            }
            var encontrado = ko.utils.arrayFirst(self.cuentas(), function (item) {
                return item.id() == cuenta.id();
            });
            encontrado.cargar(cuenta.getJson());
            location.href = contextPath + "/administrador/cuentas/lista";
        }
        setTimeout(function () {
            location.href = contextPath + "/administrador/cuentas/lista";
        }, 1000);
    }
}