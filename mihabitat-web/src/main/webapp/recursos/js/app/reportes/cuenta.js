var Cuenta = function() {
    
    var self = this;
    
    self.debe = ko.observable();
    self.haber = ko.observable();
    self.hijas = ko.observableArray();
    self.id = ko.observable();
    self.inicial = ko.observable();
    self.nombre = ko.observable();
    self.saldo = ko.observable().extend({
        currency: 2
    });;
    
    self.ficticia = ko.observable(false);
    self.movimientos = ko.observableArray();
    self.tipo = ko.observable();
    self.numero = ko.observable();
    
    self.limpiar = function() {
        self.debe(undefined);
        self.haber(undefined);
        self.hijas([]);
        self.id(undefined);
        self.inicial(undefined);
        self.nombre(undefined);
        self.saldo(undefined);
        
        self.ficticia(false);
        self.movimientos([]);
        self.tipo(undefined);
        self.numero(undefined);
    }
    
    self.cargar = function(data) {
        self.debe(data ? data.debe : undefined);
        self.haber(data ? data.haber : undefined);
        
        if (data && data.hijas) {
            ko.utils.arrayForEach(data.hijas, function( hija ) {
                var cuenta = new Cuenta();
                cuenta.cargar(hija);
                self.hijas.push(cuenta);
            });
        }
        
        self.id(data ? data.id : undefined);
        self.inicial(data ? data.inicial : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.saldo(data ? data.saldo : undefined);
        self.ficticia(data ? data.ficticia : false);
        
        if (data && data.movimientos) {
            ko.utils.arrayForEach(data.movimientos, function( mov ) {
                var movimiento = new Movimiento();
                movimiento.cargar(mov);
                self.movimientos.push(movimiento);
            });
        }
        self.tipo(data ? data.tipo : undefined);
        self.numero(data ? data.numero : undefined);
    }
}

var ordenar = function (cuenta, pintarBisnietos) {
    var cuentas = [];
    ko.utils.arrayForEach(cuenta, function (c) {
        if (c && c.numeroHija == null) {
            var padre = c;
            cuentas.push(padre);
            if (padre.cuentasHijas) {
                ko.utils.arrayForEach(padre.cuentasHijas, function (h) {
                    h.nombre = "-- " + h.nombre;
                    var hijo = h;
                    cuentas.push(hijo);
                    if (hijo.cuentasHijas) {
                        ko.utils.arrayForEach(hijo.cuentasHijas, function (n) {
                            n.nombre = "--------- " + n.nombre;
                            var nieto = n;
                            cuentas.push(nieto);
                            if (nieto.cuentasHijas && pintarBisnietos) {
                                ko.utils.arrayForEach(nieto.cuentasHijas, function (b) {
                                    b.nombre = "-------------- " + b.nombre;
                                    var bisnieto = b;
                                    cuentas.push(bisnieto);
                                });
                            }

                        });
                    }
                });
            }
        }
    });
    return cuentas;
}