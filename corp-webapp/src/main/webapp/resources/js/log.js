angular.module('myApp').controller("LogCtrl", function($scope, $http, $interval, RestService, CorpService, Util, $rootScope, $route, $window) {
	$scope.myData = [];
	$scope.selectedRow = [];
	$scope.showDetails = false;
	$scope.timeSelected = function(time) {
		// console.log("Inside timeselected" + $scope.timeSel);
		$scope.pagingOptions.currentPage = 1;
		$scope.timeSel = time;
		$rootScope.loading++;
		$http({
			method : 'GET',
			url : 'rest/logs?time=' + $scope.timeSel
		}).then(
			function(response) {
				$scope.myData = response.data;
				$scope.totalServerItems = $scope.myData.length;
				$rootScope.loading--;
				$scope
						.setPagingData(
								$scope.myData,
								$scope.pagingOptions.currentPage,
								$scope.pagingOptions.pageSize);
			});
	};
	
	$scope.timeSel = 1;
	
	$scope.rangeSelected = function() {
		// console.log("Inside timeselected" + $scope.timeSel);
		$scope.pagingOptions.currentPage = 1;
		$scope.timeSel = null;
		$rootScope.loading++;
		$http({
			method : 'GET',
			url : 'rest/logs?from=' + dateFrom.value + '&to=' + dateTo.value
		}).then(
			function(response) {
				$scope.myData = response.data;
				$rootScope.loading--;
				$scope.totalServerItems = $scope.myData.length;
				$scope.setPagingData($scope.myData,$scope.pagingOptions.currentPage,$scope.pagingOptions.pageSize);
			});
	};

	$scope.filterOptions = {
		filterText : "",
		useExternalFilter : false
	};

	$scope.pagingOptions = {
		pageSizes : [ 10, 50, 100 ],
		pageSize : 10,
		currentPage : 1
	};

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
				$http.get(
						'rest/logs?page=' + page
								+ '&size=' + pageSize).success(
						function(largeLoad) {
							data = largeLoad.filter(function(
									item) {
								return JSON.stringify(item)
										.toLowerCase().indexOf(
												ft) != -1;
							});
							$scope.setPagingData(data, page,
									pageSize);
						});
			} else {
				var url;
				if ($scope.timeSel != null) {
					url = 'rest/logs?time=' + $scope.timeSel
				}
				else {
					url = 'rest/logs?from=' + dateFrom.value + '&to=' + dateTo.value;
				}
				$rootScope.loading++;
				$http.get(url).success(function(largeLoad) {
					$rootScope.loading--;
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

	$scope.selectRow = function() {
		//console.log("SelectRow called " + $scope.selectedRow);
		//$scope.showDetails = true;
		//$window.alert($scope.selectedRow[0].msg);
	}

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
		afterSelectionChange : selcFunc = function() {
			var selection = $scope.selectedRow;
			$scope.showDetails = false;
			//console.log("Entered with selection length = " + selection.length );
			if (selection == null || selection == "" || selection.length == 0) {
				return;
			}
			else {
				//console.log(selection[0]);
				$scope.showDetails = true;
			}
		},
		showFilter : true,
		showColumnMenu : true,
		keepLastSelected : false,
		rowTemplate : '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell ng-click="selectRow()"></div>',
		columnDefs : Util.getLogColumnDefs()
	};
});