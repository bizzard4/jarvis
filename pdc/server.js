var express = require('express');
var app = express();
var pdc = require('./pdc.js');
var bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

// Server creation
var server = app.listen(8081, function() {
	var host = server.address().address;
	var port = server.address().port;
	
	console.log("Service running on http://%s:%s", host, port);
});

// From this point, server is started, next methods are all events/requests

// GET on /
app.get('/', function(req, res) {
	res.end("Jarvis PDC web service");
});

// Dashboard GET
app.get('/dashboard', function(req, res) {
	res.end("PDC dashboard - Placeholder");
});

// Available ws methods
// - tag/t
// - addInfo
// - deleteInfo
// - addTag
// - tags

// List all INFO nodes for tag t
app.get('/tag/:t', function(req, res) {
	pdc.getInfos(req.params.t, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res);
	});
});

// Add an INFO node
app.put('/tag/:t/addInfo', function(req, res) {
	pdc.addInfo(req.params.t, req.body.data, function(err, result) {
		if (err) return processError(err, res);
		res.end("Info added");
	});
});

// Delete an INFO node
app.delete('/deleteInfo/:i', function(req, res) {
	res.end("Not supported for now");
	//pdc.deleteInfo(req.params.i, function(err, result) {
	//	if (err) return processError(err, res);
	//	res.end("Info deleted");
	//});
});

// Add a TAG node
app.put('/addTag', function(req, res) {
	pdc.addTag(req.body.data, function(err, result) {
		if (err) return processError(err, res);
		res.end("Tag added");
	});
});

// List all TAG nodes
app.get('/tags', function(req, res) {
	pdc.getTags(function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res);
	});
});

// Method to return JSON result to client
var responseJSONResult = function(json, response) {
	response.writeHead(200, {'content-type':'application/json', 'content-length':Buffer.byteLength(json)}); 
	response.end(json);
};

// In case of error
var processError = function(error, response) {
	console.error(error);
	response.end("Error");
};