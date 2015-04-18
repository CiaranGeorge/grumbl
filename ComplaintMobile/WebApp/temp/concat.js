(function() {
	'use strict';

	// Define our application
	angular.module("complaints.controllers", []);
	angular.module("complaints.services", []);
	angular.module("complaints.directives", []);
	angular.module("complaints.filters", []);

	// Declare app level module which depends on filters, and services
	var complaintsApp = angular.module(
			"complaints",
			['ui.bootstrap', 'ngRoute', 'complaints.controllers', 'complaints.services',
					'complaints.directives', 'complaints.filters']).value('version', '0.1');

	complaintsApp.config([ '$routeProvider','$httpProvider', function($routeProvider,$httpProvider) {
		$httpProvider.interceptors.push('authInterceptor');

		$routeProvider.when('/login', {
			templateUrl : 'complaints/login/login.html',
			controller : 'LoginController'
		});
		$routeProvider.when('/register', {
			templateUrl : 'complaints/register/register.html',
			controller : 'RegisterController'
		});
        $routeProvider.when('/reset', {
            templateUrl : 'complaints/reset/reset.html',
            controller : 'ResetController'
        });
		$routeProvider.when('/home', {
			templateUrl : 'complaints/home/home.html',
			controller : 'HomeController'
		});
		$routeProvider.when('/create', {
			templateUrl : 'complaints/create/create.html'
			//controller : 'HomeController'
		});
		$routeProvider.otherwise({
			redirectTo : '/login'
		});
	} ]);

}());
/*js file*/
angular.module("complaints.services").factory('authInterceptor', ['$rootScope','$q','$window','$location', function ($rootScope, $q, $window, $location) {
  return {
    request: function (config) {
      config.headers = config.headers || {};
      if ($window.localStorage.token) {
        config.headers['X-Auth-Token'] = $window.localStorage.token;
      }
      return config;
    },
    response: function (response) {
      if (response.status === 401) {
    	  delete $window.localStorage.token;
    	  $location.path('login');
      }
      return response || $q.when(response);
    }
  };
}]);
/*js file*/
angular.module('complaints.controllers').controller('HomeController', ['$scope','$window','$location','HomeService', function($scope,$window,$location,HomeService) {

			HomeService.getDetails().then(function(data) {
				alert(JSON.stringify(data));
			}, function(error) {
				delete $window.localStorage.token;
			    $location.path('login');
			});

  $scope.groups = [
    {
      title: "Dynamic Group Header - 1",
      items: [{"item-title": "item 1"}, {"item-title": "item 2"}]
    },
    {
      title: "Dynamic Group Header - 2",
      items: [{"item-title": "item 3"}, {"item-title": "item 4"}]
    }
  ];

  $scope.oneAtATime = true;

  $scope.status = {
    isFirstOpen: true,
  };


  $scope.logout = function() {
    delete $window.localStorage.token;
    $location.path('login');
  };
    
  }]);  

/*js file*/
angular.module("complaints.services").factory("HomeService", ['$http', function($http) {
	return {
        getDetails: function() {
    		return $http.get('http://localhost:8080/ComplaintService/service/user/getUser').then(
    		function(data) {
    			return data.data;
    		});
        }
    };
}]);
/*js file*/
angular.module('complaints.controllers').controller('LoginController', ['$scope','LoginService','$window','$location', function($scope,LoginService,$window,$location) {
	
	if($window.localStorage.token) {
		$location.path('home');
	}

	$scope.authenticate = function(user){
		LoginService.login(user).then(
		function(data) {
			$window.localStorage.token = data.token;
			$location.path('home');
		},
		function(error) {
			if(error.data) {
				$scope.errorText = error.data.message;
				delete $window.localStorage.token;
			} else {
				$scope.errorText = "Please enter user details";
				delete $window.localStorage.token;

			}
		});
	};

	//THIS IS ONLY FOR TESTING WHILE WE HAVE NO SERVICE
	$scope.fakeLogin = function() {
		$location.path('home');
	};

	var pictureSource;   // picture source
    var destinationType; // sets the format of returned value

    // Wait for device API libraries to load
    //
    document.addEventListener("deviceready",onDeviceReady,false);

    // device APIs are available
    //
    function onDeviceReady() {
        pictureSource=navigator.camera.PictureSourceType;
        destinationType=navigator.camera.DestinationType;
    }

    // Called when a photo is successfully retrieved
    //
    onPhotoDataSuccess = function(imageData) {
      // Uncomment to view the base64-encoded image data
      // console.log(imageData);

      // Get image handle
      //
      var smallImage = document.getElementById('smallImage');

      // Unhide image elements
      //
      smallImage.style.display = 'block';

      // Show the captured photo
      // The inline CSS rules are used to resize the image
      //
      smallImage.src = "data:image/jpeg;base64," + imageData;
    };

    // Called when a photo is successfully retrieved
    //
    $scope.onPhotoURISuccess = function(imageURI) {
      // Uncomment to view the image file URI
      // console.log(imageURI);

      // Get image handle
      //
      var largeImage = document.getElementById('largeImage');

      // Unhide image elements
      //
      largeImage.style.display = 'block';

      // Show the captured photo
      // The inline CSS rules are used to resize the image
      //
      largeImage.src = imageURI;
    };

    // A button will call this function
    //
    $scope.capturePhoto = function() {
      // Take picture using device camera and retrieve image as base64-encoded string
      navigator.camera.getPicture(onPhotoDataSuccess, onFail, { quality: 50,
        destinationType: destinationType.DATA_URL });
    };

    // A button will call this function
    //
    $scope.capturePhotoEdit = function() {
      // Take picture using device camera, allow edit, and retrieve image as base64-encoded string
      navigator.camera.getPicture(onPhotoDataSuccess, onFail, { quality: 20, allowEdit: true,
        destinationType: destinationType.DATA_URL });
    };

    // A button will call this function
    //
    $scope.getPhoto = function(source) {
      // Retrieve image file location from specified source
      navigator.camera.getPicture(onPhotoURISuccess, onFail, { quality: 50,
        destinationType: destinationType.FILE_URI,
        sourceType: source });
    };

    onFail = function(error) {
    	alert("fail");
    };

	
  }]);
/*js file*/
angular.module("complaints.services").service("LoginService", ['$http','$window', function($http) {
	this.login = function(user){
		return $http.post('http://localhost:8080/ComplaintService/service/user/authenticate', user).then(
		function(data) {
			return data.data;
		});
	};
}]);
/*js file*/
angular.module('complaints.controllers').controller('RegisterController', ['RegisterService','$scope','$location',function(RegisterService,$scope,$location) {
	$scope.checkUsername = function(username){
		RegisterService.checkUsername(username).then(
		function(data) {
			$scope.doesUserExist = data;
		});
	};
	
	$scope.createUser = function(user) {
		if($scope.passwordsMatch) {
			user.password = user.password.first;
		}

		RegisterService.createUser(user).then(function(data){
			$location.path('login');
		});	
	};

	$scope.comparePassword = function(password){
		
		if(password.first == password.second){
			$scope.passwordsMatch = true;
		}else{
			$scope.passwordsMatch = false;
		}
	};

  }]);
/*js file*/
angular.module("complaints.services").factory("RegisterService", ['$http', function($http) {
	return {
        checkUsername: function(username) {
    		return $http.post('http://localhost:8080/ComplaintService/service/user/checkUsername', {"username":username}).then(function(response) {
    			return response.data;
    		});
        },
    	createUser: function(user) {
        	return $http.post('http://localhost:8080/ComplaintService/service/user/createUser', user).then(
        	function(response) {
        		return response.data;
        	});
       }
    };
}]);
/*js file*/
angular.module('complaints.controllers').controller('ResetController', ['ResetService','$scope','$location','$routeParams',function(ResetService,$scope,$location,$routeParams) {
    $scope.resetToken = $routeParams.token;
    
    $scope.forgottenRadio = 'Username';

    if($scope.resetToken!==""){
        ResetService.validateToken($scope.resetToken).then(
        function(data) {
            $scope.tokenValid = data;
        });
    }

	$scope.resetPassword = function(username){
        ResetService.resetPassword({"username":username, "requestUrl":$location.absUrl()}).then(
		function(data) {
			$scope.resetPasswordResult = data;
		});
	};
	
	$scope.usernameReminder = function(email) {
		ResetService.usernameReminder(email).then(
			function(data) {
				$scope.usernameReminderResult = data;
			});
	};

    $scope.updatePassword = function (user) {
        ResetService.updatePassword({"username":$scope.tokenValid.username, "password":user.password}).then(
        function(data) {
            $scope.passwordUpdateResult = data;
        });
    };
}]);
/*js file*/
angular.module("complaints.services").factory("ResetService", ['$http', function($http) {
	return {
        resetPassword: function(resetTransfer) {
    		return $http.post('http://localhost:8080/ComplaintService/service/user/resetPassword', resetTransfer).then(function(response) {
    			return response.data;
    		});
        },
        usernameReminder: function(email) {
        	return $http.get('http://localhost:8080/ComplaintService/service/user/forgottenUsername/'+email).then(function(response) {
                return response.data;
            });
        },
        validateToken: function(token) {
            return $http.get('http://localhost:8080/ComplaintService/service/user/resetPassword/'+token).then(function(response) {
                return response.data;
            });
        },
        updatePassword: function(userAuth) {
            return $http.post('http://localhost:8080/ComplaintService/service/user/updatePassword', userAuth).then(function(response) {
                return response.data;
            });
        }
    };
}]);