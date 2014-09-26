var myApp = angular.module('myApp', [ "ngRoute", "ngGrid", "ngQuickDate", "autocomplete" ]), permissionList = null;

myApp.factory('permissions', function($rootScope, $http) {
	//var permissionList = null;
	return {
		setPermissions : function(permissions) {
			permissionList = permissions;
			$rootScope.$broadcast('permissionsChanged');
		},
		hasPermission : function(permission) {
			//console.log("Inside HasPermission, before call " + permissionList);
			if (permissionList == null) {
				
				$http({
					method : 'GET',
					url : 'rest/login'
				}).then(function(response) {
					//console.log("Rest Call Completed");
					$rootScope.loginuser = response.data.user;
					//console.log($rootScope.loginuser.privileges);
					permissionList = $rootScope.loginuser.privileges;
					$rootScope.$broadcast('permissionsChanged');
					// console.log($rootScope.loginuser.firstName);
					//angular.bootstrap(document, [ 'myApp' ]);
				});
			}
			
			permission = permission.trim();
			return _.some(permissionList, function(item) {
				//console.log("Item is " + item);
				if (_.isString(item))
					//console.log("Item Name is " + item.Name + " and permission is " + permission);
					return item.trim() === permission;
			});
		}
	};
});

myApp.run(function(permissions) {
	permissions.setPermissions(permissionList);
});

myApp.directive('datepicker', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
         link: function (scope, element, attrs, ngModelCtrl) {
            element.datepicker({
                dateFormat: 'd  MM, yy',
                onSelect: function (date) {
                    scope.date = date;
                    scope.$apply();
                }
            });
        }
    };
});

myApp.directive(
		'hasPermission',
		function(permissions) {
			return {
				link : function(scope, element, attrs) {
					if (!_.isString(attrs.hasPermission))
						throw "hasPermission value must be a string";

					var value = attrs.hasPermission.trim();
					//console.log(value);
					var notPermissionFlag = value[0] === '!';
					if (notPermissionFlag) {
						value = value.slice(1).trim();
					}

					function toggleVisibilityBasedOnPermission() {
						var hasPermission = permissions.hasPermission(value);
						//console.log("hasPermission is " + hasPermission + " for value = " + value);
						if (hasPermission && !notPermissionFlag
								|| !hasPermission && notPermissionFlag)
							element.show();
						else
							element.hide();
					}
					toggleVisibilityBasedOnPermission();
					scope.$on('permissionsChanged',
							toggleVisibilityBasedOnPermission);
				}
			};
		});


myApp.config([ "$routeProvider", function($routeProvider) {
	$routeProvider.when("/Home", {
		controller : "ViewCtrl",
		templateUrl : "resources/home.html"
	}).when("/Materials", {
		templateUrl : "resources/materials.html",
		controller: "MaterialsCtrl"
	}).when("/Reports", {
		templateUrl : "resources/reports.html",
		controller : "ReportsCtrl"
	}).when("/Setup", {
		templateUrl : "resources/setup.html",
		controller : "MaterialsCtrl"
	}).when("/Gate-Log", {
		templateUrl : "resources/gatelog.html",
		controller : "GateCtrl"
	})
	.otherwise({
		
		redirectTo : "/Home"
	});
} ]);

myApp.controller("ReportsCtrl", function($scope, $http) {
	$http({
		method : 'GET',
		url : 'rest/reports'
	}).then(function(response) {
		$scope.reports = response.data;
	});
});

myApp.controller("GateCtrl", function($scope, $http, RestService) {
	
});

myApp.controller("SetupCtrl", function($scope, RestService) {
	$scope.selection = RestService.getCurrSelection();
	console.log($scope.selection.materialId);
});

myApp.controller("ViewCtrl", function($scope, $http, $location, $rootScope, permissions, RestService) {
	$rootScope.loading = 0;
	$scope.$watch(function() {
	    return $location.path();
	 }, function(){
		if ($location.path() == "/Home") {
			$scope.showfoooter = true;
		}
		else {
			$scope.showfoooter = false;
		}
	});
	//console.log($rootScope.loading);
	
	$http({
		method : 'GET',
		url : 'rest/login'
	}).then(function(response) {
		$rootScope.loginuser = response.data.user;
		//console.log($rootScope.loginuser.privileges);
		//permissionList = response.data.user.privileges;
	//	console.log($rootScope.loginuser.firstName);
		//angular.bootstrap(document, [ 'myApp' ]);
	});
	
	RestService.getCount();
	
	$http.get('rest/tabs').then(function(response) {
		$scope.tabs = response.data;
	});
	
	$scope.tabClass = "nav navbar-nav nav-tabs";

	$scope.loadTab = function(order) {
		//console.log($scope.tabs[order].tabName);
	};

	$scope.isTabActive = function(route) {
		return route === $location.path();
	};
	//console.log($rootScope.loading);
});
