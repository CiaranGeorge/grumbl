angular.module("complaints.services").service("CreateService", ['$http', function($http) {
	this.createComplaint = function(complaint){
		return $http.post('http://localhost:8080/ComplaintService/service/complaint/createComplaint', complaint).then(
		function(data) {
			return data.data;
		});
	};
}]);