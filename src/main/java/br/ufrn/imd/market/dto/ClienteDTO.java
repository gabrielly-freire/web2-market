package br.ufrn.imd.market.dto;

import java.time.LocalDate;

import br.ufrn.imd.market.model.enums.Genero;

public record ClienteDTO(
                Long id,
                String nome,
                String cpf,
                Genero genero,
                LocalDate dataNascimento) {

}
