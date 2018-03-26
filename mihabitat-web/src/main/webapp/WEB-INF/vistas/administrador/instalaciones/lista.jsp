<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
<title><spring:message code="mihabitat.menu.instalaciones" /> |
	<spring:message code="mihabitat.nombre" /></title>

<link
	href="${pageContext.request.contextPath}/recursos/css/portfolio.css"
	rel="stylesheet">
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li><a
				href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio" />
			</a></li>
			<li><a
				href="${pageContext.request.contextPath}/administrador/instalaciones/lista">
					<spring:message code="mihabitat.menu.instalaciones" />
			</a></li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1"
			data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.instalaciones" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<div class="well">
						<div class="row margin-bottom-40">
							<!-- BEGIN CONTENT -->
							<div class="col-md-12 col-sm-12">
								<div class="well well-sm" data-bind="visible: $root.instalaciones().length == 0">
									No se han agregaro Instalaciones o �reas de uso Com�nn para este condominio.
									<a href="/administrador/instalaciones/nuevo">Registre una Instalaci�n</a>
								</div>

								<div class="content-page">
									<div class="filter-v1">
										<div class="row mix-grid thumbnails"
											data-bind="foreach : { data: $root.instalaciones }">
											<div class="col-md-3 col-sm-4 col-xs-12 mix mix_all"
												style="display: block; opacity: 1;">
												<div class="mix-inner">
													<img alt="" style="width: 100%; ;height: 200px;position:relative;display: block;"
														data-bind="attr: { src: imagen?('data:'+imagen.tipo+';base64,'+imagen.bytes):'${pageContext.request.contextPath}/recursos/img/demo/no-imagen.png'}"
														class="img-responsive">
													
													<div class="mix-details">
														
														<h4 data-bind="text: nombre+(!activo?'(Inactivo)':'')"></h4>
														<a class="mix-link" data-bind="click: $root.actualizar"
															title="<spring:message code="mihabitat.botones.editar" />"
															><i class="fa fa-pencil"></i></a>
														<%--<a
															data-rel="fancybox-button" title="<spring:message code="mihabitat.instalacion.reservacion.reservar" />"
															href="../../assets/frontend/pages/img/works/img1.jpg"
															class="mix-preview fancybox-button"><i
															class="fa fa-calendar-o"></i></a>--%>
														<a class="mix-preview fancybox-button" data-bind="click: $root.reservar"
														   title="<spring:message code="mihabitat.instalacion.reservacion.reservar" />"
																><i class="fa fa-calendar-o"></i></a>
														<p style="text-align: justify;"
															data-bind="text: descripcion"></p>
													</div>
												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/portfolio.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaInstalacionViewModel({
				instalaciones : ${instalaciones}
			});
			ko.applyBindings(viewModel);

			var table = dibujarTabla("#table-instalaciones");
		});
	</script>
</body>