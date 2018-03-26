var ContactoProveedor = function(data) {
	PersonaAbstract.call(this, data);

	var self = this;

	self.emails = ko.observableArray();
	self.telefonos = ko.observableArray();


	self.cargarCP = function(data) {
		self.cargar(data);

		if (data && data.emails && data.emails.length > 0) {
			ko.utils.arrayForEach(data.emails, function(e) {
				var ec = new Email();
				ec.cargar(e);
				self.emails.push(ec);
			});
		}
		if (data && data.telefonos && data.telefonos.length > 0) {
			ko.utils.arrayForEach(data.telefonos, function(t) {
				var tc = new Telefono();
				tc.cargar(t);
				self.telefonos.push(tc);
			});
		}
	}
	
	self.limpiarCP = function() {
		self.limpiar();
		self.emails([]);
		self.telefonos([]);
	}
	
	self.getJsonCP = function() {
		var cd = self.estructurarCP(ko.toJS(self));
		cd = validarObject(cd);
		return cd;
	}
	
	self.estructurarCP = function(data) {
		data = self.estructurar(data);

		if (data && data.emails && data.emails.length > 0) {
			var emails = [];
			ko.utils.arrayForEach(self.emails(), function(e) {
				var email = e.getJson();
				email["@class"] = "com.bstmexico.mihabitat.proveedores.model.EmailContactoProveedor";
				emails.push(email);
			});
			data.emails = emails;
		}

		if (data && data.telefonos && data.telefonos.length > 0) {
			var telefonos = [];
			ko.utils.arrayForEach(self.telefonos(), function(t) {
				var telefono = t.getJson();
				telefono["@class"] = "com.bstmexico.mihabitat.proveedores.model.TelefonoContactoProveedor";
				telefonos.push(telefono);
			});
			data.telefonos = telefonos;
		}

		return data;
	}
}
