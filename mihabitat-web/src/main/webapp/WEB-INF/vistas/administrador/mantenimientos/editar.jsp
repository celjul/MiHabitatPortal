<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.mantenimientos"/> | <spring:message code="mihabitat.nombre"/></title>

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
				<a href="${pageContext.request.contextPath}/administrador/mantenimientos/lista">
					<spring:message code="mihabitat.menu.mantenimientos"/>
				</a>
			</li>
			<li>
				<a data-bind="attr: { href: String.format('{0}/administrador/mantenimientos/actualizar/{1}', '${pageContext.request.contextPath}', mantenimiento.id())}">
					<spring:message code="mihabitat.menu.editar"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="mantenimiento.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new MantenimientoCondominioViewModel({
				mantenimiento : ${mantenimiento}
			});
			ko.applyBindings(viewModel);
		});
	</script>
</body>