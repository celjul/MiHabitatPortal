var Contacto = function() {

    var self = this;

    self.id = ko.observable();
    self.nombre = ko.observable();
    self.emails = ko.observableArray([]);

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.nombre(data ? data.nombre + ' ' + data.apellidoPaterno + (data.apellidoMaterno?' '+data.apellidoMaterno:'') : undefined);
        
        if (data && data.emails) {
            ko.utils.arrayForEach(data.emails, function(email) {
                var e = {
                        direccion : email.direccion
                }
                self.emails.push(e);
            });
        }
    }

    self.limpiar = function() {
        self.id(undefined);
        self.nombre(undefined);
        self.emails([]);
    }

    self.getJson = function() {
        var contacto = ko.toJS(self);
        if (contacto.nombre) {
            delete contacto.nombre;
        }
        if (contacto.emails) {
            delete contacto.emails;
        }
        contacto = validarObject(contacto);
        return contacto;
    }
}