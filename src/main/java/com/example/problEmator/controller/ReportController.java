package com.example.problEmator.controller;

import com.example.problEmator.repository.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    private final ReportRepository reportRepository;

    public ReportController(ReportRepository repository) {
        this.reportRepository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/reports")
    public String submissionList(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        return "reports";
    }
}
