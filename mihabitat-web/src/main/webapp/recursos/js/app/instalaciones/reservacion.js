var Reservacion = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.fechaInicio = ko.observable(data ? data.fechaInicio : undefined);
    /*if(data.fechaInicio){
        new Date(data.fechaInicio);
    }*/
    self.fechaFin = ko.observable(data ? data.fechaFin : undefined);
    self.diaCompleto = ko.observable(data ? data.diaCompleto : false);
    self.activo = ko.observable(data ? data.activo : true);
    self.departamento = new Departamento(data ? data.departamento : undefined);
    self.contacto = new Contacto(data ? data.contacto : undefined);
    self.instalacion = new Instalacion(data ? data.instalacion : undefined);
    self.estatusReservacion = new Catalogo(data ? data.estatusReservacion : undefined);
    //self.cargo = new Cargo(data ? data.cargo : undefined);

    self.cargar = function(data) {

        self.id(data ? data.id : undefined);
        self.fechaInicio(data ? data.fechaInicio : undefined);
        self.fechaFin(data ? data.fechaFin : undefined);
        self.diaCompleto(data ? data.diaCompleto : false);
        self.activo(data ? data.activo : true);
        self.departamento.cargar(data ? data.departamento : undefined);
        self.contacto.cargar(data ? data.contacto : undefined);
        self.instalacion.cargar(data ? data.instalacion : undefined);
        self.estatusReservacion.cargar(data ? data.estatusReservacion : undefined);
        //self.cargo.cargar(data ? data.cargo : undefined);
    }

    self.limpiar = function() {

        self.id(undefined);
        self.fechaInicio(undefined);
        self.fechaFin(undefined);
        self.diaCompleto(false);
        self.activo(true);
        self.departamento.limpiar();
        self.contacto.limpiar();
        self.instalacion.limpiar();
        self.estatusReservacion.limpiar();
        //self.cargo.limpiar();
    }

    self.getJson = function() {
        var reservacion = self.estructurar(ko.toJS(self));
        reservacion = validarObject(reservacion);
        return reservacion;
    }

    self.estructurar = function(data) {



        return data;
    }

}