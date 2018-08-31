$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
});

$('#home-button, #home-button2').on('click', function (e) {
    e.preventDefault();
    $('body > div').addClass('hide');
    $('#main-page').removeClass('hide');
});

$('#search-button').on('click', function (e) {
    e.preventDefault();
    $('body > div').addClass('hide');
    $('#search-page').removeClass('hide');
});

var fillPopularList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/featured",
        success: function (data) {
            fillMainPageList($('#featured-container'), data)
        }
    });
};

var fillFeaturedList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/popular",
        success: function (data) {
            fillMainPageList($('#popular-container'), data)
        }
    });
};

var fillNewList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/new",
        success: function (data) {
            fillMainPageList($('#new-container'), data)
        }
    });
};


var fillAllList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/published",
        success: function (data) {
            fillMainPageList($('#search-container'), data)
        }
    });
};

var fillMainPageList = function (location, data) {
       
    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading">' + v.name + '</div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'

            location.append(html)
        });
    } else {
        console.log('error');
    }
};

$('#featured-container, #popular-container, #new-container, #search-container').on('click', '.col-md-2', function () {
    console.log("Extension has been clicked - 1.")
    console.log($(this) + '..................................' + $(this).attr('value'));
    var id = $(this).attr('value');
    getExtensionData(id);
    $('body > div').addClass('hide');
    $('#one-extension-page').removeClass('hide');
});


var getExtensionData = function (id) {
    $.ajax({
        type: "GET",
        url: "/api/extensions/" + id,
        success: function (data) {
            fillExtensionPage($('#one-extension-page'), data)
        }
    });
};

var fillExtensionPage = function (location, extension) {
    location.html('');

    if (extension !== '') {
        var html = "";
        html += '<div class="container justify-content-center" value="' + extension.id + '">';
        html += '<div class="row justify-content-center "><div class="col-md-7"><h1 class="page-header">' + extension.name + '</h1></div></div>';
        html += '<div class="row"><div class="col-md-3"><img class="img-responsive" src="' + extension.image + '"></div>';
        html += '<div class="col-md-2 text-left"><h3>Information</h3><div>' + extension.owner + '</div>';
        html += '<div><i class="fas fa-download"> </i> ' + extension.downloadsCounter + '</div>';
        html += '<div>Version' + extension.version + '</div>';
        html += '<div id="download"><a id="download-button" class="row btn btn-success" type="button" href="' + extension.file + '">Download</a></div></div>';
        html += '<div class="col-md-2 text-left"><h3 id="github"><a href="' + extension.link + '"><i class="fab fa-github-alt">' + '  ' + '</i>GitHub</a></h3>';
        html += '<div>Open Issues  ' + extension.issuesCounter + '</div>';
        html += '<div>Pull Requests  ' + extension.pullRequestsCounter + '</div>';
        html += '<div>Last commit ' + extension.lastCommitDate + '</div></div></div><div style="margin-bottom: 1%;"></div>';
        html += '<div class="row"><div class="col-md-7"><p>' + extension.description + '</p></div></div>';

        if (extension.tags.length > 0) {
            html += '<div class="col-md-6"><h3 class="tags">Tags</h3><div id="tag-section" class="tag-container">';
            $.each(extension.tags, function (k, v) {
                var tagsHtml = "";
                tagsHtml += '<div class="tag border">' + v.tagName + '</div>';
                html += tagsHtml;
            });
        } else {
            console.log('error');
        }
        html += '</div></div></div></div></div>';

        location.append(html)

    };
}