package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    

    //!IN QUESTO MODO, A SECONDA DEL PERCORSO IN CUI MI TROVO, DO I PERMESSI DI UTENTE O ADMIN
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(requests -> requests 
        .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
        .requestMatchers("/ingredients/create", "/ingredients/edit/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.POST, "/ingredients/**").hasAuthority("ADMIN")
        .requestMatchers("/specialoffers/**").hasAuthority("ADMIN")
        .requestMatchers("/pizzas", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")
        .requestMatchers("/").permitAll()
        .requestMatchers("/**").permitAll())
        .formLogin(Customizer.withDefaults());
        
        return http.build();
    }

    //*QUESTO METODO E' IL RESPONSABILE DELL'AUTENTICAZIONE
    @Bean
    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        //*Verifica l'esistenza dell'utente
        authProvider.setUserDetailsService(userDetailsService());

        //*Controlla che la password sia corretta
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DatabaseUserDetailsService userDetailsService(){
        return new DatabaseUserDetailsService();
    }

    //delega il db per il controllo della password
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
