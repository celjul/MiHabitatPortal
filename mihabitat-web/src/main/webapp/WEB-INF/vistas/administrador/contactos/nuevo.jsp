<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.contactos"/> | <spring:message code="mihabitat.nombre"/></title>

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
				<a href="${pageContext.request.contextPath}/administrador/contactos/lista">
					<spring:message code="mihabitat.menu.contactos"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/contactos/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="contacto.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new ContactoViewModel({
				mantenimientos : ${mantenimientos},
				grupos: ${grupos},
				catalogoEmail : ${catalogoEmail},
				catalogoTelefono : ${catalogoTelefono},
				condominio : {
					id: ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);

			$("#mantenimiento").select2();
			$("#grupos").select2();
			$("li.departamento > a").last().click();
			$("#departamento-button").on("click", function(){
				$("li.departamento > a").last().click();
				for (var i = 0; i < $("li.departamento > a").length; i++) {
					$("#form-departamento-" + i).validate();
				}
			})
		});
	</script>
</body>