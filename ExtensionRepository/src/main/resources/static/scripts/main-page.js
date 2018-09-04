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
});

function goHome() {
    $('.page').addClass('hide');
    $('#main-page').removeClass('hide');
}

$('#search-button').on('click', function (e) {
    e.preventDefault();
    $('.page').addClass('hide');
    $('#search-page').removeClass('hide');

    goHome();
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
        if(isAdmin()){
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

var fillMainPageList = function (location, data) {

    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2 item" value="' + v.id + '">';
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
            html += '<div class="col-md-2" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading">' + v.name + '</div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>';

            location.append(html);
        });
    } else {
        console.log('error');
    }
};

$('#featured-container, #popular-container, #new-container, #search-container').on('click', '.col-md-2', function () {
    console.log("Extension has been clicked - 1.");
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
            var isIE = (window.navigator.userAgent.indexOf("MSIE ") > 0 || !! navigator.userAgent.match(/Trident.*rv\:11\./));
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

var resetAddExtesnionForms = function() {
    $("[type='file']").clearFiles();
    $("[type='text']").val('');
    $('#new-extension-form').get(0).reset();
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
                if (typeof dto.image !== 'undefined') {
                    uploadExtension(dto).done(function () {
                        resetAddExtesnionForms();
                    });
                }
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
                if (typeof dto.file !== 'undefined') {
                    uploadExtension(dto).done(function () {
                        resetAddExtesnionForms();
                    });
                }
            }
        });

        event.preventDefault();
    });

    $('#file-upload-form').trigger('submit');
    $('#image-upload-form').trigger('submit');

});

// var fillWithEditableExtensions = function (data) {
//     var publishedLocation = $('#my-published');
//     var unpublshedLocation = $('#my-unpublished');
//     publishedLocation.html('');
//     unpublshedLocation.html('');

//     if (data !== '') {
//         $.each(data, function (k, v) {
//             debugger;
//             var html = "";
//                 html += '<div class="col-md-2" value="' + v.id + '">';
//                 html += '<div class="panel panel-primary"><div class="panel-heading flex-spread"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
//                 html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
//                 html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
//                 html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>';
//             if(v.publishedDate!==null){
//                 publishedLocation.append(html);
//             } else {
//                 unpublshedLocation.append(html);
//             }
//         });
//     } 
// };

var fillWithEditableExtensions = function (data) {
    var publishedLocation = $('#my-published-container');
    var unpublshedLocation = $('#my-unpublished-container');
    publishedLocation.html('');
    unpublshedLocation.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2 item" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
            html += '<div class="panel-body"><div class="img-responsive" style="background-image: url(' + v.image + ');"></div></div>';
            html += '<div class="panel-footer"><div class="extension-bottom"><div class="pull-left"><i class="fas fa-user-tie"> ' + v.owner + '</i></div>';
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'
            if(v.publishedDate!==null){
                publishedLocation.append(html);
            } else {
                unpublshedLocation.append(html);
            }
        });
    }
};

function resetEditForms() {
    $('#edit-extension-form')[0].reset();
    $('#edit-image-upload-form')[0].reset();
    $('#edit-file-upload-form')[0].reset();
}

$('#delete-extension-button').on('click', function (event) {
    var id = $('#edit-extension-modal').val();

    $.ajax({
        type: "POST",
        url: "/api/extensions/delete?id=" + id,
        headers: createAuthorizationTokenHeader(),
        success: function () {
            getMyExtensions();
        }
    });

    $('#edit-extension-modal').modal('toggle');
    resetEditForms();
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


$("#my-extensions-container").on('click', '.click-to-edit', function (e) {
    e.stopPropagation();
    var $extensionToEdit = $(this).closest('.col-md-2');
    var id = $extensionToEdit.attr('value');
    $('#edit-extension-modal').val(id);

    getExtensionById(id).done(function (extension) {
        helpers.fillEditMenu(extension);
        $('#edit-extension-modal').modal('show');
    });
});

function triggerEdit(dto) {
    console.log("edit triggered");
    editExtension(dto).done(function () {
        getMyExtensions().done(function () {
            $('#edit-extension-modal').modal('toggle');
            resetEditForms();
        })
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
    },

    getNameFromFileLink: function (file) {
        var index = file.indexOf("downloadFile/") + "downloadFile/".length;
        return file.substring(index);
    }
};

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
        console.log("resize Works");
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

    //    styleCollector0 = styleCollector1 = styleCollector2 = styleCollector3 = "";
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
        //var r = $('body').width();
        //var it = r >= 1200 ? itemsSplit[3] : r >= 992 ? itemsSplit[2] : r >= 768 ? itemsSplit[1] : itemsSplit[0];
        //$(this).attr("data-itm", it);

        var styleCollector = "@media (max-width:767px){" + styleCollector0 + "}" +
            "@media (min-width:768px){" + styleCollector1 + "}" +
            "@media (min-width:992px){" + styleCollector2 + "}" +
            "@media (min-width:1200px){" + styleCollector3 + "}";
        //$(this).append("<div class=\"ResStyleManager\"></div>")
        $(this).find("style").remove();
        $(this).append("<style>" + styleCollector + "</style>");
        ResCarouselSlide(this);

    });
    //console.log(styleCollector);
    //$("body").append("<div class=\"ResStyleManager\"></div>")
    //$('.ResStyleManager').html(null).append("<style>" + styleCollector + "</style>");
    var t1 = performance.now();
    console.log('Took', (t1 - t0).toFixed(4), 'milliseconds to Size');
}

//this function used to move the items
function ResCarousel(Btn) {
    //var t0 = performance.now();
    var parent = $(Btn).parent(),
        slide = +parent.attr("data-slide"),
        itemsDiv = parent.find('.resCarousel-inner'),
        //divValueq = +parent.attr('data-value'),
        itemSpeed = +parent.attr("data-speed"),
        itemLoad = +parent.attr("data-load"),
        //animi = parent.attr("data-animator"),
        translateXval = '',
        currentSlide = "",
        itemLenght = itemsDiv.find(".item").length,
        itemWidth = itemsDiv.find('.item').outerWidth(),
        dataItm = +Math.round(itemsDiv.outerWidth() / itemWidth),
        cond = $(Btn).hasClass("leftLst"),
        divValue = Math.round(itemsDiv.scrollLeft() / itemWidth);
    //console.log(dataItm + "," + Math.abs(dataItmq));
    //console.log(divValue + "," + divValueq);
    //console.log(cond);
    //console.log(typeof + parent.attr("data-slide"))
    itemSpeed = !isNaN(itemSpeed) ? itemSpeed : 400;
    slide = slide < dataItm ? slide : dataItm;

    if (cond) {
        currentSlide = divValue - slide;
        translateXval = currentSlide * itemWidth;
        var MoveSlide = currentSlide + slide;
        //console.log(itemloop);
        if (divValue == 0) {
            currentSlide = itemLenght - slide;
            translateXval = currentSlide * itemWidth;
            currentSlide = itemLenght - dataItm;
            itemSpeed = 400;
            //console.log(currentSlide + "," + translateXval);
        } else if (slide >= MoveSlide) {
            currentSlide = translateXval = 0;
        }
    } else {
        currentSlide = divValue + slide;
        translateXval = currentSlide * itemWidth;
        var MoveSlide = currentSlide + slide;

        //console.log(itemLenght + "," + (MoveSlide + "," + slide + "," + dataItm));
        //console.log(itemLenght + "," + (MoveSlide - slide + dataItm));
        //console.log((divValue + dataItm) + "," + itemLenght);
        if (divValue + dataItm == itemLenght) {
            currentSlide = translateXval = 0;
            itemSpeed = 400;
        } else if (itemLenght <= (MoveSlide - slide + dataItm)) {
            currentSlide = itemLenght - slide;
            translateXval = currentSlide * itemWidth;
            currentSlide = itemLenght - dataItm;
        }
        // resCarouselAnimator(itemsDiv, currentSlide + 1, currentSlide + slide);
    }
    //console.log(slide + "," + itemWidth);
    parent.attr("data-animator") == "lazy" && resCarouselAnimator(itemsDiv, cond ? 0 : 1, currentSlide + 1, currentSlide + dataItm, itemSpeed, (slide * itemWidth));
    //console.log(itemsDiv.scrollLeft() + "," + translateXval)
    //console.log(itemSpeed);
    if (!isNaN(itemLoad)) {
        itemLoad = itemLoad >= slide ? itemLoad : slide;
        //console.log((itemLenght - itemLoad) <= currentSlide + dataItm);
        //console.log((itemLenght - itemLoad) + " ," + (currentSlide + dataItm) + " ," + (itemLenght - dataItm));
        (itemLenght - itemLoad) <= (currentSlide + dataItm) && ResCarouselLoad1(itemsDiv);
    }
    itemsDiv.animate({
        scrollLeft: translateXval
    }, itemSpeed);
    parent.attr("data-value", currentSlide);

    //var t1 = performance.now();
    //console.log('Took', (t1 - t0).toFixed(4), 'milliseconds to generate');
}

function ResCarouselLoad1(e) {
    //console.log(e.attr("id"));
    $("#" + e.attr("id")).trigger("ResCarouselLoad");
}

function resCarouselAnimator(parent, direction, start, end, speed, length) {
    //console.log(parent + "," + start + "," + end);
    var val = 5;
    if (direction == 0) {
        for (var i = start - 1; i < end + 1; i++) {
            val = val * 2;
        }
        val = -val;
    }
    //console.log(length);
    //if (direction == 1) {
    //    for (var i = start - 1; i < end + 1; i++) {
    //        length = length / 2
    //        console.log(length);
    //    }
    //    //val = val;
    //}
    //val = direction == 1 ? length : -length;

    for (var i = start - 1; i < end; i++) {
        val = direction == 0 ? val / 2 : val * 2;
        //console.log(val);
        //console.log(parent.find(".item").eq(i).find("h1").text());
        parent.find(".item").eq(i).css("transform", "translateX(" + val + "px)");
    }
    setTimeout(function () {
        parent.find(".item").attr("style", "");
    }, speed - 70);
}