<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>
<head>
    <title><spring:message code="mihabitat.menu.cobranza" /> | <spring:message code="mihabitat.nombre" /></title>
	<link href="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.css" rel="stylesheet" type="text/css" media="screen">
	<style type="text/css">
		.clockpicker-popover {
			z-index: 999999;
		}
	</style>

</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li><a
                href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                    <spring:message code="mihabitat.menu.inicio" />
            </a></li>
        </ol>
		<jsp:include page="../../barranotificaciones.jsp"></jsp:include>
    </div>
    <div id="content">
        <div class="row">
            <article class="col-sm-12 col-md-12 col-lg-12">
                <div class="row">
	                <article class="col-sm-7 col-md-7 col-lg-7">
	                    <div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
	                        <div class="row">
	                            <form id="acciones-form" class="smart-form">
	                                <section class="col col-md-3">
	                                    <a class="btn btn-primary btn-xs" href="javascript:void(0);" data-bind="click: $root.emailMasivo">
	                                       <spring:message code="mihabitat.cobranza.email.masivo" />
	                                    </a>
	                                </section>
	                                <!-- ko if: $root.cobranza.seleccionada() -->
	                                    <section class="col col-md-2">
                                            <a class="btn btn-default btn-xs" href="javascript:void(0);" data-bind="click: $root.nuevaNota">
                                                    <spring:message code="mihabitat.nota" />
                                            </a>
                                        </section>
		                                <section class="col col-md-2">
		                                    <a class="btn btn-default btn-xs" href="javascript:void(0);" data-bind="click: $root.emailIndividual">
		                                            <spring:message code="mihabitat.cobranza.email" />
		                                    </a>
		                                </section>
		                                <section class="col col-md-2">
		                                    <a class="btn btn-default btn-xs" href="javascript:void(0);" data-bind="click: $root.nuevoPago">
		                                        <spring:message code="mihabitat.pago" />
	                                        </a>
		                                </section>
		                                <section class="col col-md-2">
		                                    <a class="btn btn-default btn-xs" href="javascript:void(0);" data-bind="click: $root.estadoCuenta">
		                                        <spring:message code="mihabitat.estadocuenta" />
	                                        </a>
		                                </section>
	                                <!--  /ko -->
	                            </form>
	                        </div>
	                    </div>
	                </article>
                </div>
                <div class="row">
                    <article class="col-sm-7 col-md-7 col-lg-7">
                        <div class="row">
                            <article class="col-sm-12 col-md-12 col-lg-12">
                                <div class="jarviswidget" id="wid-id-1" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
                                    <header>
		                                <span class="widget-icon"> <i class="fa fa-tachometer"></i>
		                                </span>
		                                <h2>
		                                    <spring:message code="mihabitat.menu.cobranza" />
		                                </h2>
		                            </header>
	                                <table id="table-cobranza" class="table table-bordered table-hover">
			                            <thead>
			                                <tr>
			                                    <th data-class="expand" class="col-md-3"><spring:message code="mihabitat.departamento" /></th>
			                                    <th data-class="expand" class="col-md-3"><spring:message code="mihabitat.grupos" /></th>
			                                    <th data-class="expand" class="col-md-3"><spring:message code="mihabitat.cobranza.adeudo" /></th>
			                                    <th data-class="expand" class="col-md-2"><spring:message code="mihabitat.cobranza.tiempo" /></th>
			                                    <th style="display: none;"></th>
			                                    <th data-class="expand" class="col-md-1"><i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i></th>
			                                </tr>
			                            </thead>
			                            <tbody data-bind="foreach : { data: $root.cobranzas }">
			                                <tr data-bind="style: { background : seleccionada() ? '#fcf8e3' : '' }">
			                                    <td data-bind="text: departamento.nombre"></td>
			                                    <td>
			                                        <!-- ko foreach: { data: departamento.grupos() } -->
			                                            <span class="label label-default" data-bind="text: descripcion"></span>
			                                        <!--  /ko -->
			                                    </td>
			                                    <td data-bind="currency: adeudo, symbol: $" style="text-align: right"></td>
			                                    <td data-bind="text: tiempo"></td>
			                                    <td data-bind="text: milisegundos" style="display: none;"></td>
			                                    <td>
			                                        <button class="btn btn-default btn-xs" data-bind="click: $root.seleccionar">
                                                        <i class="fa fa-cog"></i> <span class="caret"></span>
                                                    </button>
			                                    </td>
			                                </tr>
			                            </tbody>
			                        </table>
		                        </div>
                            </article>
                        </div>
                        <!-- ko if: $root.cobranza.seleccionada() -->
	                        <div class="row">
	                            <article class="col-sm-12 col-md-12 col-lg-12">
	                                <div class="jarviswidget" id="wid-id-2" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
	                                    <header>
	                                        <span class="widget-icon"> <i class="fa fa-folder-open-o"></i>
	                                        </span>
	                                        <h2>
	                                            <spring:message code="mihabitat.menu.cargos" /> - 
                                                <spring:message code="mihabitat.departamento" />: <span data-bind="text: $root.cobranza.departamento.nombre"></span>
	                                        </h2>
	                                    </header>
	                                    <table id="table-cargos" class="table table-striped table-bordered table-hover" style="width: 100%">
	                                        <thead>
	                                            <tr>
	                                                <th data-class="expand" class="col-md-2"><spring:message code="mihabitat.movimiento.fecha" /></th>
	                                                <th data-class="expand" class="col-md-5"><spring:message code="mihabitat.cargo.concepto" /></th>
	                                                <th data-class="expand" class="col-md-1"><spring:message code="mihabitat.cargo" /></th>
	                                                <th data-class="expand" class="col-md-1"><spring:message code="mihabitat.pago.descuentos" /></th>
	                                                <th data-class="expand" class="col-md-1"><spring:message code="mihabitat.pago.recargos" /></th>
	                                                <th data-class="expand" class="col-md-1"><spring:message code="mihabitat.pago.pagado" /></th>
	                                                <th data-class="expand" class="col-md-1"><spring:message code="mihabitat.pago.saldopendiente" /></th>
	                                            </tr>
	                                        </thead>
	                                        <tbody data-bind="foreach : { data: $root.cobranza.cargos }">
                                                <tr>
                                                    <td data-bind="text: fecha"></td>
                                                    <td data-bind="text: concepto"></td>
                                                    <td data-bind="currency: totalMonto, symbol: $" style="text-align: right"></td>
                                                    <td data-bind="currency: totalDescuentos, symbol: $" style="text-align: right"></td>
                                                    <td data-bind="currency: totalRecargos, symbol: $" style="text-align: right"></td>
                                                    <td data-bind="currency: totalPagado, symbol: $" style="text-align: right"></td>
                                                    <td data-bind="currency: saldoPendiente, symbol: $" style="text-align: right"></td>
                                                </tr>
                                            </tbody>
	                                    </table>
	                                </div>
	                            </article>
	                        </div>
                        <!--  /ko -->
                    </article>
                    <article class="col-sm-5 col-md-5 col-lg-5">
                        <!-- ko if: $root.cobranza.seleccionada() -->
                            <div class="row">
                                <article class="col-sm-12 col-md-12 col-lg-12">
                                    <div class="jarviswidget" id="wid-id-2" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
                                        <header>
                                            <span class="widget-icon"> <i class="fa fa-user"></i>
                                            </span>
                                            <h2>
                                                <spring:message code="mihabitat.menu.contactos" /> - 
                                                <spring:message code="mihabitat.departamento" />: <span data-bind="text: $root.cobranza.departamento.nombre"></span>
                                            </h2>
                                        </header>
                                        <table id="table-contactos" class="table table-striped table-bordered table-hover" style="width: 100%">
                                            <thead>
                                                <tr>
                                                    <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.persona.nombre" /></th>
                                                    <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.persona.emails" /></th>
                                                    <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.persona.telefonos" /></th>
                                                </tr>
                                            </thead>
                                            <tbody data-bind="foreach : { data: $root.cobranza.departamento.contactos }">
                                                <tr>
                                                    <td data-bind="text: nombre"></td>
                                                    <td>
                                                        <ul style="margin-left: -25px; font-size: x-small;">
                                                           <!-- ko foreach: { data: emails } -->
                                                               <li data-bind="text: direccion"></li>
                                                            <!--  /ko -->
                                                        </ul>
                                                    </td>
                                                    <td>
                                                        <ul style="margin-left: -25px; font-size: x-small;">
                                                           <!-- ko foreach: { data: telefonos } -->
                                                               <li data-bind="text: numero"></li>
                                                            <!--  /ko -->
                                                        </ul>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </article>
                            </div>
	                        <div class="row">
	                            <article class="col-sm-12 col-md-12 col-lg-12">
	                                <div class="jarviswidget" id="wid-id-3" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
	                                    <header>
	                                        <span class="widget-icon"> <i class="fa fa-file-text"></i>
	                                        </span>
	                                        <h2>
	                                            <spring:message code="mihabitat.notas" /> - 
                                                <spring:message code="mihabitat.departamento" />: <span data-bind="text: $root.cobranza.departamento.nombre"></span>
	                                        </h2>
	                                    </header>
	                                    <table id="table-notas" class="table table-bordered table-hover" style="width: 100%">
	                                        <thead>
	                                            <tr>
	                                                <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.nota.fecha" /></th>
	                                                <th data-class="expand" class="col-md-6"><spring:message code="mihabitat.nota" /></th>
	                                                <th data-class="expand" class="col-md-1"></th>
	                                                <th data-class="expand" class="col-md-1"><i class="fa fa-fw fa-gear txt-color-blue hidden-md hidden-sm hidden-xs"></i></th>
	                                            </tr>
	                                        </thead>
	                                        <tbody data-bind="foreach : { data: $root.cobranza.notas }">
                                                <tr>
                                                    <td data-bind="text: fecha"></td>
                                                    <td data-bind="text: nota"></td>
                                                    <td>
                                                        <!-- ko if: recordatorio.id() -->
                                                            <i class="fa fa-fw fa-clock-o"></i>
                                                        <!--  /ko -->
                                                    </td>
                                                    <td>
	                                                    <button class="btn btn-default btn-xs" data-bind="click: $root.seleccionarNota">
	                                                        <i class="fa fa-cog"></i> <span class="caret"></span>
	                                                    </button>
	                                                </td>
                                                </tr>
                                            </tbody>
	                                    </table>
	                                </div>
	                            </article>
	                        </div>
                        <!--  /ko -->
                    </article>
                </div>
            </article>
        </div>
        <div class="row">
            <div id="nota-modal" class="modal fade" tabindex="-1" data-width="640">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span><span class="sr-only">
                                    <spring:message code="mihabitat.botones.cerrar" />
                                </span>
                            </button>
                            <h4 class="modal-title">
                                <spring:message code="mihabitat.nota" /> - 
                                <spring:message code="mihabitat.departamento" />: <span data-bind="text: $root.cobranza.departamento.nombre"></span>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form id="nota-form" class="smart-form">
		                         <fieldset>
		                             <div class="row">
		                                   <section class="col col-md-6">
		                                          <section class="col col-md-12">
		                                        <label class="label">
		                                            <span class="error-required">*</span>
		                                            <spring:message code="mihabitat.nota.fecha" />
		                                         </label>
		                                         <label class="input">
		                                            <input class="form-control" type="text" name="fecha"
		                                                id="fecha" readonly="readonly" disabled="disabled"
		                                                placeholder="<spring:message code="mihabitat.nota.fecha" />"
		                                                required="required" data-bind="value: $root.nota.fecha">
		                                         </label>
		                                        </section>
		                                        <section class="col col-md-12">
		                                           <label class="toggle">
		                                                <input type="checkbox" name="email"
		                                                data-bind="checked: $root.nota.aplica">
		                                                <spring:message code="mihabitat.recordatorio" /> 
		                                                <i data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
		                                                data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
		                                            </label>
		                                        </section>
		                                   </section>
		                                   <section class="col col-md-6" class="form-group">
		                                       <label class="label">
		                                           <span class="error-required">*</span>
		                                           <spring:message code="mihabitat.nota" />
		                                       </label>
		                                       <label class="input">
		                                           <textarea class="form-control" rows="6" name="nota" id="nota"
		                                               required="required" data-bind="value: $root.nota.nota"></textarea>
		                                       </label>
		                                   </section>
		                             </div>
		                             <div class="row" data-bind="visible: $root.nota.aplica()">
		                                 <header style="margin-bottom: 10px;">
		                                      <spring:message code="mihabitat.recordatorio" />
		                                  </header>
		                                  <section class="col col-md-6">
		                               <label class="label">
		                                   <span class="error-required">*</span>
		                                   <spring:message code="mihabitat.recordatorio.fecha" />
		                                </label>
		                                <label class="input">
		                                   <input class="form-control" type="text" name="fecha-recordatorio"
		                                       id="fecha-recordatorio" readonly="readonly"
		                                       placeholder="<spring:message code="mihabitat.recordatorio.fecha" />"
		                                       required="required" data-bind="value: $root.nota.recordatorio.fecha">
		                                </label>
		                                  </section>
		                                  <section class="col col-md-6">
		                                        <label class="label">
		                                            <span class="error-required">*</span>
		                                            <spring:message code="mihabitat.recordatorio.hora" />
		                                         </label>
		                                         <label class="input">
		                                            <input class="form-control" type="text" name="hora" 
		                                                id="hora" readonly="readonly"
		                                                placeholder="<spring:message code="mihabitat.recordatorio.hora" />"
		                                                required="required" data-bind="value: $root.nota.recordatorio.hora">
		                                         </label>
		                                  </section>
		                                  <section class="col col-md-6">
		                                        <label class="toggle">
					                              <input type="checkbox" name="email"
					                              data-bind="checked: $root.nota.recordatorio.email">
					                              <spring:message code="mihabitat.recordatorio.email" /> 
					                              <i data-swchon-text="<spring:message code="mihabitat.radio.si"/>"
					                              data-swchoff-text="<spring:message code="mihabitat.radio.no"/>"></i>
					                            </label>
		                                  </section>
		                             </div>
		                         </fieldset>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <div class="row">
	                            <!-- ko if: !$root.nota.id() -->
	                                <section class="col col-md-6"></section>
                                    <section class="col col-md-6">
                                        <button type="button" class="btn btn-primary btn-block" data-bind="click: $root.guardarNota">
                                            <spring:message code="mihabitat.botones.guardar" />
                                        </button>
                                    </section>
	                            <!--  /ko -->
	                            <!-- ko if: $root.nota.id() -->
		                            <section class="col col-md-6">
		                                <button type="button" class="btn btn-danger btn-block" data-bind="click: $root.eliminarNota">
                                            <spring:message code="mihabitat.botones.eliminar" />
		                                </button>
		                            </section>
		                            <section class="col col-md-6">
		                                <button type="button" class="btn btn-primary btn-block" data-bind="click: $root.actualizarNota">
		                                    <spring:message code="mihabitat.botones.actualizar" />
		                                </button>
		                            </section>
	                            <!--  /ko -->
                             </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="envio-modal" class="modal fade" tabindex="-1" data-width="640">
		        <div class="modal-dialog">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal">
		                        <span aria-hidden="true">&times;</span><span class="sr-only">
		                            <spring:message code="mihabitat.botones.cerrar" />
		                        </span>
		                    </button>
		                    <h4 class="modal-title"><spring:message code="mihabitat.cobranza.enviar" /></h4>
		                </div>
		                <div class="modal-body">
		                    <form id="envio-form" class="smart-form">
		                        <fieldset>
		                            <div class="row">
		                                <div class="col-sm-12 col-md-12 col-lg-12">
		                                    <table id="table-emails" class="table table-striped table-bordered table-hover" style="width: 100%">
		                                        <thead>
		                                            <tr>
		                                                <th data-class="expand" class="col-md-3" id="th-envio"><spring:message code="mihabitat.departamento" /></th>
		                                                <th data-class="expand" class="col-md-4"><spring:message code="mihabitat.contacto" /></th>
		                                                <th data-class="expand" class="col-md-3"><spring:message code="mihabitat.email.direccion" /></th>
		                                                <th data-class="expand" class="col-md-2"><spring:message code="mihabitat.cobranza.enviar" /></th>
		                                            </tr>
		                                        </thead>
		                                        <tbody data-bind="foreach : { data: $root.emailsEnvio }">
		                                            <tr>
		                                                <td data-bind="text: departamento"></td>
		                                                <td data-bind="text: contacto"></td>
		                                                <td data-bind="text: direccion"></td>
		                                                <td style="text-align: center;">
		                                                    <label class="checkbox"> <input type="checkbox"
		                                                        name="checkbox-inline" data-bind="checked: seleccionado">
		                                                        <i></i>
		                                                    </label>
		                                                </td>
		                                            </tr>
		                                        </tbody>
		                                    </table>
		                                </div>
		                            </div>
		                            <div class="row">
		                              &nbsp;
		                            </div>
		                            <div class="row">
		                                <div class="col-sm-12 col-md-12 col-lg-12">
		                                    <label class="label"><spring:message code="mihabitat.cobranza.enviar.otros" /></label>
		                                    <input class="form-control" type="text" name="otros" id="otros" 
		                                        placeholder="<spring:message code="mihabitat.cobranza.enviar.otros" />"
		                                        data-bind="value: $root.otrosEnvio">
		                                </div>
		                            </div>
		                            <div class="row">
		                                <div class="col-sm-12 col-md-12 col-lg-12">
		                                    <label class="label"><spring:message code="mihabitat.cobranza.enviar.mensaje" /></label>
		                                   <textarea class="form-control" rows="3" name="mensaje" maxlength="256"
		                                        data-bind="value: $root.mensajeEnvio"></textarea>
		                                </div>
		                            </div>
		                        </fieldset>
		                    </form>
		                </div>
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-primary" data-bind="click : $root.enviar">
		                        <spring:message code="mihabitat.botones.aceptar" />
		                    </button>
		                </div>
		            </div>
		        </div>
		    </div>
        </div>
    </div>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/yadcf/jquery.dataTables.yadcf.js"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/plugin/clockpicker/clockpicker.min.js"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/catalogo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/pagos/condominio-aux.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/agrupador-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/comunes/banco-sat.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cuentas/cuenta.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/descuento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/recargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/tipo-consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/consumos/consumo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/descuento-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/recargo-departamento.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cargos/departamentos/cargo-departamento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo-aplicado.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento-cargo.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/movimientos/movimiento.js?v=${project-version}"></script>

	<script src="${pageContext.request.contextPath}/recursos/js/app/cobranza/cobranza.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cobranza/nota.js?v=${project-version}"></script>
	<script src="${pageContext.request.contextPath}/recursos/js/app/cobranza/cobranza-app.js?v=${project-version}"></script>

	<script type="text/javascript">
		$(function() {
			var viewModel = new CobranzaViewModel();
			ko.applyBindings(viewModel);

			$("#nota-form").validate();

			$("#fecha, #fecha-recordatorio").datepicker({
				minDate : new Date()
			});

			$("#hora").clockpicker({
				placement: "top",
				align: "right",
				donetext: "Ok",
				autoclose: true
			});
		});
	</script>
</body>