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
					<spring:message code="mihabitat.menu.tema" />
				</h2>
			</header>
			<div>
				<div class="jarviswidget-editbox"></div>
				<div class="widget-body no-padding">
					<div class="row">

						<div class="col-sm-12">

							<div class="well">

								<table class="table table-striped table-forum">
									<thead>
									<tr>
										<th colspan="2">
											<a href="#" data-bind="event: {click: $root.foros}"> Foros </a> >
											<a href="#" data-bind="text: $root.tema.blog.nombre()?$root.tema.blog.nombre():(''),
											event: {click: $root.temas}"> </a> >

											<span class="label" data-bind="text: ((($root.tema.tipo()==config.tipoTemaIncidencia.id)&&($root.tema.tipoIncidencia.descripcion()!=undefined))?$root.tema.tipoIncidencia.descripcion():($root.getTipo()?$root.getTipo().value:'No debe pasar')),
												css: {'label-primary' : ($root.tema.tipo()==config.tipoTemaNormal.id),
												'label-success' : ($root.tema.tipo()==config.tipoTemaEvento.id),
												'label-warning' : ($root.tema.tipo()==config.tipoTemaIncidencia.id && $root.tema.tipoIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.proyecto')" />),
												'label-danger' : ($root.tema.tipo()==config.tipoTemaIncidencia.id && $root.tema.tipoIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.incidencia')" />)}">
											</span>
											<a href="#" data-bind="text: $root.tema.nombre()?$root.tema.nombre():('Nuevo Tema'),
											event: {click: function() {$root.tema.editarTitulo(!$root.tema.editarTitulo())}}"> </a>
											<small style="font-weight: 400;line-height: 1;color: #999;font-size: 11px;" data-bind="visible: $root.tema.tipo()==config.tipoTemaNormal.id">
												creado en <em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment($root.tema.fecha(), 'YYYY-MM-DD').month()] + moment($root.tema.fecha(), 'YYYY-MM-DD').format(' D, YYYY')"></em>
											</small>
											<small style="font-weight: 400;line-height: 1;color: #999;font-size: 11px;" data-bind="visible: $root.tema.tipo()==config.tipoTemaEvento.id">
												<em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment($root.tema.fechaInicio()).month()] + moment($root.tema.fechaInicio()).format(' D, YYYY HH:mm a')"></em>
												- <em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment($root.tema.fechaFin()).month()] + moment($root.tema.fechaFin()).format(' D, YYYY HH:mm a')"></em>
											</small>
											<small style="font-weight: 400;line-height: 1;color: #999;font-size: 11px;" data-bind="visible: $root.tema.tipo()==config.tipoTemaIncidencia.id">
												registrada en <em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment($root.tema.fecha(), 'YYYY-MM-DD').month()] + moment($root.tema.fecha(), 'YYYY-MM-DD').format(' D, YYYY')"></em>
											</small>
										</th>
									</tr>
									</thead>
									</table>
											<form id="tema-form" class="smart-form" data-bind="visible: !$root.tema.id() || ($root.tema.editarTitulo() && $root.usuario.id() == $root.tema.usuario.id())">
												<fieldset>
													<section class="row">
														<section class="col col-md-2" class="form-group">
															<label class="label"><span class="error-required">*</span>
																<spring:message code="mihabitat.tema.tipo" /> </label> <label
																class="select"> <select name="tipo" id="tipo"
																						required="required"
																						data-bind="options: $root.tiposTema,
																optionsText: 'value',
																optionsValue: 'id',
																value: $root.tema.tipo,
																event: {change: function() {$root.setTipoPost();}}">
														</select><i></i>
														</label>
														</section>
														<section class="col col-md-2" class="form-group" data-bind="visible: $root.tema.tipo() == config.tipoTemaIncidencia.id">
															<label class="label"><span class="error-required">*</span>
																<spring:message code="mihabitat.incidencia.tipo" /> </label> <label
																class="select"> <select name="tipoIncidencia" id="tipoIncidencia"
																						required="required"
																						data-bind="options: $root.tiposIncidencia,
																optionsText: 'descripcion',
																optionsValue: 'id',
																value: $root.tema.tipoIncidencia.id">
														</select><i></i>
														</label>
														</section>
														<section class="col col-md-4" data-bind="visible: $root.tema.tipo() == config.tipoTemaEvento.id">
															<label class="label"> <span class="error-required">*</span>
																<spring:message code="mihabitat.tema.evento.fechaInicio" />
															</label>
															<div class="form-group">
																<div class="input-group date" id="fechaInicio">
																	<input class="form-control bg-color-white" size="16" type="text"
																		   value="" readonly>
																	<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
																</div>
															</div>
														</section>
														<section class="col col-md-4" data-bind="visible: $root.tema.tipo() == config.tipoTemaEvento.id">
															<label class="label">
																<spring:message code="mihabitat.tema.evento.fechaFin" />
															</label>
															<div class="form-group">
																<div class="input-group date" id="fechaFin">
																	<input class="form-control bg-color-white" size="16" type="text" value="" readonly>
																	<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
																</div>
															</div>
														</section>
														<section class="col col-md-10">
															<label class="label"><span
																	class="error-required">*</span><spring:message
																	code="mihabitat.tema.nombre" /> </label> <label class="input">
															<input class="form-control" type="text" name="nombre"
																   id="nombre"
																   placeholder="  <spring:message code="mihabitat.tema.nombre" />"
																   required="required" maxlength="128"
																   data-bind="value: $root.tema.nombre">

														</label>
														</section>
														<section class="col col-md-12" class="form-group" data-bind="visible: !$root.tema.id()">
															<label class="label"> <spring:message
																	code="mihabitat.tema.post.inicial" />
															</label> <label class="input"> <textarea class="form-control"
																									 rows="3" id="comentarioInicial" name="comentarioInicial" maxlength="512" required="required"
																									 placeholder="  <spring:message code="mihabitat.tema.post.comentario" />"
																									 data-bind="value: $root.postActual.comentario"></textarea>

															</label>

														</section>
													</section>

												</fieldset>
											</form>
											<section class="row smart-form bg-color-white" data-bind="visible: !$root.tema.id()">
												<section class="col col-md-12">
													<label class="label"> <spring:message
															code="mihabitat.tema.post.adjuntos" />
													</label> <label class="input">
													<form action="${pageContext.request.contextPath}/administrador/blogs/subirarchivo" class="dropzone"
														  id="mydropzone" style="min-height: 200px;max-height: 200px"></form>
												</label>

												</section>
											</section>
												<footer data-bind="visible: !$root.tema.id() || ($root.tema.editarTitulo() && $root.usuario.id() == $root.tema.usuario.id())">
													<button style="float: right" type="button"
															class="btn btn-primary"
															data-bind="click: $root.guardar, visible: !$root.tema.id()">
														<spring:message code="mihabitat.botones.guardar" />
													</button>
													<button style="float: right" type="button"
															class="btn btn-primary"
															data-bind="click: $root.actualizar, visible: $root.tema.id()">
														<spring:message code="mihabitat.botones.actualizar" />
													</button>
												</footer>
								<table class="table table-striped table-forum">
									<tbody data-bind="visible: $root.tema.id()">
									<!-- Post -->
									<!-- ko foreach: { data: $root.tema.posts} -->
									<tr>
										<td class="text-center bg-color-blueDark txt-color-white">
											<section class="col col-md-1" data-bind="visible: tipo() == config.tipoPostIncidencia">
												<span class="label" data-bind="text: estatusIncidencia.descripcion(), css: {'label-warning' : (estatusIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.solicitada')"/>),
												'label-info' : (estatusIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.pendiente')"/>),
												'label-primary' : (estatusIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.repetida')"/>),
												'label-danger' : (estatusIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.cancelado')"/>),
												'label-success' : (estatusIncidencia.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.concluido')"/>)}"> </span>
											</section>
											<section class="col col-md-3">
												<i class="fa fa-user txt-color-white"></i> &nbsp;
												<%--<strong data-bind="text: $root.usuario.persona.nombre() + ' ' + $root.usuario.persona.apellidoPaterno() + ' ' + $root.usuario.persona.apellidoMaterno()"></strong>--%>
												<strong data-bind="text: usuario.persona.nombre() + ' ' + usuario.persona.apellidoPaterno() + ' ' + usuario.persona.apellidoMaterno()"></strong>
											</section>
										</td>
									</tr>
									<tr>
										<td class="text-center" style="width: 12%;">
											<section class="col col-md-3">
											<%--<div class="push-bit">
												<span class="label" data-bind="text: estatus.descripcion(),
												css: {'label-warning' : (estatus.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.solicitada')"/>),
												'label-info' : (estatus.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.pendiente')"/>),
												'label-primary' : (estatus.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.repetida')"/>),
												'label-danger' : (estatus.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.cancelado')"/>),
												'label-success' : (estatus.id()==<spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.concluido')"/>)}"> </span>
											</div>--%><small><em data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(fecha()).month()] + moment(fecha()).format(' D, YYYY - hh:mm A')"></em></small>
											</section>
											<section class="col col-md-9 text-justify">
												<p data-bind="html: comentario().replace(/\n/g, '<br>')">
												</p>
												<div class="forum-attachment" data-bind="visible: adjuntos().length > 0">
													<span data-bind="text: adjuntos().length"></span> Archivo(s) adjunto(s)
													<ul class="list-inline" data-bind="foreach: adjuntos">
														<li class="well well-sm padding-5 margin-top-10 text-align-center">

															<%--<img style="height: 125px; max-width: 100%;" class="img-rounded  thumb"
																 data-bind="visible: archivo.tipo().substring(0,5) == 'image', attr: { src: 'data:'+archivo.tipo()+';base64,'+archivo.bytes() }">
															&lt;%&ndash;<img style="height: 125px; max-width: 100%;" class="img-rounded  thumb"
																 data-bind="visible: !(archivo.tipo().substring(0,5) == 'image'), attr: { src: '${pageContext.request.contextPath}/recursos/img/demo/no-imagen.png'}">&ndash;%&gt;--%>
															<img style="height: 100px;max-width: 120px;" class="img-rounded  thumb"
																 data-bind="attr: { src:
																 (
																 	(archivo.tipo().substring(0,5) == 'image')?'data:'+archivo.tipo()+';base64,'+archivo.bytes():
																	 (
																		(archivo.tipo() == 'application/vnd.openxmlformats-officedocument.presentationml.presentation')?'${pageContext.request.contextPath}/recursos/img/demo/ppdocument.png':
																		(
																			(archivo.tipo() == 'application/vnd.openxmlformats-officedocument.wordprocessingml.document')?'${pageContext.request.contextPath}/recursos/img/demo/word.jpg':
																			(
																				(archivo.tipo() == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')?'${pageContext.request.contextPath}/recursos/img/demo/datasheet.png':
																				(
																					(archivo.tipo() == 'application/pdf')?'${pageContext.request.contextPath}/recursos/img/demo/pdf.jpg':
																					(
																						'${pageContext.request.contextPath}/recursos/img/demo/unknoknfile.png'
																					)
																				)
																			)
																		)
																	 )
																 )

																 }">

															<br>
															<div style="width: 120px; overflow: hidden; height: 100%; max-height: 55px; min-height: 55px">
																<strong data-bind="text: archivo.nombre"></strong>
															</div>
															<br><a data-bind="click: function(){$root.descargar(archivo.id());}"> Descargar</a> | <span data-bind="text: archivo.tamano"></span>
															<%--<br>
															<a data-bind="click: function(){$root.descargar(archivo.id());}"> Descargar</a> | <a href="javascript:void(0);"> Ver</a>--%>
														</li>
													</ul>
												</div>
											</section>
										</td>
									</tr>
									<!-- /ko -->
									<!-- end Post -->

									<tr>
										<td class=" bg-color-white">

										</td>
									</tr>
									<tr data-bind="visible: $root.puedePublicar()">
										<td class=" bg-color-grayDark txt-color-white">
											<section class="col col-md-3">
												<a href="#" class="btn btn-info txt-color-white" data-bind="event: {click: function(){$root.nuevoPost(!$root.nuevoPost());}}">
													<h4 class="titled-pane">
														<i class="fa fa-arrow-circle-up" data-bind="visible: $root.nuevoPost();"></i>
														<i class="fa fa-arrow-circle-down" data-bind="visible: !$root.nuevoPost();"></i>
														Registrar Nueva Publicación </h4>
												</a>
											</section>
										</td>
									</tr>
									<tr data-bind="visible: $root.nuevoPost()">
										<td class="text-center bg-color-lighten" style="width: 12%;">
											<form id="post-form" class="smart-form">
												<div class="row smart-form">
												<section class="col col-md-2" data-bind="visible: $root.postActual.tipo() == config.tipoPostIncidencia">
													<label class="label"><span class="error-required">*</span>
														<spring:message code="mihabitat.incidencia.estatus.estatus" /> </label> <label
														class="select"> <select name="estatus" id="estatus"
																				required="required"
																				data-bind="options: $root.estatusIncidencia,
																optionsText: 'descripcion',
																optionsValue: 'id',
																value: $root.estatusIncidenciaTemp.id">
												</select><i></i>
												</label>
												</section>
												<section class="col col-md-10" class="form-group">
													<label class="label"> <span class="error-required">*</span><spring:message
															code="mihabitat.tema.post.comentario" />
													</label> <label class="input"> <textarea class="form-control"
														 rows="5" id="comentario" name="comentario" maxlength="512" required="required"
														 placeholder="  <spring:message code="mihabitat.tema.post.comentario" />"
														 data-bind="value: $root.postActual.comentario"></textarea>

													</label>

												</section>
												</div>
											</form>
											<!-- Widget ID (each widget will need unique ID)-->
											<div class="jarviswidget jarviswidget-color-blueLight" id="wid-id-0" data-widget-editbutton="false">
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
													<span class="widget-icon"> <i class="fa fa-upload"></i> </span>
													<h2>Archivos Adjuntos </h2>

												</header>

												<!-- widget div-->
												<div>

													<!-- widget edit box -->
													<div class="jarviswidget-editbox">
														<!-- This area used as dropdown edit box -->

													</div>
													<!-- end widget edit box -->

													<!-- widget content -->
													<div class="widget-body">
														<form action="${pageContext.request.contextPath}/administrador/blogs/subirarchivo"
															  class="dropzone" id="mydropzone2" style="min-height: 200px;max-height: 200px"></form>
													</div>
													<!-- end widget content -->

												</div>
												<!-- end widget div -->

											</div>
											<!-- end widget -->

												<a href="#" class="btn btn-labeled btn-success margin-top-10 pull-right"
														data-bind="event: {click: $root.guardarPost}">
													<span class="btn-label"><i class="glyphicon glyphicon-send"></i></span> Publicar </a>

										</td>
									</tr>
									<!-- end Post -->

									</tbody>
								</table>

							</div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</article>
</div>
<script type="text/javascript">
	var config = {
		tipoTemaNormal : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaNormal', value:'Tema'},
		tipoTemaEvento : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaEvento', value:'Evento'},
		tipoTemaIncidencia : {id:'com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia', value:'Incidencia o Proyecto'},
		tipoPostNormal : 'com.bstmexico.mihabitat.comunicacion.blogs.model.PostNormal',
		tipoPostIncidencia : 'com.bstmexico.mihabitat.comunicacion.blogs.model.PostIncidencia',
		tipoIncidencia : {
			proyecto : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.proyecto')" />,
			incidencia : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.tipo.incidencia')" />
		},
		estatusIncidencia : {
			solicitada : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.solicitada')" />,
			pendiente : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.pendiente')" />,
			repetida : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.repetida')" />,
			cancelado : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.cancelado')" />,
			concluido : <spring:eval expression="@propertyConfigurer.getProperty('incidencia.estatus.concluido')" />
		}
	};
</script>