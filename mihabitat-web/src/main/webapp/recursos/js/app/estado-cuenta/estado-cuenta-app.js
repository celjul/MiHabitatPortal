var EstadoCuentaViewModel = function( data ) {
    
    var self = this;

    var fechaAux = new Date();

    self.fecha = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));



    /*fecha.setMonth(fecha.getFullYear(), fecha.getMonth() + 1, 0);
    self.fin = ko.observable(moment(hoy).format('DD-MM-YYYY'));*/

    self.tabElegida = ko.observable( 1 );
    
    self.isAdmin = ko.observable(false);
    if (data && data.roles) {
        self.isAdmin(data.roles.indexOf("Administrador") > -1);
    }
    
    /*self.periodos = ko.observableArray([]);
    if (data && data.periodos && data.periodos.length > 0) {
        ko.utils.arrayForEach( data.periodos, function( periodo ) {
            self.periodos.push(periodo);
        });
    }*/
    
    self.item = new Item();
    
    self.item.label.subscribe( function( val ) {
        if( val.length > 0 ) {

            
            $( "#busqueda" ).autocomplete({
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
                        },
                        global: false
                    });
                },
                select : function( event, ui ) {
                    self.item.limpiar();
                    self.item.id( ui.item.id );
                    self.item.label( ui.item.label );
                    self.item.url( ui.item.url );
                    self.recargarDepto();
                    $("#fechaInicio").datepicker();
                    $("#fechaFin").datepicker();
                    return false;
                }
            });
        }
    });

    self.recargarDepto = function() {
        self.obtenerEstadoDeCuenta();
        $('a[href="#hr1"]').click();
    }
    
    
    /*self.periodo = ko.observable(self.periodos()[12]);*/

    self.estadocuenta = new EstadoCuenta();
    
    self.obtenerEstadoDeCuenta = function() {
        console.log("Estado de Cuenta de " + "01-" + self.inicio() + " - " + self.fin());
        
        $.ajax({
            cache : false,
            url: contextPath + self.item.url(),
            data : {
                inicio : self.inicio(),
                fin : self.fin(),
                idContacto : self.item.id()
            },
            success: function( data ) {
                self.estadocuenta.limpiar();
                if (data != undefined && data.movimientos != undefined && data.movimientos.length > 0) {
                    ko.utils.arrayForEach(data.movimientos, function (mov) {
                        if (mov.tipo.id == AppConfig.catalogos.movimientos.tipos.saldoAFavorGenerado ) {
                            mov.tipo.descripcion = "Saldo a Favor Generado";
                            var str = "<strong>Del Recibo " + mov.pagoDepartamento.pagoCondomino.folio + ": </strong>" ;
                            ko.utils.arrayForEach(mov.pagoDepartamento.pagoCondomino.estatus, function (est) {
                                str = str + (est.comentario ? est.comentario+", " : "");
                            });
                            /*if(str){
                                str =  + str;
                            }*/
                            mov.movimientoCargo = {cargo : {concepto : str}};
                        } else if (mov.tipo.id == AppConfig.catalogos.movimientos.tipos.aplicacionDeSaldoAFavor) {
                            mov.tipo.descripcion = "Aplicación de Saldo a Favor";
                            var str = "<strong>Del Recibo " + mov.pagoDepartamento.pagoCondomino.folio + ": </strong>" ;
                            ko.utils.arrayForEach(mov.pagoDepartamento.pagoCondomino.estatus, function (est) {
                                str = str + (est.comentario ? est.comentario+", " : "");
                            });
                            /*if(str){
                                str = "<strong>Del Abono: </strong>" + mov.pagoDepartamento.pagoCondomino.folio + "; " + str;
                            }*/
                            mov.movimientoCargo = {cargo : {concepto : str}};
                        } else if(mov.tipo.id == AppConfig.catalogos.movimientos.tipos.pagocargo) {
                            if(mov.haber) {
                                mov.tipo = {descripcion : "Abono"};
                            }
                            if(mov.debe) {
                                mov.tipo = {descripcion : "Cancelación de Abono"};
                            }
                            //mov.movimientoCargo = {cargo : {concepto : "Su Pago Gracias."}}
                            var str = "<strong>Del Recibo " + mov.pagoDepartamento.pagoCondomino.folio + ": </strong>" ;
                            ko.utils.arrayForEach(mov.pagoDepartamento.pagoCondomino.estatus, function (est) {
                                str = str + (est.comentario ? est.comentario+", " : "");
                            });
                            /*if(str){
                                str = "<strong>Del Abono: </strong>" + mov.pagoDepartamento.pagoCondomino.folio + "; " + str;
                            }*/
                            mov.movimientoCargo = {cargo : {concepto : str}};
                        } else if(mov.tipo.id == AppConfig.catalogos.movimientos.tipos.cargo) {
                            mov.cargo.concepto = "<strong>Aplicado a: </strong>" + mov.cargo.concepto;
                        } else {
                            mov.cargo.concepto = "<strong>Aplicado a: </strong>" + mov.cargo.concepto;
                            //mov.movimientoCargo = {cargo : {concepto : "<strong>Aplicado a: </strong>" + mov.movimientoCargo.cargo.concepto}};
                        }
                    });
                }

                self.estadocuenta.cargar( data );
                /*var current_index = $("#tab-content").tabs("option","active");
                $("#tab-content").tabs('load',current_index);*/
                self.dibujarTablaEstadoCuenta();
                self.tabElegida(1);
             }
        });
    }

    self.obtenerAvisoDeCobro = function() {
        console.log("Aviso de Cobro de " + self.fecha());
        $("#fecha").datepicker();

        $.ajax({
            cache : false,
            url: contextPath + self.item.url().replace("estado-cuenta/", "estado-cuenta/avisocobro/"),
            data : {
                fin : self.fecha(),
                idContacto : self.item.id()
            },
            success: function( data ) {
                self.estadocuenta.avisodecobro.limpiar();
                self.estadocuenta.avisodecobro.cargar( data );
                self.dibujarTablaAvisoCobro();
                self.tabElegida(2);
            }
        });
    }

    self.obtenerCargos = function() {
        console.log("Cargos...");
        $("#fecha").datepicker({
            maxDate: '0'
        });
        $.ajax({
            cache : false,
            url: contextPath + self.item.url().replace("estado-cuenta/", "estado-cuenta/cargos/"),
            data : {
            },
            success: function( data ) {
                self.estadocuenta.cargos([]);
                if (data != undefined && data.length > 0) {
                    ko.utils.arrayForEach(data, function(cargo) {
                        var c = new CargoDepartamento();
                        c.cargarCargoDepartamento(cargo);
                        c.departamento = new Departamento();
                        c.departamento.cargar(cargo.departamento);
                        c.agrupador = {
                            id: cargo.agrupador?cargo.agrupador.id:undefined
                        }

                        self.estadocuenta.cargos.push(c);
                    });
                }
                self.dibujarTablaCargos();
                self.tabElegida(3);
            }
        });
    }

    self.obtenerAbonos = function() {
        console.log("Consulta..");
            $.ajax({
                cache : false,
                url: contextPath + self.item.url().replace("estado-cuenta/", "estado-cuenta/abonos/"),
                data : {
                },
                success: function(data) {
                    /*self.estadocuenta.pagos([]);
                    if($.fn.DataTable.isDataTable("#table-pagos")) {
                        $("#table-pagos").DataTable().clear().destroy();
                    }*/
                    self.estadocuenta.pagos(data);

                    /*if (data != undefined && data.length > 0) {
                        //$("#table-pagos").DataTable().destroy();
                        ko.utils.arrayForEach(data, function(p) {
                            var esteDpto = 0;
                            var otroDpto = 0;
                            var depts = [];
                            ko.utils.arrayForEach(p.movimientos, function(m) {

                            });
                            p.esteDpto = esteDpto;
                            p.otroDpto = otroDpto;
                            p.departamentos = depts;
                            self.estadocuenta.pagos.push(p);

                        });
                    }*/
                    self.dibujarTablaAbonos();
                    self.tabElegida(4);
                }
            });

    }
    
    self.dibujarTablaEstadoCuenta = function() {
        $('#table-movimientos').dataTable({
            "aaSorting" : [[0,'desc']],
            "info":           false,
            "paging":         false,
            "searching": false
        });
        /*$('#table-resumen').dataTable({
            "info":           false,
            "paging":         false,
            "searching": false
        });*/
        $('#table-resumen').footable();
    }

    self.dibujarTablaAvisoCobro = function() {
        /*$('#table-aviso').dataTable({
            "aaSorting" : [[0,'desc']],
            "info":           false,
            "paging":         false,
            "searching": false
        });
        $('#table-resumen').footable();*/
        if($.fn.DataTable.isDataTable("#table-aviso")) {
            $("#table-aviso").DataTable().clear().destroy();
        }
        //Se agrega el código para que dinamicamente se agregue el filtro de fechas
        dibujarTabla("#table-aviso",{
            "aaSorting" : [[0,'desc']],
            "aoColumns": [
                { "iDataSort": 0 }, null, null, null, null, null, null, null
            ],
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
            "t"+
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>"
        });
    }

    self.dibujarTablaCargos = function() {
        if($.fn.DataTable.isDataTable("#table-cargos")) {
            $("#table-cargos").DataTable().clear().destroy();
        }
        //Se agrega el código para que dinamicamente se agregue el filtro de fechas
        dibujarTabla("#table-cargos",{
            "aaSorting" : [[0,'desc']],
            "aoColumns": [
                { "iDataSort": 0 }, null, null, null, null, null, null, null
            ],
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
            "t"+
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>"
        });
    }

    self.dibujarTablaAbonos = function() {
        if($.fn.DataTable.isDataTable("#table-abonos")) {
            $("#table-abonos").DataTable().clear().destroy();
        }
        //Se agrega el código para que dinamicamente se agregue el filtro de fechas
        dibujarTabla("#table-abonos",{
            "aaSorting" : [[0,'desc']],
            "aoColumns": [
                { "iDataSort": 0 }, null, null, null, null, null, null, null
            ],
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
            "t"+
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>"
        });
    }
    
    self.emailsEnvio = ko.observableArray( [] );
    self.otrosEnvio = ko.observable("");
    self.mensajeEnvio = ko.observable("");
    
    self.abrirModalEnvio = function() {
        self.limpiarEnvio();
        if (self.estadocuenta.departamento.contactos()) {
            ko.utils.arrayForEach( self.estadocuenta.departamento.contactos(), function( contacto ) {
                ko.utils.arrayForEach( contacto.id.contacto.emails, function( email ) {
                    var emailEnvio = {
                            contacto : contacto.id.contacto.nombre + " " + contacto.id.contacto.apellidoPaterno +
                                (contacto.id.contacto.apellidoMaterno?" " + contacto.id.contacto.apellidoMaterno:""),
                            direccion : email.direccion,
                            seleccionado : ko.observable(false)
                    };
                    self.emailsEnvio.push(emailEnvio);
                });
            });
        }
        if(self.tabElegida() == 1) {
            $(".edocta").modal("show");
        } else if(self.tabElegida() == 2) {
            $(".aviso").modal("show");
        } else if(self.tabElegida() == 4) {
            self.idPago(data.id);
            $(".pagos").modal("show");
        }
    }
    
    self.limpiarEnvio = function() {
        self.emailsEnvio([]);
        self.otrosEnvio("");
        self.mensajeEnvio("");
    }

    self.actualizarCargo = function(data) {
        location.href = contextPath + "/administrador/cargos-departamentos/actualizar/" + data.id();
    };

    self.actualizarCargosSimilares = function(data) {
        location.href = contextPath + "/administrador/cargos-departamentos/agrupador/actualizar/" + data.agrupador.id;
    }

    self.verPago = function(data) {
        location.href = contextPath + "/administrador/pagos/actualizar/" + data.pagoCondomino.id;
    }

    self.descargarPago = function(data) {
        window.open(contextPath + "/administrador/pagos/comprobante/" + data.pagoCondomino.id, '_blank');
    }

    self.imprimirPago = function(data) {
        window.open(contextPath + "/administrador/pagos/recibo/" + data.pagoCondomino.id, '_blank');
    }
    
    self.enviar = function() {
        var emails = [];
        ko.utils.arrayForEach(self.emailsEnvio(), function(email) {
            if (email.seleccionado()) {
                emails.push(email.direccion);
            }
        });
        if (self.otrosEnvio()) {
            var otros = self.otrosEnvio().split(";");
            if (otros) {
                ko.utils.arrayForEach(otros, function(email) {
                    emails.push(email);
                });
            }
        }

        if (emails.length > 0) {
            console.log(emails);
            if(self.tabElegida()==1){
                var url = self.item.url().replace("departamento", "enviar");
                $.ajax({
                    cache : false,
                    url : contextPath + url,
                    dataType : 'json',
                    data : {
                        inicio: self.inicio(),
                        fin: self.fin(),
                        idContacto : self.item.id(),
                        emails : emails,
                        mensaje : self.mensajeEnvio()
                    },
                    success : function(data) {
                        notificacionExito("Se enviaron los estados de cuenta correctamente");
                    }
                });
            } else if(self.tabElegida() == 2) {
                var url = contextPath + self.item.url().replace("estado-cuenta/", "estado-cuenta/avisocobro/").
                        replace("departamento", "enviar");
                $.ajax({
                    cache : false,
                    url : contextPath + url,
                    dataType : 'json',
                    data : {
                        fin: self.fecha(),
                        idContacto : self.item.id(),
                        emails : emails,
                        mensaje : self.mensajeEnvio()
                    },
                    success : function(data) {
                        notificacionExito("Se enviaron los estados de cuenta correctamente");
                    }
                });
            } else if(self.tabElegida() == 4) {
                var url = contextPath + '/administrador/pagos/enviar';
                $.ajax({
                    cache : false,
                    url : contextPath + url,
                    dataType : 'json',
                    data : {
                        emails : emails,
                        mensaje : self.mensajeEnvio(),
                        idPago : self.idPago()
                    },
                    success : function(data) {
                        notificacionExito("Se ha enviado el recibo correctamente");
                    }
                });
            }

        } else {
            notificacionAdvertencia("No hay ningún email.");
        }
        $(".modal").modal("hide");
    }
    
    self.imprimirEstadoCuenta = function() {
        var url = self.item.url().replace("departamento", "imprimir");
        url = url + "?inicio=" + self.inicio() + "&fin=" + self.fin() + "&idContacto=" + self.item.id();
        window.open(contextPath + url, '_blank');
    }

    self.imprimirAvisoCobro = function() {
        var url = contextPath + self.item.url().replace("estado-cuenta/", "estado-cuenta/avisocobro/").
                replace("departamento", "imprimir");
        /*var url = self.item.url().replace("departamento", "imprimir");*/
        url = url + "?fin=" + self.fecha() + "&idContacto=" + self.item.id();
        window.open(contextPath + url, '_blank');
    }
    
    self.registrarPago = function() {
        if (self.isAdmin()) {
            location.href = contextPath + "/administrador/pagos/nuevo";
        } else {
            location.href = contextPath + "/contacto/mis-pagos/nuevo";
        }
    }
    
    self.consultarPagos = function() {
        if (self.isAdmin()) {
            location.href = contextPath + "/administrador/pagos/lista";
        } else {
            location.href = contextPath + "/contacto/mis-pagos/lista";
        }
    }
    
    self.departamentos = ko.observableArray([]);
    if (data && data.contacto) {
        ko.utils.arrayForEach( data.contacto.departamentos, function( cd ) {
            var departamento = {
                    value : "/administrador/estado-cuenta/" + cd.id.departamento.id + "/departamento",
                    label : cd.id.departamento.nombre
            }
            self.departamentos.push(departamento);
        });
        self.item.id(data.contacto.id);
        self.item.url(self.departamentos()[0].value);
        self.obtenerEstadoDeCuenta();
    }

    if (data && data.idDepartamento && data.idContacto && data.idDepartamento > 0 && data.idContacto > 0) {
        self.item.url(contextPath + "/administrador/estado-cuenta/" + data.idDepartamento + "/departamento");
        self.item.id(data.idContacto);
        self.obtenerEstadoDeCuenta();
    }
}

var EstadoCuentaListaViewModel = function( data ) {
    
    var self = this;
    
    /*self.periodos = ko.observableArray([]);
    if (data && data.periodos && data.periodos.length > 0) {
        ko.utils.arrayForEach( data.periodos, function( periodo ) {
            self.periodos.push(periodo);
        });
    }*/

    var fechaAux = new Date();

    self.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.estados = ko.observableArray([]);
    
    self.obtenerEstadosDeCuenta = function() {
        console.log("Estados de Cuenta de " + self.inicio() + " - " + self.fin());
        
        self.removerTabla();
        self.estados([]);
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/estado-cuenta/masivo/lista",
            data : {
                inicio : self.inicio(),
                fin : self.fin()
            },
            success: function( data ) {
                ko.utils.arrayForEach( data, function( estado ) {
                    var estadoCuenta = {
                            id : estado.departamento.id,
                            departamento : estado.departamento.nombre,
                            contacto : estado.contacto ? estado.contacto.nombre + ' ' + estado.contacto.apellidoPaterno : '',
                            emails : estado.contacto ? estado.contacto.emails : [],
                            grupos : estado.departamento.grupos,
                            total : estado.saldoDeudor,
                            saldoFavor : estado.saldoFavor,
                            seleccionado : ko.observable(false),
                            seleccionable : estado.contacto && estado.contacto.emails && estado.contacto.emails.length > 0 ? true : true
                    }
                    self.estados.push(estadoCuenta);
                });
                self.dibujarTabla();
             }, error: function (xhr, ajaxOptions, thrownError) {
                 var excepcion = $.parseJSON(xhr.responseText);
                 notificacionError(excepcion.error);
             }
        });
    }
    
    self.removerTabla = function() {
        var id = "#table-estados-cuenta";
        if ( $.fn.dataTable.isDataTable( id ) ) {
            var otable = $(id).DataTable();
            otable.destroy();
            $(id + " tbody").empty();
        }
    }
    
    self.dibujarTabla = function() {
        var id = "#table-estados-cuenta";
        var otable = $(id).DataTable({
            "aoColumns": [
                          null, null, null, null, null, { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "250px",
            "scrollCollapse": true
        });
        
        yadcf.init(otable, [{
            column_number: 0,
            filter_type: "text",
            filter_default_label : "Departamento"
        }, {
            column_number: 1,
//            column_data_type: "html",
//            html_data_type: "text",
            filter_type : 'multi_select',
            filter_default_label : "Torres y etq",
            select_type: 'select2',
            column_data_type: "html",
            html_data_type: "text",
            filter_reset_button_text: false
        }, {
            column_number: 2,
            filter_type: "text",
            filter_default_label : "Condómino"
        }, {
            column_number: 3,
            filter_type: "text",
            filter_default_label : "Emails"
        },  {
            column_number: 4,
            filter_type: "range_number_slider",
            ignore_char: "\\$|,"
        }]);
        
        $("#table-estados-cuenta_filter").hide();
    }
    
    self.seleccionarTodo = ko.observable(false);
    self.seleccionarTodo.subscribe(function( val ) {
        var rows = $("#table-estados-cuenta").dataTable()._('tr', {"filter":"applied"});
        
        for (var i = 0; i < rows.length; i++) {
            ko.utils.arrayForEach( self.estados(), function( estado ) {
                if (estado.departamento == rows[i][0] && estado.seleccionable) {
                    console.log(rows[i][0]);
                    estado.seleccionado( val );
                }
            });
        }
    });


    
    self.envioMasivo = function() {
        var ids = [];
        ko.utils.arrayForEach( self.estados(), function( estado ) {
            if (estado.seleccionado()) {
                ids.push(estado.id);
            }
        });
        if (ids.length > 0) {
            $.ajax({
                cache : false,
                url : contextPath + "/administrador/estado-cuenta/masivo/enviar",
                dataType : 'json',
                data : {
                    inicio: self.inicio(),
                    fin: self.fin(),
                    ids : ids
                },
                success : function(data) {
                    notificacionExito("Se enviaron los estados de cuenta correctamente.");
                }
            });
        } else {
            notificacionAdvertencia("No hay ningún departamento seleccionado.");
        }
    }

    self.imprimirMasivo = function(formato) {
        var ids = [];
        ko.utils.arrayForEach( self.estados(), function( estado ) {
            if (estado.seleccionado()) {
                ids.push(estado.id);
            }
        });
        if (ids.length > 0) {
            var url = "/administrador/estado-cuenta/masivo/imprimir?formato=" + formato + "&inicio=" + self.inicio() + "&fin=" + self.fin() + "&ids[]=" + ids;
            /*for(i = 1 ; i < ids.length ; i++){
                url = url + "," + ids[i];
            }*/
            window.open(contextPath + url, '_blank');
        } else {
            notificacionAdvertencia("No hay ningún departamento seleccionado.");
        }
    }
    
    self.obtenerEstadosDeCuenta();
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