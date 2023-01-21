package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {

    EnderecoResponseDTO adicionarEndereco(Long PessoaId, EnderecoRequestDTO enderecoRequestDTO);

    List<EnderecoResponseDTO> listarTodosEnderecos(Long pessoaId);

    EnderecoResponseDTO mostrarEnderecoPrincipal(Long PessoaId, EnderecoRequestDTO enderecoRequestDTO);
}
