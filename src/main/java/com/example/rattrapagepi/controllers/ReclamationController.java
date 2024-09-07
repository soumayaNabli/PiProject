package com.example.rattrapagepi.controllers;

import com.example.rattrapagepi.entities.Reclamation;
import com.example.rattrapagepi.entities.enumeration.Priorite;
import com.example.rattrapagepi.entities.enumeration.StatutReclamation;
import com.example.rattrapagepi.fonctionnalites.MoisEtAnnee;
import com.example.rattrapagepi.services.ReclamationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/reclamation")
@Controller
public class ReclamationController {
    @Autowired
    private ReclamationService serviceRec;


    @PostMapping("/ajouterRec")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation){
        return serviceRec.ajouterRec(reclamation);
    }

    @GetMapping("/afficherRec")
    public List<Reclamation> findAllReclamations(){
        return serviceRec.afficherRec();
    }

    @GetMapping("/afficherRec/{id}")
    public Reclamation findReclamationById(@PathVariable int id){
        return serviceRec.afficherRecParId(id);
    }

    /*@GetMapping("/afficherRecParPriorite/{priorite}")
    public List<Reclamation> findReclamationByPriorityLevel(@PathVariable Priorite priorite){
        return serviceRec.aff(priorite);
    }*/

    @PutMapping("/modifierRec/{id}")
    public Reclamation updateReclamation(@PathVariable int id,@RequestBody Reclamation reclamation){
                    return serviceRec.modifierRec(id,reclamation);

    }

    @DeleteMapping("/supprimerRec/{id}")
    public String deleteReclamation(@PathVariable int id){
        return serviceRec.supprimerRec(id);
    }

    @GetMapping("/trierParPriorite")
    public List<Reclamation> trierRecParPriorite() {
        return serviceRec.trierRecParPriorite();
    }

    //STATISTIQUES :

    @GetMapping("/tempsMoy")
    public double tempsMoyenResolution() {
        return serviceRec.tempsMoyenResolution();
    }

    @GetMapping("/recParMois")
    public Map<MoisEtAnnee, Integer> afficherRecParMois() {
        return serviceRec.afficherRecParMois();
    }

    @GetMapping("/recParStatut")
    public List<Map<String, Object>> statParStatut() {
        return serviceRec.statParStatut();
    }






}
