package com.example.rattrapagepi.controllers;

import com.example.rattrapagepi.entities.RDV;
import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.StatutRDV;
import com.example.rattrapagepi.services.RDVService;
import com.example.rattrapagepi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rdv")
@Controller
public class RDVController {
    private RDVService serviceRDV;

    @PostMapping("/")
    public RDV create(@RequestBody RDV rdv){
        return serviceRDV.ajouter(rdv);
    }

    @GetMapping("/")
    public List<RDV> read(){return serviceRDV.lire();}

    @GetMapping("/{id}")
    public RDV findUserById (@PathVariable int id){
        return serviceRDV.afficherParId(id);
    }

    @GetMapping("/parStatut/{statut}")
    public List<RDV> findUserByStatut (@PathVariable StatutRDV statut){
        return serviceRDV.afficherParStatut(statut);
    }

    @PutMapping("/{id}")
    public RDV update(@PathVariable int id, @RequestBody RDV rdv){
        return serviceRDV.modifier(id,rdv);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        return serviceRDV.supprimer(id);
    }

    @GetMapping("/parDate")
    public List<RDV> getFutureRdvsSortedByDate() {
        return serviceRDV.trierParDate();
    }
}
