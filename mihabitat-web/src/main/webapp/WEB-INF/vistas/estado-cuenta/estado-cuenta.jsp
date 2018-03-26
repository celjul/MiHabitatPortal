<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.estadocuenta" />
					-
					<spring:message code="mihabitat.menu.estadocuenta.individual" />
				</h2>
				<div class="widget-toolbar hidden-lg hidden-md hidden-sm" data-bind="visible: $root.estadocuenta.departamento.id()">
					<div class="label label-success" data-placement="bottom" data-original-title="Saldo a Favor">
						<i class="fa fa-dollar"></i>
						<span data-bind="text: numeral($root.estadocuenta.saldoFavorAlDia()).format('0,0.00')"></span>
					</div>
				</div>
				<div class="widget-toolbar hidden-lg hidden-md hidden-sm" data-bind="visible: $root.estadocuenta.departamento.id()">
					<div class="label label-danger" rel="tooltip"
						 data-placement="bottom" data-original-title="Adeudo Total">
						<i class="fa fa-dollar"></i>
						<span data-bind="text: numeral($root.estadocuenta.saldoAlDia()).format('0,0.00')"></span>
					</div>
				</div>
				<div class="widget-toolbar hidden-xs" data-bind="visible: $root.estadocuenta.departamento.id()">
					<span style="font-weight: bolder;font-size: 14px;" class="hidden-mobile txt-color-green">Saldo a Favor: </span>
					<div class="label label-success" rel="tooltip" data-placement="bottom" data-original-title="Saldo a Favor">
						<i  style="font-weight: bolder;font-size: 18px" class="fa fa-dollar"></i>
						<span  style="font-weight: bolder;font-size: 18px" data-bind="text: numeral($root.estadocuenta.saldoFavorAlDia()).format('0,0.00')"></span>
					</div>
				</div>
				<div class="widget-toolbar hidden-xs" data-bind="visible: $root.estadocuenta.departamento.id()">
					<span style="font-weight: bolder;font-size: 14px;" class="hidden-mobile txt-color-red">Adeudo Total: </span>
					<div class="label label-danger" style="font-weight: bolder;font-size: 18px" rel="tooltip"
						 data-placement="bottom" data-original-title="Adeudo Total">
						<i class="fa fa-dollar"></i>
						<span data-bind="text: numeral($root.estadocuenta.saldoAlDia()).format('0,0.00')"></span>
					</div>
				</div>
				<%--<div class="widget-toolbar bg-color-white" data-bind="visible: $root.estadocuenta.departamento.id()">
					<div class="label" style="color: red;font-weight: bolder;font-size: 18px" rel="tooltip" data-placement="bottom" data-original-title="Saldo a Favor">
						<span class="hidden-mobile">SF: </span>
						<i class="fa fa-dollar"></i>
						<span data-bind="text: numeral($root.estadocuenta.saldoAlDia()).format('0,0.00')"></span>
					</div>
				</div>--%>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="estado-cuenta-form" class="smart-form">
						<fieldset>
							<section class="row">
								<sec:authorize access="hasRole('Administrador')">
									<section class="col col-md-3">
										<label class="label"> <span class="error-required">*</span>
											<spring:message code="mihabitat.estadocuenta.datos" />
										</label> <label class="input"> <input class="form-control"
											type="text" name="busqueda" id="busqueda"
											placeholder="Introduzca el Departamento o Condomino para ver su estado de cuenta"
											required="required" maxlength="128"
											data-bind="value: $root.item.label, valueUpdate : 'keyup'">
										</label>
									</section>
								</sec:authorize>
								<sec:authorize access="hasRole('Contacto')">
									<section class="col col-md-3">
										<label class="label"> <span class="error-required">*</span>
											<spring:message code="mihabitat.departamento" />
										</label> <label class="select"> <select style="width: 100%"
											class="select2" name="departamento" id="departamento"
											required="required"
											data-bind="options: $root.departamentos,
	                                                       optionsCaption : 'Seleccione una opción',
	                                                       optionsText: 'label',
	                                                       optionsValue: 'value',
	                                                       value: $root.item.url,
	                                                       select2: {},
	                                                       event : { change : $root.recargarDepto }">
										</select>
										</label>
									</section>
								</sec:authorize>
								<!-- ko if : $root.item.url() -->

								<section class="col col-md-9">
									<div class="well" style="font-size: 12px;min-height: 67px">
									<ul style="margin: 10px" class="list-inline">
										<li style="vertical-align: top; padding: 3px 10px">
											<ul>
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.departamento" />: </strong><span
														data-bind="text: $root.estadocuenta.departamento.nombre"></span>
												</li>
												<li style="list-style-type: none;width: 300px;">
													<strong><spring:message code="mihabitat.departamento.etiquetas" />: </strong>
													<!-- ko foreach: $root.estadocuenta.departamento.grupos -->
														<span data-bind="text: descripcion + ', '"></span>
													<!-- /ko -->
												</li>
												<!-- ko if : $root.estadocuenta.contacto.nombre() -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.nombre" />:</strong>
													<span data-bind="text: $root.estadocuenta.contacto.nombreCompleto"></span>
												</li>
												<!-- /ko -->
											</ul>
										</li>
										<!-- ko if : $root.estadocuenta.contacto.nombre() -->
										<li style="vertical-align: top; padding: 3px 10px">
											<ul>
												<!-- ko if : $root.estadocuenta.contacto.emails().length > 0 -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.emails" />:</strong>
													<ul style="margin-left: 25px">
														<!-- ko foreach: $root.estadocuenta.contacto.emails -->
														<li><span data-bind="text: direccion"></span></li>
														<!-- /ko -->
													</ul>
												</li>
												<!-- /ko -->
											</ul>
										<li style="vertical-align: top; padding: 3px 10px">
											<ul>
												<!-- ko if : $root.estadocuenta.contacto.telefonos().length > 0 -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.telefonos" />:</strong>
													<ul style="margin-left: 25px">
														<!-- ko foreach: $root.estadocuenta.contacto.telefonos -->
														<li><span data-bind="text: numero"></span></li>
														<!-- /ko -->
													</ul>
												</li>
												<!-- /ko -->
											</ul>
										</li>
										</li>



										<!-- /ko -->
									</ul>
									</div>
								</section>
								<%--<section class="col col-md-3">
									<section class="col-xs-6 col-md-6">
									<table id="table-saldoActual"
										   class="table table-bordered" >
										<thead>
										<tr >
											<th style="text-align: center">Adeudo</th>
										</tr>
										</thead>
										<tbody>
										<tr style="text-align: center">
											<td style="color: red;font-weight: bolder;font-size: 15px"
												data-bind="currency: $root.estadocuenta.saldoAlDia(), symbol: $ "></td>
										</tr>
										</tbody>
									</table>
									</section>
									<section class="col-xs-6 col-md-6" data-bind="visible: $root.estadocuenta.saldoFavor() > 0">
									<table id="table-saldoFavor"
										   class="table table-bordered" >
										<thead>
										<tr >
											<th style="text-align: center">A Favor</th>
										</tr>
										</thead>
										<tbody>
										<tr style="text-align: center">
											<td style="color: green;font-weight: bolder;font-size: 15px"
												data-bind="currency: $root.estadocuenta.saldoFavor(), symbol: $ "></td>
										</tr>
										</tbody>
									</table>
										</section>
								</section>--%>
								<!-- /ko -->
							</section>



						</fieldset>
					</form>
					<!-- ko if : $root.item.id() -->
					<div class="jarviswidget" id="wid-id-7" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false" role="widget">
						<!-- widget options:
                        usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

                        data-widget-colorbutton="false"
                        data-widget-editbutton="false"
                        data-widget-togglebutton="false"
                        data-widget-deletebutton="false"
                        data-widget-fullscreenbutton="false"
                        data-widget-custombutton="false"
                        data-widget-collapsed="true"
                        data-widget-sortable="false"

                        -->
						<header role="heading">
							<ul class="nav nav-tabs pull-left in">
								<li class="active">
									<a data-toggle="tab" href="#hr1" data-bind="click: function() {$root.obtenerEstadoDeCuenta()}"
									   aria-expanded="false"> <i class="fa fa-lg fa-calendar"></i>
										<span class="hidden-mobile hidden-tablet"> Detalle de Movimientos por Mes </span> </a>
								</li>
								<li class="">
									<a data-toggle="tab" href="#hr2" data-bind="click: function() {$root.obtenerAvisoDeCobro()}"																 aria-expanded="true">
										<i class="fa fa-lg fa-warning"></i>
										<sec:authorize access="hasRole('Administrador')">
											<span class="hidden-mobile hidden-tablet"> Aviso de Cobro</span>
										</sec:authorize>
										<sec:authorize access="hasRole('Contacto')">
											<span class="hidden-mobile hidden-tablet"> Mis Pendientes de Pago</span>
										</sec:authorize>
                                    </a>
								</li>
								<li class="">
									<a data-toggle="tab" href="#hr3" data-bind="click: function() {$root.obtenerCargos()}"																 aria-expanded="true">
										<i class="fa fa-lg fa-minus-circle"></i>
										<span class="hidden-mobile hidden-tablet"> Historial de Cargos </span> </a>
								</li>
								<li class="">
									<a data-toggle="tab" href="#hr4" data-bind="click: function() {$root.obtenerAbonos()}"																 aria-expanded="true">
										<i class="fa fa-lg fa-plus-circle"></i>
										<span class="hidden-mobile hidden-tablet"> Historial de Abonos </span> </a>
								</li>
							</ul>
							<span class="jarviswidget-loader"><i class="fa fa-refresh fa-spin"></i></span></header>

						<!-- widget div-->
						<div role="content" >

							<!-- widget edit box -->
							<div class="jarviswidget-editbox">
								<!-- This area used as dropdown edit box -->

							</div>
							<!-- end widget edit box -->

							<!-- widget content -->
							<div class="widget-body">

								<div class="tab-content">
									<div class="tab-pane active" id="hr1">

										<!-- ko if : $root.item.url() -->

										<section class="row">
											<section class="col-md-12">
												<br>
												<section class="col-md-7">
													<section class="col-md-2">
														<label> <span class="error-required">*</span>
															<spring:message code="mihabitat.estadocuenta.periodo" />
														</label>
													</section>
													<section class="col-md-4">
														<div class="input-group">
															<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaInicio"
																   id="fechaInicio"
																   required="required" readonly="readonly"
																   data-bind="value: $root.inicio, event: { change : $root.obtenerEstadoDeCuenta }">
															<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
														</div>
														<br>
													</section>
													<section class="col-md-4">
														<div class="input-group">
															<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaFin"
																   id="fechaFin"
																   required="required" readonly="readonly"
																   data-bind="value: $root.fin, event: { change : $root.obtenerEstadoDeCuenta }">
															<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
														</div>
														<br>
													</section>
												</section>
												<%--<label> <span class="error-required">*</span>
													<spring:message code="mihabitat.estadocuenta.periodo" />
												</label>
												<select
														name="periodo" id="periodo"
														required="required"
														data-bind="options: $root.periodos,
                                                       value: $root.periodo,
                                                       event : { change : $root.obtenerEstadoDeCuenta }">
												</select>--%>

												<section class="col-md-5" style="position: absolute; top: 0; right: 15px;">
												<sec:authorize access="hasRole('Administrador')">
													<a class="btn btn-success btn-sm pull-right"
													   style="margin-left: 10px;padding-bottom: 2px;padding-top: 2px;margin-bottom: 5px;"
													   data-bind="click: $root.abrirModalEnvio">
														<i class="fa fa-envelope"></i>
														<span class="hidden-mobile hidden-tablet">
															<spring:message code="mihabitat.estadocuenta.enviar" />
														</span>
													</a>
												</sec:authorize>
													<a class="btn btn-info btn-sm pull-right"
													   style="margin-left: 10px;padding-bottom: 2px;padding-top: 2px;margin-bottom: 5px;"
													   data-bind="click: $root.imprimirEstadoCuenta">
														<i class="fa fa-print"></i>
														<span class="hidden-mobile hidden-tablet">
															<spring:message
																	code="mihabitat.estadocuenta.imprimir" />
														</span>
													</a>
												</section>
											</section>
										</section>
										<section class="row">
											<section class="col col-md-3">
												<h4>
														<span><spring:message
																code="mihabitat.estadocuenta.resumen.financiero" /> </span>
												</h4>
											</section>
											<section class="col-xs-8 col-md-12" style="padding-right: 15px;padding-left: 15px;box-sizing: border-box;">
												<table id="table-resumen"
													   class="table table-bordered" style="width: 100%">
													<thead>
													<tr>
														<th data-toggle="true" class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.anterior" /></th>
														<th data-hide="phone" class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.cargos" /></th>
														<th data-hide="phone" class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.recargos" /></th>
														<th data-hide="phone" class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.descuentos" /></th>
														<%--<th data-hide="phone" class="col-md-1"><spring:message
                                                                code="mihabitat.estadocuenta.resumen.total" /></th>--%>
														<th data-hide="phone" class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.pagos" /></th>
														<th class="col-md-1"><spring:message
																code="mihabitat.estadocuenta.resumen.saldodeudor" /></th>
													</tr>
													</thead>
													<tbody>
													<tr>
														<%--<td
																data-bind="currency: $root.estadocuenta.saldoAnterior, symbol: $ "
																style="text-align: right;font-weight: bolder"></td>--%>
														<td
																style="text-align: right;color: red; font-weight: bolder">
															<span data-bind="currency: $root.estadocuenta.saldoAnterior, symbol: $ "></span>
															<div class="label label-success" data-bind="visible: $root.estadocuenta.saldoFavorAnterior()">
																+
																<span data-bind="currency: $root.estadocuenta.saldoFavorAnterior, symbol: $ "></span>
															</div>
														</td>
														<td
																data-bind="currency: $root.estadocuenta.totalCargos, symbol: $ "
																style="text-align: right;"></td>
														<td
																data-bind="currency: $root.estadocuenta.totalRecargos, symbol: $ "
																style="text-align: right;"></td>
														<td
																data-bind="currency: $root.estadocuenta.totalDescuentos, symbol: $ "
																style="text-align: right;"></td>
														<%--<td
                                                            data-bind="currency: $root.estadocuenta.total, symbol: $ "
                                                            style="text-align: right;"></td>--%>
														<td
																data-bind="currency: $root.estadocuenta.totalPagos, symbol: $ "
																style="text-align: right;"></td>
														<td
																style="text-align: right;color: red; font-weight: bolder">
															<span data-bind="currency: $root.estadocuenta.saldoDeudor, symbol: $ "></span>
															<div class="label label-success" data-bind="visible: $root.estadocuenta.saldoFavor()">
																+
																<span data-bind="currency: $root.estadocuenta.saldoFavor, symbol: $ "></span>
															</div>
														</td>
													</tr>
													</tbody>
												</table>
											</section>
										</section>

										<!-- /ko -->

										<!-- ko if : $root.estadocuenta.movimientos().length > 0 -->

										<section class="row">
											<section class="col col-md-3">
												<h4>
													<spring:message
															code="mihabitat.estadocuenta.resumen.movimientos" />
												</h4>
											</section>
										</section>

										<section class="row">
											<section class="col col-md-12">
												<div class="custom-scroll" style="overflow-y: auto; max-height:350px">
													<table id="table-movimientos"
														   class="table table-striped table-bordered" style="width: 100%">
														<thead>
														<tr>
															<th style="min-width: 65px" class="col-md-1"><spring:message
																	code="mihabitat.estadocuenta.detalle.fecha" /></th>
															<th class="col-md-2"><spring:message
																	code="mihabitat.estadocuenta.detalle.tipo" /></th>
															<th class="col-md-5"><spring:message
																	code="mihabitat.estadocuenta.detalle.concepto" /></th>
															<th class="col-md-1 hidden-mobile"><spring:message
																	code="mihabitat.estadocuenta.detalle.debe" /></th>
															<th class="col-md-1 hidden-mobile"><spring:message
																	code="mihabitat.estadocuenta.detalle.haber" /></th>
															<th class="col-md-1 visible-mobile hidden-desktop hidden-tablet" style="height: 100%"><spring:message
																	code="mihabitat.estadocuenta.detalle.monto" /></th>
														</tr>
														</thead>
														<tbody
																data-bind="foreach : { data: $root.estadocuenta.movimientos }">
														<tr>
															<td data-bind="text: (moment(fecha, 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
															<!-- ko if: typeof cargo !== 'undefined' -->
															<td data-bind="text: tipo.descripcion"></td>
															<td data-bind="html: cargo.concepto"></td>
															<!-- /ko -->
															<!-- ko if: typeof movimientoCargo !== 'undefined' -->
															<td data-bind="text: tipo.descripcion"></td>
															<%--<td data-bind="text: movimientoCargo.cargo.concepto"></td>--%>
															<td data-bind="html: movimientoCargo.cargo.concepto"></td>
															<!-- /ko -->
															<!-- ko if: debe -->
															<td class="hidden-mobile" data-bind="currency: debe, symbol: $ "
																style="text-align: right;font-weight: bolder"></td>
															<!-- /ko -->
															<!-- ko if: !debe -->
															<td class="hidden-mobile"></td>
															<!-- /ko -->
															<!-- ko if: haber -->
															<td class="hidden-mobile" data-bind="currency: haber, symbol: $ "
																style="text-align: right;font-weight: bolder"></td>
															<!-- /ko -->
															<!-- ko if: !haber -->
															<td class="hidden-mobile"></td>
															<!-- /ko -->
															<td class="visible-mobile hidden-desktop hidden-tablet" data-bind="currency: haber-debe, symbol: $ "
																style="text-align: right;height: 100%;font-weight: bolder"></td>
														</tr>
														</tbody>
													</table>
												</div>
											</section>
										</section>

										<!-- /ko -->

									</div>
									<div class="tab-pane" id="hr2">
										<section class="row">
											<section class="col col-md-4" style="vertical-align: bottom">
												<br class="hidden-sm hidden-xs"><br class="hidden-sm hidden-xs">
												<h4 style="display: inline; padding-right: 25px;vertical-align: bottom">
												<span><spring:message
														code="mihabitat.avisodecobro.cargos" /></span>
												</h4>
											</section>
											<section class="col col-md-8">
												<section class="col-xs-6 col-md-4" style="margin-top: 5px">
													<label> <span class="error-required">*</span>
														<spring:message code="mihabitat.avisodecobro.Fecha" />
													</label>
													<div class="input-group">
														<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fecha"
															   id="fecha"
															   placeholder="<spring:message code="mihabitat.avisodecobro.Fecha" />"
															   required="required" readonly="readonly"
															   data-bind="value: $root.fecha, event: { change : $root.obtenerAvisoDeCobro }">
														<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													</div>
												</section>
												<section class="col-xs-6 col-md-8" style="padding-right: 15px;padding-left: 15px;box-sizing: border-box;">
										<sec:authorize access="hasRole('Administrador')">
													<a class="btn btn-success btn-sm"
                                                           style="margin-left: 10px;padding-bottom: 2px;padding-top: 2px;margin-bottom: 5px;"
                                                           data-bind="click: $root.abrirModalEnvio">
														<i class="fa fa-envelope"></i>
                                                        <span class="hidden-mobile hidden-tablet">
                                                            <spring:message code="mihabitat.avisodecobro.enviar" />
                                                        </span>
													</a>
													<a class="btn btn-info btn-sm"
                                                           style="margin-left: 10px;padding-bottom: 2px;padding-top: 2px;margin-bottom: 5px"
                                                           data-bind="click: $root.imprimirAvisoCobro">
														<i class="fa fa-print"></i>
                                                        <span class="hidden-mobile hidden-tablet">
                                                            <spring:message
                                                                    code="mihabitat.avisodecobro.imprimir" />
                                                        </span>
													</a>
										</sec:authorize>
													<table id="table-adeudo"
														   class="table table-bordered" style="width: 100%">
														<thead>
														<tr>
															<th class="hidden-xs"><spring:message
																	code="mihabitat.avisodecobro.resumen.adeudototal" /></th>
															<th class="hidden-sm hidden-md hidden-lg"><spring:message
																	code="mihabitat.avisodecobro.resumen.adeudo" /></th>
															<%--<th data-bind="currency: $root.estadocuenta.avisodecobro.adeudoTotal, symbol: $ "
																style="text-align: center;color: red;font-weight: bolder;background-color: #fff"></th>--%>
															<th
																	style="text-align: right;color: red; font-weight: bolder">
																<span data-bind="currency: $root.estadocuenta.avisodecobro.adeudoTotal, symbol: $ "></span>
																<div class="label label-success" data-bind="visible: $root.estadocuenta.avisodecobro.saldoFavor()">
																	+
																	<span data-bind="currency: $root.estadocuenta.avisodecobro.saldoFavor, symbol: $ "></span>
																</div>
															</th>
														</tr>
														</thead>

													</table>

												</section>
											</section>
										</section>

										<section class="row well well-clean" data-bind="visible: $root.estadocuenta.avisodecobro.cargos().length == 0">
											<section class="col col-md-12">
												<span  >
													No hay cargos pendientes de pago para este departamento en la fecha señalada.
												</span>
											</section>
										</section>

										<!-- ko if : $root.estadocuenta.avisodecobro && $root.estadocuenta.avisodecobro.cargos().length > 0 -->

										<section class="row">
											<section class="col col-md-12">
<div class="custom-scroll" style="overflow-y: auto; max-height:350px">
												<table id="table-aviso" class="table table-striped table-bordered table-hover" style="width: 100%">
													<thead>
													<tr>
														<th class="hasinput" style="width: 10%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.fecha" />" />
														</th>

														<th class="hasinput" style="width: 35%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.concepto" />" />
														</th>
														<th class="hasinput" style="width: 10%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.montoOriginal" />" />
														</th>
														<th class="hasinput" style="width: 5%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.recargos" />" />
														</th>
														<th class="hasinput" style="width: 5%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.descuentos" />" />
														</th>
														<th class="hasinput" style="width: 5%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.pagado" />" />
														</th>
														<th class="hasinput" style="width: 5%;">
															<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.saldopendiente" />" />
														</th>
														<th class="hasinput" style="width: 5%;">
														</th>
													</tr>
													<tr>
														<th data-hide="phone" style="width: 10%;min-width: 65px">
															<spring:message code="mihabitat.cargo.fecha" />
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
														<th style="width: 5%;">
															<spring:message code="mihabitat.pago.saldopendiente" />
														</th>
														<th style="text-align: center;" style="width: 5%;">
															<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
														</th>
													</tr>
													</thead>
													<tbody data-bind="foreach : { data: $root.estadocuenta.avisodecobro.cargos, as :'c' }">
													<tr data-bind="css: {danger: cargo.cancelado()}">
														<td data-bind="text: (moment(fecha(), 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
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
													<sec:authorize access="hasRole('Administrador')">
															<div class="btn-group">
																<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
																	<i class="fa fa-cog"></i> <span class="caret"></span>
																</button>
																<ul class="dropdown-menu dropdown-menu-right">
																	<li>
																		<a href="javascript:void(0);" data-bind="click: $root.actualizarCargo">
																			<spring:message code="mihabitat.botones.editar" />
																		</a>
																	</li>
																	<li data-bind="visible: agrupador && agrupador.id">
																		<a href="javascript:void(0);" data-bind="click: $root.actualizarCargosSimilares">
																			<spring:message code="mihabitat.botones.editar.similares" />
																		</a>
																	</li>
																</ul>
															</div>
													</sec:authorize>
														</td>
													</tr>
													</tbody>
												</table>
</div>
											</section>
										</section>

										<!-- /ko -->


									</div>
									<div class="tab-pane active" id="hr3">

										<!-- ko if : $root.estadocuenta.cargos() && $root.estadocuenta.cargos().length > 0 && $root.tabElegida() == 3-->
<div class="custom-scroll" style="overflow-y: auto; max-height:350px">
										<table id="table-cargos" class="table table-striped table-bordered table-hover" style="width: 100%">
											<thead>
											<tr>
												<th class="hasinput" style="width: 10%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.fecha" />" />
												</th>

												<th class="hasinput" style="width: 35%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.concepto" />" />
												</th>
												<th class="hasinput" style="width: 10%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.cargo.montoOriginal" />" />
												</th>
												<th class="hasinput" style="width: 5%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.recargos" />" />
												</th>
												<th class="hasinput" style="width: 5%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.descuentos" />" />
												</th>
												<th class="hasinput" style="width: 5%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.pagado" />" />
												</th>
												<th class="hasinput" style="width: 5%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.saldopendiente" />" />
												</th>
												<th class="hasinput" style="width: 5%;">
												</th>
											</tr>
											<tr>
												<th style="width: 10%;min-width: 65px">
													<spring:message code="mihabitat.cargo.fecha" />
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
												<th style="width: 5%;">
													<spring:message code="mihabitat.pago.saldopendiente" />
												</th>
												<th style="text-align: center;" style="width: 5%;">
													<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
												</th>
											</tr>
											</thead>
											<tbody data-bind="foreach : { data: $root.estadocuenta.cargos, as :'c' }">
											<tr data-bind="css: {danger: cargo.cancelado()}">
												<td data-bind="text: (moment(fecha(), 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
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
											<sec:authorize access="hasRole('Administrador')">
													<div class="btn-group">
														<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
															<i class="fa fa-cog"></i> <span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right">
															<li>
																<a href="javascript:void(0);" data-bind="click: $root.actualizarCargo">
																	<spring:message code="mihabitat.botones.editar" />
																</a>
															</li>
															<li data-bind="visible: agrupador && agrupador.id">
																<a href="javascript:void(0);" data-bind="click: $root.actualizarCargosSimilares">
																	<spring:message code="mihabitat.botones.editar.similares" />
																</a>
															</li>
														</ul>
													</div>
											</sec:authorize>
												</td>
											</tr>
											</tbody>
										</table>
</div>
										<!-- /ko -->
									</div>
									<div class="tab-pane active" id="hr4">

										<!-- ko if : $root.estadocuenta.pagos() && $root.estadocuenta.pagos().length > 0 && $root.tabElegida() == 4-->
<div class="custom-scroll" style="overflow-y: auto; max-height:350px">
										<table id="table-abonos" class="table table-striped table-bordered table-hover" style="width: 100%">
											<thead>
											<tr>
												<th class="hasinput" style="width: 5%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.folio" />" />
												</th>
												<th class="hasinput" style="width: 10%;min-width: 50px;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.fecha" />" />
												</th>
												<th class="hasinput" style="width: 20%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.contacto" />" />
												</th>
												<th class="hasinput" style="width: 10%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.metodo" />" />
												</th>
												<th class="hasinput" style="width: 10%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.referencia" />" />
												</th>
												<th class="hasinput" style="width: 20%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.comentario" />" />
												</th>
												<th class="hasinput" style="width: 10%;">
													<input type="text" class="form-control" placeholder="<spring:message code="mihabitat.pago.monto" />" />
												</th>
												<th class="hasinput" style="width: 10%;">
												</th>
											</tr>
											<tr>
												<th data-hide="phone">
													<spring:message code="mihabitat.pago.folio" />
												</th>
												<th data-class="expand">
													<spring:message code="mihabitat.pago.fecha" />
												</th>
												<th data-hide="phone">
													<spring:message code="mihabitat.contacto" />
												</th>
												<th data-hide="phone">
													<spring:message code="mihabitat.pago.metodo" />
												</th>
												<th data-hide="phone">
													<spring:message code="mihabitat.pago.referencia" />
												</th>
												<th data-hide="phone">
													<spring:message code="mihabitat.pago.comentario" />
												</th>
												<th data-hide="phone">
													<spring:message code="mihabitat.pago.monto" />
												</th>
												<th style="text-align: center;">
													<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
												</th>
											</tr>
											</thead>
											<tbody data-bind="foreach : { data: $root.estadocuenta.pagos }">
											<tr data-bind="css: {'': (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.aprobado),
                                        warning: (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.pendiente),
                                        danger: ((pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.rechazado) ||
                                            (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.cancelado))}">
												<td data-bind="text: pagoCondomino.folio"></td>
												<td data-bind="text: (moment(pagoCondomino.fecha,'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
												<td data-bind="text: pagoCondomino.contacto.nombre + ' ' + pagoCondomino.contacto.apellidoPaterno + (pagoCondomino.contacto.apellidoMaterno? (' ' + pagoCondomino.contacto.apellidoMaterno) : '')"></td>


												<td data-bind="text: pagoCondomino.metodoPago.descripcion"></td>
												<td data-bind="text: pagoCondomino.referencia"></td>
												<td data-bind="foreach : { data: pagoCondomino.estatus }">
													<label style="font-weight: 600" data-bind="visible: comentario, text: usuario.user + ': '"></label>
													<label data-bind="visible: comentario, text: comentario"></label>

												</td>
												<td  style="text-align: right;" >
													<span class="label pull-left" data-bind="text: pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion,
															css: {'label-success': (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.aprobado),
															'label-warning': (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.pendiente),
															'label-danger': ((pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.rechazado) ||
																(pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.id == AppConfig.catalogos.estatuspago.cancelado))}">

														</span>
														<span data-bind="currency: monto, symbol: $ ">

														</span>
												</td>
												<td style="text-align: center;">
													<div class="btn-group">
														<button class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown">
															<i class="fa fa-cog"></i> <span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right">
												<sec:authorize access="hasRole('Administrador')">
															<li>
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.detalle" />
																</a>
															</li>
												</sec:authorize>
															<li data-bind="visible: pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Aprobado' && pagoCondomino.comprobante">
																<a href="javascript:void(0);" data-bind="click: $root.descargarPago">
																	<spring:message code="mihabitat.pago.adjunto" />
																</a>
															</li>
												<sec:authorize access="hasRole('Administrador')">
															<li data-bind="visible: pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Pendiente'">
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.aprobar" />
																</a>
															</li>
															<li data-bind="visible: pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Pendiente'">
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.rechazar" />
																</a>
															</li>
															<li data-bind="visible: ((pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Aprobado') || (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Cancelado'))">
																<a href="javascript:void(0);" data-bind="click: $root.abrirModalEnvio">
																	<spring:message code="mihabitat.pago.enviar" />
																</a>
															</li>
												</sec:authorize>
															<li data-bind="visible: ((pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Aprobado') || (pagoCondomino.estatus[pagoCondomino.estatus.length - 1].estatus.descripcion == 'Cancelado'))">
																<a href="javascript:void(0);" data-bind="click: $root.imprimirPago">
																	<spring:message code="mihabitat.pago.imprimir" />
																</a>
															</li>
														</ul>
													</div>
													<%--<div class="btn-group">
														<a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
														<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
														<ul class="dropdown-menu dropdown-menu-right">
															<li>
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.detalle" />
																</a>
															</li>
															<li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Aprobado' && comprobante">
																<a href="javascript:void(0);" data-bind="click: $root.descargarPago">
																	<spring:message code="mihabitat.pago.adjunto" />
																</a>
															</li>
															<li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Pendiente'">
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.aprobar" />
																</a>
															</li>
															<li data-bind="visible: estatus[estatus.length - 1].estatus.descripcion == 'Pendiente'">
																<a href="javascript:void(0);" data-bind="click: $root.verPago">
																	<spring:message code="mihabitat.pago.rechazar" />
																</a>
															</li>
															<li data-bind="visible: ((estatus[estatus.length - 1].estatus.descripcion == 'Aprobado') || (estatus[estatus.length - 1].estatus.descripcion == 'Cancelado'))">
																<a href="javascript:void(0);" data-bind="click: $root.abrirModalEnvio">
																	<spring:message code="mihabitat.pago.enviar" />
																</a>
															</li>
															<li data-bind="visible: ((estatus[estatus.length - 1].estatus.descripcion == 'Aprobado') || (estatus[estatus.length - 1].estatus.descripcion == 'Cancelado'))">
																<a href="javascript:void(0);" data-bind="click: $root.imprimirPago">
																	<spring:message code="mihabitat.pago.imprimir" />
																</a>
															</li>
														</ul>
													</div>--%>
												</td>
											</tr>
											</tbody>
										</table>
</div>
										<!-- /ko -->
									</div>
								</div>

							</div>
							<!-- end widget content -->

						</div>
						<!-- end widget div -->

					</div>
					<!-- /ko -->
				</div>
			</div>
		</div>
	</article>
</div>
<jsp:include page="envio.jsp" />
<jsp:include page="../aviso-cobro/envio.jsp" />