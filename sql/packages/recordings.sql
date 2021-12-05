create or replace package recording_mgmt as
    procedure addRecording(new_recording_name recordings.recording_name%type, recording_duration recordings.duration%type);
    procedure removeRecording(removed_recording_id recordings.recording_id%type);
    procedure updateRecording(changed_recording_id recordings.recording_id%type, new_recording_name recordings.recording_name%type, new_recording_duration recordings.duration%type);
    procedure addContributorToRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char);
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
    procedure addContributorToRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char) is
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
    procedure removeContributorToRecording(ref_recording_id recordings.recording_id%type, ref_contributor_id number, ref_role_id number, category char) is
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