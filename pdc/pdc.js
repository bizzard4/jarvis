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

// Rollback a transaction
var rollback = function(client, done) {
	client.query('ROLLBACK', function(err) {
		return done(err);
	});
};

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
exports.getTags = function (callback) {
	pool.connect(function(err, client, done) {
		client.query('SELECT row_to_json(nodes, true) as tags FROM nodes WHERE node_type=$1', ['TAG'], function(err, qresult) {
			done();
			if (err) return processError("Error in getTags", callback);
			processReturn(JSON.stringify(qresult.rows), callback);
		});
	});
};

// Get all infos for tag t as JSON (or Error)
exports.getInfos = function (tag_id, callback) {
	pool.connect(function (err, client, done) {
		client.query('SELECT row_to_json(neighbors, true) as infos FROM neighbors WHERE from_id=$1 AND node_type=$2', [tag_id, 'INFO'], function (err, qresult) {
			done();
			if (err) return processError("Error in getInfos", callback);
			processReturn(JSON.stringify(qresult.rows), callback);
		});
	});
};

// Add an info for a tag
exports.addInfo = function (tag_id, data, callback) {
	pool.connect(function (err, client, done) {
		client.query('BEGIN', function(err) {
			if (err) return rollback(client, done);
			process.nextTick(function () {
				client.query('INSERT INTO nodes(node_type, node_data) VALUES($1, $2) RETURNING node_id', ['INFO', data], function(err, qresult) {
					if (err) return rollback(client, done);
					client.query('INSERT INTO edges(edge_n1, edge_n2) VALUES($1, $2)', [tag_id, qresult.rows[0].node_id], function (err) {
						if (err) return rollback(client, done);
						client.query('COMMIT', function (err) {
							done();
							if (err) return processError("Error in addInfo", callback);
							processReturn("OK", callback);
						});
					});
				});
			});
		});
	});
};

// Add a tag name
exports.addTag = function (data, callback) {
	pool.connect(function (err, client, done) {
		client.query('INSERT INTO nodes(node_type, node_data) VALUES($1, $2)', ['TAG', data], function (err) {
			done();
			if (err) return processError("Error in addTag", callback);
			processReturn("OK", callback);
		});
	});
};

// Delete an info (not supported for now)
exports.deleteInfo = function (info_id, callback) {
	pool.connect(function (err, client, done) {
		client.query('DELETE FROM nodes', ['TAG', data], function (err) {
			done();
			if (err) return processError("Error in addTag", callback);
			processReturn("OK", callback);
		});
	});
};

