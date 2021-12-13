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

drop package collection_mgmt;
drop package compilation_mgmt;
drop package contributor_mgmt;
drop package distribution_mgmt;
drop package label_mgmt;
drop package market_mgmt;
drop package recording_mgmt;
drop package role_mgmt;
