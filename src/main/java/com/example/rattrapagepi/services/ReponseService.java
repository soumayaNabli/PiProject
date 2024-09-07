package com.example.rattrapagepi.services;

import com.example.rattrapagepi.entities.Reclamation;
import com.example.rattrapagepi.entities.Reponse;
import com.example.rattrapagepi.entities.User;
import com.example.rattrapagepi.entities.enumeration.StatutReclamation;
import com.example.rattrapagepi.repository.ReclamationRepository;
import com.example.rattrapagepi.repository.ReponseRepository;
import com.example.rattrapagepi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReponseService {
    @Autowired
    private ReponseRepository reprepo;
    private ReclamationRepository recrepo;

    //SEND EMAIL :
    private void sendEmail(String to, String subject, String body) throws MessagingException {

        String from = "techwork414@gmail.com";
        String password = "pacrvzlvscatwwkb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        //   message.setText(body);
        message.setContent(body, "text/html");
        Transport.send(message);
    }
    public Reponse ajouter(Reponse reponse) {
        Reclamation reclamation = recrepo.findById(reponse.getReclamation().getId()).orElse(null);
        User user=reclamation.getUserReclamation();
        if (reclamation != null) {
            reponse.setReclamation(reclamation);
            reponse.getReclamation().setStatut(StatutReclamation.traite);
            Reponse savedRep=reprepo.save(reponse);
            String to=user.getEmail();
            String subject = "Réclamation Traitée - Vérifiez la Réponse sur le Site Web";
            String body =String.format("Cher(e) %s ,<br> <br>Nous sommes heureux de vous informer que votre réclamation a été traitée.<br> " +
                            "Veuillez vous connecter à notre site web pour consulter la réponse.",
                    user.getUsername()) ;
            try {
                sendEmail(to, subject, body);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return savedRep;
        } else {
            return null;}
    }

    public List<Reponse> lire() {
        return reprepo.findAll();
    }

    public Reponse afficherParId (int id){
        return reprepo.findById(id).orElse(null);
    }

    public Reponse modifier(int id, Reponse reponse) {
        return reprepo.findById(id).map(newReponse -> {
            newReponse.setDescription(reponse.getDescription());
            newReponse.setDate(reponse.getDate());
            newReponse.setRating(reponse.getRating());
            return reprepo.save(newReponse);

        }).orElse(null);
    }


    public String supprimer(int id) {
        reprepo.deleteById(id);
        return "Reponse supprimée" ;
    }

    public List<Reponse> getReponsesSortedByDate() {
        return reprepo.findAllByOrderByDateAsc();
    }

    //STATISTIQUES
    public Map<Integer, Long> calculerPourcentageRatings() {
        List<Reponse> reponses = reprepo.findAll();
        long totalReponses = reponses.size();

        // Calculer le pourcentage de chaque rating
        return reponses.stream()
                .collect(Collectors.groupingBy(Reponse::getRating, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round((entry.getValue() * 100.0) / totalReponses)
                ));
    }

}
