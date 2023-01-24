package com.gabrielxavier.gerenciamentopessoa.api.dtos;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {

    @NotBlank(message = "Você deve informar um logradouro")
    @Schema(description = "Endereço da pessoa", example = "Rua Castelo Azul")
    private String logradouro;

    @NotBlank(message = "Você deve informar um CEP")
    @Schema(description = "CEP", example = "86010-100")
    private String cep;

    @NotBlank(message = "Você deve informar o número do endereço")
    @Schema(description = "Número da residência", example = "123")
    private String numero;

    @NotBlank(message = "Você deve informar a cidade")
    @Schema(description = "Cidade", example = "Londrina")
    private String cidade;

    @NotNull(message = "Você deve informar se o endereço é o 'PRINCIPAL', ou então, o 'SECUNDARIO'")
    @Schema(description = "Se o endereço é o PRINCIPAL ou SECUNDARIO", example = "PRINCIPAL")
    private TipoEndereco tipoEndereco;
}

