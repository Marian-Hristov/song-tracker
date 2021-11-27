create or replace package recording_mgmt as
    function recordingExists(searched_recording_name varchar2, searched_creation_time timestamp, recording_duration number)
        return number;
    procedure addRecording(new_recording varchar2, creation_time timestamp, recording_duration number);
    -- function recordingExistsInProduction(searched_recording_id number)
    --     return number;
    -- function recordingExistsInMusical(searched_recording_id number)
    --     return number;
    procedure removeRecording(removed_recording_id number);
    procedure updateRecording(changed_recording_id number, new_recording_name varchar2, new_creation_time timestamp, new_recording_duration number);
end recording_mgmt;
/
commit;
/
create or replace package body recording_mgmt as
    -- Check if a recording exists
    function recordingExists(searched_recording_name varchar2, searched_creation_time timestamp, recording_duration number)
    return number
    is
        found recordings.recording_id%type;
    begin
        if(searched_recording_name is null or searched_creation_time is null or duration is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select recording_id into found from recordings
        where
        recording_name = searched_recording_id and
        creation_time = searched_creation_time and
        duration = recording_duration;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a recording
    procedure addRecording(new_recording_name varchar2, creation_time timestamp, recording_duration number) is
    begin
        if (new_recording is null or creation_time is null or recording_duration is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        elsif (recordingExists(new_recording_name, creation_time, recording_duration) = 0) then
            raise_application_error(-20003, 'cannot add already existing recording');
        end if;
        insert into recordings
        values (recording_id_seq.nextval, new_recording_name, creation_time, recording_duration);
    end;
    -- -- Check if a recording exists in the productionContributions table
    -- function recordingExistsInProduction(searched_recording_id)
    -- return number
    -- is
    --     foundP productionContributions.recording_id%type;
    -- begin
    --     if searched_recording_id is null then
    --         raise_application_error(-20001, 'one or more arguments are null or empty');
    --     else    
    --         select into foundP
    --         from productionContributions
    --         where recording_id = searched_recording_id;
    --     end if;
    --     return 0;
    --     exception
    --         when no_data_found then
    --             return 1;
    -- end;
    -- -- Check if a recording exists in the musicalContributions table
    -- function recordingExistsInMusical(searched_recording_id)
    -- return number
    -- is
    --     foundM musicalContributions.recording_id%type;
    -- begin
    --     if searched_recording_id is null then
    --         raise_application_error(-20001, 'one or more arguments are null or empty');
    --     else    
    --         select into foundM
    --         from musicalContributions
    --         where recording_id = searched_recording_id;
    --     end if;
    --     return 0;
    --     exception
    --         when no_data_found then
    --             return 1;
    -- end;
    -- Remove a recording
    procedure removeRecording(removed_recording_id number) is
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
    procedure updateRecording(changed_recording_id number, new_recording_name varchar2, new_creation_time timestamp, new_recording_duration number) is
    begin
        if (changed_recording_id is null or new_recording_name is null or new_creation_time is null or new_recording_duration is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        if (recordingExists(new_recording_name, new_creation_time, new_recording_duration) = 0) then
            raise_application_error(-20004, 'recording already exists');
        end if;
        update recordings 
        set
        recording_name = new_recording_name,
        creation_time = new_creation_time,
        duration = new_recording_duration
        where recording_id = changed_recording_id;
    end;
end recording_mgmt;
/
commit;