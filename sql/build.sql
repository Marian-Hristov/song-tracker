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

-- creating the sequences
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


-- dropping the tables
drop table musicalContributions;
drop table productionContributions;
drop table productionRoles;
drop table recordingsamples;
drop table compilationSamples;
drop table distributions;
drop table collectionCompilations;

drop table recordings;
drop table contributors;
drop table musicianRoles;
drop table compilations;
drop table segment;
drop table recordlabels;
drop table markets;
drop table collections;

-- recordings / contributions
create table productionRoles(
    role_id number(5) default production_id_seq.nextval primary key,
    role_name varchar2(100) not null
);

create table recordings (
    recording_id number(5) default recording_id_seq.nextval primary key,
    name varchar2(100) not null,
    create_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_recording check(duration > 0)
);

create table contributors (
    contributor_id number(5) default contributor_id_seq.nextval primary key ,
    name varchar2(100) not null
);

create table productionContributions (
    recording_id number(5),
    contributor_id number(5),
    role_id number(5),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references productionRoles (role_id)
);

create table musicianRoles (
    role_id number(5) default musician_id_seq.nextval primary key,
    name varchar2(100) not null
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
    name varchar2(100) not null,
    creation_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_compilation check(duration > 0)
);

create table segment (
    segment_id number(5) default segment_id_seq.nextval primary key,
    main_track_offset number(5,1) not null,
    duration_in_main_track number(5,1) not null,
    component_track_offset number(5,1) not null,
    duration_of_component number(5,1) not null,
    constraint check_positive_main_track_offset_segment check(main_track_offset >= 0),
    constraint check_positive_duration_in_main_track_segment check(duration_in_main_track > 0),
    constraint check_positive_component_track_offset_segment check(component_track_offset >= 0),
    constraint check_positive_duration_of_component check(duration_of_component > 0)
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
    name varchar2(100) not null
);

create table markets (
    market_id number(5) default market_id_seq.nextval primary key,
    name varchar2(100) not null
);

create table collections (
    collection_id number(5) default collection_id_seq.nextval primary key,
    name varchar2(100) not null
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