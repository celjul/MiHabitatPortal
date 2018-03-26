<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
		   prefix="sec"%>
<head>
<title><spring:message code="mihabitat.menu.directorio" /> |
	<spring:message code="mihabitat.nombre" /></title>

<%--<link
	href="${pageContext.request.contextPath}/recursos/css/portfolio.css"
	rel="stylesheet">--%>
	<link href="${pageContext.request.contextPath}/recursos/css/footable.core.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li><a
				href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio" />
			</a></li>
			<li><a
				href="${pageContext.request.contextPath}/directorio/lista">
					<spring:message code="mihabitat.menu.directorio" />
			</a></li>
		</ol>
		<jsp:include page="../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">

		<%--<div class="row">

			<!-- ko foreach: { data: $root.directorioRegistros} -->
			<div class="col-xs-12 col-sm-6 col-md-3">
				<div class="well well-sm bg-color-white">
					<h3 class="text-primary" data-bind="text: titulo"></h3>
					<address style="min-height: 100px">
						<strong data-bind="visible: telefono()">Teléfono(s): </strong>
						<a data-bind="attr: {href: 'tel:' + telefono()}, text: telefono(), visible: telefono()"></a>
						<br data-bind="visible: telefono()">
						<strong data-bind="visible: email()">Email: </strong>
						<a data-bind="attr: {href: 'mailto:' + email()}, text: email(), visible: email()"></a>
						<br data-bind="visible: email()">
						<strong data-bind="visible: pagina()">Página Web: </strong>
						<a data-bind="attr: {href: 'http://' + pagina()}, text: pagina(), visible: pagina()"></a>
						<br data-bind="visible: pagina()">
						<strong data-bind="visible: direccion()">Dirección: </strong>
						<span data-bind="text: direccion(), visible: direccion()"></span><br>
					</address>
				</div>
			</div>
			<!-- /ko -->
		</div>--%>

				<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2><spring:message code="mihabitat.menu.directorio"/></h2>
						<sec:authorize access="hasRole('Administrador')">
							<div class="widget-toolbar">
								<a class="btn btn-success" data-toggle="modal" data-target="#myModalDirectorio" data-bind="click: $root.nuevoDirectorio"> <i class="fa fa-plus-circle"></i>
									<span class="hidden-mobile hidden-tablet">
										<spring:message code="mihabitat.botones.agregar" />
									</span>
								</a>
							</div>
						</sec:authorize>
						<ul class="nav nav-tabs pull-right in" id="myTab">
							<li class="active">
								<a data-toggle="tab" href="#s1"><i class="fa fa-plus-square"></i> <span class="hidden-mobile hidden-tablet"
																									 data-bind="text: $root.getTipoDirectorio(AppConfig.catalogos.directorio.tipos.emergencias).descripcion">  </span></a>
							</li>
							<li>
								<a data-toggle="tab" href="#s2"><i class="fa fa-info-circle"></i> <span class="hidden-mobile hidden-tablet"
																										   data-bind="text: $root.getTipoDirectorio(AppConfig.catalogos.directorio.tipos.atencion).descripcion"> </span></a>
							</li>
							<li>
								<a data-toggle="tab" href="#s3"><i class="fa fa-shopping-cart"></i> <span class="hidden-mobile hidden-tablet"
																										   data-bind="text: $root.getTipoDirectorio(AppConfig.catalogos.directorio.tipos.servicios).descripcion"> </span></a>
							</li>
							<li>
								<a data-toggle="tab" href="#s4"><i class="fa fa-building"></i> <span class="hidden-mobile hidden-tablet"
																										   data-bind="text: $root.getTipoDirectorio(AppConfig.catalogos.directorio.tipos.condominio).descripcion"> </span></a>
							</li>
						</ul>
					</header>
					<div>
						<div class="jarviswidget-editbox">
						</div>

						<div class="widget-body no-padding">
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade active in" id="s1">
									<div class="table-responsive">
									<table id="table-directorio-emergencias" class="table table-striped table-bordered table-hover" style="width: 100%">
										<thead>
										<tr>
											<th class="hasinput" style="width: 30%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.telefonos" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.email" />" />
											</th>
											<th class="hasinput" style="width: 10%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.pagina" />" />
											</th>
											<th class="hasinput" style="width: 20%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.direccion" />" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th style="width: 10%;">
												</th>
											</sec:authorize>
										</tr>
										<tr>
											<th >
												<spring:message code="mihabitat.directorio.titulo" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.telefonos" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.email" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.pagina" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.direccion" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th >

												</th>
											</sec:authorize>
										</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.directorioRegistros }">
										<!-- ko if: (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias) -->
										<tr>
											<%--<td >
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio)}"></label>
											</td>--%>
											<td >
												<span data-bind="text: titulo"></span>
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio),
														'label-primary': tipo && (tipo.id() == 0)}"></label>
											</td>
											<td >
												<a data-bind="attr: {href: 'tel:' + telefono()}, text: telefono()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'mailto:' + email()}, text: email()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'http://' + pagina()}, text: pagina()" target="_blank"></a>
											</td>
											<td >
												<a data-bind="attr: {href: 'http://maps.google.com/?q=' + direccion()}, text: direccion()" target="_blank"></a>
											</td>
											<sec:authorize access="hasRole('Administrador')">
												<td style="text-align: center;">
													<a class="btn btn-success btn-xs" data-toggle="modal"
													   data-target="#myModalDirectorio" data-bind="click: $root.editarDirectorio, visible: puedeEditar()">
														<i class="fa fa-pencil"></i> <span class="hidden-mobile"><spring:message code="mihabitat.botones.editar" /></span> </a>
												</td>
											</sec:authorize>
										</tr>
										<!-- /ko-->
										</tbody>
									</table>
									</div>
								</div>
								<div class="tab-pane fade" id="s2">
									<div class="table-responsive">
									<table id="table-directorio-atencion" class="table table-striped table-bordered table-hover" style="width: 100%">
										<thead>
										<tr>
											<%--<th class="hasinput" style="width: 5%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>--%>
											<th class="hasinput" style="width: 30%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.telefonos" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.email" />" />
											</th>
											<th class="hasinput" style="width: 10%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.pagina" />" />
											</th>
											<th class="hasinput" style="width: 20%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.direccion" />" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th style="width: 10%;">
												</th>
											</sec:authorize>
										</tr>
										<tr>
											<%--<th>
												<spring:message code="mihabitat.directorio.titulo" />
											</th>--%>
											<th>
												<spring:message code="mihabitat.directorio.titulo" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.telefonos" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.email" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.pagina" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.direccion" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th >

												</th>
											</sec:authorize>
										</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.directorioRegistros }">
										<!-- ko if: (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion) -->
										<tr>
											<%--<td >
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio)}"></label>
											</td>--%>
											<td >
												<span data-bind="text: titulo"></span>
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio),
														'label-primary': tipo && (tipo.id() == 0)}"></label>
											</td>
											<td >
												<a data-bind="attr: {href: 'tel:' + telefono()}, text: telefono()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'mailto:' + email()}, text: email()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'http://' + pagina()}, text: pagina()" target="_blank"></a>
											</td>
											<td >
												<a data-bind="attr: {href: 'http://maps.google.com/?q=' + direccion()}, text: direccion()" target="_blank"></a>
											</td>
											<sec:authorize access="hasRole('Administrador')">
												<td style="text-align: center;">
													<a class="btn btn-success btn-xs" data-toggle="modal"
													   data-target="#myModalDirectorio" data-bind="click: $root.editarDirectorio, visible: puedeEditar()">
														<i class="fa fa-pencil"></i> <span class="hidden-mobile"><spring:message code="mihabitat.botones.editar" /></span> </a>
												</td>
											</sec:authorize>
										</tr>
										<!-- /ko-->
										</tbody>
									</table>
									</div>
								</div>
								<div class="tab-pane fade" id="s3">
									<div class="table-responsive">
									<table id="table-directorio-servicios" class="table table-striped table-bordered table-hover" style="width: 100%">
										<thead>
										<tr>
											<%--<th class="hasinput" style="width: 5%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>--%>
											<th class="hasinput" style="width: 30%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.telefonos" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.email" />" />
											</th>
											<th class="hasinput" style="width: 10%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.pagina" />" />
											</th>
											<th class="hasinput" style="width: 20%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.direccion" />" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th style="width: 10%;">
												</th>
											</sec:authorize>
										</tr>
										<tr>
											<%--<th >
												<spring:message code="mihabitat.directorio.titulo" />
											</th>--%>
											<th>
												<spring:message code="mihabitat.directorio.titulo" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.telefonos" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.email" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.pagina" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.direccion" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th >

												</th>
											</sec:authorize>
										</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.directorioRegistros }">
										<!-- ko if: (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios) -->
										<tr>
											<%--<td >
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio)}"></label>
											</td>--%>
											<td >
												<span data-bind="text: titulo"></span>
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio),
														'label-primary': tipo && (tipo.id() == 0)}"></label>
											</td>
											<td >
												<a data-bind="attr: {href: 'tel:' + telefono()}, text: telefono()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'mailto:' + email()}, text: email()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'http://' + pagina()}, text: pagina()" target="_blank"></a>
											</td>
											<td >
												<a data-bind="attr: {href: 'http://maps.google.com/?q=' + direccion()}, text: direccion()" target="_blank"></a>
											</td>
											<sec:authorize access="hasRole('Administrador')">
												<td style="text-align: center;">
													<a class="btn btn-success btn-xs" data-toggle="modal"
													   data-target="#myModalDirectorio" data-bind="click: $root.editarDirectorio, visible: puedeEditar()">
														<i class="fa fa-pencil"></i> <span class="hidden-mobile"><spring:message code="mihabitat.botones.editar" /></span> </a>
												</td>
											</sec:authorize>
										</tr>
										<!-- /ko-->
										</tbody>
									</table>
									</div>
								</div>
								<div class="tab-pane fade" id="s4">
									<div class="table-responsive">
									<table id="table-directorio-condominio" class="table table-striped table-bordered table-hover" style="width: 100%">
										<thead>
										<tr>
											<%--<th class="hasinput" style="width: 5%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>--%>
											<th class="hasinput" style="width: 35%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.titulo" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.telefonos" />" />
											</th>
											<th class="hasinput" style="width: 15%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.email" />" />
											</th>
											<th class="hasinput" style="width: 10%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.pagina" />" />
											</th>
											<th class="hasinput" style="width: 20%;">
												<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.directorio.direccion" />" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th style="width: 5%;">
												</th>
											</sec:authorize>
										</tr>
										<tr>
											<%--<th>
												<spring:message code="mihabitat.directorio.titulo" />
											</th>--%>
											<th>
												<spring:message code="mihabitat.directorio.titulo" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.telefonos" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.email" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.pagina" />
											</th>
											<th>
												<spring:message code="mihabitat.directorio.direccion" />
											</th>
											<sec:authorize access="hasRole('Administrador')">
												<th >

												</th>
											</sec:authorize>
										</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.directorioRegistros }">
										<!-- ko if: (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio) || (tipo.id() == 0) -->
										<tr>
											<%--<td >
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio)}"></label>
											</td>--%>
											<td >
												<span data-bind="text: titulo"></span>
												<label class="label" data-bind="text: tipo.descripcion(),
													css: {'label-danger': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.emergencias),
														'label-warning': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.atencion),
														'label-info': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.servicios),
														'label-success': tipo && (tipo.id() == AppConfig.catalogos.directorio.tipos.condominio),
														'label-primary': tipo && (tipo.id() == 0)}"></label>
											</td>
											<td >
												<a data-bind="attr: {href: 'tel:' + telefono()}, text: telefono()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'mailto:' + email()}, text: email()"></a>
											</td>
											<td>
												<a data-bind="attr: {href: 'http://' + pagina()}, text: pagina()" target="_blank"></a>
											</td>
											<td >
												<a data-bind="attr: {href: 'http://maps.google.com/?q=' + direccion()}, text: direccion()" target="_blank"></a>
											</td>
											<sec:authorize access="hasRole('Administrador')">
												<td style="text-align: center;">
													<a class="btn btn-success btn-xs" data-toggle="modal"
													   data-target="#myModalDirectorio" data-bind="click: $root.editarDirectorio, visible: puedeEditar()">
														<i class="fa fa-pencil"></i> <span class="hidden-mobile"><spring:message code="mihabitat.botones.editar" /></span> </a>
												</td>
											</sec:authorize>
										</tr>
										<!-- /ko-->
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


	</div>
	<div id="contentModal">
		<jsp:include page="directoriomodal.jsp" />
	</div>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/app/portfolio.js?v=${project-version}"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/app/directorios/directorioregistro.js?v=${project-version}"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/app/directorios/directorioregistro-app.js?v=${project-version}"></script>
	<script
			src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/fooplugins/footable.js"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new DirectorioRegistroViewModel({
				registrosDirectorio : ${registrosDirectorio},
				tiposDirectorio : ${tiposDirectorio}
			});
			ko.applyBindings(viewModel);


			dibujarTabla("#table-directorio-atencion");
			dibujarTabla("#table-directorio-servicios");
			dibujarTabla("#table-directorio-condominio");
			dibujarTabla("#table-directorio-emergencias");
		});
	</script>
</body>