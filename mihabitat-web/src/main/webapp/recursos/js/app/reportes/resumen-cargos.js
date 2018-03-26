var ResumenDeCargos = function() {
    
    var self = this;

    self.totalCargos = ko.observable(0);
    self.totalRecargos = ko.observable(0);
    self.totalDescuentos = ko.observable(0);
    self.totalPagado = ko.observable(0);
    self.totalPendiente = ko.observable(0);

    var fechaAux = new Date();

    self.fecha = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.cargos = ko.observableArray();
    self.cancelados = ko.observable(true);
    self.detalle = ko.observable(false);
    
    self.limpiar = function() {
        self.cargos([]);
        self.totalCargos(0);
        self.totalRecargos(0);
        self.totalDescuentos(0);
        self.totalPagado(0);
        self.totalPendiente(0);
    }
    
    self.cargar = function(data) {

        if (data.cargos != undefined && data.cargos.length > 0) {
            ko.utils.arrayForEach(data.cargos, function (cargo) {
                var c = new CargoDepartamento();
                c.cargarCargoDepartamento(cargo);
                c.departamento = new Departamento();
                c.departamento.cargar(cargo.departamento);
                c.agrupador = {
                    id: cargo.agrupador ? cargo.agrupador.id : undefined
                }

                self.totalCargos(self.totalCargos() + c.totalMonto());
                self.totalRecargos(self.totalRecargos() + c.totalRecargos());
                self.totalDescuentos(self.totalDescuentos() + c.totalDescuentos());
                self.totalPagado(self.totalPagado() + c.totalPagado());
                self.totalPendiente(self.totalPendiente() + c.saldoPendiente());

                self.cargos.push(c);
            });
        }
        
        /*if (data && data.cargos && data.cargos.length > 0) {
            ko.utils.arrayForEach(data.cargos, function(c) {
                var cargo = new CargoDepartamento();
                cargo.cargar(c);
                self.cargos.push(cargo);
            });
        }*/
    }
}