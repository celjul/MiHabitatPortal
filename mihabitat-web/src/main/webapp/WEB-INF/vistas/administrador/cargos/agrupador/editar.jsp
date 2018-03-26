<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
    <title><spring:message code="mihabitat.menu.cargos"/> | <spring:message code="mihabitat.nombre"/></title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.css">
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
               <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/lista">
                   <spring:message code="mihabitat.menu.cargos"/>
               </a>
            </li>
        </ol>
        <jsp:include page="../../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <jsp:include page="cargo-agrupador.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.impl.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/departamento-aux.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-indiviso.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-prorrateo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-agrupador-simple.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-agrupador-app.js?v=${project-version}"></script>

    <script type="text/javascript">
        $(function() {
            var viewModel = new CargoAgrupadorViewModel({
                departamentos : ${departamentos},
                cuentas: ${cuentas},
                consumos : ${consumos},
                catalogoCargo : ${catalogoCargo},
                condominio : {
                    id : ${condominio.id}
                },
                cargo : ${cargo},
                catalogoInteres : ${catalogoInteres}
            });
            ko.applyBindings(viewModel);
            viewModel.calcula();

            $("#consumo, #cuenta").select2();
            $("#fecha, #descuento-fecha, #recargo-fecha").datepicker({
                //minDate: '0'
            });
            $("#cargo-form").validate();
            $("#form-cargo-departamento").validate();

            var id = "#table-departamentos";

            var otable = $(id).DataTable({
                "aaSorting" : [],
                "aoColumns": [
                    null,
                    null,
                    null,
                    { "bSearchable": false, "bSortable": false },
                    { "bSearchable": false, "bSortable": false },
                    { "bSearchable": false, "bSortable": false },
                    { "bSearchable": false, "bSortable": false },
                    { "bSearchable": false, "bSortable": false },
                    { "bSearchable": false, "bSortable": false }
                ],
                "autoWidth" : false,
                "destroy": true,
                "info": false,
                "paging": false,
                "responsive": true,
                "searching": true,
                "scrollCollapse": false
            });

            yadcf.init(otable, [{
                column_number: 0,
                filter_type: "text",
                filter_default_label : "Departammento",
                filter_reset_button_text: false
            },{
                column_number: 1,
                filter_type : 'multi_select',
                filter_default_label : "Torres y Etiquetas",
                select_type: 'select2',
                column_data_type: "html",
                html_data_type: "text",
                filter_reset_button_text: false
            },{
                column_number: 2,
                filter_type: "text",
                filter_default_label : "Condóminos",
                filter_reset_button_text: false
            }]);

            $("#table-departamentos_filter").hide();
        });
    </script>
</body>