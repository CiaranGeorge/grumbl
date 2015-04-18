angular.module("complaints.services").factory("LoginService", ['$http', function($http) {
	return {
		login: function(user){
			return $http.post('http://localhost:8080/ComplaintService/service/user/authenticate', user).then(
			function(data) {
				return data.data;
			});
		},
		facebookLogin: function(user){
			return $http.post('http://localhost:8080/ComplaintService/service/user/facebookAuth', user).then(
			function(data) {
				return data.data;
			});
		}
	};
	}]);