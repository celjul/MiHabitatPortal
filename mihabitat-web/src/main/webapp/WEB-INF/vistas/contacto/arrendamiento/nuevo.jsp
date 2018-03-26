<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.arrendatario"/> | <spring:message code="mihabitat.nombre"/></title>
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			 </li>
			<li>
				<a href="#">
					<spring:message code="mihabitat.menu.arrendatario"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/contacto/arrendamiento/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<jsp:include page="arrendatario.jsp" />
	</div>

</body>