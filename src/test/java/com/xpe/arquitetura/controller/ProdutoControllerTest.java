package com.xpe.arquitetura.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes unitários para ProdutoController.
 */
@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private ObjectMapper objectMapper;
    private ProdutoDTO produtoDTO;
    private ProdutoRequestDTO produtoRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        objectMapper = new ObjectMapper();

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook Dell");
        produtoDTO.setDescricao("Notebook Dell Inspiron 15");
        produtoDTO.setPreco(new BigDecimal("4500.00"));
        produtoDTO.setQuantidade(10);
        produtoDTO.setCategoria("Eletrônicos");
        produtoDTO.setDataCriacao(LocalDateTime.now());
        produtoDTO.setDataAtualizacao(LocalDateTime.now());

        produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Notebook Dell");
        produtoRequestDTO.setDescricao("Notebook Dell Inspiron 15");
        produtoRequestDTO.setPreco(new BigDecimal("4500.00"));
        produtoRequestDTO.setQuantidade(10);
        produtoRequestDTO.setCategoria("Eletrônicos");
    }

    @Test
    void testCriarProduto_DeveRetornar201() throws Exception {
        when(produtoService.criarProduto(any(ProdutoRequestDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$.nome").value(produtoDTO.getNome()));
    }

    @Test
    void testListarTodos_DeveRetornar200() throws Exception {
        List<ProdutoDTO> produtos = Arrays.asList(produtoDTO);
        when(produtoService.listarTodos()).thenReturn(produtos);

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$[0].nome").value(produtoDTO.getNome()));
    }

    @Test
    void testBuscarPorId_DeveRetornar200_QuandoExiste() throws Exception {
        when(produtoService.buscarPorId(1L)).thenReturn(produtoDTO);

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$.nome").value(produtoDTO.getNome()));
    }

    @Test
    void testAtualizarProduto_DeveRetornar200() throws Exception {
        when(produtoService.atualizarProduto(anyLong(), any(ProdutoRequestDTO.class))).thenReturn(produtoDTO);

        mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoDTO.getId()))
                .andExpect(jsonPath("$.nome").value(produtoDTO.getNome()));
    }

    @Test
    void testDeletarProduto_DeveRetornar204() throws Exception {
        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testContarProdutos_DeveRetornar200() throws Exception {
        when(produtoService.contarProdutos()).thenReturn(10L);

        mockMvc.perform(get("/api/produtos/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
