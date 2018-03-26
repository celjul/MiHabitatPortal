var AdeudoMeses = function(data) {
	AdeudoProveedor.call(this, data);
    
    var self = this;
    
    self.enero = ko.observable();
    self.febrero = ko.observable();
    self.marzo = ko.observable();
    self.abril = ko.observable();
    self.mayo = ko.observable();
    self.junio = ko.observable();
    self.julio = ko.observable();
    self.agosto = ko.observable();
    self.septiembre = ko.observable();
    self.octubre = ko.observable();
    self.noviembre = ko.observable();
    self.diciembre = ko.observable();
    self.total = ko.observable();
    
    self.limpiarAdeudoMeses= function() {
        self.limpiar();
        
        self.enero(undefined);
        self.febrero(undefined);
        self.marzo(undefined);
        self.abril(undefined);
        self.mayo(undefined);
        self.junio(undefined);
        self.julio(undefined);
        self.agosto(undefined);
        self.septiembre(undefined);
        self.octubre(undefined);
        self.noviembre(undefined);
        self.diciembre(undefined);
        self.total(undefined);
    }
    
    self.cargarAdeudoMeses = function(data) {
        self.cargar(data);
        
        self.enero(data ? data.enero : undefined);
        self.febrero(data ? data.febrero : undefined);
        self.marzo(data ? data.marzo : undefined);
        self.abril(data ? data.abril : undefined);
        self.mayo(data ? data.mayo : undefined);
        self.junio(data ? data.junio : undefined);
        self.julio(data ? data.julio : undefined);
        self.agosto(data ? data.agosto : undefined);
        self.septiembre(data ? data.septiembre : undefined);
        self.octubre(data ? data.octubre : undefined);
        self.noviembre(data ? data.noviembre : undefined);
        self.diciembre(data ? data.diciembre : undefined);
        self.total(data ? data.total : undefined);
    }   
}