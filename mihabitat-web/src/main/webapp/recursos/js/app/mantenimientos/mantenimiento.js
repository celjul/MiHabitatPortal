var MantenimientoCondominio = function (data) {
    var self = this;

    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.descripcion = ko.observable(data ? data.descripcion : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.monto = ko.observable(data ? data.monto : undefined);
    self.tipoCobroDepartamento = new Catalogo(data ? data.tipoCobroDepartamento : undefined);

    self.monto.subscribe(function () {
        self.monto(numeral(self.monto()).format('0,0.00'));
    });

    self.cargar = function (data) {
        self.condominio.cargar(data ? data.condominio : undefined);
        self.descripcion(data ? data.descripcion : undefined);
        self.id(data ? data.id : undefined);
        self.monto(data ? data.monto : undefined);
        self.tipoCobroDepartamento.cargar(data ? data.tipoCobroDepartamento : undefined);
    }

    self.limpiar = function () {
        self.condominio.limpiar();
        self.descripcion(undefined);
        self.id(undefined);
        self.monto(undefined);
        self.tipoCobroDepartamento.limpiar();
    }

    self.getJson = function () {
        var mantenimiento = self.estructurar(ko.toJS(self));
        mantenimiento = validarObject(mantenimiento);
        return mantenimiento;
    }

    self.estructurar = function (data) {
        if (data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        data.monto = numeral().unformat(data.monto);
        /*if(!data.id) {
         delete data.monto;
         } else {
         data.monto = numeral().unformat(data.monto);
         }*/
        data.tipoCobroDepartamento = self.tipoCobroDepartamento.getJson();
        return data;
    }
}