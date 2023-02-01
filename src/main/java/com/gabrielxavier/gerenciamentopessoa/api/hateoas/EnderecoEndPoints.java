package com.gabrielxavier.gerenciamentopessoa.api.hateoas;

import com.gabrielxavier.gerenciamentopessoa.api.controller.EnderecoController;
import com.gabrielxavier.gerenciamentopessoa.api.controller.PessoaController;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class EnderecoEndPoints {

    public static void todosLinks(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecosLink(pessoaId, enderecoResponseDTO);
        enderecoSelfLink(pessoaId, enderecoResponseDTO);
        atualizarEnderecoLink(pessoaId, enderecoResponseDTO);
        deletarEnderecoLink(pessoaId, enderecoResponseDTO);
    }

    public static void enderecosLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .listarEnderecos(pessoaId)).withRel("lista de endereços"));
    }

    public static void enderecoSelfLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(PessoaController.class)
                .slash(pessoaId + "/enderecos/" + enderecoResponseDTO.getId()).withSelfRel());
    }

    public static void deletarEnderecoLink(Long pessoaID, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .deletarEndereco(pessoaID, enderecoResponseDTO.getId())).withRel("deletar endereço"));
    }

    public static void atualizarEnderecoLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(PessoaController.class)
                .slash(pessoaId + "/enderecos/" + enderecoResponseDTO.getId()).withRel("atualizar endereço"));
    }
}
