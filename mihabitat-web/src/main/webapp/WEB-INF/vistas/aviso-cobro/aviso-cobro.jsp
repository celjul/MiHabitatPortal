<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.avisodecobro" />
					-
					<spring:message code="mihabitat.menu.avisodecobro.individual" />
				</h2>
				<div class="widget-toolbar" data-bind="visible: $root.avisodecobro.departamento.id()">
					<a class="btn btn-success" data-bind="click: $root.abrirModalEnvio">
						<i class="fa fa-envelope"></i>
						<span class="hidden-mobile hidden-tablet">
							<spring:message code="mihabitat.avisodecobro.enviar" />
						</span>
					</a>
					<a class="btn btn-success" data-bind="click: $root.imprimir">
						<i class="fa fa-print"></i>
						<span class="hidden-mobile hidden-tablet">
							<spring:message
							code="mihabitat.avisodecobro.imprimir" />
						</span>
					</a>
					<a class="btn btn-danger"
					   data-bind="click: $root.registrarPago">
						<i class="fa fa-money"></i>
						<span class="hidden-mobile hidden-tablet">
							<spring:message
							code="mihabitat.avisodecobro.pago.registrar" />
						</span>
					</a>
					<a class="btn btn-primary"
					   data-bind="click: $root.consultarPagos">
						<i class="fa fa-list"></i>
						<span class="hidden-mobile hidden-tablet">
							<spring:message
							code="mihabitat.avisodecobro.pago.consultar" />
						</span>
					</a>
				</div>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="estado-cuenta-form" class="smart-form">
						<fieldset>
							<section class="row">
								<sec:authorize access="hasRole('Administrador')">
									<section class="col col-md-4">
										<label class="label"> <span class="error-required">*</span>
											<spring:message code="mihabitat.avisodecobro.datos" />
										</label> <label class="input"> <input class="form-control"
											type="text" name="busqueda" id="busqueda"
											placeholder="<spring:message code="mihabitat.avisodecobro.datos" />"
											required="required" maxlength="128"
											data-bind="value: $root.item.label, valueUpdate : 'keyup'">
										</label>
									</section>
								</sec:authorize>
								<sec:authorize access="hasRole('Contacto')">
									<section class="col col-md-4">
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
	                                                       event : { change : $root.obtenerAvisoDeCobro }">
										</select>
										</label>
									</section>
								</sec:authorize>
								<section class="col col-md-2">
									<label class="label"> <span class="error-required">*</span>
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
								<!-- ko if : $root.item.url() -->

								<section class="col col-md-8">
									<div class="well">
									<ul style="margin: 10px" class="list-inline">
										<li style="vertical-align: top; padding: 3px 10px">
											<ul>
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.departamento" />: </strong><span
														data-bind="text: $root.avisodecobro.departamento.nombre"></span>
												</li>
												<!-- ko if : $root.avisodecobro.contacto.nombre() -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.nombre" />:</strong>
													<span data-bind="text: $root.avisodecobro.contacto.nombreCompleto"></span>
												</li>
												<!-- /ko -->
											</ul>
											</li>
										<!-- ko if : $root.avisodecobro.contacto.nombre() -->
										<li style="vertical-align: top; padding: 3px 10px">
											<ul>
												<!-- ko if : $root.avisodecobro.contacto.emails().length > 0 -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.emails" />:</strong>
													<ul style="margin-left: 25px">
														<!-- ko foreach: $root.avisodecobro.contacto.emails -->
														<li><span data-bind="text: direccion"></span></li>
														<!-- /ko -->
													</ul>
												</li>
												<!-- /ko -->
												<!-- ko if : $root.avisodecobro.contacto.telefonos().length > 0 -->
												<li style="list-style-type: none">
													<strong><spring:message code="mihabitat.persona.telefonos" />:</strong>
													<ul style="margin-left: 25px">
														<!-- ko foreach: $root.avisodecobro.contacto.telefonos -->
														<li><span data-bind="text: numero"></span></li>
														<!-- /ko -->
													</ul>
												</li>
												<!-- /ko -->
											</ul>
										</li>

										<!-- /ko -->
									</ul>
									</div>
								</section>

								<section class="col-xs-4 col-md-2" style="padding-right: 15px;padding-left: 15px;box-sizing: border-box;">
									<!-- ko if : $root.item.id() -->
									<table id="table-adeudo"
										   class="table table-bordered" style="width: 100%">
										<thead>
										<tr>
											<th data-class="expand" class="col-md-12"><spring:message
													code="mihabitat.avisodecobro.resumen.adeudototal" /></th>
										</tr>
										</thead>
										<tbody>
										<tr>
											<td
													data-bind="currency: $root.avisodecobro.adeudoTotal, symbol: $ "
													style="text-align: right;"></td>
										</tr>
										</tbody>
									</table>
									<!-- /ko -->
								</section>
								<section class="col-xs-4 col-md-2" style="padding-right: 15px;padding-left: 15px;box-sizing: border-box;">
									<!-- ko if : $root.item.id() -->
									<table id="table-saldo"
										   class="table table-bordered" style="width: 100%">
										<thead>
										<tr>
											<th data-class="expand" class="col-md-12"><spring:message
													code="mihabitat.avisodecobro.resumen.saldofavor" /></th>
										</tr>
										</thead>
										<tbody>
										<tr>
											<td
													data-bind="currency: $root.avisodecobro.saldoFavor, symbol: $ "
													style="text-align: right;"></td>
										</tr>
										</tbody>
									</table>
									<!-- /ko -->
								</section>

								<!-- /ko -->
							</section>

							<!-- ko if : $root.item.url() -->

							<section class="row">
								<section class="col col-md-6">
									<%--<h4 style="display: inline; padding-right: 25px">
										<span><spring:message
												code="mihabitat.avisodecobro.resumen.financiero" /></span>
									</h4>
									<label> <span class="error-required">*</span>
										<spring:message code="mihabitat.avisodecobro.periodo" />
									</label>--%>



									<%--<select
											name="periodo" id="periodo"
											required="required"
											data-bind="options: $root.periodos,
                                                       value: $root.periodo,
                                                       event : { change : $root.obtenerEstadoDeCuenta }">
									</select>--%>
								</section>
							</section>

							<!-- /ko -->

							<!-- ko if : $root.avisodecobro.cargos().length > 0 -->

							<section class="row">
								<section class="col col-md-3">
									<h4>
										<spring:message
											code="mihabitat.avisodecobro.cargos" />
									</h4>
								</section>
							</section>

							<section class="row">
								<section class="col col-md-12">
									<div class="custom-scroll" style="overflow-y: auto; max-height:350px">
										<table id="table-cargos"
											class="table table-striped table-bordered" style="width: 100%">
											<thead>
												<tr>
													<th ><spring:message
															code="mihabitat.cargo.fecha" /></th>
													<th ><spring:message
															code="mihabitat.cargo.concepto" /></th>
													<th ><spring:message
															code="mihabitat.avisodecobro.resumen.pagos" /></th>
													<th ><spring:message
															code="mihabitat.avisodecobro.resumen.adeudo" /></th>
												</tr>
											</thead>
											<tbody
												data-bind="foreach : { data: $root.avisodecobro.cargos }">
												<tr>
													<td data-bind="text: fecha"></td>
													<td data-bind="text: tipo.descripcion() + ': ' + concepto()"></td>
													<td style="text-align: right;height: 100%" data-bind="currency: totalPagado(), symbol: '$'"></td>
													<td style="text-align: right;height: 100%">
															<span data-bind="currency: saldoPendiente, symbol: $ "></span>
															<br data-bind="visible: ((descuento.id()?true:false) && ((moment(descuento.fecha(), 'DD-MM-YYYY').isAfter(moment($root.fecha(), 'DD-MM-YYYY'))) ||
																	  (moment(descuento.fecha(), 'DD-MM-YYYY').isSame(moment($root.fecha(), 'DD-MM-YYYY')))))">
															<span class="badge bg-color-green" style="color: #fff;font-size: 11px;padding: 5px;margin-top: 5px;"
																  data-bind="visible: ((descuento.id()?true:false) && ((moment(descuento.fecha(), 'DD-MM-YYYY').isAfter(moment($root.fecha(), 'DD-MM-YYYY'))) ||
																	  (moment(descuento.fecha(), 'DD-MM-YYYY').isSame(moment($root.fecha(), 'DD-MM-YYYY'))))),
																	  text: '* $' + (saldoPendiente() - descuentosPorAplicar()) +' Con Descuento de $' + descuentosPorAplicar() + ' pagando antes de ' + descuento.fecha()"> </span>
															<br data-bind="visible: ((recargo.id()?true:false) && ((moment(recargo.fecha(), 'DD-MM-YYYY').isAfter(moment($root.fecha(), 'DD-MM-YYYY'))) ||
																		(moment(recargo.fecha(), 'DD-MM-YYYY').isSame(moment($root.fecha(), 'DD-MM-YYYY')))))">
															<span class="badge bg-color-red" style="color: #fff;font-size: 11px;padding: 5px;margin-top: 5px;"
																  data-bind="visible: ((recargo.id()?true:false) && ((moment(recargo.fecha(), 'DD-MM-YYYY').isAfter(moment($root.fecha(), 'DD-MM-YYYY'))) ||
																		(moment(recargo.fecha(), 'DD-MM-YYYY').isSame(moment($root.fecha(), 'DD-MM-YYYY'))))),
																		text: '* $' + (parseFloat(saldoPendiente()) + parseFloat(recargosPorAplicar())) + ' Con Recargo de ' + recargosPorAplicar() + ' pagando despues de ' + recargo.fecha()"> </span>

													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</section>
							</section>

							<!-- /ko -->

						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>
<jsp:include page="envio.jsp" />