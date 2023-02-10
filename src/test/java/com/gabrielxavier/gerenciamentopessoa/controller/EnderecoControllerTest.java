package com.gabrielxavier.gerenciamentopessoa.controller;

import com.gabrielxavier.gerenciamentopessoa.api.controller.EnderecoController;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.EnderecoResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enumclass.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.service.EnderecoService;
import com.gabrielxavier.gerenciamentopessoa.util.EnderecoCreator;
import com.gabrielxavier.gerenciamentopessoa.util.PessoaCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EnderecoControllerTest {

    @InjectMocks
    EnderecoController enderecoController;

    @Mock
    EnderecoService enderecoService;

    CollectionModel<EnderecoResponseDTO> enderecoResponseDTOList;

    EnderecoResponseDTO enderecoResponseDTO;

    EnderecoRequestDTO enderecoRequestDTO;

    Pessoa pessoa;

    @BeforeEach
    void setup() {
        enderecoResponseDTOList = CollectionModel.of(List
                .of(EnderecoCreator.criarEnderecoResponseDto1(), EnderecoCreator.criarEnderecoResponseDto2()));

        pessoa = PessoaCreator.criarPessoa1();

        enderecoResponseDTO = EnderecoCreator.criarEnderecoResponseDto1();

        enderecoRequestDTO = EnderecoCreator.criarEnderecoRequestDto1();

        when(enderecoService.listarTodosEnderecos(anyLong())).thenReturn(enderecoResponseDTOList);

        when(enderecoService.mostrarEnderecoPrincipal(anyLong())).thenReturn(enderecoResponseDTO);

        when(enderecoService.adicionarEndereco(1L, enderecoRequestDTO)).thenReturn(enderecoResponseDTO);

        doNothing().when(enderecoService).deletarEnderecoPorId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("listarEnderecos retorna uma lista de enderecos quando sucesso")
    void listarEnderecos_RetornaUmaListaDeEnderecos_QuandoSucesso() {

        ResponseEntity<CollectionModel<EnderecoResponseDTO>> response = enderecoController.listarEnderecos(pessoa.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    @DisplayName("mostrarEnderecoPricnipal retorna endereço principal quando sucesso")
    void mostrarEnderecoPrincipal_RetornaEnderecoPrincipal_QuandoSucesso() {

        ResponseEntity<EnderecoResponseDTO> response = enderecoController.mostrarEnderecoPrincipal(1L);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(TipoEndereco.PRINCIPAL, response.getBody().getTipoEndereco());
    }

    @Test
    @DisplayName("adicionarEndereco retorna um EnderecoResponseDTO quando sucesso")
    void adicionarEndereco_RetornaUmEnderecoResponseDTO_QuandoSucesso() {

        ResponseEntity<EnderecoResponseDTO> response = enderecoController.adicionarEndereco(1L, enderecoRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(enderecoResponseDTO, response.getBody());
    }

    @Test
    @DisplayName("atualizarEndereco retorna um EnderecoResponseDTO quando sucesso")
    void atualizarEndereco_RetornaUmEnderecoResponseDTO_QuandoSucesso() {

        EnderecoRequestDTO enderecoRequestDTOAtualizar = EnderecoCreator.atualizarEnderecoRequestDto1();
        EnderecoResponseDTO enderecoResponseDTOAtualizar = EnderecoCreator.atualizarEnderecoResponseDto1();

        when(enderecoService.atualizarEndereco(1L, 1L, enderecoRequestDTOAtualizar))
                .thenReturn(enderecoResponseDTOAtualizar);

        ResponseEntity<EnderecoResponseDTO> response = enderecoController.atualizarEndereco(1L, 1L, enderecoRequestDTOAtualizar);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecoResponseDTOAtualizar, response.getBody());
    }

    @Test
    @DisplayName("deletarEndereco retorna void quando sucesso")
    void deletarEndereco_RetornaVoid_QuandoSucesso() {

        ResponseEntity<Void> response = enderecoController.deletarEndereco(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
