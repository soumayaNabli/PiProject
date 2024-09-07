package com.example.rattrapagepi.entities;


import com.example.rattrapagepi.entities.enumeration.Priorite;
import com.example.rattrapagepi.entities.enumeration.StatutReclamation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatutReclamation statut;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    @Lob
    private byte[] fichier;


    @ToString.Exclude
    @ManyToOne
    @JsonBackReference
    private User userReclamation;

    @ToString.Exclude
    @OneToOne(mappedBy = "reclamation")
    @JsonManagedReference
    private Reponse reponse;

    @PrePersist
    public void onCreate() {
        date = LocalDate.now();
    }

}
