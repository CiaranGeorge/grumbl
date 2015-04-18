angular.module('complaints.controllers').controller('HomeController', ['$scope','$rootScope','$window','$location','HomeService','$ionicPlatform','$state','$ionicSideMenuDelegate','$ionicPopup', function($scope,$rootScope,$window,$location,HomeService,$ionicPlatform,$state,$ionicSideMenuDelegate,$ionicPopup) {

	$scope.groups = [
		{"status":"New Grumbls", "color":"tealAcc","complaints":[],"statusCode":"N"}, 
		{"status":"Pending Grumbl Response", "color":"blueAcc","complaints":[],"statusCode":"P"}, 
		{"status":"Additional Details Required", "color":"orangeAcc","complaints":[],"statusCode":"A"},
		{"status":"Grumbl Resolved", "color":"greenAcc","complaints":[],"statusCode":"R"}
	];
	
	//$scope.userDetails = {};
	$scope.shownGroup = $scope.groups[0];
	
	$ionicPlatform.onHardwareBackButton(function(event) {
		if($ionicSideMenuDelegate.isOpen()) {
			$ionicSideMenuDelegate.toggleRight();
		} else if($state.current.name==="home"){
			navigator.app.exitApp();
		}
	});

	HomeService.getDetails().then(function(data) {
		data.complaintEntities.forEach(function(value) {
			$scope.groups.forEach(function(innerVal) {
				if(innerVal.statusCode === value.status) {
					innerVal.complaints.push(value);
				}
			});
		});
		$scope.userDetails = data;
	}, function(error) {
		if(!error.status) {
			$ionicPopup.alert({
			     title: '<strong>An error has occured!</strong>',
			     template: '<p class="errorText">Check your connection and try again.</p>'
			   });
		} else {
			$ionicPopup.alert({
			     title: '<strong>An error has occured!</strong>',
			     template: '<p class="errorText">Session has expired.</p>'
			   });
		}
		delete $window.localStorage.token;
		$location.path('login');
	});
				
	$scope.logout = function() {
		delete $window.localStorage.token;
		$location.path('login');
	};
	
	$scope.createGrumbl = function() {
		$location.path('create');
	};

	$scope.toggleRight = function() {
		$ionicSideMenuDelegate.toggleRight();
	};
	
	$scope.toggleGroup = function(group) {
		if ($scope.isGroupShown(group)) {
	        $scope.shownGroup = null;
		} else {
		    $scope.shownGroup = group;
		}
	};
	
	$scope.changePass = function() {
		$location.path('changePassword');
	};
		  
	$scope.isGroupShown = function(group) {
	    return $scope.shownGroup === group;
	};
	    
}]);  
