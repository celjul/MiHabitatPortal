<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.gastos"/> | <spring:message code="mihabitat.nombre"/></title>
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
               <a href="${pageContext.request.contextPath}/administrador/gastos/lista">
                   <spring:message code="mihabitat.gastos"/>
               </a>
            </li>
        </ol>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.gastos"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/gastos/nuevo" class="btn btn-success"><i class="fa fa-plus-circle"></i><spring:message code="mihabitat.menu.nuevo.gastos"/></a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-gastos" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <!--<th class="hasinput col-md-1">
                                   <input type="text" class="form-control" placeholder="#" />
                               </th>-->
                               <th class="hasinput col-md-3">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.gastos.proveedor" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.gastos.fecha" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.gastos.metodo" />" />
                               </th>
                               <th class="hasinput col-md-1">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.gastos.detalles.monto" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.gastos.cuenta" />" />
                               </th>
                               <th class="hasinput col-md-1">
                               </th>
                            </tr>                     
                            <tr>
                                <!--<th data-hide="phone" style="text-align: center;">
                                   #
                                </th>-->
                                <th data-class="expand">
                                   <spring:message code="mihabitat.gastos.proveedor" />
                                </th>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.gastos.fecha" />
                                </th>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.gastos.metodo" />
                                </th>
                                <th data-hide="phone tablet">
                                    <spring:message code="mihabitat.gastos.detalles.monto" />
                                </th>
                                <th data-class="expand">
                                    <spring:message code="mihabitat.gastos.cuenta" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.gastos }">
                            <tr>
                                <!--<td data-bind="text: id" style="text-align: center;"></td>-->
                                <td data-bind="text: proveedor.nombre"></td>
                                <td data-bind="text: fecha"></td>
                                <td data-bind="text: metodoPago.descripcion"></td>
                                <td data-bind="currency: movimientoGasto.debe, symbol: $ " style="text-align: right;"></td>
                                <td data-bind="text: movimientoGasto.cuenta.nombre"></td>
                                <td style="text-align: center;">
                                   <div class="btn-group">
                                       <a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
                                       <a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
                                       <ul class="dropdown-menu">
                                           <li>
                                               <a href="javascript:void(0);" data-bind="click: $root.ver">
                                                   <spring:message code="mihabitat.botones.editar" />
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

    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/gasto-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new GastoListViewApp({
                gastos : ${gastos}
            });
            ko.applyBindings(viewModel);

            var table = dibujarTabla("#table-gastos");
        });
    </script>
</body>