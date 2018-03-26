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
                    <spring:message code="mihabitat.otroingreso" />
                </h2>
                <div class="widget-toolbar">
                    <a href="${pageContext.request.contextPath}/administrador/otrosingresos/lista" class="btn btn-success"> <i class="fa fa-list"></i> Listado </a>
                </div>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <form id="otroIngreso-form" class="smart-form">
                        <fieldset>
                            <section class="row">
                                <section class="col col-md-2" class="form-group">
                                    <label class="label"><span class="error-required">*</span>
                                        <spring:message code="mihabitat.otroingreso.fecha" />
                                    </label>
                                    <div class="input-group">
                                        <input
                                                class="form-control bg-color-white" style="cursor: pointer" type="text" name="fecha" id="fecha"
                                                required="required" readonly="readonly"
                                                data-bind="value: $root.otroIngreso.fecha">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </section>
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.otroingreso.metododepago" />
                                    </label> <label class="select"> <select style="width: 100%"
                                                                           class="select2" name="metodo" id="metodo" required="required"
                                                                           data-bind="options: $root.metodosPago,
                                                        optionsText: 'descripcion',
                                                        optionsValue: 'id',
                                                        value: $root.otroIngreso.metodoPago.id">
                                        </select><i></i>
                                    </label>
                                </section>
                                <section class="col col-md-3" class="form-group"
                                         data-bind="visible: $root.otroIngreso.metodoPago.id() == AppConfig.catalogos.metodos.deposito ||
										                                                                      $root.otroIngreso.metodoPago.id() == AppConfig.catalogos.metodos.cheque ||
										                                                                      $root.otroIngreso.metodoPago.id() == AppConfig.catalogos.metodos.transferencia">
                                    <label class="label"> <spring:message
                                            code="mihabitat.otroingreso.referencia" />
                                    </label> <input class="form-control" type="text" name="referencia"
                                                    id="referencia"
                                                    placeholder="<spring:message code="mihabitat.otroingreso.referencia" />"
                                                    maxlength="32"
                                                    data-bind="value: $root.otroIngreso.referencia">
                                </section>
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.otroingreso.cuenta" />
                                    </label> <label class="input"> <select style="width: 100%"
                                                                           class="select2" name="cuenta" id="cuenta" required="required"
                                                                           data-bind="options: $root.bancosCajas,
                                                        optionsCaption : 'Seleccionar',
                                                        optionsText: 'nombre',
                                                        optionsValue: 'id',
                                                        value: $root.otroIngreso.movimientoOtroIngreso.cuenta.id,
                                                        select2: {},
                                                        <%--event: {
                                                        	change: $root.getSaldo
                                                        },--%>
                                                        disable : $root.otroIngreso.id()">
                                        </select>
                                    </label>
                                </section>
                            </section>
                            <section class="row">
                                <header><spring:message code="mihabitat.otroingreso.conceptos" /></header>
                                <article class="col-sm-12 col-md-12 col-lg-12">
                                    <table id="table-otrosIngresos"
                                           class="table table-striped table-bordered table-hover"
                                           style="width: 100%">
                                        <thead>
                                        <tr>
                                            <th class="col col-md-3">
                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.otroingreso.conceptos.cuenta" />
                                                </label>
                                            </th>
                                            <th class="col col-md-6">
                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.otroingreso.conceptos.concepto" />
                                                </label>
                                            </th>
                                            <th class="col col-md-2">
                                                <label class="label"> <span class="error-required">*</span>
                                                    <spring:message code="mihabitat.otroingreso.conceptos.monto" />
                                                </label>
                                            </th>
                                            <th class="col col-md-1">
                                                <a data-bind="click: $root.otroIngreso.agregarConcepto" class="btn btn-default btn-xs">
                                                    <spring:message code="mihabitat.otroingreso.conceptos.agregar" />
                                                </a>
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody data-bind="foreach : { data: $root.otroIngreso.conceptos }">
                                        <tr>
                                            <td class="col col-md-3">
                                                <label class="select">
                                                    <select name="cuentaConcepto" id="cuentaConcepto" required="required" class="input-sm"
                                                            data-bind="options: $root.cuentasIngresos,
					                                                        optionsCaption : 'Seleccionar',
					                                                        optionsText: 'nombre',
					                                                        optionsValue: 'id',
					                                                        value: movimientoConceptoOtroIngreso.cuenta.id,
					                                                        attr:{ name: 'cuenta-' + $index() }">
                                                    </select><i></i>
                                                </label>
                                            </td>
                                            <td class="col col-md-6">
                                                <label class="input">
                                                    <input class="input-sm" type="text" name="concepto" id="concepto"
                                                           placeholder="<spring:message code="mihabitat.otroingreso.conceptos.concepto" />"
                                                           required="required"
                                                           data-bind="value: concepto, attr:{ name: 'concepto-' + $index() }">
                                                </label>
                                            </td>
                                            <td class="col col-md-2">
                                                <label class="input">
                                                    <input
                                                            class="input-sm number money" type="text" name="monto" id="monto"
                                                            placeholder="<spring:message code="mihabitat.otroingreso.conceptos.monto" />"
                                                            required="required" min="1"
                                                            data-bind="value: movimientoConceptoOtroIngreso.haber, attr:{ name: 'monto-' + $index() }">
                                                </label>
                                            </td>
                                            <td class="col col-md-1">
                                                <a data-bind="click: $root.otroIngreso.eliminarConcepto" class="btn btn-default btn-xs">
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
                                    data-bind="click: $root.guardar, visible: !$root.otroIngreso.id()">
                                <spring:message code="mihabitat.botones.guardar" />
                            </button>
                            <button style="float: right" type="button"
                                    class="btn btn-primary"
                                    data-bind="click: $root.actualizar, visible: $root.otroIngreso.id()">
                                <spring:message code="mihabitat.botones.actualizar" />
                            </button>
                        </footer>
                    </form>
                </div>
            </div>
        </div>
    </article>
</div>