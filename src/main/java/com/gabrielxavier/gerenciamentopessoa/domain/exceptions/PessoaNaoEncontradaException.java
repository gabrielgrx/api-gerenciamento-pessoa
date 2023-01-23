package com.gabrielxavier.gerenciamentopessoa.domain.exceptions;

public class PessoaNaoEncontradaException extends NegocioException {

    public PessoaNaoEncontradaException(String msg) {
        super(msg);
    }
}
