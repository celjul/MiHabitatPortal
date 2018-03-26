var AgrupadorSat = function(data) {
	var self = this;
	
	self.codigo = ko.observable(data ? data.codigo : undefined);
	self.id = ko.observable(data ? data.id : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	
	self.cargar = function(data) {
		self.codigo(data ? data.codigo : undefined);
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
	}
	
	self.limpiar = function() {
		self.codigo(undefined);
		self.id(undefined);
		self.nombre(undefined);
	}
	
	self.getJson = function() {
		 var agrupadorSat = ko.toJS(self);
		 agrupadorSat = validarObject(agrupadorSat);
	     return agrupadorSat;
	}
	
}