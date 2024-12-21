package br.ufrn.imd.market.dto;

import br.ufrn.imd.market.model.enums.Role;

public record RegisterDTO(
                String login,
                String password,
                Role role) {
}
