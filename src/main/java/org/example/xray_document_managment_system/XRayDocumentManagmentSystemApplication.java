package org.example.xray_document_managment_system;

import org.example.xray_document_managment_system.Security.Configs.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RSAKeyProperties.class})
public class XRayDocumentManagmentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRayDocumentManagmentSystemApplication.class, args);
    }

}
