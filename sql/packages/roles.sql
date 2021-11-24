create or replace package role_mgmt as
    procedure addRole(category varchar2, new_role_name varchar2);
    procedure removeRole(category varchar2, role_id char);
    procedure updateRole(category varchar2, role_id char, role_name varchar2);
    function roleExists(category varchar2, role_id char)
        return number;
    e_null_arg exception;
    pragma exception_init(e_null_arg, -20001);
    e_category_does_not_exist exception;
    pragma exception_init(e_category_does_not_exist, -20002);
end role_mgmt;

create or replace package body role_mgmt as
    -- Add a role
    procedure addRole(category varchar2, new_role_name varchar2) is
    begin
        if (category is null or new_role_name is null) then
            raise e_null_arg;
        end if;
        if category = 'musician' then
            insert into musicianRoles
            values (musician_id_seq.nextval, new_role_name);
        elsif category = 'production' then
            insert into productionRoles
            values (production_id_seq.nextval, new_role_name);
        else
            raise_application_error(-20001, 'specified category does not exist');
        end if;
    end;
    -- Remove a role
    procedure removeRole(category varchar2, role_id char) is
    begin
        if (category is null or role_id is null) then
            raise_application_error(-20002, 'one or more arguments are null');
        end if;
        if category = 'musician' then
            insert into musicianRoles
            values (musician_id_seq.nextval, new_role_name);
        elsif category = 'production' then
            insert into productionRoles
            values (production_id_seq.nextval, new_role_name);
        else
            raise_application_error(-20001, 'specified category does not exist');
        end if;
    end;
end role_mgmt;