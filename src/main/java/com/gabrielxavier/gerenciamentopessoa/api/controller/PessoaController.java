package com.gabrielxavier.gerenciamentopessoa.api.controller;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.service.impl.PessoaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaServiceImpl pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> adicionarPessoa(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.adicionarPessoa(pessoaRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(pessoaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PessoaResponseDTO>> listarPessoas() {

        CollectionModel<PessoaResponseDTO> pessoaResponseDTOS = pessoaService.listarPessoas();
        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pessoaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.atualizarPessoa(id, pessoaRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        pessoaService.deletarPessoaPorId(id);
        return ResponseEntity.noContent().build();
    }
}
