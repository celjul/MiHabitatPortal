var CondominioViewModel = function(data) {
	var self = this;
	
	self.condominio = new Condominio();
	self.administradores = ko.observableArray(Object.getArray(data, "administradores"));
	self.paises = ko.observableArray(Object.getArray(data, "paises"));
	self.admins = ko.computed(function(){
		return $.map(self.administradores(), function(item){
			return {
				id : item.id,
				nombre : item.persona.nombre,
				apellidoPaterno : item.persona.apellidoPaterno,
				apellidoMaterno : item.persona.apellidoMaterno
			}
		})
	}, self);
	if (data && data.condominio) {
		self.condominio.cargar(data.condominio);
	}
}

var ListaCondominiosViewModel = function(data) {
	self = this;
	self.condominios = ko.observableArray([]);
	if (data.condominios != undefined && data.condominios.length > 0) {
		ko.utils.arrayForEach(data.condominios, function(c) {
			self.condominios.push(c);
		});
	}
	self.actualizar = function(data) {
		location.href = contextPath + "/super-administrador/condominios/actualizar/" + data.id;
	};
	
	self.condominio = new Condominio();
	self.administradores = ko.observableArray(Object.getArray(data, "administradores"));
	self.paises = ko.observableArray(Object.getArray(data, "paises"));
	self.admins = ko.computed(function(){
		return $.map(self.administradores(), function(item){
			return {
				id : item.id,
				nombre : item.persona.nombre,
				apellidoPaterno : item.persona.apellidoPaterno,
				apellidoMaterno : item.persona.apellidoMaterno
			}
		})
	}, self);
}