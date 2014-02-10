/**
 * Created by jmx-dev on 12/3/13.
 */

/** REQ **/
var mongoose = require('mongoose');

var User = mongoose.model('User');

exports.doCreate = function(req, res){
    User.create({
        name: req.body.FullName
    },
    function( err, user){
        if(!err){
            console.log("User created and saved: " + user);
        }
    });
};

exports.getUser = function(req, res){
    if(req.params.email){
        User.findOne({'email' : req.params.email},
        'name email',
        function(err, users){
            if(!err){
                res.json(users);
            }
        });
    }
}

exports.getUsers = function(req, res){

};


