<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.contactos"/> | <spring:message code="mihabitat.nombre"/></title>

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
				<a href="${pageContext.request.contextPath}/administrador/contactos/lista">
					<spring:message code="mihabitat.menu.contactos"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.contactos"/></h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-contactos" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.nombre" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.emails" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.persona.telefonos" />" />
								</th>
								<th class="hasinput" style="width: 15%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.departamentos" />" />
								</th>
								<%--<th class="hasinput" style="width: 10%;">
								</th>--%>
							</tr>					  
							<tr>
								<th data-class="expand">
									<spring:message code="mihabitat.persona.nombre" />
								</th>
								<th data-hide="phone">
									<spring:message code="mihabitat.persona.emails" />
								</th>
								<th data-hide="phone">
									<spring:message code="mihabitat.persona.telefonos" />
								</th>
								<th data-hide="phone">
									<spring:message code="mihabitat.menu.departamentos" />
								</th>
								<%--<th style="text-align: center;">
									<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
								</th>--%>
							</tr>
						</thead>
						<tbody data-bind="foreach : { data: $root.contactos }">
							<tr>
								<td data-bind="text: nombre + ' ' + apellidoPaterno + ' ' + apellidoMaterno"></td>
								<td>
									<ul>
										<!-- ko foreach: { data: emails} -->
										<li data-bind="text: direccion"></li>
										<!-- /ko -->
									</ul>
								</td>
								<td>
									<ul>
										<!-- ko foreach: { data: telefonos} -->
										<li data-bind="text: numero"></li>
										<!-- /ko -->
									</ul>
								</td>
								<td>
									<ul>
										<!-- ko foreach: { data: departamentos} -->
										<li>
										<a data-bind="click: $root.actualizarTemp">
											<i class="fa fa-home txt-color-red" data-bind="visible: propietario"></i>
											<i class="fa fa-user txt-color-green" data-bind="visible: habitante"></i>
											<i class="fa fa-envelope txt-color-orangeDark" data-bind="visible: principal"></i>
											<label style="cursor:pointer;" data-bind="text: id.departamento.nombre + ' - '"></label>
											<!-- ko foreach: { data: id.departamento.grupos} -->
											<label style="cursor:pointer;" data-bind="text: descripcion + ', '"></label>
											<!-- /ko -->
										</a>
										</li>
										<!-- /ko -->
									</ul>
								</td>
								<%--<td style="text-align: center;">
									<a class="btn btn-success btn-xs" data-bind="click: $root.actualizarTemp">
										<i class="fa fa-pencil"></i> Editar </a>
								</td>--%>
							</tr>
						</tbody>
					</table>
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

	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaContactoViewModel({
				contactos : ${contactos}
			});
			ko.applyBindings(viewModel);

			var table = dibujarTabla("#table-contactos");
		});
	</script>
</body>