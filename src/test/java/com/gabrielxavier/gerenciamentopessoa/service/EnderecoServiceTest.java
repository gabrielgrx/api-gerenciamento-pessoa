package com.gabrielxavier.gerenciamentopessoa.service;

import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.EnderecoNaoEncontradoException;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.EnderecoRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.service.EnderecoService;
import com.gabrielxavier.gerenciamentopessoa.util.EnderecoCreator;
import com.gabrielxavier.gerenciamentopessoa.util.PessoaCreator;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    EnderecoService enderecoService;

    @Mock
    EnderecoRepository enderecoRepository;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    MapStructMapperImpl mapStructMapper;

    Pessoa pessoa;

    Endereco endereco;

    List<Endereco> enderecoList;

    @BeforeEach
    void setup() {
        enderecoList = List.of(EnderecoCreator.criarEndereco1(), EnderecoCreator.criarEndereco2());

        endereco = EnderecoCreator.criarEndereco1();

        pessoa = PessoaCreator.criarPessoa1();
        pessoa.getEnderecos().add(EnderecoCreator.criarEndereco1());
        pessoa.getEnderecos().add(EnderecoCreator.criarEndereco2());

        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        when(enderecoRepository.existsByLogradouro(pessoa.getEnderecos().get(0).getLogradouro()))
                .thenReturn(false);

        when(enderecoRepository.existsByNumero(pessoa.getEnderecos().get(0).getNumero()))
                .thenReturn(false);

        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        when(enderecoRepository.findById(endereco.getId())).thenReturn(Optional.ofNullable(endereco));

        when(mapStructMapper.enderecoToenderecoResponseDto(EnderecoCreator.criarEndereco1()))
                .thenReturn(EnderecoCreator.criarEnderecoResponseDto1());

        when(mapStructMapper.enderecoToenderecoResponseDto(EnderecoCreator.criarEndereco2()))
                .thenReturn(EnderecoCreator.criarEnderecoResponseDto2());

        doNothing().when(enderecoRepository).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("listarTodosEnderecos retorna um CollectionModel de endereços da pessoa quando sucesso")
    void listarTodosEnderecos_RetornaUmCollectionModelEnderecosDaPessoa_QuandoSucesso() {

        Long idEsperado = pessoa.getEnderecos().get(0).getId();
        String logradouroEsperado1 = pessoa.getEnderecos().get(0).getLogradouro();
        TipoEndereco tipoEnderecoEsperado = pessoa.getEnderecos().get(0).getTipoEndereco();
        String logradouroEsperado2 = pessoa.getEnderecos().get(1).getLogradouro();

        CollectionModel<EnderecoResponseDTO> enderecoResponseDTOS = enderecoService.listarTodosEnderecos(pessoa.getId());
        List<EnderecoResponseDTO> enderecoResponseDTOSList = enderecoResponseDTOS.getContent().stream().collect(Collectors.toList());

        assertThat(enderecoResponseDTOS)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        assertThat(enderecoResponseDTOSList.get(0).getId()).isEqualTo(idEsperado);
        assertThat(enderecoResponseDTOSList.get(0).getLogradouro()).isEqualTo(logradouroEsperado1);
        assertThat(enderecoResponseDTOSList.get(0).getTipoEndereco()).isEqualTo(tipoEnderecoEsperado);
        assertThat(enderecoResponseDTOSList.get(1).getLogradouro()).isEqualTo(logradouroEsperado2);
    }

    @Test
    @DisplayName("adicionarEndereco retorna um enderecoResponseDTO quando sucesso")
    void adicionarEndereco_RetornaUmEnderecoResponseDTO_QuandoSucesso() {

        Long idEsperado = PessoaCreator.criarPessoaResponseDto1().getId();
        String logradouroEsperado = pessoa.getEnderecos().get(0).getLogradouro();
        String cepEsperado = pessoa.getEnderecos().get(0).getCep();
        String numeroEsperado = pessoa.getEnderecos().get(0).getNumero();
        String cidadeEsperado = pessoa.getEnderecos().get(0).getCidade();

        EnderecoRequestDTO enderecoRequestDTO = EnderecoCreator.criarEnderecoRequestDto1();

        when(mapStructMapper.enderecoRequestDtoToEndereco(enderecoRequestDTO)).thenReturn(endereco);

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.adicionarEndereco(pessoa.getId(), enderecoRequestDTO);

        assertThat(enderecoResponseDTO).isNotNull();
        assertThat(enderecoResponseDTO.getId()).isEqualTo(idEsperado);
        assertThat(enderecoResponseDTO.getLogradouro()).isEqualTo(logradouroEsperado);
        assertThat(enderecoResponseDTO.getCep()).isEqualTo(cepEsperado);
        assertThat(enderecoResponseDTO.getNumero()).isEqualTo(numeroEsperado);
        assertThat(enderecoResponseDTO.getCidade()).isEqualTo(cidadeEsperado);
    }

    @Test
    @DisplayName("adicionarEndereco retorna NegocioException quando logradouro e numero existem")
    void adicionarEndereco_RetornaNegocioException_QuandoLogradouroENumeroExisem() {

        when(enderecoRepository.existsByLogradouro(pessoa.getEnderecos().get(0).getLogradouro()))
                .thenReturn(true);

        when(enderecoRepository.existsByNumero(pessoa.getEnderecos().get(0).getNumero()))
                .thenReturn(true);

        EnderecoRequestDTO enderecoRequestDTO = EnderecoCreator.criarEnderecoRequestDto1();

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> enderecoService.adicionarEndereco(pessoa.getId(), enderecoRequestDTO))
                .withMessage("Este endereço já está cadastrado para essa pessoa");
    }

    @Test
    @DisplayName("adicionarEndereco retorna NegocioException quando  captura ConstraintViolationException")
    void adicionarEndereco_RetornaNegocioException_QuandoCapturaConstraintViolationException() {

        EnderecoRequestDTO enderecoRequestDTO = EnderecoCreator.criarEnderecoRequestDtoCEPInvalido();

        when(mapStructMapper.enderecoRequestDtoToEndereco(enderecoRequestDTO)).thenReturn(endereco);

        when(enderecoService.adicionarEndereco(pessoa.getId(), enderecoRequestDTO)).thenThrow(ConstraintViolationException.class);

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> enderecoService.adicionarEndereco(pessoa.getId(), enderecoRequestDTO))
                .withMessage("O CEP deve ser no formato xxxxx-xxx");
    }

    @Test
    @DisplayName("mostrarEnderecoPrincipal retorna um EnderecoResponseDTO quando sucesso")
    void mostrarEnderecoPrincipal_RetornaUmEnderecoResponseDTO_QuandoSucesso() {

        Long idEsperado = pessoa.getEnderecos().get(0).getId();
        String logradouroEsperado1 = pessoa.getEnderecos().get(0).getLogradouro();
        TipoEndereco tipoEnderecoEsperado = pessoa.getEnderecos().get(0).getTipoEndereco();

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.mostrarEnderecoPrincipal(pessoa.getId());

        assertThat(enderecoResponseDTO).isNotNull();

        assertThat(enderecoResponseDTO.getId()).isEqualTo(idEsperado);
        assertThat(enderecoResponseDTO.getLogradouro()).isEqualTo(logradouroEsperado1);
        assertThat(enderecoResponseDTO.getTipoEndereco()).isEqualTo(tipoEnderecoEsperado);
    }

    @Test
    @DisplayName("mostrarEnderecoPrincipal retorna NegocioException quando pessoa não possui endereço principal")
    void mostrarEnderecoPrincipal_RetornaNegocioException_QuandoPessoaNaoPossuiEnderecoPrincipal() {

        Endereco enderecoSecundario = EnderecoCreator.criarEndereco2();
        enderecoSecundario.setTipoEndereco(TipoEndereco.SECUNDARIO);

        Pessoa pessoaSemEndrecoPrincipal = PessoaCreator.criarPessoa1();
        pessoaSemEndrecoPrincipal.getEnderecos().add(enderecoSecundario);

        when(pessoaRepository.findById(pessoaSemEndrecoPrincipal.getId())).thenReturn(Optional.of(pessoaSemEndrecoPrincipal));

        assertThatExceptionOfType(NegocioException.class)
                .isThrownBy(() -> enderecoService.mostrarEnderecoPrincipal(pessoaSemEndrecoPrincipal.getId()))
                .withMessage("Essa pessoa não tem um endereço principal registrado");
    }

    @Test
    @DisplayName("atualizarEndereco retorna um enderecoResponseDTO quando sucesso")
    void atualizarEndereco_RetornaUmEnderecoResponseDTO_QuandoSucesso() {

        Long idEsperado = EnderecoCreator.criarEndereco1().getId();
        String logradouroEsperado = EnderecoCreator.atualizarEnderecoRequestDto1().getLogradouro();

        Pessoa pessoaParaAtualizarEndereco = PessoaCreator.criarPessoa1();
        pessoaParaAtualizarEndereco.getEnderecos().add(EnderecoCreator.criarEndereco1());

        EnderecoRequestDTO enderecoRequestDTO = EnderecoCreator.atualizarEnderecoRequestDto1();

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(pessoaParaAtualizarEndereco.getEnderecos().get(0)));

        when(mapStructMapper.enderecoToenderecoResponseDto(pessoaParaAtualizarEndereco.getEnderecos().get(0)))
                .thenReturn(EnderecoCreator.atualizarEnderecoResponseDto1());

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.atualizarEndereco(pessoaParaAtualizarEndereco.getId()
                , 1L, enderecoRequestDTO);

        assertThat(enderecoResponseDTO).isNotNull();
        assertThat(enderecoResponseDTO.getId()).isEqualTo(idEsperado);
        assertThat(enderecoResponseDTO.getLogradouro()).isEqualTo(logradouroEsperado);
    }

    @Test
    @DisplayName("deletarEndercoPorId retorna vazio quando sucesso")
    void deletarEnderecoPorId_RetornaVazio_QuandoSucesso() {

        assertThatCode(() -> enderecoService.deletarEnderecoPorId(pessoa.getId(), endereco.getId())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("deletarEndercoPorId retorna EnderecoNaoEncontradoException quando endereco nao existe")
    void deletarEnderecoPorId_RetornaEnderecoNaoEncontradoException_QuandoEnderecoNaoExiste() {

        assertThatExceptionOfType(EnderecoNaoEncontradoException.class)
                .isThrownBy(() -> enderecoService.deletarEnderecoPorId(1L, 3L))
                .withMessage("Endereço não existe");
    }
}
