/**
 * Created by jmx-dev on 12/4/13.
 */

var mongoose = require('mongoose');

var Customer = mongoose.model('Customer');
var Address = mongoose.model('Address');

exports.doCreate = function(req,res){




    Customer.create({
        name: req.body.name,
        description: req.body.description,
        phone: req.body.phone,
        email: req.body.email,
        tenantID: 1,
        fax: req.body.fax,
        createdOn: Date.now(),
        updatedOn: Date.now()
        },

        function(err, customer){
           if(err){
               res.send(err);
               }
        Customer.find(function(err, customer){
            if(err)
                res.send(err)
            res.json(customer);
        });
        }
    );
};



exports.getType = function(req, res){
    res.json([
        {}])
};

exports.getCustomer = function(req, res){

    return Customer.find({},'name',{},function(err, customers){
        if(!err){
            return res.send(customers);

        }
        else {
            console.log(err);
            return res.send(500, {error: 'Warning Major Tom!'});
        }
    });

   


};