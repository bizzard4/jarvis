DROP TABLE edges, nodes;

CREATE TABLE nodes
(
	node_id				serial PRIMARY KEY,
	node_type			VARCHAR(50) NOT NULL,
	node_data			TEXT NOT NULL,
	node_creation		date NOT NULL,
	node_modified		date NOT NULL
);

CREATE TABLE edges
(
	edge_id				serial PRIMARY KEY,
	edge_n1				INT NOT NULL REFERENCES nodes(node_id),
	edge_n2				INT NOT NULL REFERENCES nodes(node_id),
	edge_creation		date NOT NULL,
	edge_modified		date NOT NULL,
	UNIQUE (edge_n1, edge_n2)
);