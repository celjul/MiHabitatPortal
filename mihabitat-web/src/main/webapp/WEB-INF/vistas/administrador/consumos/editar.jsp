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
			<li>
				<a href="${pageContext.request.contextPath}/administrador/consumos/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="consumo.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new TipoConsumoViewModel({
				cuentas: ${cuentas},
				catalogoUnidad : ${catalogoUnidad},
				condominio : {
					id : ${condominio.id}
				},
				consumo : ${consumo},
				catalogoTipoConsumo : ${catalogoTipoConsumo}
			});
			ko.applyBindings(viewModel);

			$("#unidad").select2();
			$("#unidadLectura").select2();
			$("#unidadConversion").select2();
			$("#cuenta").select2();
			$("#consumo-form").validate();
		});
	</script>
</body>

