var UsuarioViewModel = function(data){
	self = this;
	
	self.usuario = new Usuario(Object.get(data, "usuario"));
	self.roles = ko.observableArray(Object.getArray(data, "roles"));
	self.catalogoEmail = ko.observableArray(Object.getArray(data, "catalogoEmail"));
	self.catalogoTelefono = ko.observableArray(Object.getArray(data, "catalogoTelefono"));
}
	

var ListaUsuariosViewModel = function(data) {
	self = this;
	
	self.usuario = new Usuario(Object.get(data, "usuario"));
	self.roles = ko.observableArray(Object.getArray(data, "roles"));
	self.catalogoEmail = ko.observableArray(Object.getArray(data, "catalogoEmail"));
	self.catalogoTelefono = ko.observableArray(Object.getArray(data, "catalogoTelefono"));
	
	self.usuarios = ko.observableArray(Object.getArray(data, "usuarios"));
	
	self.actualizar = function(data) {
		location.href = String.format("{0}/usuarios/{1}", PathConfig.usuarios, data.id);
	};
}