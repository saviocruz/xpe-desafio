package com.xpe.arquitetura.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.model.Produto;
import com.xpe.arquitetura.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para Produto.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
        
        produto = new Produto();
        produto.setNome("Notebook Dell");
        produto.setDescricao("Notebook Dell Inspiron 15");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setQuantidade(10);
        produto.setCategoria("Eletrônicos");
        produto = produtoRepository.save(produto);
    }

    @Test
    @WithMockUser
    void testCriarProduto_IntegracaoCompleta() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Mouse Logitech");
        requestDTO.setDescricao("Mouse sem fio Logitech");
        requestDTO.setPreco(new BigDecimal("150.00"));
        requestDTO.setQuantidade(50);
        requestDTO.setCategoria("Acessórios");

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Mouse Logitech"))
                .andExpect(jsonPath("$.preco").value(150.00));
    }

    @Test
    @WithMockUser
    void testListarProdutos_IntegracaoCompleta() throws Exception {
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(produto.getId()))
                .andExpect(jsonPath("$[0].nome").value(produto.getNome()));
    }

    @Test
    @WithMockUser
    void testAtualizarProduto_IntegracaoCompleta() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Notebook Dell Atualizado");
        requestDTO.setDescricao("Notebook Dell Inspiron 15 - Atualizado");
        requestDTO.setPreco(new BigDecimal("4800.00"));
        requestDTO.setQuantidade(8);
        requestDTO.setCategoria("Eletrônicos");

        mockMvc.perform(put("/api/produtos/" + produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook Dell Atualizado"))
                .andExpect(jsonPath("$.preco").value(4800.00));
    }

    @Test
    @WithMockUser
    void testDeletarProduto_IntegracaoCompleta() throws Exception {
        mockMvc.perform(delete("/api/produtos/" + produto.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testBuscarPorNome_IntegracaoCompleta() throws Exception {
        mockMvc.perform(get("/api/produtos/nome/Notebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(produto.getId()))
                .andExpect(jsonPath("$[0].nome").value(produto.getNome()));
    }
}
