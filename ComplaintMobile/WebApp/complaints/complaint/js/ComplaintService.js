angular.module("complaints.services").factory("ComplaintService", ['$http', function($http) {
	return {
		getComplaint: function(id) {
    		return $http.get('http://localhost:8080/ComplaintService/service/complaint/getComplaint/'+id).then(function(response) {
    			return response.data;
    		});
        },
        submitComment: function(comment) {
        	return $http.post('http://localhost:8080/ComplaintService/service/complaint/createComment',comment).then(function(response) {
    			return response.data;
    		});
        }
    };
}]);