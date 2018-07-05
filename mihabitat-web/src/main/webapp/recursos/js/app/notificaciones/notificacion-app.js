var FuncionalidadNotificacionApp = function(idList) {

    var self = this;

    var listadoNotificaciones = [];
    var listadoNotificacionesNuevos = [];
    var listadoNotificacionesValidaciones = [];
    var listadoNotificacionesPendientes = [];

    var conteoPagosPendientes = 0;
    var conteoReservacionesPendientes = 0;
    var conteoCargos = 0;
    var conteoRecargos = 0;
    var conteoPagos = 0;
    var conteoReservaciones = 0;
    var conteoIncidenciasSolicitadas = 0;
    var conteoIncidencias = 0;

    var panelMovimientosNuevos = 0;
    var panelValidacionesNuevos = 0;
    var panelPendientesNuevos = 0;


    self.crearPaneles = function(lista) {
        listadoNotificaciones = [];
        listadoNotificacionesNuevos = [];
        listadoNotificacionesValidaciones = [];
        listadoNotificacionesPendientes = []
        var panelPendientes = "#panelPendientesAdmin";
        $(panelPendientes).empty();
        panelPendientesNuevos = 0;
        conteoPagosPendientes = 0;
        conteoReservacionesPendientes = 0;
        conteoIncidenciasSolicitadas = 0;
        var panelValidaciones = "#panelValidacionesContacto";
        $(panelValidaciones).empty();
        panelValidacionesNuevos = 0;
        conteoPagos = 0;
        conteoReservaciones = 0;
        conteoIncidencias = 0;
        var panelMovimientos = "#panelMovimientosContacto";
        $(panelMovimientos).empty();
        panelMovimientosNuevos = 0;
        conteoCargos = 0;
        conteoRecargos = 0;
        listadoNotificaciones = self.ordenarAsc(lista);
        if (listadoNotificaciones && listadoNotificaciones.length > 0) {
            $.each(listadoNotificaciones,function(i,e){
                if(e.subtipo == "nuevo-cargo") {
                    conteoCargos++;
                    if(!e.visto) {
                        panelMovimientosNuevos++;
                    }
                    listadoNotificacionesNuevos.push(e);
                    self.crearItem(e,panelMovimientos);
                }
                else if(e.subtipo == "nuevo-recargo") {
                    conteoRecargos++;
                    if(!e.visto) {
                        panelMovimientosNuevos++;
                    }
                    listadoNotificacionesNuevos.push(e);
                    self.crearItem(e,panelMovimientos);
                }
                else if(e.subtipo == "pago-aprobado" || e.subtipo == "pago-rechazado" || e.subtipo == "pago-cancelado"
                    || e.subtipo == "pago-pendiente") {
                    conteoPagos++;
                    if(!e.visto) {
                        panelValidacionesNuevos++;
                    }
                    listadoNotificacionesValidaciones.push(e);
                    self.crearItem(e,panelValidaciones);
                }
                else if(e.subtipo == "reservacion-aprobada" || e.subtipo == "reservacion-rechazada" || e.subtipo == "reservacion-cancelada"
                    || e.subtipo == "reservacion-pendiente") {
                    conteoReservaciones++;
                    if(!e.visto) {
                        panelValidacionesNuevos++;
                    }
                    listadoNotificacionesValidaciones.push(e);
                    self.crearItem(e,panelValidaciones);
                }
                else if(e.subtipo == "incidencia-pendiente" || e.subtipo == "incidencia-repetida" || e.subtipo == "incidencia-cancelado"
                    || e.subtipo == "incidencia-concluido" || e.subtipo == "incidencia-solicitada") {
                    conteoIncidencias++;
                    if(!e.visto) {
                        panelValidacionesNuevos++;
                    }
                    listadoNotificacionesValidaciones.push(e);
                    self.crearItem(e,panelValidaciones);
                }
                if(e.subtipo == "pago-pendiente") {
                    conteoPagosPendientes++;
                    if(!e.visto) {
                        panelPendientesNuevos++;
                    }
                    listadoNotificacionesPendientes.push(e);
                    self.crearItem(e,panelPendientes);
                }
                if(e.subtipo == "reservacion-pendiente") {
                    conteoReservacionesPendientes++;
                    if(!e.visto) {
                        panelPendientesNuevos++;
                    }
                    listadoNotificacionesPendientes.push(e);
                    self.crearItem(e,panelPendientes);
                }
                if(e.subtipo == "incidencia-solicitada") {
                    conteoIncidenciasSolicitadas++;
                    if(!e.visto) {
                        panelPendientesNuevos++;
                    }
                    listadoNotificacionesPendientes.push(e);
                    self.crearItem(e,panelPendientes);
                }
            });
        }

        //Movimientos
        $("#botonTodosMovimientos").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Todas (" + (conteoCargos+conteoRecargos) + ")");
        $("#botonCargos").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Cargos (" + conteoCargos + ")");
        $("#botonRecargos").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Recargos (" + conteoRecargos + ")");
        $("#botonCargos2").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Cargos (" + conteoCargos + ")");
        $("#botonRecargos2").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Recargos (" + conteoRecargos + ")");
        if(panelMovimientosNuevos == 0){
            $("#conteoMovimientos").remove();
        }
        else {
            var conteoMoviminetos = document.createElement("b");
            $(conteoMoviminetos).addClass("badgeBST bg-color-red");
            $(conteoMoviminetos).attr("id", "conteoMovimientos");
            $(conteoMoviminetos).html(panelMovimientosNuevos);
            $("#nuevosActivity").append($(conteoMoviminetos));
        }

        //Validaciones
        $("#botonTodosValidaciones").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Todas (" + (conteoPagos+conteoReservaciones+conteoIncidencias) + ")");
        $("#botonPagos").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Pagos (" + conteoPagos + ")");
        $("#botonReservaciones").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Resevaciones (" + conteoReservaciones + ")");
        $("#botonIncidencias").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Incidencias (" + conteoIncidencias + ")");
        if(panelValidacionesNuevos == 0){
            $("#conteoValidaciones").remove();
        }
        else {
            var conteoValidaciones = document.createElement("b");
            $(conteoValidaciones).addClass("badgeBST bg-color-red");
            $(conteoValidaciones).attr("id", "conteoValidaciones");
            $(conteoValidaciones).html(panelValidacionesNuevos);
            $("#validacionesActivity").append($(conteoValidaciones));
        }

        //Pendientes
        $("#botonTodosPendientes").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Todas (" + (conteoReservacionesPendientes+conteoPagosPendientes+conteoIncidenciasSolicitadas) + ")");
        $("#botonPagosPendientes").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Pagos (" + conteoPagosPendientes + ")");
        $("#botonReservacionesPendientes").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Reservaciones (" + conteoReservacionesPendientes + ")");
        $("#botonIncidenciasSolicitadas").html("<input type=\"radio\" name=\"activity\" id=\"ajax/notify/mail.html\" style=\"width: 20%;\">Incidencias y Proyectos (" + conteoIncidenciasSolicitadas + ")");
        if(panelPendientesNuevos == 0){
            $("#conteoPendientes").remove();
        }
        else {
            var conteoPendientes = document.createElement("b");
            $(conteoPendientes).addClass("badgeBST bg-color-red");
            $(conteoPendientes).attr("id", "conteoPendientes");
            $(conteoPendientes).html(panelPendientesNuevos);
            $("#pendientesActivity").append($(conteoPendientes));
        }

        $(".ultimaActualizacion").html(new Date().toLocaleString());

    }

    eliminarNotificacion = function(idNotificacion) {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + "/notificaciones/eliminar/" + idNotificacion,
            type: 'POST',
            success: function (data) {
                self.obtenerNotificaciones();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Ocurrio un error al eliminar la notificación");
            }
        });
    }
    vistoNotificacion = function(idNotificacion, visto) {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + "/notificaciones/visto/" + idNotificacion + "/" + visto,
            type: 'POST',
            success: function (data) {
                self.obtenerNotificaciones();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Ocurrio un error al marcar como vista la notificación");
            }
        });
    }

    listarPor = function(tipo, panel) {
        $(panel).empty();
        if(panel == "#panelPendientesAdmin") {
            $.each(listadoNotificacionesPendientes, function (i, e) {
                if (tipo == 'todos' || e.subtipo == tipo) {
                    self.crearItem(e, panel);
                }

            });
        }
        else if(panel == "#panelMovimientosContacto") {
            $.each(listadoNotificacionesNuevos, function (i, e) {
                if (tipo == 'todos' || e.subtipo == tipo) {
                    self.crearItem(e, panel);
                }

            });
        }
        else if(panel == "#panelValidacionesContacto") {
            $.each(listadoNotificacionesValidaciones, function (i, e) {
                if (tipo == 'todos' || e.subtipo.indexOf(tipo) >= 0) {
                    self.crearItem(e, panel);
                }

            });
        }
    }

    self.crearItem = function (notificacion, panel) {
        var li = document.createElement("li");
        $(li).attr("id", "channel" + notificacion.id);


        var spanGeneral = document.createElement("span");
        $(spanGeneral).addClass("padding-10");
        $(spanGeneral).addClass("text-justify");
        $(spanGeneral).click(function(e){ vistoNotificacion(notificacion.id, true);window.location=contextPath+notificacion.link;e.stopPropagation();});
        $(spanGeneral).attr("link", notificacion.link);
        $(spanGeneral).attr("idReferencia", notificacion.id);
        /*$(spanGeneral).attr("onclick", "window.location='" + notificacion.link + "'");*/
        $(spanGeneral).attr("style", "cursor: pointer;");
        if(!notificacion.visto){
            $(spanGeneral).addClass("unread");
        }

        var em = document.createElement("em");
        var i = document.createElement("i");

        var aEliminar = document.createElement("a");
        var iEliminar = document.createElement("i");
        var aVisto = document.createElement("a");
        var iVisto = document.createElement("i");

        if(notificacion.subtipo == "pago-pendiente"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-orange txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-dollar fa-2x");
        }
        else if(notificacion.subtipo == "reservacion-pendiente"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-blue txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-tags fa-2x");
        }
        /*else if(notificacion.subtipo == "incidencia-solicitada"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-orange txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-bomb fa-2x");
        }*/
        else if(notificacion.subtipo == "nuevo-cargo"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-teal txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-exclamation-circle fa-2x");
            $(iVisto).addClass("fa fa-fw fa-eye txt-color-greenDark");
            $(aVisto).addClass("btn btn-default btn-xs pull-right avisto");
            $(aVisto).attr("visto", notificacion.visto);
            $(aVisto).click(function(e){ vistoNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
            $(aVisto).append($(iVisto));

            $(iEliminar).addClass("fa fa-fw fa-times txt-color-red");
            $(aEliminar).addClass("btn btn-default btn-xs pull-right aeliminar");
            $(aEliminar).attr("visto", notificacion.visto);
            $(aEliminar).click(function(e){ eliminarNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
            $(aEliminar).append($(iEliminar));

            $(spanGeneral).append($(aEliminar));
            $(spanGeneral).append($(aVisto));

        }
        else if(notificacion.subtipo == "nuevo-recargo"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-red txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-exclamation fa-2x");

            $(iVisto).addClass("fa fa-fw fa-eye txt-color-greenDark");
            $(aVisto).addClass("btn btn-default btn-xs pull-right avisto");
            $(aVisto).attr("visto", notificacion.visto);
            $(aVisto).click(function(e){ vistoNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
            $(aVisto).append($(iVisto));

            $(iEliminar).addClass("fa fa-fw fa-times txt-color-red");
            $(aEliminar).addClass("btn btn-default btn-xs pull-right aeliminar");
            $(aEliminar).attr("visto", notificacion.visto);
            $(aEliminar).click(function(e){ eliminarNotificacion(notificacion.id);e.stopPropagation();});
            $(aEliminar).append($(iEliminar));

            $(spanGeneral).append($(aEliminar));
            $(spanGeneral).append($(aVisto));
        }
        else if(notificacion.subtipo == "pago-aprobado" || notificacion.subtipo == "reservacion-aprobada"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-green txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-check fa-2x");

            $(iVisto).addClass("fa fa-fw fa-eye txt-color-greenDark");
            $(aVisto).addClass("btn btn-default btn-xs pull-right avisto");
            $(aVisto).attr("visto", notificacion.visto);
            $(aVisto).click(function(e){ vistoNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
            $(aVisto).append($(iVisto));

            $(iEliminar).addClass("fa fa-fw fa-times txt-color-red");
            $(aEliminar).addClass("btn btn-default btn-xs pull-right aeliminar");
            $(aEliminar).attr("visto", notificacion.visto);
            $(aEliminar).click(function(e){ eliminarNotificacion(notificacion.id);e.stopPropagation();});
            $(aEliminar).append($(iEliminar));

            $(spanGeneral).append($(aEliminar));
            $(spanGeneral).append($(aVisto));
        }
        else if(notificacion.subtipo == "pago-rechazado" || notificacion.subtipo == "pago-cancelado" ||
            notificacion.subtipo == "reservacion-rechazada" || notificacion.subtipo == "reservacion-cancelada"){
            $(em).addClass("badge padding-5 no-border-radius bg-color-red txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-times fa-2x");
            $(iVisto).addClass("fa fa-fw fa-eye txt-color-greenDark");
            $(aVisto).addClass("btn btn-default btn-xs pull-right avisto");
            $(aVisto).attr("visto", notificacion.visto);
            $(aVisto).click(function(e){ vistoNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
            $(aVisto).append($(iVisto));

            $(iEliminar).addClass("fa fa-fw fa-times txt-color-red");
            $(aEliminar).addClass("btn btn-default btn-xs pull-right aeliminar");
            $(aEliminar).attr("visto", notificacion.visto);
            $(aEliminar).click(function(e){ eliminarNotificacion(notificacion.id);e.stopPropagation();});
            $(aEliminar).append($(iEliminar));

            $(spanGeneral).append($(aEliminar));
            $(spanGeneral).append($(aVisto));
        }
        else if(notificacion.subtipo.substr(0,10) == "incidencia"){
            $(em).addClass("badge padding-5 no-border-radius txt-color-white pull-left margin-right-5");
            if(notificacion.subtipo == "incidencia-solicitada"){
                $(em).addClass("bg-color-orange");
            } else {
                if (notificacion.subtipo == "incidencia-pendiente") {
                    $(em).addClass("bg-color-blue");
                } else if (notificacion.subtipo == "incidencia-repetida") {
                    $(em).addClass("bg-color-blueDark");
                } else if (notificacion.subtipo == "incidencia-cancelado") {
                    $(em).addClass("bg-color-red");
                } else if (notificacion.subtipo == "incidencia-concluido") {
                    $(em).addClass("bg-color-green");
                } else if (notificacion.subtipo == "incidencia-solicitada") {
                    $(em).addClass("bg-color-orange");
                }

                $(iVisto).addClass("fa fa-fw fa-eye txt-color-greenDark");
                $(aVisto).addClass("btn btn-default btn-xs pull-right avisto");
                $(aVisto).attr("visto", notificacion.visto);
                $(aVisto).click(function(e){ vistoNotificacion(notificacion.id, notificacion.visto);e.stopPropagation();});
                $(aVisto).append($(iVisto));

                $(iEliminar).addClass("fa fa-fw fa-times txt-color-red");
                $(aEliminar).addClass("btn btn-default btn-xs pull-right aeliminar");
                $(aEliminar).attr("visto", notificacion.visto);
                $(aEliminar).click(function(e){ eliminarNotificacion(notificacion.id);e.stopPropagation();});
                $(aEliminar).append($(iEliminar));

                $(spanGeneral).append($(aEliminar));
                $(spanGeneral).append($(aVisto));
            }
            $(i).addClass("fa fa-fw fa-bomb fa-2x");
        }
        else {
            $(em).addClass("badge padding-5 no-border-radius bg-color-blueDark txt-color-white pull-left margin-right-5");
            $(i).addClass("fa fa-fw fa-question fa-2x");
        }

        var spanContenido = document.createElement("span");
        $(spanContenido).html(notificacion.formattedHtmlAlert);

        $(em).append($(i));
        $(spanGeneral).append($(em));
        $(spanGeneral).append($(spanContenido));
        $(li).append($(spanGeneral));
        $(panel).append($(li));
    }

    self.obtenerNotificaciones = function() {
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + "/notificaciones/todas",
            type: 'GET',
            dataType: 'json',
            data: '',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                self.crearPaneles(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Ocurrio un error al recuperar las notificaciones");
            },
             global: false
        });
    }

    self.ordenarAsc = function(notificaciones) {
        var ordenados = notificaciones;
        var hoy = new Date();
        ordenados.sort(function (a, b){
            var fechaA = new Date(a.fecha);
            var fechaB = new Date(b.fecha);
            /*if(a.titulo == "reservacion-pendiente" && b.titulo == "reservacion-pendiente") {
                return (Math.abs(fechaA.getTime() - hoy.getTime()) - Math.abs(fechaB.getTime() - hoy.getTime()));
            }
            return (Math.abs(fechaB.getTime() - hoy.getTime()) - Math.abs(fechaA.getTime() - hoy.getTime()));*/

            /*if(a.titulo == "pago-pendiente" && b.titulo == "pago-pendiente") {
                return (Math.abs(fechaB.getTime() - hoy.getTime()) - Math.abs(fechaA.getTime() - hoy.getTime()));
            }*/
            return (Math.abs(fechaA.getTime() - hoy.getTime()) - Math.abs(fechaB.getTime() - hoy.getTime()));
        });
        return ordenados;
    }
}