$(document).ready(function () {
    fillPopularList();
    fillFeaturedList();
    fillNewList();
});

var goHome = function () {
    $('.page').addClass('hide');
    $('#main-page').removeClass('hide');
}

$('#home-button').on('click', function (e) {
    e.preventDefault();
    goHome();
});

// $('#my-account').on('click', function (e) {
//     e.preventDefault();
//     var token = getJwtToken();
//     if (token) {
//         getMyExtensions();
//         $('.page').addClass('hide');
//         $('#my-extensions-page').removeClass('hide');
//     } else {
//         $('.page').addClass('hide');
//         $('#login-page').removeClass('hide');
//     }
// });

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

$('#my-account-button').on('click', function (e) {
    var token = getJwtToken();
    var html = '';
    if (token && !isTokenExpired(token)) {
        html += "<li><a id=" + "go-to-mine" + ">My extensions</a></li>";
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

$('#featured-category, #popular-category, #new-category').on('click', '.extension', function () {
    var id = $(this).attr('value');
    getExtensionData(id);
    $('#main-page').addClass('hide');
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
        html += '<div class="extension-page" value="' > +extension.id + '">';
        html += '<div class="top"><div class="inner-top"><div id="image-container">';
        html += '<img src="' + extension.image + '"></div>';
        html += '<div class="basic-info vertical">';
        html += '<div id="name" class="title">' + extension.name + '</div>';
        html += '<div id="owner" class="overview">' + extension.owner + '</div>';
        html += '<div id="downloads-number" class="overview"><i class="fas fa-download"></i>' + '  ' + extension.downloadsCounter + '</div>';
        html += '<div id="download-link"><a href="' + extension.file + '" id="download-button">Download</a></div></div><div class="additional-info vertical"><div id="version">Version<div class="small-padding">' + extension.version + '</div></div>';
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
76

function getMyExtensions() {
    var token = getJwtToken();
    if (token) {
        return $.ajax({
            type: "GET",
            url: "/api/extensions/mine",
            headers: createAuthorizationTokenHeader(),
            success: function (data) {
                fillWithEditableExtensions($('#my-extensions-container'), data);
            }
        });
    } else return;
};

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

$('#open-new-extension-modal').on('click', function () {
    $('#new-extension-modal').modal('show');
});

$('#add-extension-button').on('click', function () {

    var dto = {
        'name': $('#extension-name').val(),
        'description': $('#extension-description').val(),
        'link': $('#github-link').val(),
        'tagNames': $('#extension-tags').val().split(', '),
    }

    function uploadExtension() {
        $.ajax({
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
                    uploadExtension();
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
                    uploadExtension();
                }
            }
        });

        event.preventDefault();
    });

    $('#file-upload-form').trigger('submit');
    $('#image-upload-form').trigger('submit');

});

var fillWithEditableExtensions = function (location, data) {
    location.html('');

    if (data !== '') {
        $.each(data, function (k, v) {
            var html = "";
            html += '<div class="col-md-2" value="' + v.id + '">';
            html += '<div class="panel panel-primary"><div class="panel-heading flex-spread"><div>' + v.name + '</div><div><i class="far fa-edit click-to-edit"></i></div></div>';
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
            getMyExtensions();
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
    }

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
    editExtension(dto).done(function () {
        getMyExtensions().done(function () {
            $('#edit-extension-modal').modal('toggle');
        })
    });
}

function getExtensionById(id) {
    return $.ajax({
        type: "GET",
        url: "/api/extensions/" + id,
        headers: createAuthorizationTokenHeader()
    })
};

function editExtension(data) {
    return $.ajax({
        type: "POST",
        url: "/api/extensions/edit",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json',
        headers: createAuthorizationTokenHeader(),
        success: function (response) {
            console.log(response);
        }
    });
};

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
        if (tags.length > 0) tags.slice(0, -2);

        $('#edit-extension-tags').val(tags);

        $('#edit-image-name-text').attr('value', this.getNameFromFileLink(extension.image));
        $('#edit-image-name-text').attr('oldValue', extension.image);
        $('#edit-file-name-text').attr('value', this.getNameFromFileLink(extension.file));
        $('#edit-file-name-text').attr('oldValue', extension.file);
    },

    getNameFromFileLink: function (file) {
        var index = file.indexOf("downloadFile/") + "downloadFile/".length;
        return file.substring(index);
    },

    getFullFileLink: function (fileName) {
        return "http://localhost:8080/api/files/downloadFile/" + fileName;
    }
}