package com.ascencio.dev.springwebfluxapi.web;

import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsRequestDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsResponseDto;
import com.ascencio.dev.springwebfluxapi.service.ProgramsService;
import com.ascencio.dev.springwebfluxapi.service.ReportService;
import lombok.RequiredArgsConstructor;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/v1/programs")
@RequiredArgsConstructor
public class ProgramsController {


    @Autowired
    private ReportService service;

    private final ProgramsService programsService;

    @GetMapping(value="/list", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<ProgramsResponseDto> findAll() {
        return this.programsService.findAll();
    }

    @GetMapping(value="/listI", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<ProgramsResponseDto> findInactive() {
        return this.programsService.findInactive();
    }

    @GetMapping(value = "/find/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<ProgramsResponseDto> findById(@PathVariable Integer id) {
        return this.programsService.findById(id);
    }

    @PutMapping(value = "/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<ProgramsResponseDto> update(@PathVariable Integer id, @RequestBody ProgramsRequestDto dto) {
        return this.programsService.update(id, dto);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<ProgramsResponseDto> create(@RequestBody ProgramsRequestDto dto) {
        return this.programsService.create(dto);
    }

    @GetMapping("/report/{format}")
    public Mono<String> generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.generateReport(format);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return this.programsService.delete(id);
    }

    @PutMapping(value = "/restore/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<ProgramsResponseDto> restore(@PathVariable Integer id) {
        return this.programsService.restore(id);
    }


    @PostMapping("/bulk")
    public Mono<Void> saveAsignationProgramsBulk(@RequestBody ProgramsAsignationDto dto){
        return this.programsService.saveTeenBulk(dto);
    }
}
