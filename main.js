$('#save-task').click(function(){
    var data = 
    {content: $('#task-title').val(), 
    done: false};
    
    $.ajax({
        url: 'http://localhost:8080/todo',
        type:"POST",
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function(){
            loadData();
        }
      })
});

$(document).on('click', '#tasks li .delete-item', function(){
    var id= $(this).attr('data-id');
    $(this).parent().remove();
    $.ajax({
        url: 'http://localhost:8080/todo/'+id,
        type:"DELETE",
        contentType:"application/json; charset=utf-8",
        dataType:"json",
      })
});


$(document).ready(function(){
    loadData();
});

function loadData() {
    $.getJSON('http://localhost:8080/todo/all', function(data){
       $('#tasks').html("");
        for (var index = 0; index < data.length; index++) {
            const element = data[index];
            var $el = $('<li>');
            if (element.done == true){
                $el.addClass('line-through');
            }
            // console.log(element.id);
    $el.html('<span>' + element.content + '<span>');
    var $delete = $('<button>');
    $delete.addClass('delete-item');
    $delete.text('Dzest');
    $delete.attr('data-id', element.id);
    $('#task-title').val('');
    $el.append($delete);
    $('#tasks').append($el);
        }
    });
}

$(document).on('click', '#tasks li span', function(){
    var $parent = $(this).parent();
    if($parent.hasClass('line-through')) {
        $parent.removeClass('line-through');
    } else {
        $parent.parent().addClass('line-through');
    }

    var id = $parent.parent().find('button').attr('data-id');
    var data = 
    {content: $parent.parent().find('span').text(), 
    done: $(this).hasClass('line-through')
};
    $.ajax({
        url: 'http://localhost:8080/todo/'+id,
        type:"PATCH",
        data: JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function(){
            loadData();
        }
      })
//    $.post('http://localhost:8080/todo/all', data, function(){
//        loadData();
//    });
});



// $('#save-task').click(function(){
// 
//     var $el = $('<li>');
//     $el.text($('#task-title').val());
//     var $delete = $('<button>');
//     $delete.addClass('delete-item');
//     $delete.text('Dzest');
//     $('#task-title').val('');
//     $el.append($delete);
//     $('#tasks').append($el);
// });
// $(document).on('click', '#tasks li .delete-item', function(){
//     $(this).parent().remove();
// });