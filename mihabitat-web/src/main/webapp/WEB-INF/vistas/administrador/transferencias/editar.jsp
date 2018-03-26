<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.transferencias.detalle"/> | <spring:message code="mihabitat.nombre"/></title>
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
               <a href="${pageContext.request.contextPath}/administrador/transferencias/editar">
                   <spring:message code="mihabitat.transferencia"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="transferencia.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/gastos/movimiento-gasto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/transferencias/transferencia-app.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/transferencias/transferencia.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new  TransferenciaViewApp({
                bancosCajas : ${bancosCajas},
                metodosTransferencia : ${metodosTransferencia},
                condominio : {
                    id: ${condominio.id}
                },
                transferencia : ${transferencia}
            });
            ko.applyBindings(viewModel);

            $("#fecha").datepicker({
                maxDate: '0'
            });
            $("#transferencia-form").validate();
            $("#cuentaRetiro").select2();
            $("#cuentaDeposito").select2();
        });
    </script>
</body>