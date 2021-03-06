<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.*" %> 
<head>
	<title><spring:message code="mihabitat.menu.visitas"/> | <spring:message code="mihabitat.nombre"/></title>
	

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
				<h2><spring:message code="mihabitat.menu.visitas"/></h2>

			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-departamentos" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.nombre" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.fechaentrada" />" />
							   </th>
							   <th class="hasinput" style="width: 15%; ">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.fechasalida" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.departamento" />" />
							   </th>				   
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.placas" />" />
							   </th>
							   <th class="hasinput" style="width: 15%;">
							    <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.visitas.status" />" />
							   </th>
							   <th class="hasinput" style="width: 10%;">
							   </th>
							</tr>					  
							<tr>
								<th data-class="expand">
								   <spring:message code="mihabitat.menu.visitas.nombre" />
								</th>
								<th data-class="expand">
								   <spring:message code="mihabitat.menu.visitas.fechaentrada" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.visitas.fechasalida" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.visitas.departamento" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.visitas.placas" />
								</th>
								<th data-hide="phone">
								   <spring:message code="mihabitat.menu.visitas.status" />
								</th>
								<th style="text-align: center;">
								   <i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
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
								<td style="text-align: center;">
									<input hidden="true" type="text" id="${item.nombre} ${item.apPaterno} ${item.apMaterno}">
									<button class="btn btn-success btn-xs">
										<i class="fa fa-pencil"></i> Registrar Salida </button>
									<input hidden="true" type="text" id="${item.idArrendador}">
								</td>
																
							</tr>	
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<form name="myform" id="myform" action="${pageContext.request.contextPath}/vigilante/visitantes/actualizar" method = "POST">
<input type="hidden" id="idVisitante" name="idVisitante" value="0" />
<input type="hidden" id="nombreCompleto" name="nombreCompleto" value="0"/>
<input type="submit" hidden="true">
</form>

	<script type="text/javascript">
	$('.form-control').keyup(function(){
		 var val=$(this).val();    
		        $('table tbody tr').hide();
		         var trs=$('table tbody tr').filter(function(d){
		         return $(this).text().toLowerCase().indexOf(val)!=-1;
		         });
		         trs.show();   
		});
	
	$('button').on('click', function(){
		  var id= $(this).next('input').attr('id');
		  var nombre = $(this).prev('input').attr('id');
	      document.getElementById('idVisitante').value = id;
	      document.getElementById('nombreCompleto').value = nombre;
	      if (confirm("Desea registrar Salida de "+nombre)) {
	    	  var myform = document.getElementById('myform');
		      myform.submit();
	    	}       
	});
	</script>
</body>