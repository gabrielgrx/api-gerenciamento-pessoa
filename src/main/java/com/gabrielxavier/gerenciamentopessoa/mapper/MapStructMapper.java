package com.gabrielxavier.gerenciamentopessoa.mapper;

import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO);

    PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa);

    Endereco enderecoRequestDtoToEndereco(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco);
}
