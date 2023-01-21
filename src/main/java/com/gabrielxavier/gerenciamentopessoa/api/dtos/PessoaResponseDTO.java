package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaResponseDTO {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Endereco> enderecos;
}
