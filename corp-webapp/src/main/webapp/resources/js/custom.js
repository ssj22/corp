var myApp = angular.module('myApp', [ "ngRoute", "ngGrid", "autocomplete", "ngIdle", "ui.bootstrap"]), permissionList = null;

myApp.config(function($idleProvider, $keepaliveProvider) {
    // configure $idle settings
    $idleProvider.idleDuration(10); // in seconds
    $idleProvider.warningDuration(10); // in seconds
    $keepaliveProvider.interval(20*60); // in minutes
    $keepaliveProvider.http('rest/dummyweight');

})
.run(function($keepalive, $http, $rootScope){
    // start watching when the app runs. also starts the $keepalive service by default.
    $keepalive.start();
		$http({
			method : 'GET',
			url : 'rest/logindata'
		}).then(function(response) {
			$rootScope.loginuser = response.data.user;
			$rootScope.isAdmin = $rootScope.loginuser.adminUser;
		});
});

myApp.config(function($httpProvider) {
  var interceptor = ['$rootScope', '$q', function(scope, $q) {
 
      function success(response) {
        return response;
      }
 
      function error(response) {
        var status = response.status;
 
        if (status === 401) {
          var deferred = $q.defer();
          var req = {
            config: response.config,
            deferred: deferred
          };
          scope.requests401.push(req);
          scope.$broadcast('event:loginRequired');
          return deferred.promise;
        }
        // otherwise
        return $q.reject(response);
 
      }
 
      return function(promise) {
        return promise.then(success, error);
      }
 
    }];
  $httpProvider.responseInterceptors.push(interceptor);
});

myApp.run(['$rootScope', '$http', function(scope, $http) {
 
  /**
   * Holds all the requests which failed due to 401 response.
   */
  scope.requests401 = [];
 
  /**
   * On 'event:loginConfirmed', resend all the 401 requests.
   */
  scope.$on('event:loginConfirmed', function() {
	 // console.log("loginConfirmed called ");
    var i, requests = scope.requests401;
    for (i = 0; i < requests.length; i++) {
      retry(requests[i]);
    }
    scope.requests401 = [];
 
    function retry(req) {
      $http(req.config).then(function(response) {
        req.deferred.resolve(response);
      });
    }
  });
 
  /**
   * On 'event:loginRequest' send credentials to the server.
   */
  scope.$on('event:loginRequired', function(event, username, password) {
	//console.log("loginRequired called with " + event + username + password);  
	  
    var payload = $.param({j_username: username, j_password: password});
    var config = {
      headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    };
    $http.post('j_spring_security_check', payload, config).success(function(data) {
      if (data === 'AUTHENTICATION_SUCCESS') {
        scope.$broadcast('event:loginConfirmed');
      }
    });
  });
 
  /**
   * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
   */
  scope.$on('event:logoutRequest', function() {
    $http.put('j_spring_security_logout', {}).success(function() {
    	console.log("logoutRequest called after spring logout success"); 	
    	ping();
    });
  });
 
  /**
   * Ping server to figure out if user is already logged in.
   */
  function ping() {
	  //console.log("ping called");
    $http.get('rest/dummyweight').success(function() {
      scope.$broadcast('event:loginConfirmed');
    });
  }
  ping();
 
}]);

myApp.factory('permissions', function($rootScope, $http) {
	//var permissionList = null;
	return {
		setPermissions : function(permissions) {
			//console.log("set permissions called with permissions = " + permissions);
			permissionList = permissions;
			$rootScope.$broadcast('permissionsChanged');
		},
		hasPermission : function(permission) {
			//console.log("Inside HasPermission, before call " + permissionList);
			//console.log($rootScope.loginuser.privileges);
			if ($rootScope.loginuser == undefined || $rootScope.loginuser.userId == undefined) {return;}

			if (permissionList == null) {
				//console.log("permissionList is null. Hence calling logindata");
				$http({
					method : 'GET',
					url : 'rest/privileges?userId=' + $rootScope.loginuser.userId
				}).then(function(response) {
					//console.log("Rest Call Completed");
					permissionList = response.data;
					$rootScope.$broadcast('permissionsChanged');
					//console.log($rootScope.loginuser.firstName);
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

myApp.directive('datepick', function () {
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
					//console.log(attrs);
					//console.log(element);
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
						//console.log("Reached Toggle Visibility for element " + element[0].innerText + " with value = " + value + " and hasPermission = " + hasPermission);
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
	}).when("/Users", {
		templateUrl : "resources/users.html",
		controller : "UserCtrl"
	}).when("/Setup", {
		templateUrl : "resources/setup.html",
		controller : "SetupCtrl"
	}).when("/Log", {
		templateUrl : "resources/log.html",
		controller : "LogCtrl"
	}).when("/logout", {
		templateUrl : "login.html"
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

myApp.controller("ViewCtrl", function($scope, $http, $location, $rootScope, permissions, RestService, $window) {
	$scope.logout = function() {
		//console.log("logoutRequest called after spring logout success");
		$http.post('j_spring_security_logout', {}).success(function() {
	    	 	
	    	$window.location.reload();
	    });
	}
	
	$rootScope.loading = 0;
	$scope.$watch(function() {
		//console.log($location.path());	
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
	//console.log("before logindata")
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
