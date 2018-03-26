var InicioViewModel = function(data) {
	var self = this;

	self.hoy = new Date();

	self.reporteIngresosEgresos = new IngresosEgresos();
	self.reporteCuentasCobrar = new CuentasCobrar();
	self.listaIngresos = ko.observable();
	self.listaBancos = ko.observable();
	self.listaEgresos = ko.observable();
	self.listaPorCobrar = ko.observable();
	self.listaPorPagar = ko.observable();

	self.temas = ko.observableArray([]);
	self.actividadPosts = ko.observableArray([]);
	self.tareasCriticas = ko.observableArray([]);
	self.tareasPendientes = ko.observableArray([]);
	self.tareasCompletadas = ko.observableArray([]);

	self.tareaActual = new Tarea();

	self.actualizarPendientes = function() {
		var inicioPendientesAdmin = $('#inicioPendientesAdmin');
		//$(inicioPendientesAdmin).empty();
		var pendientes = $('#panelPendientesAdmin').clone();
		$(pendientes).attr("id", "inicioPanelAdmilLista");
		if($(pendientes).children().length>0) {
			$.each($(pendientes).children(), function (i, item) {
				var span = $(item).find(".padding-10");
				$(span).click(function (e) {
					vistoNotificacion($(span).attr('idReferencia'), true);
					window.location = $(span).attr('link');
					e.stopPropagation();
				});
			});
			$(inicioPendientesAdmin).append($(pendientes));
		} else {
			var ul = document.createElement("ul");
			$(ul).addClass('notification-body');
			var li = document.createElement("li");
			var span = document.createElement("span");
			var span2 = document.createElement("span");
			$(span).addClass("padding-10 text-justify bg-color-teal");
			$(span2).html("<strong class='txt-color-white margin-top-10'>No hay pendientes de atención...</strong>");
			$(span).append($(span2));
			$(li).append($(span));
			$(ul).append($(li));
			$(inicioPendientesAdmin).append($(ul));
		}

	}

	self.consulta = function() {
		$.ajax({
			cache : false,
			url: contextPath + "/administrador/inicio/carga",
			data : '',
			success: function(data) {
				console.log(data);
				self.reporteIngresosEgresos.cargar(data.reporteIngresosEgresos);

				self.listaBancos('<table>');
				ko.utils.arrayForEach(data.reporteIngresosEgresos.bancos.hijas, function(b) {
					self.listaBancos((self.listaBancos()?self.listaBancos():'') + '<tr><td class="font-xs" style="padding-right: 20px"> Banco - ' + b.nombre + '</td><td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td></tr>');
				});
				ko.utils.arrayForEach(data.reporteIngresosEgresos.cajas.hijas, function(b) {
					self.listaBancos((self.listaBancos()?self.listaBancos():'') + '<tr><td class="font-xs" style="padding-right: 20px"> Caja - ' + b.nombre + '</td><td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td></tr>');
				});
				self.listaBancos(self.listaBancos() + '</table>');

				self.listaIngresos('<table>');
				ko.utils.arrayForEach(data.reporteIngresosEgresos.ingresos.hijas, function(b) {
					self.listaIngresos((self.listaIngresos()?self.listaIngresos():'') + '<tr><td class="font-xs" style="padding-right: 20px">' + b.nombre + '</td><td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td></tr>');
				});
				self.listaIngresos(self.listaIngresos() + '</table>');

				self.listaEgresos('<table>');
				ko.utils.arrayForEach(data.reporteIngresosEgresos.egresos.hijas, function(b) {
					self.listaEgresos((self.listaEgresos()?self.listaEgresos():'') + '<tr><td class="font-xs" style="padding-right: 20px">' + b.nombre + '</td><td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td></tr>');
				});
				self.listaEgresos(self.listaEgresos() + '</table>');


				if(data.reporteCuentasCobrar.adeudos && data.reporteCuentasCobrar.adeudos.length > 0){
					data.reporteCuentasCobrar.adeudos.sort(function(a, b){return b.saldo - a.saldo});
					self.listaPorCobrar('<table>');
					var i = 0;
					ko.utils.arrayForEach(data.reporteCuentasCobrar.adeudos, function(b) {
						i++;
						if(i < 10) {
							self.listaPorCobrar((self.listaPorCobrar() ? self.listaPorCobrar() : '') + '<tr><td class="font-xs" style="padding-right: 20px"> Dpto ' + b.departamento + '</td>' +
							'<td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td><td class="text-align-right font-xs"> $' + b.antiguedad + '</td></tr>');
						}
						if(i == 10){
							self.listaPorCobrar((self.listaPorCobrar()?self.listaPorCobrar():'') + '<tr><td class="font-xs" style="padding-right: 20px"> ... ' + '</td><td class="text-align-right font-xs"> '  + '</td></tr>');
						}
					});
					self.listaPorCobrar(self.listaPorCobrar() + '</table>');
				}

				if(data.reporteCuentasPagar.adeudos && data.reporteCuentasPagar.adeudos.length > 0) {
					data.reporteCuentasPagar.adeudos.sort(function (a, b) {
						return b.saldo - a.saldo
					});
					self.listaPorPagar('<table>');
					var i = 0;
					ko.utils.arrayForEach(data.reporteCuentasPagar.adeudos, function (b) {
						i++;
						if (i < 10) {
							self.listaPorPagar((self.listaPorPagar() ? self.listaPorPagar() : '') + '<tr><td class="font-xs" style="padding-right: 20px"> ' + b.proveedor + '</td><td class="text-align-right font-xs"> $' + numeral(b.saldo).format('0,0.00') + '</td></tr>');
						}
						if (i == 10) {
							self.listaPorPagar((self.listaPorPagar() ? self.listaPorPagar() : '') + '<tr><td class="font-xs" style="padding-right: 20px"> ... ' + '</td><td class="text-align-right font-xs"> ' + '</td></tr>');
						}
					});
					self.listaPorPagar(self.listaPorPagar() + '</table>');
				}

				ko.utils.arrayForEach(data.eventos, function(b) {
					$('#calendar').fullCalendar('renderEvent', {
						title : b.titulo,
						start : b.inicio,
						end : b.fin,
						description : b.descripcion,
						className : ["event", (b.tipo=='evento'?"bg-color-greenLight":(
														b.tipo=='cobranza'?"bg-color-red":(
														b.tipo=='recordatorio'?"bg-color-orange":"bg-color-blue"))),
							"txt-color-white"],
						tipo: b.tipo,
						idReferencia: b.idReferencia
					} , true );
				});

				data.temas.sort(function(a, b){return b.fecha - a.fecha});
				self.temas(data.temas);
				self.temas.splice(20,self.temas.length);

				var ahora = new Date();
				ko.utils.arrayForEach(self.temas(), function(t) {
					 var contPosts = 0;
					 ko.utils.arrayForEach(t.posts, function(p) {
						 contPosts++;
						 self.actividadPosts.push({
						 	 nombre: p.usuario.persona.nombre + ' ' + p.usuario.persona.apellidoPaterno + ' ' + p.usuario.persona.apellidoMaterno,
							 descripcion: contPosts==1?'publicó un nuevo tema':'comentó sobre el tema',
							 tema: t.nombre,
							 fecha: p.fecha,
							 blog: t.blog.nombre,
							 id: t.id,
							 comentario: p.comentario,
							 antiguedad: 'Hace ' + (Math.floor((ahora.getTime() - p.fecha)/(1000*60*60*24))>0?Math.floor((ahora.getTime() - p.fecha)/(1000*60*60*24)) + 'd':Math.floor((ahora.getTime() - p.fecha)/(1000*60*60)) + 'h')
						 });
					 });
				 });

				self.actividadPosts.sort(function(a, b){return b.fecha - a.fecha});
				self.actividadPosts.splice(20,self.actividadPosts.length);

				data.tareas.sort(function(a, b){return b.fecha - a.fecha});
				ko.utils.arrayForEach(data.tareas, function(t) {
					if(t.completada){
						self.tareasCompletadas.push(t);
					} else if(t.critica) {
						self.tareasCriticas.push(t);
					} else {
						self.tareasPendientes.push(t);
					}
				});

				self.actualizarPendientes();

			},
             global: false
		});
	}

	self.verPost = function(data) {
		location.href = contextPath + "/administrador/blogs/temas/actualizar/" + data.id;
	}

	self.nuevaTarea = function() {
		self.tareaActual.limpiar();
		$('#fechaVencimiento').data("DateTimePicker").date(null);
	}

	self.editarTarea = function(data) {
		self.tareaActual.cargar(data);
		$('#fechaVencimiento').data("DateTimePicker").date(new Date(data.fechaVencimiento));
	}

	self.guardarTarea = function() {
		if ($("#tarea-form").valid()) {
			var vencimiento = new Date($('#fechaVencimiento').data("DateTimePicker").date().year(),
				$('#fechaVencimiento').data("DateTimePicker").date().month(),
				$('#fechaVencimiento').data("DateTimePicker").date().date(),
				$('#fechaVencimiento').data("DateTimePicker").date().hour(),
				$('#fechaVencimiento').data("DateTimePicker").date().minute(),
				0,
				0);
			self.tareaActual.fechaVencimiento(vencimiento);
			self.tareaActual.fecha(new Date());
			var tarea = JSON.stringify(self.tareaActual.getJson());
			console.log(tarea);
			$.ajax({
				url: contextPath + "/administrador/inicio/tarea/guardar",
				type: 'POST',
				dataType: 'json',
				data: tarea,
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito('Se ha agregado la tarea con éxito.');
					self.tareasCompletadas([]);
					self.tareasCriticas([]);
					self.tareasPendientes([]);
					ko.utils.arrayForEach(data, function(t) {
						if(t.completada){
							self.tareasCompletadas.push(t);
						} else if(t.critica) {
							self.tareasCriticas.push(t);
						} else {
							self.tareasPendientes.push(t);
						}
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al agregar la tarea.");
				}
			});
		}
		else {
			notificacionAdvertencia("El formulario tiene errores.");
		}
	}

	self.actualizarTarea = function() {
		if ($("#tarea-form").valid()) {
			var vencimiento = new Date($('#fechaVencimiento').data("DateTimePicker").date().year(),
				$('#fechaVencimiento').data("DateTimePicker").date().month(),
				$('#fechaVencimiento').data("DateTimePicker").date().date(),
				$('#fechaVencimiento').data("DateTimePicker").date().hour(),
				$('#fechaVencimiento').data("DateTimePicker").date().minute(),
				0,
				0);
			self.tareaActual.fechaVencimiento(vencimiento);
			var tarea = JSON.stringify(self.tareaActual.getJson());
			console.log(tarea);
			$.ajax({
				url: contextPath + "/administrador/inicio/tarea/actualizar",
				type: 'POST',
				dataType: 'json',
				data: tarea,
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito('Se ha modificado la tarea con éxito.');
					self.tareasCompletadas([]);
					self.tareasCriticas([]);
					self.tareasPendientes([]);
					ko.utils.arrayForEach(data, function(t) {
						if(t.completada){
							self.tareasCompletadas.push(t);
						} else if(t.critica) {
							self.tareasCriticas.push(t);
						} else {
							self.tareasPendientes.push(t);
						}
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al modificar la tarea.");
				}
			});
		}
		else {
			notificacionAdvertencia("El formulario tiene errores.");
		}
	}

	self.completadaTarea = function(data) {
			self.tareaActual.cargar(data);
			var tarea = JSON.stringify(self.tareaActual.getJson());
			console.log(tarea);
			$.ajax({
				url: contextPath + "/administrador/inicio/tarea/actualizar",
				type: 'POST',
				dataType: 'json',
				data: tarea,
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito('Se ha modificado la tarea con éxito.');
					self.tareasCompletadas([]);
					self.tareasCriticas([]);
					self.tareasPendientes([]);
					ko.utils.arrayForEach(data, function(t) {
						if(t.completada){
							self.tareasCompletadas.push(t);
						} else if(t.critica) {
							self.tareasCriticas.push(t);
						} else {
							self.tareasPendientes.push(t);
						}
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al modificar la tarea.");
				}
			});
	}

	self.eliminarTarea = function(data) {
		if(data.id) {
			self.tareaActual.cargar(data);
		}
		if (self.tareaActual.id()) {
			bootbox.confirm("¿Estas seguro de que quieres eliminar esta tarea?", function(result) {
				if (result){
					var tarea = JSON.stringify(self.tareaActual.getJson());
					console.log(tarea);
					$.ajax({
						url: contextPath + "/administrador/inicio/tarea/eliminar",
						type: 'POST',
						dataType: 'json',
						data: tarea,
						contentType: 'application/json',
						mimeType: 'application/json',
						success: function (data) {
							notificacionExito('Se ha eliminado la tarea con éxito.');
							self.tareasCompletadas([]);
							self.tareasCriticas([]);
							self.tareasPendientes([]);
							ko.utils.arrayForEach(data, function(t) {
								if(t.completada){
									self.tareasCompletadas.push(t);
								} else if(t.critica) {
									self.tareasCriticas.push(t);
								} else {
									self.tareasPendientes.push(t);
								}
							});
						},
						error : function(jqXHR, textStatus, errorThrown) {
							notificacionError("Ocurrio un error al eliminar la tarea.");
						}
					});
				} else {

				}
			});
		}
		else {
			notificacionAdvertencia("No se ha seleccionado ninguna Tarea.");
		}
	}

}
