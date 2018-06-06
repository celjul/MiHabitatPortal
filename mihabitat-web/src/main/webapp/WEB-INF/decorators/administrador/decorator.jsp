<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html>
<html>
<head>
    <title><decorator:title default="miHabitat"/></title>
    <jsp:include page="../comunes/comunesEstilos.jsp"/>
    <decorator:head/>
</head>
<body class="desktop-detected pace-done estilo-mihabitat fixed-header fixed-navigation">
<header id="header">
    <div id="logo-group">
        <span id="logo"> <img src="${pageContext.request.contextPath}/recursos/img/logo.png"
                              alt="<spring:message code="mihabitat.nombre" />"> </span>
    </div>
     <div> 
     <input list="browsers" placeholder ="Buscar en el menú" name="chooseOptions" class="form-control-2">
	<datalist id="browsers">
  		<option id="administrador/mantenimientos/lista" value="<spring:message code="mihabitat.menu.mantenimientos" />">
  		<option id="administrador/grupos/lista" value="<spring:message code="mihabitat.menu.grupos" />">
  		<option id="administrador/cuentas/lista" value="<spring:message code="mihabitat.menu.cuentas" />">
   	<option id="administrador/consumos/lista" value="<spring:message code="mihabitat.menu.consumos" />">
  	<option id="administrador/condominios/configuracion" value="<spring:message code="mihabitat.menu.configuracion.condominio.mensajespdf" />">
  	<option id="administrador/confnotificaciones/lista" value="<spring:message code="mihabitat.menu.notificaciones.configuracion" />">
   	<option id="administrador/departamentos/nuevo" value="<spring:message code="mihabitat.menu.nuevo.departamento" />">
  	<option id="administrador/departamentos/lista" value="<spring:message code="mihabitat.menu.lista.departamentos" />">
  	<option id="administrador/contactos/lista" value="<spring:message code="mihabitat.menu.lista.contactos" />">
  	<option id="administrador/cargos-departamentos/nuevo" value="<spring:message code="mihabitat.menu.cargos.nuevo" />">
  	<option id="administrador/cargos-departamentos/lista" value="<spring:message code="mihabitat.menu.cargos.lista" />">
  	<!--- <option id="administrador/mantenimientos/lista" value="<spring:message code="mihabitat.menu.cargos.lista" />"> -->
  	<option id="administrador/cargos-recurrentes/lista" value="<spring:message code="mihabitat.menu.cargos.recurrentes" />">
  	<option id="administrador/pagos/nuevo" value="<spring:message code="mihabitat.menu.nuevo.pagos" />">
  	<option id="administrador/pagos/lista" value="<spring:message code="mihabitat.menu.lista.pagos" />"> 
  	<option id="administrador/otrosingresos/nuevo" value="<spring:message code="mihabitat.menu.filtro.registrar.otrosingresos" />">
  	<option id="administrador/otrosingresos/lista" value="<spring:message code="mihabitat.menu.lista.otrosingresos" />">
  	
  	<option id="administrador/ingresosnoidentificados/nuevo" value="<spring:message code="mihabitat.menu.filtro.registrarnuevo.ingreso.noidentificado" />">
  	<option id="administrador/ingresosnoidentificados/lista" value="<spring:message code="mihabitat.menu.filtro.listadeingresosnoidentidficados" />">
  	<option id="administrador/facturasporpagar/nuevo" value="<spring:message code="mihabitat.menu.nuevo.facturasporpagar" />">
  	<option id="administrador/facturasporpagar/cargar" value="<spring:message code="mihabitat.menu.cargar.facturasporpagar" />">
  	<option id="administrador/facturasporpagar/lista" value="<spring:message code="mihabitat.menu.lista.facturasporpagar" />">
  	<option id="administrador/pago-proveedores/nuevo" value="<spring:message code="mihabitat.menu.proveedores.pagos" />">
  	<option id="administrador/gastos/nuevo" value="<spring:message code="mihabitat.menu.nuevo.gastos" />">
  	<option id="administrador/gastos/lista" value="<spring:message code="mihabitat.menu.lista.gastos" />">
  	<option id="administrador/transferencias/nuevo" value="<spring:message code="mihabitat.transferencia.nuevo" />">
  	<option id="administrador/transferencias/lista" value="<spring:message code="mihabitat.transferencia.lista" />">
  	<option id="administrador/aviso-cobro/individual" value="<spring:message code="mihabitat.menu.avisodecobro.ver" />">
  	<option id="administrador/aviso-cobro/masivo" value="<spring:message code="mihabitat.menu.avisodecobro.observar" />">
  	<option id="administrador/estado-cuenta/individual" value="<spring:message code="mihabitat.estadocuenta.menu.depto" />">
  	<option id="administrador/estado-cuenta/masivo" value="<spring:message code="mihabitat.estadocuenta.menu.deptos" />">
  	<option id="administrador/cobranza/consulta" value="<spring:message code="mihabitat.menu.cobranza" />">
  	<option id="administrador/instalaciones/nuevo" value="<spring:message code="mihabitat.menu.instalaciones.nueva" />">
  	  	<option id="administrador/instalaciones/lista" value="<spring:message code="mihabitat.menu.instalaciones.reservar" />">
  	  	<option id="administrador/instalaciones/lista" value="<spring:message code="mihabitat.menu.instalaciones.editar" />">
  	  	<option id="administrador/instalaciones/lista" value="<spring:message code="mihabitat.menu.instalaciones.lista" />">
  	
  	<option id="administrador/proveedores/nuevo" value="<spring:message code="mihabitat.menu.proveedor.registrarnuevo" />">
  	<option id="administrador/proveedores/lista" value="<spring:message code="mihabitat.menu.lista.proveedores" />">
  	<option id="administrador/reportes/ingresos-egresos" value="<spring:message code="mihabitat.reportes.ingresosyegresos" />">
  	<option id="administrador/reportes/ingresos-egresos" value="<spring:message code="mihabitat.menu.reporte.estadocuenta.condominio" />">
  	
 	<option id="administrador/reportes/cuenta-detalle" value="<spring:message code="mihabitat.reportes.cuentadetalle" />">
 	<option id="administrador/reportes/cuenta-detalle" value="<spring:message code="mihabitat.menu.reporte.movimientoporcuenta.detalle" />">
 	
  	<option id="administrador/reportes/resumen-cargos" value="<spring:message code="mihabitat.reportes.resumencargos" />">
  	<option id="administrador/reportes/resumen-cargos" value="<spring:message code="mihabitat.menu.reporte.detalledecargos" />">
  	
 	<option id="administrador/reportes/resumen-abonos" value="<spring:message code="mihabitat.reportes.resumenabonos" />">
 	 	<option id="administrador/reportes/resumen-abonos" value="<spring:message code="mihabitat.menu.reporte.detalledeabonos" />">
 	
 	<option id="administrador/reportes/cuentas-cobrar" value="<spring:message code="mihabitat.reportes.cuentasporcobrar" />">
 	<option id="administrador/reportes/cuentas-cobrar" value="<spring:message code="mihabitat.menu.reporte.cuentasporcobrar" />">
 	
 	<option id="administrador/reportes/saldo-departamento" value="<spring:message code="mihabitat.reportes.saldodepartamento" />">
 	 	<option id="administrador/reportes/saldo-departamento" value="<spring:message code="mihabitat.menu.reporte.saldopordepartamento" />">
 	
 	<option id="administrador/reportes/antiguedad-cuentas-cobrar" value="<spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar" />">
 	 	<option id="administrador/reportes/antiguedad-cuentas-cobrar" value="<spring:message code="mihabitat.menu.reporte.antiguedaddecuentasporcobrar" />">
 	
 	<option id="administrador/reportes/morosidad" value="<spring:message code="mihabitat.reportes.morosidad" />">
 		<option id="administrador/reportes/morosidad" value="<spring:message code="mihabitat.menu.reporte.morosidad" />">
 	
 	<option id="administrador/reportes/cumplimiento-efectividad-cobranza" value="<spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza" />">
 		<option id="administrador/reportes/cumplimiento-efectividad-cobranza" value="<spring:message code="mihabitat.menu.reporte.efectividaddecobranza" />">
  	
  	<option id="administrador/reportes/bancos-cajas" value="<spring:message code="mihabitat.reportes.bancos.cajas" />">
  	  	<option id="administrador/reportes/bancos-cajas" value="<spring:message code="mihabitat.menu.reporte.debancosycajas" />">
  	
   	<option id="administrador/reportes/balanza-comprobacion" value="<spring:message code="mihabitat.reportes.balanza.comprobacion" />">
   		<option id="administrador/reportes/balanza-comprobacion" value="<spring:message code="mihabitat.menu.reporte.balanzadecomprobación" />">
   		
 	<option id="administrador/reportes/pagos-contacto" value="<spring:message code="mihabitat.reportes.pagos.contacto" />"> 
 		<option id="administrador/reportes/pagos-contacto" value="<spring:message code="mihabitat.menu.reporte.abonosxcondomino" />">
 		
  	<option id="administrador/reportes/pagos-departamento" value="<spring:message code="mihabitat.reportes.pagos.departamento" />">
  		<option id="administrador/reportes/pagos-departamento" value="<spring:message code="mihabitat.menu.reporte.abonosxdepto" />">
  		
 	<option id="administrador/reportes/saldo-favor" value="<spring:message code="mihabitat.reportes.saldofavor" />">
 		<option id="administrador/reportes/saldo-favor" value="<spring:message code="mihabitat.menu.reporte.saldoafavor" />">
 	
 	<option id="administrador/reportes/cuentas-pagar" value="<spring:message code="mihabitat.reportes.cuentasporpagar" />">
 	 	<option id="administrador/reportes/cuentas-pagar" value="<spring:message code="mihabitat.menu.reporte.cuentasxpagar" />"> 
 	 
 	<option id="administrador/reportes/antiguedad-cuentas-pagar" value="<spring:message code="mihabitat.reportes.antiguedadcuentasporpagar" />"> 
 		<option id="administrador/reportes/antiguedad-cuentas-pagar" value="<spring:message code="mihabitat.menu.reporte.antiguedaddecuentasporpagar" />">
 		 
	<option id="administrador/reportes/reporteegresos" value="<spring:message code="mihabitat.reportes.gastos" />">  		  		  			
		<option id="administrador/reportes/reporteegresos" value="<spring:message code="mihabitat.menu.reporte.degastos" />">
		
 	<option id="administrador/blogs/7/temas/lista" value="<spring:message code="mihabitat.menu.incidencias" />">
	 	<option id="administrador/blogs/7/temas/lista" value="<spring:message code="mihabitat.menu.comunicacion.listadeproyectose.incidencias" />"> 
 		<option id="administrador/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevoproyecto.incidencia" />">
 		<option id="administrador/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevotema" />">
 		<option id="administrador/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevo.evento" />">
 		
  	<option id="administrador/blogs/4/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.aviso" />">
  	<option id="administrador/blogs/4/temas/lista" value="<spring:message code="mihabitat.menu.comunicacion.aviso.verlista" />">
  	 
 	<option id="administrador/directorio/lista" value="<spring:message code="mihabitat.menu.directorio" />"> 
  	<option id="administrador/blogs/lista" value="<spring:message code="mihabitat.menu.blogs" />"> 
   	<option id="administrador/arrendamiento/nuevo" value="<spring:message code="mihabitat.menu.filtro.registrarnuevo.arrendatario" />"> 
   	<option id="administrador/arrendamiento/lista" value="<spring:message code="mihabitat.menu.arrendatario.lista" />"> 			  	
 	<option id="administrador/visitantes/nuevo" value="<spring:message code="mihabitat.menu.visitas.nuevo" />">
 	<option id="administrador/visitantes/nuevo" value="<spring:message code="mihabitat.menu.filtro.visitante" />"> 
 	<option id="administrador/visitantes/lista" value="<spring:message code="mihabitat.menu.visitas.lista" />">
 	
 	<!--- <option id="administrador/visitantes/lista" value="<spring:message code="mihabitat.menu.votaciones.lista" />"> -->
 	<!--- <option id="administrador/visitantes/lista" value="<spring:message code="mihabitat.menu.votaciones.nueva" />"> -->
  		  
	</datalist>
	</div>
    <div id="hide-menu" class="btn-header pull-left">
            <span> <a href="javascript:void(0);" data-action="toggleMenu"
                      title="<spring:message code="mihabitat.menu.colapasar" />"><i
                    class="fa fa-reorder"></i></a> </span>
    </div>
    <div class="pull-right">

        <ul id="mobile-profile-img" class="header-dropdown-list hidden-xs padding-5">
            <li class="">
                <a href="#" class="dropdown-toggle no-margin userdropdown">
                    <img src="${pageContext.request.contextPath}/recursos/img/avatars/male.png"
                         alt="${usuario.persona.nombre} ${usuario.persona.apellidoPaterno}" class="online"/>
                </a>
            </li>
        </ul>
        <div id="logout" class="btn-header transparent pull-right">
					<span>
						<a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
                            <i class="fa fa-chevron-down"></i>
                        </a>
					</span>
        </div>
        <div id="search-mobile" class="btn-header transparent pull-right">
            <span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
        </div>
        <form action="#" class="header-search pull-right">
            <input type="text" name="busquedaPrincipal" id="busquedaPrincipal"
                   placeholder="<spring:message code="mihabitat.menu.buscar" />"
                   maxlength="128">
            <button type="submit">
                <i class="fa fa-search"></i>
            </button>
            <a href="javascript:void(0);" id="cancel-search-js"
               title="<spring:message code="mihabitat.menu.buscar.cancelar" />"><i class="fa fa-times"></i></a>
        </form>
        <c:if test="${escontacto}">
            <div id="comocondomino" class="btn-header transparent pull-right">
                <span> <a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${condominio.id}"
                          title="<spring:message code="mihabitat.menu.comocondomino" />"><i class="fa fa-user"></i></a> </span>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${not empty condominio.id}">
                <ul class="header-dropdown-list">
                    <li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-building"></i> <span class="hidden-xs"> ${condominio.nombre} </span> <i
                                class="fa fa-angle-down"></i>
                        </a>
                        <ul class="dropdown-menu pull-right">
                            <li class="active">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-building"></i> <span> ${condominio.nombre} </span> <i
                                        class="fa fa-angle-down"></i>
                                </a>
                            </li>
                            <c:forEach items="${condominios}" var="c">
                                <c:if test="${c.id ne condominio.id}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${c.id}">
                                            <i class="fa fa-building"></i>${c.nombre}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
                <spring:message code="mihabitat.administrador.sincondominio"/>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<aside id="left-panel">
    <div class="login-info">

				<span>
					<%--<a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
						<img src="${pageContext.request.contextPath}/recursos/img/avatars/male.png" alt="${usuario.user}" class="online" /> 
						<span>
							${usuario.persona.nombre} ${usuario.persona.apellidoPaterno}
						</span>
						<i class="fa fa-angle-down"></i>
					</a>--%>
					<ul>
                        <li class="dropdown"
                            style="list-style-type: none; left: 0px;padding-left: 15px;position: absolute">
                            <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Dropdown <span class="caret"></span></a>--%>
                            <a href="javascript:void(0);" data-toggle="dropdown" aria-expanded="false">
                                <img src="${pageContext.request.contextPath}/recursos/img/avatars/male.png"
                                     alt="${usuario.user}" class="online"/>
								<span>
									${usuario.persona.nombre} ${usuario.persona.apellidoPaterno}
								</span>
                                <i class="fa fa-angle-down"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                <%--<li>
                                    <a href="#">Perfil</a>
                                </li>--%>
                                <li class="divider"></li>
                                <li>
                                    <a href="<c:url value="/j_spring_security_logout"/>"
                                       title="<spring:message code="mihabitat.menu.salir" />" data-action="userLogout"
                                       data-logout-msg="<spring:message code="mihabitat.menu.salir.confirmar" />">
                                        <i class="fa fa-sign-out"></i>
                                        Salir
                                    </a>
                                </li>
                                <!--<li>
                                    <a href="${pageContext.request.contextPath}/administrador/confnotificaciones/enviarPrueba">Test SendinBlue</a>
                                </li>-->
                            </ul>
                        </li>
                    </ul>
				</span>
    </div>
    <nav>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}"><i
                        class="fa fa-lg fa-fw fa-building"></i> <span class="menu-item-parent"><spring:message
                        code="mihabitat.menu.inicio"/></span></a>
            </li>

            <c:choose>
                <c:when test="${not empty condominio.id}">
                    <li>
                        <a href="#"><i class="fa fa-lg fa-fw fa-gear"></i> <span
                                class="menu-item-parent"><spring:message
                                code="mihabitat.menu.configuracion"/></span></a>
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/mantenimientos/lista"><i
                                        class="fa fa-fw fa-tasks"></i> <spring:message
                                        code="mihabitat.menu.mantenimientos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/grupos/lista"><i
                                        class="fa fa-fw fa-tags"></i> <spring:message code="mihabitat.menu.grupos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/cuentas/lista"><i
                                        class="fa fa-fw fa-clipboard"></i> <spring:message
                                        code="mihabitat.menu.cuentas"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/consumos/lista"><i
                                        class="fa fa-fw fa-dashboard"></i> <spring:message
                                        code="mihabitat.menu.consumos"/></a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-gear"></i> <spring:message
                                        code="mihabitat.menu.configuracion.condominio"/></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/condominios/configuracion"><spring:message
                                                code="mihabitat.menu.configuracion.condominio.mensajespdf"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/confnotificaciones/lista"><spring:message
                                                code="mihabitat.menu.notificaciones.configuracion"/></a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-lg fa-fw fa-th-large"></i> <span
                                class="menu-item-parent"><spring:message
                                code="mihabitat.menu.departamentos"/></span></a>
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/departamentos/nuevo"
                                   id="departamentos-nuevo"><spring:message
                                        code="mihabitat.menu.nuevo.departamento"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/departamentos/lista"
                                   id="departamentos-lista"><spring:message
                                        code="mihabitat.menu.lista.departamentos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/contactos/lista"
                                   id="contactos-lista"><spring:message code="mihabitat.menu.lista.contactos"/></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-lg fa-fw fa-dollar"></i> <span
                                class="menu-item-parent"><spring:message
                                code="mihabitat.menu.gestionfinanciera"/></span></a>
                        <ul>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-download"></i> <span
                                        class="menu-item-parent"><spring:message code="mihabitat.menu.ingresos"/></span></a>
                                <ul>
                                    <li>
                                        <a href="#"><i class="fa fa-lg fa-fw fa-edit"></i> <span
                                                class="menu-item-parent"><spring:message
                                                code="mihabitat.menu.cargos"/></span></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/nuevo"
                                                   id="cargos-departamento-nuevo"> <spring:message
                                                        code="mihabitat.menu.cargos.nuevo"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/lista"
                                                   id="cargos-departamento-lista"> <spring:message
                                                        code="mihabitat.menu.cargos.lista"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/mantenimientos/lista"
                                                   id="mantenimientos-configuracion"> <spring:message
                                                        code="mihabitat.menu.mantenimientos.configuracion"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/cargos-recurrentes/lista"
                                                   id="cargos-recurrentes-lista"> <spring:message
                                                        code="mihabitat.menu.cargos.recurrentes"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#"><i class="fa fa-lg fa-fw fa-money"></i> <span
                                                class="menu-item-parent"><spring:message
                                                code="mihabitat.menu.pagos"/></span></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/pagos/nuevo"><spring:message
                                                        code="mihabitat.menu.nuevo.pagos"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/pagos/lista"><spring:message
                                                        code="mihabitat.menu.lista.pagos"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#"><i class="fa fa-lg fa-fw fa-plus"></i> <spring:message
                                                code="mihabitat.menu.otrosingresos"/></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/otrosingresos/nuevo"><spring:message
                                                        code="mihabitat.menu.nuevo.otrosingresos"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/otrosingresos/lista"><spring:message
                                                        code="mihabitat.menu.lista.otrosingresos"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#"><i class="fa fa-lg fa-fw fa-question"></i> <spring:message
                                                code="mihabitat.menu.ingresosnoidentificados"/></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/ingresosnoidentificados/nuevo"><spring:message
                                                        code="mihabitat.menu.nuevo.ingresosnoidentificados"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/ingresosnoidentificados/lista"><spring:message
                                                        code="mihabitat.menu.lista.ingresosnoidentificados"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-upload"></i> <span
                                        class="menu-item-parent"><spring:message code="mihabitat.menu.egresos"/></span></a>
                                <ul>
                                    <li>
                                        <a href="#"><i class="fa fa-fw fa-inbox"></i> <spring:message
                                                code="mihabitat.menu.facturasporpagar"/></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/facturasporpagar/nuevo"><spring:message
                                                        code="mihabitat.menu.nuevo.facturasporpagar"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/facturasporpagar/cargar"><spring:message
                                                        code="mihabitat.menu.cargar.facturasporpagar"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/facturasporpagar/lista"><spring:message
                                                        code="mihabitat.menu.lista.facturasporpagar"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/pago-proveedores/nuevo"><spring:message
                                                        code="mihabitat.menu.proveedores.pagos"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#"><i class="fa fa-fw fa-minus"></i> <spring:message
                                                code="mihabitat.gastos"/></a>
                                        <ul>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/gastos/nuevo"><spring:message
                                                        code="mihabitat.menu.nuevo.gastos"/></a>
                                            </li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/administrador/gastos/lista"><spring:message
                                                        code="mihabitat.menu.lista.gastos"/></a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-exchange"></i> <span
                                        class="menu-item-parent"><spring:message
                                        code="mihabitat.transferencias"/></span></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/transferencias/nuevo">
                                            <spring:message code="mihabitat.transferencia.nuevo"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/transferencias/lista"><spring:message
                                                code="mihabitat.transferencia.lista"/></a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a href="#"><i class="fa fa-lg fa-fw fa-suitcase"></i> <span
                                class="menu-item-parent"><spring:message
                                code="mihabitat.menu.gestionadministrativa"/></span></a>
                        <ul>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-file-text"></i> <spring:message
                                        code="mihabitat.menu.avisodecobro"/></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/aviso-cobro/individual"><spring:message
                                                code="mihabitat.menu.avisodecobro.individual"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/aviso-cobro/masivo"><spring:message
                                                code="mihabitat.menu.avisodecobro.masivo"/></a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-file-text"></i> <spring:message
                                        code="mihabitat.menu.estadocuenta"/></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/estado-cuenta/individual"><spring:message
                                                code="mihabitat.menu.estadocuenta.individual"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/estado-cuenta/masivo"><spring:message
                                                code="mihabitat.menu.estadocuenta.masivo"/></a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/cobranza/consulta"><i
                                        class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span
                                        class="menu-item-parent"><spring:message code="mihabitat.menu.cobranza"/></span></a>

                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-bookmark"></i> <span
                                        class="menu-item-parent"><spring:message
                                        code="mihabitat.menu.instalaciones"/></span></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/instalaciones/nuevo"><spring:message
                                                code="mihabitat.menu.nuevo.instalaciones"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/instalaciones/lista"><spring:message
                                                code="mihabitat.menu.lista.instalaciones"/></a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-truck"></i> <span
                                        class="menu-item-parent"><spring:message
                                        code="mihabitat.menu.proveedores"/></span></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/proveedores/nuevo"><spring:message
                                                code="mihabitat.menu.nuevo.proveedores"/></a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/administrador/proveedores/lista"><spring:message
                                                code="mihabitat.menu.lista.proveedores"/></a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-fw fa-file-excel-o"></i> <span
                                class="menu-item-parent"><spring:message code="mihabitat.reportes"/></span></a>
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/ingresos-egresos/"><spring:message
                                        code="mihabitat.reportes.ingresosyegresos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/cuenta-detalle/"><spring:message
                                        code="mihabitat.reportes.cuentadetalle"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/resumen-cargos/"><spring:message
                                        code="mihabitat.reportes.resumencargos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/resumen-abonos/"><spring:message
                                        code="mihabitat.reportes.resumenabonos"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/cuentas-cobrar/"><spring:message
                                        code="mihabitat.reportes.cuentasporcobrar"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/saldo-departamento/"><spring:message
                                        code="mihabitat.reportes.saldodepartamento"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/antiguedad-cuentas-cobrar/"><spring:message
                                        code="mihabitat.reportes.antiguedadcuentasporcobrar"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/morosidad/"><spring:message
                                        code="mihabitat.reportes.morosidad"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/cumplimiento-efectividad-cobranza/"><spring:message
                                        code="mihabitat.reportes.cumplimiento.efectividad.cobranza"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/bancos-cajas/"><spring:message
                                        code="mihabitat.reportes.bancos.cajas"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/balanza-comprobacion/"><spring:message
                                        code="mihabitat.reportes.balanza.comprobacion"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/pagos-contacto/"><spring:message
                                        code="mihabitat.reportes.pagos.contacto"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/pagos-departamento/"><spring:message
                                        code="mihabitat.reportes.pagos.departamento"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/saldo-favor/"><spring:message
                                        code="mihabitat.reportes.saldofavor"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/cuentas-pagar/"><spring:message
                                        code="mihabitat.reportes.cuentasporpagar"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/antiguedad-cuentas-pagar/"><spring:message
                                        code="mihabitat.reportes.antiguedadcuentasporpagar"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/reportes/reporteegresos/"><spring:message
                                        code="mihabitat.reportes.gastos"/></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-lg fa-fw fa-comments"></i> <span
                                class="menu-item-parent"><spring:message code="mihabitat.menu.comunicacion"/></span></a>
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/blogs/7/temas/lista">
                                    <i class="fa fa-fw fa-stack-overflow"></i>
                                    <spring:message code="mihabitat.menu.incidencias"/>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/blogs/4/temas/lista">
                                    <i class="fa fa-fw fa-bullhorn"></i>
                                    <spring:message code="mihabitat.menu.blogs.avisos"/>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/directorio/lista">
                                    <i class="fa fa-fw fa-book"></i>
                                    <spring:message code="mihabitat.menu.directorio"/>
                                </a>
                            </li>
                     <!--       <li>
                             <a href="#"><i class="fa fa-line-chart"></i>
                    	<span class="menu-item-parent"><spring:message code="mihabitat.menu.votaciones"/></span>
                    	</a>
                    	<ul>
                    

                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/votaciones/nuevo">
                    		<spring:message code="mihabitat.menu.votaciones.nueva"/></a>
                    	</li>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/votaciones/lista">
                    		<spring:message code="mihabitat.menu.votaciones.lista"/></a>
                    	</li>
                    	</ul>
                    </li> 
                     -->
                            <li>
                                <a href="${pageContext.request.contextPath}/administrador/blogs/lista">
                                    <i class="fa fa-fw fa-comments-o"></i>
                                    <spring:message code="mihabitat.menu.blogs"/>
                                </a>
                                    <%--<ul>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/contacto/blogs/lista"><spring:message code="mihabitat.menu.lista"/></a>
                                        </li>
                                    </ul>--%>
                            </li>
                        </ul>
                    </li>
                    <li>
                    	<a href="#"><i class="fa fa-lg fa-fw fa-hotel"></i>
                    	<span class="menu-item-parent"><spring:message code="mihabitat.menu.arrendatario"/></span>
                    	</a>
                    	<ul>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/arrendamiento/nuevo">
                    		<spring:message code="mihabitat.menu.arrendatario.nuevo"/></a>
                    	<li>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/arrendamiento/lista">
                    		<spring:message code="mihabitat.menu.arrendatario.lista"/></a>
                    	<li>
                    	</ul>
                    </li>
                    <li>
                    	<a href="#"><i class="fa fa-lg fa-fw fa-bell"></i>
                    	<span class="menu-item-parent"><spring:message code="mihabitat.menu.visitas"/></span>
                    	</a>
                    	<ul>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/visitantes/nuevo">
                    		<spring:message code="mihabitat.menu.visitas.nuevo"/></a>
                    	<li>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/administrador/visitantes/lista">
                    		<spring:message code="mihabitat.menu.visitas.lista"/></a>
                    	<li>
                    	</ul>
                    </li>
                    </c:when>
            </c:choose>
        </ul>
    </nav>
			<span class="minifyme" data-action="minifyMenu">
				<i class="fa fa-arrow-circle-left hit"></i>
			</span>
</aside>
<div id="main" role="main">
    <jsp:include page="../comunes/comunesJS.jsp"/>

    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app.min.js?v=${project-version}"></script>

    <%--SCRIPTS PARA LAS NOTIFICACIONES--%>
    <script src="${pageContext.request.contextPath}/recursos/js/app/notificaciones/notificacion-app.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/sock.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/stomp.js?v=${project-version}"></script>

    <script type="text/javascript">
        var notifiacionesApp = new FuncionalidadNotificacionApp({});
        notifiacionesApp.obtenerNotificaciones();

        $(function () {
            $('.activity').click(function (e) {
                var $this = $(this);

                if ($this.find('.badge').hasClass('bg-color-red')) {
                    $this.find('.badge').removeClassPrefix('bg-color-');
                    $this.find('.badge').text("0");
                }

                if (!$this.next('.ajax-dropdown').is(':visible')) {
                    $this.next('.ajax-dropdown').css("left", -86 + ($this.position().left - 83));
                    $this.next('.ajax-dropdown').css("top", 47);
                    $this.next('.ajax-dropdown').fadeIn(150);
                    $this.addClass('active');
                } else {
                    $this.next('.ajax-dropdown').fadeOut(150);
                    $this.removeClass('active');
                }

                var theUrlVal = $this.next('.ajax-dropdown').find('.btn-group > .active > input').attr('id');

                //clear memory reference
                $this = null;
                theUrlVal = null;

                e.preventDefault();
            });

            $('#busquedaPrincipal').keyup(function (e) {
                var $this = $(this);
                var val = $('#busquedaPrincipal').val();

                $("#busquedaPrincipal").autocomplete({
                    minLength: 1,
                    source: function (request, response) {
                        $.ajax({
                            url: contextPath + "/administrador/busqueda/buscar",
                            dataType: "json",
                            data: {
                                key: val
                            },
                            success: function (data) {
                                response($.map(data, function (item) {
                                    return {
                                        id: item.id,
                                        label: item.label,
                                        url: item.url
                                    };
                                }));
                            },
                            global: false
                        });
                    },
                    select: function (event, ui) {
                        /*self.item.id( ui.item.id );
                         self.item.label( ui.item.label );
                         self.item.url( ui.item.url );
                         self.obtenerEstadoDeCuenta();*/
                        location.href = contextPath + ui.item.url;
                        return false;
                    }
                });
            });

            var stompClient = null;
            var socket = new SockJS('/portfolio');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                $.ajax({
                    async: true,
                    cache: false,
                    url: contextPath + "/notificaciones/canales",
                    type: 'GET',
                    dataType: 'json',
                    data: '',
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        $.each(data, function (i, e) {
                            stompClient.subscribe("/topic/notificaciones/" + e, function (notificacion) {
                                notificacionInfo(JSON.parse(notificacion.body), '${rol}');
                                notifiacionesApp.obtenerNotificaciones();
                            });
                        });
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("Ocurrio un error al suscribirse a las notificaciones");
                    },
                    global: false
                });
            });

            // close dropdown if mouse is not inside the area of .ajax-dropdown
            $(document).mouseup(function (e) {
                if (!$('.ajax-dropdown').is(e.target) && $('.ajax-dropdown').has(e.target).length === 0) {
                    $('.ajax-dropdown').fadeOut(150);
                    $('.ajax-dropdown').prev().removeClass("active");
                }
            });

            // loading animation (demo purpose only)
            $('button[data-btn-loading]').on('click', function () {
                var btn = $(this);
                btn.button('loading');
                setTimeout(function () {
                    btn.button('reset');
                }, 3000);
            });

            // NOTIFICATION IS PRESENT
            // Change color of lable once notification button is clicked

            $this = $('#activity > .badge');

            if (parseInt($this.text()) > 0) {
                $this.addClass("bg-color-red bounceIn animated");

                //clear memory reference
                $this = null;
            }

            $("#show-shortcut").click(function () {
                {
                    /*$("#shortcut").css("display","block");*/
                    function a() {
                        $("#shortcut").animate({height: "hide"}, 300, "easeOutCirc"), $("#shortcut").css("display", "none!important")
                    }

                    function b() {
                        $("#shortcut").animate({height: "show"}, 200, "easeOutCirc"), $("#shortcut").css("display", "block!important")
                    }

                    $("#shortcut").is(":visible") ? a() : b(), $("#shortcut").find("a").click(function (b) {
                        b.preventDefault(), window.location = $(this).attr("href"), setTimeout(a, 300)
                    }), $(document).mouseup(function (b) {
                        $("#shortcut").is(b.target) || 0 !== $("#shortcut").has(b.target).length || a()
                    })
                }

            });
        });
    </script>
    <%--FIN DE SCRIPTS DE NOTIFICACIONES--%>
    <decorator:body/>
</div>
<div class="page-footer">
    <div class="row">
        <div class="col-xs-12 col-sm-10">
            <span class="txt-color-white"><spring:message code="mihabitat.nombre"/> | <spring:message
                    code="mihabitat.titulo"/></span>
        </div>
        <div class="scroll-top-wrapper">
				 	<span class="scroll-top-inner">
						<i class="fa fa-2x fa-arrow-circle-up"></i>
					</span>
        </div>
    </div>
</div>
<!-- SHORTCUT AREA -->

<div id="shortcut">
    <ul>
        <li>
            <a href="/administrador/cargos-departamentos/nuevo" class="jarvismetro-tile big-cubes bg-color-orange">
                <span class="iconbox"> <i class="fa fa-edit fa-4x"></i> <span> <spring:message
                        code="mihabitat.menu.cargos.nuevo"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/cargos-departamentos/lista" class="jarvismetro-tile big-cubes bg-color-orange">
                <span class="iconbox"> <i class="fa fa-list fa-4x"></i> <span> <spring:message
                        code="mihabitat.menu.cargos.lista"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/pagos/nuevo" class="jarvismetro-tile big-cubes bg-color-red"> <span class="iconbox"> <i
                    class="fa fa-money fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.nuevo.pagos"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/pagos/lista" class="jarvismetro-tile big-cubes bg-color-red"> <span class="iconbox"> <i
                    class="fa fa-list fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.lista.pagos"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/gastos/nuevo" class="jarvismetro-tile big-cubes bg-color-redLight"> <span
                    class="iconbox"> <i class="fa fa-arrow-circle-up fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.nuevo.gastos"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/gastos/lista" class="jarvismetro-tile big-cubes bg-color-redLight"> <span
                    class="iconbox"> <i class="fa fa-list fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.lista.gastos"/> </span> </span> </a>
        </li>
        <li class="hidden-mobile">
            <a href="/administrador/facturasporpagar/cargar" class="jarvismetro-tile big-cubes bg-color-purple"> <span
                    class="iconbox"> <i
                    class="fa fa-file-code-o fa-4x"></i> <span> Cargar Facturas por Pagar </span> </span> </a>
        </li>
        <li class="hidden-mobile">
            <a href="/administrador/facturasporpagar/lista" class="jarvismetro-tile big-cubes bg-color-purple"> <span
                    class="iconbox"> <i class="fa fa-list fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.lista.facturasporpagar"/> </span> </span> </a>
        </li>
        <li class="hidden-mobile">
            <a href="/administrador/pago-proveedores/nuevo" class="jarvismetro-tile big-cubes bg-color-purple"> <span
                    class="iconbox"> <i class="fa fa-arrow-circle-right fa-4x"></i> <span> <spring:message
                    code="mihabitat.menu.proveedores.pagos"/> </span> </span> </a>
        </li>
        <%--<li>
            <a href="/administrador/estado-cuenta/individual" class="jarvismetro-tile big-cubes bg-color-greenLight"> <span class="iconbox"> <i class="fa fa-book fa-4x"></i> <span> <spring:message code="mihabitat.menu.estadocuenta"/> </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/cobranza/consulta" class="jarvismetro-tile big-cubes bg-color-blueDark"> <span class="iconbox"> <i class="fa fa-file-text fa-4x"></i> <span> <spring:message code="mihabitat.menu.cobranza"/> <span class="label pull-right bg-color-darken">99</span></span> </span> </a>
        </li>
        <li>
            <a href="/administrador/instalaciones/lista" class="jarvismetro-tile big-cubes bg-color-green"> <span class="iconbox"> <i class="fa fa-picture-o fa-4x"></i> <span> <spring:message code="mihabitat.menu.lista.instalaciones"/>  </span> </span> </a>
        </li>
        <li>
            <a href="/administrador/blogs/lista" class="jarvismetro-tile big-cubes bg-color-pinkDark"> <span class="iconbox"> <i class="fa fa-comments fa-4x"></i> <span> <spring:message code="mihabitat.menu.blogs"/> </span> </span> </a>
        </li>--%>
    </ul>
</div>
<div class="scroll-top-wrapper">
			<span class="scroll-top-inner">
				<i class="fa fa-2x fa-arrow-circle-up"></i>
			</span>
</div>


<%--<div id="shortcut">
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/administrador/perfil/${usuario.user}" data-toggle="modal" data-target="#myModal" class="jarvismetro-tile big-cubes selected bg-color-pinkDark">
                <span class="iconbox"> <i class="fa fa-user fa-4x"></i>
                    <span><spring:message code="mihabitat.usuarios.perfil"/></span>
                </span>
            </a>
        </li>
    </ul>
</div>--%>
<!-- END SHORTCUT AREA -->

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="modalTitle"><spring:message code="mihabitat.usuarios.perfil"/></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Title" required/>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" placeholder="Content" rows="5" required></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="category"> Category</label>
                            <select class="form-control" id="category">
                                <option>Articles</option>
                                <option>Tutorials</option>
                                <option>Freebies</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="tags"> Tags</label>
                            <input type="text" class="form-control" id="tags" placeholder="Tags"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="well well-sm well-primary">
                            <form class="form form-inline " role="form">
                                <div class="form-group">
                                    <input type="text" class="form-control" value="" placeholder="Date" required/>
                                </div>
                                <div class="form-group">
                                    <select class="form-control">
                                        <option>Draft</option>
                                        <option>Published</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success btn-sm">
                                        <span class="glyphicon glyphicon-floppy-disk"></span> Save
                                    </button>
                                    <button type="button" class="btn btn-default btn-sm">
                                        <span class="glyphicon glyphicon-eye-open"></span> Preview
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancel
                </button>
                <button type="button" class="btn btn-primary">
                    Post Article
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
</body>

<script type="text/javascript">
var requestContextPath = '${pageContext.request.contextPath}';
$(function()  {
	  $('input[name=chooseOptions]').on('input',function() {
		  
	    var selectedOption = $('option[value="'+$(this).val()+'"]');
	   	if(!(selectedOption.attr('id')==undefined)){
	   		location.href=requestContextPath+"/"+selectedOption.attr('id');}
	 	  });
	});
</script>
</html>
