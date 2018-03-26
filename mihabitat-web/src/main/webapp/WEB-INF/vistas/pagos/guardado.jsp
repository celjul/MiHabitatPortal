<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<div id="guardado-modal" class="modal fade" tabindex="-1"
	data-width="300">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<sec:authorize access="hasRole('Administrador')">
					<button type="button" class="close"
						data-bind="click : $root.reedireccionar">
						<span aria-hidden="true">&times;</span><span class="sr-only">
							<spring:message code="mihabitat.botones.cerrar" />
						</span>
					</button>
				</sec:authorize>
				<sec:authorize access="hasRole('Contacto')">
					<button type="button" class="close"
						data-bind="click : $root.reedireccionarContacto">
						<span aria-hidden="true">&times;</span><span class="sr-only">
							<spring:message code="mihabitat.botones.cerrar" />
						</span>
					</button>
				</sec:authorize>
				<h4 class="modal-title">
					<spring:message code="mihabitat.pago.enviar" />
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<p>
							<sec:authorize access="hasRole('Administrador')">
								<spring:message code="mihabitat.pago.guardado.admin" />
							</sec:authorize>
							<sec:authorize access="hasRole('Contacto')">
								<spring:message code="mihabitat.pago.guardado.contacto" />
							</sec:authorize>
						</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<sec:authorize access="hasRole('Administrador')">
					<button style="float: left" type="button" class="btn btn-success"
						data-bind="click: $root.imprimir">
						<spring:message code="mihabitat.pago.imprimir" />
					</button>
					<button style="float: left" type="button" class="btn btn-success"
						data-bind="click: $root.abrirModalEnvio">
						<spring:message code="mihabitat.pago.enviar" />
					</button>
					<button type="button" class="btn btn-primary"
						data-bind="click : $root.reedireccionar">
						<spring:message code="mihabitat.botones.aceptar" />
					</button>
				</sec:authorize>
				<sec:authorize access="hasRole('Contacto')">
					<button type="button" class="btn btn-primary"
						data-bind="click : $root.reedireccionarContacto">
						<spring:message code="mihabitat.botones.aceptar" />
					</button>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>