package com.example.problEmator.controller;

import com.example.problEmator.model.Report;
import com.example.problEmator.repository.ReportRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ReportController {
    private final ReportRepository repository;

    public ReportController(ReportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/reports")
    public String submissionList(Model model) {
        model.addAttribute("reports", repository.findAll());
        return "reports";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Wyświetlanie formularza edycji zgłoszenia
    @GetMapping("/reports/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editReport(@PathVariable Long id, Model model) {
        Optional<Report> optionalReport = repository.findById(id);
        if (optionalReport.isPresent()) {
            model.addAttribute("report", optionalReport.get());
            return "edit_report";
        } else {
            return "redirect:/reports";
        }
    }

//    // Aktualizacja zgłoszenia
//    @PostMapping("/reports/update/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String updateReport(@PathVariable Long id, @ModelAttribute Report report) {
//        Optional<Report> optionalReport = repository.findById(id);
//        if (optionalReport.isPresent()) {
//            Report existingReport = optionalReport.get();
//            existingReport.setStatus(report.getStatus());
//            existingReport.setStartDate(report.getStartDate());
//            repository.save(existingReport);
//        }
//        return "redirect:/reports";
//    }

    @PostMapping("/reports/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateReport(@PathVariable Long id, @ModelAttribute Report report) {
        Optional<Report> optionalReport = repository.findById(id);
        if (optionalReport.isPresent()) {
            Report existingReport = optionalReport.get();

            // Sprawdzamy, czy status został zmieniony
            if (!existingReport.getStatus().equals(report.getStatus())) {
                existingReport.setStatus(report.getStatus());
                existingReport.setStartDate(LocalDate.from(LocalDateTime.now())); // Ustawiamy aktualną datę i godzinę za każdym razem, gdy status się zmienia

            }

            repository.save(existingReport);
        }
        return "redirect:/reports";
    }
}
