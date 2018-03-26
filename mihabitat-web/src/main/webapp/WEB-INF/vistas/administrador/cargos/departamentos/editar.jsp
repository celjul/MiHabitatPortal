<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <title><spring:message code="mihabitat.menu.cargos"/> | <spring:message code="mihabitat.nombre"/></title>
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
    <jsp:include page="cargo-departamento.jsp"/>
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
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento-app.js?v=${project-version}"></script>
<script type="text/javascript">
    $(function () {
        var viewModel = new CargoDepartamentoViewModel({
            cuentas: ${cuentas},
            consumos: ${consumos},
            condominio: {
                id: ${condominio.id}
            },
            cargo: ${cargo},
            catalogoInteres: ${catalogoInteres}
        });
        ko.applyBindings(viewModel);

        $("#tipo, #consumo, #cuenta").select2();
        $("#fecha, #descuento-fecha, #recargo-fecha").datepicker({
            //minDate: '0'
        });
        $("#fecha-descuento, #fecha-recargo").datepicker({
            maxDate: new Date(),
            minDate: $("#fecha").datepicker("getDate")
        });

        $("#cargo-form, #descuento-form, #recargo-form").validate();

        $('#table-movimientos').dataTable({
            "info": false,
            "paging": false,
            "searching": false
        });
    });
</script>
</body>