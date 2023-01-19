package com.gabrielxavier.gerenciamentopessoa.repository;

import com.gabrielxavier.gerenciamentopessoa.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
