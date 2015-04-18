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