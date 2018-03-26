var DepartamentoViewModel = function(data) {
	self = this;
	
	self.departamento = new Departamento();
	self.contactoTemporal = new ContactoDepartamento();
	self.idContactoTemporal = 0;
	self.sustituir = ko.observable(undefined);

	if (data && data.departamento) {
		self.departamento.cargar(data.departamento);
	} else {
		self.departamento.activo(true);
	}
	
	if (data && data.condominio) {
		self.departamento.condominio.cargar(data.condominio);
		ko.utils.arrayForEach(self.departamento.contactos(), function(c) {
			c.id.contacto.condominio.cargar(data.condominio);
		});
	}
	
	/*if(!self.departamento.id()){
		self.departamento.agregarContacto();
	}*/
	
	self.mantenimientos = ko.observableArray([]);
	if (data && data.mantenimientos) {
		$.each(data.mantenimientos, function(i, m) {
			var mantenimiento = new MantenimientoCondominio();
			mantenimiento.cargar(m);
			self.mantenimientos.push(mantenimiento);
		});
	}

	self.tiposCobro = ko.observableArray([]);
	if (data.tiposCobro != undefined && data.tiposCobro.length > 0) {
		ko.utils.arrayForEach(data.tiposCobro, function (m) {
			var mant = new Catalogo();
			mant.cargar(m);
			self.tiposCobro.push(mant);
		});
	}

	self.contactos = ko.observableArray([]);
	if (data && data.contactos) {
		$.each(data.contactos, function(i, c) {
			var contacto = new Contacto();
			contacto.cargar(c);
			self.contactos.push(contacto);
		});
	}
	
	self.grupos = ko.observableArray([]);
	if (data && data.grupos) {
		$.each(data.grupos, function(i, g) {
			var grupo = new GrupoCondominio();
			grupo.cargar(g);
			self.grupos.push(grupo);
		});
	}
	
	self.catalogoEmail = ko.observableArray([]);
	if (data && data.catalogoEmail) {
		$.each(data.catalogoEmail, function(i, ce) {
			var catalogo = new Catalogo();
			catalogo.cargar(ce);
			self.catalogoEmail.push(catalogo);
		});
	}
	
	self.catalogoTelefono = ko.observableArray([]);
	if (data && data.catalogoTelefono) {
		$.each(data.catalogoTelefono, function(i, ct) {
			var catalogo = new Catalogo();
			catalogo.cargar(ct);
			self.catalogoTelefono.push(catalogo);
		});
	}

	self.checarMantenimientoIndiviso = function () {
		var encontrado = ko.utils.arrayFirst(self.mantenimientos(), function (item) {
			return item.id() == self.departamento.mantenimiento.id();
		});
		return encontrado ? encontrado.tipoCobroDepartamento.id() == AppConfig.catalogos.cargo.tiposCobroMantenimiento.indiviso : false;
	}

	self.limpiar = function() {
		var idCondominio = self.departamento.condominio.id();
		self.departamento.limpiar();
		self.departamento.condominio.id(idCondominio)
		$("#departamento-form").validate();
		$("#mantenimiento").select2();
		$("#grupos").select2();
	}

	self.seleccionado = function() {
		if(self.contactoTemporal.id.contacto.id() && self.contactoTemporal.id.contacto.id() > 0){
			var encontrado = ko.utils.arrayFirst(self.contactos(), function(item) {
				return item.id() == self.contactoTemporal.id.contacto.id();
			});
			if(encontrado) {
				self.contactoTemporal.id.contacto.limpiar();
				self.contactoTemporal.id.contacto.cargar(encontrado.getJson());
			} else {
				self.contactoTemporal.id.contacto.limpiar();
			}
		}
		else  {
			self.contactoTemporal.id.contacto.limpiar();
		}
		//$("#contactoSeleccionado").select2();
	}

	self.nuevoContacto  = function() {
		if(self.departamento.contactos().length < 5) {
			self.contactoTemporal.limpiar();
			//self.contactoTemporal.id.contacto.cargar({id: self.idContactoTemporal});
			self.sustituir(undefined);
			self.contactoTemporal.id.contacto.condominio.id(self.departamento.condominio.id());
			$("#contactoSeleccionado").select2();
			$("#myModalContacto").modal("show");
		} else {
			notificacionAdvertencia("Solo se pueden tener un m�ximo de 5 contactos por departamento");
		}
	}

	self.editarContacto  = function(data) {
		self.contactoTemporal.limpiar();
		self.contactoTemporal.cargar(data.getJson());
		self.sustituir(self.contactoTemporal.id.contacto.id());
		$("#contactoSeleccionado").select2();
		$("#myModalContacto").modal("show");
	}

	self.agregarContacto  = function() {
		if ($("#form-contacto").valid()) {
			if(self.sustituir()) {
				self.departamento.sustituirContacto(self.sustituir(),self.contactoTemporal.getJson());
				var encontrado = ko.utils.arrayFirst(self.contactos(), function(item) {
					return item.id() == self.contactoTemporal.id.contacto.id();
				});
				encontrado.cargar(self.contactoTemporal.id.contacto.getJson());
			} else {
				if(!self.contactoTemporal.id.contacto.id()){
					self.idContactoTemporal--;
					var c = new Contacto();
					c.cargar(self.contactoTemporal.id.contacto.getJson());
					c.id(self.idContactoTemporal);
					self.contactos.push(c);
					$("#contactoSeleccionado").select2();
					self.contactoTemporal.id.contacto.id(self.idContactoTemporal);
				}
				self.departamento.agregarContacto(self.contactoTemporal.getJson());

			}
			$("#contactoSeleccionado").select2();
			$("#myModalContacto").modal("hide");
		}
	}

	self.eliminarContacto  = function(data) {
		self.departamento.removerContacto(data);
	}
	
	self.guardar = function() {
		if (self.departamento.contactos().length > 0) {
			var validateFormDepto = $("#departamento-form").valid();
			//var validateFormContactos = self.validarContactos();
			
			//if (validateFormDepto && validateFormContactos) {
			if (validateFormDepto) {
				var notificaciones;
				ko.utils.arrayForEach(self.departamento.contactos(), function(c) {
					if(c.id.contacto.id() < 0) {
						c.id.contacto.id(undefined);
					}
					if(c.id.contacto.usuario && c.id.contacto.usuario.email() && !c.id.contacto.usuario.id()) {
						notificaciones = notificaciones?notificaciones + 'notificaciones=' + c.id.contacto.usuario.email() + '&':'notificaciones=' + c.id.contacto.usuario.email() + '&';
						c.id.contacto.usuario.limpiar();
					}
				});
				var departamento = JSON.stringify(self.departamento.getJson());
				console.log(departamento);
				console.log(notificaciones);
				$.ajax({
					url : contextPath + "/administrador/departamentos/guardar?" +
						notificaciones,
					type : 'POST',
					dataType : 'json',
					data : departamento,
					contentType : 'application/json',
					mimeType : 'application/json',
					success : function(data) {
						self.departamento.limpiar();
						self.departamento.cargar(data);
						notificacionExito("El departamento se ha guardado correctamente");
						$("li.contacto > a").first().click()
						setTimeout(function() {
							location.href = contextPath + "/administrador/departamentos/lista";
						}, 1000);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						notificacionError("Ocurrio un error al guardar el departamento");
					}
				});
			} else {
				notificacionAdvertencia("El formulario tiene errores");
			}
		} else {
			notificacionAdvertencia("Se requiere al menos un contacto");
		}
	}

	self.actualizar = function() {
		if (self.departamento.contactos().length > 0) {
			var validateFormDepto = $("#departamento-form").valid();
			//var validateFormContactos = self.validarContactos();

			//if (validateFormDepto && validateFormContactos) {
			if (validateFormDepto) {
				var notificaciones;
				ko.utils.arrayForEach(self.departamento.contactos(), function(c) {
					if(c.id.contacto.id() < 0) {
						c.id.contacto.id(undefined);
					}
					if(c.id.contacto.usuario && c.id.contacto.usuario.email() && !c.id.contacto.usuario.id()) {
						notificaciones = notificaciones?notificaciones + 'notificaciones=' + c.id.contacto.usuario.email() + '&':'notificaciones=' + c.id.contacto.usuario.email() + '&';
						c.id.contacto.usuario.limpiar();
					}
				});
				var departamento = JSON.stringify(self.departamento.getJson());
				console.log(departamento);
				$.ajax({
					async : true,
					cache : false,
					url : contextPath + "/administrador/departamentos/actualizar?" +
						notificaciones,
					type : 'POST',
					dataType : 'json',
					data : departamento,
					contentType : 'application/json',
					mimeType : 'application/json',
					success : function(data) {
						self.departamento.limpiar();
						self.departamento.cargar(data);
						notificacionExito("El departamento se ha actualizado correctamente");
						$("li.contacto > a").first().click()
					},
					error : function(jqXHR, textStatus, errorThrown) {
						notificacionError("Ocurrio un error al actualizar el departamento");
					}
				});
			} else {
				notificacionAdvertencia("El formulario tiene errores");
			}
		} else {
			notificacionAdvertencia("Se requiere al menos un contacto");
		}
	}

	/*self.guardarContacto = function() {
		var validForm = $("#contacto-form").valid();
		if (validForm) {
			var contacto = JSON.stringify(self.contactoTemporal.id.contacto.getJson());
			console.log(contacto);
			$.ajax({
				cache : false,
				url : contextPath + "/administrador/contactos/guardar?" +
				decodeURIComponent($.param($("[name=notificaciones]").serializeArray())),
				type : 'POST',
				dataType : 'json',
				data : contacto,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.contactoTemporal.id.contacto.limpiar();
					self.contactoTemporal.id.contacto.cargar(data);
					self.agregarContacto(self.contactoTemporal.getJson());
					notificacionExito("El contacto se ha guardado correctamente");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el contacto");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}

	self.actualizarContacto = function() {
		var validForm = $("#contacto-form").valid();
		var validarDeptos = self.validarDepartamentos();

		if (validForm && validarDeptos) {
			var contacto = JSON.stringify(self.contacto.getJson());

			console.log(contacto);
			$.ajax({
				cache : false,
				url : contextPath + "/administrador/contactos/actualizar",
				type : 'POST',
				dataType : 'json',
				data : contacto,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.contacto.limpiar();
					self.contacto.cargar(data);
					notificacionExito("El contacto se ha actualizado correctamente");
					$("li.departamento > a").first().click();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al actualizar el contacto");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}*/
	
	self.validarContactos = function() {
		var valido = true;
		for (var i = 0; i < self.departamento.contactos().length; i++) {
			$("#a-contacto-" + i).click();
			if (!$("#form-contacto-" + i).valid()) {
			valido = false;
			return false;
			}
		}
		return valido;
	}

	//C�digo para el modal de agregar Mantenimientos
	self.mantenimientoCondominioViewModel = new MantenimientoCondominioViewModel();

	self.nuevoMantenimiento = function(data) {
		self.mantenimientoCondominioViewModel.limpiar();
	}

	self.editarMantenimiento = function(data) {
		console.log("hola");
		$.ajax({
			async : true,
			cache : false,
			url : contextPath + "/administrador/mantenimientos/actualizar/" + (typeof data.id === "function" ? data.id() : data.id),
			type : 'GET',
			dataType : 'json',
			data : '',
			success : function(data) {
				/*self.mantenimientoCondominioViewModel = new MantenimientoCondominioViewModel({
				 mantenimiento : data
				 });*/
				self.mantenimientoCondominioViewModel.editar({
					mantenimiento : data
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				notificacionError("Ocurrio un error al guardar el mantenimiento");
			}
		});
	}

	self.guardarMantenimiento = function(data) {
		self.mantenimientoCondominioViewModel.guardar();
		if(self.mantenimientoCondominioViewModel.operacionCorrecta()) {
			self.mantenimientos.push(self.mantenimientoCondominioViewModel.mantenimiento);
		}
	}

	self.actualizarMantenimiento = function(data) {
		self.mantenimientoCondominioViewModel.actualizar();
		if(self.mantenimientoCondominioViewModel.operacionCorrecta()) {
			var mantenimiento = self.mantenimientoCondominioViewModel.mantenimiento;
			var encontrado = ko.utils.arrayFirst(self.mantenimientos(), function(item) {
				return item.id() == mantenimiento.id();
			});
			encontrado.cargar(mantenimiento.getJson());
		}
	}

	//C�digo para el modal de agregar Grupos
	self.grupoCondominioViewModel = new GrupoCondominioViewModel();

	self.nuevoGrupo = function(data) {
		self.grupoCondominioViewModel.limpiar();
	}

	self.editarGrupo = function(data) {
		$.ajax({
			async : true,
			cache : false,
			url : contextPath + "/administrador/grupos/actualizar/" + (typeof data.id === "function" ? data.id() : data.id),
			type : 'GET',
			dataType : 'json',
			data : '',
			success : function(data) {
				self.grupoCondominioViewModel.editar({
					grupo : data
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				notificacionError("Ocurrio un error al guardar el mantenimiento");
			}
		});
	}

	self.guardarGrupo = function(data) {
		self.grupoCondominioViewModel.guardar();
		if(self.grupoCondominioViewModel.operacionCorrecta()) {
			self.grupos.push(self.grupoCondominioViewModel.grupo);
		}
	}

	self.actualizarGrupo = function(data) {
		self.grupoCondominioViewModel.actualizar();
		if(self.grupoCondominioViewModel.operacionCorrecta()) {
			var grupo = self.grupoCondominioViewModel.grupo;
			var encontrado = ko.utils.arrayFirst(self.grupos(), function(item) {
				return item.id() == grupo.id();
			});
			encontrado.cargar(grupo.getJson());
		}
	}


}

var ListaDepartamentoViewModel = function(data) {
	self = this;
	
	self.departamentos = ko.observableArray([]);
	
	if (data.departamentos != undefined && data.departamentos.length > 0) {
		ko.utils.arrayForEach(data.departamentos, function(d) {
			self.departamentos.push(d);
		});
	}
	
	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/departamentos/actualizar/" + data.id;
	};

	self.imprimir = function(formato) {
		var url = contextPath + "/administrador/departamentos/imprimir?formato=" + formato;
		window.open(contextPath + url, '_blank');
	}
}