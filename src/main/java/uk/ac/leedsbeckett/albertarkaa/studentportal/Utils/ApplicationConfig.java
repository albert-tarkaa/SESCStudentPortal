package uk.ac.leedsbeckett.albertarkaa.studentportal.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.ac.leedsbeckett.albertarkaa.studentportal.Repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

private final UserRepository UserRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> UserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
