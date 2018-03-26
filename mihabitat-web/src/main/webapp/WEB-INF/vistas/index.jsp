<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html id="extr-page">
<head>
<meta charset="utf-8">
<title><spring:message code="mihabitat.nombre" /> | <spring:message
		code="mihabitat.titulo" /></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">


<meta http-equiv="content-type" content="text/html">
<meta charset="utf-8">
<meta name="dc.language" scheme="rfc1766" content="Español">
<meta name="author" content="Red Rocket">
<meta name="reply-to" content="redrocketagenciadigital@gmail.com">
<meta name="resource-type" content="document">
<meta name="revisit-after" content="10 days">
<meta name="robots" content="all">


<%--<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/smartadmin-production.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/smartadmin-skins.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/recursos/css/your_style.css">--%>

<%--<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/recursos/img/favicon/favicon.ico"
	type="image/x-icon">
<link rel="icon"
	href="${pageContext.request.contextPath}/recursos/img/favicon/favicon.ico"
	type="image/x-icon">--%>

<%--<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">--%>

<%--<link rel="apple-touch-icon"
	href="${pageContext.request.contextPath}/recursos/img/splash/sptouch-icon-iphone.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-ipad.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-iphone-retina.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="${pageContext.request.contextPath}/recursos/img/splash/touch-icon-ipad-retina.png">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/ipad-landscape.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/ipad-portrait.png"
	media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
<link rel="apple-touch-startup-image"
	href="${pageContext.request.contextPath}/recursos/img/splash/iphone.png"
	media="screen and (max-device-width: 320px)">--%>

	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/recursos/img/landing/favicon.png" />
	<!--Estilos-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/general.css"  />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/flexslider.css"  />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/owl.carousel.css"  />
    <script src="//code.tidio.co/ltfdyj2asio00lnzs250tyh0i34wlhqg.js"></script>
</head>

<body >
<!--Inicia  Loading-->
<div id="login">
<form action="<c:url value='j_spring_security_check' />" id="login-form" method="post" name="enviar" novalidate>
    <!--alerta de error -->
    <div class="alert alert-danger" role="alert" id="alerta">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <span class="sr-only">Error:</span>
        <spring:message code="mihabitat.login.error" />
    </div>
    <!--condicion de login-->
    <c:if test="${not empty error}">
        <!--modifica el estilo de la alerta y la hace visible-->
        <script>
             document.getElementById("alerta").style.display = "block";
             document.getElementById("login").style.display="blcok";
        </script>
	</c:if>
    <ul>
        <li><input type="text"  name="j_username" placeholder="usuario"/></li>
        <li><input type="password"  name="j_password"   placeholder="contraseña"/></li>
        <li id="ingresar">
<%--<button type="button" class="btn btn-primary"
        data-bind="click: $root.ingresar" id="btn-login">
    <spring:message code="mihabitat.login.ingreso" />
</button>--%>
        <a href="#" class="btn btn-primary" data-bind="click: $root.ingresar" id="btn-login" >Ingresar</a></li>
        <li id="olvide">
            <a href="${pageContext.request.contextPath}/recuperar-password">
        <spring:message code="mihabitat.login.olvidar.password"/>
        </a>
        </li>
        <li id="olvide2">
            <a>
                Recordar contraseña
            </a>
        </li>
        <li><input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" ></li>
    </ul>
</form>
</div>

<div class="contLoading"></div>



<!-- iniciar sesion movil -->


<div class="contLoading"></div>







<!--Termina Loading-->
<!--Inicia  Menu-->
<header>
<div class="contGenMenu menuNoFijo">
<a href="#home" alt="Mi Hábitat" title="Mi Hábitat">
<img src="${pageContext.request.contextPath}/recursos/img/landing/mihabitat.png" class="logoMenu" alt="Mi Hábitat" title="Mi Hábitat" />
</a>
<!-- <div class="contBtnMenu menuWeb">
<span class="lineM lineM1"></span>
<span class="lineM lineM2"></span>
<span class="lineM lineM3"></span>
</div> -->
<div class="contBtnMenu menuMov">
<span class="lineM lineM1"></span>
<span class="lineM lineM2"></span>
<span class="lineM lineM3"></span>
</div>
<div class="contMenu2">
<a href="#home"      class="tiempo" title="¿Qué es MiHábitat?">¿Qué es MiHábitat?</a>
<a href="#porque"    class="tiempo" title="¿Por qué MiHábitat?">¿Por qué MiHábitat?</a>
<a href="#servicios"  title="Servicios">Características </a>
<a href="#clientes"  class="tiempo" title="Nuestros clientes">Nuestros clientes</a>
<a href="#contacto"  class="tiempo" title="Contacto">Contacto</a>
<a  class="sesion" 	id="sesion" class="tiempo"   title="Iniciar Sesion">Iniciar Sesion</a>
</div>
</div>
<div class="contMenuMov">
<div class="contOpcMov">
<a href="#home"      title="¿Qué es MiHábitat?">¿Qué es MiHábitat?</a><br />
<a href="#porque"    title="¿Por qué MiHábitat?">¿Por qué MiHábitat?</a><br />
<a href="#servicios" title="Servicios">Características </a><br />
<a href="#clientes"  title="Nuestros clientes">Nuestros clientes</a><br />
<a href="#contacto"  title="Contacto">Contacto</a><br />
<a href="${pageContext.request.contextPath}/login" class="sesion" title="Iniciar sesion" >Iniciar Sesion </a><br />

</div>
</div>

</header>




<!--Termina Menu-->
<!--Inicia  Landing-->
<section class="contGenPag">
<!--Inicia Home-->
<section class="seccion" id="home">
<div class="contTitulo">
<h1>¿Qué es MiHábitat?</h1>
<p><strong>MiHábitat</strong> es una plataforma en basada en Internet destinada a mejorar el servicio de administración de condominios habitacionales y comerciales. Los administradores y empresas de administración que lo utilizan reducen su carga de trabajo y mejoran la imagen con sus condóminos de forma impresionante.</p>
<a href="http://www.mihabitat.com.mx/#contacto" class="btnN" style="float: left;">Inicie una <br /> prueba Gratis</a>
<a href="http://www.mihabitat.com.mx/demo" class="btnN" style="float: right;">Entre a Nuestro <br /> Condominio Demo</a>
</div>
<div class="contSlider flexslider">
<ul class="slides">
<li><div class="slide slide1"></div></li>
<li><div class="slide slide2"></div></li>
<li><div class="slide slide3"></div></li>
</ul>
</div>
</section>
<!--Porque-->
<section class="seccion" id="porque">
<div class="contTitulo2">
<h1>¿Por qué MiHábitat?</h1>
<p>MiHábitat ha sido creado por manos mexicanas emprendedoras como una solución totalmente orientada a la persona que lo utiliza, dándote una experiencia de confort, facilidad de uso, ahorro de esfuerzo y tiempo y con una calidad inmejorable, no hay otra opción que sea tan fácil de utilizar, implementar y tan amigable como la nuestra. Si lo dudas, entra a nuestra demo y te darás cuenta la diferencia entre cualquier sistema y MiHábitat totalmente pensado para ti.</p>
<!-- <a href="http://www.mihabitat.com.mx/" class="btnN" style="float: left;">Ir a demo</a>
<a href="http://www.mihabitat.com.mx/" class="btnN" style="float: right;">video</a> -->
<h2>Nuestras Ventajas</h2>
<ul class="listaVentajas">
<li>Ahorra tiempo.</li>
<li>Desde cualquier dispositivo con acceso a Internet.</li>
<li>Actualizaciones y mejoras constantes.</li>
<li>Mejora la imagen con tus condóminos.</li>
<li>Altos estándares de Seguridad y protección de datos.</li>
<li>Disponibilidad 24/7.</li>
<li>Línea directa de Soporte Profesional para cualquier duda o problema.</li>

</ul>
<a href="http://www.mihabitat.com.mx/demo" class="btnN btnVent">Ir a Demo</a>
<a href="http://www.mihabitat.com.mx/zonaabierta/proximamente" class="btnN btnVent">Video</a>
</div>
<!-- <div class="contSlider">
<div class="slide slide1"></div>
</div> -->
<div class="contImgVent">
<img src="${pageContext.request.contextPath}/recursos/img/landing/free-flat-devices.png" />
</div>
</section>




<!--Inicia Ventajas-->
<!-- <section class="seccion" id="ventajas">
<div class="contenedorV">
<div class="contImgVent">
<img src="img/free-flat-devices.png" />
</div>
<div class="contVentajas">
<h2>Nuestras Ventajas</h2>
<ul class="listaVentajas">
    <li>Ahorra tiempo.</li>
    <li>Desde cualquier dispositivo con acceso a Internet.</li>
    <li>Actualizaciones y mejoras constantes.</li>
    <li>Mejora la imagen con tus condóminos.</li>
    <li>Altos estándares de Seguridad y protección de datos.</li>
    <li>Disponibilidad 24/7.</li>
    <li>Línea directa de Soporte Profesional para cualquier duda o problema.</li>
    <a href="http://www.mihabitat.com.mx/" class="btnN btnVent">Ir a Demo</a>
</ul>
</div>
</div>
</section>
-->






<!--Inicia Características -->
<section class="seccion" id="servicios">
<div class="conteServ">
<h2>Características </h2>
    <span class="contList">
        <span class="lista" id="pes1">Gestión Financiera</span>
        <span class="lista" id="pes2">Gestión Administrativa</span>
        <span class="lista" id="pes3">Comunicación Condominal</span>
        <span class="lista lastlist" id="pes4">App y Portal de Autoservicio para el Condómino</span>
        <br />
        <div class="contListas">
            <div class="opcList pes1">
                <h1>Gestión Financiera</h1>
                <ul>
                    <li>Gestión de Cargos y Cuotas de Mantenimiento</li>
                    <li>Cargos por Consumo Prorrateado</li>
                    <li>Registro y Aprobación de Pagos y  Pagos no Identificados</li>
                    <li>Facturación Electrónica</li>
                    <li>Generación Automática de Cargos</li>
                    <li>Egresos por Proveedores y Facturas por Pagar</li>
                    <li>Gestión de Cuentas Contables y Presupuestos</li>
                </ul>
                <img src="${pageContext.request.contextPath}/recursos/img/landing/icon-calculator.png" class="calc" />
            </div>
            <div class="opcList pes2">
                <h1>Gestión Administrativa.</h1>
                <ul>
                    <li>Administración de Departamentos y Condóminos</li>
                    <li>Módulo de Seguimiento de Cobranza</li>
                    <li>Envío o Impresión de Estados de Cuenta y Recibos de Pago</li>
                    <li>Administración y Reservación de Áreas Comunes</li>
                    <li>Módulo de tareas y pendientes de la Administración</li>
                    <li>Seguimiento de Incidencias y Proyectos del Condominio</li>
                    <li>Más de 15 Reportes Útiles para las juntas con el comité o condóminos.</li>
                </ul>
                <img src="${pageContext.request.contextPath}/recursos/img/landing/icon-note.png" class="nota" />
            </div>
            <div class="opcList pes3">
                <h1>Comunicación Condominal.</h1>
                <ul>
                    <li>Panel Electrónico de Avisos</li>
                    <li>Repositorio de Documentos del  Condominio (Reglamentos, Actas, etc)</li>
                    <li>Aviso Oportuno para Compra y Venta de Objetos</li>
                    <li>Directorio del Condominio</li>
                    <li>Foros de Opinión para los Condóminos</li>
                    <li>Reporte de Incidencias y seguimiento de Proyectos</li>
                    <li>Noticias y Opiniones Expertas de asuntos Condominales</li>
                    <li>Notificaciones en Tiempo Real de la actividad del Condominio.</li>
                </ul>
                <img src="${pageContext.request.contextPath}/recursos/img/landing/icon-buildings.png" class="edif" />
            </div>
            <div class="opcList pes4">
                <h1>App y Portal de Autoservicio para el Condómino</h1>
                <ul>
                    <li>Consulta en Tiempo Real del Saldo de mis Departamentos</li>
                    <li>Impresión de mis recibos de Pago</li>
                    <li>Recepción de Notificaciones de Avisos, Nuevos Cargos, etc.</li>
                    <li>Consulta del Estado de Cuenta y Movimientos</li>
                    <li>Solicitudes de Reservación de Instalaciones o Áreas</li>
                    <li>Acceso a Foros del Condominio.</li>
                    <li>Registro de Pago en Línea (Depósito, Transferencia, TDC, OXXO, 7-Eleven y demás formas de pago)</li>
                </ul>
                <img src="${pageContext.request.contextPath}/recursos/img/landing/icon-computer.png" class="comp" />
            </div>
        </div>
    </span>
</div>
</section>
<!--Inicia Clientes-->
<section class="seccion" id="clientes">
<div class="contClientes">
<h2>Nuestros clientes nos respaldan</h2>
<div class="conSlideClientes">
<div class="SlideClientes">
    <div class="opinionCli">
        <img src="${pageContext.request.contextPath}/recursos/img/landing/img1.png" />
        <h3>Carlos Quintero Castro | Asociación de Condóminos Los Laureles </h3>
        <p>"No cabe duda que estos tipos saben lo que necesitamos, me ahorra horas de trabajo y problemas con mis condóminos, además, gracias a su portal de autoservicio las solicitudes de atención se han disminuido mucho. Tenemos cuentas claras y una comunicación fluida con los condóminos. ¡Buen trabajo!"</p>
    </div>
    <div class="opinionCli">
        <img src="${pageContext.request.contextPath}/recursos/img/landing/img1.png" />
        <h3>L.A.E. Francisco Javier Sanchez Islas | COYED Administración</h3>
        <p>"Teníamos anteriormente otro sistema, la verdad es que era muy complicado, mis administradores llamaban a cada rato por dudas y errores que cometian debido a que la herramienta no era clara y al gran numero de problemas que tenía, sin decir que además el soporte era pésimo, muy tardado y con respuestas que dejaban mucho que desear. He de reconocer que con esta herramienta mejoramos mucho el desempeño, mis administradores ahora pueden atender más condominios sin hacer más esfuerzo, no más errores y su soporte es excelente."</p>
    </div>
    <div class="opinionCli">
        <img src="${pageContext.request.contextPath}/recursos/img/landing/img1.png" />
        <h3>Miguel Ruiz Alcantara | CRUDAUN Administradores</h3>
        <p>"Es una herramienta muy intuitiva, todo se encuentra claro y la operación es muy simple. Sus tareas automatizadas son excelentes, nos ahorran estar generando cargos cada mes,su sitio de autoservicio permite que sea el condómino el que registre su pago, produce informes para las reuniones mensuales, todo esto ahorra un tiempo descomunal y por lo tanto bastante dinero."</p>
    </div>
</div>
<div class="slidPrev"></div>
<div class="slidNext"></div>
</div>
<%--<div class="contLogos">
<span data-slide="1">Logo 1</span>
<span data-slide="2">Logo 2</span>
<span data-slide="3">Logo 3</span>
<span data-slide="4">Logo 4</span>
</div>--%>
</div>
</section>

<!--Inicia Contacto-->
<section class="seccion" id="contacto">
<div class="contGenCont">
<div class="contForm">
<h3>Contáctanos</h3>
<form id="formContacto" action="${pageContext.request.contextPath}/zonaabierta/contacto" method="get">
    <input type="text" id="inpNom" name="inpNom" placeholder="NOMBRE Y APELLIDO" />
    <input type="text" id="inpEma" name="inpEma" placeholder="E-MAIL" />
    <input type="number" id="inpTel" name="inpTel" placeholder="TELÉFONO" />
    <textarea id="comen" name="comen" placeholder="COMENTARIOS"></textarea>
    <span class="btnEnv btnN">ENVIAR</span>
</form>
<div class="contEnviando" style="display: none;">
    <h4>Enviando...<br /> Por favor espera.</h4>
    <span class="enviando"></span>
</div>
<div class="contResp" style="display: none;">
    <h4>Gracias<br /> Pronto Nos Pondremos En Contacto Contigo.</h4>
    <span class="btnNuev btnN">Nuevo Comentario</span>
</div>
</div>
<footer>
<div class="conGenFooter">
    <p style="text-align: center;padding: 10px 0px;">
        <span id="siteseal"><script async type="text/javascript" src="https://seal.godaddy.com/getSeal?sealID=vTq349MKH35Y0Zo0nkxOb5XdsB0DOmlF4iiustWAyNUWSuCGto4kUZZb0r7g"></script></span>
    </p>
    <p class="datosWeb" style="text-align: center;padding: 10px 0px;font-size: 14px;">
        <a href="mailto:hola@mihabitat.com.mx" style="font-size: 14px;">hola@mihabitat.com.mx</a>&nbsp;&nbsp; · &nbsp;&nbsp;<a href="tel:5512041299" style="font-size: 14px;">(55) 1204-1299</a><br />
        Un producto de BSTMéxico Tecnologías de Información &nbsp;&nbsp;·&nbsp;&nbsp; <a href="http://www.bstmexico.com/" target="_blank" style="font-size: 14px;">www.bstmexico.com</a><br />
        <a href="${pageContext.request.contextPath}/zonaabierta/avisodeprivacidad" target="_blank"  style="text-decoration: underline;font-size: 14px;">Aviso de Privacidad</a>


    </p>
    <p class="datosMovil" style="display: none;">
        <a href="mailto:ventas@mihabitat.com.mx">ventas@mihabitat.com.mx</a>&nbsp;&nbsp;
        <a href="tel:5512041299">(55) 1204-1299</a><br />
        Un producto de BSTMéxico <br /> Tecnologías de Información<br />
        <a href="http://www.bstmexico.com/" target="_blank">www.bstmexico.com</a><br />
        <a href="${pageContext.request.contextPath}/zonaabierta/avisodeprivacidad" target="_blank" style="text-decoration: underline">Aviso de Privacidad</a><br />
    </p>
</div>
</footer>
</div>
</section>
</section>
<!--Termina Landing-->
<!--Scripts-->
<script src="${pageContext.request.contextPath}/recursos/js/plugin/pace/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/libs/jquery-2.0.2.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/libs/jquery-ui-1.10.3.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-3.3.0.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/bootstrap/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.es.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/masked-input/jquery.maskedinput.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/landing/jquery.flexslider-min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/landing/owl.carousel.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/recursos/js/notification/SmartNotification.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/util.js"></script>--%>
<%--<script src="js/jquery-2.1.3.min.js"></script>--%>

<script src="${pageContext.request.contextPath}/recursos/js/app/login/ingreso-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/landing/general.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/landing/envia.js?v=${project-version}"></script>
<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
    $(function() {
        var viewModel = new IngresoViewModel();
        ko.applyBindings(viewModel);
        $("#login-form").validate();
    });
</script>
<%--<script type="text/javascript">
function enviar_formulario(){
    document.enviar.submit()
}
</script>--%>
<script>
$(document).ready(function(){
$("#sesion").click(function(){
$("#login").slideToggle("slow");
});
});
</script>
</body>
</html>