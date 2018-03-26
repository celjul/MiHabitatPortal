var AdeudoDias = function(data) {
	AdeudoProveedor.call(this, data);
    
    var self = this;
    
    self._1_30 = ko.observable();
    self._31_60 = ko.observable();
    self._61_90 = ko.observable();
    self._91_180 = ko.observable();
    self._181 = ko.observable();
    self.total = ko.observable();
    
    self.limpiarAdeudoDias = function() {
        self.limpiar();
        
        self._1_30(undefined);
        self._31_60(undefined);
        self._61_90(undefined);
        self._91_180(undefined);
        self._181(undefined);
        self.total(undefined);
    }
    
    self.cargarAdeudoDias = function(data) {
        self.cargar(data);
        
        self._1_30(data ? data._1_30 : undefined);
        self._31_60(data ? data._31_60 : undefined);
        self._61_90(data ? data._61_90 : undefined);
        self._91_180(data ? data._91_180 : undefined);
        self._181(data ? data._181 : undefined);
        self.total(data ? data.total : undefined);
    }   
}