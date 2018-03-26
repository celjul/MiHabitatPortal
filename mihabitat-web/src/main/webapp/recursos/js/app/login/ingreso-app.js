var IngresoViewModel = function() {
	self = this;

	self.ingresar = function() {
		if ($("#login-form").valid()) {
			$("#login-form").submit();
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}

	$("#btnPassword").click(function() {
		if($("#form-forget-password").valid()){
			$.ajax({
    			async : true,
    			cache : false,
    			url : contextPath + '/password/recuperar/enviar',
    			type : 'POST',
    			dataType : 'json',
    			data : {
    				email : $("#email-recuperar-password").val()
    			},
    			success : function(data) {
    				if (data) {
    					notificacionExito("El nuevo password se envio a tu email.");
    					$("#email-recuperar-password").val("");
    				} else {
    					notificacionAdvertencia("No se puedo enviar el password, comprueba el email ingresado");
    				}
    			},
    			error : function(jqXHR, textStatus, errorThrown) {
    				notificacionError("No se puedo enviar el password.");
    			}
    		});
    	}
	});
}