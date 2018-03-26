var Municipio = function(data) {
	var self = this;
	
	self.id =  ko.observable(data ? data.id : undefined);
	self.nombre =  ko.observable(data ? data.nombre : undefined);
	self.estado = new Estado(data ? data.estado : undefined);
	self.colonias = ko.observableArray([]);
	
	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.cargarColonias();
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.nombre(undefined);
		self.colonias([]);
	}
	
	self.cargarColonias = function(direccion) {
		if (direccion instanceof Direccion) {
				self.colonias([]);
				direccion.colonia.limpiar();
		}
		self.colonias([]);
		if (self.id()) {
			var path = String.format("{0}/direccion/municipio/{1}/colonias", contextPath, self.id());
			$.ajax({
		        url: path,
		        async : false,
		        success: function(items) {
		        	ko.utils.arrayForEach(items, function(c) {
		        		var colonia = new Colonia(c);
				        self.colonias.push(colonia);
				    });
		        	 
		        },
		   });  
		}
	}
	
	self.getJson = function() {
        var municipio = self.estructurar(ko.toJS(self));
        municipio = validarObject(municipio);
        return municipio;
    }
	
	self.estructurar = function(data) {
        if (data.estado) {
            data.estado = self.estado.getJson();
        }
        if (data.colonias) {
        	delete data.colonias;
        }
        return data;
    }
}