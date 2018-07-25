var CuentasCobrarViewModel = function(data) {
    
    var self  = this;
    
    self.reporte = new CuentasCobrar();
    
    self.consulta = function() {
        console.log("Consulta - " + self.reporte.fin());
        
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/cuentas-cobrar/consulta",
            data : {
                fin : self.reporte.fin()
            },
            success: function(data) {
                self.reporte.limpiar();
                self.reporte.cargar(data);
                //console.log(JSON.stringify(ko.toJS(self.reporte)));
            }
        });
    }
    
    self.imprimir = function(formato) {
        var url = "/administrador/reportes/cuentas-cobrar/imprimir?fin="
                    + self.reporte.fin() + "&formato=" + formato;
        window.open(contextPath + url, '_blank');
    }
    
    if (!self.reporte.fin()) {
        if (data.fin) {
            self.reporte.fin(data.fin);
        } else {
            var fecha = new Date();
            self.reporte.fin(fecha.getDate() + "-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear());
        }
        self.consulta();
    }
}