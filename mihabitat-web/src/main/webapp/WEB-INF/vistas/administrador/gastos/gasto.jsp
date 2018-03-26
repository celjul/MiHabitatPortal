<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.gasto" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="gasto-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.gastos.proveedor" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="proveedor" id="proveedor" required="required"
										data-bind="options: $root.proveedores,
	                                                   optionsCaption : 'Seleccione una opción',
	                                                   optionsText: 'descripcion',
	                                                   optionsValue: 'id',
	                                                   value: $root.gasto.proveedor.id,
	                                                   select2: {},
	                                                   disable : $root.gasto.id()">
									</select>
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.gastos.fecha" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="fecha" id="fecha"
										placeholder="<spring:message code="mihabitat.gastos.fecha" />"
										required="required" maxlength="10"
										data-bind="value: $root.gasto.fecha">
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.gastos.metodo" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="metodo" id="metodo" required="required"
										data-bind="options: $root.metodosPago,
                                                        optionsCaption : 'Seleccione una opci�n',
                                                        optionsText: 'descripcion',
                                                        optionsValue: 'id',
                                                        value: $root.gasto.metodoPago.id,
                                                        select2: {}">
									</select>
									</label>
								</section>
								<section class="col col-md-3" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.gastos.cuenta" /> - <span data-bind="currency: $root.saldo, symbol: '$'"></span>
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="cuenta" id="cuenta" required="required"
										data-bind="options: $root.bancosCajas,
                                                        optionsCaption : 'Seleccione una opci�n',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.gasto.movimientoGasto.cuenta.id,
                                                        select2: {},
                                                        event: {
                                                        	change: $root.getSaldo
                                                        },
                                                        disable : $root.gasto.id()">
									</select>
									</label>
								</section>
							</section>
							<section class="row">
								<header><spring:message code="mihabitat.gastos.detalles" /></header>
								<article class="col-sm-12 col-md-12 col-lg-12">
									<table id="table-departamentos"
										class="table table-striped table-bordered table-hover"
										style="width: 100%">
										<thead>
											<tr>
												<th class="col col-md-3">
													<label class="label"> <span class="error-required">*</span>
														<spring:message code="mihabitat.gastos.detalles.cuenta" />
													</label>
												</th>
												<th class="col col-md-6">
													<label class="label"> <span class="error-required">*</span>
														<spring:message code="mihabitat.gastos.detalles.concepto" />
													</label>
												</th>
												<th class="col col-md-2">
													<label class="label"> <span class="error-required">*</span>
														<spring:message code="mihabitat.gastos.detalles.monto" />
													</label>
												</th>
												<th class="col col-md-1">
													<a data-bind="click: $root.gasto.agregarDetalle" class="btn btn-default btn-xs">
														<spring:message code="mihabitat.gastos.detalles.agregar" />
													</a>
												</th>
											</tr>
										</thead>
										<tbody data-bind="foreach : { data: $root.gasto.detalles }">
											<tr>
												<td class="col col-md-3">
													<label class="select">
														<select name="cuenta" id="cuenta" required="required" class="input-sm"
															data-bind="options: $root.egresos,
					                                                        optionsCaption : 'Seleccione una opci�n',
					                                                        optionsText: 'nombre',
					                                                        optionsValue: 'id',
					                                                        value: movimientoDetallle.cuenta.id,
					                                                        attr:{ name: 'cuenta-' + $index() }">
														</select><i></i>
													</label>
												</td>
												<td class="col col-md-6">
													<label class="input">
														<input class="input-sm" type="text" name="concepto" id="concepto"
														placeholder="<spring:message code="mihabitat.gastos.detalles.concepto" />"
														required="required"
														data-bind="value: concepto, attr:{ name: 'concepto-' + $index() }">
													</label>
												</td>
												<td class="col col-md-2">
													<label class="input"> 
														<input
															class="input-sm number money" type="text" name="monto" id="monto"
															placeholder="<spring:message code="mihabitat.gastos.detalles.monto" />"
															required="required" min="1"
															data-bind="value: movimientoDetallle.debe, attr:{ name: 'monto-' + $index() }">
													</label>
												</td>
												<td class="col col-md-1">
													<a data-bind="click: $root.gasto.eliminarDetalle" class="btn btn-default btn-xs">
														<spring:message code="mihabitat.botones.eliminar" />
													</a>
												</td>
											</tr>
										</tbody>
									</table>
								</article>
							</section>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.gasto.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.gasto.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
							<button style="float: right" type="button"
									class="btn btn-danger"
									data-bind="click: $root.cancelar, visible: $root.gasto.id()">
								<spring:message code="mihabitat.botones.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>