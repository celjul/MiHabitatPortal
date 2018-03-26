var MovimientoCargo = function() {
    
    Movimiento.call(this);
    var self = this;
    
//    self.cargo = new CargoDepartamento();
    self.cancelado = ko.observable();
    self.tipo = new Catalogo();
    self.aplicados = ko.observableArray([]);
    //Campos extras
    
    self.pagos = ko.observableArray([]);
    self.pagoscancelados = ko.observableArray([]);
    self.autogenerado = ko.observable();
    
    self.totalPagado = ko.computed(function() {
        var pagado = 0;
        ko.utils.arrayForEach(self.pagos(), function(pago) {
            if (pago.aplicado() && pago.imprimible()) {
                if (pago.haber()) {
                    pagado = pagado + pago.haber();
                }
            }
        });
        return pagado;
    });
    
    self.totalCancelado = ko.computed(function() {
        var cancelado = 0;
        ko.utils.arrayForEach(self.pagoscancelados(), function(pagocancelado) {
            if (pagocancelado.aplicado() && pagocancelado.imprimible()) {
                if (pagocancelado.debe()) {
                    cancelado = cancelado + pagocancelado.debe();
                }
            }
        });
        return cancelado;
    });
    
    self.total = ko.computed(function() {
        return self.totalPagado() - self.totalCancelado();
    });
    
    self.limpiarMC = function() {
//        self.cargo.limpiar();
    	self.limpiar();
        self.tipo.limpiar();
        self.cancelado( undefined );
        self.autogenerado(undefined);
        
    }
    
    self.cargarMC = function(data) {
//        self.cargo.cargar(data ? data.cargo : undefined);
        self.cancelado(data ? data.cancelado : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
        self.autogenerado(data ? data.autogenerado : undefined);
        
        if (data && data.aplicados) {
            ko.utils.arrayForEach(data.aplicados, function(m) {
                switch (m.tipo.id) {
                    case AppConfig.catalogos.movimientos.tipos.pagorecargo :
                    case AppConfig.catalogos.movimientos.tipos.pagocargo :
                    case AppConfig.catalogos.movimientos.tipos.pagodescuento :
                        var mov = new MovimientoCargoAplicado();
                        mov.cargarMCA(m);
                        self.pagos.push(mov);
                        break;
                    case AppConfig.catalogos.movimientos.tipos.cancelacionpagorecargo :
                    case AppConfig.catalogos.movimientos.tipos.cancelacionpagocargo :
                    case AppConfig.catalogos.movimientos.tipos.cancelacionpagodescuento :
                        var mov = new MovimientoCargoAplicado();
                        mov.cargarMCA(m);
                        self.pagoscancelados.push(mov);
                        break;
                }
                var mov = new MovimientoCargoAplicado();
                mov.cargarMCA(m);
                    self.aplicados.push(mov);
            });
        }
        self.cargar(data);
    }
    
    self.getJsonMC = function() {
        var movimiento = self.estructurar(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }
    
    self.estructurar = function (data) {
        data = self.getJson();
        if (data && data.tipo) {
            data.tipo = self.tipo.getJson();
        }
        
        var aplicados = [];
        if(self.aplicados().length > 0) {
            ko.utils.arrayForEach(self.aplicados(), function(m) {
                aplicados.push( m.getJsonMCA() );
            });
        }
        data.aplicados = aplicados;
        
        delete data.pagos;
        delete data.pagoscancelados;
        delete data.totalPagado;
        delete data.totalCancelado;
        delete data.total;
        return data;
    }
}