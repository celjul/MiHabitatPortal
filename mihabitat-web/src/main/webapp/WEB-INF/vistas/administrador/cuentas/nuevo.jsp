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
	           <a href="${pageContext.request.contextPath}/administrador/grupos/lista">
	               <spring:message code="mihabitat.menu.cuentas"/>
	           </a>
	        </li>
	        <li>
               <a href="${pageContext.request.contextPath}/administrador/grupos/nuevo">
                   <spring:message code="mihabitat.menu.nuevo"/>
               </a>
            </li>
	    </ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
	    <jsp:include page="cuenta.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta-app.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new CuentaViewModel({
				bancos : ${bancos},
				agrupadores : ${agrupadores},
				cuentasContables : ${cuentasContables},
				condominio: {
					id : ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);

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