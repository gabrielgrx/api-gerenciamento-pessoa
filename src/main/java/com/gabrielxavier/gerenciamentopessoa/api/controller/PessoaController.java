package com.gabrielxavier.gerenciamentopessoa.api.controller;

import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dtos.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import com.gabrielxavier.gerenciamentopessoa.domain.service.impl.PessoaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaServiceImpl pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> adicionarPessoa(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.adicionarPessoa(pessoaRequestDTO);

        todosLinks(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PessoaResponseDTO>> listarPessoas() {

        CollectionModel<PessoaResponseDTO> pessoaResponseDTOS = pessoaService.listarPessoas();

        pessoaResponseDTOS.forEach(this::pessoaSelfLink);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.buscarPorId(id);

        todosLinks(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pessoaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaRequestDTO pessoaRequestDTO) {

        PessoaResponseDTO pessoaResponseDTO = pessoaService.atualizarPessoa(id, pessoaRequestDTO);

        pessoaSelfLink(pessoaResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {

        pessoaService.deletarPessoaPorId(id);

        return ResponseEntity.noContent().build();
    }

    private void todosLinks(PessoaResponseDTO pessoaResponseDTO) {
        pessoasLink(pessoaResponseDTO);
        pessoaSelfLink(pessoaResponseDTO);
        pessoaAtualizarLink(pessoaResponseDTO);
        pessoaDeletarLink(pessoaResponseDTO);
        enderecoPrincipalLink(pessoaResponseDTO);
        enderecosLinks(pessoaResponseDTO);
    }

    private void pessoaSelfLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).buscarPorId(pessoaResponseDTO.getId())).withSelfRel());
    }

    private void pessoasLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).listarPessoas()).withRel("lista de pessoas"));
    }

    private void pessoaAtualizarLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(PessoaController.class).slash(pessoaResponseDTO.getId()).withRel("Atualizar Pessoa"));
    }

    private void pessoaDeletarLink(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(PessoaController.class).deletarPessoa(pessoaResponseDTO.getId())).withRel("DeletarPessoa"));
    }

    private void enderecosLinks(PessoaResponseDTO pessoaResponseDTO) {
        pessoaResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                .listarEnderecos(pessoaResponseDTO.getId())).withRel("lista de endereços desta pessoa"));
    }

    private void enderecoPrincipalLink(PessoaResponseDTO pessoaResponseDTO) {
        List<Endereco> enderecos = pessoaResponseDTO.getEnderecos();
        if (enderecos != null) {
            for (Endereco e : enderecos) {
                if (e.getTipoEndereco() == TipoEndereco.PRINCIPAL) {
                    pessoaResponseDTO.add(linkTo(methodOn(EnderecoController.class)
                            .mostrarEnderecoPrincipal(pessoaResponseDTO.getId()))
                            .withRel("endereço principal"));
                }
            }
        }
    }
}
