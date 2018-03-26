var Facturasxpagar = function(data) {

    var self = this;

    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.cuenta = new Cuenta();
    self.id = ko.observable();
    self.proveedor = new Proveedor(data ? data.condominio : undefined);
    self.fechaRecepcion = ko.observable();
    self.fechaVencimiento = ko.observable();
    self.numeroFactura = ko.observable();
    self.monto = ko.observable();
    self.comentarios = ko.observable();


    /**self.fechaVencimientoCalculada = ko.pureComputed( {
        read: function() {
            if (self.fechaVencimiento) {return self.fechaVencimiento();}
            if (self.proveedor.id() && self.fechaRecepcion()) {
                var fecVenc = Date.convertir(self.fechaRecepcion());
                fecVenc.setDate(fecVenc.getDate() + self.proveedor.diasCredito());
                return Date.convertirFecha(fecVenc);
            }
        },
        write: function(value) {
            console.log(value)
            self.fechaVencimiento(value);
        },
        owner: self
    });**/
    
    self.limpiar = function() {
        self.condominio.limpiar();
        self.id(undefined);
        self.proveedor.limpiar();
        self.cuenta.limpiar();
        self.fechaRecepcion(undefined);
        self.fechaVencimiento(undefined);
        self.numeroFactura(undefined);
        self.monto(undefined);
        self.comentarios(undefined);
    }
    

    self.cargar = function(data) {
    	console.log("cargar facturas por pagar");
        console.log("..." + data);

        self.condominio.cargar(data ? data.condominio : undefined);
        self.id(data ? data.id : undefined);
        self.proveedor.cargar(data ? data.proveedor : undefined);
        self.cuenta.cargar(data ? data.cuenta : undefined);
        self.fechaRecepcion(data ? data.fechaRecepcion : undefined);
        self.fechaVencimiento(data ? data.fechaVencimiento : undefined);
        self.numeroFactura(data ? data.numeroFactura : undefined);
        self.monto(data ? data.monto : undefined);
        self.comentarios(data ? data.comentarios : undefined);
    }
    
    
    self.getJson = function() {
        var factura = self.estructurar(ko.toJS(self));
        factura = validarObject(factura);
        return factura;
    }
    
    
    self.estructurar = function(data) {
        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }

        if(data && data.proveedor) {
            data.proveedor = self.proveedor.getJson();
        }
        if (data && data.cuenta) {
            data.cuenta = self.cuenta.getJson();
        }

        //delete data.cuenta;
        return data;
    }
    
}
