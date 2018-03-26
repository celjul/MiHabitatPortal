var CobranzaViewModel = function() {
    
    var self = this;
    
    self.cobranzas = ko.observableArray([]);
    
    $.ajax({
        cache : false,
        url: contextPath + "/administrador/cobranza/lista",
        success: function( data ) {
            ko.utils.arrayForEach(data, function(c) {
                var cobranza = new Cobranza();
                cobranza.cargar(c);
                self.cobranzas.push(cobranza);
            });
            self.dibujarTablaCobranza();
         }, error: function (xhr, ajaxOptions, thrownError) {
             var excepcion = $.parseJSON(xhr.responseText);
             notificacionError(excepcion.error);
         }
    });
    
    self.dibujarTablaCobranza = function() {
        var otable = $("#table-cobranza").DataTable({
            "aaSorting" : [],
            "aoColumns": [
                          null, null, null, { "iDataSort": 4 }, null, { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "150px",
            "scrollCollapse": true
        });

        yadcf.init(otable, [{
            column_number: 0,
            filter_type: "text",
            filter_default_label : "Departamento"
        }, {
            column_number: 1,
            filter_type : 'multi_select',
            filter_default_label : "Grupo",
            select_type: 'select2',
            column_data_type: "html",
            html_data_type: "text",
            filter_reset_button_text: false
        }, {
            column_number: 2,
            filter_type: "range_number_slider",
            ignore_char: "\\$|,"
        }]);

        $("#table-cobranza_filter").hide();
    }
    
    self.cobranza = new Cobranza();
    
    self.seleccionar = function(cobranza) {
        self.cobranza.limpiar();
        cobranza.seleccionada(true);
        ko.utils.arrayForEach( self.cobranzas(), function(c) {
            if (c != cobranza) {
                c.seleccionada(false);
            }
        });
        
        self.cobranza.seleccionada(true);
        self.cobranza.departamento.id(cobranza.departamento.id());
        self.cobranza.departamento.nombre(cobranza.departamento.nombre());
        ko.utils.arrayForEach(cobranza.departamento.contactos(), function(c) {
            self.cobranza.departamento.contactos.push(c);
        });
        self.dibujarTablaContactos();
        
        ko.utils.arrayForEach(cobranza.cargos(), function(c) {
            self.cobranza.cargos.push(c);
        });
        self.dibujarTablaCargos();
        
        self.cargarNotas();
    }
    
    self.dibujarTablaContactos = function() {
        var otable = $("#table-contactos").DataTable({
            "aaSorting" : [],
            "aoColumns": [
                          null, null, null
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "150px",
            "scrollCollapse": true
        });
        $("#table-contactos_filter").hide();
    }
    
    self.dibujarTablaCargos = function() {
        var otable = $("#table-cargos").DataTable({
            "aaSorting" : [],
            "aoColumns": [
                          null, null, null, null, null, null, null
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "150px",
            "scrollCollapse": true
        });
        $("#table-cargos_filter").hide();
    }
    
    self.cargarNotas = function() {
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/cobranza/notas",
            data : {
                idDepartamento : self.cobranza.departamento.id()
            },
            success: function( data ) {
                ko.utils.arrayForEach(data, function(n) {
                    var nota = new Nota();
                    nota.cargar(n);
                    self.cobranza.notas.push(nota);
                });
                self.dibujarTablaNotas();
             }, error: function (xhr, ajaxOptions, thrownError) {
                 var excepcion = $.parseJSON(xhr.responseText);
                 notificacionError(excepcion.error);
             }
        });
    }
    
    self.dibujarTablaNotas = function() {
        var otable = $("#table-notas").DataTable({
            "aaSorting" : [],
            "aoColumns": [
                          null, null, { "bSearchable": false, "bSortable": false }, { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "150px",
            "scrollCollapse": true
        });
        $("#table-notas_filter").hide();
    }
    
    self.removerTablaNotas = function() {
        if ( $.fn.dataTable.isDataTable( "#table-notas" ) ) {
            var otable = $("#table-notas").DataTable();
            otable.destroy();
            $("#table-notas tbody").empty();
        }
    }
    
    self.nota = new Nota();
    
    self.nuevaNota = function() {
        self.nota.limpiar();
        self.nota.fecha(Date.convertirFecha(new Date()));
        $("#nota-modal").modal("show");
    }
    
    self.guardarNota = function() {
        if ($("#nota-form").valid()) {
            self.removerTablaNotas();
            var nota = JSON.stringify(self.nota.getJson(self.cobranza.departamento));
            console.log(nota);
            $.ajax({
                url : contextPath + "/administrador/cobranza/nota/guardar",
                type : 'POST',
                dataType : 'json',
                data : nota,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    var anteriores = self.cobranza.notas();
                    self.cobranza.notas([]);
                    ko.utils.arrayForEach(anteriores, function(n) {
                        self.cobranza.notas.push(n);
                    });
                    
                    self.nota.limpiar();
                    var nota = new Nota();
                    nota.cargar(data);
                    self.cobranza.notas.push(nota);
                    
                    self.dibujarTablaNotas();
                    self.limpiarFormNotas();
                    $("#nota-modal").modal("hide");
                    notificacionExito("La nota se ha guardado correctamente.");
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar la nota.");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores.");
        }
    }
    
    self.seleccionarNota = function(nota) {
        self.nota.limpiar();
        self.nota.cargar(nota.getJson(self.cobranza.departamento));
        $("#nota-modal").modal("show");
    }
    
    self.actualizarNota = function() {
        if ($("#nota-form").valid()) {
            self.removerTablaNotas();
            var nota = JSON.stringify(self.nota.getJson(self.cobranza.departamento));
            console.log(nota);
            $.ajax({
                url : contextPath + "/administrador/cobranza/nota/actualizar",
                type : 'POST',
                dataType : 'json',
                data : nota,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    var anteriores = self.cobranza.notas();
                    self.cobranza.notas([]);
                    ko.utils.arrayForEach(anteriores, function(n) {
                        if (n.id () != self.nota.id()) {
                            self.cobranza.notas.push(n);
                        }
                    });
                    
                    self.nota.limpiar();
                    var nota = new Nota();
                    nota.cargar(data);
                    self.cobranza.notas.push(nota);
                    
                    self.dibujarTablaNotas();
                    self.limpiarFormNotas();
                    $("#nota-modal").modal("hide");
                    notificacionExito("La nota se ha actualizado correctamente");
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al actualizar la nota");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores.");
        }
    }
    
    self.eliminarNota = function() {
        bootbox.confirm("¿Deseas eliminar la nota?", function(result) {
            if (result) {
                self.removerTablaNotas();
                var nota = JSON.stringify(self.nota.getJson(self.cobranza.departamento));
                console.log(nota);
                $.ajax({
                    url : contextPath + "/administrador/cobranza/nota/eliminar",
                    type : 'POST',
                    dataType : 'json',
                    data : nota,
                    contentType : 'application/json',
                    mimeType : 'application/json',
                    success : function(data) {
                        var anteriores = self.cobranza.notas();
                        self.cobranza.notas([]);
                        ko.utils.arrayForEach(anteriores, function(n) {
                            if (n.id () != self.nota.id()) {
                                self.cobranza.notas.push(n);
                            }
                        });
                        
                        self.nota.limpiar();
                        self.dibujarTablaNotas();
                        self.limpiarFormNotas();
                        $("#nota-modal").modal("hide");
                        notificacionExito("La nota se ha eliminado correctamente");
                    },
                    error : function(jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al eliminar la nota");
                    }
                });
            }
        });
    }
    
    self.limpiarFormNotas = function() {
        $("#nota-form label").each(function() {
            $(this).removeClass("state-success");
            $(this).removeClass("state-error");
        });
        $("#nota-form input[type=text]").each(function() {
            $(this).removeClass("invalid");
            $(this).removeClass("valid");
        });
        $("#nota-form input[type=checkbox]").each(function() {
            $(this).removeClass("invalid");
            $(this).removeClass("valid");
        });
        $("#nota-form textarea").each(function() {
            $(this).removeClass("invalid");
            $(this).removeClass("valid");
        });
        var validator = $("#nota-form").validate();
        validator.resetForm();
    }
    
    self.estadoCuenta = function() {
        location.href = contextPath + "/administrador/estado-cuenta/individual";
    }
    
    self.nuevoPago = function() {
        location.href = contextPath + "/administrador/pagos/nuevo";
    }
    
    self.emailsEnvio = ko.observableArray();
    self.otrosEnvio = ko.observable();
    self.mensajeEnvio = ko.observable();
    
    self.emailIndividual = function() {
        self.limpiarEnvio();
        ko.utils.arrayForEach(self.cobranza.departamento.contactos(), function(contacto) {
            ko.utils.arrayForEach(contacto.emails(), function(email) {
                var emailEnvio = {
                        departamento : self.cobranza.departamento.nombre(),
                        contacto : contacto.nombre(),
                        direccion : email.direccion,
                        seleccionado : ko.observable(false)
                };
                self.emailsEnvio.push(emailEnvio);
            });
        });
        $("#envio-modal").modal("show");
        self.dibujarTablaEmails();
    }
    
    self.emailMasivo = function() {
        self.limpiarEnvio();
        ko.utils.arrayForEach(self.cobranzas(), function(cobranza) {
            ko.utils.arrayForEach(cobranza.departamento.contactos(), function(contacto) {
                ko.utils.arrayForEach(contacto.emails(), function(email) {
                    var emailEnvio = {
                            departamento : cobranza.departamento.nombre(),
                            contacto : contacto.nombre(),
                            direccion : email.direccion,
                            seleccionado : ko.observable(false)
                    };
                    self.emailsEnvio.push(emailEnvio);
                });
            });
        });
        $("#envio-modal").modal("show");
        self.dibujarTablaEmails();
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
            $("#envio-modal").modal("hide");
            console.log(emails);
            $.ajax({
                cache : false,
                url : contextPath + "/administrador/cobranza/envio",
                dataType : 'json',
                data : {
                    emails : emails,
                    mensaje : self.mensajeEnvio()
                },
                success : function(data) {
                    notificacionExito("Se envio el mensaje correctamente");
                }
            });
        } else {
            notificacionAdvertencia("No hay ningún email.");
        }
    }
    
    self.limpiarEnvio = function() {
        self.emailsEnvio([]);
        self.otrosEnvio(undefined);
        self.mensajeEnvio(undefined);
        self.removerTablaEmails();
    }
    
    self.removerTablaEmails = function() {
        if ( $.fn.dataTable.isDataTable( "#table-emails" ) ) {
            var otable = $("#table-emails").DataTable();
            otable.destroy();
            $("#table-emails tbody").empty();
        }
    }
    
    self.dibujarTablaEmails = function() {
        var otable = $("#table-emails").DataTable({
            "aaSorting" : [],
            "aoColumns": [
                          null, null, null, { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : false,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "250px",
            "scrollCollapse": true
        });
        $("#table-emails_filter").hide();

        setTimeout(function() { 
            $("#th-envio").click();
        }, 250);
    }
}