var Departamento = function() {
    
    var self = this;

    //Para poder crear cargos en esta pantalla
    self.seleccionado = ko.observable();
    //self.cargo = new CargoDepartamento();
    //self.mantenimiento = new MantenimientoCondominio();

    self.id = ko.observable();
    self.nombre = ko.observable();
    self.grupos = ko.observableArray([]);


    self.cargar = function(data) {

        //para poder crear cargos en esta pantalla
        //self.cargo.cargarCargoDepartamento(data ? data.cargo : undefined);
        //self.mantenimiento.cargar(data ? data.mantenimiento : undefined);

        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.grupos(data ? data.grupos : []);

    }

    self.limpiar = function() {

        //para poder crfear cargos en esta pantalla
        //self.cargo.limpiar();

        self.seleccionado(undefined);
        //self.mantenimiento.limpiar();

        self.id(undefined);
        self.nombre(undefined);
        self.grupos([]);
    }

    self.getJson = function() {
        var departamento = self.estructurar(ko.toJS(self));
        departamento = validarObject(departamento);
        return departamento;
    }
    
    self.estructurar = function(data) {
        /*if (departamento.total) {
            delete departamento.total;
        }*/
        /*if (data.mantenimiento) {
            data.mantenimiento = self.mantenimiento.getJson();
        }*/
        /*if(data.cargo) {
            data.departamento.id = self.id();
        }*/
        if(data.grupos) {
            delete data.grupos;
        }
        return data;
    }
}
