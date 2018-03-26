var Estado = function(data) {
	var self = this;
	
	self.id =  ko.observable(data ? data.id : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	self.pais = new Pais(data ? data.pais : undefined);
	self.municipios = ko.observableArray([]);

	self.cargar = function(data) {
		self.id(data ? data.id : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.cargarMunicipios();
	}
	
	self.limpiar = function() {
		self.id(undefined);
		self.nombre(undefined);
		self.municipios([]);
	}
	
	self.cargarMunicipios = function(direccion) { 
		if (direccion instanceof Direccion) {
			self.municipios([]);
			direccion.colonia.municipio.limpiar();
			direccion.colonia.limpiar();
		}
		self.municipios([]);
		if (self.id()) {
			var path = String.format("{0}/direccion/estado/{1}/municipios", contextPath, self.id());
			$.ajax({
				url: path,
				async : false,
				success: function(items) {
					ko.utils.arrayForEach(items, function(m) {
				        var municipio = new Municipio(m);
				         self.municipios.push(municipio);
				     });
		        },
			}); 
		}
	}

	self.getJson = function() {
        var estado = self.estructurar(ko.toJS(self));
        estado = validarObject(estado);
        return estado;
    }
	
	self.estructurar = function(data) {
        if (data.pais) {
            data.pais = self.pais.getJson();
        }
        if (data.municipios) {
        	 delete data.municipios;
        }
        return data;
    }
}