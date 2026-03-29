package com.xpe.arquitetura.config;

import com.xpe.arquitetura.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuração de segurança da aplicação.
 * 
 * Esta classe configura o Spring Security para:
 * - Desabilitar CSRF
 * - Permitir acesso ao H2 Console e Swagger
 * - Exigir autenticação para endpoints /api/**
 * - Configurar JWT filter
 * - Definir PasswordEncoder
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desabilitar CSRF
            .csrf(csrf -> csrf.disable())
            
            // Configurar sessão como stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Configurar autorização de requisições
            .authorizeHttpRequests(auth -> auth
                // Permitir acesso ao H2 Console
                .requestMatchers("/h2-console/**").permitAll()
                
                // Permitir acesso ao Swagger
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/v3/api-docs.yaml").permitAll()
                
                // Permitir acesso ao endpoint de login
                .requestMatchers("/api/auth/login").permitAll()
                
                // Exigir autenticação para endpoints /api/**
                .requestMatchers("/api/**").authenticated()
                
                // Permitir qualquer outra requisição
                .anyRequest().permitAll()
            )
            
            // Desabilitar X-Frame-Options para permitir H2 Console em iframes
            // Desabilitar HSTS para evitar que browsers forcem HTTPS automaticamente
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin().disable())
                .httpStrictTransportSecurity(hsts -> hsts.disable())
            )
            
            // Configurar exceção handling para retornar 401 em vez de 403
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\"}");
                })
            )
            
            // Adicionar JWT filter antes do UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean UserDetailsService que substitui o autoconfigurado pelo Spring Boot.
     * Este bean vazio evita que o Spring Boot crie um usuário padrão.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Criar um UserDetailsService vazio em memória
        UserDetails user = User.builder()
            .username("dummy")
            .password(passwordEncoder().encode("dummy"))
            .roles("NONE")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
