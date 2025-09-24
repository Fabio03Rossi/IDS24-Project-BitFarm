package com.github.fabio03rossi.bitfarm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                        .requestMatchers("/moderazione/getAllRichiesteAccettazioni").permitAll()
                        .requestMatchers("/moderazione/accettaArticolo").permitAll()
                        .requestMatchers("/moderazione/accettaEvento").permitAll()
                        .requestMatchers("/moderazione/rifiutaArticolo").permitAll()
                        .requestMatchers("/moderazione/rifiutaEvento").permitAll()
                        // Account
                        .requestMatchers("/account/creaUtente").permitAll()
                        .requestMatchers("/account/creaAzienda").permitAll()
                        .requestMatchers("/account/creaCuratore").permitAll()
                        .requestMatchers("/account/modificaUtente").permitAll()
                        .requestMatchers("/account/modificaAzienda").permitAll()
                        .requestMatchers("/account/modificaCuratore").permitAll()
                        .requestMatchers("/account/eliminaUtente").permitAll()
                        .requestMatchers("/account/eliminaAzienda").permitAll()
                        .requestMatchers("/account/eliminaCuratore").permitAll()
                        .requestMatchers("/account/modificaAzienda").permitAll()
                        // Acquisto
                        .requestMatchers("/acquisti/aggiungiAlCarrello").permitAll()
                        .requestMatchers("/acquisti/rimuoviDalCarrello").permitAll()
                        .requestMatchers("/acquisti/svuotaCarrello").permitAll()
                        .requestMatchers("/acquisti/acquista").permitAll()
                        .requestMatchers("/moderazione/rifiutaEvento").permitAll()
                        // Contenuti
                        .requestMatchers("/contenuti/creaArticolo").permitAll()
                        .requestMatchers("/contenuti/creaPacchetto").permitAll()
                        .requestMatchers("/contenuti/modificaArticolo").permitAll()
                        .requestMatchers("/contenuti/modificaPacchetto").permitAll()
                        .requestMatchers("/contenuti/eliminaArticolo").permitAll()
                        .requestMatchers("/contenuti/eliminaPacchetto").permitAll()
                        .requestMatchers("/contenuti/creaEvento").permitAll()
                        .requestMatchers("/contenuti/modificaEvento").permitAll()
                        .requestMatchers("/contenuti/eliminaEvento").permitAll()
                        // Verifica
                        .requestMatchers("/verifiche/getAllRichiesteAziende").permitAll()
                        .requestMatchers("/verifiche/accettaRegistrazioneAzienda").permitAll()
                        .requestMatchers("/verifiche/rifiutaRegistrazioneAzienda").permitAll()
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
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("adminpsw")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
