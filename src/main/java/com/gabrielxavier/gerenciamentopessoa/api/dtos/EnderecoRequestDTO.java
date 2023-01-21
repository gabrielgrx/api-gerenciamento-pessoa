package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {

    @NotBlank(message = "Você deve informar um logradouro")
    private String logradouro;

    @NotBlank(message = "Você deve informar um CEP")
    private String cep;

    @NotBlank(message = "Você deve informar o número do endereço")
    private String numero;

    @NotBlank(message = "Você deve informar a cidade")
    private String cidade;

    @NotNull(message = "Você deve informar se o endereço é o 'PRINCIPAL', ou então, o 'SECUNDARIO'")
    private TipoEndereco tipoEndereco;
}

