<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- ko if: $root.cargo.cargo.cancelado() -->
<div class="alert alert-danger alert-block">
    <a class="close" data-dismiss="alert" href="#">X</a>
    <h4 class="alert-heading">
        <spring:message code="mihabitat.pago.estatus"></spring:message>
        <spring:message code="mihabitat.cargo.cancelado"/>
    </h4>
    <spring:message code="mihabitat.cargo.cancelado.mensaje"/>
</div>
<!-- /ko -->

<div class="row">
    <article class="col-sm-12 col-md-12 col-lg-12">
        <div class="jarviswidget" id="wid-id-2"
             data-widget-colorbutton="false" data-widget-editbutton="false"
             data-widget-custombutton="false">
            <header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>

                <h2>
                    <spring:message code="mihabitat.cargo"/>
                    <span data-bind="text: $root.cargo.tipo.id, text: $root.cargo.tipo.descripcion"></span>
                    -
                    <span data-bind="text: $root.cargo.concepto"></span>
                </h2>

                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/cargos-departamentos/lista"
                       class="btn btn-info"> <i class="fa fa-list"></i> <spring:message
                            code="mihabitat.botones.cargos.irlista"/> </a>
                </div>
                <div class="widget-toolbar" role="menu">
                    <div class="label label-danger" rel="tooltip" data-placement="bottom"
                         data-original-title="Saldo Pendiente">
                        <i class="fa fa-dollar"></i> <span
                            data-bind="text: numeral($root.cargo.saldoPendiente()).format('0,0.00')"></span>
                    </div>
                </div>
                <div class="widget-toolbar" role="menu">
                    <div class="label label-info" rel="tooltip" data-placement="bottom"
                         data-original-title="Departamento">
                        <i class="fa fa-building"></i> <span data-bind="text: $root.departamento.nombre"></span>
                    </div>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <form id="cargo-form" class="smart-form">
                        <fieldset>
                            <%--<section class="row">
                                <section class="col col-md-3" class="form-group">
                                    <label class="toggle">
                                    <input type="checkbox" id="activo" name="activo"
                                           data-bind="checked: $root.cargo.activo"> <i
                                        data-swchon-text="Si" data-swchoff-text="No"></i> <spring:message
                                        code="mihabitat.cargo.activo" />
                                </label>
                                </section>
                            </section>--%>
                            <section class="row">
                                <section class="col col-md-2" class="form-group" data-bind="visible: !$root.cargo.id()">
                                    <label class="label"> <spring:message
                                            code="mihabitat.cargo.tipo"/>
                                    </label> <label class="input"> <select style="width: 100%"
                                                                           class="select2" name="tipo" id="tipo"
                                                                           required="required"
                                                                           disabled="disabled">
                                    <option
                                            data-bind="value: $root.cargo.tipo.id, text: $root.cargo.tipo.descripcion"
                                            selected="selected"></option>
                                </select>
                                </label>
                                </section>
                                <section class="col col-md-4" class="form-group">
                                    <label class="label"> <spring:message
                                            code="mihabitat.cargo.concepto"/>
                                    </label> <label class="input"> <input class="form-control"
                                                                          type="text" name="concepto"
                                                                          placeholder="<spring:message code="mihabitat.cargo.concepto" />"
                                                                          required="required" maxlength="128"
                                                                          data-bind="value: $root.cargo.concepto">
                                </label>
                                </section>
                                <section class="col col-md-2" class="form-group">
                                    <label class="label"><span class="error-required">*</span>
                                        <spring:message code="mihabitat.cargo.fecha"/>
                                    </label>

                                    <div class="input-group">
                                        <input
                                                class="form-control bg-color-white" style="cursor: pointer" type="text"
                                                name="fecha" id="fecha"
                                                required="required" readonly="readonly"
                                                data-bind="value: $root.cargo.fecha">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </section>
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <spring:message
                                            code="mihabitat.cuenta"/>
                                    </label>
                                    <label class="input">
                                        <select style="width: 100%"
                                                class="select2" name="cuenta" id="cuenta" required="required"
                                                data-bind="options: $root.cuentas,
                                                     optionsCaption : 'Seleccione una opciï¿½n',
                                                   optionsText: 'nombre',
                                                    optionsValue: 'id',
                                                    value: $root.cargo.cuenta.id
                                                     select2: {}, enable: $root.editable()">
                                        </select>
                                    </label>
                                </section>
                                <section class="col col-md-2" class="form-group"
                                         data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
                                    <label class="label"><span class="error-required">*</span>
                                        <spring:message code="mihabitat.consumo.tipo"/> </label> <label
                                        class="input"> <select style="width: 100%"
                                                               class="select2" name="consumo" id="consumo"
                                                               required="required" disabled="disabled"
                                                               data-bind="options: $root.consumos,
                                                       optionsCaption : 'Seleccione una opciï¿½n',
                                                       optionsText: 'nombre',
                                                       optionsValue: 'id',
                                                       value: $root.cargo.consumo.tipo.id,
                                                       select2: {}">
                                </select>
                                </label>
                                </section>

                                <section class="col col-md-2" class="form-group"
                                         data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
                                    <label class="label"> <span class="error-required">*</span> <spring:message
                                            code="mihabitat.cargo.prorrateo.anterior"/>
                                    </label>

                                    <div class="input-group"><input
                                            class="form-control number text-align-center" type="text" name="anterior"
                                            id="anterior"
                                            placeholder="<spring:message code="mihabitat.cargo.prorrateo.anterior" />"
                                            required="required" min="0"
                                            data-bind="value: $root.cargo.consumo.lecturaAnterior, enable: $root.cargo.editable()">
                                        <span class="input-group-addon text-align-right"
                                              data-bind="text: $root.cargo.consumo.tipo.unidad.descripcion() + 's'">
													</span>
                                    </div>
                                </section>
                                <section class="col col-md-2" class="form-group"
                                         data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
                                    <label class="label"> <span class="error-required">*</span> <spring:message
                                            code="mihabitat.cargo.prorrateo.nueva"/>
                                    </label>

                                    <div class="input-group"><input
                                            class="form-control number text-align-center" type="text"
                                            name="lecturaNueva"
                                            id="lecturaNueva"
                                            placeholder="<spring:message code="mihabitat.cargo.prorrateo.nueva" />"
                                            required="required" min="0"
                                            data-bind="value: $root.cargo.consumo.lecturaNueva, enable: $root.cargo.editable()">
                                        <span class="input-group-addon text-align-right"
                                              data-bind="text: $root.cargo.consumo.tipo.unidad.descripcion() + 's'">
													</span>
                                    </div>
                                </section>

                                <section class="col col-md-2" class="form-group"
                                         data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.cargo.prorrateo.costo"/>
                                    </label>

                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input
                                                class="form-control number text-align-center" type="text"
                                                name="constoUnidad"
                                                id="constoUnidad"
                                                required="required" min="0" disabled="disabled"
                                                data-bind="value: $root.costoUnidad.formatted">
                                        <%--<span class="input-group-addon text-align-right" data-bind="text: 'Por ' + $root.cargo.consumo.tipo.unidad.descripcion()"></span>--%>
                                        <span class="input-group-addon text-align-right" style="width: 40px"
                                              data-bind="text: 'Por ' + ($root.cargo.consumo.tipo.aplicaConversion()?$root.cargo.consumo.tipo.unidadConversion.descripcion():$root.cargo.consumo.tipo.unidad.descripcion())"></span>
                                    </div>
                                </section>

                                <section class="col col-md-2" class="form-group"
                                         data-bind="visible: $root.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo">
                                    <label class="label"> <span class="error-required">*</span> <spring:message
                                            code="mihabitat.cargo.prorrateo.consumo"/>
                                    </label>

                                    <div class="input-group"><input
                                            class="form-control number text-align-center" type="text"
                                            name="consumoProrrateo"
                                            id="consumoProrrateo"
                                            placeholder="<spring:message code="mihabitat.cargo.prorrateo.consumo" />"
                                            required="required" min="0" disabled="disabled"
                                            data-bind="value: $root.cargo.consumo.tipo.aplicaConversion()?($root.cargo.consumo.consumo() * $root.cargo.consumo.tipo.factorConversion()):$root.cargo.consumo.consumo()">
                                        <span class="input-group-addon text-align-right"
                                              data-bind="text: ($root.cargo.consumo.tipo.aplicaConversion()?$root.cargo.consumo.tipo.unidadConversion.descripcion():$root.cargo.consumo.tipo.unidad.descripcion()) + 's'">
													</span>
                                    </div>
                                </section>
                                <section class="col col-md-2">
                                    <label class="label"> <spring:message
                                            code="mihabitat.cargo.montoOriginal"/>
                                    </label>

                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input
                                                class="form-control number text-align-center" type="text" name="monto"
                                                style="float: left" style="display: block"
                                                id="monto"
                                                placeholder="<spring:message code="mihabitat.cargo.monto" />"
                                                required="required" min="0"
                                                data-bind="value: $root.cargo.monto.formatted, enable: $root.cargo.editable()">
                                    </div>
                                </section>
                            </section>
                            <section class="row">
                                <section class="col col-md-5 bg-color-lighten">
                                    <label class="toggle">
                                        <header class=" bg-color-lighten">
                                            <spring:message code="mihabitat.cargo.descuento"/>
                                        </header>
                                        <input type="checkbox" name="descuento"
                                               data-bind="checked: $root.cargo.aplicaDescuento"> <i
                                            data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
                                            data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
                                    </label>
                                    <fieldset data-bind="visible: $root.cargo.aplicaDescuento()"
                                              class=" bg-color-lighten">
                                        <section class="row">
                                            <section class="col col-3 form-group">
                                                <section class="inline-group">
                                                    <label class="radio"> <input type="radio"
                                                                                 name="descuento-porcentaje"
                                                                                 required="required"
                                                                                 data-bind="checkedValue: true, checked: $root.cargo.descuento.porcentaje">
                                                       
                                                        <i></i> <spring:message
                                                                code="mihabitat.cargo.descuento.porcentaje"/>
                                                    </label>
                                                    <label class="radio"> <input type="radio"
                                                                                 name="descuento-porcentaje"
                                                                                 required="required"
                                                                                 data-bind="checkedValue: false, checked: $root.cargo.descuento.porcentaje">
                                                        <i></i> <spring:message
                                                                code="mihabitat.cargo.descuento.monto"/>
                                                    </label>
                                                </section>
                                            </section>
                                            <section class="col col-4">
                                                <label class="label"
                                                       data-bind="visible: !$root.cargo.descuento.porcentaje()"> <span
                                                        class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.descuento.monto"/>
                                                </label>
                                                <label class="label"
                                                       data-bind="visible: $root.cargo.descuento.porcentaje()"> <span
                                                        class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.descuento.porcentaje"/>
                                                </label>

                                                <div class="input-group">
                                                    <span class="input-group-addon"
                                                          data-bind="visible: !$root.cargo.descuento.porcentaje()">$</span>
                                                    <span class="input-group-addon"
                                                          data-bind="visible: $root.cargo.descuento.porcentaje()">%</span>
                                                    <input
                                                            class="form-control number" type="text"
                                                            name="descuento-monto" id="montoDescuento"
                                                            style="float: left" style="display: block"
                                                            required="required" min="0"
                                                            data-bind="value: $root.cargo.descuento.monto">
                                                </div>
                                            </section>
                                            <section class="col col-4">
                                                <label class="label"> <spring:message
                                                        code="mihabitat.cargo.descuento.fecha"/>
                                                </label>

                                                <div class="input-group">
                                                    <input class="form-control bg-color-white" style="cursor: pointer"
                                                           type="text"
                                                           name="descuento-fecha" id="descuento-fecha"
                                                           style="float: left" style="display: block"
                                                           required="required" readonly="readonly"
                                                           data-bind="value: $root.cargo.descuento.fecha">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar"></i></span>
                                                </div>
                                            </section>

                                        </section>
                                    </fieldset>
                                </section>
                                <section class="col col-md-1"></section>
                                <section class="col col-md-6 bg-color-lighten">
                                    <label class="toggle">
                                        <header class="bg-color-lighten">
                                            <spring:message code="mihabitat.cargo.recargo"/>
                                        </header>
                                        <input type="checkbox" name="descuento"
                                               data-bind="checked: $root.cargo.aplicaRecargo"> <i
                                            data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
                                            data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
                                    </label>
                                    <fieldset class="bg-color-lighten" data-bind="visible: $root.cargo.aplicaRecargo()">
                                        <section class="row">
                                            <section class="col col-md-3 form-group">
                                                <section class="inline-group">
                                                    <label class="radio"> <input type="radio"
                                                                                 name="recargo-porcentaje"
                                                                                 required="required"
                                                                                 data-bind="checkedValue: true, checked: $root.cargo.recargo.porcentaje">
                                                        <i></i> <spring:message
                                                                code="mihabitat.cargo.recargo.porcentaje"/>
                                                    </label>
                                                    <label class="radio"> <input type="radio"
                                                                                 name="recargo-porcentaje"
                                                                                 required="required"
                                                                                 data-bind="checkedValue: false, checked: $root.cargo.recargo.porcentaje">
                                                        <i></i> <spring:message
                                                                code="mihabitat.cargo.recargo.monto"/>
                                                    </label>
                                                </section>
                                            </section>
                                            <section class="col col-md-3">
                                                <label class="label"
                                                       data-bind="visible: !$root.cargo.recargo.porcentaje()"> <span
                                                        class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.recargo.monto"/>
                                                </label>
                                                <label class="label"
                                                       data-bind="visible: $root.cargo.recargo.porcentaje()"> <span
                                                        class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.recargo.porcentaje"/>
                                                </label>
                                                
                                             

                                                <div class="input-group">
                                                    <span class="input-group-addon"
                                                          data-bind="visible: !$root.cargo.recargo.porcentaje()">$</span>
                                                    <span class="input-group-addon"
                                                          data-bind="visible: $root.cargo.recargo.porcentaje()">%</span>
                                                    <input
                                                            class="form-control number" type="text"
                                                            name="recargo-monto" id="recargo-monto"
                                                            style="float: left" style="display: block"
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
										<section class="col col-md-4" data-bind= "visible:!$root.cargo.recargo.porcentaje()">
                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.recargo.fecha"/>
                                                </label>

                                                <div class="input-group">
                                                    <input class="form-control bg-color-white" style="cursor: pointer" 
                                                           type="text"
                                                           name="recargo-fecha" id="recargo-fecha"
                                                           style="float: left" style="display: block"
                                                           required="required" readonly="readonly"
                                                           data-bind="value: $root.cargo.recargo.fecha">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar"></i></span>
                                                </div>
                                            </section>
                                            
                                        </section>
                                        <section class="row">
                                                 <section class="col col-md-5" data-bind="visible: $root.cargo.recargo.porcentaje()" >                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.recargo.fecha"/>
                                                </label>

                                                <div class="input-group">
                                                    <input class="form-control bg-color-white" style="cursor: pointer" 
                                                           type="text"
                                                           name="recargo-fecha" id="recargo-fecha"
                                                           style="float: left" style="display: block"
                                                           required="required" readonly="readonly"
                                                           data-bind="value: $root.cargo.recargo.fecha">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar"></i></span>
                                                </div>
                                            </section>
                                            <section class="col col-md-3"  data-bind="visible: $root.cargo.aplicaRecargo() && $root.cargo.recargo.porcentaje()">
                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.cargo.recargo.interes"/>
                                                </label><label class="select"> <select style="width: 100%"
                                                                                       name="tipoInteres"
                                                                                       id="tipoInteres"
                                                                                       style="float: left" style="display: block"
                                                                                       required="required"
                                                                                       data-bind="options: $root.catalogoInteres,
	                                                    optionsCaption : 'Seleccione una opción',
	                                                    optionsText: 'descripcion',
	                                                    optionsValue: 'id',
	                                                    value: $root.cargo.recargo.tipoInteres.id" class="valid">
                                            </select><i></i>
                                            </label>
                                            </section>
                                        </section>
                                    </fieldset>
                                </section>
                            </section>
                        </fieldset>                        <footer>
                            <!-- ko if: !$root.cargo.cargo.cancelado() -->
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.actualizar, visible: $root.cargo.id()">
                                <spring:message code="mihabitat.botones.actualizar"/>
                            </button>
                            <button style="float: right" type="button" class="btn btn-danger"
                                    data-bind="click: $root.cancelarCargo, visible: $root.cargo.id()">
                                <spring:message code="mihabitat.botones.cargo.cancelar"/>
                            </button>
                            <%--<button style="float: left" type="button" class="btn btn-success"
                                data-bind="click: $root.modalDescuento, visible: $root.cargo.id()">
                                <spring:message code="mihabitat.botones.descuento.agregar" />
                            </button>
                            <button style="float: left" type="button" class="btn btn-warning"
                                data-bind="click: $root.modalRecargo, visible: $root.cargo.id()">
                                <spring:message code="mihabitat.botones.recargo.agregar" />
                            </button>--%>
                            <!-- /ko -->
                        </footer>
                    </form>
                    <section class="row smart-form">
                        <section class="col col-md-12 bg-color-lighten">
                            <header class="smart-form bg-color-lighten">
                                Detalle de Movimientos
                                <!-- ko if: !$root.cargo.cargo.cancelado() -->
                                <a class="btn btn-xs btn-success" style="float: right"
                                   data-bind="click: $root.modalDescuento, visible: $root.cargo.id()">
                                    <i class="fa fa-minus-circle"></i>
                                    <spring:message code="mihabitat.botones.descuento.agregar"/>
                                </a>
                                <a class="btn btn-xs btn-warning margin-right-5" style="float: right"
                                   data-bind="click: $root.modalRecargo, visible: $root.cargo.id()">
                                    <i class="fa fa-plus-circle"></i>
                                    <spring:message code="mihabitat.botones.recargo.agregar"/>
                                </a>
                                <!-- /ko -->
                            </header>
                            <fieldset class="bg-color-lighten">
                                <div class="table-responsive">
                                    <table id="table-movimientos"
                                           class="table table-striped table-bordered table-hover bg-color-white"
                                           style="width: 100%">
                                        <thead>
                                        <tr>
                                            <th data-class="expand" class="col-md-1"><spring:message
                                                    code="mihabitat.cargo.fecha"/></th>
                                            <th class="col-md-6"><spring:message
                                                    code="mihabitat.cargo.concepto"/></th>
                                            <th data-hide="phone, tablet" class="col-md-2"><spring:message
                                                    code="mihabitat.movimiento.haber"/></th>
                                            <th data-hide="phone, tablet" class="col-md-2"><spring:message
                                                    code="mihabitat.movimiento.debe"/></th>
                                            <th class="col-md-1"><i
                                                    class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- ko foreach : { data: $root.cargo.movimientos, as: 'm' } -->
                                        <!-- ko if: (m instanceof MovimientoCargoAplicado ? m.imprimible() : true) -->
                                        <tr>
                                            <td data-bind="text: (moment(fecha(), 'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
                                            <td
                                                    data-bind="text: tipo.descripcion() + ': ' + $root.cargo.concepto()"></td>
                                            <!-- ko if: haber() || (haber() == 0)-->
                                            <td data-bind="currency: haber, symbol: $ "
                                                style="text-align: right;"></td>
                                            <!-- /ko -->
                                            <!-- ko if: !haber() && (haber() != 0)-->
                                            <td></td>
                                            <!-- /ko -->
                                            <!-- ko if: debe() || (debe() == 0) -->
                                            <td data-bind="currency: debe, symbol: $ "
                                                style="text-align: right;"></td>
                                            <!-- /ko -->
                                            <!-- ko if: !debe() && (debe() != 0) -->
                                            <td></td>
                                            <!-- /ko -->
                                            <td style="text-align: center">
                                                <!-- ko if: tipo.id() == AppConfig.catalogos.movimientos.tipos.descuento && !cancelado() -->
                                                <a href="javascript:void(0);" class="btn btn-circle btn-danger"
                                                   data-bind="click : $root.cancelarDescuento"> <i
                                                        class="fa fa-trash-o"></i>
                                                </a> <!-- /ko -->
                                                <!-- ko if: tipo.id() == AppConfig.catalogos.movimientos.tipos.recargo && !cancelado() -->
                                                <a href="javascript:void(0);" class="btn btn-circle btn-danger"
                                                   data-bind="click : $root.cancelarRecargo"> <i
                                                        class="fa fa-trash-o"></i>
                                                </a> <!-- /ko -->
                                            </td>
                                        </tr>
                                        <!-- /ko -->
                                        <!-- /ko -->
                                        <tr>
                                            <td class="bg-color-darken txt-color-white"><strong
                                                    data-bind="text: (moment(new Date()).format('YYYY-MM-DD'))"></strong>
                                            </td>
                                            <td class="bg-color-darken txt-color-white"><strong>SALDO TOTAL</strong>
                                            </td>
                                            <td class="bg-color-darken txt-color-white"></td>
                                            <td class="bg-color-darken txt-color-white" style="text-align: right;">
                                                <strong data-bind="currency: $root.cargo.saldoPendiente, symbol: $ ">
                                                </strong>
                                            </td>
                                            <td class="bg-color-darken txt-color-white" style="text-align: center">
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                        </section>
                    </section>
                </div>
            </div>
        </div>
    </article>
</div>
<div id="descuento-modal" class="modal fade" tabindex="-1"
     data-width="300">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">
						<spring:message code="mihabitat.botones.cerrar"/>
					</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="mihabitat.botones.descuento.agregar"/>
                </h4>
            </div>
            <div class="modal-body">
                <form id="descuento-form" class="smart-form">
                    <fieldset>
                        <section class="row">
                            <section class="col col-3">
                                <label class="label"><spring:message
                                        code="mihabitat.cargo.descuento.manual.fecha"/><span
                                        class="error-required">*</span></label> <input
                                    class="form-control bg-color-white" style="cursor: pointer;"
                                    type="text" name="fecha-descuento" id="fecha-descuento"
                                    required="required" readonly="readonly"
                                    data-bind="value: $root.movimiento.fecha">
                            </section>
                            <section class="col col-4">
                                <label class="label"><spring:message
                                        code="mihabitat.cargo.descuento.manual.monto"/><span
                                        class="error-required">*</span></label>

                                <div class="input-group"><span class="input-group-addon">$</span>
                                    <input class="form-control number text-align-center" type="text"
                                           name="montoHaber" id="montoHaber"
                                           required="required" min="0"
                                           data-bind="value: $root.movimiento.haber.formatted">
                                </div>
                            </section>
                        </section>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                        data-bind="click : $root.generarDescuento">
                    <spring:message code="mihabitat.botones.descuento.agregar"/>
                </button>
            </div>
        </div>
    </div>
</div>
<div id="recargo-modal" class="modal fade" tabindex="-1"
     data-width="300">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">
						<spring:message code="mihabitat.botones.cerrar"/>
					</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="mihabitat.botones.recargo.agregar"/>
                </h4>
            </div>
            <div class="modal-body">
                <form id="recargo-form" class="smart-form">
                    <fieldset>
                        <section class="row">
                            <section class="col col-4">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cargo.recargo.manual.fecha"/>
                                </label>

                                <div class="input-group">
                                    <input
                                            class="form-control bg-color-white" style="cursor: pointer" type="text"
                                            name="fecha-recargo" id="fecha-recargo"
                                            required="required" readonly="readonly"
                                            data-bind="value: $root.movimiento.fecha">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                </div>
                                <%--<label class="label"><spring:message
                                        code="mihabitat.cargo.recargo.fecha" /><span
                                    class="error-required">*</span></label> <input class="form-control"
                                    type="text" name="fecha-recargo" id="fecha-recargo"
                                    placeholder="<spring:message code="mihabitat.cargo.recargo.fecha" />"
                                    required="required" readonly="readonly"
                                    data-bind="value: $root.movimiento.fecha">--%>
                            </section>
                            <section class="col col-3">
                                <label class="label"><spring:message
                                        code="mihabitat.cargo.recargo.manual.monto"/><span
                                        class="error-required">*</span></label>

                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input class="form-control number text-align-right" type="text" 
                                           name="montoDebe" id="montoDebe"
                                           required="required" min="0"
                                           data-bind="value: $root.movimiento.debe.formatted">
                                </div>
                            </section>
                        </section>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                        data-bind="click : $root.generarRecargo">
                    <spring:message code="mihabitat.botones.recargo.agregar"/>
                </button>
            </div>
        </div>
    </div>
</div>