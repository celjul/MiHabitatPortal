<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html id="extr-page">
<head>
<meta charset="utf-8">
<title><spring:message code="mihabitat.nombre" /> | <spring:message
		code="mihabitat.titulo" /></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/smartadmin-production.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/smartadmin-skins.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/your_style.css">

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/recursos/img/landing/favicon.ico"
	type="image/x-icon">
<link rel="icon"
	href="${pageContext.request.contextPath}/recursos/img/landing/favicon.ico"
	type="image/x-icon">

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

<link rel="apple-touch-icon"
	href="${pageContext.request.contextPath}/recursos/img/splash/sptouch-icon-iphone.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-ipad.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-iphone-retina.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-ipad-retina.png">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/ipad-landscape.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/ipad-portrait.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/iphone.png"
	media="screen and (max-device-width: 320px)">
</head>

<body class="animated fadeInDown">
	<header id="header">
		<div id="logo-group">
			<span id="logo"> <img
				src="${pageContext.request.contextPath}/recursos/img/logo.png"
				alt="<spring:message code="mihabitat.nombre" />">
			</span>
		</div>
	</header>

	<div id="main" role="main">
		<div id="content" class="container">
			<div class="row">
				<div
					class="col-xs-12 col-sm-12 col-md-7 col-lg-8 hidden-xs hidden-sm">
					<h1 class="txt-color-red login-header-big">
						<spring:message code="mihabitat.nombre" />
					</h1>
					<div class="hero">
						<div class="pull-left login-desc-box-l">
							<h4 class="paragraph-header">
								<spring:message code="mihabitat.titulo" />
							</h4>
						</div>
						<img
							src="${pageContext.request.contextPath}/recursos/img/demo/iphoneview.png"
							class="pull-right display-image" alt="" style="width: 210px">
					</div>
					<%--<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<h5 class="about-heading">
								<spring:message code="mihabitat.info.panel.uno.titulo" />
							</h5>
							<p>
								<spring:message code="mihabitat.info.panel.uno.descripcion" />
							</p>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<h5 class="about-heading">
								<spring:message code="mihabitat.info.panel.dos.titulo" />
							</h5>
							<p>
								<spring:message code="mihabitat.info.panel.dos.descripcion" />
							</p>
						</div>
					</div>--%>

				</div>
				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
					<div class="well no-padding">
						<form action="" id="form-forget-password"
							class="smart-form client-form">
							<header>
								<spring:message code="mihabitat.login.password.recuperar" />
								</label>
							</header>

							<fieldset>

								<section>
									<label class="label"><spring:message
											code="mihabitat.login.email" /></label> <label class="input">
										<i class="icon-append fa fa-envelope"></i> <input type="email"
										id="email-recuperar-password">
									</label>
								</section>
							</fieldset>
							<footer>
								<button type="button" class="btn btn-primary" id="btnPassword">
									<spring:message code="mihabitat.login.password.enviar" />
								</button>
								<button type="button" class="btn btn-primary"
									onclick="regresarInicio()">
									</i>
									<spring:message code="mihabitat.login.password.regresar" />
								</button>
							</footer>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/pace/pace.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/jquery-2.0.2.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-3.3.0.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/bootstrap/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.es.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/masked-input/jquery.maskedinput.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/notification/SmartNotification.min.js"></script>
	<script src="/recursos/js/plugin/blockui/jquery.blockui.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/util.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/login/ingreso-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		$(function() {
			var viewModel = new IngresoViewModel();
			ko.applyBindings(viewModel);

			$("#form-forget-password").validate();
		});
		function regresarInicio() {
			location.href = contextPath + "/login";
		}
	</script>

	<script
		src="${pageContext.request.contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app.min.js?v=${project-version}"></script>
</body>
</html>