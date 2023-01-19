package com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos;

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

    private LocalDate dataNascimento;

    private List<Endereco> enderecos;

}
