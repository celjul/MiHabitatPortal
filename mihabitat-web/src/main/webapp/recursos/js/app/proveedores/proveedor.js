var Proveedor = function(data) {

    var self = this;
    self.activo = ko.observable(data ? data.activo : undefined);
    self.esEmpleado = ko.observable(data ? data.esEmpleado : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.contactos = ko.observableArray(data ? data.contactos : []);
    self.cuenta = new Cuenta();
    self.id = ko.observable();
    self.giros = ko.observableArray(data ? data.giros: []);
    self.nombre = ko.observable(data ? data.nombre : undefined);
    
    self.rfc = ko.observable(data ? data.rfc : undefined);
    self.razonSocial = ko.observable(data ? data.razonSocial : undefined);
    self.diasCredito = ko.observable(data ? data.diasCredito : undefined);
    
    self.direccion = new Direccion(data ? data.direccion :undefined);
    self.tipoPersona = ko.observable(data ? data.tipoPersona : undefined);

    self.esFacturable = ko.observable(data ? data.esFacturable : false);
    
    self.limpiar = function() {
    	self.activo(undefined);
    	self.esEmpleado(undefined);
        self.condominio.limpiar();
        self.id(undefined);
        self.nombre(undefined);

        self.rfc(undefined);
        self.razonSocial(undefined);
        self.diasCredito(undefined);
        
        self.contactos([]);
        self.direccion.limpiar();
        self.tipoPersona(undefined);

        self.esFacturable(false);
    }
    

    self.cargar = function(data) {
    	console.log("cargar proveedores");

    	self.activo(data ? data.activo : undefined);
    	self.esEmpleado(data ? data.esEmpleado: undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        
        self.direccion.cargar(data ? data.direccion : undefined);

        if (data && data.contactos && data.contactos.length > 0) {
			ko.utils.arrayForEach(data.contactos, function(c) {
				var cd = new ContactoProveedor();
				cd.cargarCP(c);
				self.contactos.push(cd);
			});
		}
        self.cuenta.cargar(data ? data.cuenta : undefined);

        self.id(data ? data.id : undefined);
               
        if (data && data.giros && data.giros.length > 0) {
			ko.utils.arrayForEach(data.giros, function(g) {
//				var grupo = new GrupoCondominio();
//				grupo.cargar(g);
				self.giros.push(g.id);
			});
		}
        
        self.nombre(data ? data.nombre : undefined);
        self.rfc(data ? data.rfc: undefined);
        self.razonSocial(data ? data.razonSocial: undefined);
        self.diasCredito(data ? data.diasCredito : undefined);
        self.tipoPersona(data ? data.tipoPersona : undefined);

        self.esFacturable(data ? data.esFacturable : false);
    }
    
    
    self.getJson = function() {
        var proveedor = self.estructurar(ko.toJS(self));
        proveedor = validarObject(proveedor);
        return proveedor;
    }
    
    
    self.estructurar = function(data) {
        if (data && data.condominio) {
            //data.condominio = self.condominio.getJson();
            data.condominio = {
                id : data.condominio.id
            }
        }

        if (data && data.cuenta) {
            //data.cuenta = self.cuenta.getJson();
            data.cuenta = {
                id : data.cuenta.id
            }
        }
                
        if (data.giros && data.giros.length > 0) {
			var giros = [];
			ko.utils.arrayForEach(self.giros(), function(g) {
				try {
					var giro = g.getJson();
				} catch(err) {
					var giro = {
							id: g
					}
				}
				giros.push(giro);
			});
			data.giros = giros;
		}
        
        if(data.direccion){
    		data.direccion = self.direccion.getJson();
    	}
        
        if (data.contactos && data.contactos.length > 0) {
			var contactos = [];
			ko.utils.arrayForEach(self.contactos(), function(c) {
				var cd = c.getJsonCP();
				contactos.push(cd);
			});
			data.contactos = contactos;
		}
        data.diasCredito = parseInt(self.diasCredito());

        if (data.tipoPersona == "Moral") {
            data.tipoPersona = 0;
        } else {
            data.tipoPersona = 1;
        }
        return data;
    }
    
    
    self.agregarContacto = function() {
		if (self.contactos().length < 5) {
			self.contactos.push(new ContactoProveedor());
		} else {
			notificacionAdvertencia("Se ha alcanzado el número máximo de contactos");
		}
	}
    
    
    self.removerContacto = function(data) {
		bootbox.confirm("¿Deseas remover el contacto?", function(result) {
			if (result){
				self.contactos.remove(data);
				$("li.contacto > a").first().click();
			}
		}); 
	}


    self.existe = function() {
        if (String.returnValue(self.rfc()).length > 0) {
            $.ajax({
                url : contextPath + '/administrador/proveedores/existe',
                type : 'POST',
                dataType : 'json',
                data : {
                    condominio : self.condominio.id(),
                    rfc : self.rfc()
                },
                success : function(data) {
                    if (data.rfc) {
                        bootbox.confirm("El proveedor ya existe ¿Deseas cargar los datos?", function(result) {
                            if (result){
                                self.limpiar();
                                self.cargar(data);

                                $("#proveedor-pais").select2();
                                $("#condominio-estado").select2();
                                $("#condominio-municipio").select2();
                                $("#condominio-colonia").select2();
                                $("#giros").select2();

                                $("#cuenta").select2();
                                $("li.contacto > a").last().click();
                                $("#contacto-button").on("click", function(){
                                    $("li.contacto > a").last().click();
                                    for (var i = 0; i < $("li.contacto > a").length; i++) {
                                        $("#form-contacto-" + i).validate();
                                    }
                                })
                                $("#proveedor-estado").select2();
                                $("#proveedor-municipio").select2();
                                $("#proveedor-colonia").select2();
                            } else {
                                self.limpiar();
                            }
                        });
                    }
                }
            });
        }
    }

    
}
