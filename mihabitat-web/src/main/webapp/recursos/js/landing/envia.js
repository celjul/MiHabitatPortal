$(document).ready(function(){
	$(".btnEnv").click(function(){
        evioMail();
    });
	$(".btnNuev").click(function(){
        nuevoComentario();
    });
});
function evioMail () {
	    var url = contextPath + "/zonaabierta/contacto";
        $("#inpNom").val($("#inpNom").val().replace(/^\s+|\s+$/g, '').replace(/'/g, "´"));
        $('#comen').val().replace(/^\s+|\s+$/g, '').replace(/'/g, "´");
        if ($('#inpNom').val().length < 3) {
            alert("Escriba Su Nombre");
			$("#inpNom").focus();
			return false;
        }
        if (!(/[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/).test($("#inpEma").val())) { //validando campo email
            alert("Escriba Su E-mail. Ejemplo: nombre@servidor.com");
			$("#inpEma").focus();
            return false;
        }
        if ($('#comen').val().length < 5) { 
            alert("Escriba Un Comentario");
		    $("#comen").focus();
            return false;
        }
        //Consulta por ajax
        $("#formContacto").fadeOut(function(){
            limpiaForm();
            $(".contEnviando").fadeIn();
        });
        $.ajax({
            type: "POST",
            url: url,
            data: $("#formContacto").serialize(), // Adjuntar los campos del formulario enviado.
            success: function(data)
            {
                $(".contEnviando").fadeOut(function(){
                    limpiaForm();
                    $(".contResp").fadeIn();
                });
            },
            error: function(data){
                alert("Lo sentimos, no he mos podido enviar tu comentario.");
                $(".contEnviando").fadeOut(function(){
                    limpiaForm();
                    $("#formContacto").fadeIn();
                });
            }
        });
}
function limpiaForm () {
	$("#inpNom").val('');
    $("#inpEma").val('');
    $("#comen").val('');
}
function nuevoComentario () {
	limpiaForm();
	$(".contResp").fadeOut(function(){
		$("#formContacto").fadeIn();
	});
}
