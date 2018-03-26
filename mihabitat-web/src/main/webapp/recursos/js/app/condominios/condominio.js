var Condominio = function(data) {
	var self = this;

	self.id = ko.observable(data ? data.id : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	self.direccion = new Direccion(data ? data.direccion :undefined);
	self.administradores = ko.observableArray(data ? data.administradores : []);

	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.direccion.cargar(data ? data.direccion : undefined);
		 if (data && data.administradores && data.administradores.length > 0) {
			  ko.utils.arrayForEach(data.administradores, function(u) {
//				var usuario = new Usuario();
//				usuario.cargar(u);
				self.administradores.push(u.id);
			});
		 }
	}

	self.limpiar = function() {
		self.id(undefined);
		self.nombre(undefined);
		self.direccion.limpiar();
	}

	self.getJson = function() {
		var condominio = self.estructurar(ko.toJS(self));
		condominio = validarObject(condominio);
		return condominio;
	}
	
	self.estructurar = function(data) {
		if (data.administradores && data.administradores.length > 0) {
				var administradores = [];
				ko.utils.arrayForEach(self.administradores(), function(ad) {
				try {
					 var usuario = ad.getJson();
				}catch(err){
					var usuario= {
						id: ad	
					}
				}
				administradores.push(usuario);
			});
			  data.administradores = administradores;
		}
		if(data.direccion){
			data.direccion = self.direccion.getJson();
		}
		return data;
	}
	
	self.guardar = function() {
 		if ($("#condominio-form").valid()) {
			var condominio = JSON.stringify(self.getJson());
			console.log(condominio);
			
			var metodo = "POST";

			if (self.id()) {
				metodo = "PUT";
			}
			
			$.ajax({
				url : String.format("{0}", PathConfig.condominios),
				type : metodo,
				dataType : "json",
				data : condominio,
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					self.limpiar();
					notificacionExito("El condominio se ha guardado correctamente");
					setTimeout(function() {
						location.href = String.format("{0}/lista", PathConfig.condominios )
					}, PathConfig.timeout);
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