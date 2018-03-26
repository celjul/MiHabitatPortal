<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html id="extr-page">
	<head>
		<meta charset="utf-8">
		<title><spring:message code="mihabitat.nombre" /> | <spring:message code="mihabitat.titulo" /></title>
		<jsp:include page="../../../decorators/comunes/comunesEstilos.jsp"></jsp:include>
	</head>
	
	<body class="animated fadeInDown">
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
							<spring:message code="mihabitat.enlace.caducado" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../../../decorators/comunes/comunesJS.jsp"></jsp:include>
		<script src="${pageContext.request.contextPath}/recursos/js/app/util.js?v=${project-version}"></script>

		<script src="${pageContext.request.contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/app.min.js?v=${project-version}"></script>
	</body>
</html>