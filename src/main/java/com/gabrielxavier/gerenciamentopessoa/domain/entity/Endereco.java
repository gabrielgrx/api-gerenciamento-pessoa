package com.gabrielxavier.gerenciamentopessoa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabrielxavier.gerenciamentopessoa.domain.entity.enums.TipoEndereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$")
    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    @JsonIgnore
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "tipo_endereco")
    private TipoEndereco tipoEndereco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
