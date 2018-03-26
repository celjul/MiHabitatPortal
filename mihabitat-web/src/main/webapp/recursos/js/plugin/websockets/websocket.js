/**
 * 
 */

var stompClient = null;

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
	document.getElementById('response').innerHTML = '';
}

function connect() {
	var socket = new SockJS('/portfolio');
	stompClient = Stomp.over(socket);
	stompClient.connect({},function(frame){
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/notificaciones', function(greeting){
			alert("Hola "+ greeting);
		});
		stompClient.subscribe('/topic/notificaciones/departamento/1/todos', function(notificacion){
			notificacionExito(JSON.parse(notificacion.body).mensaje);
		});
	});
}

function disconnect(){
	if(stompClient != null){
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

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
