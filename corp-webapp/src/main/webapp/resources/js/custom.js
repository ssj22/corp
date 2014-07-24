var myApp = angular.module('myApp', [ "ngRoute", "ngGrid", "ngQuickDate" ]), permissionList = null;

myApp.factory('counts', function($rootScope, $http) {
	return {
		getCount: function() {
			$http.get('rest/counts').then(function(response) {
				$rootScope.counts = response.data;
			});
		}
	};
});

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

myApp.controller(
				"MaterialsCtrl",
				function($scope, $http, $interval, counts) {
					$scope.times = [ {
						name : 'Today',
						val : '1'
					}, {
						name : 'Week',
						val : '7'
					}, {
						name : 'Month',
						val : '30'
					}, {
						name : 'Quarter',
						val : '90'
					}, {
						name : "Year",
						val : '365'
					} ];
					$scope.sel = $scope.times[0];
					$scope.disabled = true;
					$scope.printready = false;
					$scope.selectedRow = [];
					$scope.myData = [];
					$scope.readOnly = true;
					$scope.editMode = false;
					$scope.grossBtnVisible = false;
					$scope.tareBtnVisible = false;
					
					counts.getCount();

					$scope.printEntry = function() {
						$scope.selection = $scope.selectedRow[0];
					};
					
					$scope.editEntry = function() {
						// console.log("selectedRow = " +
						$scope.selection = $scope.selectedRow[0];
						$scope.readOnly = false;
						$scope.editMode = true;
						$scope.weight();
					};

					$scope.createEntry = function() {
						// console.log($scope.sel.val);
						$scope.selection = null;
						$scope.readOnly = false;
						$scope.editMode = false;
					};

					$scope.cancelEntry = function() {
						$scope.readOnly = true;
						$scope.stopWeight();
					};

					$scope.saveEntry = function() {
						// console.log($scope.selection);
						$http({
							method : 'POST',
							url : 'rest/materials',
							data : $scope.selection,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).then(
								function(response) {
									// console.log(response.data);
									$scope.stopWeight();
									$scope.readOnly = true;
									counts.getCount();
									$scope.getPagedDataAsync(
											$scope.pagingOptions.pageSize,
											$scope.pagingOptions.currentPage);
								});
					};

					$http({
						method : 'GET',
						url : 'rest/vibhags'
					}).then(function(response) {
						$scope.vibhags = response.data;
					});

					$http({
						method : 'GET',
						url : 'rest/vehicles'
					}).then(function(response) {
						$scope.vehicles = response.data;
					});

					$http({
						method : 'GET',
						url : 'rest/stockItems'
					}).then(function(response) {
						$scope.stocks = response.data;
					});

					$http({
						method : 'GET',
						url : 'rest/PrimaryGroups'
					}).then(function(response) {
						$scope.pgs = response.data;
					});

					$scope.getWeight = function() {
						$http({
							method : 'GET',
							url : 'rest/weight'
						})
						.then(
							function(response) {
								if ($scope.selection.entryType == 1) {
									if ($scope.selection.status == null) {
										$scope.selection.grossWt = response;
									} else if ($scope.selection.status == "INITIATED") {
										$scope.selection.tareWt = response;
									}
								} else {
									if ($scope.selection.status == null) {
										$scope.selection.tareWt = response;
									} else if ($scope.selection.status == "INITIATED") {
										$scope.selection.grossWt = response;
									}
								}
							});
					};

					var stop;
					$scope.weight = function() {
						if ( angular.isDefined(stop) ) return;
						
						stop = $interval(function() {
							$scope.getWeight();
						},100);
					};
						
					$scope.stopWeight = function() {
						if (angular.isDefined(stop)) {
							$interval.cancel(stop);
							stop = undefined;
						}
				    };
					
				    $scope.$on('$destroy', function() {
				        // Make sure that the interval nis destroyed too
				        $scope.stopWeight();
				      });    
					    
					$scope.weightBtns = function() {
						// console.log($scope.selection.status);
						$scope.weight();
					};

					$scope.timeSelected = function() {
						// console.log("Inside timeselected" + $scope.sel.val);
						$scope.pagingOptions.currentPage = 1;
						$http({
							method : 'GET',
							url : 'rest/materials?time=' + $scope.sel.val
						})
						.then(
							function(response) {
								$scope.myData = response.data;
								$scope.totalServerItems = $scope.myData.length;
								$scope.setPagingData(
										$scope.myData,
										$scope.pagingOptions.currentPage,
										$scope.pagingOptions.pageSize);
							});
					};

					$scope.loadMore = function() {
						var page = $scope.totalServerItems;
						var size = page;
						// console.log("page = " + page + ", size = " + size);
						$http({
								method : 'GET',
								url : 'rest/materials?page=' + page
										+ '\&size=' + size + "\&more=true"
						 })
						.then(
							function(response) {
								// console.log(response.data);
								if (response.data.length > 0) {
									// console.log("Before: " +
									// $scope.myData);
									$scope.myData.push.apply(
											$scope.myData,
											response.data);
									// console.log("After: " +
									// $scope.myData);
									$scope.totalServerItems = $scope.myData.length;
									console.log($scope.totalServerItems);
									$scope.setPagingData(
											$scope.myData,
											$scope.pagingOptions.currentPage,
											$scope.pagingOptions.pageSize);
									console.log($scope.totalServerItems);
								}
							});
					};

					$scope.filterOptions = {
						filterText : "",
						entryType : [ 1, 2 ],
						useExternalFilter : false
					};

					$scope.pagingOptions = {
						pageSizes : [ 10, 50, 100 ],
						pageSize : 10,
						currentPage : 1
					};

					$scope.filterInOut = function(type) {
						//console.log(type);
						var index = $scope.filterOptions.entryType
								.indexOf(type);
						if (index == -1) {
							$scope.filterOptions.entryType.push(type);
						} else {
							$scope.filterOptions.entryType.splice(index, 1);
						}

					};

					$scope.setPagingData = function(data, page, pageSize) {
						//console.log("Entered Paging Data with " + page + "," + pageSize);
						//console.log("Data Size " + data.length);
						var pagedData = data.slice((page - 1) * pageSize, page
								* pageSize);
						$scope.myData = pagedData;
						//console.log("MyData Size " + $scope.myData.length);
						$scope.totalServerItems = data.length;
						 //console.log("Total Sever Items = " + data.length);
						if (!$scope.$$phase) {
							$scope.$apply();
						}
					};
					$scope.getPagedDataAsync = function(pageSize, page,
							searchText) {
						//console.log("Entered Paging Data Async with " + page + "," + pageSize + ", " + searchText);
						setTimeout(
								function() {
									var data;
									if (searchText) {
										// console.log("searchText = " +
										// searchText);
										var ft = searchText.toLowerCase();
										$http
											.get(
													'rest/materials?page='
															+ page
															+ '&size='
															+ pageSize)
											.success(
												function(largeLoad) {
													data = largeLoad
															.filter(function(
																	item) {
																return JSON
																		.stringify(
																				item)
																		.toLowerCase()
																		.indexOf(
																				ft) != -1;
															});
													$scope.setPagingData(
															data,
															page,
															pageSize);
												});
									} else {
										//console.log("getPagesDataAsync with pageSize = " + $scope.myData.length);
										var size = $scope.totalServerItems;
										// console.log(size);
										if (typeof size == 'undefined') {
											size = $scope.sel.val;
										}
										$http.get(
											'rest/materials?time=' + $scope.sel.val)
												.success(
												function(largeLoad) {
													//console.log("there is no searchText");
													$scope.setPagingData(
														largeLoad,
														page,
														pageSize);
												});
									}
								}, 100);
					};

					$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
							$scope.pagingOptions.currentPage);

					$scope.$watch(
						'pagingOptions',
						function(newVal, oldVal) {
							console.log("Paging Options: newVal.pageSize = " + newVal.pageSize 
							+ ", oldVal.pageSize = " + oldVal.pageSize +
							", newVal.currentPage = " + newVal.currentPage + ", oldVal.currentPage = " +
							oldVal.currentPage);
							if (newVal !== oldVal
									&& (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)) {
								$scope
										.getPagedDataAsync(
												$scope.pagingOptions.pageSize,
												$scope.pagingOptions.currentPage,
												$scope.filterOptions.filterText);
							}
						}, true);
					
					$scope.$watch('filterOptions', function(newVal, oldVal) {
						console.log("Filter Options: newVal.pageSize = "
								+ newVal.pageSize + ", oldVal.pageSize = "
								+ oldVal.pageSize + ", newVal.currentPage = "
								+ newVal.currentPage
								+ ", oldVal.currentPage = "
								+ oldVal.currentPage + ", newVal.entryType = "
								+ newVal.entryType + ", oldVal.entryType = "
								+ oldVal.entryType);
						if (newVal !== oldVal) {
							$scope.getPagedDataAsync(
									$scope.pagingOptions.pageSize,
									$scope.pagingOptions.currentPage,
									$scope.filterOptions.filterText);
						}
					}, true);

					$scope.gridOptions = {
						data : 'myData',
						enablePaging : true,
						showFooter : true,
						enableColumnReordering : true,
						enableColumnResize : true,
						totalServerItems : 'totalServerItems',
						pagingOptions : $scope.pagingOptions,
						filterOptions : $scope.filterOptions,
						jqueryUITheme : true,
						multiSelect : false,
						selectedItems : $scope.selectedRow,
						afterSelectionChange : function() {
							if ($scope.selectedRow != "") {
								$scope.disabled = false;
								// console.log($scope.selectedRow[0].status);
								if ($scope.selectedRow[0].status == "COMPLETED") {
									$scope.printready = true;
								} else {
									$scope.printready = false;
								}

							} else {
								$scope.disabled = true;
								$scope.printready = false;
							}
						},
						showFilter : true,
						showColumnMenu : true,
						keepLastSelected : false,
						rowTemplate : '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell ng-click="selectRow()"></div>',
						columnDefs : [ {
							field : 'materialId',
							displayName : 'S.No.',
							width : '70px'
						}, {
							field : 'entryTypeText',
							displayName : 'Type',
							width : '70px'
						}, {
							field : 'challanNo',
							displayName : 'Challan',
							width : '70px',
							visible : false
						},{
							field : 'status',
							displayName : 'Status',
							width : '130px'
						}, {
							field : 'vendorName',
							displayName : 'Vendor',
							width : '160px'
						}, {
							field : 'stockName',
							displayName : 'Stock',
							width : '160px'
						}, {
							field : 'transportName',
							displayName : 'Transport',
							width : '90px',
							visible : false
						}, {
							field : 'vehicleNumber',
							displayName : 'Vehicle',
							width : '130px'
						}, {
							field : 'vehicleInTime',
							displayName : 'Vehicle In Time',
							cellFilter : 'date:\"MM/dd/yyyy @ h:mma\"',
							width : '220px'
						}, {
							field : 'vehicleOutTime',
							displayName : 'Vehicle Out Time',
							cellFilter : 'date:\"MM/dd/yyyy @ h:mma\"',
							width : '220px',
							visible : false
						}, {
							field : 'grossWt',
							displayName : 'GrossWt',
							width : '90px'
						}, {
							field : 'netWt',
							displayName : 'NetWt',
							width : '90px'
						}, {
							field : 'tareWt',
							displayName : 'TareWt',
							width : '90px'
						}, {
							field : 'vibhagName',
							displayName : 'Vibhag',
							width : '130px',
							visible : false
						}, {
							field : 'orderId',
							displayName : 'Order ID',
							width : '90px',
							visible : false
						} ]
					};

				});

myApp.config([ "$routeProvider", function($routeProvider) {
	$routeProvider.when("/Home", {
		controller : "ViewCtrl",
		templateUrl : "resources/home.html"
	}).when("/Materials", {
		templateUrl : "resources/materials.html",
		controller : "MaterialsCtrl"
	}).when("/EditMaterials", {
		templateUrl : "resources/editmaterial.html",
		controller : "MaterialsCtrl"
	}).when("/Reports", {
		templateUrl : "resources/reports.html",
		controller : "ReportsCtrl"
	}).when("/Setup", {
		templateUrl : "resources/setup.html",
		controller : "SetupCtrl"
	}).otherwise({
		
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

myApp.controller("SetupCtrl", function($scope) {

});

myApp.controller("ViewCtrl", function($scope, $http, $location, $rootScope, permissions, counts) {
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
	
	
	$http({
		method : 'GET',
		url : 'rest/login'
	}).then(function(response) {
		$rootScope.loginuser = response.data.user;
		//console.log($rootScope.loginuser.privileges);
		//permissionList = response.data.user.privileges;
		// console.log($rootScope.loginuser.firstName);
		//angular.bootstrap(document, [ 'myApp' ]);
	});
	
	counts.getCount();

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

});
