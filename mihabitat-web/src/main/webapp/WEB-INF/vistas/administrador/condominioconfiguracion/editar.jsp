<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.configuracion.condominio"/> | <spring:message code="mihabitat.nombre"/></title>

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
               <a href="${pageContext.request.contextPath}/administrador/condominio/configuracion">
                   <spring:message code="mihabitat.menu.configuracion.condominio"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="condominioconfiguracion.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/app/configuracioncondominio/configuracioncondominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/configuracioncondominio/configuracioncondominio-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new ConfiguracionCondominioViewModel({
                configuracionCondominio : ${configuracionCondominio}
            });
            ko.applyBindings(viewModel);
        });
    </script>
</body>