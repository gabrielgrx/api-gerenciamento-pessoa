package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaRequestDTO {

    @NotBlank(message = "Você deve informar um nome")
    private String nome;

    @NotNull(message = "Você deve informar uma data de nascimento no formato yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
