<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.cargos.recurrentes"/> | <spring:message code="mihabitat.nombre"/></title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.css">

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
            <li>
               <a href="${pageContext.request.contextPath}/administrador/cargos-recurrentes/nuevo">
                   <spring:message code="mihabitat.menu.nuevo"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="cargo-recurrente.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recurrentes/descuento-recurrente.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recurrentes/recargo-recurrente.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recurrentes/cargo-recurrente.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recurrentes/cargo-recurrente-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new CargoRecurrenteViewModel({
                instalaciones : ${instalaciones},
                mantenimientos : ${mantenimientos},
                departamentos : ${departamentos},
                cuentas: ${cuentas},
                catalogoMes : ${catalogoMes},
                catalogoCargo : ${catalogoCargo},
                condominio : {
                    id : ${condominio.id}
                },
                cargo : ${cargo},
                catalogoInteres : ${catalogoInteres}
            });
            ko.applyBindings(viewModel);

            /*$("#tipo").select2();*/
            $("#instalacion").select2();
            $("#cuenta").select2();
            $("#cargo-form").validate();

            var id = "#table-departamentos";

            $(id).dataTable( {
                "info":           false,
                "paging":         false,
                "searching":      false

            } );

            viewModel.cargo.dibujarTablaDepartamentos();
        });
    </script>
</body>