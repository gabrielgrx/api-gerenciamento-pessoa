package com.gabrielxavier.gerenciamentopessoa.dtos;

import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.entity.enums.TipoEndereco;
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

    private TipoEndereco tipoEndereco;

    private Pessoa pessoa;
}
