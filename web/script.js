$(document).ready(function(){
       $.base64.utf8encode = true;
       $('.submit').click(function () {
           var encoded = $.base64.btoa($('#encodestring').val());
           allocate(encoded);
           $.ajax({
               url: "/api/compile/"+encoded, 
               async: true,
               cache: true,
               success: function(data, textStatus, jqXHR) {
                var $response = $(data);
                var input = $.base64.atob($response["input"]);
                var output = $.base64.atob($response["output"]);
                success(input,output);
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
function allocate(encoded) {
	
}

/*
 * Replace builder div with compiled code. Identified by encoded id.
 */
function success(encoded,decoded) {
	alert("success: "+encoded+" "+decoded);x
}
