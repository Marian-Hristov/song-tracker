create or replace contributor_mgmt as
    function contributorExists(searched_contributor_name varchar2)
        return number(1);
    procedure addContributor(new_contributor_name varchar2);
    procedure removeContributor(deleted_contributor_name varchar2);
    procedure updateContributor(old_contributor_name varchar2, new_contributor_name varchar2);
end contributor_mgmt;

create or replace package body contributor_mgmt as
    -- Checking if a contributor exists
    function contributorExists(searched_contributor_name varchar2)
    return number
    is
        found contributors.contributor_id%type;
    begin
        if searched_contributor_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select contributor_id into found from contributors where contributor_name = searched_contributor_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add contributor
    procedure addContributor(new_contributor_name varchar2) is
    begin
        if new_contributor_name is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(contributorExists(new_contributor_name) = 1) then
            raise_application_error(-20004, 'contributor already exists');
        end if;
        insert into contributor
        values (contributor_id_seq.nextval, new_contributor_name);
    end;
    -- Remove a contributor
    procedure removeContributor(deleted_contributor_name varchar2)
    is
        found contributors.contributor_id%type;
    begin
        if deleted_contributor_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (contributorExists(deleted_contributor_name) = 1) then
            raise_application_error(-20003, 'cannot delete contributor that does not exist');
        end if;
        -- Getting the id of the contributor base on the name
        select contributor_id into found from contributors where contributor_name = deleted_contributor_name;
        -- Deleting the contributor from contribution tables
        delete from musicalContributions
        where contributor_id = found;
        delete from productionContributions
        where contributor_id = found;
        -- Deleting contributor in contributors table
        delete from contributors
        where contributor_id = found;
    end;
    -- Updating a contributor
    procedure updateContributor(old_contributor_name varchar2, new_contributor_name varchar2)
    is
        found contributors.contributor_id%type;
    begin
        if(old_contributor_name is null or new_contributor_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (contributorExists(old_contributor_name) = 1) then
            raise_application_error(-20003, 'cannot update contributor that does not exist');
        elsif (contributorExists(new_contributor_name) = 1) then
            raise_application_error(-20003, 'cannot update contributor to contributor that already exists');
        end if;
        -- Getting the id of the contributor based on the name
        select contributor_id into found from contributors where contributor_name = old_contributor_name;
        -- Updating contributor name
        update contributors set contributor_name = new_contributor_name where contributor_id = found;
    end;
end contributor_mgmt;