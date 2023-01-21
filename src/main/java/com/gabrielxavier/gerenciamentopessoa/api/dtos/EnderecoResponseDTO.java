package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseDTO {

    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;

    private TipoEndereco tipoEndereco;
}
