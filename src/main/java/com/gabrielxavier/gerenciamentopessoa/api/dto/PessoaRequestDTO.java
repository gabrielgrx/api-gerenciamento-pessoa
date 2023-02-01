package com.gabrielxavier.gerenciamentopessoa.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaRequestDTO {

    @NotBlank(message = "Você deve informar um nome")
    @Schema(description = "Nome da Pessoa", example = "Kleber Silva")
    private String nomeCompleto;

    @NotNull(message = "Você deve informar uma data de nascimento no formato yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data de nascimento da pessoa", example = "1990-12-17")
    private LocalDate dataNascimento;
}
