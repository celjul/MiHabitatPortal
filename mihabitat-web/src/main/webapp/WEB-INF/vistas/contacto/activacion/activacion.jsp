<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html id="extr-page">
	<head>
		<meta charset="utf-8">
		<title><spring:message code="mihabitat.nombre" /> | <spring:message code="mihabitat.titulo" /></title>
		<jsp:include page="../../../decorators/comunes/comunesEstilos.jsp"></jsp:include>
	</head>
	
	<body class="animated fadeInDown estilo-mihabitat">
		<header id="header">
			<div id="logo-group">
				<span id="logo"> <img src="${pageContext.request.contextPath}/recursos/img/logo.png" alt="<spring:message code="mihabitat.nombre" />"> </span>
			</div>
		</header>

		<div id="main" role="main">
			<div id="content" class="container">
				<div class="row">
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 hidden-xs hidden-sm">
						<h1 class="txt-color-red login-header-big"><spring:message code="mihabitat.nombre" /></h1>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
						<div>
							<jsp:include page="contacto.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="../../../decorators/comunes/comunesJS.jsp"></jsp:include>
		<script src="${pageContext.request.contextPath}/recursos/js/app/util.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/numeral.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app/activacion/activacion-app.js?v=${project-version}"></script>
		<script type="text/javascript">
			var contextPath = "${pageContext.request.contextPath}";
			$(function() {
				var viewModel = new ActivacionViewModel({
					catalogoEmail : ${catalogoEmail},
					catalogoTelefono : ${catalogoTelefono},
					contacto : ${contacto},
					enlace : ${enlace}
				});
				ko.applyBindings(viewModel);
				$("#contacto-form").validate();
			});
		</script>
		<script src="${pageContext.request.contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app.min.js?v=${project-version}"></script>
	</body>
</html>