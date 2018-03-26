<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.departamentos"/> | <spring:message code="mihabitat.nombre"/></title>
	

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
			   <a href="${pageContext.request.contextPath}/administrador/departamentos/lista">
				   <spring:message code="mihabitat.menu.departamentos"/>
			   </a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.departamentos"/></h2>
				<div class="widget-toolbar">
					<a href="#" data-bind="event : { click: function() { $root.imprimir('pdf') } }" class="btn btn-primary btn-xs"> <i class="fa fa-file-pdf-o"></i> PDF  </a>
					<a href="#" data-bind="event : { click: function() { $root.imprimir('xlsx') } }" class="btn btn-primary btn-xs"> <i class="fa fa-file-excel-o"></i> XLS </a>
					<a href="#" data-bind="event : { click: function() { $root.imprimir('csv') } }" class="btn btn-primary btn-xs"> <i class="fa fa-file-excel-o"></i> CSV </a>
					<a href="#" data-bind="event : { click: function() { $root.imprimir('txt') } }" class="btn btn-primary btn-xs"> <i class="fa fa-file-text-o"></i> TXT </a>
					<a href="${pageContext.request.contextPath}/administrador/departamentos/nuevo" class="btn btn-success"> <i class="fa fa-plus-circle"></i> Agregar </a>
				</div>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-departamentos" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
							   <th class="hasinput" style="width: 20%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.departamento.nombre" />" />
							   </th>
							   <th class="hasinput" style="width: 20%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.mantenimientos" />" />
							   </th>
							   <th class="hasinput" style="width: 20%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.grupos" />" />
							   </th>
							   <th class="hasinput" style="width: 30%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.contactos" />" />
							   </th>
							   <th class="hasinput" style="width: 10%;">
							   </th>
							</tr>					  
							<tr>
								<th data-class="expand">
								   <spring:message code="mihabitat.departamento.nombre" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.mantenimiento" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.grupos" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.contactos" />
								</th>
								<th style="text-align: center;">
								   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
								</th>
							</tr>
						</thead>
						<tbody data-bind="foreach : { data: $root.departamentos }">
							<tr>
								<td data-bind="text: nombre"></td>
								<td data-bind="text: mantenimiento.descripcion + ' - $' + numeral(mantenimiento.monto).format('0,0.00')"></td>
								<td>
									<ul>
										<!-- ko foreach: { data: grupos} -->
										<li data-bind="text: descripcion"></li>
										<!-- /ko -->
									</ul>
								</td>
								<td>
									<ul>
										<!-- ko foreach: { data: contactos} -->
										<li >
											<span data-bind="text: id.contacto.nombre + ' ' + id.contacto.apellidoPaterno + ' ' + id.contacto.apellidoMaterno"></span>
											<i class="fa fa-home txt-color-red" data-bind="visible: propietario"></i>
											<i class="fa fa-user txt-color-green" data-bind="visible: habitante"></i>
											<i class="fa fa-envelope txt-color-orangeDark" data-bind="visible: principal"></i>
										</li>
										<!-- /ko -->
									</ul>
								</td>
								<td style="text-align: center;">
									<a class="btn btn-success btn-xs" data-bind="click: $root.actualizar">
										<i class="fa fa-pencil"></i> Editar </a>
								</td>
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

	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaDepartamentoViewModel({
				departamentos : ${departamentos}
			});
			ko.applyBindings(viewModel);

			var table = dibujarTabla("#table-departamentos");
		});
	</script>
</body>