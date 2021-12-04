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
    recording_id number(5),
    contributor_id number(5),
    role_id number(5),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references productionRoles (role_id)
);

create table musicalContributions (
    recording_id number(5),
    contributor_id number(5),
    role_id number(5),
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
    compilation_id number(5),
    contributor_id number(5),
    role_id number(5),
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
    compilation_id number(5),
    recording_id number(5),
    segment_id number(5),
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (segment_id) references segment (segment_id)
);

create table compilationsamples (
    compilation_id number(5),
    compilation_used number(5),
    segment_id number(5),
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
    collection_name varchar2(100) not null,
    set_id number(5),
    foreign key (set_id) references collections (collection_id)
);


create table distributions (
    distribution_id number(5) default distribution_id_seq.nextval primary key,
    collection_id number(5),
    release_date date not null,
    label_id number(5),
    market_id number(5),
    foreign key (collection_id) references collections (collection_id),
    foreign key (label_id) references recordlabels (label_id),
    foreign key (market_id) references markets (market_id)
);

create table collectioncompilations (
    collection_id number(5),
    compilation_id number(5),
    foreign key (collection_id) references collections (collection_id),
    foreign key (compilation_id) references compilations (compilation_id)
);

-- login
create table STLogs (
    log_id number(5) default log_id_seq.nextval primary key,
    log_message varchar2(1000) not null,
    log_time timestamp default localTimestamp(0) not null
);
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
        ', collection_name: '||:new.collection_name || ', set_id: ' || :new.set_id);
    end if;
    if updating then
        insert into STLogs (log_message) values (user ||
        ' updated table collections. Old values collection_id: '||:old.collection_id||
        ', collection_name: '||:old.collection_name||
        ', set_id:' || :old.set_id ||
        '. New values collection_id: '||:new.collection_id||
        ', collection_name: '||:new.collection_name ||
        ', set_id: ' || :new.set_id);
    end if;

    if deleting then
        insert into STLogs (log_message) values (user ||
        ' deleted from table collections values collection_id: '||:old.collection_id||
        ', collection_name: '||:old.collection_name || ', set_id: ' || :old.set_id);
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