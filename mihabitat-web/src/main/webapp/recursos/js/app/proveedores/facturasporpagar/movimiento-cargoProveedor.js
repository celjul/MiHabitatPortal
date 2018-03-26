var MovimientoCfdi = function(data) {
    Movimiento.call(this);

    var self = this;

    self.cancelado = ko.observable();
    //self.cfdi = new CFDI(data ? data.cfdi : undefined);
    self.tipo = new Catalogo();
    self.aplicados = ko.observableArray([]);
    
    //Campos extras
    self.pagos = ko.observableArray([]);
    
    self.pagado = ko.computed(function() {
        var pagado = 0;
        ko.utils.arrayForEach(self.pagos(), function(pago) {
            if (pago.haber()) {
                pagado = pagado - pago.haber();
            } else if (pago.debe()) {
                pagado = pagado + pago.debe();
            }
        });
        return pagado;
    });

    self.limpiarMCP = function() {
        //self.cfdi.limpiar()
        self.tipo.limpiar();
        self.cancelado(undefined);
        self.aplicados([]);
        self.pagos([]);
    }


    self.cargarMCP = function(data) {

        self.cancelado(data ? data.cancelado : undefined);
        //self.cfdi.cargar(data ? data.cfdi : undefined);
        self.tipo.cargar(data ? data.tipo : undefined);
        
        if (data && data.aplicados) {
            console.log(data.aplicados);
            ko.utils.arrayForEach(data.aplicados, function(m) {
                switch (m.tipo.id) {
                    case AppConfig.catalogos.movimientos.tipos.pago :
                        var mov = new MovimientoCfdiAplicado();
                        mov.cargarMCA(m);
                        self.pagos.push(mov);
                        break;
                }
                var mov = new MovimientoCfdiAplicado();
                mov.cargarMCA(m);
                self.aplicados.push(mov);
            });
        }
    }


    self.getJsonMCP = function() {
        var movimiento = self.estructurarMCP(ko.toJS(self));
        movimiento = validarObject(movimiento);
        return movimiento;
    }


    self.estructurarMCP = function(data) {
        if (data && data.cfdi) {
            data.cfdi = {
                id: data.cfdi.id
            };
        }
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
        delete data.pagado;
        return data;
    }

}
