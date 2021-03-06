<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title><spring:message code="mihabitat.menu.pagos"/> | <spring:message code="mihabitat.nombre"/></title>

    <link href="${pageContext.request.contextPath}/recursos/css/footable.core.css" rel="stylesheet" type="text/css">
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
            <a href="${pageContext.request.contextPath}/administrador/pagos/lista">
                <spring:message code="mihabitat.menu.pagos"/>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/administrador/pagos/nuevo">
                <spring:message code="mihabitat.menu.nuevo"/>
            </a>
        </li>
    </ol>
    <jsp:include page="../../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
    <jsp:include page="../../pagos/pago.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/dropzone/dropzone.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/fooplugins/footable.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-fileupload/js/jquery.fileupload.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/comprobante-upload.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-indiviso.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-simple.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-prorrateo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/departamento-aux.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/contacto-aux.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/estatus.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-departamento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-tarjeta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador-app.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/pago-app.js?v=${project-version}"></script>
<script type="text/javascript">
    $(function () {
        var viewModel = new PagoViewModel({
            <c:if test="${not empty ingresoNoIdentificado }">
            ingresoNoIdentificado: ${ingresoNoIdentificado},
            </c:if>
            rol: 'administrador',
            contactos: ${contactos},
            cuentas: ${cuentas},
            cuentasIngresos: ${cuentasIngresos},
            tiposCargo: ${tiposCargo},
            metodosPago: ${metodosPago},
            estatus: {
                usuario: {
                    id: ${usuario.id}
                },
                estatus: {
                    id: AppConfig.catalogos.estatuspago.aprobado
                }
            },
            pago: {
                <c:if test="${not empty contacto }">
                contacto: ${contacto},
                </c:if>
                condominio: {
                    id: ${condominio.id}
                }
            }
        });
        ko.applyBindings(viewModel);
        /*$("#pago, #cuentaIngreso").select2();*/
        $("#fecha").datepicker({
            maxDate: '0'
        });
        $('#fechaNuevoCargo').datepicker({
            maxDate: '0'
        });
        /*var table = dibujarTabla(".table-cargos");*/
        $("#pago-form").validate();
    });
</script>
</body>