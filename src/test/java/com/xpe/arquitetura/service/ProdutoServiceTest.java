package com.xpe.arquitetura.service;

import com.xpe.arquitetura.adapter.ProdutoAdapter;
import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.exception.ResourceNotFoundException;
import com.xpe.arquitetura.factory.ProdutoFactory;
import com.xpe.arquitetura.model.Produto;
import com.xpe.arquitetura.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ProdutoService.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoFactory produtoFactory;

    @Mock
    private ProdutoAdapter produtoAdapter;

    @InjectMocks
    private ProdutoService produtoService;

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
    void testCriarProduto_DeveRetornarProdutoDTO() {
        when(produtoFactory.criarProduto(any(ProdutoRequestDTO.class))).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoAdapter.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.criarProduto(produtoRequestDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO.getId(), resultado.getId());
        assertEquals(produtoDTO.getNome(), resultado.getNome());
        verify(produtoFactory, times(1)).criarProduto(any(ProdutoRequestDTO.class));
        verify(produtoRepository, times(1)).save(any(Produto.class));
        verify(produtoAdapter, times(1)).toDTO(any(Produto.class));
    }

    @Test
    void testListarTodos_DeveRetornarListaDeProdutos() {
        List<Produto> produtos = Arrays.asList(produto);
        List<ProdutoDTO> produtosDTO = Arrays.asList(produtoDTO);

        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoAdapter.toDTOList(anyList())).thenReturn(produtosDTO);

        List<ProdutoDTO> resultado = produtoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoRepository, times(1)).findAll();
        verify(produtoAdapter, times(1)).toDTOList(anyList());
    }

    @Test
    void testBuscarPorId_DeveRetornarProduto_QuandoExiste() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoAdapter.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(produtoDTO.getId(), resultado.getId());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoAdapter, times(1)).toDTO(any(Produto.class));
    }

    @Test
    void testBuscarPorId_DeveLancarExcecao_QuandoNaoExiste() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtoService.buscarPorId(99L));
        verify(produtoRepository, times(1)).findById(99L);
    }

    @Test
    void testAtualizarProduto_DeveRetornarProdutoAtualizado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoFactory.atualizarProduto(any(Produto.class), any(ProdutoRequestDTO.class))).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoAdapter.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoService.atualizarProduto(1L, produtoRequestDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO.getId(), resultado.getId());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoFactory, times(1)).atualizarProduto(any(Produto.class), any(ProdutoRequestDTO.class));
        verify(produtoRepository, times(1)).save(any(Produto.class));
        verify(produtoAdapter, times(1)).toDTO(any(Produto.class));
    }

    @Test
    void testDeletarProduto_DeveRemoverProduto() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletarProduto(1L);

        verify(produtoRepository, times(1)).existsById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
