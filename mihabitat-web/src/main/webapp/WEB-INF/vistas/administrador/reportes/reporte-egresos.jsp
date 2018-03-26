<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<head>
    <title><spring:message code="mihabitat.reportes"/> | <spring:message code="mihabitat.nombre"/></title>
    <style type="text/css">
        .currency {
            text-align: right;
        }
    </style>
</head>
<body>
<div id="ribbon">
    <ol class="breadcrumb">
        <li>
            <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                <spring:message code="mihabitat.menu.inicio"/>
            </a>
        </li>
    </ol>
</div>
<div id="content">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <div class="jarviswidget" id="wid-id-1">
                <header>
                        <span class="widget-icon"> <i class="fa fa-edit"></i>
                        </span>

                    <h2>
                        <spring:message code="mihabitat.reportes.gastos"/>
                    </h2>
                </header>
                <div>
                    <div class="jarviswidget-editbox"></div>
                    <div class="widget-body no-padding">
                        <form id="reporte-form" class="smart-form">
                            <fieldset>
                                <div class="col-sm-12 col-md-12 col-lg-12">
                                    <div class="row">
                                        <div class="col col-md-2">
                                            <label class="label">
                                                <span class="error-required">*</span><spring:message
                                                    code="mihabitat.reportes.gastos.fecha.inicio"/>
                                            </label>
                                            <input class="form-control" type="text" name="inicio" id="inicio"
                                                   placeholder="<spring:message code="mihabitat.reportes.gastos.fecha.inicio" />"
                                                   required="required" readonly="readonly" style="background: white;"
                                                   data-bind="value: $root.reporte.inicio">
                                        </div>
                                        <div class="col col-md-2">
                                            <label class="label">
                                                <span class="error-required">*</span><spring:message
                                                    code="mihabitat.reportes.gastos.fecha.fin"/>
                                            </label>
                                            <input class="form-control" type="text" name="fin" id="fin"
                                                   placeholder="<spring:message code="mihabitat.reportes.gastos.fecha.fin" />"
                                                   required="required" readonly="readonly" style="background: white;"
                                                   data-bind="value: $root.reporte.fin">
                                        </div>
                                        <div class="col col-md-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <button type="button" class="btn btn-default btn-sm btn-block"
                                                    data-bind="event : { click: $root.valida }">
                                                <spring:message code="mihabitat.botones.aceptar"/>
                                            </button>
                                        </div>
                                        <div class="col col-md-4">
                                            <div class="row">
                                                <div class="col col-md-3">
                                                    <label class="label">
                                                        &nbsp;
                                                    </label>
                                                    <button type="button" class="btn btn-primary btn-xs btn-block"
                                                            data-bind="event : { click: function() { $root.imprimir('pdf') } }">
                                                        <i class="fa fa-file-pdf-o"></i>
                                                        <spring:message code="mihabitat.reportes.pdf"/>
                                                    </button>
                                                </div>
                                                <div class="col col-md-3">
                                                    <label class="label">
                                                        &nbsp;
                                                    </label>
                                                    <button type="button" class="btn btn-primary btn-xs btn-block"
                                                            data-bind="event : { click: function() { $root.imprimir('xlsx') } }">
                                                        <i class="fa fa-file-excel-o"></i>
                                                        <spring:message code="mihabitat.reportes.xls"/>
                                                    </button>
                                                </div>
                                                <div class="col col-md-3">
                                                    <label class="label">
                                                        &nbsp;
                                                    </label>
                                                    <button type="button" class="btn btn-primary btn-xs btn-block"
                                                            data-bind="event : { click: function() { $root.imprimir('csv') } }">
                                                        <i class="fa fa-file-excel-o"></i>
                                                        <spring:message code="mihabitat.reportes.csv"/>
                                                    </button>
                                                </div>
                                                <div class="col col-md-3">
                                                    <label class="label">
                                                        &nbsp;
                                                    </label>
                                                    <button type="button" class="btn btn-primary btn-xs btn-block"
                                                            data-bind="event : { click: function() { $root.imprimir('txt') } }">
                                                        <i class="fa fa-file-text-o"></i>
                                                        <spring:message code="mihabitat.reportes.txt"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col col-md-2">
                                            &nbsp;
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <section id="widget-grid" class="">
        <div class="row">
            <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget" id="wid-id-2" data-widget-editbutton="false">
                    <header>
                        <span class="widget-icon"> <i class="fa fa-table"></i> </span>

                        <h2></h2>
                    </header>
                    <div>
                        <div class="jarviswidget-editbox">
                        </div>
                        <div class="widget-body no-padding">
                            <div class="panel-group smart-accordion-default" id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading no-padding">
                                        <div class="table-responsive">
                                            <table id="table-cuentas" class="table table-striped table-bordered"
                                                   style="width: 100%">
                                                <thead>
                                                <tr>
                                                    <th class="col-sm-3 col-md-3 col-lg-2"><spring:message
                                                            code="mihabitat.reportes.gastos.detalles.fecha"/></th>
                                                    <th class="col-sm-3 col-md-3 col-lg-4"><spring:message
                                                            code="mihabitat.reportes.gastos.detalles.proveedor"/></th>
                                                    <th class="col-sm-3 col-md-3 col-lg-3"><spring:message
                                                            code="mihabitat.reportes.gastos.detalles.concepto"/></th>
                                                    <th class="col-sm-3 col-md-3 col-lg-3"><spring:message
                                                            code="mihabitat.reportes.gastos.detalles.metodo"/></th>
                                                    <th class="col-sm-3 col-md-3 col-lg-2"><spring:message
                                                            code="mihabitat.reportes.gastos.detalles.monto"/></th>
                                                </tr>
                                                </thead>
                                                <tbody data-bind="foreach : { data: $root.reporte.egresos }">
                                                <tr class="active">
                                                    <td data-bind="text: moment(fecha()).format('YYYY-MM-DD')"></td>
                                                    <td data-bind="text: proveedor.nombre"></td>
                                                    <td data-bind="text: conceptos"></td>
                                                    <td data-bind="text: metodoPago.descripcion"></td>
                                                    <td data-bind="text: montoEgreso"></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
        </div>
    </section>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/reporte-egresos.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/egreso-detalle.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/egresos-detalle-app.js?v=${project-version}"></script>

<script type="text/javascript">
    $(function () {
        var viewModel = new ReporteEgresoViewModel();
        ko.applyBindings(viewModel);
        $("#inicio, #fin").datepicker();
    });
</script>
</body>