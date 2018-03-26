var PersonaAbstract = function(data) {
	var self = this;

	self.apellidoMaterno = ko.observable();
	self.apellidoPaterno = ko.observable();
	self.id = ko.observable();
	self.nombre = ko.observable();

	self.nombreCompleto = ko.computed(function() {
		var nombreCompleto = String.returnValue(self.nombre());
		nombreCompleto += String.returnValue(self.apellidoPaterno());
		nombreCompleto += String.returnValue(self.apellidoMaterno());
		if (nombreCompleto == "") {
			nombreCompleto = "Sin Nombre";
		}
		return nombreCompleto;
	}, self);


	self.cargar = function(data) {
		self.apellidoMaterno(data ? data.apellidoMaterno : undefined);
		self.apellidoPaterno(data ? data.apellidoPaterno : undefined);
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
	}

	self.limpiar = function() {
		self.apellidoMaterno(undefined);
		self.apellidoPaterno(undefined);
		self.id(undefined);
		self.nombre(undefined);
	}

	self.getJson = function() {
		var persona = self.estructurar(ko.toJS(self));
		contacto = validarObject(persona);
		return persona;
	}

	self.estructurar = function(data) {
		if (data.nombreCompleto) {
			delete data.nombreCompleto;
		}
		return data;
	}

	self.agregarEmail = function() {
		self.emails.push(new Email());
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

}