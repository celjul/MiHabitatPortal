var Usuario = function(data) {
	var self = this;
	
	self.getRoles = function(roles) {
		if (roles.length > 0) {
			return $.map(roles, function(item) {
				return item.id;
			});
		}
	}
	self.id = ko.observable(Object.get(data, "id"));
	self.activo = ko.observable(Object.get(data, "activo"));
	self.password = ko.observable(Object.get(data, "password"));
	self.user = ko.observable(Object.get(data, "user"));
	self.roles = ko.observableArray(self.getRoles(Object.getArray(data, "roles")));
	self.email = ko.observable(Object.get(data, "email"));
	self.persona = new Persona(Object.get(data, "persona"));
	
	self.cargar = function(data) {
		self.id(Object.get(data, "id"));
		self.activo(Object.get(data, "activo"));
		self.password(Object.get(data, "password"));
		self.user(Object.get(data, "user"));
		
		var roles = self.getRoles(Object.getArray(data, "roles"));
		self.roles(roles);
		
		self.persona.cargar(Object.get(data, "persona"));
		self.email(Object.get(data, "email"));
	}

	self.limpiar = function() {
		self.id(undefined);
		self.activo(undefined);
		self.password(undefined);
		self.user(undefined);
		self.roles([]);
		self.email(undefined);
		self.persona.limpiar();
	}

	self.getJson = function() {
		var usuario = self.estructurar(ko.toJS(self));
		usuario = validarObject(usuario);
		
		if (usuario && usuario.persona) {
			delete usuario.persona.nombreCompleto;
			
			if (usuario.persona.emails) {
				$.each(usuario.persona.emails, function(i, item) {
					item["@class"] = "com.bstmexico.mihabitat.comunes.personas.model.EmailPersona";
				});
			}
			
			if (usuario.persona.telefonos) {
				$.each(usuario.persona.telefonos, function(i, item) {
					item["@class"] = "com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona";
				});
			}
		}

		return usuario;
	}	
	
	self.estructurar = function(data) {
		if (data && data.roles && data.roles.length > 0) {
			var roles = [];
			ko.utils.arrayForEach(self.roles(), function(r) {
				try {
					var rol = r.getJson();
				}catch(err){
					var rol= {
						id: r	
					}
				}
				roles.push(rol);
			});
			
			data.roles = roles;
		}
		return data;
	}  
	
	self.existe = function() {
		if (!self.id() && String.returnValue(self.user()).length > 4){
			$.ajax({
				//url : String.format("{0}/usuarios/existe", PathConfig.usuarios), // contextPath + '/personas/existe',
				url : String.format("{0}/activacion/usuarios/existe", contextPath), // contextPath + '/personas/existe',
				type : "GET",
				dataType : "json",
				data : {
					user : self.user()
				},
				success : function(data) {
					if (data) {
						notificacionAdvertencia("El usuario ya existe, ingrese otro.");
						self.user(undefined);
					}
				}
			});
		}
	}
	
	self.existeEmail = function(email) {
		$.ajax({
			//url : String.format("{0}/usuarios/existe", PathConfig.usuarios),
			url : String.format("{0}/activacion/usuarios/existe", contextPath),
			type : "GET",
			dataType : "json",
			data : {
				email : email.direccion()
			},
			success : function(data) {
				if (data) {
					bootbox.confirm("La persona ya existe ¿Deseas cargar los datos?", function(result) {
						if (result){
							self.limpiar();
							self.cargar(data);
							$("#roles").select2();
						} else {
							email.direccion("");
						}
					});
				}
			}
		});
	}

	self.existeEmailActivacion = function(email) {
		$.ajax({
			//url : String.format("{0}/usuarios/existe", PathConfig.usuarios),
			url : String.format("{0}/activacion/usuarios/existe", contextPath),
			type : "GET",
			dataType : "json",
			data : {
				email : email.direccion()
			},
			success : function(data) {
				if (data) {
					notificacionAdvertencia("Ya existe un usuario asignado a ese email, por favor intente con otro.");
					email.direccion("");
				}
			}
		});
	}
	
	self.guardar = function() {
 		if ($("#usuario-form").valid()) {
 			var metodo;
			var usuario = JSON.stringify(self.getJson());
			console.log(usuario);
			
			if (self.id()) {
				metodo = "PUT";
			} else {
				metodo = "POST";
			}
			
			$.ajax({
				url : String.format("{0}/usuarios", PathConfig.usuarios),
				type : metodo,
				dataType : 'json',
				data : usuario,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.limpiar();
					$("#confirmar").val("");
					notificacionExito("El usuario se ha guardado correctamente");
					setTimeout(function() {
						location.href = String.format("{0}/usuarios/lista", PathConfig.usuarios )
					}, PathConfig.timeout);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el usuario");
				}
			});
		} else {
				notificacionAdvertencia("El formulario tiene errores");
		}
	}
}