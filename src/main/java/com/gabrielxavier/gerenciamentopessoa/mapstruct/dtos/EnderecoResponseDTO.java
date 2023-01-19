package com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos;

import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseDTO {

    private Long id;

    private String logradouro;

    private String cep;

    private Integer numero;

    private String cidade;

    private boolean enderecoPrincipal;

    private Pessoa pessoa;
}
