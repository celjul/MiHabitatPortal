<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.condominios"></spring:message> | <spring:message code="mihabitat.nombre"/></title>
</head>
<body>
	<div id="ribbon">
        <ol class="breadcrumb">
           <li>
				<a href="${pageContext.request.contextPath}/super-administrador/inicio">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/super-administrador/condominios/lista">
					<spring:message code="mihabitat.menu.condominios"/>
				</a>
			</li>
        </ol>
    </div>
	<div id="content">
        <jsp:include page="condominio.jsp" />
    </div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function(){
			var viewModel = new CondominioViewModel({
				condominio : ${condominioEditar},
				paises : ${paises},
				administradores : ${administradores}
			});
			ko.applyBindings(viewModel);
			$("#condominio-form").validate();
			$("#administradores").select2();
			$("#condominio-pais").select2();
			$("#condominio-estado").select2();
			$("#condominio-municipio").select2();
			$("#condominio-colonia").select2();
		});
	</script>
</body>