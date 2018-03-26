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
			   <a href="${pageContext.request.contextPath}/administrador/proveedores/lista">
				   <spring:message code="mihabitat.menu.proveedores"/>
			   </a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.proveedores"/></h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-proveedores" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.proveedor.nombre" />" />
							   </th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.proveedor.giros" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.proveedor.diasCredito" />" />
								</th>
								<th class="hasinput" style="width: 20%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.contactos" />" />
								</th>
								<th class="hasinput" style="width: 10%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.proveedor.estatus" />" />
								</th>
							   <th class="hasinput" style="width: 40%;">
							   </th>
							</tr>					  
							<tr>
								<th data-class="expand">
								   <spring:message code="mihabitat.proveedor.nombre" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.proveedor.giros" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.proveedor.diasCredito" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.menu.contactos" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.proveedor.estatus" />
								</th>
								<th style="text-align: center;">
								   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
								</th>
							</tr>
						</thead>
						<tbody data-bind="foreach : { data: $root.proveedores }">
							<tr>
								<td data-bind="text: nombre"></td>
								<td>
									<ul>
										<!-- ko foreach: { data: giros} -->
										<li data-bind="text: descripcion"></li>
										<!-- /ko -->
									</ul>
								</td>
								<td data-bind="text: diasCredito"></td>
								<td>
									<ul>
										<!-- ko foreach: { data: contactos} -->
										<li data-bind="text: (nombre + ' ' + apellidoPaterno + ' ') + (apellidoMaterno ? apellidoMaterno : '')"></li>
										<!-- /ko -->
									</ul>
								</td>
								<td data-bind="text: activo  ? 'Activo' : 'Inactivo'" style="text-align: center;"></td>

								<td style="text-align: center;">
								   <div class="btn-group">
									   <a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
									   <a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
									   <ul class="dropdown-menu">
										   <li>
											   <a href="javascript:void(0);" data-bind="click: $root.actualizar">
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
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/proveedores/proveedor-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaProveedorViewModel({
				proveedores : ${proveedores}
			});
			ko.applyBindings(viewModel);
			var table = dibujarTabla("#table-proveedores");
		});
	</script>
</body>