package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

public interface PessoaService {

    PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO);

    CollectionModel<PessoaResponseDTO> listarPessoas();

    PessoaResponseDTO buscarPorId(Long id);

    PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO);

    void deletarPessoaPorId(Long id);
}
