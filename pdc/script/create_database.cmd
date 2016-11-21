psql -f dropview.sql jarvis_pdc jarvis_master
psql -f droptable.sql jarvis_pdc jarvis_master
psql -f createtable.sql jarvis_pdc jarvis_master
psql -f createview.sql jarvis_pdc jarvis_master