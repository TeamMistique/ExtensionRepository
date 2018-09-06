function isAdmin() {
    if (justGetJwtToken() && ajaxisAdmin()) return true;
    else return false;
}

$('#login-form-link').click(function (e) {
    $('.login-error').addClass('hide');
    $("#login-form").delay(100).fadeIn(100);
    $("#register-form").fadeOut(100);
    $('#register-form-link').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
});

$('#register-form-link').click(function (e) {
    $('.login-error').addClass('hide');
    $("#register-form").delay(100).fadeIn(100);
    $("#login-form").fadeOut(100);
    $('#login-form-link').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
});

$('#login-form').submit(function (e) {
    e.preventDefault();

    var data = $(this).serialize();
    var url = '/api/login';

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (data) {
            setJwtToken(data.token);
            refresh()
        },
        error: function (xhr, status, error) {
            $('.login-error').addClass('hide');
            var err = JSON.parse(xhr.responseText);
            console.log(err.message);
            if(err.message == "User is disabled") $('#disabled-user').removeClass('hide');
            else $('#wrong-pass').removeClass('hide');
        }
    });
});

$('#register-form').submit(function (e) {
    e.preventDefault();

    var data = $(this).serialize();
    var url = '/api/signup';

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function () {
            $.ajax({
                type: "POST",
                url: '/api/login',
                data: data,
                success: function (data) {
                    setJwtToken(data.token);
                    refresh();
                },
                error: function () {
                    $('#existing-user').removeClass('hide');
                }
            });
        }
    });
});

$('#user-dropdown').on('click', '#log-out-button', function (e) {
    removeJwtToken();
    refresh();
    e.preventDefault();
});