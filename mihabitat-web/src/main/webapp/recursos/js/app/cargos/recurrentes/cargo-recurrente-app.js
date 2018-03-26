var CargoRecurrenteViewModel = function(data) {

    var self = this;
    
    self.cargo = new CargoRecurrente();
    self.departamentos = ko.observableArray([]);
    if (data && data.departamentos) {
        $.each(data.departamentos, function(i, d) {
            var departamento = new Departamento();
            departamento.cargar(d);
            departamento.grupos([]);
            if(d.grupos){
            	 $.each(d.grupos, function(i, g) {
                     var grupo = new GrupoCondominio();
                     grupo.cargar(g);
                     departamento.grupos.push(grupo);
                 });
            }
            self.departamentos.push(departamento);
        });
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
    
    
    self.catalogoMes = ko.observableArray([]);
    if (data && data.catalogoMes) {
        $.each(data.catalogoMes, function(i, m) {
            var mes = new Catalogo();
            mes.id(m.id);
			mes.descripcion(m.label);
			
            self.catalogoMes.push(mes);
        });
    }
    
    if(data && data.cargo.meses){
		ko.utils.arrayForEach(data.cargo.meses, function(m) {
			ko.utils.arrayForEach(self.catalogoMes(), function(cm) {
				if( m == cm.id() ) {
					cm.seleccionado( true );
				}
			});
        });
	}
    
    self.catalogoCargo = ko.observableArray([]);
    if (data && data.catalogoCargo) {
        $.each(data.catalogoCargo, function(i, c) {
        	var catalogo = new Catalogo();
            catalogo.cargar(c);
        	if(!(AppConfig.catalogos.cargo.tipos.consumo === catalogo.id())){
        		self.catalogoCargo.push(catalogo);
        	} 
        });
    }
    
    self.catalogoInteres = ko.observableArray([]);
    if(data && data.catalogoInteres){
    	$.each(data.catalogoInteres, function(i,tipoInteres){
    		var interes = new Catalogo();
    		interes.cargar(tipoInteres);
    		self.catalogoInteres.push(interes);
    	});
    }

	self.mantenimientos = ko.observableArray([]);
	if(data && data.mantenimientos){
		$.each(data.mantenimientos, function(i,m){
			var mantenimiento = new MantenimientoCondominio();
			mantenimiento.cargar(m);
			self.mantenimientos.push(mantenimiento);
		});
	}

	self.instalaciones = ko.observableArray([]);
	if(data && data.instalaciones){
		$.each(data.instalaciones, function(i,inst){
			var instalacion = new Instalacion();
			instalacion.cargar(inst);
			self.instalaciones.push(instalacion);
		});
	}

    if (data && data.cargo) {
        self.cargo.cargarCargoRecurrente(data.cargo, self.departamentos(), self.catalogoMes());
    }
    if (data && data.condominio) {
        self.cargo.condominio.cargar(data.condominio);
    }
    
    self.seleccionarMeses = ko.observable(false);
    self.seleccionarMeses.subscribe(function(val) {
        ko.utils.arrayForEach(self.catalogoMes(), function(m) {
            m.seleccionado(val);
        });
    });
    
    self.seleccionarDepartamentos = ko.observable(false);
    self.seleccionarDepartamentos.subscribe(function(val) {
    	var rows = $("#table-departamentos").dataTable()._('tr', {"filter":"applied"});
    	for (var i = 0; i < rows.length; i++) {
    		ko.utils.arrayForEach( self.departamentos(), function( d ) {
    			if( d.nombre() == rows[i][0] ) {
    				d.seleccionado( val );
    			}
    		});
    	}
    });
    
    self.cargo.todos.subscribe(function(value){
    	if(value){
    		self.seleccionarDepartamentos(!value)
    		ko.utils.arrayForEach(self.departamentos(), function(d) {
                d.seleccionado(!value);
            });
    	}	
    });
    
    self.guardar = function() {
		if(self.validaFecha()){
			if (self.validaDepartamentos()) {
				if(self.validaMeses()) {
    			if ($("#cargo-form").valid()) {
    				var cargo = JSON.stringify(self.cargo.getJsonCargoRecurrente(self.departamentos(), self.catalogoMes()));
    				console.log(cargo);
//    				return false;
    				$.ajax({
    					url : contextPath + "/administrador/cargos-recurrentes/guardar",
    					type : 'POST',
    					dataType : 'json',
    					data : cargo,
    					contentType : 'application/json',
    					mimeType : 'application/json',
    					success : function(data) {
    						self.cargo.limpiarCargoRecurrente();
    						self.cargo.cargarCargoRecurrente(data, self.departamentos(), self.catalogoMes());
    						notificacionExito("El cargo se ha guardado correctamente");
    					},
    					error : function(jqXHR, textStatus, errorThrown) {
    						notificacionError("Ocurrio un error al guardar el cargo");
    					}
    				});
    			} else {
    				notificacionAdvertencia("El formulario tiene errores");
    			}
				} else {
					notificacionAdvertencia("Debes seleccionar al menos un mes.");
				}
			} else {
				notificacionAdvertencia("Debes seleccionar al menos un departamento.");
			}
		} /*else {
		 notificacionAdvertencia("El dia de descuento tiene que ser mayor al día de cargo y el día de recargo debe ser mayor al día de descuento");
		 }*/
    }
    
    self.actualizar = function() {
    	if(self.validaFecha()){
    		if (self.validaDepartamentos()) {
				if(self.validaMeses()) {
					if ($("#cargo-form").valid()) {
						var cargo = JSON.stringify(self.cargo.getJsonCargoRecurrente(self.departamentos(), self.catalogoMes()));
						console.log(cargo);
						$.ajax({
							url: contextPath + "/administrador/cargos-recurrentes/actualizar",
							type: 'POST',
							dataType: 'json',
							data: cargo,
							contentType: 'application/json',
							mimeType: 'application/json',
							success: function (data) {
								self.cargo.limpiarCargoRecurrente();
								self.cargo.cargarCargoRecurrente(data, self.departamentos(), self.catalogoMes());
								notificacionExito("El cargo se ha actualizado correctamente");
							},
							error: function (jqXHR, textStatus, errorThrown) {
								notificacionError("Ocurrio un error al actualizar el cargo");
							}
						});
					} else {
						notificacionAdvertencia("El formulario tiene errores");
					}
				} else {
					notificacionAdvertencia("Debes seleccionar al menos un mes.");
				}
    		} else {
    			notificacionAdvertencia("Debes seleccionar al menos un departamento.");
        	} 
        } /*else {
    		notificacionAdvertencia("El dia de descuento tiene que ser mayor al día de cargo y el día de recargo debe ser mayor al día de descuento");
		}*/
    }
    
    self.validaDepartamentos = function() {
    	var valid = false;
		if(self.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.mantenimiento){
			if(!self.cargo.todos()){
				ko.utils.arrayForEach(self.departamentos(), function(d) {
					if (d.seleccionado()) {
						valid = true;
					}
				});
				return valid;
			}else{
				return true;
			}
		} else {
			return true;
		}
    }
    
    self.validaMeses = function() {
        var valid = false;
        ko.utils.arrayForEach(self.catalogoMes(), function(m) {
            if (m.seleccionado()) {
                valid = true;
            }
        });
        return valid;
    }
    
    self.validaFecha = function(){
    	var diaCargo;
		if(!self.cargo.seleccionarDiaMes() && !self.cargo.primerDiaMes() && !self.cargo.ultimoDiaMes()){
			notificacionAdvertencia("Debe elegir un día de aplicación de cargo.");
			return false;
		}
    	if(self.cargo.seleccionarDiaMes()){
    		diaCargo = self.cargo.dia();
    	}else if(self.cargo.primerDiaMes()){
    		diaCargo = 1;
    	}else if(self.cargo.ultimoDiaMes()){
    		diaCargo = 28;
    	}
    	
    	var diaDescuento = self.cargo.descuento.dia();
    	var diaRecargo = self.cargo.recargo.dia();
    	diaCargo = parseInt(diaCargo);
    	diaDescuento = parseInt(diaDescuento);
    	diaRecargo = parseInt(diaRecargo);
    	
    	if (!self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
			return true;
		} else if (self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
			return true;
			/*if (diaDescuento >= diaCargo) {
				return true;
			} else {
				return false;
			}*/
		} else if (!self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
			return true;
			/*if (diaRecargo >= diaCargo) {
				return true;
			} else {
				return false;
			}*/
		} else if (self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
			if ((diaRecargo >= diaDescuento)) {
				return true;
			} else {
				notificacionAdvertencia("El dia de recargo debe ser mayor o igual al día de descuento");
				return false;
			}
		}
    }
    
    self.clickTabla = function() {
        setTimeout(function() {
            $("#th-departamento").click();
        }, 250);
        
    }
}

var ListaCargoRecurrenteViewModel = function(data) {
    self = this;
    
    self.cargos = ko.observableArray([]);
    
    var meses = AppConfig.catalogos.meses.descripcion.split(",");
    var descripcionMeses = [];
    if (data.cargos != undefined && data.cargos.length > 0) {
        ko.utils.arrayForEach(data.cargos, function(c) {
        	ko.utils.arrayForEach(c.meses, function(m) {
        		var mes = meses[ m ];
        		descripcionMeses.push(mes);
            });
        	c.meses = descripcionMeses;
            self.cargos.push(c);
            descripcionMeses = [];
        });
    }
    
    self.actualizar = function(data) {
        location.href = contextPath + "/administrador/cargos-recurrentes/actualizar/" + data.id;
    }
}