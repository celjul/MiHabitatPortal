var ContactoDepartamento = function(data) {
	var self = this;
	
	self.id = new ContactoDepartamentoId(data ? data.id : undefined);
	self.habitante = ko.observable(data ? data.habitante : false);
	self.principal = ko.observable(data ? data.principal : false);
	self.propietario = ko.observable(data ? data.propietario : false);
	
	self.cargar = function(data) {
		self.id.cargar(data ? data.id : undefined);
		self.habitante(data ? data.habitante : false);
		self.principal(data ? data.principal : false);
		self.propietario(data ? data.propietario : false);
	}
	
	self.limpiar = function() {
		self.id.limpiar();
		self.habitante(false);
		self.principal(false);
		self.propietario(false);
	}
	
	self.getJson = function() {
		var cd = self.estructurar(ko.toJS(self));
		cd = validarObject(cd);
		return cd;
	}
	
	self.estructurar = function(data) {
		if (data.id) {
			data.id = self.id.getJson();
		}
		return data;
	}
}

var ContactoDepartamentoId = function(data) {
	var self = this;
	
	self.contacto = new Contacto(data ? data.contacto : undefined);
	self.departamento = new Departamento(data ? data.departamento : undefined);
	
	self.cargar = function(data) {
		self.contacto.cargar(data ? data.contacto : undefined);
		self.departamento.cargar(data ? data.departamento : undefined);
	}
	
	self.limpiar = function() {
		self.contacto.limpiar();
		self.departamento.limpiar();
	}
	
	self.getJson = function() {
		var cdId = self.estructurar(ko.toJS(self));
		cdId = validarObject(cdId);
		return cdId;
	}
	
	self.estructurar = function(data) {
		if (data.contacto) {
			data.contacto = self.contacto.getJson();
		}
		if (data.departamento) {
			data.departamento = self.departamento.getJson();
		}
		return data
	}
}