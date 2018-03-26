<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" />
<c:set value="${contextPath}/recursos/js/plugin" var="plugin" />
<c:set value="${contextPath}/recursos/js/app" var="app" />

<head>
	<title><spring:message code="mihabitat.menu.condominios"/> | <spring:message code="mihabitat.nombre"/></title>
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
				<a href="${contextPath}/super-administrador/condominios/lista">
					<spring:message code="mihabitat.menu.condominios"/>
				</a>
			</li>
			
		</ol>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.condominios"/></h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-condominios" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
								<th class="hasinput" style="width: 5%;">
									<input type="text" class="form-control" placeholder="#" />
								</th>
								<th class="hasinput" style="width: 15%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.condominio.nombre" />" />
								</th>
								<th class="hasinput" style="width: 10%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.condominio.administradores" />" />
								</th>
								<th class="hasinput" style="width: 60%;">
									<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.direccion.completa" />" />
								</th>
								<th class="hasinput" style="width: 10%;">
								</th>
							</tr>
							<tr>
								<th data-hide="phone" style="text-align: center;">
									#
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.condominio.nombre" />
								</th>
								<th data-class="expand">
									<spring:message code="mihabitat.condominio.administradores" />
								</th>
								 <th data-class="expand">
									<spring:message code="mihabitat.direccion.completa" />
								</th>
								<th style="text-align: center;">
									<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
								</th>
							</tr>
						</thead>
						<tbody data-bind="foreach : { data: $root.condominios, as :'con' }">
							<tr>
								<td data-bind="text: con.id" style="text-align: center;"></td>
								<td data-bind="text: con.nombre"></td>
							 <td>
								<ul data-bind="foreach : { data : con.administradores, as : 'ad' }">
									<li data-bind="text: ad.user"></li>
								</ul>
							</td>
								<td data-bind="text: '<spring:message code="mihabitat.direccion.municipio" /> :' +' '+ direccion.colonia.municipio.nombre 
													+ ' <spring:message code="mihabitat.direccion.colonia" /> :' +' '+ direccion.colonia.nombre 
													+ ' <spring:message code="mihabitat.direccion.calle" /> :' +' '+direccion.calle 
													+ ' <spring:message code="mihabitat.direccion.noExterior" /> :'+' '+direccion.noExterior"></td>
								<td style="text-align: center;">
									<div class="btn-group">
										<a class="btn btn-default btn-xs" href="javascript:void(0);">
											<i class="fa fa-cog"></i>
										</a>
										<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);" data-bind="attr: {id: 'menu-' + id}">
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu">
											<li>
												<a href="javascript:void(0);" data-bind="attr: {id: 'link-edit-' + id, href: String.format('{0}/actualizar/{1}', PathConfig.condominios, id)}">
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
	<jsp:include page="modales/condominio.jsp" />
	<script src="${plugin}/datatables/jquery.dataTables.min.js"></script>
	<script src="${plugin}/datatables/dataTables.colVis.min.js"></script>
	<script src="${plugin}/datatables/dataTables.tableTools.min.js"></script>
	<script src="${plugin}/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${plugin}/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${plugin}/datatables/dataTables.impl.js"></script>

	<script src="${app}/condominios/condominio.js?v=${project-version}"></script>
	<script src="${app}/direcciones/pais.js?v=${project-version}"></script>
	<script src="${app}/direcciones/estado.js?v=${project-version}"></script>
	<script src="${app}/direcciones/municipio.js?v=${project-version}"></script>
	<script src="${app}/direcciones/colonia.js?v=${project-version}"></script>
	<script src="${app}/direcciones/direccion.js?v=${project-version}"></script>
	<script src="${app}/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${app}/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${app}/personas/telefono.js?v=${project-version}"></script>
	<script src="${app}/personas/email.js?v=${project-version}"></script>
	<script src="${app}/personas/persona.js?v=${project-version}"></script>
	<script src="${app}/condominios/condominio-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ListaCondominiosViewModel({
				condominios : ${condominios},
				administradores : ${administradores},
				paises : ${paises}
			});
			ko.applyBindings(viewModel);
			var table = dibujarTabla("#table-condominios");
			$("div.toolbar").html('<div class="text-right"><button type="button" id="btn-nuevo-condominio" data-toggle="modal" data-target="#modal-condominio" class="btn btn-primary" ><spring:message code="mihabitat.botones.nuevo" /> </button></div>');
		});
	</script>
</body>