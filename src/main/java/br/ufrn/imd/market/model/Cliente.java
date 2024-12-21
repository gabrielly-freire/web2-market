package br.ufrn.imd.market.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import br.ufrn.imd.market.model.enums.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Builder
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @Column(name = "cpf",unique = true)
    @NotBlank(message = "O CPF do cliente é obrigatório")
    @CPF(message = "O CPF informado é inválido")  
    private String cpf;

    @NotNull(message = "O gênero do cliente é obrigatório")
    private Genero genero;

    @NotNull(message = "A data de nascimento do cliente é obrigatória")
    private LocalDate dataNascimento;
    
    @Builder.Default
    private Boolean isAtivo = true;

    @Builder
    public Cliente(Long id, String nome, String cpf, Genero genero, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.isAtivo = true;
    }
}
