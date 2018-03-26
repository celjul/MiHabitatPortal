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
            <li>
               <a href="${pageContext.request.contextPath}/administrador/gastos/nuevo">
                   <spring:message code="mihabitat.menu.nuevo"/>
               </a>
            </li>
        </ol>
    </div>
    <div id="content">
        <jsp:include page="gasto.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-gasto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/gasto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/gasto-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new GastoViewApp({
                proveedores : ${proveedores},
                bancosCajas : ${bancosCajas},
                egresos : ${egresos},
                metodosPago : ${metodosPago},
                condominio : {
                    id: ${condominio.id}
                }
            });
            ko.applyBindings(viewModel);

            $("#proveedor").select2();
            $("#fecha").datepicker({
                maxDate: '0'
            });
            $("#gasto-form").validate();
        });
    </script>
</body>