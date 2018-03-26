var PagoViewModel = function (data) {

    var self = this;

    self.idPagoActual = ko.observable();

    self.detalleVisible = ko.observable(false);

    self.rol = data ? data.rol : undefined;

    self.pago = new Pago();

    self.ingresoNI = ko.observable(false);

    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone("div#comprobante", {
        url: contextPath + "/comprobante/subir",
        addRemoveLinks: true,
        maxFilesize: 5,//MB
        maxFiles: 1,
        /*dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',*/
        dictResponseError: '¡Error al subir archivo!',
        dictFileTooBig: 'Los archivos no pueden ser más grandes de 1MB',
        dictMaxFilesExceeded: 'No se puede agregar más de un archivo',
        dictCancelUpload: 'Cancelar',
        dictRemoveFile: 'Remover',
        acceptedFiles: 'image/png, image/x-png, image/gif, image/jpeg, application/pdf, text/plain, application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    });
    myDropzone.on("removedfile", function (file) {
        self.removerArchivo(file);
    });
    myDropzone.on("error", function (file, error) {
        this.removeFile(file);
        notificacionAdvertencia(error);
    });
    myDropzone.on("addedfile", function (file) {
        var bajarButton = Dropzone.createElement("<a class='dz-download'>Descargar</a>");
        $(bajarButton).attr('href', contextPath + "/comprobante/comprobantepornombre?nombre=" + file.name);
        $(bajarButton).attr('target', '_blank');
        file.previewElement.appendChild(bajarButton);
    });

    if (data && data.pago) {
        self.pago.cargar(data.pago);
        self.idPagoActual(data.pago.id)
    }
    if (data && data.archivo && data.archivo.bytes) {
        var file = {
            name: data.archivo.name,
            size: data.archivo.size,
            status: Dropzone.ADDED,
            accepted: true
        };
        myDropzone.emit("addedfile", file);
        myDropzone.emit("thumbnail", file, data.archivo.type ? "data:" + data.archivo.type + ";base64," + data.archivo.bytes : "");
        myDropzone.emit("complete", file);
        myDropzone.files.push(file);
    }
    self.estatus = new EstatusPago();
    if (data && data.estatus) {
        self.estatus.cargar(data.estatus);
    }
    self.departamentos = ko.observableArray([]);

    self.contactos = ko.observableArray([]);
    if (data && data.contactos) {
        ko.utils.arrayForEach(data.contactos, function (c) {
            var contacto = new Contacto();
            contacto.cargar(c);
            self.contactos.push(contacto);
        });
    }

    self.metodosPago = ko.observableArray([]);
    if (data && data.metodosPago) {
        ko.utils.arrayForEach(data.metodosPago, function (mp) {
            var metodo = new Catalogo();
            metodo.cargar(mp);
            self.metodosPago.push(metodo);
        });
    }

    self.cuentas = ko.observableArray([]);
    if (data && data.cuentas) {
        self.cuentas(ordenar(data.cuentas, true));
    }

    self.cuentasIngresos = ko.observableArray([]);
    if (data && data.cuentasIngresos) {
        self.cuentasIngresos(ordenar(data.cuentasIngresos, true));
    }

    self.tiposCargo = ko.observableArray([]);
    if (data && data.tiposCargo) {
        ko.utils.arrayForEach(data.tiposCargo, function (tc) {
            if (tc.id != AppConfig.catalogos.cargo.tipos.consumo) {
                self.tiposCargo.push(tc);
            }

        });
    }

    self.item = new Item();

    self.item.label.subscribe(function (val) {
        if (val.length > 0) {
            self.item.limpiar();
            //console.log( val );

            $("#busqueda").autocomplete({
                minLength: 1,
                source: function (request, response) {
                    $.ajax({
                        url: contextPath + "/administrador/estado-cuenta/buscar",
                        dataType: "json",
                        data: {
                            key: val
                        },
                        success: function (data) {
                            response($.map(data, function (item) {
                                return {
                                    id: item.id,
                                    label: item.label,
                                    url: item.url
                                };
                            }));
                        },
                        global: false
                    });
                },
                select: function (event, ui) {
                    self.item.id(ui.item.id);
                    self.item.label(ui.item.label);
                    self.item.url(ui.item.url);
                    self.pago.contacto.id(self.item.id());
                    /*self.obtenerDepartamentos();
                     self.getSaldo();
                     self.obtenerCargos();*/

                    Dropzone.autoDiscover = false;
                    var myDropzone = new Dropzone("div#comprobante", {
                        url: contextPath + "/comprobante/subir",
                        addRemoveLinks: true,
                        maxFilesize: 5,//MB
                        maxFiles: 1,
                        /*dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',*/
                        dictResponseError: '¡Error al subir archivo!',
                        dictFileTooBig: 'Los archivos no pueden ser más grandes de 1MB',
                        dictMaxFilesExceeded: 'No se puede agregar más de un archivo',
                        dictCancelUpload: 'Cancelar',
                        dictRemoveFile: 'Remover',
                        acceptedFiles: 'image/png, image/x-png, image/gif, image/jpeg, application/pdf, text/plain, application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document'
                    });
                    myDropzone.on("removedfile", function (file) {
                        self.removerArchivo(file);
                    });
                    myDropzone.on("error", function (file, error) {
                        this.removeFile(file);
                        notificacionAdvertencia(error);
                    });
                    myDropzone.on("addedfile", function (file) {
                        var bajarButton = Dropzone.createElement("<a class='dz-download'>Descargar</a>");
                        $(bajarButton).attr('href', contextPath + "/comprobante/comprobantepornombre?nombre=" + file.name);
                        $(bajarButton).attr('target', '_blank');
                        file.previewElement.appendChild(bajarButton);
                    });

                    return false;
                }
            });
        }
    });

    self.obtenerPendientes = function () {
        self.obtenerDepartamentos();
        //self.getSaldo();
        self.obtenerCargos();
    }

    self.pago.contacto.id.subscribe(function () {
        self.buscarContacto();
    });

    self.buscarContacto = function () {
        if (self.pago.contacto.id()) {
            var c = ko.utils.arrayFirst(self.contactos(), function (item) {
                return item.id() == self.pago.contacto.id();
            });
            if (c) {
                self.pago.contacto.nombre(c.nombre());
                self.pago.contacto.emails(c.emails());
            }
        }
    }

    self.obtenerCargos = function () {
        /*self.pago.fecha(moment(new Date()).format('DD-MM-YYYY'));*/
        /*self.pago.fecha(undefined);
         self.pago.metodoPago.id(undefined);
         self.saldoFavor(false);*/
        if (self.pago.contacto.id()) {
            $.ajax({
                async: false,
                cache: false,
                url: contextPath + '/administrador/pagos/cargos',
                type: 'POST',
                dataType: 'json',
                data: {
                    contacto: self.pago.contacto.id()
                    /*,
                     fecha : self.pago.fecha()*/
                },
                success: function (data) {
                    if (data) {
                        self.procesaCargos(data, false);
                        self.calculaAutomaticamente();
                        self.calculaDescuento();
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    self.departamentos([]);
                    notificacionError("Error al obtener cargos pendientes de pago.");
                }
            });
        } else {
            self.departamentos([]);
        }
    }

    self.obtenerDepartamentos = function () {
        if (self.pago.contacto.id()) {
            $.ajax({
                async: false,
                cache: false,
                cache: false,
                url: contextPath + '/administrador/contactos/departamentos',
                type: 'GET',
                dataType: 'json',
                data: {
                    contacto: self.pago.contacto.id()
                },
                success: function (data) {
                    if (data) {
                        self.departamentos([]);
                        ko.utils.arrayForEach(data, function (contactoDepartamento) {
                            //var departamento = new Departamento();
                            var pd = new PagoDepartamento();


                            pd.departamento.cargar(contactoDepartamento.id.departamento);
                            self.departamentos.push(pd);
                        });
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    self.departamentos([]);
                    notificacionError("Este contacto no tiene departamentos.");
                }
            });
        } else {
            self.departamentos([]);
        }
    }

    self.procesaCargos = function (data, ceros, pago) {
        /*self.departamentos([]);
         ko.utils.arrayForEach(data, function(cargo) {
         var agregarDepartamento = true;
         ko.utils.arrayForEach(self.departamentos(), function(departamento) {
         if (departamento.id() == cargo.departamento.id) {
         agregarDepartamento = false;
         return false;
         }
         });
         if (agregarDepartamento) {
         var departamento = new Departamento();
         departamento.cargar(cargo.departamento);
         self.departamentos.push(departamento);
         }
         });*/
        ko.utils.arrayForEach(self.departamentos(), function (departamento) {
            departamento.cargos([]);
            ko.utils.arrayForEach(data, function (cargo) {
                if (departamento.departamento.id() == cargo.departamento.id /*&& cargo.fecha <= self.pago.fecha()*/) {
                        var cd = new CargoDepartamento();
                    cd.cargarCargoDepartamento(cargo);

                    if(self.pago.id()) {
                         var pagTemp = 0;
                         ko.utils.arrayForEach(cargo.movimientos, function(mov) {
                             ko.utils.arrayForEach(mov.aplicados, function(apl) {
                                 if(apl.pago && (apl.pago.id == self.pago.id())) {
                                     if(apl.haber){
                                         pagTemp = pagTemp + apl.haber;
                                     }
                                     if(apl.debe){
                                         pagTemp = pagTemp - apl.debe;
                                     }

                                 }
                             });
                         });

                         cd.pagoTemporal(pagTemp);
                    }

                    if (ceros) {
                        departamento.cargos.push(cd);
                    } else if (cd.saldoPendiente() > 0) {
                        departamento.cargos.push(cd);
                    }
                }
            });
        });
        /*ko.utils.arrayForEach(self.departamentos(), function(departamento) {
         if (departamento == undefined || departamento.cargos().length == 0) {
         self.departamentos.remove(departamento);
         }
         });*/
        /*self.departamentos.remove( function (item) {
         return item.cargos().length == 0;
         }
         );*/
        $('.table-cargos').footable();
    }

    self.existenDescuentos = ko.observable();

    self.calculaDescuento = function () {
        if (self.pago.fecha()) {
            self.existenDescuentos(false);
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                    ko.utils.arrayForEach(cargo.descuentos(), function (mov) {
                        if (mov.tipo.descripcion() == 'Descuento por fecha') {
                            cargo.descuentos.remove(mov);
                        }
                    });
                    ko.utils.arrayForEach(cargo.movimientos(), function (mov) {
                        if (mov.tipo.descripcion() == 'Descuento por fecha') {
                            cargo.movimientos.remove(mov);
                        }
                    });
                    if (cargo.aplicaDescuento() && self.aplicaDescuento(cargo.descuento)) {
                        self.existenDescuentos(true);
                        var monto = 0;
                        if (cargo.descuento.porcentaje()) {
                            monto = cargo.totalMonto() * (cargo.descuento.monto() * .01)
                        } else {
                            monto = cargo.descuento.monto();
                        }
                        var mov = new MovimientoCargo();
                        mov.cargarMC({
                            haber: monto,
                            tipo: {
                                id: AppConfig.catalogos.movimientos.descuento,
                                descripcion: 'Descuento por fecha'
                            }
                        });
                        cargo.descuentos.push(mov);
                        cargo.movimientos.push(mov);
                    }
                });
            });
            /*if (existen) {
             notificacionAdvertencia("Hay descuentos disponibles para los cargos, " +
             "para aplicarlos es necesario cubrir el monto completo.");
             }*/
            self.calculaAutomaticamente();
        }
    }

    self.aplicaDescuento = function (descuento) {
        if (self.pago && self.pago.fecha() && descuento && descuento.fecha()) {
            var fechaPago = Date.convertir(self.pago.fecha());
            var fechaDescuento = Date.convertir(descuento.fecha());
            return fechaPago <= fechaDescuento;
        } else {
            return false;
        }
    }

    self.getSaldo = function () {
        $.ajax({
            async: false,
            cache: false,
            url: contextPath + '/administrador/pagos/saldo-favor',
            type: 'POST',
            dataType: 'json',
            data: {
                contacto: self.pago.contacto.id()
            },
            success: function (data) {

                ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                    if(data[departamento.departamento.id()]) {
                        departamento.monto(data[departamento.departamento.id()]);
                    } else {
                        departamento.monto(0);
                    }
                    self.calculaAutomaticamente(departamento.departamento.id());
                });


                /*self.saldoFavor(data);
                self.calculaAutomaticamente();*/
                /*if (data > 0) {
                 self.saldoFavor(data);
                 self.calculaAutomaticamente();
                 } else {
                 if (!self.pago.id()) {
                 self.pago.metodoPago.id(undefined);
                 notificacionError("No existen saldos a favor.");
                 }
                 }*/
            },
            global: false
        });
    }


    self.saldoFavor = ko.observable(0);
    self.pago.metodoPago.id.subscribe(function () {
        //CAMBIO PARA SF
        self.pago.monto(0);
        /*self.saldoFavor(0);
        self.nuevoSaldoFavor(0);*/
        ko.utils.arrayForEach(self.departamentos(), function (departamento) {
            departamento.monto(0);
            ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                cargo.pagoTemporal(0);
            });

            /*self.calculaAutomaticamenteMontoAplicado(departamento.departamento.id());*/

        });
        if (self.pago.metodoPago.id() == AppConfig.catalogos.metodos.saldofavor) {
            if (self.pago.contacto.id()) {
                self.getSaldo();
            } else {
                self.pago.metodoPago.id(undefined);
                notificacionError("Primero selecciona un condómino/departamento.");
            }
        }
    });

    /*self.pago.monto.subscribe(function () {
        if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
            self.calculaAutomaticamente();
        }
    });*/

        //Cambio de Pagos Departamentos
     self.calculaAutomaticamenteMontoAplicado = function (idDepartamento) {
         if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
             self.calculaAutomaticamente(idDepartamento);
         }
     }

    self.nuevoSaldoFavor = ko.observable(0);

    self.calculaAutomaticamente = function (idDepartamento) {
        var montoDisponible = 0;
        self.pago.monto(0);
        ko.utils.arrayForEach(self.departamentos(), function (dpt) {
                montoDisponible = dpt.monto();
                if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
                    self.pago.monto(self.pago.monto() + dpt.monto());
               } else {
                    self.pago.monto(self.pago.monto() + dpt.pagadoTemporal());
               }
               if (montoDisponible > 0) {
                   if(dpt.departamento.id() == idDepartamento) {
                       ko.utils.arrayForEach(dpt.cargos(), function (cargo) {
                           if (montoDisponible > cargo.saldoPendiente()) {
                               cargo.pagoTemporal(cargo.saldoPendiente());
                           } else {
                               cargo.pagoTemporal(montoDisponible);
                           }
                           montoDisponible = (montoDisponible - cargo.pagoTemporal()).toFixed(2);
                       });
                   }
                   self.calculaDisponible(montoDisponible);
               } else {
                   if(dpt.departamento.id() == idDepartamento) {
                       ko.utils.arrayForEach(dpt.cargos(), function (cargo) {
                           cargo.pagoTemporal(0);
                       });
                   }
               }
        });
    }

    /*self.calculaAutomaticamente = function () {
        var montoDisponible = 0;
        if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
            montoDisponible = self.pago.monto();
        } else {
            montoDisponible = self.saldoFavor();
        }
        if (montoDisponible > 0) {
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                    if (montoDisponible > cargo.saldoPendiente()) {
                        cargo.pagoTemporal(cargo.saldoPendiente());
                    } else {
                        cargo.pagoTemporal(montoDisponible);
                    }
                    montoDisponible = (montoDisponible - cargo.pagoTemporal()).toFixed(2);
                });
            });
            self.calculaDisponible(montoDisponible);
        } else {
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                    cargo.pagoTemporal(0);
                });
            });
        }
    }*/

    self.calculaMontosManual = function (cargoSeleccionado,idDepartamento) {
        var montoDisponible = 0;

        ko.utils.arrayForEach(self.departamentos(), function (dpt) {
            if(dpt.departamento.id() == idDepartamento){
                if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
                    montoDisponible = dpt.monto();
                } else {
                    montoDisponible = dpt.monto();
                    //montoDisponible = self.saldoFavor();
                }
            }
        });
            if (montoDisponible > 0) {
                if (cargoSeleccionado.pagoTemporal() < 0) {
                    cargoSeleccionado.pagoTemporal(0);
                    return false;
                }
                ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                    if(departamento.id() == idDepartamento){
                        ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                            if (cargoSeleccionado.id() != cargo.id()) {
                                montoDisponible = parseFloat(montoDisponible - cargo.pagoTemporal()).toFixed(2);
                            }
                        });
                    }
                });
                if (cargoSeleccionado.pagoTemporal()) {
                    if (montoDisponible < cargoSeleccionado.pagoTemporal()) {
                        cargoSeleccionado.pagoTemporal(montoDisponible);
                        notificacionAdvertencia("El monto del pago no es suficiente para cubrir esa cantidad del cargo.");
                    }
                    if (cargoSeleccionado.pagoTemporal() > cargoSeleccionado.saldoPendiente()) {
                        cargoSeleccionado.pagoTemporal(cargoSeleccionado.saldoPendiente());
                    }
                    montoDisponible = parseFloat(montoDisponible - cargoSeleccionado.pagoTemporal()).toFixed(2);
                }
                self.calculaDisponible(montoDisponible);
            } else {
                notificacionError("No hay una cantidad disponible para realizar el pago del cargo.");
                cargoSeleccionado.pagoTemporal(0);
            }
    }

    /*self.calculaMontosManual = function (cargoSeleccionado) {
        var montoDisponible = 0;
        if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
            montoDisponible = self.pago.monto();
        } else {
            montoDisponible = self.saldoFavor();
        }
        if (montoDisponible > 0) {
            if (cargoSeleccionado.pagoTemporal() < 0) {
                cargoSeleccionado.pagoTemporal(0);
                return false;
            }
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                    if (cargoSeleccionado.id() != cargo.id()) {
                        montoDisponible = parseFloat(montoDisponible - cargo.pagoTemporal()).toFixed(2);
                    }
                });
            });
            if (cargoSeleccionado.pagoTemporal()) {
                if (montoDisponible < cargoSeleccionado.pagoTemporal()) {
                    cargoSeleccionado.pagoTemporal(montoDisponible);
                    notificacionAdvertencia("El monto del pago no es suficiente para cubrir esa cantidad del cargo.");
                }
                if (cargoSeleccionado.pagoTemporal() > cargoSeleccionado.saldoPendiente()) {
                    cargoSeleccionado.pagoTemporal(cargoSeleccionado.saldoPendiente());
                }
                montoDisponible = parseFloat(montoDisponible - cargoSeleccionado.pagoTemporal()).toFixed(2);
            }
            self.calculaDisponible(montoDisponible);
        } else {
            notificacionError("No hay una cantidad disponible para realizar el pago del cargo.");
            cargoSeleccionado.pagoTemporal(0);
        }
    }*/

    self.calculaDisponible = function (montoDisponible) {
        if (self.pago.metodoPago.id() != AppConfig.catalogos.metodos.saldofavor) {
            self.nuevoSaldoFavor(montoDisponible);
        } else {
            if (!self.pago.id()) {
                self.nuevoSaldoFavor(montoDisponible);
                //CAMBIO SF
                //self.pago.monto(self.saldoFavor() - montoDisponible);
            } else {
                self.nuevoSaldoFavor(montoDisponible);
            }
        }
    }

    if (data && data.ingresoNoIdentificado) {
        self.ingresoNI(true);
        self.item.id(self.pago.contacto.id());
        self.item.label(self.pago.contacto.nombre());
        /*+ " " + self.pago.contacto.apellidoPaterno() + " " + (self.pago.contacto.apellidoMaterno()?self.pago.contacto.apellidoMaterno():'') );*/
        //CAMBIO PARA OTROS INGRESOS NI
        //self.pago.monto(data.ingresoNoIdentificado.monto);
        self.pago.fecha(data.ingresoNoIdentificado.fecha);
        self.pago.metodoPago.cargar(data.ingresoNoIdentificado.metodoPago);
        self.pago.referencia(data.ingresoNoIdentificado.referencia);
        self.pago.cuenta.cargar(data.ingresoNoIdentificado.cuentaBanco);

        self.obtenerDepartamentos();
        //self.getSaldo();
        self.obtenerCargos();

        if(self.departamentos().length > 0) {
            self.departamentos()[0].monto(data.ingresoNoIdentificado.monto);
            self.calculaAutomaticamenteMontoAplicado(self.departamentos()[0].departamento.id());
        }

        $("#cuenta").select2();

    }

    self.guardar = function () {
        if ($("#pago-form").valid() && self.pago.metodoPago.id() && self.pago.fecha()) {
            /*if (self.rol == 'contacto') {
                self.departamentos([]);
            }*/
            var pago = self.pago.getJson(self.estatus, self.departamentos);
            var pagoTarjeta = self.pagotarjeta.getJson(pago);

            var pagoDto = {
            		pago : pago,
            		pagoTarjeta : pagoTarjeta
            }
            pagoDto = JSON.stringify(pagoDto);
            console.log(pagoDto);
            $.ajax({
                url: contextPath + "/administrador/pagos/guardar",
                type: 'POST',
                dataType: 'json',
                data: pagoDto,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    /*self.pago.limpiar();
                     self.pago.cargar(data);
                     self.buscarContacto();*/
                    self.idPagoActual(data.id);
                    notificacionExito("El abono se ha guardado correctamente");
                    $("#guardado-modal").modal("show");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el abono");
                    console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores o no se completó algún campo requerido.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.guardarAsignacionIngresoNoIdentificado = function () {
        if ($("#pago-form").valid() && self.pago.metodoPago.id() && self.pago.fecha()) {
            var pago = self.pago.getJson(self.estatus, self.departamentos);

            pago = JSON.stringify(pago);
            console.log(pago);
            $.ajax({
                url: contextPath + "/administrador/pagos/guardarConIngresoNoIdentificado",
                type: 'POST',
                dataType: 'json',
                data: pago,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    /*self.pago.limpiar();
                     self.pago.cargar(data);
                     self.buscarContacto();*/
                    self.idPagoActual(data.id);
                    notificacionExito("La asignación se ha realizado de forma correcta, ahora el Ingreso No Identificado se ha cancelado y se ha registrado el nuevo Abono");
                    $("#guardado-modal").modal("show");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el abono");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores o no se completó algún campo requerido.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.reedireccionar = function () {
        $("#guardado-modal").modal("hide");
        location.href = contextPath + "/administrador/pagos/lista";
    }

    self.reedireccionarContacto = function () {
        $("#guardado-modal").modal("hide");
        location.href = contextPath + "/contacto/mis-pagos/lista";
    }

    self.tienePagos = function (pago) {
        var tiene = false;
        if (pago.movimientos && pago.movimientos.length > 0) {
            tiene = true;
        }
        return tiene;
    }

    self.descargar = function (data) {
        window.open(contextPath + "/administrador/pagos/comprobante/" + self.pago.id(), '_blank');
    }

    self.imprimir = function (data) {
        window.open(contextPath + "/administrador/pagos/recibo/" + self.idPagoActual(), '_blank');
    }

    self.emailsEnvio = ko.observableArray([]);
    self.otrosEnvio = ko.observable("");
    self.mensajeEnvio = ko.observable("");

    self.abrirModalEnvio = function () {
        self.limpiarEnvio();
        if (self.pago.contacto.emails()) {
            ko.utils.arrayForEach(self.pago.contacto.emails(), function (email) {
                var emailEnvio = {
                    contacto: self.pago.contacto.nombre(),
                    direccion: email.direccion,
                    seleccionado: ko.observable(false)
                };
                self.emailsEnvio.push(emailEnvio);
            });
        }
        $("#envio-modal").modal("show");
    }

    self.limpiarEnvio = function () {
        self.emailsEnvio([]);
        self.otrosEnvio("");
        self.mensajeEnvio("");
    }

    self.enviar = function () {
        var emails = [];
        ko.utils.arrayForEach(self.emailsEnvio(), function (email) {
            if (email.seleccionado()) {
                emails.push(email.direccion);
            }
        });
        if (self.otrosEnvio()) {
            var otros = self.otrosEnvio().split(";");
            if (otros) {
                ko.utils.arrayForEach(otros, function (email) {
                    emails.push(email);
                });
            }
        }

        if (emails.length > 0) {
            console.log(emails);
            $.ajax({
                cache: false,
                url: contextPath + '/administrador/pagos/enviar',
                type: 'POST',
                dataType: 'json',
                data: {
                    emails: emails,
                    mensaje: self.mensajeEnvio(),
                    idPago: self.idPagoActual()
                },
                success: function (data) {
                    notificacionExito("Se enviaron los recibos de pago correctamente");
                }
            });
        } else {
            notificacionAdvertencia("No hay ningún email.");
        }
        $("#envio-modal").modal("hide");
    }

    self.aprobar = function () {
        $("#cuenta").attr("required", "required");
        $("#comentario").removeAttr("required");
        self.estatus.estatus.id(AppConfig.catalogos.estatuspago.aprobado);
        var pago = self.pago.getJson(self.estatus, self.departamentos);
        if ($("#pago-form").valid()) {
            pago = JSON.stringify(pago);
            console.log(pago);
            $.ajax({
                url: contextPath + "/administrador/pagos/aprobar",
                type: 'POST',
                dataType: 'json',
                data: pago,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    notificacionExito("Se aprobo el pago correctamente");
                    self.reedireccionar();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al aprobar el pago: " + errorThrown);
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores o no se registro algún campo requerido.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.rechazar = function () {
        $("#cuenta").removeAttr("required");
        $("#comentario").attr("required", "required");
        self.pago.cuenta.id(undefined);
        self.estatus.estatus.id(AppConfig.catalogos.estatuspago.rechazado);
        if ($("#pago-form").valid()) {
            if (self.estatus.comentario()) {
                //self.departamentos([]);
                var pago = self.pago.getJson(self.estatus, self.departamentos);
                pago = JSON.stringify(pago);
                console.log(pago);
                $.ajax({
                    url: contextPath + "/administrador/pagos/rechazar",
                    type: 'POST',
                    dataType: 'json',
                    data: pago,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        notificacionExito("Se rechazo el pago correctamente");
                        self.reedireccionar();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al rechazar el pago");
                    }
                });
            } else {
                notificacionAdvertencia("Se requiere un motivo en el campo comentario.");
                $('#tabs li a[href="#hb1"]').tab('show');
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores o no se registro algún campo requerido.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.reenviar = function () {
        self.estatus.estatus.id(AppConfig.catalogos.estatuspago.pendiente);
        if ($("#pago-form").valid()) {
            if (self.estatus.comentario()) {
                //self.departamentos([]);
                var pago = self.pago.getJson(self.estatus, self.departamentos);
                pago = JSON.stringify(pago);
                console.log(pago);
                $.ajax({
                    url: contextPath + "/contacto/mis-pagos/reenviar",
                    type: 'POST',
                    dataType: 'json',
                    data: pago,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        notificacionExito("Se rechazo el pago correctamente");
                        self.reedireccionarContacto();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al rechazar el pago");
                    }
                });
            } else {
                notificacionAdvertencia("Se requiere un motivo en el campo comentario.");
                $('#tabs li a[href="#hb1"]').tab('show');
            }
        } else {
            notificacionAdvertencia("El formulario tiene errores o no se registro algún campo requerido.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.cancelar = function () {
        $("#comentario").removeAttr("disabled");
        $("#comentario").attr("required", "required");
        self.estatus.estatus.id(AppConfig.catalogos.estatuspago.cancelado);
        var pago = self.pago.getJson(self.estatus, self.departamentos);
        if ($("#pago-form").valid()) {
            if (self.estatus.comentario()) {
                pago = JSON.stringify(pago);
                console.log(pago);
                $.ajax({
                    url: contextPath + "/administrador/pagos/cancelar",
                    type: 'POST',
                    dataType: 'json',
                    data: pago,
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        notificacionExito("Se cancelo el pago correctamente");
                        self.reedireccionar();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        notificacionError("Ocurrio un error al cancelar el pago");
                    }
                });
            } else {
                notificacionAdvertencia("Se requiere un motivo en el campo comentario.");
                $('#tabs li a[href="#hb1"]').tab('show');
            }
        } else {
            notificacionAdvertencia("Se requiere un motivo.");
            $('#tabs li a[href="#hb1"]').tab('show');
        }
    }

    self.total = ko.computed(function () {
        var monto = 0;
        ko.utils.arrayForEach(self.departamentos(), function (departamento) {
            monto = monto + departamento.total();
        });
        return monto;
    });

    self.editable = function () {
        var e = true;
        if (self.pago.id() && self.pago.estatus()) {
            var estatus = self.ultimoEstatus();
            if (estatus == 'Aprobado' || estatus == 'Cancelado') {
                e = false;
            }
        }
        return e;
    }

    self.ultimoEstatus = function () {
        var estatus = self.pago.estatus()[self.pago.estatus().length - 1].estatus.descripcion();
        return estatus;
    }

    if (data && data.cargos) {
        self.obtenerDepartamentos();
        if(self.ultimoEstatus() == 'Pendiente' || self.ultimoEstatus() == 'Rechazado') {
            self.obtenerCargos();
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                var obj = ko.utils.arrayFirst(data.pago.pagosDepartamento, function(pd) {
                    return departamento.departamento.id() == pd.departamento.id;
                });
                if(obj) {
                    departamento.monto(obj.monto);
                    if (self.ultimoEstatus() != 'Aprobado' && departamento.monto() > 0) {
                        self.calculaAutomaticamenteMontoAplicado(departamento.departamento.id());
                    }
                }
            });
        } else {
            self.pago.monto(0);
            self.procesaCargos(data.cargos, true);
            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                var obj = ko.utils.arrayFirst(data.pago.pagosDepartamento, function(pd) {
                    return departamento.departamento.id() == pd.departamento.id;
                });
                if(obj) {
                    departamento.monto(obj.monto);
                    self.pago.monto(self.pago.monto() + departamento.monto());
                    /*ko.utils.arrayForEach(departamento.car, function (cargo) {
                        departamento.cargos().push(cargo);
                    });*/
                }
            });
        }
        /*self.getSaldo();
        self.nuevoSaldoFavor(self.pago.monto());
        self.obtenerDepartamentos();
        if (self.ultimoEstatus() == 'Pendiente' || self.ultimoEstatus() == 'Rechazado') {
            self.obtenerCargos();
        } else {
            self.procesaCargos(data.cargos, true);

            ko.utils.arrayForEach(self.departamentos(), function (departamento) {
                ko.utils.arrayForEach(departamento.cargos(), function (cargo) {
                    var pagado = 0;
                    var pagTemp = 0;
                    ko.utils.arrayForEach(cargo.movimientos(), function (movimiento) {
                        ko.utils.arrayForEach(self.pago.movimientos(), function (aplicado) {
                            if (movimiento.id() == aplicado.id()) {
                                if (aplicado.haber()) {
                                    pagTemp = pagTemp + aplicado.haber();
                                    self.nuevoSaldoFavor(self.nuevoSaldoFavor() - aplicado.haber());
                                    if (self.ultimoEstatus() != 'Aprobado') {
                                        self.calculaMontosManual(cargo);
                                    }
                                } else if (aplicado.debe()) {
                                    pagTemp = pagTemp - aplicado.debe();
                                    self.nuevoSaldoFavor(self.nuevoSaldoFavor() + aplicado.debe());
                                }
                            }
                        });
                        cargo.pagoTemporal(pagTemp);
                    });
                });
            });
        }*/
        /*if (self.ultimoEstatus() != 'Pendiente') {
            self.saldoFavor
        }*/
        //self.calculaDisponible();
    }

    if (self.contactos().length == 1 && !self.pago.id()) {
        self.pago.contacto.id(self.contactos()[0].id());
        /*self.obtenerDepartamentos();*/
        self.obtenerCargos();
    }

    self.removerArchivo = function (file) {
        console.log('Remover archivo: ' + file.name);
        $.ajax({
            url: contextPath + "/comprobante/eliminararchivo",
            type: 'POST',
            dataType: 'json',
            data: {file: file.name},
            success: function (data) {

            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al eliminar el archivo");
            }
        });
    }

    self.cambiarTab = function (data) {
        if (data == 1) {
            $('#tabs li a[href="#hb1"]').tab('show');
        } else if (data == 2) {
            if ($("#pago-form").valid()) {
                $('#tabs li a[href="#hb2"]').tab('show');
            } else {
                notificacionAdvertencia("Existen errores en el formualario, favor de verificar.");
                return false;
            }
        }
    }

    self.cambiarTabSinCheck = function (data) {
        if (data == 1) {
            $('#tabs li a[href="#hb1"]').tab('show');
        } else if (data == 2) {
            $('#tabs li a[href="#hb2"]').tab('show');
        }
    }

    self.detalleMovimientos = ko.observableArray([]);
    self.verDetalleMovimientos = function (data) {
        self.detalleMovimientos(data.movimientos());
    }

    self.pagarCompleto = function (data, idDepartamento) {
        data.pagoTemporal(data.saldoPendiente());
        self.calculaMontosManual(data, idDepartamento);
    }

    self.noPagar = function (data, idDepartamento) {
        data.pagoTemporal(0.00);
        self.calculaMontosManual(data, idDepartamento);
    }

    //SECCION DE GESTIÓN DE CARGOS EN ESTA PANTALLA
    self.cargoActual = new Departamento();
    //self.cargoAgrupadorViewModel = new CargoAgrupadorViewModel();
    self.nuevoCargo = function (data) {
        self.cargoActual.limpiar();
        self.cargoActual.id(data.id());
        self.cargoActual.seleccionado(true);
        self.cargoActual.cargo.condominio.id(self.pago.condominio.id());
    }
    /*self.guardarNuevoCargo = function() {
     if ($("#cargo-form").valid()) {
     var monto = self.cargoActual.cargo.monto();
     var departamentos = [self.cargoActual];

     self.cargoAgrupadorViewModel.departamentos(departamentos);
     self.cargoAgrupadorViewModel.cargo.cargarCargoAgrupador({
     fecha: (self.cargoActual.cargo.fecha()),
     monto: (self.cargoActual.cargo.monto()),
     tipo: {
     id: (self.cargoActual.cargo.tipo.id())
     },
     cuenta: {
     id: (self.cargoActual.cargo.cuenta.id())
     },
     concepto: (self.cargoActual.cargo.concepto()),
     activo: true,
     mantenimientoDepartamento: true,
     condominio: {
     id: self.pago.condominio.id()
     }
     }, self.cargoAgrupadorViewModel.departamentos());
     self.cargoAgrupadorViewModel.departamentos()[0].seleccionado(true);
     self.cargoAgrupadorViewModel.departamentos()[0].cargo.monto(monto);
     self.cargoAgrupadorViewModel.guardar();
     setTimeout(self.obtenerCargos(), 3000);
     }
     }*/
    self.guardarNuevoCargo = function () {
        if ($("#cargo-form").valid()) {
            var cargo = (self.cargoActual.cargo.getJsonCargoDepartamento(self.cargoActual.cargo.fecha()));
            cargo.departamento = {
                id: self.cargoActual.id()
            }
            cargo.activo = true;
            cargo.mantenimientoDepartamento = true;
            cargo = JSON.stringify(cargo);
            console.log(cargo);
            $.ajax({
                url: contextPath
                + "/administrador/cargos-departamentos/guardar",
                type: 'POST',
                dataType: 'json',
                data: cargo,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.obtenerCargos();
                    notificacionExito("Cargo generado correctamente");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el cargo");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.pagotarjeta = new PagoTarjeta();
}

////////////////////////M O D E L O   P A R A   L A   L I S T A/////////////////

var ListaPagoViewModel = function (data) {
    self = this;

    self.pagos = ko.observableArray([]);

    self.fechaInicial = ko.observable();
    self.fechaFinal = ko.observable();

    self.valida = function () {
        if (self.fechaInicial() && self.fechaFinal()) {
            if (self.getDate(self.fechaInicial()) > self.getDate(self.fechaFinal())) {
                notificacionAdvertencia("La fecha inicial debe ser anterior a la final.");
                return false;
            }
            return true;
        } else {
            notificacionAdvertencia("Especifica la fechas.");
            return false;
        }
    }

    self.getDate = function (string) {
        var año = string.substring(6, 10);
        var mes = string.substring(3, 5);
        var dia = string.substring(0, 2);
        var fecha = (año.concat("/") + mes.concat("/") + dia)
        ms = Date.parse(fecha);
        fecha2 = new Date(ms);
        return new Date(fecha2)
    }

    self.consulta = function () {
        console.log("Consulta - " + self.fechaInicial() + " - " + self.fechaFinal());

        if (self.valida()) {
            $.ajax({
                cache: false,
                url: contextPath + "/administrador/pagos/listaConFechas",
                data: {
                    inicio: self.fechaInicial(),
                    fin: self.fechaFinal()
                },
                success: function (data) {
                    self.pagos([]);
                    if ($.fn.DataTable.isDataTable("#table-pagos")) {
                        $("#table-pagos").DataTable().clear().destroy();
                    }
                    self.pagos(data);

                    /*if (data != undefined && data.length > 0) {
                        //$("#table-pagos").DataTable().destroy();
                        ko.utils.arrayForEach(data, function (p) {
                            var depts = [];
                            ko.utils.arrayForEach(p.movimientos, function (m) {
                                if (depts.indexOf(m.movimientoCargo.cargo.departamento.nombre) <= -1) {
                                    depts.push(m.movimientoCargo.cargo.departamento.nombre);
                                }
                            });
                            p.departamentos = depts;
                            self.pagos.push(p);
                        });
                    }*/

                    //Se agrega el código para que dinamicamente se agregue el filtro de fechas
                    dibujarTabla("#table-pagos", {
                        "aaSorting": [[0, 'desc']],
                        "aoColumns": [
                            {"iDataSort": 0}, null, null, null, null, null, null, null, null, null
                        ]
                    });
                    $("div.toolbar").html("<div class='col col-md-6'><div class='input-group'><span class='input-group-addon'>De</span><input class='form-control' type='text' name='inicio' id='inicio' required='required' readonly='readonly' style='background: white;' data-bind='value: $root.fechaInicial'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span> </div> </div> <div class='col col-md-6'> <div class='input-group'> <span class='input-group-addon'>A </span> <input class='form-control' type='text' name='fin' id='fin' required='required' readonly='readonly' style='background: white;' data-bind='value: $root.fechaFinal'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span> </div> </div>");
                    $("#inicio, #fin").datepicker();
                    var inicio = document.getElementById('inicio');
                    ko.applyBindingsToNode(inicio, {value: self.fechaInicial});
                    var fin = document.getElementById('fin');
                    ko.applyBindingsToNode(fin, {value: self.fechaFinal});


                }
            });
        }
    }

    if (!self.fechaInicial() && !self.fechaFinal()) {
        if (data && data.fin && data.inicio) {
            self.fechaInicial(data.inicio);
            self.fechaFinal(data.fin);
        } else {
            var fecha = new Date();
            self.fechaInicial("01-" + (((fecha.getMonth() + 1).toString().length < 2) ? ('0' + (fecha.getMonth() + 1)) : (fecha.getMonth() + 1)) + "-" + fecha.getFullYear());
            self.fechaFinal(fecha.getDate() + "-" + (((fecha.getMonth() + 1).toString().length < 2) ? ('0' + (fecha.getMonth() + 1)) : (fecha.getMonth() + 1)) + "-" + fecha.getFullYear());
        }
        self.consulta();
    }

    self.fechaInicial.subscribe(function () {
        self.consulta();
    });

    self.fechaFinal.subscribe(function () {
        self.consulta();
    });

    /*if (data.pagos != undefined && data.pagos.length > 0) {
     ko.utils.arrayForEach(data.pagos, function(p) {
     var depts = [];
     ko.utils.arrayForEach(p.movimientos, function(m) {
     if(depts.indexOf(m.movimientoCargo.cargo.departamento.nombre) <= -1) {
     depts.push(m.movimientoCargo.cargo.departamento.nombre);
     }
     });
     p.departamentos = depts;
     self.pagos.push(p);
     });
     }*/

    self.ver = function (data) {
        location.href = contextPath + "/administrador/pagos/actualizar/" + data.id;
    }

    self.verContacto = function (data) {
        location.href = contextPath + "/contacto/mis-pagos/actualizar/" + data.id;
    }

    self.descargar = function (data) {
        window.open(contextPath + "/administrador/pagos/comprobante/" + data.id, '_blank');
    }

    self.imprimir = function (data) {
        window.open(contextPath + "/administrador/pagos/recibo/" + data.id, '_blank');
    }

    self.emailsEnvio = ko.observableArray([]);
    self.otrosEnvio = ko.observable("");
    self.mensajeEnvio = ko.observable("");
    self.idPago = ko.observable("");

    self.abrirModalEnvio = function (data) {
        self.limpiarEnvio();
        self.idPago(data.id);
        if (data.contacto.emails) {
            ko.utils.arrayForEach(data.contacto.emails, function (email) {
                var emailEnvio = {
                    contacto: data.contacto.nombre + ' ' + data.contacto.apellidoPaterno,
                    direccion: email.direccion,
                    seleccionado: ko.observable(false)
                };
                self.emailsEnvio.push(emailEnvio);
            });
        }
        $("#envio-modal").modal("show");
    }

    self.limpiarEnvio = function () {
        self.emailsEnvio([]);
        self.otrosEnvio("");
        self.mensajeEnvio("");
        self.idPago("");
    }

    self.enviar = function () {
        var emails = [];
        ko.utils.arrayForEach(self.emailsEnvio(), function (email) {
            if (email.seleccionado()) {
                emails.push(email.direccion);
            }
        });
        if (self.otrosEnvio()) {
            var otros = self.otrosEnvio().split(";");
            if (otros) {
                ko.utils.arrayForEach(otros, function (email) {
                    emails.push(email);
                });
            }
        }

        if (emails.length > 0) {
            console.log(emails);
            $.ajax({
                cache: false,
                url: contextPath + '/administrador/pagos/enviar',
                type: 'POST',
                dataType: 'json',
                data: {
                    emails: emails,
                    mensaje: self.mensajeEnvio(),
                    idPago: self.idPago()
                },
                success: function (data) {
                    notificacionExito("Se enviaron los recibos de pago correctamente");
                }
            });
        } else {
            notificacionAdvertencia("No hay ningún email.");
        }
        $("#envio-modal").modal("hide");
    }
}

var Item = function (data) {

    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.label = ko.observable(data ? data.label : undefined);
    self.url = ko.observable(data ? data.url : undefined);

    self.limpiar = function () {
//        self.id( undefined );
        //self.label( undefined );
        self.url(undefined);
    }
}