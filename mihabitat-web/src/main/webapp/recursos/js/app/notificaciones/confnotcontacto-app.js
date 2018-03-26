var ConfiguracionNotificacionContactoApp = function(data) {

    var self = this;

    self.configuracionNotificacion = new ConfiguracionNotificacionContacto();

    self.tiposNotificacion = ko.observableArray([]);
    if (data && data.tiposNotificacion) {
        ko.utils.arrayForEach(data.tiposNotificacion, function (c) {
            var tipo = new Catalogo();
            tipo.cargar(c);
            self.tiposNotificacion.push(tipo);
        });
    }

    self.listadoConfNots = ko.observableArray();
    if(data.listadoConfNots) {
        ko.utils.arrayForEach(data.listadoConfNots, function (c) {
            var cnf = new ConfiguracionNotificacionContacto();
            cnf.cargar(c);
            self.listadoConfNots.push(cnf);
        });
    }

    self.notificacionSeleccionada = new ko.observable();

    self.guardar = function () {
        var configuracionNotificacion = self.configuracionNotificacion.getJson();

        configuracionNotificacion = JSON.stringify(configuracionNotificacion);
        console.log(configuracionNotificacion);
        $.ajax({
            url: contextPath + "/administrador/confnotificaciones/guardar",
            type: 'POST',
            dataType: 'json',
            data: configuracionNotificacion,
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                /*self.listadoConfNots([]);
                ko.utils.arrayForEach(data, function (c) {
                    var cnf = new ConfiguracionNotificacionContacto();
                    cnf.cargar(c);
                    self.listadoConfNots.push(cnf);
                });*/
                notificacionExito("Se ha guardado la configuracion.");
                setTimeout(function () {
                    location.href = contextPath + "/administrador/confnotificaciones/lista";
                }, 1000);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al guardar el abono");
            }
        });
    }

    self.tiposNotificacionParaGuardar = ko.observableArray([]);
    self.guardarMuchos = function () {
        //var tiposNotificacionParaGuardar = self.tiposNotificacionParaGuardar.getJson();

        var tiposNotificacionParaGuardar = JSON.stringify(self.tiposNotificacionParaGuardar());
        console.log(tiposNotificacionParaGuardar);
        $.ajax({
            url: contextPath + "/administrador/confnotificaciones/guardar/multiple",
            type: 'POST',
            dataType: 'json',
            data: tiposNotificacionParaGuardar,
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
               /* self.listadoConfNots([]);
                $('#table-listadoConfNots').dataTable();
                ko.utils.arrayForEach(data, function (c) {
                    var cnf = new ConfiguracionNotificacionContacto();
                    cnf.cargar(c);
                    self.listadoConfNots.push(cnf);
                });*/
                notificacionExito("Se ha guardado la configuracion.");
                setTimeout(function () {
                    location.href = contextPath + "/administrador/confnotificaciones/lista";
                }, 1000);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al guardar el abono");
            }
        });
    }

    self.cambioUno = function (cnf) {
        if(cnf.contactoDepartamento.id.contacto.id && cnf.contactoDepartamento.id.departamento.id) {
            self.configuracionNotificacion.cargar(cnf.getJson());
            self.guardar();
        }
    }

    self.cambioMuchosApp = function (not) {
        bootbox.confirm("&#191;Est&aacute; seguro que desea configurar todos los cond&oacute;minos filtrados para que reciban estas notificaciones &uacute;nicamente en la Aplicaci&oacute;n&#63;", function (result) {
            if (result) {
                var rows = $("#table-listadoConfNots").dataTable()._('tr', {
                    "filter" : "applied"
                });
                self.tiposNotificacionParaGuardar([]);
                for (var i = 0; i < rows.length; i++) {
                    console.log(i + ' ' + rows[i][0]);
                    ko.utils.arrayForEach(self.listadoConfNots(), function(l) {
                        if ((l.contactoDepartamento.id.contacto.id() + '|' + l.contactoDepartamento.id.departamento.id()) == rows[i][0]) {
                            var confNotificacion = l.getJson();

                            if(not == 1){
                                confNotificacion.tipoNotificacionNuevoCargo.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 2){
                                confNotificacion.tipoNotificacionNuevoRecargo.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else  if(not == 3){
                                confNotificacion.tipoNotificacionAbonoValidado.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 4){
                                confNotificacion.tipoNotificacionAbonoCancelado.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 5){
                                confNotificacion.tipoNotificacionAprovecharDescuento.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 6){
                                confNotificacion.tipoNotificacionEvitarRecargo.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 7){
                                confNotificacion.tipoNotificacionAvisoCobranza.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 8){
                                confNotificacion.tipoNotificacionNuevoTema.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 9){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaPropio.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 10){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaComentado.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 11){
                                confNotificacion.tipoNotificacionNuevoComentario.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 12){
                                confNotificacion.tipoNotificacionNuevoAviso.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 13){
                                confNotificacion.tipoNotificacionReservacionValidada.id = AppConfig.catalogos.tiponotificaciones.app;
                            } else if(not == 14){
                                confNotificacion.tipoNotificacionIncidenciaActualizada.id = AppConfig.catalogos.tiponotificaciones.app;
                            }

                            self.tiposNotificacionParaGuardar.push(confNotificacion);
                        }
                    });
                }
                self.guardarMuchos();
            }
        });
    }

    self.cambioMuchosEmail = function (not) {
        bootbox.confirm("&#191;Est&aacute; seguro que desea configurar todos los cond&oacute;minos filtrados para que reciban estas notificaciones v&iacute;a Email&#63;", function (result) {
            if (result) {
                var rows = $("#table-listadoConfNots").dataTable()._('tr', {
                    "filter" : "applied"
                });
                self.tiposNotificacionParaGuardar([]);
                for (var i = 0; i < rows.length; i++) {
                    console.log(i + ' ' + rows[i][0]);
                    ko.utils.arrayForEach(self.listadoConfNots(), function(l) {
                        if ((l.contactoDepartamento.id.contacto.id() + '|' + l.contactoDepartamento.id.departamento.id()) == rows[i][0]) {

                            var confNotificacion = l.getJson();

                            if(not == 1){
                                confNotificacion.tipoNotificacionNuevoCargo.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 2){
                                confNotificacion.tipoNotificacionNuevoRecargo.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else  if(not == 3){
                                confNotificacion.tipoNotificacionAbonoValidado.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 4){
                                confNotificacion.tipoNotificacionAbonoCancelado.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 5){
                                confNotificacion.tipoNotificacionAprovecharDescuento.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 6){
                                confNotificacion.tipoNotificacionEvitarRecargo.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 7){
                                confNotificacion.tipoNotificacionAvisoCobranza.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 8){
                                confNotificacion.tipoNotificacionNuevoTema.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 9){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaPropio.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 10){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaComentado.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 11){
                                confNotificacion.tipoNotificacionNuevoComentario.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 12){
                                confNotificacion.tipoNotificacionNuevoAviso.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 13){
                                confNotificacion.tipoNotificacionReservacionValidada.id = AppConfig.catalogos.tiponotificaciones.email;
                            } else if(not == 14){
                                confNotificacion.tipoNotificacionIncidenciaActualizada.id = AppConfig.catalogos.tiponotificaciones.email;
                            }

                            self.tiposNotificacionParaGuardar.push(confNotificacion);
                        }
                    });
                }
                self.guardarMuchos();
            }
        });
    }

    self.cambioMuchosNinguno = function (not) {
        bootbox.confirm("&#191;Est&aacute; seguro que desea configurar todos los cond&oacute;minos filtrados para que NO reciban estas notificaciones&#63;", function (result) {
            if (result) {
                var rows = $("#table-listadoConfNots").dataTable()._('tr', {
                    "filter" : "applied"
                });
                self.tiposNotificacionParaGuardar([]);
                for (var i = 0; i < rows.length; i++) {
                    console.log(i + ' ' + rows[i][0]);
                    ko.utils.arrayForEach(self.listadoConfNots(), function(l) {
                        if ((l.contactoDepartamento.id.contacto.id() + '|' + l.contactoDepartamento.id.departamento.id()) == rows[i][0]) {

                            var confNotificacion = l.getJson();

                            if(not == 1){
                                confNotificacion.tipoNotificacionNuevoCargo.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 2){
                                confNotificacion.tipoNotificacionNuevoRecargo.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else  if(not == 3){
                                confNotificacion.tipoNotificacionAbonoValidado.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 4){
                                confNotificacion.tipoNotificacionAbonoCancelado.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 5){
                                confNotificacion.tipoNotificacionAprovecharDescuento.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 6){
                                confNotificacion.tipoNotificacionEvitarRecargo.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 7){
                                confNotificacion.tipoNotificacionAvisoCobranza.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 8){
                                confNotificacion.tipoNotificacionNuevoTema.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 9){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaPropio.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 10){
                                confNotificacion.tipoNotificacionNuevoComentarioTemaComentado.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 11){
                                confNotificacion.tipoNotificacionNuevoComentario.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 12){
                                confNotificacion.tipoNotificacionNuevoAviso.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 13){
                                confNotificacion.tipoNotificacionReservacionValidada.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            } else if(not == 14){
                                confNotificacion.tipoNotificacionIncidenciaActualizada.id = AppConfig.catalogos.tiponotificaciones.ninguno;
                            }

                            self.tiposNotificacionParaGuardar.push(confNotificacion);
                        }
                    });
                }
                self.guardarMuchos();
            }
        });
    }

}