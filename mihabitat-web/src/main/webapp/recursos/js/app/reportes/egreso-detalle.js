var EgresoDetalle = function () {

    var self = this;

    self.fecha = ko.observable();
    self.proveedor = new Proveedor();
    self.conceptos = ko.observable();
    self.metodoPago = new Catalogo();
    self.montoEgreso = ko.observable();

    self.cargar = function (data) {
        self.fecha(data ? data.fecha : undefined);
        self.proveedor.cargar(data ? data.proveedor : undefined);
        self.conceptos(data ? data.conceptos : undefined);
        self.metodoPago.cargar(data ? data.metodoPago : undefined);
        self.montoEgreso(data ? data.montoEgreso : undefined);
    }

    self.limpiar = function () {
        self.fecha(undefined);
        self.proveedor.limpiar;
        self.conceptos(undefined);
        self.metodoPago.limpiar;
        self.montoEgreso(undefined);
    }
}