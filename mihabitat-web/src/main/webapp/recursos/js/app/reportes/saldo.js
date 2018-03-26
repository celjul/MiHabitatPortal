var Saldo = function() {
    
    var self = this;
    
    self.id = ko.observable();
    self.torresEtiquetas = ko.observable();
    self.departamento = ko.observable();
    self.monto = ko.observable();
    self.generados = ko.observableArray();
    self.gastados = ko.observableArray();
    
    self.limpiar = function() {
        self.id(undefined);
        self.torresEtiquetas(undefined);
        self.departamento(undefined);
        self.monto(undefined);
        self.generados([]);
        self.gastados([]);
    }
    
    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.torresEtiquetas(data ? data.torresEtiquetas : undefined);
        self.departamento(data ? data.departamento : undefined);
        self.monto(data ? data.monto : undefined);
        
        if (data && data.generados) {
            ko.utils.arrayForEach(data.generados, function(s) {
                var detalle = new SaldoDetalle();
                detalle.cargar(s);
                self.generados.push(detalle);
            });
        }
        
        /*if (data && data.gastados) {
            ko.utils.arrayForEach(data.gastados, function(s) {
                var detalle = new SaldoDetalle();
                detalle.cargar(s);
                self.generados.push(detalle);
            });
        }

        self.generados().sort(
            function(a, b){
                if(a.fecha() < b.fecha()) return -1;
                if(a.fecha() > b.fecha()) return 1;
                return 0;
            }
        );*/
    }
}