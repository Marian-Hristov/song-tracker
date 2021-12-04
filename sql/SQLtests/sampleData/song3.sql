begin
insert into contributors (contributor_name) values ('Alex Resoagli');
insert into contributors (contributor_name) values ('Christopher Brody Brown');
insert into contributors (contributor_name) values ('Glenn Fischbach');
insert into contributors (contributor_name) values ('Bruno Mars');
insert into contributors (contributor_name) values ('Charles Moniz');
insert into contributors (contributor_name) values ('Anderson Park');
insert into contributors (contributor_name) values ('Randy Merrill');
insert into contributors (contributor_name) values ('Serban Ghenea');
insert into contributors (contributor_name) values ('John Hanes');
insert into contributors (contributor_name) values ('Bryce Bordone');
-- insert into contributors (contributor_name) values ('D'Mile');
-- TODO check how to escape ' character
insert into contributors (contributor_name) values ('Cody Chicowski');
insert into contributors (contributor_name) values ('Luigi Mazzochi');
insert into contributors (contributor_name) values ('Emma Kummrow');
insert into contributors (contributor_name) values ('Blake Espy');
insert into contributors (contributor_name) values ('Gared Crawford');
insert into contributors (contributor_name) values ('Tess Varley');
insert into contributors (contributor_name) values ('Natasha Colkett');
insert into contributors (contributor_name) values ('Jonathan Kim');
insert into contributors (contributor_name) values ('Yoshihiko Nakano');
insert into contributors (contributor_name) values ('Charles Moniz');

commit;

insert into recordlabels (label_name) values ('Aftermath');
insert into recordlabels (label_name) values ('Atlantic');

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
insert into compilationsamples values (4, 2, 2);
insert into compilationsamples values (4, 3, 3);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);

-- comp vocals plus instrumental
insert into compilations (compilation_name, creation_time, duration)
values ('beat it final comp', current_timestamp, 258);
insert into compilationsamples values (5, 1, 1);
insert into compilationsamples values (5, 4, 4);

commit;

insert into collections (collection_name) values ('Beat it');
insert into collectioncompilations values (1, 5);

insert into distribution (colletion_id, release_date, label_id, market_id)
values (1, to_date('08-02-2008', 'dd-mm-yyyy'), 1, 1);

commit;

insert into productioncontributions values (1, 11, 4);
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
end;