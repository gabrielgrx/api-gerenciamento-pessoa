package com.gabrielxavier.gerenciamentopessoa.domain.service.impl;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.EnderecoRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.service.EnderecoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapStructMapperImpl mapStructMapper;

    @Transactional
    @Override
    public EnderecoResponseDTO adicionarEndereco(Long pessoaId, EnderecoRequestDTO enderecoRequestDTO) {
        Pessoa pessoa = findByIdPessoa(pessoaId);
        Endereco endereco = mapStructMapper.enderecoRequestDtoToEndereco(enderecoRequestDTO);
        endereco.setPessoa(pessoa);
        enderecoRepository.save(endereco);
        return mapStructMapper.enderecoToenderecoResponseDto(endereco);
    }

    @Transactional
    @Override
    public List<EnderecoResponseDTO> listarTodosEnderecos(Long pessoaId) {
        Pessoa pessoa = findByIdPessoa(pessoaId);
        List<Endereco> enderecos = pessoa.getEnderecos();
        return enderecos.stream()
                .map(e -> mapStructMapper.enderecoToenderecoResponseDto(e)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EnderecoResponseDTO mostrarEnderecoPrincipal(Long pessoaId, EnderecoRequestDTO enderecoRequestDTO) {
        List<EnderecoResponseDTO> listaEnderecos = listarTodosEnderecos(pessoaId);
        for (EnderecoResponseDTO enderecoBuscado : listaEnderecos) {
            if (enderecoBuscado.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)) {
                return enderecoBuscado;
            }
        }
        throw new NegocioException("Esta pessao não tem um endereço principal registrado");
    }

    private Pessoa findByIdPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));
    }
}
