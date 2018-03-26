var ContactoViewModel = function(data) {
	self = this;
	
	self.usuario = new Usuario();
	self.contacto = new Contacto();
	
	self.condominio = new Condominio(data.condominio);
	
	if (data && data.contacto) {
		self.contacto.cargar(data.contacto);
	} else {
		self.contacto.activo(true);
	}
	
	if (data && data.condominio) {
		self.contacto.condominio.cargar(data.condominio);
		ko.utils.arrayForEach(self.contacto.departamentos(), function(d) {
			d.id.departamento.condominio.cargar(data.condominio);
		});
	}
	
	self.catalogoEmail = ko.observableArray([]);
	if (data && data.catalogoEmail) {
		self.catalogoEmail(data.catalogoEmail);
//		$.each(data.catalogoEmail, function(i, ce) {
//			var catalogo = new Catalogo();
//			catalogo.cargar(ce);
//			self.catalogoEmail.push(catalogo);
//		});
	}
	
	self.catalogoTelefono = ko.observableArray([]);
	if (data && data.catalogoTelefono) {
		self.catalogoTelefono(data.catalogoTelefono);
//		$.each(data.catalogoTelefono, function(i, ct) {
//			var catalogo = new Catalogo();
//			catalogo.cargar(ct);
//			self.catalogoTelefono.push(catalogo);
//		});
	}
	
	self.mantenimientos = ko.observableArray([]);
	if (data && data.mantenimientos) {
		self.mantenimientos(data.mantenimientos);
//		$.each(data.mantenimientos, function(i, m) {
//			var mantenimiento = new MantenimientoCondominio();
//			mantenimiento.cargar(m);
//			self.mantenimientos.push(mantenimiento);
//		});
	}
	
	self.grupos = ko.observableArray([]);
	if (data && data.grupos) {
		self.grupos(data.grupos);
//		$.each(data.grupos, function(i, g) {
//			var grupo = new GrupoCondominio();
//			grupo.cargar(g);
//			self.grupos.push(grupo);
//		});
	}
	
	self.guardar = function() {
		var validForm = $("#contacto-form").valid();
		var validarDeptos = self.validarDepartamentos();
		if (validForm && validarDeptos) {
			var contacto = JSON.stringify(self.contacto.getJson());
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
					self.contacto.limpiar();
					self.contacto.cargar(data);
					notificacionExito("El contacto se ha guardado correctamente");
					$("li.departamento > a").first().click();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el contacto");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}

	self.actualizar = function() {
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
	}
	
	self.validarDepartamentos = function() {
		var valido = true;
		for (var i = 0; i < self.contacto.departamentos().length; i++) {
			try {
				$("#a-departamento-" + i).click();
				if (!$("#form-departamento-" + i).valid()) {
					valido = false;
					return false;
				}
			} catch (err) {
			}
		}
		return valido;
	}
}

var ListaContactoViewModel = function(data) {
	self = this;
	
	self.contactos = ko.observableArray([]);
	
	if (data.contactos != undefined && data.contactos.length > 0) {
//		ko.utils.arrayForEach(data.contactos, function(c) {
			self.contactos(data.contactos);
//		});
	}
	
	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/contactos/actualizar/" + data.id;
	};
	self.actualizarTemp = function(data) {
		location.href = contextPath + "/administrador/departamentos/actualizar/" + data.id.departamento.id;
	};
}