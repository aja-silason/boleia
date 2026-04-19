package com.boleia.boleia.shared.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.auth.oauth2.GoogleCredentials;

@Configuration
public class FirebaseConfig {
    
    // @PostConstruct
    // public void initialize(){
    //    System.out.println("DEBUG: Tentando inicializar o Firebase...");
    //     try {
    //         ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            
    //         if (!resource.exists()) {
    //             System.err.println("Erro: O arquivo serviceAccountKey.json não foi encontrado em src/main/resources/");
    //             return;
    //         }

    //         InputStream serviceAccount = resource.getInputStream();

    //         FirebaseOptions options = FirebaseOptions.builder()
    //                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //                 .build();

    //         if (FirebaseApp.getApps().isEmpty()) {
    //             FirebaseApp.initializeApp(options);
    //             System.out.println("Firebase inicializado com sucesso!");
    //         }
            
    //     } catch (IOException e) {
    //         System.err.println("Erro crítico ao inicializar Firebase: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // 1. Procurar o arquivo no classpath (resources)
        ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
        
        if (!resource.exists()) {
            throw new IOException("Arquivo serviceAccountKey.json não encontrado em src/main/resources/");
        }

        InputStream serviceAccount = resource.getInputStream();

        // 2. Configurar as opções
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // 3. Inicializar apenas se não houver um App padrão (evita erros em Hot Reload)
        if (FirebaseApp.getApps().isEmpty()) {
            System.out.println("✅ Firebase inicializado com sucesso via @Bean!");
            return FirebaseApp.initializeApp(options);
        }
        
        return FirebaseApp.getInstance();
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        // Agora você pode injetar o FirebaseMessaging diretamente nos seus Services!
        return FirebaseMessaging.getInstance(firebaseApp);
    }

}
