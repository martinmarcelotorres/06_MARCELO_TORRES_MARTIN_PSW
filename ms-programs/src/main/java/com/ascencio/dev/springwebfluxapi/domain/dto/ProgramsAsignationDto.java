package com.ascencio.dev.springwebfluxapi.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProgramsAsignationDto {

    private Integer id;
    private List<ProgramsAssigndDto> teens;
    private Integer id_program;
    private LocalDate assignment_date;

    @Getter
    @Setter
    public static class ProgramsAssigndDto{
        private Integer id_adolescent;
    }
}
