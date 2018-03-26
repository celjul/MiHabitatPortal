var AntiguedadCuentasCobrarViewModel = function() {
    
    var self = this;
    
    self.tipos = ko.observableArray([{descripcion: "Dias", id : 1}, {descripcion: "Meses", id : 2}, {descripcion: "Años", id : 3}]);
    self.tipo = ko.observable();
    
    self.anios = ko.observableArray();
    self.anios.push(new Date().getFullYear() - 4);
    self.anios.push(new Date().getFullYear() - 3);
    self.anios.push(new Date().getFullYear() - 2);
    self.anios.push(new Date().getFullYear() - 1);
    self.anios.push(new Date().getFullYear());
    
    self.reporte = new AntiguedadCuentasCobrar();
    self.reporte.detalleContacto(true);

    var fecha = new Date();
    self.reporte.fin(fecha.getDate() + "-" + (fecha.getMonth() +1) + "-" + fecha.getFullYear());
    
    self.consulta = function() {
        console.log("Consulta - tipo: " + self.tipo() + " - fecha fin: " + self.reporte.fin() + " - año: " + self.reporte.anio());
        
        self.removerTabla();
        if (self.valida()) {
            $.ajax({
                cache : false,
                url: contextPath + "/administrador/reportes/antiguedad-cuentas-cobrar/consultar",
                data : {
                    tipo: self.tipo(),
                    fin : self.reporte.fin(),
                    anio: self.reporte.anio(),
                    anios : self.anios()
                },
                success: function(data) {
                    self.reporte.limpiarACC();
                    self.reporte.cargarACC(data, self.tipo());
                    if (self.reporte.adeudos().length <= 0) {
                        notificacionAdvertencia("No se encontraron resultados");
                    }
                    self.dibujarTabla();
                }
            });
        }
    }
    
    self.removerTabla = function() {
        for (var int = 1; int <= 3; int++) {
            var id = int == 1 ? "#table-dias" : int == 2 ? "#table-meses" : int == 3 ? "#table-anios" : "";
            
            if ( $.fn.dataTable.isDataTable( id ) ) {
                console.log("Eliminando " + id);
                var otable = $(id).DataTable();
                otable.destroy();
                $(id + " tbody").empty();
            }
        }
    }
    
    self.dibujarTabla = function() {
        var responsiveHelper_dt_basic = undefined;
        var responsiveHelper_datatable_fixed_column = undefined;
        var responsiveHelper_datatable_col_reorder = undefined;
        var responsiveHelper_datatable_tabletools = undefined;

        var breakpointDefinition = {
            tablet : 1024,
            phone : 480
        };
        
        var id = self.tipo() == 1 ? "#table-dias" : self.tipo() == 2 ? "#table-meses" : self.tipo() == 3 ? "#table-anios" : "";
        console.log("Construyendo " + id);
        var otable = $(id).DataTable({
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "ordering" : false,
            "scrollY": "250px",
            "scrollCollapse": true,
            "preDrawCallback" : function() {
                if (!responsiveHelper_datatable_fixed_column) {
                    responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($(id), breakpointDefinition);
                }
            },
            "rowCallback" : function(nRow) {
                responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
            },
            "drawCallback" : function(oSettings) {
                responsiveHelper_datatable_fixed_column.respond();
            },
        });
        $(id + "_filter").hide();
    }
    
    self.imprimir = function(formato) {
        if (self.valida()) {
            var url = contextPath + "/administrador/reportes/antiguedad-cuentas-cobrar/imprimir?formato=" + formato 
                + "&tipo=" + self.tipo()
                + (self.reporte.fin() ? "&fin=" + self.reporte.fin() : "")
                + (self.reporte.anio() ? "&anio=" + self.reporte.anio() : "")
                + "&anios=" + self.anios()
                + "&contacto=" + self.reporte.detalleContacto();
            window.open(contextPath + url, '_blank');
        }
    }
    
    self.valida = function () {
        var valid = true;
        switch (self.tipo()) {
            case 1:
                if (!self.reporte.fin()) {
                    notificacionAdvertencia("Selecciona la fecha de fin");
                    valid = false;
                }
                break;
            case 2:
                if (!self.reporte.anio()) {
                    notificacionAdvertencia("Selecciona el año");
                    valid = false;
                }
                break;
            case 3:
                
                break;
            default:
                notificacionAdvertencia("Selecciona el tipo");
        }
        return valid;
    }
    
    self.tipo.subscribe(function(tipo) {
        self.reporte.limpiarACC();
        
        //self.reporte.fin(undefined);
        self.reporte.anio(undefined);
        
        switch (tipo) {
        case 1:
            self.consulta();
            break;

        case 2:
            self.reporte.anio(new Date().getFullYear());
            break;
            
        case 3:
            self.consulta();
            break;
        }
    });
    
    self.reporte.fin.subscribe(function(fin){
        if (fin) {
            self.consulta();
        }
    });
    
    self.reporte.anio.subscribe(function(anio){
        if (anio) {
            self.consulta();
        }
    });
    
    if (!self.tipo()) {
        self.tipo(self.tipos()[0].id);
    }
}