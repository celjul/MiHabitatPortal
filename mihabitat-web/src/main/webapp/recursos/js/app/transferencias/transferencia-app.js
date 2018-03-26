var TransferenciaViewApp = function(data) {

    var self = this;

    self.bancosCajas = ko.observableArray();
    if (data && data.bancosCajas) {
        self.bancosCajas(ordenar(data.bancosCajas, true));
    }


    self.metodosTransferencia = ko.observableArray();
    if (data && data.metodosTransferencia) {
        ko.utils.arrayForEach(data.metodosTransferencia, function (mp) {
            if(mp.id != AppConfig.catalogos.metodos.saldofavor) {
                if(mp.id == AppConfig.catalogos.metodos.deposito) {
                    mp.descripcion = mp.descripcion + '/Retiro'
                }
                var metodo = new Catalogo();
                metodo.cargar(mp);
                self.metodosTransferencia.push(mp);
            }
        });
    }

    self.condominio = ko.observable(data.condominio.id);
    self.transferencia = new Transferencia();

    self.valida = function() {
        if (!$("#transferencia-form").valid()) {
            notificacionError("El formulario no es válido");
            return false;
        }
        return true;
    }

    self.guardar = function() {
        if (self.valida()) {
            var transferencia = self.transferencia.getJson();
            transferencia.condominio = {
                id: self.condominio()
            }
            console.log(JSON.stringify(transferencia));
            $.ajax({
                url: contextPath + "/administrador/transferencias/guardar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(transferencia),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("La transferencia se a realizado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/transferencias/lista";
                    }, 1000);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al realizar la transferencia");
                }
            });
        }
    }

    /*self.actualizar = function() {
        if (self.valida()) {
            var otroIngreso = self.otroIngreso.getJson();
            otroIngreso.condominio = {
                id: self.condominio()
            }
            console.log(JSON.stringify(otroIngreso));
            $.ajax({
                url: contextPath + "/administrador/otrosingresos/actualizar",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(otroIngreso),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("El ingreso se ha actualizado correctamente");
                    setTimeout(function() {
                        location.href = contextPath + "/administrador/otrosingresos/lista";
                    }, 1000);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al actualizar el ingreso");
                }
            });
        }
    }*/

    if (data && data.transferencia) {
        self.transferencia.cargar(data.transferencia);
        /*self.getSaldo();*/

    }

    self.cancelar = function() {

    }
}

var TransferenciaListViewApp = function(data) {

    var self = this;

    self.transferencias = ko.observableArray();

    if (data && data.transferencias) {
        ko.utils.arrayForEach(data.transferencias, function (g) {
            self.transferencias.push(g);
        });
    }

    self.ver = function(data) {
        location.href = contextPath + "/administrador/transferencias/actualizar/" + data.id;
    }
}