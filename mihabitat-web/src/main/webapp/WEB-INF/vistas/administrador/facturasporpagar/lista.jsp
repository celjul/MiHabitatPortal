<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <title><spring:message code="mihabitat.menu.facturasporpagar"/> | <spring:message code="mihabitat.nombre"/></title>
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
            <a href="${pageContext.request.contextPath}/administrador/facturasporpagar/lista">
                <spring:message code="mihabitat.menu.facturasporpagar"/>
            </a>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
        <header>
            <span class="widget-icon"> <i class="fa fa-table"></i> </span>

            <h2><spring:message code="mihabitat.menu.facturasporpagar"/></h2>
        </header>
        <div>
            <div class="jarviswidget-editbox"></div>
            <div class="widget-body no-padding">
                <table id="table-facturasxp" class="table table-striped table-bordered table-hover" style="width: 100%">
                    <thead>
                    <tr>
                        <th class="hasinput" style="width: 15%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.proveedor" />"/>
                        </th>
                        <th class="hasinput" style="width: 15%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.facturasporpagar.uuid" />"/>
                        </th>
                        <th class="hasinput" style="width: 10%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.facturasporpagar.fechaRecepcion" />"/>
                        </th>
                        <th class="hasinput" style="width: 10%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />"/>
                        </th>
                        <th class="hasinput" style="width: 10%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.mantenimientos.monto" />"/>
                        </th>
                        <th class="hasinput" style="width: 15%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.facturasporpagar.pagado" />"/>
                        </th>
                        <th class="hasinput" style="width: 15%;">
                            <input type="text" class="form-control"
                                   placeholder="<spring:message code="mihabitat.facturasporpagar.adeudo" />"/>
                        </th>
                        <th class="hasinput" style="width: 40%;">
                        </th>
                    </tr>
                    <tr>
                        <th data-class="expand">
                            <spring:message code="mihabitat.proveedor"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.facturasporpagar.uuid"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.facturasporpagar.fechaRecepcion"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.facturasporpagar.fechaVencimiento"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.mantenimientos.monto"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.facturasporpagar.pagado"/>
                        </th>
                        <th data-class="expand">
                            <spring:message code="mihabitat.facturasporpagar.adeudo"/>
                        </th>
                        <th style="text-align: center;">
                            <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                        </th>
                    </tr>
                    </thead>
                    <tbody data-bind="foreach : { data: $root.facturasxp }">
                    <tr>
                        <td data-bind="text: proveedor.nombre"></td>
                        <td data-bind="text: uuid"></td>
                        <td data-bind="text: fechaEmision"></td>
                        <td data-bind="text: fechaVencimiento"></td>
                        <td data-bind="text: total"></td>
                        <td data-bind="text: pagado"></td>
                        <td data-bind="text: pendiente"></td>
                        <td style="text-align: center;">
                            <div class="btn-group">
                                <button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-cog"></i> <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li>
                                        <a href="#" data-toggle="modal" data-target="#PreviewCFDIModal"
                                           data-bind="click: $root.previewCFDI">
                                            <spring:message code="mihabitat.botones.preview"/>
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
    <!-- Modal de la Vista Previa de la Factura (ver detalle) -->
    <div class="modal fade" id="PreviewCFDIModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="widget-body no-padding">
            <fieldset>
                <div class="modal-dialog" style="width: 80%">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="myModalLabel"><spring:message
                                    code="mihabitat.facturasporpagar.vistaPrevia"/></h4>
                        </div>
                        <div class="modal-body">
                            <form id="previewcfdi-form" class="smart-form">
                                <div class="row">
                                    <section class="col col-md-6">
                                        <label data-bind="text: '<spring:message code="mihabitat.proveedor.razonSocial" />: ' + $root.temporal.emisor()"/>
                                    </section>
                                    <section class="col col-md-6">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.foliofiscal" />: ' + $root.temporal.uuid()"/>
                                    </section>
                                    <section class="col col-md-6">
                                        <label data-bind="text: '<spring:message code="mihabitat.proveedor.rfc" />: ' + $root.temporal.rfc()"/>
                                    </section>
                                    <section class="col col-md-3">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.fechaEmision" />: ' + $root.temporal.fechaEmision()"/>
                                    </section>
                                    <section class="col col-md-2">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.tipo" />: ' + $root.temporal.tipo()"/>
                                    </section>
                                </div>
                                <div class="row">
                                    <section class="col col-md-6">
                                    </section>
                                    <section class="col col-md-4">
                                        <label data-bind="text: ' <spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />: ' + ($root.temporal.fechaVencimiento() ? $root.temporal.fechaVencimiento() : 'No asignada') "/>
                                    </section>
                                </div>
                                <div class="row">
                                    <section class="col col-md-6">
                                    </section>
                                    <section class="col col-md-2">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.formaPago" />: ' + $root.temporal.formadepago()"/>
                                    </section>
                                    <section class="col col-md-2">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.metodoPago" />: ' + $root.temporal.metododepago()"/>
                                    </section>
                                    <section class="col col-md-2">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.condicionPago" />: ' + $root.temporal.condiciondepago()"/>
                                    </section>
                                </div>
                                <div class="row">
                                    <table id="cfdi-conceptos" class="table">
                                        <thead>
                                        <tr>
                                            <th><spring:message code="mihabitat.facturasporpagar.cantidad"/></th>
                                            <th><spring:message code="mihabitat.facturasporpagar.unidad"/></th>
                                            <th><spring:message code="mihabitat.facturasporpagar.codigo"/></th>
                                            <th><spring:message code="mihabitat.facturasporpagar.concepto"/></th>
                                            <th><spring:message code="mihabitat.facturasporpagar.precio"/></th>
                                            <th><spring:message code="mihabitat.facturasporpagar.importe"/></th>
                                            </tr>
                                        </thead>

                                        <tbody data-bind="foreach : {data: $root.temporal.conceptos}">
                                        <tr>
                                            <td data-bind="text: cantidad"></td>
                                            <td data-bind="text: unidadDeMedida"></td>
                                            <td data-bind="text: codigo"></td>
                                            <td data-bind="text: descripcion"></td>
                                            <td data-bind="text: precioUnitario"></td>
                                            <td data-bind="text: importe"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </br></br>
                                <div class="row">
                                    <section class="col col-md-9">
                                    </section>
                                    <section class="col col-md-3">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.impuestoRetenido" />: ' + $root.temporal.impuestoRetenido()"/>
                                    </section>
                                </div>
                                <div class="row">
                                    <section class="col col-md-9">
                                    </section>
                                    <section class="col col-md-3">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.impuestoTrasladado"/>: ' + $root.temporal.impuestoTrasladado()"/>
                                    </section>
                                </div>
                                <div class="row">
                                    <section class="col col-md-9">
                                    </section>
                                    <section class="col col-md-3">
                                        <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.total"/>: ' + $root.temporal.total()"/>
                                    </section>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">
                                <spring:message code="mihabitat.botones.aceptar"/>
                            </button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </fieldset>
            <!--</form>-->
        </div>
        <!-- /.modal -->
    </div>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/pagos/movimiento-cfdiAplicado.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/detalle.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/movimiento-cargoProveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/cfdi.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/facturasporpagar-app.js?v=${project-version}"></script>

<script type="text/javascript">
    $(function () {
        var viewModel = new ListaFacturaPorPagarViewModel({
            facturasxp: ${facturasxp}
        });
        ko.applyBindings(viewModel);
        var table = dibujarTabla("#table-facturasxp");
    });
</script>
</body>