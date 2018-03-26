var	TipoConsumoViewModel = function(data) {
	var self = this;
	 
	self.consumo = new TipoConsumo();
	self.operacionCorrecta = ko.observable(false);
	if (data && data.consumo) {
		self.consumo.cargar(data.consumo);
	}
	if (data && data.condominio) {
		self.consumo.condominio.cargar(data.condominio);
	}
	
	self.cuenta = new Cuenta();
	self.cuentas = ko.observableArray([]);
    if (data && data.cuentas) {
//        $.each(data.cuentas, function(i, c) {
//            var cuenta = new Cuenta();
//            cuenta.cargar(c);
//            self.cuentas.push(c);
//        });
		self.cuentas(ordenar(data.cuentas,true));
    }
    
    self.catalogoUnidad = ko.observableArray([]);
    if (data && data.catalogoUnidad) {
        $.each(data.catalogoUnidad, function(i, c) {
            var catalogo = new Catalogo();
            catalogo.cargar(c);
            self.catalogoUnidad.push(catalogo);
        });
    }
    
    self.catalogoTipoConsumo = ko.observableArray([]);
    if( data && data.catalogoTipoConsumo ){
    	$.each( data.catalogoTipoConsumo, function( i, tc ) {
    		var catalogo = new Catalogo();
    		catalogo.cargar(tc);
    		self.catalogoTipoConsumo.push(catalogo);
    	});
    }
    
    if(!self.consumo.id()){
    	self.consumo.catalogoTipoConsumo.id(self.catalogoTipoConsumo()[0].id());
    }

	self.editar = function(data) {
		if (data && data.consumo) {
			self.consumo.cargar(data.consumo);
		}
	}

	self.limpiar = function() {
		self.consumo.limpiar();
	}
    
    self.guardar = function(){
    	if ($("#consumo-form").valid()) {
    		var consumo = JSON.stringify(self.consumo.getJson());  
            console.log(consumo);
            console.log(ko.toJS(data));
            $.ajax({
				async : false,
				cache : false,
				url : contextPath + "/administrador/consumos/guardar",
                type : 'POST',
                dataType : 'json',
                data : consumo,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.consumo.limpiar();
                    self.consumo.cargar(data);
					$("#myModalConsumo").modal("hide");
					notificacionExito("El consumo se han guardado correctamente");
					self.operacionCorrecta(true);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	notificacionError("Ocurrio un error al guardar el consumo");
					self.operacionCorrecta(false);
                }
             });
        } else {
        	notificacionAdvertencia("El formulario tiene errores");
			self.operacionCorrecta(false);
        }
    }
    
    self.actualizar = function() {
		if ($("#consumo-form").valid()) {
			var consumo = JSON.stringify(self.consumo.getJson());
			console.log(consumo);
			$.ajax({
				async : false,
				cache : false,
				url : contextPath + "/administrador/consumos/actualizar",
				type : 'POST',
				dataType : 'json',
				data : consumo,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.consumo.limpiar();
					self.consumo.cargar(data);
					$("#myModalConsumo").modal("hide");
					notificacionExito("El consumo se ha actualizado correctamente");
					self.operacionCorrecta(true);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al actualizar el consumo");
					self.operacionCorrecta(false);
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
			self.operacionCorrecta(false);
		}	
	}
    
}

var ListaConsumoViewModel = function(data) {
	self = this;

	self.tipoConsumoViewModel = new TipoConsumoViewModel(data);
	
	self.consumos = ko.observableArray([]);

	if (data.consumos != undefined && data.consumos.length > 0) {
		ko.utils.arrayForEach(data.consumos, function(t) {
			var tc = new TipoConsumo();
			tc.cargar(t);
			self.consumos.push(tc);
		});
	}

	self.nuevoConsumo = function(data) {
		self.tipoConsumoViewModel.limpiar();
		$("#unidad").select2();
		$("#unidadLectura").select2();
		$("#unidadConversion").select2();
		$("#cuenta").select2();
	}

	self.editarConsumo = function(data) {
		$.ajax({
			async : true,
			cache : false,
			url : contextPath + "/administrador/consumos/actualizar/" + (typeof data.id === "function" ? data.id() : data.id),
			type : 'GET',
			dataType : 'json',
			data : '',
			success : function(data) {
				/*self.mantenimientoCondominioViewModel = new MantenimientoCondominioViewModel({
				 mantenimiento : data
				 });*/
				self.tipoConsumoViewModel.editar({
					consumo : data
				});
				$("#unidad").select2();
				$("#unidadLectura").select2();
				$("#unidadConversion").select2();
				$("#cuenta").select2();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				notificacionError("Ocurrio un error al guardar el consumo");
			}
		});
	}

	self.guardarConsumo = function(data) {
		self.tipoConsumoViewModel.guardar();
		if(self.tipoConsumoViewModel.operacionCorrecta()) {
			var consumo = new TipoConsumo();
			consumo.cargar(self.tipoConsumoViewModel.consumo.getJson());
			/*var consumo = self.tipoConsumoViewModel.consumo;*/065127
			var item = ko.utils.arrayFirst(self.tipoConsumoViewModel.cuentas(), function(a) {
				return a.id === consumo.cuenta.id();
			});
			item.nombre = item.nombre.replace('-- ', '');
			item.nombre = item.nombre.replace('--', '');
			consumo.cuenta.cargar(item);
			item = ko.utils.arrayFirst(self.tipoConsumoViewModel.catalogoUnidad(), function(c) {
				return c.id() === consumo.unidad.id();
			});
			consumo.unidad.descripcion(item.descripcion());
			item = ko.utils.arrayFirst(self.tipoConsumoViewModel.catalogoTipoConsumo(), function(tc) {
				return tc.id() === consumo.catalogoTipoConsumo.id();
			});
			consumo.catalogoTipoConsumo.descripcion(item.descripcion());
			self.consumos.push(consumo);
		}
	}

	self.actualizarConsumo = function(data) {
		self.tipoConsumoViewModel.actualizar();
		if(self.tipoConsumoViewModel.operacionCorrecta()) {
			var consumo = self.tipoConsumoViewModel.consumo;
			var encontrado = ko.utils.arrayFirst(self.consumos(), function(item) {
				return item.id() == consumo.id();
			});
			encontrado.cargar(consumo.getJson());
			var item = ko.utils.arrayFirst(self.tipoConsumoViewModel.cuentas(), function(a) {
				return a.id === consumo.cuenta.id();
			});
			item.nombre = item.nombre.replace('-- ', '');
			item.nombre = item.nombre.replace('--', '');
			encontrado.cuenta.cargar(item);
			item = ko.utils.arrayFirst(self.tipoConsumoViewModel.catalogoUnidad(), function(c) {
				return c.id() === consumo.unidad.id();
			});
			encontrado.unidad.descripcion(item.descripcion());
			item = ko.utils.arrayFirst(self.tipoConsumoViewModel.catalogoTipoConsumo(), function(tc) {
				return tc.id() === consumo.catalogoTipoConsumo.id();
			});
			encontrado.catalogoTipoConsumo.descripcion(item.descripcion());

		}
	}
	
	/*if (data.consumos != undefined && data.consumos.length > 0) {
		ko.utils.arrayForEach(data.consumos, function(c) {
			self.consumos.push(c);
		});
	}
	
	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/consumos/actualizar/" + data.id;
	};*/
}