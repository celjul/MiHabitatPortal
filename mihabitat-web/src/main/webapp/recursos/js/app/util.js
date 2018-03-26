validarObject = function(object) {
	var obj = $.parseJSON(JSON.stringify(object));
	for ( var key in obj) {
		if (typeof obj[key] === "object") {
			var otherObj = obj[key];
			if ($.isEmptyObject(otherObj)) {
				delete obj[key];
			} else {
				obj[key] = validarObject(otherObj);
				if ($.isEmptyObject(obj[key])) {
					delete obj[key];
				}
			}
		}
	}
	return obj;
}

String.returnValue = function(value, char) {
	return value ? (char ? char : " ") + value : "";
}

String.format = function(format) {
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};

notificacionExito = function(contenido) {
	$.smallBox({
		title : "Exito",
		content : "<i class='fa fa-bell'></i> <i>" + contenido + "</i>",
		color : "#739E73",
		iconSmall : "fa fa-check bounce animated",
		timeout : 5000
	});
}

notificacionAdvertencia = function(contenido) {
	$.smallBox({
		title : "Advertencia",
		content : "<i class='fa fa-bell'></i> <i>" + contenido + "</i>",
		color : "#C79121",
		iconSmall : "fa fa-shield fadeInLeft animated",
		timeout : 5000
	});
}

notificacionInfoEvent = function(contenido, rol) {
	$.smallBox({
		title : contenido.titulo,
		content :  contenido.inicio + (contenido.fin?(' - ' + contenido.fin):'') + "<br>" +contenido.mensaje + " <p class='text-align-right'>" +
		"<a href='" + "/"+rol.toLowerCase()+contenido.link + "' class='btn btn-danger btn-sm'>Ver más</a></p>",
		color : "#01B6AF",
		icon : "fa fa-calendar swing animated",
		timeout : 10000
	});
}

notificacionInfo = function(contenido, rol) {
	$.smallBox({
		title : contenido.titulo,
		content : contenido.mensaje + " <p class='text-align-right'>" +
		"<a href='" + "/"+rol.toLowerCase()+contenido.link + "' class='btn btn-danger btn-sm'>Ver más</a></p>",
		color : "#01B6AF",
		icon : "fa fa-bell swing animated",
		timeout : 10000
	});
}

notificacionError = function(contenido) {
	$.smallBox({
		title : "Error",
		content : "<i class='fa fa-bell'></i> <i>" + contenido + "</i>",
		color : "#C46A69",
		iconSmall : "fa fa-warning shake animated",
		timeout : 5000
	});
}

notificacionConfirmacion = function(contenido, callback) {
	$.SmartMessageBox({
		title : "Confirmación",
		content : contenido,
		buttons : '[Si][No]'
	}, function(ButtonPressed) {
		if (ButtonPressed === "Si") {
			callback()
			/*$.smallBox({
				title : "Callback function",
				content : "<i class='fa fa-clock-o'></i> <i>You pressed Yes...</i>",
				color : "#659265",
				iconSmall : "fa fa-check fa-2x fadeInRight animated",
				timeout : 4000
			});*/
		}
		/*if (ButtonPressed === "No") {
			$.smallBox({
				title : "Callback function",
				content : "<i class='fa fa-clock-o'></i> <i>You pressed No...</i>",
				color : "#C46A69",
				iconSmall : "fa fa-times fa-2x fadeInRight animated",
				timeout : 4000
			});
		}*/

	});
	//e.preventDefault();
};


$(document).ajaxStart(function() {
	bloquear()
});

$(document).ajaxComplete(function() {
	desbloquear()
});

bloquear = function() {
	$.blockUI({
		message : '<h3>Por favor espere...</h3>',
		theme : false,
		baseZ : 9999999,
		css : {
			border : 'none',
			padding : '25px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '15px',
			'-moz-border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		}
	});
}

desbloquear = function() {
	setTimeout($.unblockUI, 250);
}

function nospaces(t) {
	if (t.value.match(/\s/g)) {
		t.value = t.value.replace(/\s/g, '');
	}
}

/**
 * <p>
 * Funcion que se encarga de actualizar en la vista el menu seleccionado.
 * </p>
 */
$(function() {
	$.ajaxSetup({ cache: false });

	/*
	 * Obtenemos la ruta en la que se encuentra el navegador y lo comparamos con
	 * cada uno de los menus, para agregar la clase "active" al menú, si es que
	 * coincide.
	 */
	var menu = $("nav ul a[href!=#]");
	var contexto = String($(location).attr('pathname'));
	menu.each(function() {
		var hrefA = $(this).attr("href").split("?")[0];

		if (contexto === hrefA) {
			$(this).parent().addClass("active");
		}
	});
});

ko.subscribable.fn.capitalize = function () {
	var el = this;
	
	
	return el.subscribe(function (latestValue) {
		var contenido = $.trim(latestValue);
		var arrayContenido = contenido.split(" ");
		var arrayNuevo = [];
		for (var i = 0; i < arrayContenido.length; i++) {
			var contenidoNuevo = arrayContenido[i].charAt(0).toUpperCase()
					+ arrayContenido[i].slice(1).toLowerCase();
			if ($.trim(contenidoNuevo) != "") {
				arrayNuevo.push(contenidoNuevo);
			}
		}
		el(arrayNuevo.join(" "));
	}, el);
};


ko.subscribable.fn.subscribeChanged = function (callback) {
	var savedValue = this.peek();
	return this.subscribe(function (latestValue) {
		var oldValue = savedValue;
		savedValue = latestValue;
		callback(latestValue, oldValue);
	});
};

/**
 * Convierte una fecha en formato dd-MM-yyyy a una fecha válida en JavaScript.
 */
Date.convertir = function(sinformato) {
    var partes = sinformato.split("-");
    return new Date(partes[2], partes[1] - 1, partes[0]);
}

Date.convertirFecha = function(date) {
	return ('0' + date.getDate()).slice(-2) +  "-"
	+ ('0' + (date.getMonth()+1) ).slice(-2) + "-" + date.getFullYear();
}

/**
 * Convierte una hora en arreglo hh,mm,ss,ml a una fecha válida en JavaScript (hh:mm).
 */
if(!String.formatHour) {
	String.fotmatHour = function(hora) {
		if (hora && hora.length) {
			if(hora[0] != undefined && hora[1] != undefined) {
				if(hora[0] < 10) {
					hora[0]="0"+hora[0]
				}
				if(hora[1] < 10) {
					hora[1]="0"+hora[1]
				}
				return hora[0] + ":" + hora[1];
			}
		}
		return "";
	}

}

/**
 * Convierte milisegundos en una fecha y hora en formato mm/dd/yyyy HH:MM
 */
if(!String.formatMilisecondsToStringCalendar) {
	String.formatMilisecondsToStringCalendar = function(milisegundos) {
		var dateStr = new Date(milisegundos);
		dateStr = (new Date(dateStr)).getDate()+"/"+((new Date(dateStr)).getMonth()+1)+"/"+(new Date(dateStr)).getFullYear()+" "+(new Date(dateStr)).getHours()+":"+(new Date(dateStr)).getMinutes();
		return dateStr;
	}

}

/**
 * Convierte una fecha en formato yyyy-MM-dd a fecha en formato dd-MM-yyyy
 */
if(!String.formatToServer) {
	String.formatToServer = function(fecha) {
		var arr = fecha.split("-");
		var date = new Date(dateArr[0],dateArr[1]-1,dateArr[2]);

		return date.getDate()+'-'+(date.getMonth()+1)+'-'+date.getFullYear();
	}
}

/**
 * Convierte fecha y hora en formato mm/dd/yyyy HH:MM en formato ISO para poder enviarlo al servidor yyyy-mm-ddTHH:MM:ss.SSSZ
 */
if(!String.formatStringCalendarToISOString) {
	String.formatStringCalendarToISOString = function(stringCalendar) {
		var arr = stringCalendar.split(" ");
		var dateArr = arr[0].split("-");
		var hourArr = arr[1].split(":");
		var date = new Date(dateArr[0],dateArr[1]-1,dateArr[2],hourArr[0],hourArr[1],0,0);

		var dateStr = date.toISOString();
		return dateStr;
	}

}

/**
 * Borra todas las propiedades del objeto que cumplan con el patrón especificado
 */
var removeObjectProperties = function(obj, propsPatron) {
	for (var i in obj) {
		if(i.match(propsPatron) != null) {
			delete obj[i];
		}
	}
	return obj;
};