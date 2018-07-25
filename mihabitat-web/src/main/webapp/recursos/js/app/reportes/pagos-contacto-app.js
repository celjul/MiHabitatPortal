var PagosContactoViewModel = function() {
    
    var self = this;
    
    self.anios = ko.observableArray();
    self.anios.push(new Date().getFullYear() - 4);
    self.anios.push(new Date().getFullYear() - 3);
    self.anios.push(new Date().getFullYear() - 2);
    self.anios.push(new Date().getFullYear() - 1);
    self.anios.push(new Date().getFullYear());
    
    self.reporte = new Pagos();
    
    self.consulta = function() {
        console.log("Consulta - tipo: " + self.reporte.anio());
        
        if (self.valida()) {
            $.ajax({
                cache : false,
                url: contextPath + "/administrador/reportes/pagos-contacto/consultar",
                data : {
                    anio: self.reporte.anio()
                },
                success: function(data) {
                    self.reporte.limpiar();
                    self.reporte.cargar(data);
                }
            });
        }
    }
    
    self.imprimir = function(formato) {
        if (self.valida()) {
            var url = "/administrador/reportes/pagos-contacto/imprimir"
                + "?formato=" + formato
                + "&anio=" + self.reporte.anio();
            window.open(contextPath + url, '_blank');
        }
    }
    
    self.valida = function () {
        var valid = true;
        if (!self.reporte.anio()) {
            notificacionAdvertencia("Selecciona el año");
            valid = false;
        }
        return valid;
    }
    
    self.reporte.anio.subscribe(function(anio) {
        self.consulta();
    });
    
    if (!self.reporte.anio()) {
        self.reporte.anio(new Date().getFullYear());
    }
}