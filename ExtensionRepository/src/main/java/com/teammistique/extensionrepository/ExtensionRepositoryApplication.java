package com.teammistique.extensionrepository;

import com.teammistique.extensionrepository.config.FileStorageProperties;
import com.teammistique.extensionrepository.config.ImageStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class, ImageStorageProperties.class
})
public class ExtensionRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtensionRepositoryApplication.class, args);
    }
}
