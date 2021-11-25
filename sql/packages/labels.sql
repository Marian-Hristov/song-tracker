create or replace package label_mgmt as
    function labelExists(searched_label_id char)
        return number(1);
    procedure addLabel(new_label varchar2);
    procedure removeLabel(removed_label varchar2);
end label_mgmt;

create or replace package body label_mgmt as
    -- Check if a user exists 
    function labelExists(searched_label_id char)
    return number(1)
    is
        found recordLabels.label_id%type;
    begin
        if(searched_recording_id is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select label_id into found from recordLabels where label_id = searched_label_id;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Adding a label
    procedure addLabel(new_label varchar2) is
    begin
        if new_label is null then
            raise_application_error(-20002, 'one or more arguments are null');
        else
            insert into recordLabels
            values (label_id_seq.nextval, new_label);
        end if;
        -- TODO test that you cannot add a recording if it already exists, and is this the right way to do it?
        exception
            when dup_val_on_index then
                raise_application_error(-20003, 'cannot add already existing label');
    end;
    -- TODO make procedure to remove a label, do we remove the distributions also?
end label_mgmt;

-- TODO PLEASE READ THIS BEFORE CONTINUING
-- when deleting rows that are required in other tables, why not have a null placeholder
-- value that tells the user that some information has been removed? or even null is perfectly fine
-- Is there a way to change a FK to a normal value only on one row?

-- ANOTHER PROBLEM:
-- When we check if something exists, what should we check for the name or the id or both together or separetely?
-- Is dup_val_on_index going to solve our problem more efficiently?