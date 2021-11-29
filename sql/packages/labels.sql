create or replace package label_mgmt as
    function labelExists(searched_label_name recordlabels.label_name%type)
        return number;
    procedure addLabel(new_label_name recordlabels.label_name%type);
    procedure removeLabel(deleted_label_id recordlabels.label_id%type);
    procedure updateLabel(old_label_name recordlabels.label_name%type, new_label_name recordlabels.label_name%type);
end label_mgmt;
/
commit;
/
create or replace package body label_mgmt as
    -- Checking if a label exists
    function labelExists(searched_label_name recordlabels.label_name%type)
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
    procedure addLabel(new_label_name recordlabels.label_name%type) is
    begin
        if new_label_name is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        insert into recordLabels (label_name)
        values (new_label_name);
    end;
    -- Remove a label
    procedure removeLabel(deleted_label_id recordlabels.label_id%type) is
    begin
        if deleted_label_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif deleted_label_id < 1 then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        -- Deleting the label from distributions table
        delete from distributions
        where label_id = deleted_label_id;
        -- Deleting label in recordLabels table
        delete from recordLabels
        where label_id = deleted_label_id;
    end;
    -- Updating a role
    procedure updateLabel(old_label_name recordlabels.label_name%type, new_label_name recordlabels.label_name%type)
    is
        found recordLabels.label_id%type;
    begin
        if(old_label_name is null or new_label_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (labelExists(old_label_name) = 0) then
            raise_application_error(-20003, 'cannot update label that does not exist');
        elsif (labelExists(new_label_name) = 0) then
            raise_application_error(-20003, 'cannot update label to label that already exists');
        end if;
        -- Getting the id of the label based on the name
        select label_id into found from recordLabels where label_id = old_label_name;
        -- Updating label name
        update recordLabels set label_name = new_label_name where label_id = found;
    end; 
end label_mgmt;
/
commit;