<div id="modal-cambiar-password" class="modal fade" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog" style="width: 300px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title"><spring:message code="mihabitat.password.titulo" /></h4>
			</div>
			<form id="form-cambiar-password" novalidate="novalidate">
				<div class="modal-body">
					<div class="row">
						<div class="form-group">
							<div class="col-md-11">
								<label for="password-actual" class="control-label"><span
									class="error-required">*</span><spring:message code="mihabitat.password.actual" /></label> <input
									type="password" id="password-actual"
									placeholder="Contraseña actual" maxlength="13"
									class="form-control" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<div class="col-md-11">
								<label for="new-password" class="control-label"><span
									class="error-required">*</span><spring:message code="mihabitat.password.nuevo" /></label> <input
									type="password" id="new-password"
									placeholder="Nuevo Password" maxlength="13"
									class="form-control" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-11">
							<div class="form-group">
								<label for="confirmar-password" class="control-label"><span
									class="error-required">*</span><spring:message code="mihabitat.usuarios.password.confirmar" /></label> <input
									type="password" id="confirmar-password"
									placeholder="Confirmar password" maxlength="13"
									class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn" onclick="cambiarPassword()"><spring:message code="mihabitat.login.password.enviar" /></button>
					<button type="button" class="btn" onclick="cancelarPassword()"><spring:message code="mihabitat.password.cancelar" /></button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	function cambiarPassword(){
			if($('#new-password').val() === $('#confirmar-password').val()){
				$.ajax({
					url : contextPath + "/password/recuperar/cambiar",
					dataType: "json",			
					data : {
						passwordActual : $('#password-actual').val(),
						passwordNuevo :  $('#new-password').val()
					},
					success: function(data){
						if (data) {
							notificacionExito("Se actualizo correctamente su contraseña. Se ha enviado un correo de confirmación a su cuenta de correo.");
							$('#confirmar-password').val("");
							$('#new-password').val("");
							$('#password-actual').val("");
	    				} else {
	    					notificacionAdvertencia("No se puedo enviar el password, comprueba el email ingresado");
	    					$('#password-actual').val("");
	    				}
					}
				});
			}
	};		
	
	function cancelarPassword(){
		$("#modal-cambiar-password").modal("hide");
		$('#confirmar-password').val("");
		$('#new-password').val("");
		$('#password-actual').val("");
	};
</script>