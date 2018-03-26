<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
    <title><spring:message code="mihabitat.menu.departamentos"/> | <spring:message code="mihabitat.nombre"/></title>

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
            <a href="${pageContext.request.contextPath}/administrador/departamentos/lista">
                <spring:message code="mihabitat.menu.departamentos"/>
            </a>
        </li>
        <li>
            <a data-bind="attr : {href : String.format('${pageContext.request.contextPath}/administrador/departamentos/actualizar/{0}', departamento.id())}">
                <spring:message code="mihabitat.menu.editar"/>
            </a>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
    <jsp:include page="departamento.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento-app.js?v=${project-version}"></script>

<script type="text/javascript">
    $(function () {
        var viewModel = new DepartamentoViewModel({
            contactos: ${contactos},
            mantenimientos: ${mantenimientos},
            grupos: ${grupos},
            catalogoEmail: ${catalogoEmail},
            catalogoTelefono: ${catalogoTelefono},
            departamento: ${departamento},
            tiposCobro: ${catalogoTipoCobroDepartamento},
            condominio: {
                id: ${condominio.id}
            }
        });
        ko.applyBindings(viewModel);

        $("#departamento-form").validate();
        $("#mantenimiento").select2();
        $("#grupos").select2();
        $("#contactoSeleccionado").select2();
        $("li.contacto > a").last().click();
        $("#contacto-button").on("click", function () {
            $("li.contacto > a").last().click();
            for (var i = 0; i < $("li.contacto > a").length; i++) {
                $("#form-contacto-" + i).validate();
            }
        })
    });
</script>
</body>