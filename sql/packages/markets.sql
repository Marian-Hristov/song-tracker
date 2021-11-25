create or replace package market_mgmt as
    function marketExists(searched_market_id char)
    return number(1);
    procedure addMarket(new_market varchar2);
    procedure removeMarket(removed_market_id varchar2);
end market_mgmt;

create or replace package body market_mgmt as
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
    procedure addMarket(new_market varchar2) is
    begin
        if new_market is null then
            raise_application_error(-20002, 'one or more arguments are null');
        else
            insert into markets
            values (market_id_seq.nextval, new_market);
        end if;
        -- TODO test that you cannot add a recording if it already exists, and is this the right way to do it?
        exception
            when dup_val_on_index then
                raise_application_error(-20003, 'cannot add already existing market');
    end;
    -- TODO do removeMarket
end market_mgmt;