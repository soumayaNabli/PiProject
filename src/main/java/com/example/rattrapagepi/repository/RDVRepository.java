package com.example.rattrapagepi.repository;

import com.example.rattrapagepi.entities.RDV;
import com.example.rattrapagepi.entities.enumeration.StatutRDV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RDVRepository extends JpaRepository<RDV,Integer> {
    List<RDV> findByStatut(StatutRDV statut);
    List<RDV> findAllByOrderByDateAsc();
}
