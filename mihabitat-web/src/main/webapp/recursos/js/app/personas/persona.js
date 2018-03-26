var Persona = function(data) {
	var self = this;

	self.apellidoMaterno = ko.observable(Object.get(data, "apellidoMaterno"));
	self.apellidoPaterno = ko.observable(Object.get(data, "apellidoPaterno"));
	self.emails = ko.observableArray(Object.getArray(data, "emails"));
	self.id = ko.observable(Object.get(data, "id"));
	self.nombre = ko.observable(Object.get(data, "nombre"));
	self.telefonos = ko.observableArray(Object.getArray(data, "telefonos"));
//	self.usuario = new Usuario(data ? data.usuario : undefined);
	
	
	self.nombre.capitalize();
	self.apellidoMaterno.capitalize();
	self.apellidoPaterno.capitalize();
	
	self.nombreCompleto = ko.computed(function() {
		var nombreCompleto = String.returnValue(self.nombre());
		nombreCompleto += String.returnValue(self.apellidoPaterno());
		nombreCompleto += String.returnValue(self.apellidoMaterno());
		if (nombreCompleto == "") {
			nombreCompleto = "Sin Nombre";
		}
		return nombreCompleto;
	}, self);
	
	self.existe = function(email) {
		if (!self.id() && String.returnValue(email.direccion()).length > 0) {
			$.ajax({
				url : contextPath + '/personas/existe',
				type : "GET",
				dataType : 'json',
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
	}
	
	self.agregarEmail = function() {
		self.emails.push(new Email());
	}
	
	self.removerEmail = function(data) {
		bootbox.confirm("¿Deseas remover el email?", function(result) {
			if (result){
				var email = ko.toJS(data);
				if (email.id) {
					$.ajax({
						url : String.format("{0}/{1}/emails/{2}", PathConfig.personas, self.id(), email.id),
						type : "DELETE",
						success : function(response) {
							if (response) {
								self.emails.remove(data);
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							notificacionError("Ocurrio un error al eliminar el email.");
						}
					});
				} else {
					self.emails.remove(data);
				}
			}
		}); 
	}
	
	self.agregarTelefono = function() {
		self.telefonos.push(new Telefono());
	}
	
	self.removerTelefono = function(data) {
		bootbox.confirm("¿Deseas remover el telefono?", function(result) {
			if (result){
				var tel = ko.toJS(data);
				
				if (tel.id) {
					$.ajax({
						url : String.format("{0}/{1}/telefonos/{2}", PathConfig.personas, self.id(), tel.id),
						type : "DELETE",
						success : function(response) {
							if (response) {
								self.telefonos.remove(data);
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							notificacionError("Ocurrio un error al eliminar el teléfono.");
						}
					});
				} else {
					self.telefonos.remove(data);
				}
			}
		}); 
	}

	self.cargar = function(data) {
		self.apellidoMaterno(data ? data.apellidoMaterno : undefined);
		self.apellidoPaterno(data ? data.apellidoPaterno : undefined);
		
		if (data && data.emails && data.emails.length > 0) {
			ko.utils.arrayForEach(data.emails, function(e) {
				var email = new Email();
				email.cargar(e);
				self.emails.push(email);
			});
		}

		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);

		if (data && data.telefonos && data.telefonos.length > 0) {
			ko.utils.arrayForEach(data.telefonos, function(t) {
				var telefono = new Telefono();
				telefono.cargar(t);
				self.telefonos.push(telefono);
			});
		}
	}

	self.limpiar = function() {
		self.apellidoMaterno(undefined);
		self.apellidoPaterno(undefined);
		self.emails([]);
		self.id(undefined);
		self.nombre(undefined);
		self.telefonos([]);
	}

	self.getJson = function() {
		var contacto = self.estructurar(ko.toJS(self));
		
		contacto = validarObject(contacto);
		return contacto;
	}

	self.estructurar = function(data) {
		if (data.nombreCompleto) {
			delete data.nombreCompleto;
		}
		if (data && data.emails && data.emails.length > 0) {
			var emails = [];
			ko.utils.arrayForEach(self.emails(), function(e) {
				var email = e.getJson();
				email["@class"] = "com.bstmexico.mihabitat.comunes.personas.model.EmailPersona";
				
				emails.push(email);
			});
			data.emails = emails;
		}
		
		if (data && data.telefonos && data.telefonos.length > 0) {
			var telefonos = [];
			ko.utils.arrayForEach(self.telefonos(), function(t) {
				var telefono = t.getJson();
				telefono["@class"] = "com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona";
				
				telefonos.push(telefono);
			});
			data.telefonos = telefonos;
		}

		return data;
	}
}