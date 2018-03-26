var InstalacionViewModel = function(data) {
	var self = this;


	self.instalacion = new Instalacion();
	self.cuentas = ko.observableArray([]);
	self.unidades = ko.observableArray([]);

	if (data && data.instalacion) {
		self.instalacion.cargar(data.instalacion);
	}
	else {
		for(var i=0;i<7;i++) {
			var disponibilidad = new Disponibilidad();
			disponibilidad.dia(i);
			disponibilidad.activo(true);
			disponibilidad.setDiaCompleto();
			self.instalacion.disponibilidades.push(disponibilidad);
		}
	}

	if (data && data.condominio) {
		self.instalacion.condominio.cargar(data.condominio);
	}

	if (data && data.imagen) {
		self.instalacion.imagen.cargar(data.imagen);
	}

	if (data && data.cuentas) {
		self.cuentas(ordenar(data.cuentas,true));
	}

	if (data && data.unidades) {
		ko.utils.arrayForEach(data.unidades, function(b) {
			var unidad = new Catalogo();
			unidad.cargar(b);
			self.unidades.push(unidad);

		});
	}

	self.instalacion.unidad.id.subscribe(function() {
		var cont = ko.utils.arrayFirst(self.unidades(), function(item) {
			return item.id() == self.instalacion.unidad.id();
		});
		self.instalacion.unidad.descripcion(cont?cont.descripcion():'');
	});

	self.guardar = function() {
		if ($("#instalacion-form").valid()) {

			var instalacion = JSON.stringify(self.instalacion.getJson());
			console.log(instalacion);
			$.ajax({

				url : contextPath + "/administrador/instalaciones/guardar",
				type : 'POST',
				dataType : 'json',
				data : instalacion,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.instalacion.limpiar();
					self.instalacion.cargar(data);
					notificacionExito("La instalacion se ha guardado correctamente");

				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar la instalacion");
				}
			});
			/*}else{
			 notificacionAdvertencia("La cuenta ya existe, intente con otro numero de cuenta.");
			 }*/
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}

	self.actualizar = function(){
		if ($("#instalacion-form").valid()) {
			var instalacion = JSON.stringify(self.instalacion.getJson());
			console.log(instalacion);
			$.ajax({
				async : true,
				cache : false,
				url : contextPath + "/administrador/instalaciones/actualizar",
				type : 'POST',
				dataType : 'json',
				data : instalacion,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					self.instalacion.limpiar();
					self.instalacion.cargar(data);
					notificacionExito("La instalación se ha actualizado correctamente");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar la instalación");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores");
		}
	}
}

var ReservarInstalacionViewModel = function(data) {
	var self = this;
	var html = function(id) { return document.getElementById(id); }; //just a helper

	self.instalacion = new Instalacion();
	self.cuentas = ko.observableArray([]);
	self.estatus = ko.observableArray([]);
	self.unidades = ko.observableArray([]);
	self.rol = data.rol;
	self.usuario = data.usuario;
	self.aceptoReglamento = ko.observable(false);

	//variables para el LigthBox del calendario que ayuda a modificar eventos
	self.contactoSeleccionado = new Contacto(data ? data.contactoSeleccionado : undefined);
	self.departamentoSeleccionado = new Departamento(data ? data.departamentoSeleccionado : undefined);
	self.departamentosContacto = ko.observableArray([]);
	self.readOnly = ko.observable(false);
	self.pendiente = ko.observable(true);
	self.estatusSeleccionado = new Catalogo();

	self.contactos = ko.observableArray([]);
	if (data && data.contactos) {
		ko.utils.arrayForEach(data.contactos, function(c) {
			var contacto = new Contacto();
			contacto.cargarContacto(c);
			self.contactos.push(contacto);
		});
	}

	self.getDepartamentosContacto = function() {
		if(self.contactoSeleccionado && self.contactoSeleccionado.id) {
			var cont = ko.utils.arrayFirst(self.contactos(), function(item) {
				return item.id() == self.contactoSeleccionado.id();
			});
			self.departamentosContacto(cont?cont.departamentos():undefined);
		}
	}

	if(self.rol != 'administrador') {
		self.contactoSeleccionado.id(self.contactos()[0].id());
		self.getDepartamentosContacto();
		//$("#contacto").disabled('disabled');
	}

	self.inicializarCalendario = function(instalacion) {
		scheduler.config.xml_date="%Y-%m-%d %H:%i";
		scheduler.config.first_hour = 0;
		scheduler.config.last_hour = 24;
		scheduler.config.scroll_hour = 8;
		scheduler.config.time_step = 30;
		scheduler.config.touch = "force";
		scheduler.config.full_day = true;
		scheduler.config.all_timed = 'short',
		scheduler.config.multi_day = true;
		scheduler.config.limit_time_select = true;
		scheduler.config.details_on_create = true;
		scheduler.config.details_on_dblclick = true;
		scheduler.config.collision_limit = self.instalacion.maximoReservaciones();

		scheduler.templates.event_class=function(start, end, event){
			var css = "";

			if(self.rol == 'administrador' || (event.contacto == self.contactos()[0].id())){
				if(event.estatusReservacion == config.estatusReservacion.pendiente) {
					css += "pendiente";
				} else if(event.estatusReservacion == config.estatusReservacion.reservada) {
					css += "reservada";
				}
				else if(event.estatusReservacion == config.estatusReservacion.rechazada) {
					css += "rechazada";
				}
				else if(event.estatusReservacion == config.estatusReservacion.cancelada) {
					css += "cancelada";
				}
			}
			/*if(event.id == scheduler.getState().select_id){
				css += " selected";
			}*/
			return css; // default return

			/*
			 Note that it is possible to create more complex checks
			 events with the same properties could have different CSS classes depending on the current view:

			 var mode = scheduler.getState().mode;
			 if(mode == "day"){
			 // custom logic here
			 }
			 else {
			 // custom logic here
			 }
			 */
		};

		//1436392175550
		//1436392175549

		scheduler.showLightbox = function(id) {
			var ev = scheduler.getEvent(id);

			scheduler.startLightbox(id, html("calendarLightBox"));
			$('#calendarLightBox').attr("style", "top: 0px;");
			if(ev.idReservacion){
				self.contactoSeleccionado.id(ev.contacto);
				self.getDepartamentosContacto();
				self.departamentoSeleccionado.id(ev.departamento);
			} else {
				self.contactoSeleccionado.id(ev.contacto);
				self.getDepartamentosContacto();
				if(self.contactoSeleccionado.id()) {
					self.departamentoSeleccionado.id(self.departamentosContacto()[0].id.departamento.id());
				}
			}

			self.readOnly(ev.readonly);
			self.pendiente(ev.pendiente);
			var est = (ko.utils.arrayFirst(self.estatus(), function (item) {
				return item.id() == ev.estatusReservacion;
			}));
			self.estatusSeleccionado.id(est.id());
			self.estatusSeleccionado.descripcion(est.descripcion());

			/*$('#inicioDate').datetimepicker('setDate',new Date(ev.start_date));
			$('#inicioHour').datetimepicker('setDate',new Date(ev.start_date));
			$('#finDate').datetimepicker('setDate',new Date(ev.end_date));
			$('#finHour').datetimepicker('setDate',new Date(ev.end_date));*/

			$('#inicioDate').data("DateTimePicker").date(new Date(ev.start_date));
			$('#inicioHour').data("DateTimePicker").date(new Date(ev.start_date));
			$('#finDate').data("DateTimePicker").date(new Date(ev.end_date));
			$('#finHour').data("DateTimePicker").date(new Date(ev.end_date));

			$("#contacto").select2();
			$("#departamento").select2();

			$("#calendarLightBox").modal("show");
		};



		ko.utils.arrayForEach(instalacion.disponibilidades(), function(disponibilidad) {
			if(disponibilidad.activo() == false) {
				scheduler.blockTime(disponibilidad.dia(), "fullday");
			}
			else if(instalacion.unidad.id == AppConfig.catalogos.unidadesreservacion.hora && disponibilidad.diaCompleto() == false) {
				var arrInicio = disponibilidad.horaInicio().split(":");
				var arrFin = disponibilidad.horaFin().split(":");
				scheduler.blockTime(disponibilidad.dia(), [0,arrInicio[0]*60+parseInt(arrInicio[1]),arrFin[0]*60+parseInt(arrFin[1]),1440]);
			}
		});

		scheduler.init('scheduler_here',new Date(),self.instalacion.unidad.id()==config.unidades.dia?"month":"week");

		ko.utils.arrayForEach(instalacion.reservaciones(), function(c) {
			if(self.rol == 'administrador' || (c.contacto && c.contacto.id() &&
					c.contacto.usuario.id() == self.usuario)){
				scheduler.addEvent({
					id: c.id(),
					idReservacion: c.id(),
					start_date: String.formatMilisecondsToStringCalendar(c.fechaInicio()),
					end_date: String.formatMilisecondsToStringCalendar(c.fechaFin()),
					text: c.contacto.nombreCompleto(),
					contacto: c.contacto.id(),
					departamento: c.departamento.id(),
					estatusReservacion: c.estatusReservacion.id(),
					readonly:false,
					pendiente: (self.rol == 'administrador'?true:c.estatusReservacion.id() == config.estatusReservacion.pendiente)
				});
			} else if(c.estatusReservacion.id() == config.estatusReservacion.reservada){
					scheduler.addEvent({
						id: c.id(),
						idReservacion: c.id(),
						start_date: String.formatMilisecondsToStringCalendar(c.fechaInicio()),
						end_date: String.formatMilisecondsToStringCalendar(c.fechaFin()),
						text: 'Reservado',
						contacto: '',
						departamento: '',
						estatusReservacion: c.estatusReservacion.id(),
						readonly:true,
						pendiente: false
					});
			}
		});

		function block_readonly(id){
			if (!id) return true;
			return (!this.getEvent(id).readonly && this.getEvent(id).pendiente);
		}
		scheduler.attachEvent("onBeforeDrag",block_readonly)
		scheduler.attachEvent("onClick",block_readonly)

		self.prepara_evento = function(id) {
			var ev = scheduler.getEvent(id);
			/*var diaFinal = new Date();
			diaFinal.setDate(ev.end_date.getDate());
			diaFinal.setMonth(ev.end_date.getMonth());
			diaFinal.setFullYear(ev.end_date.getFullYear());*/
			self.aceptoReglamento(false);
			ev.idReservacion = undefined;
			if(self.rol == 'administrador'){
				self.contactoSeleccionado.cargar(undefined);
				ev.estatusReservacion = config.estatusReservacion.reservada;

			} else {
				ev.contacto = self.contactos()[0].id();
				ev.estatusReservacion = config.estatusReservacion.pendiente;

			}

			if(self.instalacion.unidad.id() == AppConfig.catalogos.unidadesreservacion.dia){
				ev.start_date.setHours(0);
				ev.start_date.setMinutes(0);
				ev.end_date.setHours(24);
				ev.end_date.setMinutes(0);
				/*ev.end_date.setDate(diaFinal.getDate());
				ev.end_date.setMonth(diaFinal.getMonth());
				ev.end_date.setFullYear(diaFinal.getFullYear());*/
			}

			ev.pendiente = true;
			ev.readonly = false;
			self.departamentoSeleccionado.cargar(undefined);
		}


		//Esta variable la tuve que usar ya que no se por qué pero perdia la referencia al evento al crearse
		self.eventoAux;
		scheduler.attachEvent("onBeforeEventChanged", function(ev, e, is_new, original){
			self.prepara_evento(ev.id);
			scheduler.showLightbox(ev.id);
			self.eventoAux = ev;
		});

/*
		scheduler.attachEvent("onEventAdded", function(id,ev){
			console.log("jial");
		});*/

		//Se inicializa el datetimePicker para el modal de detalle del evento
		/*$('#inicio').datetimepicker({
			language:  'es',
			format: 'dd MM yyyy - HH:ii',
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			minView: self.instalacion.unidad.id()==config.unidades.dia?2:0,
			startView: 2,
			forceParse: 0,
			showMeridian: 1

		});*/

		$('#inicioDate').datetimepicker({
			format: 'DD MMMM YYYY',
			locale: 'es',
			showTodayButton: true,
			allowInputToggle: true,
			ignoreReadonly: true
		});
		$('#inicioHour').datetimepicker({
			format: 'HH:mm A',
			showTodayButton: true,
			allowInputToggle: true,
			ignoreReadonly: true
		});

		$('#finDate').datetimepicker({
			format: 'DD MMMM YYYY',
			locale: 'es',
			showTodayButton: true,
			allowInputToggle: true,
			ignoreReadonly: true
		});
		$('#finHour').datetimepicker({
			format: 'HH:mm A',
			showTodayButton: true,
			allowInputToggle: true,
			ignoreReadonly: true
		});


	}

	self.save_form = function() {
		/*var inicio = new Date($('#inicioDate').data("DateTimePicker").date().toISOString().substring(0,11) +
						$('#inicioHour').data("DateTimePicker").date().toISOString().substring(11,24));*/

		var inicio = new Date($('#inicioDate').data("DateTimePicker").date().year(),
			$('#inicioDate').data("DateTimePicker").date().month(),
			$('#inicioDate').data("DateTimePicker").date().date(),
			$('#inicioHour').data("DateTimePicker").date().hour(),
			$('#inicioHour').data("DateTimePicker").date().minute(),
			0,
			0);

		/*var fin = new Date($('#finDate').data("DateTimePicker").date().toISOString().substring(0,11) +
		$('#finHour').data("DateTimePicker").date().toISOString().substring(11,24));*/

		var fin = new Date($('#finDate').data("DateTimePicker").date().year(),
			$('#finDate').data("DateTimePicker").date().month(),
			$('#finDate').data("DateTimePicker").date().date(),
			$('#finHour').data("DateTimePicker").date().hour(),
			$('#finHour').data("DateTimePicker").date().minute(),
			0,
			0);


		if(inicio.getTime() >= fin.getTime()) {
			notificacionAdvertencia("¡La fecha final de la reservación debe ser mayor a la inicial!");
		}
		else if (!(self.rol == 'administrador') && !self.aceptoReglamento()) {
			notificacionAdvertencia("Debe aceptar los terminos y condiciones del reglamento de uso.");
		}
		else {
			var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
			if(!ev) {
				ev = self.eventoAux;
				scheduler.addEvent(ev);
			}
			var tempInicio = ev.start_date;
			var tempFin = ev.end_date;

			/*ev.start_date = $('#inicio').datetimepicker('getDate');
			ev.end_date = $('#fin').datetimepicker('getDate');*/

			ev.start_date = inicio;
			ev.end_date = fin;

			var cont = ko.utils.arrayFirst(self.contactos(), function (item) {
				return item.id() == self.contactoSeleccionado.id();
			});
			ev.text = cont ? cont.nombreCompleto() : "Reservación sin condómino definido";
			ev.contacto = self.contactoSeleccionado.id();
			ev.departamento = self.departamentoSeleccionado.id();

			if(scheduler.checkLimitViolation(ev)) {
				if(scheduler.checkCollision(ev)) {

					ev.estatusReservacion = (self.rol == 'contacto' && self.estatusSeleccionado.id() != AppConfig.catalogos.estatusreservacion.cancelada)?
						AppConfig.catalogos.estatusreservacion.pendiente:self.estatusSeleccionado.id();
					ev.pendiente = self.pendiente();

					self.reservar(ev.id,false)
					scheduler.endLightbox(true, html("calendarLightBox"));
					$('#calendarLightBox').modal('hide');

				}
				else {
					ev.start_date = tempInicio;
					ev.end_date = tempFin;

					ev.estatusReservacion = AppConfig.catalogos.estatusreservacion.rechazada;
					notificacionAdvertencia("El horario donde intenta reservar ya no tiene reservaciones disponibles, por favor elija otro horario o contacte con el Administrador.");
				}
			} else {
				ev.start_date = tempInicio;
				ev.end_date = tempFin;

				ev.estatusReservacion = AppConfig.catalogos.estatusreservacion.rechazada;
				notificacionAdvertencia("Está intentando reservar en un horario no disponible, por favor elija otro horario o contacte al Administrador.");
			}
		}

	}
	self.close_form = function() {
		scheduler.endLightbox(false, html("calendarLightBox"));
	}
	self.new_event = function() {
		scheduler.addEventNow();
	}

	self.delete_event = function() {
		var event_id = scheduler.getState().lightbox_id;
		self.reservar(event_id,true)
		scheduler.endLightbox(false, html("calendarLightBox"));
		scheduler.deleteEvent(event_id);
	}

	self.aprobar = function() {
		var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
		var est = (ko.utils.arrayFirst(self.estatus(), function (item) {
			return item.id() == config.estatusReservacion.reservada;
		}));
		self.estatusSeleccionado.id(est.id());
		self.estatusSeleccionado.descripcion(est.descripcion());
		self.pendiente(self.rol == 'administrador');
	}

	self.rechazar = function() {
		var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
		var est = (ko.utils.arrayFirst(self.estatus(), function (item) {
			return item.id() == config.estatusReservacion.rechazada;
		}));
		self.estatusSeleccionado.id(est.id());
		self.estatusSeleccionado.descripcion(est.descripcion());
		self.pendiente(self.rol == 'administrador');
	}

	self.cancelar = function() {
		var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
		var est = (ko.utils.arrayFirst(self.estatus(), function (item) {
			return item.id() == config.estatusReservacion.cancelada;
		}));
		self.estatusSeleccionado.id(est.id());
		self.estatusSeleccionado.descripcion(est.descripcion());
		self.pendiente(self.rol == 'administrador');
	}

	self.solicitar = function() {
		var ev = scheduler.getEvent(scheduler.getState().lightbox_id);
		var est = (ko.utils.arrayFirst(self.estatus(), function (item) {
			return item.id() == config.estatusReservacion.pendiente;
		}));
		self.estatusSeleccionado.id(est.id());
		self.estatusSeleccionado.descripcion(est.descripcion());
		self.pendiente(true);
	}

	if (data && data.instalacion) {
		self.instalacion.cargar(data.instalacion);
		self.inicializarCalendario(self.instalacion);
	}

	if (data && data.imagen) {
		self.instalacion.imagen.cargar(data.imagen);
	}

	if (data && data.cuentas) {
		self.cuentas(ordenar(data.cuentas,true));
	}

	if (data && data.estatus) {
		ko.utils.arrayForEach(data.estatus, function(b) {
			var estat = new Catalogo();
			estat.cargar(b);
			self.estatus.push(estat);

		});
	}

	if (data && data.unidades) {
		ko.utils.arrayForEach(data.unidades, function(b) {
			var unidad = new Catalogo();
			unidad.cargar(b);
			self.unidades.push(unidad);

		});
	}

	self.reservar = function(id, eliminar){
		/*if (eliminar || $("#reservacion-form").valid()) {*/

			var ev = scheduler.getEvent(id);
			if(!ev) {
				ev = self.eventoAux;
			}
			var reservacion = (JSON.parse(JSON.stringify(ev)));
			//var reservacionesAux = [];
			reservacion.activo = true;
			reservacion.diaCompleto = false;
			reservacion.fechaInicio = reservacion.start_date;
			reservacion.fechaFin = reservacion.end_date;


			reservacion = removeObjectProperties(reservacion, /^_/);

			if (reservacion.idReservacion) {
				reservacion.id = parseInt(reservacion.idReservacion, 10);
			} else {
				delete reservacion.id;
			}

			reservacion.instalacion = {id: self.instalacion.id()};

			if (reservacion.contacto) {
				reservacion.contacto = {id: parseInt(reservacion.contacto, 10)};
			} else {
				delete reservacion.contacto;
			}

			if (reservacion.departamento) {
				reservacion.departamento = {id: parseInt(reservacion.departamento, 10)};
			} else {
				delete reservacion.departamento;
			}

			if (reservacion.estatusReservacion) {
				reservacion.estatusReservacion = {id: parseInt(reservacion.estatusReservacion, 10)};
			}

			delete reservacion.start_date;
			delete reservacion.end_date;
			delete reservacion.text;
			delete reservacion.idReservacion;
			delete reservacion.readonly;
			delete reservacion.pendiente;

			var reservacionString = JSON.stringify(reservacion)

			console.log(reservacionString);
			$.ajax({
				async : true,
				cache : false,
				url : eliminar?contextPath + "/administrador/instalaciones/reservar/eliminar":(self.rol == 'administrador'?contextPath + "/administrador/instalaciones/reservar/guardar":contextPath + "/contacto/mis-reservaciones/reservar/guardar"),
				type : 'POST',
				dataType : 'json',
				data : reservacionString,
				contentType : 'application/json',
				mimeType : 'application/json',
				success : function(data) {
					notificacionExito(eliminar?"La reservación se ha eliminado":(self.rol == 'administrador'?"La reservación ha sido guardada.":contextPath + "La reservación ha sido guardada."));
					var reservacionesRes = data;
					self.instalacion.reservaciones([]);
					if (reservacionesRes && reservacionesRes.length > 0) {
						ko.utils.arrayForEach(reservacionesRes, function(c) {
							var cd = new Reservacion();
							cd.cargar(c);
							self.instalacion.reservaciones.push(cd);
						});
					}
					scheduler.clearAll();
					ko.utils.arrayForEach(self.instalacion.reservaciones(), function(c) {
						if(self.rol == 'administrador' || (c.contacto && c.contacto.id() &&
							c.contacto.usuario.id() == self.usuario)){
							scheduler.addEvent({
								id: c.id(),
								idReservacion: c.id(),
								start_date: String.formatMilisecondsToStringCalendar(c.fechaInicio()),
								end_date: String.formatMilisecondsToStringCalendar(c.fechaFin()),
								text: c.contacto.nombreCompleto(),
								contacto: c.contacto.id(),
								departamento: c.departamento.id(),
								estatusReservacion: c.estatusReservacion.id(),
								readonly:false,
								pendiente: (self.rol == 'administrador'?true:c.estatusReservacion.id() == config.estatusReservacion.pendiente)
							});
						} else if(c.estatusReservacion.id() == config.estatusReservacion.reservada){
							scheduler.addEvent({
								id: c.id(),
								idReservacion: c.id(),
								start_date: String.formatMilisecondsToStringCalendar(c.fechaInicio()),
								end_date: String.formatMilisecondsToStringCalendar(c.fechaFin()),
								text: 'Reservado',
								contacto: '',
								departamento: '',
								estatusReservacion: c.estatusReservacion.id(),
								readonly:true,
								pendiente: false
							});
						}


					});

				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar la reservación");

				}
			});
		/*} else {
			notificacionAdvertencia("El formulario tiene errores");

		}*/
	}

}

var ListaInstalacionViewModel = function(data) {
	self = this;

	self.instalaciones = ko.observableArray([]);

	if (data.instalaciones != undefined && data.instalaciones.length > 0) {
		ko.utils.arrayForEach(data.instalaciones, function(c) {
			self.instalaciones.push(c);
		});
	}

	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/instalaciones/actualizar/" + data.id;
	};

	self.reservar = function(data) {
		location.href = contextPath + "/administrador/instalaciones/reservar/" + data.id;
	};

	self.reservarContacto = function(data) {
		location.href = contextPath + "/contacto/mis-reservaciones/reservar/" + data.id;
	};
}

var inicializarRelojes = function () {
	/*
	 * CLOCKPICKER
	 */
	$('.timepicker').clockpicker({
		placement: 'bottom',
		align: "right",
		donetext: 'Done'
	});
}

