package com.gabrielxavier.gerenciamentopessoa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Pattern(regexp = "^\\[0-9]{5}-[0-9]{3}$" )
    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
}
