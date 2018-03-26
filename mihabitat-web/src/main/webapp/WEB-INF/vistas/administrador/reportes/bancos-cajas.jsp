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
                            <spring:message code="mihabitat.reportes.bancos.cajas" />
                        </h2>
                    </header>
                    <div>
	                    <div class="jarviswidget-editbox"></div>
	                        <div class="widget-body no-padding">
			                    <form id="reporte-form" class="smart-form">
			                        <fieldset>
		                                <div class="col-sm-12 col-md-12 col-lg-12">
		                                    <div class="row">
		                                        <div class="col col-md-1">
		                                            &nbsp;
		                                        </div>
		                                        <div class="col col-md-2">
				                                    <label class="label">
				                                        <span class="error-required">*</span><spring:message code="mihabitat.reportes.bancos.cajas.inicio" />
				                                    </label>
				                                    <input class="form-control" type="text" name="inicio" id="inicio" 
				                                        placeholder="<spring:message code="mihabitat.reportes.bancos.cajas.inicio" />"
				                                        required="required" readonly="readonly" style="background: white;"
				                                        data-bind="value: $root.reporte.inicio">
				                                </div>
				                                <div class="col col-md-2">
				                                    <label class="label">
				                                        <span class="error-required">*</span><spring:message code="mihabitat.reportes.bancos.cajas.fin" />
				                                    </label>
				                                    <input class="form-control" type="text" name="fin" id="fin" 
				                                        placeholder="<spring:message code="mihabitat.reportes.bancos.cajas.fin" />"
				                                        required="required" readonly="readonly" style="background: white;"
				                                        data-bind="value: $root.reporte.fin">
				                                </div>
		                                        <div class="col col-md-2">
		                                            <label class="label">
		                                                &nbsp;
		                                            </label>
		                                            <label class="toggle">
		                                                <input type="checkbox" name="activo" id="activo" data-bind="checked: $root.reporte.detalle">
		                                                <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.saldodepartamento.detalle" />
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
		                                        <div class="col col-md-2">
		                                            &nbsp;
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
                                <div class="panel-group smart-accordion-default" id="accordion">
                                    <div class="panel panel-default">
                                        <div class="panel-heading no-padding">
                                            <div class="table-responsive">
	                                            <table id="table-cuentas" class="table table-striped table-bordered" style="width: 100%">
	                                                <thead>
	                                                    <tr>
	                                                        <th class="col-sm-1 col-md-1 col-lg-1">&nbsp;</th>
	                                                        <th class="col-sm-3 col-md-3 col-lg-3"><spring:message code="mihabitat.cuenta" /></th>
	                                                        <th class="col-sm-2 col-md-1 col-lg-2"><spring:message code="mihabitat.reportes.bancos.cajas.inicial" /></th>
	                                                        <th class="col-sm-2 col-md-1 col-lg-2"><spring:message code="mihabitat.reportes.bancos.cajas.debe" /></th>
	                                                        <th class="col-sm-2 col-md-1 col-lg-2"><spring:message code="mihabitat.reportes.bancos.cajas.haber" /></th>
	                                                        <th class="col-sm-2 col-md-1 col-lg-2"><spring:message code="mihabitat.reportes.bancos.cajas.saldo" /></th>
	                                                    </tr>
	                                                </thead>
	                                                <tbody>
	                                                    <!-- ko foreach: { data: $root.reporte.cuentas } -->
	                                                        <tr>
	                                                            <td class="col-md-1 panel-title">
	                                                                <!-- ko if: movimientos().length > 0 -->
	                                                                    <a  data-toggle="collapse" data-bind="attr : { href: '#collapse-movs' + id() }" class="collapsed" style="padding: 0px 20px">
	                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i> <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
	                                                                    </a>
	                                                                <!-- /ko -->
	                                                            </td>
	                                                            <td data-bind="text: nombre"></td>
	                                                            <td data-bind="currency: inicial, symbol: '$'" class="currency"></td>
	                                                            <td data-bind="currency: debe, symbol: '$'" class="currency"></td>
	                                                            <td data-bind="currency: haber, symbol: '$'" class="currency"></td>
	                                                            <td data-bind="currency: saldo, symbol: '$'" class="currency"></td>
	                                                        </tr>
	                                                        <!-- ko if: movimientos().length > 0 -->
	                                                            <tr class="panel-collapse collapse" data-bind="attr : { id: 'collapse-movs' + id()}">
	                                                                <td colspan="2"> &nbsp; </td>
	                                                                <td colspan="7" class="no-padding">
	                                                                    <table class="table table-striped table-bordered" style="width: 100%;">
	                                                                        <thead>
	                                                                            <tr class="active">
	                                                                                <th class="col-sm-2 col-md-2 col-lg-2">
	                                                                                   <spring:message code="mihabitat.cargo.fecha" />
	                                                                                </th>
	                                                                                <th class="col-sm-2 col-md-2 col-lg-2">
	                                                                                   <spring:message code="mihabitat.cargo.concepto" />
	                                                                                </th>
	                                                                                 <th class="col-sm-2 col-md-2 col-lg-2">
	                                                                                   <spring:message code="mihabitat.pago.referencia" />
	                                                                                </th>
	                                                                                 <th class="col-sm-2 col-md-2 col-lg-2">
	                                                                                   <spring:message code="mihabitat.persona.nombre" />
	                                                                                </th>
	                                                                                <th class="col-sm-1 col-md-1 col-lg-1">
	                                                                                   <spring:message code="mihabitat.movimiento.bancocaja.debe" />
	                                                                                </th>
	                                                                                <th class="col-sm-1 col-md-1 col-lg-1">
	                                                                                   <spring:message code="mihabitat.movimiento.bancocaja.haber" />
	                                                                               </th>
	                                                                            </tr>
	                                                                        </thead>
	                                                                        <tbody>
	                                                                            <!-- ko foreach: { data: movimientos } -->
	                                                                                <tr class="active">
	                                                                                    <td data-bind="text: fecha"></td>
	                                                                                    <td data-bind="text: descripcion"></td>
	                                                                                    <td data-bind="text: referencia"></td>
	                                                                                    <td data-bind="text: nombre"></td>
	                                                                                    <!-- ko if: debe -->
	                                                                                        <td data-bind="currency: debe, symbol: $ " style="text-align: right;"></td>
	                                                                                        <td> &nbsp; </td>
	                                                                                    <!-- /ko -->
	                                                                                    <!-- ko if: haber -->
	                                                                                        <td> &nbsp; </td>
	                                                                                        <td data-bind="currency: haber, symbol: $ " style="text-align: right;"></td>
	                                                                                    <!-- /ko -->
	                                                                                </tr>
	                                                                            <!-- /ko -->
	                                                                        </tbody>
	                                                                    </table>
	                                                                </td>
	                                                            </tr>
	                                                        <!-- /ko -->
	                                                    <!-- /ko -->
	                                                </tbody>
	                                            </table>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
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

	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/movimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/bancos-cajas.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/bancos-cajas-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new BancosCajasViewModel();
			ko.applyBindings(viewModel);
			$("#inicio, #fin").datepicker();
		});
	</script>
</body>