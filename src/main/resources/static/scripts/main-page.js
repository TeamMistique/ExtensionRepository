$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
    fillAllList();
    ResCarouselOnInit();
});

$('#home-button').on('click', function (e) {
    e.preventDefault();
    $('.page').addClass('hide');
    $('#main-page').removeClass('hide');
    $('#extension-modal').removeClass('hide');
    $('#edit-extension-modal').removeClass('hide');
});

function goHome() {
    $('.page').addClass('hide');
    $('#main-page').removeClass('hide');
}

$('#search-button').on('click', function (e) {
    e.preventDefault();
    $('.page').addClass('hide');
    $('#search-page').removeClass('hide');
    $('#extension-modal').removeClass('hide');
    $('#edit-extension-modal').removeClass('hide');
});

$('#user-dropdown').on('click', '#go-to-mine', function (e) {
    getMyExtensions();
    $('.page').addClass('hide');
    $('#my-extensions-page').removeClass('hide');
    $('#user-dropdown').toggle();
    e.preventDefault();
});

$('#user-dropdown').on('click', '#login-button', function (e) {
    $('.page').addClass('hide');
    $('#login-page').removeClass('hide');
    $('#user-dropdown').toggle();
    e.preventDefault();
});

$('#user-dropdown').on('click', '#go-to-admin-panel', function (e) {
    ajaxCalls.getUnpublished().done(function (data) {
        adminFillWithEditable($('#unpublished-container'), data);
    });

    ajaxCalls.getAllUsers().done(function (data) {
        fillUsersTable($('#user-info'), data);
    });

    $('.page').addClass('hide');
    $('#admin-page').removeClass('hide');
    $('#user-dropdown').toggle();
    ajaxCalls.getSyncPerionInMillis().done(function(data){
        var inMinutes = data/60000;
        $('#real-interval').html(inMinutes);
        $('#sync-interval').attr('placeholder', inMinutes);
    });
    e.preventDefault();
});

$('#my-account-button').on('click', function (e) {
    var token = getJwtToken();
    var html = '';
    if (token) {
        html += "<li><a id=" + "go-to-mine" + ">My extensions</a></li>";
        if (isAdmin()) {
            html += "<li><a id=" + "go-to-admin-panel" + ">Admin Panel</a></li>";
        }
        html += "<li><a id=" + "log-out-button" + ">Log out</a></li>";
    } else {
        html += "<li><a id=" + "login-button" + ">Log in</a></li>";
    }

    $('#user-dropdown').html("");
    $('#user-dropdown').append(html);
    $('#user-dropdown').toggle();

    e.preventDefault();
});

var fillPopularList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/popular",
        success: function (data) {
            if (isAdmin()) {
                adminFillWithEditable($('#popular-container'), data);
            } else {
                fillMainPageList($('#popular-container'), data);
            }
        }
    });
};

var fillFeaturedList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/featured",
        success: function (data) {
            if (isAdmin()) {
                adminFillWithEditable($('#featured-container'), data);
            } else {
                fillMainPageList($('#featured-container'), data);
            }
        }
    });
};

var fillNewList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/new",
        success: function (data) {
            if (isAdmin()) {
                adminFillWithEditable($('#new-container'), data);
            } else {
                fillMainPageList($('#new-container'), data);
            }
        }
    });
};

var fillMainPageList = function (location, data) {

    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2 item" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
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

var fillAllList = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/published",
        success: function (data) {
            if (isAdmin()) {
                adminFillSearch($('#search-container'), data);
            } else {
                fillSearchPageList($('#search-container'), data);
            }
        }
    });
};

var fillSearchPageList = function (location, data) {

    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading extension-title">' + v.name + '</div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'

            location.append(html)
        });
    } else {
        console.log('error');
    }
};

$('#featured-container, #popular-container, #new-container, #search-container, #unpublished-container, #my-extensions-container').on('click', '.col-md-2', function () {
    console.log("Extension has been clicked - 1.")
    console.log($(this) + '..................................' + $(this).attr('value'));
    var id = $(this).attr('value');
    getExtensionById(id).done(function (data) {
        fillExtensionPage($('#one-extension-page'), data);
    })
});

var fillExtensionPage = function (location, extension) {
    location.html('');

    if (extension !== '') {
        var html = "";

        html += '<div  class="modal-header" value="' + extension.id + '">';
        html += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
        html += '<h2 class="modal-title">' + extension.name + '</h2></div>'
        html += '<div class="modal-body"><div class="container-fluid"><div class="row"><div class="col-xs-1"></div>'
        html += '<div class="col-md-4"><img class="img-responsive" src="' + extension.image + '"></div>';
        html += '<div class="extension-info col-md-2 ml-auto text-left"><h3 class="text-bold">Information</h3>';
        html += '<div>' + extension.owner + '</div>';
        html += '<div><div class="fas fa-download"> ' + extension.downloadsCounter + '</div></div>';
        html += '<div>Version <div>' + extension.version + '</div></div></div>';
        html += '<div class="extension-info col-md-5 ml-auto text-left"><h3 id="github">';
        html += '<a href="' + extension.link + '" target="_blank"><i class="fab fa-github-alt"> </i> GitHub</a></h3>';
        html += '<div>Open Issues: <div>' + extension.issuesCounter + '</div></div>';
        html += '<div>Pull Requests: <div>' + extension.pullRequestsCounter + '</div></div>';
        if(extension.lastCommitDate === null){
            html += '<div>Last commit: <div class="date">n/a</div></div></div></div><div style="margin-bottom: 3%;"></div><div class="row">';
        } else{
            html += '<div>Last commit: <div class="date">' + moment(extension.lastCommitDate).format('MM/DD/YYYY') + '</div></div></div></div><div style="margin-bottom: 3%;"></div><div class="row">';
        }

        html += '<div class="col-xs-1"></div><div id="this-extension-description" class="col-md-10"><p>' + extension.description + '</p></div></div>';

        if (extension.tags.length > 0) {
            html += '<div class="row"><div class="col-xs-1"></div><div class="col-md-7"><h3 class="tags">Tags</h3><div class="tag-container">';

            $.each(extension.tags, function (k, v) {
                var tagsHtml = "";
                tagsHtml += '<div>' + v.tagName + '</div>';
                html += tagsHtml;
            });

            html += '</div></div></div>';
        }

        if (isAdmin()) {
            html += '<div class="row"><div class="col-xs-1"></div><div class="col-md-7"><h3 style="display: inline;margin-right: 10px;">Sync Info</h3>';
            html += '<a id = "sync-button" class="btn btn-success btn-sm"> Sync now </a>';
            html += '<div class="sync-container">';
            if(extension.lastSuccessfulSync === null){
                html += '<div>Last Successful Sync: <i id="lss">n/a</i></div>';
            } else {
                html += '<div>Last Successful Sync: <i id="lss">' + moment(extension.lastSuccessfulSync).format('LLLL') + '</i></div>';
            }
            if(extension.lastFailedSync === null){
                html += '<div>Last Failed Sync: <i id="lfs">n/a</i></div>';
            } else{
                html += '<div>Last Failed Sync: <i id="lfs">' + moment(extension.lastFailedSync).format('LLLL') + '</i></div>';
            }
            if(extension.failedSyncDetails === null){
                html += '<div>Last Failed Sync Details: <i id="lfsd">n/a</i></div>';
            } else {
                html += '<div>Last Failed Sync Details: <i id="lfsd">' + extension.failedSyncDetails + '</i></div>';
            }

            html += '</div></div></div>';
        }

        html += '</div></div><div class="modal-footer"><div class="col-md-9"></div><div class="col-md-2">';
        html += '<button id="download-button" type="button" class="btn btn-success btn-lg" value="' + extension.file + '">Download</button></div></div></div></div></div>';

        location.append(html)

    }
};

$('#one-extension-page').on('click', '#sync-button', function () {
    console.log('sync clicked');
    var id = $('#one-extension-page .modal-header').attr('value');
    console.log('id=' + id);
    ajaxCalls.triggerOneSync(id).done(function (extension) {
        $('#lss').html(moment(extension.lastSuccessfulSync).format('LLLL'));
        $('#lfs').html(moment(extension.lastFailedSync).format('LLLL'));
        $('#lfsd').html(extension.failedSyncDetails);
    })
});

function getMyExtensions() {
    var token = getJwtToken();
    if (token) {
        return $.ajax({
            type: "GET",
            url: "/api/extensions/mine",
            headers: createAuthorizationTokenHeader(),
            success: function (data) {
                fillWithEditableExtensions(data);
            }
        });
    }
}

$(document).on('change', '.btn-file :file', function () {
    var input = $(this);
    var label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [label]);
});

$('.btn-file :file').on('fileselect', function (event, label) {

    var input = $(this).parents('.input-group').find(':text'),
        log = label;

    if (input.length) {
        input.attr('value', log);
    } else {
        if (log) alert(log);
    }
});

$.fn.extend({
    clearFiles: function () {
        $(this).each(function () {
            var isIE = (window.navigator.userAgent.indexOf("MSIE ") > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./));
            if ($(this).prop("type") == 'file') {
                if (isIE == true) {
                    $(this).replaceWith($(this).val('').clone(true));
                } else {
                    $(this).val("");
                }
            }
        });
        return this;
    }
});

$('#open-new-extension-modal').on('click', function () {
    $('#new-extension-modal').modal('show');
});

function uploadExtension(dto) {
    return $.ajax({
        type: "POST",
        url: "/api/extensions/add",
        data: JSON.stringify(dto),
        contentType: 'application/json',
        dataType: 'json',
        headers: createAuthorizationTokenHeader(),
        success: function (response) {
            console.log(response);
            getMyExtensions();
            $('#new-extension-modal').modal('toggle');
        }
    });
}

var refresh = function () {
    window.location.reload(true);
}

$('#add-extension-button').on('click', function () {

    var dto = {
        'name': $('#extension-name').val(),
        'description': $('#extension-description').val(),
        'link': $('#github-link').val(),
        'tagNames': $('#extension-tags').val().match(/\w+/g)
    };

    debugger;
    $('#file-upload-form').submit(function (event) {
        var formElement = this;
        var formData = new FormData(formElement);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/files/uploadFile",
            data: formData,
            processData: false,
            contentType: false,
            headers: createAuthorizationTokenHeader(),
            success: function (data) {
                dto.file = data.downloadURI;
                $('#image-upload-form').trigger('submit');
            }, 
            error: function(){
                alert("There was an error with the file upload");
                refresh();
            }
        });

        event.preventDefault();
    });

    $('#image-upload-form').submit(function (event) {
        var formElement = this;
        var formData = new FormData(formElement);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/files/uploadImage",
            data: formData,
            processData: false,
            contentType: false,
            headers: createAuthorizationTokenHeader(),
            success: function (data) {
                dto.image = data.downloadURI;
                uploadExtension(dto).done(function () {
                    refresh();
                });
            },
            error: function(){
                alert("There was an error with the image upload");
                refresh();
            }
        });

        event.preventDefault();
    });

    $('#file-upload-form').trigger('submit');
});

var fillWithEditableExtensions = function (data) {
    var publishedLocation = $('#my-published-container');
    var unpublshedLocation = $('#my-unpublished-container');
    publishedLocation.html('');
    unpublshedLocation.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2 item" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading" style= "display: flex; justify-content: space-between"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'
            if (v.publishedDate !== null) {
                publishedLocation.append(html);
            } else {
                unpublshedLocation.append(html);
            }
        });
    }
};

var adminFillWithEditable = function (location, data) {
    location.html('');
    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2 item" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading" style= "display: flex; justify-content: space-between"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'
            location.append(html);
        });
    }
};

var adminFillSearch = function (location, data) {

    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading" style= "display: flex; justify-content: space-between"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'

            location.append(html)
        });
    } else {
        console.log('error');
    }
};

$('#delete-extension-button').on('click', function (event) {
    var id = $('#edit-extension-modal').val();

    $.ajax({
        type: "POST",
        url: "/api/extensions/delete?id=" + id,
        headers: createAuthorizationTokenHeader(),
        success: function () {
            refresh();
        }
    });

    $('#edit-extension-modal').modal('toggle');
    event.preventDefault();
});

$('#edit-extension-button').on('click', function (event) {
    var dto = {
        "id": $('#edit-extension-modal').val(),
        "name": $('#edit-extension-name').val(),
        "description": $('#edit-extension-description').val(),
        "link": $('#edit-github-link').val(),
        "tagNames": $('#edit-extension-tags').val().match(/\w+/g)
    };

    if ($('#edit-image-name-text').val() !== helpers.getNameFromFileLink($('#edit-image-name-text').attr('oldValue'))) {
        $('#edit-image-upload-form').submit(function (event) {
            var formElement = this;
            var formData = new FormData(formElement);

            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/api/files/uploadImage",
                data: formData,
                processData: false,
                contentType: false,
                headers: createAuthorizationTokenHeader(),
                success: function (data) {
                    dto.image = data.downloadURI;
                    if (typeof dto.file !== 'undefined') {
                        triggerEdit(dto);
                    }
                }
            });

            event.preventDefault();
        });

        $('#edit-image-upload-form').submit();
    } else {
        dto.image = $('#edit-image-name-text').attr('oldValue');
        if (typeof dto.file !== 'undefined') {
            triggerEdit(dto);
        }
    }

    if ($('#edit-file-name-text').val() !== helpers.getNameFromFileLink($('#edit-file-name-text').attr('oldValue'))) {
        $('#edit-file-upload-form').submit(function (event) {
            var formElement = this;
            var formData = new FormData(formElement);

            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/api/files/uploadFile",
                data: formData,
                processData: false,
                contentType: false,
                headers: createAuthorizationTokenHeader(),
                success: function (data) {
                    dto.file = data.downloadURI;
                    if (typeof dto.image !== 'undefined') {
                        triggerEdit(dto);
                    }
                }
            });

            event.preventDefault();
        });

        $('#edit-file-upload-form').submit();
    } else {
        dto.file = $('#edit-file-name-text').attr('oldValue');
        if (typeof dto.image !== 'undefined') {
            triggerEdit(dto);
        }
    }

    event.preventDefault();
});


$("#my-extensions-container, #popular-container, #featured-container, #new-container, #search-container, #unpublished-container").on('click', '.click-to-edit', function (e) {
    e.stopPropagation();
    e.preventDefault();
    var $extensionToEdit = $(this).closest('.col-md-2');
    var id = $extensionToEdit.attr('value');
    $('#edit-extension-modal').val(id);

    getExtensionById(id).done(function (extension) {
        helpers.fillEditMenu(extension);
        $('#edit-extension-modal').modal('show');
    });
});

function triggerEdit(dto) {
    editExtension(dto).done(function () {
        refresh();
    });
}

function getExtensionById(id) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/" + id,
        headers: createAuthorizationTokenHeader()
    })
}

function editExtension(data) {
    return $.ajax({
        type: "POST",
        url: "/api/extensions/edit",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json',
        cache: false,
        headers: createAuthorizationTokenHeader(),
        success: function (response) {
            console.log(response);
        }
    });
}

function downloadURI(uri) {
    var link = document.createElement("a");
    link.download = helpers.getNameFromFileLink(uri);
    link.href = uri;
    link.click();
}

$('#one-extension-page').on('click', '#download-button', function (event) {
    debugger;
    var uri = $(this).attr('value');
    downloadURI(uri);
    event.preventDefault();
});



var helpers = {
    fillEditMenu: function (extension) {
        $('#edit-extension-modal').val(extension.id);
        $('#edit-extension-name').val(extension.name);
        $('#edit-extension-description').val(extension.description);
        $('#edit-github-link').val(extension.link);

        var tags = "";
        $.each(extension.tags, function (k, v) {
            tags += v.tagName + ", "
        });
        if (tags.length > 0) tags = tags.slice(0, -2);

        $('#edit-extension-tags').val(tags);

        $('#edit-image-name-text').attr('value', this.getNameFromFileLink(extension.image));
        $('#edit-image-name-text').attr('oldValue', extension.image);
        $('#edit-file-name-text').attr('value', this.getNameFromFileLink(extension.file));
        $('#edit-file-name-text').attr('oldValue', extension.file);

        //add buttons for publish and feature
        if (isAdmin()) {
            $('.admin-button').removeClass('hide');
            var publishText = 'Publish';
            var featureText = 'Feature';
            if (extension.publishedDate !== null) publishText = 'Unpublish';
            if (extension.featuredDate !== null) featureText = 'Unfeature';
            $('#publish-extension-button').html(publishText);
            $('#feature-extension-button').html(featureText);
        }
    },

    getNameFromFileLink: function (file) {
        var index = file.indexOf("downloadFile/") + "downloadFile/".length;
        return file.substring(index);
    }
};

$('#edit-buttons-container').on('click', '#publish-extension-button', function (event) {
    debugger;
    var id = $('#edit-extension-modal').val();
    ajaxCalls.publishExtension(id).done(function () {
        refresh();
    });
    event.preventDefault();
});

$('#edit-buttons-container').on('click', '#feature-extension-button', function (event) {
    debugger;
    var id = $('#edit-extension-modal').val();
    ajaxCalls.changeFeatureStatus(id).done(function (data) {
        if(data == null) {
            var published = $('#publish-extension-button').html();
            if(published === "Publish"){
                alert("You can not feature an unpublished extension.");
            } else {
                alert("Featured list seems to be full. Unfeature another extension to be able to feature this one.")
            }
        } else {
            refresh();
        }
    });
    event.preventDefault();
});

// ------------------  Carousel ---------------------

function ResCarouselOnInit() {
    ResCarouselSize();
    $(document).on('click', '.leftLst, .rightLst', function () {
        ResCarousel(this);
    });
    $(document).on("mouseenter", ".ResHover", function () {
        $(this).addClass("ResHovered");
    });

    $(document).on("mouseleave", ".ResHover", function () {
        $(this).removeClass("ResHovered");
    });
}

$(window).resize(function () {
    var r = new Date();
    setTimeout(function () {
        ResCarouselResize(r);
    }, 200);
});

function ResCarouselSlide(e) {
    var thiss = $(e).find(".rightLst");
    var dataInterval = $(e).attr('data-interval');
    !isNaN(dataInterval) && $(e).addClass("ResHover") && setInterval(function () {
        !(thiss.parent().hasClass("ResHovered")) && ResCarousel(thiss);
    }, +(dataInterval));
}

function ResCarouselResize() {
    function myfunction() {
        $('.resCarousel').each(function () {
            var divValue = $(this).attr('data-value');
            var itemWidth = $(this).find('.item').width();
            $(this).find(".resCarousel-inner").scrollLeft(divValue * itemWidth);
        });
    }
    myfunction();
}

//this function define the size of the items
function ResCarouselSize() {
    var t0 = performance.now();

    $('.resCarousel').each(function (index) {
        var itemsSplit = $(this).attr("data-items").split('-');
        $(this).addClass("ResSlid" + index);

        for (var i = 0; i < 4; i++) {
            if (i == 0) {
                var styleCollector0 = ".ResSlid" + index + " .item {width: " + 100 / itemsSplit[i] + "%}";
            } else if (i == 1) {
                var styleCollector1 = ".ResSlid" + index + " .item {width: " + 100 / itemsSplit[i] + "%}";
            } else if (i == 2) {
                var styleCollector2 = ".ResSlid" + index + " .item {width: " + 100 / itemsSplit[i] + "%}";
            } else if (i == 3) {
                var styleCollector3 = ".ResSlid" + index + " .item {width: " + 100 / itemsSplit[i] + "%}";
            }
        }

        $(this).attr("data-value", "0");

        var styleCollector = "@media (max-width:767px){" + styleCollector0 + "}" +
            "@media (min-width:768px){" + styleCollector1 + "}" +
            "@media (min-width:992px){" + styleCollector2 + "}" +
            "@media (min-width:1200px){" + styleCollector3 + "}";

        $(this).find("style").remove();
        $(this).append("<style>" + styleCollector + "</style>");
        ResCarouselSlide(this);
    });
    var t1 = performance.now();
}

//this function used to move the items
function ResCarousel(Btn) {

    var parent = $(Btn).parent(),
        slide = +parent.attr("data-slide"),
        itemsDiv = parent.find('.resCarousel-inner'),
        itemSpeed = +parent.attr("data-speed"),
        itemLoad = +parent.attr("data-load"),

        translateXval = '',
        currentSlide = "",
        itemLenght = itemsDiv.find(".item").length,
        itemWidth = itemsDiv.find('.item').outerWidth(),
        dataItm = +Math.round(itemsDiv.outerWidth() / itemWidth),
        cond = $(Btn).hasClass("leftLst"),
        divValue = Math.round(itemsDiv.scrollLeft() / itemWidth);

    itemSpeed = !isNaN(itemSpeed) ? itemSpeed : 400;
    slide = slide < dataItm ? slide : dataItm;

    if (cond) {
        currentSlide = divValue - slide;
        translateXval = currentSlide * itemWidth;
        var MoveSlide = currentSlide + slide;

        if (divValue == 0) {
            currentSlide = itemLenght - slide;
            translateXval = currentSlide * itemWidth;
            currentSlide = itemLenght - dataItm;
            itemSpeed = 400;

        } else if (slide >= MoveSlide) {
            currentSlide = translateXval = 0;
        }
    } else {
        currentSlide = divValue + slide;
        translateXval = currentSlide * itemWidth;
        var MoveSlide = currentSlide + slide;

        if (divValue + dataItm == itemLenght) {
            currentSlide = translateXval = 0;
            itemSpeed = 400;
        } else if (itemLenght <= (MoveSlide - slide + dataItm)) {
            currentSlide = itemLenght - slide;
            translateXval = currentSlide * itemWidth;
            currentSlide = itemLenght - dataItm;
        }
    }

    parent.attr("data-animator") == "lazy" && resCarouselAnimator(itemsDiv, cond ? 0 : 1, currentSlide + 1, currentSlide + dataItm, itemSpeed, (slide * itemWidth));

    if (!isNaN(itemLoad)) {
        itemLoad = itemLoad >= slide ? itemLoad : slide;
        (itemLenght - itemLoad) <= (currentSlide + dataItm) && ResCarouselLoad1(itemsDiv);
    }
    itemsDiv.animate({
        scrollLeft: translateXval
    }, itemSpeed);
    parent.attr("data-value", currentSlide);
}

function ResCarouselLoad1(e) {
    $("#" + e.attr("id")).trigger("ResCarouselLoad");
}

function resCarouselAnimator(parent, direction, start, end, speed, length) {
    var val = 5;
    if (direction == 0) {
        for (var i = start - 1; i < end + 1; i++) {
            val = val * 2;
        }
        val = -val;
    }

    for (var i = start - 1; i < end; i++) {
        val = direction == 0 ? val / 2 : val * 2;
        parent.find(".item").eq(i).css("transform", "translateX(" + val + "px)");
    }
    setTimeout(function () {
        parent.find(".item").attr("style", "");
    }, speed - 70);
}

// ---------- search filter -------------
function filterByName(name) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/filter?name=" + name,
    });
};

function sortByDownloads(name) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByNumberOfDownloads?name=" + name,
    });
};

function sortByLastCommit(name) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByLastCommit?name=" + name,
    });
};

function sortByUpload(name) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByUpload?name=" + name,
    });
};

$('#search-magnifier').on('click', function (e) {
    var word = $('#search-param').val();
    console.log(word);
    filterByName(word).done(function (data) {
        if (isAdmin()) {
            adminFillSearch($('#search-container'), data);
        } else {
            fillSearchPageList($('#search-container'), data);
        }
        $('#search-concept').html('Order by');
    })
    e.preventDefault();
});

$('#sort-by-downloads').on('click', function (event) {
    var $this = $(this);
    var name = $('#search-param').val();
    sortByDownloads(name).done(function (data) {
        if (isAdmin()) {
            adminFillSearch($('#search-container'), data);
        } else {
            fillSearchPageList($('#search-container'), data);
        }
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

$('#sort-by-upload').on('click', function (event) {
    var $this = $(this);
    var name = $('#search-param').val();
    sortByUpload(name).done(function (data) {
        if (isAdmin()) {
            adminFillSearch($('#search-container'), data);
        } else {
            fillSearchPageList($('#search-container'), data);
        }
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

$('#sort-by-last-commit').on('click', function (event) {
    var $this = $(this);
    var name = $('#search-param').val();
    sortByLastCommit(name).done(function (data) {
        if (isAdmin()) {
            adminFillSearch($('#search-container'), data);
        } else {
            fillSearchPageList($('#search-container'), data);
        }
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

//  ------------ admin page -----------------

$(document).ready(function () {
    $('.filterable .btn-filter').click(function () {
        var $panel = $(this).parents('.filterable'),
            $filters = $panel.find('.filters input'),
            $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });

    $('.filterable .filters input').keyup(function (e) {
        /* Ignore tab key */
        var code = e.keyCode || e.which;
        if (code == '9') return;
        /* Useful DOM data and selectors */
        var $input = $(this),
            inputContent = $input.val().toLowerCase(),
            $panel = $input.parents('.filterable'),
            column = $panel.find('.filters th').index($input.parents('th')),
            $table = $panel.find('.table'),
            $rows = $table.find('tbody tr');
        /* Dirtiest filter function ever ;) */
        var $filteredRows = $rows.filter(function () {
            var value = $(this).find('td').eq(column).text().toLowerCase();
            return value.indexOf(inputContent) === -1;
        });
        /* Clean previous no-result if exist */
        $table.find('tbody .no-result').remove();
        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
        $rows.show();
        $filteredRows.hide();
        /* Prepend no-result row if all rows are filtered */
        if ($filteredRows.length === $rows.length) {
            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="' + $table.find('.filters th').length + '"><b>No result found</b></td></tr>'));
        }
    });
});

var fillUsersTable = function (location, data) {
    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            console.log(v);
            var username = v.username;
            var html = "";
            html += '<tr value="' + v.id + '"><td class="text-center" style="vertical-align: middle;">' + v.id + '</td>';
            html += '<td class="username" style="vertical-align: middle;">' + username + '</td>';
            html += '<td style="vertical-align: middle;">';

            ajaxCalls.getUserExtensions(username).done(function (data) {
                $.each(data, function (key, value) {
                    html += '<div>' + value.name + '</div>';
                })
                html += '</td><td class="text-center" style="vertical-align: middle;">';
                if (!isUserAdmin(v)) {
                    if (v.enabled == true) {
                        html += '<a href="#" class="btn btn-danger btn-sm disable-button"><span class="glyphicon glyphicon-ban-circle"></span> Disable</a>';
                        html += '<a class="btn btn-success btn-sm hide enable-button" href="#"><span class="glyphicon glyphicon-ok-circle"></span> Enable</a></td></tr>';
                        console.log("test if")
                        location.append(html);
                    } else {
                        html += '<a href="#" class="btn btn-danger hide btn-sm disable-button"><span class="glyphicon glyphicon-ban-circle"></span> Disable</a>';
                        html += '<a class="btn btn-success btn-sm  enable-button" href="#"><span class="glyphicon glyphicon-ok-circle"></span> Enable</a></td></tr>';
                        location.append(html);
                    }
                } else {

                    html += '<div id="" class="btn btn-primary btn-sm fake-button"><span class="glyphicon glyphicon-ok-circle"></span> Admin</div></td></tr>';
                    location.append(html);
                }
            })
        });
    } else {
        console.log('error')
    }
};

$('#user-container').on('click', '.disable-button, .enable-button', function (e) {
    var thisButton = $(this);
    var otherButton = $(this).siblings('a');
    var userToBan = $(this).parent().siblings('.username');
    var username = userToBan.html();
    ajaxCalls.changeUserEnabled(username).done(function () {
        thisButton.addClass('hide');
        otherButton.removeClass('hide');
    });
    e.preventDefault();
});

$('#edit-time').on('click', function (e) {
    $(this).addClass('hide');
    $('#real-interval').addClass('hide');
    $('#sync-interval').removeClass('hide');
    $('#save-edit-time').removeClass('hide');
});

$('#save-edit-time').on('click', function (e) {
    $(this).addClass('hide');
    debugger;
    var period = $('#sync-interval').val();
    console.log(period);
    if (period == "") {
        period = 60;
    }
    console.log(period);
    ajaxCalls.changeSyncPeriod(period).done(function () {
        $('#real-interval').html(period);
        $('#real-interval').removeClass('hide');
        $('#edit-time').removeClass('hide');
        $('#sync-interval').addClass('hide');
    });
});


$('#sync-now-button').on('click', function (e) {
    ajaxCalls.triggerSyncAll().done(function () {
        alert("Sync done");
    });

});