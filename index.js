$('#save-URL').click(function(){
    var data = 
    {id: $('#input-35').val(), 
    url: $('#input-36').val()
    };
    
    $.ajax({
        url: 'http://localhost:8080',
        type:"POST",
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
 
      })
});

/*
$("button").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/pages/test/",
        data: { 
            id: $(this).val(), // < note use of 'this' here
            access_token: $("#access_token").val() 
        },
        success: function(result) {
            alert('ok');
        },
        error: function(result) {
            alert('error');
        }
    });
});



$(document).on('click', '#tasks li .delete-item', function(){
    var id= $(this).attr('data-id');
    $(this).parent().remove();
    $.ajax({
        url: 'http://localhost:8080/links/'+id,
        type:"DELETE",
        contentType:"application/json; charset=utf-8",
        dataType:"json",
      })
});
*/