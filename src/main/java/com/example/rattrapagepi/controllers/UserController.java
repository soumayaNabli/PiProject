package com.example.rattrapagepi.controllers;

import com.example.rattrapagepi.DTO.AuthRequest;
import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.Role;
import com.example.rattrapagepi.security.JwtUtil;
import com.example.rattrapagepi.security.UserDetailsServiceImpl;
import com.example.rattrapagepi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService serviceUser;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }



    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        serviceUser.registerUser(user);  // Utilisation de serviceUser
        return ResponseEntity.ok(user);
    }
    @GetMapping("/afficherUser")
    public List<User> read(){return serviceUser.lire();}

    @GetMapping("/afficherUser/{id}")
    public User findUserById (@PathVariable int id){
        return serviceUser.afficherParId(id);
    }

    @GetMapping("/afficherUserParRole/{role}")
    public List<User> findUserByRole (@PathVariable Role role){
        return serviceUser.afficherParRole(role);
    }

    @PutMapping("/modifierUser/{id}")
    public User update(@PathVariable int id,@RequestBody User user){
        return serviceUser.modifier(id,user);
    }

    @DeleteMapping("/supprimerUser/{id}")
    public String delete(@PathVariable int id){
        return serviceUser.supprimer(id);
    }

    //LOGIN
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Authentifier l'utilisateur
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException e) {
            // Gérer l'exception d'authentification
            throw new Exception("Incorrect username or password", e);
        }

        // Charger les détails de l'utilisateur et générer le JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return "Authentification faite avec succès !";
    }
}




