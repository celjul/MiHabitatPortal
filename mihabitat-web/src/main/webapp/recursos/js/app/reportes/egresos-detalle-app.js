var ReporteEgresoViewModel = function (data) {

    var self = this;

    self.reporte = new ReporteEgresos();

    self.consulta = function () {
        console.log("Consulta - " + self.reporte.inicio() + " - " + self.reporte.fin());
        $.ajax({
            cache: false,
            url: contextPath + "/administrador/reportes/reporteegresos/consulta",
            data: {
                inicio: self.reporte.inicio(),
                fin: self.reporte.fin()
            },
            success: function (data) {
                self.reporte.limpiar();
                self.reporte.cargar(data);
            }
        });
    }

    self.imprimir = function (formato) {
        if (self.valida()) {
            var url = contextPath + "/administrador/reportes/reporteegresos/imprimir"
                + "?formato=" + formato
                + "&inicio=" + self.reporte.inicio()
                + "&fin=" + self.reporte.fin();
            window.open(contextPath + url, '_blank');
        }
    }

    self.valida = function () {
        if (self.isValid()) {
            self.consulta();
        }
    }

    self.isValid = function () {
        if (self.reporte.inicio() && self.reporte.fin()) {
            if (self.getDate(self.reporte.inicio()) > self.getDate(self.reporte.fin())) {
                notificacionAdvertencia("La fecha inicial debe ser anterior a la final.");
                return false;
            }
            return true;
        }
    }

    self.getDate = function (string) {
        var año = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (año.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }

    if (!self.reporte.fin() && !self.reporte.inicio()) {
        if (data && data.fin && data.inicio) {
            self.reporte.inicio(data.inicio);
            self.reporte.fin(data.fin);
        } else {
            var fecha = new Date();
            //self.reporte.inicio("01-" + (fecha.getMonth() + 1) + "-" + fecha.getFullYear());
            //self.reporte.fin(fecha.getDate() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getFullYear());
        }
        self.consulta();
    }
}