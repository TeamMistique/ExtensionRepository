var TOKEN_KEY = 'jwtToken';

var getJwtToken = function () {
    var token =  localStorage.getItem(TOKEN_KEY);
    if(token && getExpiryTimeFromToken() < Date.now()/1000){
        removeJwtToken();
        refresh();
    }
    return token;
};

var justGetJwtToken = function(){
    return localStorage.getItem(TOKEN_KEY);
}

var setJwtToken = function (token) {
    localStorage.setItem(TOKEN_KEY, token);
};

var removeJwtToken = function () {
    localStorage.removeItem(TOKEN_KEY);
};

var createAuthorizationTokenHeader = function () {
    var token = getJwtToken();
    if (token) {
        return {
            "Authorization": "Bearer " + token
        };
    } else {
        return {
            'Content-Type': 'application/json'
        };
    }
}

function parseJwt () {
    var token = justGetJwtToken();
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(window.atob(base64));
};

function ajaxisAdmin(){
    var claims = parseJwt();
    var admin = false;
    $.each(claims.scopes, function(k, v){
        if(v.role == "ROLE_ADMIN") admin = true;
    });
    return admin;
};

function getExpiryTimeFromToken (){
    var claims = parseJwt();
    return claims.exp;
}

function isUserAdmin(user){
    var admin = false;
    $.each(user.authorities, function(k,v){
        if(v.role=="ROLE_ADMIN") admin = true;
    });
    return admin;
}
