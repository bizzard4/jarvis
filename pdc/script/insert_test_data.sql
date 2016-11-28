COPY subjects FROM 'D:\Programmation\GIT\jarvis\pdc\script\test_data\subjects_test_data.csv' DELIMITER ',' CSV;
COPY value_types FROM 'D:\Programmation\GIT\jarvis\pdc\script\test_data\value_types_test_data.csv' DELIMITER ',' CSV;
COPY entries FROM 'D:\Programmation\GIT\jarvis\pdc\script\test_data\entries_test_data.csv' DELIMITER ',' CSV;

ALTER SEQUENCE entries_entry_id_seq RESTART WITH 5;
ALTER SEQUENCE subjects_subject_id_seq RESTART WITH 2;
ALTER SEQUENCE value_types_value_type_id_seq RESTART WITH 2;
