<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>

<div class="row">
    <article class="col-sm-12 col-md-12 col-lg-12">
        <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-2"
             data-widget-colorbutton="false" data-widget-editbutton="false"
             data-widget-custombutton="false">
            <header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>

                <h2 style="max-width: 50px">
                    <spring:message code="mihabitat.pago"/>
                </h2>
                <!-- ko if: $root.pago.id() -->
                <div class="widget-toolbar pull-left" role="menu">

                    <!-- ko if: $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Pendiente' -->
					<span class="label label-warning bounceIn animated"
                          style="float: right; margin-left: 20px; margin-top: 7px;font-size: 100%"
                          rel="tooltip" data-placement="bottom"
                          data-original-title="<spring:message code="mihabitat.pago.pendiente.mensaje"></spring:message>">
						<%--<spring:message code="mihabitat.pago.estatus"></spring:message>
						---%>
						<spring:message code="mihabitat.pago.pendiente"/>
					</span>
                    <!-- /ko -->
                    <!-- ko if: $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Aprobado' -->
					<span class="label label-success bounceIn animated"
                          style="float: right; margin-left: 20px; margin-top: 7px;font-size: 100%"
                          rel="tooltip" data-placement="bottom"
                          data-original-title="<spring:message code="mihabitat.pago.aprobado.mensaje"></spring:message>">
						<%--<spring:message code="mihabitat.pago.estatus"></spring:message>
						---%>
						<spring:message code="mihabitat.pago.aprobado"/>
					</span>
                    <!-- /ko -->
                    <!-- ko if: $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Rechazado' -->
					<span class="label label-danger bounceIn animated"
                          style="float: right; margin-left: 20px; margin-top: 7px;font-size: 100%"
                          rel="tooltip" data-placement="bottom"
                          data-original-title="<spring:message code="mihabitat.pago.rechazado.mensaje"></spring:message>">
						<%--<spring:message code="mihabitat.pago.estatus"></spring:message>
						---%>
						<spring:message code="mihabitat.pago.rechazado"/>
					</span>
                    <!-- /ko -->
                    <!-- ko if: $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Cancelado' -->
					<span class="label label-danger bounceIn animated"
                          style="float: right; margin-left: 20px; margin-top: 7px;font-size: 100%"
                          rel="tooltip" data-placement="bottom"
                          data-original-title="<spring:message code="mihabitat.pago.cancelado.mensaje"></spring:message>">
						<%--<spring:message code="mihabitat.pago.estatus"></spring:message>
						---%>
						<spring:message code="mihabitat.pago.cancelado"/>
					</span>
                    <!-- /ko -->
                </div>
                <!-- /ko -->
                <div class="widget-toolbar" data-bind="visible: $root.rol == 'administrador'">
                    <a href="${pageContext.request.contextPath}/administrador/pagos/lista" class="btn btn-success"> <i
                            class="fa fa-list"></i>
                        <span class="hidden-mobile">Listado</span>
                    </a>
                </div>
                <div class="widget-toolbar" data-bind="visible: $root.rol == 'contacto'">
                    <a href="${pageContext.request.contextPath}/contacto/mis-pagos/lista" class="btn btn-success"> <i
                            class="fa fa-list"></i>
                        <span class="hidden-mobile">Listado</span>
                    </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">


                    <section class="row" data-bind="visible: $root.existenDescuentos">
                        <section class="col col-md-12">
                            <div class="alert alert-success fade in" style="border-width: 0 0 0 5px!important;">
                                <i class="fa-fw fa fa-check"></i>
                                <strong>Descuentos por Pronto Pago</strong> Hay descuentos disponibles para los cargos,
                                para aplicarlos es necesario cubrir el monto completo.
                            </div>
                        </section>
                    </section>

                    <form id="pago-form" class="smart-form">
                        <fieldset>
                            <section class="col col-md-9">
                                <section class="row">
                                    <sec:authorize access="hasRole('Administrador')">
                                        <section class="col col-md-4" data-bind="visible: !$root.pago.id()">
                                            <label class="label"> <span class="error-required">*</span>
                                                <spring:message code="mihabitat.estadocuenta.datos"/>
                                            </label> <label class="input"> <input class="form-control"
                                                                                  type="text" name="busqueda"
                                                                                  id="busqueda"
                                                                                  placeholder="<spring:message code="mihabitat.estadocuenta.datos" />"
                                                                                  required="required" maxlength="128"
                                                                                  data-bind="value: $root.item.label, valueUpdate : 'keyup'">
                                        </label>
                                        </section>

                                        <section class="col col-md-4" class="form-group"
                                                 data-bind="visible: $root.pago.id()">
                                            <label class="label"> <span class="error-required">*</span>
                                                <spring:message code="mihabitat.contacto"/>
                                            </label> <label class="input"> <select style="width: 100%"
                                                                                   class="select2" name="contacto"
                                                                                   id="contacto"
                                                                                   required="required"
                                                                                   data-bind="options: $root.contactos,
                                                                   optionsCaption : 'Seleccionar',
                                                                   optionsText: 'nombre',
                                                                   optionsValue: 'id',
                                                                   value: $root.pago.contacto.id,
                                                                   select2: {},

                                                                   disable : $root.pago.id()">
                                        </select>
                                        </label>
                                        </section>
                                    </sec:authorize>

                                    <section class="col col-md-3" class="form-group"
                                             data-bind="visible: $root.pago.contacto.id()">
                                        <label class="label"> <span class="error-required">*</span>
                                            <spring:message code="mihabitat.pago.fecha"/>
                                        </label>

                                        <div class="input-group">
                                            <input class="form-control bg-color-white text-align-center"
                                                   style="cursor: pointer" type="text" name="fecha"
                                                   id="fecha"
                                                   placeholder="<spring:message code="mihabitat.pago.fecha" />"
                                                   required="required" readonly="readonly"
                                                   data-bind="value: $root.pago.fecha, event: { change : function() {$root.calculaDescuento();$root.obtenerPendientes()} }, enable: $root.editable()">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        </div>
                                    </section>
                                    <!-- ko if: $root.pago.contacto.id() -->
                                    <section class="col col-md-3" class="form-group">
                                        <label class="label"> <span class="error-required">*</span>
                                            <spring:message code="mihabitat.pago.metodo"/>
                                        </label> <label class="select"> <select style="width: 100%"
                                                                                name="metodo" id="metodo"
                                                                                required="required"
                                                                                data-bind="options: $root.metodosPago,
															   optionsCaption : 'Seleccionar',
															   optionsText: 'descripcion',
															   optionsValue: 'id',
															   value: $root.pago.metodoPago.id,
															   enable: $root.editable()">
                                    </select><i></i>
                                    </label>
                                    </section>
<!--                                     INICIA SECCION PAGO TARJETA -->

                                    <!-- ko if: $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.tarjeta -->
                                    <section class="col col-md-10" class="form-group"
                                             data-bind="visible: (($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.tarjeta">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.tarjeta.nombre" />
                                        </label> <input class="form-control" type="text" name="nombreTarjeta"
                                                        id="nombreTarjeta" required="required"
                                                        placeholder="<spring:message code="mihabitat.pago.tarjeta.nombre" />"
                                                        data-bind="value: $root.pagotarjeta.nombre, enable: $root.editable()">
                                        </label>
                                    </section>

                                    <section class="col col-md-6" class="form-group"
                                             data-bind="visible: (($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.tarjeta">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.tarjeta.no" />
                                        </label> <input class="form-control" type="text" name="numeroTarjeta"
                                                        id="numeroTarjeta" required="required"
                                                        placeholder="<spring:message code="mihabitat.pago.tarjeta.no" />"
                                                        data-bind="value: $root.pagotarjeta.tarjeta, enable: $root.editable()">
                                        </label>
                                    </section>

                                    <section class="col col-md-2" class="form-group"
                                             data-bind="visible: (($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.tarjeta">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.tarjeta.expiracion" />
                                        </label> <input class="form-control" type="number" name="expiracionTarjeta"
                                                        id="expiracionTarjeta" maxlength="4" required="required"
                                                        placeholder="<spring:message code="mihabitat.pago.tarjeta.expiracion" />"
                                                        data-bind="value: $root.pagotarjeta.expiracion, enable: $root.editable()">
                                        </label>
                                    </section>

                                    <section class="col col-md-2" class="form-group"
                                             data-bind="visible: (($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.tarjeta">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.tarjeta.cvv" />
                                        </label> <input class="form-control" type="number" name="cvvTarjeta"
                                                        id="cvvTarjeta" maxlength="3" required="required"
                                                        placeholder="<spring:message code="mihabitat.pago.tarjeta.cvv" />"
                                                        data-bind="value: $root.pagotarjeta.cvv, enable: $root.editable()">
                                        </label>
                                    </section>

									<!--                                     FIN SECCION PAGO TARJETA -->
									<!-- /ko-->
                                    <section class="col col-md-3" class="form-group"
                                             data-bind="visible: (($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.deposito ||
																													  $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.cheque ||
																													  $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.transferencia)
																  && $root.rol == 'contacto')">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.referencia"/>
                                        </label> <input class="form-control" type="text" name="referenciaCont"
                                                        id="referenciaCont"
                                                        placeholder="<spring:message code="mihabitat.pago.referencia" />"
                                                        maxlength="32"
                                                        data-bind="value: $root.pago.referencia, enable: $root.editable()">
                                    </section>
                                    <section class="col col-md-6" class="form-group" data-bind="visible: $root.rol == 'contacto'">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.comentario"/></label>
                                        <label class="textarea textarea-expandable">
															<textarea class="custom-scroll" name="comentarioCont"
                                                                      id="comentarioCont" maxlength="256"
                                                                      data-bind="value: $root.estatus.comentario, enable: $root.editable()"></textarea>
                                        </label>
                                    </section>
                                    <section class="col col-xs-12 col-sm-6 col-md-2"
                                             >
                                        <%--<label class="label"> <span class="error-required">*</span>
                                            <spring:message
                                                    code="mihabitat.pago.monto"/>
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input readonly="readonly"
                                                    class="form-control number text-align-center" type="text"
                                                    name="monto"
                                                    id="monto"
                                                    placeholder="<spring:message code="mihabitat.pago.monto" />"
                                                    required="required" min="0"
                                                    data-bind="value: $root.pago.monto.formatted, enable: ($root.editable() && $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor)">
                                        </div>--%>
                                    </section>
                                    <!-- /ko-->
                                </section>
                                <section class="row" data-bind="visible: ($root.pago.contacto.id() && $root.rol == 'administrador')">

                                    <section class="col col-md-3" class="form-group"
                                             data-bind="visible: $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.deposito ||
																													  $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.cheque ||
																													  $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.transferencia">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.referencia"/>
                                        </label> <input class="form-control" type="text" name="referencia"
                                                        id="referencia"
                                                        placeholder="<spring:message code="mihabitat.pago.referencia" />"
                                                        maxlength="32"
                                                        data-bind="value: $root.pago.referencia, enable: $root.editable()">
                                    </section>
                                    <section class="col col-md-3" class="form-group"
                                             data-bind="visible: $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor">
                                        <sec:authorize access="hasRole('Administrador')">
                                            <label class="label"> <span class="error-required">*</span>
                                                <spring:message
                                                        code="mihabitat.cuenta.bancos.deposito"/>
                                            </label>
                                            <label class="input"> <select style="width: 100%"
                                                                          class="select2" name="cuenta" id="cuenta"
                                                                          required="required"
                                                                          data-bind="options: $root.cuentas,
															   optionsCaption : 'Seleccionar',
															   optionsText: 'nombre',
															   optionsValue: 'id',
															   value: $root.pago.cuenta.id,
															   select2: {}, enable: $root.editable()">
                                            </select>
                                            </label>
                                        </sec:authorize>
                                    </section>
                                    <section class="col col-md-6" class="form-group">
                                        <label class="label"> <spring:message
                                                code="mihabitat.pago.comentario"/></label>
                                        <label class="textarea textarea-expandable">
															<textarea class="custom-scroll" name="comentario"
                                                                      id="comentario" maxlength="256"
                                                                      data-bind="value: $root.estatus.comentario, enable: $root.editable()"></textarea>
                                        </label>
                                    </section>
                                    <!-- /ko-->
                                </section>
                                <!-- ko if: $root.pago.id() -->
                                <section class="row">
                                    <section class="col col-md-12" class="form-group">

                                        <a data-toggle="collapse" href="#comentarios"
                                           aria-expanded="true" aria-controls="comentarios"> <spring:message
                                                code="mihabitat.pago.historico"/>
                                        </a>

                                        <div class="collapse in" id="comentarios">
                                            <div class="well">
                                                <!-- ko if: $root.pago.estatus().length <= 0 -->
                                                <spring:message code="mihabitat.pago.historico.no"/>
                                                <!-- /ko -->
                                                <!-- ko if: $root.pago.estatus().length > 0 -->
                                                <table id="table-comentarios" class="table table-hover"
                                                       style="width: 100%">
                                                    <thead>
                                                    <tr>
                                                        <th class="col-md-1"><spring:message
                                                                code="mihabitat.pago.estatus"/></th>
                                                        <th class="col-md-1"><spring:message
                                                                code="mihabitat.pago.comentario"/></th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <!-- ko foreach: { data: $root.pago.estatus } -->
                                                    <tr>
                                                        <td data-bind="text: estatus.descripcion"></td>
                                                        <td data-bind="text: comentario"></td>
                                                    </tr>
                                                    <!--  /ko -->
                                                    </tbody>
                                                </table>
                                                <!--  /ko -->
                                            </div>
                                        </div>

                                    </section>
                                </section>
                                <!--  /ko -->
                            </section>
                            <!-- ko if: $root.pago.contacto.id() -->
                            <section class="col col-md-3">
                                <section class="col col-md-12" class="form-group">
                                    <label class="label">
                                        <spring:message code="mihabitat.pago.comprobante"/>
                                    </label>

                                    <div id="comprobante" class="dropzoneMH"
                                         style="min-width: 100%;text-align: center;">
                                        <div class="dz-default dz-message">
												<span>
													<span class="text-center">
														<span class="font-lg visible-xs-block visible-sm-block visible-lg-block">
															<span class="font-lg">
																<i class="fa fa-caret-right text-danger"></i>
																Arrastra o da Click aquí para agregar el comprobante
															</span>
														</span>
													</span>
												</span>
                                        </div>
                                    </div>
                                </section>
                            </section>
                            <!-- /ko-->
                        </fieldset>
                        <!-- ko if: $root.pago.contacto.id() && $root.pago.fecha() -->
                        <header style="padding-top: 0px;margin-top: 0px;">
                            Aplicación del Pago - <label style="background-color: #cccccc" data-bind="text: '$' + $root.pago.monto.formatted()"></label>
                        </header>
                        <fieldset>

                            <section class="row">
                                <section class="col col-md-12">
                                    <div class="panel-group smart-accordion-default" id="accordion"
                                         data-bind="visible: ($root.departamentos().length > 0)">
                                        <!-- ko foreach: { data: $root.departamentos, as: 'pd' } -->
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse"
                                                       style="background-color: #353D4B!important; color: #ffffff"
                                                       data-bind="attr : { href: '#collapse' + pd.departamento.id()}"> <i
                                                            class="fa fa-lg fa-angle-down pull-right"></i> <i
                                                            class="fa fa-lg fa-angle-up pull-right"></i> <spring:message
                                                            code="mihabitat.departamento"/> - <span
                                                            data-bind="text: departamento.nombre"></span>

                                                        -
                                                        <span data-bind="visible: !$root.pago.id()"><spring:message
                                                                code="mihabitat.pago.total"/> <span
                                                                data-bind="currency: total, symbol: $"></span>
                                                        </span>
                                                    </a>
                                                </h4>
                                            </div>
                                            <div class="panel-collapse collapse in"
                                                 data-bind="attr : { id: 'collapse' + departamento.id()}">

                                                <div class="panel-body no-padding custom-scroll"
                                                     style="max-height: 180px; overflow-y: auto; background-color: #fafafa;">
                                                    <sec:authorize access="hasRole('Administrador')">
                                                        <%--<a class="btn btn-success pull-right padding-5 margin-top-5 margin-right-5 margin-bottom-5"
                                                           data-toggle="modal" data-target="#cargoSimpleModalModal"
                                                           data-bind="click: $root.nuevoCargo, visible: !$root.pago.id() || ($root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Pendiente')">
                                                            <i class="fa fa-plus"></i>
                                                            <spring:message code="mihabitat.menu.cargos.nuevo" />
                                                        </a>--%>
                                                    </sec:authorize>
                                                    <div class="row">
                                                        <%--<section class="row smart-form" data-bind="visible: $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor &&
                                                                         !($root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() != 'Pendiente')"
                                                                 style="margin-bottom: 0px">
                                                            <div class="col col-xs-12 col-sm-6 col-md-3">
                                                                <div class="col col-md-3">
                                                                    <label class="label" style="padding-top: 10px;">
                                                                        Monto:
                                                                    </label>
                                                                </div>
                                                                <div class="col col-md-9">
                                                                    <div class="input-group"
                                                                         style="padding-top: 5px;padding-bottom: 5px">
                                                                        <span class="input-group-addon">$</span>
                                                                        <input
                                                                                class="form-control number text-align-center"
                                                                                type="text"
                                                                                placeholder="<spring:message code="mihabitat.pago.monto" />"
                                                                                required="required" min="0"
                                                                                data-bind="attr : { id: 'montoDepto' + departamento.id()}, value: monto.formatted,
                                                                    enable: ($root.editable() && $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor),
                                                                    event: { change: function() {$root.calculaAutomaticamenteMontoAplicado(departamento.id())}}">
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <section class="col-md-3"
                                                                     style="margin-bottom: 0px;padding-right: 0px">
                                                                <div class="alert alert-info fade in"
                                                                     style="border-width: 0 0 0 0!important;margin: 0px;">
                                                                    <i class="fa-fw fa fa-money"></i>
                                                                    <strong>Saldo a Favor: </strong> <span
                                                                        data-bind="currency: $root.saldoFavor(), symbol: $"></span>
                                                                </div>
                                                            </section>
                                                            <section class="col-md-3"
                                                                     style="margin-bottom: 0px;padding-right: 0px">
                                                                <div class="alert alert-warning fade in"
                                                                     style="border-width: 0 0 0 0!important;margin: 0px;">
                                                                    <i class="fa-fw fa fa-money"></i>
                                                                    <strong>Saldo Aplicado a Cargos: </strong> <span
                                                                        data-bind="currency: ($root.saldoFavor()-$root.nuevoSaldoFavor()), symbol: $"></span>
                                                                </div>
                                                            </section>
                                                            <section class="col col-md-3"
                                                                     style="margin-bottom: 0px;padding-left: 0px">
                                                                <div class="alert alert-success fade in"
                                                                     style="border-width: 0 0 0 0!important;margin: 0px;">
                                                                    <i class="fa-fw fa fa-money"></i>
                                                                    <strong>Nuevo Saldo a Favor: </strong> <span
                                                                        data-bind="currency: $root.nuevoSaldoFavor(), symbol: $"></span>
                                                                </div>
                                                            </section>
                                                        </section>--%>
                                                        <section class="row"
                                                                 <%--data-bind="visible: $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor"--%>
                                                                 style="margin-bottom: 0px">
                                                            <div class="col col-xs-12 col-sm-6 col-md-3">
                                                                <div class="col col-md-3">
                                                                    <label class="label" style="padding-top: 10px;">
                                                                        Monto:
                                                                    </label>
                                                                </div>
                                                                <div class="col col-md-9">
                                                                    <div class="input-group"
                                                                         style="padding-top: 5px;padding-bottom: 5px">
                                                                        <span class="input-group-addon">$</span>
                                                                        <input
                                                                                class="form-control number text-align-center"
                                                                                type="text"
                                                                                placeholder="<spring:message code="mihabitat.pago.monto" />"
                                                                                required="required" min="0"
                                                                                data-bind="attr : { id: 'montoDepto' + departamento.id()}, value: monto.formatted,
                                                                    enable: ($root.editable() && $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor),
                                                                    event: { change: function() {$root.calculaAutomaticamenteMontoAplicado(departamento.id())}}">
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <section class="col-md-3" style="margin-bottom: 0px;margin-right: 5px">
                                                                <div class="alert alert-warning fade in"
                                                                     style="border-width: 0 0 0 0!important;margin: 0px;">
                                                                    <i class="fa-fw fa fa-minus-circle"></i>
                                                                    <strong>Aplicado a Cargos: </strong>  <span
                                                                        data-bind="currency: (pagadoTemporal()), symbol: $"></span>
                                                                </div>
                                                            </section>
                                                            <section class="col col-md-3"
                                                                     style="margin-bottom: 0px;padding-left: 0px;margin-right: 5px">
                                                                <div class="alert alert-success fade in"
                                                                     style="border-width: 0 0 0 0!important;margin: 0px;">
                                                                    <i class="fa-fw fa fa-plus-circle"></i>
                                                                    <strong data-bind="visible: $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor">Aplicado a Saldo a Favor: </strong>
                                                                    <strong data-bind="visible: $root.pago.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor">Nuevo Saldo a Favor: </strong>
                                                                    <span
                                                                        data-bind="currency: (monto() - pagadoTemporal()), symbol: $"></span>
                                                                </div>
                                                            </section>
                                                        </section>
                                                        <%--<section class="row smart-form" data-bind="visible: ($root.pago.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor &&
                                                                ($root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() != 'Pendiente'))"
                                                                 style="margin-bottom: 0px">
                                                            <div class="col col-xs-12 col-sm-6 col-md-3">
                                                                <div class="col col-md-3">
                                                                    <label class="label" style="padding-top: 10px;">
                                                                        Monto:
                                                                    </label>
                                                                </div>
                                                                <div class="col col-md-9">
                                                                    <div class="input-group"
                                                                         style="padding-top: 5px;padding-bottom: 5px">
                                                                        <span class="input-group-addon">$</span>
                                                                        <input
                                                                                class="form-control number text-align-center"
                                                                                type="text"
                                                                                placeholder="<spring:message code="mihabitat.pago.monto" />"
                                                                                required="required" min="0"
                                                                                data-bind="attr : { id: 'montoDepto' + departamento.id()}, value: monto.formatted,
                                                                    enable: ($root.editable() && $root.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor),
                                                                    event: { change: function() {$root.calculaAutomaticamenteMontoAplicado(departamento.id())}}">
                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </section>--%>
                                                    </div>
                                                    <div class="well" data-bind="visible: !(cargos().length > 0)">
                                                        <!-- ko if: !$root.pago.id() -->
                                                        <span> No se tienen registrados cargos pendientes para este departamento. </span>
                                                        <!-- /ko-->
                                                        <!-- ko if: $root.pago.id() -->
                                                        <span> No se aplicaron abonos a este departamento o no hay cargos pendientes de pago. </span>
                                                        <!-- /ko-->
                                                        <sec:authorize access="hasRole('Administrador')">
                                                            <%--<a class="btn btn-success pull-right padding-5 margin-top-5 margin-right-5 margin-bottom-5"
                                                               data-toggle="modal" data-target="#cargoSimpleModalModal"
                                                               data-bind="click: $root.nuevoCargo, visible: !$root.pago.id() || ($root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Pendiente')">
                                                                <i class="fa fa-plus"></i>
                                                                <spring:message code="mihabitat.menu.cargos.nuevo" />
                                                            </a>--%>
                                                        </sec:authorize>
                                                    </div>
                                                    <table class="table table-bordered table-hover table-cargos bg-color-white"
                                                           data-bind="visible: (cargos().length > 0)"
                                                           style="width: 99%">
                                                        <thead>
                                                        <tr>
                                                            <th width="9%"><spring:message
                                                                    code="mihabitat.cargo.fecha"/></th>
                                                            <th width="18%" data-toggle="true"><spring:message
                                                                    code="mihabitat.cargo.concepto"/></th>

                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.pago.recargos"/></th>
                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.pago.descuentos"/></th>
                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.cargo.tabladepagos.monto"/></th>
                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.pago.recargos"/></th>
                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.pago.descuentos"/></th>
                                                            <th width="9%" data-hide="phone"><spring:message
                                                                    code="mihabitat.pago.pagado"/></th>

                                                            <th width="9%"><spring:message
                                                                    code="mihabitat.pago.saldopendiente"/></th>
                                                            <sec:authorize access="hasRole('Administrador')">
                                                                <th width="9%">
															<span data-bind="visible: $root.editable()">
																<spring:message code="mihabitat.pago.pagar"/>
															</span>
															<span data-bind="visible: !$root.editable()">
																<spring:message code="mihabitat.pago.pagarAprobado"/>
															</span>
                                                                </th>
                                                            </sec:authorize>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <!-- ko foreach: { data: cargos, as: 'crg' } -->
                                                        <tr style="font-size: smaller;"
                                                            data-bind="css: {success: aplicaDescuento() && $root.aplicaDescuento(descuento)}">
                                                            <td>
                                                                <span data-bind="text: (moment(fecha(),'DD-MM-YYYY').format('YYYY-MM-DD'))"></span>
                                                            </td>
                                                            <td>
                                                                <span class="label label-success txt-color-white"
                                                                      style="width: 100px"
                                                                      data-bind="visible: aplicaDescuento() && $root.aplicaDescuento(descuento)">
															    Aplica Descuento</span><span
                                                                    data-bind="text: concepto"></span>

                                                                <%--<span class="badge bg-color-green" style="color: #fff;font-size: 11px;padding: 5px;margin-top: 5px;"
                                                                      data-bind="visible: descuento.id(),
                                                              text: 'Descuento de $' + descuentosPorAplicar() + ' pagando antes de ' + descuento.fecha()"> </span>

                                                                <span class="badge bg-color-red" style="color: #fff;font-size: 11px;padding: 5px;margin-top: 5px;"
                                                                      data-bind="visible: recargo.id(),
                                                              text: 'Recargos de $' + recargosPorAplicar() + ' pagando despues de ' + recargo.fecha()"> </span>--%>


                                                            </td>

                                                            <td data-bind="text: (recargo && recargo.id()) ? (!recargo.porcentaje()?'$':'') + numeral(recargo.monto()).format('0,0.00') +
									                                    (recargo.porcentaje()?'%':'') + ' Pagando despues de ' + recargo.fecha(): 'NA'"
                                                                    ></td>
                                                            <td data-bind="text: (descuento && descuento.id()) ? (!descuento.porcentaje()?'$':'') + numeral(descuento.monto()).format('0,0.00') +
																		(descuento.porcentaje()?'%':'') + ' Pagando antes de ' + descuento.fecha()  : 'NA'"
                                                                    ></td>
                                                            <td data-bind="currency: totalMonto, symbol: $ "
                                                                style="text-align: right;"></td>
                                                            <td data-bind="currency: totalRecargos, symbol: $ "
                                                                style="text-align: right;"></td>
                                                            <td data-bind="currency: totalDescuentos, symbol: $ "
                                                                style="text-align: right;"></td>
                                                            <td data-bind="currency: totalPagado, symbol: $ "
                                                                style="text-align: right;"></td>

                                                            <td style="text-align: right;">
                                                                <a style="cursor: pointer; text-decoration: underline"
                                                                   data-toggle="modal"
                                                                   data-target="#detalleCargoModaldetalleCargoModal"
                                                                   data-bind="currency: saldoPendiente, symbol: $, click: $root.verDetalleMovimientos">
                                                                </a>
                                                            </td>
                                                            <sec:authorize access="hasRole('Administrador')">
                                                                <td>
                                                                    <div class="row">
                                                                        <div class="col col-md-9">
                                                                            <input class="form-control number text-align-center input-xs"
                                                                                   style="font-size: smaller; min-width: 80px"
                                                                                   type="text" name="montoTemporal"
                                                                                   id="montoTemporal"
                                                                                   placeholder="<spring:message code="mihabitat.pago.monto" />"
                                                                                   min="0"
                                                                                   data-bind="value: pagoTemporal.formatted, event : { change : function() {$root.calculaMontosManual(crg, pd.departamento.id());} }, enable: ($root.editable())">
                                                                        </div>
                                                                        <div class="col col-md-2 text-align-center"
                                                                             data-bind="visible: $root.editable()">
                                                                            <a style="cursor: pointer;"
                                                                               data-bind="click: function() {$root.pagarCompleto(crg, pd.departamento.id());}">
                                                                                <i class="fa font-md fa-fw fa-check-circle txt-color-green"></i>
                                                                            </a>
                                                                            <a style="cursor: pointer;"
                                                                               data-bind="click: function() {$root.noPagar(crg, pd.departamento.id());}">
                                                                                <i class="fa font-md fa-fw fa-times-circle txt-color-red"></i>
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </sec:authorize>
                                                        </tr>
                                                        <!-- /ko -->
                                                        </tbody>

                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                </section>
                            </section>
                        </fieldset>
                        <footer>
                            <sec:authorize access="hasRole('Administrador')">
                                <button style="float: right" type="button"
                                        class="btn btn-danger"
                                        data-bind="click: $root.rechazar,
									   visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Pendiente'">
                                    <spring:message code="mihabitat.pago.rechazar"/>
                                </button>
                                <button style="float: right" type="button"
                                        class="btn btn-success"
                                        data-bind="click: $root.aprobar,
									    visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Pendiente'">
                                    <spring:message code="mihabitat.pago.aprobar"/>
                                </button>
                                <button style="float: right" type="button"
                                        class="btn btn-danger"
                                        data-bind="click: $root.cancelar,
                                       visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Aprobado'">
                                    <spring:message code="mihabitat.pago.cancelar"/>
                                </button>
                            </sec:authorize>
                            <sec:authorize access="hasRole('Contacto')">
                                <button style="float: right" type="button"
                                        class="btn btn-warning"
                                        data-bind="click: $root.reenviar,
									   visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Rechazado'">
                                    <spring:message code="mihabitat.pago.reenviar"/>
                                </button>
                            </sec:authorize>
                            <%--<button style="float: left" type="button" class="btn btn-warning"
                                    data-bind="click: $root.descargar, visible: $root.pago.id() && $root.pago.comprobante()">
                                <spring:message code="mihabitat.pago.comprobante.descargar" />
                            </button>--%>
                            <button style="float: left" type="button" class="btn btn-success"
                                    data-bind="click: $root.imprimir,
                                    visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Aprobado'">
                                <spring:message code="mihabitat.pago.imprimir"/>
                            </button>
                            <button style="float: left" type="button" class="btn btn-success"
                                    data-bind="click: $root.abrirModalEnvio,
                                    visible: $root.pago.id() && $root.pago.estatus()[$root.pago.estatus().length - 1].estatus.descripcion() == 'Aprobado'">
                                <spring:message code="mihabitat.pago.enviar"/>
                            </button>
                            <%--<button style="float: right" type="button" onclick=""
                                    class="btn btn-primary"
                                    data-bind="click: $root.guardar, visible: !$root.pago.id()">
                                <spring:message code="mihabitat.botones.siguiente" />
                            </button>--%>
                            <%--<sec:authorize access="hasRole('Administrador')">
                                <a class="btn btn-primary"
                                   data-bind="visible: !$root.pago.id(), click: function(){$root.cambiarTab(2);}">
                                    <spring:message code="mihabitat.botones.aplicacionpago" />
                                </a>
                            </sec:authorize>--%>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.guardar, visible: !$root.pago.id() && !$root.ingresoNI()">
                                <spring:message code="mihabitat.botones.guardar"/>
                            </button>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.guardarAsignacionIngresoNoIdentificado, visible: $root.ingresoNI()">
                                <spring:message code="mihabitat.botones.guardar"/>
                            </button>
                        </footer>
                        <!-- /ko-->
                    </form>


                </div>
            </div>
        </div>
    </article>
</div>
<jsp:include page="envio.jsp"/>
<jsp:include page="guardado.jsp"/>
<!-- Modal Detalle Cargo -->
<div class="modal fade draggable-modal" id="detalleCargoModaldetalleCargoModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="font-weight: bolder"
                    id="myModalLabelDetalleCargo"><spring:message code="mihabitat.estadocuenta.resumen.movimientos"/>
                </h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th class="col-md-2"><spring:message
                                code="mihabitat.movimiento.fecha"/></th>
                        <th class="col-md-4"><spring:message
                                code="mihabitat.movimiento.descripcion"/></th>
                        <th class="col-md-2"><spring:message
                                code="mihabitat.movimiento.debe"/></th>
                        <th class="col-md-2"><spring:message
                                code="mihabitat.movimiento.haber"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- ko foreach: { data: $root.detalleMovimientos, as: 'm' } -->
                    <!-- ko if: (m instanceof MovimientoCargoAplicado ? m.imprimible() : true) -->
                    <tr
                            data-bind="style: { background : (m instanceof MovimientoCargoAplicado && !m.aplicado()) ? '#efe1b3' : '' }">
                        <td data-bind="text: (moment(fecha(),'DD-MM-YYYY').format('YYYY-MM-DD'))"></td>
                        <td data-bind="text: tipo.descripcion"></td>
                        <!-- ko if: debe() -->
                        <td data-bind="currency: debe, symbol: $ "
                            style="text-align: right;"></td>
                        <!-- /ko -->
                        <!-- ko if: !debe() -->
                        <td></td>
                        <!-- /ko -->
                        <!-- ko if: haber() -->
                        <td data-bind="currency: haber, symbol: $ "
                            style="text-align: right;"></td>
                        <!-- /ko -->
                        <!-- ko if: !haber() -->
                        <td></td>
                        <!-- /ko -->
                    </tr>
                    <!-- /ko -->
                    <!-- /ko -->
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <section class="col col-md-4 col-xs-3  pull-right">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="mihabitat.botones.cerrar"/>
                    </button>
                </section>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- Modal Cargo Simple -->
<%--<div class="modal fade draggable-modal" id="cargoSimpleModalModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="font-weight: bolder"
                    id="myModalLabelCargoSimple"><spring:message code="mihabitat.cargo"/>
                </h4>
            </div>
            <div class="modal-body">
                <form id="cargo-form">
                    <div class="modal-body">
                        <section class="row smart-form">
                            <section class="col col-md-4" class="form-group">
                                <label class="label"> <span class="error-required">*</span> <spring:message
                                        code="mihabitat.departamento"/>
                                </label>
                                <label class="select"> <select style="width: 100%"
                                                               name="departamento" id="departamento" required="required"
                                                               data-bind="options: $root.departamentos,
	                                                   optionsText: 'nombre',
	                                                   optionsValue: 'id',
	                                                   value: $root.cargoActual.id">
                                </select><i></i>
                                </label>
                            </section>
                            <section class="col col-md-5" class="form-group">
                                <label class="label"> <span class="error-required">*</span> <spring:message
                                        code="mihabitat.cargo.tipo"/>
                                </label>
                                <label class="select"> <select style="width: 100%"
                                                               name="tipo" id="tipo" required="required"
                                                               data-bind="options: $root.tiposCargo,
	                                                   optionsText: 'descripcion',
	                                                   optionsValue: 'id',
	                                                   value: $root.cargoActual.cargo.tipo.id">
                                </select><i></i>
                                </label>
                            </section>
                            <section class="col col-md-3">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cargo.monto"/>
                                </label>

                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input
                                            class="form-control number text-align-right" type="text" name="montoCargo"
                                            id="montoCargo"
                                            min="0"
                                            data-bind="value: $root.cargoActual.cargo.monto.formatted">
                                </div>
                            </section>

                            <section class="col col-md-4" class="form-group">
                                <label class="label"><span class="error-required">*</span>
                                    <spring:message code="mihabitat.cargo.fecha"/>
                                </label>

                                <div class="input-group">
                                    <input
                                            class="form-control bg-color-white" style="cursor: pointer" type="text"
                                            name="fechaNuevoCargo" id="fechaNuevoCargo"
                                            required="required" readonly="readonly"
                                            data-bind="value: $root.cargoActual.cargo.fecha">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                </div>
                            </section>

                            <section class="col col-md-8" class="form-group">
                                <label class="label"> <span class="error-required">*</span>
                                    <spring:message code="mihabitat.cuenta.ingresos"/>
                                </label> <label class="input"> <select style="width: 100%"
                                                                       class="select2" name="cuentaIngreso"
                                                                       id="cuentaIngreso" required="required"
                                                                       data-bind="options: $root.cuentasIngresos,
                                                        optionsCaption : 'Seleccionar',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.cargoActual.cargo.cuenta.id,
                                                        select2: {}">
                            </select>
                            </label>
                            </section>
                            <section class="col col-md-8" class="form-group">
                                <label class="label"> <span class="error-required">*</span>
                                    <spring:message code="mihabitat.cargo.concepto"/>
                                </label> <label class="input"> <input class="form-control"
                                                                      type="text" name="concepto"
                                                                      placeholder="<spring:message code="mihabitat.cargo.concepto" />"
                                                                      required="required" maxlength="128"
                                                                      data-bind="value: $root.cargoActual.cargo.concepto">
                            </label>
                            </section>
                        </section>
                    </div>
                    <div class="modal-footer">
                        <section class="col col-md-3 col-xs-6  pull-right">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                <spring:message code="mihabitat.botones.cerrar"/>
                            </button>
                        </section>
                        <section class="col col-md-3 col-xs-6 pull-right">
                            <button type="button" class="btn btn-success" data-dismiss="modal"
                                    data-bind="event : {click : $root.guardarNuevoCargo}">
                                <spring:message code="mihabitat.botones.guardar"/>
                            </button>
                        </section>
                    </div>
                </form>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>--%>
<!-- /.modal -->


