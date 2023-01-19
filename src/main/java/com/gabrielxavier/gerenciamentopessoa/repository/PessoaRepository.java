package com.gabrielxavier.gerenciamentopessoa.repository;

import com.gabrielxavier.gerenciamentopessoa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
