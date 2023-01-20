package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO);

    List<PessoaResponseDTO> listarTodos();

    PessoaResponseDTO buscarPorId(Long id);
}
