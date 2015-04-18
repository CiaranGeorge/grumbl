angular.module("complaints.services").factory("HomeService", ['$http', function($http) {
	return {
        getDetails: function() {
    		return $http.get('http://localhost:8080/ComplaintService/service/user/getUser').then(
    		function(data) {
    			return data.data;
    		});
        }
    };
}]);