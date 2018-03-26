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
					<spring:message code="mihabitat.contacto" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="contacto-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label">
										<span class="error-required">*</span>
										<spring:message
											code="mihabitat.persona.nombre" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.nombre" />"
										required="required" maxlength="128"
										data-bind="value: $root.contacto.persona.nombre, disable: $root.usuario.id()">
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> 
										<span class="error-required">*</span>
										<spring:message
											code="mihabitat.persona.apellidoPaterno" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="apellidoPaterno"
										placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
										required="required" maxlength="128"
										data-bind="value: $root.contacto.persona.apellidoPaterno, disable: $root.usuario.id()">
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoMaterno" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="apellidoMaterno"
										placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
										maxlength="128"
										data-bind="value: $root.contacto.persona.apellidoMaterno, disable: $root.usuario.id()">
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.contacto.activo" />
									</label> <label class="toggle"> <input type="checkbox"
										name="activo" data-bind="checked: $root.contacto.activo">
										<i data-swchon-text="ON" data-swchoff-text="OFF"></i>
									<spring:message code="mihabitat.contacto.activo" />
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-6">
									<header>
										<spring:message code="mihabitat.persona.emails" />
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="contacto-button"
											data-bind="click: $root.contacto.persona.agregarEmail"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.contacto.persona.emails} -->
										<section class="row">
											<section class="col col-6" class="form-group">
												<label class="input"> <input class="form-control"
													type="email" name="direccion"
													placeholder="<spring:message code="mihabitat.email.direccion" />"
													required="required" maxlength="64"
													data-bind="value: direccion, event:{ change: $parent.contacto.existe },
																disable: $root.usuario.id() && direccion() == $root.usuario.email()">
												</label>
											</section>												
											<section class="col col-5" class="form-group">
												<label class="select"> <select name="tipo-email"
													required="required"
													data-bind="options: $root.catalogoEmail,
																		optionsCaption : 'Seleccione una opción',
																		optionsText: 'descripcion',
																		optionsValue: 'id',
																		value: tipo.id">
												</select> <i></i>
												</label>
											</section>
											<section class="col col-1" class="form-group">
												<a href="javascript:void(0);" class="button-icon"
													data-bind="click : $parent.contacto.persona.removerEmail, 
													visible: !$root.usuario.id() || direccion() != $root.usuario.email()">
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
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="contacto-button"
											data-bind="click: $root.contacto.persona.agregarTelefono"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.contacto.persona.telefonos} -->
										<section class="row">
											<section class="col col-2" class="form-group">
												<label class="input"> <input class="form-control"
													type="text" name="lada"
													placeholder="<spring:message code="mihabitat.telefono.lada" />"
													maxlength="5" data-bind="value: lada">
												</label>
											</section>
											<section class="col col-4" class="form-group">
												<label class="input"> <input class="form-control"
													type="text" name="numero"
													placeholder="<spring:message code="mihabitat.telefono.numero" />"
													required="required" maxlength="16"
													data-bind="value: numero">
												</label>
											</section>
											<section class="col col-3" class="form-group">
												<label class="select"> <select name="tipo-telefono"
													required="required"
													data-bind="options: $root.catalogoTelefono,
																			optionsCaption : 'Seleccione una opción',
																			optionsText: 'descripcion',
																			optionsValue: 'id',
																			value: tipo.id">
												</select> <i></i>
												</label>
											</section>
											<section class="col col-2" class="form-group">
												<label class="input"> <input class="form-control"
													type="text" name="extension"
													placeholder="<spring:message code="mihabitat.telefono.extension" />"
													maxlength="5" data-bind="value: extension, visible: tipo.id() == AppConfig.catalogos.persona.telefono.oficina">
												</label>
											</section>
											<section class="col col-1" class="form-group">
												<a href="javascript:void(0);" class="button-icon"
													data-bind="click : $parent.contacto.removerTelefono"> <i
													class="fa fa-minus-circle"></i>
												</a>
											</section>
										</section>
										<!-- /ko -->
									</fieldset>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-3" class="form-group" data-bind="visible: $root.usuario.id()">
									<label class="input">
									<spring:message code="mihabitat.usuarios.email"/>
									<input class="form-control" type="text" name="email"
											data-bind="value: $root.usuario.email" readonly="readonly">	
									</label>
								</section>
								<section class="col col-md-3" class="form-group" data-bind="visible: !$root.usuario.id()">
									<label class="label"> <spring:message
												code="mihabitat.usuarios.email" />
									</label> 
										<label class="select"> <select name="notificaciones"
												data-bind="options: $root.contacto.persona.emails,
													optionsCaption : 'Seleccione una opción',
													optionsText: 'direccion',
													optionsValue: 'direccion' ">
										</select> <i></i>
									</label>
								</section>
							</section>
						</fieldset>
						<footer>
							<button type="button" class="btn btn-primary" 
									data-bind="click: $root.guardar, visible: !$root.contacto.id()">
									<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button type="button" class="btn btn-primary"
									data-bind="click: $root.actualizar, visible: $root.contacto.id()">
							 		<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>

<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-3"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.departamentos" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body">
					<ul id="myTab1" class="nav nav-tabs bordered">
						<!-- ko foreach: { data: $root.contacto.departamentos } -->
						<li class="departamento"><a data-toggle="tab"
							data-bind="attr: {href : '#div-departamento-' + $index(), id : 'a-departamento-' + $index()}">
								<i class="fa fa-lg fa-square"></i> <span
								class="hidden-mobile hidden-tablet"
								data-bind="text: id.departamento.nombreCompleto"
								style="margin: 0px 10px"></span>
								<button class="close"
									data-bind="click: $root.contacto.removerDepartamento">×</button>
						</a></li>
						<!-- /ko -->
						<li class="pull-right"><a href="javascript:void(0);"
							class="button-icon" id="departamento-button"
							data-bind="click: function(data, event) { $root.contacto.agregarDepartamento(condominio) }">
								<i class="fa fa-plus-circle"></i> <spring:message
									code="mihabitat.botones.agregar" />
						</a></li>
					</ul>
					<div id="myTabContent1" class="tab-content padding-10">
						<!-- ko foreach: { data: $root.contacto.departamentos } -->
						<div class="tab-pane fade"
							data-bind="attr: {id : 'div-departamento-' + $index()}">
							<form class="smart-form"
								data-bind="attr: {id : 'form-departamento-' + $index()}">
								<fieldset>
									<section class="row">
										<section class="col col-md-4">
											<label class="label">
												<span class="error-required">*</span> 
												<spring:message
													code="mihabitat.departamento.nombre" />
											</label> <label class="input"> <input class="form-control"
												type="text" name="descripcion"
												placeholder="<spring:message code="mihabitat.departamento.nombre" />"
												required="required" maxlength="128"
												data-bind="value: id.departamento.nombre, event:{ blur: id.departamento.existe }, disable: id.departamento.id()">
											</label>
										</section>
										<section class="col col-md-4" class="form-group">
											<label class="label">
												<span class="error-required">*</span>
												<spring:message
													code="mihabitat.mantenimientos" />
											</label> <label class="input"> <select style="width: 100%"
												class="select2" name="mantenimiento" id="mantenimiento"
												required="required"
												data-bind="options: $root.mantenimientos,
																		optionsCaption : 'Seleccione una opción',
																		optionsText: 'descripcion',
																		optionsValue: 'id',
																		value: id.departamento.mantenimiento.id,
																		select2: {}">
											</select>
											</label>
										</section>
										<section class="col col-md-4" class="form-group">
											<label class="label"> <spring:message
													code="mihabitat.menu.grupos" />
											</label> <label class="input"> <select style="width: 100%;"
												class="select2" name="grupos" id="grupos"
												multiple="multiple"
												data-bind="options: $root.grupos, 
																		optionsText: 'descripcion',
																		optionsValue: 'id',
																		selectedOptions : id.departamento.grupos,
																		select2: { placeholder: 'Selecciona opciones' }">
											</select>
											</label>
										</section>
									</section>
									<section class="row">
										<section class="col col-md-4" class="form-group">
											<label class="label"> <spring:message
													code="mihabitat.departamento.observaciones" />
											</label> <label class="input"> <textarea class="form-control"
													rows="2" name="observaciones"
													data-bind="value: id.departamento.observaciones"></textarea>
											</label>
										</section>
										<section class="col col-md-2" class="form-group">
											<label class="label"> <spring:message
													code="mihabitat.cd.principal" />
											</label> <label class="toggle"> <input type="checkbox"
												name="activo" data-bind="checked: principal"> <i
												data-swchon-text="ON" data-swchoff-text="OFF"></i>
											<spring:message code="mihabitat.cd.principal" />
											</label>
										</section>
										<section class="col col-md-1" class="form-group"></section>
										<section class="col col-md-2" class="form-group">
											<label class="label"> <spring:message
													code="mihabitat.cd.propietario" />
											</label> <label class="toggle"> <input type="checkbox"
												name="activo" data-bind="checked: propietario"> <i
												data-swchon-text="ON" data-swchoff-text="OFF"></i>
											<spring:message code="mihabitat.cd.propietario" />
											</label>
										</section>
										<section class="col col-md-1" class="form-group"></section>
										<section class="col col-md-2" class="form-group">
											<label class="label"> <spring:message
													code="mihabitat.cd.habitante" />
											</label> <label class="toggle"> <input type="checkbox"
												name="activo" data-bind="checked: habitante"> <i
												data-swchon-text="ON" data-swchoff-text="OFF"></i>
											<spring:message code="mihabitat.cd.habitante" />
											</label>
										</section>
									</section>
								</fieldset>
							</form>
						</div>
						<!-- /ko -->
					</div>
				</div>
			</div>
		</div>
	</article>
</div>
