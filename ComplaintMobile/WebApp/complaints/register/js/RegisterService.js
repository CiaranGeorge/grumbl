angular.module("complaints.services").factory("RegisterService", ['$http', function($http) {
	return {
        checkUsername: function(username) {
    		return $http.post('http://localhost:8080/ComplaintService/service/user/checkUsername', {"username":username}).then(function(response) {
    			return response.data;
    		});
        },
    	createUser: function(user) {
        	return $http.post('http://localhost:8080/ComplaintService/service/user/createUser', user).then(
        	function(response) {
        		return response.data;
        	});
       }
    };
}]);