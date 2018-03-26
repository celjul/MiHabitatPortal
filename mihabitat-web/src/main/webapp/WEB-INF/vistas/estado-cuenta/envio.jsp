<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="envio-modal" class="modal fade edocta" tabindex="-1" data-width="500">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">
                        <spring:message code="mihabitat.botones.cerrar" />
                    </span>
                </button>
                <h4 class="modal-title"><spring:message code="mihabitat.estadocuenta.enviar" /></h4>
            </div>
            <div class="modal-body">
                <form id="envio-form" class="smart-form">
                    <fieldset>
		                <div class="row">
		                    <div class="col-sm-12 col-md-12 col-lg-12">
			                        <table id="table-contactos" class="table table-striped table-bordered table-hover" style="width: 100%">
			                        <thead>
			                            <tr>
			                                <th data-class="expand" class="col-md-6"><spring:message code="mihabitat.contacto" /></th>
			                                <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.email.direccion" /></th>
			                                <th data-class="expand" class="col-md-2"><spring:message code="mihabitat.estadocuenta.enviar" /></th>
			                            </tr>
			                        </thead>
			                        <tbody data-bind="foreach : { data: $root.emailsEnvio }">
			                            <tr>
			                                <td data-bind="text: contacto"></td>
			                                <td data-bind="text: direccion"></td>
			                                <td style="text-align: center;">
		                                        <label class="checkbox"> <input type="checkbox"
		                                            name="checkbox-inline" data-bind="checked: seleccionado">
		                                            <i></i>
		                                        </label>
			                                </td>
			                            </tr>
			                        </tbody>
			                    </table>
		                    </div>
		                </div>
		                <div class="row">
		                  &nbsp;
		                </div>
		                <div class="row">
		                    <div class="col-sm-12 col-md-12 col-lg-12">
		                        <label class="label"><spring:message code="mihabitat.estadocuenta.enviar.otros" /></label>
		                        <input class="form-control" type="text" name="otros" id="otros" 
		                            placeholder="<spring:message code="mihabitat.estadocuenta.enviar.otros" />"
		                            data-bind="value: $root.otrosEnvio">
		                    </div>
		                </div>
		                <div class="row">
		                    <div class="col-sm-12 col-md-12 col-lg-12">
		                        <label class="label"><spring:message code="mihabitat.estadocuenta.enviar.mensaje" /></label>
		                       <textarea class="form-control" rows="3" name="mensaje" maxlength="256"
		                            data-bind="value: $root.mensajeEnvio"></textarea>
		                    </div>
		                </div>
	                </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bind="click : $root.enviar">
                    <spring:message code="mihabitat.botones.aceptar" />
                </button>
            </div>
        </div>
    </div>
</div>