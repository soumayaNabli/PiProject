package com.example.rattrapagepi.repository;

import com.example.rattrapagepi.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse,Integer> {

    List<Reponse> findAllByOrderByDateAsc();
}
