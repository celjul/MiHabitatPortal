var ConfiguracionNotificacionContacto = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.contactoDepartamento = new ContactoDepartamento(data ? data.contactoDepartamento : undefined);
    self.tipoNotificacionNuevoCargo = new Catalogo(data ? data.tipoNotificacionNuevoCargo : undefined);
    self.tipoNotificacionNuevoRecargo = new Catalogo(data ? data.tipoNotificacionNuevoRecargo : undefined);
    self.tipoNotificacionAbonoValidado = new Catalogo(data ? data.tipoNotificacionAbonoValidado : undefined);
    self.tipoNotificacionAbonoCancelado = new Catalogo(data ? data.tipoNotificacionAbonoCancelado : undefined);
    self.tipoNotificacionAprovecharDescuento = new Catalogo(data ? data.tipoNotificacionAprovecharDescuento : undefined);
    self.tipoNotificacionEvitarRecargo = new Catalogo(data ? data.tipoNotificacionEvitarRecargo : undefined);
    self.tipoNotificacionAvisoCobranza = new Catalogo(data ? data.tipoNotificacionAvisoCobranza : undefined);
    self.tipoNotificacionNuevoTema = new Catalogo(data ? data.tipoNotificacionNuevoTema : undefined);
    self.tipoNotificacionNuevoComentarioTemaPropio = new Catalogo(data ? data.tipoNotificacionNuevoComentarioTemaPropio : undefined);
    self.tipoNotificacionNuevoComentarioTemaComentado = new Catalogo(data ? data.tipoNotificacionNuevoComentarioTemaComentado : undefined);
    self.tipoNotificacionNuevoComentario = new Catalogo(data ? data.tipoNotificacionNuevoComentario : undefined);
    self.tipoNotificacionNuevoAviso = new Catalogo(data ? data.tipoNotificacionNuevoAviso : undefined);
    self.tipoNotificacionReservacionValidada = new Catalogo(data ? data.tipoNotificacionReservacionValidada : undefined);
    self.tipoNotificacionIncidenciaActualizada = new Catalogo(data ? data.tipoNotificacionIncidenciaActualizada : undefined);

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.contactoDepartamento.cargar(data ? data.contactoDepartamento : undefined);
        self.tipoNotificacionNuevoCargo.cargar(data ? data.tipoNotificacionNuevoCargo : undefined);
        self.tipoNotificacionNuevoRecargo.cargar(data ? data.tipoNotificacionNuevoRecargo : undefined);
        self.tipoNotificacionAbonoValidado.cargar(data ? data.tipoNotificacionAbonoValidado : undefined);
        self.tipoNotificacionAbonoCancelado.cargar(data ? data.tipoNotificacionAbonoCancelado : undefined);
        self.tipoNotificacionAprovecharDescuento.cargar(data ? data.tipoNotificacionAprovecharDescuento : undefined);
        self.tipoNotificacionEvitarRecargo.cargar(data ? data.tipoNotificacionEvitarRecargo : undefined);
        self.tipoNotificacionAvisoCobranza.cargar(data ? data.tipoNotificacionAvisoCobranza : undefined);
        self.tipoNotificacionNuevoTema.cargar(data ? data.tipoNotificacionNuevoTema : undefined);
        self.tipoNotificacionNuevoComentarioTemaPropio.cargar(data ? data.tipoNotificacionNuevoComentarioTemaPropio : undefined);
        self.tipoNotificacionNuevoComentarioTemaComentado.cargar(data ? data.tipoNotificacionNuevoComentarioTemaComentado : undefined);
        self.tipoNotificacionNuevoComentario.cargar(data ? data.tipoNotificacionNuevoComentario : undefined);
        self.tipoNotificacionNuevoAviso.cargar(data ? data.tipoNotificacionNuevoAviso : undefined);
        self.tipoNotificacionReservacionValidada.cargar(data ? data.tipoNotificacionReservacionValidada : undefined);
        self.tipoNotificacionIncidenciaActualizada.cargar(data ? data.tipoNotificacionIncidenciaActualizada : undefined);
    }

    self.limpiar = function() {
        self.id(undefined);
        self.contactoDepartamento.limpiar();
        self.tipoNotificacionNuevoCargo.limpiar();
        self.tipoNotificacionNuevoRecargo.limpiar();
        self.tipoNotificacionAbonoValidado.limpiar();
        self.tipoNotificacionAbonoCancelado.limpiar();
        self.tipoNotificacionAprovecharDescuento.limpiar();
        self.tipoNotificacionEvitarRecargo.limpiar();
        self.tipoNotificacionAvisoCobranza.limpiar();
        self.tipoNotificacionNuevoTema.limpiar();
        self.tipoNotificacionNuevoComentarioTemaPropio.limpiar();
        self.tipoNotificacionNuevoComentarioTemaComentado.limpiar();
        self.tipoNotificacionNuevoComentario.limpiar();
        self.tipoNotificacionNuevoAviso.limpiar();
        self.tipoNotificacionReservacionValidada.limpiar();
        self.tipoNotificacionIncidenciaActualizada.limpiar();
    }

    self.getJson = function() {
        var configuracionNotificacionContacto = self.estructurar(ko.toJS(self));
        configuracionNotificacionContacto = validarObject(configuracionNotificacionContacto);
        return configuracionNotificacionContacto;
    }

    self.estructurar = function(data) {
        if (data.contactoDepartamento) {
            if(data.contactoDepartamento.id.contacto) {
                data.contactoDepartamento.id.contacto = {id : data.contactoDepartamento.id.contacto.id }
            }
            if(data.contactoDepartamento.id.departamento) {
                data.contactoDepartamento.id.departamento = {id : data.contactoDepartamento.id.departamento.id }
            }
        }
        return data;
    }

}