package com.ascencio.dev.springwebfluxapi.web;

import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsRequestDto;
import com.ascencio.dev.springwebfluxapi.domain.dto.ProgramsResponseDto;
import com.ascencio.dev.springwebfluxapi.service.ProgramsService;
    import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(ProgramsController.class)
class ProgramsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProgramsService programsService;

    @Test
    public void createProgram() {
        // Datos de prueba para la solicitud
        ProgramsRequestDto requestDto = new ProgramsRequestDto();
        requestDto.setId(2);
        requestDto.setName("ADN");
        requestDto.setType("Tutor familiar");
        requestDto.setBeneficiary("Padres");
        requestDto.setResponsible("Equipo Soa");
        requestDto.setDescription("Escuela para padres");
        requestDto.setDuration(6);
        requestDto.setCondition("A");

        // Respuesta simulada del servicio para create
        ProgramsResponseDto responseDto = new ProgramsResponseDto(); // Puedes ajustar esto según tu respuesta real
        when(programsService.create(requestDto)).thenReturn(Mono.just(responseDto));

        // Realizar la solicitud POST y verificar la respuesta
        webTestClient.post()
                .uri("/v1/programs/save")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProgramsResponseDto.class)
                .isEqualTo(responseDto);
    }

    @Test
    public void findAllPrograms() {
        // Datos de prueba para la respuesta del servicio findAll
        List<ProgramsResponseDto> responseDtoList = new ArrayList<>();

        // Agregar programas de prueba a responseDtoList
        ProgramsResponseDto program1 = new ProgramsResponseDto();
        program1.setId(1);
        program1.setName("Programa 1");
        program1.setDescription("Descripción del Programa 1");

        ProgramsResponseDto program2 = new ProgramsResponseDto();
        program2.setId(2);
        program2.setName("Programa 2");
        program2.setDescription("Descripción del Programa 2");

        responseDtoList.add(program1);
        responseDtoList.add(program2);

        // Respuesta simulada del servicio findAll
        when(programsService.findAll()).thenReturn(Flux.fromIterable(responseDtoList));

        // Realiza la solicitud GET y verifica la respuesta
        webTestClient.get()
                .uri("/v1/programs/list")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProgramsResponseDto.class)
                .isEqualTo(responseDtoList);
    }

    @Test
    public void findInactivePrograms() {
        // Datos de prueba para la respuesta del servicio findInactive
        List<ProgramsResponseDto> responseDtoList = new ArrayList<>();

        // Agregar programas inactivos de prueba a responseDtoList
        ProgramsResponseDto program1 = new ProgramsResponseDto();
        program1.setId(1);
        program1.setName("Programa Inactivo 1");
        program1.setDescription("Descripción del Programa Inactivo 1");

        ProgramsResponseDto program2 = new ProgramsResponseDto();
        program2.setId(2);
        program2.setName("Programa Inactivo 2");
        program2.setDescription("Descripción del Programa Inactivo 2");
        responseDtoList.add(program1);
        responseDtoList.add(program2);

        // Respuesta simulada del servicio findInactive
        when(programsService.findInactive()).thenReturn(Flux.fromIterable(responseDtoList));

        // Realiza la solicitud GET y verifica la respuesta
        webTestClient.get()
                .uri("/v1/programs/listI")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProgramsResponseDto.class)
                .isEqualTo(responseDtoList);
    }

    @Test
    public void updateProgram() {
        // Datos de prueba para la solicitud
        ProgramsRequestDto requestDto = new ProgramsRequestDto();
        requestDto.setId(1);
        requestDto.setName("Programa Actualizado");
        requestDto.setType("Nuevo Tipo");
        requestDto.setBeneficiary("Nuevo Beneficiario");
        requestDto.setResponsible("Nuevo Responsable");
        requestDto.setDescription("Nueva Descripción");
        requestDto.setDuration(10);
        requestDto.setCondition("I");

        // Respuesta simulada del servicio para update
        ProgramsResponseDto responseDto = new ProgramsResponseDto(); // Puedes ajustar esto según tu respuesta real
        when(programsService.update(1, requestDto)).thenReturn(Mono.just(responseDto));

        // Realizar la solicitud PUT y verificar la respuesta
        webTestClient.put()
                .uri("/v1/programs/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProgramsResponseDto.class)
                .isEqualTo(responseDto);
    }

    @Test
    public void deleteProgram() {
        // ID de programa de prueba a eliminar
        int programId = 1;

        // Respuesta simulada del servicio para delete
        when(programsService.delete(programId)).thenReturn(Mono.empty());

        // Realizar la solicitud DELETE y verificar el estado
        webTestClient.delete()
                .uri("/v1/programs/delete/1")
                .exchange()
                .expectStatus().isNoContent();
    }

}
