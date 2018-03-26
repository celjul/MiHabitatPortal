var Post = function(data) {

    var self = this;

    self.id = ko.observable();
    self.tema = new Tema();
    self.comentario = ko.observable();
    self.usuario = new Usuario();
    self.adjuntos = ko.observableArray();
    self.fecha = ko.observable();
    self.activo = ko.observable(true);

    self.tipo = ko.observable(data ? data['@class'] : undefined);

    //PostIncidencia
    self.estatusIncidencia= new Catalogo(data ? data.estatusIncidencia : config.estatusIncidencia.solicitada);

    self.limpiar = function() {
        self.id(undefined);
        self.tema.limpiar();
        self.comentario(undefined);
        self.usuario.limpiar();
        self.adjuntos([]);
        self.fecha(undefined);
        self.activo(true);
        self.tipo(undefined);

        //PostIncidencia
        self.estatusIncidencia.limpiar();
    }

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.tema.cargar(data ? data.tema : undefined);
        self.comentario(data ? data.comentario : undefined);
        self.usuario.cargar(data ? data.usuario : undefined)
        if (data && data.adjuntos) {
            ko.utils.arrayForEach(data.adjuntos, function(e) {
                var adjunto = new AdjuntoPost();
                adjunto.cargar(e);
                self.adjuntos.push(adjunto);
            });
        }
        self.fecha(data ? data.fecha : undefined);
        self.activo(data ? data.activo : true);

        self.tipo(data ? data['@class'] : undefined);

        /*if(self.tema.tipo == config.tipoTemaNormal){
            self.tipo(config.tipoPostNormal);
        } else if(self.tema.tipo == config.tipoTemaEvento){
            self.tipo(config.tipoPostNormal);
        } else if(self.tema.tipo == config.tipoTemaIncidencia){
            self.tipo(config.tipoPostIncidencia);
        }*/

        //PostIncidencia
        self.estatusIncidencia.cargar(data ? data.estatusIncidencia : config.estatusIncidencia.solicitada);
    }

    self.getJson = function() {
        var post = self.estructurar(ko.toJS(self));
        post = validarObject(post);
        return post;
    }

    self.estructurar = function(data) {
        /*delete data.activo;*/
        delete data.usuario;
        /*data.fecha = new Date().getTime();*/
        /*if (data && data.usuario) {
            data.usuario = {id: 1};
        }*/

        if (data && data.tema) {
            var temaTemp = data.tema;
            data.tema = {id: data.tema.id};
            data.tema['@class'] = temaTemp.tipo;
        }

        if (data && data.tipo) {
            data['@class'] = data.tipo;
            if(data.tipo != config.tipoPostIncidencia) {
                delete data.estatusIncidencia;
            }
            delete data.tipo;
        }
        return data;
    }
}

/*
var Usuario = function() {

    var self = this;

    self.id = ko.observable();
    self.user = ko.observable();

    self.cargar = function(data) {
        self.id(data ? data.id : undefined);
        self.user(data ? data.user : undefined);
    }

    self.limpiar = function(data) {
        self.id(undefined);
        self.user(undefined);
    }

    self.getJson = function() {
        var usuario = ko.toJS(self);
        usuario = validarObject(usuario);
        return usuario;
    }
}*/
