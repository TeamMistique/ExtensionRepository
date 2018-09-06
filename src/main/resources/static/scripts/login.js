function isAdmin() {
    if(justGetJwtToken() && ajaxisAdmin()) return true;
    else return false;
}

$('#login-form-link').click(function (e) {
    $("#login-form").delay(100).fadeIn(100);
    $("#register-form").fadeOut(100);
    $('#register-form-link').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
});

$('#register-form-link').click(function (e) {
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
                    admin = ajaxisAdmin();
                    if (!isAdmin()) {
                        $('.admin-button').hide();
                    } else {
                        $('.admin-button').show();
                    }
                    goHome();
                }
            });
        }
    });
});

$('#user-dropdown').on('click', '#log-out-button', function (e) {
    removeJwtToken();
    admin = false;
    refresh();
    e.preventDefault();
});