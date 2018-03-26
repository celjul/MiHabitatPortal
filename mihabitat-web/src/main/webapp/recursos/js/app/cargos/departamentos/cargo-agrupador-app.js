var CargoAgrupadorViewModel = function(data) {
    var self = this;

    self.cargo = new CargoAgrupador();
    
    self.cargo.tipo.id.subscribe(function(val) {
        if (!self.cargo.id()) {
            if (self.cargo.consumo) {
                self.limpiarConsumos();
            }
            self.cargo.monto(undefined);
            ko.utils.arrayForEach(self.departamentos(), function(m) {
                if (m.cargo.consumo) {
                    m.cargo.consumo.limpiarCD();
                }
                m.cargo.monto(0);
            });
        }
        
        var isConsumo = self.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.consumo ? true : false;
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            d.seleccionado(isConsumo);
        });
        if (!isConsumo) {
            self.consumo(undefined);
        } else {
            ko.utils.arrayForEach(self.departamentos(), function(d) {
                d.cargo.consumo.lecturaAnterior(0);
                d.cargo.consumo.lecturaNueva(0);
            });
        }
        
        var isMantenimiento = self.cargo.tipo.id() == AppConfig.catalogos.cargo.tipos.mantenimiento ? true : false;
        if (!isMantenimiento) {
            self.cargo.mantenimientoDepartamento(false);
        }
        
        self.clickTabla();
    });
    
    self.clickTabla = function() {
        setTimeout(function() {                                                                                                                                                 
            $("#th-departamento").click();
        }, 250);
    }

    self.consumos = ko.observableArray([]);
    if (data && data.consumos) {
        $.each(data.consumos, function(i, c) {
            var consumo = new TipoConsumo();
            consumo.cargar(c);
            self.consumos.push(consumo);
        });
    }
    
    self.consumoIndiviso = new ConsumoAgrupadorIndiviso();
    self.consumoSimple = new ConsumoAgrupadorSimple();
    self.consumoProrrateo = new ConsumoAgrupadorProrrateo();
    
    self.consumoSimple.costoUnidad.subscribe(function() {
        self.calcula();
    });
    self.consumoProrrateo.consumo.subscribe(function() {
        self.calcula();
    });
    
    self.consumoProrrateo.costoUnidad.subscribe(function() {
        self.calcula();
    });

    self.consumo = ko.observable();
    
    self.consumo.subscribe(function(val) {
        if (val != undefined) {
            var c = ko.utils.arrayFirst(self.consumos(), function(item) {
                return item.id() == val;
            });
            if (!self.cargo.id()) {
                self.limpiarConsumos();
                if (c.catalogoTipoConsumo.id() == AppConfig.catalogos.consumos.tipos.simple) {
                    self.consumoSimple.costoUnidad(c.costoPorUnidad());
                    self.consumoSimple.catalogoTipo.id(c.catalogoTipoConsumo.id());
                    self.consumoSimple.catalogoTipo.descripcion(c.catalogoTipoConsumo.descripcion());
                    self.consumoSimple.tipo.id(c.id());
                    self.consumoSimple.tipo.nombre(c.nombre());
                    self.consumoSimple.tipo.unidad.id(c.unidad.id());
                    self.consumoSimple.tipo.unidad.descripcion(c.unidad.descripcion());
                    self.consumoSimple.tipo.unidadConversion.id(c.unidadConversion.id());
                    self.consumoSimple.tipo.unidadConversion.descripcion(c.unidadConversion.descripcion());
                    self.consumoSimple.tipo.aplicaConversion(c.aplicaConversion());
                    self.consumoSimple.tipo.factorConversion(c.factorConversion());
                    self.consumoSimple.type("simple");
                    self.cargo.consumo = self.consumoSimple;
                    self.setConsumoDepartamento("dep-simple");
                } else if (c.catalogoTipoConsumo.id() == AppConfig.catalogos.consumos.tipos.prorrateo) {
                    self.consumoProrrateo.costoUnidad(c.costoPorUnidad());
                    self.consumoProrrateo.catalogoTipo.id(c.catalogoTipoConsumo.id());
                    self.consumoProrrateo.catalogoTipo.descripcion(c.catalogoTipoConsumo.descripcion());
                    self.consumoProrrateo.tipo.id(c.id());
                    self.consumoProrrateo.tipo.nombre(c.nombre());
                    self.consumoProrrateo.tipo.unidad.id(c.unidad.id());
                    self.consumoProrrateo.tipo.unidad.descripcion(c.unidad.descripcion());
                    self.consumoProrrateo.tipo.unidadConversion.id(c.unidadConversion.id());
                    self.consumoProrrateo.tipo.unidadConversion.descripcion(c.unidadConversion.descripcion());
                    self.consumoProrrateo.tipo.aplicaConversion(c.aplicaConversion());
                    self.consumoProrrateo.tipo.factorConversion(c.factorConversion());
                    self.consumoProrrateo.type("prorrateo");
                    self.cargo.consumo = self.consumoProrrateo;
                    self.setConsumoDepartamento("dep-prorrateo");
                }
                self.consumoAnterior(c.id());
            } else {
                if (c.catalogoTipoConsumo.id() == AppConfig.catalogos.consumos.tipos.simple) {
                    self.consumoSimple.cargarCAS(data.cargo.consumo);
                    self.cargo.consumo = self.consumoSimple;
                } else if (c.catalogoTipoConsumo.id() == AppConfig.catalogos.consumos.tipos.prorrateo) {
                    self.consumoProrrateo.cargarCAP(data.cargo.consumo);
                    self.cargo.consumo = self.consumoProrrateo;
                }
            }
            self.cargo.concepto(c.nombre());
            self.cargo.cuenta.id(c.cuenta.id());
            self.clickTabla();
        }
    });
    
    self.limpiarConsumos = function() {
        if (self.consumoProrrateo.catalogoTipo.id()) {
            self.consumoProrrateo.limpiarCAP();
            self.cargo.consumo.limpiarCAP();
        } else if (self.consumoSimple.catalogoTipo.id()) {
            self.consumoSimple.limpiarCAS();
            self.cargo.consumo.limpiarCAS();
        }
        ko.utils.arrayForEach(self.departamentos(), function(m) {
            if (m.cargo.consumo) {
                m.cargo.consumo.limpiarCD();
            }
        });
    }

    self.setConsumoDepartamento = function(tipo) {
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            d.cargo.consumo.costoUnidad(self.cargo.consumo.costoUnidad());
            d.cargo.consumo.catalogoTipo.cargar(self.cargo.consumo.catalogoTipo.getJson());
            d.cargo.consumo.tipo.id(self.cargo.consumo.tipo.id());
            d.cargo.consumo.type(tipo);
            d.cargo.consumo.lecturaAnterior(0);
            d.cargo.consumo.lecturaNueva(0);
            if (tipo == "dep-prorrateo") {
                d.cargo.consumo.consumoFactor(0);
            } else {
                d.cargo.consumo.consumoFactor(undefined);
            }
        });
    }

    self.calcula = function() {
        if (self.cargo.consumo instanceof ConsumoAgrupadorSimple) {
            self.calculaSimple();
        } else if (self.cargo.consumo instanceof ConsumoAgrupadorProrrateo) {
            self.calculaProrrateo();
        }
    }
    
    self.calculaSimple = function() {
        if (!self.consumoSimple.costoUnidad()) {
            self.consumoSimple.costoUnidad(0);
        }
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            if (d.cargo.editable()) {
                if (!d.cargo.monto()) {
                    d.cargo.monto(0);
                }
                if (!d.cargo.consumo.lecturaAnterior()) {
                    d.cargo.consumo.lecturaAnterior(0);
                }
                if (!d.cargo.consumo.lecturaNueva()) {
                    d.cargo.consumo.lecturaNueva(0);
                }
                if(self.consumoSimple.tipo.aplicaConversion()) {
                    d.cargo.monto(parseFloat(d.cargo.consumo.consumo() * self.consumoSimple.tipo.factorConversion() * self.consumoSimple.costoUnidad()).toFixed(2));
                } else {
                    d.cargo.monto(parseFloat(d.cargo.consumo.consumo() * self.consumoSimple.costoUnidad()).toFixed(2));
                }

            }
        });
    }

    self.calculaProrrateo = function() {
        if (!self.consumoProrrateo.consumo()) {
            self.consumoProrrateo.consumo(0);
        }
        if (!self.consumoProrrateo.costoUnidad()) {
            self.consumoProrrateo.costoUnidad(0);
        }
        if (!self.consumoProrrateo.factor()) {
            self.consumoProrrateo.factor(0);
        }
        
        var total = 0;
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            total = parseFloat(total) + parseFloat(d.cargo.consumo.consumo());
        });
        
        if (total > 0) {
            self.consumoProrrateo.factor(parseFloat(self.consumoProrrateo.consumo() / total).toFixed(4));
        }
        
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            if (d.cargo.editable()) {
                if(self.consumoProrrateo.tipo.aplicaConversion()) {
                    d.cargo.consumo.consumoFactor(parseFloat(d.cargo.consumo.consumo() * self.consumoProrrateo.tipo.factorConversion() * self.consumoProrrateo.factor()).toFixed(4));
                } else {
                    d.cargo.consumo.consumoFactor(parseFloat(d.cargo.consumo.consumo() * self.consumoProrrateo.factor()).toFixed(4));
                }
                d.cargo.monto(parseFloat(d.cargo.consumo.consumoFactor() * self.consumoProrrateo.costoUnidad()).toFixed(2));
            }
        });
    }

    self.cargo.mantenimientoDepartamento.subscribe(function(val) {
        if (val) {
            ko.utils.arrayForEach(self.departamentos(), function(d) {
                if(d.mantenimiento.tipoCobroDepartamento.id() == AppConfig.catalogos.cargo.tiposCobroMantenimiento.indiviso){
                    d.cargo.monto(d.mantenimiento.monto() * d.unidadIndiviso());
                }else{
                    d.cargo.monto(d.mantenimiento.monto());
                }
            });
        }
    });
    
    self.setCargoMonto = function() {
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            if (d.cargo.editable()) {
                d.cargo.monto(self.cargo.monto());
            }
        });
    }

    self.catalogoInteres = ko.observableArray([]);
    if (data && data.catalogoInteres) {
        $.each(data.catalogoInteres, function(i, tipoInteres) {
            var interes = new Catalogo();
            interes.cargar(tipoInteres);
            self.catalogoInteres.push(interes);
        });
    }

    self.consumoAnterior = function(tipo) {
        if (tipo) {
            $.ajax({
                url : contextPath  + "/administrador/cargos-departamentos/agrupador/anterior",
                type : 'POST',
                dataType : 'json',
                data : {
                    tipo : tipo
                },
                success : function(data) {
                    if (data) {
                        $.each(data.cargos, function(i, c) {
                            ko.utils.arrayForEach(self.departamentos(), function(d) {
                                if (c.departamento.id == d.id()) {
                                    d.cargo.consumo.lecturaAnterior(c.consumo.lecturaNueva);
                                    d.cargo.consumo.lecturaNueva(c.consumo.lecturaNueva);
                                    return false;
                                }
                            });
                        });
                        self.calcula();
                    }
                }
            });
        }
    }

    self.departamentos = ko.observableArray([]);
    if (data && data.departamentos) {
        $.each(data.departamentos, function(i, d) {
            var departamento = new Departamento();
            departamento.cargar(d);
            self.departamentos.push(departamento);
        });
    }

    self.cuenta = new Cuenta();
    self.cuentas = ko.observableArray([]);
    if (data && data.cuentas) {
        self.cuentas(ordenar(data.cuentas, true));
    }

    self.catalogoCargo = ko.observableArray([]);
    if (data && data.catalogoCargo) {
        $.each(data.catalogoCargo, function(i, c) {
            var catalogo = new Catalogo();
            catalogo.cargar(c);
            self.catalogoCargo.push(catalogo);
        });
    }

    if (data && data.cargo) {
        self.cargo.cargarCargoAgrupador(data.cargo, self.departamentos());
        if(!data.cargo.id){
            self.cargo.tipo.id(AppConfig.catalogos.cargo.tipos.extraordinarios);
        }
    }

    if (data && data.condominio) {
        self.cargo.condominio.cargar(data.condominio);
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            d.cargo.condominio.cargar(data.condominio);
        });
    }

    self.seleccionarDepartamentos = ko.observable(false);
    self.seleccionarDepartamentos.subscribe(function(val) {
        var rows = $("#table-departamentos").dataTable()._('tr', {
            "filter" : "applied"
        });
        for (var i = 0; i < rows.length; i++) {
            ko.utils.arrayForEach(self.departamentos(), function(d) {
                if (d.nombre() == rows[i][0]) {
                    d.seleccionado(val);
                }
            });
        }
    });

    self.guardar = function() {
        if ($("#cargo-form").valid()) {
            if (self.validarFecha()) {
                if (self.validaDepartamentos()) {
                    if (self.cargo.consumo instanceof ConsumoAgrupadorSimple) {
                        self.cargo.consumo = self.consumoSimple;
                    } else if (self.cargo.consumo instanceof ConsumoAgrupadorProrrateo) {
                        self.cargo.consumo = self.consumoProrrateo;
                    }
                    var cargo = JSON.stringify(self.cargo.getJsonCargoAgrupador(self.departamentos()));
                    console.log(cargo);
                    $.ajax({
                        url : contextPath
                                + "/administrador/cargos-departamentos/agrupador/guardar",
                        type : 'POST',
                        dataType : 'json',
                        data : cargo,
                        contentType : 'application/json',
                        mimeType : 'application/json',
                        success : function(data) {
                            /*self.cargo.limpiarCargoAgrupador();
                            self.cargo.cargarCargoAgrupador(data, self
                                    .departamentos());
                            self.calcula();*/
                            notificacionExito("Los cargos se han guardado correctamente");
                            setTimeout(function() {
                                location.href = contextPath + "/administrador/cargos-departamentos/lista";
                            }, 1000);
                        },
                        error : function(jqXHR, textStatus, errorThrown) {
                            notificacionError("Ocurrio un error al guardar los cargos");
                        }
                    });
                } else {
                    notificacionAdvertencia("Se requiere al menos un departamento");
                }
            } else {
                notificacionAdvertencia("El dia de descuento tiene que ser mayor al día de cargo y el día de recargo debe ser mayor al día de descuento");
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.actualizar = function() {
        if ($("#cargo-form").valid()) {
            if (self.validarFecha()) {
                if (self.validaDepartamentos()) {
                    if (self.cargo.consumo instanceof ConsumoAgrupadorSimple) {
                        self.cargo.consumo = self.consumoSimple;
                    } else if (self.cargo.consumo instanceof ConsumoAgrupadorProrrateo) {
                        self.cargo.consumo = self.consumoProrrateo;
                    }
                    var cargo = JSON.stringify(self.cargo.getJsonCargoAgrupador(self.departamentos()));
                    console.log(cargo);
                    $.ajax({
                        url : contextPath
                                + "/administrador/cargos-departamentos/agrupador/actualizar",
                        type : 'POST',
                        dataType : 'json',
                        data : cargo,
                        contentType : 'application/json',
                        mimeType : 'application/json',
                        success : function(data) {
                            /*self.cargo.limpiarCargoAgrupador();
                            self.cargo.cargarCargoAgrupador(data, self
                                    .departamentos());*/
                            notificacionExito("Los cargos se han actualizado correctamente");
                            setTimeout(function() {
                                location.href = contextPath + "/administrador/cargos-departamentos/lista";
                            }, 1000);
                        },
                        error : function(jqXHR, textStatus, errorThrown) {
                            notificacionError("Ocurrio un error al guardar los cargos");
                        }
                    });
                } else {
                    notificacionAdvertencia("Se requiere al menos un departamento");
                }
            } else {
                notificacionAdvertencia("El dia de descuento tiene que ser mayor al día de cargo y el día de recargo debe ser mayor al día de descuento");
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.validaDepartamentos = function() {
        var valid = false;
        ko.utils.arrayForEach(self.departamentos(), function(d) {
            if (d.seleccionado()) {
                valid = true;
            }
        });
        return valid;
    }

    self.validarFecha = function() {
        var fechaDeCargo = self.cargo.fecha();
        var fechaDeRecargo = self.cargo.recargo.fecha();
        var fechaDeDescuento = self.cargo.descuento.fecha();

        if (!self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
            return true;
        } else if (self.cargo.aplicaDescuento() && !self.cargo.aplicaRecargo()) {
            if (self.getDate(fechaDeDescuento) >= self.getDate(fechaDeCargo)) {
                return true;
            } else {
                return false;
            }
        } else if (!self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
            if (self.getDate(fechaDeRecargo) >= self.getDate(fechaDeCargo)) {
                return true;
            } else {
                return false;
            }
        } else if (self.cargo.aplicaDescuento() && self.cargo.aplicaRecargo()) {
            if ((self.getDate(fechaDeRecargo) >= self.getDate(fechaDeDescuento))
                    && (self.getDate(fechaDeDescuento) >= self
                            .getDate(fechaDeCargo))) {
                return true;
            } else {
                return false;
            }
        }
    }

    self.getDate = function(string) {
        var año = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (año.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }
    
    if (data && data.cargo.consumo) {
        self.consumo(data.cargo.consumo.tipo.id);
        //self.calcula();
    }
}
