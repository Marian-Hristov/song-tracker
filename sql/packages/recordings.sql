create or replace package recording_mgmt as
    procedure addRecording(name varchar2, creation_time timestamp, duration number);
    procedure removeRecording(recording_id char);
    procedure updateRecording(recording_id char);
    function recordingExists(recording_id char)
        return number;
end recording_mgmt;

create or replace package body recording_mgmt as
    procedure addRecording(name varchar2, creation_time timestamp, duration number) is
    begin
        if (name is null or creation_time is null or duration is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        else
            insert into recordings
            values (recording_id_seq.nextval, name, creation_time, duration);
        end if;
    end;
end recording_mgmt;