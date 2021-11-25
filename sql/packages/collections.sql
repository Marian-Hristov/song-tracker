create or replace procedure createCollection(
name in collections.name%type,
collection_id out collections.collection_id%type
)
as
begin
    if (name is null) then
        raise_application_error(-20001, 'the name in the procedure createCollection is null');
    end if;
    insert into collections (name) values (name);
    collection_id := collection_id_seq.currval;
end;

create or replace procedure addCompilationToCollection(
collection_id in collections.collection_id%type,
compilation_id in compilations.compilation_id%type
)
as
begin
    if (collection_id < 1 or compilation_id < 1) then
        raise_application_error(-20001, 'the collection_id or the compilation_id is bellow 1')
end;