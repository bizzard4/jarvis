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
// Subject : add subject , get all entries, get all subjects
// Entries : get an entry, add entry, delete entry

// ---------- Entries ----------

// DELETE an entry
app.delete("/entry/:t", function(req, res) {
	pdc.deleteEntry(req.params.t, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 200);
	});
});

// POST an entry
app.post("/entry", function(req, res) {
	pdc.addEntry(req.body.entry, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 201);
	});
});

// GET an entry
app.get("/entry/:entry_id", function(req, res) {
	pdc.getEntry(req.params.entry_id, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 201);
	});
});

// ---------- Subjects ----------

// POST a subject
app.post('/subject', function(req, res) {
	pdc.addSubject(req.body.subject, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 201);
	});
});

// GET entries for a subject
app.get('/subject/:subject_id', function(req, res) {
	pdc.getEntries(req.params.subject_id, function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 200);
	});
});

// GET all subjects
app.get('/subject', function(req, res) {
	pdc.getSubjects(function(err, result) {
		if (err) return processError(err, res);
		responseJSONResult(result, res, 200);
	});
});

//---------- Utils ----------

// Method to return JSON result to client
var responseJSONResult = function(json, response, return_code) {
	response.writeHead(return_code, {'content-type':'application/json', 'content-length':Buffer.byteLength(json)}); 
	response.end(json);
};

// In case of error
// TODO : Error code https://github.com/bizzard4/jarvis/issues/2
var processError = function(error, response) {
	console.error(error);
	response.end("Error");
};