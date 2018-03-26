<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <title><spring:message code="mihabitat.menu.ingresosnoidentificados"/> | <spring:message
            code="mihabitat.nombre"/></title>
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
            <a href="${pageContext.request.contextPath}/administrador/ingresosnoidentificados/lista">
                <spring:message code="mihabitat.menu.ingresosnoidentificados"/>
            </a>
        </li>
    </ol>
</div>
<div id="content">
    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
        <header>
            <span class="widget-icon"> <i class="fa fa-table"></i> </span>

            <h2><spring:message code="mihabitat.menu.ingresosnoidentificados"/></h2>

            <div class="widget-toolbar">
                <a href="${pageContext.request.contextPath}/administrador/ingresosnoidentificados/nuevo"
                   class="btn btn-success"> <i class="fa fa-plus-circle"></i> Agregar </a>
            </div>
        </header>
        <div>
            <div class="jarviswidget-editbox"></div>
            <div class="widget-body no-padding">
                <table id="table-ingresosNoIdentificados" class="table table-striped table-bordered table-hover"
                       style="width: 100%">
                    <thead>
                    <tr>
                        <th class="hasinput col-md-1">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.fecha" />"/>
                        </th>
                        <th class="hasinput col-md-1">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.metododepago" />"/>
                        </th>
                        <th class="hasinput col-md-1">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.monto" />"/>
                        </th>
                        <th class="hasinput col-md-1">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.cuenta" />"/>
                        </th>
                        <th class="hasinput col-md-2">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.comentario" />"/>
                        </th>
                        <th class="hasinput col-md-2">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.ingresonoidentificado.estatus" />"/>
                        </th>
                        <th class="hasinput col-md-1">
                        </th>
                    </tr>
                    <tr>
                        <th data-class="expand">
                            <spring:message code="mihabitat.ingresonoidentificado.fecha"/>
                        </th>
                        <th data-hide="phone">
                            <spring:message code="mihabitat.ingresonoidentificado.metododepago"/>
                        </th>
                        <th>
                            <spring:message code="mihabitat.ingresonoidentificado.monto"/>
                        </th>
                        <th data-hide="phone, tablet">
                            <spring:message code="mihabitat.ingresonoidentificado.cuenta"/>
                        </th>
                        <th data-hide="phone, tablet">
                            <spring:message code="mihabitat.ingresonoidentificado.comentario"/>
                        </th>
                        <th>
                            <spring:message code="mihabitat.ingresonoidentificado.estatus"/>
                        </th>
                        <th style="text-align: center;">
                            <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                        </th>
                    </tr>
                    </thead>
                    <tbody data-bind="foreach : { data: $root.ingresosNoIdentificados }">
                    <tr data-bind="css : {
                                            'warning': estatus.id == AppConfig.catalogos.estatusotrosingresos.registrado,
                                            '': estatus.id == AppConfig.catalogos.estatusotrosingresos.aplicado,
                                            'danger': estatus.id == AppConfig.catalogos.estatusotrosingresos.cancelado}">
                        <td data-bind="text: fecha"></td>
                        <td data-bind="text: metodoPago.descripcion"></td>
                        <td data-bind="currency: monto, symbol: $ " style="text-align: right;"></td>
                        <td data-bind="text: cuentaBanco.nombre"></td>
                        <td data-bind="text: comentario"></td>
                        <td>
                            <label class="label" data-bind="text: estatus.descripcion, css : {
                                            'label-warning': estatus.id == AppConfig.catalogos.estatusotrosingresos.registrado,
                                            'label-success': estatus.id == AppConfig.catalogos.estatusotrosingresos.aplicado,
                                            'label-danger': estatus.id == AppConfig.catalogos.estatusotrosingresos.cancelado}">
                            </label>
                            &nbsp;
                            <span data-bind="text: aplicadoEn"></span>
                        </td>
                        <td style="text-align: center;">
                            <a href="javascript:void(0);" class="btn btn-xs btn-success" data-bind="click: $root.ver">
                                <i class="fa fa-pencil"></i>
                                <spring:message code="mihabitat.botones.detalle"/>
                            </a>
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

<script src="${pageContext.request.contextPath}/recursos/js/app/ingresosnoidentificados/ingresosnoidentificados-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/ingresosnoidentificados/ingresosnoidentificados.js?v=${project-version}"></script>
<script type="text/javascript">
    $(function () {
        var viewModel = new IngresosNoIdentificadosListViewApp({
            ingresosNoIdentificados: ${ingresosNoIdentificados}
        });
        ko.applyBindings(viewModel);
        var table = dibujarTabla("#table-ingresosNoIdentificados");
    });
</script>
</body>