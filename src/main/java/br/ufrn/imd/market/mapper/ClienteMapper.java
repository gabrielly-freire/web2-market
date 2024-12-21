package br.ufrn.imd.market.mapper;

import org.springframework.stereotype.Component;

import br.ufrn.imd.market.dto.ClienteDTO;
import br.ufrn.imd.market.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente entity) {
        return new ClienteDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getGenero(),
                entity.getDataNascimento());
    }

    public Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                dto.id(),
                dto.nome(),
                dto.cpf(),
                dto.genero(),
                dto.dataNascimento());
    }
}
