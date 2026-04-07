package com.boleia.boleia.shared.config;

import java.io.FileInputStream;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

@Configuration
public class FirebaseConfig {
    
    @PostConstruct
    public void initialize(){
        try {
     
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
