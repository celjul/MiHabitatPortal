var AvisoDeCobro = function() {
    
    var self = this;
    
    self.saldoFavor = ko.observable();
    self.cargos = ko.observableArray( [] );
    self.departamento = new Departamento();
    self.contacto = new Contacto();
    self.fecha = ko.observable();

    self.adeudoTotal = ko.computed(function() {
        var adeudo = 0;
        ko.utils.arrayForEach(self.cargos(), function(cargo) {
            adeudo = adeudo + cargo.saldoPendiente();
        });
        return adeudo;
    });
    
    self.limpiar = function() {
        self.saldoFavor( undefined );
        self.cargos([]);
        self.departamento.limpiar();
        self.contacto.limpiar();
        self.fecha(undefined);
    }
    
    self.cargar = function( data ) {
        self.saldoFavor( data ? data.saldoFavor : undefined );

        /*if (data && data.cargos && data.cargos.length > 0) {
            ko.utils.arrayForEach( data.cargos, function( car ) {
                self.cargos.push( car );
            });
        }*/

        if (data && data.cargos != undefined && data.cargos.length > 0) {
            ko.utils.arrayForEach(data.cargos, function(cargo) {
                var c = new CargoDepartamento();
                c.cargarCargoDepartamento(cargo);
                c.departamento = new Departamento();
                c.departamento.cargar(cargo.departamento);
                c.agrupador = {
                    id: cargo.agrupador?cargo.agrupador.id:undefined
                }

                self.cargos.push(c);
            });
        }

        self.departamento.cargar( data ? data.departamento : undefined );
        self.contacto.cargarContacto( data ? data.contacto : undefined );
        self.fecha(data ? data.fecha : undefined);
    }
}
