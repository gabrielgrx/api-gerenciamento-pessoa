package com.gabrielxavier.gerenciamentopessoa.service;

import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaResponseDTO;

import java.util.List;

public interface PessoaService {

    PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO);

    List<PessoaResponseDTO> listarTodos();
}
