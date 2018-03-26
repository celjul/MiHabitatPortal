$(function() {
	var url = contextPath + "/controller/comprobante/fileupload";
	
	$('#fileupload').fileupload(
		{
			maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(xml)$/i,
			dataType : 'json',
			done : function(e, data) {
//				$("#uploaded-files tr:has(td)").remove();
				console.log(e);
				console.log(data);
	
				$("#uploaded-files").append(
						$('<tr/>').append(
								$('<td/>').text(data.result.name))
								.append(
										$('<td/>').text(
												data.result.size))
								.append(
										$('<td/>').text(
												data.result.type)))// end
																	// $("#uploaded-files").append()
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