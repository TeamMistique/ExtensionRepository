$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
    fillAllList();
    ResCarouselOnInit();
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
            html += '<div class="pull-right"><i class="fas fa-download"> ' + v.downloadsCounter + '</i></div></div></div></div></div>'

            location.append(html)
        });
    } else {
        console.log('error');
    }
};

// $('#featured-container, #popular-container, #new-container, #search-container').on('click', '.col-md-2', function () {
//     console.log("Extension has been clicked - 1.")
//     console.log($(this) + '..................................' + $(this).attr('value'));
//     var id = $(this).attr('value');
//     getExtensionData(id);
//     $('body > div').addClass('hide');
//     $('#one-extension-page').removeClass('hide');
// });


// var getExtensionData = function (id) {
//     $.ajax({
//         type: "GET",
//         url: "/api/extensions/" + id,
//         success: function (data) {
//             fillExtensionPage($('#one-extension-page'), data)
//         }
//     });
// };

// var fillExtensionPage = function (location, extension) {
//     location.html('');

//     if (extension !== '') {
//         var html = "";
//         html += '<div class="container justify-content-center" value="' + extension.id + '">';
//         html += '<div class="row justify-content-center "><div class="col-md-7"><h1 class="page-header">' + extension.name + '</h1></div></div>';
//         html += '<div class="row"><div class="col-md-3"><img class="img-responsive" src="' + extension.image + '"></div>';
//         html += '<div class="col-md-2 text-left"><h3>Information</h3><div>' + extension.owner + '</div>';
//         html += '<div><i class="fas fa-download"> </i> ' + extension.downloadsCounter + '</div>';
//         html += '<div>Version' + extension.version + '</div>';
//         html += '<div id="download"><a id="download-button" class="row btn btn-success" type="button" href="' + extension.file + '">Download</a></div></div>';
//         html += '<div class="col-md-2 text-left"><h3 id="github"><a href="' + extension.link + '"><i class="fab fa-github-alt">' + '  ' + '</i>GitHub</a></h3>';
//         html += '<div>Open Issues  ' + extension.issuesCounter + '</div>';
//         html += '<div>Pull Requests  ' + extension.pullRequestsCounter + '</div>';
//         html += '<div>Last commit ' + extension.lastCommitDate + '</div></div></div><div style="margin-bottom: 1%;"></div>';
//         html += '<div class="row"><div class="col-md-7"><p>' + extension.description + '</p></div></div>';

//         if (extension.tags.length > 0) {
//             html += '<div class="col-md-6"><h3 class="tags">Tags</h3><div id="tag-section" class="tag-container">';
//             $.each(extension.tags, function (k, v) {
//                 var tagsHtml = "";
//                 tagsHtml += '<div class="tag border">' + v.tagName + '</div>';
//                 html += tagsHtml;
//             });
//         } else {
//             console.log('error');
//         }
//         html += '</div></div></div></div></div>';

//         location.append(html)

//     };
// }

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

function filterNames() {

    $('#search-magnifier').on('click', function (e) {
        e.preventDefault();
       var word = $('#search_param').find('input').val();
       console.log(word)
       fiterByName(word);

    });
}

var fiterByName = function () {
    $.ajax({
        type: "GET",
        url: "/api/extensions/filter",
        success: function (data) {
            fillMainPageList($('#all-container'), data)
        }
    });
};