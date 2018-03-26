<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
<title><spring:message code="mihabitat.menu.blogs" /> |
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
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<!-- row -->
		<div class="row">

			<div class="col-sm-12">

				<div class="well">

					<table class="table table-striped table-forum">
						<thead>
						<tr>
							<th colspan="2">MiHábitat News</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Temas</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Publicaciones</th>
							<th class="hidden-xs hidden-sm" style="width: 200px;">Última Publicación</th>
						</tr>
						</thead>
						<tbody>

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[0].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-globe fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[0].nombre, event:{click: function(){$root.actualizar($root.blogs()[0])}}">

								</a>
									<small data-bind="text: $root.blogs()[0].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[0].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[0].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[0].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[0].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[0].ultimoPost?($root.blogs()[0].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[0].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[0].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[0].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[0].ultimoPost.fecha).month()] + moment($root.blogs()[0].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[1].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-microphone fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[1].nombre, event:{click: function(){$root.actualizar($root.blogs()[1])}}">

								</a>
									<small data-bind="text: $root.blogs()[1].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[1].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[1].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[1].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[1].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[1].ultimoPost?($root.blogs()[1].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[1].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[1].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[1].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[1].ultimoPost.fecha).month()] +
											moment($root.blogs()[1].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[2].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-pencil fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[2].nombre, event:{click: function(){$root.actualizar($root.blogs()[2])}}">

								</a>
									<small data-bind="text: $root.blogs()[2].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[2].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[2].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[2].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[2].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[2].ultimoPost?($root.blogs()[2].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[2].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[2].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[2].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[2].ultimoPost.fecha).month()] +
											moment($root.blogs()[2].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						</tbody>
					</table>

					<table class="table table-striped table-forum">
						<thead>
						<tr>
							<th colspan="2">Foros del Condominio</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Temas</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Publicaciones</th>
							<th class="hidden-xs hidden-sm" style="width: 200px;">Última Publicación</th>
						</tr>
						</thead>
						<tbody>
						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[3].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-bullhorn fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[3].nombre, event:{click: function(){$root.actualizar($root.blogs()[3])}}">

								</a>
									<small data-bind="text: $root.blogs()[3].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[3].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[3].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[3].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[3].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[3].ultimoPost?($root.blogs()[3].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[3].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[3].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[3].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[3].ultimoPost.fecha).month()] + moment($root.blogs()[3].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->
						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[4].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-building fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[4].nombre, event:{click: function(){$root.actualizar($root.blogs()[4])}}">

								</a>
									<small data-bind="text: $root.blogs()[4].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[4].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[4].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[4].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[4].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[4].ultimoPost?($root.blogs()[4].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[4].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[4].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[4].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[4].ultimoPost.fecha).month()] + moment($root.blogs()[4].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[5].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-clipboard fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[5].nombre, event:{click: function(){$root.actualizar($root.blogs()[5])}}">
								</a>
									<small data-bind="text: $root.blogs()[5].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[5].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[5].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[5].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[5].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[5].ultimoPost?($root.blogs()[5].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[5].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[5].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[5].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[5].ultimoPost.fecha).month()] + moment($root.blogs()[5].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->
						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[6].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-bomb fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[6].nombre, event:{click: function(){$root.actualizar($root.blogs()[6])}}">
								</a>
									<small data-bind="text: $root.blogs()[6].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[6].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[6].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[6].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[6].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[6].ultimoPost?($root.blogs()[6].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[6].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[6].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[6].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[6].ultimoPost.fecha).month()] + moment($root.blogs()[6].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->
						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[7].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-dollar fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[7].nombre, event:{click: function(){$root.actualizar($root.blogs()[7])}}">
								</a>
									<small data-bind="text: $root.blogs()[7].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[7].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[7].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[7].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[7].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[7].ultimoPost?($root.blogs()[7].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[7].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[7].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[7].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[7].ultimoPost.fecha).month()] + moment($root.blogs()[7].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						</tbody>
					</table>


					<table class="table table-striped table-forum">
						<thead>
						<tr>
							<th colspan="2">Soporte</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Temas</th>
							<th class="text-center hidden-xs hidden-sm" style="width: 100px;">Publicaciones</th>
							<th class="hidden-xs hidden-sm" style="width: 200px;">Última Publicación</th>
						</tr>
						</thead>
						<tbody>

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[8].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-book fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[8].nombre, event:{click: function(){$root.actualizar($root.blogs()[8])}}">
								</a>
									<small data-bind="text: $root.blogs()[8].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[8].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[8].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[8].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[8].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[8].ultimoPost?($root.blogs()[8].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[8].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[8].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[8].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[8].ultimoPost.fecha).month()] + moment($root.blogs()[8].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[9].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-question-circle fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[9].nombre, event:{click: function(){$root.actualizar($root.blogs()[9])}}">
								</a>
									<small data-bind="text: $root.blogs()[9].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[9].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[9].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[9].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[9].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[9].ultimoPost?($root.blogs()[9].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[9].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[9].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[9].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[9].ultimoPost.fecha).month()] + moment($root.blogs()[9].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						<!-- TR -->
						<tr data-bind="css: { warning: !$root.blogs()[10].visto }">
							<td class="text-center" style="width: 40px;"><i class="fa fa-user-md fa-2x text-muted"></i></td>
							<td>
								<h4><a href="#" data-bind="text: $root.blogs()[10].nombre, event:{click: function(){$root.actualizar($root.blogs()[10])}}">
								</a>
									<small data-bind="text: $root.blogs()[10].descripcion"></small>
								</h4>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[10].temas.length"></a>
							</td>
							<td class="text-center hidden-xs hidden-sm">
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[10].conteoPost"></a>
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: !$root.blogs()[10].ultimoPost">
							</td>
							<td class="hidden-xs hidden-sm" data-bind="visible: $root.blogs()[10].ultimoPost">por
								<a href="javascript:void(0);" data-bind="text: $root.blogs()[10].ultimoPost?($root.blogs()[10].ultimoPost.usuario.persona.nombre + ' '
									+ $root.blogs()[10].ultimoPost.usuario.persona.apellidoPaterno + ' ' + $root.blogs()[10].ultimoPost.usuario.persona.apellidoMaterno
									):'Desconocido'"></a>
								<br>
								<small><i data-bind="text: $root.blogs()[10].ultimoPost?(AppConfig.catalogos.meses.descripcion.split(',')[moment($root.blogs()[10].ultimoPost.fecha).month()] + moment($root.blogs()[10].ultimoPost.fecha).format(' D, YYYY - hh:mm a')):'Desconocida'"></i></small>
							</td>
						</tr>
						<!-- end TR -->

						</tbody>
					</table>


				</div>
			</div>

		</div>

		<!-- end row -->

		<!-- row -->

		<div class="row">

			<!-- a blank row to get started -->

		</div>

		<!-- end row -->

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
			src="${pageContext.request.contextPath}/recursos/js/app/blogs/blogs-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaBlogViewModel({
				usuario : ${user},
				blogs : ${blogs},
				rol: 'administrador'
			});
			ko.applyBindings(viewModel);
		});
	</script>
</body>