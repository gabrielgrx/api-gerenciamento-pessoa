package com.gabrielxavier.gerenciamentopessoa.api.controller;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.service.impl.EnderecoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pessoas/{id}")
public class EnderecoController {

    @Autowired
    EnderecoServiceImpl enderecoService;

    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(@PathVariable(name = "id") Long pessoaId,
                                                                 @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.adicionarEndereco(pessoaId, enderecoRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(enderecoResponseDTO.getId()).toUri();

        todosLinks(pessoaId, enderecoResponseDTO);

        return ResponseEntity.created(uri).body(enderecoResponseDTO);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<CollectionModel<EnderecoResponseDTO>> listarEnderecos(@PathVariable(name = "id") Long pessoaId) {

        CollectionModel<EnderecoResponseDTO> enderecoResponseDTOS = enderecoService.listarTodosEnderecos(pessoaId);

        enderecoResponseDTOS.forEach(e -> enderecosLink(pessoaId, e));

        return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseDTOS);
    }

    @GetMapping("/enderecos/principal")
    public ResponseEntity<EnderecoResponseDTO> mostrarEnderecoPrincipal(@PathVariable(name = "id") Long pessoaId) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.mostrarEnderecoPrincipal(pessoaId);

        todosLinks(pessoaId, enderecoResponseDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(enderecoResponseDTO);
    }

    @PutMapping("/enderecos/{enderecoId}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable(name = "id") Long pessoaId,
                                                               @PathVariable(name = "enderecoId") Long enderecoId,
                                                               @RequestBody EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.atualizarEndereco(pessoaId, enderecoId, enderecoRequestDTO);

        enderecoSelfLink(pessoaId, enderecoResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseDTO);
    }

    @DeleteMapping("/enderecos/{enderecoId}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable(name = "id") Long pessoaId, @PathVariable(name = "enderecoId") Long enderecoId) {

        enderecoService.deletarEnderecoPorId(pessoaId, enderecoId);
        return ResponseEntity.noContent().build();
    }

    private void todosLinks(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecosLink(pessoaId, enderecoResponseDTO);
        enderecoSelfLink(pessoaId, enderecoResponseDTO);
        atualizarEnderecoLink(pessoaId, enderecoResponseDTO);
        deletarEnderecoLink(pessoaId, enderecoResponseDTO);
    }

    private void enderecosLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .listarEnderecos(pessoaId)).withRel("lista de endereços"));
    }

    private void enderecoSelfLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(PessoaController.class)
                .slash(pessoaId + "/enderecos/" + enderecoResponseDTO.getId()).withSelfRel());
    }

    private void deletarEnderecoLink(Long pessoaID, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .deletarEndereco(pessoaID, enderecoResponseDTO.getId())).withRel("deletar endereço"));
    }

    private void atualizarEnderecoLink(Long pessoaId, EnderecoResponseDTO enderecoResponseDTO) {
        enderecoResponseDTO.add(linkTo(PessoaController.class)
                .slash(pessoaId + "/enderecos/" + enderecoResponseDTO.getId()).withRel("atualizar endereço"));
    }
}
