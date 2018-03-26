<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Modal -->
<div class="modal fade" id="myModalGrupo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel"><spring:message code="mihabitat.grupo"/></h4>
			</div>
			<div class="modal-body">

				<form id="grupo-form" class="smart-form">
					<fieldset>
						<section class="col col-md-12">
							<label class="label">
								<span class="error-required">*</span>
								<spring:message code="mihabitat.grupos.descripcion" />
							</label>
							<label class="input">
								<input class="form-control" type="text" name="descripcion"
									   placeholder="<spring:message code="mihabitat.grupos.descripcion" />"
									   required="required" maxlength="128"
									   data-bind="value: $root.grupoCondominioViewModel.grupo.descripcion">
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
						data-bind="click: $root.guardarGrupo, visible: !$root.grupoCondominioViewModel.grupo.id()">
					<spring:message code="mihabitat.botones.guardar" />
				</button>
				<button style="float: right" type="button" class="btn btn-primary"
						data-bind="click: $root.actualizarGrupo, visible: $root.grupoCondominioViewModel.grupo.id()">
					<spring:message code="mihabitat.botones.actualizar" />
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<%--

<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-6">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					<spring:message code="mihabitat.grupos" />
				</h2>				
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<form id="grupo-form" class="smart-form">
						<fieldset>
							<section class="col col-md-12">
								<label class="label">
									<span class="error-required">*</span>
									<spring:message code="mihabitat.grupos.descripcion" />
								 </label>
								 <label class="input">
									 <input class="form-control" type="text" name="descripcion" 
										 placeholder="<spring:message code="mihabitat.grupos.descripcion" />"
										 required="required" maxlength="128"
										 data-bind="value: $root.grupo.descripcion">
								</label>
							</section>
						</fieldset>
						<footer>
							<button style="float: right" type="button" class="btn btn-primary" 
									data-bind="click: $root.guardar, visible: !$root.grupo.id()">
									<spring:message code="mihabitat.botones.guardar" />
							</button>
							<button style="float: right" type="button" class="btn btn-primary"
									data-bind="click: $root.actualizar, visible: $root.grupo.id()">
									<spring:message code="mihabitat.botones.actualizar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</article>
</div>--%>
