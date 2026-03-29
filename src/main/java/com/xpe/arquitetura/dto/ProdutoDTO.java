package com.xpe.arquitetura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para resposta de Produto.
 * 
 * Este DTO é utilizado para retornar dados do produto nas respostas da API,
 * ocultando detalhes internos da entidade.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private String categoria;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
