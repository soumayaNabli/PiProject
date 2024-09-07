package com.example.rattrapagepi.repository;

import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findByRole(Role role);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);


}
