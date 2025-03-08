package org.example.xray_document_managment_system.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SwaggerConfiguration {
    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("zinzou@gmail.com");
        contact.setName("HIT200");
        contact.setUrl("https://localhost:8080");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("X-Ray Document Managment System")
                .version("1.0")
                .contact(contact)
                .description("Core Backend").termsOfService("https://localhost:8080")
                .license(mitLicense);

        return new OpenAPI().info(info);

    }
}
