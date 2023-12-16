package com.ascencio.dev.springwebfluxapi.service;

import com.ascencio.dev.springwebfluxapi.domain.model.Programs;
import com.ascencio.dev.springwebfluxapi.repository.ProgramsRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceInactive {

    @Autowired
    private ProgramsRepository repository;

    public Mono<byte[]> generateReport(String reportFormat) {
        return repository.findAll()
                .collectList()
                .flatMap(programs -> generateAndSaveReport(programs, reportFormat))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error generating report: " + e.getMessage(), e)));
    }

    private Mono<byte[]> generateAndSaveReport(List<Programs> programs, String reportFormat) {
        return Mono.fromCallable(() -> {
            // Filtrar solo los programas con condition 'I'
            List<Programs> filteredPrograms = programs.stream()
                    .filter(program -> "I".equals(program.getCondition()))
                    .collect(Collectors.toList());

            // load file and compile it
            File file = ResourceUtils.getFile("classpath:programs_inactive.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(filteredPrograms);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Josez");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return saveReport(jasperPrint, reportFormat);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private byte[] saveReport(JasperPrint jasperPrint, String reportFormat) throws JRException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (reportFormat.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            } else  {
                throw new IllegalArgumentException("Invalid report format: " + reportFormat);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new JRException("Error saving report: " + e.getMessage(), e);
        }
    }
}
