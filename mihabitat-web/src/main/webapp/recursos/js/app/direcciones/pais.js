var Pais = function(data) {
	var self = this;
	
	self.id = ko.observable(data ? data.id : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	self.estados = ko.observableArray([]);
	
	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.cargarEstados();
	}
		
	self.limpiar= function(data) {
		self.id(undefined);
		self.nombre(undefined);
		self.estados([]);
	}
		
	self.cargarEstados = function(direccion) {
		if (direccion instanceof Direccion) {
			self.estados([]);
			direccion.colonia.municipio.estado.limpiar(direccion);
			direccion.colonia.municipio.limpiar();
			direccion.colonia.limpiar();
		}
		self.estados([]);
		if (self.id()) {
			var path = String.format("{0}/direccion/pais/{1}/estados", contextPath, self.id());
			$.ajax({
				url: path,
				async : false,
				success: function(items) {
					ko.utils.arrayForEach(items, function(e) {
						var estado = new Estado(e);
						self.estados.push(estado);
					 });
				 },
			});
		}
	}
		
	self.getJson = function() {
		var pais = self.estructurar(ko.toJS(self));
		pais = validarObject(pais);
		return pais;
	 }
		
	self.estructurar = function(data) {
			if (data.pais) {
				data.pais = self.pais.getJson();
			}
			if (data.estados) {
				delete data.estados;
			}
			return data;
	 }
}