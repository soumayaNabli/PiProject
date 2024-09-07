package com.example.rattrapagepi.services;

import com.example.rattrapagepi.entities.RDV;
import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.StatutRDV;
import com.example.rattrapagepi.repository.RDVRepository;
import com.example.rattrapagepi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RDVService {
    @Autowired
    private RDVRepository rdvRepo;

    public RDV ajouter(RDV rdv) {
        rdv.setStatut(StatutRDV.planifie);
        return rdvRepo.save(rdv);
    }

    public List<RDV> lire() {
        return rdvRepo.findAll();
    }

    public RDV afficherParId (int id){
        return rdvRepo.findById(id).orElse(null);
    }

    public List<RDV> afficherParStatut (StatutRDV statut){
        return (List<RDV>) rdvRepo.findByStatut(statut);
    }

    public RDV modifier(int id, RDV rdv) {
        return rdvRepo.findById(id).map(newRDV -> {
            newRDV.setDate(rdv.getDate());
            newRDV.setDuree(rdv.getDuree());
            newRDV.setSujet(rdv.getSujet());
            newRDV.setStatut(rdv.getStatut());
            return rdvRepo.save(newRDV);

        }).orElse(null);
    }


    public String supprimer(int id) {
        rdvRepo.deleteById(id);
        return "Rendez-vous supprimé" ;
    }

    public List<RDV> trierParDate() {
        LocalDateTime now = LocalDateTime.now();
        return rdvRepo.findAllByOrderByDateAsc().stream()
                .filter(rdv -> rdv.getDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 3600000) // Exécuter toutes les heures (3600000 ms)
    public void updateStatut() {
        List<RDV> rdvs = rdvRepo.findAll();
        LocalDateTime maintenant = LocalDateTime.now();

        for (RDV rdv : rdvs) {
            if (maintenant.isAfter(rdv.getDate()) && rdv.getStatut() != StatutRDV.effectue) {
                rdv.setStatut(StatutRDV.effectue);
                rdvRepo.save(rdv);
            }
        }
    }
}
