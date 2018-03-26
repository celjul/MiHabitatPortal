var IngresosEgresos = function () {

    var self = this;

    self.bancosCorte = new Cuenta();
    self.cajasCorte = new Cuenta();
    self.cobrar = new Cuenta();
    self.pagar = new Cuenta();
    self.ingresos = new Cuenta();
    self.egresos = new Cuenta();
    self.saldos = new Cuenta();
    self.bancos = new Cuenta();
    self.cajas = new Cuenta();
    self.total = new Cuenta();

    self.inicio = ko.observable();
    self.fin = ko.observable();

    self.cuentasinicial = ko.observableArray();
    self.cuentas = ko.observableArray();
    self.cuentasingresos = ko.observableArray();
    self.cuentasegresos = ko.observableArray();
    self.cuentassaldos = ko.observableArray();
    self.cuentasfinal = ko.observableArray();

    self.limpiar = function () {
        self.bancosCorte.limpiar();
        self.cajasCorte.limpiar();
        self.cobrar.limpiar();
        self.pagar.limpiar();
        self.ingresos.limpiar();
        self.egresos.limpiar();
        self.saldos.limpiar();
        self.bancos.limpiar();
        self.cajas.limpiar();
        self.total.limpiar();

        self.inicio(undefined);
        self.fin(undefined);

        self.cuentasinicial([]);
        self.cuentas([]);
        self.cuentasingresos([]);
        self.cuentasegresos([]);
        self.cuentassaldos([]);
        self.cuentasfinal([]);
    }

    self.cargar = function (data) {
        self.bancosCorte.cargar(data ? data.bancosCorte : undefined);
        self.cajasCorte.cargar(data ? data.cajasCorte : undefined);
        self.cobrar.cargar(data ? data.cobrar : undefined);
        self.pagar.cargar(data ? data.pagar : undefined);
        self.ingresos.cargar(data ? data.ingresos : undefined);
        self.egresos.cargar(data ? data.egresos : undefined);
        self.saldos.cargar(data ? data.saldos : undefined);
        self.bancos.cargar(data ? data.bancos : undefined);
        self.cajas.cargar(data ? data.cajas : undefined);
        self.total.cargar(data ? data.total : undefined);

        self.inicio(data ? data.inicio : undefined);
        self.fin(data ? data.fin : undefined);

        self.cuentasinicial.push(self.bancosCorte);
        self.cuentasinicial.push(self.cajasCorte);
        self.cuentas.push(self.cobrar);
        self.cuentas.push(self.pagar);
        self.cuentasingresos.push(self.ingresos);
        self.cuentasegresos.push(self.egresos);
        self.cuentassaldos.push(self.saldos);
        self.cuentasfinal.push(self.bancos);
        self.cuentasfinal.push(self.cajas);
    }
}