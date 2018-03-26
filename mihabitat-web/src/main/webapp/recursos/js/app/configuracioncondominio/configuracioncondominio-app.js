var ConfiguracionCondominioViewModel = function(data) {
	var self = this;
	
	self.configuracionCondominio = new ConfiguracionCondominio();
	if (data && data.configuracionCondominio) {
		self.configuracionCondominio.cargar(data.configuracionCondominio);
	}

	self.guardar = function() {
		if ($("#configuracioncondominio-form").valid()) {
			var configuracionCondominio = JSON.stringify(self.configuracionCondominio.getJson());
			console.log(configuracionCondominio);
			var metodo = "POST";
			if (self.configuracionCondominio.id()) {
				metodo = "PUT";
			}
			$.ajax({
				url : contextPath + '/administrador/condominios/configuracion/guardar',
				type : metodo,
				dataType : "json",
				data : configuracionCondominio,
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					self.configuracionCondominio.limpiar();
					self.configuracionCondominio.cargar(data);
					notificacionExito("La configuracion del condominio se ha guardado correctamente");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el condominio");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}
}