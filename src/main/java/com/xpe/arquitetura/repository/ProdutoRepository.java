package com.xpe.arquitetura.repository;

import com.xpe.arquitetura.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para a entidade Produto.
 * 
 * Utiliza Spring Data JPA para operações de persistência.
 * Fornece métodos CRUD básicos e queries customizadas.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca produtos pelo nome (case insensitive, parcial).
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de produtos encontrados
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    /**
     * Conta o total de produtos cadastrados.
     * 
     * @return Número total de produtos
     */
    long count();
}
