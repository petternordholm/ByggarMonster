$(document).ready(function(){
       $.base64.utf8encode = true;
       $('.submit').click(function () {
           var encoded = $.base64.btoa($('#encodestring').val());
           allocate();
           $.ajax({
               url: "api.py",
               type: "POST",
               data: {"input": encoded},
               async: true,
               cache: true,
               success: function(data, textStatus, jqXHR) {
                var output = data["output"];
                success(output);
               },
               error: function(data, textStatus, jqXHR) {
                alert(data);
               }
           });
       });
});

/*
 * Create builder div with spinner. Using encoded as id.
 */
function allocate() {
 $(".builders").prepend('<div class="builder""><img class="clear" src="spinner.gif"></div>');
}

/*
 * Replace builder div with compiled code. Identified by encoded id.
 */
function success(output) {
 $(".builders img").first().parent().html('<textarea class="output code clear">'+output+'</textarea>');
}
