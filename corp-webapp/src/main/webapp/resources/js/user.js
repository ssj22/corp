angular.module('myApp').controller("UserCtrl", function($scope, RestService, $http, $rootScope, $window) {
	  $scope.userActionFlag = false;
	  $scope.searchUserFlag = false;
	  $scope.savesuccess = false;
	  $scope.loadPermissions = function(typed) {
		  //console.log("LoadPermissions called with typed = " + typed);
		  $scope.selectedUser = $scope.userMap[typed];
		  console.log($scope.selectedUser);
		  $http.get("rest/privileges?userId=" + $scope.userMap[typed].userId).then(function(response) {
			 $scope.selItems = Object.keys(response.data);
			 $scope.selItemMap = response.data;
			 //console.log($scope.selItems);
			 $http.get("rest/privileges").then(function(response) {
				 $scope.items = Object.keys(response.data);
				 $scope.itemMap = response.data;
				 for (i = 0; i < $scope.items.length; i++) {
					 //console.log("i = " + i);
					 //console.log("for " + $scope.items[i] + ", index is " + $scope.selItems.indexOf($scope.items[i]));
					 if ($scope.selItems.indexOf($scope.items[i]) > -1) {
						 $scope.items.splice(i--, 1);
					 }
			     }
			  });
		  });	 
		  $scope.userActionFlag = true;
		  $scope.savesuccess = false;
	  };
	  
	  $scope.editUser = function() {
		  $scope.searchUserFlag = true;
		  $http.get("rest/usernames").then(function(response) {
			  $scope.users = Object.keys(response.data);
			  $scope.userMap = response.data;
			  console.log($scope.userMap);
		  });
		  $scope.savesuccess = false;
	  };
	  
	  $scope.deselectAllPriv = function() {
		  $scope.items = Object.keys($scope.itemMap);
		  $scope.selItems = [];
	  };
	  
	  $scope.selectAllPriv = function() {
		  $scope.selItems = Object.keys($scope.itemMap);
		  $scope.items = [];
	  };
	  
	  $scope.createUser = function() {
		  console.log("createUser called");
		  $scope.searchUserFlag = false;
		  $scope.userActionFlag = true;
		  $scope.selectedUser = {};
		  $http.get("rest/privileges").then(function(response) {
			 $scope.items = Object.keys(response.data);
			 $scope.itemMap = response.data;
			 $scope.selItems = [];
			 $scope.selItemMap = {};
		  });
		  $scope.savesuccess = false;
	  };
	  
	  $scope.savePermissions = function() {
		  console.log("selectedUser = " + $scope.selectedUser.firstName);
		  var privMap = {};
		  $scope.users = {};
		  $scope.searchUserFlag = false;
		  for (i = 0; i < $scope.selItems.length; i++) {
			  //console.log($scope.selItems[i]);
			  privMap[$scope.selItems[i]] = $scope.itemMap[$scope.selItems[i]];
		  }
		  $scope.selectedUser.privileges = privMap;
		  console.log("selectedUser = " + $scope.selectedUser.privileges);
		  $http({
				method : 'POST',
				url : 'rest/users?by=' + $rootScope.loginuser.userId,
				data : $scope.selectedUser,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).then(function() {
				$scope.userActionFlag = false; 
		  });
		  $scope.savesuccess = true;
		  //$window.location.reload();
	  }
	  
	  $scope.selItem = function(item) {
		  console.log("Selected Item is " + item);
		  
		  $scope.items.splice($scope.items.indexOf(item), 1);
		  
		  $scope.selItems.splice(-1, 0, item);
		  $scope.items.sort();
		  $scope.selItems.sort();
	  }
	  
	  $scope.unSelItem = function(item) {
		  console.log("UnSelected Item is " + item);
		  var index = $scope.selItems.indexOf(item);
		  $scope.selItems.splice(index, 1);
		  $scope.items.splice(-1, 0, item);
		  $scope.items.sort();
		  $scope.selItems.sort();
	  }

	  $scope.addItem = function() {
	    var newItemNo = $scope.items.length + 1;
	    $scope.items.push('Item ' + newItemNo);
	  };

	  $scope.status = {
	    isFirstOpen: true,
	    isFirstDisabled: false
	  };
});