<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<head>
    <title><spring:message code="mihabitat.reportes"/> | <spring:message code="mihabitat.nombre"/></title>
    <style type="text/css">
        .currency {
            text-align: right;
        }
    </style>
</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                   <spring:message code="mihabitat.menu.inicio"/>
               </a>
             </li>
        </ol>
    </div>
    <div id="content">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget" id="wid-id-1">
                    <header>
                        <span class="widget-icon"> <i class="fa fa-edit"></i>
                        </span>
                        <h2>
                            <spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza" />
                        </h2>
                    </header>
                    <div>
                        <div class="jarviswidget-editbox"></div>
                        <div class="widget-body no-padding">
                            <form id="reporte-form" class="smart-form">
                                <fieldset>
                                    <div class="col-sm-12 col-md-12 col-lg-12">
                                        <div class="row">
                                            <div class="col col-md-2">
                                                <label class="label">
                                                    <spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.tipo" />
                                                </label>
                                                <label class="select">
                                                    <select name="tipo" id="tipo" required="required"
                                                        data-bind="options: $root.tipos,
                                                                     optionsText: 'descripcion',
                                                                     optionsValue: 'id',
                                                                     value: $root.tipo">
                                                    </select>
                                                    <i></i>
                                                </label>
                                            </div>
                                            <div class="col col-md-2" data-bind="visible: $root.tipo() == 1">
                                                <label class="label">
                                                    <spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.anio" />
                                                </label>
                                                <label class="select">
                                                    <select name="anio" id="anio" required="required"
                                                        data-bind="options: $root.anios, 
                                                                     optionsCaption : 'Seleccione una opci�n',
                                                                     value: $root.reporte.anio">
                                                    </select>
                                                    <i></i>
                                                </label>
                                            </div>
                                            <div class="col col-md-4">
                                                <div class="row">
                                                   <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('pdf') } }">
                                                                <i class="fa fa-file-pdf-o"></i>
                                                                <spring:message code="mihabitat.reportes.pdf" />
                                                            </button>
                                                    </div>
                                                    <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('xlsx') } }">
                                                            <i class="fa fa-file-excel-o"></i>
                                                            <spring:message code="mihabitat.reportes.xls" />
                                                        </button>
                                                    </div>
                                                    <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('csv') } }">
                                                            <i class="fa fa-file-excel-o"></i>
                                                            <spring:message code="mihabitat.reportes.csv" />
                                                        </button>
                                                    </div>
                                                    <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('txt') } }">
                                                            <i class="fa fa-file-text-o"></i>
                                                            <spring:message code="mihabitat.reportes.txt" />
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <section id="widget-grid" class="">
            <div class="row">
                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="jarviswidget" id="wid-id-2" data-widget-editbutton="false">
                        <header>
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2></h2>
                        </header>
                        <div>
                            <div class="jarviswidget-editbox">
                            </div>
                            <div class="widget-body no-padding">
	                            <table id="table-periodos" class="table table-striped table-bordered table-hover" style="width: 100%">
	                                <thead>
	                                    <tr>
	                                        <th data-class="expand"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.periodo" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.cobros" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.pagos.tiempo" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.pagos.extemporaneo" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.pagos.pendientes" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.porcentaje.cumpliento" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.porcentaje.efectividad" /></th>
	                                        <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.cumplimiento.efectividad.cobranza.porcentaje.morosidad" /></th>
	                                    </tr>
	                                </thead>
	                                <tbody data-bind="foreach : { data: $root.reporte.periodos }">
	                                    <tr>
	                                        <td data-bind="text: periodo"></td>
	                                        <td data-bind="currency: cobros, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: pagosATiempo, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: pagosExtemporaneos, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: porCobrar, symbol: '$'" class="currency"></td>
	                                        <td data-bind="text: porcentajeCumplimiento() + '%'" class="currency"></td>
	                                        <td data-bind="text: porcentajeEfectividad() + '%'" class="currency"></td>
	                                        <td data-bind="text: porcentajeMorosidad() + '%'" class="currency"></td>
	                                    </tr>
	                                </tbody>
	                            </table>
                            </div>
                        </div>
                    </div>
                </article>
            </div>
        </section>
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/periodo-cobranza.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cumplimiento-efectividad-cobranza.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cumplimiento-efectividad-cobranza-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new CumplimientoEfectividadCobranzaViewModel();
            ko.applyBindings(viewModel);
        });
    </script>
</body>