<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.otrosingresos"/> | <spring:message code="mihabitat.nombre"/></title>
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
               <a href="${pageContext.request.contextPath}/administrador/otrosingresos/lista">
                   <spring:message code="mihabitat.menu.otrosingresos"/>
               </a>
            </li>
        </ol>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.otrosingresos"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/otrosingresos/nuevo" class="btn btn-success"> <i class="fa fa-plus-circle"></i> Agregar </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-otrosIngresos" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.otroingreso.fecha" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.otroingreso.metododepago" />" />
                               </th>
                               <th class="hasinput col-md-1">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.otroingreso.conceptos.monto" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.otroingreso.cuenta" />" />
                               </th>
                               <th class="hasinput col-md-1">
                               </th>
                            </tr>                     
                            <tr>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.otroingreso.fecha" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.otroingreso.metododepago" />
                                </th>
                                <th>
                                    <spring:message code="mihabitat.otroingreso.conceptos.monto" />
                                </th>
                                <th data-hide="phone, tablet">
                                    <spring:message code="mihabitat.otroingreso.cuenta" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.otrosIngresos }">
                            <tr>
                                <td data-bind="text: fecha"></td>
                                <td data-bind="text: metodoPago.descripcion"></td>
                                <td data-bind="currency: movimientoOtroIngreso.haber, symbol: $ " style="text-align: right;"></td>
                                <td data-bind="text: movimientoOtroIngreso.cuenta.nombre"></td>
                                <td style="text-align: center;">
                                    <a href="javascript:void(0);" class="btn btn-xs btn-success" data-bind="click: $root.ver"> <i class="fa fa-pencil"></i>
                                        <spring:message code="mihabitat.botones.detalle" />
                                    </a>
                                   <%--<div class="btn-group">
                                       <a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
                                       <a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
                                       <ul class="dropdown-menu dropdown-menu-right">
                                           <li>
                                               <a href="javascript:void(0);" data-bind="click: $root.ver">
                                                   <spring:message code="mihabitat.botones.editar" />
                                               </a>
                                           </li>
                                       </ul>
                                   </div>--%>
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

    <script src="${pageContext.request.contextPath}/recursos/js/app/otrosingresos/otrosingresos-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new OtroIngresoListViewApp({
                otrosIngresos : ${otrosIngresos}
            });
            ko.applyBindings(viewModel);
            var table = dibujarTabla("#table-otrosIngresos");
        });
    </script>
</body>