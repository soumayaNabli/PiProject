package com.example.rattrapagepi.repository;

import com.example.rattrapagepi.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ReclamationRepository extends JpaRepository<Reclamation,Integer> {

    @Query("SELECT r.statut AS statut, COUNT(r) * 100.0 /(SELECT COUNT(r2) FROM Reclamation r2) AS pourcentage " +
            "FROM Reclamation r " +
            "GROUP BY r.statut")
    List<Map<String, Object>> statParStatut();
}
