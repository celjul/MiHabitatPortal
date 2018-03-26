<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="row">
	<section class="row"></section>
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.cargo.recurrente" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="cargo-form" class="smart-form">
						<fieldset>
							<section class="row" data-bind="visible: $root.cargo.id">
								<section class="col col-md-2" class="form-group"
										>
									 <label class="toggle">
									<input type="checkbox" id="activo" name="activo"
										   data-bind="checked: $root.cargo.activo"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
									<spring:message code="mihabitat.cargo.activo" />
								</label>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.cargo.tipo" />
									</label> <label class="select"> <select style="width: 100%"
										name="tipo" id="tipo"class="form-control" required="required"
										data-bind="options: $root.catalogoCargo,
	                                                   optionsText: 'descripcion',
	                                                   optionsValue: 'id',
	                                                   value: $root.cargo.tipo.id,
	                                                   event : {change : $root.cargo.comprueba},
	                                                   enable: !$root.cargo.id()">
									</select> <i></i>
									</label>
								</section>
								<section class="col col-md-3" class="form-group" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.mantenimiento" />
									</label> <label class="input"> <select style="width: 100%"
																		   class="select2" name="mantenimiento" id="mantenimiento" required="required"
																		   data-bind="options: $root.mantenimientos,
	                                                   optionsCaption : 'Seleccionar',
	                                                   optionsText: function(item) {
														   return item.descripcion() + ' - $' + item.monto();
													    },
	                                                   optionsValue: 'id',
	                                                   value: $root.cargo.mantenimiento.id,
	                                                   select2: {},
	                                                   event : {change : function(item) {
	                                                   		var encontrado = ko.utils.arrayFirst($root.mantenimientos(), function(i) {
																return i.id() == $root.cargo.mantenimiento.id();
															});
														   $root.cargo.concepto('Cuota de Mantenimiento - ' + encontrado.descripcion());
													    }}">
									</select>
									</label>
								</section>
								<section class="col col-md-3" class="form-group" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.instalacion">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.instalacion" />
									</label> <label class="input"> <select style="width: 100%"
																		   class="select2" name="instalacion" id="instalacion" required="required"
																		   data-bind="options: $root.instalaciones,
	                                                   optionsCaption : 'Seleccionar',
	                                                   optionsText: 'nombre',
	                                                   optionsValue: 'id',
	                                                   value: $root.cargo.instalacion.id,
	                                                   select2: {},
	                                                   event : {change : function(item) {
	                                                   		var encontrado = ko.utils.arrayFirst($root.instalaciones(), function(i) {
																return i.id() == $root.cargo.instalacion.id();
															});
														   $root.cargo.concepto('Cuota de Uso de Instalación - ' + encontrado.nombre());
													    }}">
								</select>
								</label>
								</section>
								<section class="col col-md-4 form-group"
									>
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.cargo.concepto" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="concepto"
										placeholder="<spring:message code="mihabitat.cargo.concepto" />"
										required="required" maxlength="128"
										data-bind="value: $root.cargo.concepto">
									</label>
								</section>
								<section class="col col-md-2"
										 data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.mantenimiento">
									<label class="label">
										<span class="error-required">*</span>
										<spring:message code="mihabitat.cargo.recurrente.monto" />
									</label>
									<div class="input-group">
										<span class="input-group-addon">$</span>
										<input
										class="form-control number text-align-right" type="text" name="monto"
										id="monto"
										placeholder="<spring:message code="mihabitat.cargo.recurrente.monto" />"
										required="required" min="1"
										data-bind="value: $root.cargo.monto">
									</div>
								</section>
								<section class="col col-md-3">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.cuenta.ingresos" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="cuenta" id="cuenta" required="required"
										data-bind="options: $root.cuentas,
	                                                    optionsCaption : 'Seleccionar',
	                                                    optionsText: 'nombre',
	                                                    optionsValue: 'id',
	                                                    value: $root.cargo.cuenta.id,
	                                                    select2: {}, event: {change :$root.cuenta.conocerCuenta($root.cuentas)}">
									</select>
									</label>
								</section>
								<%--<section class="col col-md-4 form-group"
									data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento">
									<label class="label"> &nbsp; </label> <label class="toggle">
										<input type="checkbox" name="mantenimiento"
										data-bind="checked: $root.cargo.mantenimientoDepartamento, event: {change : $root.clickTabla}">
										<spring:message
											code="mihabitat.cargo.recurrente.mantenimiento" /> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>

									</label>
								</section>--%>
							</section>
							<section class="row">
								<section class="col col-md-3 form-group bg-color-lighten">
									<section class="col col-md-12">
										<label class="label"><strong> <spring:message
												code="mihabitat.cargo.recurrente.dia" /></strong>
										</label>
										<label class="radio"> <input type="checkbox"
											name="diaCargo" data-bind="checked: $root.cargo.primerDiaMes">
											<i></i> <spring:message
												code="mihabitat.cargo.recurrente.primerDiaMes" />
										</label> <label class="radio"> <input type="checkbox"
											name="diaCargo" data-bind="checked: $root.cargo.ultimoDiaMes">
											<i></i> <spring:message
												code="mihabitat.cargo.recurrente.ultimoDiaMes" />
										</label>
										<label class="radio"> <input type="checkbox"
											name="diaCargo"
											data-bind="checked: $root.cargo.seleccionarDiaMes"> <i></i>
											<spring:message
												code="mihabitat.cargo.recurrente.seleccionarDiaMes" />
										</label>
											<section class="col col-md-6" data-bind="visible: $root.cargo.seleccionarDiaMes">
												<div class="input-group">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
													<input class="form-control" type="number" maxlength="2"
														   name="dia" min="0" max="28"
														   data-bind="value: $root.cargo.dia">
												</div>
											</section>
									</section>
								</section>
								<section class="col col-md-1 form-group">
									</section>
								<section class="col col-md-8 form-group bg-color-lighten">
									<section class="row">
										<section class="col col-4" class="form-group">
											<label class="label">
												<strong><spring:message code="mihabitat.cargo.recurrente.meses" /></strong>
											</label>
											<label class="checkbox">
													<input type="checkbox" name="checkbox-inline" data-bind="checked: seleccionarMeses">
												<i></i> <strong><spring:message code="mihabitat.cargo.recurrente.meses.todos" /></strong>
											</label>
										</section>
										<section class="col col-12" class="form-group">
											<div class="inline-group">
												<!-- ko foreach: $root.catalogoMes -->
												<%--<section class="col col-2" class="form-group">--%>
												<label class="checkbox"> <input type="checkbox"
																				name="checkbox" data-bind="checked: seleccionado">
													<i></i><span data-bind="text: descripcion"></span>
												</label>
												<%--</section>--%>
												<!-- /ko -->
											</div>
										</section>
									</section>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-5 bg-color-lighten">
									<label class="toggle"> <header class="bg-color-lighten">
											<spring:message code="mihabitat.cargo.descuento" />
										</header> <input type="checkbox" name="descuento"
										data-bind="checked: $root.cargo.aplicaDescuento"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
									</label>
									<section class="row"
										data-bind="visible: $root.cargo.aplicaDescuento()">
										<section class="col col-3 form-group">
											<label class="radio"> <input type="radio"
																		 name="descuento-porcentaje" required="required"
																		 data-bind="checkedValue: true, checked: $root.cargo.descuento.porcentaje">
												<i></i> <spring:message
														code="mihabitat.cargo.descuento.porcentaje" />
											</label>

												<label class="radio"> <input type="radio"
																			 name="descuento-porcentaje" required="required"
																			 data-bind="checkedValue: false, checked: $root.cargo.descuento.porcentaje">
													<i></i> <spring:message
															code="mihabitat.cargo.descuento.monto" />
												</label>

										</section>
										<section class="col col-md-4 form-group">
											<label class="label" data-bind="visible: !$root.cargo.descuento.porcentaje()"> <span class="error-required"  >*</span>
												<spring:message code="mihabitat.cargo.descuento.monto" />
											</label>
											<label class="label" data-bind="visible: $root.cargo.descuento.porcentaje()"> <span class="error-required"  >*</span>
												<spring:message code="mihabitat.cargo.descuento.porcentaje" />
											</label>
											<div class="input-group">
												<span class="input-group-addon" data-bind="visible: !$root.cargo.descuento.porcentaje()">$</span>
												<span class="input-group-addon" data-bind="visible: $root.cargo.descuento.porcentaje()">%</span>
												<input class="form-control number text-align-right" type="text"
													   name="descuento-monto" id="montoDescuento"
													   required="required" min="0"
													   data-bind="value: $root.cargo.descuento.monto">
											</div>
										</section>
										<section class="col col-md-4 form-group">
											<label class="label"> <span class="error-required">*</span>
												<spring:message
													code="mihabitat.cargo.recurrente.descuento.dia" />
											</label>
											<div class="input-group">
												<input class="form-control text-align-right" type="number"
													   name="descuento-dia"
													   required="required" min="1" maxlength="2"
													   data-bind="value: $root.cargo.descuento.dia">
												<span class="input-group-addon">Días</span>
											</div>
										</section>

									</section>
								</section>
								<section class="col col-md-1">
									</section>
								<section class="col col-md-6 form-group bg-color-lighten">
									<label class="toggle"> <header class="bg-color-lighten">
											<spring:message code="mihabitat.cargo.recargo" />
										</header> <input type="checkbox" name="recargo"
										data-bind="checked: $root.cargo.aplicaRecargo"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si" />"
										data-swchoff-text="<spring:message code="mihabitat.radio.no" />"></i>
									</label>
									<section class="row"
										data-bind="visible: $root.cargo.aplicaRecargo()">
										<section class="col col-2 form-group">
											<%--<section class="inline-group">--%>
												<label class="radio"> <input type="radio"
																			 name="recargo-porcentaje" required="required"
																			 data-bind="checkedValue: true, checked: $root.cargo.recargo.porcentaje">
													<i></i> <spring:message
															code="mihabitat.cargo.recargo.porcentaje" />
												</label>
												<label class="radio"> <input type="radio"
																			 name="recargo-porcentaje" required="required"
																			 data-bind="checkedValue: false, checked: $root.cargo.recargo.porcentaje">
													<i></i> <spring:message
															code="mihabitat.cargo.recargo.monto" />
												</label>
											<%--</section>--%>
										</section>
										<section class="col col-3 form-group">
											<label class="label" data-bind="visible: !$root.cargo.recargo.porcentaje()"> <span class="error-required">*</span>
												<spring:message code="mihabitat.cargo.recargo.monto" />
											</label>
											<label class="label" data-bind="visible: $root.cargo.recargo.porcentaje()"> <span class="error-required">*</span>
												<spring:message code="mihabitat.cargo.recargo.porcentaje" />
											</label>
											<div class="input-group">
												<span class="input-group-addon" data-bind="visible: !$root.cargo.recargo.porcentaje()">$</span>
												<span class="input-group-addon" data-bind="visible: $root.cargo.recargo.porcentaje()">%</span>
												<input class="form-control number text-align-right" type="text"
													name="recargo-monto" id="recargo-monto"
													required="required" min="0"
													data-bind="value: $root.cargo.recargo.monto">
											</div>
										</section>
										<section class="col col-2 form-group" data-bind="visible: $root.cargo.recargo.porcentaje()">
											<label class= "label" >
									        	<spring:message code="mihabitat.cargo.recargo.redondear"/>
											</label>
										    <label class="toggle">
											 	<input type="checkbox" name="redondear"
													data-bind="checked: $root.cargo.recargo.redondear"> <i
													data-swchon-text="<spring:message code="mihabitat.radio.si" />"
													data-swchoff-text="<spring:message code="mihabitat.radio.no" />"></i>
											</label>						
										</section>
										<section class="col col-4 form-group">
											<label class="label"> <span class="error-required">*</span>
												<spring:message
													code="mihabitat.cargo.recurrente.recargo.dia" />
											</label>
											<div class="input-group">
												<input class="form-control text-align-right" type="number" name="recargo-dia"
													required="required" min="1" maxlength="2"
													data-bind="value: $root.cargo.recargo.dia">
												<span class="input-group-addon">Días</span>
											</div>
										</section>

									<%--</section>
									<section class="row"
										data-bind="visible: $root.cargo.aplicaRecargo()">--%>
										<section class="col col-3 form-group">
											<label class="label"> <span class="error-required">*</span>
												<spring:message code="mihabitat.cargo.recargo.interes" />
											</label>
											<label class="select"> <select style="width: 100%"
												name="tipoInteres" id="tipoInteres"
												required="required"
												data-bind="options: $root.catalogoInteres,
	                                                    optionsText: 'descripcion',
	                                                    optionsValue: 'id',
	                                                    value: $root.cargo.recargo.tipoInteres.id">
											</select> <i></i>
											</label>
										</section>
									</section>
								</section>
							</section>
							<section class="row" data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.mantenimiento">
								<section class="col col-md-12 form-group bg-color-lighten">
									<header class="bg-color-lighten">
										Aplicar Cargo Recurrente a:
									</header>

									<section class="row">
										<section class="col col-md-6 ">
											<label class="radio"> <input type="radio"
																		 name="todosDepartamentos"
																		 data-bind="checkedValue: true, checked: $root.cargo.todos,
																		 event: {change: $root.cargo.dibujarTablaDepartamentos}">
												<i></i> <spring:message
														code="mihabitat.cargo.recurrente.departamentos.todos" />
											</label>
										</section>
										<section class="col col-md-6 ">
											<label class="radio"> <input type="radio"
																		 name="algunosDepartamentos"
																		 data-bind="checkedValue: false, checked: $root.cargo.todos,
																		 event: {change: $root.cargo.dibujarTablaDepartamentos}">
												<i></i> <spring:message
														code="mihabitat.cargo.recurrente.departamentos.algunos" />
											</label>
										</section>
									</section>
									<section>
										<section class="col col-md-12" data-bind="visible: !$root.cargo.todos()">
										<div class="table-responsive">
											<table id="table-departamentos"
												class="table table-striped table-bordered table-hover bg-color-white"
												style="width: 100%">
												<thead style="color: #333">
													<tr>
														<th id="th-departamento"><spring:message
																code="mihabitat.departamento.nombre" /></th>
														<th ><spring:message
																code="mihabitat.menu.grupos" /></th>
														<th><spring:message
																code="mihabitat.menu.contactos" /></th>
														<th>
															<spring:message
																	code="mihabitat.botones.seleccionartodos" />&nbsp;&nbsp;
															<label class="checkbox">
																<input type="checkbox"
																name="checkbox-inline"
																data-bind="checked: seleccionarDepartamentos"> <i></i>
															</label>
														</th>
													</tr>
												</thead>
												<tbody data-bind="foreach : { data: $root.departamentos }">
													<tr>
														<td data-bind="text: nombre" style="color: #333"></td>
														<td>
															<!-- ko foreach: { data: grupos } -->
															<div style="margin-top: 5px; display: inline-block;">
																<span class="label-primary"
																	style="display: inline; padding: .2em .6em .3em; font-size: 75%; font-weight: 700; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: .25em;"
																	data-bind="text: descripcion"></span>
															</div> <!--  /ko -->
														</td>
														<td style="color: #333">
															<ul style="margin-left: 20px;font-size: 10px;">
																<!-- ko foreach: { data: contactos } -->
																<li style="font-size: 12px;" data-bind="text: id.contacto.nombre() + ' ' + id.contacto.apellidoPaterno() + ' ' + id.contacto.apellidoMaterno()"></li>
																<!--  /ko -->
															</ul>
														</td>
														<td style="text-align: center">
															<div class="smart-form">
																<label class="checkbox"> <input type="checkbox"
																	name="checkbox-inline"
																	data-bind="checked: seleccionado"> <i></i>
																</label>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
										</section>
									</section>
								</section>
							</section>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.cargo.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.cargo.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>

