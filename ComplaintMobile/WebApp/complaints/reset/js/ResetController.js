angular.module('complaints.controllers').controller('ResetController', ['ResetService','$ionicSlideBoxDelegate','$scope','$location','$ionicLoading','$ionicPopup',function(ResetService,$ionicSlideBoxDelegate,$scope,$location,$ionicLoading,$ionicPopup) {
    
    $scope.forgottenRadio = 'Username';

	$scope.resetPassword = function(username){
		$ionicLoading.show({
			 templateUrl: 'loader.html'
		 });
        ResetService.resetPassword(username).then(
		function(data) {
			$ionicLoading.hide();
			$location.path('login');
		},
		function(error) {
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
	};
	
	$scope.usernameReminder = function(email) {
		$ionicLoading.show({
			 templateUrl: 'loader.html'
		 });
		ResetService.usernameReminder(email).then(
			function(data) {
				$ionicLoading.hide();
				$location.path('login');
			},
			function(error) {
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
	};

    $scope.selected = 'username';

    $scope.returnToLogin = function() {
        $location.path('login');
    };
    
	$scope.nextPage = function() {
	    $ionicSlideBoxDelegate.next();
	    $scope.selected = 'password';
	};
	
	$scope.previousPage = function() {
	    $ionicSlideBoxDelegate.previous();
	    $scope.selected = 'username';
	};
	  
	$scope.disableSwipe = function() {
		$ionicSlideBoxDelegate.enableSlide(false);
	};
}]);