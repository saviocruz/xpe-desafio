package com.xpe.arquitetura.adapter;

import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.model.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter Pattern para conversão entre DTOs e Entities.
 * 
 * Esta classe é responsável por converter objetos entre diferentes camadas da aplicação,
 * isolando a lógica de conversão e facilitando a manutenção.
 */
@Component
public class ProdutoAdapter {

    /**
     * Converte um ProdutoRequestDTO para uma entidade Produto.
     * 
     * @param dto DTO de requisição
     * @return Entidade Produto
     */
    public Produto toEntity(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCategoria(dto.getCategoria());
        return produto;
    }

    /**
     * Converte uma entidade Produto para um ProdutoDTO.
     * 
     * @param produto Entidade Produto
     * @return DTO de resposta
     */
    public ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        dto.setCategoria(produto.getCategoria());
        dto.setDataCriacao(produto.getDataCriacao());
        dto.setDataAtualizacao(produto.getDataAtualizacao());
        return dto;
    }

    /**
     * Converte uma lista de entidades Produto para uma lista de ProdutoDTO.
     * 
     * @param produtos Lista de entidades Produto
     * @return Lista de DTOs de resposta
     */
    public List<ProdutoDTO> toDTOList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
