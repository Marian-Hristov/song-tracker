create or replace package compilation_mgmt as
    procedure createCompilation(compilation_name in compilations.compilation_name%type, compilation_id out compilations.compilation_id%type);
    procedure addSampleToCompilation(
        compilation_id in compilations.compilation_id%type,
        main_track_offset in segment.main_track_offset%type,
        duration_in_main_track in segment.duration_in_main_track%type,
        component_track_offset in segment.component_track_offset%type,
        duration_of_component in segment.duration_of_component%type,
        sample_id in recordings.recording_id%type,
        sample_type in char
    );
end compilation_mgmt;
/
create or replace package body compilation_mgmt as

    procedure createCompilation(
        compilation_name in compilations.compilation_name%type,
        compilation_id out compilations.compilation_id%type
    )
    as
    begin
        if(compilation_name is null) then
            raise_application_error(-20001, 'the compilation_name is null for procedure createCompilation');
        end if;
        insert into compilations (compilation_name, creation_time, duration) values (compilation_name, localTimeStamp(0), 0);
        compilation_id := compilation_id_seq.currval;
    end;

    procedure addSampleToCompilation(
        compilation_id in compilations.compilation_id%type,
        main_track_offset in segment.main_track_offset%type,
        duration_in_main_track in segment.duration_in_main_track%type,
        component_track_offset in segment.component_track_offset%type,
        duration_of_component in segment.duration_of_component%type,
        sample_id in recordings.recording_id%type,
        sample_type in char
    )
    as
        segment_id segment.segment_id%type;
        max_duration number;
        segment_start segment.main_track_offset%type;
        segment_duration segment.duration_in_main_track%type;
    begin
        -- check for empty values
        if(main_track_offset < 0 or duration_in_main_track < 0 or component_track_offset < 0 or duration_of_component < 0) then
            raise_application_error(-20001, 'one or more of the durations is less than 0');
        end if;

        -- create the segment
        insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component) values (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component);
        segment_id := segment_id_seq.currval;

        --  link segment to compilation
        if(sample_type = 'c') then
            insert into compilationSamples values (compilation_id, sample_id, segment_id);
        elsif (sample_type = 'r') then
            insert into recordingSamples values (compilation_id, sample_id, segment_id);
        else
            raise_application_error(-20001, 'the sample type must be either "c" or "r"');
        end if;

        -- change the duration of the compilation
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
end compilation_mgmt;