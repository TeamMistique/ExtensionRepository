var TOKEN_KEY = 'jwtToken';

var getJwtToken = function () {
    return localStorage.getItem(TOKEN_KEY);
};

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
    var token = getJwtToken();
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(window.atob(base64));
};

function isAdmin(){
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

