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
            <spring:message code="mihabitat.menu.facturasporpagar"/>
        </li>
        <li>
            <spring:message code="mihabitat.menu.cargar"/>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div class="row">
    <article class="col-sm-12 col-md-12 col-lg-12">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2"
             data-widget-colorbutton="false" data-widget-editbutton="false"
             data-widget-custombutton="false">
            <header>
                <h2 style="max-width: 100px">
                    <spring:message code="mihabitat.facturasporpagar.carga"/>
                </h2>

                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/facturasporpagar/lista"
                       class="btn btn-success">
                        <i class="fa fa-list"></i>
                        <span class="hidden-mobile">Listado</span>
                    </a>
                </div>
            </header>
        </div>
        <form id="frmCertificado" method="post" enctype="multipart/form-data" action="">
            <div class="pull-right">
                <button style="float: left;" type="button" class="btn btn-primary" data-bind="click: $root.guardar">
                    <spring:message code="mihabitat.botones.cargar"/>
                </button>
            </div>
            <input class="form-control " id="fileupload" type="file" name="file"
                   accept="text/xml" multiple
                   data-url="${pageContext.request.contextPath}/administrador/facturasporpagar/fileupload">

            <div id="progress" class="progress">
                <div class="progress-bar" role="progressbar" style="width: 0%;"></div>
            </div>

            <table id="cfdi-files" class="table">
                <thead>
                <tr>
                    <th><spring:message code="mihabitat.facturasporpagar.nombreArchivo"/></th>
                    <th><spring:message code="mihabitat.facturasporpagar.proveedor"/></th>
                    <th><spring:message code="mihabitat.facturasporpagar.fechaEmision"/></th>
                    <th><spring:message code="mihabitat.facturasporpagar.fechaVencimiento"/></th>
                    <th><spring:message code="mihabitat.facturasporpagar.total"/></th>
                    <th><spring:message code="mihabitat.proveedor.razonSocial"/></th>

                    <th style="text-align: center;">
                        <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                    </th>
                </tr>
                </thead>

                <tbody data-bind="foreach : {data: $root.cfdis}">
                <tr>
                    <td data-bind="text: archivo.nombre"></td>
                    <td data-bind="text: emisor"></td>
                    <td data-bind="text: fechaEmision"></td>
                    <td data-bind="text: proveedor.diasCredito() ? fechaVencimiento : 'No asignada' "></td>
                    <td data-bind="text: total"></td>
                    <td>
                        <a href="#" data-toggle="modal" data-target="#proveedorModal"
                           data-bind="visible: !proveedor.id(), click: $root.prepararAgregarProveedor">
                            <spring:message code="mihabitat.botones.proveedor.agregar"/>
                        </a>
                        <label data-bind="text: proveedor.razonSocial, visible: proveedor.id"/>
                    </td>

                    <td style="text-align: center;">
                        <div class="btn-group">
                            <a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
                            <a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown"
                               href="javascript:void(0);"><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="javascript:void(0);" data-bind="click: $root.remover">
                                        <spring:message code="mihabitat.botones.eliminar"/>
                                    </a>
                                </li>
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


        </form>
    </article>
</div>

<!-- Modal Agregar Proveedor -->
<div class="modal fade" id="proveedorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">

    <div class="widget-body no-padding">
        <fieldset>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel"><spring:message
                                code="mihabitat.botones.proveedor.agregar"/></h4>
                    </div>
                    <div class="modal-body">
                        <form id="proveedor-form" class="smart-form">

                            <div class="row">
                                <section class="col col-md-6">
                                    <label data-bind="text: '<spring:message code="mihabitat.proveedor.tipoPersona" />: ' + $root.proveedor.tipoPersona()"/>
                                </section>
                            </div>
                            <div class="row">
                                <section class="col col-md-6">
                                    <label data-bind="text: '<spring:message code="mihabitat.proveedor.razonSocial" />: ' + $root.proveedor.razonSocial()"/>
                                </section>
                                <section class="col col-md-4">
                                    <label data-bind="text: '<spring:message code="mihabitat.proveedor.rfc" />: ' + $root.proveedor.rfc()"/>
                                </section>
                            </div>

                            <div class="row">
                                <section class="col col-md-6">
                                    <label class="label">
                                        <span class="error-required">*</span>
                                        <spring:message code="mihabitat.proveedor.nombre"/>
                                    </label>
                                    <label class="input">
                                        <input class="form-control" type="text" name="descripcion"
                                               placeholder="<spring:message code="mihabitat.proveedor.nombre" />"
                                               required="required" maxlength="128"
                                               data-bind="value: $root.proveedor.nombre">
                                    </label>
                                </section>
                                <section class="col col-md-4" class="form-group">
                                    <label class="label">
                                        <span> </span>
                                        <spring:message code="mihabitat.proveedor.diasCredito"/>
                                    </label>
                                    <label class="input">
                                        <input class="form-control" type="number" name="diasCredito"
                                               placeholder="<spring:message code="mihabitat.proveedor.diasCredito" />"
                                               required="required" min="1" maxlength="3"
                                               data-bind="value: $root.proveedor.diasCredito">
                                    </label>
                                </section>
                            </div>
                            <div class="row">
                                <section class="col col-md-6" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.proveedor.giros"/>
                                    </label>
                                    <label class="input">
                                        <select style="width: 100%;"
                                                class="select2" name="giros" id="giros" required="required"
                                                multiple="multiple"
                                                data-bind="options: $root.giross,
															optionsText: 'descripcion',
															optionsValue: 'id',
															selectedOptions : $root.proveedor.giros,
															select2: { placeholder: 'Giro(s)' }">
                                        </select>
                                    </label>
                                </section>
                                <section class="col col-md-4" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.cuenta"/>
                                    </label>
                                    <label class="input">
                                        <select style="width: 100%"
                                                class="select2" name="cuenta" id="cuenta" required="required"
                                                data-bind="options: $root.cuenta,
													optionsCaption : 'Seleccione',
													optionsText: 'nombre',
													optionsValue: 'id',
													value: $root.proveedor.cuenta.id,
													select2: {}">
                                        </select>
                                    </label>
                                </section>
                            </div>
                        </form>

                    </div>
                    <div class="modal-footer">

                        <button type="button" class="btn btn-primary" data-bind="click: $root.guardarProveedor">
                            <spring:message code="mihabitat.botones.guardar"/>
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Cancelar
                        </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </fieldset>
        <!--</form>-->
    </div>
</div>
<!-- /.modal -->
<!-- Modal Proveedor -->


<!-- Modal Preview -->
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
                                code="mihabitat.botones.preview"/></h4>
                        <%--<fieldset data-bind="visible: !$root.regCFDI.fechaVencimiento()">
                            No existe ning?n proveedor asociado al RFC del CDFI a cargar.
                            <a href="#" data-dismiss="modal" data-toggle="modal" data-target="#proveedorModal" >
                            <spring:message code="mihabitat.botones.proveedor.agregar" /></a>
                        </fieldset>--%>
                        <div class="alert alert-danger alert-block"
                             data-bind="visible: !$root.regCFDI.fechaVencimiento()">
                            <h4 class="alert-heading">
                                <spring:message code="mihabitat.facturasporpagar.alerta"></spring:message>
                            </h4>
                            <spring:message code="mihabitat.facturasporpagar.rechazado.mensaje"/>
                            <a href="#" data-dismiss="modal" data-toggle="modal" data-target="#proveedorModal"
                               class="btn btn-primary btn-xs">
                                <spring:message code="mihabitat.botones.proveedor.agregar"/></a>
                        </div>
                    </div>
                    <div class="modal-body">
                        <form id="previewcfdi-form" class="smart-form">

                            <div class="row">
                                <section class="col col-md-6">
                                    <label data-bind="text: '<spring:message code="mihabitat.proveedor.razonSocial" />: ' + $root.regCFDI.emisor()"/>
                                </section>
                                <section class="col col-md-6">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.foliofiscal" />: ' + $root.regCFDI.uuid()"/>
                                </section>
                                <section class="col col-md-6">
                                    <label data-bind="text: '<spring:message code="mihabitat.proveedor.rfc" />: ' + $root.regCFDI.rfc()"/>
                                </section>
                                <section class="col col-md-3">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.fechaEmision" />: ' + $root.regCFDI.fechaEmision()"/>
                                </section>
                                <section class="col col-md-2">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.tipo" />: ' + $root.regCFDI.tipo()"/>
                                </section>
                            </div>

                            <div class="row">
                                <section class="col col-md-6">
                                </section>
                                <section class="col col-md-4">
                                    <label data-bind="text: ' <spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />: ' + ($root.regCFDI.fechaVencimiento() ? $root.regCFDI.fechaVencimiento() : 'No asignada') "/>
                                </section>
                            </div>
                            <div class="row">
                                <section class="col col-md-6">
                                </section>
                                <section class="col col-md-2">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.formaPago" />: ' + $root.regCFDI.formadepago()"/>
                                </section>
                                <section class="col col-md-2">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.metodoPago" />: ' + $root.regCFDI.metododepago()"/>
                                </section>
                                <section class="col col-md-2">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.condicionPago" />: ' + $root.regCFDI.condiciondepago() "/>
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

                                        <th style="text-align: center;"
                                            data-bind="visible: $root.regCFDI.fechaVencimiento()">
                                            <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                            <spring:message code="mihabitat.facturasporpagar.cuentaContable"/>
                                        </th>
                                    </tr>
                                    </thead>

                                    <tbody data-bind="foreach : {data: $root.regCFDI.conceptos}">
                                    <tr>
                                        <td data-bind="text: cantidad"></td>
                                        <td data-bind="text: unidadDeMedida"></td>
                                        <td data-bind="text: codigo"></td>
                                        <td data-bind="text: descripcion"></td>
                                        <td data-bind="text: precioUnitario"></td>
                                        <td data-bind="text: importe"></td>

                                        <td width="120px" data-bind="visible: $root.regCFDI.fechaVencimiento()">
                                            <label class="input">
                                                <select style="width: 100%"
                                                        class="select2" name="cuenta" id="cuenta" required="required"
                                                        data-bind="options: $root.cuenta,
													optionsText: 'nombre',
													optionsValue: 'id',
													value: cuenta.id,
													select2: {}">
                                                </select>
                                            </label>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            </br></br>

                            <div class="row">
                                <section class="col col-md-9">
                                </section>
                                <section class="col col-md-3">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.impuestoRetenido" />: ' + $root.regCFDI.impuestoRetenido()"/>
                                </section>
                            </div>
                            <div class="row">
                                <section class="col col-md-9">
                                </section>
                                <section class="col col-md-3">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.impuestoTrasladado" />: ' + $root.regCFDI.impuestoTrasladado()"/>
                                </section>
                            </div>
                            <div class="row">
                                <section class="col col-md-9">
                                </section>
                                <section class="col col-md-3">
                                    <label data-bind="text: '<spring:message code="mihabitat.facturasporpagar.total" />: ' + $root.regCFDI.total()"/>
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
<!-- /.Modal PreviewCFDIModal -->

<script src="${pageContext.request.contextPath}/recursos/js/app/fileupload/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/fileupload/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/fileupload/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/carga-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/cfdi.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/detalle.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/movimiento-cargoProveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/contacto-proveedor.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script type="text/javascript">
    $(function () {
        var viewModel = new CargaViewModel({
            giros: ${giros},
            cuenta: ${cuenta},
            condominio: {
                id: ${condominio.id}
            }
        });
        ko.applyBindings(viewModel);

        $("#cuenta").select2();
        var table = dibujarTabla("#cfdi-files");
    });
</script>
</body>
</html>