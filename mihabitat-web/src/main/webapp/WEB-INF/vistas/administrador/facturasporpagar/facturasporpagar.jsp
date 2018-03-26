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
					<spring:message code="mihabitat.facturasporpagar" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="facturasporpagar-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-6">
									<label class="label">
										<span class="error-required">*</span>
										<spring:message code="mihabitat.proveedor" />
									</label>
									<label class="input">
										<select style="width: 100%"
												class="select2" name="proveedor" id="proveedor" required="required"
												data-bind="options: $root.proveedores,
											optionsCaption : 'Seleccione una opción',
											optionsText: 'nombre',
											optionsValue: 'id',
											value: $root.facturasxp.proveedor.id,
											select2: {},
											event: { change : $root.proveedorById }">
										</select>
									</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.fechaRecepcion" />
									</label> <input class="form-control" type="text" name="fechaRecepcion" id="fechaRecepcion"
													placeholder="<spring:message code="mihabitat.facturasporpagar.fechaRecepcion" />"
													required="required" readonly="readonly"
													data-bind="value: $root.facturasxp.fechaEmision,
													event: { change : $root.calculaFechaVencimiento }">
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />
									</label> <input class="form-control" type="text" name="fechaVencimiento" id="fechaVencimiento"
													placeholder="<spring:message code="mihabitat.facturasporpagar.fechaVencimiento" />"
													required="required" readonly="readonly"
													data-bind="value: $root.facturasxp.fechaVencimiento">
								</section>
							</section>

							<section class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.foliofiscal" />
									</label>
									<label class="input">
										<input class="form-control" type="text" name="folioFiscal"
											   placeholder="<spring:message code="mihabitat.facturasporpagar.foliofiscal" />"
											   maxlength="36" minlength="32" required="required"
											   data-bind="value: $root.facturasxp.uuid, event:{ change: $root.facturasxp.existe }">
									</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.tipo" />
									</label>
									<label class="select">
										<select name="tipo-cfdi" required="required"
												data-bind="options: $root.tipo_cfdi,
												optionsCaption : 'Seleccione',
												optionsText: 'descripcion',
												optionsValue: 'descripcion',
												value: $root.facturasxp.tipo">
										</select>
										<i></i>
									</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.formaPago" />
									</label>
									<label class="select">
										<select name="formaPago" required="required"
												data-bind="options: $root.forma_pago,
												optionsCaption : 'Seleccione',
												optionsText: 'descripcion',
												optionsValue: 'descripcion',
												value: $root.facturasxp.formadepago">
										</select>
										<i></i>
									</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label">  <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.metodoPago" />
									</label>
									<label class="select">
										<select name="metodo" required="required"
												data-bind="options: $root.metodosPago,
												optionsCaption : 'Seleccione',
												optionsText: 'descripcion',
												optionsValue: 'descripcion',
												value: $root.facturasxp.metododepago">
										</select>
										<i></i>
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label">  <span class="error-required">*</span>
										<spring:message code="mihabitat.facturasporpagar.condicionPago" />
									</label>
									<label class="input">
										<input class="form-control" type="text" name="condicionPago"
											   placeholder="<spring:message code="mihabitat.facturasporpagar.condicionPago" />"
											   required="required" data-bind="value: $root.facturasxp.condiciondepago">
									</label>
								</section>
								<br/><br>
								<section class="col col-md-5" class="form-group" data-bind="visible: $root.facturasxp.proveedor.rfc()">
									<label data-bind="text: '<spring:message code="mihabitat.proveedor.rfc"/>: ' + $root.facturasxp.rfc()"/>
								</section>
							</section>

						</fieldset>


						<section class="row">
							<section class="col col-12">
								<header>
									<spring:message code="mihabitat.facturasporpagar.conceptos" />
									<a href="javascript:void(0);" class="button-icon pull-right" id="contacto-button"
									   			data-bind="click: $root.facturasxp.agregarConcepto">
										<i class="fa fa-plus-circle"></i>
									</a>
								</header>

								<fieldset>
									<table id="conceptos-cfdi" class="table">
										<thead>
										<tr>
											<th width="10%"><spring:message code="mihabitat.facturasporpagar.cantidad"/></th>
											<th width="10%"><spring:message code="mihabitat.facturasporpagar.unidad"/></th>
											<th width="10%"><spring:message code="mihabitat.facturasporpagar.codigo"/></th>
											<th width="30%"><spring:message code="mihabitat.facturasporpagar.concepto"/></th>
											<th width="10%"><spring:message code="mihabitat.facturasporpagar.precio"/></th>
											<th width="15%"><spring:message code="mihabitat.facturasporpagar.importe"/></th>
											<th width="20%">
												<i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
												<spring:message code="mihabitat.facturasporpagar.cuenta" />
											</th>
										</tr>
										</thead>

										<tbody data-bind="foreach : {data: $root.facturasxp.conceptos}">
										<tr>
											<td><label class="input">
												<input class="form-control number" type="text" name="cantidad"
													   required="required" min="1" maxlength="5"
													   data-bind="value: cantidad , attr : {name:'cantidad' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<input class="form-control" type="text" name="unidad"
													   required="required" min="1" maxlength="10"
													   data-bind="value: unidadDeMedida, attr : {name:'unidad' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<input class="form-control" type="text" name="codigo"
													   required="required" min="1" maxlength="10"
													   data-bind="value: codigo, attr : {name:'codigo' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<input class="form-control" type="text" name="concepto"
													   required="required" min="1" maxlength="100"
													   data-bind="value: descripcion, attr : {name:'concepto' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<input class="form-control number" type="text" name="precioUnitario"
													   required="required" min="1" maxlength="10"
													   data-bind="value: precioUnitario, attr : {name:'precioUnitario' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<input class="form-control" type="number" name="importe" disabled="true"
													   data-bind="value: importe, , attr : {name:'importe' + $index()}">
												</label>
											</td>
											<td><label class="input">
													<select style="width: 100%"
															class="select2" name="cuenta" id="cuenta" required="required"
															data-bind="options: $root.cuenta,
													optionsText: 'nombre',
													optionsValue: 'id',
													value: cuenta.id,
													select2: {}">
													</select>
												</label>
											</td>
											<td>
												<a href="javascript:void(0);" class="button-icon" data-bind="click : $parent.facturasxp.removerConcepto">
													<i class="fa fa-minus-circle"></i>
												</a>
											</td>

										</tr>

										</tbody>
									</table>
								</fieldset>

							</section>
						</section>


						<section class="row">
							<section class="col col-md-2" class="form-group">
								<label class="label"> <spring:message code="mihabitat.facturasporpagar.impuestoRetenido" />
								</label>
							</section>
							<section class="col col-md-2" class="form-group">
								<label class="input">
									<input class="form-control number money" type="text" name="impuestoRetenido"
										   id="impuestoRetenido" placeholder="<spring:message code="mihabitat.facturasporpagar.impuestoRetenido" />"
										   required="required" data-bind="value: $root.facturasxp.impuestoRetenido">
								</label>
							</section>
						</section>

						<section class="row">
							<section class="col col-md-2" class="form-group">
								<label class="label"> <spring:message code="mihabitat.facturasporpagar.impuestoTrasladado" />
							</label>
							</section>
							<section class="col col-md-2" class="form-group">
							<label class="input">
							<input class="form-control number money" type="text" name="impuestoTrasladado"
								   id="impuestoTrasladado" placeholder="<spring:message code="mihabitat.facturasporpagar.impuestoTrasladado" />"
								   required="required" data-bind="value: $root.facturasxp.impuestoTrasladado">
							</label>
							</section>
						</section>
						<section class="row">
							<section class="col col-md-2" class="form-group">
								<label class="label"> <spring:message code="mihabitat.facturasporpagar.total" />
							</label>
							</section>
							<section class="col col-md-2" class="form-group">
							<label class="input">
								<input class="form-control number money" type="text" name="total"
									   id="total" placeholder="<spring:message code="mihabitat.facturasporpagar.total" />"
									   required="required" min="1"
									   data-bind="value: $root.facturasxp.totalCalculado">
							</label>
							</section>
						</section>

						<footer>
							<button type="button" class="btn btn-primary"
									data-bind="click: $root.guardar, visible: !$root.facturasxp.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button type="button" class="btn btn-primary"
									data-bind="click: $root.actualizar, visible: $root.facturasxp.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>

						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>
