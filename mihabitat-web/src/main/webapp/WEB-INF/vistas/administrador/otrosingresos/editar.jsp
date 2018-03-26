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
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="otroingreso.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-gasto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/otrosingresos/conceptoingreso.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/otrosingresos/otrosingresos.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/otrosingresos/otrosingresos-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new OtroIngesoViewApp({
                bancosCajas : ${bancosCajas},
                cuentasIngresos : ${cuentasIngresos},
                metodosPago : ${metodosPago},
                condominio : {
                    id: ${condominio.id}
                },
                otroIngreso : ${otroIngreso}
            });
            ko.applyBindings(viewModel);

            $("#fecha").datepicker({
                maxDate: '0'
            });
            $("#otroIngreso-form").validate();
        });
    </script>
</body>