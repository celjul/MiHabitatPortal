<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<head>
    <title><spring:message code="mihabitat.reportes"/> | <spring:message code="mihabitat.nombre"/></title>
    <style type="text/css">
        .panel-title {
            font-size: small;
        }

        .currency {
            text-align: right;
        }

        .link {
            text-decoration: underline;
            cursor: pointer;
        }

        .sin-hijos {
            margin-left: 40px;
        }

        .primer {
            background: #848484;
            color: white;
            font-weight: bold;
        }

        .segundo {
            background: #BFBFBF;
            color: black;
        }

        .tercer {
            background: #E0E0E0;
            color: black;
        }

        .cuarto {
            color: black;
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
                        <spring:message code="mihabitat.reportes.ingresosyegresos"/>
                    </h2>
                </header>
                <form id="reporte-form" class="smart-form">
                    <fieldset>
                        <div class="row">
                            <div class="col col-md-2">
                                <label class="label">
                                    <span class="error-required">*</span><spring:message
                                        code="mihabitat.reportes.ingresosyegresos.inicio"/>
                                </label>
                                <input class="form-control" type="text" name="inicio" id="inicio"
                                       placeholder="<spring:message code="mihabitat.reportes.ingresosyegresos.inicio" />"
                                       required="required" readonly="readonly" style="background: white;"
                                       data-bind="value: $root.reporte.inicio">
                            </div>
                            <div class="col col-md-2">
                                <label class="label">
                                    <span class="error-required">*</span><spring:message
                                        code="mihabitat.reportes.ingresosyegresos.fin"/>
                                </label>
                                <input class="form-control" type="text" name="fin" id="fin"
                                       placeholder="<spring:message code="mihabitat.reportes.ingresosyegresos.fin" />"
                                       required="required" readonly="readonly" style="background: white;"
                                       data-bind="value: $root.reporte.fin">
                            </div>
                            <div class="col col-md-2">
                                <label class="label">
                                    &nbsp;
                                </label>
                                <button type="button" class="btn btn-default btn-sm btn-block"
                                        data-bind="event : { click: $root.valida }">
                                    <spring:message code="mihabitat.botones.aceptar"/>
                                </button>
                            </div>
                            <div class="col col-md-2">
                                &nbsp;
                            </div>
                            <div class="col col-md-4">
                                <div class="row">
                                    <div class="col col-md-3">
                                        <label class="label">
                                            &nbsp;
                                        </label>
                                        <button type="button" class="btn btn-primary btn-xs btn-block"
                                                data-bind="event : { click: function() { $root.imprimir('pdf') } }">
                                            <i class="fa fa-file-pdf-o"></i>
                                            <spring:message code="mihabitat.reportes.pdf"/>
                                        </button>
                                    </div>
                                    <div class="col col-md-3">
                                        <label class="label">
                                            &nbsp;
                                        </label>
                                        <button type="button" class="btn btn-primary btn-xs btn-block"
                                                data-bind="event : { click: function() { $root.imprimir('xlsx') } }">
                                            <i class="fa fa-file-excel-o"></i>
                                            <spring:message code="mihabitat.reportes.xls"/>
                                        </button>
                                    </div>
                                    <div class="col col-md-3">
                                        <label class="label">
                                            &nbsp;
                                        </label>
                                        <button type="button" class="btn btn-primary btn-xs btn-block"
                                                data-bind="event : { click: function() { $root.imprimir('csv') } }">
                                            <i class="fa fa-file-excel-o"></i>
                                            <spring:message code="mihabitat.reportes.csv"/>
                                        </button>
                                    </div>
                                    <div class="col col-md-3">
                                        <label class="label">
                                            &nbsp;
                                        </label>
                                        <button type="button" class="btn btn-primary btn-xs btn-block"
                                                data-bind="event : { click: function() { $root.imprimir('txt') } }">
                                            <i class="fa fa-file-text-o"></i>
                                            <spring:message code="mihabitat.reportes.txt"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            &nbsp;
                        </div>

                        <div class="row">
                            &nbsp;
                        </div>

                        <!--                             SALDO INICIAL -->

                        <%--<div  id="total">

                            <h2 class="panel-title"><spring:message code="mihabitat.reportes.ingresosyegresos.total"/></h2>
                        </div>--%>
                        <div class="row">
                            <div class="col-sm-2 col-md-2 col-lg-2 total">
                                <h4><spring:message code="mihabitat.reporte.ingresosyegresos.saldoinicioperiodo"/></h4>

                                <span data-bind="currency: ($root.reporte.bancosCorte.saldo() + $root.reporte.cajasCorte.saldo()), symbol: '$'"></span>

                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="panel-group smart-accordion-default" <%--id="accordion-primer"--%>>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <%--<h4 class="panel-title"><a><spring:message code="mihabitat.reportes.ingresosyegresos.cuentasinicial" /></a></h4>--%>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.retiro"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.deposito"/></a>
                                                    </h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.saldo"/></a></h4>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <!-- ko foreach: { data: $root.reporte.cuentasinicial } -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="primer">
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-6 col-lg-6">
                                                        <h4 class="panel-title">
                                                            <!-- ko if: hijas().length > 0 -->
                                                            <a data-toggle="collapse"
                                                               data-bind="attr : { href : '#collapse-primer' + id() }">
                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                <span class="link"
                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            </a>
                                                            <!-- /ko -->
                                                            <!-- ko if: hijas().length == 0 -->
                                                            <span class="link sin-hijos"
                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            <!-- /ko -->
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: debe, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: haber, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: saldo, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- ko if: hijas().length > 0 -->
                                        <div class="panel-collapse collapse in"
                                             data-bind="attr : { id : 'collapse-primer' + id() }">
                                            <div class="panel-body">
                                                <div class="panel-group smart-accordion-default" id="accordion-segundo">
                                                    <!-- ko foreach: { data: hijas } -->
                                                    <div class="panel panel-default">
                                                        <div class="segundo">
                                                            <div class="row">
                                                                <div class="col-sm-1 col-md-1 col-lg-1">&nbsp;</div>
                                                                <div class="col-sm-5 col-md-5 col-lg-5">
                                                                    <h4 class="panel-title">
                                                                        <!-- ko if: hijas().length > 0 -->
                                                                        <a data-toggle="collapse"
                                                                           data-parent="#accordion-segundo"
                                                                           data-bind="attr : { href : '#collapse-segundo' + id() }"
                                                                           class="collapsed">
                                                                            <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                            <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                            <span class="link"
                                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        </a>
                                                                        <!-- /ko -->
                                                                        <!-- ko if: hijas().length == 0 -->
                                                                        <span class="link sin-hijos"
                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        <!-- /ko -->
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: debe, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: haber, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- ko if: hijas().length > 0 -->
                                                    <div class="panel-collapse collapse"
                                                         data-bind="attr : { id : 'collapse-segundo' + id() }">
                                                        <div class="panel-body">
                                                            <div class="panel-group smart-accordion-default"
                                                                 id="accordion-tercer">
                                                                <!-- ko foreach: { data: hijas } -->
                                                                <div class="panel panel-default">
                                                                    <div class="tercer">
                                                                        <div class="row">
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                &nbsp;</div>
                                                                            <div class="col-sm-4 col-md-4 col-lg-4">
                                                                                <h4 class="panel-title">
                                                                                    <!-- ko if: hijas().length > 0 -->
                                                                                    <a data-toggle="collapse"
                                                                                       data-parent="#accordion-tercer"
                                                                                       data-bind="attr : { href : '#collapse-tercer' + id() }"
                                                                                       class="collapsed">
                                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                                        <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                                        <span class="link"
                                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    </a>
                                                                                    <!-- /ko -->
                                                                                    <!-- ko if: hijas().length == 0 -->
                                                                                    <span class="link sin-hijos"
                                                                                          data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    <!-- /ko -->
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ko if: hijas().length > 0 -->
                                                                <div class="panel-collapse collapse"
                                                                     data-bind="attr : { id : 'collapse-tercer' + id() }">
                                                                    <div class="panel-body">
                                                                        <div class="panel-group smart-accordion-default"
                                                                             id="accordion-cuarto">
                                                                            <!-- ko foreach: { data: hijas } -->
                                                                            <div class="panel panel-default">
                                                                                <div class="cuarto">
                                                                                    <div class="row">
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            &nbsp;</div>
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            <h4 class="panel-title">
                                                                                                <span class="link sin-hijos"
                                                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /ko -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- /ko -->
                                                                <!-- /ko -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /ko -->
                                                    <!-- /ko -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            &nbsp;
                        </div>

                        <!--                             INGRESOS -->

                        <div class="row">
                            <div class="col-sm-2 col-md-2 col-lg-2 tituloafuera">
                                <h4 class="panel-title"><spring:message
                                        code="mihabitat.reportes.ingresosyegresos.ingresosperiodos"/>

                            </div>
                        </div>


                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="panel-group smart-accordion-default" id="accordion-primer">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <%--<h4 class="panel-title"><a><spring:message code="mihabitat.reportes.ingresosyegresos.cuentasingresos" /></a></h4>--%>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.debe"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.haber"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.saldo"/></a></h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ko foreach: { data: $root.reporte.cuentasingresos } -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="primer">
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-6 col-lg-6">
                                                        <h4 class="panel-title">
                                                            <!-- ko if: hijas().length > 0 -->
                                                            <a data-toggle="collapse"
                                                               data-bind="attr : { href : '#collapse-primer' + id() }">
                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                <span class="link"
                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            </a>
                                                            <!-- /ko -->
                                                            <!-- ko if: hijas().length == 0 -->
                                                            <span class="link sin-hijos"
                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            <!-- /ko -->
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: debe, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: haber, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: saldo, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- ko if: hijas().length > 0 -->
                                        <div class="panel-collapse collapse in"
                                             data-bind="attr : { id : 'collapse-primer' + id() }">
                                            <div class="panel-body">
                                                <div class="panel-group smart-accordion-default" id="accordion-segundo">
                                                    <!-- ko foreach: { data: hijas } -->
                                                    <div class="panel panel-default">
                                                        <div class="segundo">
                                                            <div class="row">
                                                                <div class="col-sm-1 col-md-1 col-lg-1">&nbsp;</div>
                                                                <div class="col-sm-5 col-md-5 col-lg-5">
                                                                    <h4 class="panel-title">
                                                                        <!-- ko if: hijas().length > 0 -->
                                                                        <a data-toggle="collapse"
                                                                           data-parent="#accordion-segundo"
                                                                           data-bind="attr : { href : '#collapse-segundo' + id() }"
                                                                           class="collapsed">
                                                                            <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                            <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                            <span class="link"
                                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        </a>
                                                                        <!-- /ko -->
                                                                        <!-- ko if: hijas().length == 0 -->
                                                                        <span class="link sin-hijos"
                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        <!-- /ko -->
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: debe, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: haber, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- ko if: hijas().length > 0 -->
                                                    <div class="panel-collapse collapse"
                                                         data-bind="attr : { id : 'collapse-segundo' + id() }">
                                                        <div class="panel-body">
                                                            <div class="panel-group smart-accordion-default"
                                                                 id="accordion-tercer">
                                                                <!-- ko foreach: { data: hijas } -->
                                                                <div class="panel panel-default">
                                                                    <div class="tercer">
                                                                        <div class="row">
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                &nbsp;</div>
                                                                            <div class="col-sm-4 col-md-4 col-lg-4">
                                                                                <h4 class="panel-title">
                                                                                    <!-- ko if: hijas().length > 0 -->
                                                                                    <a data-toggle="collapse"
                                                                                       data-parent="#accordion-tercer"
                                                                                       data-bind="attr : { href : '#collapse-tercer' + id() }"
                                                                                       class="collapsed">
                                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                                        <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                                        <span class="link"
                                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    </a>
                                                                                    <!-- /ko -->
                                                                                    <!-- ko if: hijas().length == 0 -->
                                                                                    <span class="link sin-hijos"
                                                                                          data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    <!-- /ko -->
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ko if: hijas().length > 0 -->
                                                                <div class="panel-collapse collapse"
                                                                     data-bind="attr : { id : 'collapse-tercer' + id() }">
                                                                    <div class="panel-body">
                                                                        <div class="panel-group smart-accordion-default"
                                                                             id="accordion-cuarto">
                                                                            <!-- ko foreach: { data: hijas } -->
                                                                            <div class="panel panel-default">
                                                                                <div class="cuarto">
                                                                                    <div class="row">
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            &nbsp;</div>
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            <h4 class="panel-title">
                                                                                                <span class="link sin-hijos"
                                                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /ko -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- /ko -->
                                                                <!-- /ko -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /ko -->
                                                    <!-- /ko -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            &nbsp;
                        </div>

                        <!--                             EGRESOS -->

                        <div class="row">
                            <div class="col-sm-2 col-md-2 col-lg-2 tituloafuera">
                                <h4 class="panel-title"><spring:message
                                        code="mihabitat.reportes.ingresosyegresos.egresosperiodos"/>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="panel-group smart-accordion-default" id="accordion-primer">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <%--<h4 class="panel-title"><a><spring:message code="mihabitat.reportes.ingresosyegresos.cuentasegresos" /></a></h4>--%>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.debe"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.haber"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.saldo"/></a></h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ko foreach: { data: $root.reporte.cuentasegresos } -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="primer">
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-6 col-lg-6">
                                                        <h4 class="panel-title">
                                                            <!-- ko if: hijas().length > 0 -->
                                                            <a data-toggle="collapse"
                                                               data-bind="attr : { href : '#collapse-primer' + id() }">
                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                <span class="link"
                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            </a>
                                                            <!-- /ko -->
                                                            <!-- ko if: hijas().length == 0 -->
                                                            <span class="link sin-hijos"
                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            <!-- /ko -->
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: debe, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: haber, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: saldo, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- ko if: hijas().length > 0 -->
                                        <div class="panel-collapse collapse in"
                                             data-bind="attr : { id : 'collapse-primer' + id() }">
                                            <div class="panel-body">
                                                <div class="panel-group smart-accordion-default" id="accordion-segundo">
                                                    <!-- ko foreach: { data: hijas } -->
                                                    <div class="panel panel-default">
                                                        <div class="segundo">
                                                            <div class="row">
                                                                <div class="col-sm-1 col-md-1 col-lg-1">&nbsp;</div>
                                                                <div class="col-sm-5 col-md-5 col-lg-5">
                                                                    <h4 class="panel-title">
                                                                        <!-- ko if: hijas().length > 0 -->
                                                                        <a data-toggle="collapse"
                                                                           data-parent="#accordion-segundo"
                                                                           data-bind="attr : { href : '#collapse-segundo' + id() }"
                                                                           class="collapsed">
                                                                            <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                            <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                            <span class="link"
                                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        </a>
                                                                        <!-- /ko -->
                                                                        <!-- ko if: hijas().length == 0 -->
                                                                        <span class="link sin-hijos"
                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        <!-- /ko -->
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: debe, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: haber, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- ko if: hijas().length > 0 -->
                                                    <div class="panel-collapse collapse"
                                                         data-bind="attr : { id : 'collapse-segundo' + id() }">
                                                        <div class="panel-body">
                                                            <div class="panel-group smart-accordion-default"
                                                                 id="accordion-tercer">
                                                                <!-- ko foreach: { data: hijas } -->
                                                                <div class="panel panel-default">
                                                                    <div class="tercer">
                                                                        <div class="row">
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                &nbsp;</div>
                                                                            <div class="col-sm-4 col-md-4 col-lg-4">
                                                                                <h4 class="panel-title">
                                                                                    <!-- ko if: hijas().length > 0 -->
                                                                                    <a data-toggle="collapse"
                                                                                       data-parent="#accordion-tercer"
                                                                                       data-bind="attr : { href : '#collapse-tercer' + id() }"
                                                                                       class="collapsed">
                                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                                        <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                                        <span class="link"
                                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    </a>
                                                                                    <!-- /ko -->
                                                                                    <!-- ko if: hijas().length == 0 -->
                                                                                    <span class="link sin-hijos"
                                                                                          data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    <!-- /ko -->
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ko if: hijas().length > 0 -->
                                                                <div class="panel-collapse collapse"
                                                                     data-bind="attr : { id : 'collapse-tercer' + id() }">
                                                                    <div class="panel-body">
                                                                        <div class="panel-group smart-accordion-default"
                                                                             id="accordion-cuarto">
                                                                            <!-- ko foreach: { data: hijas } -->
                                                                            <div class="panel panel-default">
                                                                                <div class="cuarto">
                                                                                    <div class="row">
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            &nbsp;</div>
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            <h4 class="panel-title">
                                                                                                <span class="link sin-hijos"
                                                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /ko -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- /ko -->
                                                                <!-- /ko -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /ko -->
                                                    <!-- /ko -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            &nbsp;
                        </div>

                        <!--                             SALDO FINAL -->

                        <div class="row">
                            <div class="col-sm-2 col-md-2 col-lg-2 total">
                                <h4><spring:message code="mihabitat.reportes.ingresosyegresos.saldofinalperiodo"/></h4>

                                <span data-bind="currency: ($root.reporte.bancos.saldo() + $root.reporte.cajas.saldo()), symbol: '$'"></span>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="panel-group smart-accordion-default" id="accordion-primer">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <%--<h4 class="panel-title"><a><spring:message code="mihabitat.reportes.ingresosyegresos.cuentasfinal" /></a></h4>--%>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.retiro"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.deposito"/></a>
                                                    </h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.saldo"/></a></h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ko foreach: { data: $root.reporte.cuentasfinal } -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="primer">
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-6 col-lg-6">
                                                        <h4 class="panel-title">
                                                            <!-- ko if: hijas().length > 0 -->
                                                            <a data-toggle="collapse"
                                                               data-bind="attr : { href : '#collapse-primer' + id() }">
                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                <span class="link"
                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            </a>
                                                            <!-- /ko -->
                                                            <!-- ko if: hijas().length == 0 -->
                                                            <span class="link sin-hijos"
                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            <!-- /ko -->
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: debe, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: haber, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: saldo, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- ko if: hijas().length > 0 -->
                                        <div class="panel-collapse collapse in"
                                             data-bind="attr : { id : 'collapse-primer' + id() }">
                                            <div class="panel-body">
                                                <div class="panel-group smart-accordion-default" id="accordion-segundo">
                                                    <!-- ko foreach: { data: hijas } -->
                                                    <div class="panel panel-default">
                                                        <div class="segundo">
                                                            <div class="row">
                                                                <div class="col-sm-1 col-md-1 col-lg-1">&nbsp;</div>
                                                                <div class="col-sm-5 col-md-5 col-lg-5">
                                                                    <h4 class="panel-title">
                                                                        <!-- ko if: hijas().length > 0 -->
                                                                        <a data-toggle="collapse"
                                                                           data-parent="#accordion-segundo"
                                                                           data-bind="attr : { href : '#collapse-segundo' + id() }"
                                                                           class="collapsed">
                                                                            <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                            <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                            <span class="link"
                                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        </a>
                                                                        <!-- /ko -->
                                                                        <!-- ko if: hijas().length == 0 -->
                                                                        <span class="link sin-hijos"
                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        <!-- /ko -->
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: debe, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: haber, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- ko if: hijas().length > 0 -->
                                                    <div class="panel-collapse collapse"
                                                         data-bind="attr : { id : 'collapse-segundo' + id() }">
                                                        <div class="panel-body">
                                                            <div class="panel-group smart-accordion-default"
                                                                 id="accordion-tercer">
                                                                <!-- ko foreach: { data: hijas } -->
                                                                <div class="panel panel-default">
                                                                    <div class="tercer">
                                                                        <div class="row">
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                &nbsp;</div>
                                                                            <div class="col-sm-4 col-md-4 col-lg-4">
                                                                                <h4 class="panel-title">
                                                                                    <!-- ko if: hijas().length > 0 -->
                                                                                    <a data-toggle="collapse"
                                                                                       data-parent="#accordion-tercer"
                                                                                       data-bind="attr : { href : '#collapse-tercer' + id() }"
                                                                                       class="collapsed">
                                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                                        <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                                        <span class="link"
                                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    </a>
                                                                                    <!-- /ko -->
                                                                                    <!-- ko if: hijas().length == 0 -->
                                                                                    <span class="link sin-hijos"
                                                                                          data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    <!-- /ko -->
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ko if: hijas().length > 0 -->
                                                                <div class="panel-collapse collapse"
                                                                     data-bind="attr : { id : 'collapse-tercer' + id() }">
                                                                    <div class="panel-body">
                                                                        <div class="panel-group smart-accordion-default"
                                                                             id="accordion-cuarto">
                                                                            <!-- ko foreach: { data: hijas } -->
                                                                            <div class="panel panel-default">
                                                                                <div class="cuarto">
                                                                                    <div class="row">
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            &nbsp;</div>
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            <h4 class="panel-title">
                                                                                                <span class="link sin-hijos"
                                                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /ko -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- /ko -->
                                                                <!-- /ko -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /ko -->
                                                    <!-- /ko -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            &nbsp;
                        </div>

                        <!--                             CUENTAS -->

                        <div class="row">
                            <div class="col-sm-2 col-md-2 col-lg-2 tituloafuera">
                                <h4 class="panel-title"><spring:message
                                        code="mihabitat.reportes.ingresosyegresos.cuentaspendientes"/>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="panel-group smart-accordion-default" id="accordion-primer">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-sm-6 col-md-6 col-lg-6">
                                                    <%--<h4 class="panel-title"><a><spring:message code="mihabitat.reportes.ingresosyegresos.cuentas" /></a></h4>--%>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.debe"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.haber"/></a></h4>
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <h4 class="panel-title"><a><spring:message
                                                            code="mihabitat.reportes.ingresosyegresos.saldo"/></a></h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- ko foreach: { data: $root.reporte.cuentas } -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="primer">
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-6 col-lg-6">
                                                        <h4 class="panel-title">
                                                            <!-- ko if: hijas().length > 0 -->
                                                            <a data-toggle="collapse"
                                                               data-bind="attr : { href : '#collapse-primer' + id() }">
                                                                <i class="fa fa-fw fa-plus-circle txt-color-green"></i>

                                                                <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                <span class="link"
                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            </a>
                                                            <!-- /ko -->
                                                            <!-- ko if: hijas().length == 0 -->
                                                            <span class="link sin-hijos"
                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                            <!-- /ko -->
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: debe, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: haber, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                    <div class="col-sm-2 col-md-2 col-lg-2">
                                                        <h4 class="panel-title currency">
                                                            <a data-bind="currency: saldo, symbol: '$'"></a>
                                                        </h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- ko if: hijas().length > 0 -->
                                        <div class="panel-collapse collapse in"
                                             data-bind="attr : { id : 'collapse-primer' + id() }">
                                            <div class="panel-body">
                                                <div class="panel-group smart-accordion-default" id="accordion-segundo">
                                                    <!-- ko foreach: { data: hijas } -->
                                                    <div class="panel panel-default">
                                                        <div class="segundo">
                                                            <div class="row">
                                                                <div class="col-sm-1 col-md-1 col-lg-1">&nbsp;</div>
                                                                <div class="col-sm-5 col-md-5 col-lg-5">
                                                                    <h4 class="panel-title">
                                                                        <!-- ko if: hijas().length > 0 -->
                                                                        <a data-toggle="collapse"
                                                                           data-parent="#accordion-segundo"
                                                                           data-bind="attr : { href : '#collapse-segundo' + id() }"
                                                                           class="collapsed">
                                                                            <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                            <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                            <span class="link"
                                                                                  data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        </a>
                                                                        <!-- /ko -->
                                                                        <!-- ko if: hijas().length == 0 -->
                                                                        <span class="link sin-hijos"
                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                        <!-- /ko -->
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: debe, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: haber, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                                    <h4 class="panel-title currency">
                                                                        <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                    </h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- ko if: hijas().length > 0 -->
                                                    <div class="panel-collapse collapse"
                                                         data-bind="attr : { id : 'collapse-segundo' + id() }">
                                                        <div class="panel-body">
                                                            <div class="panel-group smart-accordion-default"
                                                                 id="accordion-tercer">
                                                                <!-- ko foreach: { data: hijas } -->
                                                                <div class="panel panel-default">
                                                                    <div class="tercer">
                                                                        <div class="row">
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                &nbsp;</div>
                                                                            <div class="col-sm-4 col-md-4 col-lg-4">
                                                                                <h4 class="panel-title">
                                                                                    <!-- ko if: hijas().length > 0 -->
                                                                                    <a data-toggle="collapse"
                                                                                       data-parent="#accordion-tercer"
                                                                                       data-bind="attr : { href : '#collapse-tercer' + id() }"
                                                                                       class="collapsed">
                                                                                        <i class="fa fa-fw fa-plus-circle txt-color-green"></i>
                                                                                        <i class="fa fa-fw fa-minus-circle txt-color-red"></i>
                                                                                        <span class="link"
                                                                                              data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    </a>
                                                                                    <!-- /ko -->
                                                                                    <!-- ko if: hijas().length == 0 -->
                                                                                    <span class="link sin-hijos"
                                                                                          data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                    <!-- /ko -->
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                            <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                <h4 class="panel-title currency">
                                                                                    <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- ko if: hijas().length > 0 -->
                                                                <div class="panel-collapse collapse"
                                                                     data-bind="attr : { id : 'collapse-tercer' + id() }">
                                                                    <div class="panel-body">
                                                                        <div class="panel-group smart-accordion-default"
                                                                             id="accordion-cuarto">
                                                                            <!-- ko foreach: { data: hijas } -->
                                                                            <div class="panel panel-default">
                                                                                <div class="cuarto">
                                                                                    <div class="row">
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            &nbsp;</div>
                                                                                        <div class="col-sm-3 col-md-3 col-lg-3">
                                                                                            <h4 class="panel-title">
                                                                                                <span class="link sin-hijos"
                                                                                                      data-bind="text : nombre, event: { click : $root.click }"></span>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: debe, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: haber, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                        <div class="col-sm-2 col-md-2 col-lg-2">
                                                                                            <h4 class="panel-title currency">
                                                                                                <a data-bind="currency: saldo, symbol: '$'"></a>
                                                                                            </h4>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /ko -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!-- /ko -->
                                                                <!-- /ko -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /ko -->
                                                    <!-- /ko -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /ko -->
                                    </div>
                                    <!-- /ko -->
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/cuenta.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/movimiento.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/ingresos-egresos.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/reportes/ingresos-egresos-app.js?v=${project-version}"></script>
<script type="text/javascript">
    $(function () {
        var viewModel = new IngresosEgresosViewModel();
        ko.applyBindings(viewModel);
        $("#inicio, #fin").datepicker();
    });
</script>
</body>