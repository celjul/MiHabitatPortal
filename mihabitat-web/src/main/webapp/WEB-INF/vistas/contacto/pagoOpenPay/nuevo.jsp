<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.*" %> 
<head>
	<title><spring:message code="mihabitat.menu.arrendatario"/> | <spring:message code="mihabitat.nombre"/></title>
	  <script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
  <script type="text/javascript"
        src="https://openpay.s3.amazonaws.com/openpay.v1.min.js"></script>
<script type='text/javascript'
  src="https://openpay.s3.amazonaws.com/openpay-data.v1.min.js"></script>
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${condominio.id}">
					<spring:message code="mihabitat.menu.inicio"/>
				</a>
			 </li>
			<li>
				<a href="#">
					<spring:message code="mihabitat.menu.arrendatario"/>
				</a>
			</li>
			<li>
				<a href="#">
					<spring:message code="mihabitat.menu.arrendatario.editar"/>
				</a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">

<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div 
			class="jarviswidget" 
			id="wid-id-3"	
			data-widget-colorbutton="false" 
			data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> 
					<i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.arrendatario" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form action = "${pageContext.request.contextPath}/contacto/pagoOpenPay/pagar" method = "POST" id="payment-form" class="smart-form">
					 <input type="hidden" name="token_id" id="token_id">
					 <input type="hidden" name="deviceSessionId" id="deviceSessionId">
						<fieldset>
							<section class="row">
							<section class="col col-3">
										<label class="label">Departamento</label> 
									  <input class="form-control" list="departamentos" name="chooseOption" required="required">
 										 <datalist id="departamentos">
										<c:forEach var="item" items="${items}">
										 	<option id="${item.id}" value="${item.nombre}"></option>
										</c:forEach>
									 </datalist>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label"><span class="error-required">*</span>Nombre Titular</label> 
										<label class="input"> 
											<input 
											id="txt_nombretitular"
											name="txt_nombretitular"
												data-openpay-card="holder_name"
												class="form-control"	
												type="text" 
												placeholder="Nombre Titular"
												required="required" 
												maxlength="128">
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label"><span class="error-required">*</span>Apellido Titular</label> 
										<label class="input"> 
											<input 
											id="txt_apellidotitular"
											name="txt_apellidotitular"
												class="form-control"	
												type="text" 
												placeholder="Nombre Titular"
												required="required" 
												maxlength="128">
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label"><span class="error-required">*</span>Numero Tarjeta></label> 
										<label class="input"> 
											<input 
											data-openpay-card="card_number"
												class="form-control"
												type="text" 
												placeholder="Numero Tarjeta"
												required="required" 
												maxlength="16">
										</label>
									</section>
								</section>
							<section class="row">
							<section class="col col-md-3" class="form-group">
										<label class="label">Monto</label> 
										<label class="input"> 
											<input 
												name="txtMonto"
												class="form-control"
												type="number" 
												placeholder="Monto">
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label">CVV</label> 
										<label class="input"> 
											<input 
											data-openpay-card="cvv2"
												class="form-control"
												type="text" 
												placeholder="CVV"
												maxlength="3">
										</label>
									</section>
																		<section class="col col-md-3" class="form-group">
										<label class="label">Mes Expiracion</label> 
										<label class="input"> 
											<input 
											data-openpay-card="expiration_month"
												class="form-control"
												type="text" 
												placeholder="MM"
												maxlength="2">
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label">Año Expiracion</label> 
										<label class="input"> 
											<input 
											data-openpay-card="expiration_year"
												class="form-control"
												type="text" 
												placeholder="YY"
												maxlength="2">
										</label>
									</section>									
							</section>
							<section class="row">
							</section>
							
						</fieldset>
						<footer>
							
							<button type="button" id="pay-button" class="btn btn-primary"> 
									<spring:message code="mihabitat.botones.guardar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>
</div>

<script type="text/javascript">
 $(document).ready(function() {
  OpenPay.setId('mz8cpzsq1p5nyvv1tyny');
  OpenPay.setApiKey('pk_040e1117777644bcb65135ff424a2305');
  OpenPay.setSandboxMode(true);
  var deviceSessionId = OpenPay.deviceData.setup("payment-form", "deviceIdHiddenFieldName");
  $('#deviceSessionId').val(deviceSessionId);
  });
 
 $('#pay-button').on('click', function(event) {
     event.preventDefault();
     $("#pay-button").prop( "disabled", true);
     OpenPay.token.extractFormAndCreate('payment-form', success_callbak, error_callbak);              

});
 
 var success_callbak = function(response) {
     var token_id = response.data.id;
     $('#token_id').val(token_id);
     $('#payment-form').submit();
};

var error_callbak = function(response) {
    var desc = response.data.description != undefined ?
       response.data.description : response.message;
    alert("ERROR [" + response.status + "] " + desc);
    $("#pay-button").prop("disabled", false);
};
</script>

</body>