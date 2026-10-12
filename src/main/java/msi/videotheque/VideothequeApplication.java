package msi.videotheque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "API Vidéothèque",
        version = "1.0",
        description = "Gestion des films, catégories et acteurs (labo MSI Spring Boot)"))
@SpringBootApplication
public class VideothequeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideothequeApplication.class, args);
    }
}