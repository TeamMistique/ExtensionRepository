var fillUsersTable = function (location, data) {
    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var username = v.username;
            var html = "";
            html += '<tr value="' + v.id + '"><td class="text-center" style="vertical-align: middle;">' + v.id + '</td>';
            html += '<td style="vertical-align: middle;">' + username + '</td>';
            html += '<td style="vertical-align: middle;">';


            ajaxCalls.getUserExtensions(username).done(function (data) {
                $.each(data, function (key, value) {
                    html += '<div>' + value.name + '</div>';
                })
                html += '</td> </tr>';
                location.append(html);
            })
        });

    }
};