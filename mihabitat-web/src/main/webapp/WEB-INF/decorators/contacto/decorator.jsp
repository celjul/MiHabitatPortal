<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html>
<html>
    <head>
    	<title><decorator:title default="miHabitat"/></title>
        <jsp:include page="../comunes/comunesEstilos.jsp" />
        <decorator:head />
    </head>
	<body class="desktop-detected pace-done estilo-mihabitat fixed-header fixed-navigation">
	<header id="header">
            <div id="logo-group">
                <span id="logo"> <img src="${pageContext.request.contextPath}/recursos/img/logo.png" alt="<spring:message code="mihabitat.nombre" />"> </span>
            </div>
           <div> <input list="browsers" placeholder= "Buscar en el menú" name="chooseOptions" class="form-control-2">

<datalist id="browsers">
  <option id="contacto/mis-pagos/nuevo" value="<spring:message code="mihabitat.menu.nuevo.pagos" />">
  <option id="contacto/mis-pagos/nuevo" value="<spring:message code="mihabitat.menu.contacto.abonos.pagar" />">
  <option id="contacto/mis-pagos/lista" value="<spring:message code="mihabitat.menu.contacto.abonos.ver" />">
  <option id="contacto/mis-pagos/lista" value="<spring:message code="mihabitat.menu.contacto.abonos.listade.pagos" />">
   <option id="contacto/mis-reservaciones/lista" value="<spring:message code="mihabitat.menu.instalaciones.lista" />">
   <option id="contacto/mis-reservaciones/lista" value="<spring:message code="mihabitat.menu.instalaciones.reservar" />">
  <option id="contacto/mis-estados-cuenta/consulta" value="<spring:message code="mihabitat.menu.contacto.estadodecuenta.ver" />">
  <option id="contacto/blogs/7/temas/lista" value="<spring:message code="mihabitat.menu.comunicacion.listadeproyectose.incidencias" />">
  <option id="contacto/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevoproyecto.incidencia" />">
  <option id="contacto/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevotema" />">
  <option id="contacto/blogs/7/temas/nuevo" value="<spring:message code="mihabitat.menu.comunicacion.registrarnuevo.evento" />">
   <option id="contacto/blogs/4/temas/lista" value="<spring:message code="mihabitat.menu.comunicacion.aviso.verlista" />">
  <option id="contacto/directorio/lista" value="<spring:message code="mihabitat.menu.directorio" />">
  <option id="contacto/blogs/lista" value="<spring:message code="mihabitat.menu.blogs" />">
    <option id="contacto/arrendamiento/lista" value="<spring:message code="mihabitat.menu.filtro.registrarnuevo.arrendatario" />">
  <option id="contacto/arrendamiento/lista" value="<spring:message code="mihabitat.menu.arrendatario.lista" />">
  
</datalist></div>
            <div class="pull-right">
                <div id="hide-menu" class="btn-header pull-right">
                    <span> <a href="javascript:void(0);" data-action="toggleMenu" title="<spring:message code="mihabitat.menu.colapasar" />"><i class="fa fa-reorder"></i></a> </span>
                </div>
                <ul id="mobile-profile-img" class="header-dropdown-list hidden-xs padding-5">
                    <li class="">
                        <a href="#" class="dropdown-toggle no-margin userdropdown"> 
                            <img src="${pageContext.request.contextPath}/recursos/img/avatars/male.png" alt="${usuario.persona.nombre}" class="online" />  
                        </a>
                    </li>
                </ul>
                <div id="logout" class="btn-header transparent pull-right">
                    <span> <a href="<c:url value="/j_spring_security_logout"/>" title="<spring:message code="mihabitat.menu.salir" />" data-action="userLogout" data-logout-msg="<spring:message code="mihabitat.menu.salir.confirmar" />"><i class="fa fa-sign-out"></i></a> </span>
                </div>
                <%--<div id="search-mobile" class="btn-header transparent pull-right">
                    <span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
                </div>
                <form action="#" class="header-search pull-right">
                    <input id="search-fld"  type="text" name="param" placeholder="<spring:message code="mihabitat.menu.buscar" />">
                    <button type="submit">
                        <i class="fa fa-search"></i>
                    </button>
                    <a href="javascript:void(0);" id="cancel-search-js" title="<spring:message code="mihabitat.menu.buscar.cancelar" />"><i class="fa fa-times"></i></a>
                </form>--%>
                <div id="fullscreen" class="btn-header transparent pull-right">
                    <span> <a href="javascript:void(0);" data-action="launchFullscreen" title="<spring:message code="mihabitat.menu.pantallacompleta" />"><i class="fa fa-arrows-alt"></i></a> </span>
                </div>
                <c:if test="${esadministrador}">
                    <div id="esadministrador" class="btn-header transparent pull-right">
                        <span> <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}" title="<spring:message code="mihabitat.menu.comoadministrador" />"><i class="fa fa-sliders"></i></a> </span>
                    </div>
                </c:if>
                <c:choose>
	                <c:when test="${not empty condominio.id}">
	                    <ul class="header-dropdown-list">
	                        <li>
	                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
	                                <i class="fa fa-building"></i> <span class="hidden-xs"> ${condominio.nombre} </span><i class="fa fa-angle-down"></i>
	                            </a>
	                            <ul class="dropdown-menu pull-right">
	                                <li class="active">
	                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                                        <i class="fa fa-building"></i> <span> ${condominio.nombre} </span><i class="fa fa-angle-down"></i>
	                                    </a>
	                                </li>
	                                <c:forEach items="${condominios}" var="c">
	                                    <c:if test="${c.id ne condominio.id}">
	                                        <li>
	                                            <a href="${pageContext.request.contextPath}/contacto/inicio?condominio=${c.id}"><i class="fa fa-building"></i>${c.nombre}
	                                            </a>
	                                        </li>
	                                    </c:if>
	                                </c:forEach>
	                            </ul>
	                        </li>
	                    </ul>
	                </c:when>
	                <c:otherwise>
	                    <spring:message code="mihabitat.contacto.sindepartamento" />
	                </c:otherwise>
                </c:choose>
            </div>
        </header>
        <aside id="left-panel">
            <div class="login-info">
                <span>
                    <a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
                        <img src="${pageContext.request.contextPath}/recursos/img/avatars/male.png" alt="${usuario.user}" class="online" /> 
                        <span>
                           ${usuario.persona.nombre} ${usuario.persona.apellidoPaterno}
                        </span>
                        <i class="fa fa-angle-down"></i>
                    </a> 
                </span>
            </div>
            <nav>
                <ul>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/contacto/inicio?departamento=${departamento.id}"><i class="fa fa-lg fa-fw fa-building"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.inicio"/></span></a>
                    </li>                  
                    <c:choose>
	                    <c:when test="${not empty condominio.id}">
	                       <li>
	                          <a href="#"><i class="fa fa-lg fa-fw fa-money"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.pagos"/></span></a>
	                          <ul>
	                              <li>
	                                  <a href="${pageContext.request.contextPath}/contacto/mis-pagos/nuevo"><spring:message code="mihabitat.menu.nuevo.pagos"/></a>
	                              </li>
	                              <li>
	                                  <a href="${pageContext.request.contextPath}/contacto/mis-pagos/lista"><spring:message code="mihabitat.menu.lista.pagos"/></a>
	                              </li>
	                          </ul>
	                      </li>
                        <li>
                            <a href="#"><i class="fa fa-lg fa-fw fa-bookmark"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.instalaciones"/></span></a>
                            <ul>
                                <li>
                                    <a href="${pageContext.request.contextPath}/contacto/mis-reservaciones/lista"><spring:message code="mihabitat.menu.reservar.instalaciones"/></a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-lg fa-fw fa-file-text"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.estadocuenta"/></span></a>
                            <ul>
                                <li>
                                    <a href="${pageContext.request.contextPath}/contacto/mis-estados-cuenta/consulta"><spring:message code="mihabitat.menu.estadocuenta.consulta"/></a>
                                </li>
                            </ul>
                        </li>
                            <li>
                                <a href="#"><i class="fa fa-lg fa-fw fa-comments"></i> <span class="menu-item-parent"><spring:message code="mihabitat.menu.comunicacion"/></span></a>
                                <ul>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/contacto/blogs/7/temas/lista">
                                            <i class="fa fa-fw fa-stack-overflow"></i>
                                            <spring:message code="mihabitat.menu.incidencias"/>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/contacto/blogs/4/temas/lista">
                                            <i class="fa fa-fw fa-bullhorn"></i>
                                            <spring:message code="mihabitat.menu.blogs.avisos"/>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/contacto/directorio/lista">
                                            <i class="fa fa-fw fa-book"></i>
                                            <spring:message code="mihabitat.menu.directorio"/>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/contacto/blogs/lista">
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
                    		<a href="${pageContext.request.contextPath}/contacto/arrendamiento/nuevo">
                    		<spring:message code="mihabitat.menu.arrendatario.nuevo"/></a>
                    	<li>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/contacto/arrendamiento/lista">
                    		<spring:message code="mihabitat.menu.arrendatario.lista"/></a>
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
            <jsp:include page="../comunes/comunesJS.jsp" />
            <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/pais.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/estado.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/municipio.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/colonia.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app/direcciones/direccion.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app/numeral.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app.config.js?v=${project-version}"></script>
            <script src="${pageContext.request.contextPath}/recursos/js/app.min.js?v=${project-version}"></script>

            <%--SCRIPTS PARA LAS NOTIFICACIONES--%>
            <script src="${pageContext.request.contextPath}/recursos/js/app/notificaciones/notificacion-app.js?v=${project-version}"></script>
            <script  src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/sock.js?v=${project-version}"></script>
            <script  src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/stomp.js?v=${project-version}"></script>
            <script type="text/javascript">
                var notifiacionesApp = new FuncionalidadNotificacionApp({});
                notifiacionesApp.obtenerNotificaciones();

                $(function(){
                    $('.activity').click(function(e) {
                        var $this = $(this);

                        if ($this.find('.badge').hasClass('bg-color-red')) {
                            $this.find('.badge').removeClassPrefix('bg-color-');
                            $this.find('.badge').text("0");
                        }

                        if (!$this.next('.ajax-dropdown').is(':visible')) {
                            $this.next('.ajax-dropdown').css("left",-86+($this.position().left-83));
                            $this.next('.ajax-dropdown').css("top",47);
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

                    var stompClient = null;
                    var socket = new SockJS('/portfolio');
                    stompClient = Stomp.over(socket);
                    stompClient.connect({},function(frame){
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
                                $.each(data,function(i,e){
                                    stompClient.subscribe("/topic/notificaciones/"+e, function(notificacion){
                                        notificacionInfo(JSON.parse(notificacion.body),'${rol}');
                                        notifiacionesApp.obtenerNotificaciones();
                                    });
                                });
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                console.log("Ocurrio un error al suscribirse a las notificaciones");
                            }
                        });
                    });

                    function sendname() {
                        var name = document.getElementById('name').value;
                        $.ajax({
                            async: true,
                            cache: false,
                            url: contextPath + "/notificaciones/prueba",
                            type: 'POST',
                            data: name,
                            success: function (data) {
                                notificacionExito('Se enviÃ³...');
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                notificacionError("Ocurrio un error al recuperar las notificaciones");
                            }
                        });
                    }



                    // close dropdown if mouse is not inside the area of .ajax-dropdown
                    $(document).mouseup(function(e) {
                        if (!$('.ajax-dropdown').is(e.target) && $('.ajax-dropdown').has(e.target).length === 0) {
                            $('.ajax-dropdown').fadeOut(150);
                            $('.ajax-dropdown').prev().removeClass("active");
                        }
                    });

                    // loading animation (demo purpose only)
                    $('button[data-btn-loading]').on('click', function() {
                        var btn = $(this);
                        btn.button('loading');
                        setTimeout(function() {
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
                });
            </script>
            <%--FIN DE SCRIPTS DE NOTIFICACIONES--%>
            <decorator:body />
        </div>
         <div class="page-footer">
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <span class="txt-color-white"><spring:message code="mihabitat.nombre"/> | <spring:message code="mihabitat.titulo"/></span>
                </div>
            </div>
            <div class="scroll-top-wrapper">
				<i class="fa fa-2x fa-arrow-circle-up"></i>		
			</div>
        </div>
        
        <div id="shortcut">
			<ul> 
				<li>
                	<a href="${pageContext.request.contextPath}/mi-usuario/password" class="jarvismetro-tile big-cubes bg-color-blue"> 
                	<span class="iconbox"> <i class="fa fa-lock fa-4x"></i> 
                	<span class="menu-item-parent"><spring:message code="mihabitat.menu.miusuario.password"/></span></span></a>  
                </li>
			</ul>
		</div>
    
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
    </body>
</html>
