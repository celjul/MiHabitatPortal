var ActivacionViewModel = function(data) {
	self = this;
	
	self.contacto = new Contacto();
	self.usuario = new Usuario();
	
	self.usuario.email(data.enlace.email);
	
	if (data && data.contacto) {
		self.contacto.cargar(data.contacto);
	}
	
	self.catalogoEmail = ko.observableArray([]);
	if (data && data.catalogoEmail) {
		$.each(data.catalogoEmail, function(i, ce) {
			var catalogo = new Catalogo();
			catalogo.cargar(ce);
			self.catalogoEmail.push(catalogo);
		});
	}
	
	self.catalogoTelefono = ko.observableArray([]);
	if (data && data.catalogoTelefono) {
		$.each(data.catalogoTelefono, function(i, ct) {
			var catalogo = new Catalogo();
			catalogo.cargar(ct);
			self.catalogoTelefono.push(catalogo);
		});
	}
	
	self.activar = function() {
		if ($("#contacto-form").valid()) {
			
			var contacto = self.contacto.getJson();
			var usuario = self.usuario.getJson();
			//usuario.persona = contacto.persona;
			usuario.persona.nombre = contacto.nombre;
			usuario.persona.apellidoPaterno = contacto.apellidoPaterno;
			usuario.persona.apellidoMaterno = contacto.apellidoMaterno;

			usuario.persona.emails = contacto.emails;
			usuario.persona.telefonos = contacto.telefonos;

			if(usuario.persona.emails) {
				$.each(usuario.persona.emails, function (i, em) {
					em['@class'] = 'com.bstmexico.mihabitat.comunes.personas.model.EmailPersona';
					em.id = undefined;
				});
			}

			if(usuario.persona.telefonos){
				$.each(usuario.persona.telefonos, function(i, tl) {
					tl['@class'] = 'com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona';
					tl.id = undefined;
				});
			}
			
			usuario = JSON.stringify(usuario);
			var url = String.format("{0}/activacion/activar/{1}", contextPath, 
					data.enlace.id);
			console.log(usuario);
			console.log(url);
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				data : usuario,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					if (data) {
						notificacionExito("Se ha activado tu cuenta correctamente");
						setTimeout(function() {
							location.href = contextPath + "/login";						   
						}, 1000);					   
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al activar tu cuenta");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}
}