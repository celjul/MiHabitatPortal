var ConsumoAgrupadorProrrateo = function() {
	
	Consumo.call( this );
	
	var self = this;
	
	self.consumo = ko.observable();
	self.factor = ko.observable();
	self.type = ko.observable("prorrateo");
	self.total = ko.computed(function() {
        var consumo = 0;
        if (self.consumo() && self.costoUnidad()) {
                consumo = parseFloat(self.consumo() * self.costoUnidad()).toFixed(2);
        }
        return consumo;
    });
	
	
	self.limpiarCAP  = function() {
		self.limpiar();
		self.consumo( undefined );
		self.factor( undefined );
		self.type( undefined );
	}
	
	self.cargarCAP  = function( data ) {
		self.cargar( data );
		self.consumo( data ? data.consumo : undefined );
		self.factor( data ? data.factor : undefined );
		self.type( data ? data.type : undefined );
	}
	
	self.getJsonCAP = function() {
		var consumo = self.estructurarCAP( ko.toJS( self ) );
		consumo = validarObject( consumo );
		return consumo;
	}
	
	self.estructurarCAP = function( data ) {
		data = self.getJson();
		delete data.total;
		return data;
	}
}