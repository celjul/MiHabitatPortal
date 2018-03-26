var CuentaDetalleViewModel = function(data) {
    
    var self  = this;
    
    self.reporte = new CuentaDetalle();

    var fechaAux = new Date();

    /*self.fecha = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));*/

    self.reporte.fin(data.fin ? data.fin : moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.reporte.inicio(data.inicio ? data.inicio : moment(fechaAux).format('DD-MM-YYYY'));

    self.cuentas = ko.observableArray([]);
    if (data && data.cuentas) {
        self.cuentas(ordenar(data.cuentas, true));
    }
    
    self.consulta = function() {
        console.log("Consulta - " + self.reporte.inicio() + " - " + self.reporte.fin());
        
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/cuenta-detalle/consulta",
            data : {
                idCuenta : self.reporte.cuenta.id(),
                inicio : self.reporte.inicio(),
                fin : self.reporte.fin(),
                ficticia : self.reporte.cuenta.ficticia()
            },
            success: function(data) {
                self.reporte.limpiar();
                self.reporte.cargar(data);
//                console.log(JSON.stringify(ko.toJS(self.reporte)));
            }
        });
    }
    
    self.valida = function() {
        if (self.isValid()) {
            self.consulta();
        }
    }
    
    self.isValid = function() {
        if (self.reporte.cuenta.id() && self.reporte.inicio() && self.reporte.fin()) {
            if (self.getDate(self.reporte.inicio()) > self.getDate(self.reporte.fin())) {
                notificacionAdvertencia("La fecha inicial debe ser anterior a la final.");
                return false;
            }
            return true;
        }
    }
    
    self.imprimir = function(formato) {
        if (self.isValid()) {
            var url = contextPath + "/administrador/reportes/cuenta-detalle/imprimir?idCuenta="
                        + self.reporte.cuenta.id() + "&inicio="
                        + self.reporte.inicio() + "&fin="
                        + self.reporte.fin() + "&formato="
                        + formato + "&ficticia=" + self.reporte.cuenta.ficticia();
            window.open(contextPath + url, '_blank');
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

    if (!self.reporte.cuenta.id() && data.cuenta) {
        self.reporte.cuenta.id(data.cuenta);
        self.reporte.cuenta.ficticia(data.ficticia);
        self.consulta();
    }
}