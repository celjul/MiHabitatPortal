var Blog = function(data) {
    var self = this;

    self.id = ko.observable(data ? data.id : undefined);
    self.condominio = new Condominio(data ? data.condominio : undefined);
    /*self.tipo = new Catalogo(data ? data.tipo : {id:config.tipoIncidencia.proyecto});*/
    self.nombre= ko.observable(data ? data.nombre : undefined);
    self.descripcion= ko.observable(data ? data.descripcion : undefined);
    self.fecha= ko.observable(data ? data.fecha : (new Date()).toJSON().substr(0,10));
    self.activo = ko.observable(data ? data.activo : true);
    self.temas = ko.observableArray(data ? data.temas : []);

    self.cargar = function(data) {

        self.id(data ? data.id : undefined);
        self.condominio.cargar(data ? data.condominio : undefined);
        self.nombre(data ? data.nombre : undefined);
        self.descripcion(data ? data.descripcion : undefined);
        self.fecha(data ? (new Date(data.fecha).toJSON().substr(0,10)) : undefined)
        self.activo(data ? data.activo : true);
        if (data && data.temas) {
            ko.utils.arrayForEach(data.temas, function(e) {
                var tema = new Tema();
                tema.cargar(e);
                self.temas.push(tema);
            });
        }
    }

    self.limpiar = function() {

        self.id(undefined);
        self.condominio.limpiar();
        self.nombre(undefined);
        self.descripcion(undefined);
        self.fecha(undefined);
        self.activo(true);
        self.temas([]);

    }

    //TODO Guardado y actualización de blogs no soportado aún
    /*self.getJson = function() {
        var blog = self.estructurar(ko.toJS(self));
        blog = validarObject(blog);
        return blog;
    }

    self.estructurar = function(data) {

        delete data.editarTitulo;
        delete data.temas;

        if (data && data.condominio) {
            data.condominio = self.condominio.getJson();
        }
        /!*if (data.avances && data.avances.length > 0) {
         var avances = [];
         ko.utils.arrayForEach(self.avances(), function(c) {
         var cd = c.getJson();
         avances.push(cd);
         });
         data.avances = avances;
         }*!/

        if (data && data.tipo) {
            data.tipo = self.tipo.getJson();
        }

        return data;
    }*/
}