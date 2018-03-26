var ReporteEgresos = function () {

    var self = this;

    self.inicio = ko.observable();
    self.fin = ko.observable();
    self.egresos = ko.observableArray();

    self.limpiar = function () {
        self.inicio(undefined);
        self.fin(undefined);
        self.egresos([]);
    }

    self.cargar = function (data) {
        self.egresos([]);
        if (data && data.egresos) {
            ko.utils.arrayForEach(data.egresos, function (c) {
                var egresos = new EgresoDetalle();
                egresos.cargar(c);
                self.egresos.push(egresos);
            });
        }
    }
}