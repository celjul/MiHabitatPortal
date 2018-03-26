var Detalle = function() {
	var self = this;
	
	self.id = ko.observable();
	self.concepto = ko.observable();
	self.movimientoDetallle = new MovimientoDetalle();
	
	self.limpiar = function() {
		self.id(undefined);
		self.concepto(undefined);
		self.movimientoDetallle.limpiarMD();
	}
	
	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.concepto(data ? data.concepto :undefined);
		self.movimientoDetallle.cargarMD(data ? data.movimientoDetallle : undefined);
	}
	
	self.getJson = function () {
        var detalle = self.estructurar(ko.toJS(self));
        detalle = validarObject(detalle);
        return detalle;
    }
	
	self.estructurar = function(data) {
		if (data.movimientoDetallle) {
			data.movimientoDetallle = self.movimientoDetallle.getJsonMD();
		}
		return data;
	}
}