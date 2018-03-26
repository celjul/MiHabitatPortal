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
    <div id="hide-menu" class="btn-header pull-left">
            <span> <a href="javascript:void(0);" data-action="toggleMenu"
                      title="<spring:message code="mihabitat.menu.colapasar" />"><i
                    class="fa fa-reorder"></i></a> </span>
    </div>
	<div>
	<input list="browsers" name="chooseOptions" class="form-control-2">

<datalist id="browsers">
  <option id="vigilante/visitantes/lista" value="<spring:message code="mihabitat.menu.visitas.lista" />">
  <option id="vigilante/visitantes/nuevo" value="<spring:message code="mihabitat.menu.visitas.nuevo" />">
  <option id="vigilante/arrendamiento/lista" value="<spring:message code="mihabitat.menu.arrendatario.lista" />">
</datalist>
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
					
        </div>
        <div id="search-mobile" class="btn-header transparent pull-right">
            <span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
        </div>
        <c:if test="${escontacto}">
            <div id="comocondomino" class="btn-header transparent pull-right">
                <span> <a href="${pageContext.request.contextPath}/vigilante/inicio?condominio=${condominio.id}"
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
                                        <a href="${pageContext.request.contextPath}/vigilante/inicio?condominio=${c.id}">
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
                               <li class="divider"></li>
                                <li>
                                    <a href="<c:url value="/j_spring_security_logout"/>"
                                       title="<spring:message code="mihabitat.menu.salir" />" data-action="userLogout"
                                       data-logout-msg="<spring:message code="mihabitat.menu.salir.confirmar" />">
                                        <i class="fa fa-sign-out"></i>
                                        Salir
                                    </a>
                                </li>
                             
                            </ul>
                        </li>
                    </ul>
				</span>
    </div>
    <nav>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/vigilante/inicio?condominio=${condominio.id}"><i
                        class="fa fa-lg fa-fw fa-building"></i> <span class="menu-item-parent"><spring:message
                        code="mihabitat.menu.inicio"/></span></a>
            </li>

            <c:choose>
                <c:when test="${not empty condominio.id}">
                    <li>
                    	<a href="#"><i class="fa fa-lg fa-fw fa-hotel"></i>
                    	<span class="menu-item-parent"><spring:message code="mihabitat.menu.arrendatario"/></span>
                    	</a>
                    	<ul>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/vigilante/arrendamiento/lista">
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
                    		<a href="${pageContext.request.contextPath}/vigilante/visitantes/nuevo">
                    		<spring:message code="mihabitat.menu.visitas.nuevo"/></a>
                    	<li>
                    	<li>
                    		<a href="${pageContext.request.contextPath}/vigilante/visitantes/lista">
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

<!-- Menu Dinamico Scripts -->
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

</body>
</html>
