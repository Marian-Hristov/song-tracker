
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

-- recordings / contrubutions
create table productionRoles(
    role_id char(5) primary key,
    role_name varchar2(100) not null
);

create table recordings (
    recording_id char(5) primary key,
    name varchar2(100) not null,
    create_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_recording check(duration > 0)
);

create table contributors (
    contributor_id char(5) primary key,
    name varchar2(100) not null
);

create table productionContributions (
    recording_id char(5),
    contributor_id char(5),
    role_id char(5),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references productionRoles (role_id)
);

create table musicianRoles (
    role_id char(5) primary key,
    name varchar2(100) not null
);

create table musicalContributions (
    recording_id char(5),
    contributor_id char(5),
    role_id char(5),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (contributor_id) references contributors (contributor_id),
    foreign key (role_id) references musicianroles (role_id)
);

-- compilations / samples


create table compilations (
    compilation_id char(5) primary key,
    name varchar2(100) not null,
    creation_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_compilation check(duration > 0)
);

create table segment (
    segment_id char(5) primary key,
    main_track_offset number(5,1) not null,
    duration_in_main_track number(5,1) not null,
    component_track_offset number(5,1) not null,
    duration_of_component number(5,1) not null,
    constraint check_positive_main_track_offset_segment check(main_track_offset >= 0),
    constraint check_postive_duration_in_main_track_segment check(duration_in_main_track > 0),
    constraint check_positive_component_track_offset_segment check(component_track_offset >= 0),
    constraint check_positive_duration_of_component check(duration_of_component > 0)
);

create table recordingsamples (
    compilation_id char(5),
    recording_id char(5),
    segment_id char(5),
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (recording_id) references recordings (recording_id),
    foreign key (segment_id) references segment (segment_id)
);

create table compilationsamples (
    compilation_id char(5),
    compilation_used char(5),
    segment_id char(5),
    foreign key (compilation_id) references compilations (compilation_id),
    foreign key (compilation_used) references compilations (compilation_id),
    foreign key (segment_id) references segment (segment_id)
);


-- Album / Distribution

create table recordlabels (
    label_id char(5) primary key,
    name varchar2(100) not null
);

create table markets (
    market_id char(5) primary key,
    name varchar2(100) not null
);

create table collections (
    collection_id char(5) primary key,
    name varchar2(100) not null
);

create table distributions (
    distribution_id char(5) primary key,
    collection_id char(5),
    release_date date not null,
    label_id char(5),
    market_id char(5),
    foreign key (collection_id) references collections (collection_id),
    foreign key (label_id) references recordlabels (label_id),
    foreign key (market_id) references markets (market_id)
);

create table collectioncompilations (
    collection_id char(5),
    compilation_id char(5),
    foreign key (collection_id) references collections (collection_id),
    foreign key (compilation_id) references compilations (compilation_id)
);