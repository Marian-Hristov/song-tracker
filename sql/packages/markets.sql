create or replace package market_mgmt as
    function marketExists(searched_market_id char)
    return number(1);
    procedure addMarket(new_market varchar2);
    procedure removeMarket(removed_market_id varchar2);
    procedure updateMarket(old_market_name varchar2, new_market_name varchar2);
end market_mgmt;

create or replace package body market_mgmt as
    -- Check if a market exists
    function marketExists(searched_market_id char) 
    is
        found markets.market_id%type;
    begin
        if searched_market_id is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        end if;
        select into found from marktes where market_id = searched_market_id;
        return 0;
        exception
            when dup_val_on_index then
                return 1;
    end;
    -- Add a new market
    procedure addMarket(new_market varchar2) is
    begin
        if new_market is null then
            raise_application_error(-20002, 'one or more arguments are null');
        end if;
        if (marketExists(new_market) = 1) then
                raise_application_error(-20003, 'cannot add already existing market');
        end if;
        insert into markets
        values (market_id_seq.nextval, new_market);
    end; 
    -- Remove a market
    procedure removeMarket(deleted_market_name varchar2)
    is
        found markets.market_id%type;
    begin
        if deleted_market_name is null then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (marketExists(deleted_market_name) = 1) then
            raise_application_error(-20003, 'cannot delete market that does not exist');
        end if;
        -- Getting the id of the market base on the name
        select market_id into found from markets where market_id = deleted_market_name;
        -- Deleting market in markets table
        delete from markets
        where market_id = found;
    end;
    -- Update market
    procedure updateMarket(old_market_name varchar2, new_market_name varchar2)
    is
        found markets.market_id%type;
    begin
        if(old_market_name is null or new_market_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (marketExists(old_market_name) = 1) then
            raise_application_error(-20003, 'cannot update market that does not exist');
        elsif (marketExists(new_market_name) = 1) then
            raise_application_error(-20003, 'cannot update market to market that already exists');
        end if;
        -- Getting the id of the market based on the name
        select market_id into found from markets where market_id = old_market_name;
        -- Updating market name
        update markets set market_name = new_market_name where market_id = found;
    end;
end market_mgmt;