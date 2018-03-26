var InicioContactoViewModel = function(data) {
	var self = this;

	self.hoy = new Date();

	self.estadosDeCuenta = ko.observableArray([]);

	self.temas = ko.observableArray([]);
	self.actividadPosts = ko.observableArray([]);

	self.actualizarPendientes = function() {
		var inicioContacto = $('#inicioNotContacto');
		$(inicioContacto).empty();
		var pendientes = $('#panelValidacionesContacto').clone();
		$(pendientes).attr("id", "inicioPanelContValLista");
		$.each($(pendientes).children(), function(i, item) {
			var span = $(item).find(".padding-10");
			$(span).click(function(e){ vistoNotificacion($(span).attr('idReferencia'), true);window.location=$(span).attr('link');e.stopPropagation();});
			var visto = $(span).find('.avisto');
			$(visto).click(function(e){ vistoNotificacion($(span).attr('idReferencia'), ($(visto).attr('visto')=='true'));setTimeout(function(){ self.actualizarPendientes() }, 1000);e.stopPropagation();});
			var eliminar = $(span).find('.aeliminar');
			$(eliminar).click(function(e){ eliminarNotificacion($(span).attr('idReferencia'));setTimeout(function(){ self.actualizarPendientes() }, 1000);e.stopPropagation();});
		});
		$(inicioContacto).append($(pendientes));
		var cargos = $('#panelMovimientosContacto').clone();
		$(cargos).attr("id", "inicioPanelContMovsLista");
		$.each($(cargos).children(), function(i, item) {
			var spanCargos = $(item).find(".padding-10");
			$(spanCargos).click(function(e){ vistoNotificacion($(spanCargos).attr('idReferencia'), true);window.location=$(spanCargos).attr('link');e.stopPropagation();});
			var vistoCargos = $(spanCargos).find('.avisto');
			$(vistoCargos).click(function(e){ vistoNotificacion($(spanCargos).attr('idReferencia'), ($(vistoCargos).attr('visto')=='true'));setTimeout(function(){ self.actualizarPendientes() }, 1000);e.stopPropagation();});
			var eliminarCargos = $(spanCargos).find('.aeliminar');
			$(eliminarCargos).click(function(e){ eliminarNotificacion($(spanCargos).attr('idReferencia'));setTimeout(function(){ self.actualizarPendientes() }, 1000);e.stopPropagation();});
		});
		$(inicioContacto).append($(cargos));
	}

	self.consulta = function() {
		$.ajax({
			cache : false,
			url: contextPath + "/contacto/inicio/carga",
			data : '',
			success: function(data) {
				console.log(data);

				self.estadosDeCuenta(data.estadosDeCuenta);

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

				self.actualizarPendientes();

				var sparks = $('#sparks')
				var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];

				ko.utils.arrayForEach(self.estadosDeCuenta(), function(t) {

					t.listaCargos='<table>';
					ko.utils.arrayForEach(t.cargos, function(b) {
						var c = new CargoDepartamento();
						c.cargarCargoDepartamento(b);
						if(c.saldoPendiente()>0) {
							t.listaCargos = ((t.listaCargos ? t.listaCargos : '') + '<tr><td class="font-xs" style="padding-right: 20px">' + b.concepto + '</td><td class="text-align-right font-xs"> $' + numeral(c.saldoPendiente()).format('0,0.00') + '</td></tr>');
						}
					});
					t.listaCargos=(t.listaCargos + '</table>');

					var li = document.createElement("li");
					var h5 = document.createElement("h5");
					var span = document.createElement("span");
					var a = document.createElement("a");
					var div = document.createElement("div");

					$(li).addClass("sparks-info");
					$(span).addClass("txt-color-blue font-md");
					$(a).addClass("txt-color-blue");
					if(t.saldoFavorAlDia) {
						$(a).html('$'+numeral(t.saldoDeudor).format('0,0.00') + '&nbsp;&nbsp;<div class="label label-success"> <i class="fa fa-plus-circle"></i> ' + numeral(t.saldoFavorAlDia).format('0,0.00') + '</div>');
					} else {
						$(a).html('$'+numeral(t.saldoDeudor).format('0,0.00'));
					}

					$(h5).html('Departamento ' + t.departamento.nombre);
					$(a).attr('rel','popover-hover');
					$(a).attr('data-placement','bottom');
					$(a).attr('data-original-title','Cargos Pendientes de Pago');
					$(a).attr('data-html','true');
					$(a).attr('data-content', t.listaCargos);
					$(a).css('cursor','pointer');
					$(a).popover({trigger: "hover"});
					$(div).addClass("sparkline txt-color-blue hidden-mobile hidden-md hidden-sm");
					$(div).attr('id', 'departamento'+ t.id);

					$(span).append($(a));
					$(h5).append($(span));
					$(li).append($(h5));
					$(li).append($(div));
					$(sparks).append($(li));

					/*$('#departamento'+ t.id).sparkline(values, {
						type: 'bar',
						height: '26px',
						width: '82px',
						barColor: '#57889c',
						tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
						tooltipValueLookups: {
							'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
						}
					});*/


				});

				/*if(self.estadosDeCuenta().length){
					var li = document.createElement("li");
					var h5 = document.createElement("h5");
					var span = document.createElement("span");
					var a = document.createElement("a");
					var div = document.createElement("div");

					$(li).addClass("sparks-info");
					$(span).addClass("txt-color-green font-md");
					$(a).addClass("txt-color-green");
					$(a).html('$'+numeral(self.estadosDeCuenta()[0].saldoFavor).format('0,0.00'));
					$(h5).html('Mi Saldo a Favor');
					$(a).popover({trigger: "hover"});
					$(div).addClass("sparkline txt-color-green hidden-mobile hidden-md hidden-sm");
					$(div).attr('id', 'saldoFavor');

					$(span).append($(a));
					$(h5).append($(span));
					$(li).append($(h5));
					$(li).append($(div));
					$(sparks).append($(li));

					$('#saldoFavor').sparkline(values, {
						type: 'bar',
						height: '26px',
						width: '82px',
						barColor: '#496949',
						tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
						tooltipValueLookups: {
							'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
						}
					});
				}*/
			}
		});
	}

	self.verPost = function(data) {
		location.href = contextPath + "/contacto/blogs/temas/actualizar/" + data.id;
	}



}
