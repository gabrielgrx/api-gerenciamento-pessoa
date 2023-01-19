package com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos;

import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaRequestDTO {

    @NotNull(message = "Você deve informar um nome")
    private String nome;

    @NotNull(message = "Você deve informar uma data de nascimento")
    private LocalDate dataNascimento;

    private List<Endereco> enderecos;
}
