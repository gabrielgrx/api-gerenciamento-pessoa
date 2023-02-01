package com.gabrielxavier.gerenciamentopessoa.common.mapper;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO);

    PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa);

    Endereco enderecoRequestDtoToEndereco(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco);
}
