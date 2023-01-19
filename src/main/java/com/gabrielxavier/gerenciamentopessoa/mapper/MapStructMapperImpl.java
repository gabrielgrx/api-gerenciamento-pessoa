package com.gabrielxavier.gerenciamentopessoa.mapper;

import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.repository.EnderecoRepository;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {
    private final EnderecoRepository enderecoRepository;

    public MapStructMapperImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO) {
        if (pessoaRequestDTO == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());
        pessoa.setEnderecos(pessoaRequestDTO.getEnderecos());

        return pessoa;
    }

    @Override
    public PessoaResponseDTO pessoaToPessoaResponseDto(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();

        pessoaResponseDTO.setId(pessoa.getId());
        pessoaResponseDTO.setNome(pessoa.getNome());
        pessoaResponseDTO.setDataNascimento(pessoa.getDataNascimento());
        pessoaResponseDTO.setEnderecos(pessoa.getEnderecos());

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
        endereco.setTipoEndereco(endereco.getTipoEndereco());
        endereco.setPessoa(enderecoRequestDTO.getPessoa());

        return endereco;
    }

    @Override
    public EnderecoResponseDTO enderecoToenderecoResponseDto(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();

        enderecoResponseDTO.setLogradouro(endereco.getLogradouro());
        enderecoResponseDTO.setCep(endereco.getCep());
        enderecoResponseDTO.setNumero(endereco.getNumero());
        enderecoResponseDTO.setCidade(endereco.getCidade());
        enderecoResponseDTO.setTipoEndereco(endereco.getTipoEndereco());
        enderecoResponseDTO.setPessoa(endereco.getPessoa());

        return enderecoResponseDTO;
    }
}
