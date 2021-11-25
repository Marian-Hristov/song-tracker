create or replace package recording_mgmt as
    procedure addRecording(name varchar2, creation_time timestamp, duration number);
    function recordingExistsInProduction(searched_recording_id char)
        return number(1);
    function recordingExistsInMusical(searched_recording_id char)
        return number(1);
    procedure removeRecording(removed_recording_id char);
    function recordingExists(searched_recording_id char)
        return number(1);
end recording_mgmt;

create or replace package body recording_mgmt as
    -- Add a recording
    procedure addRecording(name varchar2, creation_time timestamp, duration number) is
    begin
        if (name is null or creation_time is null or duration is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        else
            insert into recordings
            values (recording_id_seq.nextval, name, creation_time, duration);
        end if;
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
            from musicalContr ibutions
            where recording_id = searched_recording_id;
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
end recording_mgmt;