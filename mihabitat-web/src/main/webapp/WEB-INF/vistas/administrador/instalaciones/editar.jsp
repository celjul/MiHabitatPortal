<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.instalaciones"></spring:message> | <spring:message code="mihabitat.nombre"/></title>
	<link rel="canonical" href="http://codepen.io/mrsafraz/pen/uIrwC">
	<link rel="stylesheet prefetch" href="${pageContext.request.contextPath}/recursos/css/knockout-file-bindings.css">
</head>
<body>
	<div id="ribbon">
        <ol class="breadcrumb">
           <li>
				<a href="${pageContext.request.contextPath}/administrador/inicio">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/administrador/instalaciones/lista">
					<spring:message code="mihabitat.menu.instalaciones"/>
				</a>
			</li>
        </ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
	<div id="content">
        <jsp:include page="instalacion.jsp" />
    </div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/clockpicker/clockpicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-file-bindings.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.iframe-transport.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.fileupload.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/archivo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/disponibilidad.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/instalaciones/instalacion-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function(){
			var viewModel = new InstalacionViewModel({
				cuentas : ${cuentas},
				unidades : ${unidades},
				instalacion : ${instalacion},
				condominio: {
					id : ${condominio.id}
				}
			});
			ko.applyBindings(viewModel);

			$("#instalacion-form").validate();
			$("#cuenta").select2();


			$("#fecha").datepicker({
				minDate: '0' });

			inicializarRelojes();

			$.validator.addMethod("horaPosterior",function(value, element, params){
				if (params && $.trim($(params).val()) != "" && $.trim(value) != "") {
					var arrInicial = $(params).val().split(":");
					var arrFinal = value.split(":");

					/*var hInicial = new Date(parseInt(arrInicial[0]), parseInt(arrInicial[1]) - 1, parseInt(arrInicial[2]));
					 var hFinal = new Date(parseInt(arrFinal[0]), parseInt(arrFinal[1]) - 1, parseInt(arrFinal[2]));*/

					return arrInicial[0]+arrInicial[1] <= arrFinal[0]+arrFinal[1];
				} else return true;
			}, "La hora Final debe ser mayor a la hora Inicial");
			$.validator.addMethod("horaAnterior",function(value, element, params){
				if (params && $.trim($(params).val()) != "" && $.trim(value) != "") {
					var arrInicial = $(params).val().split(":");
					var arrFinal = value.split(":");

					/*var hInicial = new Date(parseInt(arrInicial[0]), parseInt(arrInicial[1]) - 1, parseInt(arrInicial[2]));
					 var hFinal = new Date(parseInt(arrFinal[0]), parseInt(arrFinal[1]) - 1, parseInt(arrFinal[2]));*/

					return arrInicial[0]+arrInicial[1] >= arrFinal[0]+arrFinal[1];
				} else return true;
			}, "La hora Inicial debe ser menor a la hora Final");

		});
	</script>
</body>