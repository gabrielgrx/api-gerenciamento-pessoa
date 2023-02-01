package com.gabrielxavier.gerenciamentopessoa.domain.service;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.EnderecoNaoEncontradoException;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.EnderecoRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MapStructMapperImpl mapStructMapper;

    @Transactional
    public EnderecoResponseDTO adicionarEndereco(Long pessoaId, EnderecoRequestDTO enderecoRequestDTO) {

        try {
            Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findById(pessoaId)
                    .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não existe")));

            if (enderecoRepository.existsByLogradouro(enderecoRequestDTO.getLogradouro())
                    && enderecoRepository.existsByNumero(enderecoRequestDTO.getNumero())) {
                throw new NegocioException("Este endereço já está cadastrado para essa pessoa");
            }

            atualizaEnderecoParaPrincipal(pessoaId, enderecoRequestDTO);

            Endereco endereco = mapStructMapper.enderecoRequestDtoToEndereco(enderecoRequestDTO);
            endereco.setPessoa(pessoa.get());
            enderecoRepository.save(endereco);

            return mapStructMapper.enderecoToenderecoResponseDto(endereco);

        } catch (ConstraintViolationException e) {
            throw new NegocioException("O CEP deve ser no formato xxxxx-xxx");
        }
    }

    @Transactional
    public CollectionModel<EnderecoResponseDTO> listarTodosEnderecos(Long pessoaId) {

        Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não existe")));

        return CollectionModel.of(pessoa.get().getEnderecos().stream()
                .map(e -> mapStructMapper.enderecoToenderecoResponseDto(e)).collect(Collectors.toList()));
    }

    @Transactional
    public EnderecoResponseDTO mostrarEnderecoPrincipal(Long pessoaId) {

        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);

        List<Endereco> enderecos = pessoa.get().getEnderecos();

        for (Endereco enderecoBuscado : enderecos) {
            if (enderecoBuscado.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)) {
                return mapStructMapper.enderecoToenderecoResponseDto(enderecoBuscado);
            }
        }

        throw new NegocioException("Essa pessoa não tem um endereço principal registrado");
    }

    @Transactional
    public void deletarEnderecoPorId(Long pessoaId, Long enderecoId) {

        pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não existe"));

        enderecoRepository.findById(enderecoId).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não existe"));

        enderecoRepository.deleteById(enderecoId);
    }

    @Transactional
    public EnderecoResponseDTO atualizarEndereco(Long pessoaId, Long enderecoId, EnderecoRequestDTO enderecoRequestDTO) {

        Optional<Endereco> enderecoParaAtualizar = enderecoRepository.findById(enderecoId);

        atualizaEnderecoParaPrincipal(pessoaId, enderecoRequestDTO);

        atualizarDadosEndereco(enderecoParaAtualizar.get(), enderecoRequestDTO);

        enderecoRepository.save(enderecoParaAtualizar.get());

        return mapStructMapper.enderecoToenderecoResponseDto(enderecoParaAtualizar.get());
    }

    private void atualizaEnderecoParaPrincipal(Long pessoaId, EnderecoRequestDTO enderecoRequestDTO) {

        if (enderecoRequestDTO.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)) {

            Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);

            List<Endereco> enderecos = (pessoa.get().getEnderecos());

            enderecos.forEach(e -> {
                if (e.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)) {
                    e.setTipoEndereco(TipoEndereco.SECUNDARIO);
                }
            });
        }
    }

    private void atualizarDadosEndereco(Endereco endereco, EnderecoRequestDTO enderecoRequestDTO) {

        endereco.setLogradouro(enderecoRequestDTO.getLogradouro());
        endereco.setNumero(enderecoRequestDTO.getNumero());
        endereco.setCep(enderecoRequestDTO.getCep());
        endereco.setCidade(enderecoRequestDTO.getCidade());
        endereco.setTipoEndereco(enderecoRequestDTO.getTipoEndereco());
    }
}
