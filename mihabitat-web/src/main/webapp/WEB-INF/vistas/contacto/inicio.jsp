<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
	<title><spring:message code="mihabitat.menu.inicio"/> | <spring:message code="mihabitat.nombre"/></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/fullcalendar.min.css" type="text/css" media="screen" title="no title" charset="utf-8">
</head>
<body>
	<div id="ribbon">
		<ol class="breadcrumb" style="min-width: 25%;">
			<li><a href="${pageContext.request.contextPath}/contacto/inicio"><spring:message code="mihabitat.menu.inicio"/></a></li>
		</ol>
		<jsp:include page="../barranotificaciones.jsp"></jsp:include>
	</div>
	<div id="content">
		<div class="row">
			<div class="col-xs-12 col-sm-5 col-md-5 col-lg-12">
				<ul id="sparks" class="">

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
								<%--<li class="active">
									<a data-toggle="tab" href="#s1"><i class="fa fa-building"></i> <span class="hidden-mobile hidden-tablet"> Actividades de la Administración </span></a>
								</li>--%>

								<li class="active">
									<a data-toggle="tab" href="#s1"><i class="fa fa-check-square-o"></i> <span class="hidden-mobile hidden-tablet"> Comunicación del Condominio </span></a>
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
												<%--<div class="row">--%>
													<div class="ibox float-e-margins" >
														<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
															<h5 style="color: #FFF"><i class="fa fa-bullhorn"></i> Avisos al Condominio</h5>
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
												<%--</div>
												<div class="row">--%>

												<%--</div>--%>
											</div>

											<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
												<div class="ibox float-e-margins">
													<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
														<h5 style="background-color: #353D4B; color: #FFF"> <i class="fa fa-warning"></i> Notificaciones Importantes </h5>
													</div>
													<div class="ibox-content" style="height: 150px; overflow-y: auto; padding: 0px" id="inicioNotContacto">

													</div>
												</div>
												<div class="ibox float-e-margins">
													<div class="ibox-title" style="background-color: #353D4B; color: #FFF">
														<h5 style="color: #FFF"><i class="fa fa-comments"></i> Actividad en Foros</h5>
													</div>
													<div class="ibox-content" style="height: 150px; overflow-y: auto">

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
										</div>
									</div>
									<!-- end s1 tab pane -->

									<div class="tab-pane fade" id="s2">
										<div class="widget-body-toolbar bg-color-white">
											<div class="row">
												<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

												</div>
												<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

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
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/sparkline/jquery.sparkline.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.cust.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.resize.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.time.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/flot/jquery.flot.tooltip.min.js"></script>
	<!-- Full Calendar -->
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/fullcalendar.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/fullcalendar/lang-all.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/condominios/condominio.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/inicio/inicio-contacto-app.js?v=${project-version}"></script>
	<script>
		$(document).ready(function() {
			var viewModel = new InicioContactoViewModel({});
			ko.applyBindings(viewModel);
			viewModel.consulta();
			// DO NOT REMOVE : GLOBAL FUNCTIONS!
			pageSetUp();
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
								}, 'contacto'
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