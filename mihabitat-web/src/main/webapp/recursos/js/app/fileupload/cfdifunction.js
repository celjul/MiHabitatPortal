$(function() {
	var url = contextPath + "/administrador/facturasporpagar/fileupload";
	
	$('#fileupload').fileupload(
		{
			maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(xml)$/i,
			dataType : 'json',

			done : function(e, data) {
//				$("#uploaded-files tr:has(td)").remove();
				console.log(e);
				console.log(data);
	
				$("#cfdi-files tbody").append(

						$('<tr/>')
							    .append($('<td/>').text(data.files[0].name))
								.append($('<td/>').text(data.result.rfc))
								.append($('<td/>').text(data.result.emisor))
								.append($('<td/>').text(data.result.total))

							    .append($('<td/>').html(
									"<td style='text-align: center;'>" +
									"<div id='lalaladiv'>" +
									"<a class='lalala'>mensaje </a> " +
									"</div>" +

									"<div class='btn-group'>" +
									"<a class='btn btn-default btn-xs' href='javascript:void(0);'><i class='fa fa-cog'></i></a>" +
									"<a class='btn btn-default dropdown-toggle btn-xs' data-toggle='dropdown' href='javascript:void(0);'><span class='caret'></span></a>" +
									"<ul class='dropdown-menu'>" +
									"<li>" +
										"<a href='javascript:void(0);' data-bind='click: $root.show' class='lalala'>" +
										"actualizar" +
										"</a>" +
									"</li>" +
									"</ul>" +
									"</div>" +
									"</td>") )
				)
			},
	
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total
						* 100, 10);
				$('#progress .progress-bar').css('width',
						progress + '%');
			}
	
		}).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');;
});