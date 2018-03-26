<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<head>
<title><spring:message code="mihabitat.menu.estadocuenta" /> |
	<spring:message code="mihabitat.nombre" /></title>

	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.css">

</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li><a
				href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio" />
			</a></li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="row">
			<article class="col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2"
					data-widget-colorbutton="false" data-widget-editbutton="false"
					data-widget-custombutton="false">
					<header>
						<span class="widget-icon"> <i class="fa fa-edit"></i>
						</span>
						<h2>
							<spring:message code="mihabitat.estadocuenta" />
							-
							<spring:message code="mihabitat.menu.estadocuenta.masivo" />
						</h2>
					</header>

					<form id="envio-form" class="smart-form">
						<fieldset>
							<section class="row">
								<%--<section class="col col-md-2">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.estadocuenta.periodo" />
									</label> <label class="select"> <select style="width: 100%"
										class="select2" name="periodo" id="periodo"
										required="required"
										data-bind="options: $root.periodos,
                                                      value: $root.periodo,
                                                      select2: {},
                                                      event : { change : $root.obtenerEstadosDeCuenta }">
									</select>
									</label>
								</section>--%>
									<section class="col col-md-2">
										<label class="label"> <span class="error-required">*</span>
											Desde:
										</label>
										<div class="input-group">
											<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaInicio"
												   id="fechaInicio"
												   required="required" readonly="readonly"
												   data-bind="value: $root.inicio, event : { change : $root.obtenerEstadosDeCuenta }">
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
										<br>
									</section>
									<section class="col col-md-2">
										<label class="label"> <span class="error-required">*</span>
											Hasta:
										</label>
										<div class="input-group">
											<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaFin"
												   id="fechaFin"
												   required="required" readonly="readonly"
												   data-bind="value: $root.fin, event : { change : $root.obtenerEstadosDeCuenta }">
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
										<br>
									</section>
								<section class="col col-md-8 col-sm-8">
									<div class="alert alert-block alert-warning">
										<a class="close" data-dismiss="alert" href="#">x</a>
										<h4 class="alert-heading">
											<spring:message code="mihabitat.estadocuenta.masivo.mensaje.titulo"></spring:message>
										</h4>
										<spring:message code="mihabitat.estadocuenta.masivo.mensaje" />
									</div>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-12">
									<div class=""></div>
									<table id="table-estados-cuenta"
										   class="table table-striped table-bordered table-hover"
										   style="width: 100%">
										<thead>
										<tr>
											<th data-class="expand" class="col-md-2"><spring:message
													code="mihabitat.departamento" /></th>
											<th data-class="expand" class="col-md-1"><spring:message
													code="mihabitat.grupos" /></th>
											<th data-class="expand" class="col-md-2"><spring:message
													code="mihabitat.contacto" /> <spring:message
													code="mihabitat.cd.principal" /></th>
											<th data-class="expand" class="col-md-2"><spring:message
													code="mihabitat.persona.emails" /></th>
											<th data-class="expand" class="col-md-2"><spring:message
													code="mihabitat.estadocuenta.resumen.saldodeudor" /></th>
											<td data-class="expand" class="col-md-1 smart-form"><label
													class="checkbox"> <input type="checkbox"
																			 name="checkbox-inline" data-bind="checked: seleccionarTodo">
												<i></i>&nbsp;&nbsp;<spring:message
														code="mihabitat.estadocuenta.masivo.seleccionar.todos" />
											</label></td>
										</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.estados }">
										<tr>
											<td data-bind="text: departamento"></td>
											<td>
												<!-- ko foreach: { data: grupos } -->
												<div style="margin-top: 5px; display: inline-block;">
									       <span class="label label-default"
												 data-bind="text: descripcion"></span>
												</div>
												<!--  /ko -->
											</td>
											<td data-bind="text: contacto"></td>
											<td>
												<ul style="margin-left: 10px">
													<!-- ko foreach: { data: emails } -->
													<li><span data-bind="text: direccion"></span></li>
													<!--  /ko -->
												</ul>
											</td>
											<td class="currency">
												<span data-bind="currency: total, symbol: '$'" ></span>
													<span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;padding: 3px;">
														&nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
													</span>
											</td>
											<%--<td data-bind="currency: total, symbol: $"
												style="text-align: right"></td>--%>
											<td style="text-align: center"
												data-bind="style: { background : !seleccionable ? '#f7f0d9' : '' }">
												<div class="smart-form">
													<label class="checkbox"> <input type="checkbox"
																					name="checkbox-inline"
																					data-bind="checked: seleccionado, enable: seleccionable">
														<i></i>
													</label>
												</div>
											</td>
										</tr>
										</tbody>
									</table>
								</section>
							</section>

						</fieldset>
						<footer>
							<section class="row">
								<section class="col col-md-3 pull-right">
									<a class="btn btn-primary btn-block"
									   data-bind="click: $root.envioMasivo"> <spring:message
											code="mihabitat.estadocuenta.enviar" />
									</a>
								</section>
								<section class="col col-md-3 pull-right">
									<button type="button" class="btn btn-primary btn-xs btn-block"
											data-bind="event : { click: function() { $root.imprimirMasivo('pdf') } }">
										<i class="fa fa-file-pdf-o"></i>
										<spring:message code="mihabitat.estadocuenta.imprimir" />
									</button>
								</section>
								<section class="col col-md-3 pull-right">
									<button type="button" class="btn btn-primary btn-xs btn-block"
											data-bind="event : { click: function() { $root.imprimirMasivo('xlsx') } }">
										<i class="fa fa-file-excel-o"></i>
										<spring:message code="mihabitat.estadocuenta.imprimirXls" />
									</button>
								</section>

							</section>
						</footer>
					</form>

				</div>
			</article>
		</div>

		<%--<div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.departamentos" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">

				</div>
			</div>
		</div>--%>
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/estado-cuenta/estado-cuenta-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new EstadoCuentaListaViewModel();
			ko.applyBindings(viewModel);

			$("#fechaInicio").datepicker();
			$("#fechaFin").datepicker();
		});
	</script>
</body>