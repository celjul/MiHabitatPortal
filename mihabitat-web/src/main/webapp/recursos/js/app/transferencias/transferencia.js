var Transferencia = function(data) {

    var self = this;

    self.id = ko.observable();
    self.retiro = new MovimientoGasto();
    self.deposito = new MovimientoGasto();
    self.monto = ko.observable().extend({
        currency: 2
    });
    self.fecha = ko.observable();
    self.comentario = ko.observable();
    self.condominio = new Condominio();
    self.metodoTransferencia = new Catalogo();


    self.limpiar = function() {
        self.id(undefined);
        self.retiro.limpiar();
        self.deposito.limpiar();
        self.monto(undefined);
        self.fecha(undefined);
        self.comentario(undefined);
        self.condominio.limpiar();
        self.metodoTransferencia.limpiar();
    }


    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.retiro.cargarMG(data ? data.retiro : undefined);
        self.deposito.cargarMG(data? data.deposito : undefined);
        self.monto(data? data.monto : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.comentario(data? data.comentario: undefined);
        self.condominio.cargar(data? data.condominio: undefined);
        self.metodoTransferencia.cargar(data? data.metodoTransferencia: undefined);
        $("#cuentaRetiro").select2();
        $("#cuentaDeposito").select2();
    }

    self.getJson = function () {
        var transferencia = self.estructurar(ko.toJS(self));
        transferencia = validarObject(transferencia);
        return transferencia;
    }

    self.estructurar = function(data) {
        if (data.condominio) {
            data.condominio = {
                id: data.condominio.id
            }
        }

        if (data.metodoTransferencia) {
            data.metodoTransferencia = {
                id: data.metodoTransferencia.id
            }
        }
        if (data.retiro) {
            data.retiro = self.retiro.getJsonMG();
            data.retiro.aplicado = true;
            data.retiro.cancelado = false;
            data.retiro.fecha = data.fecha;
            data.retiro.tipo = {
                id: AppConfig.catalogos.movimientos.tipos.transferencia
            }
        }
        if (data.deposito) {
            data.deposito = self.deposito.getJsonMG();
            data.deposito.aplicado = true;
            data.deposito.cancelado = false;
            data.deposito.fecha = data.fecha;
            data.deposito.tipo = {
                id: AppConfig.catalogos.movimientos.tipos.transferencia
            }
        }

        return data;
    }
}
