insert into markets (market_name) values ('Asia');
insert into markets (market_name) values ('Africa');
insert into markets (market_name) values ('Europe');
insert into markets (market_name) values ('North-America');
insert into markets (market_name) values ('South-America'); -- 5
insert into markets (market_name) values ('Worldwide');
commit;
insert into musicianRoles values (musician_id_seq.nextval, 'accordionist');
insert into musicianRoles values (musician_id_seq.nextval, 'piper');
insert into musicianRoles values (musician_id_seq.nextval, 'banjoist');
insert into musicianRoles values (musician_id_seq.nextval, 'bongosero');
insert into musicianRoles values (musician_id_seq.nextval, 'bassist'); -- 5
insert into musicianRoles values (musician_id_seq.nextval, 'bassoonist');
insert into musicianRoles values (musician_id_seq.nextval, 'clarinetist');
insert into musicianRoles values (musician_id_seq.nextval, 'cellist');
insert into musicianRoles values (musician_id_seq.nextval, 'drummer');
insert into musicianRoles values (musician_id_seq.nextval, 'euphoniumist'); -- 10
insert into musicianRoles values (musician_id_seq.nextval, 'flutist');
insert into musicianRoles values (musician_id_seq.nextval, 'guitarist');
insert into musicianRoles values (musician_id_seq.nextval, 'harpist');
insert into musicianRoles values (musician_id_seq.nextval, 'harpsichordist');
insert into musicianRoles values (musician_id_seq.nextval, 'hornist'); -- 15
insert into musicianRoles values (musician_id_seq.nextval, 'keyboardist');
insert into musicianRoles values (musician_id_seq.nextval, 'lutenist');
insert into musicianRoles values (musician_id_seq.nextval, 'mandolinist');
insert into musicianRoles values (musician_id_seq.nextval, 'marimbist');
insert into musicianRoles values (musician_id_seq.nextval, 'oboist'); -- 20
insert into musicianRoles values (musician_id_seq.nextval, 'organist');
insert into musicianRoles values (musician_id_seq.nextval, 'percussionist');
insert into musicianRoles values (musician_id_seq.nextval, 'pianist');
insert into musicianRoles values (musician_id_seq.nextval, 'recorder player');
insert into musicianRoles values (musician_id_seq.nextval, 'saxophonist'); -- 25
insert into musicianRoles values (musician_id_seq.nextval, 'tocaores');
insert into musicianRoles values (musician_id_seq.nextval, 'trumpeter');
insert into musicianRoles values (musician_id_seq.nextval, 'tubaist');
insert into musicianRoles values (musician_id_seq.nextval, 'ukulelist');
insert into musicianRoles values (musician_id_seq.nextval, 'violist'); -- 30
insert into musicianRoles values (musician_id_seq.nextval, 'violinist');
insert into musicianRoles values (musician_id_seq.nextval, 'xylophonist');
commit;
insert into productionRoles values (production_id_seq.nextval, 'composer');
insert into productionRoles values (production_id_seq.nextval, 'remixer');
insert into productionRoles values (production_id_seq.nextval, 'recording engineer');
insert into productionRoles values (production_id_seq.nextval, 'producer');
insert into productionRoles values (production_id_seq.nextval, 'co-producer'); -- 5
insert into productionRoles values (production_id_seq.nextval, 'accompanist');
insert into productionRoles values (production_id_seq.nextval, 'arranger');
insert into productionRoles values (production_id_seq.nextval, 'audio engineer');
insert into productionRoles values (production_id_seq.nextval, 'vocalist');
insert into productionRoles values (production_id_seq.nextval, 'backing singer'); -- 10
insert into productionRoles values (production_id_seq.nextval, 'conductor');
insert into productionRoles values (production_id_seq.nextval, 'mixer');
insert into productionRoles values (production_id_seq.nextval, 'mastering engineer');
insert into productionRoles values (production_id_seq.nextval, 'sound designer');
insert into productionRoles values (production_id_seq.nextval, 'performer'); -- 15
insert into productionRoles values (production_id_seq.nextval, 'writter');
insert into productionRoles values (production_id_seq.nextval, 'lyricist');
insert into productionRoles values (production_id_seq.nextval, 'assistant engineer');
insert into productionRoles values (production_id_seq.nextval, 'mixing assistant');
commit;