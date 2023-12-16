package com.ascencio.dev.springwebfluxapi.service;

import com.ascencio.dev.springwebfluxapi.domain.model.Programs;
import com.ascencio.dev.springwebfluxapi.repository.ProgramsRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ProgramsRepository repository;

    public Mono<String> generateReport(String reportFormat) {
        String path = "C:\\Users\\josez\\Downloads";

        return repository.findAll()
                .collectList()
                .flatMap(programs -> generateAndSaveReport(programs, reportFormat, path))
                .onErrorResume(e -> Mono.just("Error generating report: " + e.getMessage()));
    }

    private Mono<String> generateAndSaveReport(List<Programs> programs, String reportFormat, String path) {
        return Mono.fromCallable(() -> {
            // load file and compile it
            File file = ResourceUtils.getFile("classpath:programs.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(programs);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Josez");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            saveReport(jasperPrint, reportFormat, path);

            return "Report generated successfully. Path: " + path;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void saveReport(JasperPrint jasperPrint, String reportFormat, String path) throws JRException {
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\programs.html");
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\programs.pdf");
        } else {
            throw new IllegalArgumentException("Invalid report format: " + reportFormat);
        }
    }}


