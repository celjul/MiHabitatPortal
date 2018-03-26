var Instalacion = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.activo = ko.observable(data ? data.activo : true);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.costo= ko.observable(data ? data.costo : 0).extend({
		currency: 2
	});
    self.nombre= ko.observable(data ? data.nombre : undefined);
    self.descripcion= ko.observable(data ? data.descripcion : undefined);
    self.reglamento= ko.observable(data ? data.reglamento : undefined);
    self.imagen = new Archivo(data ? data.imagen : undefined);
    self.maximoReservaciones= ko.observable(data ? data.maximoReservaciones : undefined);
    self.cuenta = new Cuenta(data ? data.cuenta : undefined);
    self.unidad = new Catalogo(data ? data.unidad : undefined);
    self.disponibilidades = ko.observableArray(data ? data.disponibilidades : []);
	self.reservaciones = ko.observableArray(data ? data.reservaciones : []);
    self.cobroAutomatico = ko.observable(data ? data.cobroAutomatico : true);
    
    self.auxCambioImagen = true;
  
    //Funcionalidad para carga de imagenes
	self.fileData = ko.observable({
	    dataURL: ko.observable()
	  });
	self.onClear = function(fileData){
		notificacionConfirmacion("¿Está seguro de eliminar la Imagen?", function() {
			document.getElementById('fileupload').value = "";
			fileData.clear && fileData.clear();
		});                            
	  };
	//Función para cada vez que cambien la imagen se suba a session  
	self.fileData().dataURL.subscribe(function(dataURL){
		if(self.auxCambioImagen){
			if(document.getElementById("fileupload")) {
				var file = document.getElementById("fileupload").files[0];


				var fd = new FormData();

				var xhr = new XMLHttpRequest();
				if (!file) {
					xhr.open("POST", '/administrador/instalaciones/eliminarimagen', true);
				}
				else {
					fd.append("file", file);
					xhr.open("POST", '/administrador/instalaciones/subirimagen', true);
				}
				xhr.onreadystatechange = function () {
					/*if (xhr.readyState == 4) {
					 alert('success');
					 }*/
				};
				xhr.send(fd);
			}
		}
		else {
			self.auxCambioImagen = true;
		}
	});
	
    
    self.cargar = function(data) {
    	
    	self.id(data ? data.id : undefined);
    	self.activo(data ? data.activo : true);
    	self.condominio.cargar(data ? data.condominio : undefined);
    	self.costo(data ? data.costo : 0);
    	self.nombre(data ? data.nombre : undefined);
    	self.descripcion(data ? data.descripcion : undefined);
    	self.reglamento(data ? data.reglamento : undefined);
    	self.imagen.cargar(data ? data.imagen : undefined);
    	self.maximoReservaciones(data ? data.maximoReservaciones : undefined);
    	self.cuenta.cargar(data ? data.cuenta : undefined);
    	self.unidad.cargar(data ? data.unidad : undefined);
    	if (data && data.disponibilidades && data.disponibilidades.length > 0) {
			ko.utils.arrayForEach(data.disponibilidades, function(c) {
				var cd = new Disponibilidad();
				cd.cargar(c);
				self.disponibilidades.push(cd);
			});
		}
		if (data && data.reservaciones && data.reservaciones.length > 0) {
			ko.utils.arrayForEach(data.reservaciones, function(c) {
				var cd = new Reservacion();
				cd.cargar(c);
				self.reservaciones.push(cd);
			});
		}
    	self.cobroAutomatico(data ? data.cobroAutomatico : undefined);
    	self.auxCambioImagen = false;
    	self.fileData().dataURL(self.imagen&&self.imagen.tipo()?("data:"+self.imagen.tipo()+";base64,"+self.imagen.bytes()):"");
    	

    }
    
    self.limpiar = function() {

    	self.id(undefined);
    	self.activo(true);
    	self.condominio.limpiar();
    	self.costo(undefined);
    	self.nombre(undefined);
    	self.descripcion(undefined);
    	self.reglamento(undefined);
    	self.imagen.limpiar(undefined);
    	self.maximoReservaciones(undefined);
    	self.cuenta.limpiar();
    	self.unidad.limpiar();
    	self.disponibilidades([]);
		self.reservaciones([]);
    	self.cobroAutomatico(true);
    	self.fileData().dataURL(undefined);
    	self.auxCambioImagen = true;
    	    	
    }

    self.getJson = function() {
        var instalacion = self.estructurar(ko.toJS(self));
        instalacion = validarObject(instalacion);
        return instalacion;
    }
    
    self.estructurar = function(data) {

    	delete data.fileData;
    	delete data.auxCambioImagen;
    	
    	if (data.condominio) {
			data.condominio = self.condominio.getJson();
		}
    	if (data.imagen) {
			delete data.imagen;
		}
    	if(data.cobroAutomatico) {
    		if (data.cuenta) {
    			data.cuenta = self.cuenta.getJson();
    		}
        	if (data.unidad) {
    			data.unidad = self.unidad.getJson();
    		}
    	}
    	else {
    		delete data.cuenta;
    		delete data.unidad;
    	}

		if (data.disponibilidades && data.disponibilidades.length > 0) {
			var disponibilidades = [];
			ko.utils.arrayForEach(self.disponibilidades(), function(c) {
				var cd = c.getJson();
				disponibilidades.push(cd);
			});
			data.disponibilidades = disponibilidades;
		}

		if (data.reservaciones && data.reservaciones.length > 0) {
			var reservaciones = [];
			ko.utils.arrayForEach(self.reservaciones(), function(c) {
				var cd = c.getJson();
				reservaciones.push(cd);
			});
			data.reservaciones = reservaciones;
		}

		return data;
	}
    
 }