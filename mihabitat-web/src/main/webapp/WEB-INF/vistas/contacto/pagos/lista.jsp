<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.pagos"/> | <spring:message code="mihabitat.nombre"/></title>
</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${condominio.id}">
                   <spring:message code="mihabitat.menu.inicio"/>
               </a>
             </li>
            <li>
               <a href="${pageContext.request.contextPath}/contacto/mis-pagos/lista">
                   <spring:message code="mihabitat.menu.pagos"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.pagos"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/contacto/mis-pagos/nuevo" class="btn btn-success"> <i class="fa fa-plus-circle"></i> Agregar </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-pagos" class="table table-bordered table-hover" style="width: 100%">
                        <thead>
                        <tr>
                            <th class="hasinput" style="width: 10%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.contacto" />" />
                            </th>
                            <th class="hasinput" style="width: 10%;min-width: 50px;">
                            </th>
                            <th class="hasinput" style="width: 20%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.contacto" />" />
                            </th>
                            <th class="hasinput" style="width: 10%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.departamento" />" />
                            </th>
                            <th class="hasinput" style="width: 15%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.metodo" />" />
                            </th>
                            <th class="hasinput" style="width: 10%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.referencia" />" />
                            </th>
                            <th class="hasinput" style="width: 15%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.monto" />" />
                            </th>
                            <%--<th class="hasinput" style="width: 10%;">
                                <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.estatus" />" />
                            </th>--%>
                            <th class="hasinput" style="width: 10%;">
                            </th>
                        </tr>
                        <tr>
                            <th data-hide="phone">
                                <spring:message code="mihabitat.pago.folio" />
                            </th>
                            <th data-class="expand">
                                <spring:message code="mihabitat.pago.fecha" />
                            </th>
                            <th data-hide="phone">
                                <spring:message code="mihabitat.contacto" />
                            </th>
                            <th data-hide="phone">
                                <spring:message code="mihabitat.departamento" />
                            </th>
                            <th data-hide="phone">
                                <spring:message code="mihabitat.pago.metodo" />
                            </th>
                            <th data-hide="phone">
                                <spring:message code="mihabitat.pago.referencia" />
                            </th>
                            <th>
                                <spring:message code="mihabitat.pago.monto" />
                            </th>
                            <%-- <th>
                                 <spring:message code="mihabitat.pago.estatus" />
                             </th>--%>
                            <th style="text-align: center;">
                                <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                            </th>
                        </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.pagos }">
                            <tr data-bind="css: {success: (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.aprobado),
                                        warning: (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.pendiente),
                                        danger: ((estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.rechazado) ||
                                            (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.cancelado))}">
                                <td data-bind="text: folio"></td>
                                <td data-bind="text: (moment(fecha,'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
                                <td data-bind="text: contacto.nombre + ' ' + contacto.apellidoPaterno + (contacto.apellidoMaterno? (' ' + contacto.apellidoMaterno) : '')"></td>
                                <td data-bind="foreach : { data: pagosDepartamento }">
                                    <label data-bind="text: departamento.nombre + ' '"></label>
                                </td>
                                <td data-bind="text: metodoPago.descripcion"></td>
                                <td data-bind="text: referencia"></td>
                                <td  style="text-align: right;">
                                    <span class="label pull-left" data-bind="text: estatus[estatus.length - 1].estatus.descripcion,
                                        css: {'label-success': (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.aprobado),
                                        'label-warning': (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.pendiente),
                                        'label-danger': ((estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.rechazado) ||
                                            (estatus[estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.cancelado))}">

                                    </span>
                                    <span data-bind="currency: monto, symbol: $ ">

                                    </span>
                                </td>
                                <%--<td data-bind="text: estatus[estatus.length - 1].estatus.descripcion"></td>--%>
                                <td style="text-align: center;">
                                   <div class="btn-group">
                                       <a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
                                       <a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
                                       <ul class="dropdown-menu dropdown-menu-right">
                                           <li>
                                               <a href="javascript:void(0);" data-bind="click: $root.verContacto">
                                                   <spring:message code="mihabitat.pago.detalle" />
                                               </a>
                                           </li>
                                           <li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Aprobado' && comprobante">
                                               <a href="javascript:void(0);" data-bind="click: $root.descargar">
                                                   <spring:message code="mihabitat.pago.adjunto" />
                                               </a>
                                           </li>
                                           <li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Aprobado'">
                                               <a href="javascript:void(0);" data-bind="click: $root.abrirModalEnvio">
                                                   <spring:message code="mihabitat.pago.enviar" />
                                               </a>
                                           </li>
                                           <li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Aprobado'">
                                               <a href="javascript:void(0);" data-bind="click: $root.imprimir">
                                                   <spring:message code="mihabitat.pago.imprimir" />
                                               </a>
                                           </li>
                                       </ul>
                                   </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../../pagos/envio.jsp" />
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaPagoViewModel({
                pagos : ${pagos}
            });
            ko.applyBindings(viewModel);
            /*var table = dibujarTabla("#table-pagos",{
                "aaSorting" : [[0,'desc']],
                "aoColumns": [
                    { "iDataSort": 0 }, null, null, null, null, null, null, null
                ]
            });*/
        });
    </script>
</body>