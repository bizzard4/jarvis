var express = require('express');
var app = express();
var pdc = require('./pdc.js');
var bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));
app.use('/dashboard', express.static('public'));

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

// Available ws methods
// - subjects
// - subject :s

// List all entries from a subject
app.get('/subject/:s', function(req, res) {
	pdc.getEntries(req.params.s, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res);
	});
});

// List all subject
app.get('/subjects', function(req, res) {
	pdc.getSubjects(function(err, result) {
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