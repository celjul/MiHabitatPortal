var ConsumoDepartamento = function() {
	
	Consumo.call( this );
	
	var self = this;
	
	self.lecturaAnterior = ko.observable();
	self.lecturaNueva = ko.observable();
	self.consumoFactor = ko.observable();
	self.type = ko.observable();
	self.consumo = ko.computed(function() {
		if(self.tipo.aplicaConversion()) {
			consumo = parseFloat(self.lecturaNueva() - self.lecturaAnterior()).toFixed(4);
		}
        else {
			consumo = parseFloat(self.lecturaNueva() - self.lecturaAnterior()).toFixed(4);
			consumo = parseFloat(consumo * self.tipo.factorConversion()).toFixed(4);
		}
        return consumo;
    });
	
	self.limpiarCD = function() {
		self.limpiar();
		self.lecturaAnterior( undefined );
		self.lecturaNueva( undefined );
		self.consumoFactor( undefined );
		self.type( undefined );
	}
	
	self.cargarCD = function( data ) {
		self.cargar( data );
		self.lecturaAnterior( data ? data.lecturaAnterior : undefined );
		self.lecturaNueva( data ? data.lecturaNueva : undefined );
		self.consumoFactor( data ? data.consumoFactor : undefined );
		self.type( data ? data.type : undefined );
	}
	
	self.getJsonCD = function() {
		var consumo = self.estructurarConsumoDep( ko.toJS( self ) );
		consumo.validarObject( consumo  );
		return consumo;
	}
	
	self.estructurarConsumoDep = function( data ) {
		data = self.getJson();
		delete data.consumo;
		return data;
	} 
}