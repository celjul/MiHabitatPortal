<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.condominio" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="condominio-form" class="smart-form">
						<fieldset>
							<div class="row">
								<section class="col col-md-4">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.condominio.nombre" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="nombre" id="nombre"
										placeholder="<spring:message code="mihabitat.condominio.nombre" />"
										required="required" maxlength="128"
										data-bind="value: $root.condominio.nombre">
									</label>
								</section>
								<section class="col col-md-6" class="form-group">
									<label class="label"> <span class="error-required">*</span>
									<spring:message code="mihabitat.condominio.administradores" />
									</label> <label class="input"> <select style="width: 100%;"
										class="select2" name="administradores" id="administradores"
										multiple="multiple" required="required"
										data-bind="options: $root.admins, 
															optionsText: function(item) {
																return item.nombre + ' ' + item.apellidoPaterno
															},
															optionsValue: 'id',
															selectedOptions : $root.condominio.administradores,
															select2: { placeholder: 'Selecciona opciones' }">
									</select>
									</label>
								</section>
							</div>
						</fieldset>
						<header>
							<spring:message code="mihabitat.direccion" />
						</header>
						<fieldset>
							<div class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.pais" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="condominio-pais" id="condominio-pais"
										required="required"
										data-bind="options: paises,
													optionsCaption : 'Seleccione una opción',
													optionsText: 'nombre',
													optionsValue: 'id',
													value: condominio.direccion.colonia.municipio.estado.pais.id,
													select2: {}">
										</select>
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.estado" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="condominio-estado"
										id="condominio-estado" required="required"
										data-bind="options: condominio.direccion.colonia.municipio.estado.pais.estados,
															optionsText: 'nombre',
															optionsValue: 'id',
															optionsCaption: 'Seleccione una Opción',
															value : condominio.direccion.colonia.municipio.estado.id,
															select2: {}">
									</select>
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.municipio" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="condominio-municipio"
										id="condominio-municipio" required="required"
										data-bind="options: condominio.direccion.colonia.municipio.estado.municipios,
 														optionsText: 'nombre', 
 														optionsValue: 'id', 
 														optionsCaption: 'Seleccione una Opción', 
 														value :condominio.direccion.colonia.municipio.id,
 														select2: {}">
									</select>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-md-4" class="form-group">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.colonia" />
									</label> <label class="input"><select style="width: 100%"
										class="select2" name="condominio-colonia"
										id="condominio-colonia" required="required"
										data-bind="options: condominio.direccion.colonia.municipio.colonias,
 														optionsText: 'nombre',
 														optionsValue: 'id', 
 														optionsCaption: 'Seleccione una Opción', 
 														value : condominio.direccion.colonia.id, 
 														select2: {}">
									</select>
									</label>
								</section>

								<section class="col col-md-4">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.calle" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="condominio-calle" id="condominio-calle"
										placeholder="<spring:message code="mihabitat.direccion.calle" />"
										required="required" maxlength="128"
										data-bind="value: $root.condominio.direccion.calle">

									</label>
								</section>
								<section class="col col-md-2">
									<label class="label"><span class="error-required">*</span><spring:message
											code="mihabitat.direccion.noExterior" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="condominio-noExt" id="condominio-noExt"
										placeholder="<spring:message code="mihabitat.direccion.noExterior" />"
										required="required" maxlength="32"
										data-bind="value: $root.condominio.direccion.noExterior">
									</label>
								</section>
								<section class="col col-md-2">
									<label class="label"> <spring:message
											code="mihabitat.direccion.noInterior" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="condominio-noInt" id="condominio-noInt"
										placeholder="<spring:message code="mihabitat.direccion.noInterior" />"
										maxlength="32"
										data-bind="value: $root.condominio.direccion.noInterior">
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-md-2">
									<label class="label"> <spring:message
											code="mihabitat.direccion.cp" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="condominio-cp" id="condominio-cp"
										placeholder="<spring:message code="mihabitat.direccion.cp" />"
										required="required" maxlength="5" disabled="disabled"
										data-bind="value: $root.condominio.direccion.colonia.codigoPostal">

									</label>
								</section>
								<section class="col col-md-6">
									<label class="label"> <spring:message
											code="mihabitat.direccion.referencias" />
									</label> <label class="textarea"> <textarea cols="20" rows="3"
											id="condominio-referencia"
											placeholder="<spring:message code="mihabitat.direccion.referencias" />"
											maxlength="512" name="referencia"
											data-bind="value : $root.condominio.direccion.referencias">
								 	</textarea>
									</label>
								</section>
							</div>
						</fieldset>
						<c:if test="${not param.hideButtons}">
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-guardar-condominio"
								data-bind="click: $root.condominio.guardar, visible: !$root.condominio.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-actualizar-condominio"
								data-bind="click: $root.condominio.guardar, visible: $root.condominio.id()">
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