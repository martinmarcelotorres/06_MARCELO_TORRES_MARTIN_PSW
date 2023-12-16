package com.ascencio.dev.springwebfluxapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProgramsResponseDto implements Serializable {
   private static final long serialVersionUID = 8735757125749188522L;

   private Integer id;
   private String name;
   private String type;
   private String beneficiary;
   private String responsible;
   private String description;
   private Integer duration;
   private String condition;
   private String level;

   @JsonFormat(pattern = "dd-MM-yyyy")
   @DateTimeFormat(pattern = "dd-MM-yyyy")
   private LocalDate startDate;

   @JsonFormat(pattern = "dd-MM-yyyy")
   @DateTimeFormat(pattern = "dd-MM-yyyy")
   private LocalDate endDate;

   public ProgramsResponseDto() {

   }
}
