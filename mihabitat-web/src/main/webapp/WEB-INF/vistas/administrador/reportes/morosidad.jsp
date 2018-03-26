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
                            <spring:message code="mihabitat.reportes.morosidad" />
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
			                                        <spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.tipo" />
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
	                                        <div class="col col-md-2" >
	                                            <label class="label">
	                                                <span class="error-required">*</span><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.fin" />
	                                            </label>
	                                            <input class="form-control" type="text" name="fin" id="fin" 
	                                                placeholder="<spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.fin" />"
	                                                required="required" readonly="readonly" style="background: white;"
	                                                data-bind="value: $root.reporte.fin">
	                                        </div>
	                                        <div class="col col-md-2" data-bind="visible: $root.tipo() == 2">
	                                            <label class="label">
	                                                <spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.anio" />
	                                            </label>
	                                            <label class="select">
	                                                <select name="anio" id="anio" required="required"
	                                                    data-bind="options: $root.anios, 
	                                                                 optionsCaption : 'Seleccionar',
	                                                                 value: $root.reporte.anio">
	                                                </select>
	                                                <i></i>
	                                            </label>
	                                        </div>
	                                        <div class="col col-md-1">
                                                <label class="label">
                                                    <span class="error-required">*</span><spring:message code="mihabitat.reportes.morosidad.dias" />
                                                </label>
                                                <input class="form-control" type="number" name="dias" id="dias" 
                                                    placeholder="<spring:message code="mihabitat.reportes.morosidad.dias" />"
                                                    required="required"
                                                    data-bind="value: $root.reporte.dias">
                                            </div>
                                            <div class="col col-md-1">
                                                <label class="label">
                                                    <span class="error-required">*</span><spring:message code="mihabitat.reportes.morosidad.monto" />
                                                </label>
                                                <input class="form-control" type="number" name="monto" id="monto" 
                                                    placeholder="<spring:message code="mihabitat.reportes.morosidad.monto" />"
                                                    required="required"
                                                    data-bind="value: $root.reporte.monto">
                                            </div>
											<div class="col col-md-2">
											    <label class="label">
											        &nbsp;
											    </label>
											    <label class="toggle">
											        <input type="checkbox" name="activo" id="activo" data-bind="checked: $root.reporte.detalleContacto">
											        <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.contacto" />
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
				                <!-- ko if: $root.tipo() == 1 -->
				                    <table id="table-dias" class="table table-striped table-bordered table-hover" style="width: 100%">
				                        <thead>
				                            <tr>
				                                <th data-class="expand"><spring:message code="mihabitat.departamento" /></th>
												<th data-hide="phone"><spring:message code="mihabitat.departamento.etiquetas" /></th>
				                                <th data-hide="phone"><spring:message code="mihabitat.contacto" /></th>
				                                <th><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.total" /></th>
				                                <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.uno" /></th>
				                                <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.treintayuno" /></th>
				                                <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.sesentayuno" /></th>
				                                <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.novetayuno" /></th>
				                                <th data-hide="phone, tablet"><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.mascientoochenta" /></th>
				                            </tr>
				                        </thead>
				                        <tbody data-bind="foreach : { data: $root.reporte.adeudos }">
			                                <tr>
			                                    <td data-bind="text: departamento"></td>
												<td data-bind="text: torresEtiquetas"></td>
												<td data-bind="text: contacto"></td>
												<td>
													<span data-bind="currency: total, symbol: '$'" ></span>
													<span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;">
														&nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
													</span>
												</td>
			                                    <td data-bind="currency: _1_30, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: _31_60, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: _61_90, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: _91_180, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: _181, symbol: '$'" class="currency"></td>
			                                </tr>
				                        </tbody>
				                    </table>
				                <!-- /ko -->
				                <!-- ko if: $root.tipo() == 2 -->
				                    <table id="table-meses" class="table table-striped table-bordered table-hover" style="width: 100%">
				                        <thead>
				                            <tr>
				                                <th data-class="expand"><spring:message code="mihabitat.departamento" /></th>
												<th data-hide="phone"><spring:message code="mihabitat.departamento.etiquetas" /></th>
				                                <th data-hide="phone"><spring:message code="mihabitat.contacto" /></th>
				                                <th><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.total" /></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[0].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[1].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[2].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[3].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[4].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[5].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[6].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[7].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[8].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[9].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[10].substring(0, 3)"></th>
				                                <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[11].substring(0, 3)"></th>
				                            </tr>
				                        </thead>
				                        <tbody data-bind="foreach : { data: $root.reporte.adeudos }">
			                                <tr>
			                                    <td data-bind="text: departamento"></td>
												<td data-bind="text: torresEtiquetas"></td>
												<td data-bind="text: contacto"></td>
												<td>
													<span data-bind="currency: total, symbol: '$'" ></span>
													<span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;">
														&nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
													</span>
												</td>
			                                    <td data-bind="currency: enero, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: febrero, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: marzo, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: abril, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: mayo, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: junio, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: julio, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: agosto, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: septiembre, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: octubre, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: noviembre, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: diciembre, symbol: '$'" class="currency"></td>
			                                </tr>
				                        </tbody>
				                    </table>
				                <!-- /ko -->
				                <!-- ko if: $root.tipo() == 3 -->
				                    <table id="table-anios" class="table table-striped table-bordered table-hover" style="width: 100%">
				                        <thead>
				                            <tr>
				                                <th data-class="expand"><spring:message code="mihabitat.departamento" /></th>
												<th data-hide="phone"><spring:message code="mihabitat.departamento.etiquetas" /></th>
				                                <th data-hide="phone"><spring:message code="mihabitat.contacto" /></th>
				                                <th><spring:message code="mihabitat.reportes.antiguedadcuentasporcobrar.mensual.total" /></th>
				                                <th data-hide="phone, tablet" data-bind="text: $root.reporte.anios()[0]"></th>
				                                <th data-hide="phone, tablet" data-bind="text: $root.reporte.anios()[1]"></th>
				                                <th data-hide="phone, tablet" data-bind="text: $root.reporte.anios()[2]"></th>
				                                <th data-hide="phone, tablet" data-bind="text: $root.reporte.anios()[3]"></th>
				                                <th data-hide="phone, tablet" data-bind="text: $root.reporte.anios()[4]"></th>
				                            </tr>
				                        </thead>
				                        <tbody data-bind="foreach : { data: $root.reporte.adeudos }">
			                                <tr>
			                                    <td data-bind="text: departamento"></td>
												<td data-bind="text: torresEtiquetas"></td>
												<td data-bind="text: contacto"></td>
												<td>
													<span data-bind="currency: total, symbol: '$'" ></span>
													<span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;">
														&nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
													</span>
												</td>
			                                    <td data-bind="currency: anio_1, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: anio_2, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: anio_3, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: anio_4, symbol: '$'" class="currency"></td>
			                                    <td data-bind="currency: anio_5, symbol: '$'" class="currency"></td>
			                                </tr>
				                        </tbody>
				                    </table>
				                <!-- /ko -->
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

	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo-dias.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo-meses.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo-anios.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuentas-cobrar.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/antiguedad-cuentas-cobrar.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/morosidad-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new MorosidadViewModel();
			ko.applyBindings(viewModel);
			$("#fin").datepicker();
		});
	</script>
</body>