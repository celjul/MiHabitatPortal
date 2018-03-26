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
                    <spring:message code="mihabitat.transferencia" />
                </h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/transferencias/lista" class="btn btn-info"> <i class="fa fa-list"></i> <span class="hidden-mobile"> <spring:message code="mihabitat.transferencia.lista"/>  </span> </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <form id="transferencia-form" class="smart-form">
                        <fieldset>



                            <section class="row">
                                <section class="col col-md-3" >
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.transferencias.cuenta.retiro" />
                                    </label> <label class="input"> <select style="width: 100%"
                                                                           class="select2" name="cuentaRetiro" id="cuentaRetiro"
                                                                           required="required"
                                                                           data-bind="options: $root.bancosCajas,
                                                                                optionsCaption : 'Seleccionar',
                                                                                optionsText: 'nombre',
                                                                                optionsValue: 'id',
                                                                                value: $root.transferencia.retiro.cuenta.id,
                                                                                select2: {}
                                                                                <%--event: {
                                                                                    change: $root.getSaldo
                                                                                },--%>">
                                </select>
                                </label>
                                </section>
                                <section class="col col-md-3">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.transferencias.cuenta.deposito" />
                                    </label> <label class="input"> <select style="width: 100%"
                                                                           class="select2" name="cuentaDeposito" id="cuentaDeposito"
                                                                           required="required"
                                                                           data-bind="options: $root.bancosCajas,
                                                                                optionsCaption : 'Seleccionar',
                                                                                optionsText: 'nombre',
                                                                                optionsValue: 'id',
                                                                                value: $root.transferencia.deposito.cuenta.id,
                                                                                select2: {}
                                                                                <%--event: {
                                                                                    change: $root.getSaldo
                                                                                },--%>">
                                </select>
                                </label>
                                </section>

                                <section class="col col-md-2" >
                                    <label class="label"><span class="error-required">*</span>
                                        <spring:message code="mihabitat.transferencias.fecha" />
                                    </label>
                                    <div class="input-group">
                                        <input
                                                class="form-control bg-color-white" style="cursor: pointer" type="text" name="fecha" id="fecha"
                                                required="required" readonly="readonly"
                                                data-bind="value: $root.transferencia.fecha">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </section>

                                <section class="col col-md-3">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.transferencias.metodoTransferencia" />
                                    </label> <label class="select"> <select style="width: 100%"
                                                                           name="metodo" id="metodo" required="required"
                                                                           data-bind="options: $root.metodosTransferencia,
                                                        optionsText: 'descripcion',
                                                        optionsValue: 'id',
                                                        value: $root.transferencia.metodoTransferencia.id">
                                        </select>
                                        <i></i>
                                    </label>
                                </section>








                                <section class="col col-md-6" class="form-group" id="comentario">
                                    <label class="label"> <spring:message
                                            code="mihabitat.transferencias.comentario" /></label>
                                    <label class="textarea textarea-expandable">
															<textarea class="custom-scroll" name="comentario"
                                                                      maxlength="256"
                                                                      data-bind="value: $root.transferencia.comentario"></textarea>
                                    </label>
                                </section>



                                <section class="col col-md-2" id="monto">
                                    <label class="label"> <span class="error-required">*</span><spring:message
                                            code="mihabitat.transferencias.monto" />
                                    </label>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input
                                                class="form-control number text-align-center" type="text" name="monto"

                                                required="required" min="0"
                                                data-bind="value: $root.transferencia.monto.formatted">
                                    </div>
                                </section>





                            </section>



                        </fieldset>
                        <footer>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.guardar, visible: !$root.transferencia.id()">
                                <spring:message code="mihabitat.botones.guardar" />
                            </button>
                            <%--<button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.actualizar, visible: $root.otroIngreso.id()">
                                <spring:message code="mihabitat.botones.actualizar" />
                            </button>--%>
                        </footer>
                    </form>
                </div>
            </div>
        </div>
    </article>
</div>