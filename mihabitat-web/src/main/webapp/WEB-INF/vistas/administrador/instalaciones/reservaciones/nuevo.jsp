<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.instalaciones.reservaciones"/> | <spring:message code="mihabitat.nombre"/></title>
	<link rel="canonical" href="http://codepen.io/mrsafraz/pen/uIrwC">
	<link rel="stylesheet prefetch" href="${pageContext.request.contextPath}/recursos/css/knockout-file-bindings.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/dhtmlxscheduler/dhtmlxscheduler.css" type="text/css" media="screen" title="no title" charset="utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/dhtmlxscheduler/calendarioreservaciones.css" type="text/css" media="screen" title="no title" charset="utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/bootstrap-datetimepicker.min.css" type="text/css" media="screen" title="no title" charset="utf-8">

</head>
<body >
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/instalaciones/lista">
					<spring:message code="mihabitat.menu.instalaciones"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/instalaciones/reservaciones/lista">
					<spring:message code="mihabitat.menu.instalaciones.reservaciones"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/instalaciones/reservaciones/nuevo">
					<spring:message code="mihabitat.menu.nuevo"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content" >
		<jsp:include page="../../../instalaciones/reservaciones/reservacion.jsp" />
	</div>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-file-bindings.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.iframe-transport.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.fileupload.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/clockpicker/clockpicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_units.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_limit.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_serialize.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_all_timed.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_collision.js" type="text/javascript" charset="utf-8"></script>
	<script src='${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/ext/dhtmlxscheduler_timeline.js' type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/dhtmlxScheduler_v4.3.1/codebase/locale/locale_es.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/locale/locales.js" type="text/javascript" charset="utf-8"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/reservacion.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/disponibilidad.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion-app.js?v=${project-version}"></script>
	<script type="text/javascript" >
		$(function() {
			var viewModel = new ReservarInstalacionViewModel({
				contactos: ${contactos},
				estatus: ${estatus},
				instalacion : ${instalacion},
				rol: 'administrador',
				condominio: {
					id : ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);

			$("#reservacion-form").validate();
			$("#contacto").select2();
			$("#departamento").select2();
			$("#calendarLightBox").draggable({
				handle: ".modal-header"
			});
			$( window ).resize(function() {
				if($( window ).width() >= 320 && $( window ).width() <= 479) {
					$('.dhx_cal_tab').css('width', '30px');
					$('[name="week_tab"]').css('left', '45px');
					$('[name="week_tab"]').html('Sem');
					$('[name="month_tab"]').css('left', '76px');

					$('.dhx_cal_today_button').css('width', '40px');
					$('.dhx_cal_today_button').css('right', '83px');

					$('.dhx_cal_prev_button').css('width', '30px');
					$('.dhx_cal_prev_button').css('right', '45px');
					$('.dhx_cal_next_button').css('width', '30px');

					$('.dhx_cal_date').css('top', '44px');
					$('.dhx_cal_header').css('top', '75px');
					$('.dhx_cal_data').css('top', '96px');
				} else {
					$('.dhx_cal_tab').css('width', '60px');
					$('[name="week_tab"]').css('left', '75px');
					$('[name="week_tab"]').html('Semana');
					$('[name="month_tab"]').css('left', '136px');

					$('.dhx_cal_today_button').css('width', '80px');
					$('.dhx_cal_today_button').css('right', '123px');

					$('.dhx_cal_prev_button').css('width', '46px');
					$('.dhx_cal_prev_button').css('right', '60px');
					$('.dhx_cal_next_button').css('width', '46px');

					$('.dhx_cal_date').css('top', '14px');
					$('.dhx_cal_header').css('top', '60px');
					$('.dhx_cal_data').css('top', '81px');
				}
			});
			/*
			 $.validator.addMethod("aceptaReglamento",function(value, element, params){
			 if(!value){
			 notificacionAdvertencia("Debe aceptar los terminos y condiciones del reglamento de uso.");
			 }
			 return value;
			 }, "Debe aceptar los terminos de uso.");
			 */
		});
	</script>
</body>