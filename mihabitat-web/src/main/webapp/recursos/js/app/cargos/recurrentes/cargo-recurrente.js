var CargoRecurrente = function( data ) {
    Cargo.call( this, data );
    var self = this;
    
    self.departamentos = ko.observableArray( data ? data.departamentos : [] );
    self.descuento = new DescuentoRecurrente( data ? data.descuento : undefined );
    self.dia = ko.observable( data ? data.dia : undefined );
    self.todos = ko.observable( data ? data.todos : undefined );
    self.primerDiaMes = ko.observable( data ? data.primerDiaMes : undefined );
    self.primerDiaMes.subscribe( function( value ){
    	if( value ){
    		self.ultimoDiaMes( false );
    		self.seleccionarDiaMes( false );
    	}
    });
    self.ultimoDiaMes = ko.observable( data ? data.ultimoDiaMes : undefined );
    self.ultimoDiaMes.subscribe( function( value ){
    	if( value ){
    		self.primerDiaMes( false );
    		self.seleccionarDiaMes( false );
    	}
    });
    self.seleccionarDiaMes = ko.observable( data ? data.seleccionarDiaMes : undefined );
    self.seleccionarDiaMes.subscribe(function( value ){
    	if( value ){
    		self.primerDiaMes( false );
    		self.ultimoDiaMes( false );
    	}
    });
    self.monto = ko.observable( data ? data.monto : undefined );
    self.monto.subscribe(function() {
        self.monto(numeral(self.monto()).format('0,0.00'));
    });
    self.mantenimientoDepartamento = ko.observable( data ? data.mantenimientoDepartamento : undefined );
    self.mantenimientoDepartamento.subscribe( function( val ) {
        if(!val) {
            self.monto(0);
        }
    });
    self.meses = ko.observableArray(data ? data.meses : []);
    self.recargo = new RecargoRecurrente(data ? data.recargo : undefined);
    
    self.aplicaDescuento = ko.observable(false);
    self.aplicaDescuento.subscribe(function(val) {
        if(!val) {
            self.descuento.limpiarDescuentoRecurrente();
        }
    });
    self.aplicaRecargo = ko.observable(false);
    self.aplicaRecargo.subscribe(function(val) {
        if(!val) {
            self.recargo.limpiarRecargoRecurrente();

        }
    });

    self.tipoRecurrente = ko.observable((data && data['@class']) ? data['@class'] : ((data && data.tipoRecurrente) ? data.tipoRecurrente : undefined));

    // MANTENIMIENTOS
    self.mantenimiento = new MantenimientoCondominio(data ? data.mantenimiento : undefined);

    // INSTALACIONES
    self.instalacion = new Instalacion(data ? data.instalacion : undefined);
    
    self.cargarCargoRecurrente = function(data, departamentos, meses) {
        if (data && data.departamentos && data.departamentos.length > 0) {
            ko.utils.arrayForEach(data.departamentos, function(d) {
                ko.utils.arrayForEach(departamentos, function(dv) {
                    if (d.id == dv.id()) {
                        dv.seleccionado(true);
                        return false;
                    }
                });
            });
        }
        
        self.descuento.cargarDescuentoRecurrente(data ? data.descuento : undefined);
        if( data.dia ) {
        	self.seleccionarDiaMes(true);
        }
        self.dia(data ? data.dia : undefined);
        self.todos(data ? data.todos : undefined);
        self.primerDiaMes(data ? data.primerDiaMes : undefined);
        self.ultimoDiaMes(data ? data.ultimoDiaMes : undefined);
        self.mantenimientoDepartamento(data ? data.mantenimientoDepartamento : undefined);
        
        if (data && data.meses && data.meses.length > 0) {
            ko.utils.arrayForEach(data.meses, function(m) {
                ko.utils.arrayForEach(meses, function(mv) {
                    if (m.id == mv.id()) {
                        mv.seleccionado(true);
                        return false;
                    }
                });
            });
        }
        self.monto(data ? data.monto : undefined);
        self.recargo.cargarRecargoRecurrente(data ? data.recargo : undefined);
        
        if (self.descuento.id()) {
            self.aplicaDescuento(true);
        }
        if (self.recargo.id()) {
            self.aplicaRecargo(true);
        }

        self.tipoRecurrente((data && data['@class']) ? data['@class'] : ((data && data.tipoRecurrente) ? data.tipoRecurrente : undefined));

        // MANTENIMIENTOS
        self.mantenimiento.cargar(data ? data.mantenimiento : undefined);

        // INSTALACIONES
        self.instalacion.cargar(data ? data.instalacion : undefined);
        
        self.cargar(data);
    }
    
    self.limpiarCargoRecurrente = function() {
        self.limpiar();
        
        self.departamentos([]);
        self.descuento.limpiarDescuentoRecurrente();
        self.dia(undefined);
        self.todos(undefined);
        self.primerDiaMes(undefined);
        self.ultimoDiaMes(undefined);
        self.seleccionarDiaMes(undefined);
        self.mantenimientoDepartamento(undefined);
        self.meses([]);
        self.monto(undefined);
        self.recargo.limpiarRecargoRecurrente();
        
        self.aplicaDescuento(false);
        self.aplicaRecargo(false);

        self.tipoRecurrente(undefined);

        // MANTENIMIENTOS
        self.mantenimiento.limpiar();

        // INSTALACIONES
        self.instalacion.limpiar();
    }
    
    self.getJsonCargoRecurrente = function(departamentos, meses) {
        var cargo = self.estructurarCargoRecurrente(ko.toJS(self), departamentos, meses);
        cargo = validarObject(cargo);
        return cargo;
    }
    
    self.estructurarCargoRecurrente = function(data, departamentos_vista, meses_vista) {
    	data = self.estructurar(data);
        
        if (departamentos_vista.length > 0) {
            var departamentos = [];
            ko.utils.arrayForEach(departamentos_vista, function(d) {
                if (d.seleccionado()) {  
                    var departamento = {
                            id: d.id(),
                            nombre : d.nombre()
                    }
                    departamentos.push(departamento);
                }
            });
            data.departamentos = departamentos;
        }

        if (meses_vista.length > 0) {
            var meses = [];
            ko.utils.arrayForEach(meses_vista, function(m) {
            	
                if (m.seleccionado()) {
                    var mes = m.id();
                    meses.push(mes);
                }
            });
            data.meses = meses;
        }
        if (data.recargo) {
            if (data.aplicaRecargo) {
                data.recargo = self.recargo.getJsonRecargoRecurrente();

            }
            else {
                delete data.recargo;
            }
        }

        if (data.descuento) {
            if (data.aplicaDescuento) {
                data.descuento = self.descuento.getJsonDescuentoRecurrente();
            }
            else {
                delete data.descuento;
            }
        }

        if (data && data.tipoRecurrente) {
            data['@class'] = data.tipoRecurrente;
            if(data.mantenimiento) {
                data.mantenimiento = {id: data.mantenimiento.id};
            }
            if(data.instalacion) {
                data.instalacion = {id: data.instalacion.id};
            }
            /*if(data.tipoRecurrente == AppConfig.catalogos.cargo.tipos.mantenimiento) {

            }
            else if(data.tipo == config.tipoTemaEvento.id) {
                delete data.tipoIncidencia;
            }
            else if(data.tipo == config.tipoTemaIncidencia.id) {
                delete data.fechaInicio;
                delete data.fechaFin;
            }*/
            delete data.tipoRecurrente;
        }

        data.monto = numeral().unformat(data.monto);
        delete data.aplicaDescuento;
        delete data.aplicaRecargo;
        delete data.seleccionarDiaMes;

        return data;
    }
    
    self.dibujarTablaDepartamentos = function() {
        var otable = $("#table-departamentos").DataTable({
            "aoColumns": [
                null, null, null, { "bSearchable": false, "bSortable": false }
            ],
            "autoWidth" : true,
            "destroy": true,
            "info": false,
            "paging": false,
            "responsive": true,
            "searching": true,
            "scrollY": "250px",
            "scrollX": false,
            "scrollCollapse": true
        });

        yadcf.init(otable, [{
            column_number: 0,
            filter_type: "text",
            filter_default_label : "Filtrar por Departamento"
        },{
            column_number: 1,
            filter_type : 'multi_select',
            filter_default_label : "Filtrar por Torre",
            select_type: 'select2',
            column_data_type: "html",
            html_data_type: "text",
            filter_reset_button_text: false
        },{
            column_number: 2,
            filter_type: "text",
            filter_default_label : "Filtrar por Condómino"
        }]);

        $("#table-departamentos_filter").hide();
    }

    self.comprueba = function() {
        if (self.tipo.id()) {
            if (self.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento) {
                self.mantenimientoDepartamento(false);
                self.tipoRecurrente('com.bstmexico.mihabitat.cargos.model.CargoRecurrenteMantenimiento');
            }
            if (self.tipo.id() == AppConfig.catalogos.cargo.tipos.instalacion) {
                self.mantenimientoDepartamento(false);
                self.tipoRecurrente('com.bstmexico.mihabitat.instalaciones.model.CargoRecurrenteInstalacion');
            }
            /*if (self.tipo.id() != AppConfig.catalogos.cargo.tipos.consumo) {
             self.mantenimientoDepartamento(false);
             self.tipoRecurrente('');
             }*/
            if (self.tipo.id() == AppConfig.catalogos.cargo.tipos.extraordinarios) {
                self.mantenimientoDepartamento(false);
                self.tipoRecurrente('com.bstmexico.mihabitat.cargos.model.CargoRecurrente');
            }
        }

        self.dibujarTablaDepartamentos();
    }
}
    