<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.transferencia.lista"/> | <spring:message code="mihabitat.nombre"/></title>

</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/administrador/transferencias/nuevo">
                   <spring:message code="mihabitat.transferencias"/>
               </a>
             </li>
            <li>
               <a href="${pageContext.request.contextPath}/administrador/transferencias/lista">
                   <spring:message code="mihabitat.transferencia.lista"/>
               </a>
            </li>
        </ol>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.transferencias"/></h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/transferencias/nuevo" class="btn btn-success"> <i class="fa fa-plus"></i> <span class="hidden-mobile"> <spring:message code="mihabitat.transferencia.nuevo"/>  </span> </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-transferencias" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.transferencias.fecha" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.transferencias.metodoTransferencia" />" />
                               </th>
                               <th class="hasinput col-md-1">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.transferencias.cuenta.retiro" />" />
                               </th>
                               <th class="hasinput col-md-2">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.transferencias.cuenta.deposito" />" />
                               </th>
                                <th class="hasinput col-md-2">
                                    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.transferencias.monto" />" />
                                </th>
                               <th class="hasinput col-md-1">
                               </th>
                            </tr>                     
                            <tr>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.transferencias.fecha" />
                                </th>

                                <th data-hide="phone">
                                   <spring:message code="mihabitat.transferencias.metodoTransferencia" />
                                </th>
                                <th>
                                    <spring:message code="mihabitat.transferencias.cuenta.retiro" />
                                </th>
                                <th data-hide="phone, tablet">
                                    <spring:message code="mihabitat.transferencias.cuenta.deposito" />
                                </th>
                                <th data-hide="phone, tablet">
                                    <spring:message code="mihabitat.transferencias.monto" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.transferencias }">
                            <tr>
                                <td data-bind="text: fecha"></td>
                                <td data-bind="text: metodoTransferencia.descripcion"></td>
                                <%--<td data-bind="currency: retiro.cuenta.nombre, symbol: $ " style="text-align: right;"></td>--%>
                                <td data-bind="text: retiro.cuenta.nombre"></td>
                                <td data-bind="text: deposito.cuenta.nombre"></td>
                                <td style="text-align: center" data-bind="text: '$' + numeral(monto).format('0,0.00')"></td>
                                <td style="text-align: center;">
                                   <div class="btn-group">

                                       <a class="btn btn-success btn-xs" data-toggle="modal"
                                          data-target="#myModalMantenimiento" href="javascript:void(0);" data-bind="click: $root.ver">
                                           <i class="fa fa-pencil"></i> <spring:message code="mihabitat.botones.detalle" /> </a>


                                   <%--<a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>--%>
                                       <%--<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>--%>
                                       <ul class="dropdown-menu">
                                           <%--<li>
                                               <a href="javascript:void(0);" data-bind="click: $root.ver">
                                                   <spring:message code="mihabitat.botones.editar" />
                                               </a>
                                           </li>--%>
                                           <li>



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
    <script src="${pageContext.request.contextPath}/recursos/js/app/transferencias/transferencia-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new TransferenciaListViewApp({
                transferencias : ${transferencias}
            });
            ko.applyBindings(viewModel);

            var table = dibujarTabla("#table-transferencias");
        });
    </script>
</body>