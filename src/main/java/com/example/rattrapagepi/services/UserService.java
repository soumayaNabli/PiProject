package com.example.rattrapagepi.services;

import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.Role;
import com.example.rattrapagepi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService  {

    @Autowired
    private UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;




    public User registerUser(User user) {
        user.setMdp(passwordEncoder.encode(user.getMdp()));
        return userRepo.save(user);
    }

    public List<User> lire() {
        return userRepo.findAll();
    }

    public User afficherParId (int id){
        return userRepo.findById(id).orElse(null);
    }

    public List<User> afficherParRole (Role role){
        return (List<User>) userRepo.findByRole(role);
    }

    public User modifier(int id, User user) {
        return userRepo.findById(id).map(user1 -> {
            user1.setUsername(user.getUsername());
           user1.setMdp(user.getMdp());
            user1.setProfession(user.getProfession());
            user1.setRole(user.getRole());
            user1.setEmail(user.getEmail());
            return userRepo.save(user1);

        }).orElse(null);
    }


    public String supprimer(int id) {
        userRepo.deleteById(id);
        return "Utilisateur supprimé" ;
    }




}
