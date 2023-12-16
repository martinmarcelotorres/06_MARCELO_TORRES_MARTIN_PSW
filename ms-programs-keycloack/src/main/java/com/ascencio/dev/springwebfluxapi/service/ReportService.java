package com.ascencio.dev.springwebfluxapi.service;

import com.ascencio.dev.springwebfluxapi.domain.model.Programs;
import com.ascencio.dev.springwebfluxapi.repository.ProgramsRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ProgramsRepository repository;

    public Mono<byte[]> generateReport(String reportFormat) {
        return repository.findAll()
                .collectList()
                .flatMap(programs -> generateAndSaveReport(programs, reportFormat))
                .onErrorResume(e -> {
                    String errorMessage = "Error generating report: " + e.getMessage();
                    return Mono.error(new RuntimeException(errorMessage, e));
                });
    }

    private Mono<byte[]> generateAndSaveReport(List<Programs> programs, String reportFormat) {
        return Mono.fromCallable(() -> {
            File file = ResourceUtils.getFile("classpath:programs.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(programs);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Josez");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return saveReport(jasperPrint, reportFormat);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private byte[] saveReport(JasperPrint jasperPrint, String reportFormat) throws JRException, IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (reportFormat.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            } else {
                throw new IllegalArgumentException("Invalid report format: " + reportFormat);
            }
            return outputStream.toByteArray();
        }
    }


    private ResponseEntity<byte[]> createResponse(byte[] content, String reportFormat) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("report." + reportFormat).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

}
