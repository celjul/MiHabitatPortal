<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Modal -->
<div class="modal fade" id="myModalCuenta" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">Cuenta</h4>
            </div>
            <div class="modal-body">
                <form id="cuenta-form" class="smart-form">
                    <fieldset>
                        <div class="row">
                            <section class="col col-md-6 col-sm-6" class="form-group">
                                <label class="label"><spring:message
                                        code="mihabitat.cuenta.padre"/> </label> <select
                                    style="width: 100%" class="select2" name="cuentaPadre"
                                    id="cuentaPadre"
                                    data-bind="options: $root.cuentaViewModel.cuentasContables,
                                                     	optionsCaption : 'Seleccionar',
 	                                                	optionsText: 'nombre',
 						                            	optionsValue: 'id',
                                                    	value: $root.cuentaViewModel.cuenta.padre.id,
                                                    	select2: {}, disable: $root.cuentaViewModel.actualizarCuentaPadre, event: {change :$root.cuentaViewModel.cuenta.conocerCuenta($root.cuentaViewModel.cuentasContables)}">
                            </select>

                            </section>
                            <section class="col col-md-6 col-sm-6">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cuenta.nombre"/> </label> <label
                                    class="input"> <input class="form-control" type="text"
                                                          name="nombre" id="nombre"
                                                          placeholder="  <spring:message code="mihabitat.cuenta.nombre" />"
                                                          maxlength="128"
                                                          data-bind="value: $root.cuentaViewModel.cuenta.nombre, disable:  !$root.cuentaViewModel.cuenta.padre.id(), event:{ blur: $root.cuentaViewModel.cuenta.validaNombre } ">

                            </label>
                            </section>
                        </div>
                        <div class="row">
                            <fieldset>
                                <legend><spring:message code="mihabitat.cuenta.noCuenta"/></legend>
                                <section class="col col-md-3 col-sm-3 col-xs-3">
                                    <label class="input" id="prueba"> <input
                                            class="form-control number" type="text" name="noCuenta"
                                            id="cuentaPadre" maxlength="4"
                                            data-bind="value: $root.cuentaViewModel.cuenta.numero, disable: $root.cuentaViewModel.cuenta.campoPadre,  attr: { minlength: $root.cuentaViewModel.cuenta.minPadre, required: $root.cuentaViewModel.cuenta.requiredPadre }, hasFocus: $root.cuentaViewModel.cuenta.isSelectedPadre, style: { background : '#efe1b3'}">
                                    </label>
                                </section>
                                <section class="col col-md-3 col-sm-3 col-xs-3">
                                    <label class="input" id="prueba"> <input
                                            class="form-control number" type="text" name="noCuenta"
                                            id="cuentaHija" maxlength="4"
                                            data-bind="value: $root.cuentaViewModel.cuenta.numeroHija, disable: $root.cuentaViewModel.cuenta.campoHija, attr: { minlength: $root.cuentaViewModel.cuenta.minHija, required: $root.cuentaViewModel.cuenta.requiredHija }, hasFocus: $root.cuentaViewModel.cuenta.isSelectedHija, style: { background : $root.cuentaViewModel.cuenta.campoHija() ? '#efe1b3' : 'white'}">

                                    </label>
                                </section>

                                <section class="col col-md-3 col-sm-3 col-xs-3">
                                    <label class="input" id="prueba"> <input
                                            class="form-control number" type="text" name="noCuenta"
                                            id="cuentaNieta" maxlength="4"
                                            data-bind="value: $root.cuentaViewModel.cuenta.numeroNieto, disable: $root.cuentaViewModel.cuenta.campoNieta,  attr: { minlength: $root.cuentaViewModel.cuenta.minNieto, required: $root.cuentaViewModel.cuenta.requiredNieto }, hasFocus: $root.cuentaViewModel.cuenta.isSelectedNieto, style: { background : $root.cuentaViewModel.cuenta.campoNieta() ? '#efe1b3' : 'white'}">

                                    </label>
                                </section>
                                <section class="col col-md-3 col-sm-3 col-xs-3">
                                    <label class="input" id="prueba"> <input
                                            class="form-control number" type="text" name="noCuenta"
                                            id="cuentaBis" maxlength="4"
                                            data-bind="value: $root.cuentaViewModel.cuenta.numeroBis, disable: $root.cuentaViewModel.cuenta.campoBisnieta,  attr: { minlength: $root.cuentaViewModel.cuenta.minBis, required: $root.cuentaViewModel.cuenta.requiredBis }, hasFocus: $root.cuentaViewModel.cuenta.isSelectedBis, style: { background : $root.cuentaViewModel.cuenta.campoBisnieta() ? '#efe1b3' : 'white'}">

                                    </label>
                                </section>
                            </fieldset>
                        </div>
                        <div class="row">
                            <section class="col col-md-6" class="form-group">
                                <label class="label"> <spring:message
                                        code="mihabitat.cuenta.agrupadorSat"/>
                                </label> <select style="width: 100%" class="select2"
                                                 name="agrupadorSat" id="agrupadorSat"
                                                 data-bind="options: $root.cuentaViewModel.agrupadores,
                                                    optionsCaption : 'Seleccionar',
	                                                optionsText: 'nombre',
						                            optionsValue: 'id',
                                                    value: $root.cuentaViewModel.cuenta.agrupadorSat.id,
                                                    select2: {}">
                            </select>
                            </section>
                            <section class="col col-md-6" class="form-group"
                                     data-bind="visible : $root.cuentaViewModel.cuenta.campoBanco">
                                <label class="label"> <spring:message
                                        code="mihabitat.cuenta.bancoSat"/>
                                </label> <select style="width: 100%" class="select2" name="bancoSat"
                                                 id="bancoSat"
                                                 data-bind="options: $root.cuentaViewModel.bancos,
                                                    optionsCaption : 'Seleccione una opción',
	                                                optionsText: 'nombre',
						                            optionsValue: 'id',
                                                    value: $root.cuentaViewModel.cuenta.bancoSat.id,
                                                    select2: {}">
                            </select>
                            </section>
                        </div>
                        <div class="row">
                            <section class="col col-md-6">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cuenta.saldoInicial"/> </label> <label
                                    class="input"> <input type="text"
                                                          class="form-control number money" name="inicial" id="inicial"
                                                          placeholder="  <spring:message code="mihabitat.cuenta.saldoInicial" />"
                                                          min="0" required="required"
                                                          data-bind="value: $root.cuentaViewModel.cuenta.inicial,
                                                          attr: { maxlength: $root.cuentaViewModel.cuenta.maxSaldoInicial},
                                                          enable: $root.cuentaViewModel.cuenta.padre.nombre() == 'BANCOS' || $root.cuentaViewModel.cuenta.padre.nombre() == 'CAJAS',
                                                          event: { change :$root.cuentaViewModel.cuenta.validaSaldoInicial}">
                            </label>
                            </section>
                            <section class="col col-md-6">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cuenta.fecha"/> </label> <label
                                    class="input"> <input class="form-control" type="text"
                                                          name="fecha" id="fecha"
                                                          placeholder="  <spring:message code="mihabitat.cuenta.fecha" />"
                                                          required="required"
                                                          data-bind="value: $root.cuentaViewModel.cuenta.fecha">
                            </label>
                            </section>
                        </div>
                        <div class="row">
                            <section class="col col-md-3" class="form-group">
                                <label class="label"> <spring:message
                                        code="mihabitat.cuenta.activo"/>
                                </label> <label class="toggle"> <input type="checkbox"
                                                                       name="activo"
                                                                       data-bind="checked: $root.cuentaViewModel.cuenta.activo, attr: { disabled: $root.cuentaViewModel.cuenta.activarCuentaSuperPadre}, event: {change :$root.cuentaViewModel.cuenta.verificarPadreActivo}">
                                <i data-swchon-text="ON" data-swchoff-text="OFF"></i>
                                <spring:message code="mihabitat.departamento.activo"/>
                            </label>
                            </section>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancelar
                </button>
                <button style="float: right" type="button" class="btn btn-primary"
                        data-bind="click: $root.guardar, visible: !$root.cuentaViewModel.cuenta.id()">
                    <spring:message code="mihabitat.botones.guardar"/>
                </button>
                <button style="float: right" type="button" class="btn btn-primary"
                        data-bind="click: $root.actualizar, visible: $root.cuentaViewModel.cuenta.id()">
                    <spring:message code="mihabitat.botones.actualizar"/>
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%--
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.menu.cuentas" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="cuenta-form" class="smart-form">
						<fieldset>
							<div class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label"><spring:message
											code="mihabitat.cuenta.padre" /> </label> <select
										style="width: 100%" class="select2" name="cuentaPadre"
										id="cuentaPadre"
										data-bind="options: $root.cuentasContables,
                                                     	optionsCaption : 'Seleccione una opción', 
 	                                                	optionsText: 'nombre', 
 						                            	optionsValue: 'id',  
                                                    	value: cuenta.padre.id,   
                                                    	select2: {}, disable: $root.actualizarCuentaPadre, event: {change :$root.cuenta.conocerCuenta($root.cuentasContables)}">
									</select>

								</section>
								<section class="col col-md-3">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.cuenta.nombre" /> </label> <label
										class="input"> <input class="form-control" type="text"
										name="nombre" id="nombre"
										placeholder="  <spring:message code="mihabitat.cuenta.nombre" />"
										maxlength="128"
										data-bind="value: $root.cuenta.nombre, disable:  !$root.cuenta.padre.id(), event:{ blur: $root.cuenta.validaNombre } ">

									</label>
								</section>
								<section class="col col-md-6">
									<section class="col col-md-2">
										<label class="label"> <spring:message
												code="mihabitat.cuenta.noCuenta" />
										</label> <label class="input" id="prueba"> <input
											class="form-control number" type="text" name="noCuenta"
											id="cuentaPadre" maxlength="4"
											data-bind="value: $root.cuenta.numero, disable: $root.cuenta.campoPadre,  attr: { minlength: $root.cuenta.minPadre, required: $root.cuenta.requiredPadre }, hasFocus: $root.cuenta.isSelectedPadre, style: { background : '#efe1b3'}">

										</label>
									</section>
									<section class="col col-md-2">
										<label class="input" id="prueba"> <input
											class="form-control number" type="text" name="noCuenta"
											id="cuentaHija" maxlength="4" style="margin-top: 24px"
											data-bind="value: $root.cuenta.numeroHija, disable: $root.cuenta.campoHija, attr: { minlength: $root.cuenta.minHija, required: $root.cuenta.requiredHija }, hasFocus: $root.cuenta.isSelectedHija, style: { background : $root.cuenta.campoHija() ? '#efe1b3' : 'white'}">

										</label>
									</section>

									<section class="col col-md-2">
										<label class="input" id="prueba"> <input
											class="form-control number" type="text" name="noCuenta"
											id="cuentaNieta" maxlength="4" style="margin-top: 24px"
											data-bind="value: $root.cuenta.numeroNieto, disable: $root.cuenta.campoNieta,  attr: { minlength: $root.cuenta.minNieto, required: $root.cuenta.requiredNieto }, hasFocus: $root.cuenta.isSelectedNieto, style: { background : $root.cuenta.campoNieta() ? '#efe1b3' : 'white'}">

										</label>
									</section>
									<section class="col col-md-2">
										<label class="input" id="prueba"> <input
											class="form-control number" type="text" name="noCuenta"
											id="cuentaBis" maxlength="4" style="margin-top: 24px"
											data-bind="value: $root.cuenta.numeroBis, disable: $root.cuenta.campoBisnieta,  attr: { minlength: $root.cuenta.minBis, required: $root.cuenta.requiredBis }, hasFocus: $root.cuenta.isSelectedBis, style: { background : $root.cuenta.campoBisnieta() ? '#efe1b3' : 'white'}">

										</label>
									</section>
								</section>
							</div>
							<div class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.cuenta.agrupadorSat" />
									</label> <select style="width: 100%" class="select2"
										name="agrupadorSat" id="agrupadorSat"
										data-bind="options: $root.agrupadores,
                                                    optionsCaption : 'Seleccione una opción',
	                                                optionsText: 'nombre',
						                            optionsValue: 'id',
                                                    value: $root.cuenta.agrupadorSat.id, 
                                                    select2: {}">
									</select>
								</section>
								<section class="col col-md-3" class="form-group"
									data-bind="visible : $root.cuenta.campoBanco">
									<label class="label"> <spring:message
											code="mihabitat.cuenta.bancoSat" />
									</label> <select style="width: 100%" class="select2" name="bancoSat"
										id="bancoSat"
										data-bind="options: $root.bancos,
                                                    optionsCaption : 'Seleccione una opción',
	                                                optionsText: 'nombre',
						                            optionsValue: 'id',
                                                    value: $root.cuenta.bancoSat.id, 
                                                    select2: {}">
									</select>
								</section>

								<section class="col col-md-3">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.cuenta.saldoInicial" /> </label> <label
										class="input"> <input type="text"
										class="form-control number money" name="inicial" id="inicial"
										placeholder="  <spring:message code="mihabitat.cuenta.saldoInicial" />"
										min="0" required="required"
										data-bind="value: $root.cuenta.inicial,attr: { maxlength: $root.cuenta.maxSaldoInicial}, event: {change :$root.cuenta.validaSaldoInicial}">
									</label>
								</section>
								<section class="col col-md-3">
									<label class="label"><span class="error-required">*</span>
										<spring:message code="mihabitat.cuenta.fecha" /> </label> <label
										class="input"> <input class="form-control" type="text"
										name="fecha" id="fecha"
										placeholder="  <spring:message code="mihabitat.cuenta.fecha" />"
										required="required" data-bind="value: $root.cuenta.fecha">
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-md-3" class="form-group">
									<label class="label"> <spring:message
											code="mihabitat.cuenta.activo" />
									</label> <label class="toggle"> <input type="checkbox"
										name="activo"
										data-bind="checked: $root.cuenta.activo, attr: { disabled: $root.cuenta.activarCuentaSuperPadre}, event: {change :$root.cuenta.verificarPadreActivo}">
										<i data-swchon-text="ON" data-swchoff-text="OFF"></i>
									<spring:message code="mihabitat.departamento.activo" />
									</label>
								</section>
							</div>
						</fieldset>
						<footer>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.guardar, visible: !$root.cuenta.id()">
								<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button"
								class="btn btn-primary"
								data-bind="click: $root.actualizar, visible: $root.cuenta.id()">
								<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>--%>
