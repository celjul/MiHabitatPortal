<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.proveedores"/> | <spring:message code="mihabitat.nombre"/></title>
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
				<a href="${pageContext.request.contextPath}/administrador/proveedores/lista">
					<spring:message code="mihabitat.menu.proveedores"/>
				</a>
			</li>
			<li>
				<a data-bind="attr : {href : String.format('${pageContext.request.contextPath}/administrador/proveedores/actualizar/{0}', proveedor.id())}">
					<spring:message code="mihabitat.menu.editar"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="proveedor.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona-abstract.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/contacto-proveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor-app.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta-app.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ProveedorViewModel({
				cuenta : ${cuenta},
				giros: ${giros},
				catalogoEmail : ${catalogoEmail},
				catalogoTelefono : ${catalogoTelefono},
				paises : ${paises},
				proveedor : ${proveedor},

				condominio: {
					id : ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);
			$("#proveedor-form").validate();
			$("#cuenta").select2();
			$("li.contacto > a").last().click();
			$("#contacto-button").on("click", function(){
				$("li.contacto > a").last().click();
				for (var i = 0; i < $("li.contacto > a").length; i++) {
					$("#form-contacto-" + i).validate();
				}
			})
		});
	</script>
</body>