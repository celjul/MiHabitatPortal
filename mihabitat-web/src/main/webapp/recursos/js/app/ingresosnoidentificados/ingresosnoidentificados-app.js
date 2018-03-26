var IngresoNoIdentificadoViewApp = function(data) {

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

    if(data && data.condominio) {
        self.condominio = ko.observable(data.condominio.id);
    }
    self.ingresoNoIdentificado = new IngresoNoIdentificado();

    self.idContacto = ko.observable();

    self.item = new Item();

    self.item.label.subscribe( function( val ) {
        if( val.length > 0 ) {
            self.item.limpiar();
            //console.log( val );

            $( "#contacto" ).autocomplete({
                minLength : 1,
                source : function( request, response ) {
                    $.ajax({
                        url: contextPath + "/administrador/estado-cuenta/buscar",
                        dataType: "json",
                        data : {
                            key : val
                        },
                        success: function( data ) {
                            response( $.map( data, function( item ) {
                                return {
                                    id : item.id,
                                    label: item.label,
                                    url: item.url
                                };
                            }));
                        }
                    });
                },
                select : function( event, ui ) {
                    self.item.id( ui.item.id );
                    self.item.label( ui.item.label );
                    self.item.url( ui.item.url );
                    self.idContacto( ui.item.id );
                    return false;
                }
            });
        }
    });

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

    self.editable = function () {
        var e = true;
        if (self.ingresoNoIdentificado.id()) {
            e = false;
        }
        return e;
    }

    self.valida = function() {
        if (!$("#ingresoNoIdentificado-form").valid()) {
            notificacionError("Verifique haber llenado todos los campos requeridos.");
            return false;
        }
        /*if (self.ingresoNoIdentificado.conceptos().length < 0) {
            notificacionError("No existe ningun concepto del ingreso");
            return false;
        }*/
        /*var total = 0;
        ko.utils.arrayForEach(self.otroIngreso.conceptos(), function (d) {
            total = total + d.movimientoConceptoOtroIngreso.haber();
        });*/
        /*if (total > self.saldo()) {
            notificacionError("El total de detalles supera el saldo de la cuenta");
            return false;
        }*/
        return true;
    }

    self.guardar = function() {
        if (self.valida()) {
            var ingresoNoIdentificado = self.ingresoNoIdentificado.getJson();
            ingresoNoIdentificado.condominio = {
                id: self.condominio()
            }
            console.log(JSON.stringify(ingresoNoIdentificado));
            $.ajax({
                url: contextPath + "/administrador/ingresosnoidentificados/guardar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(ingresoNoIdentificado),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("El ingreso se ha guardado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/ingresosnoidentificados/lista";
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
            var ingresoNoIdentificado = self.ingresoNoIdentificado.getJson();
            ingresoNoIdentificado.condominio = {
                id: self.condominio()
            }
            $.ajax({
                url: contextPath + "/administrador/ingresosnoidentificados/actualizar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(ingresoNoIdentificado),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("El ingreso se ha actualizado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/ingresosnoidentificados/lista";
                    }, 1000);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al actualizar el ingreso");
                }
            });
        }
    }

    if (data && data.ingresoNoIdentificado) {
        self.ingresoNoIdentificado.cargar(data.ingresoNoIdentificado);
        /*self.getSaldo();*/

        /*setTimeout(function() {
            self.saldo(self.saldo() + self.ingresoNoIdentificado.movimientoIngresoNoIdentificado.haber());
        });*/
    }

    self.cancelar = function() {
        bootbox.confirm("¿Deseas cancelar este ingreso?", function(result) {
            if (result) {
                var ingresoNoIdentificado = self.ingresoNoIdentificado.id();
                console.log(ingresoNoIdentificado);
                $.ajax({
                    url: contextPath + "/administrador/ingresosnoidentificados/cancelar/" + ingresoNoIdentificado,
                    type: 'POST',
                    dataType: 'json',
                    data: '',
                    success: function (data) {
                        notificacionExito("El ingreso se ha actualizado correctamente");
                        setTimeout(function () {
                            location.href = contextPath + "/administrador/ingresosnoidentificados/lista";
                        }, 1000);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al actualizar el ingreso");
                    }
                });
            }
        });
    }

    self.asignarAbono = function() {

        console.log(self.idContacto());
        var ingresoNoIdentificado = self.ingresoNoIdentificado.getJson();
        ingresoNoIdentificado = JSON.stringify(ingresoNoIdentificado);
        console.log(ingresoNoIdentificado);
        $.ajax({
            url: contextPath + "/administrador/ingresosnoidentificados/asignarIngresoNoIdentificados",
            type: 'POST',
            dataType: 'json',
            data: ingresoNoIdentificado,
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                location.href = contextPath + "/administrador/pagos/asignarIngresoNoIdentificados/" + self.idContacto();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al actualizar el ingreso");
            }
        });

    }
}

var IngresosNoIdentificadosListViewApp = function(data) {

    var self = this;

    self.ingresosNoIdentificados = ko.observableArray();

    if (data && data.ingresosNoIdentificados) {
        ko.utils.arrayForEach(data.ingresosNoIdentificados, function (g) {
            self.ingresosNoIdentificados.push(g);
        });
    }

    self.ver = function(data) {
        location.href = contextPath + "/administrador/ingresosnoidentificados/editar/" + data.id;
    }
}

var Item = function(data) {

    var self = this;

    self.id = ko.observable( data ? data.id : undefined );
    self.label = ko.observable( data ? data.label : undefined );
    self.url = ko.observable( data ? data.url : undefined );

    self.limpiar = function() {
//        self.id( undefined );
        //self.label( undefined );
        self.url( undefined );
    }
}