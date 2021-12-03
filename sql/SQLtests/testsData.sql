execute recording_mgmt.addRecording('my recording1', 100);
execute contributor_mgmt.addContributor('marian');
execute role_mgmt.addRole('p', 'sound engineer');
execute recording_mgmt.addContributorToRecording(1,1,1, 'p');
commit;