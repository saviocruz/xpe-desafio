package com.xpe.arquitetura.factory;

import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para ProdutoFactory.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoFactoryTest {

    @InjectMocks
    private ProdutoFactory produtoFactory;

    private ProdutoRequestDTO produtoRequestDTO;
    private Produto produto;

    @BeforeEach
    void setUp() {
        produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Notebook Dell");
        produtoRequestDTO.setDescricao("Notebook Dell Inspiron 15");
        produtoRequestDTO.setPreco(new BigDecimal("4500.00"));
        produtoRequestDTO.setQuantidade(10);
        produtoRequestDTO.setCategoria("Eletrônicos");

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Dell");
        produto.setDescricao("Notebook Dell Inspiron 15");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setQuantidade(10);
        produto.setCategoria("Eletrônicos");
    }

    @Test
    void testCriarProduto_DeveCriarEntityComTimestamps() {
        Produto resultado = produtoFactory.criarProduto(produtoRequestDTO);

        assertNotNull(resultado);
        assertEquals(produtoRequestDTO.getNome(), resultado.getNome());
        assertEquals(produtoRequestDTO.getDescricao(), resultado.getDescricao());
        assertEquals(produtoRequestDTO.getPreco(), resultado.getPreco());
        assertEquals(produtoRequestDTO.getQuantidade(), resultado.getQuantidade());
        assertEquals(produtoRequestDTO.getCategoria(), resultado.getCategoria());
        assertNotNull(resultado.getDataCriacao());
        assertNotNull(resultado.getDataAtualizacao());
    }

    @Test
    void testAtualizarProduto_DeveAtualizarEntity() {
        Produto resultado = produtoFactory.atualizarProduto(produto, produtoRequestDTO);

        assertNotNull(resultado);
        assertEquals(produtoRequestDTO.getNome(), resultado.getNome());
        assertEquals(produtoRequestDTO.getDescricao(), resultado.getDescricao());
        assertEquals(produtoRequestDTO.getPreco(), resultado.getPreco());
        assertEquals(produtoRequestDTO.getQuantidade(), resultado.getQuantidade());
        assertEquals(produtoRequestDTO.getCategoria(), resultado.getCategoria());
        assertNotNull(resultado.getDataAtualizacao());
    }
}
