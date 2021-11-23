
-- recordings / contributions
drop table musicalContributions;
drop table productionContributions;
drop table productionRoles;
drop table recordings;
drop table contributors;
drop table musicianRoles;

create table productionRoles(
    role_id char(5) primary key,
    role_name varchar2(100) not null
);

create table recordings (
    recording_id char(5) primary key,
    name varchar2(100) not null,
    create_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_reconrding check(duration > 0)
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
drop table compilations;

create table compilations (
    compilation_id char(5) primary key,
    name varchar2(100) not null,
    creation_time timestamp(0) not null,
    duration number(5,1) not null,
    constraint check_positive_duration_compilation check(duration > 0)
);
