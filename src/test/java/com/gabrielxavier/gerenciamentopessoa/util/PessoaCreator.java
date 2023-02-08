package com.gabrielxavier.gerenciamentopessoa.util;

import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaRequestDTO;
import com.gabrielxavier.gerenciamentopessoa.api.dto.PessoaResponseDTO;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;

import java.time.LocalDate;

public abstract class PessoaCreator {

    public static PessoaRequestDTO criarPessoaRequestDto1() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNomeCompleto("Fulano");
        pessoaRequestDTO.setDataNascimento(LocalDate.parse("1998-10-12"));
        return pessoaRequestDTO;
    }

    public static PessoaRequestDTO criarPessoaRequestDtoCamposNulos() {
        return new PessoaRequestDTO();
    }

    public static PessoaRequestDTO atualizarPessoaRequestDto1() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNomeCompleto("Fulano atualizado");
        pessoaRequestDTO.setDataNascimento(LocalDate.parse("2000-11-21"));
        return pessoaRequestDTO;
    }

    public static PessoaResponseDTO atualizarPessoaResponseDTO1() {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(1L);
        pessoaResponseDTO.setNomeCompleto("Fulano atualizado");
        pessoaResponseDTO.setDataNascimento(LocalDate.parse("2000-11-21"));
        return pessoaResponseDTO;
    }

    public static PessoaResponseDTO criarPessoaResponseDto1() {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(1L);
        pessoaResponseDTO.setNomeCompleto("Fulano");
        pessoaResponseDTO.setDataNascimento(LocalDate.parse("1998-10-12"));
        return pessoaResponseDTO;
    }

    public static PessoaResponseDTO criarPessoaResponseDto2() {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(2L);
        pessoaResponseDTO.setNomeCompleto("Ciclano");
        pessoaResponseDTO.setDataNascimento(LocalDate.parse("2000-11-21"));
        return pessoaResponseDTO;
    }

    public static Pessoa criarPessoa1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Fulano");
        pessoa.setDataNascimento(LocalDate.parse("1998-10-12"));
        return pessoa;
    }

    public static Pessoa criarPessoa2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(2L);
        pessoa.setNomeCompleto("Ciclano");
        pessoa.setDataNascimento(LocalDate.parse("2000-11-21"));
        return pessoa;
    }
}
