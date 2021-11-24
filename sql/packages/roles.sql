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
            delete from musicianRoles
            where role_id = deleted_role_id;
        elsif category = 'production' then
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