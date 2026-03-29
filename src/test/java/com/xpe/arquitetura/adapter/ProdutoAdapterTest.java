package com.xpe.arquitetura.adapter;

import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para ProdutoAdapter.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoAdapterTest {

    @InjectMocks
    private ProdutoAdapter produtoAdapter;

    private Produto produto;
    private ProdutoDTO produtoDTO;
    private ProdutoRequestDTO produtoRequestDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Dell");
        produto.setDescricao("Notebook Dell Inspiron 15");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setQuantidade(10);
        produto.setCategoria("Eletrônicos");
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook Dell");
        produtoDTO.setDescricao("Notebook Dell Inspiron 15");
        produtoDTO.setPreco(new BigDecimal("4500.00"));
        produtoDTO.setQuantidade(10);
        produtoDTO.setCategoria("Eletrônicos");
        produtoDTO.setDataCriacao(produto.getDataCriacao());
        produtoDTO.setDataAtualizacao(produto.getDataAtualizacao());

        produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Notebook Dell");
        produtoRequestDTO.setDescricao("Notebook Dell Inspiron 15");
        produtoRequestDTO.setPreco(new BigDecimal("4500.00"));
        produtoRequestDTO.setQuantidade(10);
        produtoRequestDTO.setCategoria("Eletrônicos");
    }

    @Test
    void testToEntity_DeveConverterDTOparaEntity() {
        Produto resultado = produtoAdapter.toEntity(produtoRequestDTO);

        assertNotNull(resultado);
        assertEquals(produtoRequestDTO.getNome(), resultado.getNome());
        assertEquals(produtoRequestDTO.getDescricao(), resultado.getDescricao());
        assertEquals(produtoRequestDTO.getPreco(), resultado.getPreco());
        assertEquals(produtoRequestDTO.getQuantidade(), resultado.getQuantidade());
        assertEquals(produtoRequestDTO.getCategoria(), resultado.getCategoria());
    }

    @Test
    void testToDTO_DeveConverterEntityparaDTO() {
        ProdutoDTO resultado = produtoAdapter.toDTO(produto);

        assertNotNull(resultado);
        assertEquals(produto.getId(), resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getPreco(), resultado.getPreco());
        assertEquals(produto.getQuantidade(), resultado.getQuantidade());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertEquals(produto.getDataCriacao(), resultado.getDataCriacao());
        assertEquals(produto.getDataAtualizacao(), resultado.getDataAtualizacao());
    }

    @Test
    void testToDTOList_DeveConverterListaDeEntities() {
        List<Produto> produtos = Arrays.asList(produto);

        List<ProdutoDTO> resultado = produtoAdapter.toDTOList(produtos);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produto.getId(), resultado.get(0).getId());
        assertEquals(produto.getNome(), resultado.get(0).getNome());
    }
}
