package br.ufrn.imd.market.dto;

import java.time.LocalDate;

import br.ufrn.imd.market.model.enums.Categoria;

public record ProdutoDTO(
                Long id,
                String nome,
                String marca,
                LocalDate dataFabricacao,
                LocalDate dataValidade,
                Categoria categoria,
                Double preco,
                String lote) {
}
