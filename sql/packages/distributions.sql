create or replace package distribution_mgmt as
    function distributionExists(searched_distribution_name varchar2)
        return number(1);
    procedure addDistribution(new_distribution_name varchar2);
    procedure removeDistribution(deleted_distribution_name varchar2);
    procedure updateDistribution(old_distribution_name varchar2, new_distribution_name varchar2);
end distribution_mgmt;

create or replace package body distribution_mgmt as
    -- Checking if a distribution exists
    -- TODO talk with mauricio about problem
    -- Should we check for a collection name when searching for a distribution  
    function distributionExists(searched_distribution_name varchar2)
    is
        found distrbutions.distribution_id%type;
    begin
        if searched_distribution_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
    end;
end distribution_mgmt;