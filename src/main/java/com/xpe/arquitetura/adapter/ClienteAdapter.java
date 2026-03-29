package com.xpe.arquitetura.adapter;

import com.xpe.arquitetura.dto.ClienteDTO;
import com.xpe.arquitetura.dto.ClienteRequestDTO;
import com.xpe.arquitetura.model.Cliente;

/**
 * Adapter para conversão entre Cliente e seus DTOs.
 * 
 * Esta classe implementa o padrão Adapter para centralizar
 * a lógica de conversão entre entidades e DTOs de Cliente.
 */
public class ClienteAdapter {

    /**
     * Converte uma entidade Cliente para ClienteDTO.
     * 
     * @param cliente Entidade Cliente
     * @return ClienteDTO com os dados do cliente
     */
    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
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

    /**
     * Converte um ClienteRequestDTO para atualizar uma entidade Cliente.
     * 
     * @param requestDTO DTO de requisição
     * @param cliente Entidade Cliente a ser atualizada
     */
    public static void toEntity(ClienteRequestDTO requestDTO, Cliente cliente) {
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
    }
}
