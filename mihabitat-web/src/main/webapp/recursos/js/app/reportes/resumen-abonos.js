var ResumenDeAbonos = function() {
    
    var self = this;

    self.totalAbonos = ko.observable(0);

    var fechaAux = new Date();

    self.fecha = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    self.abonos = ko.observableArray();
    self.cancelados = ko.observable(false);
    self.conSaldoFavor = ko.observable(false);
    self.detalle = ko.observable(false);
    self.usuarioComentario = ko.observable(true);
    
    self.limpiar = function() {
        self.abonos([]);
        self.totalAbonos(0);
    }
    
    self.cargar = function(data) {

        if (data.abonos != undefined && data.abonos.length > 0) {
            ko.utils.arrayForEach(data.abonos, function (abono) {
                var p = new Pago();
                p.cargar(abono);

                p.monto(abono.monto);

                self.totalAbonos(self.totalAbonos() + p.monto());

                self.abonos.push(p);
            });
        }
    }
}