angular.module("complaints.services").factory("ChangePasswordService", ['$http', function($http) {
	return {
		changePassword: function(password) {
    		return $http.post('http://localhost:8080/ComplaintService/service/user/changePassword',password).then(function(response) {
    			return response.data;
    		});
        }
    };
}]);