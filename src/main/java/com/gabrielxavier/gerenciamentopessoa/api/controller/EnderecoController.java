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

@RestController
@RequestMapping("/pessoas/{id}")
public class EnderecoController {

    @Autowired
    EnderecoServiceImpl enderecoService;

    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(@PathVariable(name = "id") Long pessoaId, @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {
        EnderecoResponseDTO enderecoResponseDTO = enderecoService.adicionarEndereco(pessoaId, enderecoRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(enderecoResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(enderecoResponseDTO);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<CollectionModel<EnderecoResponseDTO>> listarEnderecos(@PathVariable(name = "id") Long PessoaId) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.listarTodosEnderecos(PessoaId));
    }

    @GetMapping("/enderecos/principal")
    public ResponseEntity<EnderecoResponseDTO> mostrarEnderecoPrincipal(@PathVariable(name = "id") Long pessoaId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(enderecoService.mostrarEnderecoPrincipal(pessoaId));
    }

    @DeleteMapping("/enderecos/{enderecoId}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable(name = "id") Long pessoaId,@PathVariable(name = "enderecoId") Long enderecoId) {
        enderecoService.deletarEnderecoPorId(pessoaId, enderecoId);
        return ResponseEntity.noContent().build();
    }
}
