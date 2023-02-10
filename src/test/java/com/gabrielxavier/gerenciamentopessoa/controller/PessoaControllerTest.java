package com.gabrielxavier.gerenciamentopessoa.controller;

import com.gabrielxavier.gerenciamentopessoa.api.controller.PessoaController;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.service.PessoaService;
import com.gabrielxavier.gerenciamentopessoa.util.PessoaCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PessoaControllerTest {

    @InjectMocks
    PessoaController pessoaController;

    @Mock
    PessoaService pessoaService;

    CollectionModel<PessoaResponseDTO> pessoaResponseList;

    PessoaRequestDTO pessoaRequestDTO;

    PessoaResponseDTO pessoaResponseDTO;

    @BeforeEach
    void setup() {
        pessoaResponseList = CollectionModel.of(List.of(PessoaCreator.criarPessoaResponseDto1(), PessoaCreator.criarPessoaResponseDto2()));
        pessoaRequestDTO = PessoaCreator.criarPessoaRequestDto1();
        pessoaResponseDTO = PessoaCreator.criarPessoaResponseDto1();

        when(pessoaService.listarPessoas()).thenReturn(pessoaResponseList);

        when(pessoaService.buscarPorId(ArgumentMatchers.anyLong())).thenReturn(PessoaCreator.criarPessoaResponseDto1());

        when(pessoaService.adicionarPessoa(pessoaRequestDTO)).thenReturn(pessoaResponseDTO);

        doNothing().when(pessoaService).deletarPessoaPorId(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("listarPessoas retorna uma lista de pessoas quando sucesso")
    void listarPessoas_RetornaUmaListaDePessoas_QuandoSucesso() {

        ResponseEntity<CollectionModel<PessoaResponseDTO>> response = pessoaController.listarPessoas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    @DisplayName("buscarPoirId retorna um PessoaResponseDTO quando sucesso")
    void buscaroPorId_RetornaUmPessoaResponseDTO_QuandoSucesso() {

        PessoaResponseDTO pessoaResponseDTO = PessoaCreator.criarPessoaResponseDto1();

        ResponseEntity<PessoaResponseDTO> response = pessoaController.buscarPorId(pessoaResponseDTO.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(pessoaResponseDTO.getId(), response.getBody().getId());
    }

    @Test
    @DisplayName("adicionarPessoa retorna um PessoaResponseDTO quando sucesso")
    void adicionarPessoa_RetornaUmPessoaResponseDTO_QuandoSucesso() {

        ResponseEntity<PessoaResponseDTO> response = pessoaController.adicionarPessoa(pessoaRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pessoaResponseDTO, response.getBody());
    }

    @Test
    @DisplayName("atualizarPessoa retorna um PessoaResponseDTO quando sucesso")
    void atualizarPessoa_RetornaUmPessoaResponseDTO_QuandoSucesso() {

        PessoaRequestDTO pessoaRequestDTOAtualizar = PessoaCreator.atualizarPessoaRequestDto1();
        PessoaResponseDTO pessoaResponseDTOAtualizar = PessoaCreator.atualizarPessoaResponseDTO1();

        when(pessoaService.atualizarPessoa(1L, pessoaRequestDTOAtualizar)).thenReturn(pessoaResponseDTOAtualizar);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.atualizarPessoa(1L, pessoaRequestDTOAtualizar);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoaResponseDTOAtualizar, response.getBody());
    }

    @Test
    @DisplayName("deletarPessoa retorna void quando sucesso")
    void deletarPessoa_RetornaVoid_QuandoSucesso() {

        ResponseEntity<Void> response = pessoaController.deletarPessoa(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
