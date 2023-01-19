package com.gabrielxavier.gerenciamentopessoa.mapstruct.mappers;

import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.mapstruct.dtos.PessoaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO);

    PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa);

    Endereco enderecoRequestDtoToEndereco(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco);
}
