package com.gabrielxavier.gerenciamentopessoa.api.hateoas;

import com.gabrielxavier.gerenciamentopessoa.api.controller.EnderecoController;
import com.gabrielxavier.gerenciamentopessoa.api.controller.PessoaController;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class PessoaEndPoints {

    public static void pessoaSelfLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).buscarPorId(pessoaResponseDTO.getId())).withSelfRel());
    }

    public static void pessoasLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).listarPessoas()).withRel("lista de pessoas"));
    }

    public static void pessoaAtualizarLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(PessoaController.class).slash(pessoaResponseDTO.getId()).withRel("Atualizar Pessoa"));
    }

    public static void pessoaDeletarLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).deletarPessoa(pessoaResponseDTO.getId())).withRel("DeletarPessoa"));
    }

    public static void enderecosLinks(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .listarEnderecos(pessoaResponseDTO.getId())).withRel("lista de endereços desta pessoa"));
    }

    public static void enderecoPrincipalLink(PessoaResponseDTO pessoaResponseDTO) {
        List<Endereco> enderecos = pessoaResponseDTO.getEnderecos();
        if (enderecos != null) {
            for (Endereco e : enderecos) {
                if (e.getTipoEndereco() == TipoEndereco.PRINCIPAL) {
                    pessoaResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                            .mostrarEnderecoPrincipal(pessoaResponseDTO.getId()))
                            .withRel("endereço principal"));
                }
            }
        }
    }

    public static void todosLinks(PessoaResponseDTO pessoaResponseDTO) {
        pessoasLink(pessoaResponseDTO);
        pessoaSelfLink(pessoaResponseDTO);
        pessoaAtualizarLink(pessoaResponseDTO);
        pessoaDeletarLink(pessoaResponseDTO);
        enderecoPrincipalLink(pessoaResponseDTO);
        enderecosLinks(pessoaResponseDTO);
    }
}
