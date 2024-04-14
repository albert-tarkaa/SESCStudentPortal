package uk.ac.leedsbeckett.albertarkaa.studentportal.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json")
                .filter((request, next) -> next.exchange(request)
                        .flatMap(response -> {
                            if (!response.statusCode().is2xxSuccessful()) {
                                return Mono.error(new RuntimeException("Failed : HTTP error code : " + response.statusCode()));
                            }
                            return Mono.just(response);
                        }))
                .build();
    }

}
