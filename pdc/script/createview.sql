CREATE VIEW entries_per_subject AS
 SELECT value_types.value_type_name,
    entries.entry_value,
    subjects.subject_name,
    entries.entry_date,
    subjects.subject_id
   FROM entries
     JOIN value_types ON entries.entry_value_type_id = value_types.value_type_id
     JOIN subjects ON entries.entry_subject_id = subjects.subject_id;