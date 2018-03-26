var Cobranza = function() {
    
    var self = this;
    
    self.adeudo = ko.observable();
    self.cargos = ko.observableArray([]);
    self.departamento = new Departamento();
    self.milisegundos = ko.observable();
    self.notas = ko.observableArray([]);
    self.tiempo = ko.observable();
    self.seleccionada = ko.observable();
    
    self.limpiar = function() {
        self.adeudo(undefined);
        self.cargos([]);
        self.departamento.limpiar();
        self.milisegundos(undefined);
        self.notas([]);
        self.tiempo(undefined);
        self.seleccionada(undefined);
    }
    
    self.cargar = function(data) {
        self.adeudo(data ? data.adeudo : undefined);
        
        if (data && data.cargos) {
            ko.utils.arrayForEach(data.cargos, function(c) {
                var cargo = new CargoDepartamento();
                cargo.cargarCargoDepartamento(c);
                self.cargos.push(cargo);
            });
        }
        
        self.departamento.cargar(data ? data.departamento : undefined);
        self.milisegundos(data ? data.milisegundos : undefined);
        self.tiempo(data ? data.tiempo : undefined);
    }
}

var Departamento = function() {
    
    var self = this;
    
    self.contactos = ko.observableArray([]);
    self.grupos = ko.observableArray([]);
    self.id = ko.observable();
    self.nombre = ko.observable();
    
    self.limpiar = function() {
        self.contactos([]);
        self.grupos([]);
        self.id(undefined);
        self.nombre(undefined);
    }
    
    self.cargar = function (data) {
        if (data && data.contactos) {
            ko.utils.arrayForEach(data.contactos, function(c) {
                var contacto = new Contacto();
                contacto.cargar(c.id.contacto);
                self.contactos.push(contacto);
            });
        }
        
        if (data && data.grupos) {
            ko.utils.arrayForEach(data.grupos, function(g) {
                var grupo = {
                        descripcion : g.descripcion
                }
                self.grupos.push(grupo);
            });
        }
        
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre : undefined);
    }
}

var Contacto = function() {
    
    var self = this;
    
    self.emails = ko.observableArray([]);
    self.nombre = ko.observable();
    self.telefonos = ko.observableArray([]);
    
    self.limpiar = function() {
        self.emails([]);
        self.nombre(undefined);
        self.telefonos([]);
    }
    
    self.cargar = function(data) {
        if (data && data.emails) {
            ko.utils.arrayForEach(data.emails, function(e) {
                var email = {
                        direccion : e.direccion
                }
                self.emails.push(email);
            });
        }
        
        self.nombre(data ? data.nombre + ' ' + data.apellidoPaterno + ' ' + (data.apellidoMaterno ? data.apellidoMaterno : '')  : undefined);
        
        if (data && data.telefonos) {
            ko.utils.arrayForEach(data.telefonos, function(t) {
                var telefono = {
                        numero : t.numero
                }
                self.telefonos.push(telefono);
            });
        }
    }
}