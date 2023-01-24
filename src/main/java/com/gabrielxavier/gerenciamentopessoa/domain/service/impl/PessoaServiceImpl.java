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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapStructMapperImpl mapStructMapper;

    @Transactional
    @Override
    public PessoaResponseDTO adicionarPessoa(PessoaRequestDTO pessoaRequestDTO) {

        if (nomeExiste(pessoaRequestDTO)) {
            throw new NegocioException("Já existe uma pessoa cadastrada com este nome.");
        }

        Pessoa pessoa = mapStructMapper.pessoaRequestDtoToPessoa(pessoaRequestDTO);

        pessoaRepository.save(pessoa);
        return mapStructMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Transactional
    @Override
    public CollectionModel<PessoaResponseDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return CollectionModel.of(pessoas.stream()
                .map(p -> mapStructMapper.pessoaToPessoaResponseDto(p)).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public PessoaResponseDTO buscarPorId(Long id) {
        Pessoa pessoa = findByIdPessoa(id);
        return mapStructMapper.pessoaToPessoaResponseDto(pessoa);
    }

    @Transactional
    @Override
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO) {

        Pessoa pessoaParaAtualizar = findByIdPessoa(id);

        if (nomeExiste(pessoaRequestDTO) && !pessoaRequestDTO.getNome().equals(pessoaParaAtualizar.getNome())) {
            throw new NegocioException("Já existe uma pessoa cadastrada com este nome.");
        }

        atualizarDados(pessoaParaAtualizar, pessoaRequestDTO);
        pessoaRepository.save(pessoaParaAtualizar);

        return mapStructMapper.pessoaToPessoaResponseDto(pessoaParaAtualizar);
    }

    @Transactional
    @Override
    public void deletarPessoaPorId(Long id) {
        findByIdPessoa(id);
        pessoaRepository.deleteById(id);
    }


    private void atualizarDados(Pessoa pessoa, PessoaRequestDTO pessoaRequestDTO) {
        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setDataNascimento(pessoaRequestDTO.getDataNascimento());
    }

    private boolean nomeExiste(PessoaRequestDTO pessoaRequestDTO) {
        return pessoaRepository.findByNome(pessoaRequestDTO.getNome())
                .stream()
                .anyMatch(pessoa -> !pessoa.equals(pessoaRequestDTO.getNome()));
    }

    private Pessoa findByIdPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));
    }
}
