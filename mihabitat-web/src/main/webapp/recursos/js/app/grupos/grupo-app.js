var GrupoCondominioViewModel = function(data) {
	var self = this;
	
	self.grupo = new GrupoCondominio();
	self.operacionCorrecta = ko.observable(false);
	if (data && data.grupo) {
		self.grupo.cargar(data.grupo);
	}

	self.editar = function(data) {
		if (data && data.grupo) {
			self.grupo.cargar(data.grupo);
		}
	}

	self.limpiar = function() {
		self.grupo.limpiar();
	}
	
	self.guardar = function() {
		if ($("#grupo-form").valid()) {
			var grupo = JSON.stringify(self.grupo.getJson());
			console.log(grupo);
			$.ajax({
				async : false,
				cache : false,
				url : contextPath + "/administrador/grupos/guardar",
				type : 'POST',
				dataType : 'json',
				data : grupo,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.grupo.limpiar();
					self.grupo.cargar(data);
					$("#myModalGrupo").modal("hide");
					notificacionExito("El grupo se ha guardado correctamente");
					self.operacionCorrecta(true);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el grupo");
					self.operacionCorrecta(false);
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
			self.operacionCorrecta(false);
		}
	}
	
	self.actualizar = function() {
		if ($("#grupo-form").valid()) {
			var grupo = JSON.stringify(self.grupo.getJson());
			console.log(grupo);
			$.ajax({
				async : false,
				cache : false,
				url : contextPath + "/administrador/grupos/actualizar",
				type : 'POST',
				dataType : 'json',
				data : grupo,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.grupo.limpiar();
					self.grupo.cargar(data);
					$("#myModalGrupo").modal("hide");
					notificacionExito("El grupo se ha actualizado correctamente");
					self.operacionCorrecta(true);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el grupo");
					self.operacionCorrecta(false);
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
			self.operacionCorrecta(false);
		}
	}
}

var ListaGrupoCondominioViewModel = function(data) {
	self = this;

	self.grupoCondominioViewModel = new GrupoCondominioViewModel();
	
	self.grupos = ko.observableArray([]);

	if (data.grupos != undefined && data.grupos.length > 0) {
		ko.utils.arrayForEach(data.grupos, function(g) {
			var gru = new GrupoCondominio();
			gru.cargar(g);
			self.grupos.push(gru);
		});
	}

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
			/*self.grupos.push(self.grupoCondominioViewModel.grupo);*/
			var gru = new GrupoCondominio();
			gru.cargar(self.grupoCondominioViewModel.grupo.getJson());
			self.grupos.push(gru);
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