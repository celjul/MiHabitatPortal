<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.notificaciones.configuracion"/> | <spring:message code="mihabitat.nombre"/></title>
</head>
<body>
<div id="ribbon">
    <ol class="breadcrumb">
        <li>
            <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                <spring:message code="mihabitat.menu.inicio"/>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/administrador/confnotificaciones/lista">
                <spring:message code="mihabitat.menu.notificaciones.configuracion"/>
            </a>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
    <div class="alert alert-warning alert-block">
        <a class="close" data-dismiss="alert" href="#">X</a>
        <h4 class="alert-heading">
            Instrucciones:
        </h4>
        Indique por cada condómino el medio por el cual debe recibir cada notificación o bien, filtre los condóminos por nombre, departamento ó
        aquellos que sean habitantes o propietarios para configurarles masivamente. Ésta configuración aplica sólo para las nuevas notificaciones.<br>
        Notaciones: C = Contacto Principal, P = Propietario, H = Habitante <br>


    </div>
    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
        <header>
            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
            <h2><spring:message code="mihabitat.menu.notificaciones.configuracion"/></h2>
        </header>
        <div>
            <div class="jarviswidget-editbox">
            </div>

            <div class="widget-body no-padding">

                    <table id="table-listadoConfNots" class="table table-striped table-bordered table-hover" >
                        <thead>
                        <tr>
                            <th style="display: none">
                            </th>
                            <th style="min-width: 300px">
                                <spring:message code="mihabitat.contacto" />
                            </th>
                            <th style="min-width: 80px">
                                <spring:message code="mihabitat.notificacion.tipo" />
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoCargo" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(1)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(1)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(1)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoRecargo" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(2)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(2)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(2)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionAbonoValidado" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(3)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(3)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(3)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionAbonoCancelado" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(4)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(4)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(4)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionAprovecharDescuento" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(5)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(5)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(5)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionEvitarRecargo" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(6)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(6)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(6)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionAvisoCobranza" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(7)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(7)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(7)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoTema" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(8)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(8)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(8)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoComentarioTemaPropio" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(9)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(9)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(9)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>

                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoComentarioTemaComentado" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(10)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(10)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(10)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoComentario" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(11)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(11)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(11)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionNuevoAviso" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(12)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(12)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(12)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionReservacionValidada" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(13)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(13)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(13)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                            <th style="min-width: 100px">
                                <spring:message code="mihabitat.notificacion.configuracion.tipoNotificacionIncidenciaActualizada" /><br>
                                <a data-bind="event: { click: function() {$root.cambioMuchosApp(14)}}" class="btn btn-success btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> App </span>&nbsp;
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosEmail(14)}}" class="btn btn-info btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Email </span>
                                </a>
                                <a data-bind="event: { click: function() {$root.cambioMuchosNinguno(14)}}" class="btn btn-danger btn-xs" style="padding: 1px;height: 18px;text-align: center">
                                    <span style="font-size: 9px;text-align: center"> Ning </span>
                                </a>
                            </th>
                        </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.listadoConfNots, as: 'cnf' }">
                        <tr>
                            <td data-bind="text: contactoDepartamento.id.contacto.id() + '|' + contactoDepartamento.id.departamento.id()" style="display: none">

                            </td>
                            <td data-bind="text: contactoDepartamento.id.contacto.nombre() + ' ' +
                                contactoDepartamento.id.contacto.apellidoPaterno() + ' ' +
                                contactoDepartamento.id.contacto.apellidoMaterno() + ' | ' + contactoDepartamento.id.departamento.nombre()"></td>
                            <td data-bind="text: (contactoDepartamento.principal()?'C':'')  + (contactoDepartamento.propietario()?'P':'')  +
                                            (contactoDepartamento.habitante()?'H':'')"></td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoCargo.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoCargo',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoCargo.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoCargo.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoCargo.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoRecargo.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoRecargo',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoRecargo.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoRecargo.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoRecargo.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoRecargo" id="tipoNotificacionNuevoRecargo"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoRecargo.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionAbonoValidado.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionAbonoValidado',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionAbonoValidado.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionAbonoValidado.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionAbonoValidado.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionAbonoValidado" id="tipoNotificacionAbonoValidado"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionAbonoValidado.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionAbonoCancelado.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionAbonoCancelado',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionAbonoCancelado.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionAbonoCancelado.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionAbonoCancelado.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionAbonoCancelado" id="tipoNotificacionAbonoCancelado"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionAbonoCancelado.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionAprovecharDescuento.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionAprovecharDescuento',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionAprovecharDescuento.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionAprovecharDescuento.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionAprovecharDescuento.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionAprovecharDescuento" id="tipoNotificacionAprovecharDescuento"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionAprovecharDescuento.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionEvitarRecargo.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionEvitarRecargo',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionEvitarRecargo.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionEvitarRecargo.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionEvitarRecargo.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionEvitarRecargo" id="tipoNotificacionEvitarRecargo"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionEvitarRecargo.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionAvisoCobranza.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionAvisoCobranza',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionAvisoCobranza.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionAvisoCobranza.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionAvisoCobranza.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionAvisoCobranza" id="tipoNotificacionAvisoCobranza"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionAvisoCobranza.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoTema.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoTema',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoTema.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoTema.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoTema.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoTema" id="tipoNotificacionNuevoTema"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoTema.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoComentarioTemaPropio.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoComentarioTemaPropio',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoComentarioTemaPropio.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoComentarioTemaPropio.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoComentarioTemaPropio.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoComentarioTemaPropio" id="tipoNotificacionNuevoComentarioTemaPropio"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoComentarioTemaPropio.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>

                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoComentarioTemaComentado.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoComentarioTemaComentado',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoComentarioTemaComentado.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoComentarioTemaComentado.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoComentarioTemaComentado.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoComentarioTemaComentado" id="tipoNotificacionNuevoComentarioTemaComentado"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoComentarioTemaComentado.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                        data-bind="editable: tipoNotificacionNuevoComentario.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                    optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoComentario',
                                                    opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoComentario.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                     'label-success' : tipoNotificacionNuevoComentario.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                     'label-danger' : tipoNotificacionNuevoComentario.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                        data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoComentario" id="tipoNotificacionNuevoComentario"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoComentario.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionNuevoAviso.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionNuevoAviso',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionNuevoAviso.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionNuevoAviso.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionNuevoAviso.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionNuevoAviso" id="tipoNotificacionNuevoAviso"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionNuevoAviso.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>

                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionReservacionValidada.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionReservacionValidada',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionReservacionValidada.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionReservacionValidada.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionReservacionValidada.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionReservacionValidada" id="tipoNotificacionReservacionValidada"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionReservacionValidada.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                            <td style="text-align:center">
                                <a href="#" class="notOption label" data-placement="left" data-pk="1" style="color: #FFFFFF; font-size: 12px;text-align:center"
                                    data-bind="editable: tipoNotificacionIncidenciaActualizada.id, editableOptions: {pk: id, options: $root.tiposNotificacion,
                                                optionsText: 'descripcion', optionsValue: 'id', type: 'select', notificacion: 'tipoNotificacionIncidenciaActualizada',
                                                opcionConf: JSON.stringify(cnf.getJson())}, css: {'label-info' : tipoNotificacionIncidenciaActualizada.id() == AppConfig.catalogos.tiponotificaciones.email,
                                                 'label-success' : tipoNotificacionIncidenciaActualizada.id() == AppConfig.catalogos.tiponotificaciones.app,
                                                 'label-danger' : tipoNotificacionIncidenciaActualizada.id() == AppConfig.catalogos.tiponotificaciones.ninguno}"
                                    data-original-title="Notificar por..."></a>
                                <!--<label class="select">
                                    <select style="width: 100%"
                                            name="tipoNotificacionIncidenciaActualizada" id="tipoNotificacionIncidenciaActualizada"
                                            required="required"
                                            data-bind="options: $root.tiposNotificacion,
                                               optionsText: 'descripcion',
                                               optionsValue: 'id',
                                               value: tipoNotificacionIncidenciaActualizada.id,
                                               event: { change: function() {$root.cambioUno(cnf)}}">
                                    </select><i></i>
                                </label>-->
                            </td>
                        </tr>
                        </tbody>
                    </table>

            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/datatables-fixedcolumns/dataTables.fixedColumns.min.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/x-editable/x-editable.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/x-editable/knockout.x-editable.min.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/fooplugins/footable.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/footable.core.css"/>



<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/notificaciones/confnotcontacto.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/notificaciones/confnotcontacto-app.js"></script>

<script type="text/javascript">
    $(function() {
        var viewModel = new ConfiguracionNotificacionContactoApp({
            listadoConfNots : ${listadoConfNots},
            tiposNotificacion : ${tiposNotificacion}
        });
        ko.applyBindings(viewModel);

        var id = "#table-listadoConfNots";

        var otable = $(id).DataTable({

            "scrollX": true,
            "scrollY": "300px",
            "aaSorting" : [],
            "aoColumns": [
                { "bSearchable": false, "bSortable": false },
                null,
                null,
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false },
                { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : false,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollCollapse": false
        });

        yadcf.init(otable, [
            {
                column_number: 1,
                filter_type: "text",
                filter_default_label : "Condómino",
                filter_reset_button_text: false
            },
            {
                column_number: 2,
                filter_type : 'select',
                filter_default_label : "Tipo",
                select_type: 'select2',
                column_data_type: "html",
                html_data_type: "text",
                filter_reset_button_text: false
            }]);

        $("#table-listadoConfNots_filter").hide();



        $('.notOption').on('save', function(e, params) {
            if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoCargo"){
                viewModel.configuracionNotificacion.tipoNotificacionNuevoCargo.id(params.newValue);
            } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoRecargo"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoRecargo.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionAbonoValidado"){
               viewModel.configuracionNotificacion.tipoNotificacionAbonoValidado.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionAbonoCancelado"){
               viewModel.configuracionNotificacion.tipoNotificacionAbonoCancelado.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionAprovecharDescuento"){
                viewModel.configuracionNotificacion.tipoNotificacionAprovecharDescuento.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionEvitarRecargo"){
              viewModel.configuracionNotificacion.tipoNotificacionEvitarRecargo.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionAvisoCobranza"){
               viewModel.configuracionNotificacion.tipoNotificacionAvisoCobranza.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoTema"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoTema.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoComentarioTemaPropio"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoComentarioTemaPropio.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoComentarioTemaComentado"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoComentarioTemaComentado.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoComentario"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoComentario.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionNuevoAviso"){
               viewModel.configuracionNotificacion.tipoNotificacionNuevoAviso.id(params.newValue);
           } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionReservacionValidada"){
                viewModel.configuracionNotificacion.tipoNotificacionReservacionValidada.id(params.newValue);
            } else if(viewModel.notificacionSeleccionada() === "tipoNotificacionIncidenciaActualizada"){
                viewModel.configuracionNotificacion.tipoNotificacionIncidenciaActualizada.id(params.newValue);
            }

            viewModel.guardar();
        });

        $('.notOption').on('shown', function(e, editable) {
            var cnf = JSON.parse(editable.options.opcionConf);
            viewModel.configuracionNotificacion.cargar(cnf);
            viewModel.notificacionSeleccionada(editable.options.notificacion);
        });


    });
</script>
</body>
