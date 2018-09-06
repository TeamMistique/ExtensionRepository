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

    changeUserEnabled: function(username){
        return $.ajax({
            type: "POST",
            url: "/api/admin/changeUserEnabled?username="+username,
            headers: createAuthorizationTokenHeader(),
        })
    },

    getUserExtensions: function(username){
        return $.ajax({
            type: "GET",
            url: "/api/admin/userExtensions?username="+username,
            headers: createAuthorizationTokenHeader()
        });
    },

    getAllUsers: function(){
        return $.ajax({
            type: "GET",
            url: "/api/user",
            headers: createAuthorizationTokenHeader()
        });
    },

    changeSyncPeriod:function(periodInMinutes){
        return $.ajax({
            type: "POST",
            url: "/api/admin/changeSyncPeriod?delay="+periodInMinutes*60000,
            headers: createAuthorizationTokenHeader()
        });
    },

    triggerSyncAll: function(){
        return $.ajax({
            type: "POST",
            url: "/api/admin/syncAll",
            headers: createAuthorizationTokenHeader()
        });
    },

    triggerOneSync: function(id){
        return $.ajax({
            type: "POST",
            url: "/api/admin/syncOne?id="+id,
            headers: createAuthorizationTokenHeader()
        });
    }
}