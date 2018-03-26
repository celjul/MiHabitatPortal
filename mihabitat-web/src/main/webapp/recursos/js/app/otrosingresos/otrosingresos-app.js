var OtroIngesoViewApp = function(data) {

    var self = this;

    self.bancosCajas = ko.observableArray();
    if (data && data.bancosCajas) {
        self.bancosCajas(ordenar(data.bancosCajas, true));
    }

    self.cuentasIngresos = ko.observableArray();
    if (data && data.cuentasIngresos) {
        self.cuentasIngresos(ordenar(data.cuentasIngresos, true));
    }

    self.metodosPago = ko.observableArray();
    if (data && data.metodosPago) {
        ko.utils.arrayForEach(data.metodosPago, function (mp) {
            if(mp.id != AppConfig.catalogos.metodos.saldofavor) {
                var metodo = new Catalogo();
                metodo.cargar(mp);
                self.metodosPago.push(mp);
            }
        });
    }

    self.condominio = ko.observable(data.condominio.id);
    self.otroIngreso = new OtroIngreso();

    self.saldo = ko.observable();
    /*self.getSaldo = function() {
        if (self.otroIngreso.movimientoOtroIngreso.cuenta.id) {
            $.ajax({
                cache : false,
                url : contextPath + '/administrador/otrosingresos/saldo',
                type : 'POST',
                dataType : 'json',
                data : {
                    idCuenta : self.otroIngreso.movimientoOtroIngreso.cuenta.id()
                },
                success : function(data) {
                    self.saldo(data);
                    notificacionAdvertencia("El saldo de la cuenta es: " + data);
                }, error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("No se calculo el saldo.");
                }
            });
        }
    }*/

    self.valida = function() {
        if (!$("#otroIngreso-form").valid()) {
            notificacionError("El formulario no es válido");
            return false;
        }
        if (self.otroIngreso.conceptos().length < 0) {
            notificacionError("No existe ningun concepto del ingreso");
            return false;
        }
        var total = 0;
        ko.utils.arrayForEach(self.otroIngreso.conceptos(), function (d) {
            total = total + d.movimientoConceptoOtroIngreso.haber();
        });
        /*if (total > self.saldo()) {
            notificacionError("El total de detalles supera el saldo de la cuenta");
            return false;
        }*/
        return true;
    }

    self.guardar = function() {
        if (self.valida()) {
            var otroIngreso = self.otroIngreso.getJson();
            otroIngreso.condominio = {
                id: self.condominio()
            }
            console.log(JSON.stringify(otroIngreso));
            $.ajax({
                url: contextPath + "/administrador/otrosingresos/guardar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(otroIngreso),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("El ingreso se ha guardado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/otrosingresos/lista";
                    }, 1000);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el ingreso");
                }
            });
        }
    }

    self.actualizar = function() {
        if (self.valida()) {
            var otroIngreso = self.otroIngreso.getJson();
            otroIngreso.condominio = {
                id: self.condominio()
            }
            console.log(JSON.stringify(otroIngreso));
            $.ajax({
                url: contextPath + "/administrador/otrosingresos/actualizar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(otroIngreso),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("El ingreso se ha actualizado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/otrosingresos/lista";
                    }, 1000);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al actualizar el ingreso");
                }
            });
        }
    }

    if (data && data.otroIngreso) {
        self.otroIngreso.cargar(data.otroIngreso);
        /*self.getSaldo();*/

        setTimeout(function() {
            self.saldo(self.saldo() + self.otroIngreso.movimientoOtroIngreso.haber());
        });
    }

    self.cancelar = function() {

    }
}

var OtroIngresoListViewApp = function(data) {

    var self = this;

    self.otrosIngresos = ko.observableArray();

    if (data && data.otrosIngresos) {
        ko.utils.arrayForEach(data.otrosIngresos, function (g) {
            self.otrosIngresos.push(g);
        });
    }

    self.ver = function(data) {
        location.href = contextPath + "/administrador/otrosingresos/editar/" + data.id;
    }
}