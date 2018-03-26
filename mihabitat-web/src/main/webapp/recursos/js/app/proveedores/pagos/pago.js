var PagoProveedor = function(data) {

    var self = this;

    self.id = ko.observable();
    self.proveedor = new Proveedor(data ? data.proveedor : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.cuenta = new Cuenta(data ? data.cuenta : undefined);
    self.fechaPago = ko.observable(data ? data.fechaPago : undefined);
    self.metodoPago = new Catalogo(data ? data.metodoPago : undefined);
    self.total = ko.observable(data ? data.total : undefined);
    self.movimientos = ko.observableArray();
    self.referencia = ko.observable(data ? data.referencia : undefined);
    self.comentario = ko.observable(data ? data.comentario : undefined);

    self.saldo = ko.observable(data ? data.saldo : undefined);
    self.total.subscribe(function (data) {
        self.total(numeral(data).format("0,0.00"))
    });

    self.limpiar = function () {
        self.id(undefined);
        self.proveedor.limpiar();
        self.condominio.limpiar();
        self.cuenta.limpiar();
        self.fechaPago(undefined);

        self.saldo(undefined);
        self.total(undefined);
        self.metodoPago.limpiar();
        self.referencia(undefined);
        self.comentario(undefined);
        self.movimientos([]);
    }


    self.cargar = function (data) {
        console.log("cargar pago");

        self.id(data ? data.id : undefined);
        self.proveedor.cargar(data ? data.proveedor : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.fechaPago(data ? data.fechaPago : undefined);

        self.saldo(data ? data.saldo : undefined);
        self.total(data ? data.total : undefined);
        self.metodoPago.cargar(data ? data.metodoPago : undefined);
        self.referencia(data ? data.referencia : undefined);
        self.comentario(data ? data.comentario : undefined);

        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function (mov) {
                var movimiento = new MovimientoCfdiAplicado()();
                movimiento.cargar(mov);
                self.movimientos.push(movimiento);
            });
        }
    }


    self.getJson = function (cfdi) {
        var pago = self.estructurar(ko.toJS(self), cfdi);
        pago = validarObject(pago);
        return pago;
    }


    self.estructurar = function (data, cfdi) {
        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }

        if (data && data.proveedor) {
            data.proveedor = {
                id: self.proveedor.id()
            }
        }
        if (data && data.cuenta) {
            data.cuenta = {
                id: data.cuenta.id
            };
        }

        if (cfdi && cfdi.length > 0) {

            var movimientos = [];
            ko.utils.arrayForEach(cfdi, function (f) {

                var pagoTemp = numeral().unformat(f.pagoTemporal());
                if (pagoTemp > 0) {
                    var movimiento = new MovimientoCfdiAplicado();
                    movimiento.debe = pagoTemp;
                    movimiento.id = f.id();
                    delete movimiento.cuenta.cuentaAnterior;
                    delete movimiento.pagoProveedor;
                    movimientos.push(movimiento);
                }
                data.movimientos = movimientos;
            });
            data.total = numeral().unformat((self.total()));
            delete data.saldo;

            return data;
        }
    }
}