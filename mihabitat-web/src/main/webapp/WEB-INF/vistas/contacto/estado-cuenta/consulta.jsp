<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<head>
    <title><spring:message code="mihabitat.menu.estadocuenta"/> | <spring:message code="mihabitat.nombre"/></title>
    <link href="${pageContext.request.contextPath}/recursos/css/footable.core.css" rel="stylesheet" type="text/css">
    <sec:authentication property="authorities" var="roles" scope="page" />
</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${condominio.id}">
                   <spring:message code="mihabitat.menu.inicio"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="../../estado-cuenta/estado-cuenta.jsp" />
    </div>

    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/fooplugins/footable.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/estado-cuenta/estado-cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/estado-cuenta/estado-cuenta-app.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/aviso-cobro/aviso-cobro.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/aviso-cobro/aviso-cobro-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new EstadoCuentaViewModel({
                periodos : ${periodos},
                roles : '${roles}',
                contacto : ${contacto}
            });
            ko.applyBindings(viewModel);

            $("#fecha").datepicker();
            $("#fechaInicio").datepicker();
            $("#fechaFin").datepicker();
        });
    </script>
</body>