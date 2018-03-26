var Nota = function() {
    
    var self = this;
    
    self.aplica = ko.observable();
    self.departamento = new Departamento();
    self.fecha = ko.observable();
    self.id = ko.observable();
    self.nota = ko.observable();
    self.recordatorio = new Recordatorio();
    
    self.aplica.subscribe(function(val) {
        if (!val) {
            self.recordatorio.limpiar();
        }
    });
    
    self.limpiar = function() {
        self.aplica(undefined);
        self.departamento.limpiar();
        self.fecha(undefined);
        self.id(undefined);
        self.nota(undefined);
        self.recordatorio.limpiar();
    }
    
    self.cargar = function(data) {
        self.departamento.cargar(data ? data.departamento : undefined);
        self.fecha(data ? data.fecha : undefined);
        self.id(data ? data.id : undefined);
        self.nota(data ? data.nota : undefined);
        self.recordatorio.cargar(data ? data.recordatorio : undefined);
        
        if (self.recordatorio.id()) {
            self.aplica(true);
        }
    }
    
    self.getJson = function(departamento) {
        var nota = self.estructurar(ko.toJS(self), departamento);
        nota = validarObject(nota);
        return nota;
    }
    
    self.estructurar = function(data, departamento) {
        if (departamento.id()) {
            data.departamento = {
                    id: departamento.id()
            }
        }
        if (data.aplica && data.recordatorio) {
            data.recordatorio = self.recordatorio.getJson();
        }
        delete data.aplica;
        return data;
    }
}

var Recordatorio = function() {
    
    var self = this;
    
    self.email = ko.observable();
    self.fecha = ko.observable();
    self.hora = ko.observable();
    self.id = ko.observable();
    
    self.limpiar = function() {
        self.email(undefined);
        self.fecha(undefined);
        self.hora(undefined);
        self.id(undefined);
    }
    
    self.cargar = function(data) {
        self.email(data ? data.email : undefined);
        self.fecha(data ? data.fecha.substring(0, 10) : undefined);
        self.hora(data ? data.fecha.substring(11, 16) : undefined);
        self.id(data ? data.id : undefined);
    }
    
    self.getJson = function() {
        var recordatorio = self.estructurar(ko.toJS(self));
        recordatorio = validarObject(recordatorio);
        return recordatorio;
    }
    
    self.estructurar = function(data) {
        if (data.fecha && data.hora) {
            data.fecha = data.fecha + " " + data.hora;
        }
        if (!data.email) {
            data.email = false;
        }
        delete data.hora;
        return data;
    }
}