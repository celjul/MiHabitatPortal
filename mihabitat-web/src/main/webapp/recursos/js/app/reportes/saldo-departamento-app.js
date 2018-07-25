var SaldoDepartamentoViewModel = function(data) {
    
    var self = this;
    
    /*self.periodos = ko.observableArray([]);
    if (data && data.periodos && data.periodos.length > 0) {
        ko.utils.arrayForEach(data.periodos, function(periodo) {
            self.periodos.push(periodo);
        });
    }*/
    
    self.reporte = new SaldoDepartamento();

    var fechaAux = new Date();

    self.reporte.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.reporte.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));
    
    self.consulta = function() {
        /*var fecha = "01-" + self.reporte.fecha();*/
        console.log("Consulta - " + self.reporte.inicio() + " - " + self.reporte.fin());
        
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/saldo-departamento/consulta",
            data : {
                inicio : self.reporte.inicio(),
                fin : self.reporte.fin()
            },
            success: function(data) {
                self.reporte.limpiar();
                if (data != undefined && data.saldos != undefined && data.saldos.length > 0) {
                    ko.utils.arrayForEach(data.saldos, function (sld) {
                        if (sld != undefined && sld.movimientos != undefined && sld.movimientos.length > 0) {
                            ko.utils.arrayForEach(sld.movimientos, function (mov) {
                                if (mov.tipo.id == AppConfig.catalogos.movimientos.tipos.saldoAFavorGenerado) {
                                    mov.tipo.descripcion = "Saldo a Favor Generado";
                                    mov.movimientoCargo = {cargo: {concepto: " "}}
                                } else if (mov.tipo.id == AppConfig.catalogos.movimientos.tipos.aplicacionDeSaldoAFavor) {
                                    mov.tipo.descripcion = "Aplicación de Saldo a Favor";
                                    mov.movimientoCargo = {cargo: {concepto: " "}}
                                } else if (mov.tipo.id == AppConfig.catalogos.movimientos.tipos.pagocargo) {
                                    if (mov.haber) {
                                        mov.tipo = {descripcion: "Abono"};
                                    }
                                    if (mov.debe) {
                                        mov.tipo = {descripcion: "Cancelación de Abono"};
                                    }
                                    mov.movimientoCargo = {cargo: {concepto: "Su Pago Gracias."}}
                                }
                            });
                        }
                    });
                }
                self.reporte.cargar(data);
//                console.log(JSON.stringify(ko.toJS(self.reporte)));
            }
        });
    }
    
    self.imprimir = function(formato) {
        /*var fecha = "01-" + self.reporte.fecha();*/
        var url = "/administrador/reportes/saldo-departamento/imprimir?formato="
                    + formato + "&inicio=" + self.reporte.inicio() + "&fin=" +  self.reporte.fin() + "&detalle=" +  self.reporte.detalle();
        window.open(contextPath + url, '_blank');
    }
    self.consulta();
}