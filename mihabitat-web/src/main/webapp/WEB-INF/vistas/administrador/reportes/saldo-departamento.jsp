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
                            <spring:message code="mihabitat.reportes.saldodepartamento" />
                        </h2>
                    </header>
                    <form id="reporte-form" class="smart-form">
                        <fieldset>
                            <div class="row">
                                <div class="col col-sm-12 col-md-12 col-lg-12">
                                    <div class="row">
										<div class="col col-md-2">
											<label class="label">
												<span class="error-required">*</span>
												<spring:message code="mihabitat.reportes.saldodepartamento.inicio" />
											</label>
											<div class="input-group">
												<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaInicio"
													   id="fechaInicio"
													   required="required" readonly="readonly"
													   data-bind="value: $root.reporte.inicio, event: { change : $root.consulta }">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
											<br>
										</div>
										<div class="col col-md-2">
											<label class="label">
												<span class="error-required">*</span>
												<spring:message code="mihabitat.reportes.saldodepartamento.fin" />
											</label>
											<div class="input-group">
												<input class="form-control bg-color-white text-align-center" style="cursor: pointer" type="text" name="fechaFin"
													   id="fechaFin"
													   required="required" readonly="readonly"
													   data-bind="value: $root.reporte.fin, event: { change : $root.consulta }">
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
											<br>
										</div>
                                        <%--<div class="col col-md-2">
                                            &nbsp;
                                        </div>
                                        <div class="col col-md-2">
                                            <label class="label">
                                                <span class="error-required">*</span>
                                                <spring:message code="mihabitat.estadocuenta.periodo" />
                                            </label>
                                            <label class="select">
                                                <select style="width: 100%" class="select2" name="periodo" id="periodo"
			                                        required="required" 
			                                        data-bind="options: $root.periodos, value: $root.reporte.fecha, select2: {}, event : { change : $root.consulta }">
			                                    </select>
                                            </label>
                                        </div>--%>
                                        <div class="col col-md-2">
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
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                <div class="col-sm-12 col-md-12 col-lg-12">
                                    <div class="panel-group smart-accordion-default" id="accordion">
                                        <div class="panel panel-default">
                                            <div class="panel-heading no-padding">
			                                    <div class="table-responsive">
			                                        <table id="table-cuentas-cobrar" class="table table-striped table-bordered" style="width: 100%">
			                                            <thead>
			                                                <tr>
			                                                    <th rowspan="2" class="col-sm-1 col-md-1 col-lg-1">&nbsp;</th>
			                                                    <th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.departamento" /></th>
																<th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.departamento.etiquetas" /></th>
			                                                    <th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.anterior" /></th>
																<th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.favoranterior" /></th>
																<th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.concentradoanterior" /></th>
			                                                    <th rowspan="1" colspan="4" style="text-align: center;">
			                                                        <spring:message code="mihabitat.reportes.saldodepartamento.movimientos" />
			                                                    </th>
			                                                    <th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.saldodeudor" /></th>
																<th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.saldofavor" /></th>
																<th rowspan="2" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.saldoconcentrado" /></th>
			                                                </tr>
			                                                <tr>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.cargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.recargos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.descuentos" /></th>
			                                                    <th class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reporte.saldodepartamento.pagos" /></th>
			                                                </tr>
			                                            </thead>
			                                            <tbody>
			                                                <!-- ko foreach: { data: $root.reporte.saldos } -->
			                                                    <tr>
			                                                        <td class="col-md-1 panel-title">
			                                                            <a  data-toggle="collapse" data-bind="attr : { href: '#collapse-movs' + departamento.id() }" class="collapsed">
			                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i> <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
			                                                            </a>
			                                                        </td>
			                                                        <td data-bind="text: departamento.nombre"></td>
																	<td data-bind="foreach: { data: departamento.grupos, as: 'gpo' }">
																		<span data-bind="text: gpo.descripcion"></span>

																	</td>
			                                                        <td data-bind="currency: saldoAnterior, symbol: '$'" class="currency"></td>
																	<td data-bind="currency: saldoFavorAnterior, symbol: '$'" class="currency"></td>
																	<td data-bind="currency: saldoAnterior() - saldoFavorAnterior(), symbol: '$'" class="currency"></td>
			                                                        <td data-bind="currency: totalCargos, symbol: '$'" class="currency"></td>
			                                                        <td data-bind="currency: totalRecargos, symbol: '$'" class="currency"></td>
			                                                        <td data-bind="currency: totalDescuentos, symbol: '$'" class="currency"></td>
			                                                        <td data-bind="currency: totalPagos, symbol: '$'" class="currency"></td>
																	<td data-bind="currency: saldoDeudor, symbol: '$'" class="currency"></td>
																	<td data-bind="currency: saldoFavor, symbol: '$'" class="currency"></td>
																	<td data-bind="currency: saldoDeudor() - saldoFavor(), symbol: '$'" class="currency"></td>
																	<%--<td class="currency">
																		<span data-bind="currency: saldoDeudor, symbol: '$'" ></span>
																		<span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;">
																			&nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
																		</span>
																	</td>--%>
			                                                    </tr>
			                                                    <tr class="panel-collapse collapse" data-bind="attr : { id: 'collapse-movs' + departamento.id()}">
			                                                        <td colspan="2"> &nbsp; </td>
			                                                        <td colspan="7" class="no-padding">
			                                                            <table class="table table-striped table-bordered" style="width: 100%;">
				                                                            <thead>
									                                            <tr class="active">
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.cargo.fecha" />
									                                                </th>
									                                                <th class="col-sm-6 col-md-6 col-lg-6">
									                                                   <spring:message code="mihabitat.cargo.concepto" />
									                                                </th>
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.movimiento.debe" />
									                                                </th>
									                                                <th class="col-sm-2 col-md-2 col-lg-2">
									                                                   <spring:message code="mihabitat.movimiento.haber" />
									                                               </th>
									                                            </tr>
									                                        </thead>
									                                        <tbody>
									                                            <!-- ko foreach: { data: movimientos } -->
									                                                <tr class="active">
										                                                <td data-bind="text: fecha"></td>
				                                                                        <!-- ko if: typeof cargo !== 'undefined' -->
				                                                                            <td data-bind="text: tipo.descripcion + ': ' + cargo.concepto"></td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: typeof movimientoCargo !== 'undefined' -->
				                                                                            <td data-bind="text: tipo.descripcion + ': ' + movimientoCargo.cargo.concepto"></td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: debe || (debe == 0)-->
				                                                                            <td data-bind="currency: debe, symbol: $ " style="text-align: right;"></td>
				                                                                            <td> &nbsp; </td>
				                                                                        <!-- /ko -->
				                                                                        <!-- ko if: haber || (haber == 0)-->
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
			                                            </tbody>
			                                        </table>
				                                </div>
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
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/grupos/grupo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/mantenimientos/mantenimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/email.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/telefono.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/usuarios/usuario.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/personas/persona.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/contactos/contacto.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/contacto-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/departamentos/departamento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/aviso-cobro/aviso-cobro.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/estado-cuenta/estado-cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/saldo-departamento-app.js?v=${project-version}"></script>
	<script type="text/javascript">
		$(function() {
			var viewModel = new SaldoDepartamentoViewModel();
			ko.applyBindings(viewModel);
			$("#fechaInicio").datepicker();
			$("#fechaFin").datepicker();
		});
	</script>
</body>