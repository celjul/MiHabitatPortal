<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.cargos.departamentos"/> | <spring:message code="mihabitat.nombre"/></title>
    

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
               <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/lista">
                   <spring:message code="mihabitat.menu.cargos"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.cargos"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/nuevo" class="btn btn-success"> <i class="fa fa-plus-circle"></i> <spring:message code="mihabitat.botones.agregar.cargo"/> </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-cargos" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                                <th class="hasinput" style="width: 10%;">
                                    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.fecha" />" />
                                </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.departamento" />" />
                               </th>
                                <th class="hasinput" style="width: 10%;">
                                    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.departamento.etiquetas" />" />
                                </th>
                               <th class="hasinput" style="width: 35%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.concepto" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.montoOriginal" />" />
                               </th>
                               <th class="hasinput" style="width: 5%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.recargos" />" />
                               </th>
                               <th class="hasinput" style="width: 5%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.descuentos" />" />
                               </th>
                               <th class="hasinput" style="width: 5%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.pagado" />" />
                               </th>
                                <th class="hasinput" style="width: 5%;">
                                    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.saldopendiente" />" />
                                </th>
                               <th class="hasinput" style="width: 5%;">
                               </th>
                            </tr>                      
                            <tr>
                                <th >
                                    <spring:message code="mihabitat.cargo.fecha" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.departamento" />
                                </th>
                                <th data-hide="phone">
                                    <spring:message code="mihabitat.departamento.etiquetas" />
                                </th>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.cargo.concepto" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.montoOriginal" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.pago.recargos" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.pago.descuentos" />
                                </th>
                                 <th data-hide="phone">
                                   <spring:message code="mihabitat.pago.pagado" />
                                </th>
                                <th data-hide="phone">
                                    <spring:message code="mihabitat.pago.saldopendiente" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.cargos, as :'c' }">
                            <tr data-bind="css: {danger: cargo.cancelado()}">
                                <td data-bind="text: (moment(fecha(), 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
                                <td data-bind="text: departamento.nombre"></td>
                                <td data-bind="foreach: {data: departamento.grupos}">
                                    <span data-bind="text: descripcion"></span>
                                </td>
                                <td >
                                    <span data-bind="text: tipo.descripcion() + ' | ' + concepto()"></span>
                                    <span class="label label-danger" data-bind="visible: cargo.cancelado()">
                                        <spring:message code="mihabitat.cargo.cancelado" />
                                    </span>
                                </td>
                                <td data-bind="currency: totalMonto, symbol: $ " style="text-align: right;"></td>
                                <td data-bind="currency: totalRecargos, symbol: $ " style="text-align: right;"></td>
                                <td data-bind="currency: totalDescuentos, symbol: $ " style="text-align: right;"></td>
                                <td data-bind="currency: totalPagado, symbol: $ " style="text-align: right;"></td>
                                <td style="text-align: right;">
                                    <strong data-bind="currency: saldoPendiente, symbol: $ "></strong>
                                </td>
                                <td style="text-align: center;">
                                   <div class="btn-group">
                                       <button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
                                                <i class="fa fa-cog"></i> <span class="caret"></span>
                                       </button>
                                       <ul class="dropdown-menu dropdown-menu-right">
                                           <li>
                                               <a href="javascript:void(0);" data-bind="click: $root.actualizar">
                                                   <spring:message code="mihabitat.botones.editar" />
                                               </a>
                                           </li>
                                           <li data-bind="visible: agrupador && agrupador.id">
                                               <a href="javascript:void(0);" data-bind="click: $root.actualizarSimilares">
                                                   <spring:message code="mihabitat.botones.editar.similares" />
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
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/departamento-aux.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaCargoDepartamentoViewModel();
            ko.applyBindings(viewModel);
        });
    </script>
</body>