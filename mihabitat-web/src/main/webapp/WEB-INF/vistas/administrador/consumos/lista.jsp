<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.consumos"/> | <spring:message code="mihabitat.nombre"/></title>

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
               <a href="${pageContext.request.contextPath}/administrador/consumos/lista">
                   <spring:message code="mihabitat.menu.consumos"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>    
    <div id="content">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.consumos"/></h2>
                <div class="widget-toolbar">
                    <a class="btn btn-success" data-toggle="modal" data-target="#myModalConsumo" data-bind="click: $root.nuevoConsumo"> <i class="fa fa-plus-circle"></i> Agregar </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <table id="table-consumo" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <th class="hasinput" style="width: 15%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.consumo.nombre" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.consumo.unidad" />" />
                               </th>
                               <th class="hasinput" style="width: 30%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.consumo.cuenta" />" />
                               </th>      
                                <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.consumo.tipo" />" />
                               </th>
                               <th class="hasinput" style="width: 10%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.consumo.prorrateo.costo.unidad" />" />
                               </th>                         
                               <th class="hasinput" style="width: 10%;">
                               </th>
                            </tr>                      
                            <tr>
                                <th data-class="expand">
                                   <spring:message code="mihabitat.consumo.nombre" />
                                </th>
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.consumo.unidad" />
                                </th> 
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.consumo.cuenta" />
                                </th> 
                                <th data-hide="phone">
                                   <spring:message code="mihabitat.consumo.tipo" />
                                </th>
                                <th>
                                   <spring:message code="mihabitat.consumo.prorrateo.costo.unidad" />
                                </th>                          
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.consumos }">
                            <tr>
                                <td data-bind="text: nombre"></td>
                                <td data-bind="text: unidad.descripcion"></td>
                                <td data-bind="text: cuenta.nombre"></td>  
                                <td data-bind="text: catalogoTipoConsumo.descripcion"></td>  
                                <td data-bind="text: '$' + costoPorUnidad()" style="text-align: right;"></td>
                                <td style="text-align: center;">
                                    <a class="btn btn-success btn-xs" data-toggle="modal"
                                       data-target="#myModalConsumo" data-bind="click: $root.editarConsumo">
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
        <jsp:include page="consumo.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaConsumoViewModel({
                consumos : ${consumos},
                cuentas: ${cuentas},
                catalogoUnidad : ${catalogoUnidad},
                condominio : {
                    id : ${condominio.id}
                },
                catalogoTipoConsumo : ${catalogoTipoConsumo}
            });
            ko.applyBindings(viewModel);

            var table = dibujarTabla("#table-consumo");
            $("#unidad").select2();
            $("#cuenta").select2();
            $("#consumo-form").validate();
        });
    </script>
</body>