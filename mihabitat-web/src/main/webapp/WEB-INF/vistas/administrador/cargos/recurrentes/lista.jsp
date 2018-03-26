<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.cargos.recurrentes"/> | <spring:message code="mihabitat.nombre"/></title>
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
               <a href="${pageContext.request.contextPath}/administrador/cargos-recurrentes/lista">
                   <spring:message code="mihabitat.menu.cargos.recurrentes"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.cargos.recurrentes"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/cargos-recurrentes/nuevo" class="btn btn-success"> <i class="fa fa-plus-circle"></i> Programar Nuevo Cargo Recurrente </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-cargos" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                                <th class="hasinput" style="width: 15%;">
                                    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.concepto" />" />
                                </th>
                                <th class="hasinput" style="width: 15%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.tipo" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.recurrente.dia" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.descuento" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.recargo" />" />
                               </th>
                               <th class="hasinput" style="width: 15%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.recurrente.meses" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                               </th>
                            </tr>                      
                            <tr>
                                <th data-class="expand">
                                    <spring:message code="mihabitat.cargo.concepto" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.tipo" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.recurrente.dia" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.descuento" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.recargo" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cargo.recurrente.meses" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.cargos }">
                            <tr>
                                <td data-bind="text: concepto"></td>
                                <td data-bind="text: tipo.descripcion"></td>
                                <td data-bind="text: primerDiaMes?'Primer día de mes':(ultimoDiaMes?'Último día de mes':dia)"></td>
                                <td data-bind="text: (descuento && descuento.id) ? (!descuento.porcentaje?'$':'') + numeral(descuento.monto).format('0,0.00') +
                                    (descuento.porcentaje?'%':'') + ' Pagando antes de ' + descuento.dia + ' días'  : 'No'"></td>
                                <td data-bind="text: (recargo && recargo.id) ? (!recargo.porcentaje?'$':'') + numeral(recargo.monto).format('0,0.00') +
                                    (recargo.porcentaje?'%':'') + ' Pagando despues de ' + recargo.dia + ' días'  : 'No'"></td>
                                <td>
                                    	<!-- ko foreach: meses -->
                                        <div style="margin-top: 5px; display: inline-block;">
                                            <span class="label-primary"
                                                  style="display: inline; padding: .2em .6em .3em; font-size: 75%; font-weight: 700; line-height: 1;
                                                            color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline;
                                                            border-radius: .25em;"
                                                  data-bind="text: $data"></span>
                                        </div>
										<!-- /ko -->
                                </td>
                                <td style="text-align: center;">
                                    <a class="btn btn-success btn-xs" data-bind="click: $root.actualizar">
                                        <i class="fa fa-pencil"></i> Editar </a>
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

    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recurrentes/cargo-recurrente-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaCargoRecurrenteViewModel({
                cargos : ${cargos}
            });
            ko.applyBindings(viewModel);

            var table = dibujarTabla("#table-cargos");
        });
    </script>
</body>