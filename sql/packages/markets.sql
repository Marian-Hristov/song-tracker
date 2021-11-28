create or replace package market_mgmt as
    function marketExists(searched_market_name markets.market_name%type)
        return number;
    procedure addMarket(new_market markets.market_name%type);
    procedure removeMarket(removed_market_id markets.market_id%type);
    procedure updateMarket(old_market_name markets.market_name%type, new_market_name markets.market_name%type);
end market_mgmt;
/
commit
/
create or replace package body market_mgmt as
--    -- Check if a market exists
    function marketExists(searched_market_name markets.market_name%type)
    return number
    is
        found markets.market_id%type;
    begin
        if searched_market_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select market_id into found from markets where market_name = searched_market_name;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Add a new market
    procedure addMarket(new_market markets.market_name%type) is
    begin
        if new_market is null then
            raise_application_error(-20002, 'one or more arguments are null or empty');
        end if;
        insert into markets (market_name)
        values (new_market);
    end; 
    -- Remove a market
    procedure removeMarket(removed_market_id markets.market_id%type)
    is
        found markets.market_id%type;
    begin
        if removed_market_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif removed_market_id < 1 then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        -- Deleting market in markets table
        delete from markets
        where market_id = found;
    end;
    -- Update market
    procedure updateMarket(old_market_name markets.market_name%type, new_market_name markets.market_name%type)
    is
        found markets.market_id%type;
    begin
        if(old_market_name is null or new_market_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (marketExists(old_market_name) = 1) then
            raise_application_error(-20003, 'cannot update market that does not exist');
        elsif (marketExists(new_market_name) = 0) then
            raise_application_error(-20003, 'cannot update market to market that already exists');
        end if;
        -- Getting the id of the market based on the name
        select market_id into found from markets where market_name = old_market_name;
        -- Updating market name
        update markets set market_name = new_market_name where market_id = found;
    end;
end market_mgmt;
/
commit;