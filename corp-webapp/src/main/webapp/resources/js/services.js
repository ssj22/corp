angular.module('myApp')
	.factory('RestService', function($http, $q, $rootScope, $filter) {
		$rootScope.currtime = $filter('date')(new Date(), 'dd-MMM-yyyy HH:mm:ss');
		return {
			// Method to fetch Vibhag Names
			getVibhagNames: function() {
				return $http.get('rest/vibhags');
			},
			
			getVehicleNames: function(value, type) {
				if (type == 2) {
					return $http.get('rest/vehicles?tname='+value);
				}
				else if (type == 1) {
					return $http.get('rest/vehicles?name='+value);
				}	
				else {
					return $http.get('rest/vehicles');
				}
			},
			
			getStaticData: function(name) {
				return $http.get('rest/preentry?name=' + name);
			},
			
			getEligVibhags: function() {
				return $http.get('rest/eligVibhags');
			},
			
			getStockItemNames: function() {
				return $http.get('rest/stockItems');
			},
			
			getSiteNames: function() {
				return $http.get('rest/sites');
			},
			
			getPGNames: function() {
				return $http.get('rest/primaryGroups');
			},
			
			getCount: function() {
				$http.get('rest/counts').then(function(response) {
					$rootScope.counts = response.data;
				});
			},
			
			getWeight: function() {
				return $http.get('rest/dummyweight');
			},
			
			saveEntry: function(selection) {
				return $http({
					method : 'POST',
					url : 'rest/materials',
					data : selection,
					headers : {
						'Content-Type' : 'application/json'
					}
				});
			},
			
			linkEntry: function(idList, value) {
				return $http({
					method : 'POST',
					url : 'rest/link?type='+value,
					data : idList,
					headers : {
						'Content-Type' : 'application/json'
					}
				});
			}
		};	
	});

angular.module('myApp')
.factory('CorpService', function($http, $q, $rootScope, $filter) {
	return {
		checkRelation: function(selection) {
			var result = [false, true, true]; // 0 - link, 1 - unlink, 2 - attach
			for (var i = 0; i < selection.length; i++) {
				var parentId = selection[i].parentMaterialId;
				var childId = selection[i].materialId;
				
				if (selection[i].entryType == 1) {
					result[0] = false;
					result[1] = false;
					break;
				}
				
				for (var j = 0; j < selection.length; j++) {
					if (i != j) {
						var currParentId = selection[j].parentMaterialId;
						var currChildId = selection[j].materialId;
						//console.log(currParentId + ","  + currChildId);
						//console.log(currParentId != parentId);
						if ((currParentId == null || parentId == null || currParentId != parentId) 
								&& (parentId == null || currChildId != parentId) 
								&& (currParentId == null || childId != currParentId) 
								&& childId != currChildId) {
							result[1] = false;
							if (currParentId != null && parentId != null) {
								result[0] = false;
							}
							else {
								result[0] = true;
							}
							break;
						}
					}
				}
			}
			return result;
		}
	}
	
});
