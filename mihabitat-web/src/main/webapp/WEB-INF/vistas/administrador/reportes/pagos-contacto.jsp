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
                            <spring:message code="mihabitat.reportes.pagos.contacto" />
                        </h2>
                    </header>
                    <div>
                        <div class="jarviswidget-editbox"></div>
                        <div class="widget-body no-padding">
                            <form id="reporte-form" class="smart-form">
                                <fieldset>
                                    <div class="col-sm-12 col-md-12 col-lg-12">
                                        <div class="row">
                                            <div class="col col-md-2">
                                                <label class="label">
                                                    <spring:message code="mihabitat.reportes.pagos.contacto.anio" />
                                                </label>
                                                <label class="select">
                                                    <select name="anio" id="anio" required="required"
                                                        data-bind="options: $root.anios, 
                                                                     optionsCaption : 'Seleccione una opci�n',
                                                                     value: $root.reporte.anio">
                                                    </select>
                                                    <i></i>
                                                </label>
                                            </div>
                                            <div class="col col-md-4">
                                                <div class="row">
                                                   <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('pdf') } }">
                                                                <i class="fa fa-file-pdf-o"></i>
                                                                <spring:message code="mihabitat.reportes.pdf" />
                                                            </button>
                                                    </div>
                                                    <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('xlsx') } }">
                                                            <i class="fa fa-file-excel-o"></i>
                                                            <spring:message code="mihabitat.reportes.xls" />
                                                        </button>
                                                    </div>
                                                    <div class="col col-md-3">
                                                        <label class="label">
                                                            &nbsp;
                                                        </label>
                                                        <button type="button" class="btn btn-primary btn-xs btn-block" data-bind="event : { click: function() { $root.imprimir('csv') } }">
                                                            <i class="fa fa-file-excel-o"></i>
                                                            <spring:message code="mihabitat.reportes.csv" />
                                                        </button>
                                                    </div>
                                                    <div class="col col-md-3">
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
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <section id="widget-grid" class="">
            <div class="row">
                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="jarviswidget" id="wid-id-2" data-widget-editbutton="false">
                        <header>
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2></h2>
                        </header>
                        <div>
                            <div class="jarviswidget-editbox">
                            </div>
                            <div class="widget-body no-padding">
	                            <table id="table-meses" class="table table-striped table-bordered table-hover" style="width: 100%">
	                                <thead>
	                                    <tr>
	                                        <th data-hide="phone"><spring:message code="mihabitat.contacto" /></th>
	                                        <th><spring:message code="mihabitat.reportes.pagos.contacto.total" /></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[0].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[1].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[2].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[3].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[4].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[5].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[6].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[7].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[8].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[9].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[10].substring(0, 3)"></th>
	                                        <th data-hide="phone, tablet" data-bind="text: AppConfig.catalogos.meses.descripcion.split(',')[11].substring(0, 3)"></th>
	                                    </tr>
	                                </thead>
	                                <tbody data-bind="foreach : { data: $root.reporte.pagos }">
	                                    <tr>
	                                        <td data-bind="text: nombre"></td>
	                                        <td data-bind="currency: total, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: enero, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: febrero, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: marzo, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: abril, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: mayo, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: junio, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: julio, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: agosto, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: septiembre, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: octubre, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: noviembre, symbol: '$'" class="currency"></td>
	                                        <td data-bind="currency: diciembre, symbol: '$'" class="currency"></td>
	                                    </tr>
	                                </tbody>
	                            </table>
                            </div>
                        </div>
                    </div>
                </article>
            </div>
        </section>
    </div>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/pago-detalle.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/pagos.js?v=${project-version}"></script>
    <script src="${pageContext.request.contextPath}/recursos/js/app/reportes/pagos-contacto-app.js?v=${project-version}"></script>
    <script type="text/javascript">
        $(function() {
            var viewModel = new PagosContactoViewModel();
            ko.applyBindings(viewModel);
        });
    </script>
</body>