var SaldoFavorViewModel = function(data) {
    
    var self = this;
    
    self.reporte = new SaldoFavor();
    
    self.consulta = function() {
        console.log("Consulta");
        
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/saldo-favor/consulta",
            data : {
            },
            success: function(data) {
                self.reporte.limpiar();
                self.reporte.cargar(data);
            }
        });
    }
    
    self.imprimir = function(formato) {
        var url = contextPath + "/administrador/reportes/saldo-favor/imprimir?formato="
                    + formato + "&detalle=" +  self.reporte.detalle();
        window.open(contextPath + url, '_blank');
    }
    
    self.consulta();
}