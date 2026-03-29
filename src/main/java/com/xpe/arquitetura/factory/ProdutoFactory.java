package com.xpe.arquitetura.factory;

import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.model.Produto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory Pattern para criação de objetos Produto.
 * 
 * Esta classe é responsável por criar instâncias de Produto de forma controlada,
 * garantindo que os objetos sejam criados corretamente com os timestamps apropriados.
 */
@Component
public class ProdutoFactory {

    /**
     * Cria uma nova instância de Produto a partir de um DTO de requisição.
     * 
     * @param dto DTO com os dados do produto
     * @return Nova instância de Produto
     */
    public Produto criarProduto(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCategoria(dto.getCategoria());
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());
        return produto;
    }

    /**
     * Atualiza uma instância existente de Produto com dados de um DTO de requisição.
     * 
     * @param produto Produto existente a ser atualizado
     * @param dto DTO com os novos dados do produto
     * @return Produto atualizado
     */
    public Produto atualizarProduto(Produto produto, ProdutoRequestDTO dto) {
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCategoria(dto.getCategoria());
        produto.setDataAtualizacao(LocalDateTime.now());
        return produto;
    }
}
