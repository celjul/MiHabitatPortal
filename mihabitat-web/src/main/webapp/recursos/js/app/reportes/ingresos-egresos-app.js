var IngresosEgresosViewModel = function () {

    var self = this;

    self.reporte = new IngresosEgresos();

    self.consulta = function() {
        console.log("Consulta - " + self.reporte.inicio() + " - " + self.reporte.fin());

        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/ingresos-egresos/consulta",
            data : {
                inicio : self.reporte.inicio(),
                fin : self.reporte.fin()
            },
            success: function(data) {
                self.reporte.limpiar();
                self.reporte.cargar(data);
                //console.log(JSON.stringify(data));
             },
        });
    }

    self.valida = function() {
        if (self.isValid()) {
            self.consulta();
        }
    }

    self.isValid = function() {
        if (self.reporte.inicio() && self.reporte.fin()) {
            if (self.getDate(self.reporte.inicio()) > self.getDate(self.reporte.fin())) {
                notificacionAdvertencia("La fecha inicial debe ser anterior a la final.");
                return false;
            }
            return true;
        }
    }

    self.imprimir = function(formato) {
        if (self.isValid()) {
            var url = contextPath + "/administrador/reportes/ingresos-egresos/imprimir?inicio="
                        + self.reporte.inicio() + "&fin=" + self.reporte.fin() + "&formato=" + formato;
            window.open(contextPath + url, '_blank');
        }
    }

    self.click = function(data) {
        console.log(data.id());
        if (!data.id() && data.nombre() == 'CUENTAS POR COBRAR') {
            location.href = contextPath + "/administrador/reportes/cuentas-cobrar/?fin=" + self.reporte.fin();
        } else if (data.id()) {
            if (data.id() < 0) {
                notificacionAdvertencia("Esta cuenta no es de este período.");
            } else {
                location.href = contextPath + "/administrador/reportes/cuenta-detalle/?idCuenta="
                    + data.id() + "&inicio=" + self.reporte.inicio() + "&fin=" + self.reporte.fin() + "&ficticia=" + data.ficticia();
            }
        } else {
            notificacionAdvertencia("El detalle de esta cuenta no se puede mostrar.");
        }
    }

    self.getDate = function(string) {
        var anio = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (anio.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }

    if (!self.reporte.inicio() || !self.reporte.fin()) {
        var fecha = new Date();
        self.reporte.inicio("01-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear());
        self.reporte.fin(fecha.getDate() + "-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear());
        self.consulta();
    }
}