CREATE TABLE subjects
(
	subject_id	serial PRIMARY KEY,
	subject_name VARCHAR(100) NOT NULL
);

CREATE TABLE value_types
(
	value_type_id serial PRIMARY KEY,
	value_type_name VARCHAR(50) NOT NULL
);

CREATE TABLE entries
(
	entry_id serial PRIMARY KEY,
	entry_value_type_id INT NOT NULL REFERENCES value_types(value_type_id),
	entry_value INT NOT NULL,
	entry_subject_id INT NOT NULL REFERENCES subjects(subject_id),
	entry_date date
);