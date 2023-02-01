package com.gabrielxavier.gerenciamentopessoa.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.List;

@Relation(collectionRelation = "pessoas")
@Getter
@Setter
public class PessoaResponseDTO extends RepresentationModel<PessoaResponseDTO> {

    private Long id;

    private String nomeCompleto;

    private LocalDate dataNascimento;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<Endereco> enderecos;
}
