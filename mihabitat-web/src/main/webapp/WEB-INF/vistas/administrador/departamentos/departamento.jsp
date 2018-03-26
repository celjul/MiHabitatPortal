<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="row">
    <article class="col-sm-12 col-md-12 col-lg-12">
        <div class="jarviswidget" id="wid-id-2"
             data-widget-colorbutton="false" data-widget-editbutton="false"
             data-widget-custombutton="false">
            <header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
                <h2>
                    <spring:message code="mihabitat.departamento"/>
                </h2>
            </header>
            <div>
                <div class="jarviswidget-editbox"></div>
                <div class="widget-body no-padding">
                    <form id="departamento-form" class="smart-form">
                        <fieldset>
                            <section class="row" data-bind="visible: $root.departamento.id">
                                <section class="col col-md-2" class="form-group">
                                    <a class="btn btn-sm btn-info" data-bind="click: $root.limpiar">
                                        <i class="fa fa-plus-circle"></i> Crear Nuevo </a>
                                </section>
                                <section class="col col-md-2 pull-right" class="form-group">
                                    <label class="checkbox">
                                        <input type="checkbox" name="activo" id="activo"
                                               data-bind="checked: $root.departamento.activo">
                                        <i></i><spring:message code="mihabitat.departamento.activo"/>
                                    </label>
                                </section>
                            </section>
                            <section class="row">
                                <section class="col col-md-4">
                                    <label class="label">
                                        <span class="error-required">*</span>
                                        <spring:message code="mihabitat.departamento.nombre"/>
                                    </label>
                                    <label class="input">
                                        <input class="form-control" type="text" id="departamento-nombre"
                                               name="departamento-nombre"
                                               placeholder="<spring:message code="mihabitat.departamento.nombre" />"
                                               required="required" maxlength="128"
                                               data-bind="value: $root.departamento.nombre, event:{ change: $root.departamento.existe }">
                                    </label>
                                </section>
                                <section class="col col-md-4" class="form-group">
                                    <label class="label">
                                        <span class="error-required">*</span>
                                        <spring:message code="mihabitat.mantenimiento"/>
                                    </label>
                                    <div class="input-group">
                                        <select style="width:100%" class="select2"
                                                name="mantenimiento" id="mantenimiento"
                                                required="required"
                                                data-bind="options: $root.mantenimientos,
														optionsCaption : 'Seleccionar',
														optionsText: function(item) {
														   return item.descripcion() + ' - $' + item.monto();
													    },
														optionsValue: 'id',
														value: $root.departamento.mantenimiento.id,
														select2: {}">
                                        </select>
                                        <div class="input-group-btn">
                                            <button style="height: 105%; min-width: 40px;" type="button"
                                                    class="btn btn-default txt-color-greenDark" data-toggle="modal"
                                                    data-target="#myModalMantenimiento"
                                                    data-bind="click: $root.nuevoMantenimiento">
                                                <i class="fa fa-adjust fa-plus-circle fa-lg"></i></button>
                                        </div>
                                    </div>
                                </section>
                                <section class="col col-md-3" data-bind="visible: $root.checarMantenimientoIndiviso()">
                                    <label class="label">
                                        <span class="error-required">*</span>
                                        <spring:message code="mihabitat.departamento.unidadIndiviso"/>
                                    </label>
                                    <label class="input">
                                        <input class="form-control" type="text" id="departamento-unidad-indiviso"
                                               name="departamento-unidad-indiviso"
                                               required="required" maxlength="128"
                                        data-bind="value: $root.departamento.unidadIndiviso">
                                    </label>
                                </section>
                                <%--<section class="col col-md-4" class="form-group">
                                    <label class="label">
                                        <spring:message code="mihabitat.menu.grupos" />
                                    </label>
                                    <label class="input">
                                        <select style="width: 100%;" class="select2" name="grupos" id="grupos"
                                                multiple="multiple"
                                                data-bind="options: $root.grupos,
                                                        optionsText: 'descripcion',
                                                        optionsValue: 'id',
                                                        selectedOptions : $root.departamento.grupos,
                                                        select2: { placeholder: 'Selecciona opciones' }">
                                        </select>
                                    </label>
                                </section>--%>
                            </section>
                            <section class="row">
                                <section class="col col-md-4" class="form-group">
                                    <label class="label">
                                        <spring:message code="mihabitat.menu.grupos"/>
                                    </label>
                                    <div class="input-group">
                                        <select style="width: 100%;" class="select2" name="grupos" id="grupos"
                                                multiple="multiple"
                                                data-bind="options: $root.grupos,
														optionsText: 'descripcion',
														optionsValue: 'id',
														selectedOptions : $root.departamento.grupos,
														select2: { placeholder: 'Selecciona opciones' }">
                                        </select>
                                        <div class="input-group-btn">
                                            <button style="height: 105%; min-width: 40px;" type="button"
                                                    class="btn btn-default txt-color-greenDark" data-toggle="modal"
                                                    data-target="#myModalGrupo" data-bind="click: $root.nuevoGrupo">
                                                <i class="fa fa-adjust fa-plus-circle fa-lg"></i></button>
                                        </div>
                                    </div>
                                </section>
                                <section class="col col-md-8" class="form-group">
                                    <label class="label">
                                        <spring:message code="mihabitat.departamento.observaciones"/>
                                    </label>
                                    <label class="input">
										<textarea class="form-control" rows="2"
                                                  name="observaciones"
                                                  id="observaciones"
                                                  data-bind="value: $root.departamento.observaciones"></textarea>
                                    </label>
                                </section>
                            </section>
                        </fieldset>
                        <header>
                            <spring:message code="mihabitat.menu.contactos"/>
                            <a class="btn btn-sm btn-success pull-right" data-bind="click: $root.nuevoContacto">
                                <i class="fa fa-plus-circle"></i> Agregar Contacto </a>
                        </header>
                        <fieldset>
                            <section class="col col-2">
                                <i class="fa fa-home txt-color-red"></i> = Propietario
                            </section>
                            <section class="col col-2">
                                <i class="fa fa-user txt-color-green"></i> = Habitante
                            </section>
                            <section class="col col-2">
                                <i class="fa fa-envelope txt-color-orangeDark"></i> = Principal
                            </section>
                            <div class="table-responsive">
                                <table id="table-contactos" class="table table-striped table-bordered table-hover"
                                       style="width: 100%"
                                       data-bind="visible: $root.departamento.contactos().length > 0">
                                    <thead>
                                    <tr role="row">
                                        <th data-class="expand" class="txt-color-darken">
                                            <spring:message code="mihabitat.persona.nombre"/>
                                        </th>
                                        <th data-hide="phone" class="txt-color-darken">
                                            <spring:message code="mihabitat.persona.emails"/>
                                        </th>
                                        <th data-hide="phone" class="txt-color-darken">
                                            <spring:message code="mihabitat.persona.telefonos"/>
                                        </th>
                                        <th class="txt-color-darken">
                                            <spring:message code="mihabitat.usuarios.user"/>
                                        </th>
                                        <th class="txt-color-darken">

                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach : { data: $root.departamento.contactos }">
                                    <tr>
                                        <td class="txt-color-darken">
                                            <label data-bind="text: id.contacto.nombreCompleto">
                                            </label>

                                            <i class="fa fa-home txt-color-red" data-bind="visible: propietario"></i>
                                            <i class="fa fa-user txt-color-green" data-bind="visible: habitante"></i>
                                            <i class="fa fa-envelope txt-color-orangeDark"
                                               data-bind="visible: principal"></i>
                                        </td>
                                        <td>
                                            <ul data-bind="foreach: { data: id.contacto.emails}"
                                                class="txt-color-darken" style="padding-left: 10px">
                                                <li data-bind="text: direccion"></li>
                                            </ul>
                                        </td>
                                        <td>
                                            <ul data-bind="foreach: { data: id.contacto.telefonos}"
                                                class="txt-color-darken" style="padding-left: 10px">
                                                <li data-bind="text: numero"></li>
                                            </ul>
                                        </td>
                                        <td data-bind="text: id.contacto.usuario.user" class="txt-color-darken"></td>
                                        <td style="text-align: center;">
                                            <a class="btn btn-success btn-xs" data-bind="click: $root.editarContacto">
                                                <i class="fa fa-pencil"></i> Editar </a>
                                            <a class="btn btn-danger btn-xs"
                                               data-bind="click: $root.departamento.removerContacto">
                                                <i class="fa fa-minus-circle"></i> Quitar
                                            </a>

                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                        <footer>
                            <button type="button" class="btn btn-primary" id="btn-guardar-departamento"
                                    data-bind="click: $root.guardar, visible: !$root.departamento.id()">
                                <spring:message code="mihabitat.botones.guardar"/>
                            </button>
                            <button type="button" class="btn btn-primary" id="btn-actualizar-departamento"
                                    data-bind="click: $root.actualizar, visible: $root.departamento.id()">
                                <spring:message code="mihabitat.botones.actualizar"/>
                            </button>
                        </footer>
                    </form>
                </div>
            </div>
        </div>
    </article>

</div>
<div id="contentModal">
    <jsp:include page="contactoModal.jsp"/>
</div>
<div id="contentModal2">
    <jsp:include page="../mantenimientos/mantenimiento.jsp"/>
</div>
<div id="contentModal3">
    <jsp:include page="../grupos/grupo.jsp"/>
</div>