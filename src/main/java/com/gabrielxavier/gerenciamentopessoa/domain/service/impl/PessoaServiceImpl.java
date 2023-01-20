package com.gabrielxavier.gerenciamentopessoa.domain.service.impl;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.service.PessoaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapStructMapperImpl pessoaMapper;

    @Transactional
    @Override
    public PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO) {

        if (nomeExiste(pessoaRequestDTO)) {
            throw new NegocioException("Já existe uma pessoa cadastrada com este nome.");
        }

        Pessoa pessoa = pessoaMapper.pessoaRequestDtoToPessoa(pessoaRequestDTO);

        if (pessoaRequestDTO.getEnderecos() != null) {
            pessoa.getEnderecos().forEach(e -> e.setPessoa(pessoa));
        }

        pessoaRepository.save(pessoa);
        return pessoaMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Transactional
    @Override
    public List<PessoaResponseDTO> listarTodos() {
        List<Pessoa> list = pessoaRepository.findAll();
        return list.stream()
                .map(p -> pessoaMapper.pessoaToPessoaResponseDto(p)).collect(Collectors.toList());
    }

    @Override
    public PessoaResponseDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));
        return pessoaMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Override
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO) {

        Pessoa pessoaParaAtualizar = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        atualizarDados(pessoaParaAtualizar, pessoaRequestDTO);
        pessoaRepository.save(pessoaParaAtualizar);

        return pessoaMapper.pessoaToPessoaResponseDto(pessoaParaAtualizar);
    }

    private void atualizarDados(Pessoa pessoa, PessoaRequestDTO pessoaRequestDTO) {
        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());
        pessoa.setEnderecos(pessoaRequestDTO.getEnderecos());
    }

    private boolean nomeExiste(PessoaRequestDTO pessoaRequestDTO) {
        return pessoaRepository.findByNome(pessoaRequestDTO.getNome())
                .stream()
                .anyMatch(pessoa -> !pessoa.equals(pessoaRequestDTO.getNome()));
    }

}
