package br.ufrn.imd.market.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long id,
        String codigo,
        List<Long> produtosId,
        Long clienteId
) {

} 