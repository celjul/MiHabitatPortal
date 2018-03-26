<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.*" %> 
<head>
	<title><spring:message code="mihabitat.menu.arrendatario"/> | <spring:message code="mihabitat.nombre"/></title>
	

</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
			   <a href="${pageContext.request.contextPath}/vigilante/inicio?condominio=${condominio.id}">
				   <spring:message code="mihabitat.menu.inicio"/>
			   </a>
			 </li>
		</ol>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.arrendatario"/></h2>

			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-departamentos" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arrendatario.nuevo" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arrendamiento.fechaentrada" />" />
							   </th>
							   <th class="hasinput" style="width: 15%; ">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arrendamiento.fechasalida" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arremdamiento.departamento" />" />
							   </th>				   
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arremdamiento.placas" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arremdamiento.status" />" />
							   </th>
							   <th class="hasinput" style="width: 10%;">
							   </th>
							</tr>					  
							<tr>
								<th data-class="expand">
								   <spring:message code="mihabitat.menu.arrendatario.nuevo" />
								</th>
								<th data-class="expand">
								   <spring:message code="mihabitat.menu.arrendamiento.fechaentrada" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.arrendamiento.fechasalida" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.arremdamiento.departamento" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.arremdamiento.placas" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.arremdamiento.status" />
								</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="item" items="${items}">
							<tr>
								<td><label>	${item.nombre} ${item.apPaterno} ${item.apMaterno}</label></td>
								<td><label> ${item.fechaEntrada}</label> </td>
								<td><label> ${item.fechaSalida}</label></td>
								<td><label> ${item.departamento.nombre}</label></td>
								<td><label> ${item.placas}</label></td>
								<td><label> ${item.idStatus.VDescripcion}</label></td>																
							</tr>	
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	</body>
	
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/sparkline/jquery.sparkline.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.cust.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.resize.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.time.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.tooltip.min.js"></script>
	<!-- Full Calendar -->
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/fullcalendar.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/lang-all.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/inicio/inicio-contacto-app.js?v=${project-version}"></script>
	<script>
		$(document).ready(function() {
			var viewModel = new InicioContactoViewModel({});
			ko.applyBindings(viewModel);
			viewModel.consulta();
			// DO NOT REMOVE : GLOBAL FUNCTIONS!
			pageSetUp();
			/*
			 * FULL CALENDAR JS
			 */

			if ($("#calendar").length) {
				var date = new Date();
				var d = date.getDate();
				var m = date.getMonth();
				var y = date.getFullYear();

				var calendar = $('#calendar').fullCalendar({

					editable : false,
					draggable : false,
					selectable : false,
					selectHelper : true,
					unselectAuto : false,
					disableResizing : false,
					eventLimit : 2,
					lang: 'es',
					height: 360,
					defaultTimedEventDuration: '01:00:00',

					header : {
						left : 'title', //,today
						center : 'prev, next, today',
						right : '' //month, agendaDay,
					},

					select : function(start, end, allDay) {
						var title = prompt('Event Title:');
						if (title) {
							calendar.fullCalendar('renderEvent', {
										title : title,
										start : start,
										end : end,
										allDay : allDay
									}, true // make the event "stick"
							);
						}
						calendar.fullCalendar('unselect');
					},

					/*events : viewModel.eventos(),*/


					eventRender : function(event, element, icon) {
						if (!event.description == "") {
							element.find('.fc-title').append("<br/><span class='ultra-light'>" + event.description + "</span>");
						}
						if (!event.icon == "") {
							element.find('.fc-title').append("<i class='air air-top-right fa " + event.icon + " '></i>");
						}
					},

					eventClick: function(calEvent, jsEvent, view) {

						/*alert('Event: ' + calEvent.title);
						 alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
						 alert('View: ' + view.name);*/

						notificacionInfoEvent(
								{
									titulo: calEvent.title,
									mensaje: calEvent.description,
									inicio: calEvent.start.format('YYYY/MM/DD hh:mm a'),
									fin: calEvent.end?calEvent.end.format('YYYY/MM/DD hh:mm a'):'',
									link: (calEvent.tipo=='evento'?'/blogs/temas/actualizar/'+calEvent.idReferencia:(calEvent.tipo=='cobranza'?'/cobranza/consulta':''))
								}, 'contacto'
						);

					}
				});

			};

			/* hide default buttons */
			$('.fc-header-right, .fc-header-center').hide();

			// calendar prev
			$('#calendar-buttons #btn-prev').click(function() {
				$('.fc-button-prev').click();
				return false;
			});

			// calendar next
			$('#calendar-buttons #btn-next').click(function() {
				$('.fc-button-next').click();
				return false;
			});

			// calendar today
			$('#calendar-buttons #btn-today').click(function() {
				$('.fc-button-today').click();
				return false;
			});

			// calendar month
			$('#mt').click(function() {
				$('#calendar').fullCalendar('changeView', 'month');
			});

			// calendar agenda week
			$('#ag').click(function() {
				$('#calendar').fullCalendar('changeView', 'agendaWeek');
			});

			// calendar agenda day
			$('#td').click(function() {
				$('#calendar').fullCalendar('changeView', 'agendaDay');
			});
			var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
			$("#bancosCajas").sparkline(values, {
				type: 'bar',
				height: '26px',
				width: '82px',
				barColor: '#a90329',
				tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
				tooltipValueLookups: {
					'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
				}
			});
		});
	</script>
</body>