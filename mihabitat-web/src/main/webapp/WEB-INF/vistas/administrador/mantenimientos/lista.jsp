<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <title><spring:message code="mihabitat.menu.mantenimientos"/> | <spring:message code="mihabitat.nombre"/></title>
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
            <a href="${pageContext.request.contextPath}/administrador/mantenimientos/lista">
                <spring:message code="mihabitat.menu.mantenimientos"/>
            </a>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
        <header>
            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
            <h2><spring:message code="mihabitat.menu.mantenimientos"/></h2>
            <div class="widget-toolbar">
                <a class="btn btn-success" data-toggle="modal" data-target="#myModalMantenimiento"
                   data-bind="click: $root.nuevoMantenimiento"> <i class="fa fa-plus-circle"></i> <spring:message
                        code="mihabitat.botones.agregar"/> </a>
            </div>
        </header>
        <div>
            <div class="jarviswidget-editbox">
            </div>

            <div class="widget-body no-padding">
                <table id="table-mantenimientos" class="table table-striped table-bordered table-hover"
                       style="width: 100%">
                    <thead>
                    <tr>
                        <th class="hasinput" style="width: 50%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.mantenimientos.descripcion" />"/>
                        </th>
                        <th class="hasinput" style="width: 30%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.mantenimientos.monto" />"/>
                        </th>
                        <th class="hasinput" style="width: 30%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.mantenimientos.tipoCobro" />"/>
                        </th>
                        <th style="width: 20%;">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <spring:message code="mihabitat.mantenimientos.descripcion"/>
                        </th>
                        <th>
                            <spring:message code="mihabitat.mantenimientos.monto"/>
                        </th>
                        <th>
                            <spring:message code="mihabitat.mantenimientos.tipoCobro"/>
                        </th>
                        <th>

                        </th>
                    </tr>
                    </thead>
                    <tbody data-bind="foreach : { data: $root.mantenimientos }">
                    <tr>
                        <td data-bind="text: descripcion"></td>
                        <td data-bind="text: '$' + monto()" style="text-align: right;"></td>
                        <td data-bind="text: tipoCobroDepartamento.descripcion" style="text-align: center;"></td>
                        <td style="text-align: center;">
                            <a class="btn btn-success btn-xs" data-toggle="modal"
                               data-target="#myModalMantenimiento" data-bind="click: $root.editarMantenimiento">
                                <i class="fa fa-pencil"></i> <spring:message code="mihabitat.botones.editar"/> </a>

                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="contentModal">
    <jsp:include page="mantenimiento.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>


<script type="text/javascript">
    $(function () {
        var viewModel = new ListaMantenimientoCondominioViewModel({
            mantenimientos: ${mantenimientos},
            tiposCobro: ${tiposCobro}
        });
        ko.applyBindings(viewModel);
        var table = dibujarTabla("#table-mantenimientos");
    });
</script>
</body>
