<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
<title>
	<spring:message code="mihabitat.menu.temas" />
	|
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
				href="${pageContext.request.contextPath}/administrador/blogs/lista">
					<spring:message code="mihabitat.menu.blogs" />
			</a></li>
		</ol>
		<jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<!-- row -->
		<div class="row">

			<div class="col-sm-12">

				<div class="well">

					<table class="table table-striped table-forum" data-bind="visible: $root.blog().id!=AppConfig.catalogos.blogs.incidencias && $root.blog().id!=AppConfig.catalogos.blogs.soporte">
						<thead>
						<tr>
							<th colspan="2"><a href="#" data-bind="event: {click: function(){$root.foros();}}"> Foros </a> <span data-bind="text: ' > ' + $root.blog().nombre"></span>
								&nbsp;&nbsp;&nbsp;
								<a href="#" class="btn btn-primary" data-bind="visible: $root.puedeCrear(),event: {click: $root.nuevo}"><i class="fa fa-plus"></i> Crear Nuevo Tema</a>
							</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Publicaciones</th>
							<th class="hidden-xs hidden-sm" style="width: 200px;">Última Publicación</th>
						</tr>
						</thead>
						<tbody>

						<!-- TR -->
						<!-- ko foreach: { data: $root.temas} -->
						<tr data-bind="css: { warning: !visto }">
							<td class="text-center" style="width: 40px;" data-bind="visible: !visto"><i class="glyphicon glyphicon-pushpin fa-2x text-danger"></i></td>
							<td colspan="2" data-bind="attr: {colspan: visto?2:1}">

								<h4>
									<span class="label" data-bind="text: (((tipo==config.tipoTemaIncidencia.id)&&(tipoIncidencia.descripcion!=undefined))?tipoIncidencia.descripcion:($root.getTipo(tipo)?$root.getTipo(tipo).value:'No debe pasar')),
												css: {'label-primary' : (tipo==config.tipoTemaNormal.id),
												'label-success' : (tipo==config.tipoTemaEvento.id),
												'label-warning' : (tipo==config.tipoTemaIncidencia.id && tipoIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.proyecto')" />),
												'label-danger' : (tipo==config.tipoTemaIncidencia.id && tipoIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.incidencia')" />)}">
											</span>
									<a href="#" data-bind="text: nombre, event: {click: $root.actualizar}">
								</a>
									<small><a href="profile.html" data-bind="text: usuario.persona.nombre + ' ' +
									usuario.persona.apellidoPaterno + ' ' +
									usuario.persona.apellidoMaterno"></a> en <em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(fecha).month()] + moment(fecha).format(' D, YYYY - hh:mm a')"></em></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm">por
								<a href="javascript:void(0);" data-bind="text: ultimoPost?(ultimoPost.usuario.persona.nombre + ' ' +
								ultimoPost.usuario.persona.apellidoPaterno + ' ' + ultimoPost.usuario.persona.apellidoMaterno):''"></a>
								<br>
								<small><i data-bind="text: ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment(ultimoPost.fecha).month()] + moment(ultimoPost.fecha).format(' D, YYYY - hh:mm a')):''"></i></small>
							</td>
						</tr>
						<!-- /ko -->
						<!-- end TR -->

						</tbody>
					</table>


					<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1"
						 data-widget-editbutton="false" data-bind="visible: $root.blog().id==AppConfig.catalogos.blogs.incidencias || $root.blog().id==AppConfig.catalogos.blogs.soporte">
						<header>
				<span class="widget-icon"> <i class="fa fa-table"></i>
				</span>
							<h2>
								<spring:message code="mihabitat.menu.incidencias" />
							</h2>
						</header>
						<div>
							<div class="jarviswidget-editbox"></div>
							<div class="widget-body no-padding" data-bind="visible: $root.temas().length == 0">
								<div class="well">
									<div class="row margin-bottom-40">
										<!-- BEGIN CONTENT -->
										<div class="col-md-12 col-sm-12">

											<div class="well well-sm">
												No se han agregado Incidencias o Proyectos para este condominio.
												<a href="/administrador/blogs/7/temas/nuevo">Registra una Aquí</a>
											</div>

										</div>
									</div>
								</div>
							</div>
							<div class="widget-body no-padding" data-bind="visible: $root.temas().length != 0">

					<table class="table table-striped table-forum" >
						<thead>
						<tr>
							<th colspan="2"><a href="#" data-bind="event: {click: function(){$root.foros();}}"> Foros </a> <span data-bind="text: ' > ' + $root.blog().nombre"></span>
								&nbsp;&nbsp;&nbsp;
								<a href="#" class="btn btn-primary" data-bind="visible: $root.puedeCrear(),event: {click: $root.nuevo}"><i class="fa fa-plus"></i> Crear Nuevo Tema</a>
							</th>

						</tr>
						</thead>
					</table>
					<table id="table-incidencias" class="display projects-table table table-striped table-hover" cellspacing="0" width="100%"
						   data-bind="visible: $root.blog().id==AppConfig.catalogos.blogs.incidencias || $root.blog().id==AppConfig.catalogos.blogs.soporte">
						<thead>
						<tr>
							<th data-class="expand"></th>
							<th width="300px">Proyecto/Incidencia</th>
							<th>Status</th>
							<th data-hide="phone"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Iniciado por</th>
							<th data-hide="phone"><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> Inicio</th>
							<th style="display: none;"></th>
							<th data-hide="phone"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Actualizado por</th>
							<th data-hide="phone"><i class="fa fa-fw fa-calendar text-muted hidden-md hidden-sm hidden-xs"></i> Última actualización</th>
							<th style="display: none;"></th>

						</tr>
						</thead>
						<tbody data-bind="foreach : { data: $root.temas }">
						<tr >
							<td></td>
							<td>
												<span class="label label-primary" data-bind="text: (tipoIncidencia?tipoIncidencia.descripcion:'Tema'),
												css: {
												'label-warning' : (tipoIncidencia?(tipoIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.proyecto')"/>):''),
												'label-danger' : (tipoIncidencia?(tipoIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.incidencia')"/>):'')}"> </span>
								</span> &nbsp;<a href="#" data-bind="text: nombre?nombre:('Nuevo Proyecto/Incidencia'), click: $root.actualizar"> </a>
							</td>
							<td>
												<span class="label" data-bind="text: posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.descripcion:'',
													css: {'label-warning' : (posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.solicitada')"/>:''),
													'label-info' : (posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.pendiente')"/>:''),
													'label-primary' : (posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.repetida')"/>:''),
													'label-danger' : (posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.cancelado')"/>:''),
													'label-success' : (posts[posts.length-1].estatusIncidencia?posts[posts.length-1].estatusIncidencia.id==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.concluido')"/>:'')}"> </span>
							</td>
							<td data-bind="text: (posts[0].usuario.persona.nombre + ' ' + posts[0].usuario.persona.apellidoPaterno + ' ' + posts[0].usuario.persona.apellidoMaterno)"></td>
							<td data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(posts[0].fecha).month()] + moment(posts[0].fecha).format(' D, YYYY - hh:mm a')"></td>
							<td data-bind="text: posts[0].fecha" style="display: none;"></td>
							<%--<td data-bind="text: moment(avances[0].fecha).format('DD/MM/YY - hh:mm a')"></td>--%>
							<td data-bind="text: (posts[posts.length-1].usuario.persona.nombre + ' ' + posts[posts.length-1].usuario.persona.apellidoPaterno + ' ' + posts[posts.length-1].usuario.persona.apellidoMaterno)"></td>
							<td data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(posts[posts.length-1].fecha).month()] + moment(posts[posts.length-1].fecha).format(' D, YYYY - hh:mm a')"></td>
							<td data-bind="text: posts[posts.length-1].fecha" style="display: none;"></td>

						</tr>
						</tbody>
					</table>
						</div>
					</div>
				</div>

				</div>
			</div>

		</div>

		<!-- end row -->

	</div>
	<script type="text/javascript">
		var config = {
			tipoTemaNormal : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaNormal', value:'Tema'},
			tipoTemaEvento : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaEvento', value:'Evento'},
			tipoTemaIncidencia : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia', value:'Incidencia o Proyecto'},
			tipoIncidencia : {
				proyecto : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.proyecto')" />,
				incidencia : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.incidencia')" />
			}
		};
	</script>
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
			src="${pageContext.request.contextPath}/recursos/js/app/blogs/blogs-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaTemaViewModel({
				usuario : ${user},
				blog : ${blog},
				rol: 'administrador'
			});
			ko.applyBindings(viewModel);
			var table = dibujarTabla("#table-incidencias",{
				"aaSorting" : [[8,'desc']],
				"aoColumns": [
					null, null, null, null, { "iDataSort": 5 }, null, null, { "iDataSort": 8 }, null
				]
			});
		});
	</script>
</body>