<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Modal -->
<div class="modal fade" id="myModalMantenimiento" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel"><spring:message code="mihabitat.mantenimiento"/></h4>
            </div>
            <div class="modal-body">

                <form id="mantenimiento-form" class="smart-form">
                    <fieldset>
                        <section class="col col-md-6">
                            <label class="label">
                                <span class="error-required">*</span>
                                <spring:message code="mihabitat.mantenimientos.descripcion"/>
                            </label>
                            <label class="input">
                                <input class="form-control" type="text" name="descripcion"
                                       placeholder="<spring:message code="mihabitat.mantenimientos.descripcion" />"
                                       required="required" maxlength="128"
                                       data-bind="value: $root.mantenimientoCondominioViewModel.mantenimiento.descripcion">
                            </label>
                        </section>
                        <section class="col col-md-6">
                            <label class="label">
                                <span class="error-required">*</span>
                                <spring:message code="mihabitat.mantenimientos.tipoCobro"/>
                            </label> <label class="select">
                            <select style="width:100%" class="select"
                                    name="mantenimiento-tipo-cobro" id="mantenimiento-tipo-cobro"
                                    required="required"
                                    data-bind="options: $root.tiposCobro,
														optionsText: function(item) {
														   return item.descripcion();
													    },
														optionsValue: 'id',
														value: $root.mantenimientoCondominioViewModel.mantenimiento.tipoCobroDepartamento.id,">
                            </select>
                            <i></i>
                        </label>
                        </section>
                        <section class="col col-md-6">
                            <label class="label" data-bind="visible: $root.mantenimientoCondominioViewModel.mantenimiento.tipoCobroDepartamento.id() == AppConfig.catalogos.cargo.tiposCobroMantenimiento.fijo">
                                <span class="error-required">*</span>
                                <spring:message code="mihabitat.mantenimientos.monto"/>
                            </label>
                            <label class="label" data-bind="visible: $root.mantenimientoCondominioViewModel.mantenimiento.tipoCobroDepartamento.id() != AppConfig.catalogos.cargo.tiposCobroMantenimiento.fijo">
                                <span class="error-required">*</span>
                                <spring:message code="mihabitat.mantenimientos.unidadesIndiviso"/>
                            </label> <label class="input"> <input
                                class="form-control number money" type="text" name="montoMant" id="montoMant"
                                required="required" min="0"
                                data-bind="value: $root.mantenimientoCondominioViewModel.mantenimiento.monto">
                        </label>
                        </section>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    Cancelar
                </button>
                <button style="float: right" type="button" class="btn btn-primary"
                        data-bind="click: $root.guardarMantenimiento, visible: !$root.mantenimientoCondominioViewModel.mantenimiento.id()">
                    <spring:message code="mihabitat.botones.guardar"/>
                </button>
                <button style="float: right" type="button" class="btn btn-primary"
                        data-bind="click: $root.actualizarMantenimiento, visible: $root.mantenimientoCondominioViewModel.mantenimiento.id()">
                    <spring:message code="mihabitat.botones.actualizar"/>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
