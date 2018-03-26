var Recargo = function (data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.monto = ko.observable(data ? data.monto : undefined);
    self.monto.subscribe(function () {
        self.monto(numeral(self.monto()).format('0,0.00'));
    });
    self.porcentaje = ko.observable(data ? data.porcentaje : true);
    //self.simple = ko.observable(data ? data.simple : undefined);
    self.tipoInteres = new Catalogo(data ? data.tipo : {id : AppConfig.catalogos.cargo.recargos.tiposInteres.unico, descripcion: "Único"});
    self.redondear= ko.observable (data ? data.redondear : false);
    self.cargar = function (data) {
        self.id(data ? data.id : undefined);
        self.monto(data ? data.monto : undefined);
        self.redondear(data ? data.redondear : false);
        self.porcentaje(data ? data.porcentaje : true);
        //self.simple(data ? data.simple : undefined);
        self.tipoInteres.cargar(data ? data.tipoInteres : {id : AppConfig.catalogos.cargo.recargos.tiposInteres.unico, descripcion: "Único"});
    }

    self.limpiar = function () {
        self.id(undefined);
        self.redondear(false);
        self.monto(undefined);
        self.porcentaje(true);
        //self.simple(undefined);
        self.tipoInteres.limpiar();
    }

    self.getJson = function () {
        var recargo = ko.toJS(self);
        recargo = validarObject(recargo);
        return recargo;
    }

    self.estructurar = function (data) {
        if (data.tipoInteres && data.tipoInteres.id) {
            data.tipoInteres = self.tipoInteres.getJson();
        } else {
            data.tipoInteres = {
                id: AppConfig.catalogos.cargo.recargos.tiposInteres.unico
            }
        }
        data.monto = numeral().unformat(data.monto);
        return data;
    }
}