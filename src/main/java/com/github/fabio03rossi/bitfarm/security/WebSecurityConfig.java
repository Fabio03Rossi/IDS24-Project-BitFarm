package com.github.fabio03rossi.bitfarm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // TODO: Riorganizzare correttamente richieste e permessi

    // TODO: Definire la parte di autorizzazione

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // Accettazione
                        .requestMatchers("/moderazione/**").hasAuthority("CURATORE")
                        // Account
                        .requestMatchers("/account/creaUtente").permitAll()
                        .requestMatchers("/account/creaAzienda").permitAll()
                        .requestMatchers("/account/creaCuratore").hasAuthority("GESTORE")
                        .requestMatchers("/account/modificaUtente/**").hasAuthority("UTENTE")
                        .requestMatchers("/account/modificaAzienda/**").hasAuthority("AZIENDA")
                        .requestMatchers("/account/modificaCuratore/**").hasAuthority("CURATORE")
                        .requestMatchers("/account/eliminaUtente/**").hasAuthority("UTENTE")
                        .requestMatchers("/account/eliminaAzienda/**").hasAuthority("AZIENDA")
                        .requestMatchers("/account/eliminaCuratore/**").hasAuthority("GESTORE")
                        // Acquisto
                        .requestMatchers("/acquisti/**").hasAuthority("UTENTE")
                        // Contenuti
                        .requestMatchers("/contenuti/**").hasAuthority("AZIENDA")
                        // Verifica
                        .requestMatchers("/verifiche/**").hasAuthority("GESTORE")

                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails utente = new User("utente", "utente", Collections.singleton(new SimpleGrantedAuthority("UTENTE")));
        UserDetails azienda = new User("azienda", "azienda", Collections.singleton(new SimpleGrantedAuthority("AZIENDA")));
        UserDetails curatore = new User("curatore", "curatore", Collections.singleton(new SimpleGrantedAuthority("CURATORE")));
        UserDetails gestore = new User("gestore", "gestore", Collections.singleton(new SimpleGrantedAuthority("GESTORE")));

        return new InMemoryUserDetailsManager(utente, azienda, curatore, gestore);
    }
}
