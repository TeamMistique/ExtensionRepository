$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
});

var fillPopularList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/popular",
        success: function (data) {
            fillMainPageList($('#popular-list'), data)
        }
    });
};

var fillFeaturedList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/featured",
        success: function (data) {
            fillMainPageList($('#featured-list'), data)
        }
    });
};

var fillNewList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/new",
        success: function (data) {
            fillMainPageList($('#new-list'), data)
        }
    });
};

var fillMainPageList = function (location, data) {
    location.html('');

    if (data !== '') {
        $.each(data, function(k, v){
            ('<div class="extension" value="'+v.id+'">'+v.name+'<div>')
            
            var html = "";
            html += '<div class="extension" value="' + v.id + '">';
            html += '<div class="extension-image-container">';    
            html += '<img class="extension-image" src="' + v.image + '"></div>';
            html += '<div class="extension-title">' + v.name + '</div>';
            html += '<div class="bottom"><div>' + v.owner.userName + '</div>';
            html += '<div><i class="fas fa-download"></i>'+ '  '+ v.downloadsCounter + '</div></div></div>';

            location.append(html)









        });
    }
};
