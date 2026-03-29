package com.xpe.arquitetura.service;

import com.xpe.arquitetura.adapter.ProdutoAdapter;
import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.exception.ResourceNotFoundException;
import com.xpe.arquitetura.factory.ProdutoFactory;
import com.xpe.arquitetura.model.Produto;
import com.xpe.arquitetura.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Facade Pattern para operações de Produto.
 * 
 * Esta classe orquestra as operações complexas de Produto,
 * simplificando a interação entre as camadas da aplicação.
 * Utiliza Factory para criação de objetos e Adapter para conversão de DTOs.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoFactory produtoFactory;
    private final ProdutoAdapter produtoAdapter;

    /**
     * Cria um novo produto.
     * 
     * @param dto DTO com os dados do produto
     * @return DTO do produto criado
     */
    @Transactional
    public ProdutoDTO criarProduto(ProdutoRequestDTO dto) {
        Produto produto = produtoFactory.criarProduto(dto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoAdapter.toDTO(produtoSalvo);
    }

    /**
     * Lista todos os produtos cadastrados.
     * 
     * @return Lista de DTOs dos produtos
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoAdapter.toDTOList(produtos);
    }

    /**
     * Busca um produto pelo ID.
     * 
     * @param id ID do produto
     * @return DTO do produto encontrado
     * @throws ResourceNotFoundException se o produto não for encontrado
     */
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        return produtoAdapter.toDTO(produto);
    }

    /**
     * Busca produtos pelo nome (parcial, case insensitive).
     * 
     * @param nome Nome ou parte do nome do produto
     * @return Lista de DTOs dos produtos encontrados
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtoAdapter.toDTOList(produtos);
    }

    /**
     * Atualiza um produto existente.
     * 
     * @param id ID do produto a ser atualizado
     * @param dto DTO com os novos dados do produto
     * @return DTO do produto atualizado
     * @throws ResourceNotFoundException se o produto não for encontrado
     */
    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
        Produto produtoAtualizado = produtoFactory.atualizarProduto(produto, dto);
        Produto produtoSalvo = produtoRepository.save(produtoAtualizado);
        return produtoAdapter.toDTO(produtoSalvo);
    }

    /**
     * Deleta um produto pelo ID.
     * 
     * @param id ID do produto a ser deletado
     * @throws ResourceNotFoundException se o produto não for encontrado
     */
    @Transactional
    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }
        produtoRepository.deleteById(id);
    }

    /**
     * Conta o total de produtos cadastrados.
     * 
     * @return Número total de produtos
     */
    @Transactional(readOnly = true)
    public long contarProdutos() {
        return produtoRepository.count();
    }
}
