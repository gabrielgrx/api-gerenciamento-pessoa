package com.gabrielxavier.gerenciamentopessoa.domain.repository;

import com.gabrielxavier.gerenciamentopessoa.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    boolean existsByLogradouro(String logradouro);

    boolean existsByNumero(String numero);

}
