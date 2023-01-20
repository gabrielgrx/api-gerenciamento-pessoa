package com.gabrielxavier.gerenciamentopessoa.common.mapper;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO);

    PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa);

    Endereco enderecoRequestDtoToEndereco(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco);
}
