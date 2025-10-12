package by.faas.billing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${server.domain}") String domain,
                                 @Value("${server.servlet.context-path}") String contextPath) {
        Server server = new Server();
        server.setUrl(domain + contextPath);
        return new OpenAPI().servers(List.of(server));
    }
}
