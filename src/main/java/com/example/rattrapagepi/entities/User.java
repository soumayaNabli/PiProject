package com.example.rattrapagepi.entities;
import com.example.rattrapagepi.entities.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String mdp;
    private String profession;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "userReclamation")
    @JsonManagedReference
    private List<Reclamation> reclamations;

    /*@ToString.Exclude
    @OneToMany(mappedBy = "userRDV")
    @JsonManagedReference
    private List<RDV> rdvs;*/

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "userRDV",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RDV> rdvs;


}
