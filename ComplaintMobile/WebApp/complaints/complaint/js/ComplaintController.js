angular.module('complaints.controllers').controller('ComplaintController', ['ComplaintService','$scope', '$stateParams','$location','$ionicLoading', function(ComplaintService,$scope,$stateParams,$location,$ionicLoading) {
	
	var id = $stateParams.complaintId;
	
	$scope.laoded = false;
	$ionicLoading.show({
		 templateUrl: 'loader.html'
	    });
	
	ComplaintService.getComplaint(id).then(
			function(data) {
				$scope.complaint = data.dbResp.complaint;
				if($scope.complaint.imageData) {
					$scope.cameraImage ="data:image/jpeg;base64," + $scope.complaint.imageData;
				}
				$scope.loaded = true;
				$ionicLoading.hide();
			},
			function(error) {
				$ionicLoading.hide();
				$location.path('home');
			});
	
	$scope.submitComment = function(comment) {
		comment.complaintId = id;
		var newComment = angular.copy(comment);
		$scope.comment = null;
		comment.message = null;
		ComplaintService.submitComment(newComment).then(
				function(data) {
					$scope.complaint.commentEntities.push(newComment);
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
	
	$scope.checkNumber = function(index) {
		return (index+1) % 2 === 1;
	};
	
	
	$scope.toggleGroup = function(group) {
		if ($scope.isGroupShown(group)) {
	        $scope.shownGroup = null;
		} else {
		    $scope.shownGroup = group;
		}
	};
		  
	$scope.isGroupShown = function(group) {
	    return $scope.shownGroup === group;
	};
	
	
}]);