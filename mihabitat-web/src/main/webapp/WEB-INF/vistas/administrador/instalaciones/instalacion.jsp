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
					<spring:message code="mihabitat.menu.instalaciones" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="instalacion-form" class="smart-form">
						<fieldset>
                            <section class="row">
								<section class="col col-md-6" class="form-group">
								<section class="row">
								<section class="col col-md-4" class="form-group">
									<div class="container" style="width: 100%; max-width: 200px;">
										<div class="well"  data-bind="fileDrag: $root.instalacion.fileData">
											<div class="form-group row">
												<div class="col col-md-12">
													<img style="height: 125px; max-width: 100%" class="img-rounded  thumb"
														data-bind="attr: { src: instalacion.fileData().dataURL }, visible: instalacion.fileData().dataURL">
													<div data-bind="ifnot: instalacion.fileData().dataURL">
														<label class="drag-label" style="height: 125px;">Arrastra la imagen aqu�</label>
													</div>
												</div>
											</div>
										<input
												class="form-control" type="file" name="file"
												accept="image/*"
												id="fileupload"
												data-bind="fileInput: instalacion.fileData, customFileInput: {
								              buttonClass: 'btn btn-info',
								              clearButtonClass: 'btn btn-warning',
								              onClear: instalacion.onClear
								            }"/>


										</div>
									</div>
								</section>


								<section class="col col-md-8">
									<section class="form-group col col-md-6 col-sm-6" >
										<label class="label">
											<br>
										</label>
										<label class="toggle">
											<input type="checkbox" name="activo" data-bind="checked: $root.instalacion.activo">
											<i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.departamento.activo" />
										</label>

									</section>
									<section class="col col-md-6" class="form-group">
										<label class="label"><span class="error-required">*</span>
											<spring:message code="mihabitat.instalacion.unidad" /> </label> <label
											class="select"> <select name="unidad" id="unidad"
																	required="required"
																	data-bind="options: $root.unidades,
															optionsText: 'descripcion',
															optionsValue: 'id',
															value: $root.instalacion.unidad.id">
									</select><i></i>
									</label>

									</section>

									<section class="col col-md-12">
										<label class="label"><span
										class="error-required">*</span><spring:message
											code="mihabitat.instalacion.nombre" /> </label> <label class="input">
										<input class="form-control" type="text" name="nombre"
										id="nombre"
										placeholder="  <spring:message code="mihabitat.instalacion.nombre" />"
										required="required" maxlength="128"
										data-bind="value: $root.instalacion.nombre">
										</label>
									</section>
									<section class="col col-md-12">
										<label class="label"><span class="error-required">*</span>
											<spring:message
													code="mihabitat.instalacion.maximoReservaciones" /> </label> <label
											class="input"> <input class="form-control" type="text"
																  name="maximoReservaciones" id="maximoReservaciones"
																  placeholder="<spring:message code="mihabitat.instalacion.maximoReservaciones" />"
																  required="required"
																  data-bind="value: $root.instalacion.maximoReservaciones">
										</label>
									</section>

								</section>
								</section>
								<section class="row">
									<section class="col col-md-12" class="form-group">
										<label class="label"> <spring:message
												code="mihabitat.instalacion.descripcion" />
										</label> <label class="input"> <textarea class="form-control"
																				 rows="5" id="descripcion" name="descripcion" maxlength="512"
																				 placeholder="  <spring:message code="mihabitat.instalacion.descripcion" />"
																				 data-bind="value: $root.instalacion.descripcion"></textarea>

									</label>

									</section>
								</section>
								</section>

								<section class="col col-md-6" class="form-group">
								<label class="label"> <spring:message
											code="mihabitat.instalacion.disponibilidad" />
									</label>

										<div class="table-responsive">

											<table class="table table-hover table-striped" >
                                                <thead>
												<th style="text-align: center;">
													<spring:message code="mihabitat.instalacion.disponibilidad.dia" />
												</th>
												<th style="text-align: center;" data-class="expand">
													<spring:message code="mihabitat.instalacion.disponibilidad.activo" />
												</th>
												<th style="text-align: center;min-width: 70px" data-hide="phone">
													<spring:message code="mihabitat.instalacion.disponibilidad.diaCompleto" />
												</th>
												<th style="text-align: center;" >
													<spring:message code="mihabitat.instalacion.disponibilidad.horaInicio" />
												</th>
												<th style="text-align: center;" data-hide="phone">
													<spring:message code="mihabitat.instalacion.disponibilidad.horaFin" />
												</th>
                                                </thead>

												<tbody data-bind="foreach : { data: $root.instalacion.disponibilidades }">
													<tr data-bind="css: {info: !activo()}">
														<td data-bind="text: dia()==0?'<spring:message code="mihabitat.dia.cero"/>':(
														                     dia()==1?'<spring:message code="mihabitat.dia.uno"/>':(
														                     dia()==2?'<spring:message code="mihabitat.dia.dos"/>':(
														                     dia()==3?'<spring:message code="mihabitat.dia.tres"/>':(
														                     dia()==4?'<spring:message code="mihabitat.dia.cuatro"/>':(
														                     dia()==5?'<spring:message code="mihabitat.dia.cinco"/>':(
														                     dia()==6?'<spring:message code="mihabitat.dia.seis"/>':
														                     'never should happend'))))))
														"

                                                            style="padding: 3px;"></td>
														<td style="padding: 3px;text-align: center;">

                                                                <input type="checkbox" name="diaActivo" id="diaActivo" data-bind="checked: activo">


                                                        </td>
														<td style="padding: 3px;text-align: center;">

																<input type="checkbox" name="diaActivo" data-bind="checked: diaCompleto, event: { change: setDiaCompleto}, enable: activo">


														</td>
                                                        <td style="padding: 3px;text-align: center;min-width: 50px">

															<div class="form-group">
																<div class="input-group">
																	<input class="form-control timepicker" type="text" readonly="readonly"
																		   style="min-width: 40px; cursor: pointer"
																		   placeholder="<spring:message code="mihabitat.instalacion.disponibilidad.horaInicio" />"
																		   data-autoclose="true"
																		   required="required"
																		   data-bind="value: horaInicio, event: { change: setDiaCompletoCambioHora},
																		   	enable: activo, style: {backgroundColor: activo() ? '#fff' : '#ccc'},
																		   	attr: {name:'horaInicio'+dia(), horaAnterior: '#horaFin'+dia(), id:'horaInicio'+dia()}"
																			>
																	<span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
																</div>
															</div>
                                                        </td>
                                                        <td style="padding: 3px;text-align: center;min-width: 50px">

															<div class="form-group">
																<div class="input-group">
																	<input class="form-control timepicker" type="text" readonly="readonly"
																		   style="min-width: 40px; cursor: pointer"
																		   placeholder="<spring:message code="mihabitat.instalacion.disponibilidad.horaFin" />"
																		   data-autoclose="true"
																		   required="required"
																		   data-bind="value: horaFin, event: { change: setDiaCompletoCambioHora},
																		   	enable: activo, style: {backgroundColor: activo() ? '#fff' : '#ccc'},
																		   	attr: {name:'horaFin'+dia(), horaPosterior: '#horaInicio'+dia(), id:'horaFin'+dia()}"
																			>
																	<span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
																</div>
															</div>
                                                        </td>
													</tr>

												</tbody>
											</table>

										</div>

							</section>
                            </section>
							<section class="row">


								<section class="col col-md-12" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.instalacion.reglamento" />
									</label> <label class="input"> <textarea class="form-control"
											rows="3" id="reglamento" name="reglamento" maxlength="2048"
											placeholder="  <spring:message code="mihabitat.instalacion.reglamento" />"
											data-bind="value: $root.instalacion.reglamento"></textarea>

									</label>

								</section>
							</section>
							<section class="row">


								<section class="col col-md-6 col-sm-12" class="form-group">

                                    <label class="label">

                                    </label>
                                    <label class="toggle">
                                        <input type="checkbox" name="cobroAutomatico" data-bind="checked: $root.instalacion.cobroAutomatico">
                                        <i data-swchon-text="Si" data-swchoff-text="No"></i><spring:message code="mihabitat.instalacion.cobroAutomatico" />
                                    </label>


								</section>

							</section>

						</fieldset>
						<fieldset data-bind="visible: $root.instalacion.cobroAutomatico">
							<div class="row">

								<section class="col col-md-3" class="form-group">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.instalacion.cuenta" /> </label> <label
										class="input"> <select style="width: 100%"
										class="select2" name="cuenta" id="cuenta" required="required"
										data-bind="options: $root.cuentas,
														optionsCaption : 'Seleccionar',
														optionsText: 'nombre',
														optionsValue: 'id',
														value: $root.instalacion.cuenta.id">
									</select>
									</label>

								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.instalacion.costo" /></label>
									<div class="input-group">
									 	<input class="form-control text-align-right"
											name="costo" id="costo" min="0"
											required="required" data-bind="value: $root.instalacion.costo.formatted">
										<span class="input-group-addon" data-bind="text: 'Por ' + $root.instalacion.unidad.descripcion()"></span>
									</div>
								</section>
							</div>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.instalacion.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.instalacion.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>