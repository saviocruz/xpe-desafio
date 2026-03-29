package com.xpe.arquitetura.factory;

import java.time.LocalDateTime;

import com.xpe.arquitetura.dto.ClienteRequestDTO;
import com.xpe.arquitetura.model.Cliente;

/**
 * Factory para criação de instâncias de Cliente.
 * 
 * Esta classe implementa o padrão Factory para centralizar
 * a lógica de criação de entidades Cliente.
 */
public class ClienteFactory {

    /**
     * Cria uma nova instância de Cliente a partir de um DTO de requisição.
     * 
     * @param requestDTO DTO com os dados do cliente
     * @return Nova instância de Cliente
     */
    public static Cliente criarCliente(ClienteRequestDTO requestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(requestDTO.getNome());
        cliente.setEmail(requestDTO.getEmail());
        cliente.setTelefone(requestDTO.getTelefone());
        cliente.setEndereco(requestDTO.getEndereco());
        cliente.setDataCriacao(LocalDateTime.now());
        cliente.setDataAtualizacao(LocalDateTime.now());
        return cliente;
    }

    /**
     * Atualiza uma instância existente de Cliente a partir de um DTO de requisição.
     * 
     * @param cliente Cliente existente a ser atualizado
     * @param requestDTO DTO com os novos dados do cliente
     * @return Cliente atualizado
     */
    public static Cliente atualizarCliente(Cliente cliente, ClienteRequestDTO requestDTO) {
        if (requestDTO.getNome() != null) {
            cliente.setNome(requestDTO.getNome());
        }
        if (requestDTO.getEmail() != null) {
            cliente.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getTelefone() != null) {
            cliente.setTelefone(requestDTO.getTelefone());
        }
        if (requestDTO.getEndereco() != null) {
            cliente.setEndereco(requestDTO.getEndereco());
        }
        cliente.setDataAtualizacao(LocalDateTime.now());
        return cliente;
    }
}
