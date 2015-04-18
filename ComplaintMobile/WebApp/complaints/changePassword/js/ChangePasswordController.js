angular.module('complaints.controllers').controller('ChangePasswordController', ['ChangePasswordService','$scope', '$ionicLoading','$location','$ionicPopup','$window', function(ChangePasswordService,$scope,$ionicLoading,$location,$ionicPopup,$window) {
	
	$scope.password = {};
	
	$scope.comparePassword = function(password){
		if(password) {
			return (password.first == password.second);
		} 
	};
	
	$scope.changePassword = function(password,newPass) {
		if($scope.comparePassword(newPass)) {
			password.newPassword = newPass.first;
			$ionicLoading.show({
				 templateUrl: 'loader.html',
				 duration:8000
			});
			
			ChangePasswordService.changePassword(password).then(function(data) {
				$ionicLoading.hide();
				$window.localStorage.token = data.token;
				$location.path('home');
			}, function(error) {
				$ionicLoading.hide();
				if(!error.status) {
					$ionicPopup.alert({
					     title: '<strong>An error has occured!</strong>',
					     template: '<p class="errorText">Check your connection and try again.</p>'
					   });
				} else {
					$scope.error = error.data.message;
					$ionicPopup.alert({
					     title: '<strong>An error has occured!</strong>',
					     template: '<p class="errorText">{{error}}</p>',
					     scope: $scope
					});
				}
				
			});	
		}
	};
	
}]);