var Consumo = function(data) {
	
	var self = this;
	
	self.costoUnidad = ko.observable();
	self.id = ko.observable();
	self.tipo = new TipoConsumo();
	self.catalogoTipo = new Catalogo();
	
	self.limpiar = function() {
		self.costoUnidad( undefined );
		self.id( undefined );
		self.tipo.limpiar();
		self.catalogoTipo.limpiar();
	}
	
	self.cargar = function( data ) {
		self.costoUnidad( data ? data.costoUnidad : undefined );
		self.id( data ? data.id : undefined );
		self.tipo.cargar( data ? data.tipo : undefined );
		self.catalogoTipo.cargar( data ? data.catalogoTipo : undefined );
	}
	
	self.getJson = function() {
		var consumo = self.estructurar( ko.toJS( self ) );
		consumo = validarObject(consumo);
		return consumo;
	}	
	
	self.estructurar = function( data ) {
		if( data && data.tipo && data.tipo.id) {
			data.tipo = self.tipo.getJson();
		} else {
			delete data.tipo;
		}
		if( data && data.catalogoTipo) {
			data.catalogoTipo = self.catalogoTipo.getJson();
		}
		return data;
	}
}