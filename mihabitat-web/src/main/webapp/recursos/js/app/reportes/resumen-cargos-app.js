var ResumenDeCargosViewModel = function(data) {
    
    var self = this;
    
    /*self.periodos = ko.observableArray([]);
    if (data && data.periodos && data.periodos.length > 0) {
        ko.utils.arrayForEach(data.periodos, function(periodo) {
            self.periodos.push(periodo);
        });
    }*/
    
    self.reporte = new ResumenDeCargos();

    var fechaAux = new Date();

    self.reporte.fin = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));

    fechaAux.setDate(1);
    self.reporte.inicio = ko.observable(moment(fechaAux).format('DD-MM-YYYY'));
    
    self.consulta = function() {
        /*var fecha = "01-" + self.reporte.fecha();*/
        console.log("Consulta - " + self.reporte.inicio() + " - " + self.reporte.fin());
        
        $.ajax({
            cache : false,
            url: contextPath + "/administrador/reportes/resumen-cargos/consulta",
            data : {
                inicio : self.reporte.inicio(),
                fin : self.reporte.fin(),
                cancelados : self.reporte.cancelados()
            },
            success: function(data) {
                self.reporte.limpiar();
                /*if (data.cargos != undefined && data.cargos.length > 0) {
                    ko.utils.arrayForEach(data.cargos, function (cargo) {
                        var c = new CargoDepartamento();
                        c.cargarCargoDepartamento(cargo);
                        c.departamento = new Departamento();
                        c.departamento.cargar(cargo.departamento);
                        c.agrupador = {
                            id: cargo.agrupador ? cargo.agrupador.id : undefined
                        }

                        self.reporte.cargos.push(c);
                    });
                }*/
                self.reporte.cargar(data);
//                console.log(JSON.stringify(ko.toJS(self.reporte)));
            }
        });
    }
    
    self.imprimir = function(formato) {
        /*var fecha = "01-" + self.reporte.fecha();*/
        var url = contextPath + "/administrador/reportes/resumen-cargos/imprimir?formato="
                    + formato + "&inicio=" + self.reporte.inicio() + "&fin=" +  self.reporte.fin() + "&detalle=" +  self.reporte.detalle() + "&cancelados=" +  self.reporte.cancelados();
        window.open(contextPath + url, '_blank');
    }

    self.actualizar = function (data) {
        window.open(contextPath + "/administrador/cargos-departamentos/actualizar/" + data.id(),'_blank');
        //location.href = ;
    };

    self.actualizarSimilares = function (data) {
        window.open(contextPath + "/administrador/cargos-departamentos/agrupador/actualizar/" + data.agrupador.id,'_blank');
        //location.href = contextPath + "/administrador/cargos-departamentos/agrupador/actualizar/" + data.agrupador.id;
    }

    self.consulta();
}