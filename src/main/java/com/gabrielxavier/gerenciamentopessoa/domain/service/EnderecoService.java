package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import org.springframework.hateoas.CollectionModel;

public interface EnderecoService {

    EnderecoResponseDTO adicionarEndereco(Long PessoaId, EnderecoRequestDTO enderecoRequestDTO);

    CollectionModel<EnderecoResponseDTO> listarTodosEnderecos(Long pessoaId);

    EnderecoResponseDTO mostrarEnderecoPrincipal(Long PessoaId);

    void deletarEnderecoPorId(Long pessoaId, Long enderecoId);
}
