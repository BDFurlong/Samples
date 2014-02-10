/**
 * Created by jmx-dev on 12/1/13.
 */


var express = require('express');
var app = express();
app.listen(3000);

var db = require('./API/db');
var user = require('./API/routes/user');
var customer = require('./API/routes/customer');
var bid = require('./API/routes/bid');


var allowCrossDomain = function(req, res, next){
    res.header("Access-Control-Allow-Origin", "*");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    next();
}

app.configure(function(){
    app.use(express.bodyParser());
    app.use(allowCrossDomain);


});


//USER ROUTES
app.get('/user/:email', user.getUser);
app.post('/user', user.doCreate);


//CUSTOMER ROUTES
//app.get('/customer/:id', customer.getUser);

app.get('/customer/type', customer.getType);
app.get('/customer', customer.getCustomer);
app.post('/customer', customer.doCreate);



