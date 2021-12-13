-- dropping the sequences
drop sequence distribution_id_seq;
drop sequence musician_id_seq;
drop sequence production_id_seq;
drop sequence label_id_seq;
drop sequence market_id_seq;
drop sequence segment_id_seq;
drop sequence compilation_id_seq;
drop sequence recording_id_seq;
drop sequence contributor_id_seq;
drop sequence collection_id_seq;
drop sequence log_id_seq;
drop sequence compilation_role_id_sq;

commit;
-- creating the sequences
create sequence log_id_seq
    increment by 1
    start with 1
    minvalue 1
    nocycle
    cache 2;

create sequence distribution_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence musician_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence production_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence label_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence market_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence segment_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence compilation_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence recording_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence contributor_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence collection_id_seq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

create sequence compilation_role_id_sq
    increment by 1
    start with 1
    minvalue 1
    maxvalue 99999
    nocycle
    cache 2;

commit;
-- dropping the tables
drop table STLogs;
drop table musicalContributions;
drop table productionContributions;
drop table productionRoles;
drop table recordingsamples;
drop table compilationSamples;
drop table distributions;
drop table collectionCompilations;
drop table compilationContributions;
drop table collectionSets;

drop table recordings;
drop table contributors;
drop table musicianRoles;
drop table compilations;
drop table segment;
drop table recordlabels;
drop table markets;
drop table collections;
drop table compilationRoles;

commit;

-- recordings / contributions
create table productionRoles(
    role_id number(5) default production_id_seq.nextval primary key,
    role_name varchar2(100) not null
);

create table musicianRoles (
    role_id number(5) default musician_id_seq.nextval primary key,
    role_name varchar2(100) not null
);

create table recordings (
    recording_id number(5) default recording_id_seq.nextval primary key,
    recording_name varchar2(100) not null,
    creation_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_recording check(duration >= 0)
);

create table contributors (
    contributor_id number(5) default contributor_id_seq.nextval primary key ,
    contributor_name varchar2(100) not null
);

create table productionContributions (
    recording_id number(5) not null,
    contributor_id number(5) not null,
    role_id number(5) not null,
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references productionRoles (role_id)
);

create table musicalContributions (
    recording_id number(5) not null,
    contributor_id number(5) not null,
    role_id number(5) not null,
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references musicianroles (role_id)
);

-- compilations / samples

create table compilations (
    compilation_id number(5) default compilation_id_seq.nextval primary key,
    compilation_name varchar2(100) not null,
    creation_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_compilation check(duration >= 0)
);

create table compilationRoles(
    role_id number(5) default compilation_role_id_sq.nextval primary key,
    role_name varchar2(100)
);

create table compilationContributions(
    compilation_id number(5) not null,
    contributor_id number(5) not null,
    role_id number(5) not null,
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references compilationRoles (role_id)
);

create table segment (
    segment_id number(5) default segment_id_seq.nextval primary key,
    main_track_offset number(5,1) not null,
    duration_in_main_track number(5,1) not null,
    component_track_offset number(5,1) not null,
    duration_of_component_used number(5,1) not null,
    constraint check_positive_main_track_offset_segment check(main_track_offset >= 0),
    constraint check_positive_duration_in_main_track_segment check(duration_in_main_track >= 0),
    constraint check_positive_component_track_offset_segment check(component_track_offset >= 0),
    constraint check_positive_duration_of_component_used check(duration_of_component_used >= 0)
);

create table recordingsamples (
    compilation_id number(5) not null,
    recording_id number(5) not null,
    segment_id number(5) not null,
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (segment_id) references segment (segment_id)
);

create table compilationsamples (
    compilation_id number(5) not null,
    compilation_used number(5) not null,
    segment_id number(5) not null,
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (compilation_used) references compilations (compilation_id),
    foreign key (segment_id) references segment (segment_id)
);


-- Album / Distribution

create table recordlabels (
    label_id number(5) default label_id_seq.nextval primary key,
    label_name varchar2(100) not null
);

create table markets (
    market_id number(5) default market_id_seq.nextval primary key,
    market_name varchar2(100) not null
);

create table collections (
    collection_id number(5) default collection_id_seq.nextval primary key,
    collection_name varchar2(100) not null
);

create table collectionSets (
    set_id number(5) not null,
    collection_id number(5) not null,
    foreign key (set_id) references collections (collection_id),
    foreign key (collection_id) references collections (collection_id)
);


create table distributions (
    distribution_id number(5) default distribution_id_seq.nextval primary key,
    collection_id number(5) not null,
    release_date date not null,
    label_id number(5) not null,
    market_id number(5) not null,
    foreign key (collection_id) references collections (collection_id),
    foreign key (label_id) references recordlabels (label_id),
    foreign key (market_id) references markets (market_id)
);

create table collectioncompilations (
    collection_id number(5) not null,
    compilation_id number(5) not null,
    foreign key (collection_id) references collections (collection_id),
    foreign key (compilation_id) references compilations (compilation_id)
);

-- login
create table STLogs (
    log_id number(5) default log_id_seq.nextval primary key,
    log_message varchar2(1000) not null,
    log_time timestamp default localTimestamp(0) not null
);
commit;

-- triggers
/
-- compilations/samples
create or replace trigger before_insert_update_delete_compilations
before insert or update or delete
on compilations
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table compilations values compilation_id: '||:new.compilation_id||
        ', compilation_name: '||:new.compilation_name||
        ', creation_time: '||:new.creation_time||
        ', duration: '||:new.duration);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table compilations. Old values compilation_id: '||:old.compilation_id||
        ', compilation_name: '||:old.compilation_name||
        ', creation_time: '||:old.creation_time||
        ', duration: '||:old.duration||
        '. New values compilation_id: '||:new.compilation_id||
        ', compilation_name: '||:new.compilation_name||
        ', creation_time: '||:new.creation_time||
        ', duration: '||:new.duration);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table compilations values compilation_id: '||:old.compilation_id||
        ', compilation_name: '||:old.compilation_name||
        ', creation_time: '||:old.creation_time||
        ', duration: '||:old.duration);
    end if;
end;
/
create or replace trigger before_insert_update_delete_compilationcontributions
before insert or update or delete
on compilationContributions
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table compilationContributions values compilation_id: '||:new.compilation_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table compilationContributions. Old values compilation_id: '||:old.compilation_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id||
        '. New values compilation_id: '||:new.compilation_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table compilationContributions values compilation_id: '||:old.compilation_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_compilationRoles
before insert or update or delete
on compilationRoles
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table compilationRoles values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table compilationRoles. Old values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name||
        '. New values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table compilationRoles values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_recordingSamples
before insert or update or delete
on recordingSamples
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table recordingSamples values compilation_id: '||:new.compilation_id||
        ', recording_id: '||:new.recording_id||
        ', segment_id: '||:new.segment_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table recordingSamples. Old values compilation_id: '||:old.compilation_id||
        ', recording_id: '||:old.recording_id||
        ', segment_id: '||:old.segment_id||
        '. New values compilation_id: '||:new.compilation_id||
        ', recording_id: '||:new.recording_id||
        ', segment_id: '||:new.segment_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table recordingSamples values compilation_id: '||:old.compilation_id||
        ', recording_id: '||:old.recording_id||
        ', segment_id: '||:old.segment_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_compilationSamples
before insert or update or delete
on compilationSamples
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table compilationSamples values compilation_id: '||:new.compilation_id||
        ', compilation_used: '||:new.compilation_used||
        ', segment_id: '||:new.segment_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table compilationSamples. Old values compilation_id: '||:old.compilation_id||
        ', compilation_used: '||:old.compilation_used||
        ', segment_id: '||:old.segment_id||
        '. New values compilation_id: '||:new.compilation_id||
        ', compilation_used: '||:new.compilation_used||
        ', segment_id: '||:new.segment_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table compilationSamples values compilation_id: '||:old.compilation_id||
        ', compilation_used: '||:old.compilation_used||
        ', segment_id: '||:old.segment_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_segment
before insert or update or delete
on segment
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table segment values segment_id: '||:new.segment_id||
        ', main_track_offset: '||:new.main_track_offset||
        ', duration_in_main_track: '||:new.duration_in_main_track||
        ', component_track_offset: '||:new.component_track_offset||
        ', duration_of_component_used: '||:new.duration_of_component_used);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table segment. Old values segment_id: '||:old.segment_id||
        ', main_track_offset: '||:old.main_track_offset||
        ', duration_in_main_track: '||:old.duration_in_main_track||
        ', component_track_offset: '||:old.component_track_offset||
        ', duration_of_component_used: '||:old.duration_of_component_used||
        '. New values segment_id: '||:new.segment_id||
        ', main_track_offset: '||:new.main_track_offset||
        ', duration_in_main_track: '||:new.duration_in_main_track||
        ', component_track_offset: '||:new.component_track_offset||
        ', duration_of_component_used: '||:new.duration_of_component_used);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table segment values segment_id: '||:old.segment_id||
        ', main_track_offset: '||:old.main_track_offset||
        ', duration_in_main_track: '||:old.duration_in_main_track||
        ', component_track_offset: '||:old.component_track_offset||
        ', duration_of_component_used: '||:old.duration_of_component_used);
    end if;
end;
/
commit;
/
-- album / distribution

create or replace trigger before_insert_update_delete_recordLabels
before insert or update or delete
on recordLabels
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table recordLabels values label_id: '||:new.label_id||
        ', label_name: '||:new.label_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table recordLabels. Old values label_id: '||:old.label_id||
        ', label_name: '||:old.label_name||
        '. New values label_id: '||:new.label_id||
        ', label_name: '||:new.label_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table recordLabels values label_id: '||:old.label_id||
        ', label_name: '||:old.label_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_markets
before insert or update or delete
on markets
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table markets values market_id: '||:new.market_id||
        ', market_name: '||:new.market_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table markets. Old values market_id: '||:old.market_id||
        ', market_name: '||:old.market_name||
        '. New values market_id: '||:new.market_id||
        ', market_name: '||:new.market_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table markets values market_id: '||:old.market_id||
        ', market_name: '||:old.market_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_distributions
before insert or update or delete
on distributions
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table distributions values distribution_id: '||:new.distribution_id||
        ', collection_id: '||:new.collection_id||
        ', release_date: '||:new.release_date||
        ', label_id: '||:new.label_id||
        ', market_id: '||:new.market_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table distributions. Old values distribution_id: '||:old.distribution_id||
        ', collection_id: '||:old.collection_id||
        ', release_date: '||:old.release_date||
        ', label_id: '||:old.label_id||
        ', market_id: '||:old.market_id||
        '. New values distribution_id: '||:new.distribution_id||
        ', collection_id: '||:new.collection_id||
        ', release_date: '||:new.release_date||
        ', label_id: '||:new.label_id||
        ', market_id: '||:new.market_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table distributions values distribution_id: '||:old.distribution_id||
        ', collection_id: '||:old.collection_id||
        ', release_date: '||:old.release_date||
        ', label_id: '||:old.label_id||
        ', market_id: '||:old.market_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_collections
before insert or update or delete
on collections
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table collections values collection_id: '||:new.collection_id||
        ', collection_name: '||:new.collection_name);
    end if;
    if updating then
        insert into STLogs (log_message) values (user ||
        ' updated table collections. Old values collection_id: '||:old.collection_id||
        ', collection_name: '||:old.collection_name||
        '. New values collection_id: '||:new.collection_id||
        ', collection_name: '||:new.collection_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table collections values collection_id: '||:old.collection_id||
        ', collection_name: '||:old.collection_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_collectionCompilations
before insert or update or delete
on collectionCompilations
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table collectionCompilations values collection_id: '||:new.collection_id||
        ', compilation_id: '||:new.compilation_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table collectionCompilations. Old values collection_id: '||:old.collection_id||
        ', compilation_id: '||:old.compilation_id||
        '. New values collection_id: '||:new.collection_id||
        ', compilation_id: '||:new.compilation_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table collectionCompilations values collection_id: '||:old.collection_id||
        ', compilation_id: '||:old.compilation_id);
    end if;
end;
/
commit;
/
-- recordings / contributors
create or replace trigger before_insert_update_delete_productionRoles
before insert or update or delete
on productionRoles
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table productionRoles values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table productionRoles. Old values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name||
        '. New values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table productionRoles values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_productionContributions
before insert or update or delete
on productionContributions
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table productionContributions values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table productionContributions. Old values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id||
        '. New values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table productionContributions values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_recordings
before insert or update or delete
on recordings
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table recordings values recording_id: '||:new.recording_id||
        ', recording_name: '||:new.recording_name||
        ', creation_time: '||:new.creation_time||
        ', duration: '||:new.duration);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table recordings. Old values recording_id: '||:old.recording_id||
        ', recording_name: '||:old.recording_name||
        ', creation_time: '||:old.creation_time||
        ', duration: '||:old.duration||
        '. New values recording_id: '||:new.recording_id||
        ', recording_name: '||:new.recording_name||
        ', creation_time: '||:new.creation_time||
        ', duration: '||:new.duration);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table recordings values recording_id: '||:old.recording_id||
        ', recording_name: '||:old.recording_name||
        ', creation_time: '||:old.creation_time||
        ', duration: '||:old.duration);
    end if;
end;
/
create or replace trigger before_insert_update_delete_productionContributions
before insert or update or delete
on productionContributions
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table productionContributions values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table productionContributions. Old values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id||
        '. New values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table productionContributions values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_musicalContributions
before insert or update or delete
on musicalContributions
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table musicalContributions values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table musicalContributions. Old values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id||
        '. New values recording_id: '||:new.recording_id||
        ', contributor_id: '||:new.contributor_id||
        ', role_id: '||:new.role_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table musicalContributions values recording_id: '||:old.recording_id||
        ', contributor_id: '||:old.contributor_id||
        ', role_id: '||:old.role_id);
    end if;
end;
/
create or replace trigger before_insert_update_delete_productionRoles
before insert or update or delete
on productionRoles
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table productionRoles values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table productionRoles. Old values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name||
        '. New values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table productionRoles values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_musicianRoles
before insert or update or delete
on musicianRoles
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table musicianRoles values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table musicianRoles. Old values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name||
        '. New values role_id: '||:new.role_id||
        ', role_name: '||:new.role_name);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table musicianRoles values role_id: '||:old.role_id||
        ', role_name: '||:old.role_name);
    end if;
end;
/
create or replace trigger before_insert_update_delete_collectionSets
before insert or update or delete
on collectionSets
for each row
declare
begin
    if inserting then
        insert into STLogs (log_message) values (user ||
        ' inserted into table collectionSets values set_id: '||:new.set_id||
        ', collection_id: '||:new.collection_id);
    end if;
    if updating then

        insert into STLogs (log_message) values (user ||
        ' updated table collectionSets. Old values set_id: '||:old.set_id||
        ', collection_id: '||:old.collection_id||
        '. New values set_id: '||:new.set_id||
        ', collection_id: '||:new.collection_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table collectionSets values set_id: '||:old.set_id||
        ', collection_id: '||:old.collection_id);
    end if;
end;
/
commit;
/
insert into markets (market_name) values ('Asia');
insert into markets (market_name) values ('Africa');
insert into markets (market_name) values ('Europe');
insert into markets (market_name) values ('North-America');
insert into markets (market_name) values ('South-America'); -- 5
insert into markets (market_name) values ('Worldwide');
commit;
insert into musicianRoles (role_name) values ('accordionist');
insert into musicianRoles (role_name) values ('piper');
insert into musicianRoles (role_name) values ('banjoist');
insert into musicianRoles (role_name) values ('bongosero');
insert into musicianRoles (role_name) values ('bassist'); -- 5
insert into musicianRoles (role_name) values ('bassoonist');
insert into musicianRoles (role_name) values ('clarinetist');
insert into musicianRoles (role_name) values ('cellist');
insert into musicianRoles (role_name) values ('drummer');
insert into musicianRoles (role_name) values ('euphoniumist'); -- 10
insert into musicianRoles (role_name) values ('flutist');
insert into musicianRoles (role_name) values ('guitarist');
insert into musicianRoles (role_name) values ('harpist');
insert into musicianRoles (role_name) values ('harpsichordist');
insert into musicianRoles (role_name) values ('hornist'); -- 15
insert into musicianRoles (role_name) values ('keyboardist');
insert into musicianRoles (role_name) values ('lutenist');
insert into musicianRoles (role_name) values ('mandolinist');
insert into musicianRoles (role_name) values ('marimbist');
insert into musicianRoles (role_name) values ('oboist'); -- 20
insert into musicianRoles (role_name) values ('organist');
insert into musicianRoles (role_name) values ('percussionist');
insert into musicianRoles (role_name) values ('pianist');
insert into musicianRoles (role_name) values ('recorder player');
insert into musicianRoles (role_name) values ('saxophonist'); -- 25
insert into musicianRoles (role_name) values ('tocaores');
insert into musicianRoles (role_name) values ('trumpeter');
insert into musicianRoles (role_name) values ('tubaist');
insert into musicianRoles (role_name) values ('ukulelist');
insert into musicianRoles (role_name) values ('violist'); -- 30
insert into musicianRoles (role_name) values ('violinist');
insert into musicianRoles (role_name) values ('xylophonist');
commit;
insert into productionRoles (role_name) values ('composer');
insert into productionRoles (role_name) values ('remixer');
insert into productionRoles (role_name) values ('recording engineer');
insert into productionRoles (role_name) values ('producer');
insert into productionRoles (role_name) values ('co-producer'); -- 5
insert into productionRoles (role_name) values ('accompanist');
insert into productionRoles (role_name) values ('arranger');
insert into productionRoles (role_name) values ('audio engineer');
insert into productionRoles (role_name) values ('vocalist');
insert into productionRoles (role_name) values ('backing singer'); -- 10
insert into productionRoles (role_name) values ('conductor');
insert into productionRoles (role_name) values ('mixer');
insert into productionRoles (role_name) values ('mastering engineer');
insert into productionRoles (role_name) values ('sound designer');
insert into productionRoles (role_name) values ('performer'); -- 15
insert into productionRoles (role_name) values ('writter');
insert into productionRoles (role_name) values ('lyricist');
insert into productionRoles (role_name) values ('assistant engineer');
insert into productionRoles (role_name) values ('mixing assistant');
commit;
insert into compilationRoles (role_name) values ('composer');
insert into compilationRoles (role_name) values ('remixer');
insert into compilationRoles (role_name) values ('recording engineer');
insert into compilationRoles (role_name) values ('producer');
insert into compilationRoles (role_name) values ('co-producer'); -- 5
insert into compilationRoles (role_name) values ('accompanist');
insert into compilationRoles (role_name) values ('arranger');
insert into compilationRoles (role_name) values ('audio engineer');
insert into compilationRoles (role_name) values ('vocalist');
insert into compilationRoles (role_name) values ('backing singer'); -- 10
insert into compilationRoles (role_name) values ('conductor');
insert into compilationRoles (role_name) values ('mixer');
insert into compilationRoles (role_name) values ('mastering engineer');
insert into compilationRoles (role_name) values ('sound designer');
insert into compilationRoles (role_name) values ('performer'); -- 15
insert into compilationRoles (role_name) values ('writter');
insert into compilationRoles (role_name) values ('lyricist');
insert into compilationRoles (role_name) values ('assistant engineer');
insert into compilationRoles (role_name) values ('mixing assistant');
commit;
/
create or replace package collection_mgmt as
    procedure createCollection (collection_name in collections.collection_name%type);
    procedure removeCollection (ref_collection_id collections.collection_id%type);
    procedure addCompilationToCollection(collection_id in collections.collection_id%type, compilation_id in compilations.compilation_id%type);
    procedure removeCompilationFromCollection( collection_id in collections.collection_id%type, compilation_id in compilations.compilation_id%type);
    procedure updateCollection(ref_collection_id in collections.collection_id%type, ref_collection_name in collections.collection_name%type);
    procedure addCollectionToSet(collection_id in collections.collection_id%type, set_id in collections.collection_id%type);
    procedure removeCollectionFromSet(collection_id in collections.collection_id%type, set_id in collections.collection_id%type);
end collection_mgmt;
/
commit;
/
create or replace package body collection_mgmt as

    procedure addCollectionToSet(collection_id in collections.collection_id%type, set_id in collections.collection_id%type)
    as
    begin
        if(collection_id < 1 or set_id < 1) then
            raise_application_error(-20001, 'the collection_id or the set_id are invalid');
        end if;
        insert into collectionSets values (set_id, collection_id);
    end;

    procedure removeCollectionFromSet(collection_id in collections.collection_id%type, set_id in collections.collection_id%type)
    as
    begin
        if(collection_id < 1 or set_id < 1) then
            raise_application_error(-20001, 'the collection_id or the set_id are invalid');
        end if;
        delete from collectionSets where collection_id = collection_id and set_id = set_id;
    end;

    procedure removeCollection(ref_collection_id collections.collection_id%type) is
    begin
        if(ref_collection_id < 1) then
            raise_application_error(-20001, 'the collection_id is invalid');
        end if;
        delete from collectioncompilations where collection_id = ref_collection_id;
        delete from collectionsets where collection_id = ref_collection_id;
        delete from collections where collection_id = ref_collection_id;
    end;

    procedure createCollection(collection_name in collections.collection_name%type)
    as
    begin
        if (collection_name is null) then
            raise_application_error(-20001, 'the collection_name in the procedure createCollection is null');
        end if;
        insert into collections (collection_name) values (collection_name);
    end;

    procedure addCompilationToCollection(
        collection_id in collections.collection_id%type,
        compilation_id in compilations.compilation_id%type
    )
    as
    begin
        if (collection_id < 1 or compilation_id < 1) then
            raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1');
        end if;
        for compilation in (select * from collectionCompilations) loop
            if(compilation.compilation_id = compilation_id and compilation.collection_id = collection_id) then
                raise_application_error(-20001, 'this compilation is already in this collection');
            end if;
        end loop;
        insert into collectionCompilations values (collection_id, compilation_id);
    end;

    procedure removeCompilationFromCollection(
        collection_id in collections.collection_id%type,
        compilation_id in compilations.compilation_id%type
    )
    as
    begin
        if (collection_id < 1 or compilation_id < 1) then
            raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1');
        end if;
        delete from collectionCompilations where collection_id = collection_id and compilation_id = compilation_id;
    end;

    procedure updateCollection(ref_collection_id in collections.collection_id%type, ref_collection_name in collections.collection_name%type)
    as
    begin
         if (ref_collection_id < 1) then
            raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1');
        end if;
        update collections set collection_name = ref_collection_name where collection_id = ref_collection_id;
    end;
end collection_mgmt;
/
commit;
/
create or replace package compilation_mgmt as
    procedure createCompilation(ref_compilation_name in compilations.compilation_name%type);
    procedure addSampleToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_main_track_offset in segment.main_track_offset%type,
        ref_duration_in_main_track in segment.duration_in_main_track%type,
        ref_component_track_offset in segment.component_track_offset%type,
        ref_duration_of_component_used in segment.duration_of_component_used%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    );
    procedure deleteSampleFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    );
    procedure deleteCompilation(ref_compilation_id in compilations.compilation_id%type);
    procedure updateCompilation(ref_compilation_id in compilations.compilation_id%type, ref_compilation_name in compilations.compilation_name%type);
    procedure addContributorToCompilation(ref_compilation_id in compilations.compilation_id%type, ref_contributor_id in contributors.contributor_id%type, ref_role_id in compilationRoles.role_id%type);
    procedure removeContributorFromCompilation(ref_compilation_id in compilations.compilation_id%type, ref_contributor_id in contributors.contributor_id%type, ref_role_id in compilationRoles.role_id%type);
    procedure updateSegment(
        ref_segment_id segment.segment_id%type,
        new_main_track_offset segment.main_track_offset%type,
        new_duration_in_main_track segment.duration_in_main_track%type,
        new_component_track_offset segment.component_track_offset%type,
        new_duration_of_component_used segment.duration_of_component_used%type
    );
end compilation_mgmt;
/
commit;
/
create or replace package body compilation_mgmt as

    procedure createCompilation(ref_compilation_name in compilations.compilation_name%type)
    as
    begin
        if(ref_compilation_name is null) then
            raise_application_error(-20001, 'the compilation_name is null for procedure createCompilation');
        end if;
        insert into compilations (compilation_name, creation_time, duration) values (ref_compilation_name, current_timestamp, 0);
    end;

    procedure addSampleToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_main_track_offset in segment.main_track_offset%type,
        ref_duration_in_main_track in segment.duration_in_main_track%type,
        ref_component_track_offset in segment.component_track_offset%type,
        ref_duration_of_component_used in segment.duration_of_component_used%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    )
    as
        segment_id segment.segment_id%type;
        max_duration number;
        segment_start segment.main_track_offset%type;
        segment_duration segment.duration_in_main_track%type;
    begin
        -- check for empty values
        if(ref_main_track_offset < 0 or ref_duration_in_main_track < 0 or ref_component_track_offset < 0 or ref_duration_of_component_used < 0) then
            raise_application_error(-20001, 'one or more of the durations is less than 0');
        end if;

        -- create the segment
        insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used) values (ref_main_track_offset, ref_duration_in_main_track, ref_component_track_offset, ref_duration_of_component_used);
        segment_id := segment_id_seq.currval;

        --  link segment to compilation
        if(ref_sample_type = 'c') then
            insert into compilationSamples values (ref_compilation_id, ref_sample_id, segment_id);
        elsif (ref_sample_type = 'r') then
            insert into recordingSamples values (ref_compilation_id, ref_sample_id, segment_id);
        else
            raise_application_error(-20001, 'the sample type must be either "c" or "r"');
        end if;

        -- change the duration of the compilation
        max_duration :=0;
        for sample in (select * from compilationSamples where compilation_id = ref_compilation_id) loop
            select main_track_offset, duration_in_main_track into segment_start, segment_duration
                from segment where segment_id = sample.segment_id;
            if (segment_start + segment_duration > max_duration) then
                max_duration := segment_start + segment_duration;
            end if;
        end loop;

        for sample in (select * from recordingSamples where compilation_id = ref_compilation_id) loop
            select main_track_offset, duration_in_main_track into segment_start, segment_duration
                from segment where segment_id = sample.segment_id;
            if (segment_start + segment_duration > max_duration) then
                max_duration := segment_start + segment_duration;
            end if;
        end loop;

        update compilations set duration = max_duration where compilation_id = ref_compilation_id;
    end;

    procedure deleteSampleFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    )
    as
    begin
        if (ref_sample_type = 'c') then
            for sample in (select * from compilationSamples where compilation_id = ref_compilation_id and compilation_used = ref_sample_id) loop
                delete from compilationSamples where segment_id = sample.segment_id and compilation_used = ref_sample_id;
                delete from segment where segment_id = sample.segment_id;
            end loop;
        elsif (ref_sample_type = 'r') then
            for sample in (select * from recordingSamples where compilation_id = ref_compilation_id and recording_id = ref_sample_id) loop
                delete from recordingSamples where segment_id = sample.segment_id and recording_id = ref_sample_id;
                delete from segment where segment_id = sample.segment_id;
            end loop;
        else
            raise_application_error(-20001, 'the sample type must be either (c)ompilation or (r)ecording');
        end if;
    end;

    procedure deleteCompilation(ref_compilation_id in compilations.compilation_id%type)
    as
    begin

        for sample in (select * from compilationSamples where compilation_id = ref_compilation_id) loop
            delete from compilationSamples where segment_id = sample.segment_id;
            delete from segment where segment_id = sample.segment_id;
        end loop;
        for sample in (select * from recordingSamples where compilation_id = ref_compilation_id) loop
            delete from recordingSamples where segment_id = sample.segment_id;
            delete from segment where segment_id = sample.segment_id;
        end loop;
        delete from compilationContributions where compilation_id = ref_compilation_id;
        delete from collectionCompilations where compilation_id = ref_compilation_id;
        delete from compilations where compilation_id = ref_compilation_id;
    end;

    procedure updateCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_compilation_name in compilations.compilation_name%type
    )
    as
    begin
        update compilations set compilation_name = ref_compilation_name where compilation_id = ref_compilation_id;
    end;
    procedure addContributorToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_contributor_id in contributors.contributor_id%type,
        ref_role_id in compilationRoles.role_id%type
    )
    as
    begin
        if(ref_compilation_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more of the parameters for the procedure addContributorToCompilation is invalid');
        end if;
        insert into compilationContributions values (ref_compilation_id, ref_contributor_id, ref_role_id);
    end;

    procedure removeContributorFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_contributor_id in contributors.contributor_id%type,
        ref_role_id in compilationRoles.role_id%type
    )
    as
    begin
        if(ref_compilation_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more of the parameters for the procedure removeContributorFromCompilation is invalid');
        end if;
        delete from compilationContributions where compilation_id = ref_compilation_id and contributor_id = ref_contributor_id and role_id = ref_role_id;
    end;

    procedure updateSegment(
        ref_segment_id segment.segment_id%type,
        new_main_track_offset segment.main_track_offset%type,
        new_duration_in_main_track segment.duration_in_main_track%type,
        new_component_track_offset segment.component_track_offset%type,
        new_duration_of_component_used segment.duration_of_component_used%type
    )
    as
    begin
        if(ref_segment_id < 1 or new_main_track_offset < 0 or new_duration_in_main_track < 0 or new_component_track_offset < 0 or new_duration_of_component_used < 0) then
            raise_application_error(-20001, 'one or more of the parameters for the procedure updateSegment is invalid');
        end if;
        update segment
        set
        main_track_offset = new_main_track_offset,
        duration_in_main_track = new_duration_in_main_track,
        component_track_offset = new_component_track_offset,
        duration_of_component_used = new_duration_of_component_used
        where segment_id = ref_segment_id;
    end;

end compilation_mgmt;
/
commit;
/
create or replace package contributor_mgmt as
    function contributorExists(searched_contributor_name varchar2)
        return number;
    procedure addContributor(new_contributor_name varchar2);
    procedure removeContributor(deleted_contributor_id number);
    procedure updateContributor(old_contributor_name varchar2, new_contributor_name varchar2);
end contributor_mgmt;
/
commit;
/
create or replace package body contributor_mgmt as
    -- Checking if a contributor exists
    function contributorExists(searched_contributor_name varchar2)
    return number
    is
        found contributors.contributor_id%type;
    begin
        if searched_contributor_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select contributor_id into found from contributors where contributor_name = searched_contributor_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add contributor
    procedure addContributor(new_contributor_name varchar2) is
    begin
        if new_contributor_name is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        insert into contributors
        values (contributor_id_seq.nextval, new_contributor_name);
    end;
    -- Remove a contributor
    procedure removeContributor(deleted_contributor_id number) is
    begin
        if deleted_contributor_id < 1 then
            raise_application_error(-20001, 'one or more arguments are invalid');
        end if;
        -- Deleting the contributor from contribution tables
        delete from musicalContributions
        where contributor_id = deleted_contributor_id;
        delete from productionContributions
        where contributor_id = deleted_contributor_id;
        -- Deleting contributor in contributors table
        delete from contributors
        where contributor_id = deleted_contributor_id;
    end;
    -- Updating a contributor
    procedure updateContributor(old_contributor_name varchar2, new_contributor_name varchar2)
    is
        found contributors.contributor_id%type;
    begin
        if(old_contributor_name is null or new_contributor_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (contributorExists(old_contributor_name) = 1) then
            raise_application_error(-20003, 'cannot update contributor that does not exist');
        end if;
        -- Getting the id of the contributor based on the name
        select contributor_id into found from contributors where contributor_name = old_contributor_name;
        -- Updating contributor name
        update contributors set contributor_name = new_contributor_name where contributor_id = found;
    end;
end contributor_mgmt;
/
commit;
/
create or replace package distribution_mgmt as
    function distributionExists(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type)
        return number;
    procedure addDistribution(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type);
    procedure removeDistribution(deleted_distribution_id distributions.distribution_id%type);
    procedure updateDistribution(changed_distribution_id distributions.distribution_id%type, new_ref_collection_id collections.collection_id%type, new_ref_release_date date, new_ref_label_id recordlabels.label_id%type, new_ref_market_id markets.market_id%type);
end distribution_mgmt;
/
commit;
/
create or replace package body distribution_mgmt as
    -- Checking if a distribution exists
    function distributionExists(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type)
    return number
    is
        foundDistribution distributions.distribution_id%type;
    begin
        if (ref_collection_id is null or ref_release_date is null or ref_label_id is null or ref_market_id is null) then
                    raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (ref_collection_id < 1 or ref_label_id < 1 or ref_market_id < 1) then
                    raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        select distribution_id into foundDistribution from distributions
        where
        collection_id = ref_collection_id and
        release_date = ref_release_date and
        label_id = ref_label_id and
        market_id = ref_market_id;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Adding a distribution
    procedure addDistribution(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type) is
    begin
        if (ref_collection_id is null or ref_release_date is null or ref_label_id is null or ref_market_id is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        elsif (ref_collection_id < 1 or ref_label_id < 1 or ref_market_id < 1) then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        if (distributionExists(ref_collection_id , ref_release_date, ref_label_id, ref_market_id) = 0) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
        insert into distributions (collection_id, release_date, label_id, market_id)
        values (ref_collection_id, ref_release_date, ref_label_id, ref_market_id);
    end;
    -- Remove a distribution
    procedure removeDistribution(deleted_distribution_id distributions.distribution_id%type)
    is
        foundDistribution distributions.distribution_id%type;
    begin
        if deleted_distribution_id is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        -- checking if the distribution exists
        select distribution_id into foundDistribution from distributions where distribution_id = deleted_distribution_id;
        if sql%notfound then
            raise_application_error(-20003, 'cannot delete distribution that does not exist');
        end if;
        delete from distributions where distribution_id = foundDistribution;
    end;
    -- Update a distribution
    procedure updateDistribution(changed_distribution_id distributions.distribution_id%type, new_ref_collection_id collections.collection_id%type, new_ref_release_date date, new_ref_label_id recordlabels.label_id%type, new_ref_market_id markets.market_id%type) is
    begin
        if (changed_distribution_id is null or new_ref_collection_id is null or new_ref_release_date is null or new_ref_label_id is null or new_ref_market_id is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(new_ref_collection_id < 1 or new_ref_label_id < 1 or new_ref_market_id < 1) then
            raise_application_error(-20001, 'one or many arguments are in invalid range');
        end if;
        if (distributionExists(new_ref_collection_id , new_ref_release_date, new_ref_label_id, new_ref_market_id) = 0) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
        update distributions
        set
        collection_id = new_ref_collection_id,
        release_date = new_ref_release_date,
        label_id = new_ref_label_id,
        market_id = new_ref_market_id
        where distribution_id = changed_distribution_id;
    end;
end distribution_mgmt;
/
commit;
/
create or replace package label_mgmt as
    function labelExists(searched_label_name recordlabels.label_name%type)
        return number;
    procedure addLabel(new_label_name recordlabels.label_name%type);
    procedure removeLabel(deleted_label_id recordlabels.label_id%type);
    procedure updateLabel(old_label_name recordlabels.label_name%type, new_label_name recordlabels.label_name%type);
end label_mgmt;
/
commit;
/
create or replace package body label_mgmt as
    -- Checking if a label exists
    function labelExists(searched_label_name recordlabels.label_name%type)
    return number
    is
        found recordLabels.label_id%type;
    begin
        if searched_label_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select label_id into found from recordLabels where label_name = searched_label_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a label
    procedure addLabel(new_label_name recordlabels.label_name%type) is
    begin
        if new_label_name is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        insert into recordLabels (label_name)
        values (new_label_name);
    end;
    -- Remove a label
    procedure removeLabel(deleted_label_id recordlabels.label_id%type) is
    begin
        if deleted_label_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif deleted_label_id < 1 then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        -- Deleting the label from distributions table
        delete from distributions
        where label_id = deleted_label_id;
        -- Deleting label in recordLabels table
        delete from recordLabels
        where label_id = deleted_label_id;
    end;
    -- Updating a role
    procedure updateLabel(old_label_name recordlabels.label_name%type, new_label_name recordlabels.label_name%type)
    is
        found recordLabels.label_id%type;
    begin
        if(old_label_name is null or new_label_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (labelExists(old_label_name) = 1) then
            raise_application_error(-20003, 'cannot update label that does not exist');
        elsif (labelExists(new_label_name) = 0) then
            raise_application_error(-20003, 'cannot update label to label that already exists');
        end if;
        -- Getting the id of the label based on the name
        select label_id into found from recordLabels where label_name = old_label_name;
        -- Updating label name
        update recordLabels set label_name = new_label_name where label_id = found;
    end;
end label_mgmt;
/
commit;
/
create or replace package market_mgmt as
    function marketExists(searched_market_name markets.market_name%type)
        return number;
    procedure addMarket(new_market markets.market_name%type);
    procedure removeMarket(removed_market_id markets.market_id%type);
    procedure updateMarket(old_market_name markets.market_name%type, new_market_name markets.market_name%type);
end market_mgmt;
/
commit
/
create or replace package body market_mgmt as
--    -- Check if a market exists
    function marketExists(searched_market_name markets.market_name%type)
    return number
    is
        found markets.market_id%type;
    begin
        if searched_market_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select market_id into found from markets where market_name = searched_market_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a new market
    procedure addMarket(new_market markets.market_name%type) is
    begin
        if new_market is null then
            raise_application_error(-20002, 'one or more arguments are null or empty');
        end if;
        insert into markets (market_name)
        values (new_market);
    end;
    -- Remove a market
    procedure removeMarket(removed_market_id markets.market_id%type) is
    begin
        if removed_market_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif removed_market_id < 1 then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        -- Deleting market in markets table
        delete from markets
        where market_id = removed_market_id;
    end;
    -- Update market
    procedure updateMarket(old_market_name markets.market_name%type, new_market_name markets.market_name%type)
    is
        found markets.market_id%type;
    begin
        if(old_market_name is null or new_market_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (marketExists(old_market_name) = 1) then
            raise_application_error(-20003, 'cannot update market that does not exist');
        elsif (marketExists(new_market_name) = 0) then
            raise_application_error(-20003, 'cannot update market to market that already exists');
        end if;
        -- Getting the id of the market based on the name
        select market_id into found from markets where market_name = old_market_name;
        -- Updating market name
        update markets set market_name = new_market_name where market_id = found;
    end;
end market_mgmt;
/
commit;
/
create or replace package recording_mgmt as
    procedure addRecording(new_recording_name recordings.recording_name%type, recording_duration recordings.duration%type);
    procedure removeRecording(removed_recording_id recordings.recording_id%type);
    procedure updateRecording(changed_recording_id recordings.recording_id%type, new_recording_name recordings.recording_name%type, new_recording_duration recordings.duration%type);
    procedure addContributorFromRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char);
    procedure removeContributorFromRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char);
end recording_mgmt;
/
commit;
/
create or replace package body recording_mgmt as
    -- Add a recording
    procedure addRecording(new_recording_name recordings.recording_name%type, recording_duration recordings.duration%type) is
    begin
        if (new_recording_name is null or recording_duration is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        end if;
        insert into recordings (recording_name, creation_time, duration)
        values (new_recording_name, current_timestamp, recording_duration);
    end;
    -- Remove a recording
    procedure removeRecording(removed_recording_id recordings.recording_id%type) is
    begin
        if removed_recording_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        -- Deleting all references off recording, delete statement won't throw an error if
        -- there is nothing to delete
        delete from musicalContributions where recording_id = removed_recording_id;
        delete from productionContributions where recording_id = removed_recording_id;
        delete from recordingSamples where recording_id = removed_recording_id;
        delete from recordings where recording_id = removed_recording_id;
    end;
    -- Updating a recording
    procedure updateRecording(changed_recording_id recordings.recording_id%type, new_recording_name recordings.recording_name%type, new_recording_duration recordings.duration%type)
    is
        foundRecording recordings.recording_id%type;
    begin
        if (changed_recording_id is null or new_recording_name is null or new_recording_duration is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        -- checking if the given recording id exists
        select recording_id into foundRecording from recordings where recording_id = changed_recording_id;
        -- updating the recording
        update recordings
        set
        recording_name = new_recording_name,
        duration = new_recording_duration
        where recording_id = changed_recording_id;
    end;
    -- Adding a contributor with a role to a recording
    procedure addContributorFromRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char) is
    begin
        if (ref_recording_id is null or ref_contributor_id is null or ref_role_id is null or category is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (ref_recording_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        if category = 'm' then
            insert into musicalContributions
            values (ref_recording_id, ref_contributor_id, ref_role_id);
        elsif category = 'p' then
            insert into productionContributions
            values (ref_recording_id, ref_contributor_id, ref_role_id);
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Removing a contributor from a recording
    procedure removeContributorFromRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char) is
    begin
        if (ref_recording_id is null or ref_contributor_id is null or ref_role_id is null or category is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (ref_recording_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        if category = 'm' then
            delete from musicalContributions
            where recording_id = ref_recording_id
            and contributor_id = ref_contributor_id
            and role_id = ref_role_id;
        elsif category = 'p' then
            delete from productionContributions
            where recording_id = ref_recording_id
            and contributor_id = ref_contributor_id
            and role_id = ref_role_id;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
end recording_mgmt;
/
commit;
/
create or replace package role_mgmt as
    function roleExists(category char, searched_role_name musicianRoles.role_name%type)
        return number;
    procedure addRole(category char, new_role_name musicianRoles.role_name%type);
    procedure removeRole(category char, deleted_role_name musicianRoles.role_name%type);
    procedure updateRole(category char, old_role_name musicianRoles.role_name%type, new_role_name musicianRoles.role_name%type);
end role_mgmt;
/
commit;
/
create or replace package body role_mgmt as
    -- Checking if a specified role exists
    function roleExists (category char, searched_role_name musicianRoles.role_name%type)
    return number
    is
        found musicianRoles.role_name%type;
    begin
        if (category is null or searched_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        if category = 'm' then
            select role_id into found from musicianRoles where role_name = searched_role_name;
        elsif category = 'p' then
            select role_id into found from productionRoles where role_name = searched_role_name;
        elsif category = 'c' then
            select role_id into found from compilationRoles where role_name = searched_role_name;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a role
    procedure addRole(category char, new_role_name musicianRoles.role_name%type) is
    begin
        if (category is null or new_role_name is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(roleExists(category, new_role_name) = 0) then
            raise_application_error(-20004, 'role already exists');
        end if;
        if category = 'm' then
            insert into musicianRoles (role_name)
            values (new_role_name);
        elsif category = 'p' then
            insert into productionRoles (role_name)
            values (new_role_name);
        elsif category = 'c' then
            insert into compilationRoles (role_name)
            values (new_role_name);
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Remove a role
    procedure removeRole(category char, deleted_role_name musicianRoles.role_name%type)
    is
        found musicianRoles.role_id%type;
    begin
        if (category is null or deleted_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (roleExists(category, deleted_role_name) = 1) then
            raise_application_error(-20003, 'cannot delete role that does not exist');
        end if;
        if category = 'm' then
            -- Getting the id of the role based on the name
            select role_id into found from musicianRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from musicalContributions
            where role_id = found;
            -- Deleting role in role table
            delete from musicianRoles
            where role_id = found;
        elsif category = 'p' then
            -- Getting the id of the role based on the name
            select role_id into found from productionRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from productionContributions
            where role_id = found;
            -- Deleting role in role table
            delete from productionRoles
            where role_id = found;
        elsif category = 'c' then
            -- Getting the id of the role based on the name
            select role_id into found from compilationRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from compilationContributions
            where role_id = found;
            -- Deleting role in role table
            delete from compilationRoles
            where role_id = found;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Updating a role
    procedure updateRole(category char, old_role_name musicianRoles.role_name%type, new_role_name musicianRoles.role_name%type)
    is
        found musicianRoles.role_id%type;
    begin
        if (category is null or old_role_name is null or new_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (roleExists(category, old_role_name) = 1) then
            raise_application_error(-20003, 'cannot update role that does not exist');
        elsif (roleExists(category, new_role_name) = 0) then
            raise_application_error(-20003, 'cannot update role to role that already exists');
        end if;
        if category = 'm' then
            -- Getting the id of the role based on the name
            select role_id into found from musicianRoles where role_name = old_role_name;
            -- Updating role name
            update musicianRoles set role_name = new_role_name where role_id = found;
        elsif category = 'p' then
            -- Getting the id of the role based on the name
            select role_id into found from productionRoles where role_name = old_role_name;
            -- Updating role name
            update productionRoles set role_name = new_role_name where role_id = found;
        elsif category = 'c' then
            -- Getting the id of the role based on the name
            select role_id into found from compilationRoles where role_name = old_role_name;
            -- Updating role name
            update compilationRoles set role_name = new_role_name where role_id = found;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
end role_mgmt;
/
commit;
/
-- sample songs
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

-- song 2
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

-- song 3
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
insert into recordingsamples values (7, 5, 9);

-- guitar and recorder
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open guitar and recorder comp', current_timestamp, 242);
insert into recordingsamples values (8, 6, 10);

-- chords
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open chords comp', current_timestamp, 242);
insert into recordingsamples values (9, 7, 11);

-- comp of guiter, recorder, and chords
insert into compilations (compilation_name, creation_time, duration)
values ('Leave the Door Open instrumental final', current_timestamp, 242);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into compilationsamples values (10, 8, 12);
insert into compilationsamples values (10, 9, 13);

-- comp vocals plus instrumental
insert into compilations (compilation_name, creation_time, duration)
values ('beat it final comp', current_timestamp, 242);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used)
values (0, 258, 0, 258);
insert into compilationsamples values (11, 7, 14);
insert into compilationsamples values (11, 10, 15);

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
