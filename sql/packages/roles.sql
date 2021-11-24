create or replace package role_mgmt as
    procedure addRole(category varchar2, new_role_name varchar2);
    procedure removeRole(category varchar2, deleted_role_id char);
    function roleExists(category varchar2, searched_role_id char)
        return number(1);
end role_mgmt;

create or replace package body role_mgmt as
    -- Add a role
    procedure addRole(category varchar2, new_role_name varchar2) is
    begin
        if (category is null or new_role_name is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if category = 'musician' then
            insert into musicianRoles
            values (musician_id_seq.nextval, new_role_name);
        elsif category = 'production' then
            insert into productionRoles
            values (production_id_seq.nextval, new_role_name);
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Remove a role
    procedure removeRole(category varchar2, deleted_role_id char) is
    begin
        if (category is null or deleted_role_id is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        if category = 'musician' then
            -- Major problem, when we delete a contribution from a recording
            -- that only has one contribution, then we need to delete that 
            -- recording and if the recording is part of a sample that is 
            -- the only sample in a compilation, then we need to delete that
            -- compilation, and if that compilation is the only part of a 
            -- collection, then we need to delete that collection, which 
            -- means that we need to delete the associated distribution 

            -- this is the chain
            -- distribution -> collections -> collectioncompilations
            -- -> compilations -> samples -> recording -> contributor

            -- we would also need to repeat each of the steps above for
            -- each element when we're going up the chain

            -- solution: we allow each of the component to be stand alone
            -- until the user wants to link them together, meaning that 
            -- we can have a role that is not associated to any contributor
            -- or a contributor not associated to any recording, and even
            -- recordings that don't have associated contributors, we can 
            -- continue this way too with compilations, samples, segments,
            -- collections and distributions

            -- this makes sense because not all recordings are used in a 
            -- compilation, some may not be used but still kept in the DB
            delete from musicalcontributions
            where contributor_id = deleted_role_id;
            delete from musicianRoles
            where role_id = deleted_role_id;
        elsif category = 'production' then
            delete from productioncontributions
            where contributor_id = deleted_role_id;
            delete from productionRoles
            where role_id = deleted_role_id;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Checking if a specified user exists
    function roleExists(category varchar2, searched_role_id char)
    return number(1)
    is
        found musicianRoles.role_name%type;
    begin
        if (category is null or searched_role_id is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        if category = 'musician' then
            select into found from musicianRoles where role_id = searched_role_id;
        elsif category = 'production' then
            select into found from productionRoles where role_id = searched_role_id;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
end role_mgmt;