<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-1"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"><i class="fa fa-edit"></i> </span>
				<h2>
					<spring:message code="mihabitat.usuarios.user" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="usuario-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.persona.nombre" /> </label> <label
										class="input"> <input class="form-control"
										type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.nombre" />"
										required="required" maxlength="64"
										data-bind="value: $root.persona.nombre">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoPaterno" />
									</label> <label class="input"> <input
										class="form-control" type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
										required="required" maxlength="64"
										data-bind="value: $root.persona.apellidoPaterno">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoMaterno" />
									</label> <label class="input"> <input
										class="form-control" type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
										required="required" maxlength="64"
										data-bind="value: $root.persona.apellidoMaterno">
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-6">
									<header>
										<spring:message code="mihabitat.persona.emails" />
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="email-button"
											data-bind="click: $root.persona.agregarEmail"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.persona.emails} -->
										<section class="row">
											<section class="col col-6" class="form-group">
												<label class="input"> <input class="form-control"
													type="email" name="direccion"
													placeholder="<spring:message code="mihabitat.email.direccion" />"
													required="required" maxlength="64"
													data-bind="value: direccion, event:{ blur: $parent.persona.existe }, 
															   disable: $root.persona.usuario.id() && direccion() == $root.persona.usuario.email()">
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
													data-bind="click : $parent.persona.removerEmail, 
													visible: $root.persona.usuario.id() && direccion() != $root.persona.usuario.email() ">
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
											id="telefono-button"
											data-bind="click: $root.persona.agregarTelefono"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.persona.telefonos} -->
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
													maxlength="5"
													data-bind="value: extension, visible: tipo.id() == AppConfig.catalogos.persona.telefono.oficina">
												</label>
											</section>
											<section class="col col-1" class="form-group">
												<a href="javascript:void(0);" class="button-icon"
													data-bind="click : $parent.persona.removerTelefono"> <i
													class="fa fa-minus-circle"></i>
												</a>
											</section>
										</section>
										<!-- /ko -->
									</fieldset>
								</section>
							</section>
							
							<%--
							<section class="row">
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.usuarios.user" />
									</label> <label for="user" class="input"> <input
										class="form-control" onkeyup="nospaces(this)" type="text"
										name="user" id="user"
										placeholder="<spring:message code="mihabitat.usuarios.user" />"
										required="required" maxlength="64" minlength="5"
										data-bind="value: $root.persona.usuario.user,
														event:{ blur: $root.persona.usuario.existe() },
														disable: $root.persona.usuario.id()">
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.usuarios.password" />
									</label> <label class="input"> <input style="display: none">
										<input class="form-control" type="password" name="password"
										id="password" autocomplete="off"
										placeholder="<spring:message code="mihabitat.usuarios.password" />"
										required="required" maxlength="32"
										data-bind="value: $root.persona.usuario.password,
															disable: $root.persona.usuario.id()">
									</label>
								</section>
								<section class="col col-md-4"
									data-bind="visible: !$root.persona.usuario.id()">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.usuarios.password.confirmar" />
									</label> <label class="input"> <input class="form-control"
										type="password" name="confirmar" id="confirmar"
										equalTo="#password"
										placeholder="<spring:message code="mihabitat.usuarios.password" />"
										required="required" maxlength="32">
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.email" />
									</label> <label class="select"> <select name="email"
										data-bind="options: $root.persona.emails,
															optionsCaption : 'Seleccione una opción',
															optionsText: 'direccion',
															optionsValue: 'direccion',
															value: $root.persona.usuario.email,
															disable: $root.persona.usuario.id() && $root.persona.usuario.email()">
									</select> <i></i>
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.usuarios.roles" />
									</label> <label class="input"> <select style="width: 100%;"
										class="select2" name="roles" id="roles" required="required"
										multiple="multiple"
										data-bind="options: $root.roles, 
														  optionsText: 'descripcion',
														  optionsValue: 'id',
														  selectedOptions : $root.persona.usuario.roles,
														  select2: { placeholder: 'Selecciona opciones' }">
									</select>
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.activo" />
									</label> <label class="toggle"> <input type="checkbox"
										id="activo" name="activo"
										data-bind="checked: $root.persona.usuario.activo"> <i
										data-swchon-text="ON" data-swchoff-text="OFF"></i> <spring:message
											code="mihabitat.usuarios.activo" />
									</label>
								</section>
							</section> --%>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.persona.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.persona.id() ">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>