<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec"%>
<%--<script src="${pageContext.request.contextPath}/recursos/js/app/notificaciones/notificacion-app.js"></script>
<script  src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/sock.js"></script>
<script  src="${pageContext.request.contextPath}/recursos/js/plugin/websockets/stomp.js"></script>
<script type="text/javascript">
    var notifiacionesApp = new FuncionalidadNotificacionApp({});
    notifiacionesApp.obtenerNotificaciones();

    $(function(){
        $('.activity').click(function(e) {
            var $this = $(this);

            if ($this.find('.badge').hasClass('bg-color-red')) {
                $this.find('.badge').removeClassPrefix('bg-color-');
                $this.find('.badge').text("0");
            }

            if (!$this.next('.ajax-dropdown').is(':visible')) {
                $this.next('.ajax-dropdown').css("left",-86+($this.position().left-83));
                $this.next('.ajax-dropdown').css("top",47);
                $this.next('.ajax-dropdown').fadeIn(150);
                $this.addClass('active');
            } else {
                $this.next('.ajax-dropdown').fadeOut(150);
                $this.removeClass('active');
            }

            var theUrlVal = $this.next('.ajax-dropdown').find('.btn-group > .active > input').attr('id');

            //clear memory reference
            $this = null;
            theUrlVal = null;

            e.preventDefault();
        });

        var stompClient = null;
        var socket = new SockJS('/portfolio');
        stompClient = Stomp.over(socket);
        stompClient.connect({},function(frame){
            console.log('Connected: ' + frame);
            $.ajax({
                async: true,
                cache: false,
                url: contextPath + "/notificaciones/canales",
                type: 'GET',
                dataType: 'json',
                data: '',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    $.each(data,function(i,e){
                        stompClient.subscribe("/topic/notificaciones/"+e, function(notificacion){
                            notificacionInfo(JSON.parse(notificacion.body),'${rol}');
                            notifiacionesApp.obtenerNotificaciones();
                        });
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Ocurrio un error al suscribirse a las notificaciones");
                }
            });
        });

        function sendname() {
            var name = document.getElementById('name').value;
            $.ajax({
                async: true,
                cache: false,
                url: contextPath + "/notificaciones/prueba",
                type: 'POST',
                data: name,
                success: function (data) {
                    notificacionExito('Se envió...');
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    notificacionError("Ocurrio un error al recuperar las notificaciones");
                }
            });
        }



        // close dropdown if mouse is not inside the area of .ajax-dropdown
        $(document).mouseup(function(e) {
            if (!$('.ajax-dropdown').is(e.target) && $('.ajax-dropdown').has(e.target).length === 0) {
                $('.ajax-dropdown').fadeOut(150);
                $('.ajax-dropdown').prev().removeClass("active");
            }
        });

        // loading animation (demo purpose only)
        $('button[data-btn-loading]').on('click', function() {
            var btn = $(this);
            btn.button('loading');
            setTimeout(function() {
                btn.button('reset');
            }, 3000);
        });

        // NOTIFICATION IS PRESENT
        // Change color of lable once notification button is clicked

        $this = $('#activity > .badge');

        if (parseInt($this.text()) > 0) {
            $this.addClass("bg-color-red bounceIn animated");

            //clear memory reference
            $this = null;
        }
    });
</script>--%>
<sec:authorize access="hasRole('Administrador')">
<span class="ribbon-button-alignment" style="padding-right: 10px;">
		<div class="notification-group">
          <!-- Note: The activity badge color changes when clicked and resets the number to 0
            Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
          <span id="pendientesActivity" class="activity-dropdown activity">
              <i class="fa fa-check-circle"></i>
              <%--<b class="badgeBST" id="conteoPendientes"> 21 </b>--%>
          </span>

          <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
          <div class="ajax-dropdown">

            <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
            <div class="btn-group btn-group-justified" style="display: inline-block;line-height: 0px;" data-toggle="buttons">
              <label class="btn btn-default active" id="botonTodosPendientes" onclick="listarPor('todos', '#panelPendientesAdmin');">
                </label>
              <label class="btn btn-default" id="botonPagosPendientes" onclick="listarPor('pago-pendiente', '#panelPendientesAdmin');">
                </label>
              <label class="btn btn-default" id="botonReservacionesPendientes" onclick="listarPor('reservacion-pendiente', '#panelPendientesAdmin');">
              </label>
                <br />
                <label class="btn btn-default" id="botonIncidenciasSolicitadas" onclick="listarPor('incidencia-solicitada', '#panelPendientesAdmin');">
                </label>
            </div>

            <!-- notification content -->
            <div class="ajax-notifications custom-scroll">

              <ul class="notification-body" id="panelPendientesAdmin">

              </ul>


            </div>
            <!-- end notification content -->
            <!-- footer: refresh area -->
					<span > Última Actualización: <span class="ultimaActualizacion"></span>
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right" onclick="notifiacionesApp.obtenerNotificaciones();">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
            <!-- end footer -->

          </div>
          <!-- END AJAX-DROPDOWN -->

        </div>
</span>
</sec:authorize>
<sec:authorize access="!hasRole('Administrador')">
<span class="ribbon-button-alignment" style="padding-right: 10px;">
		<div class="notification-group">
            <!-- Note: The activity badge color changes when clicked and resets the number to 0
              Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
          <span id="nuevosActivity" class="activity-dropdown activity">
              <i class="fa fa-plus-circle"></i>
              <%--<b class="badgeBST" id="conteoPendientes"> 21 </b>--%>
          </span>

            <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
            <div class="ajax-dropdown">

                <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
                <div class="btn-group btn-group-justified" style="display: inline-block;line-height: 0px;" data-toggle="buttons">
                    <label class="btn btn-default active" id="botonTodosMovimientos" onclick="listarPor('todos', '#panelMovimientosContacto');">
                    </label>
                    <label class="btn btn-default" id="botonCargos" onclick="listarPor('nuevo-cargo', '#panelMovimientosContacto');">
                    </label>
                    <label class="btn btn-default" id="botonRecargos" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
                    </label>
                    <%-- <br/>
                   <label class="btn btn-default" id="botonCargos2" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
                   </label>
                   <label class="btn btn-default" id="botonRecargos2" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
                   </label>--%>
                </div>

                <!-- notification content -->
                <div class="ajax-notifications custom-scroll">

                    <ul class="notification-body" id="panelMovimientosContacto">

                    </ul>


                </div>
                <!-- end notification content -->
                <!-- footer: refresh area -->
					<span > Última Actualización: <span class="ultimaActualizacion"></span>
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right" onclick="notifiacionesApp.obtenerNotificaciones();">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
                <!-- end footer -->

            </div>
            <!-- END AJAX-DROPDOWN -->

        </div>
    </span>
<span class="ribbon-button-alignment" style="padding-right: 10px;">
    <div class="notification-group">
        <!-- Note: The activity badge color changes when clicked and resets the number to 0
          Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
          <span id="validacionesActivity" class="activity-dropdown activity">
              <i class="fa fa-check-square-o"></i>
              <%--<b class="badgeBST" id="conteoPendientes"> 21 </b>--%>
          </span>

        <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
        <div class="ajax-dropdown">

            <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
            <div class="btn-group btn-group-justified" style="display: inline-block;line-height: 0px" data-toggle="buttons">
                <label class="btn btn-default active" id="botonTodosValidaciones" onclick="listarPor('todos', '#panelValidacionesContacto');">
                </label>
                </br>
                <label class="btn btn-default" id="botonPagos" onclick="listarPor('pago', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonReservaciones" onclick="listarPor('reservacion', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonIncidencias" onclick="listarPor('incidencia', '#panelValidacionesContacto');">
                </label>
                <%-- <br/>
               <label class="btn btn-default" id="botonPagosAprobados" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>
               <label class="btn btn-default" id="botonReservacionesAprobadas" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>--%>
            </div>

            <!-- notification content -->
            <div class="ajax-notifications custom-scroll">

                <ul class="notification-body" id="panelValidacionesContacto">

                </ul>


            </div>
            <!-- end notification content -->
            <!-- footer: refresh area -->
					<span > Última Actualización: <span class="ultimaActualizacion"></span>
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right" onclick="notifiacionesApp.obtenerNotificaciones();">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
            <!-- end footer -->

        </div>
        <!-- END AJAX-DROPDOWN -->

    </div>
</span>
</sec:authorize>
<span class="ribbon-button-alignment" style="padding-right: 10px;">
    <div class="notification-group">
        <!-- Note: The activity badge color changes when clicked and resets the number to 0
          Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
          <!--  
	          <span id="mensajesActivity" class="activity-dropdown activity">
	              <i class="fa fa-envelope"></i>
	              <%--<b class="badgeBST" id="conteoPendientes"> 21 </b>--%>
	          </span>
          -->

        <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
        <div class="ajax-dropdown">

            <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
            <div class="btn-group btn-group-justified" style="display: inline-block;" data-toggle="buttons">
                <label class="btn btn-default active" id="botonTodosValidaciones" onclick="listarPor('todos', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonPagos" onclick="listarPor('pago', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonReservaciones" onclick="listarPor('reservacion', '#panelValidacionesContacto');">
                </label>
                <%-- <br/>
               <label class="btn btn-default" id="botonPagosAprobados" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>
               <label class="btn btn-default" id="botonReservacionesAprobadas" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>--%>
            </div>

            <!-- notification content -->
            <div class="ajax-notifications custom-scroll">

                <ul class="notification-body" id="panelMensajesContacto">

                </ul>


            </div>
            <!-- end notification content -->
            <!-- footer: refresh area -->
					<span > Última Actualización: <span class="ultimaActualizacion"></span>
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right" onclick="notifiacionesApp.obtenerNotificaciones();">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
            <!-- end footer -->

        </div>
        <!-- END AJAX-DROPDOWN -->

    </div>
</span>
<span class="ribbon-button-alignment" style="padding-right: 10px;">
    <div class="notification-group">
        <!-- Note: The activity badge color changes when clicked and resets the number to 0
          Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
         <%--  <span id="calendarioActivity" class="activity-dropdown activity">
              <i class="fa fa-calendar"></i>
              <b class="badgeBST" id="conteoPendientes"> 21 </b>
          </span>
 --%>
        <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
        <div class="ajax-dropdown">

            <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
            <div class="btn-group btn-group-justified" style="display: inline-block;" data-toggle="buttons">
                <label class="btn btn-default active" id="botonTodosValidaciones" onclick="listarPor('todos', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonPagos" onclick="listarPor('pago', '#panelValidacionesContacto');">
                </label>
                <label class="btn btn-default" id="botonReservaciones" onclick="listarPor('reservacion', '#panelValidacionesContacto');">
                </label>
                <%-- <br/>
               <label class="btn btn-default" id="botonPagosAprobados" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>
               <label class="btn btn-default" id="botonReservacionesAprobadas" onclick="listarPor('nuevo-recargo', '#panelMovimientosContacto');">
               </label>--%>
            </div>

            <!-- notification content -->
            <div class="ajax-notifications custom-scroll">

                <ul class="notification-body" id="panelCalendarioContacto">

                </ul>


            </div>
            <!-- end notification content -->
            <!-- footer: refresh area -->
					<span > Última Actualización: <span class="ultimaActualizacion"></span>
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right" onclick="notifiacionesApp.obtenerNotificaciones();">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
            <!-- end footer -->

        </div>
        <!-- END AJAX-DROPDOWN -->

    </div>
</span>