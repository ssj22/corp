angular.module('myApp').controller("SetupCtrl", function($scope, RestService, Util, $http, $rootScope, $window) {
	$scope.myData = [];
	$scope.editready = false;
	$scope.selectedRow = [];
	$scope.editMode = false;
	$scope.stockTypelist = [];
	$scope.pgList = [];
	$scope.vibhagTypelist = [];
	//$scope.saveReady = false;
	$scope.stockTypeMap = {};
	$scope.pgMap = {};
	$scope.vibhagTypeMap = {};

	$rootScope.loading++;
	$http.get("rest/vibhagTypes").success(function(data) {
		var vibhagTypelist = [];
		for (var i = 0, l = data.length; i < l; i++) {
			vibhagTypelist[i] = data[i].vibhagTypeName;
			$scope.vibhagTypeMap[data[i].vibhagTypeName] = data[i];
		}
		$scope.vibhagTypelist = vibhagTypelist;
		$rootScope.loading--;

	});

	$rootScope.loading++;
	RestService.getPGNames().success(function(data) {
		
		var pgList = [];
		for (var i = 0, l = data.length; i < l; i++) {
			pgList[i] = data[i].vendorName;
			$scope.pgMap[data[i].vendorName] = data[i];
		}
		$scope.pgList = pgList;
		//console.log($scope.pgList);
		$rootScope.loading--;
	});

	$rootScope.loading++;
	$http.get("rest/items").success(function(data) {
		var stockTypelist = [];
		for (var i = 0, l = data.length; i < l; i++) {
			stockTypelist[i] = data[i].mainItemname;
			$scope.stockTypeMap[data[i].mainItemname] = data[i];
		}
		$scope.stockTypelist = stockTypelist;
		$rootScope.loading--;

	});

	$scope.vibhagTypeSelect = function(value) {
		if ($scope.vibhagTypelist.indexOf(value) > -1) {
			$scope.saveReady = true;
			$scope.selection.vibhagType = $scope.vibhagTypeMap[$scope.selection.vibhagType.vibhagTypeName];
		}
		else {
			$scope.saveReady = false;
		}
	};

	$scope.vendorSelect = function(value) {
		if ($scope.pgList.indexOf(value) > -1) {
			$scope.saveReady = true;
			$scope.selection.vendor = $scope.pgMap[$scope.selection.vendor.vendorName];
			console.log($scope.selection.vendor);
		}
		else {
			$scope.saveReady = false;
		}
	}

	$scope.stockTypeSelect = function(value) {
		if ($scope.stockTypelist == null || $scope.stockTypelist.indexOf(value) > -1) {
			$scope.saveReady = true;
			$scope.selection.item = $scope.stockTypeMap[$scope.selection.item.mainItemname];
		}
		else {
			$scope.saveReady = false;
		}
	}

	$scope.editEntry = function() {
		$scope.selection = $scope.selectedRow[0];
		$scope.grossDisabled = true;
		$scope.tareDisabled = true;
		if ($scope.selection.entryType == 1) {
			if ($scope.selection.status == "INITIATED") {
				$scope.tareDisabled = false;
			}
		} else {
		    if ($scope.selection.status == "INITIATED") {
				$scope.grossDisabled = false;
			}
		}
		$scope.editMode = true;
		if ($scope.num == 3 || $scope.num == 11 || $scope.num == 44) {
			$scope.saveReady = true;
		}
	};

	$scope.createEntry = function() {
		$scope.selection = null;
		$scope.editMode = true;
		if ($scope.num == 3 || $scope.num == 11 || $scope.num == 44) {
			$scope.saveReady = true;
		}
	};

	$scope.cancelEntry = function() {
		//console.log($scope.selectedRow[0]);
		$scope.selection = $scope.selectedRow[0];
		$scope.editMode = false;
	};
	
	$scope.saveEntry = function() {
		$scope.selection.userId = $rootScope.userId;
		$scope.selection.createdBy = $rootScope.userId;
		$rootScope.loading++;
		console.log($scope.selection);
		$http({
			method : 'POST',
			url : $scope.url,
			data : $scope.selection,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response) {
			$rootScope.loading--;
			$scope.gridOptions.selectAll(false);
			$scope.getPagedDataAsync(
					$scope.pagingOptions.pageSize,
					$scope.pagingOptions.currentPage);
		});	
		$scope.editMode = false;
	};
	
	$scope.setEntity = function(no) {
		$window.sessionStorage.setItem("setupEntity", no);
		$scope.num = no;
		if ($scope.num == 2) {
			$scope.entity = "Vehicle";
			$scope.url = "rest/vehicles";
		}
		else if ($scope.num == 3) {
			$scope.entity = "Vendor";
			$scope.url = "rest/primaryGroups";
			$scope.saveReady = true;	
		}	
		else if ($scope.num == 4) {
			$scope.entity = "Stock Item";
			$scope.url = "rest/stockItems";
		}
		else if ($scope.num == 11) {
			$scope.entity = "Vibhag Type";
			$scope.url = "rest/vibhagTypes";
		}
		else if ($scope.num == 44) {
			$scope.entity = "Item Main";
			$scope.url = "rest/items";
		}
		else {
			$scope.entity = "Vibhag";
			$scope.url = "rest/vibhags";	
		}
		$window.sessionStorage.setItem("setupEntityName", $scope.entity);
		$window.sessionStorage.setItem("setupUrl", $scope.url);
			
		$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
					$scope.pagingOptions.currentPage);
		$window.location.reload();			
	}
	
	var setupEntity = $window.sessionStorage.getItem("setupEntity");
	console.log("setupEntity in Session = " + setupEntity);
	if (setupEntity == undefined || setupEntity == null) {
		$window.sessionStorage.setItem("setupEntity", 1);
		$scope.num = 1;
		$scope.url = "rest/vibhags";	
		$scope.entity = "Vibhag";
	}
	else {
		$scope.num = setupEntity;
		$scope.url = $window.sessionStorage.getItem("setupUrl");;	
		$scope.entity = $window.sessionStorage.getItem("setupEntityName");;
	}
	
	//console.log(setupEntity)
	
	$scope.filterOptions = {
		filterText : "",
		useExternalFilter : false
	};

	$scope.pagingOptions = {
		pageSizes : [ 10, 50, 100, 500 ],
		pageSize : 100,
		currentPage : 1
	};
    
	//$scope.setEntity(setupEntity);
	
	$scope.setPagingData = function(data, page, pageSize) {
		// console.log("Entered Paging Data with " + page + ","
		// + pageSize);
		// console.log("Data Size " + data.length);
		var pagedData = data.slice((page - 1) * pageSize, page
				* pageSize);
		$scope.myData = pagedData;
		// console.log("MyData Size " + $scope.myData.length);
		$scope.totalServerItems = data.length;
		// console.log("Total Sever Items = " + data.length);
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	};
	$scope.getPagedDataAsync = function(pageSize, page,
			searchText) {
		// console.log("Entered Paging Data Async with " + page
		// + "," + pageSize + ", " + searchText);
		setTimeout(function() {
			var data;
			if (searchText) {
				// console.log("searchText = " +
				// searchText);
				var ft = searchText.toLowerCase();
				$http.get($scope.url).success(
						function(largeLoad) {
							data = largeLoad.data.filter(function(
									item) {
								return JSON.stringify(item)
										.toLowerCase().indexOf(
												ft) != -1;
							});
							$scope.setPagingData(data, page,
									pageSize);
						});
			} else {
				$rootScope.loading++;
				console.log("$scope.url = " + $scope.url);
				$http.get($scope.url).success(function(largeLoad) {
					$rootScope.loading--;
					console.log(largeLoad[0]);
					$scope.setPagingData(
							largeLoad, page,
							pageSize);
				});
			}
		}, 100);
	};

	$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
				$scope.pagingOptions.currentPage);
	
	$scope.$watch('pagingOptions',
					function(newVal, oldVal) {
						if (newVal !== oldVal
								&& (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)) {
							$scope.gridOptions.selectAll(false);
							$scope
									.getPagedDataAsync(
											$scope.pagingOptions.pageSize,
											$scope.pagingOptions.currentPage,
											$scope.filterOptions.filterText);
						}
					}, true);

	$scope.$watch('filterOptions', function(newVal, oldVal) {
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
		jqueryUITheme : true,
		totalServerItems : 'totalServerItems',
		pagingOptions : $scope.pagingOptions,
		filterOptions : $scope.filterOptions,
		multiSelect : false,
		selectedItems : $scope.selectedRow,
		afterSelectionChange : selcFunc = function() {
			var selection = $scope.selectedRow;
			if (selection == null || selection == "" || selection.length == 0) {
				$scope.editready = false;
				return;
			}
			else {
				$scope.editready = true;
			}
		},
		showFilter : true,
		showColumnMenu : true,
		keepLastSelected : false,
		rowTemplate : '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell ng-click="selectRow()"></div>',
		columnDefs : Util.getSetupColumnDefs($scope.num)
	};	  
});