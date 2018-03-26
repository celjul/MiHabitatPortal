var AdeudoAnios = function(data) {
    Adeudo.call(this, data);
    
    var self = this;
    
    self.anio_1 = ko.observable();
    self.anio_2 = ko.observable();
    self.anio_3 = ko.observable();
    self.anio_4 = ko.observable();
    self.anio_5 = ko.observable();
    self.total = ko.observable();
    
    self.limpiarAdeudoAnios = function() {
        self.limpiar();
        
        self.anio_1(undefined);
        self.anio_2(undefined);
        self.anio_3(undefined);
        self.anio_4(undefined);
        self.anio_5(undefined);
        self.total(undefined);
    }
    
    self.cargarAdeudoAnios = function(data) {
        self.cargar(data);
        
        self.anio_1(data ? data.anio_1 : undefined);
        self.anio_2(data ? data.anio_2 : undefined);
        self.anio_3(data ? data.anio_3 : undefined);
        self.anio_4(data ? data.anio_4 : undefined);
        self.anio_5(data ? data.anio_5 : undefined);
        self.total(data ? data.total : undefined);
    }   
}