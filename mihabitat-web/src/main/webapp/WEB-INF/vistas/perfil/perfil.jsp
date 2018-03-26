<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.usuarios"></spring:message></title>
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath}/super-administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			
		</ol>
	</div>	
	<div id="content">
		<jsp:include page="usuario.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function(){
			var viewModel = new UsuarioViewModel({
				persona : ${personaJSON},
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