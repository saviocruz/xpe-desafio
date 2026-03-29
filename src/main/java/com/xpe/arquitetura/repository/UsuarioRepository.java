package com.xpe.arquitetura.repository;

import com.xpe.arquitetura.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade Usuario.
 * 
 * Utiliza Spring Data JPA para operações de persistência.
 * Fornece métodos para busca de usuários por username.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo username.
     * 
     * @param username Nome de usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByUsername(String username);
}
