$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
    fillAllList();
    ResCarouselOnInit();
});

$('#home-button, #home-button2').on('click', function (e) {
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

$('#user-dropdown').on('click', '#log-out-button', function (e) {
    removeJwtToken();
    $('.page').addClass('hide');
    $('#main-page').removeClass('hide');
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
    $('.page').addClass('hide');
    $('#admin-page').removeClass('hide');
    $('#user-dropdown').toggle();
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
        url: "/api/extensions/featured",
        success: function (data) {
            if(getJwtToken()&&isAdmin()){
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
        url: "/api/extensions/popular",
        success: function (data) {
            if(getJwtToken()&&isAdmin()){
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
            if(getJwtToken()&&isAdmin()){
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
            fillSearchPageList($('#search-container'), data)
        }
    });
};

var fillSearchPageList = function (location, data) {

    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2" data-toggle="modal" data-target="#extension-modal" value="' + v.id + '">';
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
        html += '<div>Open Issues <div>' + extension.issuesCounter + '</div></div>';
        html += '<div>Pull Requests <div>' + extension.pullRequestsCounter + '</div></div>';
        html += '<div>Last commit <div>' + extension.lastCommitDate + '</div></div></div></div><div style="margin-bottom: 3%;"></div><div class="row">';
        html += '<div class="col-xs-1"></div><div id="extension-description" class="col-md-10"><p>' + extension.description + '</p></div></div>';

        if (extension.tags.length > 0) {
            html += '<div class="row"><div class="col-xs-1"></div><div class="col-md-7"><h3 class="tags">Tags</h3><div class="tag-container">';

            $.each(extension.tags, function (k, v) {
                var tagsHtml = "";
                tagsHtml += '<div>' + v.tagName + '</div>';
                html += tagsHtml;
            });

            html += '</div></div></div>';
        } else {
            console.log(extension.tags.length);
        }

        html += '</div></div><div class="modal-footer"><div class="col-md-9"></div><div class="col-md-2">';
        html += '<button id="download-button" type="button" class="btn btn-success btn-lg" value="' + extension.file + '">Download</button></div></div></div></div></div>';

        location.append(html)

    }
};

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
    console.log($("#image-upload").val());
    console.log($("#file-upload").val());
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
        'tagNames': $('#extension-tags').val().split(', ')
    };

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
            html += '<div class="col-md-2 item" value="' + v.id + '">';
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

var adminFillWithEditable = function(location, data){
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
}

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
        "tagNames": $('#edit-extension-tags').val().split(", ")
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


$("#my-extensions-container, #popular-container, #featured-container, #new-container").on('click', '.click-to-edit', function (e) {
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
        if(isAdmin()){
            var publishText = 'Publish';
            var featureText = 'Feature';
            if(extension.publishedDate!==null) publishText = 'Unpublish';
            if(extension.featuredDate!==null) featureText = 'Unfeature';
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
    ajaxCalls.publishExtension(id).done(function(){
        refresh();
    });
    event.preventDefault();
});

$('#edit-buttons-container').on('click', '#feature-extension-button', function (event) {
    debugger;
    var id = $('#edit-extension-modal').val();
    ajaxCalls.changeFeatureStatus(id).done(function(){
        refresh();
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

function sortByDownloads(name){
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByNumberOfDownloads?name=" + name,
    });
};

function sortByLastCommit(name){
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByLastCommit?name=" + name,
    });
};

function sortByUpload(name){
    return $.ajax({
        type: "GET",
        url: "/api/extensions/sortByUpload?name=" + name,
    });
};

$('#search-magnifier').on('click', function (e) {
    var word = $('#search-param').val();
    console.log(word);
    filterByName(word).done(function (data) {
        fillSearchPageList($('#search-container'), data);
        $('#search-concept').html('Order by');
    })
    e.preventDefault();
});

$('#sort-by-downloads').on('click', function(event){
    var $this = $(this);
    var name = $('#search-param').val();
    sortByDownloads(name).done(function(data){
        fillSearchPageList($('#search-container'), data);
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

$('#sort-by-upload').on('click', function(event){
    var $this = $(this);
    var name = $('#search-param').val();
    sortByUpload(name).done(function(data){
        fillSearchPageList($('#search-container'), data);
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

$('#sort-by-last-commit').on('click', function(event){
    var $this = $(this);
    var name = $('#search-param').val();
    sortByLastCommit(name).done(function(data){
        fillSearchPageList($('#search-container'), data);
        $('#search-concept').html($this.html());
    })
    event.preventDefault();
});

