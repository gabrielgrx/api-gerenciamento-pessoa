package com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos;

import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {

    @NotNull(message = "Você deve informar a Pessoa à qual o endereço pertence")
    private Pessoa pessoa;

    @NotNull(message = "Você deve informar um logradouro")
    private String logradouro;

    @NotNull(message = "Você deve informar um CEP")
    private String cep;

    @NotNull(message = "Você deve informar o número do endereço")
    private Integer numero;

    @NotNull(message = "Você deve informar a cidade")
    private String cidade;

    @NotNull(message = "VocÊ deve informar true caso seja o endereço princpal. Caso não, insira false")
    private boolean enderecoPrincipal;
}

