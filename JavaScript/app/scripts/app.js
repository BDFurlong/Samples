/**
 * Created by jmx-dev on 12/1/13.
 */


angular.module('jmx', ['ngRoute','ui.bootstrap','ngResource'])

.config(function($routeProvider){



        $routeProvider.when('/index',
            {
                templateUrl: 'index.html'
            })

            .when('/newcustomer',
            {
                templateUrl: 'scripts/customers/new.html'
            })
            .when('/newbid',
            {
                templateUrl: 'views/bid/new.html'
            })
            .when('/newcustomer',
            {
                templateUrl: 'views/customers/new.html'
            });




    }
);