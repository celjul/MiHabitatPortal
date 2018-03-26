var ConfiguracionCondominio = function(data) {
	var self = this;

	self.id = ko.observable(data ? data.id : undefined);
	self.mensajeEstadoCuenta = ko.observable(data ? data.mensajeEstadoCuenta : undefined);
	self.mensajeAvisoCobro = ko.observable(data ? data.mensajeAvisoCobro : undefined);
	self.mensajeReciboPago = ko.observable(data ? data.mensajeReciboPago : undefined);

	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.mensajeEstadoCuenta(data ? data.mensajeEstadoCuenta : undefined);
		self.mensajeAvisoCobro(data ? data.mensajeAvisoCobro : undefined);
		self.mensajeReciboPago(data ? data.mensajeReciboPago : undefined);
	}

	self.limpiar = function() {
		self.id(undefined);
		self.mensajeEstadoCuenta('');
		self.mensajeAvisoCobro('');
		self.mensajeReciboPago('');
	}

	self.getJson = function() {
		var configuracioncondominio = self.estructurar(ko.toJS(self));
		configuracioncondominio = validarObject(configuracioncondominio);
		return configuracioncondominio;
	}
	
	self.estructurar = function(data) {

		return data;
	}
}