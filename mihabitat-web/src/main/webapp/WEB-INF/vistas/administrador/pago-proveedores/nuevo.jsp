<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.facturasporpagar"/> | <spring:message code="mihabitat.nombre"/></title>
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
				<a href="${pageContext.request.contextPath}/administrador/pago-proveedores/lista">
					<spring:message code="mihabitat.menu.facturasporpagar"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/pago-proveedores/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>

		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="pago-proveedores.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-bootstrap.min.js"></script>

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
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/pagos/movimiento-cfdiAplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/movimiento-cargoProveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/cfdi.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/detalle.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/facturasporpagar/facturasporpagar-app.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/pagos/pago.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/pagos/pagoproveedores-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new PagoProveedoresViewModel({
				cuenta : ${cuenta},
				proveedores : ${proveedores},
				metodosPago : ${metodosPago},
				condominio: {
					id : ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);
			$("#pagproveedores-form").validate();
			$("#proveedor").select2();
			$("#cuenta").select2();
			$("#fechaPago").datepicker({
				maxDate: '0'
			});
		});
	</script>
</body>