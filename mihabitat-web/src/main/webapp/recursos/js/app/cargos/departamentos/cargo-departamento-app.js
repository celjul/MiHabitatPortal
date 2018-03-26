var CargoDepartamentoViewModel = function (data) {

    var self = this;

    self.cargo = new CargoDepartamento();
    self.cargo.cargarCargoDepartamento(data.cargo);
    self.cargo.condominio.cargar(data.condominio);

    self.departamento = new Departamento();
    self.departamento.cargar(data.cargo.departamento);

    self.cuentas = ko.observableArray([]);
    if (data && data.cuentas) {
        self.cuentas(ordenar(data.cuentas, true));
    }

    self.consumos = ko.observableArray([]);
    if (data && data.consumos) {
        $.each(data.consumos, function (i, c) {
            var consumo = new TipoConsumo();
            consumo.cargar(c);
            self.consumos.push(consumo);
        });
    }

    self.catalogoInteres = ko.observableArray([]);
    if (data && data.catalogoInteres) {
        $.each(data.catalogoInteres, function (i, tipoInteres) {
            var interes = new Catalogo();
            interes.cargar(tipoInteres);
            self.catalogoInteres.push(interes);
        });
    }

    self.costoUnidad = ko.observable(0).extend({
        currency: 2
    });
    if (data && data.cargo && data.cargo.consumo) {
        self.costoUnidad(data.cargo.consumo.costoUnidad);
    }

    self.cargo.consumo.consumo.subscribe(function (val) {
        if (self.cargo.consumo.tipo.aplicaConversion()) {
            self.cargo.monto(parseFloat(val * self.cargo.consumo.tipo.factorConversion() * self.costoUnidad()).toFixed(2));
        } else {
            self.cargo.monto(parseFloat(val * self.costoUnidad()).toFixed(2));
        }
    });

    self.editable = function () {
        var e = true;
        if (self.cargo.id()) {
            e = false;
        }
        return e;
    }

    self.actualizar = function () {
        if ($("#cargo-form").valid()) {
            if (self.validarFecha()) {
                var cargo = self.cargo.getJsonCargoDepartamento();
                cargo.departamento = {
                    id: self.departamento.id()
                }
                cargo = JSON.stringify(cargo);
                console.log(cargo);
                $.ajax({
                    url: contextPath + "/administrador/cargos-departamentos/actualizar",
                    type: 'POST',
                    dataType: 'json',
                    data: cargo,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        setTimeout(function () {
                            location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                        }, 1000);
                        notificacionExito("El cargo se ha actualizado correctamente");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al actualizar el cargo");
                    }
                });
            } else {
                notificacionAdvertencia("El dia de descuento tiene que ser mayor al día de cargo y el día de recargo debe ser mayor al día de descuento");
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.validarFecha = function () {
        var fechaDeCargo = self.cargo.fecha();
        var fechaDeRecargo = self.cargo.recargo.fecha();
        var fechaDeDescuento = self.cargo.descuento.fecha();

        if (!self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
            return true;
        } else if (self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
            if (self.getDate(fechaDeDescuento) >= self.getDate(fechaDeCargo)) {
                return true;
            } else {
                return false;
            }
        } else if (!self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
            if (self.getDate(fechaDeRecargo) >= self.getDate(fechaDeCargo)) {
                return true;
            } else {
                return false;
            }
        } else if (self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
            if ((self.getDate(fechaDeRecargo) >= self.getDate(fechaDeDescuento))
                && (self.getDate(fechaDeDescuento) >= self
                    .getDate(fechaDeCargo))) {
                return true;
            } else {
                return false;
            }
        }
    }

    self.getDate = function (string) {
        var año = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (año.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }

    self.movimiento = new MovimientoCargo();
    self.movimiento.cargo = {
        id: self.cargo.id()
    }

    self.modalDescuento = function () {
        self.movimiento.limpiarMC();
        self.movimiento.cancelado(false);
        self.movimiento.tipo.id(AppConfig.catalogos.movimientos.tipos.descuento);
        $("#descuento-modal").modal("show");
    }

    self.modalRecargo = function () {
        self.movimiento.limpiarMC();
        self.movimiento.cancelado(false);
        self.movimiento.tipo.id(AppConfig.catalogos.movimientos.tipos.recargo);
        $("#recargo-modal").modal("show");
    }

    self.generarDescuento = function () {
        if ($("#descuento-form").valid()) {
            var descuento = self.movimiento.getJsonMC();
            descuento = JSON.stringify(descuento);
            console.log(descuento);
            $("#descuento-modal").modal("hide");
            $.ajax({
                url: contextPath + "/administrador/cargos-departamentos/descuento/nuevo",
                type: 'POST',
                dataType: 'json',
                data: descuento,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    if (data) {
                        notificacionExito("El descuento se ha generado correctamente.");
                        setTimeout(function () {
                            location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                        }, 1000);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    var error = $.parseJSON(jqXHR.responseText);
                    notificacionError("Ocurrio un error al generar el descuento. " + error.error);
                }
            });

        }
    }

    self.generarRecargo = function () {
        if ($("#recargo-form").valid()) {
            var recargo = self.movimiento.getJsonMC();
            recargo = JSON.stringify(recargo);
            console.log(recargo);
            $("#recargo-modal").modal("hide");
            $.ajax({
                url: contextPath + "/administrador/cargos-departamentos/recargo/nuevo",
                type: 'POST',
                dataType: 'json',
                data: recargo,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    if (data) {
                        notificacionExito("El recargo se ha generado correctamente.");
                        setTimeout(function () {
                            location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                        }, 1000);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al generar el recargo.");
                }
            });
        }
    }

    self.cancelarDescuento = function (data) {
        bootbox.confirm("¿Deseas cancelar el descuento?", function (result) {
            if (result) {
                var descuento = {
                    id: data.id(),
                    cargo: {
                        id: self.cargo.id()
                    }
                }
                descuento = JSON.stringify(descuento);
                console.log(descuento);
                $.ajax({
                    url: contextPath + "/administrador/cargos-departamentos/descuento/cancelar",
                    type: 'POST',
                    dataType: 'json',
                    data: descuento,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        if (data) {
                            notificacionExito("Se cancelo el descuento correctamente.");
                            setTimeout(function () {
                                location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                            }, 1000);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al cancelar el descuento.");
                    }
                });
            }
        });
    }

    self.cancelarRecargo = function (data) {
        bootbox.confirm("¿Deseas cancelar el recargo?", function (result) {
            if (result) {
                var recargo = {
                    id: data.id(),
                    cargo: {
                        id: self.cargo.id()
                    }
                }
                recargo = JSON.stringify(recargo);
                console.log(recargo);
                $.ajax({
                    url: contextPath + "/administrador/cargos-departamentos/recargo/cancelar",
                    type: 'POST',
                    dataType: 'json',
                    data: recargo,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        if (data) {
                            notificacionExito("Se cancelo el recargo correctamente.");
                            setTimeout(function () {
                                location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                            }, 1000);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al cancelar el recargo.");
                    }
                });
            }
        });
    }

    self.cancelarCargo = function () {
        bootbox.confirm("¿Deseas cancelar el cargo y todos sus movimientos existentes?", function (result) {
            if (result) {
                $.ajax({
                    url: contextPath + "/administrador/cargos-departamentos/cancelar",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        idCargo: self.cargo.id()
                    },
                    success: function (data) {
                        if (data) {
                            notificacionExito("Se cancelo el cargo correctamente.");
                            setTimeout(function () {
                                location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + self.cargo.id();
                            }, 1000);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al cancelar el cargo.");
                    }
                });
            }
        });
    }
}

var ListaCargoDepartamentoViewModel = function (data) {
    self = this;

    self.cargos = ko.observableArray([]);

    self.fechaInicial = ko.observable();
    self.fechaFinal = ko.observable();

    self.valida = function () {
        if (self.fechaInicial() && self.fechaFinal()) {
            if (self.getDate(self.fechaInicial()) > self.getDate(self.fechaFinal())) {
                notificacionAdvertencia("La fecha inicial debe ser anterior a la final.");
                return false;
            }
            return true;
        } else {
            notificacionAdvertencia("Especifica la fechas.");
            return false;
        }
    }

    self.getDate = function (string) {
        var año = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (año.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }

    self.consulta = function () {
        console.log("Consulta - " + self.fechaInicial() + " - " + self.fechaFinal());

        if (self.valida()) {
            $.ajax({
                cache: false,
                url: contextPath + "/administrador/cargos-departamentos/listaConFechas",
                data: {
                    inicio: self.fechaInicial(),
                    fin: self.fechaFinal()
                },
                success: function (data) {
                    self.cargos([]);
                    if ($.fn.DataTable.isDataTable("#table-cargos")) {
                        $("#table-cargos").DataTable().clear().destroy();
                    }

                    if (data != undefined && data.length > 0) {
                        ko.utils.arrayForEach(data, function (cargo) {
                            var c = new CargoDepartamento();
                            c.cargarCargoDepartamento(cargo);
                            c.departamento = new Departamento();
                            c.departamento.cargar(cargo.departamento);
                            c.agrupador = {
                                id: cargo.agrupador ? cargo.agrupador.id : undefined
                            }

                            self.cargos.push(c);
                        });
                    }

                    //Se agrega el código para que dinamicamente se agregue el filtro de fechas
                    dibujarTabla("#table-cargos", {
                        "aaSorting": [[0, 'desc']],
                        "aoColumns": [
                            {"iDataSort": 0}, null, null, null, null, null, null, null, null, null
                        ]
                    });
                    $("div.toolbar").html("<div class='col col-md-6'><div class='input-group'><span class='input-group-addon'>De</span><input class='form-control' type='text' name='inicio' id='inicio' required='required' readonly='readonly' style='background: white;' data-bind='value: $root.fechaInicial'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span> </div> </div> <div class='col col-md-6'> <div class='input-group'> <span class='input-group-addon'>A </span> <input class='form-control' type='text' name='fin' id='fin' required='required' readonly='readonly' style='background: white;' data-bind='value: $root.fechaFinal'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span> </div> </div>");
                    $("#inicio, #fin").datepicker();
                    var inicio = document.getElementById('inicio');
                    ko.applyBindingsToNode(inicio, {value: self.fechaInicial});
                    var fin = document.getElementById('fin');
                    ko.applyBindingsToNode(fin, {value: self.fechaFinal});


                }
            });
        }
    }

    if (!self.fechaInicial() && !self.fechaFinal()) {
        if (data && data.fin && data.inicio) {
            self.fechaInicial(data.inicio);
            self.fechaFinal(data.fin);
        } else {
            var fecha = new Date();
            self.fechaInicial("01-" + (((fecha.getMonth() + 1).toString().length < 2) ? ('0' + (fecha.getMonth() + 1)) : (fecha.getMonth() + 1)) + "-" + fecha.getFullYear());
            self.fechaFinal(fecha.getDate() + "-" + (((fecha.getMonth() + 1).toString().length < 2) ? ('0' + (fecha.getMonth() + 1)) : (fecha.getMonth() + 1)) + "-" + fecha.getFullYear());
        }
        self.consulta();
    }

    self.fechaInicial.subscribe(function () {
        self.consulta();
    });

    self.fechaFinal.subscribe(function () {
        self.consulta();
    });

    /*if (data.cargos != undefined && data.cargos.length > 0) {
     ko.utils.arrayForEach(data.cargos, function(cargo) {
     var c = new CargoDepartamento();
     c.cargarCargoDepartamento(cargo);
     c.departamento = new Departamento();
     c.departamento.cargar(cargo.departamento);
     c.agrupador = {
     id: cargo.agrupador?cargo.agrupador.id:undefined
     }

     self.cargos.push(c);
     });
     }*/

    self.actualizar = function (data) {
        location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + data.id();
    };

    self.actualizarSimilares = function (data) {
        location.href = contextPath + "/administrador/cargos-departamentos/agrupador/actualizar/" + data.agrupador.id;
    }
}