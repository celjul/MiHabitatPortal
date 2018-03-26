var TemaViewModel = function(data) {
    var self = this;


    self.tema = new Tema();
    self.postActual = new Post();
    self.estatusIncidenciaTemp = new Catalogo(config.estatusIncidencia.solicitada);
    self.usuario = new Usuario(data ? data.usuario : undefined);
    self.rol = ko.observable(data ? data.rol : undefined);
    self.nuevoPost = ko.observable(false);
    self.tiposTema = ko.observableArray([]);
    self.tipoSeleccionado = ko.observable(data ? data.tipoTema : undefined);
    self.blog = new Blog(data ? data.blog : undefined);

    //Incidencias
    self.tiposIncidencia = ko.observableArray([]);
    self.estatusIncidencia = ko.observableArray([]);

    self.setTipoPost = function (data) {
        if (self.tema.tipo() == config.tipoTemaNormal.id) {
            self.postActual.tipo(config.tipoPostNormal);
        } else if (self.tema.tipo() == config.tipoTemaEvento.id) {
            self.postActual.tipo(config.tipoPostNormal);
        } else if (self.tema.tipo() == config.tipoTemaIncidencia.id) {
            self.postActual.tipo(config.tipoPostIncidencia);
        }
    }

    if (data && data.tema) {
        self.tema.cargar(data.tema);
        self.setTipoPost();
        if(self.tema.tipo == config.tipoTemaIncidencia.id){
            self.postActual.estatusIncidencia.id((self.tema.posts()[self.tema.posts().length-1]).estatusIncidencia.id());
        }
    }
    else {
        self.postActual.estatusIncidencia.id(config.estatusIncidencia.solicitada);
        if(self.rol() != 'administrador') {
            self.tema.tipoIncidencia.id(config.tipoIncidencia.incidencia);
        }
    }

    if (data && data.tema && data.tema['@class']) {
        self.tipoSeleccionado(data.tema['@class']);
    }

    //Incidencias
    if (data && data.tipos) {
        ko.utils.arrayForEach(data.tipos, function (b) {
            var tipo = new Catalogo();
            tipo.cargar(b);
            self.tiposIncidencia.push(tipo);
        });
    }
    if (data && data.estatus) {
        ko.utils.arrayForEach(data.estatus, function (b) {
            var estatu = new Catalogo();
            estatu.cargar(b);
            self.estatusIncidencia.push(estatu);
        });
    }

    if((self.blog && (self.blog.id() == AppConfig.catalogos.blogs.incidencias || self.blog.id() == AppConfig.catalogos.blogs.soporte)) ||
        (self.tema.blog.id() && (self.tema.blog.id() == AppConfig.catalogos.blogs.incidencias || self.tema.blog.id() == AppConfig.catalogos.blogs.soporte))) {
        self.tiposTema.push(config.tipoTemaIncidencia);
    }
    self.tiposTema.push(config.tipoTemaNormal);
    self.tiposTema.push(config.tipoTemaEvento);



    moment.locale('es');
    $('#fechaInicio').datetimepicker({
        format: 'DD MMMM YYYY HH:mm A',
        locale: 'es',
        showTodayButton: true,
        allowInputToggle: true,
        ignoreReadonly: true,
        stepping: 5
    });
    $('#fechaFin').datetimepicker({
        format: 'DD MMMM YYYY HH:mm A',
        locale: 'es',
        showTodayButton: true,
        allowInputToggle: true,
        ignoreReadonly: true,
        stepping: 5
    });

    self.getTipo = function() {
        var cont = ko.utils.arrayFirst(self.tiposTema(), function(item) {
            return item.id == self.tema.tipo();
        });
        return cont;
    }

    if (data && data.blog) {
        self.tema.blog.cargar(data.blog);
    }

    self.puedePublicar = function(data){
        var puede = false;
        if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.bienvenidos){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.noticias){
            puede = true;
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.expertos){
            puede = true;
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.avisos){
            if(self.rol() == 'superadministrador' || self.rol() == 'administrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.temas){
            puede = true;
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.documentos){
            if(self.rol() == 'superadministrador' || self.rol() == 'administrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.incidencias){
            if(self.rol() == 'superadministrador' || self.rol() == 'administrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.anuncios){
            puede = true;
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.howto){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.faqs){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.tema.blog.id() && self.tema.blog.id() == AppConfig.catalogos.blogs.soporte){
            puede = true;
        }
        return puede;
    }

    self.inicializarDropZone = function (item) {
        var dropZone = item;

        Dropzone.autoDiscover = false;
        var myDropzone = new Dropzone(dropZone, {
            //url: "/file/post",
            addRemoveLinks : true,
            maxFilesize: 5,//MB
            dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
            dictResponseError: '¡Error al subir archivo!',
            dictCancelUpload: 'Cancelar',
            dictRemoveFile: 'Remover'
        });
        myDropzone.on("removedfile", function(file) {
            self.removerArchivo(file);
        });
        return myDropzone;
    }

    var auxMyDropzone1;
    var auxMyDropzone2;

    if(self.tema.id()) {
        auxMyDropzone2 = self.inicializarDropZone("#mydropzone2");
    }
    else {
        auxMyDropzone1 = self.inicializarDropZone("#mydropzone");
    }



    self.descargar = function(data) {
        window.open(contextPath  + "/" + self.rol() + "/blogs/descargar/" + data, '_blank');
    }

    self.removerArchivo = function(file){
        console.log('Remover archivo: ' + file.name);
        $.ajax({
            url : contextPath  + "/" + self.rol() + "/blogs/eliminararchivo",
            type : 'POST',
            dataType : 'json',
            data : {file: file.name},
            success : function(data) {

            },
            error : function(jqXHR, textStatus, errorThrown) {
                notificacionError("Ocurrio un error al eliminar el archivo");
            }
        });
    }

    self.temaLeido= function() {
        console.log(JSON.stringify(self.tema.getJson()));
        $.ajax({
            url : contextPath + "/" + self.rol() + "/blogs/temas/leer/" + self.tema.id(),
            type : 'POST',
            dataType : 'json',
            data : '',
            success : function(data) {
                //notificacionExito("Se actualizó la fecha de lectura del usuario.");
            },
            error : function(jqXHR, textStatus, errorThrown) {
                //notificacionExito("No se actualizó la fecha de lectura del usuario.");
            }
        });
    }

    self.guardar = function() {
        if ($("#tema-form").valid()) {
            if(self.tema.tipo() == config.tipoTemaEvento.id) {
                var inicio = new Date($('#fechaInicio').data("DateTimePicker").date().year(),
                    $('#fechaInicio').data("DateTimePicker").date().month(),
                    $('#fechaInicio').data("DateTimePicker").date().date(),
                    $('#fechaInicio').data("DateTimePicker").date().hour(),
                    $('#fechaInicio').data("DateTimePicker").date().minute(),
                    0,
                    0);
                self.tema.fechaInicio(inicio);
                var fin = new Date($('#fechaFin').data("DateTimePicker").date().year(),
                    $('#fechaFin').data("DateTimePicker").date().month(),
                    $('#fechaFin').data("DateTimePicker").date().date(),
                    $('#fechaFin').data("DateTimePicker").date().hour(),
                    $('#fechaFin').data("DateTimePicker").date().minute(),
                    0,
                    0);
                self.tema.fechaFin(fin);
            }
            var tema = JSON.stringify(self.tema.getJson());
            console.log(tema);
            $.ajax({
                url : contextPath + "/" + self.rol() + "/blogs/temas/guardar",
                type : 'POST',
                dataType : 'json',
                data : tema,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.tema.limpiar();
                    self.tema.cargar(data);
                    self.guardarPost();
                    auxMyDropzone2 = self.inicializarDropZone("#mydropzone2");  
                    notificacionExito("El Tema se ha creado correctamente.");

                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el tema.");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.actualizar = function(){
        if ($("#tema-form").valid()) {
            var tema = JSON.stringify(self.incidencia.getJson());
            console.log(tema);
            $.ajax({
                async : true,
                cache : false,
                url : contextPath  + "/" + self.rol() + "/blogs/temas/actualizar",
                type : 'POST',
                dataType : 'json',
                data : tema,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.tema.limpiar();
                    self.tema.cargar(data);
                    notificacionExito("El Tema se ha actualizado correctamente");
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al guardar el Tema");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.guardarPost = function() {
        if ($("#post-form").valid()) {

            if(self.estatusIncidenciaTemp.id()){
                self.postActual.estatusIncidencia.id(self.estatusIncidenciaTemp.id());
            }

            self.postActual.tema.id(self.tema.id());
            self.postActual.tema.tipo(self.tema.tipo());
            var postActual = JSON.stringify(self.postActual.getJson());
            console.log(postActual);
            $.ajax({
                async : false,
                url : contextPath  + "/" + self.rol() + "/blogs/post/guardar",
                type : 'POST',
                dataType : 'json',
                data : postActual,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.tema.limpiar();
                    self.postActual.limpiar();
                    self.tema.cargar(data);
                    if(auxMyDropzone1){
                        auxMyDropzone1.removeAllFiles();
                    }
                    if(auxMyDropzone2){
                        auxMyDropzone2.removeAllFiles();
                    } else {
                        auxMyDropzone2 = self.inicializarDropZone("#mydropzone2");
                    }
                    self.setTipoPost();
                    notificacionExito("Se ha publicado el post.");

                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al publicar");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.actualizarPost = function() {
        if ($("#post-form").valid()) {

            self.postActual.tema.id(self.tema.id());
            var postActual = JSON.stringify(self.postActual.getJson());
            console.log(postActual);
            $.ajax({
                url : contextPath  + "/" + self.rol() + "/blogs/post/actualizar",
                type : 'POST',
                dataType : 'json',
                data : postActual,
                contentType : 'application/json',
                mimeType : 'application/json',
                success : function(data) {
                    self.tema.limpiar();
                    self.tema.cargar(data);
                    notificacionExito("Se ha publicado la actualización");

                },
                error : function(jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al publicar");
                }
            });
        } else {
            notificacionAdvertencia("El formulario tiene errores");
        }
    }

    self.temas = function(data) {
        location.href = contextPath + "/" + self.rol() + "/blogs/" + self.tema.blog.id() + "/temas/lista" ;
    };
    self.foros = function() {
        location.href = contextPath + "/" + self.rol() + "/blogs/lista" ;
    };
}

var ListaBlogViewModel = function(data) {
    var self = this;

    self.blogs = ko.observableArray([]);
    self.rol = ko.observable(data ? data.rol : undefined);
    self.usuario = ko.observable(data ? data.usuario : undefined);

    if (data.blogs != undefined && data.blogs.length > 0) {
        ko.utils.arrayForEach(data.blogs, function(b) {
            b.conteoPost=0;
            b.ultimoPost=undefined;
            b.ultimoTemaRevisado=undefined;
            b.visto=true;
            if (b.temas != undefined && b.temas.length > 0) {
                ko.utils.arrayForEach(b.temas, function(t) {
                    b.conteoPost= b.conteoPost + t.posts.length;
                    if(!b.ultimoPost || b.ultimoPost.fecha < t.posts[t.posts.length-1].fecha) {
                        b.ultimoPost=t.posts[t.posts.length-1];
                    }
                    b.ultimoTemaRevisado = ko.utils.arrayFirst(t.temasRevisados, function(item) {
                            return (item.usuario.id == self.usuario().id);
                        });
                    b.visto = !b.visto?b.visto:(b.ultimoTemaRevisado&&b.ultimoPost)?(b.ultimoTemaRevisado.fecha > b.ultimoPost.fecha):false;
                });
            }
            self.blogs.push(b);
        });
    }

    self.actualizar = function(data) {
        location.href = contextPath + "/" + self.rol() + "/blogs/" + data.id + "/temas/lista" ;
    };
}

var ListaTemaViewModel = function(data) {
    var self = this;

    self.temas = ko.observableArray([]);
    self.rol = ko.observable(data ? data.rol : undefined);
    self.blog = ko.observable(data ? data.blog : undefined);
    self.tiposTema = ko.observableArray([]);
    self.usuario = ko.observable(data ? data.usuario : undefined);

    self.ordenarAsc = function(temas) {
        var ordenados = temas;
        var hoy = new Date();
        ordenados.sort(function (a, b){
            var fechaA = new Date(a.ultimoPost.fecha);
            var fechaB = new Date(b.ultimoPost.fecha);
            return (Math.abs(fechaA.getTime() - hoy.getTime()) - Math.abs(fechaB.getTime() - hoy.getTime()));
        });
        return ordenados;
    }

    self.tiposTema.push(config.tipoTemaIncidencia);
    self.tiposTema.push(config.tipoTemaNormal);
    self.tiposTema.push(config.tipoTemaEvento);

    self.getTipo = function(tipo) {
        var cont = ko.utils.arrayFirst(self.tiposTema(), function(item) {
            return item.id == tipo;
        });
        return cont;
    }

    if (data.blog.temas != undefined && data.blog.temas.length > 0) {
        ko.utils.arrayForEach(data.blog.temas, function(t) {
            t.tipo = t["@class"];
            t.ultimoPost=t.posts[t.posts.length-1];
            t.conteoPost= t.posts.length;
            t.ultimoTemaRevisado = undefined;
            t.visto = false;
            if(!t.tipoIncidencia){
                t.tipoIncidencia = undefined;
            }

            t.ultimoTemaRevisado = t.ultimoTemaRevisado || ko.utils.arrayFirst(t.temasRevisados, function(item) {
                    return (item.usuario.id == self.usuario().id);
                });
            t.visto= (t.ultimoTemaRevisado&&t.ultimoPost)?(t.ultimoTemaRevisado.fecha > t.ultimoPost.fecha):false;

            self.temas.push(t);
        });
    }

    self.temas(self.ordenarAsc(self.temas()));

    self.puedeCrear = function(data){
        var puede = false;
        if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.bienvenidos){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.noticias){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.expertos){
            puede = true;
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.avisos){
            if(self.rol() == 'superadministrador' || self.rol() == 'administrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.temas){
            puede = true;
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.documentos){
            if(self.rol() == 'superadministrador' || self.rol() == 'administrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.incidencias){
            puede = true;
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.anuncios){
            puede = true;
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.howto){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.faqs){
            if(self.rol() == 'superadministrador'){
                puede = true;
            } else {
                puede = false;
            }
        } else if(self.blog().id && self.blog().id == AppConfig.catalogos.blogs.soporte){
            puede = true;
        }
        return puede;
    }

    self.actualizar = function(data) {
        location.href = contextPath + "/" + self.rol() + "/blogs/temas/actualizar/" + data.id;
    };

    self.foros = function() {
        location.href = contextPath + "/" + self.rol() + "/blogs/lista" ;
    };

    self.nuevo = function(data) {
        location.href = contextPath + "/" + self.rol() +"/blogs/" + self.blog().id + "/temas/nuevo";
    };
}