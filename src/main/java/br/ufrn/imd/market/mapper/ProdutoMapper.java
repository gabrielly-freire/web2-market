package br.ufrn.imd.market.mapper;

import org.springframework.stereotype.Component;

import br.ufrn.imd.market.dto.ProdutoDTO;
import br.ufrn.imd.market.model.Produto;

@Component
public class ProdutoMapper {

    public ProdutoDTO toDTO(Produto entity) {
        return new ProdutoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getMarca(),
                entity.getDataFabricacao(),
                entity.getDataValidade(), entity.getCategoria(),
                entity.getPreco(),
                entity.getLote());
    }

    public Produto toEntity(ProdutoDTO dto) {
        return new Produto(
                dto.id(),
                dto.nome(),
                dto.marca(),
                dto.dataFabricacao(),
                dto.dataValidade(),
                dto.categoria(),
                dto.preco(),
                dto.lote());
    }
}
