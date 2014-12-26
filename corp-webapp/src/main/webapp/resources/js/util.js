angular.module('myApp')
.factory('Util', function($q, $rootScope, $filter) {
	return {
		getMaterialColumnDefs: function() {
			var columnDefs = [
			{
				field : 'materialId',
				displayName : 'S.No.',
				width : '70px'
			},{
				field : 'parentMaterialId',
				displayName : 'Parent',
				width : '70px'
			},{
				field : 'entryTypeText',
				displayName : 'Type',
				width : '70px'
			}, {
				field : 'challanNo',
				displayName : 'Challan',
				width : '70px',
				visible : false
			}, {
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
				field : 'transporterName',
				displayName : 'Transporter',
				width : '160px'
			}, {
				field : 'vehicleNumber',
				displayName : 'Vehicle',
				width : '130px'
			}, {
				field : 'vehicleInTime',
				displayName : 'Vehicle In Time',
				cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
				width : '220px'
			}, {
				field : 'vehicleOutTime',
				displayName : 'Vehicle Out Time',
				cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
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
			} ];
			
			return columnDefs;
		},
		
		getLogColumnDefs: function() {
			var columnDefs = [
			{
				field : 'newEntry',
				displayName : 'Complete',
				width : '90px',
				cellTemplate: '<div class="newentry{{row.entity.newEntry}}">&nbsp;</div>'
			}, {
				field : 'phone',
				displayName : 'Phone',
				width : '160px'
			}, {
				field : 'vibhagName',
				displayName : 'Vibhag Name',
				width : '220px'
			}, {
				field : 'msg',
				displayName : 'SMS',
				width : '230px'
			}, {
				field : 'updateDate',
				displayName : 'SMS Received Time',
				cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
				width : '220px'
			}, {
				field : 'gateInTime',
				displayName : 'SMS Update Time',
				cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
				width : '220px'
			}];
			
			return columnDefs;
		}
	};
});

//angular.module('myApp')
//.config(function ($httpProvider) {
//    $httpProvider.responseInterceptors.push('myHttpInterceptor');
//    var spinnerFunction = function (data, headersGetter) {
//        // todo start the spinner here
//        //alert('start spinner');
//        $('#mydiv').show();
//        return data;
//    };
//    $httpProvider.defaults.transformRequest.push(spinnerFunction);
//})
////register the interceptor as a service, intercepts ALL angular ajax http calls
//.factory('myHttpInterceptor', function ($q, $window) {
//    return function (promise) {
//        return promise.then(function (response) {
//            // do something on success
//            // todo hide the spinner
//            //alert('stop spinner');
//            $('#mydiv').hide();
//            return response;
//
//        }, function (response) {
//            // do something on error
//            // todo hide the spinner
//            //alert('stop spinner');
//            $('#mydiv').hide();
//            return $q.reject(response);
//        });
//    };
//});