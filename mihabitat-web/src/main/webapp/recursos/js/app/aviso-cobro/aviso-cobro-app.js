var AvisoDeCobroViewModel = function( data ) {
    
    var self = this;

    self.fecha = ko.observable(moment(new Date()).format('DD-MM-YYYY'));
    
    self.isAdmin = ko.observable(false);
    if (data && data.roles) {
        self.isAdmin(data.roles.indexOf("Administrador") > -1);
    }
    
    self.item = new Item();
    
    self.item.label.subscribe( function( val ) {
        if( val.length > 0 ) {
            self.item.limpiar();
            //console.log( val );
            
            $( "#busqueda" ).autocomplete({
                minLength : 1,
                source : function( request, response ) {
                    $.ajax({
                        url: contextPath + "/administrador/aviso-cobro/buscar",
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

                    self.obtenerAvisoDeCobro();
                    return false;
                }
            });
        }
    });

    self.avisodecobro = new AvisoDeCobro();
    
    self.obtenerAvisoDeCobro = function() {
        console.log("Aviso de Cobro de " + self.fecha());
        
        $.ajax({
            cache : false,
            url: contextPath + self.item.url(),
            data : {
                fin : self.fecha(),
                idContacto : self.item.id()
            },
            success: function( data ) {
                self.avisodecobro.limpiar();
                self.avisodecobro.cargar( data );
                self.dibujarTabla();
             }
        });
    }
    
    self.dibujarTabla = function() {
        $('#table-cargos').dataTable({
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
    
    self.emailsEnvio = ko.observableArray( [] );
    self.otrosEnvio = ko.observable("");
    self.mensajeEnvio = ko.observable("");
    
    self.abrirModalEnvio = function() {
        self.limpiarEnvio();
        if (self.avisodecobro.departamento.contactos()) {
            ko.utils.arrayForEach( self.avisodecobro.departamento.contactos(), function( contacto ) {
                ko.utils.arrayForEach( contacto.id.contacto.emails, function( email ) {
                    var emailEnvio = {
                            contacto : contacto.id.contacto.nombre + " " + contacto.id.contacto.apellidoPaterno,
                            direccion : email.direccion,
                            seleccionado : ko.observable(false)
                    };
                    self.emailsEnvio.push(emailEnvio);
                });
            });
        }
        $("#envio-modal").modal("show");
    }
    
    self.limpiarEnvio = function() {
        self.emailsEnvio([]);
        self.otrosEnvio("");
        self.mensajeEnvio("");
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
            var url = self.item.url().replace("departamento", "enviar");
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
        } else {
            notificacionAdvertencia("No hay ningún email.");
        }
        $("#envio-modal").modal("hide");
    }
    
    self.imprimir = function() {
        var url = self.item.url().replace("departamento", "imprimir");
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
                    value : "/administrador/aviso-cobro/" + cd.id.departamento.id + "/departamento",
                    label : cd.id.departamento.nombre
            }
            self.departamentos.push(departamento);
        });
        self.item.id(data.contacto.id);
        self.item.url(self.departamentos()[0].value);
        self.obtenerAvisoDeCobro();
    }

    if (data && data.idDepartamento && data.idContacto && data.idDepartamento > 0 && data.idContacto > 0) {
        self.item.url(contextPath + "/administrador/aviso-cobro/" + data.idDepartamento + "/departamento");
        self.item.id(data.idContacto);
        self.obtenerAvisoDeCobro();
    }
}


var AvisoDeCobroListaViewModel = function( data ) {
    
    var self = this;

    self.avisos = ko.observableArray([]);
    self.fecha = ko.observable(moment(new Date()).format('DD-MM-YYYY'));

    self.obtenerAvisoDeCobro = function() {
        console.log("Aviso de Cobro de " + self.fecha())
        
        self.removerTabla();
        self.avisos([]);
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/aviso-cobro/masivo/lista",
            data : {
                fin : self.fecha()
            },
            success: function( data ) {
                ko.utils.arrayForEach( data, function( aviso ) {
                    var avisoCobro = {
                            id : aviso.departamento.id,
                            departamento : aviso.departamento.nombre,
                            contacto : aviso.contacto ? aviso.contacto.nombre + ' ' + aviso.contacto.apellidoPaterno : '',
                            emails : aviso.contacto ? aviso.contacto.emails : [],
                            grupos : aviso.departamento.grupos,
                            total : aviso.saldoDeudor,
                            saldoFavor : aviso.saldoFavor,
                            seleccionado : ko.observable(false),
                            seleccionable : aviso.contacto && aviso.contacto.emails && aviso.contacto.emails.length > 0 ? true : true
                    }
                    self.avisos.push(avisoCobro);
                });
                self.dibujarTabla();
             }, error: function (xhr, ajaxOptions, thrownError) {
                 var excepcion = $.parseJSON(xhr.responseText);
                 notificacionError(excepcion.error);
             }
        });
    }
    
    self.removerTabla = function() {
        var id = "#table-avisos-cuenta";
        if ( $.fn.dataTable.isDataTable( id ) ) {
            var otable = $(id).DataTable();
            otable.destroy();
            $(id + " tbody").empty();
        }
    }
    
    self.dibujarTabla = function() {
        var id = "#table-avisos-cuenta";
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
        }, {
            column_number: 4,
            filter_type: "range_number_slider",
            ignore_char: "\\$|,"
        }]);
        
        $("#table-avisos-cuenta_filter").hide();
    }
    
    self.seleccionarTodo = ko.observable(false);
    self.seleccionarTodo.subscribe(function( val ) {
        var rows = $("#table-avisos-cuenta").dataTable()._('tr', {"filter":"applied"});
        
        for (var i = 0; i < rows.length; i++) {
            ko.utils.arrayForEach( self.avisos(), function( aviso ) {
                if (aviso.departamento == rows[i][0] && aviso.seleccionable) {
                    console.log(rows[i][0]);
                    aviso.seleccionado( val );
                }
            });
        }
    });
    
    self.envioMasivo = function() {
        var ids = [];
        ko.utils.arrayForEach( self.avisos(), function( aviso ) {
            if (aviso.seleccionado()) {
                ids.push(aviso.id);
            }
        });
        if (ids.length > 0) {
            $.ajax({
                cache : false,
                url : contextPath + "/administrador/aviso-cobro/masivo/enviar",
                dataType : 'json',
                data : {
                    fin: self.fecha(),
                    ids : ids
                },
                success : function(data) {
                    notificacionExito("Se enviaron los avisos de cobro correctamente.");
                }
            });
        } else {
            notificacionAdvertencia("No hay ningún departamento seleccionado.");
        }
    }

    self.imprimirMasivo = function(formato) {
        var ids = [];
        ko.utils.arrayForEach( self.avisos(), function( aviso ) {
            if (aviso.seleccionado()) {
                ids.push(aviso.id);
            }
        });
        if (ids.length > 0) {
            var url = "/administrador/aviso-cobro/masivo/imprimir?formato=" + formato + "&fin=" + self.fecha() + "&ids[]=" + ids[0];
            for(i = 1 ; i < ids.length ; i++){
                url = url + "," + ids[i];
            }
            window.open(contextPath + url, '_blank');
        } else {
            notificacionAdvertencia("No hay ningún departamento seleccionado.");
        }
    }
    
    self.obtenerAvisoDeCobro();
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
