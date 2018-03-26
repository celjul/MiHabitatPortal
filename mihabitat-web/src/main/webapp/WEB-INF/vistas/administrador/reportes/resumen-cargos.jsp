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
                            <spring:message code="mihabitat.reportes.resumencargos" />
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
												<spring:message code="mihabitat.reportes.resumencargos.inicio" />
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
												<spring:message code="mihabitat.reportes.resumencargos.fin" />
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
												<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.resumencargos.cancelados" />
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
			                                        <table id="table-cuentas-cobrar" class="table table-striped table-bordered" style="width: 100%">
			                                            <thead>
			                                                <tr>
																<th style="width: 10%;">
																	<spring:message code="mihabitat.cargo.fecha" />
																</th>
																<th data-hide="phone" style="width: 10%;">
																	<spring:message code="mihabitat.departamento" />
																</th>
																<th data-hide="phone" style="width: 10%;">
																	<spring:message code="mihabitat.departamento.etiquetas" />
																</th>
																<th data-class="expand" style="width: 35%;">
																	<spring:message code="mihabitat.cargo.concepto" />
																</th>
																<th data-hide="phone" style="width: 10%;">
																	<spring:message code="mihabitat.cargo.montoOriginal" />
																</th>
																<th data-hide="phone" style="width: 5%;">
																	<spring:message code="mihabitat.pago.recargos" />
																</th>
																<th data-hide="phone" style="width: 5%;">
																	<spring:message code="mihabitat.pago.descuentos" />
																</th>
																<th data-hide="phone" style="width: 5%;">
																	<spring:message code="mihabitat.pago.pagado" />
																</th>
																<th data-hide="phone" style="width: 5%;">
																	<spring:message code="mihabitat.pago.saldopendiente" />
																</th>
																<th style="text-align: center;" style="width: 5%;">
																	<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
																</th>
			                                                </tr>
			                                                <%--<tr>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.cargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.recargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.descuentos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.estadocuenta.resumen.pagos" /></th>
			                                                </tr>--%>
			                                            </thead>
			                                            <tbody>
			                                                <!-- ko foreach: { data: $root.reporte.cargos, as : 'c' } -->
															<tr data-bind="css: {danger: cargo.cancelado()}">
																<td data-bind="text: (moment(fecha(), 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
																<td data-bind="text: departamento.nombre"></td>
																<td data-bind="foreach: {data: departamento.grupos}">
																	<span data-bind="text: descripcion"></span>
																</td>
																<td >
																	<span data-bind="text: tipo.descripcion() + ' | ' + concepto()"></span>
																	<span class="label label-danger" data-bind="visible: cargo.cancelado()">
																		<spring:message code="mihabitat.cargo.cancelado" />
																	</span>
																</td>
																<td data-bind="currency: totalMonto, symbol: $ " style="text-align: right;"></td>
																<td data-bind="currency: totalRecargos, symbol: $ " style="text-align: right;"></td>
																<td data-bind="currency: totalDescuentos, symbol: $ " style="text-align: right;"></td>
																<td data-bind="currency: totalPagado, symbol: $ " style="text-align: right;"></td>
																<td style="text-align: right;">
																	<strong data-bind="currency: saldoPendiente, symbol: $ "></strong>
																</td>
																<td style="text-align: center;">
																	<div class="btn-group">
																		<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
																			<i class="fa fa-cog"></i> <span class="caret"></span>
																		</button>
																		<ul class="dropdown-menu dropdown-menu-right">
																			<li>
																				<a href="javascript:void(0);" data-bind="click: $root.actualizar">
																					<spring:message code="mihabitat.botones.editar" />
																				</a>
																			</li>
																			<li data-bind="visible: agrupador && agrupador.id">
																				<a href="javascript:void(0);" data-bind="click: $root.actualizarSimilares">
																					<spring:message code="mihabitat.botones.editar.similares" />
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
																<td style="font-weight: bolder">
																	TOTAL:
																</td>
																<td  style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalCargos(), symbol: $ "></strong>
																</td>
																<td  style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalRecargos(), symbol: $ "></strong>
																</td>
																<td  style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalDescuentos(), symbol: $ "></strong>
																</td>
																<td  style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalPagado(), symbol: $ "></strong>
																</td>
																<td style="text-align: right;">
																	<strong data-bind="currency: $root.reporte.totalPendiente(), symbol: $ "></strong>
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

	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/departamento-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento-app.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/resumen-cargos.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/resumen-cargos-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new ResumenDeCargosViewModel();
			ko.applyBindings(viewModel);
			$("#fechaInicio").datepicker();
			$("#fechaFin").datepicker();
		});
	</script>
</body>