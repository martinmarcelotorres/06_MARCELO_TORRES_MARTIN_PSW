package com.ascencio.dev.springwebfluxapi.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table
@Getter
@Setter
@NoArgsConstructor
public class Programs {

    @Id
    private Integer id;
    private String name;
    private String type;
    private String beneficiary;
    private String responsible;
    private String description;
    private Integer duration;
    private String condition;
    private String level;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    public Programs(String name, String type, String beneficiary, String responsible, String description, Integer duration, String condition, String level, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.type = type;
        this.beneficiary = beneficiary;
        this.responsible = responsible;
        this.description = description;
        this.duration = duration;
        this.condition = condition;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Programs(Integer id, String name, String type, String beneficiary, String responsible, String description, String condition, Integer duration, String level, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.beneficiary = beneficiary;
        this.responsible = responsible;
        this.description = description;
        this.condition = condition;
        this.duration = duration;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
