var MovimientoDetalle = function() {
	
	Movimiento.call(this);
	var self = this;
	
	self.aplicado = ko.observable();
	self.cancelado = ko.observable();
	self.cuenta = new Cuenta();
	self.tipo = new Catalogo();
	
	self.limpiarMD = function() {
		self.limpiar();
		
		self.aplicado(undefined);
		self.cancelado(undefined);
		self.cuenta.limpiar();
		self.tipo.limiar();
	}
	
	self.cargarMD = function(data) {
		self.cargar(data);
		
		self.aplicado(data ? data.aplicado : undefined);
		self.cancelado(data ? data.cancelado : undefined);
		self.cuenta.cargar(data ? data.cuenta : undefined);
		self.tipo.cargar(data ? data.tipo : undefined);
	}
	
	self.getJsonMD = function () {
        var movimiento = self.estructurarMD(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }
	
	self.estructurarMD = function(data) {
		data = self.getJson();
		
		if (data.cuenta) {
			data.cuenta = {
					id : data.cuenta.id
			}
		}
		if (data.tipo) {
			data.tipo = {
					id : data.tipo.id
			}
		}
		return data;
	}
}