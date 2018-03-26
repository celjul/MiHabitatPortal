var EstadoCuenta = function() {
    
    var self = this;
    
    self.saldoAnterior = ko.observable();
    self.saldoFavorAnterior = ko.observable();
    self.totalCargos = ko.observable();
    self.totalRecargos = ko.observable();
    self.totalDescuentos = ko.observable();
    self.total = ko.observable();
    self.totalPagos = ko.observable();
    self.saldoDeudor = ko.observable();
    self.saldoFavor = ko.observable();
    self.saldoFavorAlDia = ko.observable();
    self.movimientos = ko.observableArray( [] );
    self.departamento = new Departamento();
    self.contacto = new Contacto();

    self.avisodecobro = new AvisoDeCobro();
    self.cargos = ko.observableArray( [] );
    self.pagos = ko.observableArray( [] );
    self.saldoAlDia  = ko.computed(function() {
        var adeudo = 0;
        if(self.cargos() && self.cargos().length > 0){
            ko.utils.arrayForEach(self.cargos(), function(cargo) {
                adeudo = adeudo + cargo.saldoPendiente();
            });
        }
        return adeudo;
    });
    
    self.limpiar = function() {
        self.saldoAnterior( undefined );
        self.saldoFavorAnterior( undefined );
        self.totalCargos( undefined );
        self.totalRecargos( undefined );
        self.totalDescuentos( undefined );
        self.total( undefined );
        self.totalPagos( undefined );
        self.saldoDeudor( undefined );
        self.saldoFavor( undefined );
        self.saldoFavorAlDia( undefined );
        self.movimientos([]);
        self.departamento.limpiar();
        self.contacto.limpiar();

        self.avisodecobro.limpiar();
        self.cargos( [] );
        self.pagos( [] );
    }
    
    self.cargar = function( data ) {
        self.saldoAnterior( data ? data.saldoAnterior : undefined );
        self.saldoFavorAnterior( data ? data.saldoFavorAnterior : undefined );
        self.totalCargos( data ? data.totalCargos : undefined );
        self.totalRecargos( data ? data.totalRecargos : undefined );
        self.totalDescuentos( data ? data.totalDescuentos : undefined );
        self.total( data ? data.total : undefined );
        self.totalPagos( data ? data.totalPagos : undefined );
        self.saldoDeudor( data ? data.saldoDeudor : undefined );
        self.saldoFavor( data ? data.saldoFavor : undefined );
        self.saldoFavorAlDia( data ? data.saldoFavorAlDia : undefined );
        
        if (data && data.movimientos && data.movimientos.length > 0) {
            ko.utils.arrayForEach( data.movimientos, function( mov ) {
                self.movimientos.push( mov );
            });
        }
        self.departamento.cargar( data ? data.departamento : undefined );
        self.contacto.cargarContacto( data ? data.contacto : undefined );

        self.avisodecobro.cargar( data ? data.avisodecobro : undefined );

        self.cargos([]);
        if (data != undefined && data.cargos.length > 0) {
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
    }
}
