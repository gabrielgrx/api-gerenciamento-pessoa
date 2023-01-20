package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaRequestDTO {

    @NotBlank(message = "Você deve informar um nome")
    private String nome;

    @NotNull(message = "Você deve informar uma data de nascimento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private List<Endereco> enderecos;
}
