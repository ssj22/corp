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
                field : 'challanDate',
                displayName : 'Challan Date',
                cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
                width : '220px',
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
				field : 'qty',
				displayName : 'Quantity',
				width : '90px'
			}, {
				field : 'netWt',
				displayName : 'NetWt',
				width : '90px'
			}, {
				field : 'amount',
				displayName : 'Amount',
				width : '90px'
			}, {
				field : 'vibhagName',
				displayName : 'Vibhag',
				width : '130px',
				visible : false
			}, {
                field : 'invoiceNo',
                displayName : 'Invoice No',
                width : '130px',
                visible : false
            }, {
                field : 'invoiceDate',
                displayName : 'Invoice Date',
                cellFilter : 'date:\"dd-MMM-yyyy @ h:mma\"',
                width : '220px',
                visible : false
            }, {
				field : 'orderId',
				displayName : 'Order ID',
				width : '90px',
				visible : false
			} ];

			return columnDefs;
		},
		
		getSetupColumnDefs: function(num) {
			console.log(num);
			if (num == 1) {
				var columnDefs = [
					{
						field : 'vibhagId',
						displayName : 'Id',
						width : '70px'
					}, {
						field : 'vibhagName',
						displayName : 'Vibhag Name',
						width : '340px'
					}, {
						field : 'vibhagType.vibhagTypeName',
						displayName : 'Vibhag Type',
						width : '220px'
					}, {
						field : 'phone',
						displayName : 'Phone',
						width : '160px'
					}	
				];
			}	
			else if (num == 11) {
				var columnDefs = [
				{
					field : 'vibhagTypeId',
					displayName : 'Vibhag Type Id',
					width : '160px'
				},
				{
					field : 'vibhagTypeName',
					displayName : 'Vibhag Type Name',
					width : '610px'
				}];
			}	
			else if (num == 2) {
				var columnDefs = [
					{
						field : 'vehicleNumber',
						displayName : 'Number',
						width : '160px'
					}, {
						field : 'vendor.vendorName',
						displayName : 'Vendor Name',
						width : '270px'
					}, {
						field : 'height',
						displayName : 'Height',
						width : '130px'
					}, {
						field : 'length',
						displayName : 'Length',
						width : '130px'
					}, {
						field : 'breadth',
						displayName : 'Breadth',
						width : '130px'
					}, {
						field : 'volume',
						displayName : 'Volume',
						width : '160px'
					}	
				];
			}
			else if (num == 3) {
				var columnDefs = [
					{
						field : 'vendorId',
						displayName : 'Id',
						width : '70px'
					}, {
						field : 'vendorName',
						displayName : 'Vendor Name',
						width : '340px'
					}, {
						field : 'phoneNo',
						displayName : 'Vendor Phone',
						width : '220px'
					}, {
						field : 'percentage',
						displayName : '%',
						width : '70px'
					}, {
						field : 'givenCode',
						displayName : 'Code',
						width : '70px'
					}, {
						field : 'number',
						displayName : 'Number',
						width : '70px'
					}, {
						field : 'address',
						displayName : 'Address',
						width : '270px'
					}	
				];
			}
			else if (num == 4) {
				var columnDefs = [
					{
						field : 'stockItemname',
						displayName : 'Stock Name',
						width : '220px'
					}, {
						field : 'stockRate',
						displayName : 'Rate Out',
						width : '130px'
					}, {
						field : 'stockRateInword',
						displayName : 'Rate In',
						width : '130px'
					}, {
						field : 'convFact',
						displayName : 'Factor',
						width : '130px'
					}, {
						field : 'item.mainItemname',
						displayName : 'Item Name',
						width : '160px'
					}	
				];
			}
			else if (num == 44) {
				var columnDefs = [{
					field : 'mainItemname',
					displayName : 'Item Name',
					width : '220px'
				},
				{
					field : 'outAddlInd',
					displayName : 'Addl Outward',
					width : '130px',
					cellTemplate: '<div class="check{{row.entity.outAddlInd}}">&nbsp;</div>'
				},
				{
					field : 'inAddlInd',
					displayName : 'Addl Inward',
					width : '130px',
					cellTemplate: '<div class="check{{row.entity.inAddlInd}}">&nbsp;</div>'
				},
				{
					field : 'invoiceInd',
					displayName : 'Invoice',
					width : '90px',
					cellTemplate: '<div class="check{{row.entity.invoiceInd}}">&nbsp;</div>'
				},
				{
					field : 'klInd',
					displayName : 'KL',
					width : '40px',
					cellTemplate: '<div class="check{{row.entity.klInd}}">&nbsp;</div>'
				},
				{
					field : 'qtyInd',
					displayName : 'Quantity',
					width : '90px',
					cellTemplate: '<div class="check{{row.entity.qtyInd}}">&nbsp;</div>'
				},
				{
					field : 'factor',
					displayName : 'Factor',
					width : '70px'
				}];
			}
			return columnDefs;	
		},
		
		getLogColumnDefs: function() {
			var columnDefs = [
			{
				field : 'newEntry',
				displayName : 'Status',
				width : '70px',
				cellTemplate: '<div class="newentry{{row.entity.newEntry}}">&nbsp;</div>'
			}, {
				field : 'phone',
				displayName : 'Phone',
				width : '130px'
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