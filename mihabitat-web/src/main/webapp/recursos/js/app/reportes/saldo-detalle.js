var SaldoDetalle = function() {
    
    var self = this;
    
    self.id = ko.observable();
    self.fecha = ko.observable();
    self.monto = ko.observable();
    self.descripcion = ko.observable();
    
    self.limpiar = function() {
        self.id(undefined);
        self.fecha(undefined);
        self.monto(undefined);
        self.descripcion(undefined);
    }
    
    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.fecha(data ? moment(data.fecha, 'DD-MM-YYYY').format('YYYY-MM-DD') : undefined);
        self.monto(data ? data.monto : undefined);
        self.descripcion(data ? data.descripcion : undefined);
    }
}