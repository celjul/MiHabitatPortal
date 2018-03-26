<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.inicio"/> | <spring:message code="mihabitat.nombre"/></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/fullcalendar.min.css" type="text/css" media="screen" title="no title" charset="utf-8">
</head>
<body>
<div id="ribbon">
	<ol class="breadcrumb" style="min-width: 25%;">
		<li><a href="${pageContext.request.contextPath}/administrador/inicio"><spring:message code="mihabitat.menu.inicio"/></a></li>
	</ol>
	<jsp:include page="../barranotificaciones.jsp"></jsp:include>
</div>
<div id="content">
	<div class="row">
		<%--<div class="col-xs-12 col-sm-7 col-md-7 col-lg-2">
				<div class="well well-sm well-light bg-color-darken txt-color-white" style="font-size: xx-small"> <i class="fa fa-calendar fa-fw "></i>
					<span data-bind="text: $root.hoy"></span></div>
		</div>--%>
		<div class="col-xs-12 col-sm-5 col-md-5 col-lg-12">
			<ul id="sparks" class="">
				<li class="sparks-info">
					<h5> Bancos y Cajas
						<span class="txt-color-blue font-md">
							<a href="#" class="txt-color-blue" data-bind="text: '$' + numeral(($root.reporteIngresosEgresos.bancos.saldo()+$root.reporteIngresosEgresos.cajas.saldo())).format('0,0.00'), attr: { 'data-content': $root.listaBancos }"
							   rel="popover-hover" data-placement="bottom" data-original-title="Efectivo Disponible" data-html="true"
							   data-html="true">
							</a>
						</span>
					</h5>
					<div class="sparkline txt-color-blue hidden-mobile hidden-md hidden-sm" id="bancosCajas">
						1300, 1877, 2500, 2577, 2000, 2100, 3000, 2700, 3631, 2471, 2700, 3631, 2471
					</div>
				</li>
				<li class="sparks-info">
					<h5> Ingresos
						<span class="txt-color-purple font-md">
							<a class="txt-color-purple" href="#" data-bind="text: '$' + $root.reporteIngresosEgresos.ingresos.saldo.formatted(), attr: { 'data-content': $root.listaIngresos }"
							   rel="popover-hover" data-placement="bottom" data-original-title="Ingresos del Mes" data-html="true"
									>
							</a>
						</span>
					</h5>
					<div class="sparkline txt-color-purple hidden-mobile hidden-md hidden-sm" id="ingresosMes">
						110,150,300,130,400,240,220,310,220,300, 270, 210
					</div>
				</li>
				<li class="sparks-info">
					<h5> Egresos
						<span class="txt-color-greenDark font-md">
							<a href="#" class="txt-color-greenDark" data-bind="text: '$' + $root.reporteIngresosEgresos.egresos.saldo.formatted(), attr: { 'data-content': $root.listaEgresos }"
							   rel="popover-hover" data-placement="bottom" data-original-title="Egresos del Mes" data-html="true"
									>
							</a>
						</span>
					</h5>
					<div class="sparkline txt-color-greenDark hidden-mobile hidden-md hidden-sm" id="egresosMes">
						110,150,300,130,400,240,220,310,220,300, 270, 210
					</div>
				</li>
				<li class="sparks-info">
					<h5> Por Cobrar
						<span class="txt-color-red  font-md">
							<a href="#" class="txt-color-red" data-bind="text: '$' + $root.reporteIngresosEgresos.cobrar.saldo.formatted(), attr: { 'data-content': $root.listaPorCobrar }"
							   rel="popover-hover" data-placement="bottom" data-original-title="Depts con Adeudos" data-html="true"
									>

							</a>
						</span>
					</h5>
					<div class="sparkline txt-color-red hidden-mobile hidden-md hidden-sm" id="cobranza">
						110,150,300,130,400,240,220,310,220,300, 270, 210
					</div>
				</li>
				<li class="sparks-info">
					<h5> Adeudos
						<span class="txt-color-orange  font-md">
							<a href="#" class="txt-color-orange" data-bind="text: '$' + $root.reporteIngresosEgresos.pagar.saldo.formatted(), attr: { 'data-content': $root.listaPorPagar }"
							   rel="popover-hover" data-placement="bottom" data-original-title="Cuentas por Pagar" data-html="true"
									>
							</a>
						</span>
					</h5>
					<div class="sparkline txt-color-orange hidden-mobile hidden-md hidden-sm" id="adeudos">
						110,150,300,130,400,240,220,310,220,300, 270, 210
					</div>
				</li>

			</ul>
		</div>
	</div>

	<section id="widget-grid" class="">

		<!-- row -->
		<div class="row">
			<article class="col-sm-12">
				<!-- new widget -->
				<div class="jarviswidget" id="wid-id-0"
					 data-widget-togglebutton="false"
					 data-widget-editbutton="false"
					 data-widget-fullscreenbutton="false"
					 data-widget-colorbutton="false"
					 data-widget-deletebutton="false">
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
						<span class="widget-icon"> <i class="glyphicon glyphicon-stats txt-color-darken"></i> </span>
						<h2>  Panel de Control </h2>

						<ul class="nav nav-tabs pull-right in" id="myTab">
							<li class="active">
								<a data-toggle="tab" href="#s1"><i class="fa fa-building"></i> <span class="hidden-mobile hidden-tablet"> Actividades de la Administración </span></a>
							</li>

							<li>
								<a data-toggle="tab" href="#s2"><i class="fa fa-check-square-o"></i> <span class="hidden-mobile hidden-tablet"> Comunicación del Condominio </span></a>
							</li>

							<%--<li>
								<a data-toggle="tab" href="#s3"><i class="fa fa-dollar"></i> <span class="hidden-mobile hidden-tablet">Revenue</span></a>
							</li>--%>
						</ul>

					</header>

					<!-- widget div-->
					<div class="no-padding">
						<!-- widget edit box -->
						<div class="jarviswidget-editbox">

							test
						</div>
						<!-- end widget edit box -->

						<div class="widget-body">
							<!-- content -->
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade active in padding-10 no-padding-bottom" id="s1">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
											<!-- new widget -->
											<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-31" style="margin: 0px"
												 data-widget-colorbutton="false"
												 data-widget-editbutton="false"
												 data-widget-togglebutton="false"
												 data-widget-deletebutton="false"
												 data-widget-fullscreenbutton="false"
												 data-widget-custombutton="false"
												 data-widget-sortable="false"
													>

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
													<h2> Agenda </h2>
													<div class="widget-toolbar">
														<!-- add: non-hidden - to disable auto hide -->
														<div class="btn-group">
															<button class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown">
																Mostrar <i class="fa fa-caret-down"></i>
															</button>
															<ul class="dropdown-menu js-status-update pull-right">
																<li>
																	<a href="javascript:void(0);" id="mt">Mes</a>
																</li>
																<li>
																	<a href="javascript:void(0);" id="ag">Agenda</a>
																</li>
																<li>
																	<a href="javascript:void(0);" id="td">Hoy</a>
																</li>
															</ul>
														</div>
													</div>
												</header>

												<!-- widget div-->
												<div >
													<!-- widget edit box -->
													<div class="jarviswidget-editbox">

														<input class="form-control" type="text">

													</div>
													<!-- end widget edit box -->

													<div class="widget-body no-padding" style="margin-top: 10px">

														<div id="calendar" ></div>

														<!-- end content -->
													</div>

												</div>
												<!-- end widget div -->
											</div>
											<!-- end widget -->
										</div>
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">

												<div class="ibox float-e-margins" >
													<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
														<h5 style="background-color: #353D4B; color: #FFF">
															<i class="fa fa-check-square-o"></i> Tareas de la Administración
														</h5>
														<a class="btn btn-success btn-xs pull-right" style="margin-right: 10px"
														   data-toggle="modal"
														   data-target="#tareaModal" data-bind="click: $root.nuevaTarea"
																>
															<i class="glyphicon glyphicon-plus"></i>
														</a>
													</div>
													<div id="chat-body21" class="ibox-content custom-scroll smart-form padding-10" style="height: 350px; overflow-y: auto; padding: 0px">
														<div>
															<div class="feed-activity-list">
																<h5 class="todo-group-title bg-color-redLight txt-color-white"><i class="fa fa-warning"></i> Tareas Críticas (<small class="num-of-tasks txt-color-white" data-bind="text: $root.tareasCriticas().length"></small>)</h5>
																<ul id="sortable10" class="todo" data-bind="foreach : { data: $root.tareasCriticas }">
																	<li>
																		<span class="handle">
																			<label class="checkbox">
																				<input type="checkbox" name="checkbox-inline" data-bind="checked: completada, event: { change: $root.completadaTarea }">
																				<i></i>
																			</label>
																			<label>
																				<a class="btn btn-danger btn-xs txt-color-white pull-right"
																				   style="margin-top: 5px;padding-left: 3px;padding-right: 3px;"
																				   data-bind="click: $root.eliminarTarea"
																						>
																					<i class="glyphicon glyphicon-remove"></i>
																				</a>
																			</label>
																		</span>
																		<p>
																			<span class="date txt-color-red" data-bind="text: 'Vence: ' + AppConfig.catalogos.meses.descripcion.split(',')[moment(fechaVencimiento).month()] + moment(fechaVencimiento).format(' D, YYYY - hh:mm A')"></span>
																			<em data-bind="visible: ($root.hoy.getTime() > fechaVencimiento)">
																				(<i class="fa fa-warning txt-color-orange"
																					></i> <small>Vencida</small>)
																			</em>
																			<strong data-bind="text: titulo"></strong> -
																			<!--ko text: detalle-->
																			<!--/ko-->
																			[<a class="font-xs" data-toggle="modal"
																				data-target="#tareaModal" data-bind="click: $root.editarTarea" style="cursor: pointer"> Ver </a>]
																			<span class="date" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(fecha).month()] + moment(fecha).format(' D, YYYY - hh:mm A')"></span>
																		</p>
																	</li>
																</ul>
																<h5 class="todo-group-title bg-color-blueLight txt-color-white"><i class="fa fa-exclamation"></i> Tareas Pendientes (<small class="num-of-tasks txt-color-white" data-bind="text: $root.tareasPendientes().length"></small>)</h5>
																<ul id="sortable20" class="todo" data-bind="foreach : { data: $root.tareasPendientes }">
																	<li>
																		<span class="handle"> <label class="checkbox">
																			<input type="checkbox" name="checkbox-inline" data-bind="checked: completada, event: { change: $root.completadaTarea }">
																			<i></i> </label>
																		<label>
																			<a class="btn btn-danger btn-xs txt-color-white pull-right"
																			   style="margin-top: 5px;padding-left: 3px;padding-right: 3px;"
																			   data-bind="click: $root.eliminarTarea"
																					>
																				<i class="glyphicon glyphicon-remove"></i>
																			</a>
																		</label>
																		</span>

																		<p>
																			<span class="date txt-color-red" data-bind="text: 'Vence: ' + AppConfig.catalogos.meses.descripcion.split(',')[moment(fechaVencimiento).month()] + moment(fechaVencimiento).format(' D, YYYY - hh:mm A')"></span>
																			<em data-bind="visible: ($root.hoy.getTime() > fechaVencimiento)">
																				(<i class="fa fa-warning txt-color-orange"
																					></i> <small>Vencida</small>)
																			</em>
																			<strong data-bind="text: titulo"></strong> -
																			<!--ko text: detalle-->
																			<!--/ko-->
																			[<a class="font-xs" data-toggle="modal"
																				data-target="#tareaModal" data-bind="click: $root.editarTarea" style="cursor: pointer"> Ver </a>]
																			<span class="date" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(fecha).month()] + moment(fecha).format(' D, YYYY - hh:mm A')"></span>
																		</p>
																	</li>
																</ul>

																<h5 class="todo-group-title bg-color-greenLight txt-color-white"><i class="fa fa-check"></i> Tareas Completadas (<small class="num-of-tasks txt-color-white" data-bind="text: $root.tareasCompletadas().length"></small>)</h5>
																<ul id="sortable30" class="todo" data-bind="foreach : { data: $root.tareasCompletadas }">
																	<li class="complete">
																		<span class="handle">
																			<label class="checkbox">
																				<input type="checkbox" name="checkbox-inline" data-bind="checked: completada, event: { change: $root.completadaTarea }">
																				<i></i> </label>
																			<label>
																				<a class="btn btn-danger btn-xs txt-color-white pull-right"
																				   style="margin-top: 5px;padding-left: 3px;padding-right: 3px;"
																				   data-bind="click: $root.eliminarTarea"
																						>
																					<i class="glyphicon glyphicon-remove"></i>
																				</a>
																			</label>
																		</span>
																		<p>
																			<span class="date txt-color-red" data-bind="text: 'Vence: ' + AppConfig.catalogos.meses.descripcion.split(',')[moment(fechaVencimiento).month()] + moment(fechaVencimiento).format(' D, YYYY - hh:mm A')"></span>
																			<em data-bind="visible: ($root.hoy.getTime() > fechaVencimiento)">
																				(<i class="fa fa-warning txt-color-orange"
																					></i> <small>Vencida</small>)
																			</em>
																			<strong data-bind="text: titulo"></strong> -
																			<!--ko text: detalle-->
																			<!--/ko-->
																			[<a class="font-xs" data-toggle="modal"
																				data-target="#tareaModal" data-bind="click: $root.editarTarea" style="cursor: pointer"> Ver </a>]
																			<span class="date" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(fecha).month()] + moment(fecha).format(' D, YYYY - hh:mm A')"></span>
																		</p>
																	</li>
																</ul>
															</div>

														</div>
													</div>
												</div>

										</div>
										<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
											<div class="ibox float-e-margins">
												<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
													<h5 style="background-color: #353D4B; color: #FFF"> <i class="fa fa-warning"></i> Pendientes de Atención </h5>
												</div>
												<div class="ibox-content" style="height: 350px; overflow-y: auto; padding: 0px" id="inicioPendientesAdmin">

												</div>
											</div>
										</div>




									</div>
								</div>
								<!-- end s1 tab pane -->

								<div class="tab-pane fade" id="s2">
									<div class="widget-body-toolbar bg-color-white">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
												<div class="ibox float-e-margins" >
													<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
														<h5 style="background-color: #353D4B; color: #FFF"><i class="fa fa-bullhorn"></i> Avisos al Condominio</h5>
													</div>
													<div id="chat-body2" class="chat-body custom-scroll well" style="height: 350px; overflow-y: auto">
														<ul data-bind="foreach : { data: $root.temas }">
															<li class="message" data-bind="if: blog.id == AppConfig.catalogos.blogs.avisos ">
																<div class="message-text no-margin font-xs">
																	<time data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[moment(new Date(fecha)).month()] + moment(new Date(fecha)).format(' D, YYYY')">
																	</time>
																	<a class="username" data-bind="text: nombre, event: {click: $root.verPost}" style="cursor: pointer"></a>
																	<small class="text-muted"><i class="fa fa-comments"></i> <span data-bind="text: (posts.length-1) + ' comentarios'"></span></small>
																	<p data-bind="text: posts.length > 0?(posts[0].comentario.length > 150?posts[0].comentario.substring(0, 150)+'...':posts[0].comentario):'Sin Comentarios...'">
																	</p>

																</div>
															</li>
														</ul>
													</div>
												</div>
											</div>
											<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
												<div class="ibox float-e-margins">
													<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
														<h5 style="background-color: #353D4B; color: #FFF"><i class="fa fa-comments"></i> Actividad en Foros</h5>
													</div>
													<div class="ibox-content" style="height: 350px; overflow-y: auto">

														<div>
															<div class="feed-activity-list" data-bind="foreach : { data: $root.actividadPosts }">

																<div class="feed-element">
																	<a ui-sref="profile" class="pull-left">
																		<img src="/recursos/img/avatars/male.png" alt="ztapia" class="online">
																		<%--<em class="badge txt-color-white bg-color-teal">
																			Nuevo
																		</em>--%>
																	</a>
																	<div class="media-body ">
																		<small class="pull-right" data-bind="text: antiguedad"></small>
																		<strong data-bind="text: nombre"></strong> <span data-bind="text: descripcion"></span>
																		&quot;<strong data-bind="text: tema"></strong>&quot;<br>
																		<div class="well" data-bind="text: comentario">
																		</div>
																		<div class="actions">
																			<small class="text-muted" data-bind="text: blog"></small>&nbsp;&nbsp;&nbsp;
																			<a class="text-info" style="cursor: pointer;"
																			   data-bind="click: $root.verPost"><i class="fa fa-mail-forward"></i> Ver</a>
																		</div>

																	</div>
																</div>
															</div>

														</div>

													</div>
												</div>
											</div>
										</div>


									</div>

								</div>
								<!-- end s2 tab pane -->

								<%--<div class="tab-pane fade" id="s3">

									<div class="widget-body-toolbar bg-color-white smart-form" id="rev-toggles">

										<div class="inline-group">

											<label for="gra-0" class="checkbox">
												<input type="checkbox" name="gra-0" id="gra-0" checked="checked">
												<i></i> Target </label>
											<label for="gra-1" class="checkbox">
												<input type="checkbox" name="gra-1" id="gra-1" checked="checked">
												<i></i> Actual </label>
											<label for="gra-2" class="checkbox">
												<input type="checkbox" name="gra-2" id="gra-2" checked="checked">
												<i></i> Signups </label>
										</div>

										<div class="btn-group hidden-phone pull-right">
											<a class="btn dropdown-toggle btn-xs btn-default" data-toggle="dropdown"><i class="fa fa-cog"></i> More <span class="caret"> </span> </a>
											<ul class="dropdown-menu pull-right">
												<li>
													<a href="javascript:void(0);"><i class="fa fa-file-text-alt"></i> Export to PDF</a>
												</li>
												<li>
													<a href="javascript:void(0);"><i class="fa fa-question-sign"></i> Help</a>
												</li>
											</ul>
										</div>

									</div>

									<div class="padding-10">
										<div id="flotcontainer" class="chart-large has-legend-unique"></div>
									</div>
								</div>--%>
								<!-- end s3 tab pane -->
							</div>

							<!-- end content -->
						</div>

					</div>
					<!-- end widget div -->
				</div>
				<!-- end widget -->

			</article>
		</div>

		<!-- end row -->



	</section>
	<!-- end widget grid -->

</div>
<!-- Modal Tarea -->
<div class="modal fade draggable-modal" id="tareaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static"
		>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" style="font-weight: bolder"
					id="myModalLabelReservaciones"><spring:message code="mihabitat.tareas.tarea"/>
				</h4>
			</div>
			<form id="tarea-form">
				<div class="modal-body">
					<section class="row smart-form">
						<section class="col col-md-12">
							<label class="label"> <span class="error-required">*</span>
								<spring:message code="mihabitat.tareas.titulo" />
							</label>
							<label class="input">
								<input class="form-control" type="text" id="tituloTarea"
									   name="tituloTarea"
									   placeholder="<spring:message code="mihabitat.tareas.titulo" />"
									   required="required" maxlength="128"
									   data-bind="value: $root.tareaActual.titulo">

							</label>
						</section>
						<section class="col col-md-12">
							<label class="label">
								<spring:message code="mihabitat.tareas.detalle" />
							</label>
							<label class="input">
										<textarea class="form-control" rows="2"
												  name="detalleTarea"
												  id="detalleTarea"
												  data-bind="value: $root.tareaActual.detalle"></textarea>
							</label>
						</section>

						<section class="col col-md-6">
							<label class="label"> &nbsp; </label> <label class="toggle">
							<input type="checkbox" name="criticaTarea" id="criticaTarea"
								   data-bind="checked: $root.tareaActual.critica">
							<i data-swchon-text="Si" data-swchoff-text="No"></i>
							<spring:message
								code="mihabitat.tareas.critica" />
						</label>
						</section>

						<%--<section class="col col-md-3" >
							<div class="form-group">
								<div class="input-group">
									<label class="checkbox">
										<input type="checkbox" name="activo" data-bind="checked: $root.tareaActual.critica" >
										<strong><spring:message code="mihabitat.tareas.critica" /></strong>
									</label>
								</div>
							</div>
						</section>--%>
						<section class="col col-md-6">
							<label class="label"> <span class="error-required">*</span>
								<spring:message code="mihabitat.tareas.fechaVencimiento" />
							</label>
							<div class="form-group">
								<div class="input-group date" id="fechaVencimiento">
									<input class="form-control bg-color-white" size="16" type="text"
										   value="" readonly>
									<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
								</div>
							</div>
						</section>
					</section>
				</div>
				<div class="modal-footer">
					<section class="col col-md-4 col-xs-3  pull-right">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<spring:message code="mihabitat.botones.cerrar"/>
						</button>
					</section>
					<section class="col col-md-4 col-xs-3  pull-right" data-bind="visible: $root.tareaActual.id()">
						<button type="button" class="btn btn-danger" data-dismiss="modal" data-bind="event : {click : $root.eliminarTarea}">
							<spring:message code="mihabitat.botones.eliminar"/>
						</button>
					</section>
					<section class="col col-md-4 col-xs-3  pull-right" data-bind="visible: !$root.tareaActual.id()">
						<button type="button" class="btn btn-success" data-dismiss="modal" data-bind="event : {click : $root.guardarTarea}">
							<spring:message code="mihabitat.botones.guardar" />
						</button>
					</section>
					<section class="col col-md-4 col-xs-3  pull-right" data-bind="visible: $root.tareaActual.id()">
						<button type="button" class="btn btn-success" data-dismiss="modal" data-bind="event : {click : $root.actualizarTarea}">
							<spring:message code="mihabitat.botones.actualizar" />
						</button>
					</section>
				</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script src="${pageContext.request.contextPath}/recursos/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/sparkline/jquery.sparkline.min.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.cust.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.resize.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.time.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.tooltip.min.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/vectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/vectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- Full Calendar -->
<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/fullcalendar.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/lang-all.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/inicio/tarea.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuentas-cobrar.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/movimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/ingresos-egresos.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/inicio/inicio-app.js?v=${project-version}"></script>

<script>
	$(document).ready(function() {

		var viewModel = new InicioViewModel({});
		ko.applyBindings(viewModel);

		viewModel.consulta();

		// DO NOT REMOVE : GLOBAL FUNCTIONS!
		pageSetUp();

		/*
		 * PAGE RELATED SCRIPTS
		 */

		$('#fechaVencimiento').datetimepicker({
			format: 'YYYY/MM/DD HH:mm A',
			locale: 'es',
			showTodayButton: true,
			allowInputToggle: true,
			ignoreReadonly: true,
			stepping: 5
		});

		$(".js-status-update a").click(function() {
			var selText = $(this).text();
			var $this = $(this);
			$this.parents('.btn-group').find('.dropdown-toggle').html(selText + ' <span class="caret"></span>');
			$this.parents('.dropdown-menu').find('li').removeClass('active');
			$this.parent().addClass('active');
		});
		/*
		 * TODO: add a way to add more todo's to list
		 */
		// initialize sortable
		$(function() {
			$("#sortable10, #sortable20").sortable({
				handle : '.handle',
				connectWith : ".todo",
				update : countTasks
			}).disableSelection();
		});

		// check and uncheck
		$('.todo .checkbox > input[type="checkbox"]').click(function() {
			var $this = $(this).parent().parent().parent();

			if ($(this).prop('checked')) {
				$this.addClass("complete");

				// remove this if you want to undo a check list once checked
				//$(this).attr("disabled", true);
				$(this).parent().hide();

				// once clicked - add class, copy to memory then remove and add to sortable3
				$this.slideUp(500, function() {
					$this.clone().prependTo("#sortable30").effect("highlight", {}, 800);
					$this.remove();
					countTasks();
				});
			} else {
				// insert undo code here...
			}

		})
		// count tasks
		function countTasks() {

			$('.todo-group-title').each(function() {
				var $this = $(this);
				$this.find(".num-of-tasks").text($this.next().find("li").size());
			});

		}
		/*
		 * FULL CALENDAR JS
		 */

		if ($("#calendar").length) {
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();

			var calendar = $('#calendar').fullCalendar({

				editable : false,
				draggable : false,
				selectable : false,
				selectHelper : true,
				unselectAuto : false,
				disableResizing : false,
				eventLimit : 2,
				lang: 'es',
				height: 360,
				defaultTimedEventDuration: '01:00:00',

				header : {
					left : 'title', //,today
					center : 'prev, next, today',
					right : '' //month, agendaDay,
				},

				select : function(start, end, allDay) {
					var title = prompt('Event Title:');
					if (title) {
						calendar.fullCalendar('renderEvent', {
									title : title,
									start : start,
									end : end,
									allDay : allDay
								}, true // make the event "stick"
						);
					}
					calendar.fullCalendar('unselect');
				},

				/*events : viewModel.eventos(),*/


				eventRender : function(event, element, icon) {
					if (!event.description == "") {
						element.find('.fc-title').append("<br/><span class='ultra-light'>" + event.description + "</span>");
					}
					if (!event.icon == "") {
						element.find('.fc-title').append("<i class='air air-top-right fa " + event.icon + " '></i>");
					}
				},

				eventClick: function(calEvent, jsEvent, view) {

					/*alert('Event: ' + calEvent.title);
					 alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
					 alert('View: ' + view.name);*/

					notificacionInfoEvent(
							{
								titulo: calEvent.title,
								mensaje: calEvent.description,
								inicio: calEvent.start.format('YYYY/MM/DD hh:mm a'),
								fin: calEvent.end?calEvent.end.format('YYYY/MM/DD hh:mm a'):'',
								link: (calEvent.tipo=='evento'?'/blogs/temas/actualizar/'+calEvent.idReferencia:(calEvent.tipo=='cobranza'?'/cobranza/consulta':''))
							}, 'administrador'
					);

				}
			});

		};

		/* hide default buttons */
		$('.fc-header-right, .fc-header-center').hide();

		// calendar prev
		$('#calendar-buttons #btn-prev').click(function() {
			$('.fc-button-prev').click();
			return false;
		});

		// calendar next
		$('#calendar-buttons #btn-next').click(function() {
			$('.fc-button-next').click();
			return false;
		});

		// calendar today
		$('#calendar-buttons #btn-today').click(function() {
			$('.fc-button-today').click();
			return false;
		});

		// calendar month
		$('#mt').click(function() {
			$('#calendar').fullCalendar('changeView', 'month');
		});

		// calendar agenda week
		$('#ag').click(function() {
			$('#calendar').fullCalendar('changeView', 'agendaWeek');
		});

		// calendar agenda day
		$('#td').click(function() {
			$('#calendar').fullCalendar('changeView', 'agendaDay');
		});


		var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
		$("#bancosCajas").sparkline(values, {
			type: 'bar',
			width: '82px',
			barColor: '#57889c',
			height: '26px',
			tooltipPrefix: '$',
			tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{prefix}}{{value}}',
			tooltipValueLookups: {
				'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
			}
		});
		var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
		$("#ingresosMes").sparkline(values, {
			type: 'bar',
			height: '26px',
			width: '82px',
			barColor: '#6e587a',
			tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
			tooltipValueLookups: {
				'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
			}
		});
		var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
		$("#egresosMes").sparkline(values, {
			type: 'bar',
			height: '26px',
			width: '82px',
			barColor: '#496949',
			tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
			tooltipValueLookups: {
				'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
			}
		});
		var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
		$("#adeudos").sparkline(values, {
			type: 'bar',
			height: '26px',
			width: '82px',
			barColor: '#b19a6b',
			tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
			tooltipValueLookups: {
				'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
			}
		});
		var values = [1700.00, 3631.34, 2171.65, 2900.33, 1631.23, 2471.15, 2100.00, 1231.34, 2471.65, 1700.33, 3631.23, 2471.15];
		$("#cobranza").sparkline(values, {
			type: 'bar',
			height: '26px',
			width: '82px',
			barColor: '#a90329',
			tooltipFormat: '<span style=\"color: {{color}}\">&#9679;</span> {{offset:offset}} - {{value}}',
			tooltipValueLookups: {
				'offset': {0:'Diciembre', 1:'Enero', 2:'Febrero', 3:'Marzo', 4:'Abril', 5:'Mayo', 6:'Junio', 7:'Julio', 8:'Agosto', 9:'Septiembre', 10:'Octubre', 11:'Noviembre'}
			}
		});


	});

</script>
</body>
</body>