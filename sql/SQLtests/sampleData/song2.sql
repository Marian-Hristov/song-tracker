insert into contributors (contributor_name) values ('Dan Reynolds');
insert into contributors (contributor_name) values ('Ben McKee');
insert into contributors (contributor_name) values ('Daniel Platzman');
insert into contributors (contributor_name) values ('Wayne Sermon');
insert into contributors (contributor_name) values ('Justin Tranter');
insert into contributors (contributor_name) values ('Mattias Larsson');
insert into contributors (contributor_name) values ('Robin Fredriksson');

commit;

insert into recordlabels (label_name) values ('Interscope');

commit;

-- recordings

-- vocals
insert into recordings (recording_name, creation_time, duration)
values ('believer vocals', current_timestamp, 203);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 203, 0, 203);

-- compilations

--vocals
insert into compilations (compilation_name, creation_time, duration)
values ('believer vocals comp', current_timestamp, 203);
insert into recordingsamples values (6, 4, 5);

insert into collections (collection_name) values ('Believer');
insert into collectioncompilations values (2, 6);

commit;

insert into distributions (collection_id, release_date, label_id, market_id)
values (2, to_date('2017-02-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 6);

commit;

--production
insert into productioncontributions values (4, 12, 9);
insert into productioncontributions values (4, 13, 17);
insert into productioncontributions values (4, 14, 17);
insert into productioncontributions values (4, 12, 17);
insert into productioncontributions values (4, 15, 17);
insert into productioncontributions values (4, 16, 17);
insert into productioncontributions values (4, 17, 17);
insert into productioncontributions values (4, 18, 17);
-- compilation prod
insert into compilationcontributions values (6, 17, 4);
insert into compilationcontributions values (6, 18, 4);

commit;