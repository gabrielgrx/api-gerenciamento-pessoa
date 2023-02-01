package com.gabrielxavier.gerenciamentopessoa.common.mapper;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO) {
        if (pessoaRequestDTO == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNomeCompleto(pessoaRequestDTO.getNomeCompleto());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        return pessoa;
    }

    @Override
    public PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();

        pessoaResponseDTO.setId(pessoa.getId());
        pessoaResponseDTO.setNomeCompleto(pessoa.getNomeCompleto());
        pessoaResponseDTO.setDataNascimento(pessoa.getDataNascimento());

        return pessoaResponseDTO;
    }

    @Override
    public Endereco enderecoRequestDtoToEndereco(EnderecoRequestDTO enderecoRequestDTO) {
        if (enderecoRequestDTO == null) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setLogradouro(enderecoRequestDTO.getLogradouro());
        endereco.setCep(enderecoRequestDTO.getCep());
        endereco.setNumero(enderecoRequestDTO.getNumero());
        endereco.setCidade(enderecoRequestDTO.getCidade());
        endereco.setTipoEndereco(enderecoRequestDTO.getTipoEndereco());

        return endereco;
    }

    @Override
    public EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();

        enderecoResponseDTO.setId(endereco.getId());
        enderecoResponseDTO.setLogradouro(endereco.getLogradouro());
        enderecoResponseDTO.setCep(endereco.getCep());
        enderecoResponseDTO.setNumero(endereco.getNumero());
        enderecoResponseDTO.setCidade(endereco.getCidade());
        enderecoResponseDTO.setTipoEndereco(endereco.getTipoEndereco());

        return enderecoResponseDTO;
    }
}
