create or replace package label_mgmt as
    function labelExists(searched_label_name varchar2)
        return number(1);
    procedure addLabel(new_label_name varchar2);
    procedure removeLabel(deleted_label_name varchar2);
    procedure updateLabel(old_label_name varchar2, new_label_name varchar2)
end label_mgmt;

create or replace package body label_mgmt as
    -- Checking if a label exists
    function labelExists(searched_label_name varchar2)
    return number
    is
        found recordLabels.label_id%type;
    begin
        if searched_label_name is null then 
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select label_id into found from recordLabels where label_name = searched_label_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a label
    procedure addLabel(new_label_name varchar2) is
    begin
        if new_label_name is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(labelExists(new_label_name) = 1) then
            raise_application_error(-20004, 'label already exists');
        end if;
        insert into recordLabels
        values (label_id_seq.nextval, new_label_name);
    end;
    -- Remove a label
    procedure removeLabel(deleted_label_name varchar2)
    is
        found recordLabels.label_id%type;
    begin
        if deleted_label_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (labelExists(deleted_label_name) = 1) then
            raise_application_error(-20003, 'cannot delete label that does not exist');
        end if;
        -- Getting the id of the label base on the name
        select label_id into found from recordLabels where label_id = deleted_label_name;
        -- Deleting the label from distributions table
        delete from distributions
        where label_id = found;
        -- Deleting label in recordLabels table
        delete from recordLabels
        where label_id = found;
        -- TODO does the situation under apply?
        -- TODO we need to catch the error when we try to delete a value that doesn't exist specific contributions
        exception 
            when no_data_found then
                -- we just want to catch the error, so the program continues with deletions
                ;
    end;
    -- Updating a role
    procedure updateRole(old_label_name varchar2, new_label_name varchar2)
    is
        found recordLabels.label_id%type;
    begin
        if(old_label_name is null or new_label_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (labelExists(old_label_name) = 1) then
            raise_application_error(-20003, 'cannot update label that does not exist');
        elsif (labelExists(new_label_name) = 1) then
            raise_application_error(-20003, 'cannot update label to label that already exists');
        end if;
        -- Getting the id of the label based on the name
        select label_id into found from recordLabels where label_id = old_label_name;
        -- Updating label name
        update recordLabels set label_name = new_label_name where label_id = found;
    end; 
end label_mgmt;