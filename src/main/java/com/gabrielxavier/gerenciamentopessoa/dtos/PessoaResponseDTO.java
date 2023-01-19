package com.gabrielxavier.gerenciamentopessoa.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PessoaResponseDTO {

    private Long id;

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private List<Endereco> enderecos;

}
