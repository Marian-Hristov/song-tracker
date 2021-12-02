create or replace package role_mgmt as
    function roleExists(category char, searched_role_name musicianRoles.role_name%type)
        return number;
    procedure addRole(category char, new_role_name musicianRoles.role_name%type);
    procedure removeRole(category char, deleted_role_name musicianRoles.role_name%type);
    procedure updateRole(category char, old_role_name musicianRoles.role_name%type, new_role_name musicianRoles.role_name%type);
end role_mgmt;
/
commit;
/
create or replace package body role_mgmt as
    -- Checking if a specified role exists
    function roleExists (category char, searched_role_name musicianRoles.role_name%type)
    return number
    is
        found musicianRoles.role_name%type;
    begin
        if (category is null or searched_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        if category = 'm' then
            select role_id into found from musicianRoles where role_name = searched_role_name;
        elsif category = 'p' then
            select role_id into found from productionRoles where role_name = searched_role_name;
        elsif category = 'c' then
            select role_id into found from compilationRoles where role_name = searched_role_name;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a role
    procedure addRole(category char, new_role_name musicianRoles.role_name%type) is
    begin
        if (category is null or new_role_name is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(roleExists(category, new_role_name) = 0) then
            raise_application_error(-20004, 'role already exists');
        end if;
        if category = 'm' then
            insert into musicianRoles (role_name)
            values (new_role_name);
        elsif category = 'p' then
            insert into productionRoles (role_name)
            values (new_role_name);
        elsif category = 'c' then
            insert into compilationRoles (role_name)
            values (new_role_name);
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Remove a role
    procedure removeRole(category char, deleted_role_name musicianRoles.role_name%type) 
    is
        found musicianRoles.role_id%type;
    begin
        if (category is null or deleted_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (roleExists(category, deleted_role_name) = 1) then
            raise_application_error(-20003, 'cannot delete role that does not exist');
        end if;
        if category = 'm' then
            -- Getting the id of the role based on the name
            select role_id into found from musicianRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from musicalContributions
            where role_id = found;
            -- Deleting role in role table
            delete from musicianRoles
            where role_id = found;
        elsif category = 'p' then
            -- Getting the id of the role based on the name
            select role_id into found from productionRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from productionContributions
            where role_id = found;
            -- Deleting role in role table
            delete from productionRoles
            where role_id = found;
        elsif category = 'c' then
            -- Getting the id of the role based on the name
            select role_id into found from compilationRoles where role_name = deleted_role_name;
            -- Deleting role from contribution bridging table
            delete from compilationContributions
            where role_id = found;
            -- Deleting role in role table
            delete from productionRoles
            where role_id = found;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
    -- Updating a role
    procedure updateRole(category char, old_role_name musicianRoles.role_name%type, new_role_name musicianRoles.role_name%type)
    is
        found musicianRoles.role_id%type;
    begin
        if (category is null or old_role_name is null or new_role_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (roleExists(category, old_role_name) = 1) then
            raise_application_error(-20003, 'cannot update role that does not exist');
        elsif (roleExists(category, new_role_name) = 0) then
            raise_application_error(-20003, 'cannot update role to role that already exists');
        end if;
        if category = 'm' then
            -- Getting the id of the role based on the name
            select role_id into found from musicianRoles where role_name = old_role_name;
            -- Updating role name
            update musicianRoles set role_name = new_role_name where role_id = found;
        elsif category = 'p' then
            -- Getting the id of the role based on the name
            select role_id into found from productionRoles where role_name = old_role_name;
            -- Updating role name
            update productionRoles set role_name = new_role_name where role_id = found;
        else
            raise_application_error(-20002, 'specified category does not exist');
        end if;
    end;
end role_mgmt;
/
commit;