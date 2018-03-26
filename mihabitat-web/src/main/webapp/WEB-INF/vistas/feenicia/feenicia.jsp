<html>
	<header>
		<script src="${pageContext.request.contextPath}/recursos/js/libs/jquery-2.0.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/recursos/js/libs/knockout-3.3.0.js"></script>
		<script>
			var Pago = function() {
				var _self = this;
				_self.monto = ko.observable(19);
				_self.tarjeta = ko.observable("4444555566667779");
				_self.nombre = ko.observable("JOSE MOSCO ROJAS");
				_self.cvv = ko.observable("333");
				_self.expiracion = ko.observable("1701");
			}		
		
			var FeeniciaViewModel = function() {
				var _self = this;
				
				_self.pago = new Pago();
				
				_self.getOrder = function() {
					var pago = JSON.stringify(ko.toJS(_self.pago));
					console.log(pago);
					$.ajax({
						cache : false,
						url : "http://localhost:8070/feenicia/pagar",
						type : 'POST',
						dataType : 'json',
						data : pago,
						contentType : 'application/json',
						mimeType : 'application/json',
						success : function(data) {
							console.log(data);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
						}
					});
				}
				
				_self.getTransacciones = function() {
					$.ajax({
						cache : false,
						url : "http://localhost:8070/feenicia/reportes/transacciones",
						type : 'POST',
						dataType : 'json',
						contentType : 'application/json',
						mimeType : 'application/json',
						success : function(data) {
							console.log(data);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
						}
					});
				}
			}
			
			$(function() {
				var viewModel = new FeeniciaViewModel();
				ko.applyBindings(viewModel);
			});
		</script>
	</header>
	<body>
		<label>Monto</label>
		<input type="number" data-bind="value: $root.pago.monto">
		<input type="text" data-bind="value: $root.pago.tarjeta">
		<input type="text" data-bind="value: $root.pago.nombre">
		<input type="text" data-bind="value: $root.pago.cvv">
		<input type="text" data-bind="value: $root.pago.expiracion">
		<button data-bind="click: $root.getOrder">Orden</button>
		<hr />
		
		<h2>Reportes</h2>
		<button data-bind="click: $root.getTransacciones">Transacciones</button>
	</body>
</html>