var Contacto = function(data) {
	Persona.call(this, data);
	
	var self = this;
	
	self.id = ko.observable(data ? data.id : undefined)
	self.persona = new Persona(data ? data.persona : undefined)
	self.activo = ko.observable(data ? data.activo : undefined);
	self.condominio = new Condominio(data ? data.condominio : undefined);
	self.departamentos = ko.observableArray(data ? data.departamentos : []);
	self.usuario = new Usuario();
	
	self.existe = function(email) {
		if ((!self.id() || self.id() < 0) && String.returnValue(email.direccion()).length > 0) {
			$.ajax({
				async: false,
				cache : false,
				url : contextPath + '/administrador/contactos/existe',
				type : 'POST',
				dataType : 'json',
				data : {
					condominio : self.condominio.id(),
					email : email.direccion()
				},
				success : function(data) {
					if (data) {
						bootbox.confirm("El contacto ya existe ¿Deseas cargar los datos?", function(result) {
							if (result){
								self.limpiar();
								self.cargar(data);
								/*$("li.departamento > a").last().click();
								for (var i = 0; i < $("li.departamento > a").length; i++) {
									$("#form-departamento-" + i).validate();
								}
								$("li.contacto > a").last().click();
								for (var i = 0; i < $("li.contacto > a").length; i++) {
									$("#form-contacto-" + i).validate();
								}*/
							} else {
								email.direccion("");
							}
						});
					}
				}
			});
		}
	}
	
	self.agregarDepartamento = function() {
		self.departamentos.push(new ContactoDepartamento({
			habitante : false,
			principal: false,
			propietario: false,
			id : {
				departamento : {
					activo : true,
					condominio : {
						id : self.condominio.id()
					}
				}
			}
		}));
	}
	
	self.removerDepartamento = function(data) {
		bootbox.confirm("¿Deseas remover el departamento?", function(result) {
			if (result){
				self.departamentos.remove(data);
				$("li.departamento > a").first().click();
			}
		});
	}
	
	self.cargarContacto = function(data) {
	    self.cargar(data);
	    
        self.id(data ? data.id : undefined);
        self.activo(data ? data.activo : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);

        if (data && data.departamentos && data.departamentos.length > 0) {
            ko.utils.arrayForEach(data.departamentos, function(d) {
                var dc = new ContactoDepartamento();
                dc.cargar(d);
                self.departamentos.push(dc);
            });
        }
    }

	self.limpiar = function() {
		self.activo(undefined);
		self.condominio.limpiar();
		self.departamentos([]);
		self.persona.limpiar();
		self.apellidoMaterno(undefined);
		self.apellidoPaterno(undefined);
		self.emails([]);
		self.id(undefined);
		self.nombre(undefined);
		self.telefonos([]);
		self.usuario.limpiar();
	}

	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.apellidoMaterno(data ? data.apellidoMaterno : undefined);
		self.apellidoPaterno(data ? data.apellidoPaterno : undefined);

		if (data && data.emails && data.emails.length > 0) {
			ko.utils.arrayForEach(data.emails, function(e) {
				var email = new Email();
				email.cargar(e);
				self.emails.push(email);
			});
		}

		self.nombre(data ? data.nombre : undefined);

		self.usuario.cargar(data ? data.usuario : undefined);

		if (data && data.telefonos && data.telefonos.length > 0) {
			ko.utils.arrayForEach(data.telefonos, function(t) {
				var telefono = new Telefono();
				telefono.cargar(t);
				self.telefonos.push(telefono);
			});
		}

		if (data && data.condominio && data.condominio.id) {
			self.condominio.id(data.condominio.id);
		}
	}

	self.getJson = function() {
		var contacto = self.estructurar(ko.toJS(self));
		contacto = validarObject(contacto);
		return contacto;
	}

	self.estructurar = function(data) {
		data.persona = self.persona.getJson();

		if (data.condominio) {
			data.condominio = self.condominio.getJson();
		}
		if (data && data.departamentos && data.departamentos.length > 0) {
			var departamentos = [];
			ko.utils.arrayForEach(self.departamentos(), function(d) {
				var dc = d.getJson();
				departamentos.push(dc);
			});
			data.departamentos = departamentos;
		}

		if (data.nombreCompleto) {
			delete data.nombreCompleto;
		}
		if (data && data.emails && data.emails.length > 0) {
			var emails = [];
			ko.utils.arrayForEach(self.emails(), function(e) {
				var email = e.getJson();
				email["@class"] = "com.bstmexico.mihabitat.contactos.model.EmailContacto";

				emails.push(email);
			});
			data.emails = emails;
		}

		if (data && data.telefonos && data.telefonos.length > 0) {
			var telefonos = [];
			ko.utils.arrayForEach(self.telefonos(), function(t) {
				var telefono = t.getJson();
				telefono["@class"] = "com.bstmexico.mihabitat.contactos.model.TelefonoContacto";

				telefonos.push(telefono);
			});
			data.telefonos = telefonos;
		}

		if (data.usuario) {
			if(data.usuario.email){
				data.usuario = self.usuario.getJson();
			} else {
				delete data.usuario;
			}
		}

		return data;
	}
}
