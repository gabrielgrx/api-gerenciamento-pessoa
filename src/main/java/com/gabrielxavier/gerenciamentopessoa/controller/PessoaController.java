package com.gabrielxavier.gerenciamentopessoa.controller;

import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.service.impl.PessoaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaServiceImpl pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> adicionarPessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO pessoaResponseDTO = pessoaService.adicionarPessoa(pessoaRequestDTO);
        return ResponseEntity.ok(pessoaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarTodos());
    }
}
