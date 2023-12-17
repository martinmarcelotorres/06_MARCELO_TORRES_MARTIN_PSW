package com.ascencio.dev.springwebfluxapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ProgramsRequestDto implements Serializable {
    private static final long serialVersionUID = 8222253670338491507L;

    private Integer id;
    private String name;
    private String type;
    private String beneficiary;
    private String responsible;
    private String description;
    private String condition;
    private Integer duration;
    private String level;

//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private LocalDate startDate;
//
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private LocalDate endDate;
}
