var PagoTarjeta = function() {
	var _self = this;
	_self.monto = ko.observable(0);
	_self.tarjeta = ko.observable("");
	_self.nombre = ko.observable("");
	_self.cvv = ko.observable("");
	_self.expiracion = ko.observable("");
	
	_self.getJson = function(pago) {
		_self.monto = pago.monto;
		 var pagotarjeta = ko.toJS(_self);
		 pagotarjeta = validarObject(pagotarjeta);
	     return pagotarjeta;
	}
}	