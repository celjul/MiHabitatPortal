<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Modal -->
<div class="modal fade" id="myModalContacto" tabindex="-1" role="dialog" data-backdrop="static"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <spring:message code="mihabitat.contacto"/>
            </div>
            <div class="modal-body">
                <form id="form-contacto" class="smart-form">
                    <fieldset>
                        <section class="row">
                            <section class="col col-md-6 col-sm-6" class="form-group">
                                <label class="label"><spring:message
                                        code="mihabitat.contacto"/> </label> <select
                                    style="width: 100%" class="select2" name="contactoSeleccionado"
                                    id="contactoSeleccionado"
                                    data-bind="options: $root.contactos,
                                                     	optionsCaption : 'Crear Nuevo',
 	                                                	optionsText: 'nombreCompleto',
 						                            	optionsValue: 'id',
                                                    	value: $root.contactoTemporal.id.contacto.id,
                                                    	select2: {}, event: {change :$root.seleccionado()}">
                            </select>
                            </section>
                    </fieldset>
                    <fieldset>
                        <section class="row">
                            <section class="col col-4" class="form-group">
                                <label class="label">
                                    <span class="error-required">*</span>
                                    <spring:message code="mihabitat.persona.nombre"/>
                                </label>
                                <label class="input">
                                    <input class="form-control" type="text" id="nombre" name="nombre"
                                           placeholder="<spring:message code="mihabitat.persona.nombre" />"
                                           required="required" maxlength="128"
                                           data-bind="value: $root.contactoTemporal.id.contacto.nombre">
                                </label>
                            </section>
                            <section class="col col-4" class="form-group">
                                <label class="label">
                                    <span class="error-required">*</span>
                                    <spring:message code="mihabitat.persona.apellidoPaterno"/>
                                </label>
                                <label class="input">
                                    <input class="form-control" type="text" id="apellidoPaterno" name="apellidoPaterno"
                                           placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
                                           required="required" maxlength="128"
                                           data-bind="value: $root.contactoTemporal.id.contacto.apellidoPaterno">
                                </label>
                            </section>
                            <section class="col col-4" class="form-group">
                                <label class="label">
                                    <spring:message code="mihabitat.persona.apellidoMaterno"/>
                                </label>
                                <label class="input">
                                    <input class="form-control" type="text" id="apellidoMaterno" name="apellidoMaterno"
                                           placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
                                           maxlength="128"
                                           data-bind="value: $root.contactoTemporal.id.contacto.apellidoMaterno">
                                </label>
                            </section>
                        </section>
                        <section class="row">
                            <header>
                                <spring:message code="mihabitat.persona.emails"/>
                                <a href="javascript:void(0);" class="button-icon pull-right" id="add-email-button"
                                   name="add-email-button"
                                   data-bind="click: $root.contactoTemporal.id.contacto.agregarEmail">
                                    <i class="fa fa-plus-circle"></i>
                                </a>
                            </header>
                            <fieldset>
                                <!-- ko foreach: { data: $root.contactoTemporal.id.contacto.emails} -->
                                <section class="row">
                                    <section class="col col-6" class="form-group">
                                        <label class="input">
                                            <input class="form-control" type="email"
                                                   placeholder="<spring:message code="mihabitat.email.direccion" />"
                                                   required="required" maxlength="64"
                                                   data-bind="value: direccion, event:{ change: $root.contactoTemporal.id.contacto.existe }, uniqueName: true">
                                        </label>
                                    </section>
                                    <section class="col col-4" class="form-group">
                                        <label class="select">
                                            <select name="tipo-email"
                                                    required="required"
                                                    data-bind="options: $root.catalogoEmail,
																					optionsCaption : 'Seleccionar',
																					optionsText: 'descripcion',
																					optionsValue: 'id',
																					value: tipo.id,
																					uniqueName: true">
                                            </select>
                                            <i></i>
                                        </label>
                                    </section>
                                    <section class="col col-2">
                                        <a class="btn btn-danger btn-xs"
                                           data-bind="click : $root.contactoTemporal.id.contacto.removerEmail">
                                            <i class="fa fa-minus-circle"> Eliminar </i>
                                        </a>
                                    </section>
                                </section>
                                <!-- /ko -->
                            </fieldset>
                        </section>
                        <section class="row">
                            <header>
                                <spring:message code="mihabitat.persona.telefonos"/>
                                <a href="javascript:void(0);" class="button-icon pull-right" id="add-phone-button"
                                   name="add-phone-button"
                                   data-bind="click: $root.contactoTemporal.id.contacto.agregarTelefono">
                                    <i class="fa fa-plus-circle"></i>
                                </a>
                            </header>
                            <fieldset>
                                <!-- ko foreach: { data: $root.contactoTemporal.id.contacto.telefonos} -->
                                <section class="row">
                                    <section class="col col-2" class="form-group">
                                        <label class="input">
                                            <input class="form-control" type="text"
                                                   placeholder="<spring:message code="mihabitat.telefono.lada" />"
                                                   maxlength="5" data-bind="value: lada, uniqueName: true">
                                        </label>
                                    </section>
                                    <section class="col col-3" class="form-group">
                                        <label class="input">
                                            <input class="form-control" type="text"
                                                   placeholder="<spring:message code="mihabitat.telefono.numero" />"
                                                   required="required" maxlength="16"
                                                   data-bind="value: numero, uniqueName: true">
                                        </label>
                                    </section>
                                    <section class="col col-3" class="form-group">
                                        <label class="select">
                                            <select
                                                    required="required"
                                                    data-bind="options: $root.catalogoTelefono,
																						optionsCaption : 'Seleccionar',
																						optionsText: 'descripcion',
																						optionsValue: 'id',
																						value: tipo.id,
																						uniqueName: true">
                                            </select>
                                            <i></i>
                                        </label>
                                    </section>
                                    <section class="col col-2" class="form-group">
                                        <label class="input">
                                            <input class="form-control" type="text" name="extension"
                                                   placeholder="<spring:message code="mihabitat.telefono.extension" />"
                                                   maxlength="5"
                                                   data-bind="value: extension, visible: tipo.id() == AppConfig.catalogos.persona.telefono.oficina, uniqueName: true">
                                        </label>
                                    </section>
                                    <section class="col col-2" class="form-group">
                                        <a class="btn btn-danger btn-xs"
                                           data-bind="click : $root.contactoTemporal.id.contacto.removerTelefono">
                                            <i class="fa fa-minus-circle"> Eliminar </i>
                                        </a>
                                    </section>
                                </section>
                                <!-- /ko -->
                            </fieldset>
                        </section>
                        <section class="row">
                            <section class="col col-md-6" class="form-group"
                                     data-bind="visible: $root.contactoTemporal.id.contacto.usuario.id()">
                                <label class="label">
                                    <spring:message code="mihabitat.usuarios.email"/>
                                </label>
                                <label class="input">
                                    <input class="form-control" type="text" name="email"
                                           data-bind=" value: $root.contactoTemporal.id.contacto.usuario.email"
                                           readonly="readonly">
                                </label>
                            </section>
                            <section class="col col-md-6" class="form-group"
                                     data-bind="visible: !$root.contactoTemporal.id.contacto.usuario.id()">
                                <label class="label">
                                    <spring:message code="mihabitat.usuarios.email"/>
                                </label>
                                <label class="select">
                                    <select name="notificaciones" id="notificaciones"
                                            data-bind="options: $root.contactoTemporal.id.contacto.emails,
																	optionsCaption : 'Seleccione una opcion',
																	optionsText: 'direccion',
																	optionsValue: 'direccion',
																	value: $root.contactoTemporal.id.contacto.usuario.email
																	">
                                    </select>
                                    <i></i>
                                </label>
                            </section>
                            <section class="col col-md-3" class="form-group">
                                <%--<label class="label">
                                    <spring:message code="mihabitat.cd.principal" />
                                </label>
                                <label class="toggle">
                                    <input type="checkbox" name="activo" id="principal"
                                           data-bind="checked: $root.contactoTemporal.principal">
                                    <i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.principal" />
                                </label>--%>
                                <label class="checkbox">
                                    <input type="checkbox" name="activo" id="principal"
                                           data-bind="checked: $root.contactoTemporal.principal">
                                    <i></i><spring:message code="mihabitat.cd.principal"/>
                                </label>
                            </section>
                            <section class="col col-md-3" class="form-group">
                                <%--<label class="label">
                                    <spring:message code="mihabitat.cd.propietario" />
                                </label>
                                <label class="toggle">
                                    <input type="checkbox" name="activo" id="principal"
                                           data-bind="checked:  $root.contactoTemporal.propietario">
                                    <i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.propietario" />
                                </label>--%>
                                <label class="checkbox">
                                    <input type="checkbox" name="activo" id="principal"
                                           data-bind="checked:  $root.contactoTemporal.propietario">
                                    <i></i><spring:message code="mihabitat.cd.propietario"/>
                                </label>
                            </section>
                            <section class="col col-md-3" class="form-group">
                                <%--<label class="label">
                                    <spring:message code="mihabitat.cd.habitante" />
                                </label>--%>
                                <label class="checkbox">
                                    <input type="checkbox" name="checkbox" name="activo" id="habitante"
                                           data-bind="checked: $root.contactoTemporal.habitante">
                                    <i></i><spring:message code="mihabitat.cd.habitante"/>
                                </label>
                                <%--<label class="toggle">
                                    <input type="checkbox" name="activo" id="habitante"
                                           data-bind="checked:  $root.contactoTemporal.habitante">
                                    <i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.habitante" />
                                </label>--%>

                            </section>
                        </section>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancelar
                </button>
                <button style="float: right" type="button" class="btn btn-primary"
                        data-bind="click: $root.agregarContacto">
                    <spring:message code="mihabitat.botones.agregar"/>
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%--

<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-3"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.contactos" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body">
					<ul id="myTab1" class="nav nav-tabs bordered">
						<!-- ko foreach: { data: $root.departamento.contactos} -->
							<li class="contacto">
								<a data-toggle="tab" data-bind="attr: {href : '#div-contacto-' + $index(), id : 'a-contacto-' + $index()}">
									<i class="fa fa-lg fa-user"></i>
									<span class="hidden-mobile hidden-tablet" data-bind="text: id.contacto.persona.nombreCompleto" style="margin: 0px 10px"></span>
									<button class="close" data-bind="click: $root.departamento.removerContacto">x</button>
								</a>
							</li>
						<!-- /ko -->
						<li class="pull-right">
							<a href="javascript:void(0);" class="button-icon" id="contacto-button" data-bind="click: $root.departamento.agregarContacto">
								<i class="fa fa-plus-circle"></i> <spring:message code="mihabitat.botones.agregar" />
							</a>
						</li>
					</ul>
					<div id="myTabContent1" class="tab-content padding-10">
						<!-- ko foreach: { data: $root.departamento.contactos} -->
							<div class="tab-pane fade" data-bind="attr: {id : 'div-contacto-' + $index()}">
								<form class="smart-form" data-bind="attr: {id : 'form-contacto-' + $index()}" novalidate="novalidate">
									<fieldset>
										<section class="row">
											<section class="col col-md-4" class="form-group">
												<label class="label">
													<span class="error-required">*</span>
													<spring:message code="mihabitat.persona.nombre" />
												</label>
												<label class="input">
													<input class="form-control" type="text"
														placeholder="<spring:message code="mihabitat.persona.nombre" />"
														required="required" maxlength="128"
														data-bind="value: id.contacto.persona.nombre, attr: {name: 'nombre' + $index(), id: 'nombre' + $index()}">
												</label>
											</section>
											<section class="col col-md-4" class="form-group">
												<label class="label">
													<span class="error-required">*</span>
													<spring:message code="mihabitat.persona.apellidoPaterno" />
												</label>
												<label class="input">
													<input class="form-control" type="text" 
														placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
														required="required" maxlength="128"
														data-bind="value: id.contacto.persona.apellidoPaterno, attr: {name: 'apellidoPaterno' + $index(), id: 'apellidoPaterno' + $index()}">
												</label>
											</section>
											<section class="col col-md-4" class="form-group">
												<label class="label">
													<spring:message code="mihabitat.persona.apellidoMaterno" />
												</label>
												<label class="input">
													<input class="form-control" type="text" 
														placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
														maxlength="128"
														data-bind="value: id.contacto.persona.apellidoMaterno, attr: {name:'apellidoMaterno' + $index(), id: 'apellidoMaterno' + $index()}">
												</label>
											</section>
										</section>
										<section class="row">
											<section class="col col-6">
												<header>
													<spring:message code="mihabitat.persona.emails" />
													<a href="javascript:void(0);" class="button-icon pull-right"
															data-bind="click: id.contacto.persona.agregarEmail, attr: {id: 'add-email-button' + $index()}">
														<i class="fa fa-plus-circle"></i>
													</a>
												</header>
												<fieldset>
													<!-- ko foreach: { data: id.contacto.persona.emails} -->
														<section class="row">
															<section class="col col-6" class="form-group">
																<label class="input">
																	<input class="form-control" type="email" 
																		placeholder="<spring:message code="mihabitat.email.direccion" />"
																		required="required" maxlength="64"
																		data-bind="value: direccion, event:{ change: $parent.id.contacto.existe }, uniqueName: true">
																</label>
															</section>
															<section class="col col-5" class="form-group">
																<label class="select">
																	<select name="tipo-email"
																		required="required"
																		data-bind="options: $root.catalogoEmail,
																					optionsCaption : 'Seleccione una opci�n',
																					optionsText: 'descripcion',
																					optionsValue: 'id',
																					value: tipo.id,
																					uniqueName: true">
																	</select>
																	<i></i>
																</label>
															</section>
															<section class="col col-1" class="form-group">
																<a href="javascript:void(0);" class="button-icon" 
																	data-bind="click : $parent.id.contacto.persona.removerEmail">
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
														<a href="javascript:void(0);" class="button-icon pull-right" data-bind="click: id.contacto.persona.agregarTelefono, attr: {id: 'add-phone-button' + $index()}">
															<i class="fa fa-plus-circle"></i>
														</a>
													</header>
													<fieldset>
														<!-- ko foreach: { data: id.contacto.persona.telefonos} -->
															<section class="row">
																<section class="col col-2" class="form-group">
																	<label class="input">
																		<input class="form-control" type="text" 
																			placeholder="<spring:message code="mihabitat.telefono.lada" />"
																			maxlength="5" data-bind="value: lada, uniqueName: true">
																	</label>
																</section>
																<section class="col col-4" class="form-group">
																	<label class="input">
																		<input class="form-control" type="text" 
																			placeholder="<spring:message code="mihabitat.telefono.numero" />"
																			required="required" maxlength="16"
																			data-bind="value: numero, uniqueName: true">
																	</label>
																</section>
																<section class="col col-3" class="form-group">
																	<label class="select">
																		<select 
																			required="required"
																			data-bind="options: $root.catalogoTelefono,
																						optionsCaption : 'Seleccione una opci�n',
																						optionsText: 'descripcion',
																						optionsValue: 'id',
																						value: tipo.id,
																						uniqueName: true">
																		</select>
																		<i></i>
																	</label>
																</section>
																<section class="col col-2" class="form-group">
																	<label class="input">
																		<input class="form-control" type="text" name="extension" 
																			placeholder="<spring:message code="mihabitat.telefono.extension" />"
																			maxlength="5" data-bind="value: extension, visible: tipo.id() == AppConfig.catalogos.persona.telefono.oficina, uniqueName: true">
																	</label>
																</section>
																<section class="col col-1" class="form-group">
																	<a href="javascript:void(0);" class="button-icon" data-bind="click : $parent.id.contacto.persona.removerTelefono">
																		<i class="fa fa-minus-circle"></i>
																	</a>
																</section>
															</section>
														<!-- /ko -->
													</fieldset>
												</section>
										</section>
										<section class="row">
											<section class="col col-md-3" class="form-group" data-bind="visible: id.contacto.usuario.id">
												<label class="label">
													<spring:message code="mihabitat.usuarios.email"/>
												</label>
												<label class="input">
													<input class="form-control" type="text" name="email"
														data-bind=" value: id.contacto.usuario.email" readonly="readonly">
												</label>
											</section>
											<section class="col col-md-3" class="form-group" data-bind="visible: !id.contacto.usuario.id()">
												<label class="label">
													<spring:message code="mihabitat.usuarios.email"/>
												</label>
												<label class="select">
													<select name="notificaciones"
														data-bind="options: id.contacto.persona.emails,
																	optionsCaption : 'Seleccione una opci�n',
																	optionsText: 'direccion',
																	optionsValue: 'direccion',
																	value: id.contacto.usuario.email,
																	attr: {id: 'notificaciones' + $index()}">
													</select>
													<i></i>
												</label>
											</section>
											<section class="col col-md-1" class="form-group"></section>
											<section class="col col-md-2" class="form-group">
												<label class="label">
													<spring:message code="mihabitat.cd.principal" />
												</label>
												<label class="toggle">
													<input type="checkbox" name="activo" data-bind="checked: principal, , attr: {id : 'principal' + $index()}">
													<i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.principal" />
												</label>
											</section>
											<section class="col col-md-1" class="form-group"></section>
											<section class="col col-md-2" class="form-group">
												<label class="label">
													<spring:message code="mihabitat.cd.propietario" />
												</label>
												<label class="toggle">
													<input type="checkbox" name="activo" data-bind="checked: propietario, attr: {id : 'propietario' + $index()}">
													<i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.propietario" />
												</label>
											</section>
											<section class="col col-md-1" class="form-group"></section>
											<section class="col col-md-2" class="form-group">
												<label class="label">
													<spring:message code="mihabitat.cd.habitante" />
												</label>
												<label class="toggle">
													<input type="checkbox" name="activo" data-bind="checked: habitante, attr: {id : 'habitante' + $index()}">
													<i data-swchon-text="ON" data-swchoff-text="OFF"></i><spring:message code="mihabitat.cd.habitante" />
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
--%>
