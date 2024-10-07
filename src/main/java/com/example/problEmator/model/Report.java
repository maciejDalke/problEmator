package com.example.problEmator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacher;
    private String room;
    private String problemDescription;

    private String status; // Domyślny status
    private LocalDate startDate;
    private LocalDateTime submissionDate; // Data zgłoszenia

    public Report(String teacher, String room, String problemDescription) {
        this.teacher = teacher;
        this.room = room;
        this.problemDescription = problemDescription;
        this.status = "Nowe"; // Domyślny status przy tworzeniu zgłoszenia
        this.submissionDate = LocalDateTime.now(); // Aktualna data i czas zgłoszenia
    }
}
