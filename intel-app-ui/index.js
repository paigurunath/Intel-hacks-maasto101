var express = require('express');
var server = express();
server.use(express.static(__dirname + '/public'));
server.listen('3000');
server.get('*',function(req,res){
	req.sendfile('./public/index.html');
});