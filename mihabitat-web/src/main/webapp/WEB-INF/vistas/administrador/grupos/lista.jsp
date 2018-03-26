<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.grupos"/> | <spring:message code="mihabitat.nombre"/></title>
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
               <a href="${pageContext.request.contextPath}/administrador/grupos/lista">
                   <spring:message code="mihabitat.menu.grupos"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <label data-bind="'hola' + $root.grupos[0].descripcion()"></label>
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                <h2><spring:message code="mihabitat.menu.grupos"/></h2>
                <div class="widget-toolbar">
                    <a class="btn btn-success" data-toggle="modal" data-target="#myModalGrupo" data-bind="click: $root.nuevoGrupo"> <i class="fa fa-plus-circle"></i> Agregar </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">

                    <table id="table-grupos" class="table table-striped table-bordered table-hover" style="width: 100%">
                        <thead>
                            <tr>
                               <th class="hasinput" style="width: 80%;">
                                   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.grupos.descripcion" />" />
                               </th>
                               <th class="hasinput" style="width: 20%;">
                               </th>
                            </tr>                      
                            <tr>
                               <th>
                                   <spring:message code="mihabitat.grupos.descripcion" />
                                </th>
                                <th style="text-align: center;">
                                   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                </th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach : { data: $root.grupos }">
                            <tr>
                                <td data-bind="text: descripcion()"></td>
                                <td style="text-align: center;">
                                    <a class="btn btn-success btn-xs" data-toggle="modal"
                                       data-target="#myModalGrupo" data-bind="click: $root.editarGrupo">
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
        <jsp:include page="grupo.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new ListaGrupoCondominioViewModel({
                grupos : ${grupos}
            });
            ko.applyBindings(viewModel);

            var table = dibujarTabla("#table-grupos");
        });
    </script>
</body>