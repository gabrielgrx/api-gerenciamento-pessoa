package com.gabrielxavier.gerenciamentopessoa.domain.repository;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByNome(String nome);
}
