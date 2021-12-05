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
insert into contributors (contributor_name) values ('D''Mile');
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

insert into productionroles (role_name) values ('string recorder');

commit;

insert into recordlabels (label_name) values ('Aftermath');
insert into recordlabels (label_name) values ('Atlantic');

commit;

-- recordings

-- vocals
insert into recordings (recording_name, creation_time, duration)
values ('Leave the Door Open vocals', current_timestamp, 222);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (10, 222, 50, 222);

-- guitar and recorder
insert into recordings (recording_name, creation_time, duration)
values ('Leave the Door Open guitar and recorder', current_timestamp, 242);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 242, 124, 342);

-- chords cellist, violonist, violist
insert into recordings (recording_name, creation_time, duration)
values ('Leave the Door Open chords', current_timestamp, 242);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 240, 45, 402);

commit;
-- compilations

-- vocals
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open vocals comp', current_timestamp, 222);
insert into recordingsamples values (7, 5, 6);

-- guitar and recorder
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open guitar and recorder comp', current_timestamp, 242);
insert into recordingsamples values (8, 6, 7);

-- chords
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open chords comp', current_timestamp, 242);
insert into recordingsamples values (9, 7, 8);

-- comp of guiter, recorder, and chords
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open instrumental final', current_timestamp, 242);
insert into compilationsamples values (10, 8, 7);
insert into compilationsamples values (10, 9, 8);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);

-- comp vocals plus instrumental
insert into compilations (compilation_name, creation_time, duration)
values ('beat it final comp', current_timestamp, 242);
insert into compilationsamples values (11, 7, 6);
insert into compilationsamples values (11, 10, 9);

commit;

insert into collections (collection_name) values ('Leave the Door Open');
insert into collectioncompilations values (3, 11);

insert into distributions (collection_id, release_date, label_id, market_id)
values (3, to_date('2020-03-05 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 3, 6);
insert into distributions (collection_id, release_date, label_id, market_id)
values (3, to_date('2020-03-05 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 4, 6);

commit;

insert into productioncontributions values (5, 22, 1);
insert into productioncontributions values (5, 22, 9);
insert into productioncontributions values (5, 24, 9);
insert into productioncontributions values (5, 22, 17);
insert into productioncontributions values (5, 24, 17);
insert into productioncontributions values (5, 20, 17);
insert into productioncontributions values (7, 30, 20);

commit;

-- guitar and recorder
insert into musicalcontributions values (6, 20, 12);
insert into musicalcontributions values (6, 22, 12);
insert into musicalcontributions values (6, 23, 24);
-- chords cellist, violonist, violist
insert into musicalcontributions values (7, 21, 8);
insert into musicalcontributions values (7, 31, 31);
insert into musicalcontributions values (7, 32, 31);
insert into musicalcontributions values (7, 33, 31);
insert into musicalcontributions values (7, 34, 31);
insert into musicalcontributions values (7, 35, 31);
insert into musicalcontributions values (7, 36, 31);
insert into musicalcontributions values (7, 37, 30);
insert into musicalcontributions values (7, 38, 30);

-- compilation prod
insert into compilationcontributions values (11, 23, 8);
insert into compilationcontributions values (11, 25, 13);
insert into compilationcontributions values (11, 26, 12);
insert into compilationcontributions values (11, 27, 12);
insert into compilationcontributions values (11, 28, 19);
insert into compilationcontributions values (11, 22, 4);
insert into compilationcontributions values (11, 29, 4);

commit;
