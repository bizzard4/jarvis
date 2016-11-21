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

