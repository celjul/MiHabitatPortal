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
		                                        <div class="col col-md-3">
		                                            <label class="label">
		                                                &nbsp;
		                                            </label>
		                                            <label class="toggle">
		                                                <input type="checkbox" name="activo" id="activo" data-bind="checked: $root.reporte.detalle">
		                                                <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.saldofavor.detalle" />
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
	                                                        <th class="col-sm-2 col-md-2 col-lg-2"><spring:message code="mihabitat.departamento" /></th>
	                                                        <th class="col-sm-3 col-md-3 col-lg-3"><spring:message code="mihabitat.departamento.etiquetas" /></th>
	                                                        <th class="col-sm-2 col-md-2 col-lg-2"><spring:message code="mihabitat.reportes.saldofavor.saldo.monto" /></th>
	                                                    </tr>
	                                                </thead>
	                                                <tbody>
	                                                    <!-- ko foreach: { data: $root.reporte.saldos } -->
	                                                        <tr>
	                                                            <td class="col-md-1 panel-title">
	                                                                <!-- ko if: generados().length > 0 -->
	                                                                    <a  data-toggle="collapse" data-bind="attr : { href: '#collapse-movs' + $index() }" class="collapsed" style="padding: 0px 20px">
	                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i> <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
	                                                                    </a>
	                                                                <!-- /ko -->
	                                                            </td>
	                                                            <td data-bind="text: departamento"></td>
	                                                            <td data-bind="text: torresEtiquetas"></td>
	                                                            <td data-bind="currency: monto, symbol: '$'" class="currency"></td>
	                                                        </tr>
	                                                        <!-- ko if: generados().length > 0 -->
	                                                            <tr class="panel-collapse collapse" data-bind="attr : { id: 'collapse-movs' + $index()}">
	                                                                <td> &nbsp; </td>
	                                                                <td colspan="3" class="no-padding">
	                                                                    <table class="table table-striped table-bordered" style="width: 100%;">
	                                                                        <thead>
	                                                                            <tr class="active">
	                                                                                <th class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
	                                                                                    <spring:message code="mihabitat.reportes.saldofavor.saldo.pago.fecha" />
                                                                                    </th>
	                                                                                <th class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
	                                                                                   <spring:message code="mihabitat.reportes.saldofavor.saldo.pago.descripcion" />
	                                                                                </th>
	                                                                                <th class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
	                                                                                   <spring:message code="mihabitat.reportes.saldofavor.saldo.pago.monto" />
                                                                                    </th>
	                                                                            </tr>
	                                                                        </thead>
	                                                                        <tbody>
	                                                                            <!-- ko foreach: { data: generados } -->
	                                                                                <tr data-bind="css: {danger: monto() < 0, success: monto() >= 0}">
	                                                                                    <td data-bind="text: fecha"></td>
	                                                                                    <td data-bind="text: descripcion" style="white-space: normal;"></td>
	                                                                                    <td data-bind="currency: monto, symbol: $ " style="text-align: right;"></td>
	                                                                                </tr>
	                                                                            <!-- /ko -->
	                                                                            <%--<!-- ko foreach: { data: gastados } -->
                                                                                    <tr class="danger">
                                                                                        <td data-bind="text: fecha"></td>
                                                                                        <td data-bind="text: descripcion"></td>
                                                                                        <td data-bind="currency: monto, symbol: $ " style="text-align: right;"></td>
                                                                                    </tr>
                                                                                <!-- /ko -->--%>
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

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo-detalle.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo-favor.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo-favor-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new SaldoFavorViewModel();
			ko.applyBindings(viewModel);
		});
	</script>
</body>