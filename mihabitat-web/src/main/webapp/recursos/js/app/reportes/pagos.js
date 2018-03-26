var Pagos = function() {
    
    var self = this;
    
    self.anio = ko.observable();
    self.pagos = ko.observableArray();
    
    self.limpiar = function() {
        //self.anio(undefined);
        self.pagos([]);
    }
    
    self.cargar = function(data) {
        //self.anio(data ? data.anio : undefined);
        
        if (data && data.pagos) {
            ko.utils.arrayForEach(data.pagos, function( p ) {
                var dp = new PagoDetalle();
                dp.cargar(p);
                self.pagos.push(dp);
            });
        }
    }
}