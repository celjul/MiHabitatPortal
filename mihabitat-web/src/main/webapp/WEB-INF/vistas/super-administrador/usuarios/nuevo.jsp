<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${contextPath}/recursos/js/app" var="app" />

<head>
	<title><spring:message code="mihabitat.menu.usuarios"></spring:message></title>
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${contextPath}/super-administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${contextPath}/super-administrador/usuarios/lista">
					<spring:message code="mihabitat.menu.usuarios"/>
				</a>
			</li>
			<li>
				<a href="${contextPath}/super-administrador/usuarios/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>
		</ol>
	</div>	
	<div id="content">
		<jsp:include page="usuario.jsp" />
	</div>
	<script src="${app}/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${app}/personas/telefono.js?v=${project-version}"></script>
	<script src="${app}/personas/email.js?v=${project-version}"></script>
	<script src="${app}/personas/persona.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario-app.js?v=${project-version}"></script>
	<script>
		$(function(){
			var viewModel = new UsuarioViewModel({
				roles: ${roles},
				catalogoEmail : ${catalogoEmail},
				catalogoTelefono : ${catalogoTelefono}
			});
			ko.applyBindings(viewModel);
			$("#usuario-form").validate();
			$("#roles").select2();
		});
	</script>
</body>