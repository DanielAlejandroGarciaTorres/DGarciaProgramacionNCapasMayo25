package com.digis01.DGarciaProgramacionNCapasMayo25Maven.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    //SecurityFilterChain
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        httpSecurity.authorizeHttpRequests(
        configure -> configure
                //hasAnyRole -> ROLE_Programador 
                //HasAnyAuthority -> Programador
                .requestMatchers( "/alumno/**").hasAnyAuthority("PROGRAMADOR")
                .anyRequest().authenticated()
                
        );
        //formLogin
        
        return httpSecurity.build();
    }
        @Bean
        public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
            
            UserDetails programador = User.builder()
                    .password("password")
                    .username("pepito")
                    .roles("PROGRAMADOR")
                    .build();
            //passwordEncoder
            //DelegatingPasswordEncoder
            return new InMemoryUserDetailsManager(programador);
            
            //3 Usuarios, Programador, Administrador, Analista
            //Programador admin, puede hacer todo
            //Administtador -> Solo puede hacer la carga masiva
            //Analista -> Solo puede hacer peticiones GET
        }
    
}
