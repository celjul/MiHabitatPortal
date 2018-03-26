<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${contextPath}/recursos/js/plugin" var="plugin" />
<c:set value="${contextPath}/recursos/js/app" var="app" />

<head>
	<title><spring:message code="mihabitat.menu.usuarios"/> | <spring:message code="mihabitat.nombre"/></title>
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${contextPath}/super-administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${contextPath}/super-administrador/usuarios/lista">
					<spring:message code="mihabitat.menu.usuarios"/>
				</a>
			</li>
		</ol>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.usuarios"/></h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-usuarios" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
								<th class="hasinput" style="width: 5%;">
									<input type="text" class="form-control" placeholder="#" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.nombre" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.emails" />" />
								</th>
<!-- 								<th class="hasinput" style="width: 15%;"> -->
<%-- 									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.telefonos" />" /> --%>
<!-- 								</th> -->
								<th class="hasinput" style="width: 10%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.usuarios.user" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.usuarios.roles" />" />
								</th>
								<th class="hasinput" style="width: 10%;"></th>
							</tr>
							<tr>
								<th data-hide="phone" style="text-align: center;">
									#
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.persona.nombre" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.persona.emails" />
								</th>
<!-- 								<th data-class="expand"> -->
<%-- 									<spring:message code="mihabitat.persona.telefonos" /> --%>
<!-- 								</th> -->
								<th data-class="expand">
									<spring:message code="mihabitat.usuarios.user" />
								</th>
								<th data-hide="phone,tablet">
									<spring:message code="mihabitat.usuarios.roles" />
								</th>
								<th style="text-align: center;">
									<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
								</th>
							</tr>
						</thead>
						<tbody data-bind="foreach : {data: $root.usuarios }">
							<tr>
								<td data-bind="text: id" style="text-align: center;"></td>
								<td data-bind="text: persona.nombre + ' ' + persona.apellidoPaterno"></td>
								<td>
									<ul>
										<li data-bind="text: email"></li>
									</ul>
								</td>
<!-- 								<td> -->
<!-- 									<ul data-bind="foreach : { data : telefonos }"> -->
<!-- 										<li data-bind="text: numero"></li> -->
<!-- 									</ul> -->
<!-- 								</td> -->
								<td data-bind="text: user"></td>
								<td>
									<ul data-bind="foreach : { data : roles }">
										<li data-bind="text: descripcion"></li>
									</ul>
								</td>
								<td style="text-align: center;">
									<div class="btn-group">
										<a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
										<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);" 
											data-bind="attr: {id: 'menu-' + id}">
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu">
											<li>
												<a href="javascript:void(0);" data-bind="click: $root.actualizar, attr: {id: 'link-edit-' + id}">
													<spring:message code="mihabitat.botones.actualizar" />
												</a>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="modales/usuario.jsp" />
	<script src="${plugin}/datatables/jquery.dataTables.min.js"></script>
	<script src="${plugin}/datatables/dataTables.colVis.min.js"></script>
	<script src="${plugin}/datatables/dataTables.tableTools.min.js"></script>
	<script src="${plugin}/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${plugin}/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${plugin}/datatables/dataTables.impl.js"></script>

	<script src="${app}/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${app}/personas/telefono.js?v=${project-version}"></script>
	<script src="${app}/personas/email.js?v=${project-version}"></script>
	<script src="${app}/personas/persona.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario-app.js?v=${project-version}"></script>
	<script>
		$(function() {
			var viewModel = new ListaUsuariosViewModel({
				usuarios : ${usuarios},
				roles: ${roles},
				catalogoEmail : ${catalogoEmail},
				catalogoTelefono : ${catalogoTelefono}
			});
			ko.applyBindings(viewModel);
			var table = dibujarTabla("#table-usuarios");
			$("div.toolbar").html('<div class="text-right"><button type="button" id="btn-nuevo-usuario" data-toggle="modal" data-target="#modal-usuario" class="btn btn-primary" ><spring:message code="mihabitat.botones.nuevo" /> </button></div>');
		});
	</script>
</body>