/**
 * Created by jmx-dev on 12/3/13.
 */

var mongoose = require('mongoose');

var dbURI = 'mongodb://localhost/JMX';

mongoose.connect(dbURI);

mongoose.connection.on('connected', function(){
    console.log('Mongoose connected to ' + dbURI);
});

mongoose.connection.on('error', function(err){
    console.log('Mongoose connection error: ' + err);
});

mongoose.connection.on('disconnected', function(){
    console.log('Mongoose disconnected');
});

process.on('SIGINT', function(){
    mongoose.connection.close(function(){
        console.log('Mongoose disconnected through app termination');
        process.exit(0);
    });
});


/** USER SCHEMA **/
var userSchema = new mongoose.Schema({
    tenantID: Number,
    name: String,
    email: {type: String, unique: true},
    createdOn: {type: Date, default: Date.now},
    modifiedOn: Date,
    lastLogin: Date
});

/** END USER SCHEMA **/




/** ADDRESS SCHEMA **/

var addressSchema = new mongoose.Schema({

    line1: String,
    line2: String,
    description: String,
    city: String,
    state: String,
    zip: String

});
mongoose.model('Address', addressSchema);
/** END ADDRESS SCHEMA **/


/** Site Spec Schema **/
var siteSpecSchema = new mongoose.Schema({



});


/** LOCATION SCHEMA **/


var locationSchema = new mongoose.Schema({

    locationName: String,
    address: [addressSchema],
    primary: Boolean,
    phone: String,
    fax: String,
    description: String,
    type: String
    //siteSpec: [siteSpecSchema]
});

mongoose.model('Location', locationSchema);
/**END LOCATION SCHEMA **/


/** CUSTOMER SCHEMA **/

var customerSchema = new mongoose.Schema({
    tenantID: Number,
    name: String,
    description: String,
    mailingAddress: [addressSchema],
    locations: [locationSchema],
    phone: String,
    fax: String,
    email:{ type: String, unique: true},
    createdOn: Date,
    updatedOn: Date


});

/** END CUSTOMER SCHEMA **/

mongoose.model('Customer', customerSchema);
mongoose.model('User', userSchema);




