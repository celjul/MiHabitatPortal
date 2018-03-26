var Tema = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.blog = new Blog(data ? data.blog : undefined);
    self.usuario = new Usuario(data ? data.usuario : undefined);
    self.nombre= ko.observable(data ? data.nombre : undefined);
    self.fecha= ko.observable(data ? data.fecha : (new Date()).toJSON().substr(0,10));
    self.activo = ko.observable(data ? data.activo : true);
    self.posts = ko.observableArray(data ? data.posts : []);
    self.temasRevisados = ko.observableArray(data ? data.temasRevisados : []);

    self.ultimaVisualizacion = ko.computed(function() {
        var temaRevisado = undefined;
        ko.utils.arrayForEach(self.temasRevisados(), function(e) {
            if(e.usuario.id() == self.usuario.id()){
                temaRevisado = e;
            }
        });
        return temaRevisado?temaRevisado.fecha():undefined;
    });



    self.tipo = ko.observable(data ? data['@class'] : undefined);

    //TemaEvento
    self.fechaInicio= ko.observable(data ? data.fechaInicio : undefined);
    self.fechaFin= ko.observable(data ? data.fechaFin : undefined);

    //TemaIncidencia
    self.tipoIncidencia= new Catalogo(data ? data.tipoIncidencia : undefined);

    self.editarTitulo = ko.observable(false);

    self.cargar = function(data) {

        self.id(data ? data.id : undefined);
        self.blog.cargar(data ? data.blog : undefined);
        self.usuario.cargar(data ? data.usuario : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.fecha(data ? (new Date(data.fecha).toJSON().substr(0,10)) : undefined)
        self.activo(data ? data.activo : true);

        if (data && data.posts) {
            ko.utils.arrayForEach(data.posts, function(e) {
                var post = new Post();
                post.cargar(e);
                self.posts.push(post);
            });
        }

        if (data && data.temasRevisados) {
            ko.utils.arrayForEach(data.temasRevisados, function(e) {
                var tr = new TemaRevisado();
                tr.cargar(e);
                self.temasRevisados.push(tr);
            });
        }

        self.tipo(data ? data['@class'] : undefined);

        //TemaEvento
        self.fechaInicio(data ? data.fechaInicio : undefined);
        self.fechaFin(data ? data.fechaFin : undefined);

        //TemaIncidencia
        self.tipoIncidencia.cargar(data ? data.tipoIncidencia : undefined);

        self.editarTitulo(false);
    }

    self.limpiar = function() {

        self.id(undefined);
        self.blog.limpiar();
        self.usuario.limpiar();
        self.nombre(undefined);
        self.fecha(undefined);
        self.activo(true);
        self.posts([]);

        self.tipo(undefined);

        //TemaEvento
        self.fechaInicio(undefined);
        self.fechaFin(undefined);

        //TemaIncidencia
        self.tipoIncidencia.limpiar(undefined)

    }

    self.getJson = function() {
        var tema = self.estructurar(ko.toJS(self));
        tema = validarObject(tema);
        return tema;
    }

    self.estructurar = function(data) {

        delete data.editarTitulo;
        delete data.posts;

        if (data && data.blog) {
            data.blog = {id: self.blog.id()};
        }
        if (data && data.usuario) {
            data.usuario = {id: self.usuario.id()};
        }

        if (data && data.tipo) {
            data['@class'] = data.tipo;
            if(data.tipo == config.tipoTemaNormal.id) {
                delete data.fechaInicio;
                delete data.fechaFin;
                delete data.tipoIncidencia;
            }
            else if(data.tipo == config.tipoTemaEvento.id) {
                delete data.tipoIncidencia;
            }
            else if(data.tipo == config.tipoTemaIncidencia.id) {
                delete data.fechaInicio;
                delete data.fechaFin;
            }
            delete data.tipo;
        }

        return data;
    }
}