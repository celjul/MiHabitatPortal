var Direccion = function(data) {
	var self = this;
	
	self.calle = ko.observable(data ? data.calle : undefined);
	self.colonia = new Colonia(data ? data.id : undefined);
	self.id= ko.observable(data ? data.id : undefined);
	self.noExterior= ko.observable(data ? data.noExterior : undefined);
	self.noInterior= ko.observable(data ? data.noInterior : undefined);
	self.referencias= ko.observable(data ? data.referencias : undefined);
	
	self.cargar = function(data) {
		self.calle (data ? data.calle : undefined);
	    self.id(data ? data.id : undefined);
		self.noExterior(data ? data.noExterior : undefined);
		self.noInterior(data ? data.noInterior : undefined);
		self.referencias(data ? data.referencias : undefined);
		self.colonia.municipio.estado.pais.id(data ? data.colonia.municipio.estado.pais.id : undefined);
		self.colonia.municipio.estado.id(data ? data.colonia.municipio.estado.id : undefined);
		self.colonia.municipio.id(data ? data.colonia.municipio.id : undefined);
		self.colonia.id(data ? data.colonia.id : undefined);
	}
	
	self.colonia.municipio.estado.pais.id.subscribe(function() {
		self.colonia.municipio.estado.pais.cargarEstados(self);
	});
	
	self.colonia.municipio.estado.id.subscribe(function() {
		self.colonia.municipio.estado.cargarMunicipios(self);
	})

	self.colonia.municipio.id.subscribe(function() {
		self.colonia.municipio.cargarColonias(self);
		
	})
	
	self.colonia.id.subscribe(function() {
		self.colonia.actualizarColonia(self);
		
	})
	
	self.limpiar = function() {
		self.calle (undefined);
		self.colonia.limpiar();
		self.id(undefined);
		self.noExterior(undefined);
		self.noInterior(undefined);
		self.referencias(undefined);
	}
	
	self.getJson = function() {
	        var direccion = self.estructurar(ko.toJS(self));
	        direccion = validarObject(direccion);
	        return direccion;
	    }
	 
	 self.estructurar = function(data) {
		 if (data.direccionCompleta) {
	            delete data.direccionCompleta;
	        }
	        if (data.colonia) {
	            data.colonia = self.colonia.getJson();
	        }
	      return data;
	    }
}