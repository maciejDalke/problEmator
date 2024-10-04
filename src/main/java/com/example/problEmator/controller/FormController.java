package com.example.problEmator.controller;

import com.example.problEmator.model.Report;
import com.example.problEmator.repository.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    private final ReportRepository repository;

    public FormController(ReportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/form")
    public String showForm(Report report) {
        return "form";
    }

    @PostMapping("/submit")
    public String submitReport(Report report) {
        System.out.println("Submitting report: " + report);
        repository.save(report);
        return "redirect:/reports";
    }
}
