var PagoProveedoresViewModel = function(data) {

    var self = this;

    self.pago = new PagoProveedor();
	self.facturasxp = ko.observableArray([]);

	self.totalRestante = ko.observable();

	self.porcentajeRestante = ko.observable(.001);
	self.porcentajeRestante = ko.computed(function () {

		if (numeral().unformat(self.pago.total()) > 0 && self.totalRestante() > 0) {
			return (self.totalRestante() / numeral().unformat(self.pago.total()));
		} else {
			return .001;
		}
	}, this);


	if (data && data.pago) {
		self.pago.cargar(data.pago);
	}

    if (data && data.condominio) {
        //console.log("Condominio:: " + data.condominio.id);
		self.pago.condominio.cargar(data.condominio);
	}

	self.proveedores = ko.observableArray([]);
	if (data && data.proveedores) {
		self.proveedores(data.proveedores);
	}

	self.cuenta = ko.observableArray([]);
	if (data && data.cuenta) {
		self.cuenta(ordenar(data.cuenta,true));
	}

	self.removerTabla = function() {
		var id = "#table-facturasxp";
		if ( $.fn.dataTable.isDataTable( id ) ) {
			var otable = $(id).DataTable();
			otable.destroy();
			$(id + " tbody").empty();
		}
	}


	self.seleccionarTodo = ko.observable(false);

	self.seleccionarTodo.subscribe(function( val ) {
		self.limpiarMontos();

		if (val) {
			var rows = $("#table-facturasxp").dataTable()._('tr', {"filter": "applied"});

			var bandera = true;

			ko.utils.arrayForEach(self.facturasxp(), function (factura) {
				for (var i = 0; i < rows.length; i++) {

					if (factura.id() == Number(rows[i][0])) {
						factura.aplicaPago(val);
						self.verificarPaloma(factura, true);
					}

				}
			});
		}
	});


	self.dibujarTabla = function() {
		var id = "#table-facturasxp";
		var otable = $(id).DataTable({
			"aaSorting": [],
			"aoColumns": [
				null, null, null, null, null, null, null, null, null, { "bSearchable": false, "bSortable": false }
			],
			"autoWidth" : true,
			"destroy": true,
			"info": false,
			"paging": false,
			"responsive": true,
			"searching": true,
			"scrollY": "250px",
			"scrollCollapse": true
		});

		yadcf.init(otable, [{
			column_number: 0,
			filter_type: "text",
			filter_default_label : "fechaRecepcion"
		}, {
			column_number: 1,
			filter_type: "text",
			filter_default_label : "fechaVencimiento"
		}, {
			column_number: 2,
			filter_type: "text",
			filter_default_label : "uuid"
		}
		]);

		$("#table-facturasxp_filter").hide();
	}

	self.metodosPago = ko.observableArray([]);
	if (data && data.metodosPago) {
		ko.utils.arrayForEach(data.metodosPago, function(mp) {
			if (AppConfig.catalogos.metodos.saldofavor != mp.id) {
				var metodo = new Catalogo();
				metodo.cargar(mp);
				self.metodosPago.push(metodo);
			}
		});
	}


	self.proveedorById = function() {
		if (self.pago.proveedor.id()) {
			var p = ko.utils.arrayFirst(self.proveedores(), function (item) {
				return self.pago.proveedor.id() == item.id;
			});

			self.pago.proveedor.cargar(p);
		} else {
			self.pago.proveedor.limpiar();
			self.facturasxp([]);
			self.removerTabla();
		}
	}


	self.cuentaById = function() {
	    //PETICION SALDO CUENTA BANCOS Y CAJAS
		if (self.pago.cuenta.id()) {
		    $.ajax({
                cache : false,
                url : contextPath + '/administrador/pago-proveedores/saldo',
                type : 'POST',
                dataType : 'json',
                data : {
                    idCuenta : self.pago.cuenta.id()
                },
                success : function(data) {
                    self.pago.saldo(data);
                }, error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("No se calculo el saldo.");
                }
            });
		    
			self.pago.saldo(self.pago.cuenta.inicial());
			self.pago.total(undefined);
		} else {
			self.pago.saldo(undefined);
			self.pago.cuenta.limpiar();
			self.pago.total(undefined);
		}
		self.limpiarMontos();
		self.seleccionarTodo(undefined);
	}


	self.pago.cuenta.id.subscribe(function (data) {
		if (data == undefined || data == "") {
			self.pago.total(undefined);
		}
	});


	self.inicializa = function() {
		self.pago.cuenta.limpiar();
		$("#cuenta").select2();
		self.pago.saldo(undefined);
		self.pago.total(undefined);
	};




	self.obtenerFacturasxPagar = function() {
		self.seleccionarTodo(undefined);
		self.inicializa();
		self.proveedorById();

		if (self.pago.proveedor.id()){

			$.ajax({
				cache : false,
				url : contextPath + '/administrador/pago-proveedores/facturasxpagar',
				type : 'POST',
				dataType : 'json',
				data : {
					rfc : self.pago.proveedor.rfc()
				},
				success : function(data) {
				    console.log(data);
					if (data) {
						self.facturasxp([]);
						self.removerTabla();

						self.procesaCargos(data);
						self.dibujarTabla();
					}

				}, error : function(jqXHR, textStatus, errorThrown) {
					self.facturasxp([]);
					notificacionError("El proveedor seleccionado no tiene facturas por pagar.");
				}
			});
		} else {
			self.facturasxp([]);
			self.totalProveedor(0);
		}
		self.pagoTotal(numeral().unformat(self.pago.total()));
	}


	//Propiedad temporal para total
	self.totalProveedor = ko.observable();


	self.procesaCargos = function(data) {
		var total = 0;
		ko.utils.arrayForEach(data, function(g) {
			var cfdi = new CFDI();
			cfdi.cargar(g);

			total = total + cfdi.total();
			self.totalProveedor(total);

			cfdi._antiguedad(self.calculaAntiguedad(cfdi.fechaVencimiento()));
			cfdi.tooltipTitle(cfdi.uuid());
			self.facturasxp.push(cfdi);
		});
		self.totalRestante(self.pagoTotal());
	}


	//Propiedad temporal para pago total
	self.pagoTotal = ko.observable();


	self.valida = function(data) {
		self.seleccionarTodo(undefined);
		var pagoTotal = numeral().unformat(self.pago.total());

		if (self.totalProveedor() > 0) {
			if (pagoTotal > self.totalProveedor()) {
				self.pago.total(self.totalProveedor());
				pagoTotal = numeral().unformat(self.pago.total());

				if (pagoTotal > self.pago.saldo()) {
					notificacionAdvertencia("El monto de la cuenta seleccionada no es suficiente para cubrir el monto deseado");
					self.pago.total(self.pago.saldo());
				} else {
					self.pago.total(self.totalProveedor());
					notificacionAdvertencia("El monto máximo a pagar es $" +self.pago.total());
				}
			} else {
				if (pagoTotal > self.pago.saldo()) {
					notificacionAdvertencia("El monto de la cuenta seleccionada no es suficiente para cubrir el monto deseado");
					self.pago.total(self.pago.saldo());
				}
			}
		}
		self.limpiarMontos();
		self.seleccionarTodo(true);

		if (self.pago.saldo() == 0) { self.seleccionarTodo(undefined); }
	}


	self.verificarPaloma = function(data, automatico) {
		if (automatico) {
			var totalPago = numeral().unformat(self.pago.total());

			ko.utils.arrayForEach(self.facturasxp(), function (g) {
				totalPago = totalPago - numeral().unformat(g.pagoTemporal());
				self.totalRestante(totalPago);
			});

			if (totalPago >= data.total()) {
				data.pagoTemporal(data.total());
				data.aplicaPago(true);
				self.totalRestante(self.totalRestante() - data.total());
			} else {
				//data.pagoTemporal(0);
				data.aplicaPago(undefined);
				data.pagoTemporal(totalPago);
				self.totalRestante(self.totalRestante() - totalPago);

				//totalPago = 0;
				//data.aplicaPago(undefined);
			}
		} else {

			setTimeout (function() {

				var totalPago = numeral().unformat(self.pago.total());
				ko.utils.arrayForEach(self.facturasxp(), function (g) {

					if (data.id() != g.id()) {
						if (g.aplicaPago() || numeral().unformat(g.pagoTemporal()) > 0) {
							totalPago = totalPago - numeral().unformat(g.pagoTemporal());
							self.totalRestante(totalPago);
						}
					}
				});

				if (data.aplicaPago()) {
					if (totalPago >= data.total()) {
						data.pagoTemporal(data.total());
						data.aplicaPago(true);
						self.totalRestante(self.totalRestante() - data.total());
					} else {
						data.aplicaPago(undefined);
						data.pagoTemporal(totalPago);

						//totalPago = totalPago - numeral().unformat(data.pagoTemporal());
						self.totalRestante(self.totalRestante() - data.pagoTemporal());
					}
				} else {
					//self.totalRestante(self.totalRestante() + numeral().unformat(data.pagoTemporal()));
					data.pagoTemporal(0);
				}
			}, 100)

		}
	}


	/*self.distribucionMontos = function() {
		var tot = numeral().unformat(self.pago.total());

		ko.utils.arrayForEach(self.facturasxp(), function(g) {
				if (tot > 0) {
					if (tot >= g.total()) {
						g.pagoTemporal(parseFloat(g.total()).toFixed(2));
						tot = tot - numeral().unformat(g.pagoTemporal());
						g.aplicaPago(true);
					} else {
						g.pagoTemporal(parseFloat(tot).toFixed(2));
						tot = 0;
						g.aplicaPago(undefined);
					}
				} else {
					g.pagoTemporal(0);
					g.aplicaPago(undefined);
				}
		});
	}*/


	/*self.verificarMontosLista = function (montoTotal) {
		var tot = 0;
		ko.utils.arrayForEach(self.facturasxp(), function(g) {

			tot = parseFloat(tot) + parseFloat(g.pagoTemporal());
			if (tot > montoTotal) {
				notificacionAdvertencia("::: " + g.pagoTemporal());
				tot = parseFloat(g.pagoTemporal()) + parseFloat(montoTotal) - parseFloat(tot)
				return;
			}
		});
		console.log("tot:: " + tot);
		return (tot);
	}*/


	self.totalCalculado = ko.computed (function() {
		var tot = 0;
		ko.utils.arrayForEach(self.facturasxp(), function(g) {
			var temp = numeral().unformat(g.pagoTemporal());
			tot += temp;
		});
		return (tot);
	}, self)


	self.calculaMontosManual = function(cargoSeleccionado) {

		var montoTotal = numeral().unformat(self.pago.total());
		var pagoTemporal = numeral().unformat(cargoSeleccionado.pagoTemporal());
		var saldoPendiente = cargoSeleccionado.total(); //cambiar propiedad total.

		if (montoTotal > 0) {
			if (cargoSeleccionado.pagoTemporal()) {

				var acumulado = self.totalCalculado();
				if (montoTotal < pagoTemporal) {

					notificacionAdvertencia("El monto es mayor al pago capturado.");
					cargoSeleccionado.pagoTemporal(montoTotal - (acumulado - pagoTemporal));
				}

				if (saldoPendiente < pagoTemporal) {
					cargoSeleccionado.pagoTemporal(saldoPendiente);
				}

				if (pagoTemporal < 0) {
					cargoSeleccionado.pagoTemporal(0);
				}
				if (acumulado > montoTotal) {
					cargoSeleccionado.pagoTemporal(montoTotal - (acumulado - pagoTemporal));
				}

			}
			//self.totalRestante(self.totalRestante() - cargoSeleccionado.pagoTemporal());

		} else {
			notificacionError("No hay una cantidad disponible para realizar el pago del cargo.");
			cargoSeleccionado.pagoTemporal(0);
		}

		if (cargoSeleccionado.pagoTemporal() == saldoPendiente) {
			cargoSeleccionado.aplicaPago(true);
		} else {
			cargoSeleccionado.aplicaPago(undefined);
		}

		self.totalRestante(montoTotal - self.totalCalculado());

	}


	self.limpiarMontos = function() {
		ko.utils.arrayForEach(self.facturasxp(), function(g) {
			g.pagoTemporal(undefined);
			g.aplicaPago(undefined);
		});
		self.pagoTotal(numeral().unformat(self.pago.total()));
		self.totalRestante(self.pagoTotal());
	}


	self.calculaAntiguedad = function(data) {
		var fecVenc = Date.convertir(data);
		var fecHoy = new Date();

		fecVenc.setDate(fecVenc.getDate() + 1);
		var dif = fecVenc - fecHoy;

		var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
		return dias;
	}


	self.guardar = function() {
		console.log("el valor es" + numeral().unformat(self.totalRestante()));

		if ($("#pagproveedores-form").valid() && numeral().unformat(self.totalRestante()) == 0) {

			var pago = JSON.stringify(self.pago.getJson(self.facturasxp()));
			console.log(pago);

			$.ajax({
				url: contextPath + "/administrador/pago-proveedores/guardar",
				type: 'POST',
				dataType: 'json',
				data: pago,
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito("El pago se ha guardado correctamente");
				},
				error: function (jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el pago");
				}
			});
		} else {
			notificacionAdvertencia("El formulario tiene errores o no se registro algún pago.");
		}
	}


}

var ListaPagoProveedoresViewModel = function(data) {
	self = this;

	self.pago = ko.observableArray([]);

	if (data.pago != undefined && data.pago.length > 0) {
		ko.utils.arrayForEach(data.pago, function(f) {
			self.pago.push(f);
			console.log(f);
		});
	}
	self.actualizar = function(data) {
		location.href = contextPath + "/administrador/pago-proveedores/actualizar/" + data.id;
	};

}