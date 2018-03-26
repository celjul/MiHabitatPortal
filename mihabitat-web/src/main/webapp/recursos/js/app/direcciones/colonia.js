var Colonia = function(data) {
	var self = this;

	self.codigoPostal = ko.observable(data ? data.codigoPostal : undefined);
	self.id = ko.observable(data ? data.id : undefined);
	self.municipio = new Municipio(data ? data.municipio : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	
	self.cargar = function(data) {
		self.codigoPostal(data ? data.codigoPostal : undefined);
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
	}

	self.limpiar = function() {
		self.codigoPostal(undefined);
		self.id(undefined);
		self.nombre(undefined);
	}
	
	self.actualizarColonia = function() {
		if (self.id()) {
			var item = ko.utils.arrayFirst(self.municipio.colonias(), function(i) {
				return i.id() === self.id();
		});
			if (item) {
				self.nombre(item.nombre());
				self.codigoPostal(item.codigoPostal());
			}
		}
	}
	
	self.getJson = function() {
        var colonia =self.estructurar( ko.toJS(self));
        colonia = validarObject(colonia);
        return colonia;
    }
	
	self.estructurar = function(data) {
        if (data.municipio) {
            data.municipio = self.municipio.getJson();
        }
        return data;
    }
}