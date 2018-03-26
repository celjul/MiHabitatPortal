<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.cuentas"/> | <spring:message code="mihabitat.nombre"/></title>
    

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
	           <a href="${pageContext.request.contextPath}/dministrador/cuentas/lista">
	               <spring:message code="mihabitat.menu.cuentas"/>
	           </a>
	        </li>
	       
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.cuentas"/></h2>
                <div class="widget-toolbar">
                    <a class="btn btn-success" data-toggle="modal" data-target="#myModalCuenta" data-bind="click: $root.nuevo"> <i class="fa fa-plus-circle"></i> Agregar </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-cuentas" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <th class="hasinput" style="width: 15%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.noCuenta" />" />
                               </th>
                               <th class="hasinput" style="width: 20%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.nombre" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.subcuenta" />" />
                               </th>
                             	<th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.agrupadorSat" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.saldoInicial" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cuenta.estatus" />" />
                               </th>
                          	 <th class="hasinput" style="width: 5%;">
                               </th>
                            </tr> 
                   			 <tr>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cuenta.noCuenta" />
                                </th>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.cuenta.nombre" />
                                </th>
                                 <th data-hide="phone">
                                   <spring:message code="mihabitat.cuenta.subcuenta" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.cuenta.agrupadorSat" />
                                </th>
                                 <th data-hide="phone">
                                   <spring:message code="mihabitat.cuenta.saldoInicial" />
                                </th>
                                 <th data-hide="phone">
                                   <spring:message code="mihabitat.cuenta.estatus" />
                                </th>
                                 
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.cuentas , as :'c' }">
                            <tr>
                                <td data-bind="text: c.numero() + ' ' + (c.numeroHija() ? c.numeroHija() : '') + ' ' + (c.numeroNieto() ? c.numeroNieto() : '') + ' ' + (c.numeroBis() ? c.numeroBis() : '')"></td>
                                <td data-bind="text: c.nombre"></td>
                                <td data-bind="text: c.padre && c.padre.id() ? c.padre.nombre():'-' "></td>
                           		<td data-bind="text: c.agrupadorSat.nombre"></td>
                                <td data-bind="text: '$' + c.inicial()" style="text-align: right;"></td>
                           		<td data-bind="text: c.activo() ? 'Activa' : 'Inactiva'"></td>
                                <td style="text-align: center;">
                                    <a class="btn btn-success btn-xs" data-toggle="modal"
                                       data-target="#myModalCuenta" data-bind="click: $root.editar">
                                        <i class="fa fa-pencil"></i> Editar </a>

                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div id="contentModal">
        <jsp:include page="cuenta.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta-app.js"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaCuentasViewModel({
                bancos : ${bancos},
                agrupadores : ${agrupadores},
                cuentasContables : ${cuentasContables},
                condominio: {
                    id : ${condominio.id}
                },
                cuentas : ${cuentas}
            });
            ko.applyBindings(viewModel);
            var table = dibujarTabla("#table-cuentas");

            $("#cuenta-form").validate();
            $("#agrupadorSat").select2();
            $("#bancoSat").select2();
            $("#cuentaPadre").select2();
            $("#fecha").datepicker({
                maxdate: '0'
            });
            $("#fecha").datepicker('setDate',new Date());
        });
    </script>
</body>