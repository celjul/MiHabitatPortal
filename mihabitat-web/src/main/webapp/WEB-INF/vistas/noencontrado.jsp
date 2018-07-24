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

    <link rev="made" href="mailto:redrocketagenciadigital@gmail.com">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/recursos/img/landing/favicon.png" />
    <!--Estilos-->
    <link rel="stylesheet" type="text/css" media="screen"
          href="${pageContext.request.contextPath}/recursos/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" media="screen"
          href="${pageContext.request.contextPath}/recursos/css/smartadmin-production.min.css">
    <link rel="stylesheet" type="text/css" media="screen"
          href="${pageContext.request.contextPath}/recursos/css/smartadmin-skins.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/general.css"  />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/flexslider.css"  />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/landing/owl.carousel.css"  />
    <style>
        .error-text-2 {
            text-align: center;
            font-size: 700%;
            font-weight: bold;
            font-weight: 100;
            color: #333;
            line-height: 1;
            letter-spacing: -.05em;
            background-image: -webkit-linear-gradient(92deg,#333,#ed1c24);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        div#content{
            top: 100px;
            text-align: center;
            padding: 15px;
        }
        #contacto{
            overflow: visible;
            background-color: #FFF;
            z-index: 50;
        }
    </style>
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
        <a href="${pageContext.request.contextPath}/#home" alt="Mi Hábitat" title="Mi Hábitat">
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
            <a href="${pageContext.request.contextPath}/#home"      class="tiempo" title="¿Qué es MiHábitat?">¿Qué es MiHábitat?</a>
            <a href="${pageContext.request.contextPath}/#porque"    class="tiempo" title="¿Por qué MiHábitat?">¿Por qué MiHábitat?</a>
            <a href="${pageContext.request.contextPath}/#servicios"  title="Servicios">Características </a>
            <a href="${pageContext.request.contextPath}/#clientes"  class="tiempo" title="Nuestros clientes">Nuestros clientes</a>
            <a href="${pageContext.request.contextPath}/#contacto"  class="tiempo" title="Contacto">Contacto</a>
            <a  class="sesion" 	id="sesion" class="tiempo"   title="Iniciar Sesion">Iniciar Sesion</a>
        </div>
    </div>
    <div class="contMenuMov">
        <div class="contOpcMov">
            <a href="${pageContext.request.contextPath}/#home"      title="¿Qué es MiHábitat?">¿Qué es MiHábitat?</a><br />
            <a href="${pageContext.request.contextPath}/#porque"    title="¿Por qué MiHábitat?">¿Por qué MiHábitat?</a><br />
            <a href="${pageContext.request.contextPath}/#servicios" title="Servicios">Características </a><br />
            <a href="${pageContext.request.contextPath}/#clientes"  title="Nuestros clientes">Nuestros clientes</a><br />
            <a href="${pageContext.request.contextPath}/#contacto"  title="Contacto">Contacto</a><br />
            <a href="http://www.mihabitat.com/" class="sesion" title="Iniciar sesion" >Iniciar Sesion </a><br />

        </div>
    </div>

</header>




<!--Termina Menu-->
<!--Inicia  No enc-->



<!--Termina No enc-->

    <!--Inicia Contacto-->
    <section class="seccion" id="contacto">

        <div class="contGenCont">
            <!-- MAIN CONTENT -->
            <div id="content">

                <!-- row -->
                <div class="row">

                    <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">

                        <div class="row">
                            <div class="col-sm-12">
                                <div class="text-center error-box">
                                    <h1 class="error-text-2" style="font-size: 45px"> UPPSSS! </h1><br>
                                    <h2 class="font-xl"><strong>
                                        <i class="fa fa-fw fa-warning fa-lg txt-color-orangeDark"></i> Error 404: No pudimos encontrar lo que estas buscando.</strong></h2>
                                    <br />
                                    <p class="lead">
                                        Si tienes alguna duda o crees que es un error ponte en contacto a <a style="cursor: pointer" href="mailto:soporte@mihabitat.com.mx">soporte@mihabitat.com.mx</a>.
                                    </p>
                                    <p >
                                        <a class="btnN" style="color: #FFF !important;background-color: #01B6AF;" href="javascript:window.history.go(-1);">Regresar</a>
                                    </p>
                                </div>

                            </div>

                        </div>

                    </div>

                    <!-- end row -->

                </div>

            </div>
            <!-- END MAIN CONTENT -->
            <footer>
                <div class="conGenFooter">
                    <p class="datosWeb">
                        <a href="mailto:hola@mihabitat.com.mx">hola@mihabitat.com.mx</a>&nbsp;&nbsp; · &nbsp;&nbsp;<a href="tel:5512041299">(55) 1204-1299</a><br />
                        Un producto de BSTMéxico Tecnologías de Información &nbsp;&nbsp;|&nbsp;&nbsp; <a href="http://www.bstmexico.com/" target="_blank">www.bstmexico.com</a>
                    </p>
                    <p class="datosMovil" style="display: none;">
                        <a href="mailto:hola@mihabitat.com.mx">hola@mihabitat.com.mx</a>&nbsp;&nbsp;
                        <a href="tel:5512041299">(55) 1204-1299</a><br />
                        Un producto de BSTMéxico <br /> Tecnologías de Información<br />
                        <a href="http://www.bstmexico.com/" target="_blank">www.bstmexico.com</a><br />
                    </p>
                    <img class="hidden-mobile" src="${pageContext.request.contextPath}/recursos/img/landing/logo-02.png" alt="miHábitat Administración de Condominios" title="miHábitat Administración de Condominios" />
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
<script src="${pageContext.request.contextPath}/recursos/js/landing/general.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/landing/envia.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/util.js"></script>--%>
<%--<script src="js/jquery-2.1.3.min.js"></script>--%>

<script src="${pageContext.request.contextPath}/recursos/js/app/login/ingreso-app.js?v=${project-version}"></script>
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