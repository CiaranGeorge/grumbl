angular.module('complaints.directives').directive( 'heightTarget',['$window', function ($window) {

    return {
    	
        restrict: 'A',

        link: function (scope, elem, attrs) {
            var height = $window.outerHeight;
            height += 2;
            elem.css('height', height+ 'px');
        }
    };
}]);