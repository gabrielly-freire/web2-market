package br.ufrn.imd.market.model.enums;

public enum Role {
    ADMIN ("administrador"), 
    USER("usuario comum");

    private String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
