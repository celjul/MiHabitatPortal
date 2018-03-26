var AntiguedadCuentasPagar = function() {
    CuentasPagar.call(this);
    
    var self = this;
    
    self.anio = ko.observable();
    self.anios = ko.observableArray();
    
    self.monto = ko.observable();
    self.dias = ko.observable();
    
    self.limpiarACC = function() {
        self.adeudos([]);
        self.anios([]);
    }
    
    self.cargarACC = function(data, tipo) {
        
        if (tipo == 1) {
            if (data && data.adeudos) {
                ko.utils.arrayForEach(data.adeudos, function(a) {
                    var adeudo = new AdeudoDias();
                    adeudo.cargarAdeudoDias(a);
                    self.adeudos.push(adeudo);
                });
            }
        } else if (tipo == 2) {
            if (data && data.adeudos) {
                ko.utils.arrayForEach(data.adeudos, function(a) {
                    var adeudo = new AdeudoMeses();
                    adeudo.cargarAdeudoMeses(a);
                    self.adeudos.push(adeudo);
                });
            }
        } else if (tipo == 3) {
            if (data && data.adeudos) {
                ko.utils.arrayForEach(data.adeudos, function(a) {
                    var adeudo = new AdeudoAnios();
                    adeudo.cargarAdeudoAnios(a);
                    self.adeudos.push(adeudo);
                });
                ko.utils.arrayForEach(data.anios, function(a) {
                    self.anios.push(a);
                });
            }
        }
    }
}