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
			['ngCordova','complaints.controllers', 'complaints.services',
					'complaints.directives', 'complaints.filters', 'ionic','openfb']).value('version', '0.1');

	complaintsApp.config([ '$urlRouterProvider','$httpProvider','$stateProvider', function($urlRouterProvider,$httpProvider,$stateProvider) {
		$httpProvider.interceptors.push('authInterceptor');

	$stateProvider.state('login', {
			url : '/login',
			templateUrl : 'complaints/login/login.html',
			controller:'LoginController'
		}).state('register', {
			url : '/register',
			templateUrl : 'complaints/register/register.html',
			controller:'RegisterController'
		}).state('reset', {
			url : '/reset',
			templateUrl : 'complaints/reset/reset.html',
			controller:'ResetController'
		}).state('home', {
			url : '/home',
			templateUrl : 'complaints/home/home.html',
			controller:'HomeController'
		}).state('create', {
			url : '/create',
			templateUrl : 'complaints/create/create.html',
			controller:'CreateController'
		}).state('changePassword', {
			url : '/changePassword',
			templateUrl : 'complaints/changePassword/changePassword.html',
			controller:'ChangePasswordController'
		}).state('complaint', {
			url : '/complaint/:complaintId',
			templateUrl : 'complaints/complaint/complaint.html',
			controller:'ComplaintController'
		});
		// if none of the above states are matched, use this as the fallback
  	$urlRouterProvider.otherwise('/login');
	}
	]);
	
	complaintsApp.run(['OpenFB', function(OpenFB) {
		OpenFB.init('1478891292368940');
	}
	]);

}());