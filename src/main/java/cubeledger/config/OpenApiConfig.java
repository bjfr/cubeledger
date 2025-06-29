package cubeledger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for OpenAPI documentation.
 */
//@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation for the application.
     *
     * @return the OpenAPI configuration
     */
/*    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080ß")
                .description("Local Development Server");

        Contact contact = new Contact()
                .name("CubeLedger Team")
                .email("support@cubeledger.example.com")
                .url("https://cubeledger.example.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("CubeLedger API")
                .version("1.0.0")
                .description("API documentation for the CubeLedger bookkeeping application")
                .termsOfService("https://cubeledger.example.com/terms")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }*/
}
