<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<head>
    <title><spring:message code="mihabitat.reportes"/> | <spring:message code="mihabitat.nombre"/></title>
    <style type="text/css">
        .currency {
            text-align: right;
        }
    </style>
</head>
<body>
    <div id="ribbon">
        <ol class="breadcrumb">
            <li>
               <a href="${pageContext.request.contextPath}/administrador/inicio?condominio=${condominio.id}">
                   <spring:message code="mihabitat.menu.inicio"/>
               </a>
             </li>
        </ol>
    </div>
    <div id="content">
        <div class="row">
            <div class="col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget" id="wid-id-1">
                    <header>
                        <span class="widget-icon"> <i class="fa fa-edit"></i>
                        </span>
                        <h2>
                            <spring:message code="mihabitat.reportes.cuentadetalle" />
                        </h2>
                        <div class="widget-toolbar">
                            <a href="#" data-bind="event : { click: function() { $root.imprimir('txt') } }" class="btn btn-primary">
                                <i class="fa fa-file-text-o"></i>
                                <span class="hidden-mobile"><spring:message code="mihabitat.reportes.txt" /></span>
                            </a>
                        </div>
                        <div class="widget-toolbar">
                            <a href="#" data-bind="event : { click: function() { $root.imprimir('csv') } }" class="btn btn-primary">
                                <i class="fa fa-file-excel-o"></i>
                                <span class="hidden-mobile"><spring:message code="mihabitat.reportes.csv" /></span>
                            </a>
                        </div>
                        <div class="widget-toolbar">
                            <a href="#" data-bind="event : { click: function() { $root.imprimir('xlsx') } }" class="btn btn-primary">
                                <i class="fa fa-file-excel-o"></i>
                                <span class="hidden-mobile"><spring:message code="mihabitat.reportes.xls" /></span>
                            </a>
                        </div>
                        <div class="widget-toolbar">
                            <a href="#" data-bind="event : { click: function() { $root.imprimir('pdf') } }" class="btn btn-primary">
                                <i class="fa fa-file-pdf-o"></i>
                                <span class="hidden-mobile"><spring:message code="mihabitat.reportes.pdf" /></span>
                            </a>
                        </div>
                    </header>
                    <form id="reporte-form" class="smart-form">
                        <fieldset>
                            <div class="row">
                                <section class="col col-md-3" class="form-group">
                                    <label class="label"> <span class="error-required">*</span>
                                        <spring:message code="mihabitat.reportes.cuentadetalle.cuenta"/>
                                    </label> <label class="input"> <select style="width: 100%"
                                                                           class="select2" name="cuenta"
                                                                           id="cuenta"
                                                                           required="required"
                                                                           data-bind="options: $root.cuentas,
                                                                   optionsText: 'nombre',
                                                                   optionsValue: 'id',
                                                                   value: $root.reporte.cuenta.id,
                                                                   select2: {}">
                                </select>
                                </label>
                                </section>
                                <div class="col col-md-2">
                                    <label class="label">
                                        <span class="error-required">*</span><spring:message code="mihabitat.reportes.cuentadetalle.inicio" />
                                    </label>
                                    <input class="form-control" type="text" name="inicio" id="inicio" 
                                        placeholder="<spring:message code="mihabitat.reportes.cuentadetalle.inicio" />"
                                        required="required" readonly="readonly" style="background: white;"
                                        data-bind="value: $root.reporte.inicio">
                                </div>
                                <div class="col col-md-2">
                                    <label class="label">
                                        <span class="error-required">*</span><spring:message code="mihabitat.reportes.cuentadetalle.fin" />
                                    </label>
                                    <input class="form-control" type="text" name="fin" id="fin" 
                                        placeholder="<spring:message code="mihabitat.reportes.cuentadetalle.fin" />"
                                        required="required" readonly="readonly" style="background: white;"
                                        data-bind="value: $root.reporte.fin">
                                </div>
                                <div class="col col-md-2">
                                    <label class="label">
                                        &nbsp;
                                    </label>
                                    <label class="toggle">
                                        <input type="checkbox" name="ficticia" id="ficticia" data-bind="checked: $root.reporte.cuenta.ficticia">
                                        <i data-swchon-text="No" data-swchoff-text="Si"></i><spring:message code="mihabitat.reportes.cuentadetalle.ficticia" />
                                    </label>
                                </div>
                                <div class="col col-md-1">
                                    <label class="label">
                                        &nbsp;
                                    </label>
                                    <button type="button" class="btn btn-default btn-sm btn-block" data-bind="event : { click: $root.valida }">
                                        <spring:message code="mihabitat.botones.aceptar" />
                                    </button>
                                </div>
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                <div class="col col-md-12">
                                    <h2 data-bind="text: 'DETALLE DE LA CUENTA ' + $root.reporte.cuenta.nombre()"></h2>
                                </div>
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                <div class="col-sm-12 col-md-12 col-lg-12">
                                    <div class="table-responsive">
                                        <table id="table-cuenta-detalle" class="table table-striped table-bordered" style="width: 100%">
                                            <thead>
                                                <tr>
                                                    <th data-class="expand" class="col-sm-2 col-md-3 col-lg-3"><spring:message code="mihabitat.cuenta" /></th>
                                                    <th data-class="expand" class="col-sm-2 col-md-2 col-lg-2"><spring:message code="mihabitat.reportes.cuentadetalle.fecha" /></th>
                                                    <th data-hide="phone, tablet" class="col-sm-5 col-md-5 col-lg-5"><spring:message code="mihabitat.reportes.cuentadetalle.movimiento" /></th>
                                                    <th data-class="expand" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reportes.cuentadetalle.debe" /></th>
                                                    <th data-class="expand" class="col-sm-1 col-md-1 col-lg-1"><spring:message code="mihabitat.reportes.cuentadetalle.haber" /></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- ko foreach: { data: $root.reporte.movimientos } -->
                                                    <tr>
                                                        <td data-bind="text: cuenta.nombre"></td>
                                                        <td data-bind="text: fecha"></td>
                                                        <td data-bind="text: descripcion"></td>
                                                        <td data-bind="currency: debe, symbol: '$'" class="currency"></td>
                                                        <td data-bind="currency: haber, symbol: '$'" class="currency"></td>
                                                    </tr>
                                                <!-- /ko -->
                                                    <tr>
                                                        <td data-bind="text: $root.reporte.cuenta.nombre()"></td>
                                                        <td></td>
                                                        <td> TOTAL </td>
                                                        <td data-bind="currency: $root.reporte.cuenta.debe(), symbol: '$'" class="currency"></td>
                                                        <td data-bind="currency: $root.reporte.cuenta.haber(), symbol: '$'" class="currency"></td>
                                                    </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/plugin/bootstrap-datetimepicker/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/movimiento.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta-detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta-detalle-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new CuentaDetalleViewModel({
                cuenta : ${idCuenta},
                inicio : ${inicio},
                fin : ${fin},
                ficticia : ${ficticia},
                cuentas : ${cuentas}
            });
            ko.applyBindings(viewModel);
            $("#inicio, #fin").datepicker({
                dateFormat: 'dd-mm-yy'
            });
        });
    </script>
</body>