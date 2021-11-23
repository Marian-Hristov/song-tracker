drop table productionroles;
drop table contributor;
drop table recordings;


create table contributor(
    contributor_id char(5) primary key,
    name varchar2(100)
);

create table recordings(
    recording_id char(5) primary key,
    creation_time timestamp(0)
);

create table productionroles (
    recording_id char(5),
    contributor_id char(5),
    role_name varchar2(100),
    foreign key (recording_id) references recordings (recording_id)
);


