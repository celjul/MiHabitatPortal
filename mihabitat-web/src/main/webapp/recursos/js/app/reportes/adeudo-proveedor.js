var AdeudoProveedor = function() {
    
    var self = this;
    
    self.idProveedor = ko.observable();
    self.proveedor = ko.observable();
    self.saldo = ko.observable();
    self.antiguedad = ko.observable();
    
    self.limpiar = function() {
        self.idProveedor(undefined);
        self.proveedor(undefined);
        self.saldo(undefined);
        self.antiguedad(undefined);
    }
    
    self.cargar = function(data) {
        self.idProveedor(data ? data.idProveedor : undefined);
        self.proveedor(data ? data.proveedor : undefined);
        self.saldo(data ? data.saldo : undefined);
        self.antiguedad(data ? data.antiguedad : undefined);
    }
}