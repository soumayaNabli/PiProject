package com.example.rattrapagepi.services;

import com.example.rattrapagepi.entities.Reclamation;
import com.example.rattrapagepi.entities.Reponse;
import com.example.rattrapagepi.entities.enumeration.Priorite;
import com.example.rattrapagepi.entities.enumeration.StatutReclamation;
import com.example.rattrapagepi.fonctionnalites.MoisEtAnnee;
import com.example.rattrapagepi.repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository recRepo;

    @Autowired
    private BadWordFilter badWordFilterService;



    public void ajouterRecAvecFichier(String description, StatutReclamation statut, LocalDate date, Priorite priorite, MultipartFile fichier) throws IOException {
        Reclamation reclamation = new Reclamation();
        reclamation.setDescription(description);
        reclamation.setStatut(statut);
        reclamation.setDate(date);
        reclamation.setPriorite(priorite);
        reclamation.setStatut(StatutReclamation.non_traite);
        reclamation.setFichier(fichier.getBytes());
        recRepo.save(reclamation);
    }

    public Reclamation ajouterRec(Reclamation reclamation){
        String filteredDescription = badWordFilterService.filterBadWords(reclamation.getDescription());
        reclamation.setDescription(filteredDescription);
        reclamation.setStatut(StatutReclamation.non_traite);
        recRepo.save(reclamation);
        return reclamation;
    }

    public List<Reclamation> afficherRec(){
        return recRepo.findAll();
    }
    public Reclamation afficherRecParId(int id){
        return recRepo.findById(id).orElse(null);
    }
   /* public List<Reclamation> afficherRecParPriorite(Priorite priorite){
        return recRepo.findByPriorite(priorite);
    }*/

    public String supprimerRec(int id){
        recRepo.deleteById(id);
        return "reclamation supprimée : "+id;
    }
    public Reclamation modifierRec(int id,Reclamation reclamation){
        return recRepo.findById(id).map(newReclamation -> {
            newReclamation.setDescription(reclamation.getDescription());
            newReclamation.setDate(reclamation.getDate());
            newReclamation.setStatut(reclamation.getStatut());
            newReclamation.setPriorite(reclamation.getPriorite());
            newReclamation.setReponse(reclamation.getReponse());
            return recRepo.save(newReclamation);

        }).orElse(null);

    }

    public List<Reclamation> trierRecParPriorite() {
        List<Reclamation> reclamations = recRepo.findAll();

        // Trier les réclamations en utilisant l'ordre défini dans l'énumération Priorite
        return reclamations.stream()
                .sorted(Comparator.comparing(Reclamation::getPriorite, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    //STATISTIQUES:

    //calculer le temps moyen par jours de résolution d'une reclamation
    public double tempsMoyenResolution() {
        List<Reclamation> reclamations = recRepo.findAll();

        long totalDurationInDays = 0;
        int totalReclamationsWithResponses = 0;

        for (Reclamation reclamation : reclamations) {
            if (reclamation.getReponse() != null) {
                Reponse rep = reclamation.getReponse();
                Duration duration = Duration.between(reclamation.getDate().atStartOfDay(), rep.getDate().atStartOfDay());
                totalDurationInDays += duration.toDaysPart();
                totalReclamationsWithResponses++;
            }
        }

        if (totalReclamationsWithResponses == 0) {
            return 0;
        }

        return (double) totalDurationInDays / totalReclamationsWithResponses;
    }

    //récupérer le nombre de réclamations soumises par mois
    public Map<MoisEtAnnee, Integer> afficherRecParMois() {
        List<Reclamation> reclamations = recRepo.findAll();
        Map<MoisEtAnnee, Integer> reclamationsParMois = new TreeMap<>(); // Utilisez TreeMap ici

        for (Reclamation reclamation : reclamations) {
            if (reclamation.getDate() != null) {
                MoisEtAnnee monthYear = new MoisEtAnnee(reclamation.getDate().getMonth(), reclamation.getDate().getYear());
                reclamationsParMois.put(monthYear, reclamationsParMois.getOrDefault(monthYear, 0) + 1);
            }
        }

        return reclamationsParMois;
    }

    //réclamations par statut (pourcentage de chaque statut)
    public List<Map<String, Object>> statParStatut() {
        return recRepo.statParStatut();
    }



}
