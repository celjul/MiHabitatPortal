<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-1"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"><i class="fa fa-edit"></i> </span>
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
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.nombre" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.nombre" />"
										required="required" maxlength="128"
										data-bind="value: $root.contacto.nombre">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoPaterno" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
										required="required" maxlength="128"
										data-bind="value: $root.contacto.apellidoPaterno">
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.persona.apellidoMaterno" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="nombre"
										placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
										required="required" maxlength="128"
										data-bind="value: $root.contacto.apellidoMaterno">
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-6">
									<header>
										<spring:message code="mihabitat.persona.emails" />
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="contacto-button"
											data-bind="click: $root.contacto.agregarEmail"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<section class="row">
											<!-- ko foreach: { data: $root.contacto.emails} -->
											<section class="col col-6" class="form-group">
												<label class="input"> <input class="form-control"
													type="email" name="direccion"
													placeholder="<spring:message code="mihabitat.email.direccion" />"
													required="required" maxlength="64"
													data-bind="value: direccion, event:{ change: $root.usuario.existeEmailActivacion }">
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
													data-bind="click : $parent.contacto.removerEmail"> <i
													class="fa fa-minus-circle"></i>
												</a>
											</section>
											<!-- /ko -->
										</section>
									</fieldset>
								</section>
								<section class="col col-6">
									<header>
										<spring:message code="mihabitat.persona.telefonos" />
										<a href="javascript:void(0);" class="button-icon pull-right"
											id="contacto-button"
											data-bind="click: $root.contacto.agregarTelefono"> <i
											class="fa fa-plus-circle"></i>
										</a>
									</header>
									<fieldset>
										<!-- ko foreach: { data: $root.contacto.telefonos} -->
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
												</select><i></i>
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
								<section class="col col-md-4" class="form-group">
									<label class="label"> <spring:message
												code="mihabitat.usuarios.email" />
									</label> 
										<label class="select"> <select name="notificaciones"
												required="required"
												data-bind="options: $root.contacto.emails,
													optionsCaption : 'Seleccione una opción',
													optionsText: 'direccion',
													optionsValue: 'direccion',
													value: $root.usuario.email">
										</select> <i></i>
									</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-4">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.user" />
									</label> <label for="user" class="input"> <input
										class="form-control" onkeyup="nospaces(this)" type="text"
										name="user" id="user"
										placeholder="<spring:message code="mihabitat.usuarios.user" />"
										required="required" maxlength="64" minlength="5"
										data-bind="value: $root.usuario.user, event:{ change: $root.usuario.existe() }">
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.password" />
									</label> <label class="input"> <input class="form-control"
										type="password" name="password" id="password"
										placeholder="<spring:message code="mihabitat.usuarios.password" />"
										required="required" maxlength="32"
										data-bind="value: $root.usuario.password">
									</label>
								</section>
								<section class="col col-md-4">
									<label class="label"> <spring:message
											code="mihabitat.usuarios.password.confirmar" />
									</label> <label class="input"> <input class="form-control"
										type="password" name="confirmar" id="confirmar"
										equalTo="#password"
										placeholder="<spring:message code="mihabitat.usuarios.password" />"
										required="required" maxlength="32">
									</label>
								</section>
							</section>
						</fieldset>
						<footer>
							<button type="button" class="btn btn-primary"
								data-bind="click: $root.activar">
								<spring:message code="mihabitat.login.activar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>