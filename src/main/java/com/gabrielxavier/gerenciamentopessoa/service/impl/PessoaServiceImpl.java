package com.gabrielxavier.gerenciamentopessoa.service.impl;

import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.service.PessoaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Pessoa pessoa = pessoaMapper.pessoaRequestDtoToPessoa(pessoaRequestDTO);
        pessoa.getEnderecos().forEach(e -> e.setPessoa(pessoa));
        pessoaRepository.save(pessoa);
        PessoaResponseDTO pessoaResponseDTO = pessoaMapper.pessoaToPessoaResponseDto(pessoa);
        return pessoaResponseDTO;
    }

    @Override
    public List<PessoaResponseDTO> listarTodos() {
        List<Pessoa> list = pessoaRepository.findAll();
        List<PessoaResponseDTO> pessoaResponseDTOS = list.stream().map(p -> pessoaMapper.pessoaToPessoaResponseDto(p)).collect(Collectors.toList());
        return pessoaResponseDTOS;
    }
}
