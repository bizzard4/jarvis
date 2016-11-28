var fs = require('fs');
var pg = require('pg');

// Prepare variable
var jsonConfig = JSON.parse(fs.readFileSync('./config.json', 'utf8'));
var config = {
	user: jsonConfig.db_user, //env var: PGUSER
	database: jsonConfig.db_database, //env var: PGDATABASE
	password: jsonConfig.db_password, //env var: PGPASSWORD
	host: jsonConfig.db_host, // Server hosting the postgres database
	port: jsonConfig.db_port, //env var: PGPORT
	max: 10, // max number of clients in the pool
	idleTimeoutMillis: 30000, // how long a client is allowed to remain idle before being closed
}
var pool = new pg.Pool(config);
pool.on('error', function(err, client) {
	console.error('Error in idle', err.message, err.stack);
});

var processReturn = function(toreturn, callback) {
	if (callback) {
		callback(null, toreturn);
	}
};

var processError = function(error, callback) {
	console.error(error);
	if (callback) {
		callback(error, null);
	}
}

// Delete an entry
exports.deleteEntry = function (entry_id, callback) {
	pool.connect(function(err, client, done) {
		client.query("DELETE FROM entries WHERE entry_id=$1", [entry_id], function(err, qresult) {
			done();
			if (err) return processError("Error in deleteEntry ->" + err, callback);
			processReturn("OK", callback);
		});
	});
}

// Add an entry
exports.addEntry = function(entry_json, callback) {
	pool.connect(function(err, client, done) {
		client.query("INSERT INTO entries(entry_value_type_id, entry_value, entry_subject_id, entry_date) VALUES ($1, $2, $3, $4)", [entry_json.entry_value_type_id, entry_json.entry_value, entry_json.entry_subject_id, entry_json.entry_date], function(err, qresult) {
			done();
			if (err) return processError("Error in addEntry -> " + err, callback);
			processReturn("OK", callback);
		});
	});
}

// Get a specific entry
exports.getEntry = function(entry_id, callback) {
	pool.connect(function(err, client, done) {
		client.query('SELECT row_to_json(entries, true) as entry FROM entries WHERE entry_id=$1', [entry_id], function(err, qresult) {
			done();
			if (err) return processError("Error in getEntry" + err, callback);
			processReturn(JSON.stringify(qresult.rows), callback);
		});
	});
}

// Add a subject and return inserted data
exports.addSubject = function(subject_json, callback) {
	pool.connect(function(err, client, done) {
		client.query("INSERT INTO subjects(subject_name) VALUES ($1)", [subject_json.subject_name], function(err, qresult) {
			done();
			if (err) return processError("Error in addEntry -> " + err, callback);
			processReturn("OK", callback);
		});
	});
}


// Get all tags as JSON (or Error)
exports.getSubjects = function (callback) {
	pool.connect(function(err, client, done) {
		client.query('SELECT row_to_json(subjects, true) as subject FROM subjects', function(err, qresult) {
			done();
			if (err) return processError("Error in getTags", callback);
			processReturn(JSON.stringify(qresult.rows), callback);
		});
	});
};

// Get all tags as JSON (or Error)
exports.getEntries= function (subject_id, callback) {
	pool.connect(function(err, client, done) {
		client.query('SELECT row_to_json(entries_per_subject, true) as entry FROM entries_per_subject WHERE subject_id=$1', [subject_id], function(err, qresult) {
			done();
			if (err) return processError("Error in getTags", callback);
			processReturn(JSON.stringify(qresult.rows), callback);
		});
	});
};

