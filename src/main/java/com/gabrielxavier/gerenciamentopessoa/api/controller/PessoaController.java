package com.gabrielxavier.gerenciamentopessoa.api.controller;

import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.hateoas.PessoaEndPoints;
import com.gabrielxavier.gerenciamentopessoa.domain.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> adicionarPessoa(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.adicionarPessoa(pessoaRequestDTO);

        PessoaEndPoints.todosLinks(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PessoaResponseDTO>> listarPessoas() {

        CollectionModel<PessoaResponseDTO> pessoaResponseDTOS = pessoaService.listarPessoas();

        pessoaResponseDTOS.forEach(PessoaEndPoints::pessoaSelfLink);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.buscarPorId(id);

        PessoaEndPoints.todosLinks(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pessoaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO pessoaRequestDTO) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.atualizarPessoa(id, pessoaRequestDTO);

        PessoaEndPoints.pessoaSelfLink(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {

        pessoaService.deletarPessoaPorId(id);

        return ResponseEntity.noContent().build();
    }
}
