package br.ufrn.imd.market.model;

import java.time.LocalDate;

import br.ufrn.imd.market.model.enums.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "A marca do produto é obrigatória")
    private String marca;

    @NotNull(message = "A data de fabricação do produto é obrigatória")
    private LocalDate dataFabricacao;

    @NotNull(message = "A data de validade do produto é obrigatória")
    private LocalDate dataValidade;

    @NotNull(message = "A categoria do produto é obrigatória")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @NotNull(message = "O preço do produto é obrigatório")
    private double preco;

    @NotBlank(message = "O lote do produto é obrigatório")
    private String lote;
    
    @Builder.Default
    private Boolean isAtivo = true;

    @Builder
    public Produto(Long id, String nome, String marca, LocalDate dataFabricacao, LocalDate dataValidade, Categoria categoria, double preco, String lote) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.categoria = categoria;
        this.preco = preco;
        this.lote = lote;
        this.isAtivo = true;
    }
}
