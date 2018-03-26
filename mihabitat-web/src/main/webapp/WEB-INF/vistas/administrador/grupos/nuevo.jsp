<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.grupos"/> | <spring:message code="mihabitat.nombre"/></title>

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
	           <a href="${pageContext.request.contextPath}/administrador/grupos/lista">
	               <spring:message code="mihabitat.menu.grupos"/>
	           </a>
	        </li>
	        <li>
               <a href="${pageContext.request.contextPath}/administrador/grupos/nuevo">
                   <spring:message code="mihabitat.menu.nuevo"/>
               </a>
            </li>
	    </ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
	    <jsp:include page="grupo.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new GrupoCondominioViewModel({

			});
			ko.applyBindings(viewModel);

			$("#grupo-form").validate();
		});
	</script>
</body>