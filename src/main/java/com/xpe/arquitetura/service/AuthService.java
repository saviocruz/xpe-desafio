package com.xpe.arquitetura.service;

import com.xpe.arquitetura.dto.LoginRequestDTO;
import com.xpe.arquitetura.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço de autenticação.
 * 
 * Este serviço gerencia a autenticação de usuários
 * e geração de tokens JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // Usuários hardcoded para demonstração
    private static final Map<String, String> USERS = new HashMap<>();
    
    static {
        // Senhas já codificadas com BCrypt
        USERS.put("admin", "$2a$10$ex9iadcFR2CJ7QVZdFHYEOgHAtvXcXmtuM/GxC9hNezo06.XsPOdq"); // admin123
        USERS.put("user", "$2a$10$NoFBjS14D43jIX5w3IOvpuI8u4uomzvgyfdFaSYPC/OawGAgOcRW6"); // user123
    }

    /**
     * Autentica um usuário e retorna um token JWT.
     * 
     * @param loginRequest DTO com credenciais de login
     * @return Token JWT
     * @throws org.springframework.security.authentication.BadCredentialsException se as credenciais forem inválidas
     */
    public String login(LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Verificar se o usuário existe
        if (!USERS.containsKey(username)) {
            throw new org.springframework.security.authentication.BadCredentialsException("Credenciais inválidas");
        }

        // Verificar se a senha está correta
        String encodedPassword = USERS.get(username);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new org.springframework.security.authentication.BadCredentialsException("Credenciais inválidas");
        }

        // Gerar e retornar token JWT
        return jwtTokenProvider.generateToken(username);
    }
}
