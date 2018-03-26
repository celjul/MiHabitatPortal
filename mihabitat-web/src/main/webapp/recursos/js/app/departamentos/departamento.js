var Departamento = function(data) {
	var nombreDeptoAnterior;
	var self = this;

	self.activo = ko.observable(data ? data.activo : true);
	self.condominio = new Condominio(data ? data.condominio : undefined);
	self.contactos = ko.observableArray(data ? data.contactos : []);
	self.grupos = ko.observableArray(data ? data.grupos : []);
	self.id = ko.observable(data ? data.id : undefined);
	self.mantenimiento = new MantenimientoCondominio(data ? data.mantenimiento : undefined);
	self.nombre = ko.observable(data ? data.nombre : undefined);
	self.unidadIndiviso = ko.observable(data ? data.unidadIndiviso : undefined);
	self.nombre.subscribeChanged(function(oldVal, newVal) {
		nombreDeptoAnterior = newVal; 
	});
	self.observaciones = ko.observable(data ? data.observaciones : undefined);
	self.seleccionado = ko.observable(data ? data.seleccionado : undefined);
	
	self.nombreCompleto = ko.computed(function() {
		var nombreCompleto = String.returnValue(self.nombre());
		if (nombreCompleto == "") {
			nombreCompleto = "Sin Nombre";
		}
		return nombreCompleto;
	}, self);
	
	
	self.existe = function() {
		
		if (String.returnValue(self.nombre()).length > 0) {
			$.ajax({
				url : contextPath + '/administrador/departamentos/existe',
				type : 'POST',
				dataType : 'json',
				data : {
					condominio : self.condominio.id(),
					nombre : self.nombre()
				},
				success : function(data) {
					if (data) {
						bootbox.confirm("El departamento ya existe ¿Deseas cargar los datos?", function(result) {
							if (result){
								self.limpiar();
								self.cargar(data);
								$("li.contacto > a").last().click();
								for (var i = 0; i < $("li.contacto > a").length; i++) {
									$("#form-contacto-" + i).validate();
								}
								$("li.departamento > a").last().click();
								for (var i = 0; i < $("li.departamento > a").length; i++) {
									$("#form-departamento-" + i).validate();
								}
								$('[id="mantenimiento"]').select2();
								$('[id="grupos"]').select2();
							} else if (self.id()) {
								if (nombreDeptoAnterior) {
									self.nombre(nombreDeptoAnterior);
								}
							} else {
								self.nombre("");
							}
						});
					}
				}
			});
		}
	}
	
	self.agregarContacto = function(data) {
		if(data) {
			var contactoDepartamento = new ContactoDepartamento();
			contactoDepartamento.cargar(data);
			self.contactos.push(contactoDepartamento);
		} else {
			self.contactos.push(new ContactoDepartamento({
				habitante : false,
				principal: false,
				propietario: false,
				id : {
					contacto : {
						activo : true,
						condominio : {
							id : self.condominio.id()
						}
					}
				}
			}));
		}
		/*if (self.contactos().length < 5) {
			self.contactos.push(new ContactoDepartamento({
				habitante : false,
				principal: false,
				propietario: false,
				id : {
					contacto : {
						activo : true,
						condominio : {
							id : self.condominio.id()
						}
					}
				}
			}));
		} else {
			notificacionAdvertencia("Se ha alcanzado el número máximo de contactos");
		}*/
	}
	
	self.removerContacto = function(data) {
		bootbox.confirm("¿En verdad deseas remover el contacto?", function(result) {
			if (result){
				self.contactos.remove(function(item) {
					return item.id.contacto.id() == data.id.contacto.id();
				});
			}
		}); 
	}

	self.sustituirContacto = function(id,data) {
		var encontrado = ko.utils.arrayFirst(self.contactos(), function(item) {
			return item.id.contacto.id() == id;
		});
		encontrado.limpiar();
		encontrado.cargar(data);
	}
	
	self.agregarEmail = function() {
		self.emails.push(new Email());
	}
	
	self.removerEmail = function(data) {
		bootbox.confirm("¿Deseas remover el email?", function(result) {
			if (result){
				self.emails.remove(data);
			}
		}); 
	}
	
	self.agregarTelefono = function() {
		self.telefonos.push(new Telefono());
	}
	
	self.removerTelefono = function(data) {
		bootbox.confirm("¿Deseas remover el telefono?", function(result) {
			if (result){
				self.telefonos.remove(data);
			}
		}); 
	}

	self.cargar = function(data) {
		self.activo(data ? data.activo : true);
		self.condominio.cargar(data ? data.condominio : undefined);

		if (data && data.contactos && data.contactos.length > 0) {
			ko.utils.arrayForEach(data.contactos, function(c) {
				var cd = new ContactoDepartamento();
				cd.cargar(c);
				self.contactos.push(cd);
			});
		}
		if (data && data.grupos && data.grupos.length > 0) {
			ko.utils.arrayForEach(data.grupos, function(g) {
//				var grupo = new GrupoCondominio();
//				grupo.cargar(g);
				self.grupos.push(g);
			});
		}

		self.id(data ? data.id : undefined);
		self.mantenimiento.cargar(data ? data.mantenimiento : undefined);
		self.nombre(data ? data.nombre : undefined);
		self.unidadIndiviso(data ? data.unidadIndiviso : undefined);
		self.observaciones(data ? data.observaciones : undefined);
	}

	self.limpiar = function() {
		self.activo(true);
		self.condominio.limpiar();
		self.contactos([]);
		self.grupos([]);
		self.id(undefined);
		self.mantenimiento.limpiar();
		self.nombre(undefined);
		self.unidadIndiviso(undefined);
		self.observaciones(undefined);
	}

	self.getJson = function() {
		var departamento = self.estructurar(ko.toJS(self));
		departamento = validarObject(departamento);
		return departamento;
	}

	self.estructurar = function(data) {
		if (data.seleccionado) {
			delete data.seleccionado;
		}
		if (data.nombreCompleto) {
			delete data.nombreCompleto;
		}
		if (data.condominio) {
			data.condominio = self.condominio.getJson();
		}
		if (data.contactos && data.contactos.length > 0) {
			var contactos = [];
			ko.utils.arrayForEach(self.contactos(), function(c) {
				var cd = c.getJson();
				
				//delete cd.id.contacto.usuario;
				contactos.push(cd);
			});
			data.contactos = contactos;
		}
		if (data.grupos && data.grupos.length > 0) {
			var grupos = [];
			ko.utils.arrayForEach(self.grupos(), function(g) {
				try {
					var grupo = g.getJson();
				} catch(err) {
					var grupo = {
							id: g
					}
				}
				grupos.push(grupo);
			});
			data.grupos = grupos;
		}
		if (data.mantenimiento) {
			data.mantenimiento = self.mantenimiento.getJson();
		}
		return data;
	}
}