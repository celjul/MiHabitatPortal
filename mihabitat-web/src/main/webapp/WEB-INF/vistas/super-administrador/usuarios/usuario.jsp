<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-1"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"><i class="fa fa-edit"></i> </span>
				<h2>
					<spring:message code="mihabitat.usuarios.title" />
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
										type="text" name="nombre" id="nombre"
										placeholder="<spring:message code="mihabitat.persona.nombre" />"
										required="required" maxlength="64"
										data-bind="value: $root.usuario.persona.nombre">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span>
									<spring:message
											code="mihabitat.persona.apellidoPaterno" />
									</label> <label class="input"> <input
										class="form-control" type="text" name=apellido-paterno
										id="apellido-paterno"
										placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
										required="required" maxlength="64"
										data-bind="value: $root.usuario.persona.apellidoPaterno">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoMaterno" />
									</label> <label class="input"> <input
										class="form-control" type="text" name="apellido-materno" id="apellido-materno"
										placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
										maxlength="64"
										data-bind="value: $root.usuario.persona.apellidoMaterno">
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-6">
									<header>
										<spring:message code="mihabitat.persona.emails" />
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="email-button"
											data-bind="click: $root.usuario.persona.agregarEmail"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.usuario.persona.emails} -->
										<section class="row">
											<section class="col col-6" class="form-group">
												<label class="input"> <input class="form-control"
													type="email"
													placeholder="<spring:message code="mihabitat.email.direccion" />"
													required="required" maxlength="64"
													data-bind="value: direccion, event:{ change: $root.usuario.existeEmail }, 
																	disable: $root.usuario.id() && direccion == $root.usuario.email(),
																	unique: {name: true, id: true, prefix: 'direccion', counter: $index() }">
												</label>
											</section>
											<section class="col col-5" class="form-group">
												<label class="select"> <select 
													required="required"
													data-bind="options: $root.catalogoEmail,
																		optionsCaption : 'Seleccione una opción',
																		optionsText: 'descripcion',
																		optionsValue: 'id',
																		value: tipo.id,
																		unique: {name: true, id: true, prefix: 'tipo-email', counter: $index() }">
												</select> <i></i>
												</label>
											</section>
											<section class="col col-1" class="form-group">
												<a class="button-icon"
													data-bind="click : $root.usuario.persona.removerEmail,
													unique: {id: true, prefix: 'btn-del-email', counter: $index() },
													visible: $root.usuario.id() && direccion != $root.usuario.email() " >
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
											data-bind="click: $root.usuario.persona.agregarTelefono"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.usuario.persona.telefonos} -->
										<section class="row">
											<section class="col col-2" class="form-group">
												<label class="input"> <input class="form-control"
													type="text"
													placeholder="<spring:message code="mihabitat.telefono.lada" />"
													maxlength="5" data-bind="value: lada, unique: {name: true, id: true, prefix: 'lada', counter: $index() }">
												</label>
											</section>
											<section class="col col-4" class="form-group">
												<label class="input"> <input class="form-control"
													type="text" 
													placeholder="<spring:message code="mihabitat.telefono.numero" />"
													required="required" maxlength="16"
													data-bind="value: numero, unique: {name: true, id: true, prefix: 'numero', counter: $index() }">
												</label>
											</section>
											<section class="col col-3" class="form-group">
												<label class="select"> <select 
													required="required"
													data-bind="options: $root.catalogoTelefono,
																			optionsCaption : 'Seleccione una opción',
																			optionsText: 'descripcion',
																			optionsValue: 'id',
																			value: tipo.id,
																			unique: {name: true, id: true, prefix: 'tipo-telefono', counter: $index() }">
												</select> <i></i>
												</label>
											</section>
											<section class="col col-2" class="form-group">
												<label class="input"> <input class="form-control"
													type="text" 
													placeholder="<spring:message code="mihabitat.telefono.extension" />"
													maxlength="5"
													data-bind="value: extension, visible: tipo.id == AppConfig.catalogos.persona.telefono.oficina,
																	unique: {name: true, id: true, prefix: 'extension', counter: $index() }">
												</label>
											</section>
											<section class="col col-1" class="form-group">
												<a href="javascript:void(0);" class="button-icon"
													data-bind="click : $root.usuario.persona.removerTelefono, 
													unique: {name: true, id: true, prefix: 'btn-del-tel', counter: $index() }"> <i
													class="fa fa-minus-circle"></i>
												</a>
											</section>
										</section>
										<!-- /ko -->
									</fieldset>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.usuarios.user" />
									</label> <label for="user" class="input"> <input
										class="form-control" onkeyup="nospaces(this)" type="text"
										name="user" id="user"
										placeholder="<spring:message code="mihabitat.usuarios.user" />"
										required="required" maxlength="64" minlength="5"
										data-bind="value: $root.usuario.user,
														event:{ blur: $root.usuario.existe() },
														disable: $root.usuario.id()">
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
										data-bind="value: $root.usuario.password,
															disable: $root.usuario.id()">
									</label>
								</section>
								<section class="col col-md-4"
									data-bind="visible: !$root.usuario.id()">
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
									</label> <label class="select"> <select name="email-notificaciones"
										id="email-notificaciones"
										data-bind="options: $root.usuario.persona.emails,
															optionsCaption : 'Seleccione una opción',
															optionsText: 'direccion',
															optionsValue: 'direccion',
															value: $root.usuario.email,
															disable: $root.usuario.id() && $root.usuario.email()">
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
															selectedOptions : $root.usuario.roles,
															select2: { placeholder: 'Selecciona opciones' }">
									</select>
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.activo" />
									</label> <label class="toggle"> <input type="checkbox"
										id="activo" name="activo"
										data-bind="checked: $root.usuario.activo"> <i
										data-swchon-text="ON" data-swchoff-text="OFF"></i> <spring:message
											code="mihabitat.usuarios.activo" />
									</label>
								</section>
							</section>
						</fieldset>
						<c:if test="${not param.hideButtons}">
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-guardar-usuario"
								data-bind="click: $root.usuario.guardar, visible: !$root.usuario.persona.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-actualizar-usuario"
								data-bind="click: $root.usuario.guardar, visible: $root.usuario.persona.id() ">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>