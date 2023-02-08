package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapStructMapperImpl mapStructMapper;

    @Transactional
    public PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO) {
        if (pessoaRepository.existsByNomeCompleto(pessoaRequestDTO.getNomeCompleto())) {
            throw new NegocioException("Já existe uma pessoa cadastrada com este nome.");
        }

        Pessoa pessoa = mapStructMapper.pessoaRequestDtoToPessoa(pessoaRequestDTO);

        pessoaRepository.save(pessoa);

        return mapStructMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Transactional
    public CollectionModel<PessoaResponseDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return CollectionModel.of(pessoas.stream()
                .map(p -> mapStructMapper.pessoaToPessoaResponseDto(p)).collect(Collectors.toList()));
    }

    @Transactional
    public PessoaResponseDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        return mapStructMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Transactional
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoaParaAtualizar = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        if (pessoaRepository.existsByNomeCompleto(pessoaRequestDTO.getNomeCompleto()) && !pessoaRequestDTO.getNomeCompleto().equals(pessoaParaAtualizar.getNomeCompleto())) {
            throw new NegocioException("Já existe uma pessoa cadastrada com este nome");
        }

        if (pessoaRequestDTO.getNomeCompleto() == null || pessoaRequestDTO.getDataNascimento() == null) {
            throw new NegocioException("Os campos não podem ser nulos");
        }

        pessoaParaAtualizar.setNomeCompleto(pessoaRequestDTO.getNomeCompleto());
        pessoaParaAtualizar.setDataNascimento(pessoaRequestDTO.getDataNascimento());

        pessoaRepository.save(pessoaParaAtualizar);

        return mapStructMapper.pessoaToPessoaResponseDto(pessoaParaAtualizar);
    }

    @Transactional
    public void deletarPessoaPorId(Long id) {
        pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        pessoaRepository.deleteById(id);
    }
}
