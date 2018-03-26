<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.facturasporpagar"/> | <spring:message code="mihabitat.nombre"/></title>

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
				<a href="${pageContext.request.contextPath}/administrador/facturasporpagar/lista">
					<spring:message code="mihabitat.facturasporpagar"/>
				</a>
			</li>
			<li>
				<a data-bind="attr : {href : String.format('${pageContext.request.contextPath}/administrador/facturasporpagar/actualizar/{0}', facturasxp.id())}">
					<spring:message code="mihabitat.menu.editar"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="facturasporpagar.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/contacto-proveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta-app.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-app.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/movimiento-cargoProveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/cfdi.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/detalle.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/facturasporpagar-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new CfdiViewModel({
				cuenta : ${cuenta},
				proveedores : ${proveedores},
				metodosPago : ${metodosPago},
				condominio: {
					id : ${condominio.id}
				},
				facturasxp : ${facturasxp}
			});
			ko.applyBindings(viewModel);

			$("#facturasporpagar-form").validate();

			$("#proveedor").select2();
			$("#cuenta").select2();
			$("#fechaRecepcion").datepicker({
			});
			$("#fechaVencimiento").datepicker({
				minDate: '0'
			});

		});
	</script>
</body>