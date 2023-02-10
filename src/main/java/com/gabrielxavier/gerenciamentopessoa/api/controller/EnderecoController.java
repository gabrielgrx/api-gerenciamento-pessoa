package com.gabrielxavier.gerenciamentopessoa.api.controller;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.hateoas.EnderecoEndPoints;
import com.gabrielxavier.gerenciamentopessoa.domain.service.EnderecoService;
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
    EnderecoService enderecoService;

    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(@PathVariable(name = "id") Long pessoaId,
                                                                 @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.adicionarEndereco(pessoaId, enderecoRequestDTO);

        EnderecoEndPoints.todosLinks(pessoaId, enderecoResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoResponseDTO);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<CollectionModel<EnderecoResponseDTO>> listarEnderecos(@PathVariable(name = "id") Long pessoaId) {

        CollectionModel<EnderecoResponseDTO> enderecoResponseDTOS = enderecoService.listarTodosEnderecos(pessoaId);

        enderecoResponseDTOS.forEach(e -> EnderecoEndPoints.enderecosLink(pessoaId, e));

        return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseDTOS);
    }

    @GetMapping("/enderecos/principal")
    public ResponseEntity<EnderecoResponseDTO> mostrarEnderecoPrincipal(@PathVariable(name = "id") Long pessoaId) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.mostrarEnderecoPrincipal(pessoaId);

        EnderecoEndPoints.todosLinks(pessoaId, enderecoResponseDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(enderecoResponseDTO);
    }

    @PutMapping("/enderecos/{enderecoId}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable(name = "id") Long pessoaId,
                                                                 @PathVariable(name = "enderecoId") Long enderecoId,
                                                                 @RequestBody EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.atualizarEndereco(pessoaId, enderecoId, enderecoRequestDTO);

        EnderecoEndPoints.enderecoSelfLink(pessoaId, enderecoResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseDTO);
    }

    @DeleteMapping("/enderecos/{enderecoId}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable(name = "id") Long pessoaId, @PathVariable(name = "enderecoId") Long enderecoId) {

        enderecoService.deletarEnderecoPorId(pessoaId, enderecoId);
        return ResponseEntity.noContent().build();
    }
}
