<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.*" %> 
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
					<form action = "${pageContext.request.contextPath}/administrador/arrendamiento/guardar" onsubmit="return validateMyForm();" method = "POST" id="arrendatario-form" class="smart-form">
						<fieldset>
							<section class="row">
									<section class="col col-md-3" class="form-group">
										<label class="label">
											<span class="error-required">*</span>
										<spring:message code="mihabitat.persona.nombre" />
										</label> 
										<label class="input"> 
											<input 
												id="nombre"
												class="form-control"	
												type="text" 
												name="nombre" 
												placeholder="<spring:message code="mihabitat.persona.nombre" />"
												required="required" 
												maxlength="128">
											<!-- data-bind="value: $root.contacto.persona.nombre, disable: $root.usuario.id()">-->
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label"> 
											<span class="error-required">*</span>
											<spring:message	code="mihabitat.persona.apellidoPaterno" />
										</label> 
										<label class="input"> 
											<input 
												id="apellidoPaterno"
												class="form-control"
												type="text" 
												name="apellidoPaterno"
												placeholder="<spring:message code="mihabitat.persona.apellidoPaterno" />"
												required="required" 
												maxlength="128">
												<!--data-bind="value: $root.contacto.persona.apellidoPaterno, disable: $root.usuario.id()">-->
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
										<label class="label"><spring:message code="mihabitat.persona.apellidoMaterno" /></label> 
										<label class="input"> 
											<input 
												id="apellidoMaterno"
												class="form-control"
												type="text" 
												name="apellidoMaterno"
												placeholder="<spring:message code="mihabitat.persona.apellidoMaterno" />"
												maxlength="128">
												<!-- data-bind="value: $root.contacto.persona.apellidoMaterno, disable: $root.usuario.id()">-->
										</label>
									</section>
									<section class="col col-md-3" class="form-group">
									</section>
								</section>
							<section class="row">
									<section class="col col-3">
									<label class="label"><spring:message code="mihabitat.menu.arrendamiento.fechaentrada"/></label>
									 <div class="input-group date" data-provide="datepicker">
    									<input id="fechaEntrada" name="fechaEntrada" required="required" type="date" class="form-control">
   	 										<div class="input-group-addon">
        										<span class="glyphicon glyphicon-th"></span>
   											 </div>
									</div>
									</section>
									<section class="col col-3">
									<label class="label"><spring:message code="mihabitat.menu.arrendamiento.fechasalida"/></label>
									 <div class="input-group date" data-provide="datepicker">
    									<input id="fechaSalida" name ="fechaSalida" required="required" type="date" class="form-control">
   	 										<div class="input-group-addon">
        										<span class="glyphicon glyphicon-th"></span>
   											 </div>
									</div>
									</section>
									<section class="col col-3">
										<label class="label"><span class="error-required">*</span><spring:message code="mihabitat.menu.arremdamiento.condominio" /></label> 
									  
									    <input list="departamentos" class="form-control" name="chooseOption" required="required">
 										 <datalist id="departamentos">
										<c:forEach var="departamento" items="${departamentos}">
										 	<option id="${departamento.id}" value="${departamento.nombre}"></option>
										</c:forEach>
									 </datalist>
									 <input type="hidden" value="-1" name="id_departamento" id="id_departamento">
									</section>
							</section>
							<section class="row">
							</section>
							<section class="row">
								<section class="col col-md-2" class="form-group">
									<label class="label">
											<span class="error-required">*</span>
										<spring:message code="mihabitat.menu.arremdamiento.adultos" />
										</label> 
										<label class="input"> 
											<input 
												id="numAdultos"
												class="form-control"	
												type="number" 
												name="numAdultos" 
												value="1"
												required="required" >
											<!-- data-bind="value: $root.contacto.persona.nombre, disable: $root.usuario.id()">-->
										</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label">
											<span class="error-required">*</span>
										<spring:message code="mihabitat.menu.arremdamiento.ninos" />
										</label> 
										<label class="input"> 
											<input 
												id="numNinos"
												class="form-control"	
												type="number" 
												name="numNinos" 
												value="0"
												required="required" >
											<!-- data-bind="value: $root.contacto.persona.nombre, disable: $root.usuario.id()">-->
										</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label">
										<spring:message code="mihabitat.menu.arremdamiento.placas" />
										</label> 
										<label class="input"> 
											<input 
												id="placas"
												name="placas"
												class="form-control"	
												type="text" 
												name="nombre" 
												maxlength="10"
												>
										</label>
								</section>
															
							</section>
						</fieldset>
						<footer>
							<button type="submit" id="botonRegistro" class="btn btn-primary"> 
									<spring:message code="mihabitat.botones.guardar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>
<script type="text/javascript">
function validateMyForm()
{
	var today = new Date();
	var hoy = new Date();
	hoy.setDate(hoy.getDate()-1);
	var dateA = document.getElementById("fechaSalida").value;
	var dateB = document.getElementById("fechaEntrada").value;
	var depa =document.getElementById('id_departamento').value;
	var inputSalida = new Date(dateA);
	var inputEntrada = new Date(dateB);
	if (inputSalida < today) {
		notificacionAdvertencia("Fecha de salida debe ser mayor a hoy");
		    return false;
	} else if (inputEntrada < hoy) {
		notificacionAdvertencia("Fecha de entrada debe ser mayor o igual a hoy");
		    return false;
	  } else if ( inputEntrada > inputSalida) {
		  notificacionAdvertencia("Fecha de salida debe ser mayor a fecha entrada");
		    return false;
	  }else if(depa==-1){ notificacionAdvertencia("Departamento no valido");
	    return false;}
    return true;
}

$(function() {
	  $('input[name=chooseOption]').on('input',function() {
	    var selectedOption = $('option[value="'+$(this).val()+'"]');
	   	if(selectedOption.attr('id')==undefined){document.getElementById('id_departamento').value = -1;}
	   	else{document.getElementById('id_departamento').value = selectedOption.attr('id');}
	 	  });
	});
	
$(document).ready(function(){
	if(${statusguardado==1}){notificacionExito('Se registró con exito');}
	else if(${statusguardado==2}){notificacionError('Hubo un error al guardar el registro');}
	
});

</script>