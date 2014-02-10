/**
 * Created by jmx-dev on 12/7/13.
 */

var app = angular.module('jmx');

app.factory('Customer', function($resource){
    return $resource('http://localhost:3000/customer',{});
});

