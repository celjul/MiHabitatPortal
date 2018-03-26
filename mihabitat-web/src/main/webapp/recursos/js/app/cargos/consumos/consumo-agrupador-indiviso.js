var ConsumoAgrupadorIndiviso = function() {
	
	Consumo.call( this );
	
	var self = this;
	
	self.consumo = ko.observable();
	self.factor = ko.observable();
	self.type = ko.observable("indiviso");
	self.total = ko.computed(function() {
        var consumo = 0;
        if (self.consumo() && self.costoUnidad()) {
                consumo = parseFloat(self.consumo() * self.costoUnidad()).toFixed(2);
        }
        return consumo;
    });
	self.consumoTotal = ko.computed(function(){
		var consumoTotal = 0;
		if(self.consumo){
			if (self.total && self.costoUnidad()) {
				consumoTotal = parseFloat(self.total() / self.costoUnidad()).toFixed(4);
			}
			return consumoTotal;
		}
	});
	self.type = "indiviso";
	
	self.limpiarCAI = function() {
		self.limpiar();
		self.consumo( undefined );
		self.factor( undefined );
		self.type( undefined );
	}
	
	self.cargarCAI = function( data ) {
		self.cargar( data );
		self.consumo( data ? data.consumo : undefined );
		self.factor( data ? data.factor : undefined );
		self.type( data ? data.type : undefined );
	}
	
	self.getJsonCAI = function( data ) {		
		var consumo = self.estructurarCAI( ko.toJS( self ) );
		consumo = validarObject( consumo );
		return consumo;
	}
	
	self.estructurarCAI = function( data ) {
		data = self.getJson();
		delete data.total;
		delete data.consumoTotal;
		return data;
	}
}