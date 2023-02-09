package com.gabrielxavier.gerenciamentopessoa.util;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;

public abstract class EnderecoCreator {

    public static EnderecoRequestDTO criarEnderecoRequestDto1() {
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setLogradouro("Rua verde");
        enderecoRequestDTO.setNumero("123");
        enderecoRequestDTO.setCep("80808-900");
        enderecoRequestDTO.setCidade("Londrina");
        enderecoRequestDTO.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return enderecoRequestDTO;
    }

    public static EnderecoRequestDTO criarEnderecoRequestDtoCEPInvalido() {
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setLogradouro("Rua verde");
        enderecoRequestDTO.setNumero("123");
        enderecoRequestDTO.setCep("808-90");
        enderecoRequestDTO.setCidade("Londrina");
        enderecoRequestDTO.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return enderecoRequestDTO;
    }

    public static EnderecoRequestDTO atualizarEnderecoRequestDto1() {
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
        enderecoRequestDTO.setLogradouro("Rua azul");
        enderecoRequestDTO.setNumero("988");
        enderecoRequestDTO.setCep("12345-678");
        enderecoRequestDTO.setCidade("São Paulo");
        enderecoRequestDTO.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return enderecoRequestDTO;
    }

    public static EnderecoResponseDTO atualizarEnderecoResponseDto1() {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setId(1L);
        enderecoResponseDTO.setLogradouro("Rua azul");
        enderecoResponseDTO.setNumero("988");
        enderecoResponseDTO.setCep("12345-678");
        enderecoResponseDTO.setCidade("São Paulo");
        enderecoResponseDTO.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return enderecoResponseDTO;
    }

    public static EnderecoResponseDTO criarEnderecoResponseDto1() {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setId(1L);
        enderecoResponseDTO.setLogradouro("Rua verde");
        enderecoResponseDTO.setNumero("123");
        enderecoResponseDTO.setCep("80808-900");
        enderecoResponseDTO.setCidade("Londrina");
        enderecoResponseDTO.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return enderecoResponseDTO;
    }

    public static EnderecoResponseDTO criarEnderecoResponseDto2() {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setId(2L);
        enderecoResponseDTO.setLogradouro("Rua Amarela");
        enderecoResponseDTO.setNumero("444");
        enderecoResponseDTO.setCep("90909-900");
        enderecoResponseDTO.setCidade("Cambé");
        enderecoResponseDTO.setTipoEndereco(TipoEndereco.SECUNDARIO);
        return enderecoResponseDTO;
    }

    public static Endereco criarEndereco1() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setLogradouro("Rua verde");
        endereco.setNumero("123");
        endereco.setCep("80808-900");
        endereco.setCidade("Londrina");
        endereco.setTipoEndereco(TipoEndereco.PRINCIPAL);
        return endereco;
    }

    public static Endereco criarEndereco2() {
        Endereco endereco = new Endereco();
        endereco.setId(2L);
        endereco.setLogradouro("Rua Amarela");
        endereco.setNumero("444");
        endereco.setCep("90909-900");
        endereco.setCidade("Cambé");
        endereco.setTipoEndereco(TipoEndereco.SECUNDARIO);
        return endereco;
    }
}
