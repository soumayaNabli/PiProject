package com.example.rattrapagepi.entities;

import com.example.rattrapagepi.entities.enumeration.StatutRDV;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RDV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private int duree;
    private String sujet;
    @Enumerated(EnumType.STRING)
    private StatutRDV statut;

   /* @ToString.Exclude
    @ManyToOne
    @JsonBackReference
    private User userRDV;*/

    @ToString.Exclude
    @ManyToMany
    @JsonBackReference
    private List<User> userRDV;





}
