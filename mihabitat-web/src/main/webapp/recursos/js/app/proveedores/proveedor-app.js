var ProveedorViewModel = function(data) {
    
    var self = this;
    
    self.proveedor = new Proveedor();
    
    self.direccion = new Direccion();
    self.paises = ko.observableArray([]);
	self.lengthTipoPersona = ko.observable(undefined);

	//data.impuestoTrasladado = parseFloat(self.impuestoTrasladado());

	self.addDireccion = ko.observable(false);
    
    if (data && data.proveedor) {
		self.proveedor.cargar(data.proveedor);

		if (data.proveedor.direccion) {
			self.addDireccion(true);
		}
	} else {
		self.proveedor.activo(true);
		self.proveedor.tipoPersona(true);
		self.lengthTipoPersona(13);
		self.proveedor.esEmpleado(false);
	}
    
    if (data && data.condominio) {
		self.proveedor.condominio.cargar(data.condominio);
		
	}
    
    /**if(!self.proveedor.id()){
		self.proveedor.agregarContacto();
	}**/
    
    self.cuenta = ko.observableArray([]);
    if (data && data.cuenta) {
		self.cuenta(ordenar(data.cuenta,true));
    }
    
    self.giross = ko.observableArray([]);
    if (data && data.giros) {
        ko.utils.arrayForEach(data.giros, function(g) {
            var giro = new Catalogo();
            giro.cargar(g);
            self.giross.push(giro);
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
	
	if (data.paises != undefined && data.paises.length > 0) {
        ko.utils.arrayForEach(data.paises, function(p) {
            var pais = new Pais();
            pais.cargar(p);
            self.paises.push(pais);
        });
	}
    
	
	self.guardar = function() {

		var validateForm = $("#proveedor-form").valid();
		var validateFormContactos = self.validarContactos();

		if (validateForm && validateFormContactos) {
			var proveedor = JSON.stringify(self.proveedor.getJson());
			console.log(proveedor);
			$.ajax({
				url : contextPath + "/administrador/proveedores/guardar" ,
				type : 'POST',
				dataType : 'json',
				data : proveedor,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.proveedor.limpiar();
					self.proveedor.cargar(data);
					notificacionExito("El proveedor se ha guardado correctamente");
					$("li.contacto > a").first().click()
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el proveedor");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}


	self.actualizar = function() {

		if ($("#proveedor-form").valid() && self.validarContactos()) {
			var proveedor = JSON.stringify(self.proveedor.getJson());
			console.log(proveedor);
			$.ajax({
				async : true,
				cache : false,
				url : contextPath + "/administrador/proveedores/actualizar?" ,
				type : 'POST',
				dataType : 'json',
				data : proveedor,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.proveedor.limpiar();
					self.proveedor.cargar(data);
					notificacionExito("El proveedor se ha actualizado correctamente");
					$("li.contacto > a").first().click()
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al actualizar el proveedor");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}
	
	
	self.validarContactos = function() {
		var valido = true;
		for (var i = 0; i < self.proveedor.contactos().length; i++) {
			$("#a-contacto-" + i).click();
			if (!$("#form-contacto-" + i).valid()) {
			valido = false;
			return false;
			}
		}
		return valido;
	}

	self.agregarDomicilio = function() {

		if (self.addDireccion()) {
			self.addDireccion(false);
		} else {
			self.addDireccion(true);
		}
	}


	self.proveedor.tipoPersona.subscribe(function (data) {
		console.log(data);
		if (data) {
			//persona fisica
			self.lengthTipoPersona(13);
		} else {
			//persona moral
			self.lengthTipoPersona(12);

			if (self.proveedor.rfc() != null) {
				if (self.proveedor.rfc().length > 12) {
					self.proveedor.rfc(self.proveedor.rfc().substr(0,12));
				}
			}
		}
	 });

        
}

var ListaProveedorViewModel = function(data) {
	self = this;

	self.proveedores = ko.observableArray([]);

	if (data.proveedores != undefined && data.proveedores.length > 0) {
		ko.utils.arrayForEach(data.proveedores, function(p) {
			self.proveedores.push(p);

			console.log(p);
		});
	}

	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/proveedores/actualizar/" + data.id;
	};
}