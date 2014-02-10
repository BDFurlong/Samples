/**
 * Created by jmx-dev on 12/7/13.
 */

var app = angular.module('jmx');

app.directive('newCustomer', function(){
    return{

        restrict: 'E',
        templateUrl: 'views/customers/f_customer.html',
        controller: function($scope, Customer){
            $scope.createCustomer = function(){
                Customer.save($scope.customer);
            }

        }


    }
});


app.directive('customerList',function(){
    return{

        restrict: 'E',
        templateUrl: 'views/customers/customerList.html',
        controller: function($scope, Customer){

            $scope.customers = Customer.query();
        }
    }
});

app.directive('custMenu', function(){
    return{
        restrict: 'E',
        templateUrl: 'views/customers/m_createCust.html'
    }
});

app.directive('custInfo', function(){
    return{
        restrict: 'E',
        templateUrl: 'views/customers/v_customer.html'
    }
});


