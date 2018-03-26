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
					<spring:message code="mihabitat.menu.configuracion.condominio" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="configuracioncondominio-form" class="smart-form">
						<fieldset>
							<div class="row">
								<section class="col col-md-12">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.condominio.configuracion.mensajeEstadoCuenta" />
									</label> <label class="input">
									<textarea class="form-control"
											 rows="5" id="mensajeEstadoCuenta" name="mensajeEstadoCuenta" maxlength="512"
											 data-bind="value: $root.configuracionCondominio.mensajeEstadoCuenta"></textarea>
									</label>
								</section>
								<section class="col col-md-12">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.condominio.configuracion.mensajeAvisoCobro" />
									</label> <label class="input">
									<textarea class="form-control"
											  rows="5" id="mensajeAvisoCobro" name="mensajeAvisoCobro" maxlength="512"
											  data-bind="value: $root.configuracionCondominio.mensajeAvisoCobro"></textarea>
								</label>
								</section>
								<section class="col col-md-12">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.condominio.configuracion.mensajeReciboPago" />
									</label> <label class="input">
									<textarea class="form-control"
											  rows="5" id="mensajeReciboPago" name="mensajeReciboPago" maxlength="512"
											  data-bind="value: $root.configuracionCondominio.mensajeReciboPago"></textarea>
								</label>
								</section>
							</div>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-guardar-condominio"
								data-bind="click: $root.guardar, visible: !$root.configuracionCondominio.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary" id="btn-actualizar-condominio"
								data-bind="click: $root.guardar, visible: $root.configuracionCondominio.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>