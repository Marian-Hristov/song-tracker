create or replace package recording_mgmt as
    function recordingExists(searched_recording_id char)
        return number(1);
    procedure addRecording(new_recording varchar2, creation_time timestamp, duration number);
    function recordingExistsInProduction(searched_recording_id char)
        return number(1);
    function recordingExistsInMusical(searched_recording_id char)
        return number(1);
    procedure removeRecording(removed_recording_id char);
end recording_mgmt;

create or replace package body recording_mgmt as
    -- Check if a recording exists
    function recordingExists(searched_recording_id char)
    return number(1)
    is
        found recordings.recording_id%type;
    begin
        if(searched_recording_id is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select recording_id into found from recordings where recording_id = searched_recording_id;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a recording
    procedure addRecording(new_recording varchar2, creation_time timestamp, duration number) is
    begin
        if (new_recording is null or creation_time is null or duration is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        else
            insert into recordings
            values (recording_id_seq.nextval, new_recording, creation_time, duration);
        end if;
        -- TODO test that you cannot add a recording if it already exists, and is this the right way to do it?
        exception
            when dup_val_on_index then
                raise_application_error(-20003, 'cannot add already existing recording');
    end;
    -- Check if a recording exists in the productionContributions table
    function recordingExistsInProduction(searched_recording_id)
    return number(1)
    is
        foundP productionContributions.recording_id%type;
    begin
        if searched_recording_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        else    
            select into foundP
            from productionContributions
            where recording_id = searched_recording_id;
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Check if a recording exists in the musicalContributions table
    function recordingExistsInMusical(searched_recording_id)
    return number(1)
    is
        foundM musicalContributions.recording_id%type;
    begin
        if searched_recording_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        else    
            select into foundM
            from musicalContributions
            where recording_id = searched_recording_id;
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- TODO Do procedure to remove a recording
end recording_mgmt;