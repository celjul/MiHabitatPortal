var CumplimientoEfectividadCobranzaViewModel = function() {
    
    var self = this;
    
    self.tipos = ko.observableArray([{descripcion: "Mensual", id : 1}, {descripcion: "Anual", id : 2}]);
    self.tipo = ko.observable();
    
    self.anios = ko.observableArray();
    self.anios.push(new Date().getFullYear() - 4);
    self.anios.push(new Date().getFullYear() - 3);
    self.anios.push(new Date().getFullYear() - 2);
    self.anios.push(new Date().getFullYear() - 1);
    self.anios.push(new Date().getFullYear());
    
    self.reporte = new CumplimientoEfectividadCobranza();
    
    self.consulta = function() {
        console.log("Consulta - tipo: " + self.tipo());
        
        self.removerTabla();
        if (self.valida()) {
            $.ajax({
                cache : false,
                url: contextPath + "/administrador/reportes/cumplimiento-efectividad-cobranza/consultar",
                data : {
                    tipo: self.tipo(),
                    anio: self.reporte.anio(),
                    anios : self.anios()
                },
                success: function(data) {
                    self.reporte.limpiar();
                    self.reporte.cargar(data);
                    if (self.reporte.periodos().length < 1) {
                        notificacionAdvertencia("No se encontraron resultados");
                    }
                    self.dibujarTabla();
                }
            });
        }
    }
    
    self.removerTabla = function() {
        var id = "#table-periodos";
        
        if ( $.fn.dataTable.isDataTable( id ) ) {
            var otable = $(id).DataTable();
            otable.destroy();
            $(id + " tbody").empty();
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
        
        var id = "#table-periodos";
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
            var url = "/administrador/reportes/cumplimiento-efectividad-cobranza/imprimir"
                + "?formato=" + formato 
                + "&tipo=" + self.tipo()
                + (self.reporte.anio() ? "&anio=" + self.reporte.anio() : "")
                + "&anios=" + self.anios();
            window.open(contextPath + url, '_blank');
        }
    }
    
    self.valida = function () {
        var valid = true;
        switch (self.tipo()) {
            case 1:
                if (!self.reporte.anio()) {
                    notificacionAdvertencia("Selecciona el año");
                    valid = false;
                }
                break;
            case 2:
                break;
            default:
                notificacionAdvertencia("Selecciona el tipo");
        }
        return valid;
    }
    
    self.tipo.subscribe(function(tipo) {
        self.reporte.limpiar();
        self.reporte.anio(undefined);
        
        switch (tipo) {
        case 1:
            self.reporte.anio(new Date().getFullYear());
            break;
            
        case 2:
            self.consulta();
            break;
        }
    });
    
    self.reporte.anio.subscribe(function(anio) {
        self.consulta();
    });
    
    if (!self.tipo()) {
        self.tipo(self.tipos()[0].id);
    }
}