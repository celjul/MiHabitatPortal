<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${contextPath}/recursos/js/plugin" var="plugin" />
<c:set value="${contextPath}/recursos/js/app" var="app" />

<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title default="miHabitat"/></title>
		<jsp:include page="../comunes/comunesEstilos.jsp" />
		<decorator:head />
	</head>
	<body class="desktop-detected pace-done estilo-mihabitat  fixed-header fixed-navigation">
		<header id="header">
			<div id="logo-group">
				<span id="logo"> <img src="${contextPath}/recursos/img/logo.png" alt="<spring:message code="mihabitat.nombre" />"> </span>
			</div>
			<div class="pull-right">
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" data-action="toggleMenu" title="<spring:message code="mihabitat.menu.colapasar" />"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<ul id="mobile-profile-img" class="header-dropdown-list hidden-xs padding-5">
					<li class="">
						<a href="#" class="dropdown-toggle no-margin userdropdown"> 
							<img src="${contextPath}/recursos/img/avatars/male.png" alt="${persona.usuario.user}" class="online" />  
						</a>
					</li>
				</ul>
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="<c:url value="/j_spring_security_logout"/>" title="<spring:message code="mihabitat.menu.salir" />" data-action="userLogout" data-logout-msg="<spring:message code="mihabitat.menu.salir.confirmar" />"><i class="fa fa-sign-out"></i></a> </span>
				</div>
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<form action="#" class="header-search pull-right">
					<input id="search-fld"  type="text" name="param" placeholder="<spring:message code="mihabitat.menu.buscar" />">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
					<a href="javascript:void(0);" id="cancel-search-js" title="<spring:message code="mihabitat.menu.buscar.cancelar" />"><i class="fa fa-times"></i></a>
				</form>
				<div id="fullscreen" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0);" data-action="launchFullscreen" title="<spring:message code="mihabitat.menu.pantallacompleta" />"><i class="fa fa-arrows-alt"></i></a> </span>
				</div>
			</div>
		</header>
		<aside id="left-panel">
			<div class="login-info">
				<span>
					<a href="javascript:void(0);" id="show-shortcut">
						<img src="${contextPath}/recursos/img/avatars/male.png" alt="${persona.usuario.user}" class="online" /> 
						<span>
							${persona.nombre} ${persona.apellidoPaterno}
						</span>
					</a> 
				</span>
			</div>
			<nav>
				<ul>
					<li class="active">
						<a href="${contextPath}/super-administrador/inicio"><i class="fa fa-lg fa-fw fa-magic"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.inicio"/></span></a>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-child"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.usuarios"/></span></a>
						<ul>
							<li>
								<a href="${contextPath}/super-administrador/usuarios/nuevo"><spring:message code="mihabitat.menu.nuevo"/></a>
							</li>
							<li>
								<a href="${contextPath}/super-administrador/usuarios/lista"><spring:message code="mihabitat.menu.lista"/></a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#"><i class="fa fa-lg fa-fw fa-building"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.condominios"/></span></a>
						<ul>
							<li>
								<a href="${contextPath}/super-administrador/condominios/nuevo"><spring:message code="mihabitat.menu.nuevo"/></a>
							</li>
							<li>
								<a href="${contextPath}/super-administrador/condominios/lista"><spring:message code="mihabitat.menu.lista"/></a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
			<span class="minifyme" data-action="minifyMenu"> 
				<i class="fa fa-arrow-circle-left hit"></i> 
			</span>
		</aside>
		<div id="main" role="main">
			<jsp:include page="../comunes/comunesJS.jsp" />
			<script>
				var PathConfig = {
					usuarios : "${contextPath}/super-administrador",
					personas : "${contextPath}/personas",
					condominios : "${contextPath}/super-administrador/condominios",
					timeout : 5000
				}
			</script>
			<script src="${contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
			<script src="${contextPath}/recursos/js/app.min.js?v=${project-version}"></script>
			<decorator:body />
		</div>
		<div class="page-footer">
			<div class="row">
				<div class="col-xs-12 col-sm-6">
					<span class="txt-color-white"><spring:message code="mihabitat.nombre"/> | <spring:message code="mihabitat.titulo"/></span>
				</div>
				<div class="scroll-top-wrapper">
				 	<span class="scroll-top-inner">
						<i class="fa fa-2x fa-arrow-circle-up"></i>
					</span>				
				</div>
			</div>
		</div>
	</body>
</html>
