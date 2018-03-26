$(function() {
    $('#fileupload').fileupload({
        maxFileSize : 5000000,
        acceptFileTypes : /^image\/(gif|jpe?g|png)$|^application\/(pdf|msword)$|^text\/plain$|^doc$|^docx$/i,
        dataType : 'json',
        done : function(e, data) {
            $("#uploaded-files tr:has(td)").remove();
            $("#uploaded-files").append(
                    $('<tr/>').append(
                            $('<td/>').text(data.result.name)).append(
                            $('<td/>').text(data.result.size)).append(
                            $('<td/>').text(data.result.type)));
        },
        progressall : function(e, data) {
            var progress = parseInt(data.loaded / data.total
                    * 100, 10);
            $('#progress .progress-bar').css('width',
                    progress + '%');
        }
    });
});