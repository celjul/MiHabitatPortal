<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.proveedor" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">

					<form id="proveedor-form" class="smart-form">
						<fieldset>
								<div class="row">
									<section class="col col-md-3" class="form-group">
										<label class="toggle">
											<input type="checkbox" name="tipoPersona" data-bind="checked: $root.proveedor.esFacturable">
											<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.proveedor.esFacturable" />
										</label>
									</section>
									<section class="col col-md-3" class="form-group" data-bind="visible: $root.proveedor.esFacturable">
										<label class="toggle">
											<input type="checkbox" name="tipoPersona" data-bind="checked: $root.proveedor.tipoPersona">
											<i data-swchon-text="Fisica" data-swchoff-text="Moral"></i><spring:message code="mihabitat.proveedor.tipoPersona" />
										</label>
									</section>
								</div>
								<div class="row" data-bind="visible: $root.proveedor.esFacturable">
									<section class="col col-md-2" class="form-group">
										<label class="label">
											<span class="error-required">*</span>
											<spring:message code="mihabitat.proveedor.rfc" />
										</label>
										<label class="input">
											<input class="form-control" type="text" name="rfc" id="rfc"
												   placeholder="<spring:message code="mihabitat.proveedor.rfc" />"
												   required="required"
												   data-bind="value: $root.proveedor.rfc, event:{ change: $root.proveedor.existe },
												   attr:{minlength: $root.lengthTipoPersona(), maxlength: $root.lengthTipoPersona()}" >
										</label>
									</section>
									<section class="col col-md-6" class="form-group">
										<label class="label">
											<span class="error-required">*</span>
											<spring:message code="mihabitat.proveedor.razonSocial" />
										</label>
										<label class="input">
											<input class="form-control" type="text" name="razonSocial"
												   placeholder="<spring:message code="mihabitat.proveedor.razonSocial" />"
												   maxlength="60" required="required"
												   data-bind="value: $root.proveedor.razonSocial">
										</label>
									</section>
									<section class="col col-md-2" class="form-group">
										<label class="label">
											<span> </span>
											<spring:message code="mihabitat.proveedor.diasCredito" />
										</label>
										<label class="input">
											<input class="form-control" type="number" name="diasCredito"
												   placeholder="<spring:message code="mihabitat.proveedor.diasCredito" />"
												   min="1" maxlength="3"
												   data-bind="value: $root.proveedor.diasCredito">
										</label>
									</section>
								</div>



								<div class="row">
									<section class="col col-md-6">
										<label class="label" data-bind="visible: $root.proveedor.esFacturable()">
											<span class="error-required">*</span>
											<spring:message code="mihabitat.proveedor.nombreComercial"/>
										</label>
										<label class="label" data-bind="visible: !$root.proveedor.esFacturable()">
											<span class="error-required">*</span>
											<spring:message code="mihabitat.proveedor.nombre"/>
										</label>
										<label class="input">
											<input class="form-control" type="text" name="descripcion"
												placeholder="<spring:message code="mihabitat.proveedor.nombre" />"
												required="required" maxlength="128"
												data-bind="value: $root.proveedor.nombre">
										</label>
									</section>
									<!--<section class="col col-md-2">
										<br/>
										<label class="toggle">
											<input type="checkbox" name="esEmpleado" data-bind="checked: $root.proveedor.esEmpleado">
											<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.proveedor.esEmpleado" />
										</label>
									</section>-->
									<section class="col col-md-2">
										</section>
									<section class="col col-md-2" class="form-group">
										<br/>
										<label class="toggle">
											<input type="checkbox" name="activo" data-bind="checked: $root.proveedor.activo">
											<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.proveedor.activo" />
										</label>
									</section>
								</div>

								<div class="row">
									<section class="col col-md-4" class="form-group">
										<label class="label"> <span class="error-required">*</span>
											<spring:message code="mihabitat.cuenta" />
										</label>
										<label class="input">
										<select style="width: 100%"
											class="select2" name="cuenta" id="cuenta" required="required"
											data-bind="options: $root.cuenta,
													optionsCaption : 'Seleccione',
													optionsText: 'nombre',
													optionsValue: 'id',
													value: $root.proveedor.cuenta.id,
													select2: {}">
										</select>
										</label>
									</section>
									<section class="col col-md-6" class="form-group">
										<label class="label"> <span class="error-required">*</span>
											<spring:message code="mihabitat.proveedor.giros" />
										</label>
										<label class="input">
											<select style="width: 100%;"
												class="select2" name="giros" id="giros" required="required"
												multiple="multiple"
												data-bind="options: $root.giross,
															optionsText: 'descripcion',
															optionsValue: 'id',
															selectedOptions : $root.proveedor.giros,
															select2: { placeholder: 'Selecciona opciones' }">
											</select>
										</label>
									</section>
								</div>

						</fieldset>
					</form>


						<div class="jarviswidget-editbox"></div>
						<div class="widget-body">

							<ul id="myTab1" class="nav nav-tabs bordered">
								<!-- ko foreach: { data: $root.proveedor.contactos} -->
								<li class="contacto">
									<a data-toggle="tab" data-bind="attr: {href : '#div-contacto-' + $index(), id : 'a-contacto-' + $index()}">
										<i class="fa fa-lg fa-user"></i>
										<span class="hidden-mobile hidden-tablet" data-bind="text: nombreCompleto" style="margin: 0px 10px"></span>
										<button class="close" data-bind="click: $root.proveedor.removerContacto">x</button>
									</a>
								</li>
								<!-- /ko -->
								<li class="pull-right">
									<a href="javascript:void(0);" class="button-icon" id="contacto-button" data-bind="click: $root.proveedor.agregarContacto">
										<i class="fa fa-plus-circle"></i> <spring:message code="mihabitat.botones.agregar.contacto" />
									</a>
								</li>
							</ul>

							<div id="myTabContent1" class="tab-content padding-10">
								<!-- ko foreach: { data: $root.proveedor.contactos} -->
								<div class="tab-pane fade" data-bind="attr: {id : 'div-contacto-' + $index()}">
									<form class="smart-form" data-bind="attr: {id : 'form-contacto-' + $index()}">
										<fieldset>
											<section class="row">
												<section class="col col-md-4" class="form-group">
													<label class="label">
														<span class="error-required">*</span>
														<spring:message code="mihabitat.persona.nombre" />
													</label>
													<label class="input">
														<input class="form-control" type="text" name="nombre"
															   placeholder="<spring:message code="mihabitat.persona.nombre" />"
															   required="required" maxlength="128"
															   data-bind="value: nombre">
													</label>
												</section>
												<section class="col col-md-3" class="form-group">
													<label class="label">
														<spring:message code="mihabitat.persona.apellidoPaterno" />
													</label>
													<label class="input">
														<input class="form-control" type="text" name="apellidoPaterno"
															   placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
															   required="required" maxlength="128"
															   data-bind="value: apellidoPaterno">
													</label>
												</section>
												<section class="col col-md-3" class="form-group">
													<label class="label">
														<spring:message code="mihabitat.persona.apellidoMaterno" />
													</label>
													<label class="input">
														<input class="form-control" type="text" name="apellidoMaterno"
															   placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
															   maxlength="128"
															   data-bind="value: apellidoMaterno">
													</label>
												</section>
											</section>
											<section class="row">
												<section class="col col-6">
													<header>
														<spring:message code="mihabitat.persona.emails" />
														<a href="javascript:void(0);" class="button-icon pull-right" id="contacto-button" data-bind="click: agregarEmail">
															<i class="fa fa-plus-circle"></i>
														</a>
													</header>
													<fieldset>
														<!-- ko foreach: { data: emails} -->
														<section class="row">
															<section class="col col-6" class="form-group">
																<label class="input">
																	<input class="form-control" type="email" name="direccion"
																		   placeholder="<spring:message code="mihabitat.email.direccion" />"
																		   required="required" maxlength="64"
																		   data-bind="value: direccion">
																</label>
															</section>
															<section class="col col-4" class="form-group">
																<label class="select">
																	<select name="tipo-email"
																			required="required"
																			data-bind="options: $root.catalogoEmail,
																optionsCaption : 'Seleccione',
																optionsText: 'descripcion',
																optionsValue: 'id',
																value: tipo.id">
																	</select>
																	<i></i>
																</label>
															</section>
															<section class="col col-1" class="form-group">
																<a href="javascript:void(0);" class="button-icon"
																   data-bind="click : $parent.removerEmail">
																	<i class="fa fa-minus-circle"></i>
																</a>
															</section>
														</section>
														<!-- /ko -->
													</fieldset>
												</section>
												<section class="col col-6">
													<header>
														<spring:message code="mihabitat.persona.telefonos" />
														<a href="javascript:void(0);" class="button-icon pull-right" id="contacto-button" data-bind="click: agregarTelefono">
															<i class="fa fa-plus-circle"></i>
														</a>
													</header>
													<fieldset>
														<!-- ko foreach: { data: telefonos} -->
														<section class="row">
															<section class="col col-2" class="form-group">
																<label class="input">
																	<input class="form-control" type="text" name="lada"
																		   placeholder="<spring:message code="mihabitat.telefono.lada" />"
																		   maxlength="5" data-bind="value: lada">
																</label>
															</section>
															<section class="col col-4" class="form-group">
																<label class="input">
																	<input class="form-control" type="text" name="numero"
																		   placeholder="<spring:message code="mihabitat.telefono.numero" />"
																		   required="required" maxlength="16"
																		   data-bind="value: numero">
																</label>
															</section>
															<section class="col col-4" class="form-group">
																<label class="select">
																	<select name="tipo-telefono"
																			required="required"
																			data-bind="options: $root.catalogoTelefono,
																	optionsCaption : 'Seleccione',
																	optionsText: 'descripcion',
																	optionsValue: 'id',
																	value: tipo.id">
																	</select>
																	<i></i>
																</label>
															</section>
															<section class="col col-2" class="form-group">
																<label class="input">
																	<input class="form-control" type="text" name="extension"
																		   placeholder="<spring:message code="mihabitat.telefono.extension" />"
																		   maxlength="5" data-bind="value: extension, visible: tipo.id() == AppConfig.catalogos.persona.telefono.oficina">
																</label>
															</section>
															<section class="col col-1" class="form-group">
																<a href="javascript:void(0);" class="button-icon" data-bind="click : $parent.removerTelefono">
																	<i class="fa fa-minus-circle"></i>
																</a>
															</section>
														</section>
														<!-- /ko -->
													</fieldset>
												</section>
											</section>
										</fieldset>
									</form>
								</div>
								<!-- /ko -->
							</div>
						</div>


						<div class="panel-group smart-accordion-default">
							<ul class="nav nav-tabs bordered">

								<li class="pull-right">
									<a href= class="button-icon" id="domicilio-button" data-bind="click: $root.agregarDomicilio">
										<i class="fa fa-lg fa-angle-down pull-right"></i> <spring:message code="mihabitat.botones.domicilio" />
									</a>
								</li>
							</ul>

							<div class="tab-content padding-10">
									<form class="smart-form">
										<fieldset data-bind="visible: $root.addDireccion() || $root.proveedor.direccion.id">
											<section class="row">

												<section class="col col-md-2">
													<label class="label">
														<span class="error-required">*</span><spring:message code="mihabitat.direccion.pais" />
													</label>
													<label class="input">
														<select style="width: 100%"
																class="select2" name="proveedor-pais" id="proveedor-pais"
																required="required"
																data-bind="options: paises,
																optionsCaption : 'Seleccione',
																optionsText: 'nombre',
																optionsValue: 'id',
																value: proveedor.direccion.colonia.municipio.estado.pais.id,
																select2: {}">
														</select>
													</label>
												</section>

												<section class="col col-sm-2">
													<label class="label"><span class="error-required">*
														</span><spring:message code="mihabitat.direccion.estado" />
													</label>
													<label class="input">
														<select style="width: 100%"
														  class="select2" name="proveedor-estado" id="proveedor-estado" required="required"
														  data-bind="options: proveedor.direccion.colonia.municipio.estado.pais.estados,
															optionsText: 'nombre',
															optionsValue: 'id',
															optionsCaption: 'Seleccione',
															value : proveedor.direccion.colonia.municipio.estado.id,
															select2: {}">
														</select>
													</label>
												</section>

												<section class="col col-md-3" class="form-group">
													<label class="label"><span class="error-required">*
														</span><spring:message code="mihabitat.direccion.municipio" />
													</label>
													<label class="input"> <select style="width: 100%"
														  class="select2" name="proveedor-municipio" id="proveedor-municipio" required="required"
														  data-bind="options: proveedor.direccion.colonia.municipio.estado.municipios,
															optionsText: 'nombre',
															optionsValue: 'id',
															optionsCaption: 'Seleccione',
															value :proveedor.direccion.colonia.municipio.id,
															select2: {}">
													</select>
													</label>
												</section>

												<section class="col col-md-3" class="form-group">
													<label class="label"><span class="error-required">*
														</span><spring:message code="mihabitat.direccion.colonia" />
													</label>
													<label class="input"><select style="width: 100%"
															class="select2" name="proveedor-colonia" id="proveedor-colonia" required="required"
															data-bind="options: proveedor.direccion.colonia.municipio.colonias,
															optionsText: 'nombre',
															optionsValue: 'id',
															optionsCaption: 'Seleccione',
															value : proveedor.direccion.colonia.id,
															select2: {}">
													</select>
													</label>
												</section>
											</section>

											<section class="row">
												<section class="col col-md-4" class="form-group">
													<label class="label"><span class="error-required">*
														</span><spring:message code="mihabitat.direccion.calle" />
													</label>
													<label class="input"> <input class="form-control"
															 type="text" name="proveedor-calle" id="proveedor-calle"
															 placeholder="<spring:message code="mihabitat.direccion.calle" />"
															 required="required" maxlength="128"
															 data-bind="value: $root.proveedor.direccion.calle">
													</label>
												</section>
												<section class="col col-md-2" class="form-group">
													<label class="label"><span class="error-required">*
														</span><spring:message code="mihabitat.direccion.noExterior" />
													</label>
													<label class="input"> <input class="form-control"
															 type="text" name="proveedor-noExt" id="proveedor-noExt"
															 placeholder="<spring:message code="mihabitat.direccion.noExterior" />"
															 required="required" maxlength="32"
															 data-bind="value: $root.proveedor.direccion.noExterior">
													</label>
												</section>
												<section class="col col-md-2" class="form-group">
													<label class="label"> <spring:message
															code="mihabitat.direccion.noInterior" />
													</label>
													<label class="input"> <input class="form-control"
															 type="text" name="proveedor-noInt" id="proveedor-noInt"
															 placeholder="<spring:message code="mihabitat.direccion.noInterior" />"
															 maxlength="32"
															 data-bind="value: $root.proveedor.direccion.noInterior">
													</label>
												</section>
												<section class="col col-md-2" class="form-group">
													<label class="label"> <spring:message
															code="mihabitat.direccion.cp" />
													</label>
													<label class="input"> <input class="form-control"
															 type="text" name="proveedor-cp" id="proveedor-cp"
															 placeholder="<spring:message code="mihabitat.direccion.cp" />"
															 required="required" maxlength="5" disabled="disabled"
															 data-bind="value: $root.proveedor.direccion.colonia.codigoPostal">

													</label>
												</section>
											</section>
											<section class="row">
												<section class="col col-md-8" class="form-group">
													<label class="label">
														<spring:message code="mihabitat.direccion.referencias" />
													</label>
													<label class="textarea"> <textarea cols="20" rows="2"
														   id="proveedor-referencia"
														   placeholder="<spring:message code="mihabitat.direccion.referencias" />"
														   maxlength="512" name="referencia"
														   data-bind="value : $root.proveedor.direccion.referencias">
													</textarea>
													</label>
												</section>
											</section>
										</fieldset>
									</form>
							</div>
						</div>
						<div align="right">
							<footer>
								<button type="button" class="btn btn-primary"
										data-bind="click: $root.guardar, visible: !$root.proveedor.id()">
									<spring:message code="mihabitat.botones.guardar" />
								</button>
								<button type="button" class="btn btn-primary"
										data-bind="click: $root.actualizar, visible: $root.proveedor.id()">
									<spring:message code="mihabitat.botones.actualizar" />
								</button>
							</footer>
						</div>

				</div>
			</div>
		</div>
	</article>
</div>
