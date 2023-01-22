package com.gabrielxavier.gerenciamentopessoa.domain.service.impl;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.EnderecoNaoEncontradoException;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.EnderecoRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.service.EnderecoService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
        try {
            Pessoa pessoa = findByIdPessoa(pessoaId);

            if (enderecoExiste(pessoa, enderecoRequestDTO)) {
                throw new NegocioException("Este endereço já está cadastrado para essa pessoa");
            }

            Endereco endereco = mapStructMapper.enderecoRequestDtoToEndereco(enderecoRequestDTO);

            endereco.setPessoa(pessoa);
            enderecoRepository.save(endereco);
            return mapStructMapper.enderecoToenderecoResponseDto(endereco);

        } catch (ConstraintViolationException e) {
            throw new NegocioException("O CEP deve ser no formato 00000-000");
        }
    }

    @Transactional
    @Override
    public CollectionModel<EnderecoResponseDTO> listarTodosEnderecos(Long pessoaId) {
        Pessoa pessoa = findByIdPessoa(pessoaId);
        return CollectionModel.of(pessoa.getEnderecos().stream()
                .map(e -> mapStructMapper.enderecoToenderecoResponseDto(e)).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public EnderecoResponseDTO mostrarEnderecoPrincipal(Long pessoaId) {
        List<EnderecoResponseDTO> listaEnderecos = (List<EnderecoResponseDTO>) listarTodosEnderecos(pessoaId);
        for (EnderecoResponseDTO enderecoBuscado : listaEnderecos) {
            if (enderecoBuscado.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)) {
                return enderecoBuscado;
            }
        }
        throw new NegocioException("Essa pessoa não tem um endereço principal registrado");
    }

    @Transactional
    @Override
    public void deletarEnderecoPorId(Long pessoaId, Long enderecoId) {
        findByIdPessoa(pessoaId);
        findByIdEndereco(enderecoId);
        enderecoRepository.deleteById(enderecoId);
    }

    @Transactional
    @Override
    public EnderecoResponseDTO atualizarEndereco(Long pessoaid, Long enderecoId, EnderecoRequestDTO enderecoRequestDTO) {

        findByIdPessoa(pessoaid);

        Endereco enderecoParaAtualizar = findByIdEndereco(enderecoId);

        atualizarDadosEndereco(enderecoParaAtualizar, enderecoRequestDTO);
        enderecoRepository.save(enderecoParaAtualizar);

        return mapStructMapper.enderecoToenderecoResponseDto(enderecoParaAtualizar);
    }

    private Pessoa findByIdPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));
    }

    private Endereco findByIdEndereco(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não existe"));
    }

    private boolean enderecoExiste(Pessoa pessoa, EnderecoRequestDTO enderecoRequestDTO) {
        List<Endereco> pessoaEnderecos = pessoa.getEnderecos();
        for (Endereco endereco : pessoaEnderecos) {
            if (endereco.getLogradouro().equals(enderecoRequestDTO.getLogradouro())) {
                if (endereco.getNumero().equals(enderecoRequestDTO.getNumero())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void atualizarDadosEndereco(Endereco endereco, EnderecoRequestDTO enderecoRequestDTO) {
        endereco.setLogradouro(enderecoRequestDTO.getLogradouro());
        endereco.setNumero(enderecoRequestDTO.getNumero());
        endereco.setCep(enderecoRequestDTO.getCep());
        endereco.setCidade(enderecoRequestDTO.getCidade());
        endereco.setTipoEndereco(enderecoRequestDTO.getTipoEndereco());
    }
}
