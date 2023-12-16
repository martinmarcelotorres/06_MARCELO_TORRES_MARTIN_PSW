package com.ascencio.dev.springwebfluxapi.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class AsignationRequestDto implements Serializable {
    private static final long serialVersionUID = 8222253670338491507L;

    private Integer id_adolescent;
    private Integer id_program;
    private LocalDate assignment_date;
}
