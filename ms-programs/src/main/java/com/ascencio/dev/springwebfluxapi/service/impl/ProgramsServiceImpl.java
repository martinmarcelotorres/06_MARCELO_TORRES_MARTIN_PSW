package com.ascencio.dev.springwebfluxapi.service.impl;

import com.ascencio.dev.springwebfluxapi.domain.dto.AsignationRequestDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsAsignationDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsRequestDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsResponseDto;
import com.ascencio.dev.springwebfluxapi.domain.mapper.ProgramsMapper;
import com.ascencio.dev.springwebfluxapi.exception.ResourceNotFoundException;
import com.ascencio.dev.springwebfluxapi.repository.ProgramsRepository;
import com.ascencio.dev.springwebfluxapi.service.ProgramsService;
import com.ascencio.dev.springwebfluxapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;

import static com.ascencio.dev.springwebfluxapi.domain.mapper.ProgramsMapper.toModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramsServiceImpl implements ProgramsService {

    private final ProgramsRepository programsRepository;
    private final ReportService reportService;

    @Override
    public Mono<ProgramsResponseDto> findById(Integer id) {
        return this.programsRepository.findById(id)
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Flux<ProgramsResponseDto> findAll() {
        return this.programsRepository.findAll()
                .filter(programa -> programa.getCondition().equals("A"))
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Flux<ProgramsResponseDto> findInactive() {
        return this.programsRepository.findAll()
                .filter(programa -> programa.getCondition().equals("I"))
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Mono<ProgramsResponseDto> create(ProgramsRequestDto request) {
        return this.programsRepository.save(toModel(request))
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Mono<ProgramsResponseDto> update(Integer id, ProgramsRequestDto request) {
        return this.programsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El id : " + id + " no fue encontrado")))
                .flatMap(programs -> this.programsRepository.save(toModel(programs.getId(), request)))
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return this.programsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El id : " + id + " no fue encontrado")))
                .flatMap(programa -> {
                    programa.setCondition("I");
                    return this.programsRepository.save(programa);
                })
                .doOnSuccess(unused -> log.info("Se elimino el programa con id: {}", id))
                .then();

    }

    @Override
    public Mono<ProgramsResponseDto> restore(Integer id) {
        return this.programsRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El id : " + id + " no fue encontrado")))
                .flatMap(programa -> {
                    programa.setCondition("A"); // Cambia la condición a "A" (activo)
                    return this.programsRepository.save(programa);
                })
                .map(ProgramsMapper::toDto);
    }

    @Override
    public Mono<Void> saveTeenBulk(ProgramsAsignationDto dto) {

        dto.getTeens().forEach(data ->{
            AsignationRequestDto asignation = AsignationRequestDto.builder()
                    .id_adolescent(data.getId_adolescent())
                    .id_program(dto.getId_program())
                    .assignment_date(dto.getAssignment_date())
                    .build();

            callToApi(asignation);
        });

        return Mono.empty();
    }


    private void callToApi(AsignationRequestDto asignation) {
        WebClient client = WebClient.create();

        client.post()
                .uri("http://localhost:8086/v1/asignations/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(asignation))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }


}
