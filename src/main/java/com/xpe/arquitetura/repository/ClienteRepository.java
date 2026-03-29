package com.xpe.arquitetura.repository;

import com.xpe.arquitetura.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência de Cliente.
 * 
 * Estende JpaRepository para fornecer operações CRUD básicas
 * e adiciona métodos de consulta customizados.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca clientes cujo nome contenha o texto informado (case insensitive).
     * 
     * @param nome Texto a ser buscado no nome
     * @return Lista de clientes que contêm o texto no nome
     */
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca um cliente pelo email.
     * 
     * @param email Email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Verifica se existe um cliente com o email informado.
     * 
     * @param email Email a ser verificado
     * @return true se existir um cliente com o email
     */
    boolean existsByEmail(String email);

    /**
     * Conta quantos clientes existem no sistema.
     * 
     * @return Número total de clientes
     */
    @Query("SELECT COUNT(c) FROM Cliente c")
    long countClientes();
}
