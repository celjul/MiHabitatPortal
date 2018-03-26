<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.proveedores.pago" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="pagproveedores-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-3">
									<label class="label">
										<span class="error-required">*</span>
										<spring:message code="mihabitat.proveedores.pago.proveedor" />
									</label>
									<label class="input">
										<select style="width: 100%"
												class="select2" name="proveedor" id="proveedor" required="required"
												data-bind="options: $root.proveedores,
													optionsCaption : 'Seleccione',
													optionsText: 'nombre',
													optionsValue: 'id',
													value: $root.pago.proveedor.id,
													select2: {},
													event : { change : $root.obtenerFacturasxPagar }">
										</select>
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.proveedores.pago.fecha" />
									</label>
									<input class="form-control" type="text" name="fechaPago" id="fechaPago"
										   placeholder="<spring:message code="mihabitat.proveedores.pago.fecha" />"
										   required="required" readonly="readonly"
										   data-bind="value: $root.pago.fechaPago">
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.proveedores.pago.cuenta" /> -
										<spring:message code="mihabitat.proveedores.pago.saldo" />
										<span data-bind="currency: $root.pago.saldo(), symbol: $"/></span>
									</label><select style="width: 100%"
													class="select2" name="cuenta" id="cuenta" required="required"
													data-bind="options: $root.cuenta,
													optionsCaption : 'Seleccione',
													optionsText: 'nombre',
													optionsValue: 'id',
													value: $root.pago.cuenta.id,
													select2: {},
													event: {change : $root.cuentaById}">
								</select>
								</section>
								<section class="col col-md-3">
									<label class="label"> <spring:message code="mihabitat.proveedores.pago.monto" /> <!-- ko if: !$root.pago.id() -->
										- <spring:message code="mihabitat.pago.total" /> <span
												data-bind="currency: $root.totalProveedor, symbol: $"></span> <!-- /ko-->
									</label>
									<label class="input"> <input
										class="form-control number money" type="text" name="monto" id="monto"
										placeholder="<spring:message code="mihabitat.proveedores.pago.monto" />"
										required="required" min="0.1"
										data-bind="value: $root.pago.total, event: {change : $root.valida}, enable: $root.pago.cuenta.id()">
									</label>
								</section>

							</section>

							<section class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label">  <span class="error-required">*</span>
										<spring:message code="mihabitat.proveedores.pago.metodoPago" />
									</label>
									<label class="select">
										<select name="metodo" required="required"
												data-bind="options: $root.metodosPago,
												optionsCaption : 'Seleccione',
												optionsText: 'descripcion',
												optionsValue: 'id',
												value: $root.pago.metodoPago.id">
										</select>
										<i></i>
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label">  <span class="error-required">*</span>
										<spring:message code="mihabitat.proveedores.pago.referencia" />
									</label>
									<label class="input">
										<input class="form-control" type="text" name="referencia"
											   placeholder="<spring:message code="mihabitat.proveedores.pago.referencia" />"
											   required="required"
											   data-bind="value: $root.pago.referencia">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label">
										<spring:message code="mihabitat.proveedores.pago.comentario" />
									</label>
									<textarea class="form-control" rows="3" name="comentario"
											  id="comentario" maxlength="256"
											  data-bind="value: $root.pago.comentario">
									</textarea>
								</section>
							</section>

						</fieldset>

						<footer>
							<button style="float: right" type="button"
									class="btn btn-primary"
									data-bind="click: $root.guardar">
								<spring:message code="mihabitat.botones.guardar" />
							</button>

						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>


	<article class="col-sm-12 col-md-12 col-lg-12">
		<section class="row">
			<article class="col-sm-12 col-md-12 col-lg-12">
				<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2><spring:message code="mihabitat.menu.facturasporpagar"/></h2>
						<h2>
							<label class="label"> <spring:message code="mihabitat.proveedores.pago.monto" />
								- <spring:message code="mihabitat.proveedores.pago.restante" /> <span
										data-bind="currency: $root.totalRestante, symbol: $"></span>
							</label>
						</h2>

					</header>
					<div>
						<div class="jarviswidget-editbox"></div>
						<div class="widget-body no-padding">
							<table id="table-facturasxp" class="table table-striped table-bordered table-hover" style="width: 100%">
								<thead>

								<tr>
									<th data-class="expand">
										id
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.facturasporpagar.uuid" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.facturasporpagar.fechaRecepcion" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.proveedores.pago.antiguedad" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.proveedores.pago.monto" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.pago.pagado" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.pago.saldopendiente" />
									</th>
									<th data-class="expand">
										<spring:message code="mihabitat.pago.pagar" />
										<progress name="pctje" data-bind="value: $root.porcentajeRestante()"></progress>
									</th>
									<td data-class="expand" class="col-md-1 smart-form"><label class="checkbox">
										<input type="checkbox" name="checkbox-inline" data-bind="checked: seleccionarTodo, enable : $root.pagoTotal() > 0">
										<i></i>&nbsp;&nbsp;<spring:message code="mihabitat.estadocuenta.masivo.seleccionar.todos" />
									</label></td>
								</tr>
								</thead>

								<tbody data-bind="foreach : { data: $root.facturasxp, as : 'factura' }">
									<tr data-bind="css: {danger:  _antiguedad()<1, warning: _antiguedad()>0 && _antiguedad() <6}" >
										<td data-bind="text: id" > </td>
										<td>
											<a data-bind="tooltip: { title: tooltipTitle, placement: 'tooltipPlacement' }">uuid</a>
										</td>
										<td data-bind="text: fechaEmision" > </td>
										<td data-bind="text: fechaVencimiento"></td>
										<td data-bind="text: _antiguedad()<1 ? (_antiguedad() * -1) : _antiguedad() "></td>
										<td data-bind="currency: total, symbol: $ "
											class="col-md-1" style="text-align: right;"></td>
										<td data-bind="currency: pagado, symbol : $"></td>
										<td data-bind="currency: pendiente, symbol: $"></td>
										<td><input class="form-control number money" type="text" name="monto2" id="monto2"
												   placeholder="<spring:message code="mihabitat.pago.monto" />"
												   data-bind="value: pagoTemporal, event : {change : $root.calculaMontosManual},
																enable : $root.pagoTotal() > 0">
										</td>
										<td> <input type="checkbox" name="aplicaPago" data-bind="checked: aplicaPago,
														event : {change: $root.verificarPaloma(factura, false)}, enable : $root.pagoTotal() > 0"> </td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</article>
		</section>
	</article>

</div>
