create or replace package collection_mgmt as
    procedure createCollection (collection_name in collections.collection_name%type, collection_id out collections.collection_id%type);
    procedure addCompilationToCollection(collection_id in collections.collection_id%type, compilation_id in compilations.compilation_id%type);
end collection_mgmt;
/
create or replace package body collection_mgmt as

    procedure createCollection(
        collection_name in collections.collection_name%type,
        collection_id out collections.collection_id%type
    )
    as
    begin
        if (collection_name is null) then
            raise_application_error(-20001, 'the collection_name in the procedure createCollection is null');
        end if;
        insert into collections (collection_name) values (collection_name);
        collection_id := collection_id_seq.currval;
    end;

    procedure addCompilationToCollection(
        collection_id in collections.collection_id%type,
        compilation_id in compilations.compilation_id%type
    )
    as
    begin
        if (collection_id < 1 or compilation_id < 1) then
            raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1');
        end if;
        for compilation in (select * from collectionCompilations) loop
            
        end loop;
        -- TODO: check if that compilation is already in the collection
        insert into collectionCompilations values (collection_id, compilation_id);
    end;

    procedure removeCompilationFromCollection(
        collection_id in collections.collection_id%type,
        compilation_id in compilations.compilation_id%type
    )
    as
    begin
        if (collection_id < 1 or compilation_id < 1) then
            raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1');
        end if;
        delete from collectionCompilations where collection_id = collection_id and compilation_id = compilation_id;
        -- TODO: throw an error if no compilation was deleted
    end;
end collection_mgmt;