package com.gabrielxavier.gerenciamentopessoa.domain.repository;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByNomeCompleto(String nome);

    boolean existsByNomeCompleto(String nomeCompleto);
}
