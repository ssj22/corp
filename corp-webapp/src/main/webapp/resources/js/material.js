angular.module('myApp').controller("MaterialsCtrl", function($scope, $http, $interval, RestService, CorpService, Util, $rootScope, $route, permissions, $window) {
		$scope.userId = $rootScope.loginuser.userId;
		$scope.printready = false;
		$scope.linkready = false;
		$scope.unlinkready = false;
		$scope.selectedRow = [];
		$scope.myData = [];
		$scope.readOnly = true;
		$scope.transporterEmpty = true;
							
		$scope.pglist; // List of names of Primary Groups (Vendors)
		$scope.vibhaglist; // List of names of Vibhags (Engineers and Ward Offices)
		$scope.vehiclelist; // List of Vehicle numbers
		$scope.stocklist; // List of names of Stock Items
		$scope.stockMap; // List of Stock Item Objects
		$scope.sitelist; // List of names of Sites
		$scope.staticMap;
		var clearStaticData = function() {
			$scope.pglist = null;$scope.vibhaglist = null;$scope.vehiclelist = null;$scope.stocklist = null;$scope.stockMap = null;$scope.sitelist = null;
		}
		
		$scope.showTransporters = false;
		$scope.showVehicles = false;
		$scope.showSite = false;
		$scope.showMaterials = false;
		$scope.showSaveMaterials = false;
		
		$scope.fromToError = false;
		$scope.transporterError = false;
		$scope.vehicleError = false;
		$scope.stockError = false;
		
		
		// STEP 1
		$scope.selectEntryType = function() {
			//console.log($scope.selection.entryType);
			$scope.weight();
			if ($scope.selection.entryType == 2) { 
				RestService.getEligVibhags().then(function(response) {
					//console.log(response.data);
					$scope.vibhaglist = response.data;
				});
			}
			else {
				$rootScope.loading++;
				RestService.getPGNames().success(function(data) {
					var pgList = [];
					for (var i = 0, l = data.length; i < l; i++) {
						pgList[i] = data[i].vendorName;
					}
					$scope.pglist = pgList;
					$rootScope.loading--;
				});
			}
		};
		
		$scope.selectVendor = function(value) {
			if ($scope.pglist == null) {
				return;
			}
			
			if ($scope.pglist.indexOf(value) > -1) {
				$scope.showTransporters = true;
			}
			else {
				$scope.showTransporters = false;
				$scope.showVehicles = false;
				$scope.showSite = false;
				$scope.showMaterials = false;
				$scope.showSaveMaterials = false;
			}
		}
		
		$scope.selectVibhag = function(value) {
			//console.log($scope.vibhaglist.indexOf(value));
			if ($scope.vibhaglist == null) {
				return;
			}
			
			if ($scope.vibhaglist.indexOf(value) > -1) {
				$rootScope.loading++;
				RestService.getStaticData(value).success(function(data) {
					if (data == null || data == {}) {
						$scope.showTransporters = false;
					}
					else {
						$scope.showTransporters = true;
						$scope.staticMap = data;
						$scope.pglist = Object.keys(data);
					}
					$rootScope.loading--;
				});
			}
			else {
				$scope.showTransporters = false;
				$scope.showVehicles = false;
				$scope.showSite = false;
				$scope.showMaterials = false;
				$scope.showSaveMaterials = false;
			}
		}
		
		$scope.transporterSelect = function(value) {
			if ($scope.pglist == null) {
				return;
			}
			
			if ($scope.pglist.indexOf(value) > -1) {
				$scope.showVehicles = true;
				if ($scope.selection.entryType == 2) {
					$scope.vehiclelist = ($scope.staticMap[value])["v"];
					$scope.sitelist = ($scope.staticMap[value])["s"];
					$scope.stocklist = ($scope.staticMap[value])["i"];
					console.log($scope.staticMap);
				}
				else {
					$rootScope.loading++;
					RestService.getVehicleNames(value, 2).success(function(data) {
						var vehicleList = [];
						for (var i = 0, l = data.length; i < l; i++) {
							vehicleList[i] = data[i].vehicleNumber;
						}
						$scope.vehiclelist = vehicleList;
						$rootScope.loading--;
					});	
				}
			}
			else {
				$scope.showVehicles = false;
				$scope.showSite = false;
				$scope.showMaterials = false;
				$scope.showSaveMaterials = false;
			}
		}
		
		$scope.vehicleSelect = function(value) {
			if ($scope.vehiclelist == null) {
				return;
			}
			
			if ($scope.vehiclelist.indexOf(value) > -1) {
				$scope.showSite = true;
				if ($scope.selection.entryType == 1) {
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
			}
			else {
				$scope.showSite = false;
				$scope.showMaterials = false;
				$scope.showSaveMaterials = false;
			}
		}
		
		$scope.siteSelect = function(value) {
			if ($scope.sitelist == null) {
				return;
			}
			
			if (value == null || 
					value.trim() == "") {
				$scope.showMaterials = false;
				$scope.showSaveMaterials = false;
			}
			else {
				$scope.showMaterials = true;
				$rootScope.loading++;
					
				RestService.getStockItemNames().success(function(data) {
					var stockList = [];
					var stockMap = {};
					for (var i = 0, l = data.length; i < l; i++) {
						stockList[i] = data[i].stockItemname;
						stockMap[data[i].stockItemname] = data[i];
					}
					if ($scope.selection.entryType == 1) {
						$scope.stocklist = stockList;
					}
					
					$scope.stockMap = stockMap;
					//console.log($scope.stockMap);
					$rootScope.loading--;
				});
				
			}
		}
		
		$scope.stockSelect = function(value) {
			if ($scope.stocklist == null) {
				return;
			}
			
			if ($scope.stocklist.indexOf(value) > -1) {
				$scope.showSaveMaterials = true;
				$scope.checkAddl();
			}
			else {
				$scope.showSaveMaterials = false;
				$scope.addl = false;
			}
		}
		
		$scope.resetAddl = function() {
			$scope.addl = false;
			$scope.invoiceInd = false;
			$scope.klInd = false;
			$scope.heightCorr = false;
			$scope.qtyInd = false;
			$scope.amountInd = false;
		}
		
		$scope.checkAddl = function() {
			if ($scope.selection != null && $scope.selection.stockName != null) {
				//console.log($scope.selection.stockName);
				//console.log($scope.stockMap);
				var stock = $scope.stockMap[$scope.selection.stockName];
				//console.log(stock);
				if ($scope.selection.entryType == 1) {
					if (stock.item.inAddlInd) $scope.addl = true;
					else $scope.addl = false;
					
					if (stock.item.htCorrectionInd) $scope.heightCorr = true;
					else $scope.heightCorr = false;
					
					if (stock.item.qtyInd) { 
						$scope.qtyInd = true;
						$scope.amountInd = true;
					}	
					else $scope.qtyInd = false;
					
					if (stock.item.klInd) $scope.klInd = true;
					else $scope.klInd = false;
					
					if (stock.item.invoiceInd) $scope.invoiceInd = true;
					else $scope.invoiceInd = false;
				}
				else if ($scope.selection.entryType == 2) {
					if (stock.item.outAddlInd) $scope.addl = true;
					else $scope.addl = false;
					
					if (stock.item.qtyInd) $scope.qtyInd = true;
					else $scope.qtyInd = false;
					
					$scope.invoiceInd = false;
					$scope.klInd = false;
					$scope.heightCorr = false;
					$scope.amountInd = false;
				}
				
				//console.log($scope.selection.stockName + "," + $scope.selection.inAddlInd);
			}
		};
		
		$scope.relSelected = function(materiaId) {
			//console.log("materiaId in relSelected =" + materiaId);
			$http({
				method : 'GET',
				url : 'rest/materials?rel=' + materiaId
			}).then(function(response) {
				$scope.printMaterials = response.data;
				console.log("printMaterials length = " + $scope.printMaterials.length);
				var tmp={};
				for(i=0;i<$scope.printMaterials.length;i++){
				   var fromTo = $scope.printMaterials[i].vibhagName;
				   if (fromTo == null || fromTo == "") {
					   fromTo = $scope.printMaterials[i].vendorName;
				   }
				   
				   if(tmp[fromTo] == undefined) {
				        tmp[fromTo]=[]
				   }
				   tmp[fromTo].push($scope.printMaterials[i]);
				}
				$scope.materialMap = tmp;
			});
		}

		$scope.printEntry = function() {
			$scope.selection = $scope.selectedRow[0];
			var materialId = $scope.selection.parentMaterialId;
			if (materialId == null || materialId == "") {
				materialId = $scope.selection.materialId;
			}
			$scope.relSelected(materialId);
		};

		$scope.editEntry = function(rel) {
			// console.log("selectedRow = " +
			$scope.selection = $scope.selectedRow[0];
			if (rel == 1) {
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
				
			}
			$scope.readOnly = false;
			$scope.watchGrossWt();
			$scope.watchTareWt();
			$scope.attachedEntry = false;
			$scope.weight();
			console.debug($scope.selection);
			if (rel == 2) {
                var selection = {};
                for(var k in $scope.selection) {
                    selection[k]=$scope.selection[k];
                }

                selection.vehicleInTime = null;
				selection.vehicleOutTime = null;
				selection.stockName = null;
				selection.vendorName = null;
				selection.vibhagName = null;
				selection.grossWt = null;
				selection.tareWt = null;
				selection.netWt = null;
				selection.status = null;
				$scope.attachedEntry = true;
                var attachedParent = $scope.selection.materialId;
                if ($scope.selection.parentMaterialId != null) {
                    attachedParent = $scope.selection.parentMaterialId;
                }
				$scope.attachedDisplay = attachedParent + " [ Vehicle: " + $scope.selection.vehicleNumber + " ]";
				selection.materialId = null;
                $scope.selection = selection;
			}
			else {
				$scope.showTransporters = true;
				$scope.showVehicles = true;
				$scope.showSite = true;
				$scope.showMaterials = true;
				$scope.showSaveMaterials = true;
			}
			//console.log($scope.selectedRow[0]);
			//console.log($scope.selection);
			//loadStaticData();
		};

		$scope.createEntry = function() {
			// console.log($scope.timeSel);
			$scope.selection = null;
			$scope.readOnly = false;
			$scope.watchGrossWt();
			$scope.watchTareWt();
			$scope.attachedEntry = false;
			$scope.transporterEmpty = true;
			//loadStaticData();
		};

		$scope.cancelEntry = function() {
			console.log($scope.selectedRow[0]);
			$scope.readOnly = true;
			$scope.stopWeight();
			$scope.attachedEntry = false;
			clearStaticData();
			$scope.transporterEmpty = true;
			//$scope.selection = null;
			$scope.selection = $scope.selectedRow[0];
		};

		$scope.linkEntry = function(value) {
			var idList = [];
			for (var i = 0, l = $scope.selectedRow.length; i < l; i++) {
				idList[i] = $scope.selectedRow[i].materialId;
			}
			$rootScope.loading++;
			RestService.linkEntry(idList, value).then(function(response) {
				$rootScope.loading--;
				$scope.gridOptions.selectAll(false);
				$scope.getPagedDataAsync(
						$scope.pagingOptions.pageSize,
						$scope.pagingOptions.currentPage);
				
			});
		};
							
		$scope.saveEntry = function() {
			$scope.selection.userId = $rootScope.userId;
			$rootScope.loading++;
			RestService.saveEntry($scope.selection).then(function(response) {
				$scope.stopWeight();
				$rootScope.loading--;
				$scope.readOnly = true;
				RestService.getCount();
				$scope.gridOptions.selectAll(false);
				$scope.getPagedDataAsync(
						$scope.pagingOptions.pageSize,
						$scope.pagingOptions.currentPage);
			});
		};
			
		$scope.timeSelected = function(time) {
			// console.log("Inside timeselected" + $scope.timeSel);
			$scope.pagingOptions.currentPage = 1;
			$scope.timeSel = time;
			$rootScope.loading++;
			$http({
				method : 'GET',
				url : 'rest/materials?time=' + $scope.timeSel
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
				url : 'rest/materials?from=' + dateFrom.value + '&to=' + dateTo.value
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

		$scope.getWeight = function() {
            console.log("Get Weight Called");
			/*RestService.getWeight().then(
				function(response) {
					if ($scope.selection == undefined) {
						return;
					}
					
					if ($scope.selection.entryType == 1) {
						if ($scope.selection.status == null) {
							$scope.selection.grossWt = response.data;
							$scope.selection.tareWt = null;
						} else if ($scope.selection.status == "INITIATED") {
							$scope.selection.tareWt = response.data;
						}
					} else {
						if ($scope.selection.status == null) {
							$scope.selection.tareWt = response.data;
							$scope.selection.grossWt = null;
						} else if ($scope.selection.status == "INITIATED") {
							$scope.selection.grossWt = response.data;
						}
					}
				});*/
		};

		var stop;
		$scope.weight = function() {
			if (angular.isDefined(stop))
				return;

			/*stop = $interval(function() {
				$scope.getWeight();
			}, 1000);*/
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

		var grossWtListener;
		var tareWtListener;

		$scope.watchGrossWt = function() {
			grossWtListener = $scope
				.$watch(
					'selection.grossWt',
					function(newVal, oldVal) {
						if ($scope.selection == null)
							return;
						// console.log($scope.selection);
						if (angular
								.isDefined($scope.selection.tareWt)) {
							// console.log("inside");
							$scope.selection.netWt = newVal
									- $scope.selection.tareWt;
						} else {
							// console.log("inside2");
							$scope.selection.netWt = newVal;
						}
					});
		};

		$scope.watchTareWt = function() {
			tareWtListener = $scope
				.$watch(
					'selection.tareWt',
					function(newVal, oldVal) {
						if ($scope.selection == null)
							return;
						// console.log($scope.selection);
						if (angular
								.isDefined($scope.selection.grossWt)) {
							// console.log("inside3");
							$scope.selection.netWt = $scope.selection.grossWt
									- newVal;
						} else {
							// console.log("inside4");
							$scope.selection.netWt = -1
									* newVal;
						}
					});
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
							'rest/materials?page=' + page
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
						url = 'rest/materials?time=' + $scope.timeSel
					}
					else {
						url = 'rest/materials?from=' + dateFrom.value + '&to=' + dateTo.value;
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
			multiSelect : true,
			selectedItems : $scope.selectedRow,
			afterSelectionChange : selcFunc = function() {
				var selection = $scope.selectedRow;
				//console.log("Entered with selection length = " + selection.length );
				$scope.linkready = false;
				$scope.unlinkready = false;
				$scope.printready = false;
				$scope.attachready = false;
				//console.log("Inside SelcFunc");
				if (selection == null || selection == "" || selection.length == 0) {
					//console.log("returning with " + selection);
					return;
				}
				var parentId = selection[0].parentMaterialId;
				var childId = selection[0].materialId;
				//console.log("ParentId is " + parentId);
				if (parentId == null || parentId == "") {
					parentId = selection[0].materialId;
				}
				else {
					$scope.unlinkready = true;
				}
				
				//console.log("Values " + $scope.unlinkready + ", " + $scope.linkready + ", " + $scope.printready);
				
				if (selection.length == 1) { 
					if (selection[0].status != "LOCKED") {
						$scope.printready = true;
						if (selection[0].entryType == 2) {
							$scope.attachready = true;
						}
					}
					
					//console.log("1 Values " + $scope.unlinkready + ", " + $scope.linkready + ", " + $scope.printready);
				}
				else {
					$scope.attachready = false;
					
					var result = CorpService.checkRelation(selection);
					$scope.linkready = result[0];
					$scope.unlinkready = result[1];
				}
				//console.log("Values " + $scope.unlinkready + ", " + $scope.linkready + ", " + $scope.printready);
				//RestService.setCurrSelection(selection);
			},
			showFilter : true,
			showColumnMenu : true,
			keepLastSelected : false,
			rowTemplate : '<div ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell ng-click="selectRow()"></div>',
			columnDefs : Util.getMaterialColumnDefs()
		};

	});
