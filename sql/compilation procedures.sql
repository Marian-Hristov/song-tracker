create or replace procedure createCompilatinon(

name in compilations.name%type,
main_track_offset in segment.main_track_offset%type,
duration_in_main_track in segment.duration_in_main_track%type,
component_track_offset in segment.component_track_offset%type,
duration_of_component in segment.duration_of_component%type,
sample_id in recordings.recording_id%type,
sample_type in char
)
as
segment_id segment.segment_id%type;
compilation_id compilations.compilation_id%type;
max_duration number;
segment_start segment.main_track_offset%type;
segment_duration segment.duration_in_main_track%type;

begin
    if(name is null) then
        raise_application_error(-20001, 'the name is empty or null');
    end if;
    if(main_track_offset < 0 or duration_in_main_track < 0 or component_track_offset < 0 or duration_of_component <= 0) then
        raise_application_error(-20001, 'one or more of the durations is less than 0');
    end if;
    
    insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component) values (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component);
    select segment_id_seq.currval into segment_id from dual;
    
    insert into compilations (name, creation_time, duration) values (name, localtimestamp(0), 1);
    select compilation_id_seq.currval into compilation_id from dual;
    if(sample_type = 'c') then
        insert into compilationSamples values (compilation_id, sample_id, segment_id);
    
    elsif (sample_type = 'r') then
        insert into recordingSamples values (compilation_id, sample_id, segment_id);
        
    else
        raise_application_error(-20001, 'the sample type must be either "c" or "r"');
    end if;
    
    max_duration :=0;
    for sample in (select * from compilationSamples where compilation_id = compilation_id) loop
        select main_track_offset, duration_in_main_track into segment_start, segment_duration
            from segment where segment_id = sample.segment_id;
        if (segment_start + segment_duration > max_duration) then
            max_duration := segment_start + segment_duration;
        end if;
    end loop;
    
    for sample in (select * from recordingSamples where compilation_id = compilation_id) loop
        select main_track_offset, duration_in_main_track into segment_start, segment_duration
            from segment where segment_id = sample.segment_id;
        if (segment_start + segment_duration > max_duration) then
            max_duration := segment_start + segment_duration;
        end if;
    end loop;
    
    update compilations set duration = max_duration where compilation_id = compilation_id;
end;

