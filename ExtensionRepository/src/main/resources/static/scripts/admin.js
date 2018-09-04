var ajaxCalls = {
    getUnpublished: function(){
        return $.ajax({
            type: "GET",
            url: "/api/admin/unpublished",
            headers: createAuthorizationTokenHeader()
        });
    },
    
    publishExtension: function(id){
        return $.ajax({
            type: "POST",
            url: "/api/admin/publish?id="+id,
            headers: createAuthorizationTokenHeader(),
        });
    },

    changeFeatureStatus: function(id){
        return $.ajax({
            type: "POST",
            url: "/api/admin/feature?id="+id,
            headers: createAuthorizationTokenHeader(),
        });
    },

    disableUser: function(username){
        return $.ajax({
            type: "POST",
            url: "/api/admin/disableUser?username="+username,
            headers: createAuthorizationTokenHeader(),
        })
    },

    getUserExtensions: function(username){
        return $.ajax({
            type: "GET",
            url: "/api/admin/userExtensions?username="+username,
            headers: createAuthorizationTokenHeader()
        });
    }
}