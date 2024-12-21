package br.ufrn.imd.market.dto;

import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String codigo,
        List<ProdutoDTO> produtos,
        ClienteDTO cliente) {

}
