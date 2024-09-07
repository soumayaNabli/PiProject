package com.example.rattrapagepi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDate date;
    @Min(1)
    @Max(5)
    private int rating;

    @ToString.Exclude
    @OneToOne
    @JsonBackReference
    private Reclamation reclamation;

    @PrePersist
    public void onCreate() {
        date = LocalDate.now();
    }
}
