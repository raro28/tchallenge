package mx.ekthor.challenge.rest.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge API")
                        .version("0.0.0")
                        .description("Defines the endpoints and operations for the challenge. See [Github repository](https://github.com/raro28/tchallenge).")
                        .contact(new Contact()
                                .name("Héctor Díaz")
                                .url("https://github.com/raro28")
                        )
                )
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("spring-boot server")
                );
    }
}
