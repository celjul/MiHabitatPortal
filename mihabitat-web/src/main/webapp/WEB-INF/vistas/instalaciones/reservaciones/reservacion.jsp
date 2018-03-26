<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
		   prefix="sec"%>
<style type="text/css" media="screen">
	.calendar_size {
		width: 100%;
		min-height: 450px;
	}
</style>
<div class="row">
	<article class="col-sm-12 col-md-12 col-lg-12">
		<div class="jarviswidget" id="wid-id-2"
			data-widget-colorbutton="false" data-widget-editbutton="false"
			data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i>
				</span>
				<h2>
					Control de Reservaciones
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
						<fieldset>
							<div class="well" style="padding: 15px; min-height: 125px">

                            <section class="row">


								<section class="col col-md-4">
									<section class="row">
										<section class="col col-md-12">


											<div class="container" style="width: 100%; max-width: 200px;">
												<div class="well"  data-bind="fileDrag: $root.instalacion.fileData">
													<div class="form-group row">
														<div class="col col-md-12">
															<img style="height: 125px; max-width: 100%" class="img-rounded  thumb"
																 data-bind="attr: { src: instalacion.fileData().dataURL }">
														</div>
													</div>
												</div>
											</div>

										</section>
									</section>
									<br>
									<section class="row">
										<section class="row smart-form">
											<section class="col col-md-12">
										<h3 class="media-heading" data-bind="text: $root.instalacion.nombre"></h3>
										<p data-bind="text: $root.instalacion.descripcion" class="text-justify"></p><br>
										<p><b data-bind="text: ('Costo de Uso: $'+$root.instalacion.costo()+' por '+$root.instalacion.unidad.descripcion()),
										visible: $root.instalacion.cobroAutomatico"></b></p><br>
												</section>
										</section>
									</section>
									<%--</div>--%>
								</section>
								<section class="col col-md-8">

										<div class="col-sm-12 col-md-12 col-lg-12">

											<!-- new widget -->
											<div class="jarviswidget jarviswidget-color-blueDark">

												<!-- widget options:
                                                usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

                                                data-widget-colorbutton="false"
                                                data-widget-editbutton="false"
                                                data-widget-togglebutton="false"
                                                data-widget-deletebutton="false"
                                                data-widget-fullscreenbutton="false"
                                                data-widget-custombutton="false"
                                                data-widget-collapsed="true"
                                                data-widget-sortable="false"

                                                -->
												<header>
													<span class="widget-icon"> <i class="fa fa-calendar"></i> </span>
													<h2> Calendario de Reservaciones </h2>
													<div class="widget-toolbar">
														<a class="btn btn-success" href="javascript:void(0);"
														   data-bind="event : {click : $root.new_event}">
															<i class="fa fa-calendar" style="padding-right: 5px"> </i>
															<span class="hidden-mobile">Nueva Reservación</span>
														</a>
													</div>
												</header>

												<!-- widget div-->
												<div>

													<div class="widget-body no-padding">
														<!-- content goes here -->
														<div id="scheduler_here" class="dhx_cal_container calendar_size" >
															<div class="dhx_cal_navline">
																<div class="dhx_cal_prev_button">&nbsp;</div>
																<div class="dhx_cal_next_button">&nbsp;</div>
																<div class="dhx_cal_today_button"></div>
																<div class="dhx_cal_date"></div>
																<div class="dhx_cal_tab" name="month_tab" style="right:204px;"></div>
																<div class="dhx_cal_tab" name="week_tab" style="right:140px;"  data-bind="visible: $root.instalacion.unidad.id() != <spring:eval expression="@propertyConfigurer.getProperty('reservacion.unidades.dia')"/>"></div>
																<div class="dhx_cal_tab" name="day_tab" style="right:76px;"  data-bind="visible: $root.instalacion.unidad.id() != <spring:eval expression="@propertyConfigurer.getProperty('reservacion.unidades.dia')"/>"></div>
															</div>
															<div class="dhx_cal_header">
															</div>
															<div class="dhx_cal_data">
															</div>
														</div>

														<!-- end content -->
													</div>

												</div>
												<!-- end widget div -->
											</div>
											<!-- end widget -->
										</div>
								</section>

                            </section>

								</div>
						</fieldset>
						<footer>

						</footer>

				</div>
			</div>
		</div>
	</article>
</div>
<!-- Modal Reglamento -->
<div class="modal fade" id="reglamentoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel"><spring:message code="mihabitat.instalacion.reglamento" /></h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-md-12">
						<p data-bind="html: $root.instalacion.reglamento() ? ($root.instalacion.reglamento().replace(/\n/g, '<br>')) : ''" class="text-justify"></p>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Cerrar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Modal Reglamento -->
<div class="modal fade draggable-modal" id="calendarLightBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static"

	>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" data-bind="event : {click : $root.close_form}">
					&times;
				</button>
				<h4 class="modal-title" style="font-weight: bolder"
					data-bind="style: {color: ($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')"/> ? 'orange':($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')"/> ? 'green':($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.rechazada')"/> ? 'red':($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.cancelada')"/> ? 'grey':'black'))))}"
					id="myModalLabelReservaciones"><spring:message code="mihabitat.instalacion.reservacion" /><span data-bind="text: ' '+$root.estatusSeleccionado.descripcion()"></span></h4>
				<sec:authorize access="hasRole('Administrador')">
					<a class="btn btn-success btn-xs" data-bind="visible: $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')"/> ||  $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.rechazada')"/> ||  $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.cancelada')"/>, event: {click: $root.aprobar}">
						<spring:message code="mihabitat.reservacion.estatus.aprobar"/></a>
					<a class="btn btn-danger btn-xs" data-bind="visible: $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')"/>, event: {click: $root.rechazar}">
						<spring:message code="mihabitat.reservacion.estatus.rechazar"/></a>
					<a class="btn btn-info btn-xs" data-bind="visible: $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')"/>, event: {click: $root.cancelar}">
						<spring:message code="mihabitat.reservacion.estatus.cancelar"/></a>
				</sec:authorize>
				<sec:authorize access="hasRole('Contacto')">
					<a class="btn btn-info btn-xs" data-bind="visible: !$root.readOnly() && ($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')"/> ||  $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')"/>), event: {click: $root.cancelar}">
						<spring:message code="mihabitat.reservacion.estatus.cancelar"/></a>
					<a class="btn btn-warning btn-xs" data-bind="visible: !$root.readOnly() && ($root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.cancelada')"/> ||  $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.rechazada')"/> ||  $root.estatusSeleccionado.id() == <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')"/>), event: {click: $root.solicitar}">
						<spring:message code="mihabitat.reservacion.estatus.solicitar"/></a>
				</sec:authorize>
			</div>
			<%--<form id="reservacion-form" class="padding-10">--%>
			<div class="modal-body">
				<section class="row smart-form">
					<section class="col col-md-6" data-bind="visible: !$root.readOnly()">
						<label class="label">
							<spring:message code="mihabitat.instalacion.reservacion.contacto" />
						</label>
						<label class="input">
							<select style="width:100%" class="select2"
									name="contacto" id="contacto"
									data-bind="options: $root.contactos,
														optionsCaption : 'Seleccionar',
														optionsText: function(item) {return item.nombreCompleto},
														optionsValue: 'id',
														value: $root.contactoSeleccionado.id,
														select2: {},
														enable: $root.rol == 'administrador',
														event: {change: $root.getDepartamentosContacto}">
							</select>

						</label>
					</section>

					<section class="col col-md-6 " data-bind="visible: $root.instalacion.cobroAutomatico() && !$root.readOnly()">
						<label class="label">
							<spring:message code="mihabitat.instalacion.reservacion.departamento" />
						</label>
						<label class="input">
							<select style="width:100%" class="select2"
									name="departamento" id="departamento"
									data-bind="options: $root.departamentosContacto,
														optionsCaption : 'Seleccionar',
														optionsText: function(item) {return item.id.departamento.nombre},
														optionsValue: function(item) {return item.id.departamento.id},
														value: $root.departamentoSeleccionado.id,
														enable: $root.rol == 'administrador' || $root.pendiente(),
														select2: {}">
							</select>
						</label>
					</section>
				</section>
				<section class="row smart-form">
					<section  class="col col-md-6">
						<label class="label">
							<spring:message code="mihabitat.instalacion.reservacion.inicio" />
						</label>
						<div class="form-group">
							<div class="input-group date" id="inicioDate">
								<input class="form-control bg-color-white" style="cursor: pointer" size="16" type="text" value="" readonly>
								<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
							</div>
							<div class="form-group">
								<div class='input-group date' id='inicioHour'>
									<input type='text' class="form-control" style="cursor: pointer" readonly data-bind="enable: $root.instalacion.unidad.id()
										!= AppConfig.catalogos.unidadesreservacion.dia, style: {'background-color': ($root.instalacion.unidad.id() ==
										AppConfig.catalogos.unidadesreservacion.dia ? '#cccccc' : '#FFFFFF')}"/>
											<span class="input-group-addon">
												<span class="fa fa-clock-o"></span>
											</span>
								</div>
							</div>
						</div>
					</section>
					<section class="col col-md-6 ">
						<label class="label">
							<spring:message code="mihabitat.instalacion.reservacion.fin" />
						</label>
						<div class="form-group">
							<div class="input-group date" id="finDate">
								<input class="form-control bg-color-white" style="cursor: pointer" size="16" type="text" value="" readonly>
								<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
							</div>
							<div class="form-group">
								<div class='input-group date' id='finHour'>
									<input type='text' class="form-control" style="cursor: pointer" readonly data-bind="enable: $root.instalacion.unidad.id()
										!= AppConfig.catalogos.unidadesreservacion.dia, style: {'background-color': ($root.instalacion.unidad.id() ==
										AppConfig.catalogos.unidadesreservacion.dia ? '#cccccc' : '#FFFFFF')}"/>
											<span class="input-group-addon">
												<span class="fa fa-clock-o"></span>
											</span>
								</div>
							</div>
						</div>
					</section>
				</section>
				<section class="row smart-form">
					<br>
					<section class="col col-md-12 smart-form" >
						<div class="form-group" data-bind="visible: $root.instalacion.reglamento && $root.rol != 'administrador' && !$root.readOnly()">
							<div class="input-group">
								<label class="alert alert-warning">
									<input type="checkbox" name="activo" data-bind="checked: $root.aceptoReglamento" >
									<strong>He leido y acepto los terminos y condiciones del
										<a href="#" data-toggle="modal" data-target="#reglamentoModal">
											<spring:message code="mihabitat.instalacion.reglamento" />
										</a></strong>
								</label>
							</div>
						</div>
					</section>
				</section>
			</div>
			<div class="modal-footer">

					<section class="col col-md-4 col-xs-3" >
						<button type="button" class="btn btn-success" data-bind="event : {click : $root.save_form}, visible: (!$root.readOnly())">
							<spring:message code="mihabitat.reservacion.aceptar" />
						</button>
					</section>
					<section class="col col-md-4 col-xs-3" >
						<button type="button" class="btn btn-warning" data-dismiss="modal" data-bind="event : {click : $root.close_form}">
							<spring:message code="mihabitat.reservacion.cancelar" />
						</button>
					</section>

					<section class="col col-md-4 col-xs-6" >
						<button type="button" class="btn btn-danger" data-dismiss="modal" data-bind="event : {click : $root.delete_event}, visible: $root.rol == 'administrador'">
							<spring:message code="mihabitat.reservacion.eliminar" />
						</button>
					</section>
			</div>
			<%--</form>--%>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	var config = {
		estatusReservacion : {
			pendiente : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')" />,
			reservada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')" />,
			rechazada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.rechazada')" />,
			cancelada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.cancelada')" />
		},
		unidades : {
			dia : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.unidades.dia')" />
		}
	};
</script>