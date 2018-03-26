<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${contextPath}/recursos/js/app" var="app" />


<head>
	<title><spring:message code="mihabitat.menu.condominios"/> | <spring:message code="mihabitat.nombre"/></title>
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
			   <a href="${contextPath}/super-administrador/condominios/lista">
				   <spring:message code="mihabitat.menu.condominios"/>
			   </a>
			</li>
			<li>
			   <a href="${contextPath}/super-administrador/condominios/nuevo">
				   <spring:message code="mihabitat.menu.nuevo"/>
			   </a>
			</li>
		</ol>
	</div>
	<div id="content">
		<jsp:include page="condominio.jsp" />
	</div>
	<script src="${app}/condominios/condominio.js?v=${project-version}"></script>
	<script src="${app}/direcciones/pais.js?v=${project-version}"></script>
	<script src="${app}/direcciones/estado.js?v=${project-version}"></script>
	<script src="${app}/direcciones/municipio.js?v=${project-version}"></script>
	<script src="${app}/direcciones/colonia.js?v=${project-version}"></script>
	<script src="${app}/direcciones/direccion.js?v=${project-version}"></script>
	<script src="${app}/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${app}/personas/telefono.js?v=${project-version}"></script>
	<script src="${app}/personas/email.js?v=${project-version}"></script>
	<script src="${app}/personas/persona.js?v=${project-version}"></script>
	<script src="${app}/condominios/condominio-app.js?v=${project-version}"></script>
	<script>
		$(function() {
			var viewModel = new CondominioViewModel({
				administradores : ${administradores},
				paises : ${paises}
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