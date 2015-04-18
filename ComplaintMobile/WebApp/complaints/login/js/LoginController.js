angular.module('complaints.controllers').controller('LoginController', ['$scope','$rootScope','LoginService','$window','$location','$ionicLoading','OpenFB', function($scope,$rootScope,LoginService,$window,$location,$ionicLoading,OpenFB) {
	
	if($window.localStorage.token) {
		$location.path('home');
	}

	$scope.authenticate = function(user){
		 $ionicLoading.show({
			 templateUrl: 'loader.html'
		    });
		LoginService.login(user).then(
		function(data) {
			$rootScope.loggedInWithFB = false;
			$window.localStorage.token = data.token;
			$ionicLoading.hide();
			$location.path('home');
		},
		function(error) {
			if(error.data) {
				$scope.errorText = error.data.message;
			} else if(error.status === 0) {
				$scope.errorText = "There is a problem with the internet connection.";
			} else {
				$scope.errorText = "Please enter user details";
			}
			delete $window.localStorage.token;
			$ionicLoading.hide();
		});
	};
	
	 $scope.facebookLogin = function () {
		 $ionicLoading.show({
			 templateUrl: 'loader.html'
		    });
         OpenFB.login('email,read_stream,publish_stream').then(
             function () {
                 OpenFB.get('/me?fields=id,name,email').success(function (user) {
                    LoginService.facebookLogin(user).then(
                    	function(data) {
                    		$rootScope.loggedInWithFB = true;
                    		$window.localStorage.token = data.token;
                    		$ionicLoading.hide();
                    		$location.path('home');
                    	},
                    	function(error) {
                    		if(error.data) {
                    			$scope.errorText = error.data.message;
                    		} else if(error.status === 0) {
                    			$scope.errorText = "There is a problem with the internet connection.";
                    		} 
                    		delete $window.localStorage.token;
                    		$ionicLoading.hide();
                    	}
                    );
                     
                 });
             },
             function () {
         		$scope.errorText = "Facebook login failed.";
             });
     };
	
	
  }]);