var DirectorioRegistroViewModel = function(data) {
    var self = this;

    self.tiposDirectorio = ko.observableArray(data ? data.tiposDirectorio : []);

    self.getTipoDirectorio = function(id) {
        var c = ko.utils.arrayFirst(self.tiposDirectorio(), function(item) {
            return item.id == id;
        });
        return c;
    }

    self.directorioRegistros = ko.observableArray();
    ko.utils.arrayForEach(data.registrosDirectorio, function(t) {
        var dir = new DirectorioRegistro(t);
        self.directorioRegistros.push(dir);
    });

    self.directorioRegistro = new DirectorioRegistro();

    self.nuevoDirectorio = function(data) {
        self.directorioRegistro.limpiar();
    }

    self.editarDirectorio = function(data) {
        self.directorioRegistro.cargar(data.getJson());
    }

    self.guardarDirectorio = function() {
        if ($("#directorio-form").valid()) {
            var directorioRegistro = JSON.stringify(self.directorioRegistro.getJson());
            console.log(directorioRegistro);
            $.ajax({
                url : contextPath + "/administrador/directorio/guardar",
                type : 'POST',
                dataType : 'json',
                data : directorioRegistro,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.directorioRegistro.limpiar();
                    /*self.directorioRegistro.cargar(data);*/
                    var dir = new DirectorioRegistro();
                    dir.cargar(data);
                    self.directorioRegistros.push(dir);

                    notificacionExito("El registro del directorio se ha creado correctamente.");
                    $("#myModalDirectorio").modal("hide");

                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el directorio.");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.actualizarDirectorio = function(){
        if ($("#directorio-form").valid()) {
            var directorioRegistro = JSON.stringify(self.directorioRegistro.getJson());
            console.log(directorioRegistro);
            $.ajax({
                url : contextPath + "/administrador/directorio/actualizar",
                type : 'POST',
                dataType : 'json',
                data : directorioRegistro,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.directorioRegistro.limpiar();
                    self.directorioRegistro.cargar(data);

                        /*var dir = self.directorioRegistro;*/
                        var encontrado = ko.utils.arrayFirst(self.directorioRegistros(), function(item) {
                            return item.id() == self.directorioRegistro.id();
                        });
                        encontrado.cargar(self.directorioRegistro.getJson());

                    $("#myModalDirectorio").modal("hide");

                    notificacionExito("El registro del directorio se ha actualizado correctamente.");
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el directorio.");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

}
