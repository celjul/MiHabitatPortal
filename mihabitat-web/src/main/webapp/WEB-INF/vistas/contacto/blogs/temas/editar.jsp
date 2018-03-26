<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.instalaciones"></spring:message> | <spring:message code="mihabitat.nombre"/></title>
	<link rel="canonical" href="http://codepen.io/mrsafraz/pen/uIrwC">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/bootstrap-datetimepicker.min.css" type="text/css" media="screen" title="no title" charset="utf-8">
</head>
<body>
	<div id="ribbon">
        <ol class="breadcrumb">
           <li>
				<a href="${pageContext.request.contextPath}/administrador/inicio">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/blogs/lista">
					<spring:message code="mihabitat.menu.blogs"/>
				</a>
			</li>
        </ol>
		<jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
    </div>
	<div id="content">
        <jsp:include page="../../../blogs/tema.jsp" />
    </div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dropzone/dropzone.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/locale/locales.js" type="text/javascript" charset="utf-8"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/adjunto.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/post.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/temaRevisado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/tema.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/blog.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/blogs/blogs-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function(){
			var viewModel = new TemaViewModel({
				tema : ${tema},
				condominio: {
					id : ${condominio.id}
				},
				tipos : ${tipos},
				estatus : ${estatus},
				usuario: ${user},
				rol: 'contacto'
			});
			ko.applyBindings(viewModel);
			viewModel.temaLeido();
			$("#tema-form").validate();
			$("#post-form").validate();
		});
	</script>
</body>