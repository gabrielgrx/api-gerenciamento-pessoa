package com.gabrielxavier.gerenciamentopessoa.api.dto;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "endereços")
@Getter
@Setter
public class EnderecoResponseDTO extends RepresentationModel<EnderecoResponseDTO> {

    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;

    private TipoEndereco tipoEndereco;
}
