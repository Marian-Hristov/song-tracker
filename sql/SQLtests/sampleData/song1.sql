insert into contributors (contributor_name) values ('Michael Jackson');
insert into contributors (contributor_name) values ('Paul Jackson Jr.');
insert into contributors (contributor_name) values ('Steve Lukather');
insert into contributors (contributor_name) values ('Eddie Van Halen');
insert into contributors (contributor_name) values ('Steve Porcaro');
insert into contributors (contributor_name) values ('Greg Philinganes');
insert into contributors (contributor_name) values ('Greg Smith');
insert into contributors (contributor_name) values ('Bill Wolfer');
insert into contributors (contributor_name) values ('Tom Bahler');
insert into contributors (contributor_name) values ('Jeff Porcaro');
insert into contributors (contributor_name) values ('Quincy Jones');

commit;

insert into recordlabels (label_name) values ('Epic');

commit;

-- recordings

-- vocals
insert into recordings (recording_name, creation_time, duration)
values ('beat it vocals', current_timestamp, 215);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (10, 200, 0, 200);
-- guitar
insert into recordings (recording_name, creation_time, duration)
values ('beat it instrumental guitars', current_timestamp, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 30);
-- keyboard and drums
insert into recordings (recording_name, creation_time, duration)
values ('beat it instrumental keyboards and drums', current_timestamp, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 28);

-- compilations

--vocals
insert into compilations (compilation_name, creation_time, duration)
values ('beat it vocals comp', current_timestamp, 215);
insert into recordingsamples values (1, 1, 1);

--guitar
insert into compilations (compilation_name, creation_time, duration)
values ('beat it guitars comp', current_timestamp, 258);
insert into recordingsamples values (2, 2, 2);

--keyboards and drums
insert into compilations (compilation_name, creation_time, duration)
values ('beat it kbs and drums', current_timestamp, 258);
insert into recordingsamples values (3, 3, 3);

-- comp of kbs drums and guitars
insert into compilations (compilation_name, creation_time, duration)
values ('beat it instrumental final', current_timestamp, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 28);
insert into compilationsamples values (4, 2, 4);
insert into compilationsamples values (4, 3, 5);

-- comp vocals plus instrumental
insert into compilations (compilation_name, creation_time, duration)
values ('beat it final comp', current_timestamp, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 28);
insert into compilationsamples values (5, 1, 6);
insert into compilationsamples values (5, 4, 7);

commit;

insert into collections (collection_name) values ('Beat it');
insert into collectioncompilations values (1, 5);

insert into distributions (collection_id, release_date, label_id, market_id)
values (1, to_date('2008-02-08 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 1, 1);

commit;

insert into compilationcontributions values (5, 11, 4);
insert into productioncontributions values (1, 1, 17);

commit;

insert into musicalcontributions values (2, 2, 12);
insert into musicalcontributions values (2, 3, 12);
insert into musicalcontributions values (2, 4, 12);
insert into musicalcontributions values (3, 5, 16);
insert into musicalcontributions values (3, 6, 16);
insert into musicalcontributions values (3, 7, 16);
insert into musicalcontributions values (3, 8, 16);
insert into musicalcontributions values (3, 9, 16);
insert into musicalcontributions values (3, 10, 9);

commit;