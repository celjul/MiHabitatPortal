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
					<spring:message code="mihabitat.cargo" />
				</h2>

				<div class="widget-toolbar">
					<a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/lista" class="btn btn-info"> <i class="fa fa-list"></i> <span class="hidden-mobile"><spring:message code="mihabitat.botones.cargos.irlista"/> </span></a>
				</div>
				<div class="widget-toolbar">
					<a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/nuevo" class="btn btn-success"> <i class="fa fa-plus"></i><span class="hidden-mobile"> <spring:message code="mihabitat.botones.agregar.cargo"/></span> </a>
				</div>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="cargo-form" class="smart-form">
						<fieldset>
							<section class="row">
								<section class="col col-md-2" class="form-group">
									<label class="label"> <span class="error-required">*</span> <spring:message
											code="mihabitat.cargo.tipo" />
									</label> <label class="select"> <select style="width: 100%"
										 name="tipo" id="tipo" required="required"
										data-bind="options: $root.catalogoCargo,
	                                                   optionsText: 'descripcion',
	                                                   optionsValue: 'id',
	                                                   value: $root.cargo.tipo.id,
	                                                   disable : $root.cargo.id(),
	                                                   event: {change: function(item) {
	                                                   		if($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento){
														   		$root.cargo.concepto('Cuota de Mantenimiento - ' +
														   			(AppConfig.catalogos.meses.descripcion.split(',')[moment(new Date()).month()]) + ' ' +
														   			moment(new Date()).format('YYYY'));
														   		$root.cargo.mantenimientoDepartamento(true);
														    } else if($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo){
														   		$root.cargo.concepto(undefined);
														    } else if($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.instalacion){
														   		$root.cargo.concepto('Uso de Instalaciones/Áreas Comunes - ');
														    } else {
														    	$root.cargo.concepto(undefined);
														    }
													    }}">
									</select><i></i>
									</label>

								</section>
								<section class="col col-md-3" class="form-group" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
										<label class="label"><span class="error-required">*</span>
											<spring:message code="mihabitat.consumo.tipo" /> </label> <label
											class="input"> <select style="width: 100%"
																   class="select2" name="consumo" id="consumo"
																   required="required"
																   data-bind="options: $root.consumos,
                                                       optionsCaption : 'Seleccione una opción',
                                                       optionsText: 'nombre',
                                                       optionsValue: 'id',
                                                       value: $root.consumo,
                                                       select2: {},
                                                       disable : $root.cargo.id()">
									</select>
									</label>
								</section>
								<section class="col col-md-4" class="form-group">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.cargo.concepto" />
									</label> <label class="input"> <input class="form-control"
										type="text" name="concepto"
										placeholder="<spring:message code="mihabitat.cargo.concepto" />"
										required="required" maxlength="128"
										data-bind="value: $root.cargo.concepto">
									</label>
								</section>
								<section class="col col-md-3" class="form-group" data-bind="visible: !($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo)">
									<label class="label"> <span class="error-required">*</span>
										<spring:message code="mihabitat.cuenta" />
									</label> <label class="input"> <select style="width: 100%"
										class="select2" name="cuenta" id="cuenta" required="required"
										data-bind="options: $root.cuentas,
                                                        optionsCaption : 'Seleccione una opción',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.cargo.cuenta.id,
                                                        select2: {}">
									</select>
									</label>
								</section>
								<section class="col col-md-2" class="form-group">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.cargo.fecha" />
									</label>
									<div class="input-group">
										<input
											class="form-control bg-color-white" style="cursor: pointer" type="text" name="fecha" id="fecha"
											required="required" readonly="readonly"
											data-bind="value: $root.cargo.fecha">
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</section>
							</section>
							<section class="row">
								<section class="col col-md-5 bg-color-lighten">
									<label class="toggle"> <header class=" bg-color-lighten">
											<spring:message code="mihabitat.cargo.descuento" />
										</header> <input type="checkbox" name="descuento"
										data-bind="checked: $root.cargo.aplicaDescuento"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
									</label>
									<fieldset data-bind="visible: $root.cargo.aplicaDescuento()" class=" bg-color-lighten">
										<section class="row">
											<section class="col col-3 form-group">
												<section class="inline-group">
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
											</section>
											<section class="col col-4">
												<label class="label" data-bind="visible: !$root.cargo.descuento.porcentaje()"> <span class="error-required"  >*</span>
													<spring:message code="mihabitat.cargo.descuento.monto" />
												</label>
												<label class="label" data-bind="visible: $root.cargo.descuento.porcentaje()"> <span class="error-required"  >*</span>
													<spring:message code="mihabitat.cargo.descuento.porcentaje" />
												</label>
												<div class="input-group">
													<span class="input-group-addon" data-bind="visible: !$root.cargo.descuento.porcentaje()">$</span>
													<span class="input-group-addon" data-bind="visible: $root.cargo.descuento.porcentaje()">%</span>
													<input
													class="form-control number" type="text"
													name="descuento-monto" id="montoDescuento"
													required="required" min="0"
													data-bind="value: $root.cargo.descuento.monto">
												</div>
											</section>
											<section class="col col-4">
												<label class="label"> <spring:message
														code="mihabitat.cargo.descuento.fecha" />
												</label>
												<div class="input-group">
													<input class="form-control bg-color-white" style="cursor: pointer" type="text"
														name="descuento-fecha" id="descuento-fecha"
														required="required" readonly="readonly"
														data-bind="value: $root.cargo.descuento.fecha">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
												</div>
											</section>

										</section>
									</fieldset>
								</section>
								<section class="col col-md-1"></section>
								<section class="col col-md-6 bg-color-lighten">
									<label class="toggle"> <header class="bg-color-lighten">
											<spring:message code="mihabitat.cargo.recargo" />
										</header> <input type="checkbox" name="descuento"
										data-bind="checked: $root.cargo.aplicaRecargo"> <i
										data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
										data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
									</label>
									<fieldset class="bg-color-lighten" data-bind="visible: $root.cargo.aplicaRecargo()">
										<section class="row"
											>
											<section class="col col-md-3 form-group">
												<section class="inline-group">
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
												</section>
											</section>
											
											<section class="col col-md-3">
												<label class="label" data-bind="visible: !$root.cargo.recargo.porcentaje()"> <span class="error-required"  >*</span>
													<spring:message code="mihabitat.cargo.recargo.monto" />
												</label>
												
												<label class="label" data-bind="visible: $root.cargo.recargo.porcentaje()"> <span class="error-required"  >*</span>
													<spring:message code="mihabitat.cargo.recargo.porcentaje" />
												</label>
												<div class="input-group">
													<span class="input-group-addon" data-bind="visible: !$root.cargo.recargo.porcentaje()">$</span>
													<span class="input-group-addon" data-bind="visible: $root.cargo.recargo.porcentaje()">%</span>
													<input
														class="form-control number" type="text"
														name="recargo-monto" id="recargo-monto"
														required="required" min="0"
														data-bind="value: $root.cargo.recargo.monto">
												</div>
											</section>
											  <section class="col col-md-2" data-bind="visible: $root.cargo.recargo.porcentaje()">
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
											<section class="col col-md-4">
												<label class="label"> <span class="error-required"  >*</span> <spring:message
														code="mihabitat.cargo.recargo.fecha" />
												</label>
												<div class="input-group">
													<input class="form-control bg-color-white" style="cursor: pointer" type="text"
														name="recargo-fecha" id="recargo-fecha"
														required="required" readonly="readonly"
														data-bind="value: $root.cargo.recargo.fecha">
													<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
												</div>
											</section>
										</section>
										
										<section class="row"
											data-bind="visible: $root.cargo.aplicaRecargo()  && $root.cargo.recargo.porcentaje()">
											<section class="col col-md-4">
												<label class="label"> <span class="error-required">*</span>
													<spring:message code="mihabitat.cargo.recargo.interes" />
												</label><label class="select"> <select style="width: 100%"
													name="tipoInteres" id="tipoInteres"
													required="required"
													data-bind="options: $root.catalogoInteres,
	                                                    optionsText: 'descripcion',
	                                                    optionsValue: 'id',
	                                                    value: $root.cargo.recargo.tipoInteres.id">
												</select><i></i>
												</label>
											</section>
										</section>
									</fieldset>
								</section>
							</section>
							<section class="row">
								<section class="col-sm-12 col-md-12 bg-color-lighten">
									<header class="bg-color-lighten">
										Aplicación del Cargo:
									</header><br>
									<section>
										<section class="col col-md-7"
												 data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento">
											<label class="label"> &nbsp; </label> <label class="toggle">
											<input type="checkbox" name="mantenimiento"
												   data-bind="checked: $root.cargo.mantenimientoDepartamento">
											<i data-swchon-text="Si" data-swchoff-text="No"></i> <spring:message
												code="mihabitat.cargo.recurrente.mantenimiento" />
											</label>
										</section>
										<section class="col col-md-5"
												 data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.mantenimiento"></section>
										<section class="col col-md-2"
												 data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.instalacion ||
												 									$root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.extraordinarios ||
                                                                                    ($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento && !$root.cargo.mantenimientoDepartamento())">
											<label class="label">
												<spring:message code="mihabitat.cargo.monto.todos" />
											</label>
											<div class="input-group">
												<span class="input-group-addon">$</span>
												<input
													class="form-control number money" type="text" name="montoCargo"
													id="montoCargo"
													min="0"
													data-bind="value: $root.cargo.monto.formatted, event: { change: $root.setCargoMonto}">
											</div>
										</section>
									</section>
									<section class="row"
											 data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
										<section class="col col-md-12" class="form-group"
												 data-bind="visible: $root.consumoSimple.catalogoTipo.id() == AppConfig.catalogos.consumos.tipos.simple">
											<section class="col col-md-2" class="form-group">
												<label class="label"> <spring:message
														code="mihabitat.consumo.tipo" />
												</label> <label class="input"> <input class="form-control" style="background-color: #ddd"
																					  type="text" name="tipo-consumo" maxlength="128"
																					  readonly="readonly"
																					  data-bind="value: $root.consumoSimple.catalogoTipo.descripcion">
											</label>
											</section>
											<section class="col col-md-2">
												<label class="label"> <span class="error-required">*</span>
													<spring:message code="mihabitat.cargo.prorrateo.costo" />
												</label>
													<div class="input-group">
														<span class="input-group-addon">$</span>
														<input
														class="form-control number text-align-center" type="text" name="consumoSimpleCosto"
														id="consumoSimpleCosto"
														placeholder="<spring:message code="mihabitat.cargo.prorrateo.costo" />"
														required="required" min="0"
														data-bind="value: $root.consumoSimple.costoUnidad">
														<span class="input-group-addon text-align-right" style="width: 40px" data-bind="text: 'Por ' + ($root.consumoSimple.tipo.aplicaConversion()?$root.consumoSimple.tipo.unidadConversion.descripcion():$root.consumoSimple.tipo.unidad.descripcion())"></span>
													</div>
											</section>
											<%--<section class="col col-md-2" data-bind="visible: $root.consumoSimple.tipo.aplicaConversion()">
												<label class="label">
													<spring:message code="mihabitat.cargo.simple.factor" />
												</label>
												<div class="input-group">
													<span class="input-group-addon text-align-right" style="width: 40px; font-weight: bolder" data-bind="text: '1 ' + $root.consumoSimple.tipo.unidad.descripcion() + ' = ' + $root.consumoSimple.tipo.factorConversion() + ' ' + $root.consumoSimple.tipo.unidadConversion.descripcion()"></span>
														<input
															class="form-control number text-align-center" type="text" style="width: 0px"
														>
												</div>
											</section>--%>
										</section>
										<section class="col col-md-12" class="form-group"
												 data-bind="visible: $root.consumoProrrateo.catalogoTipo.id() == AppConfig.catalogos.consumos.tipos.prorrateo">
											<section class="col col-md-2" class="form-group">
												<label class="label"> <spring:message
														code="mihabitat.consumo.tipo" />
												</label> <label class="input"> <input class="form-control" style="background-color: #ddd"
																					  type="text" name="tipo-consumo" maxlength="128"
																					  readonly="readonly"
																					  data-bind="value: $root.consumoProrrateo.catalogoTipo.descripcion">
											</label>
											</section>
											<section class="col col-md-2">
												<label class="label"> <spring:message
														code="mihabitat.cargo.prorrateo.consumototal" />
												</label>
												<div class="input-group">
													<input
													class="form-control number text-align-center" type="text" name="consumoTotal"
													id="consumoTotal"
													placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumototal" />"
													required="required" min="0"
													data-bind="value:  $root.consumoProrrateo.consumo, event : { change: $root.calculaProrroteo }">
													<span class="input-group-addon text-align-right" style="width: 40px" data-bind="text: $root.consumoProrrateo.tipo.unidad.descripcion()">
													</span>
												</div>
											</section>
											<section class="col col-md-2">
												<label class="label"> <span class="error-required">*</span>
													<spring:message code="mihabitat.cargo.prorrateo.costo" />
												</label>
												<div class="input-group">
													<span class="input-group-addon">$</span>
													<input
													class="form-control number text-align-center" type="text" name="consumoProrrateoCosto"
													id="consumoProrrateoCosto"
													placeholder="<spring:message code="mihabitat.cargo.prorrateo.costo" />"
													required="required" min="0"
													data-bind="value: $root.consumoProrrateo.costoUnidad">
													<span class="input-group-addon text-align-right" style="width: 40px" data-bind="text: 'Por ' + ($root.consumoProrrateo.tipo.aplicaConversion()?$root.consumoProrrateo.tipo.unidadConversion.descripcion():$root.consumoProrrateo.tipo.unidad.descripcion())"></span>
												</div>
											</section>
											<section class="col col-md-2">
												<label class="label"> <spring:message
														code="mihabitat.cargo.prorrateo.total" />
												</label>
												<div class="input-group">
													<span class="input-group-addon">$</span>
													<input
													class="form-control number text-align-right" style="background-color: #ddd" type="text" name="consumoProrrateoTotal"
													id="consumoProrrateoTotal"
													placeholder="<spring:message code="mihabitat.cargo.prorrateo.total" />"
													required="required" min="0" disabled="disabled"
													data-bind="value: numeral($root.consumoProrrateo.total()).format('0,0.00')">
												</div>
											</section>
											<section class="col col-md-2">
												<label class="label"> <spring:message
														code="mihabitat.cargo.prorrateo.factor" />
												</label> <label class="input"> <input style="background-color: #ddd"
																					  class="form-control number money" type="number" name="factor"
																					  id="factor"
																					  placeholder="<spring:message code="mihabitat.cargo.prorrateo.factor" />"
																					  required="required" min="0" disabled="disabled"
																					  data-bind="value: $root.consumoProrrateo.factor">
											</label>
											</section>
										</section>
									</section>


									<%--tabla de cargos--%>











<%--
									<section>
										<section class="col col-md-12">
											<div class="table-responsive ">

												<table id="table-departamentos"
													   class="table table-condensed table-bordered table-hover bg-color-white"
													   style="width: 100%">
													<thead style="color: #333">
													<tr>
														<th ><spring:message
																code="mihabitat.departamento.nombre" /></th>
														<th><spring:message
																code="mihabitat.grupos" /></th>
														<th col-md-3><spring:message
																code="mihabitat.menu.contactos" /></th>
														<th col-md-3
															data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
															<spring:message code="mihabitat.cargo.prorrateo.anterior" />
															<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
														</th>
														<th col-md-3
															data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
															<spring:message code="mihabitat.cargo.prorrateo.nueva" />
															<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
														</th>
														<th col-md-3
															data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
															<spring:message code="mihabitat.cargo.prorrateo.consumo" />
															<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
														</th>
														<th col-md-3
															data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id()"><spring:message
																code="mihabitat.cargo.prorrateo.consumofactor" />
															<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
														</th>
														<th col-md-3><spring:message
																code="mihabitat.cargo.prorrateo.monto" /> ($) </th>
														<td
															data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">


															<label class="checkbox">
																<input type="checkbox"
																	   name="checkbox-inline"
																	   data-bind="checked: seleccionarDepartamentos"> <i></i> &nbsp;&nbsp;<spring:message
																	code="mihabitat.cargo.recurrente.departamentos.todos" />
															</label>

															</label>
														</td>
													</tr>
													</thead>
													<tbody data-bind="foreach : { data: $root.departamentos }">
													<tr>
														<td data-bind="text: nombre" style="text-align:center"></td>
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
																<li style="font-size: 12px;" data-bind="text: id.contacto.nombre + ' ' + id.contacto.apellidoPaterno + ' ' + id.contacto.apellidoMaterno"></li>
																<!--  /ko -->
															</ul>
														</td>
														<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
															<label class="input"> <input
																	class="input-xs number money" type="text" id="anterior"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.anterior" />"
																	required="required" min="0"
																	data-bind="value: cargo.consumo ? cargo.consumo.lecturaAnterior : '',  event:{ change: $root.calcula }, attr:{name: 'anterior_' + id(), id: 'anterior_' + id() }, enable: cargo.editable()"></label>
														</td>
														<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
															<label class="input"><input
																	class="input-xs number money" type="text" name="lectura"
																	id="lectura"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.nueva" />"
																	required="required" min="0"
																	data-bind="value: cargo.consumo ? cargo.consumo.lecturaNueva : '', event:{ change: $root.calcula }, attr:{name: 'lectura_' + id(), id: 'lectura_' + id(), greaterThan: '#anterior_' + id() }, enable: cargo.editable()"></label>
														</td>
														<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
															<label class="input"> <input
																	class="input-xs number money" type="text" name="consumo"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumo" />"
																	required="required" min="0" disabled="disabled"
																	data-bind="value:cargo.consumo ?  cargo.consumo.consumo : '', attr: {name : 'consumo_' + id()}"></label>
														</td>
														<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo" style="color: #333">
															<label class="input"><input
																	class="input-xs number money" type="text"
																	name="consumofactor"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumofactor" />"
																	required="required" min="0" disabled="disabled"
																	data-bind="value: cargo.consumo ? cargo.consumo.consumoFactor : '', attr: {name : 'consumo_factor_' + id()}"></label>
														</td>
														<td data-bind="style: { background : !cargo.editable() ? '#f7f0d9' : '' }" style="color: #333"><label class="input"> <input
																class="input-xs number money" type="text" name="monto"
																placeholder="<spring:message code="mihabitat.cargo.prorrateo.monto" />"
																required="required" min="0"
																data-bind="value: cargo.monto.formatted, attr: {name : 'monto_' + id(), disabled: ($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo?true:false)}, enable: cargo.editable()"></label></td>
														<td style="text-align: center;color: #333"
															data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">
															<label class="checkbox"> <input type="checkbox"
																							name="checkbox-inline" data-bind="checked: seleccionado, disable: !cargo.editable()">
																<i></i>
															</label>
														</td>
													</tr>
													</tbody>
												</table>
											</div>
										</section>
									</section>--%>




											<div class="table-responsive" >

												<table  id="table-departamentos" class="table bg-color-white table-striped table-bordered table-hover" id="table-cargos">
													<thead style="color: #333">
														<tr >
															<th width="10%"><%--<spring:message
																	code="mihabitat.departamento.nombre" />--%></th>
															<th class="torres" width="10%"><%--<spring:message
																	code="mihabitat.grupos" />--%></th>
															<th class="condomino" width="20%"><%--<spring:message
																	code="mihabitat.menu.contactos" />--%></th>
															<th width="10%"
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
																<spring:message code="mihabitat.cargo.prorrateo.anterior" />
																<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
															</th>
															<th width="10%"
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
																<spring:message code="mihabitat.cargo.prorrateo.nueva" />
																<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoSimple.tipo.unidad.descripcion()?$root.consumoSimple.tipo.unidad.descripcion():($root.consumoProrrateo.tipo.unidad.descripcion()?$root.consumoProrrateo.tipo.unidad.descripcion():'')) + 's)'"></span>
															</th>
															<th width="10%"
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
																<spring:message code="mihabitat.cargo.prorrateo.consumo" /><br>
																<%--<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(),
																text: '(' +
																	($root.consumoSimple.tipo.unidad.descripcion()?
																		$root.consumoSimple.tipo.unidad.descripcion():
																			($root.consumoProrrateo.tipo.unidad.descripcion()?
																				$root.consumoProrrateo.tipo.unidad.descripcion():''
																			)
																	) + 's)'"></span>--%>


																<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(),
																text: '(' +
																	($root.consumoSimple.tipo.unidad.descripcion()?
																		($root.consumoSimple.tipo.aplicaConversion()?('1 ' + $root.consumoSimple.tipo.unidad.descripcion() + ' = ' + $root.consumoSimple.tipo.factorConversion() + ' ' + $root.consumoSimple.tipo.unidadConversion.descripcion()):$root.consumoSimple.tipo.unidad.descripcion()):
																			($root.consumoProrrateo.tipo.unidad.descripcion()?
																				($root.consumoProrrateo.tipo.aplicaConversion()?('1 ' + $root.consumoProrrateo.tipo.unidad.descripcion() + ' = ' + $root.consumoProrrateo.tipo.factorConversion() + ' ' + $root.consumoProrrateo.tipo.unidadConversion.descripcion()):$root.consumoProrrateo.tipo.unidad.descripcion()):''
																			)
																	) + 's)'"></span>



															</th>
															<th width="10%"
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id()"><spring:message
																	code="mihabitat.cargo.prorrateo.consumofactor" />
																<span data-bind="visible: $root.consumoSimple.tipo.unidad.descripcion() || $root.consumoProrrateo.tipo.unidad.descripcion(), text: '(' + ($root.consumoProrrateo.tipo.aplicaConversion()?$root.consumoProrrateo.tipo.unidadConversion.descripcion():$root.consumoProrrateo.tipo.unidad.descripcion()) + 's)'"></span>
															</th>
															<th width="10%"><spring:message
																	code="mihabitat.cargo.prorrateo.monto" /> ($) </th>
															<td
																	data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">


																<label class="checkbox">
																	<input type="checkbox"
																		   name="checkbox-inline"
																		   data-bind="checked: seleccionarDepartamentos"> <i></i> &nbsp;&nbsp;<span id="checar"><spring:message
																		code="mihabitat.cargo.recurrente.departamentos.todos" /></span>
																</label>

																</label>
															</td>
														</tr>
													</thead>
													<tbody data-bind="foreach : { data: $root.departamentos }" class="tabladepareult">
														<tr>
															<td data-bind="text: nombre" class="col-md-2 col-xs-4 nombreTitulo" style="text-align:center" id="link"></td>
															<td class="torres" style="text-align: center">
																<!-- ko foreach: { data: grupos } -->
																<div style="margin-top: 5px; display: inline-block;">
																<span class="label-primary"
																	  style="display: inline; padding: .2em .6em .3em; font-size: 75%; font-weight: 700; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: .25em;"
																	  data-bind="text: descripcion"></span>
																</div> <!--  /ko -->
															</td>
															<td class="condomino" style="color: #333">
																<ul style="margin-left: 20px;font-size: 10px;">
																	<!-- ko foreach: { data: contactos } -->
																		<li style="font-size: 12px;" data-bind="text: id.contacto.nombre + ' ' + id.contacto.apellidoPaterno + ' ' + id.contacto.apellidoMaterno"></li>
																	<!--  /ko -->
																</ul>
															</td>
															<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																<label class="input"> <input
																	class="input-xs number money" type="text"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.anterior" />"
																	required="required" min="0"
																	data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,value: cargo.consumo ? cargo.consumo.lecturaAnterior : '',  event:{ change: $root.calcula }, attr:{name: 'anterior_' + id(), id: 'anterior_' + id() }, enable: cargo.editable()"></label>
															</td>
															<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"><input
																	class="input-xs number money" type="text" name="lectura"
																	id="lectura"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.nueva" />"
																	required="required" min="0"
																	data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,value: cargo.consumo ? cargo.consumo.lecturaNueva : '', event:{ change: $root.calcula }, attr:{name: 'lectura_' + id(), id: 'lectura_' + id(), greaterThan: '#anterior_' + id() }, enable: cargo.editable()"></label>
															</td>
															<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"> <input
																	class="input-xs number money" type="text" name="consumo"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumo" />"
																	required="required" min="0" disabled="disabled"
																	data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,
																		value:cargo.consumo ?

																		($root.consumoSimple.tipo.aplicaConversion()?
																			(cargo.consumo.consumo()*$root.consumoSimple.tipo.factorConversion()):
																			($root.consumoProrrateo.tipo.aplicaConversion()?
																				(cargo.consumo.consumo()*$root.consumoProrrateo.tipo.factorConversion()):
																					cargo.consumo.consumo()
																			)
																		) : '',

																		attr: {name : 'consumo_' + id()}"></label>
															</td>
															<td
																data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo" style="color: #333">
																<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo"><input
																	class="input-xs number money" type="text"
																	name="consumofactor"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumofactor" />"
																	required="required" min="0" disabled="disabled"
																	data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo,
																		value: cargo.consumo ? cargo.consumo.consumoFactor : '', attr: {name : 'consumo_factor_' + id()}"></label>
															</td>
															<td class="col-md-2 col-xs-5" data-bind="style: { background : !cargo.editable() ? '#f7f0d9' : '' }"><label class="input"> <input
																	class="input-xs number money" type="text" name="monto"
																	placeholder="<spring:message code="mihabitat.cargo.prorrateo.monto" />"
																	required="required" min="0"
																	data-bind="value: cargo.monto.formatted, attr: {name : 'monto_' + id(), disabled: ($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo?true:false)}, enable: cargo.editable()"></label></td>
															<td
																data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">
																<label class="checkbox"> <input type="checkbox"
																	name="checkbox-inline" data-bind="checked: seleccionado, disable: !cargo.editable()">
																	<i></i>
															</label>
															</td>
														</tr>
													</tbody>
												</table>
											</div>







								 <%--nueva tabla movil--%>








										<%--<div id="tablaCargosResponsive">
											<table class="table  bg-color-white table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="tablaTitulos" ><spring:message code="mihabitat.departamento.nombre" /></th>
														<th class="tablaTitulos" ><spring:message code="mihabitat.grupos" /></th>
														<td class="tablaTitulos"  data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">
															&lt;%&ndash;<strong><spring:message
                                                                    code="mihabitat.botones.aplicara" />&nbsp;&nbsp;</strong>
                                                            &ndash;%&gt;
																<label class="checkbox">
																	<spring:message code="mihabitat.cargo.recurrente.departamentos.todos" />
																	<input type="checkbox" name="checkbox-inline"
																   		data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo,checked: seleccionarDepartamentos"> <i></i> &nbsp;&nbsp;
																</label>
														</td>
													</tr>
												</thead>
												<tbody data-bind="foreach : { data: $root.departamentos }" class="tabladepareult">
													<tr>
														<td id="titulos" data-bind="text: nombre" class="nombreTitulo" style="text-align:center"></td>
														<td>
															<!-- ko foreach: { data: grupos } -->
															<div style="margin-top: 5px; display: inline-block;">
																<span class="label-primary"
																	  style="display: inline; padding: .2em .6em .3em; font-size: 75%; font-weight: 700; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: .25em;"
																	  data-bind="text: descripcion"></span>
															</div> <!--  /ko -->
														</td>
														<td style="color: #333"
															data-bind="visible: $root.cargo.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo">
															<label class="checkbox"> <input type="checkbox"
																							name="checkbox-inline" data-bind="checked: seleccionado, disable: !cargo.editable()">
																<i></i>
															</label>
														</td>

													</tr>

													<tr id="detallesMovil">
														<td COLSPAN="3">
															<ul id="listaDetalle">
																<li style="color: #333">

																		<ul style="margin-left: 20px;font-size: 10px;">
																			<!-- ko foreach: { data: contactos } -->
																			<li style="font-size: 12px;" data-bind="text: id.contacto.nombre + ' ' + id.contacto.apellidoPaterno + ' ' + id.contacto.apellidoMaterno"></li>
																			<!--  /ko -->
																		</ul>


																</li>
																<li><label data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"><spring:message code="mihabitat.cargo.prorrateo.anterior" /></label></li>
																<li
																			data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																		<label class="input"> <input
																				class="input-xs number money" type="text" id="anterior"
																				placeholder="<spring:message code="mihabitat.cargo.prorrateo.anterior" />"
																				required="required" min="0"
																				data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,value: cargo.consumo ? cargo.consumo.lecturaAnterior : '',  event:{ change: $root.calcula }, attr:{name: 'anterior_' + id(), id: 'anterior_' + id() }, enable: cargo.editable()"></label>


																</li>
																<li><label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"><spring:message code="mihabitat.cargo.prorrateo.nueva" /></label></li>
																<li
																			data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																		<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"><input
																				class="input-xs number money" type="text" name="lectura"
																				placeholder="<spring:message code="mihabitat.cargo.prorrateo.nueva" />"
																				required="required" min="0"
																				data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,value: cargo.consumo ? cargo.consumo.lecturaNueva : '', event:{ change: $root.calcula }, attr:{name: 'lectura_' + id(), id: 'lectura_' + id(), greaterThan: '#anterior_' + id() }, enable: cargo.editable()"></label>


																</li>
																<li><label data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"><spring:message code="mihabitat.cargo.prorrateo.consumo" /></label></li>
																<li
																			data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo" style="color: #333">
																		<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo"> <input
																				class="input-xs number money" type="text" name="consumo"
																				placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumo" />"
																				required="required" min="0" disabled="disabled"
																				data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo,value:cargo.consumo ?  cargo.consumo.consumo : '', attr: {name : 'consumo_' + id()}"></label>

																</li>
																<li><label data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo"><spring:message code="mihabitat.cargo.prorrateo.consumofactor" /></label></li>
																<li
																			data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo" style="color: #333">
																		<label class="input" data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo"><input
																				class="input-xs number money" type="text"
																				name="consumofactor"
																				placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumofactor" />"
																				required="required" min="0" disabled="disabled"
																				data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo && $root.consumoProrrateo.catalogoTipo.id() === AppConfig.catalogos.consumos.tipos.prorrateo,value: cargo.consumo ? cargo.consumo.consumoFactor : '', attr: {name : 'consumo_factor_' + id()}"></label>

																</li>
																<li><label data-bind="style: { background : !cargo.editable() ? '#f7f0d9' : '' }" style="color: #333"><label class="input"><spring:message code="mihabitat.cargo.prorrateo.monto" /></label></li>
																<li data-bind="style: { background : !cargo.editable() ? '#f7f0d9' : '' }" style="color: #333"><label class="input"> <input
																			class="input-xs number money" type="text" name="monto"
																			placeholder="<spring:message code="mihabitat.cargo.prorrateo.monto" />"
																			required="required" min="0"
																			data-bind="value: cargo.monto.formatted, attr: {name : 'monto_' + id(), disabled: ($root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo?true:false)}, enable: cargo.editable()"></label>
																</li>
															</ul>
														</td>
													</tr>

												</tbody>
											</table>


										</div>
--%>



									<%--termina nueva tabla--%>




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
								<spring:message code="mihabitat.botones.actualizar"/>
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>





