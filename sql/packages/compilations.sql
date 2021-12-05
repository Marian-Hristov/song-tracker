create or replace package compilation_mgmt as
    procedure createCompilation(ref_compilation_name in compilations.compilation_name%type);
    procedure addSampleToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_main_track_offset in segment.main_track_offset%type,
        ref_duration_in_main_track in segment.duration_in_main_track%type,
        ref_component_track_offset in segment.component_track_offset%type,
        ref_duration_of_component_used in segment.duration_of_component_used%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    );
    procedure deleteSampleFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_sample_id in recordings.recording_id%type,
        ref_segment_id in segment.segment_id%type,
        ref_sample_type in char
    );
    procedure deleteCompilation(ref_compilation_id in compilations.compilation_id%type);
    procedure updateCompilation(ref_compilation_id in compilations.compilation_id%type, ref_compilation_name in compilations.compilation_name%type);
    procedure addContributorToCompilation(ref_compilation_id in compilations.compilation_id%type, ref_contributor_id in contributors.contributor_id%type, ref_role_id in compilationRoles.role_id%type);
    procedure removeContributorFromCompilation(ref_compilation_id in compilations.compilation_id%type, ref_contributor_id in contributors.contributor_id%type, ref_role_id in compilationRoles.role_id%type);

end compilation_mgmt;
/
commit;
/
create or replace package body compilation_mgmt as

    procedure createCompilation(ref_compilation_name in compilations.compilation_name%type)
    as
    begin
        if(ref_compilation_name is null) then
            raise_application_error(-20001, 'the compilation_name is null for procedure createCompilation');
        end if;
        insert into compilations (compilation_name, creation_time, duration) values (ref_compilation_name, current_timestamp, 0);
    end;

    procedure addSampleToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_main_track_offset in segment.main_track_offset%type,
        ref_duration_in_main_track in segment.duration_in_main_track%type,
        ref_component_track_offset in segment.component_track_offset%type,
        ref_duration_of_component_used in segment.duration_of_component_used%type,
        ref_sample_id in recordings.recording_id%type,
        ref_sample_type in char
    )
    as
        segment_id segment.segment_id%type;
        max_duration number;
        segment_start segment.main_track_offset%type;
        segment_duration segment.duration_in_main_track%type;
    begin
        -- check for empty values
        if(ref_main_track_offset < 0 or ref_duration_in_main_track < 0 or ref_component_track_offset < 0 or ref_duration_of_component_used < 0) then
            raise_application_error(-20001, 'one or more of the durations is less than 0');
        end if;

        -- create the segment
        insert into segment (main_track_offset, duration_in_main_track, component_track_offset, duration_of_component_used) values (ref_main_track_offset, ref_duration_in_main_track, ref_component_track_offset, ref_duration_of_component_used);
        segment_id := segment_id_seq.currval;

        --  link segment to compilation
        if(ref_sample_type = 'c') then
            insert into compilationSamples values (ref_compilation_id, ref_sample_id, segment_id);
        elsif (ref_sample_type = 'r') then
            insert into recordingSamples values (ref_compilation_id, ref_sample_id, segment_id);
        else
            raise_application_error(-20001, 'the sample type must be either "c" or "r"');
        end if;

        -- change the duration of the compilation
        max_duration :=0;
        for sample in (select * from compilationSamples where compilation_id = ref_compilation_id) loop
            select main_track_offset, duration_in_main_track into segment_start, segment_duration
                from segment where segment_id = sample.segment_id;
            if (segment_start + segment_duration > max_duration) then
                max_duration := segment_start + segment_duration;
            end if;
        end loop;

        for sample in (select * from recordingSamples where compilation_id = ref_compilation_id) loop
            select main_track_offset, duration_in_main_track into segment_start, segment_duration
                from segment where segment_id = sample.segment_id;
            if (segment_start + segment_duration > max_duration) then
                max_duration := segment_start + segment_duration;
            end if;
        end loop;

        update compilations set duration = max_duration where compilation_id = ref_compilation_id;
    end;

    procedure deleteSampleFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_sample_id in recordings.recording_id%type,
        ref_segment_id in segment.segment_id%type,
        ref_sample_type in char
    )
    as
    begin
        if (ref_sample_type = 'c') then
            delete from compilationSamples
            where compilation_id = ref_compilation_id
            and compilation_used = ref_sample_id
            and segment_id = ref_segment_id;
        elsif (ref_sample_type = 'r') then
            delete from recordingSamples
            where compilation_id = ref_compilation_id
            and recording_id = ref_sample_id
            and segment_id = ref_segment_id;
        else
            raise_application_error(-20001, 'the sample type must be either (c)ompilation or (r)ecording');
        end if;
        delete from segment
        where segment_id = ref_segment_id;
    end;

    procedure deleteCompilation(ref_compilation_id in compilations.compilation_id%type)
    as
    begin
        for sample in (select * from compilationSamples where compilation_id = ref_compilation_id) loop
            delete from segment where segment_id = sample.segment_id;
        end loop;
        for sample in (select * from recordingSamples where compilation_id = ref_compilation_id) loop
            delete from segment where segment_id = sample.segment_id;
        end loop;
        delete from recordingSamples where compilation_id = ref_compilation_id;
        delete from compilationSamples where compilation_id = ref_compilation_id;
        delete from compilations where compilation_id = ref_compilation_id;
    end;

    procedure updateCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_compilation_name in compilations.compilation_name%type
    )
    as
    begin
        update compilations set compilation_name = compilation_name where compilation_id = ref_compilation_id;
    end;
    procedure addContributorToCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_contributor_id in contributors.contributor_id%type,
        ref_role_id in compilationRoles.role_id%type
    )
    as
    begin
        if(ref_compilation_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more of the parameters for the procedure addContributorToCompilation is invalid');
        end if;
        insert into compilationContributions values (ref_compilation_id, ref_contributor_id, ref_role_id);
    end;

    procedure removeContributorFromCompilation(
        ref_compilation_id in compilations.compilation_id%type,
        ref_contributor_id in contributors.contributor_id%type,
        ref_role_id in compilationRoles.role_id%type
    )
    as
    begin
        if(ref_compilation_id < 1 or ref_contributor_id < 1 or ref_role_id < 1) then
            raise_application_error(-20001, 'one or more of the parameters for the procedure removeContributorFromCompilation is invalid');
        end if;
        delete from compilationContributions where compilation_id = ref_compilation_id and contributor_id = ref_contributor_id and role_id = ref_role_id;
    end;
    
end compilation_mgmt;
/
commit;