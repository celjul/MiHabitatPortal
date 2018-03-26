<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<head>
    <title><spring:message code="mihabitat.reportes"/> | <spring:message code="mihabitat.nombre"/></title>
	<style type="text/css">
		.currency {
			text-align: right;
		}
	</style>
</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                   <spring:message code="mihabitat.menu.inicio"/>
               </a>
             </li>
        </ol>
    </div>
    <div id="content">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget" id="wid-id-1">
                    <header>
                        <span class="widget-icon"> <i class="fa fa-edit"></i>
                        </span>
                        <h2>
                            <spring:message code="mihabitat.reportes.resumenabonos" />
                        </h2>
						<div class="widget-toolbar">
							<a href="#" data-bind="event : { click: function() { $root.imprimir('txt') } }" class="btn btn-primary">
								<i class="fa fa-file-text-o"></i>
								<span class="hidden-mobile"><spring:message code="mihabitat.reportes.txt" /></span>
							</a>
						</div>
						<div class="widget-toolbar">
							<a href="#" data-bind="event : { click: function() { $root.imprimir('csv') } }" class="btn btn-primary">
								<i class="fa fa-file-excel-o"></i>
								<span class="hidden-mobile"><spring:message code="mihabitat.reportes.csv" /></span>
							</a>
						</div>
						<div class="widget-toolbar">
							<a href="#" data-bind="event : { click: function() { $root.imprimir('xlsx') } }" class="btn btn-primary">
								<i class="fa fa-file-excel-o"></i>
								<span class="hidden-mobile"><spring:message code="mihabitat.reportes.xls" /></span>
							</a>
						</div>
						<div class="widget-toolbar">
							<a href="#" data-bind="event : { click: function() { $root.imprimir('pdf') } }" class="btn btn-primary">
								<i class="fa fa-file-pdf-o"></i>
								<span class="hidden-mobile"><spring:message code="mihabitat.reportes.pdf" /></span>
							</a>
						</div>
                    </header>
                    <form id="reporte-form" class="smart-form">
                        <fieldset>
                            <div class="row">
                                <div class="col col-sm-12 col-md-12 col-lg-12">
                                    <div class="row">
										<div class="col col-md-2">
											<label class="label">
												<span class="error-required">*</span>
												<spring:message code="mihabitat.reportes.resumenabonos.inicio" />
											</label>
											<div class="input-group">
												<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaInicio"
													   id="fechaInicio"
													   required="required" readonly="readonly"
													   data-bind="value: $root.reporte.inicio">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
											<br>
										</div>
										<div class="col col-md-2">
											<label class="label">
												<span class="error-required">*</span>
												<spring:message code="mihabitat.reportes.resumenabonos.fin" />
											</label>
											<div class="input-group">
												<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaFin"
													   id="fechaFin"
													   required="required" readonly="readonly"
													   data-bind="value: $root.reporte.fin">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
											<br>
										</div>
										<div class="col col-md-2">
											<label class="label">
												&nbsp;
											</label>
											<label class="toggle">
												<input type="checkbox" name="cancelados" id="cancelados" data-bind="checked: $root.reporte.cancelados">
												<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.resumenabonos.cancelados" />
											</label>
										</div>
										<div class="col col-md-2">
											<label class="label">
												&nbsp;
											</label>
											<label class="toggle">
												<input type="checkbox" name="conSaldoFavor" id="conSaldoFavor" data-bind="checked: $root.reporte.conSaldoFavor">
												<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.resumenabonos.consaldofavor" />
											</label>
										</div>


										<div class="col col-md-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <label class="toggle">
                                                <input type="checkbox" name="usuarioComentario" id="usuarioComentario" data-bind="checked: $root.reporte.usuarioComentario">
                                                <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.resumenabonos.usuarioComentario" />
                                            </label>
                                        </div>



										<div class="col col-md-1">
											<label class="label">
												&nbsp;
											</label>
											<button type="button" class="btn btn-default btn-sm btn-block" data-bind="event : { click: $root.consulta }">
												<spring:message code="mihabitat.botones.aceptar" />
											</button>
										</div>
                                        <%--<div class="col col-md-2">
                                            &nbsp;
                                        </div>
                                        <div class="col col-md-2">
                                            <label class="label">
                                                <span class="error-required">*</span>
                                                <spring:message code="mihabitat.estadocuenta.periodo" />
                                            </label>
                                            <label class="select">
                                                <select style="width: 100%" class="select2" name="periodo" id="periodo"
			                                        required="required" 
			                                        data-bind="options: $root.periodos, value: $root.reporte.fecha, select2: {}, event : { change : $root.consulta }">
			                                    </select>
                                            </label>
                                        </div>--%>
                                        <%--<div class="col col-md-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <label class="toggle">
                                                <input type="checkbox" name="activo" id="activo" data-bind="checked: $root.reporte.detalle">
                                                <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.saldofavor.detalle" />
                                            </label>
                                        </div>--%>
                                        <div class="col col-md-2">
                                            &nbsp;
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                <div class="col-sm-12 col-md-12 col-lg-12">
                                    <div class="panel-group smart-accordion-default" id="accordion">
                                        <div class="panel panel-default">
                                            <div class="panel-heading no-padding">
			                                    <div class="table-responsive">
			                                        <table id="table-cuentas-abonos" class="table table-striped table-bordered" style="width: 100%">
			                                            <thead>
			                                                <tr>
																<th style="width: 5%;">
																	<spring:message code="mihabitat.pago.folio"/>
																</th>
																<th style="width: 10%;">
																	<spring:message code="mihabitat.pago.fecha"/>
																</th>
																<th style="width: 15%;">
																	<spring:message code="mihabitat.contacto"/>
																</th>
																<th style="width: 15%;">
																	<spring:message code="mihabitat.departamento"/>
																</th>
																<th style="width: 10%;">
																	<spring:message code="mihabitat.pago.metodo"/>
																</th>
																<th style="width: 5%;">
																	<spring:message code="mihabitat.pago.referencia"/>
																</th>
																<th style="width: 20%;">
																	<spring:message code="mihabitat.pago.comentario"/>
																</th>
																<th style="width: 5%;">
																	<spring:message code="mihabitat.pago.estatus"/>
																</th>
																<th style="width: 5%;">
																	<spring:message code="mihabitat.pago.monto"/>
																</th>
																<th style="width: 10%;">
																	<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
																</th>
			                                                <%--<tr>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.cargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.recargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.descuentos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.pagos" /></th>
			                                                </tr>--%>
			                                            </thead>
			                                            <tbody>
			                                                <!-- ko foreach: { data: $root.reporte.abonos, as : 'a' } -->
															<tr data-bind="css: {'': (estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.aprobado),
																				warning: (estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.pendiente),
																				danger: ((estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.rechazado) ||
																					(estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.cancelado))}">
																<td data-bind="text: folio()"></td>
																<td data-bind="text: (moment(fecha(),'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
																<td data-bind="text: contacto.nombre()"></td>
																<td data-bind="foreach : { data: pagosDepartamento() }">
																	<label data-bind="text: departamento.nombre() + ' - '"></label>
																	<!-- ko foreach: { data: departamento.grupos() } -->
																	<label data-bind="text: descripcion + ', '"></label>
																	<!--  /ko -->
																</td>
																<td >
																	<span class="label pull-left label-warning" style="color: white;padding: 1px;font-size: 10px" data-bind="text: metodoPago.descripcion(), visible: metodoPago.id() == AppConfig.catalogos.metodos.saldofavor">
																	</span>
																	<span data-bind="text: metodoPago.descripcion(), visible: metodoPago.id() != AppConfig.catalogos.metodos.saldofavor">
																	</span>
																</td>
																<td data-bind="text: referencia()"></td>
																<td data-bind="foreach : { data: estatus() }">
																	<label style="font-weight: 600" data-bind="visible: comentario() && $root.reporte.usuarioComentario() , text: usuario.user() + ': '"></label>
																	<label data-bind="visible: comentario(), text: comentario()"></label>

																</td>
																<td style="text-align: right;">
																	<span class="label pull-left" style="color: white;padding: 1px;font-size: 10px" data-bind="text: estatus()[estatus().length - 1].estatus.descripcion(),
																		css: {'label-success': (estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.aprobado),
																		'label-warning': (estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.pendiente),
																		'label-danger': ((estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.rechazado) ||
																			(estatus()[estatus().length - 1].estatus.id() == AppConfig.catalogos.estatuspago.cancelado))}">

																	</span>
																</td>
																<td style="text-align: right;" data-bind="text: '$' + monto.formatted()">

																</td>
																<td style="text-align: center;">
																	<div class="btn-group">
																		<a class="btn btn-default btn-xs" href="javascript:void(0);"><i
																				class="fa fa-cog"></i></a>
																		<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown"
																		   href="javascript:void(0);"><span class="caret"></span></a>
																		<ul class="dropdown-menu dropdown-menu-right">
																			<li>
																				<a href="javascript:void(0);" data-bind="click: $root.ver">
																					<spring:message code="mihabitat.pago.detalle"/>
																				</a>
																			</li>
																			<li data-bind="visible: estatus()[estatus().length - 1].estatus.descripcion() == 'Aprobado' && comprobante()">
																				<a href="javascript:void(0);" data-bind="click: $root.descargar">
																					<spring:message code="mihabitat.pago.adjunto"/>
																				</a>
																			</li>
																			<%--<li data-bind="visible: estatus()[estatus().length - 1].estatus.descripcion == 'Pendiente'">
																				<a href="javascript:void(0);" data-bind="click: $root.ver">
																					<spring:message code="mihabitat.pago.aprobar"/>
																				</a>
																			</li>
																			<li data-bind="visible: estatus()[estatus().length - 1].estatus.descripcion == 'Pendiente'">
																				<a href="javascript:void(0);" data-bind="click: $root.ver">
																					<spring:message code="mihabitat.pago.rechazar"/>
																				</a>
																			</li>
																			<li data-bind="visible: ((estatus()[estatus().length - 1].estatus.descripcion == 'Aprobado') || (estatus()[estatus().length - 1].estatus.descripcion == 'Cancelado'))">
																				<a href="javascript:void(0);" data-bind="click: $root.abrirModalEnvio">
																					<spring:message code="mihabitat.pago.enviar"/>
																				</a>
																			</li>--%>
																			<li data-bind="visible: ((estatus()[estatus().length - 1].estatus.descripcion() == 'Aprobado') || (estatus()[estatus().length - 1].estatus.descripcion() == 'Cancelado'))">
																				<a href="javascript:void(0);" data-bind="click: $root.imprimirPgo">
																					<spring:message code="mihabitat.pago.imprimir"/>
																				</a>
																			</li>
																		</ul>
																	</div>
																</td>
															</tr>
			                                                    <%--<tr class="panel-collapse collapse" data-bind="attr : { id: 'collapse-movs' + departamento.id()}">
			                                                        <td colspan="2"> &nbsp; </td>
			                                                        <td colspan="7" class="no-padding">
			                                                            <table class="table table-striped table-bordered" style="width: 100%;">
				                                                            <thead>
									                                            <tr class="active">
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.cargo.fecha" />
									                                                </th>
									                                                <th class="col-sm-6 col-md-6 col-lg-6">
									                                                   <spring:message code="mihabitat.cargo.concepto" />
									                                                </th>
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.movimiento.debe" />
									                                                </th>
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.movimiento.haber" />
									                                               </th>
									                                            </tr>
									                                        </thead>
									                                        <tbody>
									                                            <!-- ko foreach: { data: movimientos } -->
									                                                <tr class="active">
										                                                <td data-bind="text: fecha"></td>
				                                                                        <!-- ko if: typeof cargo !== 'undefined' -->
				                                                                            <td data-bind="text: tipo.descripcion + ': ' + cargo.concepto"></td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: typeof movimientoCargo !== 'undefined' -->
				                                                                            <td data-bind="text: tipo.descripcion + ': ' + movimientoCargo.cargo.concepto"></td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: debe || (debe == 0)-->
				                                                                            <td data-bind="currency: debe, symbol: $ " style="text-align: right;"></td>
				                                                                            <td> &nbsp; </td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: haber || (haber == 0)-->
				                                                                            <td> &nbsp; </td>
				                                                                            <td data-bind="currency: haber, symbol: $ " style="text-align: right;"></td>
				                                                                        <!-- /ko -->
			                                                                        </tr>
									                                            <!-- /ko -->
									                                        </tbody>
								                                        </table>
			                                                        </td>
			                                                    </tr>--%>
			                                                <!-- /ko -->
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td style="font-weight: bolder">
																	TOTAL:
																</td>
																<td  style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalAbonos(), symbol: $ "></strong>
																</td>
																<td style="text-align: center;">
																</td>
															</tr>
			                                            </tbody>
			                                        </table>
				                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-indiviso.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-simple.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-prorrateo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/departamento-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/contacto-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/estatus.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador-app.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/resumen-abonos.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/resumen-abonos-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ResumenDeAbonosViewModel();
			ko.applyBindings(viewModel);
			$("#fechaInicio").datepicker();
			$("#fechaFin").datepicker();
		});
	</script>
</body>