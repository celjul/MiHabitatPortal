var BancoSat = function(data) {
	var self = this;
	
	self.codigo = ko.observable(data ? data.codigo : undefined);
	self.id = ko.observable(data ? data.id : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	self.razon = ko.observable(data ? data.razon : undefined);
	
	self.cargar = function(data) {
		self.codigo(data ? data.codigo : undefined);
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.razon(data ? data.razon : undefined);
	}
	
	self.limpiar = function() {
		self.codigo(undefined);
		self.id(undefined);
		self.nombre(undefined);
		self.razon(undefined);
	}
	
	self.getJson = function() {
        var bancoSat = ko.toJS(self);
        bancoSat = validarObject(bancoSat);
        return bancoSat;
    }
}