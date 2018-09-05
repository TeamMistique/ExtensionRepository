var getExtensionData = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/id",
        success: function (extension) {
            fillExtensionPage($('.extension-page'), extension) //make it with id
        }
    });
};

var fillExtensionPage = function (location, extension) {
    location.html('');

    if (data !== '') {
        var html = "";
        html += '<div class="extension" value="' + extension.id + '">';
        html += '<div class="extension-image-container">';
        html += '<img class="extension-image" src="' + extension.image + '"></div>';
        html += '<div class="extension-title">' + extension.name + '</div>';
        html += '<div class="bottom"><div>' + extension.owner.userName + '</div>';
        html += '<div><i class="fas fa-download"></i>' + '  ' + extension.downloadsCounter + '</div></div></div>';

        location.append(html)

    }
};