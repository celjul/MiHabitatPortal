<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.*" %> 
<head>
	<title><spring:message code="mihabitat.menu.arrendatario.lista"/> | <spring:message code="mihabitat.nombre"/></title>
	

</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb">
			<li>
			   <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
				   <spring:message code="mihabitat.menu.inicio"/>
			   </a>
			 </li>
			<li>
			   <a href="${pageContext.request.contextPath}/administrador/arrendamiento/lista">
				   <spring:message code="mihabitat.menu.arrendatario.lista"/>
			   </a>
			</li>
		</ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-table"></i> </span>
				<h2><spring:message code="mihabitat.menu.arrendatario.lista"/></h2>

			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<table id="table-departamentos" class="table table-striped table-bordered table-hover" style="width: 100%">
						<thead>
							<tr>
							   <th class="hasinput" style="width: 15%;">
								   <input type="text" class="form-control" placeholder="<spring:message code="mihabitat.menu.arremdamiento.nombre" />" />
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
								   <spring:message code="mihabitat.menu.arremdamiento.nombre" />
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



									<div class="btn-group">
										<a class="btn btn-default btn-xs" href="javascript:void(0);"><i class="fa fa-cog"></i></a>
										<a class="btn btn-default dropdown-toggle btn-xs" data-toggle="dropdown" href="javascript:void(0);"><span class="caret"></span></a>
										<ul class="dropdown-menu dropdown-menu-right">
											<li>
												<button class="btn btn-link">
													<i class="fa fa-pencil"></i> Editar </button>
												<input hidden="false" type="text"  id="actualizarbutton" idArrendamiento="${item.idArrendador}">
											</li>
											<li>
												<button class="btn btn-link">
													<i class="fa fa-print"></i>Imprimir </button>
												<input hidden="false" type="text"  id="imprimirbutton" idArrendamiento="${item.idArrendador}">
											</li>
										</ul>
									</div>

								</td>
																
							</tr>	
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<form name="myform" id="myform" action="${pageContext.request.contextPath}/administrador/arrendamiento/actualizar" method = "POST">
<input type="hidden" id="idArrendatario" name="idArrendatario" value="0" />
<input type="submit" hidden="true">

</form>
	<form name="imprimir" id="imprimir" action="${pageContext.request.contextPath}/administrador/arrendamiento/imprimir" method = "get" target="_blank">
		<input type="hidden" id="idArrendatarioImpresion" name="idArrendatarioImpresion" value="0" />
		<input type="submit" hidden="true">
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
        var idArrendamiento = $(this).next('input').attr('idArrendamiento');
        if (id!='imprimirbutton') {
            var myform = document.getElementById('myform');
            document.getElementById('idArrendatario').value = idArrendamiento;
            myform.submit();

        } else{
            var imprimir = document.getElementById('imprimir');
            document.getElementById('idArrendatarioImpresion').value = idArrendamiento;
            imprimir.submit();

        }




	});
	</script>
	</form>
</body>