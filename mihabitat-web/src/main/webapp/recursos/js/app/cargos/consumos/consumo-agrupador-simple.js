var ConsumoAgrupadorSimple = function() {
	
	Consumo.call( this );
	
	var self = this;
	
	self.type = ko.observable("simple");
	
	self.limpiarCAS = function() {
		self.limpiar();
		self.type( undefined );
	}
	
	self.cargarCAS = function( data ) {
		self.cargar( data );
		self.type( data ? data.type : undefined );
	}
	
	self.getJsonCAS = function() {
		var consumo = self.estructurarCAS( ko.toJS( self ) );
		consumo = validarObject( consumo );
		return consumo;
	}
	
	self.estructurarCAS = function( data ) {
		data = self.getJson();
		return data;
	}
}