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
                    <spring:message code="mihabitat.ingresonoidentificado" />
                </h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/ingresosnoidentificados/lista" class="btn btn-success"> <i class="fa fa-list"></i> Listado </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <form id="ingresoNoIdentificado-form" class="smart-form">
                        <fieldset>
                            <section class="row">
                                <section class="col col-md-2" class="form-group">
                                    <label class="label"><span class="error-required">*</span>
                                        <spring:message code="mihabitat.ingresonoidentificado.fecha" />
                                    </label>
                                    <div class="input-group">
                                        <input
                                                class="form-control bg-color-white" style="cursor: pointer" type="text" name="fecha" id="fecha"
                                                required="required" readonly="readonly"
                                                data-bind="value: $root.ingresoNoIdentificado.fecha, disable : $root.ingresoNoIdentificado.id()">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </section>
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.ingresonoidentificado.metododepago" />
                                    </label> <label class="select"> <select style="width: 100%"
                                                                           name="metodo" id="metodo" required="required"
                                                                           data-bind="options: $root.metodosPago,
                                                        optionsText: 'descripcion',
                                                        optionsValue: 'id',
                                                        value: $root.ingresoNoIdentificado.metodoPago.id">
                                        </select><i></i>
                                    </label>
                                </section>
                                <section class="col col-md-3" class="form-group"
                                         data-bind="visible: $root.ingresoNoIdentificado.metodoPago.id() == AppConfig.catalogos.metodos.deposito ||
										                                                                      $root.ingresoNoIdentificado.metodoPago.id() == AppConfig.catalogos.metodos.cheque ||
										                                                                      $root.ingresoNoIdentificado.metodoPago.id() == AppConfig.catalogos.metodos.transferencia">
                                    <label class="label"> <spring:message
                                            code="mihabitat.ingresonoidentificado.referencia" />
                                    </label> <input class="form-control" type="text" name="referencia"
                                                    id="referencia"
                                                    placeholder="<spring:message code="mihabitat.ingresonoidentificado.referencia" />"
                                                    maxlength="32"
                                                    data-bind="value: $root.ingresoNoIdentificado.referencia">
                                </section>
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.ingresonoidentificado.cuenta" />
                                    </label> <label class="select"> <select style="width: 100%"
                                                                           name="cuenta" id="cuenta" required="required"
                                                                           data-bind="options: $root.bancosCajas,
                                                        optionsCaption : 'Seleccionar',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.ingresoNoIdentificado.cuentaBanco.id">
                                        </select><i></i>
                                    </label>
                                </section>
                            </section>
                            <section class="row">
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.ingresonoidentificado.cuentaIngreso" />
                                    </label> <label class="select"> <select style="width: 100%"
                                                                           name="cuentaIngresos" id="cuentaIngresos" required="required"
                                                                           data-bind="options: $root.cuentasIngresos,
                                                        optionsCaption : 'Seleccionar',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.ingresoNoIdentificado.cuentaIngreso.id
                                                        select2: {}, enable: $root.editable(),
                                                        disable : $root.ingresoNoIdentificado.id()">
                                </select><i></i>
                                </label>
                                </section>
                                <section class="col col-md-2" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.ingresonoidentificado.monto" />
                                    </label>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input
                                                class="form-control number text-align-right" type="text" name="monto"
                                                id="monto"
                                                min="0"
                                                data-bind="value: $root.ingresoNoIdentificado.monto.formatted">
                                    </div>
                                </section>
                            <%--</section>
                            <section class="row">--%>
                                <section class="col col-md-5" class="form-group">
                                    <label class="label"> <spring:message
                                            code="mihabitat.ingresonoidentificado.comentario" /></label>
                                    <label class="textarea textarea-expandable">
															<textarea class="custom-scroll" name="comentario"
                                                                      id="comentario" maxlength="256"
                                                                      data-bind="value: $root.ingresoNoIdentificado.comentario"></textarea>
                                    </label>
                                </section>
                            </section>

                        </fieldset>
                        <footer>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.guardar, visible: !$root.ingresoNoIdentificado.id()">
                                <spring:message code="mihabitat.botones.guardar" />
                            </button>
                            <button style="float: right" type="button"
                                    class="btn btn-danger"
                                    data-bind="click: $root.cancelar, visible: ($root.ingresoNoIdentificado.id() &&
                                        ($root.ingresoNoIdentificado.estatus.id() ==  AppConfig.catalogos.estatusotrosingresos.registrado))">
                                <spring:message code="mihabitat.botones.cancelar" />
                            </button>
                            <button style="float: right" type="button"
                                    class="btn btn-info" data-toggle="modal" data-target="#asignarAbono"
                                    data-bind="visible: ($root.ingresoNoIdentificado.id() &&
                                        ($root.ingresoNoIdentificado.estatus.id() ==  AppConfig.catalogos.estatusotrosingresos.registrado))">
                                <spring:message code="mihabitat.botones.asignaringresonoidentificado" />
                            </button>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.actualizar, visible: ($root.ingresoNoIdentificado.id() &&
                                        ($root.ingresoNoIdentificado.estatus.id() ==  AppConfig.catalogos.estatusotrosingresos.registrado))">
                                <spring:message code="mihabitat.botones.actualizar"/>
                            </button>
                        </footer>
                    </form>
                </div>
            </div>
        </div>
    </article>
</div>

<!-- Modal Asignar a Abono -->
<div class="modal fade draggable-modal" id="asignarAbono" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="font-weight: bolder"
                    id="myModalLabelAsignarAbono"><spring:message code="mihabitat.pago"/>
                </h4>
            </div>
            <div class="modal-body">
                <form id="cargo-form">
                    <div class="modal-body">
                        <section class="row smart-form">
                            <section class="col col-md-12" class="form-group">
                                <label class="label"> <span class="error-required">*</span> <spring:message
                                        code="mihabitat.ingresonoidentificado.contacto" />
                                </label>
                                <label class="input"> <input class="form-control"
                                                             type="text" name="contacto" id="contacto"
                                                             placeholder="Introduzca el nombre del condómino o departamento"
                                                             required="required" maxlength="128"
                                                             data-bind="value: $root.item.label, valueUpdate : 'keyup'">
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
                            <button type="button" class="btn btn-success" data-dismiss="modal" data-bind="event : {click : $root.asignarAbono}">
                                <spring:message code="mihabitat.botones.aceptar" />
                            </button>
                        </section>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->