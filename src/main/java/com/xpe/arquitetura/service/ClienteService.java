package com.xpe.arquitetura.service;

import com.xpe.arquitetura.dto.ClienteDTO;
import com.xpe.arquitetura.dto.ClienteRequestDTO;
import com.xpe.arquitetura.exception.BusinessException;
import com.xpe.arquitetura.exception.ClienteNaoEncontradoException;
import com.xpe.arquitetura.model.Cliente;
import com.xpe.arquitetura.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio relacionadas a Clientes.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Cria um novo cliente.
     * 
     * @param request DTO com dados do cliente
     * @return DTO do cliente criado
     */
    public ClienteDTO criar(ClienteRequestDTO request) {
        // Verificar se já existe cliente com o mesmo email
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe um cliente com o email: " + request.getEmail());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(request.getEndereco());

        Cliente salvo = clienteRepository.save(cliente);
        return toDTO(salvo);
    }

    /**
     * Busca todos os clientes.
     * 
     * @return Lista de DTOs de clientes
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo ID.
     * 
     * @param id ID do cliente
     * @return DTO do cliente
     */
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return toDTO(cliente);
    }

    /**
     * Busca clientes pelo nome.
     * 
     * @param nome Nome a ser buscado
     * @return Lista de DTOs de clientes
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo email.
     * 
     * @param email Email do cliente
     * @return DTO do cliente
     */
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ClienteNaoEncontradoException(email));
        return toDTO(cliente);
    }

    /**
     * Atualiza um cliente.
     * 
     * @param id ID do cliente a ser atualizado
     * @param request DTO com novos dados
     * @return DTO do cliente atualizado
     */
    public ClienteDTO atualizar(Long id, ClienteRequestDTO request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        // Verificar se o novo email já está em uso por outro cliente
        if (!cliente.getEmail().equals(request.getEmail()) && 
                clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe um cliente com o email: " + request.getEmail());
        }

        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(request.getEndereco());

        Cliente atualizado = clienteRepository.save(cliente);
        return toDTO(atualizado);
    }

    /**
     * Remove um cliente.
     * 
     * @param id ID do cliente a ser removido
     */
    public void remover(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException(id);
        }
        clienteRepository.deleteById(id);
    }

    /**
     * Conta o total de clientes.
     * 
     * @return Número total de clientes
     */
    @Transactional(readOnly = true)
    public long contar() {
        return clienteRepository.countClientes();
    }

    /**
     * Converte entidade Cliente para DTO.
     */
    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setEndereco(cliente.getEndereco());
        dto.setDataCriacao(cliente.getDataCriacao());
        dto.setDataAtualizacao(cliente.getDataAtualizacao());
        return dto;
    }
}
