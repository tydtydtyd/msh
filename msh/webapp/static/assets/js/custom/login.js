/**
 * Created by tyd on 2016/3/4.
 */
function goLogin(form) {
    $.ajax({
        method: 'post',
        url: form.attr('action'),
        data: form.serialize()
    }).success(function (data) {
        console.log(data);
        if (data.success) {
            window.location.href = data.url;
        }else{
            $('#error_message').html(data.message);
        }
    });
}