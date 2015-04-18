angular.module('complaints.controllers').controller('CreateController', ['CreateService','$scope','$ionicSlideBoxDelegate','$cordovaCamera','$ionicPopup','$ionicLoading','$location',function(CreateService,$scope,$ionicSlideBoxDelegate,$cordovaCamera,$ionicPopup,$ionicLoading,$location) {
	
	 $scope.complaint = {};
	 
	 $scope.submitComplaint = function(complaint) {
		 $ionicLoading.show({
			 templateUrl: 'loader.html'
		   });
		 CreateService.createComplaint(complaint).then(
					function(data) {
						$ionicLoading.hide();
						$location.path('home');
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
	
	$scope.takePicture = function() {
	    var options = { 
	    		quality : 75, 
	            destinationType : Camera.DestinationType.DATA_URL, 
	            sourceType : Camera.PictureSourceType.CAMERA, 
	            allowEdit : true,
	            encodingType: Camera.EncodingType.JPEG,
	            targetWidth: 800,
	            targetHeight: 800,
	            popoverOptions: CameraPopoverOptions,
	            saveToPhotoAlbum: false
	    };

	    $cordovaCamera.getPicture(options).then(function(imageData) {
	      $scope.cameraImage ="data:image/jpeg;base64," + imageData;
	      $scope.complaint.imageData = imageData;
	    }, function(err) {
	      // An error occured. Show a message to the user
	    });
	  };
	  
	  $scope.getImage = function() {
		    var options = {
		            destinationType : Camera.DestinationType.DATA_URL, 
		            sourceType : Camera.PictureSourceType.SAVEDPHOTOALBUM,
		            encodingType: Camera.EncodingType.JPEG,
		            targetWidth: 800,
		            targetHeight: 800
		    };

		    $cordovaCamera.getPicture(options).then(function(imageData) {
		      $scope.cameraImage ="data:image/jpeg;base64," + imageData;
		      $scope.complaint.imageData = imageData;
		    }, function(err) {
		      // An error occured. Show a message to the user
		    });
		  };
	  
	$scope.loadImagePopup = function() {
		var alertPopup = $ionicPopup.alert({
		     title: 'Image Taken',
		     template: '<img class="popupPic" src="{{cameraImage}}">',
		     scope: $scope,
		     buttons: [{text:'OK',type:'button-positive'}]
		   });
	};
	
	$scope.nextPage = function() {
	    $ionicSlideBoxDelegate.next();
	};
	
	$scope.previousPage = function() {
	    $ionicSlideBoxDelegate.previous();
	};
	  
	$scope.disableSwipe = function() {
		$ionicSlideBoxDelegate.enableSlide(false);
	};

  }]);