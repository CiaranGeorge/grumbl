angular.module("complaints.services").factory("ResetService", ['$http', function($http) {
	return {
        resetPassword: function(username) {
    		return $http.get('http://localhost:8080/ComplaintService/service/user/resetPassword/'+username).then(function(response) {
    			return response.data;
    		});
        },
        usernameReminder: function(email) {
        	return $http.get('http://localhost:8080/ComplaintService/service/user/forgottenUsername/'+email).then(function(response) {
                return response.data;
            });
        }
    };
}]);