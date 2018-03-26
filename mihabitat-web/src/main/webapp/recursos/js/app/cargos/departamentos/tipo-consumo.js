var TipoConsumo = function(data) {
    var self = this;
    
    self.cuenta = new Cuenta(data ? data.condominio : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    self.id = ko.observable(data ? data.id : undefined);
    self.nombre = ko.observable(data ? data.nombre : undefined);
    self.unidad = new Catalogo(data ? data.unidad : undefined);
    self.catalogoTipoConsumo = new Catalogo(data ? data.catalogoTipoConsumo : undefined);
    self.costoPorUnidad = ko.observable(data ? data.costoPorUnidad : undefined);

    self.unidadConversion = new Catalogo(data ? data.unidadConversion : undefined);
    self.aplicaConversion = ko.observable(data ? data.aplicaConversion : false);
    self.factorConversion = ko.observable(data ? data.factorConversion : 1);
    self.costoPorUnidad.subscribe(function() {
        self.costoPorUnidad(numeral(self.costoPorUnidad()).format('0,0.00'));
    });
    
    self.cargar = function(data) {
    	self.cuenta.cargar(data ? data.cuenta : undefined);
    	self.condominio.cargar(data ? data.condominio : undefined);        
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.unidad.cargar(data ? data.unidad : undefined);
        self.catalogoTipoConsumo.cargar(data ? data.catalogoTipoConsumo : undefined);
        self.costoPorUnidad(data ? data.costoPorUnidad : undefined);

        self.unidadConversion.cargar(data ? data.unidadConversion : undefined);
        self.aplicaConversion(data ? data.aplicaConversion : false);
        self.factorConversion(data ? data.factorConversion : 1);
    }
    
    self.limpiar = function() {
        self.cuenta.limpiar();
        self.condominio.limpiar();
        self.id(undefined);
        self.nombre(undefined);
        self.unidad.limpiar();
        self.catalogoTipoConsumo.limpiar();
        self.costoPorUnidad(undefined);

        self.unidadConversion.limpiar(undefined);
        self.aplicaConversion(false);
        self.factorConversion(1);
    }
    
    self.getJson = function() {
        var consumo = self.estructurar(ko.toJS(self));
        consumo = validarObject(consumo);
        return consumo;
    }
    
    self.estructurar = function(data) {
        if (data && data.cuenta) {
            data.cuenta = {
                    id : self.cuenta.id()
            }
        }
        /*if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }*/
        if (data && data.condominio) {
            data.condominio = {id: data.condominio.id};
        }
        if (data && data.unidad) {
            data.unidad = self.unidad.getJson();
        }
        if (data && data.catalogoTipoConsumo) {
        	data.catalogoTipoConsumo = self.catalogoTipoConsumo.getJson();
        }
        if (data && data.costoPorUnidad) {
            data.costoPorUnidad = numeral().unformat(data.costoPorUnidad);
        }

        return data;
    }
}