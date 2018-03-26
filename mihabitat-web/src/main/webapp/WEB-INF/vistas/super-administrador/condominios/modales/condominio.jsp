<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade" id="modal-condominio" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document" style="width: 80%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" ><spring:message code="mihabitat.usuarios.title" /></h4>
			</div>
			<div class="modal-body">
				<jsp:include page="../condominio.jsp" >
					<jsp:param value="true" name="hideButtons"/>
				</jsp:include>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button type="button" class="btn btn-primary" id="btn-modal-save-condominio" data-bind="click: $root.condominio.guardar">Guardar</button>
			</div>
		</div>
	</div>
</div>