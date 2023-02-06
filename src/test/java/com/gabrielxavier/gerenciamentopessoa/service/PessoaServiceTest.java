package com.gabrielxavier.gerenciamentopessoa.service;

import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.common.mapper.MapStructMapperImpl;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.PessoaNaoEncontradaException;
import com.gabrielxavier.gerenciamentopessoa.domain.repository.PessoaRepository;
import com.gabrielxavier.gerenciamentopessoa.domain.service.PessoaService;
import com.gabrielxavier.gerenciamentopessoa.util.PessoaCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private MapStructMapperImpl mapStructMapper;

    public List<Pessoa> pessoaList;

    @BeforeEach
    void setup() {
        pessoaList = List.of(PessoaCreator.criarPessoa1(), PessoaCreator.criarPessoa2());

        when(pessoaRepository.findAll()).thenReturn(pessoaList);

        when(pessoaRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(PessoaCreator.criarPessoa1()));

        when(pessoaRepository.save(PessoaCreator.criarPessoa1())).thenReturn(PessoaCreator.criarPessoa1());

        when(mapStructMapper.pessoaToPessoaResponseDto(PessoaCreator.criarPessoa1()))
                .thenReturn(PessoaCreator.criarPessoaResponseDto1());

        when(mapStructMapper.pessoaToPessoaResponseDto(PessoaCreator.criarPessoa2()))
                .thenReturn(PessoaCreator.criarPessoaResponseDto2());

        when(mapStructMapper.pessoaRequestDtoToPessoa(PessoaCreator.criarPessoaRequestDto1()))
                .thenReturn(PessoaCreator.criarPessoa1());
    }

    @Test
    @DisplayName("listarPessoas retorna um CollectionModel de pessoas quando sucesso")
    void listarPessoas_RetornaUmCollectionModelPessoas_QuandoSucesso() {

        Long idEsperado = pessoaList.get(0).getId();
        String nomeCompletoEsperado = pessoaList.get(0).getNomeCompleto();
        LocalDate dataAniversarioEsperada = pessoaList.get(0).getDataNascimento();
        String nomeCompletoEsperado2 = pessoaList.get(1).getNomeCompleto();

        CollectionModel<PessoaResponseDTO> pessoaResponseDTOS = pessoaService.listarPessoas();
        List<PessoaResponseDTO> pessoaResponseDTOList = pessoaResponseDTOS.getContent().stream().collect(Collectors.toList());

        assertThat(pessoaResponseDTOList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        assertThat(pessoaResponseDTOList.get(0).getId()).isEqualTo(idEsperado);
        assertThat(pessoaResponseDTOList.get(0).getNomeCompleto()).isEqualTo(nomeCompletoEsperado);
        assertThat(pessoaResponseDTOList.get(0).getDataNascimento()).isEqualTo(dataAniversarioEsperada);
        assertThat(pessoaResponseDTOList.get(1).getNomeCompleto()).isEqualTo(nomeCompletoEsperado2);
    }

    @Test
    @DisplayName("buscarPorId retorna uma pessoaResponseDTO quando sucesso")
    void buscarPorId_RetornaUmaPessoaResponseDTO_QuandoSucesso() {

        Long idEsperado = PessoaCreator.criarPessoaResponseDto1().getId();

        PessoaResponseDTO pessoaResponseDTO = pessoaService.buscarPorId(1L);

        assertThat(pessoaResponseDTO).isNotNull();
        assertThat(pessoaResponseDTO.getNomeCompleto()).isEqualTo(PessoaCreator.criarPessoaResponseDto1().getNomeCompleto());
        assertThat(pessoaResponseDTO.getId()).isEqualTo(idEsperado);
    }

    @Test
    @DisplayName("buscarPorId retorna PessoaNaoEncontradaException quando pessoa não existe")
    void buscarPorId_RetornaPessoaNaoEncontradaExcepetion_QuandoPessoaNaoExiste() {

        when(pessoaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(PessoaNaoEncontradaException.class).isThrownBy(() -> pessoaService.buscarPorId(2L))
                .withMessage("Pessoa não encontrada");
    }

    @Test
    @DisplayName("adicionarPessoa retorna PessoaResponseDTO quando sucesso")
    void adicionarPessoa_RetornaPessoaResponseDTO_QuandoSucesso() {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.adicionarPessoa(PessoaCreator.criarPessoaRequestDto1());

        assertThat(pessoaResponseDTO).isNotNull();

        System.out.println(pessoaResponseDTO.getNomeCompleto());
        System.out.println(pessoaResponseDTO.getId());
    }
}
