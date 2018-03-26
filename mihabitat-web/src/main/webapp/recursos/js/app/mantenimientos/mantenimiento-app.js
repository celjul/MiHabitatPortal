var MantenimientoCondominioViewModel = function (data) {

    var self = this;

    self.mantenimiento = new MantenimientoCondominio();
    self.operacionCorrecta = ko.observable(false);

    if (data && data.mantenimiento) {
        self.mantenimiento.cargar(data.mantenimiento);
    }

    self.editar = function (data) {
        if (data && data.mantenimiento) {
            self.mantenimiento.cargar(data.mantenimiento);
        }
    }

    self.limpiar = function () {
        self.mantenimiento.limpiar();
    }

    self.guardar = function () {
        if ($("#mantenimiento-form").valid()) {
            var mantenimiento = JSON.stringify(self.mantenimiento.getJson());
            console.log(mantenimiento);
            $.ajax({
                async: false,
                cache: false,
                url: contextPath + "/administrador/mantenimientos/guardar",
                type: 'POST',
                dataType: 'json',
                data: mantenimiento,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.mantenimiento.limpiar();
                    self.mantenimiento.cargar(data);
                    $("#myModalMantenimiento").modal("hide");
                    notificacionExito("El mantenimiento se ha guardado correctamente");
                    self.operacionCorrecta(true);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el mantenimiento");
                    self.operacionCorrecta(false);
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
            self.operacionCorrecta(false);
        }
    }

    self.actualizar = function () {
        if ($("#mantenimiento-form").valid()) {
            var mantenimiento = JSON.stringify(self.mantenimiento.getJson());
            console.log(mantenimiento);
            $.ajax({
                async: false,
                cache: false,
                url: contextPath + "/administrador/mantenimientos/actualizar",
                type: 'POST',
                dataType: 'json',
                data: mantenimiento,
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    self.mantenimiento.limpiar();
                    self.mantenimiento.cargar(data);
                    $("#myModalMantenimiento").modal("hide");
                    notificacionExito("El mantenimiento se ha actualizado correctamente");
                    self.operacionCorrecta(true);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el mantenimiento");
                    self.operacionCorrecta(false);
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
            self.operacionCorrecta(false);
        }
    }
}

var ListaMantenimientoCondominioViewModel = function (data) {

    var self = this;

    self.mantenimientoCondominioViewModel = new MantenimientoCondominioViewModel();
    self.mantenimientos = ko.observableArray([]);
    self.tiposCobro = ko.observableArray([]);

    if (data.mantenimientos != undefined && data.mantenimientos.length > 0) {
        ko.utils.arrayForEach(data.mantenimientos, function (m) {
            var mant = new MantenimientoCondominio();
            mant.cargar(m);
            self.mantenimientos.push(mant);
        });
    }

    if (data.tiposCobro != undefined && data.tiposCobro.length > 0) {
        ko.utils.arrayForEach(data.tiposCobro, function (m) {
            var mant = new Catalogo();
            mant.cargar(m);
            self.tiposCobro.push(mant);
        });
    }

    self.nuevoMantenimiento = function (data) {
        self.mantenimientoCondominioViewModel.limpiar();
    }

    self.editarMantenimiento = function (data) {
        console.log("hola");
        $.ajax({
            async: true,
            cache: false,
            url: contextPath + "/administrador/mantenimientos/actualizar/" + (typeof data.id === "function" ? data.id() : data.id),
            type: 'GET',
            dataType: 'json',
            data: '',
            success: function (data) {
                /*self.mantenimientoCondominioViewModel = new MantenimientoCondominioViewModel({
                 mantenimiento : data
                 });*/
                self.mantenimientoCondominioViewModel.editar({
                    mantenimiento: data
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al guardar el mantenimiento");
            }
        });
    }

    self.guardarMantenimiento = function (data) {
        self.mantenimientoCondominioViewModel.guardar();
        if (self.mantenimientoCondominioViewModel.operacionCorrecta()) {
            /*self.mantenimientos.push(self.mantenimientoCondominioViewModel.mantenimiento);*/
            var mant = new MantenimientoCondominio();
            mant.cargar(self.mantenimientoCondominioViewModel.mantenimiento.getJson());
            item = ko.utils.arrayFirst(self.tiposCobro(), function(c) {
                return c.id() === mant.tipoCobroDepartamento.id();
            });
            mant.tipoCobroDepartamento.descripcion(item.descripcion());
            self.mantenimientos.push(mant);
        }
    }

    self.actualizarMantenimiento = function (data) {
        self.mantenimientoCondominioViewModel.actualizar();
        if (self.mantenimientoCondominioViewModel.operacionCorrecta()) {
            var mantenimiento = self.mantenimientoCondominioViewModel.mantenimiento;
            var encontrado = ko.utils.arrayFirst(self.mantenimientos(), function (item) {
                return item.id() == mantenimiento.id();
            });
            encontrado.cargar(mantenimiento.getJson());
            item = ko.utils.arrayFirst(self.tiposCobro(), function(tc) {
                return tc.id() === mantenimiento.tipoCobroDepartamento.id();
            });
            encontrado.tipoCobroDepartamento.descripcion(item.descripcion());
        }
    }
}