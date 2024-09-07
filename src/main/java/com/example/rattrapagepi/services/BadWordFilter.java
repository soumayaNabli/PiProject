package com.example.rattrapagepi.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class BadWordFilter {
    private Set<String> badWords = new HashSet<>();

    @Value("classpath:bad-words.txt")
    private Resource badWordsFile;

    @PostConstruct
    public void loadBadWords() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(badWordsFile.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim().toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Pour gérer les erreurs liées au chargement du fichier
        }
    }

        // Méthode pour vérifier si un texte contient des mots interdits
        public boolean containsBadWords(String text) {
            for (String word : badWords) {
                if (text.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }

        // Méthode pour filtrer les mots interdits du texte
        public String filterBadWords(String text) {
            String filteredText = text;
            for (String word : badWords) {
                filteredText = filteredText.replaceAll("(?i)" + word, "***");
            }
            return filteredText;
        }


}
