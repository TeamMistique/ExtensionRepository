$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
});

$('#home-button').on('click', function (e) {
    e.preventDefault();
    $('#main-page').removeClass('hide');  
    $('#one-extension-page').addClass('hide');  
});

var fillPopularList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/popular",
        success: function (data) {
            fillMainPageList($('#inspo-extensions-popular'), data)
        }
    });
};

var fillFeaturedList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/featured",
        success: function (data) {
            fillMainPageList($('#inspo-extensions-featured'), data)
        }
    });
};

var fillNewList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/new",
        success: function (data) {
            fillMainPageList($('#inspo-extensions-new'), data)
        }
    });
};

var fillMainPageList = function (location, data) {
    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="extension" value="' + v.id + '">';
            html += '<div class="extension-image-container">';
            html += '<img class="extension-image" src="' + v.image + '"></div>';
            html += '<div class="extension-title">' + v.name + '</div>';
            html += '<div class="bottom"><div>' + v.owner + '</div>';
            html += '<div><i class="fas fa-download"></i>' + '  ' + v.downloadsCounter + '</div></div></div>';

            location.append(html)
        });
    } else {
        console.log('error');
    }
};

$('#featured-category, #popular-category, #new-category').on('click', '.extension', function(){
    console.log("Extension has been clicked - 1.")
    console.log($(this)+'..................................'+$(this).attr('value'));
    var id = $(this).attr('value');
    getExtensionData(id);
    $('#main-page').addClass('hide');  
    $('#one-extension-page').removeClass('hide');
});

var getExtensionData = function (id) {
    $.ajax({
        type: "GET",
        url: "/api/extensions/"+id,
        success: function (data) {
            fillExtensionPage($('#one-extension-page'), data)
        }
    });
};

var fillExtensionPage = function (location, extension) {
    location.html('');

    if (extension !== '') {
        var html = "";
        html +='<div class="extension-page" value="'>+ extension.id + '">';
        html += '<div class="top"><div class="inner-top"><div id="image-container">';
        html += '<img src="' + extension.image + '"></div>';
        html += '<div class="basic-info vertical">';
        html += '<div id="name" class="title">' + extension.name + '</div>';
        html += '<div id="owner" class="overview">' + extension.owner + '</div>';
        html += '<div id="downloads-number" class="overview"><i class="fas fa-download"></i>' + '  ' + extension.downloadsCounter + '</div>';
        html += '<div id="download-link"><a href="'+ extension.file + '" id="download-button">Download</a></div></div><div class="additional-info vertical"><div id="version">Version<div class="small-padding">' + extension.version + '</div></div>';
        html += '<div id="github" class="vertical"><div><a href="' + extension.link + '" class="caption">GitHub</a></div>';
        html += '<div class="text"><div>Open Issues<div class="small-padding caption">' + extension.issuesCounter + '</div></div></div>';
        html += '<div class="text"><div>Pull Requests<div class="small-padding caption">' + extension.pullRequestsCounter + '</div></div></div>';
        html += '<div class="text"><div>Last commit<div class="small-padding caption">' + extension.lastCommitDate + '</div></div></div></div></div></div></div>';
        html += '<div class="bottom-extension-page"><div class="text">' + extension.description + '</div>';
        html += '<div id="tag-section" class="tag-section"><div class="caption">Tags</div><div id="tag-list">';
        // how to fill tags


        html += '</div></div></div></div></div>';

        location.append(html)

    }
};

