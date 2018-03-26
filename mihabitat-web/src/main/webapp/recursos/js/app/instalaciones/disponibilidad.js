var Disponibilidad = function(data) {
    var self = this;
    /*console.log("DISPONIBILIDAD DATA");
    console.log(ko.toJS(data));*/



    self.id = ko.observable(data ? data.id : undefined);
    self.dia = ko.observable(data ? data.dia : undefined);
    self.activo = ko.observable(data ? data.activo : true);
    self.diaCompleto = ko.observable(data ? data.diaCompleto : undefined);
    self.horaInicio = ko.observable("00:00");
    self.horaFin= ko.observable("23:59");

    self.setDiaCompleto = function() {
        if(self.diaCompleto() == undefined) {
            self.horaInicio("00:00");
            self.horaFin("00:00");
            self.diaCompleto(true)
        }
        else if(self.diaCompleto() == false) {
            self.horaInicio("00:00");
            self.horaFin("00:00");
        }
        else {
            self.horaInicio(undefined);
            self.horaFin(undefined);
        }

    }

    self.setDiaCompletoCambioHora = function() {
        if(self.horaInicio() == "00:00" && self.horaFin() == "00:00") {
            self.diaCompleto(true);
        }
        else {
            self.diaCompleto(false);
        }
    }
    
    self.cargar = function(data) {
    	
    	self.id(data ? data.id : undefined);

    	self.horaInicio(data ? String.fotmatHour(data.horaInicio) : undefined);
        self.horaFin(data ? String.fotmatHour(data.horaFin) : undefined);
        self.setDiaCompletoCambioHora();
        self.dia(data ? data.dia : undefined);
        self.activo(data ? data.activo : undefined);

        inicializarRelojes();
    	
       /* console.log("CARGAR DISPONIBILIDAD ");
        console.log(ko.toJS(self));*/
    }
    
    self.limpiar = function() {
    	/*console.log("LIMPIAR DISPONIBILIDAD ");*/
    	
    	self.id(undefined);
    	self.horaInicio(undefined);
        self.horaFin(undefined);
        self.dia(undefined);
        self.activo(undefined);
    	    	
    }

    self.getJson = function() {
        var disponibilidad = self.estructurar(ko.toJS(self));
        disponibilidad = validarObject(disponibilidad);
        return disponibilidad;
    }
    
    self.estructurar = function(data) {
    	/*console.log("ESTRUCTURAR DISPONIBILIDAD");*/

        delete data.diaCompleto;


		return data;
	}


    
 }