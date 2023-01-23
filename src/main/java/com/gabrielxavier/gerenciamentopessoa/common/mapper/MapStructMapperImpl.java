package com.gabrielxavier.gerenciamentopessoa.common.mapper;

import com.gabrielxavier.gerenciamentopessoa.api.controller.EnderecoController;
import com.gabrielxavier.gerenciamentopessoa.api.controller.PessoaController;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public Pessoa pessoaRequestDtoToPessoa(PessoaRequestDTO pessoaRequestDTO) {
        if (pessoaRequestDTO == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(pessoaRequestDTO.getNome());
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
        pessoaResponseDTO.setNome(pessoa.getNome());
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
//
//        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
//                .listarEnderecos(endereco.getPessoa().getId()))
//                .withRel("lista de endereços"));
//
//        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
//                .deletarEndereco(endereco.getPessoa().getId(), endereco.getId()))
//                .withRel("deletar endereço"));

        return enderecoResponseDTO;
    }
}
