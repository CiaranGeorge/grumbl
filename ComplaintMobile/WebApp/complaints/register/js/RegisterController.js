angular.module('complaints.controllers').controller('RegisterController', ['RegisterService','$scope','$location','$window','LoginService','$ionicLoading','$ionicPopup',function(RegisterService,$scope,$location,$window,LoginService,$ionicLoading,$ionicPopup) {
	$scope.checkUsername = function(username){
		RegisterService.checkUsername(username).then(
		function(data) {
			$scope.doesUserExist = data;
		});
	};

	$scope.comparePassword = function(password){
		if(password.first == password.second){
			$scope.passwordsMatch = true;
		}else{
			$scope.passwordsMatch = false;
		}
	};
	
	$scope.createUser = function(registerForm,user,password) {
		if(registerForm.$valid){
			if($scope.passwordsMatch) {
				$ionicLoading.show({
					 templateUrl: 'loader.html'
				    });
				user.password = password.first;
				
				
				RegisterService.createUser(user).then(function(data) {
					$ionicLoading.hide();
					$location.path('login');
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
		} else {
			$ionicPopup.alert({
			     title: 'An error has occured!',
			     template: 'The form is invalid.'
			   });
		}
	};
	
	$scope.returnToLogin = function() {
		$location.path('login');
	};

  }]);