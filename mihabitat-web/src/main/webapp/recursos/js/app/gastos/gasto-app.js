var GastoViewApp = function(data) {
	
	var self = this;
	
	self.proveedores = ko.observableArray();
	if (data && data.proveedores) {
		ko.utils.arrayForEach(data.proveedores, function (p) {
            var proveedor = new Catalogo();
            proveedor.id(p.id);
            proveedor.descripcion(p.nombre);
            self.proveedores.push(proveedor);
        });
	}
	
	self.bancosCajas = ko.observableArray();
	if (data && data.bancosCajas) {
		self.bancosCajas(ordenar(data.bancosCajas, true));
	}
	
	self.egresos = ko.observableArray();
	if (data && data.egresos) {
		self.egresos(ordenar(data.egresos, true));
	}
	
	self.metodosPago = ko.observableArray();
	if (data && data.metodosPago) {
		ko.utils.arrayForEach(data.metodosPago, function (mp) {
            var metodo = new Catalogo();
			metodo.cargar(mp);
			self.metodosPago.push(mp);
        });
	}
	
	self.condominio = ko.observable(data.condominio.id);
	self.gasto = new Gasto();
	
	self.saldo = ko.observable();
	self.getSaldo = function() {
		if (self.gasto.movimientoGasto.cuenta.id) {
			$.ajax({
	            cache : false,
	            url : contextPath + '/administrador/gastos/saldo',
	            type : 'POST',
	            dataType : 'json',
	            data : {
	                idCuenta : self.gasto.movimientoGasto.cuenta.id()
	            },
	            success : function(data) {
	                self.saldo(data);
	                //notificacionAdvertencia("El saldo de la cuenta es: " + data);
	            }, error : function(jqXHR, textStatus, errorThrown) {
	                notificacionError("No se calculo el saldo.");
	            }
	        });
		}
	}
	
	self.valida = function() {
		if (!$("#gasto-form").valid()) {
			notificacionError("El formulario no es válido");
			return false;
		}
		if (self.gasto.detalles().length < 0) {
			notificacionError("No existe ningun detalle del gasto");
			return false;
		}
		var total = 0;
		ko.utils.arrayForEach(self.gasto.detalles(), function (d) {
            total = total + d.movimientoDetallle.debe();
        });
		if (total > self.saldo()) {
			/*notificacionError("El total de detalles supera el saldo de la cuenta");
			return false;*/
		}
		return true;
	}

	self.guardar = function() {
		if (self.valida()) {
			var gasto = self.gasto.getJson();
			gasto.condominio = {
					id: self.condominio()
			}
			console.log(JSON.stringify(gasto));
			$.ajax({
				url: contextPath + "/administrador/gastos/guardar",
				type: 'POST',
				dataType: 'json',
				data: JSON.stringify(gasto),
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito("El gasto se ha guardado correctamente");
					setTimeout(function() { 
	                    location.href = contextPath + "/administrador/gastos/lista";
	                }, 1000);
				},
				error: function (jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al guardar el gasto");
				}
			});
		}
	}
	
	self.actualizar = function() {
		if (self.valida()) {
			var gasto = self.gasto.getJson();
			gasto.condominio = {
					id: self.condominio()
			}
			console.log(JSON.stringify(gasto));
			$.ajax({
				url: contextPath + "/administrador/gastos/actualizar",
				type: 'POST',
				dataType: 'json',
				data: JSON.stringify(gasto),
				contentType: 'application/json',
				mimeType: 'application/json',
				success: function (data) {
					notificacionExito("El gasto se ha actualizado correctamente");
					setTimeout(function() { 
	                    location.href = contextPath + "/administrador/gastos/lista";
	                }, 1000);
				},
				error: function (jqXHR, textStatus, errorThrown) {
					notificacionError("Ocurrio un error al actualizar el gasto");
				}
			});
		}
	}

	self.cancelar = function() {
		bootbox.confirm("EL gasto será cancelado", function(result) {
			if (result){
				if (self.valida()) {
					var gasto = self.gasto.getJson();
					gasto.condominio = {
						id: self.condominio()
					}
					$.ajax({
						url: contextPath + "/administrador/gastos/cancelar",
						type: 'POST',
						dataType: 'json',
						data: JSON.stringify(gasto),
						contentType: 'application/json',
						mimeType: 'application/json',
						success: function (data) {
							notificacionExito("El gasto se ha cancelado correctamente");
							setTimeout(function() {
								location.href = contextPath + "/administrador/gastos/lista";
							}, 1000);
						},
						error: function (jqXHR, textStatus, errorThrown) {
							notificacionError("Ocurrio un error al cancelar el gasto");
						}
					});
				}
			}
		});

	}

	if (data && data.gasto) {
		self.gasto.cargar(data.gasto);
		self.getSaldo();
		
		setTimeout(function() { 
			self.saldo(self.saldo() + self.gasto.movimientoGasto.haber());
		});
	}
}

var GastoListViewApp = function(data) {
	
	var self = this;
	
	self.gastos = ko.observableArray();
	
	if (data && data.gastos) {
		ko.utils.arrayForEach(data.gastos, function (g) {
            self.gastos.push(g);
        });
	}
	
	self.ver = function(data) {
		location.href = contextPath + "/administrador/gastos/editar/" + data.id;
	}
}