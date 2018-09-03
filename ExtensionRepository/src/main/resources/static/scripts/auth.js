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
            "Authorization": "Bearer " + token,
        };
    } else {
        return {
            'Content-Type': 'application/json'
        };
    }
};

var isTokenExpired = function(){

    var request = function(){
        return $.ajax({
            type: "GET",
            url: "/api/expired",
            headers: createAuthorizationTokenHeader()
        });
    }
    
    request().done(function(result){
        return result;
    });
};