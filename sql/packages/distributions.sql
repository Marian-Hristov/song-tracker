create or replace package distribution_mgmt as
    -- TODO make function that receieves thins to search for collection, label and market
    type custArr is varray(3) of number;
    function getSearchedIds(searched_collection_id number, ref_label_name varchar2, ref_market_name varchar2)
        return custArr;
    function distributionExists(searched_collection_id number, release_date date, ref_label_name varchar2, ref_market_name varchar2)
        return number(1);
    procedure addDistribution(ref_collection_name varchar2, ref_label_name varchar2, ref_market_name varchar2);
    procedure removeDistribution(deleted_distribution_id varchar2);
    procedure updateDistribution(changed_distribution_id varchar2, ref_collection_name varchar2, ref_label_name varchar2, ref_market_name varchar2);
end distribution_mgmt;

create or replace package body distribution_mgmt as
    -- Getting the ids of the searched collection, label and market
    function getSearchedIds(searched_collection_id number, ref_label_name varchar2, ref_market_name varchar2)
    return custArr;
    is
        myArray custArr;
        foundCollection collections.collection_id%type;
        foundLabel recordlabels.label_id%type;
        foundMarket markets.market_id%type;
    begin
        if (searched_collection_id is null or ref_label_name is null or ref_market_name is null) then
            raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (searched_collection_id = 0) then
            raise_application_error(-20006, 'collection id cannot be 0, all ids start at 1');
        end if;
        -- Checking if the collection exists
        -- TODO all these select intos, should we just group them and then on if any on of them is null we throw a less specific exception?
        select collection_id into foundCollection from collections where collection_id = searched_collection_id;
        if foundCollection is null then
            raise_application_error(-20005, 'could not find referenced collection, could not make change to distributions');
        end if;
        select label_id into foundLabel from recordLabels where label_name = ref_label_name;
        if foundLabel is null then
            raise_application_error(-20005, 'could not find referenced label, could not make change to distributions');
        end if;
        select market_id into foundMarket from markets where market_name = ref_market_name;
        if foundMarket is null then
            raise_application_error(-20005, 'could not find referenced market, could not make change to distributions');
        end if;
        myArray(1) := foundCollection;
        myArray(2) := foundLabel;
        myArray(3) := foundMarket;
        return myArray;
        -- TODO do we need to use sql%notfound?
    end;
    -- Checking if a distribution exists 
    function distributionExists(searched_collection_id number, ref_release_date date, ref_label_name varchar2, ref_market_name varchar2)
    return number
    is 
        foundDistribution distributions.distribution_id%type;
        ids custArr;
    begin
        if (searched_collection_id is null or ref_release_date is null or ref_label_name is null or ref_market_name is null) then
                    raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (searched_contributor_id = 0)
                    raise_application_error(-20006, 'collection id cannot be 0, all ids start at 1');
        end if;
        ids := getSearchedIds(searched_collection_id, ref_label_name, ref_market_name);
        select distribution_id into foundDistribution from distributions
        when
        contribution_id = ids(1) and
        release_date = ref_release_date and
        label_id = ids(2) and
        market_id = ids(3);
        return 0;
        exception
            when no_data_found then
                return 1;
        -- TODO do we need to use sql%notfound?
    end;
    -- Adding a distribution
    procedure addDistribution(ref_collection_id number, release_date date, ref_label_name varchar2, ref_market_name varchar2);
    is
        foundCollection collections.collection_id%type;
        foundLabel recordLabels.label_id%type;
        foundMarket markets.market_id%type;
        ids custArr;
    begin
        if (ref_collection_id is null or  release_date is null or ref_label_name is null or ref_market_name is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if (distributionExists(ref_collection_id , release_date, ref_label_name, ref_market_name) = 1) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
        insert into distributions
        values (distribution_id_seq.nextval, foundCollection, release_date, foundLabel, foundMarket);
    end;
    -- Remove a distribution
    procedure removeDistribution(deleted_distribution_id varchar2)
    is
        foundDistribution distributions.distribution_id%type;
    begin
        if deleted_distribution_id is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        -- checking if the distribution exists
        select distribution_id into foundDistribution from distributions where distribution_id = deleted_distribution_id;
        -- TODO should we check sql%notfound? or send all the arguments like adding procedure
        if sql%notfound then
            raise_application_error(-20003, 'cannot delete distribution that does not exist');
        end if;
        delete from distributions where distribution_id = foundDistribution;
    end;
    -- Update a distribution
    procedure updateDistribution(changed_distribution_id varchar2, new_ref_collection_name varchar2, new_release_date date, new_ref_label_name varchar2, new_ref_market_name varchar2)
    is
        foundDistribution distributions.distribution_id%type;
        foundCollection collections.collection_id%type;
        foundLabel recordLabels.label_id%type;
        foundMarket markets.market_id%type;
    begin
        if (changed_distribution_id is null or new_ref_collection_name is null or new_release_date is null or new_ref_label_name is null or new_ref_market_name is null) then
                    raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        -- TODO solve conflict: we get collection name from procedure but here we need collection id, what do?
        if (distributionExists(changed_distribution_id , release_date, ref_label_name, ref_market_name) = 1) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
    end;
end distribution_mgmt;