package com.ascencio.dev.springwebfluxapi.web;

import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsRequestDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsResponseDto;
import com.ascencio.dev.springwebfluxapi.service.ProgramsService;
import com.ascencio.dev.springwebfluxapi.service.ReportService;

import com.ascencio.dev.springwebfluxapi.service.ReportServiceActive;
import com.ascencio.dev.springwebfluxapi.service.ReportServiceInactive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;


import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/v1/programs")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProgramsController {


    @Autowired
    private ReportService service;

    private final ProgramsService programsService;



    @Autowired
    private ReportServiceInactive reportServiceInactive;


    @Autowired
    private ReportServiceActive reportServiceActive;

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
    public Mono<ResponseEntity<byte[]>> generateReport(@PathVariable String format) {
        return service.generateReport(format)
                .map(content -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDisposition(ContentDisposition.attachment().filename("reporte_programas.pdf").build());
                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(content);
                });
    }

    @GetMapping("/reportInactive/{format}")
    public Mono<ResponseEntity<byte[]>> generateInactiveReport(@PathVariable String format) {
        return reportServiceInactive.generateReport(format)
                .map(content -> {
                    HttpHeaders headers = new HttpHeaders();
                    MediaType mediaType = format.equalsIgnoreCase("pdf") ? MediaType.APPLICATION_PDF : MediaType.TEXT_HTML;
                    headers.setContentType(mediaType);
                    headers.setContentDisposition(ContentDisposition.attachment().filename("reporte_programas_inactivos." + format).build());

                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(content);
                });

}

    @GetMapping("/reportActive/{format}")
    public Mono<ResponseEntity<byte[]>> generateActiveReport(@PathVariable String format) {
        return reportServiceActive.generateReport(format)
                .map(content -> {
                    HttpHeaders headers = new HttpHeaders();
                    MediaType mediaType = format.equalsIgnoreCase("pdf") ? MediaType.APPLICATION_PDF : MediaType.TEXT_HTML;
                    headers.setContentType(mediaType);
                    headers.setContentDisposition(ContentDisposition.attachment().filename("reporte_programas_activos." + format).build());

                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(content);
                });
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

}
