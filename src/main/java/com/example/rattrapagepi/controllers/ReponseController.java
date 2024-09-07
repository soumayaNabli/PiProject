package com.example.rattrapagepi.controllers;

import com.example.rattrapagepi.entities.Reponse;
import com.example.rattrapagepi.services.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/reponse")
@Controller
public class ReponseController {
    private ReponseService serviceReponse;

    @PostMapping("/")
    public Reponse create(@RequestBody Reponse reponse){
        return serviceReponse.ajouter(reponse);
    }

    @GetMapping("/")
    public List<Reponse> read(){return serviceReponse.lire();}

    @GetMapping("/{id}")
    public Reponse findUserById (@PathVariable int id){
        return serviceReponse.afficherParId(id);
    }

    @PutMapping("/{id}")
    public Reponse update(@PathVariable int id,@RequestBody Reponse reponse){
        return serviceReponse.modifier(id,reponse);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        return serviceReponse.supprimer(id);
    }

    @GetMapping("/parDate")
    public List<Reponse> trierParDate() {
        return serviceReponse.getReponsesSortedByDate();
    }

    //STATISTIQUES
    @GetMapping("/pourcentageRatings")
    public ResponseEntity<Map<Integer, Long>> calculerPourcentageRatings() {
        Map<Integer, Long> pourcentage = serviceReponse.calculerPourcentageRatings();
        return ResponseEntity.ok(pourcentage);
    }


}
