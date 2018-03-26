<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Modal -->
<div class="modal fade" id="myModalDirectorio" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel"><spring:message code="mihabitat.directorio"/></h4>
      </div>
      <div class="modal-body">
        <form id="directorio-form" class="smart-form">
          <fieldset>
            <section class="col col-md-6">
              <label class="label">
                <span class="error-required">*</span>
                <spring:message code="mihabitat.directorio.titulo" />
              </label>
              <label class="input">
                <input class="form-control" type="text" name="titulo"
                       required="required" maxlength="128"
                       data-bind="value: $root.directorioRegistro.titulo">
              </label>
            </section>
            <section class="col col-md-6">
              <label class="label">
                <spring:message code="mihabitat.directorio.telefonos" />
              </label> <label class="input"> <input
                    class="form-control" type="text" name="telefonos" id="telefonos"
                    maxlength="128"
                    data-bind="value: $root.directorioRegistro.telefono">
            </label>
            </section>
            <section class="col col-md-6">
              <label class="label">
                <spring:message code="mihabitat.directorio.email" />
              </label> <label class="input"> <input
                    class="form-control" type="text" name="email" id="email"
                    maxlength="128"
                    data-bind="value: $root.directorioRegistro.email">
            </label>
            </section>
            <section class="col col-md-6">
              <label class="label">
                <spring:message code="mihabitat.directorio.pagina" />
              </label> <label class="input"> <input
                    class="form-control" type="text" name="pagina" id="pagina"
                    maxlength="128"
                    data-bind="value: $root.directorioRegistro.pagina">
            </label>
            </section>
            <section class="col col-md-12">
              <label class="label">
                <spring:message code="mihabitat.directorio.direccion" />
              </label> <label class="input">
                  <textarea class="form-control" rows="2" maxlength="512" name="direccion" id="direccion"
                            data-bind="value: $root.directorioRegistro.direccion">
                  </textarea>
                </label>
            </section>
          </fieldset>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">
          <spring:message code="mihabitat.botones.cancelar" />
        </button>
        <button style="float: right" type="button" class="btn btn-primary"
                data-bind="click: $root.guardarDirectorio, visible: !$root.directorioRegistro.id()">
          <spring:message code="mihabitat.botones.guardar" />
        </button>
        <button style="float: right" type="button" class="btn btn-primary"
                data-bind="click: $root.actualizarDirectorio, visible: $root.directorioRegistro.id()">
          <spring:message code="mihabitat.botones.actualizar" />
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
