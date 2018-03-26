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
                            <spring:message code="mihabitat.reportes.cuentasporcobrar" />
                        </h2>
                    </header>
                    <form id="reporte-form" class="smart-form">
                        <fieldset>
                            <div class="row">
                                <div class="col-sm-6 col-md-6 col-lg-6">
                                    <div class="row">
	                                    <div class="col col-md-2">
		                                    &nbsp;
		                                </div>
		                                <div class="col-xs-6 col-sm-6 col-md-4 col">
		                                    <label class="label">
		                                        <span class="error-required">*</span><spring:message code="mihabitat.reportes.cuentasporcobrar.fin" />
		                                    </label>
		                                    <input class="form-control" type="text" name="fin" id="fin"
		                                        placeholder="<spring:message code="mihabitat.reportes.cuentasporcobrar.fin" />"
		                                        required="required" readonly="readonly" style="background: white;cursor: pointer"
		                                        data-bind="value: $root.reporte.fin">
		                                </div>
		                                <div class="col-xs-6 col-sm-6 col-md-4 col">
		                                    <label class="label">
		                                        &nbsp;
		                                    </label>
		                                    <button type="button" class="btn btn-default btn-sm btn-block" data-bind="event : { click: $root.consulta }">
		                                        <spring:message code="mihabitat.botones.aceptar" />
		                                    </button>
		                                </div>
		                                <div class="col col-md-2">
		                                    &nbsp;
		                                </div>
	                                </div>
	                                <div class="row">
                                        <div class="col-md-1 col-sm-1 col-xs-1">
                                            &nbsp;
                                        </div>
	                                  <div class="col-md-2 col-sm-2 col-xs-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('pdf') } }">
                                                <i class="fa fa-file-pdf-o"></i>
                                                <spring:message code="mihabitat.reportes.pdf" />
                                            </button>
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1">
                                            &nbsp;
                                        </div>
                                        <div class="col-md-2 col-sm-2 col-xs-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('xlsx') } }">
                                                <i class="fa fa-file-excel-o"></i>
                                                <spring:message code="mihabitat.reportes.xls" />
                                            </button>
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1">
                                            &nbsp;
                                        </div>
                                        <div class="col-md-2 col-sm-2 col-xs-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('csv') } }">
                                                <i class="fa fa-file-excel-o"></i>
                                                <spring:message code="mihabitat.reportes.csv" />
                                            </button>
                                        </div>
                                        <div class="col-md-1 col-sm-1 col-xs-1">
                                            &nbsp;
                                        </div>
                                        <div class="col-md-2 col-sm-2 col-xs-2">
                                            <label class="label">
                                                &nbsp;
                                            </label>
                                            <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('txt') } }">
                                                <i class="fa fa-file-text-o"></i>
                                                <spring:message code="mihabitat.reportes.txt" />
                                            </button>
                                        </div>
	                                </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                &nbsp;
                            </div>
                            
                            <div class="row">
                                <div class="col-sm-12 col-md-8 col-lg-8">
                                    <div class="table-responsive">
	                                    <table id="table-cuentas-cobrar" class="table table-striped table-bordered" style="width: 100%">
	                                        <thead>
	                                            <tr>
	                                                <th data-class="expand" class="col-sm-2 col-md-2 col-lg-2"><spring:message code="mihabitat.departamento" /></th>
	                                                <th data-hide="phone, tablet" class="col-sm-4 col-md-4 col-lg-4"><spring:message code="mihabitat.contacto" /></th>
	                                                <th data-class="expand" class="col-sm-3 col-md-3 col-lg-3"><spring:message code="mihabitat.reportes.cuentasporcobrar.saldo" /></th>
	                                                <th data-class="expand" class="col-sm-2 col-md-2 col-lg-2"><spring:message code="mihabitat.reportes.cuentasporcobrar.antiguedad" /></th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                            <!-- ko foreach: { data: $root.reporte.adeudos } -->
	                                                <tr>
	                                                    <td data-bind="text: departamento"></td>
	                                                    <td data-bind="text: contacto"></td>
	                                                    <td class="currency">
                                                            <span data-bind="currency: saldo, symbol: '$'" ></span>
                                                            <span data-bind="visible: saldoFavor" class="label label-success" style="color: #FFF;display: inline;">
                                                                &nbsp;&nbsp;<i class="fa fa-plus-circle"></i>&nbsp; <span data-bind="currency: saldoFavor, symbol: '$'" ></span>
                                                            </span>
                                                        </td>
	                                                    <td data-bind="text: antiguedad"></td>
	                                                </tr>
	                                            <!-- /ko -->
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
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/adeudo.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuentas-cobrar.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuentas-cobrar-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new CuentasCobrarViewModel({
                fin : ${fin}
            });
            ko.applyBindings(viewModel);

            $("#fin").datepicker();
        });
    </script>
</body>