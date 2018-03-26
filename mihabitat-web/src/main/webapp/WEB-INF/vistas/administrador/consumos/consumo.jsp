<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Modal -->
<div class="modal fade" id="myModalConsumo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel"><spring:message code="mihabitat.consumos"/></h4>
			</div>
			<fieldset class="modal-body">
				<form id="consumo-form" class="smart-form">
					<fieldset>
						<section class="row">
							<section class="col col-md-4">
								<label class="label"> <span class="error-required">*</span>
									<spring:message code="mihabitat.consumo.nombre" />
								</label> <label class="input"> <input class="form-control"
																	  type="text" name="descripcion"
																	  placeholder="<spring:message code="mihabitat.consumo.nombre" />"
																	  required="required" maxlength="128"
																	  data-bind="value: $root.tipoConsumoViewModel.consumo.nombre">
							</label>
							</section>
							<section class="col col-md-8">
								<label class="label"> <span class="error-required">*</span>
									<spring:message code="mihabitat.consumo.cuenta" />
								</label> <label class="input"> <select style="width: 100%"
																	   class="select2" name="cuenta" id="cuenta" required="required"
																	   data-bind="options: $root.tipoConsumoViewModel.cuentas,
	                                                    optionsCaption : 'Seleccionar',
	                                                    optionsText: 'nombre',
	                                                    optionsValue: 'id',
	                                                    value: $root.tipoConsumoViewModel.consumo.cuenta.id,
	                                                    select2: {}, event: {change :$root.tipoConsumoViewModel.cuenta.conocerCuenta($root.tipoConsumoViewModel.cuentas)}">
							</select>
							</label>
							</section>
						</section>
						<section class="row">
							<label class="toggle">
								<header >
									<spring:message code="mihabitat.consumo.aplicarconversion" />
									<input type="checkbox" name="aplicaFactor"
										   data-bind="checked: $root.tipoConsumoViewModel.consumo.aplicaConversion"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
								</header>

							</label>
							<fieldset data-bind="visible: $root.tipoConsumoViewModel.consumo.aplicaConversion" class=" bg-color-lighten">
										<section class="col col-md-3" >
											<label class="label"> <span class="error-required">*</span>
												<spring:message code="mihabitat.consumo.unidadOrigen" />
											</label> <label class="input"> <select style="width: 100%"
																				   class="select2" name="unidadLectura" id="unidadLectura" required="required"
																				   data-bind="options: $root.tipoConsumoViewModel.catalogoUnidad,
															optionsCaption : 'Seleccionar',
															optionsText: 'descripcion',
															optionsValue: 'id',
															value: $root.tipoConsumoViewModel.consumo.unidad.id,
															select2: {}"></select>
										</label>
										</section>
										<section class="col col-md-1" class="form-group">
											<label class="label">
												<br>
											</label>
											<label class="label" style="font-size: 20px; font-weight: bolder">
												>>
											</label>
										</section>
										<section class="col col-md-3" >
											<label class="label"> <span class="error-required">*</span>
												<spring:message code="mihabitat.consumo.unidadConversion" />
											</label> <label class="input"> <select style="width: 100%"
																				   class="select2" name="unidadConversion" id="unidadConversion" required="required"
																				   data-bind="options: $root.tipoConsumoViewModel.catalogoUnidad,
																			optionsCaption : 'Seleccionar',
																			optionsText: 'descripcion',
																			optionsValue: 'id',
																			value: $root.tipoConsumoViewModel.consumo.unidadConversion.id,
																			select2: {}"></select>
										</label>
										</section>
										<section class="col col-md-1" class="form-group">

										</section>
										<section class="col col-md-3" class="form-group">
											<label class="label"> <span class="error-required">*</span>
												<spring:message code="mihabitat.cargo.simple.factor" />
											</label> <label class="input"> <input
												class="form-control" type="text" name="factorConversion"
												id="factorConversion"
												min="0"
												required="required"
												data-bind="value: $root.tipoConsumoViewModel.consumo.factorConversion">
										</label>
										</section>

							</fieldset>
							<br>
							<br>
						</section>
						<section class="row">
							<section class="col col-md-4" data-bind="visible: !$root.tipoConsumoViewModel.consumo.aplicaConversion()">
								<label class="label"> <span class="error-required">*</span>
									<spring:message code="mihabitat.consumo.unidad" />
								</label> <label class="input"> <select style="width: 100%"
																	   class="select2" name="unidad" id="unidad" required="required"
																	   data-bind="options: $root.tipoConsumoViewModel.catalogoUnidad,
															optionsCaption : 'Seleccionar',
															optionsText: 'descripcion',
															optionsValue: 'id',
															value: $root.tipoConsumoViewModel.consumo.unidad.id,
															select2: {}"></select>
							</label>
							</section>

							<section class="col col-md-3" class="form-group">
								<label class="label"><spring:message
										code="mihabitat.consumo.tipo" />
								</label>
								<section class="inline-group"
										 data-bind="foreach: { data: $root.tipoConsumoViewModel.catalogoTipoConsumo}">
									<label class="radio"> <input type="radio"
																 name="tipoConsumo"
																 data-bind="checkedValue: id, checked: $root.tipoConsumoViewModel.consumo.catalogoTipoConsumo.id">
										<i></i> <span data-bind="text : descripcion"></span>
									</label>
								</section>
							</section>
							<section class="col col-md-4" class="form-group">
								<label class="label">
									<spring:message code="mihabitat.cargo.prorrateo.costo" />
								</label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-dollar"></i></span>
										<input
											class="form-control number money" type="text" name="costoUnidad"
											id="costoUnidad"
											min="0"
											data-bind="value: $root.tipoConsumoViewModel.consumo.costoPorUnidad">
								</div>
							</section>
						</section>
					</fieldset>
				</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Cancelar
						</button>
						<button style="float: right" type="button" class="btn btn-primary"
								data-bind="click: $root.guardarConsumo, visible: !$root.tipoConsumoViewModel.consumo.id()">
							<spring:message code="mihabitat.botones.guardar" />
						</button>
						<button style="float: right" type="button" class="btn btn-primary"
								data-bind="click: $root.actualizarConsumo, visible: $root.tipoConsumoViewModel.consumo.id()">
							<spring:message code="mihabitat.botones.actualizar" />
						</button>
					</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.consumos" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="consumo-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.consumo.nombre" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="descripcion"
										placeholder="<spring:message code="mihabitat.consumo.nombre" />"
										required="required" maxlength="128"
										data-bind="value: $root.consumo.nombre">
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.consumo.cuenta" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="cuenta" id="cuenta" required="required"
										data-bind="options: $root.cuentas,
	                                                    optionsCaption : 'Seleccione una opción',
	                                                    optionsText: 'nombre',
	                                                    optionsValue: 'id',
	                                                    value: $root.consumo.cuenta.id,
	                                                    select2: {}, event: {change :$root.cuenta.conocerCuenta($root.cuentas)}">
									</select>
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.consumo.unidad" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="unidad" id="unidad" required="required"
										data-bind="options: $root.catalogoUnidad,
												optionsCaption : 'Seleccione una opción',
												optionsText: 'descripcion',
												optionsValue: 'id',
												value: $root.consumo.unidad.id,
												select2: {}"></select>
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"><spring:message
											code="mihabitat.consumo.tipo" />
									</label>
									<section class="inline-group"
										data-bind="foreach: { data: $root.catalogoTipoConsumo}">
										<label class="radio"> <input type="radio"
											name="tipoConsumo"
											data-bind="checkedValue: id, checked: $root.consumo.catalogoTipoConsumo.id">
											<i></i> <span data-bind="text : descripcion"></span>
										</label>
									</section>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label">
										<spring:message code="mihabitat.cargo.prorrateo.costo" />
									</label> <label class="input"> <input
										class="form-control number money" type="text" name="costoUnidad"
										id="costoUnidad"
										placeholder="<spring:message code="mihabitat.cargo.prorrateo.costo" />"
										min="0"
										data-bind="value: $root.consumo.costoPorUnidad">
									</label>
								</section>
							</section>
							<section class="row"></section>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.consumo.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.consumo.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>--%>
