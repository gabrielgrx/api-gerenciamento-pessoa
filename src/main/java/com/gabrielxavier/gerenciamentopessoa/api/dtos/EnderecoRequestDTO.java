package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
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

    @NotNull(message = "Você deve informar se o email é o 'PRINCIPAL', ou então, o 'SECUNDARIO'")
    private TipoEndereco tipoEndereco;
}

