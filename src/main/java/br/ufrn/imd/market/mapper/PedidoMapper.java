package br.ufrn.imd.market.mapper;

import org.springframework.stereotype.Component;

import br.ufrn.imd.market.dto.PedidoRequestDTO;
import br.ufrn.imd.market.dto.PedidoResponseDTO;
import br.ufrn.imd.market.model.Pedido;

@Component
public class PedidoMapper {

    private final ClienteMapper clienteMapper;
    private final ProdutoMapper produtoMapper;

    public PedidoMapper(ClienteMapper clienteMapper, ProdutoMapper produtoMapper) {
        this.clienteMapper = clienteMapper;
        this.produtoMapper = produtoMapper;
    }

    public Pedido toEntity(PedidoResponseDTO dto) {
        return new Pedido(
                dto.id(),
                dto.codigo(),
                dto.produtos().stream().map(produtoMapper::toEntity).toList(),
                clienteMapper.toEntity(dto.cliente()));
    }

    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido entity = new Pedido();

        entity.setId(dto.id());
        entity.setCodigo(dto.codigo());
        return entity;
    }

    public PedidoResponseDTO toDTO(Pedido entity) {
        return new PedidoResponseDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getProdutos().stream().map(produtoMapper::toDTO).toList(),
                clienteMapper.toDTO(entity.getCliente()));
    }    
}
