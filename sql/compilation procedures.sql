create or replace procedure createCompilatino(

name in compilations.name%type,
main_track_offset in segment.main_track_offset%type,
duration_in_main_track in segment.duration_in_main_track%type,
component_track_offset in segment.component_track_offset%type,
duration_of_component in segment.duration_of_component%type
)
as
segment_id segment.segment_id%type;
begin
    if(name is null) then
        raise_application_error(-20001, 'the name is empty or null');
    end if;
    if(main_track_offset < 0 or duration_in_main_track < 0 or component_track_offset < 0 or duration_of_component <= 0) then
        raise_application_error(-20001, 'one or more of the durations is less than 0');
    end if;
    insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component) values (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component);
    
end;


select segment_id_seq.nextval
    from dual;
    
    select sequence_name, 
   to_char(min_value) min_value,
   to_char(max_value) max_value, 
   increment_by,
   cycle_flag, 
   order_flag, 
   cache_size, 
   to_char(Last_number) last_number
from user_sequences
where sequence_name='SEGMENT_ID_SEQ'