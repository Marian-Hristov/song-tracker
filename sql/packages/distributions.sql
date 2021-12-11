create or replace package distribution_mgmt as
    function distributionExists(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type)
        return number;
    procedure addDistribution(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type);
    procedure removeDistribution(deleted_distribution_id distributions.distribution_id%type);
    procedure updateDistribution(changed_distribution_id distributions.distribution_id%type, new_ref_collection_id collections.collection_id%type, new_ref_release_date date, new_ref_label_id recordlabels.label_id%type, new_ref_market_id markets.market_id%type);
end distribution_mgmt;
/
commit;
/
create or replace package body distribution_mgmt as
    -- Checking if a distribution exists 
    function distributionExists(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type)
    return number
    is 
        foundDistribution distributions.distribution_id%type;
    begin
        if (ref_collection_id is null or ref_release_date is null or ref_label_id is null or ref_market_id is null) then
                    raise_application_error(-20001, 'one or more arguments are null or empty');
        elsif (ref_collection_id < 1 or ref_label_id < 1 or ref_market_id < 1) then
                    raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        select distribution_id into foundDistribution from distributions
        where
        collection_id = ref_collection_id and
        release_date = ref_release_date and
        label_id = ref_label_id and
        market_id = ref_market_id;
        return 0;
        exception
            when no_data_found then
                return 1;
    end;
    -- Adding a distribution
    procedure addDistribution(ref_collection_id collections.collection_id%type, ref_release_date distributions.release_date%type, ref_label_id recordlabels.label_id%type, ref_market_id markets.market_id%type) is
    begin
        if (ref_collection_id is null or ref_release_date is null or ref_label_id is null or ref_market_id is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        elsif (ref_collection_id < 1 or ref_label_id < 1 or ref_market_id < 1) then
            raise_application_error(-20001, 'one or more provided ids are not in the allowed range');
        end if;
        if (distributionExists(ref_collection_id , ref_release_date, ref_label_id, ref_market_id) = 0) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
        insert into distributions (collection_id, release_date, label_id, market_id)
        values (ref_collection_id, ref_release_date, ref_label_id, ref_market_id);
    end;
    -- Remove a distribution
    procedure removeDistribution(deleted_distribution_id distributions.distribution_id%type)
    is
        foundDistribution distributions.distribution_id%type;
    begin
        if deleted_distribution_id is null then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        -- checking if the distribution exists
        select distribution_id into foundDistribution from distributions where distribution_id = deleted_distribution_id;
        if sql%notfound then
            raise_application_error(-20003, 'cannot delete distribution that does not exist');
        end if;
        delete from distributions where distribution_id = foundDistribution;
    end;
    -- Update a distribution
    procedure updateDistribution(changed_distribution_id distributions.distribution_id%type, new_ref_collection_id collections.collection_id%type, new_ref_release_date date, new_ref_label_id recordlabels.label_id%type, new_ref_market_id markets.market_id%type) is
    begin
        if (changed_distribution_id is null or new_ref_collection_id is null or new_ref_release_date is null or new_ref_label_id is null or new_ref_market_id is null) then
            raise_application_error(-20001, 'one or many arguments are null or empty');
        end if;
        if(new_ref_collection_id < 1 or new_ref_label_id < 1 or new_ref_market_id < 1) then
            raise_application_error(-20001, 'one or many arguments are in invalid range'); 
        end if; 
        if (distributionExists(new_ref_collection_id , new_ref_release_date, new_ref_label_id, new_ref_market_id) = 0) then
            raise_application_error(-20004, 'distribution already exists');
        end if;
        update distributions
        set 
        collection_id = new_ref_collection_id,
        release_date = new_ref_release_date,
        label_id = new_ref_label_id,
        market_id = new_ref_market_id
        where distribution_id = changed_distribution_id;
    end;
end distribution_mgmt;
/
commit;