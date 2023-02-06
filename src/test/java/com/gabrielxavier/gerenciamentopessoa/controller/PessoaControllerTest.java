package com.gabrielxavier.gerenciamentopessoa.controller;

import com.gabrielxavier.gerenciamentopessoa.api.controller.PessoaController;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.exception.PessoaNaoEncontradaException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        List<PessoaResponseDTO> pessoaResponseDTOList = List.of(PessoaCreator.criarPessoaResponseDto1());
        CollectionModel<PessoaResponseDTO> pessoaResponseDTOCollectionModel = CollectionModel.of(pessoaResponseDTOList);

        when(pessoaService.listarPessoas()).thenReturn(pessoaResponseDTOCollectionModel);

        when(pessoaService.buscarPorId(ArgumentMatchers.anyLong())).thenReturn(PessoaCreator.criarPessoaResponseDto1());

        when(pessoaService.adicionarPessoa(ArgumentMatchers.any(PessoaRequestDTO.class)))
                .thenReturn(PessoaCreator.criarPessoaResponseDto1());
    }

    @Test
    @DisplayName("listarPessoas retorna um CollectionModel de pessoas quando sucesso")
    void listarPessoas_RetornaUmCollectionModelPessoas_QuandoSucesso () throws Exception {
        String nomeCompletoEsperado = PessoaCreator.criarPessoaResponseDto1().getNomeCompleto();
        String dataAniversarioEsperada = String.valueOf(PessoaCreator.criarPessoaResponseDto1().getDataNascimento());

        CollectionModel<PessoaResponseDTO> collectionModel = pessoaController.listarPessoas().getBody();
        List<PessoaResponseDTO> pessoaResponseDTOList = new ArrayList<>(collectionModel.getContent());

        assertThat(pessoaResponseDTOList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertEquals(nomeCompletoEsperado, pessoaResponseDTOList.get(0).getNomeCompleto());
        Assertions.assertEquals(dataAniversarioEsperada, pessoaResponseDTOList.get(0).getDataNascimento().toString());
    }

    @Test
    @DisplayName("buscarPorId retorna uma pessoaResponseDTO quando sucesso")
    void buscarPorId_RetornaumaPessoaResponseDTO_QuandoSucesso () {
        Long iDEsperado = PessoaCreator.criarPessoaResponseDto1().getId();

        PessoaResponseDTO pessoaResponseDTO = pessoaController.buscarPorId(1L).getBody();

        assertThat(pessoaResponseDTO).isNotNull();

        Assertions.assertEquals(iDEsperado, pessoaResponseDTO.getId());
    }

    @Test
    @DisplayName("buscarPorId retorna PessoaNaoEncontradaException quando pessoa não existe")
    void buscarPorId_RetornaPessoaNaoEncontradaExcepetion_QuandoPessoaNaoExiste () {
        when(pessoaService.buscarPorId(2L)).thenThrow(new PessoaNaoEncontradaException("Pessoa não encontrada"));

        PessoaNaoEncontradaException pessoaNaoEncontradaException = Assertions
                .assertThrows(PessoaNaoEncontradaException.class, () -> pessoaController.buscarPorId(2L));

        assertThat(pessoaNaoEncontradaException.getMessage()).isEqualTo("Pessoa não encontrada");
    }

//    @Test
//    @DisplayName("adicionarPessoa adiciona uma pessoa e retorna pessoaResponseDTO quando sucesso")
//    void adicionarPessoa_AdicionaUmaPessoaERetornaPessoaResponseDTO_QuandoSucesso () {
//        Long iDEsperado = PessoaCreator.criarPessoaResponseDto().getId();
//
//        PessoaResponseDTO pessoaResponseDTO = pessoaController.adicionarPessoa(PessoaCreator.criarPessoaRequestDto()).getBody();
//
//        assertThat(pessoaResponseDTO)
//                .isNotNull()
//                .isEqualTo(PessoaCreator.criarPessoaResponseDto());
//
//        Assertions.assertEquals(iDEsperado, pessoaResponseDTO.getId());
//    }
}
