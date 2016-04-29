angular.module('myApp').controller("LogCtrl", function($scope, $http, $interval, RestService, CorpService, Util, $rootScope, $route, $window) {
	$scope.myData = [];
	$scope.selectedRow = [];
	$scope.showDetails = false;
	$scope.showSendSms = false;
    $scope.showSaveSms = false;
    $scope.showSms = true;
	$scope.numlist = null;
    $scope.newSms = null;
    $scope.pglist; // List of names of Primary Groups (Vendors)
    $scope.vibhaglist; // List of names of Vibhags (Engineers and Ward Offices)
    $scope.vehiclelist; // List of Vehicle numbers
    $scope.sitelist;
	
    $http.get("rest/contacts").then(function(response) {
	    $scope.groupContacts = response.data[0];
        $scope.indContacts = response.data[1];
        for (var i = 0, l = $scope.groupContacts.length; i < l; i++) {
            var obj = $scope.groupContacts[i];
            obj["class"] = "btn btn-default";
        }
        for (var i = 0, l = $scope.indContacts.length; i < l; i++) {
            var obj = $scope.indContacts[i];
            obj["class"] = "btn btn-default";
        }
	});	 

    $scope.saveGroup = function() {
        var postObject = new Object();
        postObject.contactName = $scope.groupName;
        postObject.contactNumber = $scope.numlist;
        postObject.groupInd = "true";
        postObject.contactActive = "true";

        $http({
            method : 'POST',
            url : 'rest/contacts',
            data : postObject,
            headers : {
                'Content-Type' : 'application/json',
                'Accept' : 'application/json'
            }
        }).success(function(response) {
            $window.location.reload();
        });
    }

	$scope.selItem = function(obj) {
        var btnClass = "btn btn-default";
        var value = obj["contactNumber"];
        if ($scope.numlist == null || $scope.numlist == "") {
            $scope.numlist = value;
            btnClass = "btn btn-success";
        }
		else {
            var index = $scope.numlist.indexOf(value);
            if (index == 0) {
                $scope.numlist = $scope.numlist.replace(value + ", ", "");
                $scope.numlist = $scope.numlist.replace(value, "");
            }
            else if (index > -1) {
                $scope.numlist = $scope.numlist.replace(", " + value, "");
            }
            else {
                $scope.numlist = $scope.numlist.concat(", ", value);
                btnClass = "btn btn-success";
            }

        }
        obj["class"] = btnClass;

	}
	
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
	
	$scope.sendSms = function() {
		$scope.showSendSms = false;		
	}
	
	$scope.broadcast = function() {
		$scope.showSendSms = true;
	}
	
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
                console.log($scope.myData[0]);
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
		pageSizes : [ 500, 1000, 5000 ],
		pageSize : 500,
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

    $scope.create = function() {
        $scope.showDetails = false;
        $scope.showSendSms = false;
        $scope.showCreateSms = true;
        $scope.vehicleVisible = false;
        $scope.showSite = false;
        $scope.showSms = false;
        $scope.showSaveSms = false;
        $scope.showMaterials = false;
        $scope.newSms = {
            msg: "",
            vehicleNumber: "",
            transportName: "",
            vibhagName: "",
            siteName: "",
            shift: "false"
        };

        $rootScope.loading++;
        RestService.getPGNames().success(function(data) {
            var pgList = [];
            for (var i = 0, l = data.length; i < l; i++) {
                pgList[i] = data[i].vendorName;
            }
            $scope.pglist = pgList;
            $rootScope.loading--;
        });

        $rootScope.loading++;
        RestService.getVibhagNames().success(function(data) {
            var vibhaglist = [];
            for (var i = 0, l = data.length; i < l; i++) {
                vibhaglist[i] = data[i].vibhagName;
            }

            $scope.vibhaglist = vibhaglist;

            $rootScope.loading--;
        });

        console.log($scope.showSaveSms);

    }

    $scope.selectVehicle = function(value) {
        if ($scope.vehiclelist == null || $scope.vehiclelist == undefined) {
            return;
        }

        if ($scope.vehiclelist.indexOf(value) > -1) {
            $scope.showSite = true;
            $rootScope.loading++;
            RestService.getSiteNames().success(function(data) {
                var siteList = [];
                for (var i = 0, l = data.length; i < l; i++) {
                    siteList[i] = data[i].siteName;
                }
                $scope.sitelist = siteList;
                $rootScope.loading--;
            })
        }
        else {
            $scope.showSite = false;
        }
    }

    $scope.siteSelect = function(value) {

        if (value == null ||
            value.trim() == "") {
            $scope.showMaterials = false;
        }
        else {
            $scope.showMaterials = true;
            $rootScope.loading++;

            RestService.getStockItemNames().success(function(data) {
                //console.log(data);
                var stockList = [];
                var stockMap = {};
                for (var i = 0, l = data.length; i < l; i++) {
                    stockList[i] = data[i].stockItemname;
                    stockMap[data[i].stockItemname] = data[i];
                }
                $scope.stocklist = stockList;

                $scope.stockMap = stockMap;
                //console.log($scope.stockMap);
                $rootScope.loading--;
            });

        }
    }

    $scope.stockSelect = function(value) {
        console.log(value);
        if ($scope.stocklist == null) {
            return;
        }

        if ($scope.stocklist.indexOf(value) > -1) {
            $scope.showSaveSms = true;
        }
        else {
            $scope.showSaveSms = false;
        }
    }

    $scope.transporterSelect = function(value) {
        if ($scope.pglist == null || $scope.pglist == undefined) {
            return;
        }

        if ($scope.pglist.indexOf(value) > -1) {
            $rootScope.loading++;
            RestService.getVehicleNames(value, 2).success(function(data) {
                var vehicleList = [];
                for (var i = 0, l = data.length; i < l; i++) {
                    vehicleList[i] = data[i].vehicleNumber;
                }
                $scope.vehiclelist = vehicleList;
                $rootScope.loading--;
            });
            $scope.vehicleVisible = true;
        }
        else {
            $scope.vehicleVisible = false;
        }

    }

    $scope.attach = function() {

    }

    $scope.saveSms = function() {
        var stocks = [];
        //console.log($scope.newSms.stockName);
        stocks.push($scope.newSms.stockName);
        //console.log(stocks);

        $http({
            method : 'POST',
            url : 'rest/sms?vibhagName='+$scope.newSms.vibhagName+
                    '&vehicleName='+$scope.newSms.vehicleNumber+
                    '&transportName='+$scope.newSms.transportName+
                    '&siteName='+$scope.newSms.siteName+
                    '&stockNames='+stocks+"&nightShift="+$scope.newSms.shift,
            data : null,
            headers : {
                'Content-Type' : 'application/json',
                'Accept' : 'application/json'
            }
        }).success(function(response) {
            //$window.location.reload();
        });

        $scope.cancelSms();
    }

    $scope.cancelSms = function() {
        $scope.showDetails = false;
        $scope.showSendSms = false;
        $scope.showCreateSms = false;
        $scope.vehicleVisible = false;
        $scope.showSite = false;
        $scope.showSms = true;
        $scope.showSaveSms = false;
        $scope.showMaterials = false;

        //$window.location.reload();

    }

	$scope.gridOptions = {
		data : 'myData',
		enablePaging : false,
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