var Gasto = function() {
	var self = this;
	
	self.id = ko.observable();
	self.condominio = new Condominio();
	self.detalles = ko.observableArray();
	self.fecha = ko.observable();
	self.metodoPago = new Catalogo();
	self.movimientoGasto = new MovimientoGasto();
	self.proveedor = new Catalogo();
	
	self.limpiar = function() {
		self.id(undefined);
		self.condominio.limpiar();
		self.detalles([]);
		self.fecha(undefined);
		self.metodoPago.limpiar();
		self.movimientoGasto.limpiarMG();
		self.proveedor.limpiar();
	}
	
	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.condominio.cargar(data ? data.condominio : undefined);
		
		if (data && data.detalles) {
			ko.utils.arrayForEach(data.detalles, function (d) {
                var detalle = new Detalle();
                detalle.cargar(d);
                self.detalles.push(detalle);
            });
		}
		
		self.fecha(data ? data.fecha : undefined);
		self.metodoPago.cargar(data ? data.metodoPago : undefined);
		self.movimientoGasto.cargarMG(data ? data.movimientoGasto : undefined);
		self.proveedor.cargar(data ? data.proveedor : undefined);
	}
	
	self.getJson = function () {
        var gasto = self.estructurar(ko.toJS(self));
        gasto = validarObject(gasto);
        return gasto;
    }
	
	self.estructurar = function(data) {
		if (data.condominio) {
			data.condominio = {
					id: data.condominio.id
			}
		}
		if (data.detalles) {
            var detalles = [];
            ko.utils.arrayForEach(self.detalles(), function (d) {
                var detalle = d.getJson();
                detalle.movimientoDetallle.aplicado = true;
                detalle.movimientoDetallle.cancelado = false;
                detalle.movimientoDetallle.fecha = data.fecha;
                detalle.movimientoDetallle.tipo = {
    					id: AppConfig.catalogos.movimientos.tipos.pago
    			}
                detalles.push(detalle);
            });
            data.detalles = detalles;
        }
		if (data.metodoPago) {
			data.metodoPago = {
					id: data.metodoPago.id
			}
		}
		if (data.movimientoGasto) {
			data.movimientoGasto = self.movimientoGasto.getJsonMG();
			data.movimientoGasto.aplicado = true;
			data.movimientoGasto.cancelado = false;
			data.movimientoGasto.fecha = data.fecha;
			data.movimientoGasto.tipo = {
					id: AppConfig.catalogos.movimientos.tipos.pago
			}
		}
		if (data.proveedor) {
			data.proveedor = {
					id: data.proveedor.id
			}
		}
		return data;
	}
	
	self.agregarDetalle = function() {
		self.detalles.push(new Detalle());
	}
	
	self.eliminarDetalle = function(data) {
		bootbox.confirm("¿Deseas remover el detalle?", function(result) {
			if (result){
				self.detalles.remove(data);
			}
		}); 
	}
}